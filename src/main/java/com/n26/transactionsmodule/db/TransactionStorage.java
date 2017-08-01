package com.n26.transactionsmodule.db;

import java.time.Instant;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.springframework.stereotype.Component;

import com.n26.transactionsmodule.dto.Transaction;

@Component
public class TransactionStorage {
	private ConcurrentNavigableMap<Instant, Transaction> transactionStorage;
	
	public TransactionStorage() {
		this.transactionStorage = new ConcurrentSkipListMap<>();
	}
	
	public void save(Transaction transaction) {
		Instant inst = Instant.ofEpochMilli(transaction.getTimestamp());
		transactionStorage.put(inst, transaction);
	}
	
	public ConcurrentNavigableMap<Instant, Transaction> getStorage() {
		return this.transactionStorage;
	}
}
