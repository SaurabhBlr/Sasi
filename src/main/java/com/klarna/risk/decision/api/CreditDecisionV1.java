package com.klarna.risk.decision.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.klarna.risk.decision.domain.CreditDecision;


/**
 * A class representing credit decision result.
 *
 * @param accepted The result of the request
 * @param reason The reason of the decision
 */
public record CreditDecisionV1(
		@JsonProperty(value = "accepted", required = true)
		boolean accepted,

		@JsonProperty(value = "reason", required = true)
		String reason) {

	public static CreditDecisionV1 from(CreditDecision creditDecision) {
		return new CreditDecisionV1(creditDecision.isAccepted(), creditDecision.getReason());
	}
}
