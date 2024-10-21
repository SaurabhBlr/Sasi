package com.klarna.risk.decision.repository;

/**
 * An interface for handling the persistence of customer's debt.
 */
public interface CustomerDebtRepository {

	/**
	 * Fetching current debt for the customer of given email address.
	 *
	 * @param email the primary identifier of the customer
	 * @return the customer's current debt
	 */
	int fetchCustomerDebtForEmail(String email);

	/**
	 * Add customer's debt.
	 *
	 * @param email the primary identifier of the customer
	 * @param addedCustomerDebt the added customer's debt
	 */
	void addCustomerDebt(String email, int addedCustomerDebt);

}