package com.maan.reversal;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.maan.common.LogManager;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;

public class ReversalCB {

	public String validateSearchInfo(ReversalFormBean sbean,String result)
	{
		LogManager.push("validateSearchInfo-Enter");
		if(sbean.getFromDate().equalsIgnoreCase(""))
			result="Please Select From Date";
		else if(sbean.getToDate().equalsIgnoreCase(""))
			result="Please Select To Date";
		LogManager.push("validateSearchInfo-Exit");
		return result;
	}
	
	public List getReceiptSearchList(ReversalFormBean sbean) throws CommonBaseException
	{
		List result;
		LogManager.push("getReceiptSearchList-Enter");
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getReceiptSearchList(sbean);
		LogManager.push("getReceiptSearchList-Exit");
		return result;
	}
	
	public List getCitiSearchList(ReversalFormBean sbean) throws CommonBaseException
	{
		List result;
		LogManager.push("getCitiSearchList-Enter");
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getCitiSearchList(sbean);
		LogManager.push("getCitiSearchList-Exit");
		return result;
	}
	
	public List getHdfcSearchList(ReversalFormBean sbean) throws CommonBaseException
	{
		List result;
		LogManager.push("getHdfcSearchList-Enter");
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getHdfcSearchList(sbean);
		LogManager.push("getHdfcSearchList-Exit");
		return result;
	}
	
	public List getHsbcSearchList(ReversalFormBean sbean) throws CommonBaseException
	{
		List result;
		LogManager.push("getHsbcSearchList-Enter");	
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getHsbcSearchList(sbean);
		LogManager.push("getHsbcSearchList-Exit");
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
	
	public List getReceiptReversalsList(ReversalFormBean sbean) throws CommonBaseException {
        List result;
        LogManager.push("getReceiptReversalsList-Enter");
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getReceiptReversalsList(sbean);
		LogManager.push("getReceiptReversalsList-Exit");
		return result;
	}
	
	public List getCitiReversalsList(ReversalFormBean sbean) throws CommonBaseException {
        List result;
        LogManager.push("getCitiReversalsList-Enter");	
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getCitiReversalsList(sbean);
		LogManager.push("getCitiReversalsList-Exit");
		return result;
	}
	
	public List getHdfcReversalsList(ReversalFormBean sbean) throws CommonBaseException {
        List result;
        LogManager.push("getHdfcReversalsList-Enter");
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getHdfcReversalsList(sbean);
		LogManager.push("getHdfcReversalsList-Exit");
		return result;
	}
	
	public List getHsbcReversalsList(ReversalFormBean sbean) throws CommonBaseException {
        List result;
        LogManager.push("getHsbcReversalsList-Enter");
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getHsbcReversalsList(sbean);
		LogManager.push("getHsbcReversalsList-Exit");
		return result;
	}
	
	public List getScbSearchList(ReversalFormBean sbean) throws CommonBaseException {
		List result;
		LogManager.push("getScbSearchList-Enter");
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getScbSearchList(sbean);
		LogManager.push("getScbSearchList-Exit");
		return result;
	}
	
	public List getScbReversalsList(ReversalFormBean sbean)  throws CommonBaseException {
		List result;
		LogManager.push("getScbReversalsList-Enter");
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getScbReversalsList(sbean);
		LogManager.push("getScbReversalsList-Exit");
		return result;
	}
	
	public List getAxisSearchList(ReversalFormBean sbean) throws CommonBaseException {
		List result;
		LogManager.push("getAxisSearchList-Enter");
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getAxisSearchList(sbean);
		LogManager.push("getAxisSearchList-Exit");
		return result;
	}
	
	public List getAxisReversalsList(ReversalFormBean sbean)  throws CommonBaseException {
		List result;
		LogManager.push("getAxisSearchList-Enter");
		ReversalDAO sdao=(ReversalDAOImpl)CommonDaoFactory.getDAO(ReversalDAO.class.getName());
		result=sdao.getAxisReversalsList(sbean);
		LogManager.push("getAxisReversalsList-Exit");
		return result;
	}
	
}
