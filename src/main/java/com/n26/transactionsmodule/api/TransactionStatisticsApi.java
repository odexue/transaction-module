package com.n26.transactionsmodule.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.transactionsmodule.dto.Transaction;
import com.n26.transactionsmodule.dto.TransactionStatistics;
import com.n26.transactionsmodule.exceptions.OlderThanSixtySecException;
import com.n26.transactionsmodule.services.TransactionsStatService;


@RestController
@RequestMapping("/")
public class TransactionStatisticsApi {
	
	@Autowired
	private TransactionsStatService transactionsStatService;
	
	@RequestMapping(value={"transactions"}, method = {RequestMethod.POST})
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public void addTransaction(@RequestBody Transaction transaction) throws OlderThanSixtySecException{
		transactionsStatService.addTransaction(transaction);
	}
	
	@RequestMapping(value={"statistics"}, method = {RequestMethod.GET})
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public TransactionStatistics getStatistics(){
		return transactionsStatService.getStatistics();
	}
}
