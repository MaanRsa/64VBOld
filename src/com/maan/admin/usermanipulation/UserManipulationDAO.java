package com.maan.admin.usermanipulation;

import java.util.List;
import java.util.Map;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface UserManipulationDAO extends CommonBaseDAO {

	 boolean insertUser(final UserManipulationVB creationVB) throws CommonBaseException;

	 boolean insertApproverUser(final UserManipulationVB creationVB) throws CommonBaseException;

	 //boolean removeUser(final UserManipulationVB creationVB) throws CommonBaseException;

	 boolean updateUser(final UserManipulationVB creationVB) throws CommonBaseException;

	 List getLoginInfo(final int identity) throws CommonBaseException;

	 Map getLoginIDMap() throws CommonBaseException;
	
	 String getLoginPassDate(final String loginid) throws CommonBaseException;

	 boolean isLoginOccur(final String loginID) throws CommonBaseException;

	 List getUserList(final String searchFor, final String searchOn,final String userType) throws CommonBaseException;
	 
	 List getStateList() throws CommonBaseException;
	 
	 List getDistrictList(final UserManipulationVB creationVB) throws CommonBaseException;


	 boolean isOldPwdWright(final String loginID, final String pwd) throws CommonBaseException;

	 boolean changePwd(final String loginID, final String pwd,final String loginName,final String loginSts) throws CommonBaseException;

	 Map getMenuMap(final String userType) throws CommonBaseException;

	 UserManipulationVB getSelectedMenu(final String loginID) throws CommonBaseException;
	 
	 UserManipulationVB getSelectedState(final String loginID) throws CommonBaseException;
	 
	 UserManipulationVB getSelectedDistrict(final String loginID) throws CommonBaseException;

	 boolean allocateMenuToUser(final UserManipulationVB creationVB) throws CommonBaseException;
	 
	 boolean allocateStateToUser(final UserManipulationVB creationVB) throws CommonBaseException;
	 
	 boolean allocateDistrictToUser(final UserManipulationVB creationVB) throws CommonBaseException;
	 
	 Map getInsuranceCompany(final UserManipulationVB creationVB)throws CommonBaseException;
	 
	

}
