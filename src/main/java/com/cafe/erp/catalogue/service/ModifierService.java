package com.cafe.erp.catalogue.service;

import com.cafe.erp.catalogue.model.Modifier;

import java.util.List;

public interface ModifierService {
    Modifier createModifier(Modifier modifier);
    List<Modifier> getAllModifiers();
    Modifier getModifierById(Long id);
    Modifier updateModifier(Long id, Modifier modifier);
    void deleteModifier(Long id);
}
