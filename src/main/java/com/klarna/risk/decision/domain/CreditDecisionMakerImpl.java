package com.klarna.risk.decision.domain;

import org.springframework.stereotype.Component;


/**
 * The implementation of the {@link CreditDecisionMaker} interface.
 */
@Component
public class CreditDecisionMakerImpl implements CreditDecisionMaker {

	@Override
	public CreditDecision makeCreditDecision(int purchaseAmount, int currentCustomerDebt) {
		return CreditDecision.ACCEPTED;
	}

}
