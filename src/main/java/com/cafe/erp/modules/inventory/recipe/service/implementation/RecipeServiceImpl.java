package com.cafe.erp.modules.inventory.recipe.service.implementation;


import com.cafe.erp.modules.inventory.material.entity.Material;
import com.cafe.erp.modules.inventory.material.repository.MaterialRepository;
import com.cafe.erp.modules.inventory.recipe.dto.*;
import com.cafe.erp.modules.inventory.recipe.entity.*;
import com.cafe.erp.modules.inventory.recipe.mapper.RecipeMapper;
import com.cafe.erp.modules.inventory.recipe.repository.*;
import com.cafe.erp.modules.inventory.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepo;
    private final RecipeCategoryRepository categoryRepo;
    private final RecipeVersionRepository versionRepo;
    private final RecipeItemRepository itemRepo;
    private final RecipeCostAuditRepository auditRepo;
    private final MaterialRepository materialRepo;

    private final RecipeMapper mapper;

    // ---------- CREATE RECIPE + FIRST VERSION ----------
    @Override
    public RecipeResponseDTO createRecipe(RecipeCreateRequest request) {

        if (recipeRepo.existsByRecipeNameIgnoreCase(request.getRecipeName())) {
            throw new IllegalArgumentException("Recipe name already exists");
        }

        RecipeCategory category = null;
        if (request.getCategoryId() != null) {
            category = categoryRepo.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Recipe category not found"));
        }

        if (request.getOutputQuantity() == null ||
                request.getOutputQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Output quantity must be > 0");
        }

        Recipes recipe = recipeRepo.save(
                Recipes.builder()
                        .recipeName(request.getRecipeName())
                        .recipeCategory(category)
                        .outputUnit(request.getOutputUnit())
                        .outputQuantity(request.getOutputQuantity())
                        .build()
        );

        RecipeVersions version = versionRepo.save(
                RecipeVersions.builder()
                        .recipe(recipe)
                        .versionNumber(1L)
                        .status("ACTIVE")
                        .isDefault(true)
//                        .note(request())
                        .build()
        );

        List<RecipeItems> items = buildAndSaveItems(version, request.getItems());

        // cost calc + audit
        BigDecimal totalCost = calcTotalCost(items);
        BigDecimal costPerOutput = calcCostPerOutput(totalCost, recipe.getOutputQuantity());

        auditRepo.save(
                RecipeCostAudit.builder()
                        .versions(version)
                        .totalCost(totalCost)
                        .costPerOutputUnit(costPerOutput)
                        .build()
        );

        RecipeVersionDTO defaultVersionDTO = mapper.toVersionDTO(version, totalCost, costPerOutput, items);

        return mapper.toRecipeDTO(recipe, defaultVersionDTO, List.of(defaultVersionDTO));
    }

    // ---------- GET SINGLE ----------
    @Override
    public RecipeResponseDTO getRecipe(Long recipeId) {

        Recipes recipe = recipeRepo.findByRecipeIdAndIsDeletedFalse(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        List<RecipeVersions> versions = versionRepo.findByRecipe_RecipeIdOrderByVersionNumberAsc(recipeId);

        List<RecipeVersionDTO> versionDTOs = versions.stream().map(v -> {
            List<RecipeItems> items = itemRepo.findByVersion_VersionId(v.getVersionId());

            Optional<RecipeCostAudit> latestAudit =
                    auditRepo.findTopByVersions_VersionIdOrderByCalculatedAtDesc(v.getVersionId());

            BigDecimal total = latestAudit != null ? latestAudit.get().getTotalCost() : calcTotalCost(items);
            BigDecimal costPerOutput = latestAudit != null ? latestAudit.get().getCostPerOutputUnit()
                    : calcCostPerOutput(total, recipe.getOutputQuantity());

            return mapper.toVersionDTO(v, total, costPerOutput, items);
        }).toList();

        RecipeVersionDTO defaultVersion = versionDTOs.stream()
                .filter(RecipeVersionDTO::getIsDefault)
                .findFirst()
                .orElse(versionDTOs.isEmpty() ? null : versionDTOs.get(0));

        return mapper.toRecipeDTO(recipe, defaultVersion, versionDTOs);
    }

    // ---------- LIST ----------
    @Override
    public List<RecipeResponseDTO> listRecipes() {

        return recipeRepo.findByIsDeletedFalse().stream().map(recipe -> {

            List<RecipeVersions> versions = versionRepo
                    .findByRecipe_RecipeIdOrderByVersionNumberAsc(recipe.getRecipeId());

            if (versions.isEmpty()) {
                return mapper.toRecipeDTO(recipe, null, List.of());
            }

            List<RecipeVersionDTO> versionDTOs = versions.stream().map(v -> {
                List<RecipeItems> items = itemRepo.findByVersion_VersionId(v.getVersionId());
                Optional<RecipeCostAudit> audit =
                        auditRepo.findTopByVersions_VersionIdOrderByCalculatedAtDesc(v.getVersionId());

                BigDecimal total = audit != null ? audit.get().getTotalCost() : calcTotalCost(items);
                BigDecimal costPerOutput = audit != null ? audit.get().getCostPerOutputUnit()
                        : calcCostPerOutput(total, recipe.getOutputQuantity());

                return mapper.toVersionDTO(v, total, costPerOutput, items);
            }).toList();

            RecipeVersionDTO defaultVersion = versionDTOs.stream()
                    .filter(RecipeVersionDTO::getIsDefault)
                    .findFirst()
                    .orElse(versionDTOs.get(0));

            return mapper.toRecipeDTO(recipe, defaultVersion, versionDTOs);
        }).toList();
    }

    // ---------- UPDATE RECIPE CORE ----------
    @Override
    public RecipeResponseDTO updateRecipe(Long recipeId, RecipeUpdateRequest request) {

        Recipes recipe = recipeRepo.findByRecipeIdAndIsDeletedFalse(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        if (request.getRecipeName() != null) recipe.setRecipeName(request.getRecipeName());

        if (request.getCategoryId() != null) {
            RecipeCategory cat = categoryRepo.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            recipe.setRecipeCategory(cat);
        }

        if (request.getOutputUnit() != null) recipe.setOutputUnit(request.getOutputUnit());

        if (request.getOutputQuantity() != null &&
                request.getOutputQuantity().compareTo(BigDecimal.ZERO) > 0) {
            recipe.setOutputQuantity(request.getOutputQuantity());
        }

        recipeRepo.save(recipe);

        return getRecipe(recipeId);  // reuse logic
    }

    // ---------- SOFT DELETE ----------
    @Override
    public void deleteRecipe(Long recipeId) {
        Recipes recipe = recipeRepo.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
        recipe.setIsDeleted(true);
        recipeRepo.save(recipe);
    }

    // ---------- CREATE NEW VERSION ----------
    @Override
    public RecipeVersionDTO createVersion(Long recipeId, RecipeVersionCreateRequest request) {

        Recipes recipe = recipeRepo.findByRecipeIdAndIsDeletedFalse(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        Long nextVersionNo = versionRepo.findByRecipe_RecipeIdOrderByVersionNumberAsc(recipeId)
                .stream()
                .map(RecipeVersions::getVersionNumber)
                .max(Long::compareTo)
                .orElse(0L) + 1;

        RecipeVersions version = versionRepo.save(
                RecipeVersions.builder()
                        .recipe(recipe)
                        .versionNumber(nextVersionNo)
                        .status("TESTING")
                        .note(request.getNotes())
                        .isDefault(false)
                        .build()
        );

        List<RecipeItems> items = buildAndSaveItems(version, request.getItems());

        BigDecimal total = calcTotalCost(items);
        BigDecimal costPerOutput = calcCostPerOutput(total, recipe.getOutputQuantity());

        auditRepo.save(
                RecipeCostAudit.builder()
                        .versions(version)
                        .totalCost(total)
                        .costPerOutputUnit(costPerOutput)
                        .build()
        );

        return mapper.toVersionDTO(version, total, costPerOutput, items);
    }

    // ---------- CLONE VERSION ----------
    @Override
    public RecipeVersionDTO cloneVersion(Long recipeId, RecipeVersionCloneRequest request) {

        Recipes recipe = recipeRepo.findByRecipeIdAndIsDeletedFalse(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        RecipeVersions source = versionRepo.findById(request.getSourceVersionId())
                .orElseThrow(() -> new RuntimeException("Source version not found"));

        if (!source.getRecipe().getRecipeId().equals(recipeId)) {
            throw new IllegalArgumentException("Source version does not belong to this recipe");
        }

        Long nextVersionNo = versionRepo.findByRecipe_RecipeIdOrderByVersionNumberAsc(recipeId)
                .stream()
                .map(RecipeVersions::getVersionNumber)
                .max(Long::compareTo)
                .orElse(0L) + 1;

        RecipeVersions newVersion = versionRepo.save(
                RecipeVersions.builder()
                        .recipe(recipe)
                        .versionNumber(nextVersionNo)
                        .status("TESTING")
                        .isDefault(false)
                        .note(request.getNotes() != null ? request.getNotes() : source.getNote())
                        .build()
        );

        List<RecipeItems> sourceItems = itemRepo.findByVersion_VersionId(source.getVersionId());

        List<RecipeItems> clonedItems = sourceItems.stream().map(i ->
                RecipeItems.builder()
                        .version(newVersion)
                        .material(i.getMaterial())
                        .quantity(i.getQuantity())
                        .uomCode(i.getUomCode())
                        .costPerUnit(i.getCostPerUnit())
                        .totalCost(i.getTotalCost())
                        .build()
        ).toList();

        itemRepo.saveAll(clonedItems);

        BigDecimal total = calcTotalCost(clonedItems);
        BigDecimal costPerOutput = calcCostPerOutput(total, recipe.getOutputQuantity());

        auditRepo.save(
                RecipeCostAudit.builder()
                        .versions(newVersion)
                        .totalCost(total)
                        .costPerOutputUnit(costPerOutput)
                        .build()
        );

        return mapper.toVersionDTO(newVersion, total, costPerOutput, clonedItems);
    }

    // ---------- UPDATE VERSION STATUS / DEFAULT ----------
    @Override
    public RecipeVersionDTO updateVersionStatus(Long versionId, RecipeVersionStatusUpdateRequest request) {

        RecipeVersions version = versionRepo.findById(versionId)
                .orElseThrow(() -> new RuntimeException("Version not found"));

        if (request.getStatus() != null) {
            version.setStatus(request.getStatus());
        }

        if (Boolean.TRUE.equals(request.getMakeDefault())) {
            // unset other defaults
            List<RecipeVersions> versions =
                    versionRepo.findByRecipe_RecipeIdAndIsDeletedFalse(version.getRecipe().getRecipeId());
            versions.forEach(v -> v.setIsDefault(v.getVersionId().equals(versionId)));
            versionRepo.saveAll(versions);
        } else if (request.getMakeDefault() != null && !request.getMakeDefault()) {
            version.setIsDefault(false);
        }

        versionRepo.save(version);

        // recalc dto
        List<RecipeItems> items = itemRepo.findByVersion_VersionId(versionId);
        Recipes recipe = version.getRecipe();

        Optional<RecipeCostAudit> audit =
                auditRepo.findTopByVersions_VersionIdOrderByCalculatedAtDesc(versionId);

        BigDecimal total = audit != null ? audit.get().getTotalCost() : calcTotalCost(items);
        BigDecimal costPerOutput = audit != null ? audit.get().getCostPerOutputUnit()
                : calcCostPerOutput(total, recipe.getOutputQuantity());

        return mapper.toVersionDTO(version, total, costPerOutput, items);
    }

    // ---------- RECALC ONE VERSION ----------
    @Override
    public RecipeVersionDTO recalculateVersionCost(Long versionId) {

        RecipeVersions version = versionRepo.findById(versionId)
                .orElseThrow(() -> new RuntimeException("Version not found"));

        Recipes recipe = version.getRecipe();
        List<RecipeItems> items = itemRepo.findByVersion_VersionId(versionId);

        // refresh costs from material.unitCost
        items.forEach(i -> {
            Material m = i.getMaterial();
            BigDecimal unitCost = m.getUnitCost() != null
                    ? m.getUnitCost()
                    : BigDecimal.ZERO;
            i.setCostPerUnit(unitCost);
            i.setTotalCost(unitCost.multiply(i.getQuantity()));
        });
        itemRepo.saveAll(items);

        BigDecimal total = calcTotalCost(items);
        BigDecimal costPerOutput = calcCostPerOutput(total, recipe.getOutputQuantity());

        auditRepo.save(
                RecipeCostAudit.builder()
                        .versions(version)
                        .totalCost(total)
                        .costPerOutputUnit(costPerOutput)
                        .build()
        );

        return mapper.toVersionDTO(version, total, costPerOutput, items);
    }

    // ---------- RECALC ALL VERSIONS USING MATERIAL ----------
    @Override
    public void recalculateCostsForMaterial(Long materialId) {

        List<RecipeItems> affectedItems = itemRepo.findByMaterial_MaterialId(materialId);

        // group by versionId
        Map<Long, List<RecipeItems>> byVersion = affectedItems.stream()
                .collect(Collectors.groupingBy(i -> i.getVersion().getVersionId()));

        for (Map.Entry<Long, List<RecipeItems>> entry : byVersion.entrySet()) {
            Long versionId = entry.getKey();
            recalculateVersionCost(versionId);
        }
    }

    // ---------- HELPERS ----------
    private List<RecipeItems> buildAndSaveItems(RecipeVersions version,
                                                List<RecipeItemCreateRequest> reqItems) {

        if (reqItems == null || reqItems.isEmpty()) {
            throw new IllegalArgumentException("Recipe must have at least one ingredient");
        }

        List<RecipeItems> items = reqItems.stream().map(i -> {
            Material material = materialRepo.findById(i.getMaterialId())
                    .orElseThrow(() -> new RuntimeException("Material not found: " + i.getMaterialId()));

            BigDecimal quantity = i.getQuantity();
            if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Quantity must be > 0 for material " + i.getMaterialId());
            }

            BigDecimal unitCost = material.getUnitCost() != null
                    ? material.getUnitCost()
                    : BigDecimal.ZERO;

            return RecipeItems.builder()
                    .version(version)
                    .material(material)
                    .quantity(quantity)
                    .uomCode(i.getUomCode())
                    .costPerUnit(unitCost)
                    .totalCost(unitCost.multiply(quantity))
                    .build();
        }).toList();

        return itemRepo.saveAll(items);
    }

    private BigDecimal calcTotalCost(List<RecipeItems> items) {
        return items.stream()
                .map(RecipeItems::getTotalCost)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calcCostPerOutput(BigDecimal totalCost, BigDecimal outputQty) {
        if (outputQty == null || outputQty.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return totalCost.divide(outputQty, 6, RoundingMode.HALF_UP);
    }
}
