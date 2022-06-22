package com.tribal.creditlineapi.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tribal.creditlineapi.dto.CreditLineDTO;
import com.tribal.creditlineapi.service.CreditLineService;

@RestController
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

    @PostMapping
    public ResponseEntity<?> postCreditLine(@Valid @RequestBody CreditLineDTO creditLineDTO){
        if(this.creditLineService.setCreditLine(creditLineDTO)){
            return ResponseEntity.ok("The Credit Line was accepted");
        }else{
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
    
    }

}
