package com.nagarro.trade.controller;

import com.nagarro.trade.exception.InvalidRequestException;
import com.nagarro.trade.validators.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.trade.service.TraderService;
import com.nagarro.trade.entity.Trader;
import com.nagarro.trade.entity.request.TraderEquityRequest;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TraderController {

	private HttpServletRequest request;

	private TraderService traderService;

	@Autowired
	public TraderController(HttpServletRequest request, TraderService traderService) {
		this.request = request;
		this.traderService = traderService;
	}
	
	
	@PostMapping("/equity/buy")
	public ResponseEntity<Long> buyAnEquity(@RequestBody TraderEquityRequest traderEquityRequest) throws InvalidRequestException {
		if (RequestValidator.isValidRequest(request))
			return new ResponseEntity<>(traderService.buyAnEquity(traderEquityRequest),HttpStatus.CREATED);
		throw new InvalidRequestException("Invalid Request");
	}
	
	@DeleteMapping("/equity/sell")
	public ResponseEntity<String> sellAnEquity(@RequestParam(value = "traderId") Long traderId,
			@RequestParam(value = "equityId") Long equityId) throws InvalidRequestException {
		if (RequestValidator.isValidRequest(request)) {
			traderService.sellAnEquity(traderId, equityId);
			return ResponseEntity.status(HttpStatus.OK).build();
		}else {
			throw new InvalidRequestException("Invalid Request");
		}
	}
	
	@PutMapping("/trader/{traderId}/fund/{fund}")
	public ResponseEntity<Trader> addFund(@PathVariable(required=true) Integer fund,@PathVariable(required=true) Long traderId) throws InvalidRequestException {
		if (RequestValidator.isValidRequest(request))
			return new ResponseEntity<>(traderService.addFunds(fund,traderId),HttpStatus.OK);
		throw new InvalidRequestException("Invalid Request");
	}

}
