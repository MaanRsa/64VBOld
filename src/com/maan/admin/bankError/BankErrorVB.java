package com.maan.admin.bankError;

public class BankErrorVB {
	
	private String bankid;
	private String reasondesc;
	private String rejectionType;
	private String active;
    private String errorid;
    private String page;
    
    
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getErrorid() {
		return errorid;
	}
	public void setErrorid(String errorid) {
		this.errorid = errorid;
	}
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
		this.bankid = bankid;
	}
	public String getReasondesc() {
		return reasondesc;
	}
	public void setReasondesc(String reasondesc) {
		this.reasondesc = reasondesc.toUpperCase();
	}
	public String getRejectionType() {
		return rejectionType;
	}
	public void setRejectionType(String rejectionType) {
		this.rejectionType = rejectionType.toUpperCase();
	}
	
}
