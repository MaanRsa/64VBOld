package com.maan.admin.TmasValidation;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.maan.common.LogManager;



public class TmasValidationBean 
{
    private String updateTmasValidationId;
	private String mfrid; 
	private String typeid;
	private String dbColumnName;
	private String excelHeaderName;
	private String validationStatus;
	private String mandatoryStatus;
	private String fieldLength;
	private String referenceStatus;
	private String referenceTable;
	private String referenceColumn;
	private String referenceCondition;
	private String checkValue;
	private String checkValueCond;
	private String xmlTagName;
	private String xgenColumn;
	private String dataType;
	private String dataFormat;
	private String active;
	private String method;
	private String validId;
	private String mode;
	private String pagenavination;
	
	public String getPagenavination() {
		return pagenavination;
	}
	public void setPagenavination(String pagenavination) {
		this.pagenavination = pagenavination;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getValidId() {
		return validId;
	}
	public void setValidId(String validId) {
		this.validId = validId;
	}
	public String getCheckValue() {
		return checkValue;
	}
	public String getCheckValueCond() {
		return checkValueCond;
	}
	public String getDataFormat() {
		return dataFormat;
	}
	public String getDataType() {
		return dataType;
	}
	public String getDbColumnName() {
		return dbColumnName;
	}
	public String getExcelHeaderName() {
		return excelHeaderName;
	}
	public String getFieldLength() {
		return fieldLength;
	}
	public String getMandatoryStatus() {
		return mandatoryStatus;
	}
	public String getMethod() {
		return method;
	}
	public String getMfrid() {
		return mfrid;
	}
	public String getReferenceColumn() {
		return referenceColumn;
	}
	public String getReferenceCondition() {
		return referenceCondition;
	}
	public String getReferenceStatus() {
		return referenceStatus;
	}
	public String getReferenceTable() {
		return referenceTable;
	}
	public String getTypeid() {
		return typeid;
	}
	public String getValidationStatus() {
		return validationStatus;
	}
	public String getXgenColumn() {
		return xgenColumn;
	}
	public String getXmlTagName() {
		return xmlTagName;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	public void setCheckValueCond(String checkValueCond) {
		this.checkValueCond = checkValueCond;
	}
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public void setDbColumnName(String dbColumnName) {
		this.dbColumnName = dbColumnName;
	}
	public void setExcelHeaderName(String excelHeaderName) {
		this.excelHeaderName = excelHeaderName;
	}
	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}
	public void setMandatoryStatus(String mandatoryStatus) {
		this.mandatoryStatus = mandatoryStatus;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public void setMfrid(String mfrid) {
		this.mfrid = mfrid;
	}
	public void setReferenceColumn(String referenceColumn) {
		this.referenceColumn = referenceColumn;
	}
	public void setReferenceCondition(String referenceCondition) {
		this.referenceCondition = referenceCondition;
	}
	public void setReferenceStatus(String referenceStatus) {
		this.referenceStatus = referenceStatus;
	}
	public void setReferenceTable(String referenceTable) {
		this.referenceTable = referenceTable;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public void setValidationStatus(String validationStatus) {
		this.validationStatus = validationStatus;
	}
	public void setXgenColumn(String xgenColumn) {
		this.xgenColumn = xgenColumn;
	}
	public void setXmlTagName(String xmlTagName) {
		this.xmlTagName = xmlTagName;
	}
	public void reset(final ActionMapping mapping,final HttpServletRequest request) {
		LogManager.push("this is reset methods");
		LogManager.logExit();
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getUpdateTmasValidationId() {
		return updateTmasValidationId;
	}
	public void setUpdateTmasValidationId(String updateTmasValidationId) {
		this.updateTmasValidationId = updateTmasValidationId;
	}
	 
}
