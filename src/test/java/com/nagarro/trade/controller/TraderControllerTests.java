package com.nagarro.trade.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.trade.exception.ExceptionHandler;
import com.nagarro.trade.service.TraderService;
import com.nagarro.trade.entity.Trader;
import com.nagarro.trade.entity.request.TraderEquityRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TraderController.class)
public class TraderControllerTests {

    @MockBean
    private TraderService traderService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpServletRequest request;

    @BeforeEach
    public void setup() {
        request = new MockHttpServletRequest();
        request.addHeader("Accept", "application/json");
        TraderController traderController = new TraderController(request, traderService);
        mockMvc = MockMvcBuilders.standaloneSetup(traderController).setControllerAdvice(new ExceptionHandler()).build();
    }

    @Test
    public void testBuyAnIndividual() throws Exception{
        TraderEquityRequest traderEquityRequest = new TraderEquityRequest(1L, 1L);
        when(traderService.buyAnEquity(traderEquityRequest)).thenReturn(1L);
        mockMvc.perform(post("/equity/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(traderEquityRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Matchers.equalTo(1)));
    }

    @Test
    public void testBuyAnIndividualReturnsBadRequest() throws Exception{
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept", "application/xml");
        TraderController traderController = new TraderController(request, traderService);
        mockMvc = MockMvcBuilders.standaloneSetup(traderController).setControllerAdvice(new ExceptionHandler()).build();
        TraderEquityRequest traderEquityRequest = new TraderEquityRequest(1L, 1L);
        when(traderService.buyAnEquity(traderEquityRequest)).thenReturn(1L);
        mockMvc.perform(post("/equity/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(traderEquityRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSellAnEquity() throws Exception{
        Long traderId=1L;
        Long equityId=1L;
        doNothing().when(traderService).sellAnEquity(traderId,equityId);
        mockMvc.perform(delete("/equity/sell")
                .param("traderId", "1")
                .param("equityId","1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowBadRequestIfSendingInvalidArgumentsWhileSellingAnEquity() throws Exception{
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept", "application/xml");
        TraderController traderController = new TraderController(request, traderService);
        mockMvc = MockMvcBuilders.standaloneSetup(traderController).setControllerAdvice(new ExceptionHandler()).build();
        Long traderId=1L;
        Long equityId=1L;
        doNothing().when(traderService).sellAnEquity(traderId,equityId);
        mockMvc.perform(delete("/equity/sell")
                .param("traderId", "1")
                .param("equityId","1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddFunds() throws Exception{
        Trader trader = new Trader();
        trader.setTraderId(1L);
        trader.setName("John");
        trader.setFund(1000);
        trader.setEquities(new ArrayList<>());
        when(traderService.addFunds(Mockito.anyInt(),Mockito.anyLong())).thenReturn(trader);
        mockMvc.perform(put("/trader/1/fund/100"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.traderId", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.fund", Matchers.equalTo(1000)))
                .andExpect(jsonPath("$.name", Matchers.is("John")))
                .andExpect(jsonPath("$.equities", Matchers.empty()));
    }

    @Test
    public void shouldThrowBadRequestIfSendingInvalidArgumentsWhileAddingFunds() throws Exception{
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Accept", "application/xml");
        TraderController traderController = new TraderController(request, traderService);
        mockMvc = MockMvcBuilders.standaloneSetup(traderController).setControllerAdvice(new ExceptionHandler()).build();
        Trader trader = new Trader();
        trader.setTraderId(1L);
        trader.setName("John");
        trader.setFund(1000);
        trader.setEquities(new ArrayList<>());
        when(traderService.addFunds(Mockito.anyInt(),Mockito.anyLong())).thenReturn(trader);
        mockMvc.perform(put("/trader/1/fund/100"))
                .andExpect(status().isBadRequest());
    }
}
