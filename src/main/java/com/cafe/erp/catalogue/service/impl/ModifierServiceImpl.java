package com.cafe.erp.catalogue.service.impl;

import com.cafe.erp.catalogue.model.Modifier;
import com.cafe.erp.catalogue.repository.ModifierRepository;
import com.cafe.erp.catalogue.service.ModifierService;
import com.cafe.erp.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModifierServiceImpl  implements ModifierService {

    private final ModifierRepository modifierRepository;

    @Override
    public Modifier createModifier(Modifier modifier) {
        return modifierRepository.save(modifier);
    }

    @Override
    public List<Modifier> getAllModifiers() {
        return modifierRepository.findAll();
    }

    @Override
    public Modifier getModifierById(Long id) {
        return modifierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Modifier not found"));
    }

    @Override
    public Modifier updateModifier(Long id, Modifier modifier) {
        return modifierRepository.findById(id)
                .map(modifier1 -> {
                    modifier1.setTitle(modifier.getTitle());
                    modifier1.setDefaultSalePrice(modifier.getDefaultSalePrice());
                    modifier1.setIsDeleted(modifier.getIsDeleted());
                    modifier1.setModifierGroup(modifier.getModifierGroup());
                    modifier1.setFoodType(modifier.getFoodType());

                    modifier1.setUpdatedBy(modifier.getUpdatedBy());
                    return modifierRepository.save(modifier1);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Modifier not found"));
    }

    @Override
    public void deleteModifier(Long id) {
        modifierRepository.deleteById(id);
    }



}
