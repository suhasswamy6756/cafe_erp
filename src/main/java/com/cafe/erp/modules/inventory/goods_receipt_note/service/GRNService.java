package com.cafe.erp.modules.inventory.goods_receipt_note.service;

import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNCreateRequestDTO;
import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNCreateResponseDTO;
import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNResponseDTO;
import com.cafe.erp.modules.inventory.goods_receipt_note.dto.GRNSubmitRequestDTO;

import java.util.List;

public interface GRNService {

    GRNCreateResponseDTO createGRN(GRNCreateRequestDTO request);

    GRNResponseDTO submitGRN(GRNSubmitRequestDTO request);

    GRNResponseDTO approveGRN(Long grnId);

    GRNResponseDTO getGRN(Long grnId);

    List<GRNResponseDTO> listGRN();
}

