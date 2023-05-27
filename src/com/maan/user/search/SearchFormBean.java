
package com.maan.user.search;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


public class SearchFormBean extends ActionForm {

	private String bankName;
	private String bankId="select";
	private String chequeNo;
	private String chequeAmount;
	private HashMap amount = new HashMap();
	private HashMap chequeNos = new HashMap();
	private String searchIn="Bank";
	private String searchFor="exact";
	private String fromJsp;
	private String method;
	private String realised="yes";
	private List searchResult;
	private String checkStatus;
	private String actualChequeNo;
	private String actualChequeAmount;
	private String checks;
	private String currentcheckeditems;
	private String condncheck;
	private String reqfrom;
	private String[][] checkeddatas;
	private String path;
	private String search;
	private String transactionNo;
	private String bankNo1;
	private String bankNo2;
	private String receiptNo;
	private String policyNo;
	private String searchOption;
	private String searchValue;
	private String pagination;
	
	

	public String getPagination() {
		return pagination;
	}

	public void setPagination(String pagination) {
		this.pagination = pagination;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}


	private String partToShow;
	
	public String getFromJsp() {
		return fromJsp;
	}

	public void setFromJsp(String fromJsp) {
		this.fromJsp = fromJsp;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}
	

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		return null;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	
	}

	public List getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(List searchResult) {
		this.searchResult = searchResult;
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

	public String getSearchIn() {
		return searchIn;
	}

	public void setSearchIn(String searchIn) {
		this.searchIn = searchIn;
	}

	public String getSearchFor() {
		return searchFor;
	}

	public void setSearchFor(String searchFor) {
		this.searchFor = searchFor;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getRealised() {
		return realised;
	}

	public void setRealised(String realised) {
		this.realised = realised;
	}


	


	public String getChecks() {
		return checks;
	}

	public void setChecks(String checks) {
		this.checks = checks;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String[][] getCheckeddatas() {
		return checkeddatas;
	}

	public void setCheckeddatas(String[][] checkeddatas) {
		this.checkeddatas = checkeddatas;
	}

	public String getCondncheck() {
		return condncheck;
	}

	public void setCondncheck(String condncheck) {
		this.condncheck = condncheck;
	}

	public String getCurrentcheckeditems() {
		return currentcheckeditems;
	}

	public void setCurrentcheckeditems(String currentcheckeditems) {
		this.currentcheckeditems = currentcheckeditems;
	}

	public String getReqfrom() {
		return reqfrom;
	}

	public void setReqfrom(String reqfrom) {
		this.reqfrom = reqfrom;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public HashMap getAmount() {
		return amount;
	}

	public void setAmount(HashMap amount) {
		this.amount = amount;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public HashMap getChequeNos() {
		return chequeNos;
	}

	public void setChequeNos(HashMap chequeNos) {
		this.chequeNos = chequeNos;
	}

	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
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

	public String getBankNo1() {
		return bankNo1;
	}

	public void setBankNo1(String bankNo1) {
		this.bankNo1 = bankNo1;
	}

	public String getBankNo2() {
		return bankNo2;
	}

	public void setBankNo2(String bankNo2) {
		this.bankNo2 = bankNo2;
	}

	public String getPartToShow() {
		return partToShow;
	}

	public void setPartToShow(String partToShow) {
		this.partToShow = partToShow;
	}
	

	@Override
	public void reset(ActionMapping arg0, ServletRequest arg1) {
		bankId="select";
		chequeNo="";
	}

	
	
}