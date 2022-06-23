package com.tribal.creditlineapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tribal.creditlineapi.entity.CreditLineEntity;

@Repository
public interface CreditLineDAO extends JpaRepository<CreditLineEntity, Long>{
    @Transactional
    @Query("from CreditLineEntity e where e.id = (select max(id) from CreditLineEntity)")
    public CreditLineEntity findLast();

    @Transactional
    @Query("from CreditLineEntity e where e.id > (select max(id)-3 from CreditLineEntity)")
    public List<CreditLineEntity> findLastThree();

}
