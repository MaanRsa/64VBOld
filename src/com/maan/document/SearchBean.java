package com.maan.document;

public class SearchBean {
 
	private String policyNumber="";
	private String validateStatus="";
	private String endorsementNumber="";
	private String pendingReason="";
	private String batchID="";
	private String[] valueArray;
	
	public String getBatchID() {
		return batchID;
	}
	public void setBatchID(String batchID) {
		this.batchID = batchID;
	}
	public String getPendingReason() {
		return pendingReason;
	}
	public void setPendingReason(String pendingReason) {
		this.pendingReason = pendingReason;
	}
	public String getEndorsementNumber() {
		return endorsementNumber;
	}
	public void setEndorsementNumber(String endorsementNumber) {
		this.endorsementNumber = endorsementNumber;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getValidateStatus() {
		return validateStatus;
	}
	public void setValidateStatus(String validateStatus) {
		this.validateStatus = validateStatus;
	}
	public String[] getValueArray() {
		return valueArray;
	}
	public void setValueArray(String[] valueArray) {
		this.valueArray = valueArray;
	}
	
	
	
	
	
	
	
}
