package com.maan.upload2;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.maan.common.base.CommonBaseVB;

public class UploadVB extends CommonBaseVB {

	
	private static final long serialVersionUID = 1L;
	private String method ;
	private FormFile uploadFile;
	private String srNo;
	private String duplicateTotal;
	private String invalidTotal;
	private String otherTotal;
	private String registerTotal;
	private String transactedTotal;
	private String insCompanyId;
	private String errorrecords;
	private String transactionDate;
	private String transactionId;
	private String invoiceno;
	private String errordata;
	
	private String detailsmasterid;
	private int sno;
	
	
	private boolean checkedstatus=false;
	
	public boolean isCheckedstatus() {
		return checkedstatus;
	}

	public void setCheckedstatus(boolean checkedstatus) {
		this.checkedstatus = checkedstatus;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	

	public String getOtherTotal() {
		return otherTotal;
	}

	public void setOtherTotal(String otherTotal) {
		this.otherTotal = otherTotal;
	}

	public String getRegisterTotal() {
		return registerTotal;
	}

	public void setRegisterTotal(String registerTotal) {
		this.registerTotal = registerTotal;
	}

	public String getSrNo() {
		return srNo;
	}

	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	

	public String getTransactedTotal() {
		return transactedTotal;
	}

	public void setTransactedTotal(String transactedTotal) {
		this.transactedTotal = transactedTotal;
	}

	

	public String getDuplicateTotal() {
		return duplicateTotal;
	}

	public void setDuplicateTotal(String duplicateTotal) {
		this.duplicateTotal = duplicateTotal;
	}

	public String getInvalidTotal() {
		return invalidTotal;
	}

	public void setInvalidTotal(String invalidTotal) {
		this.invalidTotal = invalidTotal;
	}

	public String getInsCompanyId() {
		return insCompanyId;
	}

	public void setInsCompanyId(String insCompanyId) {
		this.insCompanyId = insCompanyId;
	}

	

	public String getInvoiceno() {
		return invoiceno;
	}

	public void setInvoiceno(String invoiceno) {
		this.invoiceno = invoiceno;
	}

	

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	
	public String getDetailsmasterid() {
		return detailsmasterid;
	}

	public void setDetailsmasterid(String detailsmasterid) {
		this.detailsmasterid = detailsmasterid;
	}

	public String getErrorrecords() {
		return errorrecords;
	}

	public void setErrorrecords(String errorrecords) {
		this.errorrecords = errorrecords;
	}

	public String getErrordata() {
		return errordata;
	}

	public void setErrordata(String errordata) {
		this.errordata = errordata;
	}

	
	
}
