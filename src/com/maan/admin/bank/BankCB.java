package com.maan.admin.bank;

import java.util.ArrayList;
import java.util.List;

import com.maan.admin.master.error.ErrorDAO;
import com.maan.admin.master.error.ErrorDAOImpl;
import com.maan.admin.master.error.ErrorVB;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;

public class BankCB extends CommonBaseCB  {
	
	
	public List getBankDetails() throws CommonBaseException
	{
		LogManager.push("Bank getbankDetails() method Starts");
		LogManager.logEnter();
	
		ArrayList list=null;
		final BankDAO bankDAO = (BankDAOImpl) CommonDaoFactory.getDAO(BankDAO.class.getName());
		 list  = (ArrayList) bankDAO.getBankDetails();
	
		LogManager.logExit();
		LogManager.push("bank getbankDetails()) method Ends");
		LogManager.popRemove();
		return list;
		
	}
	
	public List getEditBankDetails(String bankid)throws CommonBaseException
	{
		
		LogManager.push("Bank getEditBankDetails() method Starts");
		LogManager.logEnter();
	
		List list=null;
		final BankDAO bankDAO = (BankDAOImpl) CommonDaoFactory.getDAO(BankDAO.class.getName());
		 list  = (List) bankDAO.getEditBankDetails(bankid);
	
		LogManager.logExit();
		LogManager.push("bank getEditBankDetails()) method Ends");
		LogManager.popRemove();
		return list;
		
		
	}
	
	public int insertBankDetails(BankVB sVB)throws CommonBaseException
	{
		LogManager.push("Bank insertBankDetails() method Starts");
		LogManager.logEnter();
		
		final BankDAO bankDAO = (BankDAOImpl) CommonDaoFactory.getDAO(BankDAO.class.getName());
		final int result =bankDAO.insertBankDetails(sVB); 
		LogManager.logExit();
		LogManager.push("Bank insertBankDetails()) method Ends");
		LogManager.popRemove();
		return result;
	}
	
	public int updateBankDetails(BankVB sVB,String bankid)throws CommonBaseException
	{
		LogManager.push("Bank updateBankDetails() method Starts");
		LogManager.logEnter();
		
		final BankDAO bankDAO = (BankDAOImpl) CommonDaoFactory.getDAO(BankDAO.class.getName());
		final int result =bankDAO.updateBankDetails(sVB,bankid); 
		
		LogManager.logExit();
		LogManager.push("Bank updateBankDetails()) method Ends");
		LogManager.popRemove();
		
		return result;
	}
	
	

}
