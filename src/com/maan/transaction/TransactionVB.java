package com.maan.transaction;

import com.maan.common.base.CommonBaseVB;

public class TransactionVB extends CommonBaseVB 
{
	private static final long serialVersionUID = 1L;
	
	private String transactionNo;
	private String transactionNo2;
	private String totalRecords;
	private String duplicates;
	private String chequeexists;
	private String chequenotexists;
	private String matched;
	private String pending;
	private String processed="N";
	private String transdate;
	private String exists;
	private String recorddates;
	private String recordcounts;
	private String checkStatus;
	private String paymentRecords;
	private String invalid;
	private String reversals;
	private String available;
	private String paymentsMatched;
	
	
	
	public String getPaymentsMatched() {
		return paymentsMatched;
	}
	public void setPaymentsMatched(String paymentsMatched) {
		this.paymentsMatched = paymentsMatched;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getInvalid() {
		return invalid;
	}
	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}
	public String getPaymentRecords() {
		return paymentRecords;
	}
	public void setPaymentRecords(String paymentRecords) {
		this.paymentRecords = paymentRecords;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getRecordcounts() {
		return recordcounts;
	}
	public void setRecordcounts(String recordcounts) {
		this.recordcounts = recordcounts;
	}
	public String getRecorddates() {
		return recorddates;
	}
	public void setRecorddates(String recorddates) {
		this.recorddates = recorddates;
	}
	public String getExists() {
		return exists;
	}
	public void setExists(String exists) {
		this.exists = exists;
	}
	public String getTransdate() {
		return transdate;
	}
	public void setTransdate(String transdate) {
		this.transdate = transdate;
	}
	public String getProcessed() {
		return processed;
	}
	public void setProcessed(String processed) {
		this.processed = processed;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public String getChequeexists() {
		return chequeexists;
	}
	public void setChequeexists(String chequeexists) {
		this.chequeexists = chequeexists;
	}
	public String getChequenotexists() {
		return chequenotexists;
	}
	public void setChequenotexists(String chequenotexists) {
		this.chequenotexists = chequenotexists;
	}
	public String getDuplicates() {
		return duplicates;
	}
	public void setDuplicates(String duplicates) {
		this.duplicates = duplicates;
	}
	public String getMatched() {
		return matched;
	}
	public void setMatched(String matched) {
		this.matched = matched;
	}
	public String getPending() {
		return pending;
	}
	public void setPending(String pending) {
		this.pending = pending;
	}
	public String getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getTransactionNo2() {
		return transactionNo2;
	}
	public void setTransactionNo2(String transactionNo2) {
		this.transactionNo2 = transactionNo2;
	}
	public String getReversals() {
		return reversals;
	}
	public void setReversals(String reversals) {
		this.reversals = reversals;
	}
	
	
}