package com.maan.reversal;
import org.apache.struts.action.ActionForm;

public class ReversalFormBean extends ActionForm {

	private String bankName;
	private String fromDate;
	private String toDate;
	private String partToShow;
	private String method;
	private String depositdate;

	public String getDepositdate() {
		return depositdate;
	}
	public void setDepositdate(String depositdate) {
		this.depositdate = depositdate;
	}

	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPartToShow() {
		return partToShow;
	}
	public void setPartToShow(String partToShow) {
		this.partToShow = partToShow;
	}

	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}

}