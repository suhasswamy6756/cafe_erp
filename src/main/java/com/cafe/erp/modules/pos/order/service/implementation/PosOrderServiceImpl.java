package com.cafe.erp.modules.pos.order.service.implementation;


import com.cafe.erp.common.exception.ResourceNotFoundException;
import com.cafe.erp.modules.admin.location.entity.Location;
import com.cafe.erp.modules.admin.location.repository.LocationsRepository;
import com.cafe.erp.modules.inventory.recipe.entity.RecipeCostAudit;
import com.cafe.erp.modules.inventory.recipe.entity.RecipeItems;
import com.cafe.erp.modules.inventory.recipe.entity.RecipeVersions;
import com.cafe.erp.modules.inventory.recipe.entity.Recipes;
import com.cafe.erp.modules.inventory.recipe.repository.RecipeCostAuditRepository;
import com.cafe.erp.modules.inventory.recipe.repository.RecipeItemRepository;
import com.cafe.erp.modules.inventory.recipe.repository.RecipeRepository;
import com.cafe.erp.modules.inventory.recipe.repository.RecipeVersionRepository;
import com.cafe.erp.modules.inventory.stock.entity.Stock;
import com.cafe.erp.modules.inventory.stock.repository.StockRepository;
import com.cafe.erp.modules.pos.order.dto.*;
import com.cafe.erp.modules.pos.order.entity.PosConsumption;
import com.cafe.erp.modules.pos.order.entity.PosOrder;
import com.cafe.erp.modules.pos.order.entity.PosOrderItem;
import com.cafe.erp.modules.pos.order.repository.PosConsumptionRepository;
import com.cafe.erp.modules.pos.order.repository.PosOrderItemRepository;
import com.cafe.erp.modules.pos.order.repository.PosOrderRepository;
import com.cafe.erp.modules.pos.order.service.PosOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PosOrderServiceImpl implements PosOrderService {

    private final PosOrderRepository orderRepo;
    private final PosOrderItemRepository itemRepo;
    private final PosConsumptionRepository consumptionRepo;

    private final RecipeRepository recipeRepo;
    private final RecipeVersionRepository versionRepo;
    private final RecipeItemRepository recipeItemRepo;
    private final RecipeCostAuditRepository recipeCostAuditRepo;

    private final StockRepository stockRepo;
    private final LocationsRepository locationRepo;

    // ---------------- CREATE POS ORDER ----------------
    @Transactional
    @Override
    public PosOrderResponseDTO createOrder(PosOrderCreateRequest request) {

        Location location = locationRepo.findById(request.getLocationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        PosOrder order = PosOrder.builder()
                .invoiceNumber("POS-" + UUID.randomUUID().toString().substring(0, 8))
                .location(location)
                .paymentMode(request.getPaymentMode())
                .remarks(request.getRemarks())
                .status("PLACED")
                .totalAmount(BigDecimal.ZERO)
                .totalTax(BigDecimal.ZERO)
                .build();

        orderRepo.save(order);

        BigDecimal orderTotal = BigDecimal.ZERO;
        List<PosOrderItem> orderItems = new ArrayList<>();

        for (PosOrderItemRequest itemReq : request.getItems()) {

            Recipes recipe = recipeRepo.findById(itemReq.getRecipeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));

            RecipeVersions version = versionRepo
                    .findByRecipeAndIsDefaultTrueAndStatus(recipe, "ACTIVE")
                    .orElseThrow(() -> new IllegalStateException("No ACTIVE recipe version"));

            BigDecimal qty = itemReq.getQuantity();
            if (qty.compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("Quantity must be > 0");

            // ✅ COST (internal)
            BigDecimal costPerUnit = recipeCostAuditRepo
                    .findTopByVersions_VersionIdOrderByCalculatedAtDesc(version.getVersionId())
                    .map(RecipeCostAudit::getCostPerOutputUnit)
                    .orElseThrow(() -> new IllegalStateException("Recipe cost not calculated"));

            // ✅ SELLING PRICE (from request / menu)
            BigDecimal sellingPrice = costPerUnit.multiply(recipe.getOutputQuantity());

            BigDecimal lineTotal = sellingPrice.multiply(qty);

            PosOrderItem orderItem = PosOrderItem.builder()
                    .order(order)
                    .recipeId(recipe.getRecipeId())
                    .recipeName(recipe.getRecipeName())
                    .quantity(qty)
                    .price(sellingPrice)
                    .costPrice(costPerUnit)
                    .totalPrice(lineTotal)
                    .build();

            itemRepo.save(orderItem);
            orderItems.add(orderItem);
            orderTotal = orderTotal.add(lineTotal);

            // ---------- STOCK DEDUCTION ----------
            List<RecipeItems> recipeItems =
                    recipeItemRepo.findByVersion_VersionId(version.getVersionId());

            for (RecipeItems ri : recipeItems) {

                BigDecimal usedQty = ri.getQuantity()
                        .multiply(qty)
                        .divide(recipe.getOutputQuantity(), 6, RoundingMode.HALF_UP);

                Stock stock = stockRepo
                        .findByMaterial_MaterialIdAndLocation_LocationIdAndIsDeletedFalse(
                                ri.getMaterial().getMaterialId(),
                                location.getLocationId()
                        )
                        .orElseThrow(() ->
                                new IllegalStateException("No stock for " + ri.getMaterial().getName())
                        );

                if (stock.getQuantity().compareTo(usedQty) < 0)
                    throw new IllegalStateException("Insufficient stock for " + ri.getMaterial().getName());

                stock.setQuantity(stock.getQuantity().subtract(usedQty));
                stockRepo.save(stock);

                consumptionRepo.save(
                        PosConsumption.builder()
                                .order(order)
                                .orderItem(orderItem)
                                .materialId(ri.getMaterial().getMaterialId())
                                .materialName(ri.getMaterial().getName())
                                .usedQty(usedQty)
                                .uomCode(ri.getUomCode())
                                .build()
                );
            }
        }

        order.setItems(orderItems);
        order.setTotalAmount(orderTotal);
        orderRepo.save(order);

        return getOrder(order.getOrderId());
    }


    // ---------------- GET ORDER ----------------
    @Override
    public PosOrderResponseDTO getOrder(Long orderId) {

        PosOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        List<PosOrderItemDTO> items = order.getItems().stream().map(item ->
                PosOrderItemDTO.builder()
                        .orderItemId(item.getOrderItemId())
                        .recipeId(item.getRecipeId())
                        .recipeName(item.getRecipeName())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .totalPrice(item.getTotalPrice())
                        .consumption(
                                consumptionRepo.findByOrderItem(item).stream()
                                        .map(c -> PosConsumptionDTO.builder()
                                                .materialId(c.getMaterialId())
                                                .materialName(c.getMaterialName())
                                                .usedQty(c.getUsedQty())
                                                .uomCode(c.getUomCode())
                                                .build())
                                        .collect(Collectors.toList())
                        )
                        .build()
        ).toList();

        return PosOrderResponseDTO.builder()
                .orderId(order.getOrderId())
                .invoiceNumber(order.getInvoiceNumber())
                .locationId(order.getLocation().getLocationId())
                .totalAmount(order.getTotalAmount())
                .paymentMode(order.getPaymentMode())
                .items(items)
                .build();
    }

    // ---------------- LIST ----------------
    @Override
    public List<PosOrderResponseDTO> listOrders() {
        return orderRepo.findAll().stream()
                .map(o -> getOrder(o.getOrderId()))
                .toList();
    }

    // ---------------- CANCEL (REVERSE STOCK) ----------------
    @Override
    public void cancelOrder(Long orderId) {

        PosOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!"PLACED".equals(order.getStatus()))
            throw new IllegalStateException("Only PLACED orders can be cancelled");

        List<PosConsumption> consumptions =
                consumptionRepo.findByOrder(order);

        for (PosConsumption c : consumptions) {

            Stock stock = stockRepo
                    .findByMaterial_MaterialIdAndLocation_LocationIdAndIsDeletedFalse(
                            c.getMaterialId(),
                            order.getLocation().getLocationId()
                    )
                    .orElseThrow();

            stock.setQuantity(stock.getQuantity().add(c.getUsedQty()));
            stockRepo.save(stock);
        }

        order.setStatus("CANCELLED");
        orderRepo.save(order);
    }

    @Transactional
    @Override
    public PosOrderResponseDTO addItem(Long orderId, PosOrderItemRequest req) {

        PosOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getStatus().equals("PLACED"))
            throw new IllegalStateException("Cannot modify order after checkout");

        Recipes recipe = recipeRepo.findById(req.getRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found"));

        BigDecimal qty = req.getQuantity();
        if (qty.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Quantity must be > 0");

        RecipeVersions version = versionRepo
                .findByRecipeAndIsDefaultTrueAndStatus(recipe, "ACTIVE")
                .orElseThrow(() -> new IllegalStateException("No active recipe version"));

        BigDecimal costPerUnit = recipeCostAuditRepo
                .findTopByVersions_VersionIdOrderByCalculatedAtDesc(version.getVersionId())
                .map(RecipeCostAudit::getCostPerOutputUnit)
                .orElseThrow(() -> new IllegalStateException("Recipe cost not found"));

        BigDecimal lineAmount = costPerUnit.multiply(qty);

        PosOrderItem item = PosOrderItem.builder()
                .order(order)
                .recipeId(recipe.getRecipeId())
                .recipeName(recipe.getRecipeName())
                .quantity(qty)
                .price(costPerUnit)
                .costPrice(costPerUnit)
                .totalPrice(lineAmount)
                .build();

        itemRepo.save(item);

        order.setTotalAmount(order.getTotalAmount().add(lineAmount));
        orderRepo.save(order);

        return getOrder(orderId);
    }

    @Transactional
    @Override
    public PosOrderResponseDTO removeItem(Long orderId, Long orderItemId) {

        PosOrder order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getStatus().equals("PLACED"))
            throw new IllegalStateException("Cannot modify order after checkout");

        PosOrderItem item = itemRepo.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found"));

        if (!item.getOrder().getOrderId().equals(orderId))
            throw new IllegalStateException("Item does not belong to this order");

        order.setTotalAmount(order.getTotalAmount().subtract(item.getTotalPrice()));

        itemRepo.delete(item);
        orderRepo.save(order);

        return getOrder(orderId);
    }

    @Transactional
    @Override
    public PosOrderResponseDTO checkout(PosCheckoutRequest request) {

        PosOrder order = orderRepo.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getStatus().equals("PLACED"))
            throw new IllegalStateException("Order already processed");

        Location location = order.getLocation();

        for (PosOrderItem item : order.getItems()) {

            Recipes recipe = recipeRepo.findById(item.getRecipeId())
                    .orElseThrow(() -> new RuntimeException("Recipe missing"));

            RecipeVersions version = versionRepo
                    .findByRecipeAndIsDefaultTrueAndStatus(recipe, "ACTIVE")
                    .orElseThrow(() -> new RuntimeException("Recipe version missing"));

            List<RecipeItems> recipeItems =
                    recipeItemRepo.findByVersion_VersionId(version.getVersionId());

            for (RecipeItems ri : recipeItems) {

                BigDecimal usedQty = ri.getQuantity()
                        .multiply(item.getQuantity())
                        .divide(recipe.getOutputQuantity(), 6, RoundingMode.HALF_UP);

                Stock stock = stockRepo
                        .findByMaterial_MaterialIdAndLocation_LocationIdAndIsDeletedFalse(
                                ri.getMaterial().getMaterialId(),
                                location.getLocationId()
                        )
                        .orElseThrow(() ->
                                new IllegalStateException("Stock not found for " + ri.getMaterial().getName()));

                if (stock.getQuantity().compareTo(usedQty) < 0)
                    throw new IllegalStateException("Insufficient stock for " + ri.getMaterial().getName());

                stock.setQuantity(stock.getQuantity().subtract(usedQty));
                stockRepo.save(stock);

                consumptionRepo.save(
                        PosConsumption.builder()
                                .order(order)
                                .orderItem(item)
                                .materialId(ri.getMaterial().getMaterialId())
                                .materialName(ri.getMaterial().getName())
                                .usedQty(usedQty)
                                .uomCode(ri.getUomCode())
                                .build()
                );
            }
        }

        order.setPaymentMode(request.getPaymentMode());
        order.setStatus("COMPLETED");

        orderRepo.save(order);

        return getOrder(order.getOrderId());
    }

}

