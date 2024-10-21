package com.klarna.risk.decision.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;


/**
 * The implementation of the {@link CustomerDebtRepository} interface.
 */
@Repository
public class CustomerDebtRepositoryImpl implements CustomerDebtRepository {

	private final Map<String, Integer> customerDebtStorage = Maps.newConcurrentMap();


	@Override
	public int fetchCustomerDebtForEmail(String email) {
		return customerDebtStorage.getOrDefault(email, 0);
	}


	@Override
	public void addCustomerDebt(String email, int amount) {
		customerDebtStorage.compute(email, (key, oldDebt) -> oldDebt == null ? amount : oldDebt + amount);
	}

}
