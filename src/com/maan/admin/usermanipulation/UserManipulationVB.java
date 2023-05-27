package com.maan.admin.usermanipulation;

public class UserManipulationVB {
	private int id;

	private String password;

	private String userName;

	private String loginID;

	private String userType;

	private String createdBy;

	private String status;
	
	private String menuIDStr;
	
    private String insCompanyId;
    
    private String stateCode;
    
    private String stateCodes;
    
    private String stateName;
    
    private String districtCode;
    
    private String districtCodes;

	private String districtName;
	
	
	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(final String createdBy) {
		this.createdBy = createdBy;
	}

	public int getId() {
		return id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(final String loginID) {
		this.loginID = loginID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(final String userType) {
		this.userType = userType;
	}

	public String getMenuIDStr() {
		return menuIDStr;
	}

	public void setMenuIDStr(final String menuIDStr) {
		this.menuIDStr = menuIDStr;
	}

	public String getDistrictCodes() {
		return districtCodes;
	}

	public void setDistrictCodes(String districtCodes) {
		this.districtCodes = districtCodes;
	}

	public String getStateCodes() {
		return stateCodes;
	}

	public void setStateCodes(String stateCodes) {
		this.stateCodes = stateCodes;
	}

	public String getInsCompanyId() {
		return insCompanyId;
	}

	public void setInsCompanyId(String insCompanyId) {
		this.insCompanyId = insCompanyId;
	}

}
