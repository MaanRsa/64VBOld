package com.maan.user.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.maan.common.LogManager;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;

import com.maan.upload2.UploadDAO;
import com.maan.upload2.UploadDAOImpl;
import com.maan.upload2.UploadForm;

public class SearchCB {

	public String validateSearchInfo(SearchFormBean sbean,String result)
	{
		if((sbean.getChequeNo().equalsIgnoreCase("")) && (sbean.getChequeAmount().equalsIgnoreCase("")) && (sbean.getReceiptNo().equalsIgnoreCase("")))
		{
			result="Please enter Cheque no. or Cheque Amount or Receipt No.";
			
		}
		
		else if(isString(sbean.getChequeAmount())&& !(sbean.getChequeAmount().equalsIgnoreCase("")))
		{
			result="Cheque amount is not valid";
		}
		return result;
	}
	public String validatePolicySearchInfo(SearchFormBean sbean,String result)
	{
		if(sbean.getPolicyNo().equalsIgnoreCase(""))
		{
				result="Please enter PolicyNo";
			
		}
		return result;
	}
	public List getSearchList(SearchFormBean sbean) throws CommonBaseException
	{
		List result;
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getSearchList(sbean);
		
		return result;
	}
	public List getPolicySearchList(SearchFormBean sbean) throws CommonBaseException
	{
		List result;
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getPolicySearchList(sbean);
		
		return result;
	}
	/*public List getBankSearchList(SearchFormBean sbean) throws CommonBaseException
	{
		List result;
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getBankSearchList(sbean);
		
		return result;
	}
	public List getReceiptSearchList(SearchFormBean sbean) throws CommonBaseException
	{
		List result;
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getReceiptSearchList(sbean);
		
		return result;
	}*/
	public boolean isString(String arg)
	{
		boolean result=true;
		String pattern ="[0-9.]+";
		Pattern p=Pattern.compile(pattern);
		Matcher m=p.matcher(arg);
		if(m.matches())
		result=false;
			
		return result;
	}
	public Map getBankList(final SearchFormBean form) throws CommonBaseException{
		LogManager.info("searchCB bankList() Method - Enter");
		Map result = new HashMap();
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getBankList(form);
		
		LogManager.info("searchCB bankList() Method - Exit");
		return result;
	}

	public void getReceiptDetail(ReceiptDetails receipt)throws CommonBaseException  {
		LogManager.info("searchCB getReceiptDetail() Method - Enter");
	
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		sdao.getReceiptDetail(receipt);
		LogManager.info("searchCB getReceiptDetail() Method - Exit");
		
	}
	public List doManualRealization(String searchOption,String searchValue) throws CommonBaseException
	{
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		return sdao.doManualRealization(searchOption,searchValue);
	}
		

	public void updateChecked(String receiptNo, String checkStatus)throws CommonBaseException {
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		sdao.updateChecked(receiptNo,checkStatus);
		
	}
	public List getManulRealizedList(String searchOption, String searchValue)throws CommonBaseException {
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		return sdao.getManulRealizedList(searchOption,searchValue);
		
	}
	public List getPendingList(SearchFormBean sbean) throws CommonBaseException{
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		return sdao.getPendingList(sbean);
		
	}
	

}
