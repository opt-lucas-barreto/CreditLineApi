package com.tribal.creditlineapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tribal.creditlineapi.dao.CreditLineDAO;
import com.tribal.creditlineapi.dto.CreditLineDTO;
import com.tribal.creditlineapi.entity.CreditLineEntity;
import com.tribal.creditlineapi.utils.CreditLineUtils;

@Service
public class CreditLineServiceImpl implements CreditLineService{

    /*private static final String RATER_LIMIT_NAME = "raterLimit";

    private static final int NEW_LIMIT_FOR_PIRIOD = 3;

    private static final int NEW_LIMIT_REFRESH_PERIOD = 20;
    */

    @Autowired
    private CreditLineDAO creditLineDAO;

    @Override
    public List<CreditLineDTO> getAll() {
        
        List<CreditLineDTO> creditLineDTO = new ArrayList<>();

        for (CreditLineEntity creditLineEntity : creditLineDAO.findAll()) {
            creditLineDTO.add(CreditLineUtils.createCreditLineDTO(creditLineEntity));
        }
        
       return creditLineDTO;
    }

    @Override
    public Boolean setCreditLine(CreditLineDTO creditLineDTO) {

        CreditLineEntity creditLineEntity = CreditLineUtils.createCreditLineEntity(creditLineDTO);
        
        creditLineEntity.setAccepted(CreditLineUtils.isCreditLineAccepeted(creditLineEntity));
        
        

        //TODO: The accepted credit line will be the same regardless of the inputs

        if(creditLineEntity.getAccepted()){
            //CreditLineUtils.updateRateLimits(RATER_LIMIT_NAME, NEW_LIMIT_FOR_PIRIOD, NEW_LIMIT_REFRESH_PERIOD);
            
        }
        
        
        creditLineDAO.save(creditLineEntity);
        
        //TODO: Must return the response entity, with the accepted and creditLine authorized fields
        return true;
    }
    
}
