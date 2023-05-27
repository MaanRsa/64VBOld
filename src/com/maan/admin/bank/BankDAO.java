package com.maan.admin.bank;

import java.util.List;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface BankDAO  extends CommonBaseDAO{
	
	List getBankDetails() throws CommonBaseException;
	int insertBankDetails(BankVB sVB)throws CommonBaseException;
	List getEditBankDetails(String bankid)throws CommonBaseException;
	int updateBankDetails(BankVB sVB,String bankid)throws CommonBaseException;
	

}
