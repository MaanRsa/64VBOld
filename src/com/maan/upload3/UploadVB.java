package com.maan.upload3;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.maan.common.base.CommonBaseVB;

public class UploadVB extends CommonBaseVB {
	
	private static final long serialVersionUID = 1L;
	private String method ;
	private String transactionDate;
	private String transactionId;
	private String receiptNumber; 
	private String receiptDate;
	private String transSource;
	private String paymentMethod;
	private String chequeNumber;
	private String chequeDate;
	private String chequeAmount;
	private String realisationStatus;
	private String realisationDate;
	private String chequeReturnCode;
	private String chequeReturnReason;
	private String dateTime; 
	private String bankNo;
	private String bankCode;
	private String bankName;
	private String paymentReceipt;
	private String manualRemarks;
	private String depositDate;
	private String depositCreditDate;
	private String draweeBankName;
	
	public String getManualRemarks() {
		return manualRemarks;
	}

	public void setManualRemarks(String manualRemarks) {
		this.manualRemarks = manualRemarks;
	}

	public String getPaymentReceipt() {
		return paymentReceipt;
	}

	public void setPaymentReceipt(String paymentReceipt) {
		this.paymentReceipt = paymentReceipt;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
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

	public String getChequeReturnCode() {
		return chequeReturnCode;
	}

	public void setChequeReturnCode(String chequeReturnCode) {
		this.chequeReturnCode = chequeReturnCode;
	}

	public String getChequeReturnReason() {
		return chequeReturnReason;
	}

	public void setChequeReturnReason(String chequeReturnReason) {
		this.chequeReturnReason = chequeReturnReason;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getRealisationStatus() {
		return realisationStatus;
	}

	public void setRealisationStatus(String realisationStatus) {
		this.realisationStatus = realisationStatus;
	}


	public String getRealisationDate() {
		return realisationDate;
	}

	public void setRealisationDate(String realisationDate) {
		this.realisationDate = realisationDate;
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

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(String depositDate) {
		this.depositDate = depositDate;
	}

	public String getDepositCreditDate() {
		return depositCreditDate;
	}

	public void setDepositCreditDate(String depositCreditDate) {
		this.depositCreditDate = depositCreditDate;
	}

	public String getDraweeBankName() {
		return draweeBankName;
	}

	public void setDraweeBankName(String draweeBankName) {
		this.draweeBankName = draweeBankName;
	}

	
	
}
