package com.maan.admin.bankError;

import java.util.List;
import java.util.Map;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface BankErrorDAO  extends CommonBaseDAO{
	
	List getBankErrorDetails() throws CommonBaseException;
	Map getBankRejectionList() throws CommonBaseException;
	int insertBankErrorDetails(BankErrorVB sVB)throws CommonBaseException;
	List getEditBankErrorDetails(String errorid)throws CommonBaseException;
	int updateBankErrorDetails(BankErrorVB sVB,String errorid)throws CommonBaseException;
	

}
