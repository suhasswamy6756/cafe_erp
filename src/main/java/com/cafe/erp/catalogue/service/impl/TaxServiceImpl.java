package com.cafe.erp.catalogue.service.impl;

import com.cafe.erp.catalogue.model.Tax;
import com.cafe.erp.catalogue.repository.TaxRepository;
import com.cafe.erp.catalogue.service.TaxService;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService{

    private final TaxRepository taxRepository;

    @Override
    public Tax createTax(Tax tax) {
        return taxRepository.save(tax);
    }

    @Override
    public List<Tax> getAllTaxes() {
       return taxRepository.findAll();
    }

    @Override
    public Tax getTaxById(Long id) {
        return taxRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found"));
    }

    @Override
    public Tax updateTax(Long id, Tax tax) {
        return taxRepository.findById(id)
                .map(existingTax -> {
                    existingTax.setTaxType(tax.getTaxType());
                    existingTax.setTaxCode(tax.getTaxCode());
                    existingTax.setName(tax.getName());
                    existingTax.setDescription(tax.getDescription());
                    existingTax.setApplicableModes(tax.getApplicableModes());
                    existingTax.setApplicableOn(tax.getApplicableOn());
                    existingTax.setTaxPercentage(tax.getTaxPercentage());
                    return taxRepository.save(existingTax);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Tax not found"));
    }

    @Override
    public void deleteTax(Long id) {
        taxRepository.findById(id)
                .ifPresent(timing -> {
                    timing.setIsDeleted(true);
//                    timing.setDeletedBy(deletedBy);
                    timing.setDeletedAt(LocalDateTime.now());
                    taxRepository.save(timing);
                });

    }
}
