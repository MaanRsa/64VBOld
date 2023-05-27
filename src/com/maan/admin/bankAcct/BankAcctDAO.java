package com.maan.admin.bankAcct;

import java.util.List;
import java.util.Map;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface BankAcctDAO  extends CommonBaseDAO{
	
	List getBankAcctDetails() throws CommonBaseException;
	int insertBankAcctDetails(BankAcctVB sVB)throws CommonBaseException;
	List getEditBankAcctDetails(String bankid)throws CommonBaseException;
	/*int updateBankDetails(BankAcctVB sVB,String bankid)throws CommonBaseException;
	*/
	Map getBankList()throws CommonBaseException;
	int updateBankAcctDetails(BankAcctVB svb, String bankid) throws CommonBaseException;
 
}
