package com.klarna.risk.decision.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CustomerDebtRepositoryTest {

	private CustomerDebtRepository customerDebtRepository;


	@BeforeEach
	public void before() {
		customerDebtRepository = new CustomerDebtRepositoryImpl();
	}


	@AfterEach
	public void after() {
		customerDebtRepository = null;
	}


	@Test
	public void newCustomerDebtIsZero() {
		int customerDebt = customerDebtRepository.fetchCustomerDebtForEmail("john@doe.com");
		assertThat(customerDebt).isEqualTo(0);
	}


	@Test
	public void returningCustomerDebtIsNotZero() {
		for (int i = 0; i < 5; i++) {
			int customerDebt = customerDebtRepository.fetchCustomerDebtForEmail("john@doe.com");
			assertThat(customerDebt).isEqualTo(10 * i);
			customerDebtRepository.addCustomerDebt("john@doe.com", 10);
		}
	}


	@Test
	public void customersShouldNotAffectEachOther() {
		int customerDebt = customerDebtRepository.fetchCustomerDebtForEmail("john@doe.com");
		assertThat(customerDebt).isEqualTo(0);

		customerDebtRepository.addCustomerDebt("john@doe.com", 10);
		customerDebt = customerDebtRepository.fetchCustomerDebtForEmail("john@doe.com");
		assertThat(customerDebt).isEqualTo(10);

		customerDebtRepository.addCustomerDebt("test@email.com", 10);
		customerDebt = customerDebtRepository.fetchCustomerDebtForEmail("john@doe.com");
		assertThat(customerDebt).isEqualTo(10);
	}
}
