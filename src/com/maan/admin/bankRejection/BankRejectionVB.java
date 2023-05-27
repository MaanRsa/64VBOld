package com.maan.admin.bankRejection;

public class BankRejectionVB {
	
	private String rejectionid;
	private String rejectiontypedesc;
	private String rejectiontypeid;
	private String status;
	private int bankID;
	
	
	public int getBankID() {
		return bankID;
	}
	public void setBankID(int bankID) {
		this.bankID = bankID;
	}
	public String getRejectionid() {
		return rejectionid;
	}
	public void setRejectionid(String rejectionid) {
		this.rejectionid = rejectionid;
	}
	public String getRejectiontypedesc() {
		return rejectiontypedesc;
	}
	public void setRejectiontypedesc(String rejectiontypedesc) {
		this.rejectiontypedesc = rejectiontypedesc.toUpperCase();
	}
	public String getRejectiontypeid() {
		return rejectiontypeid;
	}
	public void setRejectiontypeid(String rejectiontypeid) {
		this.rejectiontypeid = rejectiontypeid.toUpperCase();
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
