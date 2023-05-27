package com.maan.admin.TmasValidation;

import com.maan.common.LogManager;

public class TmasValidationQuery 
{
	

	public static final String GetListExist = " SELECT DISTINCT TV.VALID_ID,MM.POLICYTYPE TYPE_ID,TV.DB_COLUMN_NAME,TV.EXCEL_HEADER_NAME,TV.VALIDATION_STATUS,TV.MANDATORY_STATUS,TV.FIELD_LENGTH,TV.DATA_TYPE,TV.DATA_FORMAT,TV.REFERENCE_STATUS,TV.REFERENCE_TABLE,TV.REFERENCE_COLUMN,TV.REFERENCE_CONDITION,TV.CHECK_VALUE,TV.CHECK_VALUE_COND,TV.ACTIVE,TV.MFR_ID,TV.XGEN_COLUMN,TV.XML_TAG_NAME FROM TMAS_VALIDATION TV,TMAS_POLICYTYPE MM WHERE TV.MFR_ID=? AND TV.TYPE_ID=MM.POLICYTYPEID ";

	public static String InsertTmasValidation() 
	{
		StringBuffer query=new StringBuffer();
		
		LogManager.push( " --------------Method Enter into TmasValidation Query level--------------   ");
		
		query.append( " INSERT INTO TMAS_VALIDATION " );
		
		query.append( " ( VALID_ID,TYPE_ID,DB_COLUMN_NAME,EXCEL_HEADER_NAME " );
		
		query.append( " ,VALIDATION_STATUS,MANDATORY_STATUS,FIELD_LENGTH,DATA_TYPE " );
		
		query.append( " ,DATA_FORMAT,REFERENCE_STATUS,REFERENCE_TABLE,REFERENCE_COLUMN,REFERENCE_CONDITION " );
		
		query.append( " ,CHECK_VALUE,CHECK_VALUE_COND,ACTIVE,MFR_ID,XGEN_COLUMN,XML_TAG_NAME ) " );
		
		query.append( " values " );
		
		query.append( " ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ) " );
		
		LogManager.push("Insert Query----------------"+query.toString());
		
		return query.toString();
	}

	
}
