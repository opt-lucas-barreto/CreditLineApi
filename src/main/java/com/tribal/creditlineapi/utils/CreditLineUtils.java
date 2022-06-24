package com.tribal.creditlineapi.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.tribal.creditlineapi.dto.CreditLineDTO;
import com.tribal.creditlineapi.entity.CreditLineEntity;

public class CreditLineUtils {
    
    private static final String SME = "SME";
    private static final String STARTUP = "STARTUP";

    public static CreditLineEntity createCreditLineEntity(CreditLineDTO creditLineDTO){
        CreditLineEntity creditLineEntity = 
            new CreditLineEntity(creditLineDTO.getFoundingType(),
                                 creditLineDTO.getCashBalance(),
                                 creditLineDTO.getMonthlyRevenue(),
                                 creditLineDTO.getRequestedDate(),
                                 creditLineDTO.getRequestedCreditLine()
                                 );
        
        return creditLineEntity;
    }

    public static CreditLineDTO createCreditLineDTO(CreditLineEntity creditLineEntity){
        CreditLineDTO creditLineDTO = 
            new CreditLineDTO(creditLineEntity.getFoundingType(),
            creditLineEntity.getCashBalance(),
            creditLineEntity.getMonthlyRevenue(),
            creditLineEntity.getRequestedCreditLine(),
            creditLineEntity.getRequestedDateTime(),
            creditLineEntity.getAccepted(),
            creditLineEntity.getCreditLineAuthorized()
                                 );
        
        return creditLineDTO;
    }
    /**
     *  One third of the cash balance (3:1 ratio)
     * One fifth of the monthly revenue (5:1 ratio
     * When it is SME, it's only needed to calculate the recommended credit line based one the monthly revenue.
        And when it is Startup, the recommended credit line is the maximum value between the rule based on the
        cash balance and the monthly revenue.
        
     * 
     */
    public static BigDecimal calculateAvailableCreditLine(CreditLineEntity creditLineEntity){
        BigDecimal creditLineAvailable = BigDecimal.valueOf(0L);

        if(creditLineEntity.getFoundingType().equals(SME)){

            creditLineAvailable = 
                creditLineEntity.getMonthlyRevenue().divide(BigDecimal.valueOf(5L),RoundingMode.HALF_EVEN).setScale(2);

        }else if(creditLineEntity.getFoundingType().equals(STARTUP)){
            BigDecimal creditLineMonthlyRevenue = 
                creditLineEntity.getMonthlyRevenue().divide(BigDecimal.valueOf(5L),RoundingMode.HALF_EVEN).setScale(2);
            BigDecimal creditLineCashBalance = 
                creditLineEntity.getCashBalance().divide(BigDecimal.valueOf(3L),RoundingMode.HALF_EVEN).setScale(2);
            creditLineAvailable = 
                (creditLineMonthlyRevenue.compareTo(creditLineCashBalance) >= 0 ? creditLineMonthlyRevenue : creditLineCashBalance);

        }
        return creditLineAvailable;
    }

    /**
     * Once the recommended credit line has been calculated, the next step is to determine whether the application
        is accepted or not.
        If the recommended credit line is higher, then the application is accepted; otherwise, it is rejected
     * @param creditLineEntity
     * @return Boolean
     */
    public static Boolean isCreditLineAccepeted(CreditLineEntity creditLineEntity){
        
        BigDecimal creditLineAvailable = calculateAvailableCreditLine(creditLineEntity);
        
        return 
            creditLineAvailable.compareTo(creditLineEntity.getRequestedCreditLine()) > 0 ? Boolean.TRUE : Boolean.FALSE;
        
    }

    
}
