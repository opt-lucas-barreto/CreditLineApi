package com.tribal.creditlineapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CreditLineDTO {
    
    @JsonProperty
    @NotNull
    @NotBlank
    private String foundingType;

    @JsonProperty
    @NotNull
    private BigDecimal cashBalance;
    
    @JsonProperty
    @NotNull
    private BigDecimal monthlyRevenue;
    
    @JsonProperty
    @NotNull
    private BigDecimal requestedCreditLine;

    @JsonProperty
    @Nullable
    private LocalDateTime requestedDate = LocalDateTime.now();

    @JsonProperty
    @Null
    private Boolean accepted;

    @JsonProperty
    @Null
    private BigDecimal creditLineAuthorized;


    public CreditLineDTO(@NotNull @NotBlank String foundingType, @NotNull BigDecimal cashBalance,
            @NotNull BigDecimal monthlyRevenue, @NotNull BigDecimal requestedCreditLine, LocalDateTime requestedDate,
            @Null Boolean accepted, @Null BigDecimal creditLineAuthorized) {
        this.foundingType = foundingType;
        this.cashBalance = cashBalance;
        this.monthlyRevenue = monthlyRevenue;
        this.requestedCreditLine = requestedCreditLine;
        this.requestedDate = requestedDate;
        this.accepted = accepted;
        this.creditLineAuthorized = creditLineAuthorized;
    }



    public CreditLineDTO() {
    }

    

    public String getFoundingType() {
        return foundingType;
    }

    public void setFoundingType(String foundingType) {
        this.foundingType = foundingType;
    }

    public BigDecimal getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }

    public BigDecimal getMonthlyRevenue() {
        return monthlyRevenue;
    }

    public void setMonthlyRevenue(BigDecimal monthlyRevenue) {
        this.monthlyRevenue = monthlyRevenue;
    }

    public BigDecimal getRequestedCreditLine() {
        return requestedCreditLine;
    }

    public void setRequestedCreditLine(BigDecimal requestedCreditLine) {
        this.requestedCreditLine = requestedCreditLine;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }


    public BigDecimal getCreditLineAuthorized() {
        return creditLineAuthorized;
    }

    public void setCreditLineAuthorized(BigDecimal creditLineAuthorized) {
        this.creditLineAuthorized = creditLineAuthorized;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accepted == null) ? 0 : accepted.hashCode());
        result = prime * result + ((cashBalance == null) ? 0 : cashBalance.hashCode());
        result = prime * result + ((creditLineAuthorized == null) ? 0 : creditLineAuthorized.hashCode());
        result = prime * result + ((foundingType == null) ? 0 : foundingType.hashCode());
        result = prime * result + ((monthlyRevenue == null) ? 0 : monthlyRevenue.hashCode());
        result = prime * result + ((requestedCreditLine == null) ? 0 : requestedCreditLine.hashCode());
        result = prime * result + ((requestedDate == null) ? 0 : requestedDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CreditLineDTO other = (CreditLineDTO) obj;
        if (accepted == null) {
            if (other.accepted != null)
                return false;
        } else if (!accepted.equals(other.accepted))
            return false;
        if (cashBalance == null) {
            if (other.cashBalance != null)
                return false;
        } else if (!cashBalance.equals(other.cashBalance))
            return false;
        if (creditLineAuthorized == null) {
            if (other.creditLineAuthorized != null)
                return false;
        } else if (!creditLineAuthorized.equals(other.creditLineAuthorized))
            return false;
        if (foundingType == null) {
            if (other.foundingType != null)
                return false;
        } else if (!foundingType.equals(other.foundingType))
            return false;
        if (monthlyRevenue == null) {
            if (other.monthlyRevenue != null)
                return false;
        } else if (!monthlyRevenue.equals(other.monthlyRevenue))
            return false;
        if (requestedCreditLine == null) {
            if (other.requestedCreditLine != null)
                return false;
        } else if (!requestedCreditLine.equals(other.requestedCreditLine))
            return false;
        if (requestedDate == null) {
            if (other.requestedDate != null)
                return false;
        } else if (!requestedDate.equals(other.requestedDate))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CreditLineDTO [accepted=" + accepted + ", cashBalance=" + cashBalance + ", creditLineAuthorized="
                + creditLineAuthorized + ", foundingType=" + foundingType + ", monthlyRevenue=" + monthlyRevenue
                + ", requestedCreditLine=" + requestedCreditLine + ", requestedDate=" + requestedDate + "]";
    }
   
}
