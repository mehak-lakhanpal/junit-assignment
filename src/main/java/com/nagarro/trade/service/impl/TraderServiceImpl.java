package com.nagarro.trade.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.trade.service.TraderService;
import com.nagarro.trade.entity.Equity;
import com.nagarro.trade.entity.Trader;
import com.nagarro.trade.entity.request.TraderEquityRequest;
import com.nagarro.trade.repository.EquityRepository;
import com.nagarro.trade.repository.TraderRepository;
import com.nagarro.trade.utility.Utility;

@Service
public class TraderServiceImpl implements TraderService {

	@Autowired
	private TraderRepository traderRepository;

	@Autowired
	private EquityRepository equityRepository;

	@Override
	public Long buyAnEquity(TraderEquityRequest traderEquityRequest) {
		Long id = null;
		checkIfTraderCanBuySellEquity();
		Trader trader = traderRepository.findById(traderEquityRequest.getTraderId()).orElseThrow(() -> new NullPointerException("Trader does'nt exists"));
		Equity equity = equityRepository.findById(traderEquityRequest.getEquityId()).orElseThrow(() -> new NullPointerException("Equity does'nt exists"));
			List<Equity> equities = trader.getEquities();
			Optional<Equity> particularEquity = equities.stream()
					.filter(e -> e.getEquityId().equals(traderEquityRequest.getEquityId())).findAny();
			if (!particularEquity.isPresent()) {
				if (trader.getFund() >= equity.getPrice()) {
					trader.setFund(trader.getFund()-equity.getPrice());
					equities.add(equity);
					trader.setEquities(equities);
					
					id = traderRepository.save(trader).getTraderId();
				} else {
					throw new IllegalArgumentException("Trader does'nt have sufficient funds to buy this equity");
				}
			} else {
				throw new IllegalArgumentException("Trader already bought this equity");
			}
		return id;
	}

	@Override
	public void sellAnEquity(Long traderId, Long equityId) {
		checkIfTraderCanBuySellEquity();
		Trader trader = traderRepository.findById(traderId).orElseThrow(() -> new NullPointerException("Trader does'nt exists"));
			List<Equity> equities = trader.getEquities();
			if (equities != null && !equities.isEmpty()) {
				Optional<Equity> particularEquity = equities.stream().filter(e -> e.getEquityId().equals(equityId))
						.findAny();
				if (particularEquity.isPresent()) {
					equities.remove(particularEquity.get());
					trader.setEquities(equities);
					trader.setFund(trader.getFund() + particularEquity.get().getPrice());
					traderRepository.save(trader);
				} else {
					throw new IllegalArgumentException("Trader does'nt have requested equity with Id: " + equityId);
				}
			} else {
				throw new NullPointerException("Trader does'nt have any equities to sell");
			}
	}

	private void checkIfTraderCanBuySellEquity() {
		if (!Utility.canBuySellEquity()) {
			throw new IllegalArgumentException("Can only buy from 9 to 5 and Monday to Friday");
		}
	}

	@Override
	public Trader addFunds(Integer fund, Long traderId) {
		Trader trader = traderRepository.findById(traderId).orElseThrow(() -> new NullPointerException("Trader does'nt exists"));
		trader.setFund(trader.getFund() + fund);
		return traderRepository.save(trader);
	}


}
