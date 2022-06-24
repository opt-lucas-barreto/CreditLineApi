package com.tribal.creditlineapi.service;

import java.util.List;

import com.tribal.creditlineapi.dto.CreditLineDTO;

import io.github.bucket4j.Bucket;


public interface CreditLineService {
    
    List<CreditLineDTO> getAll();

    CreditLineDTO setCreditLine(CreditLineDTO creditLineDtoList);

    public Long countLastThreeIsNotAccepted();

    public Bucket getBucketRateLimit();

    public CreditLineDTO getLast();

    public CreditLineDTO getSecondLast();
}
