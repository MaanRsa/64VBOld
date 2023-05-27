package com.maan.document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.maan.common.LogManager;
import com.maan.common.Runner;
import com.maan.common.MotorBaseDAOImpl;
import com.maan.common.dbconnection.DBConnection;


public class SearchDaoImpl extends MotorBaseDAOImpl {
	
	private static final String EMPTY = "";	
	private final static String DCODE = "DEALERCODE";
	private final static String PIOLCODE="POLICYISSUINGOUTLETCODE";
	
	public String getMasTable(final String mfrId,final String policyTypeId)
	{
		String result = EMPTY;
		String query  = EMPTY;
		String[] args= new String[2];
		query = "select MASTER_TABLE from TMAS_POLICYTYPE where MFR_ID=? and POLICYTYPEID=?";
		args[0]=mfrId;
		args[1]=policyTypeId;
		result = Runner.singleSelection(query,args);
		return result;	
	}

	public String getRawMasTable(final String mfrId,final String policyTypeId)
	{
		String result = EMPTY;
		String query  = EMPTY;
		String[] args= new String[2];
		query = "select MASTER_TEMP_TABLE from TMAS_POLICYTYPE where MFR_ID=? and POLICYTYPEID=?";
		args[0]=mfrId;
		args[1]=policyTypeId;
		result = Runner.singleSelection(query,args);
		return result;	
	}
	
	
	
	
	
	public String[] getValues(final String firstValue,final String mfrId)
	{
		
		
		LogManager.push("INSIDE GET VALUES STARTS");
		
		String[] secondValue=firstValue.split(",");
		
		String[] result=new String[secondValue.length];
		
		for(int x=0;x<secondValue.length;x++){
			
			
			String[] third=secondValue[x].split("/");
			
				
				
				String value1=Runner.singleSelection("select EXCEL_HEADER_NAME from TMAS_VALIDATION where VALID_ID='"+third[0]+"' and MFR_ID='"+mfrId+"'");
				String value2=Runner.singleSelection("select ERROR_DESC from TMAS_ERROR_MASTER where ERROR_CODE='"+third[1]+"' and MFR_ID='"+mfrId+"'");
				
				String value3=value1+"-"+value2;
				
			
			result[x]=value3;
			
			
		}
		
		LogManager.push("INSIDE GET VALUES ENDS");
		
		
		
		
		
		for(int p=0;p<result.length;p++){
		
		
			LogManager.push("result["+p+"]==>"+result[p]);
			
		
		}
		
		
		
		
		
		
		
		return result;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String[][] getMotorPolDetail(final String mfrId,final String policyNumber,final String nrMasTable,final String enMasTable,final String startNumber,final String endNumber) {
		String[][] result = new String[0][0];
		String query = EMPTY;
		String nrDealerCode = "POLICYISSUINGDEALERCODE";
		String enDealerCode = DCODE;
		String endorsementNo = "ENDORSEMENTNO";
		
		if("102".equals(mfrId))
		{
			enDealerCode = DCODE;
			nrDealerCode = DCODE;
		}
		else if("103".equals(mfrId))	
		{
			enDealerCode = PIOLCODE; 
			nrDealerCode = PIOLCODE;
			endorsementNo="XGEN_ENDORSEMENT_NO";
		}
		//LogManager.push("mfrId==="+mfrId);
		String[] args= new String[4];
		query = "select * from( select tranDetails.*,rownum rnum1 from (" +
				"select POLICYNO,XGEN_POLICY_NUMBER,XGEN_PRODUCT_CODE,CLIENTCODE,"+enDealerCode+","+endorsementNo+" as ENDORSEMENTNO,XGEN_PRODUCT_ID from "+enMasTable+" where upper(POLICYNO)=upper(?) union " +
				"select POLICYNO,XGEN_POLICY_NUMBER,XGEN_PRODUCT_CODE,CLIENTCODE,"+nrDealerCode+",'000',XGEN_PRODUCT_ID from "+nrMasTable+" where upper(POLICYNO)=upper(?) order by ENDORSEMENTNO desc) tranDetails where rownum <= to_number(?)) where rnum1 >= to_number(?)";
		args[0]=policyNumber;
		args[1]=policyNumber;
		args[2]=endNumber;
		args[3]=startNumber;
		
		result = Runner.multipleSelection(query,args);
		//LogManager.push("result length"+result.length);
		return result;
	}
	
	public String[][] getXgenPolDetail(final String mfrId,final String policyNumber,final String nrMasTable,final String enMasTable,final String startNumber,final String endNumber) {
		String[][] result = new String[0][0];
		String query = EMPTY;
		String nrDealerCode = "POLICYISSUINGDEALERCODE";
		String enDealerCode = DCODE;
		String endorsementNo = "ENDORSEMENTNO";
		
		if("102".equals(mfrId))
		{
			enDealerCode = DCODE;
			nrDealerCode = DCODE;
		}
		else if("103".equals(mfrId))
		{
			enDealerCode = PIOLCODE; 
			nrDealerCode = PIOLCODE;
			endorsementNo="XGEN_ENDORSEMENT_NO";
		}
		String[] args= new String[4];
		query = "select * from( select tranDetails.*,rownum rnum1 from (" +
				"select POLICYNO,XGEN_POLICY_NUMBER,XGEN_PRODUCT_CODE,CLIENTCODE,"+enDealerCode+","+endorsementNo+" as ENDORSEMENTNO,XGEN_PRODUCT_ID from "+enMasTable+" where upper(XGEN_POLICY_NUMBER)=upper(?) union " +
				"select POLICYNO,XGEN_POLICY_NUMBER,XGEN_PRODUCT_CODE,CLIENTCODE,"+nrDealerCode+",'000',XGEN_PRODUCT_ID from "+nrMasTable+" where upper(XGEN_POLICY_NUMBER)=upper(?)) tranDetails where rownum <= to_number(?)) where rnum1 >= to_number(?)";
		args[0]=policyNumber;
		args[1]=policyNumber;
		args[2]=endNumber;
		args[3]=startNumber;
		result = Runner.multipleSelection(query,args);
		//LogManager.push("result length"+result.length);
		return result;
	}
	
	public String getMotorPolCount(final String mfrId,final String policyNumber,final String nrMasTable,final String enMasTable) {
		String result = EMPTY;
		String query = EMPTY;
		String[] args= new String[2];
		query = "select (select count(POLICYNO) from "+enMasTable+" where upper(POLICYNO)=upper(?))" +
				"+ (select count(POLICYNO) from "+nrMasTable+" where upper(POLICYNO)=upper(?)) from dual";
		args[0]=policyNumber;
		args[1]=policyNumber;
		result = Runner.singleSelection(query, args);
		//LogManager.push("result"+result);
		return result;
	}
	
	
	
	
	public ArrayList getTemValues(final String mfrId,final String policyNumber,final String nrMasTable,final String enMasTable) {
		/*
		String query = EMPTY;
		String[] args= new String[2];
		query = "select POLICYNO,VALIDATE_STATUS,'' from '"+nrMasTable+"' where upper(POLICYNO)=upper(?) union select POLICYNO,VALIDATE_STATUS,ENDORSEMENTNO from '"+enMasTable+"' where upper(POLICYNO)=upper(?)";
		args[0]=policyNumber;
		args[1]=policyNumber;
		String[][] result = Runner.multipleSelection(query, args);
		//LogManager.push("result"+result);
		return result;POLICYNO
		POLICYNO like '%"+ policyNumber.toLowerCase(Locale.ENGLISH) + "%'
		*/
		String query = "select POLICYNO,replace(SUBSTR(VALIDATE_STATUS,3),'*','/') as aa,'' as a from "+nrMasTable+" where upper(POLICYNO) like upper('%"+ policyNumber.toLowerCase(Locale.ENGLISH) + "%') and  VALIDATE_STATUS not like('%X%') union select POLICYNO,replace(SUBSTR(VALIDATE_STATUS,3),'*','/') as aa,ENDORSEMENTNO as a from "+enMasTable+" where upper(POLICYNO) like upper('%"+ policyNumber.toLowerCase(Locale.ENGLISH) + "%') and  VALIDATE_STATUS not like('%X%')";
		LogManager.push("query--------->"+query);
		ArrayList list=new ArrayList();
		String[][] finalResult = null;
		PreparedStatement pre = null;
		Connection con =null;
		ResultSet rSet = null;
		try
		{
			
			con = DBConnection.getInstance().getDBConnection();
			pre	= con.prepareStatement(query);
			//LogManager.push("multipleSelection...qry is..."+q);
			
			rSet=pre.executeQuery();
			
			while(rSet.next()){
				
				SearchBean bean=new SearchBean();
				bean.setPolicyNumber(rSet.getString("POLICYNO"));
				bean.setValidateStatus(rSet.getString("aa"));
				bean.setEndorsementNumber(rSet.getString("a"));
				
				list.add(bean);
				
				
			}
			
			
			
			
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			//finalResult = new String[0][0];
			//LogManager.push("Query is ..."+q);
			//LogManager.push("Error in Runner multipleSelection preparedstatement ..."+e.toString());
			//e.printStackTrace();
		} finally {
			if (rSet != null) {
				try {
					rSet.close();
				} catch (Exception e) {
				}
				rSet = null;
			}
			if (pre != null) {
				try {
					pre.close();
				} catch (Exception e) {
				}
				pre = null;
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
				}
				con = null;
			}
		}
		return list;
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	public String getXgenPolCount(final String mfrId,final String policyNumber,final String nrMasTable,final String enMasTable) {
		String result = EMPTY;
		String query = EMPTY;
		String[] args= new String[2];
		query = "select (select count(POLICYNO) from "+enMasTable+" where upper(XGEN_POLICY_NUMBER)=upper(?))" +
				"+ (select count(XGEN_POLICY_NUMBER) from "+nrMasTable+" where upper(XGEN_POLICY_NUMBER)=upper(?)) from dual";
		args[0]=policyNumber;
		args[1]=policyNumber;
		result = Runner.singleSelection(query, args);
		//LogManager.push("result"+result);
		return result;
	}
}