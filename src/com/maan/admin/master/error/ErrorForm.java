package com.maan.admin.master.error;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseForm;

public class ErrorForm extends CommonBaseForm 
{
	private static final long serialVersionUID = 1L;
	
	private String errorId;	
	private String errorCode;
	private String errorDesc;	
	private String active;
	private String mode;
	private String method;
	private String updateErrorId;
	private String searchBy;
	private String searchValue;
	
	public ActionErrors validate(final ActionMapping mapping,final HttpServletRequest request) {
		return null;
	}
	
	public void reset(final ActionMapping mapping,final HttpServletRequest request) {
		LogManager.push("this is reset methods");
		LogManager.logExit();
	}

	public String getActive() {
		return active;
	}

	public void setActive(final String active) {
		this.active = active;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(final String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(final String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(final String errorId) {
		this.errorId = errorId;
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

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(final String searchBy) {
		this.searchBy = searchBy;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(final String searchValue) {
		this.searchValue = searchValue;
	}

	public String getUpdateErrorId() {
		return updateErrorId;
	}

	public void setUpdateErrorId(final String updateErrorId) {
		this.updateErrorId = updateErrorId;
	}	
}