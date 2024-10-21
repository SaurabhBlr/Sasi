package com.klarna.risk.decision.controller;

import static com.google.common.base.Preconditions.checkArgument;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Strings;
import com.klarna.risk.decision.api.CreditDecisionV1;
import com.klarna.risk.decision.api.CreditRequestV1;
import com.klarna.risk.decision.service.CreditDecisionServiceV1;


@RestController
public class RiskDecisionController {

	private final CreditDecisionServiceV1 creditDecisionService;


	public RiskDecisionController(CreditDecisionServiceV1 creditDecisionService) {
		this.creditDecisionService = creditDecisionService;
	}


	@PostMapping(value = "/v1/decision", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CreditDecisionV1> decision(@RequestBody CreditRequestV1 creditRequest) {
		checkArgument(creditRequest != null);
		checkArgument(!Strings.isNullOrEmpty(creditRequest.email()));
		checkArgument(!Strings.isNullOrEmpty(creditRequest.firstName()));
		checkArgument(!Strings.isNullOrEmpty(creditRequest.lastName()));
		checkArgument(creditRequest.purchaseAmount() > 0);
		return ResponseEntity.ok(creditDecisionService.handleCreditRequestV1(creditRequest));
	}

}
