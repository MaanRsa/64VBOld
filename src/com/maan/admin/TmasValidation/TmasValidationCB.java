package com.maan.admin.TmasValidation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;


public class TmasValidationCB extends CommonBaseCB 
{
	public Map getTypeId(TmasValidationForm formObj,String id) throws CommonBaseException
	{
		LogManager.push("------------Method Enter Into Tmas_validation CB-----------------");
		LogManager.logEnter();
		Map Result=new HashMap();
		final TmasValidationDAO tmasvalidationDAO=(TmasValidationDAOImpl) CommonDaoFactory.getDAO(TmasValidationDAO.class.getName());
		Result=tmasvalidationDAO.getTypeId(formObj,id);
		LogManager.logExit();
		LogManager.push("------------Method Exit Into Tmas_validation CB-------------------");
		LogManager.logExit();
		return Result;
	}

	public boolean insertTmasValidation(TmasValidationBean beanObj)throws CommonBaseException
	{
		
		LogManager.push("------------Method Enter Into InsertTmas_validation CB-----------------");
		LogManager.logEnter();
		final TmasValidationDAO tmasvalidationDAO=(TmasValidationDAOImpl) CommonDaoFactory.getDAO(TmasValidationDAO.class.getName());
		boolean SaveFlage=tmasvalidationDAO.GetInsertTmasValidation(beanObj);
		LogManager.logExit();
		LogManager.push("------------Method Exit Into InsertTmas_validation CB-------------------");
		LogManager.push("Insert CB Exit=========>");
		LogManager.logExit();
	
	    return SaveFlage;
	}

	public List ListTmasValidation(String mfrId)throws CommonBaseException
	{
		LogManager.push("------------Method Enter Into ListTmas_validation CB-----------------");
		LogManager.logEnter();
		final TmasValidationDAO tmasvalidationDAO=(TmasValidationDAOImpl) CommonDaoFactory.getDAO(TmasValidationDAO.class.getName());
		final List list=tmasvalidationDAO.GetListTmasValidation(mfrId);
		LogManager.logExit();
		LogManager.push("------------Method Exit Into ListTmas_validation CB-------------------");
		LogManager.logExit();

		return list;
	}

	public List getEditTmasValidation(String updateTmasValidationId)throws CommonBaseException
	{
		LogManager.push("------------Method Enter Into EditTmas_validation CB-----------------");
		LogManager.logEnter();
		final TmasValidationDAO tmasvalidationDAO=(TmasValidationDAOImpl) CommonDaoFactory.getDAO(TmasValidationDAO.class.getName());
		final List list=tmasvalidationDAO.GetEditTmasValidation(updateTmasValidationId);
		LogManager.logExit();
		LogManager.push("------------Method Exit Into EditTmas_validation CB-------------------");
		LogManager.logExit();

		return list;
	}

	public void UpdateTmasValidation(TmasValidationBean beanObj)throws CommonBaseException
	{
		LogManager.push("------------Method Enter Into UpdateTmas_validation CB-----------------");
		LogManager.logEnter();
		final TmasValidationDAO tmasvalidationDAO=(TmasValidationDAOImpl) CommonDaoFactory.getDAO(TmasValidationDAO.class.getName());
		tmasvalidationDAO.UpdateTmasValidation(beanObj);
		LogManager.logExit();
		LogManager.push("------------Method Exit Into UpdateTmas_validation CB-------------------");
		LogManager.logExit();

		
	}


	
}
