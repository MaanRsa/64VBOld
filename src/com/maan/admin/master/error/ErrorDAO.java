package com.maan.admin.master.error;

import java.util.List;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface ErrorDAO extends CommonBaseDAO 
{
	 String insertErrorDetails(ErrorVB errorVB) throws CommonBaseException;
	 String updateErrorDetails(ErrorVB errorVB) throws CommonBaseException;
	 List getErrorList() throws CommonBaseException;
	 List getEditErrorDetails(String updateId) throws CommonBaseException;
	 List getSearchByErrorCode(String searchValue) throws CommonBaseException;
	 List getSearchByErrorDesc(String searchValue) throws CommonBaseException;
	
}