package com.klarna.risk.decision.repository;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.klarna.risk.decision.domain.CreditDecision;


/**
 * The implementation of the {@link CreditHistoryRepository} interface.
 */
@Repository
public class CreditHistoryRepositoryImpl implements CreditHistoryRepository {

	@Override
	public Collection lookupTransactions(String email) {
		throw new UnsupportedOperationException("The method is not implemented!");
	}


	@Override
	public Collection lookupTransactions(String email, String reason) {
		throw new UnsupportedOperationException("The method is not implemented!");
	}


	@Override
	public void persistTransaction(String email, int purchaseAmount, CreditDecision decision) {
		throw new UnsupportedOperationException("The method is not implemented!");
	}
}
