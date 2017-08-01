package com.n26.transactionsmodule.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

import com.n26.transactionsmodule.dto.Transaction;


public class ValidationUtilTest {
	
	@Test
	public void isOlderThan60SecsTest() {
		assertFalse(ValidationUtil.isOlderThan60Secs(1504200549000l));
	}
	
	@Test
	public void isTransactionValidTest() {
		assertFalse(ValidationUtil.isTransactionValid(new Transaction(null, null)));
	}

}
