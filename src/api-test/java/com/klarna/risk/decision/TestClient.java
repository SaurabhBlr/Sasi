package com.klarna.risk.decision;

import java.util.List;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.klarna.risk.decision.api.CreditDecisionV1;
import com.klarna.risk.decision.api.CreditRequestV1;


@Lazy
@TestComponent
public class TestClient {

	private final TestRestTemplate restTemplate;
	private final String host;


	public TestClient(TestRestTemplate restTemplate, @LocalServerPort int port) {
		this.restTemplate = restTemplate;
		this.host = "http://localhost:" + port;
	}


	public ResponseEntity<CreditDecisionV1> creditDecision(String email, String firstName, String lastName, int purchaseAmount) {
		CreditRequestV1 requestBody = new CreditRequestV1(email, firstName, lastName, purchaseAmount);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(List.of(MediaType.APPLICATION_JSON));
		return restTemplate.exchange(host + "/v1/decision", HttpMethod.POST, new HttpEntity<>(requestBody, headers), CreditDecisionV1.class);
	}

}
