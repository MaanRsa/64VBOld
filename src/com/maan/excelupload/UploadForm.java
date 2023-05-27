package com.maan.excelupload;

import org.apache.struts.upload.FormFile;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;


import com.maan.common.MotorBaseForm;

public class UploadForm extends MotorBaseForm{
	
	
	private static final long serialVersionUID = 1L;
	private String method;
	private FormFile uploadFile;
	private String uploadType;
	private String mode;
	private int batchid;
	private String bankName;
	private String isDirect = "";
	private String realizeStatus;
	
	private String tranId;
	
	public String getRealizeStatus() {
		return realizeStatus;
	}

	public void setRealizeStatus(String realizeStatus) {
		this.realizeStatus = realizeStatus;
	}

	public String getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(String isDirect) {
		this.isDirect = isDirect;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getBatchid() {
		return batchid;
	}

	public void setBatchid(final int batchid) {
		this.batchid = batchid;
	}

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(final ActionMapping mapping,
			final HttpServletRequest request) {
		return null;
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(final ActionMapping mapping,final HttpServletRequest request) {
		}


	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(final FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(final String uploadType) {
		this.uploadType = uploadType;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(final String method) {
		this.method = method;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(final String mode) {
		this.mode = mode;
	}

	public String getTranId() {
		return tranId;
	}

	public void setTranId(String tranId) {
		this.tranId = tranId;
	}
	
	
	
}
