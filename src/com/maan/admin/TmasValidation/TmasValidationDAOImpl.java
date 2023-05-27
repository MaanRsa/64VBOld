package com.maan.admin.TmasValidation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.jdbc.core.RowMapper;

import com.maan.common.LogManager;
import com.maan.common.MotorBaseDAOImpl;
import com.maan.common.Runner;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.MotorBaseException;

public class TmasValidationDAOImpl extends CommonBaseDAOImpl implements TmasValidationDAO
{

	public Map getTypeId(TmasValidationForm formObj,String id) throws CommonBaseException 
	{
	   LogManager.push("----------Method Enter Into TmasValidationDAOImpl-----------");
	   LogManager.logEnter();
	   Map Result=new HashMap();
	   String mfrid=id;
	   try
	   {
		   final String query = "select unique policytypeid,policytype from TMAS_POLICYTYPE where active=1 and mfr_id=" + mfrid + " ";
		   final List list = this.mytemplate.queryForList(query);
		   if(list!=null && list.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					final Map temp = (Map)list.get(i);
					Result.put(temp.get("POLICYTYPEID"), temp.get("POLICYTYPE"));
				}
			}
		}
	   catch (Exception e) 
	   {
		   LogManager.fatal(e);
	   }
	   LogManager.push("----------Method Exit Into TmasValidationDAOImpl-------------");
	   LogManager.logExit();
	   LogManager.popRemove();
		return Result;
	}

	public boolean GetInsertTmasValidation(TmasValidationBean beanObj) throws CommonBaseException
	{
		LogManager.push("Insert Impl");
		 LogManager.push("----------Method Enter Into InsertTmasValidationDAOImpl-----------");
		 LogManager.logEnter();
			boolean saveFlag=false;
			
		 try
		 {
			 LogManager.push("Insert Start");
			 String[] args=TmasValidationArguments.InsertArguments(beanObj);
			 for(int i=0;i<args.length;i++)
			 {
				 LogManager.push("=====Value:::===>"+args[i]);
			 }
			 //Result=Runner.multipleInsertion(TmasValidationQuery.InsertTmasValidation(),args);
			 //String query= "  INSERT INTO TMAS_VALIDATION ( VALID_ID,TYPE_ID,DB_COLUMN_NAME,EXCEL_HEADER_NAME,VALIDATION_STATUS,MANDATORY_STATUS,FIELD_LENGTH,DATA_TYPE,DATA_FORMAT,REFERENCE_STATUS,REFERENCE_TABLE,REFERENCE_COLUMN,REFERENCE_CONDITION ";
			 
			 final int Result=this.mytemplate.update(TmasValidationQuery.InsertTmasValidation(), args);
			 if(Result==0)
			 {
				 LogManager.push("---->false");
				 saveFlag=false;
			 }
			 else
			 {
				 LogManager.push("----->true");
				 saveFlag=true;
				 
			 }
			 LogManager.push("Flag====>"+saveFlag);
			 
		 }
		 catch (Exception e) 
		 {
			 LogManager.fatal(e);
		 }
		 LogManager.push("----------Method Exit Into TmasValidationDAOImpl-------------");
		 LogManager.logExit();
		 LogManager.popRemove();
		return saveFlag;
	}

	public List GetListTmasValidation(String mfrId) throws CommonBaseException
	{
		LogManager.push("----------Method Enter Into TmasValidationDAOImpl-----------");
		 LogManager.logEnter();
		 Object[] args=new Object[1];
		 args[0]=mfrId;
		 LogManager.push("mfrId=======>"+mfrId);
		    LogManager.push("TmasValidationQuery.GetListExist"+TmasValidationQuery.GetListExist);
			final List list = this.mytemplate.query(TmasValidationQuery.GetListExist,args, new RowMapper() {
				public Object mapRow(final ResultSet rs, final int arg)throws SQLException {
				    TmasValidationBean beanObj=new TmasValidationBean();
				    beanObj.setValidId(rs.getString("VALID_ID"));
				    beanObj.setTypeid(rs.getString("TYPE_ID"));
				    beanObj.setDbColumnName(rs.getString("DB_COLUMN_NAME"));
				    beanObj.setExcelHeaderName(rs.getString("EXCEL_HEADER_NAME"));
				    beanObj.setValidationStatus(rs.getString("VALIDATION_STATUS").trim().equalsIgnoreCase("Y")?"Yes":"No");
				    beanObj.setMandatoryStatus(rs.getString("MANDATORY_STATUS"));
				    beanObj.setFieldLength(rs.getString("FIELD_LENGTH"));
				    beanObj.setDataType(rs.getString("DATA_TYPE"));
				    beanObj.setDataFormat(rs.getString("DATA_FORMAT"));
				    beanObj.setReferenceStatus(rs.getString("REFERENCE_STATUS"));
				    beanObj.setReferenceTable(rs.getString("REFERENCE_TABLE"));
				    beanObj.setReferenceColumn(rs.getString("REFERENCE_COLUMN"));
				    beanObj.setReferenceCondition(rs.getString("REFERENCE_CONDITION"));
				    beanObj.setCheckValue(rs.getString("CHECK_VALUE"));
				    beanObj.setCheckValueCond(rs.getString("CHECK_VALUE_COND"));
				    beanObj.setActive(rs.getString("ACTIVE"));
				    beanObj.setMfrid(rs.getString("MFR_ID"));
				    beanObj.setXgenColumn(rs.getString("XGEN_COLUMN"));
				    beanObj.setXmlTagName(rs.getString("XML_TAG_NAME"));
				    return beanObj;
				}
			});

 			
		 LogManager.push("----------Method Exit Into ListTmasValidationDAOImpl-------------");
		 LogManager.logExit();
		 LogManager.popRemove();
		return list;
	}

	public List GetEditTmasValidation(String updateTmasValidationId) throws CommonBaseException 
	{
		LogManager.push("--------------GetEditTmasValidation------------------------------");
		 LogManager.logEnter();
		 String query;
		 List list;
		 query= " SELECT VALID_ID,TYPE_ID,DB_COLUMN_NAME,EXCEL_HEADER_NAME,VALIDATION_STATUS,MANDATORY_STATUS,FIELD_LENGTH,DATA_TYPE,DATA_FORMAT,REFERENCE_STATUS,REFERENCE_TABLE,REFERENCE_COLUMN,REFERENCE_CONDITION,CHECK_VALUE,CHECK_VALUE_COND,ACTIVE,MFR_ID,XGEN_COLUMN,XML_TAG_NAME FROM TMAS_VALIDATION WHERE VALID_ID = " + updateTmasValidationId + " order by TYPE_ID " ;
		 list = (List) this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rs,final int idenity) throws SQLException {
					TmasValidationBean bean=new TmasValidationBean();
				    bean.setValidId(rs.getString("VALID_ID"));
				    bean.setTypeid(rs.getString("TYPE_ID"));
				    bean.setDbColumnName(rs.getString("DB_COLUMN_NAME"));
				    bean.setExcelHeaderName(rs.getString("EXCEL_HEADER_NAME"));
				    bean.setValidationStatus(rs.getString("VALIDATION_STATUS"));
				    bean.setMandatoryStatus(rs.getString("MANDATORY_STATUS"));
				    bean.setFieldLength(rs.getString("FIELD_LENGTH"));
				    bean.setDataType(rs.getString("DATA_TYPE"));
				    bean.setDataFormat(rs.getString("DATA_FORMAT"));
				    bean.setReferenceStatus(rs.getString("REFERENCE_STATUS"));
				    bean.setReferenceTable(rs.getString("REFERENCE_TABLE"));
				    bean.setReferenceColumn(rs.getString("REFERENCE_COLUMN"));
				    bean.setReferenceCondition(rs.getString("REFERENCE_CONDITION"));
				    bean.setCheckValue(rs.getString("CHECK_VALUE"));
				    bean.setCheckValueCond(rs.getString("CHECK_VALUE_COND"));
				    bean.setMfrid(rs.getString("MFR_ID"));
				    bean.setActive(rs.getString("ACTIVE"));
				    bean.setXgenColumn(rs.getString("XGEN_COLUMN"));
				    bean.setXmlTagName(rs.getString("XML_TAG_NAME"));
				    return bean;
				}
		 });
		 LogManager.logExit();
			LogManager.push("--------------Method Exit Into GetTmasValidationDAO--------");
			LogManager.popRemove(); 
		return list;
	}

	public void UpdateTmasValidation(TmasValidationBean beanObj) throws CommonBaseException
	{

		LogManager.push("--------------Enter UpdateTmasValidation------------------------------");
		 LogManager.logEnter();
		 String query;
		 query= " UPDATE TMAS_VALIDATION SET TYPE_ID = ?,DB_COLUMN_NAME = ?,EXCEL_HEADER_NAME = ?,VALIDATION_STATUS = ?,MANDATORY_STATUS = ?,FIELD_LENGTH = ?,DATA_TYPE = ?,DATA_FORMAT = ?,REFERENCE_STATUS = ?,REFERENCE_TABLE = ?,REFERENCE_COLUMN = ?,REFERENCE_CONDITION = ?,CHECK_VALUE = ?,CHECK_VALUE_COND = ?,ACTIVE = ?,MFR_ID = ?,XGEN_COLUMN = ?,XML_TAG_NAME = ? WHERE VALID_ID = ? " ;
			Object[] args = new Object[19];
			args[0]=beanObj.getTypeid();
			args[1]=beanObj.getDbColumnName();
			args[2]=beanObj.getExcelHeaderName();
			args[3]=beanObj.getValidationStatus();
			args[4]=beanObj.getMandatoryStatus();
			args[5]=beanObj.getFieldLength();
			args[6]=beanObj.getDataType();
			args[7]=beanObj.getDataFormat();
			args[8]=beanObj.getReferenceStatus();
			args[9]=beanObj.getReferenceTable();
			args[10]=beanObj.getReferenceColumn();
			args[11]=beanObj.getReferenceCondition();
			args[12]=beanObj.getCheckValue();
			args[13]=beanObj.getCheckValueCond();
			args[14]=beanObj.getActive();
			args[15]="0";
			args[16]=beanObj.getXgenColumn();
			args[17]=beanObj.getXmlTagName();
			args[18]=beanObj.getValidId();
			
			this.mytemplate.update(query, args);

			LogManager.logExit();
			LogManager.push("--------------------------Exit UpdateTmasValidation-------------------");
			LogManager.popRemove(); 
	}

}
