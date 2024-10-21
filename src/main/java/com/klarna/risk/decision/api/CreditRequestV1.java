package com.klarna.risk.decision.api;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A class representing the details of credit request.
 * @param email The email of the customer
 * @param purchaseAmount The total amount of this purchase
 * @param firstName The first name of the customer
 * @param lastName The last name of the customer
 */
public record CreditRequestV1(
		@JsonProperty(value = "email", required = true)
		String email,

		@JsonProperty(value = "first_name")
		String firstName,

		@JsonProperty(value = "last_name")
		String lastName,

		@JsonProperty(value = "purchase_amount", required = true)
		int purchaseAmount) {

}
