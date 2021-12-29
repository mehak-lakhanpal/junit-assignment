package com.nagarro.trade.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Trader {
	
	@Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
	private Long traderId;
	private String name;
	private int fund;
	@OneToMany(targetEntity=Equity.class,fetch = FetchType.LAZY)
	@JoinColumn(name="traderId",referencedColumnName = "traderId")
	private List<Equity> equities;
	
}
