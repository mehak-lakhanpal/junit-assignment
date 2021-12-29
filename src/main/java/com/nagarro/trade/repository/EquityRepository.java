package com.nagarro.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.trade.entity.Equity;

@Repository
public interface EquityRepository extends JpaRepository<Equity, Long>{

}
