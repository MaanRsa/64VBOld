package com.maan.admin.bankRejection;

import java.util.List;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface BankRejectionDAO  extends CommonBaseDAO{
	
	List getBankRejectionDetails() throws CommonBaseException;
	int insertBankRejectionDetails(BankRejectionVB sVB)throws CommonBaseException;
	List getEditBankRejectionDetails(String rejectid)throws CommonBaseException;
	int updateBankRejectionDetails(BankRejectionVB sVB,String rejectid)throws CommonBaseException;

}
