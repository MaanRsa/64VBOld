package com.maan.excelupload;

import java.util.List;

public class UploadVB {
	private String tempqry;
	private String mainqry;
	private String masterqry;
	private String unknowncolumn;
	private String tempTableName;
	private String policyType;
	private int typeID;
	private String masterTempTbl="";
	private String mastterTbl;
	private int batchID;
	private int manufactureID;
	private String fieldseparater;
	private String uploadoption;
	private String mfrcode;
	private String fileformat;
	private String fileformatsin;
	private boolean uploadstatus;
	private int totalRecords;
	private int uploaded;
	private int pending;
	private String uploadTransts;
	private String xgenStatus;
	private String condition;
	private String tables;
	private List queries;
	private int headerlineno;
	private String filePath;
	private String invalid ;
	private String isDirect = "";
	private String realizeStatus;
	
	
	
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
	public String getInvalid() {
		return invalid;
	}
	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getHeaderlineno() {
		return headerlineno;
	}
	public void setHeaderlineno(final int headerlineno) {
		this.headerlineno = headerlineno;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(final String condition) {
		this.condition = condition;
	}
	public String getXgenStatus() {
		return xgenStatus;
	}
	public void setXgenStatus(final String xgenStatus) {
		this.xgenStatus = xgenStatus;
	}
	public int getPending() {
		return pending;
	}
	public void setPending(final int pending) {
		this.pending = pending;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(final int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getUploaded() {
		return uploaded;
	}
	public void setUploaded(final int uploaded) {
		this.uploaded = uploaded;
	}
	public String getFieldseparater() {
		return fieldseparater;
	}
	public void setField_separater(final String fieldseparater) {
		this.fieldseparater = fieldseparater;
	}
	public String getFileformat() {
		return fileformat;
	}
	public void setFileformat(final String fileformat) {
		this.fileformat = fileformat;
	}
	public String getMfrcode() {
		return mfrcode;
	}
	public void setMfrcode(final String mfrcode) {
		this.mfrcode = mfrcode;
	}
	public String getUploadoption() {
		return uploadoption;
	}
	public void setUpload_option(final String uploadoption) {
		this.uploadoption = uploadoption;
	}
	public int getManufactureID() {
		return manufactureID;
	}
	public void setManufactureID(final int manufactureID) {
		this.manufactureID = manufactureID;
	}
	public String getMainqry() {
		return mainqry;
	}
	public void setMainqry(final String mainqry) {
		this.mainqry = mainqry;
	}
	public String getTempqry() {
		return tempqry;
	}
	public void setTempqry(final String tempqry) {
		this.tempqry = tempqry;
	}
	public String getTempTableName() {
		return tempTableName;
	}
	public void setTempTable_Name(final String tempTableName) {
		this.tempTableName = tempTableName;
	}
	public String getUnknowncolumn() {
		return unknowncolumn;
	}
	public void setUnknowncolumn(final String unknowncolumn) {
		this.unknowncolumn = unknowncolumn;
	}
	public int getBatchID() {
		return batchID;
	}
	public void setBatchID(final int batchID) {
		this.batchID = batchID;
	}
	public String getMasterTempTbl() {
		return masterTempTbl;
	}
	public void setMasterTempTbl(final String masterTempTbl) {
		this.masterTempTbl = masterTempTbl;
	}
	public String getMastterTbl() {
		return mastterTbl;
	}
	public void setMastterTbl(final String mastterTbl) {
		this.mastterTbl = mastterTbl;
	}
	public String getPolicyType() {
		return policyType;
	}
	public void setPolicyType(final String policyType) {
		this.policyType = policyType;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(final int typeID) {
		this.typeID = typeID;
	}
	public String getMasterqry() {
		return masterqry;
	}
	public void setMasterqry(final String masterqry) {
		this.masterqry = masterqry;
	}
	public String getFileformatsin() {
		return fileformatsin;
	}
	public void setFileformatsin(final String fileformatsin) {
		this.fileformatsin = fileformatsin;
	}
	public boolean isUploadstatus() {
		return uploadstatus;
	}
	public void setUploadstatus(final boolean uploadstatus) {
		this.uploadstatus = uploadstatus;
	}
	public String getUploadTransts() {
		return uploadTransts;
	}
	public void setUploadTransts(final String uploadTransts) {
		this.uploadTransts = uploadTransts;
	}
	public String getTables() {
		return tables;
	}
	public void setTables(final String tables) {
		this.tables = tables;
	}
	public List getQueries() {
		return queries;
	}
	public void setQueries(final List queries) {
		this.queries = queries;
	}
	

}
