package com.cafe.erp.catalogue.service;

import com.cafe.erp.catalogue.model.Discount;

import java.util.List;

public interface DiscountService {
    Discount createDiscount(Discount discount);
    List<Discount> getAllDiscounts();
    Discount getDiscountById(Long id);
    Discount updateDiscount(Long id, Discount discount);
    void deleteDiscount(Long id);
}
