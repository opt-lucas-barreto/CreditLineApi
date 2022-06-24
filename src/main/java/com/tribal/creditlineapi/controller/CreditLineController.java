package com.tribal.creditlineapi.controller;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    
    @Autowired
    private CreditLineService creditLineService;
    Bucket bucketRateLimite = null; 

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
            
            
            Long countLastThreeIsNotAccepted = this.creditLineService.countLastThreeIsNotAccepted();
            if(countLastThreeIsNotAccepted >= 3){
                responseEntityBody = new ResponseEntityBody("A sales agent will contact you", BigDecimal.ZERO);
                return ResponseEntity.ok(responseEntityBody);
            }
            
            
            if(newCreditLine !=null && newCreditLine.getAccepted()){            

                responseEntityBody = 
                    new ResponseEntityBody("The Credit Line was accept!", newCreditLine.getCreditLineAuthorized());
                
                final HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("X-RateLimit-Remaining", ""+bucketRateLimite.getAvailableTokens());
                
                ResponseEntity<ResponseEntityBody> response = new ResponseEntity<ResponseEntityBody>(responseEntityBody, responseHeaders, HttpStatus.OK);
                return response;

            }else if(newCreditLine !=null && !newCreditLine.getAccepted()){

                responseEntityBody = 
                    new ResponseEntityBody("The Credit Line was NOT accept!", newCreditLine.getCreditLineAuthorized());
                final HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("X-RateLimit-Remaining", ""+bucketRateLimite.getAvailableTokens());
                
                ResponseEntity<ResponseEntityBody> response = new ResponseEntity<ResponseEntityBody>(responseEntityBody, responseHeaders, HttpStatus.OK);
                return response;

            }else{

                return (ResponseEntity<?>) ResponseEntity.badRequest();

            }
        }
                
        responseEntityBody = new ResponseEntityBody("Number of hits exceeded. Try Later", BigDecimal.ZERO);
        
        final HttpHeaders responseHeaders = new HttpHeaders();
        ConsumptionProbe probe = bucketRateLimite.tryConsumeAndReturnRemaining(1);
        responseHeaders.set("X-RateLimit-Reset", ""+TimeUnit.NANOSECONDS.toSeconds(probe.getNanosToWaitForRefill()));
        
        ResponseEntity<ResponseEntityBody> response = 
            new ResponseEntity<ResponseEntityBody>(responseEntityBody, responseHeaders, HttpStatus.TOO_MANY_REQUESTS);
        
        return response;
                    
    }

}
