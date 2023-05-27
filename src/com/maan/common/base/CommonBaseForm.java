package com.maan.common.base;

import java.io.Serializable;

import org.apache.struts.validator.ValidatorForm;

public class CommonBaseForm extends ValidatorForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String from;
	
	private String showPart;
	
	private String method;
	
	public String getShowPart() {
		return showPart;
	}
	public void setShowPart(String showPart) {
		this.showPart = showPart;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
}
