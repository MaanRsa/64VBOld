
package com.maan.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class ReceiptDetails extends ActionForm {

	private String bankName;
	private String bankId;
	private String chequeNo;
	private String receiptNo;
	private String chequeAmount;
	private String method;
	private String actualChequeNo;
	private String actualChequeAmount;
	private String reason;
	private String transactionNo;
	private String status;
	private String receiptDate;
	private String bankChequeNo;
	private String bankChequeAmt;
	private String name;
	private String receipt;
	private String bankAvail;
	private String from;
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getBankAvail() {
		return bankAvail;
	}
	public void setBankAvail(String bankAvail) {
		this.bankAvail = bankAvail;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public String getActualChequeAmount() {
		return actualChequeAmount;
	}
	public void setActualChequeAmount(String actualChequeAmount) {
		this.actualChequeAmount = actualChequeAmount;
	}
	public String getActualChequeNo() {
		return actualChequeNo;
	}
	public void setActualChequeNo(String actualChequeNo) {
		this.actualChequeNo = actualChequeNo;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getChequeAmount() {
		return chequeAmount;
	}
	public void setChequeAmount(String chequeAmount) {
		this.chequeAmount = chequeAmount;
	}
	public String getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getBankChequeAmt() {
		return bankChequeAmt;
	}
	public void setBankChequeAmt(String bankChequeAmt) {
		this.bankChequeAmt = bankChequeAmt;
	}
	public String getBankChequeNo() {
		return bankChequeNo;
	}
	public void setBankChequeNo(String bankChequeNo) {
		this.bankChequeNo = bankChequeNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
		
	
}