package com.maan.admin.bankError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;

public class BankErrorCB extends CommonBaseCB  {
	
	public List getBankErrorDetails() throws CommonBaseException
	{
		LogManager.push("Bank getBankErrorDetails() method Starts");
		LogManager.logEnter();
	
		ArrayList list=null;
		final BankErrorDAO bankErrorDAO = (BankErrorDAOImpl) CommonDaoFactory.getDAO(BankErrorDAO.class.getName());
		 list  = (ArrayList) bankErrorDAO.getBankErrorDetails();
	
		LogManager.logExit();
		LogManager.push("bank getBankErrorDetails()) method Ends");
		LogManager.popRemove();
		return list;
		
	}
	public Map getBankRejectionList() throws CommonBaseException{
		LogManager.info("bankErrorCB getBankRejectionList() Method - Enter");
		Map result = new HashMap();
		BankErrorDAO bdao=(BankErrorDAOImpl)CommonDaoFactory.getDAO(BankErrorDAO.class.getName());
		result=bdao.getBankRejectionList();
		
		LogManager.info("bankErrorCB getBankRejectionList() Method - Exit");
		return result;
	}
	
	public List getEditBankErrorDetails(String errorid)throws CommonBaseException
	{
		
		LogManager.push("Bank getEditBankErrorDetails() method Starts");
		LogManager.logEnter();
	
		List list=null;
		final BankErrorDAO bankErrorDAO = (BankErrorDAOImpl) CommonDaoFactory.getDAO(BankErrorDAO.class.getName());
		 list  = (List) bankErrorDAO.getEditBankErrorDetails(errorid);
	
		LogManager.logExit();
		LogManager.push("bank getEditBankErrorDetails()) method Ends");
		LogManager.popRemove();
		return list;
		
		
	}
	
	public int insertBankErrorDetails(BankErrorVB sVB)throws CommonBaseException
	{
		LogManager.push("Bank insertBankErrorDetails() method Starts");
		LogManager.logEnter();
		
		final BankErrorDAO bankErrorDAO = (BankErrorDAOImpl) CommonDaoFactory.getDAO(BankErrorDAO.class.getName());
		final int result =bankErrorDAO.insertBankErrorDetails(sVB); 
		LogManager.logExit();
		LogManager.push("Bank insertBankErrorDetails()) method Ends");
		LogManager.popRemove();
		return result;
	}
	
	public int updateBankErrorDetails(BankErrorVB sVB,String errorid)throws CommonBaseException
	{
		LogManager.push("Bank updateBankErrorDetails() method Starts");
		LogManager.logEnter();
		
		final BankErrorDAO bankErrorDAO = (BankErrorDAOImpl) CommonDaoFactory.getDAO(BankErrorDAO.class.getName());
		final int result =bankErrorDAO.updateBankErrorDetails(sVB,errorid); 
		
		LogManager.logExit();
		LogManager.push("Bank updateBankErrorDetails()) method Ends");
		LogManager.popRemove();
		
		return result;
	}
	
	

}
