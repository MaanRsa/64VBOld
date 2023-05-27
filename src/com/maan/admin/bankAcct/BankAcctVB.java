package com.maan.admin.bankAcct;

import java.math.BigDecimal;

public class BankAcctVB {
	
	private String bankid;
	private String bankname;
	private String bankAcctCode;
	private BigDecimal bankAcctNo;
	private String status;
	public String getBankAcctCode() {
		return bankAcctCode;
	}
	public void setBankAcctCode(String bankAcctCode) {
		this.bankAcctCode = bankAcctCode.toUpperCase();
	}
	public BigDecimal getBankAcctNo() {
		return bankAcctNo;
	}
	public void setBankAcctNo(BigDecimal bankAcctNo) {
		this.bankAcctNo = bankAcctNo;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
