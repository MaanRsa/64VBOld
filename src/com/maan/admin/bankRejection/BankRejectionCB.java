package com.maan.admin.bankRejection;

import java.util.ArrayList;
import java.util.List;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;

public class BankRejectionCB extends CommonBaseCB  {
	
	public List getBankRejectionDetails() throws CommonBaseException
	{
		LogManager.push("Bank getbankDetails() method Starts");
		LogManager.logEnter();
	
		ArrayList list=null;
		final BankRejectionDAO bankRejectionDAO = (BankRejectionDAOImpl) CommonDaoFactory.getDAO(BankRejectionDAO.class.getName());
		 list  = (ArrayList) bankRejectionDAO.getBankRejectionDetails();
	
		LogManager.logExit();
		LogManager.push("bank getbankDetails()) method Ends");
		LogManager.popRemove();
		return list;
		
	}


	public List getEditBankRejectionDetails(String rejectid)throws CommonBaseException
	{
		
		LogManager.push("Bank getEditBankRejectionDetails() method Starts");
		LogManager.logEnter();
	
		List list=null;
		final BankRejectionDAO bankRejectionDAO = (BankRejectionDAOImpl) CommonDaoFactory.getDAO(BankRejectionDAO.class.getName());
		 list  = (List) bankRejectionDAO.getEditBankRejectionDetails(rejectid);
	
		LogManager.logExit();
		LogManager.push("bank getEditBankRejectionDetails()) method Ends");
		LogManager.popRemove();
		return list;
		
		
	}
	
	public int insertBankRejectionDetails(BankRejectionVB sVB)throws CommonBaseException
	{
		LogManager.push("Bank insertBankRejectionDetails() method Starts");
		LogManager.logEnter();
		
		final BankRejectionDAO bankRejectionDAO = (BankRejectionDAOImpl) CommonDaoFactory.getDAO(BankRejectionDAO.class.getName());
		final int result =bankRejectionDAO.insertBankRejectionDetails(sVB); 
		LogManager.logExit();
		LogManager.push("Bank insertBankRejectionDetails()) method Ends");
		LogManager.popRemove();
		return result;
	}

	public int updateBankRejectionDetails(BankRejectionVB sVB,String rejectid)throws CommonBaseException
	{
		LogManager.push("Bank updateBankRejectionDetails() method Starts");
		LogManager.logEnter();
		
		final BankRejectionDAO bankRejectionDAO = (BankRejectionDAOImpl) CommonDaoFactory.getDAO(BankRejectionDAO.class.getName());
		final int result =bankRejectionDAO.updateBankRejectionDetails(sVB,rejectid); 
		
		LogManager.logExit();
		LogManager.push("Bank updateBankRejectionDetails()) method Ends");
		LogManager.popRemove();
		
		return result;
	}
	
	

}
