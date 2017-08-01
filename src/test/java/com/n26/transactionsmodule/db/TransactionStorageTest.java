package com.n26.transactionsmodule.db;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.n26.transactionsmodule.dto.Transaction;

public class TransactionStorageTest {
	
	private TransactionStorage transactionStorage;
	
	@Before
	public void setup() {
		transactionStorage = new TransactionStorage();
	}
	
	@Test
	public void saveTest() {
		Transaction trans = new Transaction(12.4, 1478192204000l);
		transactionStorage.save(trans);
		assertTrue(transactionStorage.getStorage().size() == 1);
	}

}
