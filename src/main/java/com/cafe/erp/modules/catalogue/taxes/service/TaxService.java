package com.cafe.erp.modules.catalogue.taxes.service;

import com.cafe.erp.modules.catalogue.taxes.dto.TaxRequestDTO;
import com.cafe.erp.modules.catalogue.taxes.dto.TaxResponseDTO;

import java.util.List;

public interface TaxService {

    TaxResponseDTO createTax(TaxRequestDTO dto);

    TaxResponseDTO updateTax(Long id, TaxRequestDTO dto);

    List<TaxResponseDTO> getAllTaxes();

    TaxResponseDTO getTaxById(Long id);

    void deleteTax(Long id);
}

