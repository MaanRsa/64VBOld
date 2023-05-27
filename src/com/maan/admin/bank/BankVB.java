package com.maan.admin.bank;

public class BankVB {
	
	private String bankid;
	private String bankname;
	private String banktable;
	private String chequeno;
	private String chequeamt;
	private String chequestatus;
	private String reason;
	private String active;
	private String receiptNo;
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid.toUpperCase();
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBanktable() {
		return banktable;
	}
	public void setBanktable(String banktable) {
		this.banktable = banktable;
	}
	public String getChequeamt() {
		return chequeamt;
	}
	public void setChequeamt(String chequeamt) {
		this.chequeamt = chequeamt;
	}
	public String getChequeno() {
		return chequeno;
	}
	public void setChequeno(String chequeno) {
		this.chequeno = chequeno;
	}
	public String getChequestatus() {
		return chequestatus;
	}
	public void setChequestatus(String chequestatus) {
		this.chequestatus = chequestatus;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	
}
