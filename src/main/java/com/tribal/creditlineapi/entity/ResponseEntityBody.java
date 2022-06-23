package com.tribal.creditlineapi.entity;

import java.math.BigDecimal;

public class ResponseEntityBody {
    
    private String message;
    private BigDecimal creditLineAuthorized;

    public ResponseEntityBody(String message, BigDecimal creditLineAuthorized) {
        this.message = message;
        this.creditLineAuthorized = creditLineAuthorized;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public BigDecimal getCreditLineAuthorized() {
        return creditLineAuthorized;
    }
    public void setCreditLineAuthorized(BigDecimal creditLineAuthorized) {
        this.creditLineAuthorized = creditLineAuthorized;
    }
    @Override
    public String toString() {
        return "ResponseEntity [creditLineAuthorized=" + creditLineAuthorized + ", message=" + message + "]";
    }
    
}
