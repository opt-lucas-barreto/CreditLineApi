package com.tribal.creditlineapi.controller;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tribal.creditlineapi.dto.CreditLineDTO;
import com.tribal.creditlineapi.entity.ResponseEntityBody;
import com.tribal.creditlineapi.service.CreditLineService;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;

@RestController
@RequestMapping("/")
public class CreditLineController {
    
    private final CreditLineService creditLineService;
    Bucket bucketRateLimite = null; 

    public CreditLineController(CreditLineService creditLineService){
        this.creditLineService = creditLineService;
    }

    @GetMapping
    public ResponseEntity<?> testServer(){
        return ResponseEntity.ok("Server On");
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.creditLineService.getAll());
    }

    @PostMapping("/setCreditLine")
    public ResponseEntity<?> postCreditLine(@Valid @RequestBody CreditLineDTO creditLineDTO){

        ResponseEntityBody responseEntityBody = null;
        if(bucketRateLimite == null || bucketRateLimite.tryConsume(1)){
            CreditLineDTO newCreditLine= this.creditLineService.setCreditLine(creditLineDTO);
            
            if(bucketRateLimite == null
                ||( creditLineService.getSecondLast() != null
                    && !creditLineService.getSecondLast().getAccepted()
                    && newCreditLine.getAccepted())){   
                bucketRateLimite = creditLineService.getBucketRateLimit();
                //already consume 1 hit when the bucketRateLimit is null
                bucketRateLimite.tryConsume(1);
            } 
            
            
            System.out.println( "+++++getAvailableTokens: "+bucketRateLimite.getAvailableTokens());
            
            
            Long countLastThreeIsNotAccepted = this.creditLineService.countLastThreeIsNotAccepted();
            if(countLastThreeIsNotAccepted >= 3){
                responseEntityBody = new ResponseEntityBody("A sales agent will contact you", BigDecimal.ZERO);
                return ResponseEntity.ok(responseEntityBody);
            }
            
            
            if(newCreditLine !=null && newCreditLine.getAccepted()){            

                System.out.println( "2+++++getAvailableTokens: "+bucketRateLimite.getAvailableTokens());

                responseEntityBody = 
                    new ResponseEntityBody("The Credit Line was accept!", newCreditLine.getCreditLineAuthorized());
                ResponseEntity<ResponseEntityBody> response = ResponseEntity.ok(responseEntityBody);
                return response;

            }else if(newCreditLine !=null && !newCreditLine.getAccepted()){

                System.out.println( "2+++++getAvailableTokens: "+bucketRateLimite.getAvailableTokens());

                responseEntityBody = 
                    new ResponseEntityBody("The Credit Line was NOT accept!", newCreditLine.getCreditLineAuthorized());
                    ResponseEntity<ResponseEntityBody> response = ResponseEntity.ok(responseEntityBody);
                    return response;

            }else{

                return (ResponseEntity<?>) ResponseEntity.badRequest();

            }
        }
        System.out.println("++++++++++++++++Number of hits exceeded+++++++++++++");
        ConsumptionProbe probe = bucketRateLimite.tryConsumeAndReturnRemaining(1);
        System.out.println("Seconds to refresh: " + TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill()));
        responseEntityBody = new ResponseEntityBody("Number of hits exceeded. Try Later", BigDecimal.ZERO);
        
        ResponseEntity<ResponseEntityBody> response = new ResponseEntity<ResponseEntityBody>(responseEntityBody, HttpStatus.TOO_MANY_REQUESTS);
        
        return response;
                    
    }

}
