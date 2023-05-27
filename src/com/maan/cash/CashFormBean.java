
package com.maan.cash;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class CashFormBean extends ActionForm {

	private String bankName;
	private String bankId="select";
	private String method;
	private String transactionDate;
	private String partToShow;
	private String slno;
	private String bankno;
	private String bankAmount;
	private String bankLocation;
	private int sumAmount;
	private int maxamount;
	private int minamount;
	private String[] branchCode;
	private String branchName;
	private List branchList;
	private String operation;
	private String banknoedit;
	private String search;
	private String branchCodes;
	
	
	public String getBranchCodes() {
		return branchCodes;
	}

	public void setBranchCodes(String branchCodes) {
		this.branchCodes = branchCodes;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getBanknoedit() {
		return banknoedit;
	}

	public void setBanknoedit(String banknoedit) {
		this.banknoedit = banknoedit;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String[] getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String[] branchCode) {
		this.branchCode = branchCode;
	}

	public List getBranchList() {
		return branchList;
	}

	public void setBranchList(List branchList) {
		this.branchList = branchList;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public int getMaxamount() {
		return maxamount;
	}

	public void setMaxamount(int maxamount) {
		this.maxamount = maxamount;
	}

	public int getMinamount() {
		return minamount;
	}

	public void setMinamount(int minamount) {
		this.minamount = minamount;
	}

	public int getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(int sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getBankLocation() {
		return bankLocation;
	}

	public void setBankLocation(String bankLocation) {
		this.bankLocation = bankLocation;
	}

	public String getBankAmount() {
		return bankAmount;
	}

	public void setBankAmount(String bankAmount) {
		this.bankAmount = bankAmount;
	}

	public String getBankno() {
		return bankno;
	}

	public void setBankno(String bankno) {
		this.bankno = bankno;
	}

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPartToShow() {
		return partToShow;
	}

	public void setPartToShow(String partToShow) {
		this.partToShow = partToShow;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	
	}

	
	
}