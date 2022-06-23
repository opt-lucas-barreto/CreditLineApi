package com.tribal.creditlineapi.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;


@Entity
@Table(name = "CREDIT_LINE")
public class CreditLineEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    
    @NotNull
    @Column(name = "founding_type", columnDefinition = "VARCHAR(7) CHECK (founding_type IN ('SME', 'STARTUP'))")
    private String foundingType;

    @NotNull
    private BigDecimal cashBalance;

    @NotNull
    private BigDecimal monthlyRevenue;

    @NotNull
    private BigDecimal requestedCreditLine;

    @NotNull
    private LocalDateTime requestedDateTime;
    
    @Nullable
    private Boolean accepted;

    @Nullable
    private BigDecimal creditLineAuthorized;

    public CreditLineEntity() {
    }

    public CreditLineEntity(@NotNull String foundingType, @NotNull BigDecimal cashBalance,
            @NotNull BigDecimal monthlyRevenue, @NotNull LocalDateTime requestedDateTime,
            @NotNull BigDecimal requestedCreditLine) {
        this.foundingType = foundingType;
        this.cashBalance = cashBalance;
        this.monthlyRevenue = monthlyRevenue;
        this.requestedDateTime = requestedDateTime;
        this.requestedCreditLine =  requestedCreditLine;
        this.accepted = null;
        this.creditLineAuthorized = null;
    }

    public CreditLineEntity(@NotNull String foundingType, @NotNull BigDecimal cashBalance,
            @NotNull BigDecimal monthlyRevenue, @NotNull LocalDateTime requestedDateTime, 
            Boolean accepted, @NotNull BigDecimal requestedCreditLine) {
        this.foundingType = foundingType;
        this.cashBalance = cashBalance;
        this.monthlyRevenue = monthlyRevenue;
        this.requestedDateTime = requestedDateTime;
        this.requestedCreditLine =  requestedCreditLine;
        this.accepted = accepted;
        this.creditLineAuthorized = null;
    }

    public CreditLineEntity(Long id, @NotNull String foundingType, @NotNull BigDecimal cashBalance,
            @NotNull BigDecimal monthlyRevenue, @NotNull BigDecimal requestedCreditLine,
            @NotNull LocalDateTime requestedDateTime, Boolean accepted, BigDecimal creditLineAuthorized) {
        Id = id;
        this.foundingType = foundingType;
        this.cashBalance = cashBalance;
        this.monthlyRevenue = monthlyRevenue;
        this.requestedCreditLine = requestedCreditLine;
        this.requestedDateTime = requestedDateTime;
        this.accepted = accepted;
        this.creditLineAuthorized = creditLineAuthorized;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public LocalDateTime getRequestedDateTime() {
        return requestedDateTime;
    }

    public void setRequestedDateTime(LocalDateTime requestedDateTime) {
        this.requestedDateTime = requestedDateTime;
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
        result = prime * result + ((Id == null) ? 0 : Id.hashCode());
        result = prime * result + ((accepted == null) ? 0 : accepted.hashCode());
        result = prime * result + ((cashBalance == null) ? 0 : cashBalance.hashCode());
        result = prime * result + ((creditLineAuthorized == null) ? 0 : creditLineAuthorized.hashCode());
        result = prime * result + ((foundingType == null) ? 0 : foundingType.hashCode());
        result = prime * result + ((monthlyRevenue == null) ? 0 : monthlyRevenue.hashCode());
        result = prime * result + ((requestedCreditLine == null) ? 0 : requestedCreditLine.hashCode());
        result = prime * result + ((requestedDateTime == null) ? 0 : requestedDateTime.hashCode());
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
        CreditLineEntity other = (CreditLineEntity) obj;
        if (Id == null) {
            if (other.Id != null)
                return false;
        } else if (!Id.equals(other.Id))
            return false;
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
        if (requestedDateTime == null) {
            if (other.requestedDateTime != null)
                return false;
        } else if (!requestedDateTime.equals(other.requestedDateTime))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "CreditLineEntity [Id=" + Id + ", accepted=" + accepted + ", cashBalance=" + cashBalance
                + ", creditLineAuthorized=" + creditLineAuthorized + ", foundingType=" + foundingType
                + ", monthlyRevenue=" + monthlyRevenue + ", requestedCreditLine=" + requestedCreditLine
                + ", requestedDateTime=" + requestedDateTime + "]";
    }

   
    
}
