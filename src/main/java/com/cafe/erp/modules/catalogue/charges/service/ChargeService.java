package com.cafe.erp.modules.catalogue.charges.service;


import com.cafe.erp.modules.catalogue.charges.dto.ChargeRequestDTO;
import com.cafe.erp.modules.catalogue.charges.dto.ChargeResponseDTO;

import java.util.List;

public interface ChargeService {

    ChargeResponseDTO createCharge(ChargeRequestDTO dto);

    ChargeResponseDTO updateCharge(Long id, ChargeRequestDTO dto);

    List<ChargeResponseDTO> getAllCharges();

    ChargeResponseDTO getChargeById(Long id);

    void deleteCharge(Long id);
}

