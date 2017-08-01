package com.n26.transactionsmodule.util;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.n26.transactionsmodule.dto.Transaction;


public final class ValidationUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ValidationUtil.class);
	
	public static boolean isTransactionValid(Transaction transaction) {
		if(null != transaction && null != transaction.getAmount() && null != transaction.getTimestamp()) {
			return true;
		}
		return false;
	}
	
	public static boolean isOlderThan60Secs(long timeStamp, Instant now) {
		Instant targetInstant = Instant.ofEpochMilli(timeStamp);
		if(targetInstant.isAfter(now)) {
			return true;
		}
		return false;
	}
	
}









