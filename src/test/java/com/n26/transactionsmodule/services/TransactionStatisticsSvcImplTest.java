package com.n26.transactionsmodule.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.n26.transactionsmodule.db.TransactionStorage;
import com.n26.transactionsmodule.dto.Transaction;
import com.n26.transactionsmodule.dto.TransactionStatistics;
import com.n26.transactionsmodule.exceptions.InvalidDataException;


public class TransactionStatisticsSvcImplTest {

	@Mock
	private TransactionStorage transactionStorage;
	
	@InjectMocks
	private TransactionStatisticsSvcImpl svcImpl = new TransactionStatisticsSvcImpl();
	
	private Instant now;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		now = Instant.now();
	}
	
	@Test
	public void addTransactionTest() {
		Transaction trans = new Transaction(null, null);
		boolean noError = true;
		try {
			svcImpl.addTransaction(trans);
		} catch (InvalidDataException e) {
			noError = false;
		}
		assertFalse(noError);
	}
	
	@Test
	public void getStatisticsTest() {
		when(transactionStorage.getStorageList()).thenReturn(dummyMapList());
		TransactionStatistics transStat = svcImpl.getStatistics();
		assertTrue(transStat.getCount() == 1);
	}
	
	@Test
	public void getTransListTest() {
		List<Transaction> list = svcImpl.getAllTransList(dummyMapList());
		assertTrue(list.size() == 1);
	}
	
	@Test
	public void getDoubleStatsTEst() {
		DoubleSummaryStatistics dobStats = svcImpl.getDoubleStats(dummyTransList());
		assertTrue(dobStats.getCount() == 2l);
		assertTrue(dobStats.getSum() == (12.4+33.99));
		assertTrue(dobStats.getMin() == 12.4);
		assertTrue(dobStats.getMax() == 33.99);
		assertTrue(dobStats.getAverage() == dobStats.getSum()/2);
	}
	
	private List<Transaction> dummyTransList() {
		List<Transaction> list = new ArrayList<>();
		Transaction trans = new Transaction(12.4, 1478192204000l);
		list.add(trans);
		trans = new Transaction(33.99, 1478192204000l);
		list.add(trans);
		return list;
	}
	
	private ConcurrentNavigableMap<Instant, Transaction> dummyMap() {
		ConcurrentNavigableMap<Instant, Transaction> map = new ConcurrentSkipListMap<>();
		Instant target = now.minusSeconds(40l);
		map.put(target, new Transaction(12.33, target.toEpochMilli()));
		return map;
	}
	
	private ConcurrentNavigableMap<Instant, List<Transaction>> dummyMapList() {
		List<Transaction> transList = new ArrayList<>();
		Instant target = now.minusSeconds(40l);
		Transaction trans = new Transaction(12.33, target.toEpochMilli());
		transList.add(trans);
		ConcurrentNavigableMap<Instant, List<Transaction>> map = new ConcurrentSkipListMap<>();
		map.put(target, transList);
		return map;
	}

}






