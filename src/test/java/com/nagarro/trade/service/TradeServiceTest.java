package com.nagarro.trade.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nagarro.trade.utility.Utility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.nagarro.trade.service.impl.TraderServiceImpl;
import com.nagarro.trade.entity.Equity;
import com.nagarro.trade.entity.Trader;
import com.nagarro.trade.entity.request.TraderEquityRequest;
import com.nagarro.trade.repository.EquityRepository;
import com.nagarro.trade.repository.TraderRepository;

@ExtendWith(SpringExtension.class)
public class TradeServiceTest {
	
	@InjectMocks
	private TraderServiceImpl tradeService;
	
	@Mock
	private TraderRepository traderRepository;
	
	@Mock
	private EquityRepository equityRepository;

	@Captor
	private ArgumentCaptor<Long> captor;
	
	@DisplayName("Buy an Equity successfully mockito")
	@Test
	void shouldBuyAnEquitySuccessfully() throws Exception {
		TraderEquityRequest traderEquityRequest = new TraderEquityRequest(1L, 1L);
		Equity equity = new Equity();
		equity.setEquityId(1L);
		equity.setPrice(400);
		Trader trader = new Trader();
		trader.setTraderId(1L);
		trader.setFund(1000);
		trader.setEquities(new ArrayList<>());
		when(traderRepository.findById(traderEquityRequest.getTraderId())).thenReturn(Optional.of(trader));
		when(equityRepository.findById(traderEquityRequest.getEquityId())).thenReturn(Optional.of(equity));
		try(MockedStatic<Utility> utility = Mockito.mockStatic(Utility.class)){
			when(traderRepository.save(trader)).thenReturn(trader);
			utility.when(Utility::canBuySellEquity).thenReturn(true);
			assertEquals(true,Utility.canBuySellEquity());
			assertEquals(Long.valueOf(1L), tradeService.buyAnEquity(traderEquityRequest));
		}
	}
	
	@DisplayName("Cannot Buy an Equity because it already bought  mockito")
	@Test
	void shouldThrowExceptionIfAlreadyBought() throws Exception {
		TraderEquityRequest traderEquityRequest = new TraderEquityRequest(1L, 1L);
		Equity equity = new Equity();
		equity.setEquityId(1L);
		equity.setPrice(400);
		Trader trader = new Trader();
		trader.setTraderId(1L);
		trader.setFund(1000);
		List<Equity> equities = new ArrayList<>();
		equities.add(equity);
		trader.setEquities(equities);
		when(traderRepository.findById(traderEquityRequest.getTraderId())).thenReturn(Optional.of(trader));
		when(equityRepository.findById(traderEquityRequest.getEquityId())).thenReturn(Optional.of(equity));
		try(MockedStatic<Utility> utility = Mockito.mockStatic(Utility.class)){
			utility.when(Utility::canBuySellEquity).thenReturn(true);
			assertEquals(true,Utility.canBuySellEquity());
			assertThrows(IllegalArgumentException.class,()->tradeService.buyAnEquity(traderEquityRequest));
		}
	}
	
	@DisplayName("Cannot Buy an Equity because of insufficient funds mockito")
	@Test
	void shouldThrowExceptionIfDoesNotHaveSufficientFunds() throws Exception {
		TraderEquityRequest traderEquityRequest = new TraderEquityRequest(1L, 1L);
		Equity equity = new Equity();
		equity.setEquityId(1L);
		equity.setPrice(400);
		Trader trader = new Trader();
		trader.setTraderId(1L);
		trader.setFund(100);
		trader.setEquities(new ArrayList<>());
		when(traderRepository.findById(traderEquityRequest.getTraderId())).thenReturn(Optional.of(trader));
		when(equityRepository.findById(traderEquityRequest.getEquityId())).thenReturn(Optional.of(equity));
		try(MockedStatic<Utility> utility = Mockito.mockStatic(Utility.class)){
			utility.when(Utility::canBuySellEquity).thenReturn(true);
			assertEquals(true,Utility.canBuySellEquity());
			IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
					()->tradeService.buyAnEquity(traderEquityRequest));
			assertEquals("Trader does'nt have sufficient funds to buy this equity",thrown.getMessage());
		}
	}
	
	@DisplayName("Cannot Buy an Equity because trader does'nt exist mockito")
	@Test
	void shouldThrowNullPointerExceptionIfTraderDoesNotExist() throws Exception {
		TraderEquityRequest traderEquityRequest = new TraderEquityRequest(1L, 1L);
		Equity equity = new Equity();
		equity.setEquityId(1L);
		equity.setPrice(400);

		when(traderRepository.findById(traderEquityRequest.getTraderId())).thenReturn(null);
		when(equityRepository.findById(traderEquityRequest.getEquityId())).thenReturn(Optional.of(equity));
		try(MockedStatic<Utility> utility = Mockito.mockStatic(Utility.class)){
			utility.when(Utility::canBuySellEquity).thenReturn(true);
			assertEquals(true,Utility.canBuySellEquity());
			NullPointerException thrown = assertThrows(NullPointerException.class,
					()->tradeService.buyAnEquity(traderEquityRequest));
		}
		// assertEquals("Trader or Equity does'nt exists",thrown.getMessage());
	}
	
	@DisplayName("Cannot Buy an Equity because equity does'nt exist mockito")
	@Test
	void shouldThrowNullPointerExceptionIfEquityDoesNotExist() throws Exception {
		TraderEquityRequest traderEquityRequest = new TraderEquityRequest(1L, 1L);
		Trader trader = new Trader();
		trader.setTraderId(1L);
		trader.setFund(100);
		trader.setEquities(new ArrayList<>());
		when(traderRepository.findById(traderEquityRequest.getTraderId())).thenReturn(Optional.of(trader));
		when(equityRepository.findById(traderEquityRequest.getEquityId())).thenReturn(null);
		try(MockedStatic<Utility> utility = Mockito.mockStatic(Utility.class)){
			utility.when(Utility::canBuySellEquity).thenReturn(true);
			assertEquals(true,Utility.canBuySellEquity());
			NullPointerException thrown = assertThrows(NullPointerException.class,
					()->tradeService.buyAnEquity(traderEquityRequest));
		}
		// assertEquals("Trader or Equity does'nt exists",thrown.getMessage());
	}

	@DisplayName("Cannot Buy an Equity because trade market is unavailable")
	@Test
	void shouldThrowExceptionIfCanBuySellEquityReturnsFalse() throws Exception {
		TraderEquityRequest traderEquityRequest = new TraderEquityRequest(1L, 1L);
		Equity equity = new Equity();
		equity.setEquityId(1L);
		equity.setPrice(400);
		Trader trader = new Trader();
		trader.setTraderId(1L);
		trader.setFund(100);
		trader.setEquities(new ArrayList<>());
		when(traderRepository.findById(traderEquityRequest.getTraderId())).thenReturn(Optional.of(trader));
		when(equityRepository.findById(traderEquityRequest.getEquityId())).thenReturn(Optional.of(equity));
		try(MockedStatic<Utility> utility = Mockito.mockStatic(Utility.class)){
			utility.when(Utility::canBuySellEquity).thenReturn(false);
			assertEquals(false,Utility.canBuySellEquity());
			IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
					()->tradeService.buyAnEquity(traderEquityRequest));
			assertEquals("Can only buy from 9 to 5 and Monday to Friday",thrown.getMessage());
		}
	}

	@DisplayName("Sell an Equity successfully mockito")
	@Test
	void shouldSellAnEquitySuccessfully() throws Exception {
		Long traderId=1L;
		Long equityId=1L;
		Equity equity = new Equity();
		equity.setEquityId(1L);
		equity.setPrice(400);
		Trader trader = new Trader();
		trader.setTraderId(1L);
		trader.setFund(1000);
		List<Equity> equities = new ArrayList<>();
		equities.add(equity);
		trader.setEquities(equities);
		when(traderRepository.findById(traderId)).thenReturn(Optional.of(trader));
		//when(traderRepository.save(trader)).thenReturn(trader);
		try(MockedStatic<Utility> utility = Mockito.mockStatic(Utility.class)){
			utility.when(Utility::canBuySellEquity).thenReturn(true);
			assertEquals(true,Utility.canBuySellEquity());
			tradeService.sellAnEquity(traderId,equityId);
		}
		verify(traderRepository).save(trader);
		//verify(traderRepository).findById(captor.capture());
	}

	@DisplayName("Not enough equities to sell mockito")
	@Test
	void shouldThrowExceptionIfNoEquitiesToSell() throws Exception {
		Long traderId=1L;
		Long equityId=1L;
		Equity equity = new Equity();
		equity.setEquityId(1L);
		equity.setPrice(400);
		Trader trader = new Trader();
		trader.setTraderId(1L);
		trader.setFund(1000);
		trader.setEquities(new ArrayList<>());
		when(traderRepository.findById(traderId)).thenReturn(Optional.of(trader));
		try(MockedStatic<Utility> utility = Mockito.mockStatic(Utility.class)){
			utility.when(Utility::canBuySellEquity).thenReturn(true);
			assertEquals(true,Utility.canBuySellEquity());
			NullPointerException thrown = assertThrows(NullPointerException.class,
					()->tradeService.sellAnEquity(traderId,equityId));
			assertEquals("Trader does'nt have any equities to sell",thrown.getMessage());
		}
	}

	@DisplayName("Not enough equities to sell mockito")
	@Test
	void shouldThrowExceptionIfRequestedEquityNotFound() throws Exception {
		Long traderId=1L;
		Long equityId=1L;
		Equity equity = new Equity();
		equity.setEquityId(2L);
		equity.setPrice(400);
		Trader trader = new Trader();
		trader.setTraderId(1L);
		trader.setFund(1000);
		List<Equity> equities = new ArrayList<>();
		equities.add(equity);
		trader.setEquities(equities);
		when(traderRepository.findById(traderId)).thenReturn(Optional.of(trader));
		try(MockedStatic<Utility> utility = Mockito.mockStatic(Utility.class)){
			utility.when(Utility::canBuySellEquity).thenReturn(true);
			assertEquals(true,Utility.canBuySellEquity());
			IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class,
					()->tradeService.sellAnEquity(traderId,equityId));
			assertEquals("Trader does'nt have requested equity with Id: "+equityId,thrown.getMessage());
		}
	}

	@DisplayName("Add Fund Successfully mockito")
	@Test
	void shouldThrowExceptionWhenTraderIsNullWhileAddingFunds() throws Exception {
		Long traderId = 1L;
		Integer fund =100;
		when(traderRepository.findById(traderId)).thenReturn(null);
		assertThrows(NullPointerException.class,
				()->tradeService.addFunds(fund,traderId));
	}

	@DisplayName("Add Fund Successfully mockito")
	@Test
	void shouldAddFundsSuccessfully() throws Exception {
		Long traderId = 1L;
		Integer fund =100;
		Trader trader = new Trader();
		trader.setFund(100);
		trader.setTraderId(1L);
		trader.setFund(100);
		when(traderRepository.findById(traderId)).thenReturn(Optional.of(trader));
		when(traderRepository.save(trader)).thenReturn(trader);
		assertEquals(200,tradeService.addFunds(fund,traderId).getFund());
		assertTrue(tradeService.addFunds(fund,traderId).getTraderId().equals(1L));
	}
}
