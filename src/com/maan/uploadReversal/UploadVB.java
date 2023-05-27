package com.maan.uploadReversal;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.maan.common.base.CommonBaseVB;

public class UploadVB extends CommonBaseVB {

	
	private static final long serialVersionUID = 1L;
	private String method ;
    private String transactionDate;
	private String transactionId;
	private String invoiceno;
	private String errordata;
	private String receiptNumber; 
	private String receiptDate;
	private String transSource;
	private String chequeNumber;
	private String chequeDate;
	private String chequeAmount;
	private String receiptAGName;
	private String receiptslno;
	private boolean checkedstatus=false;
	private String status;
	private String paymentNo;
	private String matched;
	
	
	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getMatched() {
		return matched;
	}

	public void setMatched(String matched) {
		this.matched = matched;
	}

	public boolean isCheckedstatus() {
		return checkedstatus;
	}

	public void setCheckedstatus(boolean checkedstatus) {
		this.checkedstatus = checkedstatus;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	public String getErrordata() {
		return errordata;
	}

	public void setErrordata(String errordata) {
		this.errordata = errordata;
	}

	public String getChequeAmount() {
		return chequeAmount;
	}

	public void setChequeAmount(String chequeAmount) {
		this.chequeAmount = chequeAmount;
	}

	public String getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getChequeNumber() {
		return chequeNumber;
	}

	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getTransSource() {
		return transSource;
	}

	public void setTransSource(String transSource) {
		this.transSource = transSource;
	}

	public String getReceiptAGName() {
		return receiptAGName;
	}

	public void setReceiptAGName(String receiptAGName) {
		this.receiptAGName = receiptAGName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReceiptslno() {
		return receiptslno;
	}

	public void setReceiptslno(String receiptslno) {
		this.receiptslno = receiptslno;
	}

	
	
	
	
}
