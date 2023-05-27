package com.maan.admin.TmasValidation;

import com.maan.common.Runner;


public class TmasValidationArguments {

	public static String[] InsertArguments(TmasValidationBean beanObj) 
	{
		String[] args=new String[19];
		args[0]=GetmaxValidId();
		args[1]=beanObj.getTypeid();
		args[2]=beanObj.getDbColumnName();
		args[3]=beanObj.getExcelHeaderName();
		args[4]=beanObj.getValidationStatus();
		args[5]=beanObj.getMandatoryStatus();
		args[6]=beanObj.getFieldLength();
		args[7]=beanObj.getDataType();
		args[8]=beanObj.getDataFormat();
		args[9]=beanObj.getReferenceStatus();
		args[10]=beanObj.getReferenceTable();
		args[11]=beanObj.getReferenceColumn();
		args[12]=beanObj.getReferenceCondition();
		args[13]=beanObj.getCheckValue();
		args[14]=beanObj.getCheckValueCond();
		args[15]=beanObj.getActive();
		args[16]="0";
		args[17]=beanObj.getXgenColumn();
		args[18]=beanObj.getXmlTagName();
		return args;
	}
	
	private static String GetmaxValidId()
	{
		
		String ValidId=Runner.singleSelection(" select max(VALID_ID)+1 from TMAS_VALIDATION  ");
		return ValidId;
	}



}
