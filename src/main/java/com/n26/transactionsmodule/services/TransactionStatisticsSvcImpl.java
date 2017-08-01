package com.n26.transactionsmodule.services;

import java.time.Instant;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.transactionsmodule.db.TransactionStorage;
import com.n26.transactionsmodule.dto.Transaction;
import com.n26.transactionsmodule.dto.TransactionStatistics;
import com.n26.transactionsmodule.exceptions.OlderThanSixtySecException;
import com.n26.transactionsmodule.util.ValidationUtil;

@Service
public class TransactionStatisticsSvcImpl implements TransactionsStatService {
	
	@Autowired
	private TransactionStorage tansactionStorage;
	
	private Instant now;
	
	public TransactionStatisticsSvcImpl() {
		this.now = Instant.now();
	}
	
	@Override
	public void addTransaction(Transaction transaction) throws OlderThanSixtySecException {
		if(ValidationUtil.isTransactionValid(transaction) && !ValidationUtil.isOlderThan60Secs(transaction.getTimestamp(), now)) {
			tansactionStorage.save(transaction);
		} else {
			throw new OlderThanSixtySecException("Time stamp is older than sixty seconds");
		}
	}

	@Override
	public TransactionStatistics getStatistics() {
		ConcurrentNavigableMap<Instant, Transaction> map = tansactionStorage.getStorage();
		Instant sixtySecBeforeNow = now.minusSeconds(60l);
		ConcurrentNavigableMap<Instant, Transaction> subMap = map.subMap(sixtySecBeforeNow, true, now, true);
		List<Transaction> transList = getTransList(subMap);
		TransactionStatistics transStats = null;
		if(null != transList && transList.size() != 0) {
			DoubleSummaryStatistics doubleStats = getDoubleStats(transList);
			transStats = new TransactionStatistics(doubleStats.getSum(), doubleStats.getAverage(),
					doubleStats.getMax(), doubleStats.getMin(), doubleStats.getCount());
		} else {
			transStats = new TransactionStatistics(0d, 0d, 0d, 0d, 0l);
		}
		return transStats;
	}
	
	protected List<Transaction> getTransList(ConcurrentNavigableMap<Instant, Transaction> subMap) {
		List<Transaction> list = new ArrayList<>(subMap.values());
		return list;
	}
	
	protected DoubleSummaryStatistics getDoubleStats(List<Transaction> transList) {
		DoubleSummaryStatistics doubleStat = transList.stream().collect(Collectors.summarizingDouble(Transaction::getAmount));
		return doubleStat;
	}
	
}












