package com.nagarro.trade.service;

import com.nagarro.trade.entity.Trader;
import com.nagarro.trade.entity.request.TraderEquityRequest;

public interface TraderService {
	
	Long buyAnEquity(TraderEquityRequest traderEquityRequest);
	void sellAnEquity(Long traderId,Long equityId);
	Trader addFunds(Integer fund, Long traderId);

}
