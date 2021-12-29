package com.nagarro.trade.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class TraderEquityRequest {
	@NonNull
	@JsonProperty("traderId")
	private Long traderId;
	@NonNull
	@JsonProperty("equityId")
	private Long equityId;

	public TraderEquityRequest(){
		super();
	}

	public TraderEquityRequest(@NonNull Long traderId, @NonNull Long equityId) {
		this.traderId = traderId;
		this.equityId = equityId;
	}
}
