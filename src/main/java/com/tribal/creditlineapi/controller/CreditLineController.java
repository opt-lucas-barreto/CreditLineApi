package com.tribal.creditlineapi.controller;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tribal.creditlineapi.dto.CreditLineDTO;
import com.tribal.creditlineapi.entity.ResponseEntityBody;
import com.tribal.creditlineapi.service.CreditLineService;

@RestController
@RequestMapping("/")
public class CreditLineController {
    
    private final CreditLineService creditLineService;

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
        Long countLastThreeIsNotAccepted = this.creditLineService.countLastThreeIsNotAccepted();
        
        if(countLastThreeIsNotAccepted > 2){
            responseEntityBody = new ResponseEntityBody("A sales agent will contact you", BigDecimal.ZERO);
            return ResponseEntity.ok(responseEntityBody);
        }
        
        CreditLineDTO newCreditLine = this.creditLineService.setCreditLine(creditLineDTO);
        if(newCreditLine !=null && newCreditLine.getAccepted()){

            responseEntityBody = 
                new ResponseEntityBody("The Credit Line was accept!", newCreditLine.getCreditLineAuthorized());
            return ResponseEntity.ok(responseEntityBody);

        }else if(newCreditLine !=null && !newCreditLine.getAccepted()){

            countLastThreeIsNotAccepted++;
            if(countLastThreeIsNotAccepted > 2){
                responseEntityBody = new ResponseEntityBody("A sales agent will contact you", BigDecimal.ZERO);
                return ResponseEntity.ok(responseEntityBody);
            }
            responseEntityBody = 
                new ResponseEntityBody("The Credit Line was NOT accept!", newCreditLine.getCreditLineAuthorized());
            return ResponseEntity.ok(responseEntityBody);

        }else{

            return (ResponseEntity<?>) ResponseEntity.badRequest();

        }
    
    }

}
