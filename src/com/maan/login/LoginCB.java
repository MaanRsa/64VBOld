package com.maan.login;

import java.util.List;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;

public class LoginCB extends CommonBaseCB {

	public List makeAuthendication(final LoginVB loginVB)
			throws CommonBaseException {

		List result;
		LogManager.push("Login() method ");
		LogManager.logEnter();

		
		final LoginDAO loginDAO = (LoginDAOImpl) CommonDaoFactory.getDAO(LoginDAO.class.getName());
		result = loginDAO.makeAuthendication(loginVB);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public boolean insertSessionInfo(final LoginVB loginVB,
			final String sessionId) throws CommonBaseException {

		boolean result;
		LogManager.push("insertSessionInfo() method ");
		LogManager.logEnter();

		
		final LoginDAO loginDAO = (LoginDAOImpl) CommonDaoFactory.getDAO(LoginDAO.class.getName());
		result = loginDAO.insertSessionInfo(loginVB, sessionId);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	public String updateSessionInfo(final LoginVB loginVB,
			final String sessionId) throws CommonBaseException {

		String result;
		LogManager.push("updateSessionInfo() method ");
		LogManager.logEnter();

		
		final LoginDAO loginDAO = (LoginDAOImpl) CommonDaoFactory.getDAO(LoginDAO.class.getName());
		result = loginDAO.updateSessionInfo(loginVB, sessionId);

		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	
	public String getInvestigationUserCode(final LoginForm loginForm) throws CommonBaseException
	{
		String result="";
		LogManager.push("getInvestigationUserCode() method ");
		LogManager.logEnter();
		
		final LoginDAO loginDAO = (LoginDAOImpl) CommonDaoFactory.getDAO(LoginDAO.class.getName());
		result = loginDAO.getInvestigationUserCode(loginForm);
		
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
}
