package com.tribal.creditlineapi.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tribal.creditlineapi.dao.CreditLineDAO;
import com.tribal.creditlineapi.dto.CreditLineDTO;
import com.tribal.creditlineapi.entity.CreditLineEntity;
import com.tribal.creditlineapi.utils.CreditLineUtils;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Service
public class CreditLineServiceImpl implements CreditLineService{

    /*private static final String RATER_LIMIT_NAME = "raterLimit";

    private static final int NEW_LIMIT_FOR_PIRIOD = 3;

    private static final int NEW_LIMIT_REFRESH_PERIOD = 20;
    */

    /*@Autowired*/
    private CreditLineDAO creditLineDAO;

    public CreditLineServiceImpl(CreditLineDAO creditLineDAO) {
        this.creditLineDAO = creditLineDAO;
    }

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
        CreditLineEntity creditLineEntity = null;

        if(lastCreditLine != null && lastCreditLine.getAccepted()){
            creditLineEntity = new CreditLineEntity(lastCreditLine.getFoundingType()
                                                    , lastCreditLine.getCashBalance()
                                                    ,lastCreditLine.getMonthlyRevenue()
                                                    , lastCreditLine.getRequestedDateTime()
                                                    , lastCreditLine.getAccepted()
                                                    , lastCreditLine.getRequestedCreditLine());
            creditLineEntity.setCreditLineAuthorized(CreditLineUtils.calculateAvailableCreditLine(creditLineEntity));
            return CreditLineUtils.createCreditLineDTO(creditLineDAO.save(creditLineEntity));
        }

        creditLineEntity = CreditLineUtils.createCreditLineEntity(creditLineDTO);
        
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

    @Override
    public Bucket getBucketRateLimit() {
        
        CreditLineEntity lastCreditLine = creditLineDAO.findLast();

        if(lastCreditLine == null){
            Bandwidth bandwidth = Bandwidth.classic(1, Refill.intervallyAligned(1, Duration.ofSeconds(5),ZonedDateTime.now().toInstant(), true));
            return  Bucket.builder().addLimit(bandwidth).build();
        }

        //If the system receives 3 or more requests within two minutes, return the http code 429
        if(lastCreditLine.getAccepted()){
            Bandwidth bandwidth = Bandwidth.classic(2, Refill.intervallyAligned(2, Duration.ofMinutes(2),ZonedDateTime.now().toInstant(), true));
            return  Bucket.builder().addLimit(bandwidth).build();
            
        }else {
            //Don't allow a new application requests within 30 seconds next to the previous one, if so, return HTTP code 429
            Bandwidth bandwidth = Bandwidth.classic(1, Refill.intervallyAligned(1, Duration.ofSeconds(30),ZonedDateTime.now().toInstant(), true));
            return  Bucket.builder().addLimit(bandwidth).build();
            
        }
    }

    /**
     * brings the CreditLine DTO of the last registry
     * @return CreditLineDTO
     */
    @Override
    public CreditLineDTO getLast() {
        CreditLineEntity lastCreditLine = creditLineDAO.findLast();
        if( lastCreditLine != null)
            return CreditLineUtils.createCreditLineDTO(lastCreditLine);
        else
            return null;
    }

    /**
     * @return CreditLineDTO
     */
    @Override
    public CreditLineDTO getSecondLast() {
        List<CreditLineEntity> creditLineEntityList = creditLineDAO.findLastThree();
        if(creditLineEntityList == null)
            return null;

        if(creditLineEntityList.size() == 3)
            return CreditLineUtils.createCreditLineDTO(creditLineEntityList.get(1));
        else
            return CreditLineUtils.createCreditLineDTO(creditLineEntityList.get(0));

    }
    
}
