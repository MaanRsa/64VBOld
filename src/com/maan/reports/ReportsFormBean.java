package com.maan.reports;

import org.apache.struts.action.ActionForm;

public class ReportsFormBean extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String citiTotal;
	private String axisTotal;
	private String scbTotal;
	private String hdfcTotal;
	private String hsbcTotal;
	
	private String citiMatched;
	private String citiNonMatched;
	private String citiUnmatchedAmount;
	
	private String axisMatched;
	private String axisNonMatched;
	private String axisUnmatchedAmount;
	
	private String scbMatched;
	private String scbNonMatched;
	private String scbUnmatchedAmount;
	
	private String hdfcMatched;
	private String hdfcNonMatched;
	private String hdfcUnmatchedAmount;
	
	private String hsbcMatched;
	private String hsbcNonMatched;
	private String hsbcUnmatchedAmount;
	
	private String paymentType;
	private String method;
	private String display;
	private String paymentSource;
	private String requestFrom;
	
	private String receiptTotal;
	private String receiptNonMatched;
	private String receiptMatched;
	private String receiptUnmatchedAmount;
	
	private String citiReversal;
	private String axisReversal;
	private String hdfcReversal;
	private String hsbcReversal;
	private String scbReversal;
	
	public String getCitiReversal() {
		return citiReversal;
	}
	public void setCitiReversal(String citiReversal) {
		this.citiReversal = citiReversal;
	}
	public String getAxisReversal() {
		return axisReversal;
	}
	public void setAxisReversal(String axisReversal) {
		this.axisReversal = axisReversal;
	}
	public String getHdfcReversal() {
		return hdfcReversal;
	}
	public void setHdfcReversal(String hdfcReversal) {
		this.hdfcReversal = hdfcReversal;
	}
	public String getHsbcReversal() {
		return hsbcReversal;
	}
	public void setHsbcReversal(String hsbcReversal) {
		this.hsbcReversal = hsbcReversal;
	}
	public String getScbReversal() {
		return scbReversal;
	}
	public void setScbReversal(String scbReversal) {
		this.scbReversal = scbReversal;
	}
	public String getReceiptTotal() {
		return receiptTotal;
	}
	public void setReceiptTotal(String receiptTotal) {
		this.receiptTotal = receiptTotal;
	}
	public String getReceiptNonMatched() {
		return receiptNonMatched;
	}
	public void setReceiptNonMatched(String receiptNonMatched) {
		this.receiptNonMatched = receiptNonMatched;
	}
	public String getReceiptMatched() {
		return receiptMatched;
	}
	public void setReceiptMatched(String receiptMatched) {
		this.receiptMatched = receiptMatched;
	}
	public String getReceiptUnmatchedAmount() {
		return receiptUnmatchedAmount;
	}
	public void setReceiptUnmatchedAmount(String receiptUnmatchedAmount) {
		this.receiptUnmatchedAmount = receiptUnmatchedAmount;
	}
	public String getRequestFrom() {
		return requestFrom;
	}
	public void setRequestFrom(String requestFrom) {
		this.requestFrom = requestFrom;
	}
	public String getPaymentSource() {
		return paymentSource;
	}
	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	private String startDate;
	private String endDate;
	
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getAxisMatched() {
		return axisMatched;
	}
	public void setAxisMatched(String axisMatched) {
		this.axisMatched = axisMatched;
	}
	public String getAxisNonMatched() {
		return axisNonMatched;
	}
	public void setAxisNonMatched(String axisNonMatched) {
		this.axisNonMatched = axisNonMatched;
	}
	public String getAxisUnmatchedAmount() {
		return axisUnmatchedAmount;
	}
	public void setAxisUnmatchedAmount(String axisUnmatchedAmount) {
		this.axisUnmatchedAmount = axisUnmatchedAmount;
	}
	public String getScbMatched() {
		return scbMatched;
	}
	public void setScbMatched(String scbMatched) {
		this.scbMatched = scbMatched;
	}
	public String getScbNonMatched() {
		return scbNonMatched;
	}
	public void setScbNonMatched(String scbNonMatched) {
		this.scbNonMatched = scbNonMatched;
	}
	public String getScbUnmatchedAmount() {
		return scbUnmatchedAmount;
	}
	public void setScbUnmatchedAmount(String scbUnmatchedAmount) {
		this.scbUnmatchedAmount = scbUnmatchedAmount;
	}
	public String getHdfcMatched() {
		return hdfcMatched;
	}
	public void setHdfcMatched(String hdfcMatched) {
		this.hdfcMatched = hdfcMatched;
	}
	public String getHdfcNonMatched() {
		return hdfcNonMatched;
	}
	public void setHdfcNonMatched(String hdfcNonMatched) {
		this.hdfcNonMatched = hdfcNonMatched;
	}
	public String getHdfcUnmatchedAmount() {
		return hdfcUnmatchedAmount;
	}
	public void setHdfcUnmatchedAmount(String hdfcUnmatchedAmount) {
		this.hdfcUnmatchedAmount = hdfcUnmatchedAmount;
	}
	public String getHsbcMatched() {
		return hsbcMatched;
	}
	public void setHsbcMatched(String hsbcMatched) {
		this.hsbcMatched = hsbcMatched;
	}
	public String getHsbcNonMatched() {
		return hsbcNonMatched;
	}
	public void setHsbcNonMatched(String hsbcNonMatched) {
		this.hsbcNonMatched = hsbcNonMatched;
	}
	public String getHsbcUnmatchedAmount() {
		return hsbcUnmatchedAmount;
	}
	public void setHsbcUnmatchedAmount(String hsbcUnmatchedAmount) {
		this.hsbcUnmatchedAmount = hsbcUnmatchedAmount;
	}
	public String getCitiUnmatchedAmount() {
		return citiUnmatchedAmount;
	}
	public void setCitiUnmatchedAmount(String citiUnmatchedAmount) {
		this.citiUnmatchedAmount = citiUnmatchedAmount;
	}
	public String getCitiTotal() {
		return citiTotal;
	}
	public void setCitiTotal(String citiTotal) {
		this.citiTotal = citiTotal;
	}
	public String getAxisTotal() {
		return axisTotal;
	}
	public void setAxisTotal(String axisTotal) {
		this.axisTotal = axisTotal;
	}
	public String getScbTotal() {
		return scbTotal;
	}
	public void setScbTotal(String scbTotal) {
		this.scbTotal = scbTotal;
	}
	public String getHdfcTotal() {
		return hdfcTotal;
	}
	public void setHdfcTotal(String hdfcTotal) {
		this.hdfcTotal = hdfcTotal;
	}
	public String getHsbcTotal() {
		return hsbcTotal;
	}
	public void setHsbcTotal(String hsbcTotal) {
		this.hsbcTotal = hsbcTotal;
	}
	public String getCitiMatched() {
		return citiMatched;
	}
	public void setCitiMatched(String citiMatched) {
		this.citiMatched = citiMatched;
	}
	public String getCitiNonMatched() {
		return citiNonMatched;
	}
	public void setCitiNonMatched(String citiNonMatched) {
		this.citiNonMatched = citiNonMatched;
	}
	
}
