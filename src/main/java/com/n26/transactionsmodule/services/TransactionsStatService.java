package com.n26.transactionsmodule.services;

import com.n26.transactionsmodule.dto.Transaction;
import com.n26.transactionsmodule.dto.TransactionStatistics;
import com.n26.transactionsmodule.exceptions.InvalidDataException;

public interface TransactionsStatService {
	
	public void addTransaction(Transaction transaction) throws InvalidDataException;
	
	public TransactionStatistics getStatistics();
	
}
