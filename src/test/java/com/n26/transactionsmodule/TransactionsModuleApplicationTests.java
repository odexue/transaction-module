package com.n26.transactionsmodule;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.transactionsmodule.dto.Transaction;
import com.n26.transactionsmodule.dto.TransactionStatistics;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class TransactionsModuleApplicationTests {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	private static final String API_TRANSACTION = "/transactions";
	private static final String API_STATISTICS = "/statistics";
	
	@Test
	public void test() {
		assertNotNull(restTemplate);
		
		Instant now = Instant.now();
		now = now.plus(20l, ChronoUnit.HOURS);
		
		Transaction trans = new Transaction(12.4, now.toEpochMilli());
		ResponseEntity responseEntity = restTemplate.postForEntity(API_TRANSACTION, trans, String.class);
		assertTrue(responseEntity.getStatusCode().toString().equalsIgnoreCase(HttpStatus.NO_CONTENT.toString()));
		
		now = Instant.now();
		now = now.minusSeconds(20l);
		trans = new Transaction(12.4, now.toEpochMilli());
		responseEntity = restTemplate.postForEntity(API_TRANSACTION, trans, String.class);
		assertTrue(responseEntity.getStatusCode().toString().equalsIgnoreCase(HttpStatus.CREATED.toString()));
		
		now = Instant.now();
		now = now.minusSeconds(20l);
		trans = new Transaction(12.4, now.toEpochMilli());
		restTemplate.postForEntity(API_TRANSACTION, trans, String.class);
		
		ResponseEntity<TransactionStatistics> responseEntityStat = restTemplate.getForEntity(API_STATISTICS, TransactionStatistics.class);
		assertTrue(responseEntityStat.getBody().getCount() == 2);
		assertTrue(responseEntityStat.getBody().getSum() == (12.4*2));
	}
	
}


















