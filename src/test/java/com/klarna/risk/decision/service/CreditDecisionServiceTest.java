package com.klarna.risk.decision.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.klarna.risk.decision.api.CreditDecisionV1;
import com.klarna.risk.decision.api.CreditRequestV1;
import com.klarna.risk.decision.domain.CreditDecision;
import com.klarna.risk.decision.domain.CreditDecisionMaker;
import com.klarna.risk.decision.repository.CreditHistoryRepository;
import com.klarna.risk.decision.repository.CustomerDebtRepository;


@ExtendWith(MockitoExtension.class)
public class CreditDecisionServiceTest {

	@Mock
	private CustomerDebtRepository customerDebtRepository;

	@Mock
	private CreditDecisionMaker creditDecisionMaker;

	@Mock
	private CreditHistoryRepository creditHistoryRepository;

	@InjectMocks
	private CreditDecisionServiceV1 creditDecisionService;


	@Test
	public void shouldAcceptCreditRequest() {
		CreditRequestV1 creditRequest = defaultCreditRequestOfPurchaseAmount(10);

		when(customerDebtRepository.fetchCustomerDebtForEmail(creditRequest.email()))
				.thenReturn(7);
		when(creditDecisionMaker.makeCreditDecision(10, 7))
				.thenReturn(CreditDecision.ACCEPTED);

		CreditDecisionV1 decision = creditDecisionService.handleCreditRequestV1(creditRequest);
		assertThat(decision.accepted()).isTrue();
		assertThat(decision.reason()).isEqualTo("ok");
	}


	@Test
	public void shouldRejectCreditRequestAmount() {
		CreditRequestV1 creditRequest = defaultCreditRequestOfPurchaseAmount(11);

		when(customerDebtRepository.fetchCustomerDebtForEmail(creditRequest.email()))
				.thenReturn(7);
		when(creditDecisionMaker.makeCreditDecision(11, 7))
				.thenReturn(CreditDecision.MAX_AMOUNT_BREACH);

		CreditDecisionV1 decision = creditDecisionService.handleCreditRequestV1(creditRequest);
		assertThat(decision.accepted()).isFalse();
		assertThat(decision.reason()).isEqualTo("amount");
	}


	@Test
	public void shouldRejectCreditRequestDebt() {
		CreditRequestV1 creditRequest = defaultCreditRequestOfPurchaseAmount(5);

		when(customerDebtRepository.fetchCustomerDebtForEmail(creditRequest.email()))
				.thenReturn(97);
		when(creditDecisionMaker.makeCreditDecision(5, 97))
				.thenReturn(CreditDecision.DEBT);

		CreditDecisionV1 decision = creditDecisionService.handleCreditRequestV1(creditRequest);
		assertThat(decision.accepted()).isFalse();
		assertThat(decision.reason()).isEqualTo("debt");
	}


	@Test
	public void shouldAddCustomerDebtWhenCreditAccepted() {
		CreditRequestV1 creditRequest = defaultCreditRequestOfPurchaseAmount(10);

		when(customerDebtRepository.fetchCustomerDebtForEmail(creditRequest.email()))
				.thenReturn(7);
		when(creditDecisionMaker.makeCreditDecision(10, 7))
				.thenReturn(CreditDecision.ACCEPTED);

		creditDecisionService.handleCreditRequestV1(creditRequest);

		ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
		verify(customerDebtRepository).addCustomerDebt(eq(creditRequest.email()), captor.capture());
		assertThat(captor.getValue()).isEqualTo(10);
	}


	private CreditRequestV1 defaultCreditRequestOfPurchaseAmount(int amount) {
		return new CreditRequestV1("john@doe.com", "John", "Doe", amount);
	}

}
