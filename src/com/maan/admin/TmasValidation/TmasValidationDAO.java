package com.maan.admin.TmasValidation;

import java.util.List;
import java.util.Map;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;



public interface TmasValidationDAO extends  CommonBaseDAO
{

   Map getTypeId(TmasValidationForm formObj,String id)throws CommonBaseException;

   boolean GetInsertTmasValidation(TmasValidationBean beanObj)throws CommonBaseException;

   List GetListTmasValidation(String mfrId)throws CommonBaseException;

List GetEditTmasValidation(String updateTmasValidationId)throws CommonBaseException;

void UpdateTmasValidation(TmasValidationBean beanObj)throws CommonBaseException;
	

}
