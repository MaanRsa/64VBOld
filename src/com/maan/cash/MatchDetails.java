
package com.maan.cash;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class MatchDetails  {

	private String depositno;
	private String noofmatched;
	private String matchedReceiptNos;
	private String matchedReceiptdates;
	private String bankName;
	private String bankNo;
	
	
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getDepositno() {
		return depositno;
	}
	public void setDepositno(String depositno) {
		this.depositno = depositno;
	}
	public String getMatchedReceiptdates() {
		return matchedReceiptdates;
	}
	public void setMatchedReceiptdates(String matchedReceiptdates) {
		this.matchedReceiptdates = matchedReceiptdates;
	}
	public String getMatchedReceiptNos() {
		return matchedReceiptNos;
	}
	public void setMatchedReceiptNos(String matchedReceiptNos) {
		this.matchedReceiptNos = matchedReceiptNos;
	}
	public String getNoofmatched() {
		return noofmatched;
	}
	public void setNoofmatched(String noofmatched) {
		this.noofmatched = noofmatched;
	}
	
	
	
}