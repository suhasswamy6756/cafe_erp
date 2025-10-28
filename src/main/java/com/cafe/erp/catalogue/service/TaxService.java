package com.cafe.erp.catalogue.service;

import com.cafe.erp.catalogue.model.Tax;

import java.util.List;

public interface TaxService{
    Tax createTax(Tax tax);
    List<Tax> getAllTaxes();
    Tax getTaxById(Long id);
    Tax updateTax(Long id, Tax tax);
    void deleteTax(Long id);
}
