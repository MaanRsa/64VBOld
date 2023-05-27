package com.maan.search;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;

import com.maan.common.LogManager;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;
import com.maan.upload2.UploadDAO;
import com.maan.upload2.UploadDAOImpl;
import com.maan.upload2.UploadForm;

public class SearchCB {

	public String validateSearchInfo(SearchFormBean sbean,String result)
	{
		if(sbean.getBankId().equalsIgnoreCase("select"))
		{
				result="Please select Bank Name";
			
		}
		else if((sbean.getChequeNo().equalsIgnoreCase("")) && (sbean.getChequeAmount().equalsIgnoreCase("")) && (sbean.getReceiptNo().equalsIgnoreCase("")))
		{
				result="Please enter Cheque no. or Cheque Amount or Receipt No.";
			
		}
		else if(isString(sbean.getChequeAmount())&& !(sbean.getChequeAmount().equalsIgnoreCase("")))
		{
			result="Cheque amount is not valid";
		}
		if((sbean.getChequeNo().length()>0)&&sbean.getChequeNo().length()<4&&sbean.getSearchFor().equalsIgnoreCase("similar"))
		{
			result=result+"</br>"+"Cheque no should contain minimum 4 characters.";
		}
		if((sbean.getReceiptNo().length()>0)&&sbean.getReceiptNo().length()<6&&sbean.getSearchFor().equalsIgnoreCase("similar"))
		{
			result=result+"</br>"+"Receipt no should contain minimum 6 characters.";
		}	
		if(!sbean.getSearchFor().equalsIgnoreCase("exact")&&!sbean.getSearchIn().equalsIgnoreCase("Receipt"))
		{
			if(!sbean.getFromDate().equalsIgnoreCase(""))
			{   
				try{
					String fromDate=sbean.getFromDate();
					DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		        	Date date2 = (Date)formatter.parse(fromDate);
					int flag=DateUtils.addMonths(new Date(), -6).compareTo(date2);
				    System.out.println(flag);
	        	    if(flag==1)
	        	    {
	        	    	result=result+"</br>"+"Deposit date should be in last 6 months period.";
	        	    }
	        	
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
			else
			{
				result=result+"</br>"+"Please enter the Deposit Date";
			}
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
	public List getReceiptReversal(SearchFormBean sbean) throws CommonBaseException
	{
		List result;
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getReceiptReversal(sbean);
		
		return result;
	}
	public List getMatchedList(SearchFormBean sbean, String sid) throws CommonBaseException
	{
		List result;
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getMatchedList(sbean,sid);
		
		return result;
	}
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

	public List getNotRealisedList(SearchFormBean sbean)throws CommonBaseException {
		List result;
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getNotRealisedList(sbean);
		
		return result;
	}
	
	public List getNotRealizedList(SearchFormBean sbean)throws CommonBaseException {
		List result;
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getNotRealizedList(sbean);
		
		return result;
	}

	public void updateChequeDetails(List list, SearchFormBean form)throws CommonBaseException  {
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		sdao.updateChequeDetails(list,form);
		
	}
	public void updateReversalDetails(List list, SearchFormBean form)throws CommonBaseException  {
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		sdao.updateReversalDetails(list,form);
		
	}

	public List getRealisedTransactionList(SearchFormBean form)throws CommonBaseException  {
		List result;
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getRealisedTransactionList(form);
		return result;
	}

	public List getNotMatchedList(SearchFormBean sbean, String sid) throws CommonBaseException  {
		LogManager.info("searchCB getNotMatchedList() Method - Enter");
		
		List result;
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getNotMatchedList(sbean,sid);
		LogManager.info("searchCB getNotMatchedList() Method - exit");
		
		return result;
	}

	public void getReceiptDetail(ReceiptDetails receipt)throws CommonBaseException  {
		LogManager.info("searchCB getReceiptDetail() Method - Enter");
	
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		sdao.getReceiptDetail(receipt);
		LogManager.info("searchCB getReceiptDetail() Method - Exit");
		
	}

	public List getBankDuplicates(String transid, String bankid) throws CommonBaseException{
		LogManager.info("searchCB getBankDuplicates() Method - Enter");
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		List result=sdao.getBankDuplicates(transid,bankid);
		LogManager.info("searchCB getBankDuplicates() Method - Exit");
	     return result;
	}
	public List getReceiptDuplicates(String transid) throws CommonBaseException{
		LogManager.info("searchCB getReceiptDuplicates() Method - Enter");
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		List result=sdao.getReceiptDuplicates(transid);
		LogManager.info("searchCB getReceiptDuplicates() Method - Exit");
	     return result;
	}

	public List getReceiptInvalids(String transid)throws CommonBaseException{
        LogManager.info("searchCB getReceiptInvalids() Method - Enter");
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		List result=sdao.getReceiptInvalids(transid);
		LogManager.info("searchCB getReceiptInvalids() Method - Exit");
	     return result;
	}
	public List getBankInvalids(String transid, String bankid)throws CommonBaseException{
        LogManager.info("searchCB getReceiptInvalids() Method - Enter");
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		List result=sdao.getBankInvalids(transid,bankid);
		LogManager.info("searchCB getReceiptInvalids() Method - Exit");
	     return result;
	}
	
	public List getReceiptReversals(String transid)throws CommonBaseException{
        LogManager.info("searchCB getReceiptReversals() Method - Enter");
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		List result=sdao.getReceiptReversals(transid);
		LogManager.info("searchCB getReceiptReversals() Method - Exit");
	     return result;
	}
	public List getBankReversals(String transid, String bankid)throws CommonBaseException{
        LogManager.info("searchCB getBankReversals() Method - Enter");
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		List result=sdao.getBankReversals(transid,bankid);
		LogManager.info("searchCB getBankReversals() Method - Exit");
	     return result;
	}
	public List getReceiptPayments(String transid)throws CommonBaseException{
        LogManager.info("searchCB getReceiptPayments() Method - Enter");
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		List result=sdao.getReceiptPayments(transid);
		LogManager.info("searchCB getReceiptPayments() Method - Exit");
	     return result;
	}
	
	public void updateReverse(SearchFormBean sForm)throws CommonBaseException{
        LogManager.info("searchCB updateReverse() Method - Enter");
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		sdao.updateReverse(sForm);
		LogManager.info("searchCB updateReverse() Method - Exit");
	   
	}
	public void updateFields(SearchFormBean sForm)throws CommonBaseException{
        LogManager.info("searchCB updateFields() Method - Enter");
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		sdao.updatefields(sForm);
		LogManager.info("searchCB updateFields() Method - Exit");
	   
	}
	public void updateReverse2(SearchFormBean sForm)throws CommonBaseException{
        LogManager.info("searchCB updateReverse() Method - Enter");
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		sdao.updateReverse2(sForm);
		LogManager.info("searchCB updateReverse() Method - Exit");
	}

	public List getReversalList(SearchFormBean form) throws CommonBaseException {
        List result;
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getReversalList(form);
		return result;
	}
	
	public void updateReversalFields(SearchFormBean sForm)throws CommonBaseException{
        LogManager.info("searchCB updateFields() Method - Enter");
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		sdao.updateReversalFields(sForm);
		LogManager.info("searchCB updateFields() Method - Exit");
	}

	public void updateReverseReversal(SearchFormBean form)throws CommonBaseException{
		LogManager.info("searchCB updateReverse() Method - Enter");
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		sdao.updateReverseReversal(form);
		LogManager.info("searchCB updateReverse() Method - Exit");
	}

	public List getBankNocheqeus(String transid, String bankid)throws CommonBaseException{
        LogManager.info("searchCB getReceiptInvalids() Method - Enter");
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		List result=sdao.getBankNocheqeus(transid,bankid);
		LogManager.info("searchCB getReceiptInvalids() Method - Exit");
	    return result;
	}

	public List getReversalUpdatedList(String[] bankNo1,SearchFormBean form, String sid) throws CommonBaseException{
         List result;
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
		result=sdao.getReversalUpdatedList(bankNo1,form,sid);
		
		return result;
	}

	public void processActuals(SearchFormBean form) throws CommonBaseException{
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
        sdao.processActuals(form);
		
		
	}

	public void deleteTemp(String sid)throws CommonBaseException{
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
        sdao.deleteTemp(sid);
		
	}

	public void updateSelectedData(String[] bankNo,String[] actualChequeNo,String[] actualChequeAmount,String bankData)throws CommonBaseException{
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
        sdao.updateSelectedData(bankNo,actualChequeNo,actualChequeAmount,bankData);
	}
	public void updateBankReversalData(String bankNos,SearchFormBean form)throws CommonBaseException{
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
        sdao.updateBankReversalData(bankNos,form);
        
	}
	
	public void updateBankActualDetails(String[] actualChequeNos,String[] actualChequeAmount,String[] bankNos,SearchFormBean form)throws CommonBaseException{
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
        sdao.updateBankActualData(actualChequeNos,actualChequeAmount,bankNos,form);
        
	}

	public List getNotMatchedActualList(String[] bankNo,SearchFormBean sbean,String sid)throws CommonBaseException {
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
        List list=sdao.getNotMatchedActualList(bankNo,sbean,sid);
		return list;
	}

	public List getActualMatchedList(String[] bankNos, SearchFormBean sbean,String id)throws CommonBaseException  {
		
		SearchDAO sdao=(SearchDAOImpl)CommonDaoFactory.getDAO(SearchDAO.class.getName());
        List list=sdao.getActualMatchedList(bankNos,sbean,id);
		return list;
		
	}
	
}
