package com.klarna.risk.decision;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.klarna.risk.decision.api.CreditDecisionV1;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = RiskDecisionApplication.class)
@Import({
		TestClient.class
})
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CreditDecisionApiTest {

	@Autowired
	private TestClient testClient;


	@Test
	public void customerDebtLimitShouldBeExceeded() {
		ResponseEntity<CreditDecisionV1> response;
		for (int i = 0; i < 10; i++) {
			response = sendCreditDecision(10);
			assertThat(response.getStatusCode().value()).isEqualTo(200);
			CreditDecisionV1 creditDecision = response.getBody();
			assertThat(creditDecision).isNotNull();
			assertThat(creditDecision.accepted()).isTrue();
			assertThat(creditDecision.reason()).isEqualTo("ok");
		}

		response = sendCreditDecision(1);
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		CreditDecisionV1 creditDecision = response.getBody();
		assertThat(creditDecision).isNotNull();
		assertThat(creditDecision.accepted()).isFalse();
		assertThat(creditDecision.reason()).isEqualTo("debt");
	}


	@Test
	public void requestUpTo10ShouldBeAccepted() {
		ResponseEntity<CreditDecisionV1> response = sendCreditDecision(10);
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		CreditDecisionV1 creditDecision = response.getBody();
		assertThat(creditDecision).isNotNull();
		assertThat(creditDecision.accepted()).isTrue();
		assertThat(creditDecision.reason()).isEqualTo("ok");
	}


	@Test
	public void requestAbove10ShouldNotBeAccepted() {
		ResponseEntity<CreditDecisionV1> response = sendCreditDecision(11);
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		CreditDecisionV1 creditDecision = response.getBody();
		assertThat(creditDecision).isNotNull();
		assertThat(creditDecision.accepted()).isFalse();
		assertThat(creditDecision.reason()).isEqualTo("amount");
	}


	private ResponseEntity<CreditDecisionV1> sendCreditDecision(int purchaseAmount) {
		return testClient.creditDecision("john@doe.com", "john", "doe", purchaseAmount);
	}

}
