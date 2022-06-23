package com.tribal.creditlineapi.service;

import java.math.BigDecimal;
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
    public CreditLineDTO setCreditLine(CreditLineDTO creditLineDTO) {

        CreditLineEntity lastCreditLine = creditLineDAO.findLast();

        if(lastCreditLine != null && lastCreditLine.getAccepted()){
            return CreditLineUtils.createCreditLineDTO(lastCreditLine);
        }

        CreditLineEntity creditLineEntity = CreditLineUtils.createCreditLineEntity(creditLineDTO);
        
        creditLineEntity.setAccepted(CreditLineUtils.isCreditLineAccepeted(creditLineEntity));

        if(creditLineEntity.getAccepted()){
            creditLineEntity.setCreditLineAuthorized(CreditLineUtils.calculateAvailableCreditLine(creditLineEntity));
        }else{
            creditLineEntity.setCreditLineAuthorized(BigDecimal.ZERO);
        }
        
        return CreditLineUtils.createCreditLineDTO(creditLineDAO.save(creditLineEntity));
    }

    /**
     * count if the last three registries on DB is not accepted
     * @return
     */
    @Override
    public Long countLastThreeIsNotAccepted(){
        Long out = 0L;
        List<CreditLineEntity> creditLineEntityList = creditLineDAO.findLastThree();
        for (CreditLineEntity creditLineEntity : creditLineEntityList) {
            out += ( !creditLineEntity.getAccepted() ? 1 : 0 );
        }
        return out;
    }
    
}
