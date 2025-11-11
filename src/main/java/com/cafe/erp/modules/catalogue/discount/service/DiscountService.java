package com.cafe.erp.modules.catalogue.discount.service;

import com.cafe.erp.modules.catalogue.discount.dto.DiscountRequestDTO;
import com.cafe.erp.modules.catalogue.discount.dto.DiscountResponseDTO;

import java.util.List;

public interface DiscountService {

    DiscountResponseDTO createDiscount(DiscountRequestDTO dto);

    DiscountResponseDTO updateDiscount(Long id, DiscountRequestDTO dto);

    List<DiscountResponseDTO> getAllDiscounts();

    DiscountResponseDTO getDiscountById(Long id);

    void deleteDiscount(Long id);
}

