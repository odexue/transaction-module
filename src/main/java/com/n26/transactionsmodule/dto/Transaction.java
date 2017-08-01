package com.n26.transactionsmodule.dto;

public class Transaction {
	private Double amount;
	private Long timestamp;
	
	public Transaction(Double amount, Long timeStamp) {
		this.amount = amount;
		this.timestamp = timeStamp;
	}
	
	public Transaction() {
		
	}
	
	public Double getAmount() {
		return amount;
	}
	public Long getTimestamp() {
		return timestamp;
	}
}
