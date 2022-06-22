package com.tribal.creditlineapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tribal.creditlineapi.entity.CreditLineEntity;

@Repository
public interface CreditLineDAO extends JpaRepository<CreditLineEntity, Long>{
    
}
