package com.maan.cash;

import java.util.List;
import java.util.Map;

import com.maan.common.exception.CommonBaseException;
import com.maan.login.LoginForm;


public interface CashDAO {
	
	public Map getBankList(CashFormBean form) throws CommonBaseException;
	public List getBranchList(CashFormBean form) throws CommonBaseException;
	public List getSearchList(CashFormBean sbean) throws CommonBaseException;
	public List getReceiptList(CashFormBean sbean) throws CommonBaseException;
	public String updateSelected(String checkedRecptNos,String uncheckedRecptNos)throws CommonBaseException;
	public void updateReceipt()throws CommonBaseException;
	public boolean checkvalid(CashFormBean sbean, LoginForm loginForm) throws CommonBaseException;
	public List getMatchDetail(String bankval,CashFormBean sbean) throws CommonBaseException;
	public List getReceiptMatchDetail(String bankval,CashFormBean sbean) throws CommonBaseException;
	public String updateAsSelected(CashFormBean sbean)throws CommonBaseException;
	public String updateUnSelected(CashFormBean sbean)throws CommonBaseException;
	public String getsum()throws CommonBaseException;
	public void setEmpty()throws CommonBaseException;
	public int getCount()throws CommonBaseException;
	public String getBankId(String bankName);
}
