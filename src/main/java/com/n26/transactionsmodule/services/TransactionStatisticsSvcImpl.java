package com.n26.transactionsmodule.services;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.transactionsmodule.db.TransactionStorage;
import com.n26.transactionsmodule.dto.Transaction;
import com.n26.transactionsmodule.dto.TransactionStatistics;
import com.n26.transactionsmodule.exceptions.InvalidDataException;
import com.n26.transactionsmodule.util.ValidationUtil;

@Service
public class TransactionStatisticsSvcImpl implements TransactionsStatService {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionStatisticsSvcImpl.class);
	
	@Autowired
	private TransactionStorage tansactionStorage;
	
	@Override
	public void addTransaction(Transaction transaction) throws InvalidDataException {
		if(ValidationUtil.isTransactionValid(transaction)) {
			tansactionStorage.save(transaction);
		} else {
			throw new InvalidDataException("Cannot save invalid data");
		}
	}

	@Override
	public TransactionStatistics getStatistics() {
		ConcurrentNavigableMap<Instant, List<Transaction>> mapList = tansactionStorage.getStorageList();
		Instant now = Instant.now();
		Instant sixtySecBeforeNow = now.minusSeconds(60l);
		//LOOKS FOR INSTANTS THAT HAPPENED WITHIN THE LAST 60 SECONDS FROM NOW
		ConcurrentNavigableMap<Instant, List<Transaction>> subMapList = mapList.subMap(sixtySecBeforeNow, true, now, true);
		List<Transaction> allTransList = getAllTransList(subMapList);
		TransactionStatistics transStats = null;
		if(null != allTransList && allTransList.size() != 0) {
			DoubleSummaryStatistics doubleStats = getDoubleStats(allTransList);
			transStats = new TransactionStatistics(doubleStats.getSum(), doubleStats.getAverage(),
					doubleStats.getMax(), doubleStats.getMin(), doubleStats.getCount());
		} else {
			transStats = new TransactionStatistics(0d, 0d, 0d, 0d, 0l);
		}
		return transStats;
	}
	
	protected DoubleSummaryStatistics getDoubleStats(List<Transaction> transList) {
		DoubleSummaryStatistics doubleStat = transList.stream().collect(Collectors.summarizingDouble(Transaction::getAmount));
		return doubleStat;
	}
	
	protected List<Transaction> getAllTransList(ConcurrentNavigableMap<Instant, List<Transaction>> subMapList) {
		List<Transaction> list = new ArrayList<>();
		for(Instant inst : subMapList.keySet()) {
			list.addAll(subMapList.get(inst));
		}
		return list;
	}
}












