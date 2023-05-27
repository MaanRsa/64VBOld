package com.maan.transaction;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.maan.common.base.CommonBaseForm;

public class TransactionForm extends CommonBaseForm
{
	private String method ;
	private String transactionNo;
	private String transaction;
	private String searchIn;
	private String realised;
	private String bankId;
	private int processCount;
	
	
	
	public int getProcessCount() {
		return processCount;
	}

	public void setProcessCount(int processCount) {
		this.processCount = processCount;
	}

	private static final long serialVersionUID = 1L;
	public void reset(final ActionMapping mapping,final HttpServletRequest request) 
	{
		
	}

	public ActionErrors validate(final ActionMapping mapping,
			final HttpServletRequest request) {
		return null;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	

	public String getSearchIn() {
		return searchIn;
	}

	public void setSearchIn(String searchIn) {
		this.searchIn = searchIn;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	

	public String getRealised() {
		return realised;
	}

	public void setRealised(String realised) {
		this.realised = realised;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

		
}