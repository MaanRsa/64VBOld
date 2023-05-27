package com.maan.admin.menu;

import java.util.List;
import java.util.Map;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;

public class AdminMenuCB extends CommonBaseCB {

	public boolean insertMenu(final AdminMenuVB menuVB)
			throws CommonBaseException {

		boolean result;
		LogManager.push("insertMenu() method ");
		LogManager.logEnter();

		
		final AdminMenuDAO menuDAO = (AdminMenuDAOImpl) CommonDaoFactory.getDAO(AdminMenuDAO.class
				.getName());
		result = menuDAO.insertMenu(menuVB);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public boolean updateMenu(final AdminMenuVB menuVB)
			throws CommonBaseException {

		boolean result;
		LogManager.push("updateMenu() method ");
		LogManager.logEnter();

		
		final AdminMenuDAO menuDAO = (AdminMenuDAOImpl) CommonDaoFactory.getDAO(AdminMenuDAO.class
				.getName());
		result = menuDAO.updateMenu(menuVB);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public List getMenuInfo(final int idval) throws CommonBaseException {

		List result;
		LogManager.push("getMenuInfo() method ");
		LogManager.logEnter();

		
		final AdminMenuDAO menuDAO = (AdminMenuDAOImpl) CommonDaoFactory.getDAO(AdminMenuDAO.class
				.getName());
		result = menuDAO.getMenuInfo(idval);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public List getAllMenu(final String type, final String idval)
			throws CommonBaseException {

		List result;
		LogManager.push("getAllMenu() method ");
		LogManager.logEnter();

		
		final AdminMenuDAO menuDAO = (AdminMenuDAOImpl) CommonDaoFactory.getDAO(AdminMenuDAO.class
				.getName());
		result = menuDAO.getAllMenu(type, idval);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public Map getMenuMap(final String type, final boolean status)
			throws CommonBaseException {

		Map result;
		LogManager.push("getMenuMap() method ");
		LogManager.logEnter();

		
		final AdminMenuDAO menuDAO = (AdminMenuDAOImpl) CommonDaoFactory.getDAO(AdminMenuDAO.class
				.getName());
		result = menuDAO.getMenuMap(type, status);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
}
