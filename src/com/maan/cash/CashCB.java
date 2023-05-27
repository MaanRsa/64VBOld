package com.maan.cash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.maan.common.LogManager;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;
import com.maan.login.LoginForm;

public class CashCB {

	public String validateCashInfo(CashFormBean sbean,String result)
	{
		
		 if(sbean.getBankId().equalsIgnoreCase("select"))
		{
				result="Please select Bank Name";
			
		}
		 else if(sbean.getTransactionDate().equalsIgnoreCase(""))
			{
					result="Please Enter Date";
				
			}		
		return result;
	}
	
	
	
	public boolean isString(String arg)
	{
		boolean result=true;
		String pattern ="[0-9]+";
		Pattern p=Pattern.compile(pattern);
		Matcher m=p.matcher(arg);
		if(m.matches())
		result=false;
			
		return result;
	}
	public Map getBankList(final CashFormBean form) throws CommonBaseException{
		LogManager.info("cashCB bankList() Method - Enter");
		Map result = new HashMap();
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		result=sdao.getBankList(form);
		
		LogManager.info("cashCB bankList() Method - Exit");
		return result;
	}
	
	public List getBranchList(final CashFormBean form) throws CommonBaseException{
		LogManager.info("cashCB bankList() Method - Enter");
		List result;
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		result=sdao.getBranchList(form);
		
		LogManager.info("cashCB bankList() Method - Exit");
		return result;
	}
	public List getSearchList(CashFormBean sbean) throws CommonBaseException
	{
		List result;
		
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		result=sdao.getSearchList(sbean);
		
		return result;
	}
	public List getReceiptList(CashFormBean sbean) throws CommonBaseException
	{
		List result;
		
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		result=sdao.getReceiptList(sbean);
		
		return result;
	}



	public String updateSelected(String checkedRecptNos,String uncheckedRecptNos)throws CommonBaseException {
       
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		String sum=sdao.updateSelected(checkedRecptNos,uncheckedRecptNos);
		return sum;
		
	}
	public String updateAsSelected(CashFormBean sbean)throws CommonBaseException {
	       
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		String sum = sdao.updateAsSelected(sbean);
		return sum;
		
	}
	public String updateUnSelected(CashFormBean sbean)throws CommonBaseException {
	       
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		String sum = sdao.updateUnSelected(sbean);
		return sum;
		
	}
	public void updateReceipt()throws CommonBaseException {
       
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		sdao.updateReceipt();
		
	}



	public boolean checkvalid(CashFormBean sbean, LoginForm loginForm)throws CommonBaseException {
		// TODO Auto-generated method stub
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		boolean flag=sdao.checkvalid(sbean,loginForm);
		return flag;
	}



	public List getMatchDetail(String bankval,CashFormBean sbean) throws CommonBaseException {
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		List list=sdao.getMatchDetail(bankval,sbean);
		return list;
	}


	public List getReceiptMatchDetail(String bankval,CashFormBean sbean) throws CommonBaseException {
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		List list=sdao.getReceiptMatchDetail(bankval,sbean);
		return list;
	}



	public String getsum() throws CommonBaseException {
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		String sum=sdao.getsum();
		return sum;
	}



	public void setEmpty()throws CommonBaseException {
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		sdao.setEmpty();
			
	}

	public int getCount() throws CommonBaseException {
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
		int count=sdao.getCount();
		return count;
	}
	public String getBankId(String bankName) throws CommonBaseException {
		CashDAO sdao=(CashDAOImpl)CommonDaoFactory.getDAO(CashDAO.class.getName());
        String bankId=sdao.getBankId(bankName);
		return bankId;
	}
}
