package com.nagarro.trade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.trade.entity.Trader;

@Repository
public interface TraderRepository extends JpaRepository<Trader, Long>{

}
