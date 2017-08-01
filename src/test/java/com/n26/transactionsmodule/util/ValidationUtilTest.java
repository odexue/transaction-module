package com.n26.transactionsmodule.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

import com.n26.transactionsmodule.dto.Transaction;


public class ValidationUtilTest {

private Instant now;
	
	@Before
	public void setup() {
		now = Instant.now();
	}
	
	@Test
	public void isOlderThan60SecsTest() {
		assertTrue(ValidationUtil.isOlderThan60Secs(1504200549000l, this.now));
	}
	
	@Test
	public void isTransactionValidTest() {
		assertFalse(ValidationUtil.isTransactionValid(new Transaction(null, null)));
	}

}
