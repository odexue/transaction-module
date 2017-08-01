package com.n26.transactionsmodule.dto;

public class TransactionStatistics {
	
	private Double sum;
	private Double avg;
	private Double max;
	private Double min;
	private Long count;
	
	public TransactionStatistics(Double sum, Double avg, Double max, Double min, Long count) {
		this.sum = sum;
		this.avg = avg;
		this.max = max;
		this.min = min;
		this.count = count;
	}
	
	public TransactionStatistics() {
		
	}

	public Double getSum() {
		return sum;
	}

	public Double getAvg() {
		return avg;
	}

	public Double getMax() {
		return max;
	}

	public Double getMin() {
		return min;
	}

	public Long getCount() {
		return count;
	}
}
