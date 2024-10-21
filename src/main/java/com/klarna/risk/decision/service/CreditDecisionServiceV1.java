package com.klarna.risk.decision.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klarna.risk.decision.api.CreditDecisionV1;
import com.klarna.risk.decision.api.CreditRequestV1;
import com.klarna.risk.decision.domain.CreditDecision;
import com.klarna.risk.decision.domain.CreditDecisionMaker;
import com.klarna.risk.decision.repository.CustomerDebtRepository;


/**
 * The public API of the credit decision solution.
 */
@Service
public class CreditDecisionServiceV1 {

	private final CustomerDebtRepository customerDebtRepository;
	private final CreditDecisionMaker creditDecisionMaker;


	@Autowired
	public CreditDecisionServiceV1(CustomerDebtRepository customerDebtRepository, CreditDecisionMaker creditDecisionMaker) {
		this.customerDebtRepository = customerDebtRepository;
		this.creditDecisionMaker = creditDecisionMaker;
	}


	/**
	 * Handles the credit decision process.
	 *
	 * @param creditRequestV1 The credit request with the amount and the customer's details
	 * @return The decision
	 */
	public CreditDecisionV1 handleCreditRequestV1(CreditRequestV1 creditRequestV1) {

		int customerDebt = customerDebtRepository.fetchCustomerDebtForEmail(creditRequestV1.email());

		CreditDecision creditDecision = creditDecisionMaker.makeCreditDecision(creditRequestV1.purchaseAmount(), customerDebt);

		if (creditDecision.isAccepted()) {
			customerDebtRepository.addCustomerDebt(creditRequestV1.email(), creditRequestV1.purchaseAmount());
		}

		return CreditDecisionV1.from(creditDecision);
	}

}
