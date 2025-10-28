package com.cafe.erp.catalogue.service.impl;

import com.cafe.erp.catalogue.model.Discount;
import com.cafe.erp.catalogue.repository.DiscountRepository;
import com.cafe.erp.catalogue.service.DiscountService;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    @Override
    public Discount createDiscount(Discount discount) {
        return discountRepository.save(discount);
    }

    @Override
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @Override
    public Discount getDiscountById(Long id) {
        return discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found"));
    }

    @Override
    public Discount updateDiscount(Long id, Discount discount) {
        Discount existingDiscount = getDiscountById(id);
        existingDiscount.setDiscountName(discount.getDiscountName());
        existingDiscount.setApplicableOn(discount.getApplicableOn());
        existingDiscount.setDiscountType(discount.getDiscountType());
        existingDiscount.setDiscountValue(discount.getDiscountValue());
        existingDiscount.setDescription(discount.getDescription());
        existingDiscount.setMaximumDiscountValue(discount.getMaximumDiscountValue());
        existingDiscount.setMinimumBillSubtotal(discount.getMinimumBillSubtotal());
        existingDiscount.setIsActive(discount.getIsActive());
        return discountRepository.save(existingDiscount);
    }

    @Override
    public void deleteDiscount(Long id) {
        discountRepository.deleteById(id);
    }
}
