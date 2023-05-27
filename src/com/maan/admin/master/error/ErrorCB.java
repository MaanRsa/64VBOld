package com.maan.admin.master.error;

//import java.util.ArrayList;
import java.util.List;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;

public class ErrorCB extends CommonBaseCB 
{
	public String insertErrorDetailsCB(final ErrorVB errorVB) throws CommonBaseException
	{
		LogManager.push("ErrorCB insertErrorDetailsCB() method Starts");
		LogManager.logEnter();
	
		
		final ErrorDAO errorDAO = (ErrorDAOImpl) CommonDaoFactory.getDAO(ErrorDAO.class.getName());
		final String result = errorDAO.insertErrorDetails(errorVB);
	
		LogManager.logExit();
		LogManager.push("ErrorCB insertErrorDetailsCB() method Ends");
		LogManager.popRemove();
		return result;
	}
	
	public String updateErrorDetailsCB(final ErrorVB errorVB) throws CommonBaseException
	{
		LogManager.push("ErrorCB updateErrorDetailsCB() method Starts");
		LogManager.logEnter();
	
		
		final ErrorDAO errorDAO = (ErrorDAOImpl) CommonDaoFactory.getDAO(ErrorDAO.class.getName());
		final String result = errorDAO.updateErrorDetails(errorVB);
	
		LogManager.logExit();
		LogManager.push("ErrorCB updateErrorDetailsCB() method Ends");
		LogManager.popRemove();
		return result;
		
	}
	
	public List getErrorListCB() throws CommonBaseException
	{
		LogManager.push("ErrorCB getErrorListCB() method Starts");
		LogManager.logEnter();
	
		
		final ErrorDAO errorDAO = (ErrorDAOImpl) CommonDaoFactory.getDAO(ErrorDAO.class.getName());
		final List result = errorDAO.getErrorList();
	
		LogManager.logExit();
		LogManager.push("ErrorCB getErrorListCB() method Ends");
		LogManager.popRemove();
		return result;		
	}	
	
	public List getEditErrorDetailsCB(final String updateId) throws CommonBaseException
	{
 		    LogManager.push("ErrorCB getEditErrorDetailsCB() method Starts");
			LogManager.logEnter();
		
			
			final ErrorDAO errorDAO = (ErrorDAOImpl) CommonDaoFactory.getDAO(ErrorDAO.class.getName());
			final List result = errorDAO.getEditErrorDetails(updateId);
		
			LogManager.logExit();
			LogManager.push("ErrorCB getEditErrorDetailsCB() method Ends");
			LogManager.popRemove();
			return result;		
	}
	
	public List getSearchByErrorCodeCB(final String searchValue) throws CommonBaseException
	{
		LogManager.push("ErrorCB getSearchByErrorCodeCB() method Starts");
		LogManager.logEnter();
	
		
		final ErrorDAO errorDAO = (ErrorDAOImpl) CommonDaoFactory.getDAO(ErrorDAO.class.getName());
		final List result = errorDAO.getSearchByErrorCode(searchValue);
	
		LogManager.logExit();
		LogManager.push("ErrorCB getSearchByErrorCodeCB() method Ends");
		LogManager.popRemove();
		return result;		
	}
	
	public List getSearchByErrorDescCB(final String searchValue) throws CommonBaseException
	{
		LogManager.push("ErrorCB getSearchByErrorDescCB() method Starts");
		LogManager.logEnter();
	
		
		final ErrorDAO errorDAO = (ErrorDAOImpl) CommonDaoFactory.getDAO(ErrorDAO.class.getName());
		final List result = errorDAO.getSearchByErrorDesc(searchValue);
	
		LogManager.logExit();
		LogManager.push("ErrorCB getSearchByErrorDescCB() method Ends");
		LogManager.popRemove();
		return result;		
	}
}