package com.maan.admin.usermanipulation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maan.admin.menu.AdminMenuCB;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;


public class UserManipulationCB extends CommonBaseCB {

	public boolean insertUser(final UserManipulationVB creationVB)
			throws CommonBaseException {

		boolean result;
		LogManager.push("insertUser() method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.insertUser(creationVB);
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public Map getUserTypeList(final String userType)
	{
		final Map uType=new HashMap();
		uType.put("admin", "Admin");
		//uType.put("approver1", "Approver1 User");
		//uType.put("approver2", "Approver2 User");
		//uType.put("investigateuser", "Investigate User");
		uType.put("uploaduser", "Upload User");
		//uType.put("user", "User");
		return uType;
	}
	
	public boolean insertApproverUser(final UserManipulationVB creationVB)
		throws CommonBaseException {
	
	boolean result;
	LogManager.push("insertApproverUser() method ");
	LogManager.logEnter();
		
	final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory.getDAO(UserManipulationDAO.class.getName());
	result = menuDAO.insertApproverUser(creationVB);
	LogManager.logExit();
	LogManager.popRemove(); // Should be the last statement
	return result;
	}
	
	/*public boolean removeUser(final UserManipulationVB creationVB)
	throws CommonBaseException {
	
	boolean result;
	LogManager.push("removeUser() method ");
	LogManager.logEnter();
	
	final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
			.getDAO(UserManipulationDAO.class.getName());
	result = menuDAO.removeUser(creationVB);
	LogManager.logExit();
	LogManager.popRemove(); // Should be the last statement
	return result;
	}*/
	
	public boolean updateUser(final UserManipulationVB creationVB)
			throws CommonBaseException {

		boolean result;
		LogManager.push("updateUser() method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.updateUser(creationVB);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public boolean allocateMenuToUser(final UserManipulationVB creationVB)
			throws CommonBaseException {

		boolean result;
		LogManager.push("allocateMenuToUser() method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.allocateMenuToUser(creationVB);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	public boolean allocateStateToUser(final UserManipulationVB creationVB)
			throws CommonBaseException {

		boolean result;
		LogManager.push("allocateStateToUser method ");
		LogManager.logEnter();

		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.allocateStateToUser(creationVB);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	public boolean allocateDistrictToUser(final UserManipulationVB creationVB)
			throws CommonBaseException {

		boolean result;
		LogManager.push("allocateDistrictToUser method ");
		LogManager.logEnter();

		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.allocateDistrictToUser(creationVB);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}


	public boolean isLoginOccur(final String loginID) throws CommonBaseException {

		boolean result;
		LogManager.push("isLoginOccur() method ");
		LogManager.logEnter();

		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.isLoginOccur(loginID);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public List getLoginInfo(final int idval) throws CommonBaseException {

		List result;
		LogManager.push("getLoginInfo() method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.getLoginInfo(idval);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public List getUserList(final String searchFor, final String searchOn,final String userType)
			throws CommonBaseException {

		List result;
		LogManager.push("getUserList() method ");
		LogManager.logEnter();
		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.getUserList(searchFor, searchOn,userType);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public boolean isOldPwdWright(final String loginID, final String pwd)
			throws CommonBaseException {

		boolean result;
		LogManager.push("isOldPwdWright() method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.isOldPwdWright(loginID, pwd);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public boolean changePwd(final String loginID, final String pwd,final String loginName,final String loginSts)
			throws CommonBaseException {

		boolean result;
		LogManager.push("isOldPwdWright() method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.changePwd(loginID, pwd,loginName,loginSts);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public Map getLoginIDMap() throws CommonBaseException {

		Map result;
		LogManager.push("getLoginIDMap() method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.getLoginIDMap();

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public String getLoginPassDate(final String loginid) throws CommonBaseException {

		String result;
		LogManager.push("getLoginPassDate() method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.getLoginPassDate(loginid);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public List getMenuList(final String userType) throws CommonBaseException {

		List result;
		LogManager.push("getMenuMap() method ");
		LogManager.logEnter();

		final AdminMenuCB menuCB = new AdminMenuCB();
		result = menuCB.getAllMenu(userType, "0");

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public List getStateList() throws CommonBaseException {

		List result;
		LogManager.push("getStateList() method ");
		LogManager.logEnter();

		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.getStateList();

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	public List getDistrictList(final UserManipulationVB creationVB) throws CommonBaseException {

		List result;
		LogManager.push("getDistrictList() method ");
		LogManager.logEnter();

		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.getDistrictList(creationVB);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public UserManipulationVB getSelectedMenu(final String loginID) throws CommonBaseException {

		UserManipulationVB result;
		LogManager.push("getMenuMap() method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.getSelectedMenu(loginID);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	public UserManipulationVB getSelectedState(final String loginID) throws CommonBaseException {

		UserManipulationVB result;
		LogManager.push("getSelectedState method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.getSelectedState(loginID);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	public UserManipulationVB getSelectedDistrict(final String loginID) throws CommonBaseException {

		UserManipulationVB result;
		LogManager.push("getSelectedState method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory
				.getDAO(UserManipulationDAO.class.getName());
		result = menuDAO.getSelectedDistrict(loginID);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	
	public Map getInsuranceCompany(final UserManipulationVB creationVB) throws CommonBaseException {

	    Map mp=null;
		LogManager.push("getMenuMap() method ");
		LogManager.logEnter();

		
		final UserManipulationDAO menuDAO = (UserManipulationDAOImpl) CommonDaoFactory.getDAO(UserManipulationDAO.class.getName());
		mp = menuDAO.getInsuranceCompany(creationVB);
        
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return mp;
	}

    
	
}
