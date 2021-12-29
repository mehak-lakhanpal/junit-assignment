package com.nagarro.trade.repository;

import com.nagarro.trade.entity.Equity;
import com.nagarro.trade.entity.Trader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TraderEquityActualDbRepositoryTests {

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private EquityRepository equityRepository;

    @Test
    public void saveEquityTraderSuccessfully(){
        Equity equity = new Equity();
        equity.setPrice(400);
        equity = equityRepository.save(equity);
        Trader trader = new Trader();
        trader.setFund(400);
        trader.setName("John");
        List<Equity> equities = new ArrayList<>();
        equities.add(equity);
        trader.setEquities(equities);
        trader = traderRepository.save(trader); //save trader
        Trader findedTrader = traderRepository.findById(trader.getTraderId()).get();  //find saved trader by id
        assertEquals("John",findedTrader.getName());
        assertTrue(equityRepository.findById(equity.getEquityId()).isPresent());
        assertEquals(1,findedTrader.getEquities().size());
        Long traderId = findedTrader.getTraderId();
        traderRepository.delete(findedTrader); //delete saved trader
        assertThrows(RuntimeException.class,
                ()->traderRepository.findById(traderId).get());  //throws exception while finding deleted trader
        Long equityId = equity.getEquityId();
        equityRepository.delete(equity); //delete saved equity
        assertThrows(RuntimeException.class,
                ()->equityRepository.findById(equityId).get()); //throws exception while finding deleted equity
    }
}
