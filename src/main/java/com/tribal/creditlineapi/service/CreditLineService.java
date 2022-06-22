package com.tribal.creditlineapi.service;

import java.util.List;

import com.tribal.creditlineapi.dto.CreditLineDTO;


public interface CreditLineService {
    
    List<CreditLineDTO> getAll();

    Boolean setCreditLine(CreditLineDTO creditLineDtoList);
}
