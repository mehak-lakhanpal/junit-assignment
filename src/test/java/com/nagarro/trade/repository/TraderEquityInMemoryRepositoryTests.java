package com.nagarro.trade.repository;

import com.nagarro.trade.entity.Equity;
import com.nagarro.trade.entity.Trader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TraderEquityInMemoryRepositoryTests {

    private TraderRepository traderRepository;

    private EquityRepository equityRepository;

    @BeforeEach
    void setUp(){
        traderRepository = new TraderInMemoryRepository();
        equityRepository = new EquityInMemoryRepository();
    }

    @Test
    public void saveEquitySuccessfully(){
        Equity equity = new Equity();
        equity.setEquityId(2L);
        equity.setName("Equity1");
        equity.setPrice(400);
        equity = equityRepository.save(equity);
        Equity findedEquity = equityRepository.findById(equity.getEquityId()).get();
        assertNotNull(findedEquity);
        assertEquals(400,findedEquity.getPrice());
        assertEquals("Equity1",findedEquity.getName());
    }

    @Test
    public void saveTraderSuccessfully(){
        Equity equity = new Equity();
        equity.setPrice(400);
        equity = equityRepository.save(equity);
        Trader trader = new Trader();
        trader.setTraderId(2L);
        trader.setFund(400);
        trader.setName("John");
        List<Equity> equities = new ArrayList<>();
        equities.add(equity);
        trader.setEquities(equities);
        trader = traderRepository.save(trader);
        Trader findedTrader = traderRepository.findById(trader.getTraderId()).get();
        assertNotNull(findedTrader);
        assertEquals("John",findedTrader.getName());
        assertEquals(1,findedTrader.getEquities().size());

    }

}
