package com.n26.transactionsmodule.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.transactionsmodule.dto.Transaction;
import com.n26.transactionsmodule.dto.TransactionStatistics;
import com.n26.transactionsmodule.exceptions.InvalidDataException;
import com.n26.transactionsmodule.services.TransactionsStatService;
import com.n26.transactionsmodule.util.ValidationUtil;


@RestController
@RequestMapping("/")
public class TransactionStatisticsApi {
	
	@Autowired
	private TransactionsStatService transactionsStatService;
	
	@RequestMapping(value={"transactions"}, method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) throws InvalidDataException{
		ResponseEntity<Void> resp = null;
		if(ValidationUtil.isOlderThan60Secs(transaction.getTimestamp())) {
			resp = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			resp = ResponseEntity.status(HttpStatus.CREATED).build();
		}
		transactionsStatService.addTransaction(transaction);
		return resp;
	}
	
	@RequestMapping(value={"statistics"}, method = {RequestMethod.GET})
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public TransactionStatistics getStatistics(){
		return transactionsStatService.getStatistics();
	}
	
	
}
