
package com.maan.user.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class ReceiptDetails extends ActionForm {


	private String receiptagcode;
	private String receiptagname;
	private String receiptbranchcode;
	private String receiptdate;
	private String receiptno;
	private String paymenttype;
	private String chequeno;
	private String chequedate;
	private String amount;
	private String banknameandloc;
	private String particulars;
	private String creditcardtype;
	private String creditcardbank;
	private String transactionreference;
	private String chequestatus;
	private String reason;
	private String depositdate;
	private String receiptNo;
	
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBanknameandloc() {
		return banknameandloc;
	}
	public void setBanknameandloc(String banknameandloc) {
		this.banknameandloc = banknameandloc;
	}
	public String getChequedate() {
		return chequedate;
	}
	public void setChequedate(String chequedate) {
		this.chequedate = chequedate;
	}
	public String getChequeno() {
		return chequeno;
	}
	public void setChequeno(String chequeno) {
		this.chequeno = chequeno;
	}
	public String getCreditcardbank() {
		return creditcardbank;
	}
	public void setCreditcardbank(String creditcardbank) {
		this.creditcardbank = creditcardbank;
	}
	public String getCreditcardtype() {
		return creditcardtype;
	}
	public void setCreditcardtype(String creditcardtype) {
		this.creditcardtype = creditcardtype;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getReceiptagcode() {
		return receiptagcode;
	}
	public void setReceiptagcode(String receiptagcode) {
		this.receiptagcode = receiptagcode;
	}
	public String getReceiptagname() {
		return receiptagname;
	}
	public void setReceiptagname(String receiptagname) {
		this.receiptagname = receiptagname;
	}
	public String getReceiptbranchcode() {
		return receiptbranchcode;
	}
	public void setReceiptbranchcode(String receiptbranchcode) {
		this.receiptbranchcode = receiptbranchcode;
	}
	public String getReceiptdate() {
		return receiptdate;
	}
	public void setReceiptdate(String receiptdate) {
		this.receiptdate = receiptdate;
	}
	public String getReceiptno() {
		return receiptno;
	}
	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}
	public String getTransactionreference() {
		return transactionreference;
	}
	public void setTransactionreference(String transactionreference) {
		this.transactionreference = transactionreference;
	}
	public String getChequestatus() {
		return chequestatus;
	}
	public void setChequestatus(String chequestatus) {
		this.chequestatus = chequestatus;
	}
	public String getDepositdate() {
		return depositdate;
	}
	public void setDepositdate(String depositdate) {
		this.depositdate = depositdate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
		
	
}