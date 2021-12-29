package com.nagarro.trade.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Equity {
	
	@Id  
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
	private Long equityId;
	private String name;
	private String company;
	private Integer price;
}
