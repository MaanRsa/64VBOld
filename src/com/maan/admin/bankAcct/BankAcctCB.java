package com.maan.admin.bankAcct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;

public class BankAcctCB extends CommonBaseCB  {
	
	public List getBankAcctDetails() throws CommonBaseException
	{
		LogManager.push("Bank getbankDetails() method Starts");
		LogManager.logEnter();
	
		ArrayList list=null;
		final BankAcctDAO bankAcctDAO = (BankAcctDAOImpl) CommonDaoFactory.getDAO(BankAcctDAO.class.getName());
		 list  = (ArrayList) bankAcctDAO.getBankAcctDetails();
	
		LogManager.logExit();
		LogManager.push("bank getbankDetails()) method Ends");
		LogManager.popRemove();
		return list;
		
	}
	public Map getBankList() throws CommonBaseException{
		LogManager.info("bankacctCB bankList() Method - Enter");
		Map result = new HashMap();
		BankAcctDAO bdao=(BankAcctDAOImpl)CommonDaoFactory.getDAO(BankAcctDAO.class.getName());
		result=bdao.getBankList();
		
		LogManager.info("bankacctCB bankList() Method - Exit");
		return result;
	}
	
	public List getEditBankAcctDetails(String bankid)throws CommonBaseException
	{
		
		LogManager.push("Bank getEditBankAcctDetails() method Starts");
		LogManager.logEnter();
	
		List list=null;
		final BankAcctDAO bankAcctDAO = (BankAcctDAOImpl) CommonDaoFactory.getDAO(BankAcctDAO.class.getName());
		 list  = (List) bankAcctDAO.getEditBankAcctDetails(bankid);
	
		LogManager.logExit();
		LogManager.push("bank getEditBankAcctDetails()) method Ends");
		LogManager.popRemove();
		return list;
		
		
	}
	
	public int insertBankAcctDetails(BankAcctVB sVB)throws CommonBaseException
	{
		LogManager.push("Bank insertBankDetails() method Starts");
		LogManager.logEnter();
		
		final BankAcctDAO bankAcctDAO = (BankAcctDAOImpl) CommonDaoFactory.getDAO(BankAcctDAO.class.getName());
		final int result =bankAcctDAO.insertBankAcctDetails(sVB); 
		LogManager.logExit();
		LogManager.push("Bank insertBankDetails()) method Ends");
		LogManager.popRemove();
		return result;
	}
	 
	
	public int updateBankAcctDetails(BankAcctVB sVB,String bankid)throws CommonBaseException
	{
		LogManager.push("Bank updateBankDetails() method Starts");
		LogManager.logEnter();
		
		final BankAcctDAO bankAcctDAO = (BankAcctDAOImpl) CommonDaoFactory.getDAO(BankAcctDAO.class.getName());
		final int result =bankAcctDAO.updateBankAcctDetails(sVB,bankid); 
		
		LogManager.logExit();
		LogManager.push("Bank updateBankDetails()) method Ends");
		LogManager.popRemove();
		
		return result;
	}
	

	
}
