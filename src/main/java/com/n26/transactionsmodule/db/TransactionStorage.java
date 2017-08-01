package com.n26.transactionsmodule.db;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.n26.transactionsmodule.dto.Transaction;

@Component
public class TransactionStorage {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionStorage.class);
	/*
	 * To accommodate for transactions with duplicated timestamps.
	 * Transactions with the same timestamp are put in the same list.
	 */
	private ConcurrentNavigableMap<Instant, List<Transaction>> transactionStorageList;
	
	public TransactionStorage() {
		this.transactionStorageList = new ConcurrentSkipListMap<>();
	}
	
	public void save(Transaction transaction) {
		Instant inst = Instant.ofEpochMilli(transaction.getTimestamp());
		List<Transaction> transList = transactionStorageList.get(inst);
		if(null == transList) {
			transList = new ArrayList<>();
			transList.add(transaction);
			transactionStorageList.put(inst, transList);
		} else {
			transList.add(transaction);
		}
	}
	
	public ConcurrentNavigableMap<Instant, List<Transaction>> getStorageList() {
		return this.transactionStorageList;
	}
	
}












