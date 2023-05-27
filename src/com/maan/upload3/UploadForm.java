package com.maan.upload3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.maan.common.base.CommonBaseForm;

public class UploadForm extends CommonBaseForm
{
	private static final long serialVersionUID = 1L;
	private String back;
	private String method ;
	private FormFile uploadFile;
	private String uploaderrdatas;
	private String srNo;
	private String errdatabean;
	private String transactionDate;
	private String transactionId;
	private String requestfrom;
	private String reqfrom;
	private String condncheck;
	private String results;
	private String transactionid;
	
	
	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public String getCondncheck() {
		return condncheck;
	}

	public void setCondncheck(String condncheck) {
		this.condncheck = condncheck;
	}

	public String getReqfrom() {
		return reqfrom;
	}

	public void setReqfrom(String reqfrom) {
		this.reqfrom = reqfrom;
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

	public void reset(final ActionMapping mapping,final HttpServletRequest request){
		
	}

	public ActionErrors validate(final ActionMapping mapping,
			final HttpServletRequest request) {
		return null;
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

	public String getSrNo() {
		return srNo;
	}

	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}

	

	public String getBack() {
		return back;
	}

	public void setBack(String back) {
		this.back = back;
	}



	public String getRequestfrom() {
		return requestfrom;
	}

	public void setRequestfrom(String requestfrom) {
		this.requestfrom = requestfrom;
	}

	
	public String getResults() {
		return results;
	}

	public void setResults(String results) {
		this.results = results;
	}

	public String getUploaderrdatas() {
		return uploaderrdatas;
	}

	public void setUploaderrdatas(String uploaderrdatas) {
		this.uploaderrdatas = uploaderrdatas;
	}

	public String getErrdatabean() {
		return errdatabean;
	}

	public void setErrdatabean(String errdatabean) {
		this.errdatabean = errdatabean;
	}

	
}