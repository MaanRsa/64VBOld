package com.maan.excelupload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.Runner;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;
import com.maan.common.exception.MotorBaseException;
import com.maan.transaction.TransactionVB;

public class UploadDAOImpl extends CommonBaseDAOImpl implements UploadDAO {
	String query = "";
	
	public UploadVB collectTransactionInfo(final int batchid,final boolean status)throws MotorBaseException {
		
		LogManager.push("CollectTransactionInfo() method-------------------Enter");
		List list;
		final List queries=new ArrayList();
		Map values;
		int typeid = 0, mfr_id = 0;
		final UploadVB uploadVB = new UploadVB();
		query = "SELECT A.MFR_ID as MFR_ID,A.TYPEID as TYPEID,A.TOTAL_NO_OF_RECORDS as TOTAL_NO_OF_RECORDS,"+
		"A.UPLOADED_COUNT as UPLOADED_COUNT,A.PENDING_COUNT as PENDING_COUNT,A.UPLOAD_STATUS as UPLOAD_STATUS,"
		+ "B.POLICYTYPE as POLICYTYPE,B.MASTER_TEMP_TABLE as MASTER_TEMP_TABLE,B.MASTER_TABLE as MASTER_TABLE," +
		"B.XGEN_STATUS AS XGEN_STATUS FROM TTRN_UPLOAD A,TMAS_POLICYTYPE B WHERE A.BATCHID="+ batchid
		+ " AND A.TYPEID=B.POLICYTYPEID AND A.MFR_ID=B.MFR_ID ORDER BY A.TYPEID ";
	
        //LogManager.push("This Is the Testing for masterMovemenet  ");
        LogManager.push("+++Query"+query);
		list = this.mytemplate.queryForList(query);
		if (!list.isEmpty()) {
			values = (Map) list.get(0);
			uploadVB.setMasterTempTbl((String) values.get("MASTER_TEMP_TABLE"));
		 	uploadVB.setMastterTbl((String) values.get("MASTER_TABLE"));
	 		uploadVB.setPolicyType((String) values.get("POLICYTYPE"));
			uploadVB.setXgenStatus((String) values.get("XGEN_STATUS"));
			typeid = Integer.parseInt(((java.math.BigDecimal) values.get("TYPEID")).toString());
     		uploadVB.setTypeID(typeid);
			mfr_id = Integer.parseInt(((java.math.BigDecimal) values.get("MFR_ID")).toString());
	    	uploadVB.setManufactureID(mfr_id);
			uploadVB.setTotalRecords(Integer.parseInt((java.math.BigDecimal) values	.get("TOTAL_NO_OF_RECORDS") == null ? "0": ((java.math.BigDecimal) values.get("TOTAL_NO_OF_RECORDS")).toString()));
 			uploadVB.setUploaded(Integer.parseInt((java.math.BigDecimal) values	.get("UPLOADED_COUNT") == null ? "0": ((java.math.BigDecimal) values.get("UPLOADED_COUNT")).toString()));
     		uploadVB.setPending(Integer.parseInt((java.math.BigDecimal) values.get("PENDING_COUNT") == null ? "0": ((java.math.BigDecimal) values.get("PENDING_COUNT"))	.toString()));
			uploadVB.setUploadTransts(values.get("UPLOAD_STATUS")==null?"":(String) values.get("UPLOAD_STATUS"));		 
		}

		if (status && uploadVB.getMasterTempTbl() != null&& uploadVB.getMastterTbl() != null) {
		query = "select VALID_ID,TYPE_ID,EXCEL_HEADER_NAME,DB_COLUMN_NAME,ACTIVE,DATA_TYPE,DATA_FORMAT,"
		+ "REFERENCE_STATUS,REFERENCE_TABLE,REFERENCE_COLUMN,MANDATORY_STATUS,XGEN_COLUMN from "
		+ "TMAS_VALIDATION  where type_id="+ typeid+ " and mfr_id=" + mfr_id + "  order by VALID_ID";
		LogManager.push("This Query Fir reference table>>>>"+query);
		list = this.mytemplate.queryForList(query);
		LogManager.push("list=="+list);
		if (!list.isEmpty()) {
			String dbcolumn, xcelcolumn, xgenDummyColumn = "", data_type, data_format;
			final StringBuffer masterqry=new StringBuffer(),tempqry=new StringBuffer();
			final String condition = "";
			String refStatus, refTable, refColumn, mandatorySts,xgenColumn;
			final String tables="" ,mastertemp = uploadVB.getMasterTempTbl();
			for (int j = 0; j < list.size(); j++) {
				values = (Map) list.get(j);
				final String active = (String) values.get("ACTIVE");
				if ("Y".equalsIgnoreCase(active)|| "X".equalsIgnoreCase(active) || "1".equalsIgnoreCase(active)) {
					dbcolumn = (String) values.get("DB_COLUMN_NAME");
					xcelcolumn = (String) values.get("EXCEL_HEADER_NAME");
					data_type = (String) values.get("DATA_TYPE") == null ? "": (String) values.get("DATA_TYPE");
					data_format = (String) values.get("DATA_FORMAT");
					refStatus = (String) values.get("REFERENCE_STATUS") == null ? "": (String) values.get("REFERENCE_STATUS");
					mandatorySts = (String) values.get("MANDATORY_STATUS") == null ? "": (String) values.get("MANDATORY_STATUS");
					masterqry.append(dbcolumn.trim());
					masterqry.append(',');
			
					if ("DATE".equalsIgnoreCase(data_type)) 
						tempqry.append( ("to_date(" + mastertemp + "."+ dbcolumn.trim() + ",'" + data_format + "')")+ ",");
					else if ("TIMESTAMP".equalsIgnoreCase(data_type)) 
						tempqry.append(("to_date(substr("+ mastertemp+ "."+ dbcolumn.trim()+ ",1,"+ (data_format.equalsIgnoreCase("DD/MON/YYYY") ? "11": "10") + "),'" + data_format + "')")+ ",");
					else if ("TIME".equalsIgnoreCase(data_type)) 
						tempqry.append( ("replace(" + mastertemp + "."+ dbcolumn.trim() + ",':','')")+ ",");
					else if (refStatus.equalsIgnoreCase("Y") && !mandatorySts.equalsIgnoreCase("P") && ((String) values.get("XGEN_COLUMN") != null))
					{
						refTable = (String) values.get("REFERENCE_TABLE");
						refColumn = (String) values.get("REFERENCE_COLUMN");
						xgenColumn = (String) values.get("XGEN_COLUMN");
						if (mfr_id == 102 && typeid == 101) {
							xgenDummyColumn = xgenColumn;
							xgenDummyColumn = xgenDummyColumn.replaceAll("TBL", "TTRN_TATA_ENDORSEMENT");
							xgenColumn = xgenColumn.replaceAll("TBL",uploadVB.getMastterTbl());
						} else {
							xgenColumn = xgenColumn.replaceAll("TBL",uploadVB.getMastterTbl());
						}
	                    StringBuffer Dquery=new StringBuffer();
						Dquery.append( "update " + uploadVB.getMastterTbl()+ " set "+uploadVB.getMastterTbl()+"."+ dbcolumn + " ");
					    Dquery.append( " = (select " + xgenColumn+ " from " + refTable + " where " + uploadVB.getMastterTbl() + "." + dbcolumn+ " ");
					    Dquery.append(" = " + refTable + "." + refColumn+ " and " + uploadVB.getMastterTbl()+ ".validate_status='Y'  ");
						Dquery.append( "and " + uploadVB.getMastterTbl() + "." + dbcolumn+ " is not null ");
						if(!dbcolumn.equalsIgnoreCase("POLICYNO"))
							Dquery.append( " and " + refTable+ ".mfr_id=" + uploadVB.getManufactureID()+ "");
						
						Dquery.append(  " and " + refTable + ".ACTIVE='1' and "+ uploadVB.getMastterTbl()+" ");
						Dquery.append(  ".xgen_moved_status is null) " + "where "+ uploadVB.getMastterTbl()+ ".validate_status='Y' and " );
						Dquery.append( "" + uploadVB.getMastterTbl() + "." + dbcolumn+ " is not null and "+ uploadVB.getMastterTbl()+ ".xgen_moved_status is null and " );
						Dquery.append( ""+ uploadVB.getMastterTbl() + ".active='1'" );
						LogManager.push("Printed by  ==>>>>>>>"+Dquery.toString());
						queries.add(Dquery.toString());
						LogManager.push("This is Query For Run Time"+Dquery.toString());
						if ("X".equalsIgnoreCase(active)) 
							tempqry.append("trim("+(mastertemp + "." + xcelcolumn.trim())+ "),");
						else 
							tempqry.append("trim("+(mastertemp + "." + dbcolumn.trim())+ "),");						
					} else if ("X".equalsIgnoreCase(active)) 
						tempqry.append("trim("+(mastertemp + "." + xcelcolumn.trim())+ "),");
					 else 
						tempqry.append(("trim("+mastertemp + "." + dbcolumn.trim())+ "),");
				}
			}
			uploadVB.setMainqry(masterqry.toString());
			uploadVB.setTempqry(tempqry.toString());
			uploadVB.setCondition(condition);
			uploadVB.setTables(tables);
			uploadVB.setQueries(queries);
			LogManager.push("CollectTransactionInfo() method status 1 masterqry"+ masterqry.length()+ "tempqry===>  "+ tempqry.length());
			LogManager.push("Master Query is  "+masterqry);
			LogManager.push("Temp Query is"+tempqry);
			LogManager.push("Query Size is "+queries.size() +" Query is "+queries);
		}
	}
	LogManager.push("CollectTransactionInfo() method");
	
	 // Should be the last statement
	LogManager.push("CollectTransactionInfo() method-------------------Exit");
	return uploadVB;
}
	
	public boolean recordNoUpdate(final String destable_name,final int productid,final int batchId) {
		LogManager.push("recordNoUpdate() method-entry"+destable_name);
		
		 
		this.mytemplate.execute("update " + destable_name
						+ " set recordno=rownum  where batchid="+ batchId
						+ " and xgen_productid=" + productid);
		
		LogManager.push("recordNoUpdate() method- Exit query is"+"update " + destable_name
				+ " set recordno=rownum  where batchid="+ batchId
				+ " and xgen_productid=" + productid);
		
		 // Should be the last statement
		return true;
	}

	public boolean recordnoBulkUpdate(List tableList) {
		LogManager.push("recordnoBulkUpdate() method-entry ");
		
		Map values;
		 
		for (int i = 0; i < tableList.size(); i++) {
			values = (Map) tableList.get(i);
			String destable_name = (String) values.get("TABLE_NAME");
			
			int productid = Integer.parseInt(((java.math.BigDecimal) values
					.get("PRODUCT_ID")).toString());
			LogManager.push("recordnoBulkUpdate() method- Enter Loop"+i);
			LogManager.push("destable_name is"+destable_name+"productid is"+productid);
			if (!(destable_name.equalsIgnoreCase("ttrn_policy_info"))) {
			query = "update "
					+destable_name
					+ " a set a.recordno=(select b.recordno from  ttrn_policy_info b where a.XGEN_POLICYNO=b.XGEN_POLICYNO and a.XGEN_ENDORSEMENTNO=b.XGEN_ENDORSEMENTNO and a.active='T' and a.active=b.active  and a.batchid=b.batchid and xgen_productid="+productid+") where a.active='T' and xgen_productid="+productid;
			LogManager.push("recordnoBulkUpdate() method- query " + query);
			this.mytemplate.execute(query);
			}

		}
		LogManager.push("recordnoBulkUpdate() method- Exit");
		
		 // Should be the last statement
		return true;
	}

	private void masterUpdateProcess(String tableName,String getFld1,String getFld2,String getCond1,String getCond2,String chkCond1,String chkCond2, String updateFld, String updateValue, String splCondFld, String validFldName, String validFldValue1, String validFldValue2) {
		String selQry = "", updateQry = "", chequeNo = "", chequeAmt = "", status = "";
		updateQry = "update " + tableName  + " set " + updateFld + " = '" + updateValue + "' where (" + getFld1 + "||" + getFld2 + ") in (select (" + getFld1 + "||" + getFld2 + ") from " + tableName + " where " + getCond1 + " and " + getCond2 + " group by " + getFld1 + "," + getFld2 + " having count(*) > 1)";
		System.out.println("updateQry " + updateQry);
		this.mytemplate.update(updateQry);
		
		selQry = "select " + getFld1 + "," + getFld2 + " from " + tableName + " where " + updateFld + " = '" + updateValue + "' and " + getCond1;
		System.out.println("selQry " + selQry);
		List list = this.mytemplate.queryForList(selQry);
		System.out.println("list size " + list.size());
		try {
			if(list.size() > 0) {
				for(int i =0; i < list.size(); i++) {
					Map<String,Object> map = (Map)list.get(i);
					chequeNo = map.get(getFld1.toUpperCase()).toString();
					chequeAmt = map.get(getFld2.toUpperCase()).toString();
					selQry= "select unique (case when((SELECT SUM (" + getFld2 + ") FROM " + tableName + " WHERE " + validFldName + " = '" + validFldValue1 + "' AND " +
					chkCond1 + " = '"+chequeNo+"' and " + chkCond2 + "="+chequeAmt+" and " + getCond1 + ") - " +
						"(SELECT SUM (" + getFld2 + ") FROM " + tableName + " WHERE " + getCond1 + " and " + validFldName + " = '" + validFldValue2 + "' " +
						"AND " + chkCond1 + " = '"+chequeNo+"' and " + chkCond2 + "="+chequeAmt+"))>0 then 'C' else 'D' end) " +
						"as status from " + tableName + " where " +getCond1 + " and " + chkCond1 + "='"+chequeNo+
						"' and " + chkCond2 + " = "+chequeAmt;
					System.out.println("selQry " + selQry);
					Map resMap = this.mytemplate.queryForMap(selQry);
					if(resMap != null && resMap.size()>0 ) {
						status = resMap.get("STATUS").toString();
					}
					updateQry = "update " + tableName + " set " + updateFld + " = '" + status + "' where " + getCond1 + " and " + chkCond1 + "='"+chequeNo+"' and " + chkCond2 + "="+chequeAmt+" and " + splCondFld + "=(select max(" + splCondFld + ") from " + tableName + " where " + getCond1 + " and " + chkCond1 + " ='"+chequeNo+"' and " + chkCond2 + "="+chequeAmt+")";
					System.out.println("updateQry " + updateQry);
					this.mytemplate.update(updateQry);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String moveToMaster(final String MasterQuery, final String MastertableName,	final String temp, final String TempTable,final int batchid,final int typeid,final String xgenStatus,final int mfr_id, final String condition,final String tables,	final List Queries)  {
		LogManager.push("moveToMaster() method-Enter");
		LogManager.push("This Is typeid"+typeid+"and Mfr_id"+mfr_id);
		String status = "";
		String TempQuery = new String(temp);
		String dataQry = TempQuery;
		try {
			if(TempTable.equalsIgnoreCase("temp_citi_bank"))
			dataQry=TempQuery.replaceAll("temp_citi_bank.CHEQUE_AMT", "ABS(temp_citi_bank.CHEQUE_AMT)");
			else if(TempTable.equalsIgnoreCase("temp_scb_bank"))
				dataQry=TempQuery.replaceAll("temp_scb_bank.CHQ_AMOUNT", "ABS(temp_scb_bank.CHQ_AMOUNT)");
			else
				dataQry=TempQuery.replaceAll(TempTable+".INSTRUMENT_AMOUNT", "ABS("+TempTable+".INSTRUMENT_AMOUNT)");
			LogManager.push(">>>>>>>>"+dataQry);
			
			TempQuery = dataQry;
			if (MastertableName != null && TempTable != null) {
				this.mytemplate	.execute("update TTRN_UPLOAD set upload_status='P' where batchid="+ batchid);
				LogManager.push("Enter Into Master Query Move Statge");
				if(typeid==101)
				{
					//receipt date checking for date format
					String qry= "SELECT (CASE when substr(receipt_date,instr(receipt_date,'/',1,1)+1,instr(receipt_date,'/',1,2)-instr(receipt_date,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_receipt_master where validate_status='Y' and batchid=?";
					String cols[]= new String [1];
					cols[0]=Integer.toString(batchid);
					String[][] val=Runner.multipleSelection(qry, cols);
					String resultreceiptdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultreceiptdate="Y";
							break;
						}
					}
				
					if(resultreceiptdate.equalsIgnoreCase("Y"))
					{   
						status="- Receipt date";
						//TempQuery =	TempQuery.replaceAll( "RECEIPT_DATE,'DD/MM/YYYY'","RECEIPT_DATE,'MM/DD/YYYY'");
					}
					
				//cheque date checking
				qry= "SELECT (CASE when substr(CHEQUE_DATE,instr(CHEQUE_DATE,'/',1,1)+1,instr(CHEQUE_DATE,'/',1,2)-instr(CHEQUE_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_receipt_master where validate_status='Y' and batchid=?";
				//String cols[]= new String [1];
				cols[0]=Integer.toString(batchid);
				 val=Runner.multipleSelection(qry, cols);
				String resultchequedate="N";
				for(int i=0;i<val.length;i++)
				{
					if(val[i][0].equalsIgnoreCase("Y"))
					{
						resultchequedate="Y";
						break;
					}
				}
				if(resultchequedate.equalsIgnoreCase("Y"))
				{
					status="- Cheque date ";
					//TempQuery = TempQuery.replaceAll( "CHEQUE_DATE,'DD/MM/YYYY'","CHEQUE_DATE,'MM/DD/YYYY'");
				}
				
				//due_date checking date format
				qry= "SELECT (CASE when substr(DUE_DATE,instr(DUE_DATE,'/',1,1)+1,instr(DUE_DATE,'/',1,2)-instr(DUE_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_receipt_master where validate_status='Y' and batchid=?";
				//String cols[]= new String [1];
				cols[0]=Integer.toString(batchid);
				 val=Runner.multipleSelection(qry, cols);
				String resultduedate="N";
				for(int i=0;i<val.length;i++)
				{
					if(val[i][0].equalsIgnoreCase("Y"))
					{
						resultduedate="Y";
						break;
					}
				}
				if(resultduedate.equalsIgnoreCase("Y"))
				{
					status="-Due Date ";
					//TempQuery = TempQuery.replaceAll( "DUE_DATE,'DD/MM/YYYY'","DUE_DATE,'MM/DD/YYYY'");
				}
				// CREDIT_CARD_EXPIRY date check
				qry= "SELECT (CASE when substr(CREDIT_CARD_EXPIRY,instr(CREDIT_CARD_EXPIRY,'/',1,1)+1,instr(CREDIT_CARD_EXPIRY,'/',1,2)-instr(CREDIT_CARD_EXPIRY,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_receipt_master where validate_status='Y' and batchid=?";
				
				cols[0]=Integer.toString(batchid);
				 val=Runner.multipleSelection(qry, cols);
				String resultcreditcardexpirydate="N";
				for(int i=0;i<val.length;i++)
				{
					if(val[i][0].equalsIgnoreCase("Y"))
					{
						resultcreditcardexpirydate="Y";
						break;
					}
				}
				if(resultcreditcardexpirydate.equalsIgnoreCase("Y"))
				{
					status="-Credit Card Expiry ";
					//TempQuery = TempQuery.replaceAll( "CREDIT_CARD_EXPIRY,'DD/MM/YYYY'","CREDIT_CARD_EXPIRY,'MM/DD/YYYY'");
				}
				
				//ends checking date format flexibility
				query = "insert into " +MastertableName + "( RECEIPT_SL_NO , " + MasterQuery + ") " +
							" (select RECEIPTSEQ.nextval," + TempQuery+ " from " + TempTable + tables + " where "	
									+ TempTable + ".validate_status='Y' and batchid="+batchid+" and " + TempTable + ".active='1'" + condition +  " )";
							LogManager.push("This is REceipt"+query);
					try {
						this.mytemplate.execute(query);
					} 
					catch (Exception e) {
						String error = e.toString(); 
						if(error.contains("not a valid month")) {
							status="Invalid date Format"+status;
							LogManager.push("Exception in date format");
						} else if(error.contains("invalid") || error.contains("invalid number")){
							status="Invalid number ";
							LogManager.push("Exception in number");
						} else
							status = e.getMessage();
						e.printStackTrace();
					}
				}
				else if (typeid==102)
				{
					// checking deposit date
					String qry= "SELECT (CASE when substr(DEPOSIT_DATE,instr(DEPOSIT_DATE,'/',1,1)+1,instr(DEPOSIT_DATE,'/',1,2)-instr(DEPOSIT_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_citi_bank where validate_status='Y' and batchid=?";
					String cols[]= new String [1];
					cols[0]=Integer.toString(batchid);
					String[][] val=Runner.multipleSelection(qry, cols);
					String resultdepositdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultdepositdate="Y";
							break;
						}
					}
					
					if(resultdepositdate.equalsIgnoreCase("Y"))
					{   
						status="-deposit date ";
						//TempQuery =	TempQuery.replaceAll( "DEPOSIT_DATE,'DD/MM/YYYY'","DEPOSIT_DATE,'MM/DD/YYYY'");
					} 
					
					// checking CREDIT_DEBIT_DATE
					qry= "SELECT (CASE when substr(CREDIT_DEBIT_DATE,instr(CREDIT_DEBIT_DATE,'/',1,1)+1,instr(CREDIT_DEBIT_DATE,'/',1,2)-instr(CREDIT_DEBIT_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_citi_bank where validate_status='Y' and batchid=?";
					
					cols[0]=Integer.toString(batchid);
					val=Runner.multipleSelection(qry, cols);
					String resultcreditdebitdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultcreditdebitdate="Y";
							break;
						}
					}
					
					if(resultcreditdebitdate.equalsIgnoreCase("Y"))
					{   
						status="-Credit/Debit date ";
						//TempQuery =	TempQuery.replaceAll( "CREDIT_DEBIT_DATE,'MM/DD/YYYY'","CREDIT_DEBIT_DATE,'DD/MM/YYYY'");
					}
					
					// 
			       // checking CHECK_DATA
					qry= "SELECT (CASE when substr(CHECK_DATA,instr(CHECK_DATA,'/',1,1)+1,instr(CHECK_DATA,'/',1,2)-instr(CHECK_DATA,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_citi_bank where validate_status='Y' and  batchid=?";
				   
					cols[0]=Integer.toString(batchid);
					val=Runner.multipleSelection(qry, cols);
					String resultcheckdata="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultcheckdata="Y";
							break;
						}
					} 
					
					if(resultcheckdata.equalsIgnoreCase("Y"))
					{   
						status="-Check data ";
						//TempQuery =	TempQuery.replaceAll( "CHECK_DATA,'DD/MM/YYYY'","CHECK_DATA,'MM/DD/YYYY'");
					}
					
					
					query = "insert into " +MastertableName + "( BANK_NO , " + MasterQuery + ") " +
					" (select CITIBANKSEQ.nextval," + TempQuery+ " from " + TempTable + tables + " where "	
					+ TempTable + ".validate_status='Y' and batchid="+batchid+" and " + TempTable + ".active='1'" + condition +  " )";
				LogManager.push("This is CITI bank"+query);
				LogManager.push("This Is Citit Bank:------>"+query);
				try {
					this.mytemplate.execute(query);
				} 
				catch (Exception e) {
						// TODO Auto-generated catch block
						String error = e.toString(); 
						if(error.contains("not a valid month")){
							status="Invalid date Format "+status;
							LogManager.push("Exception in date format");
						} else if(error.contains("invalid") || error.contains("invalid number")){
							status="Invalid number ";
							LogManager.push("Exception in number");
						} else 
							status = e.getMessage();
						e.printStackTrace();
				}
				masterUpdateProcess(MastertableName, "cheque_no", "cheque_amt","batchid="+batchid,"type_crdr ='D'","cheque_no","cheque_amt", "status", "D", "bank_no","type_crdr","C","D");
				
				/*//Credit  Debit status for Citi
			    String querysel="select  cheque_no, cheque_amt from citi_bank where batchid="+batchid+" and cheque_no in ( select  cheque_no from citi_bank where batchid="+batchid+" and type_crdr ='D' ) and cheque_amt in ( select  cheque_amt from citi_bank where batchid="+batchid+"  and type_crdr ='D') group by cheque_no,cheque_amt having count(*)>1 ";
                LogManager.push("query to sel repeated cheques citi:"+querysel);
			    List list = this.mytemplate.query(querysel, new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString(1)+"_"+rset.getString(2);
					}
						
				});
			    if(list.size()>0){
				LogManager.push("list size of cheque nos repeated"+list.size());
				LogManager.push("Cheque no repeated citi"+list.get(0));
				String update="";
				String chqdetail[]=new String [2];
				
				for(int i=0;i<list.size();i++)
				{
				    chqdetail= list.get(i).toString().split("_");	
					LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
					update="update citi_bank set status='D' where cheque_no='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+" and batchid="+batchid;
					LogManager.push("Update for repeated cheque's status citi:"+ update);
					this.mytemplate.update(update);
					update="select unique (case when((SELECT SUM (CHEQUE_AMT) FROM   CITI_BANK WHERE   type_crdr = 'C'   AND cheque_no = '"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+" and batchid="+batchid+")  - (SELECT   SUM (CHEQUE_AMT) FROM   CITI_BANK  WHERE   batchid="+batchid+" and type_crdr = 'D' AND cheque_no = '"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from citi_bank where batchid="+batchid+" and cheque_no='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1];
       				LogManager.push("update for status:"+update);
					Object result=this.mytemplate.queryForObject(update,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString("status");
					}} );
					LogManager.push("result.toString()"+result.toString());
					if(result.toString().equalsIgnoreCase("C"))
					{
					update="update citi_bank set status='C' where batchid="+batchid+" and cheque_no='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+" and bank_no=(select max(bank_no) from citi_bank where batchid="+batchid+" and cheque_no ='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+")";
					LogManager.push("Update for max cheque's status citi:"+ update);
					this.mytemplate.update(update);
					}
				}
			    }*/	
				}
				else if(typeid==105)
				{
					LogManager.push("SCB File Upload------------>Name UploadDAOIMP"+MastertableName);
					//SELECT (CASE when substr(DEPOSIT_DATE,instr(DEPOSIT_DATE,'/',1,1)+1,instr(DEPOSIT_DATE,'/',1,2)-instr(DEPOSIT_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_citi_bank where validate_status='Y' and batchid=?
					//CREDIT_DEBIT_DATE
					//String qry=" SELECT (CASE when substr(CREDIT_DEBIT_DT,instr(CREDIT_DEBIT_DT,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					String qry= "SELECT (CASE when substr(CREDIT_DEBIT_DT,instr(CREDIT_DEBIT_DT,'/',1,1)+1,instr(CREDIT_DEBIT_DT,'/',1,2)-instr(CREDIT_DEBIT_DT,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					String cols[]=new String[1];
					cols[0]=Integer.toString(batchid);
					String[][] val=Runner.multipleSelection(qry,cols);
					String resultcreditdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultcreditdate="Y";
							break;
						}
					}
					
					if(resultcreditdate.equalsIgnoreCase("Y"))
					{   
						status="-Credit/Debit date ";
						//TempQuery =	TempQuery.replaceAll( "CREDIT_DEBIT_DT,'DD/MM/YYYY'","CREDIT_DEBIT_DT,'MM/DD/YYYY'");
					}
					
					//PICKUPDT
					//qry=" SELECT (CASE when substr(PICKUPDT,instr(PICKUPDT,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					qry= "SELECT (CASE when substr(PICKUPDT,instr(PICKUPDT,'/',1,1)+1,instr(PICKUPDT,'/',1,2)-instr(PICKUPDT,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					cols[0]=Integer.toString(batchid);
					val=Runner.multipleSelection(qry,cols);
					String resultpickupdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultpickupdate="Y";
							break;
						}
					}
					
					if(resultpickupdate.equalsIgnoreCase("Y"))
					{   
						status="-Pickup date ";
						//TempQuery =	TempQuery.replaceAll( "PICKUPDT,'DD/MM/YYYY'","PICKUPDT,'MM/DD/YYYY'");
					}
					
					//DEP_DATE
					//qry=" SELECT (CASE when substr(DEP_DATE,instr(DEP_DATE,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					qry= "SELECT (CASE when substr(DEPOSIT_DATE,instr(DEPOSIT_DATE,'/',1,1)+1,instr(DEPOSIT_DATE,'/',1,2)-instr(DEPOSIT_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					cols[0]=Integer.toString(batchid);
					val=Runner.multipleSelection(qry,cols);
					String resultdepdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultdepdate="Y";
							break;
						}
					}
					
					if(resultdepdate.equalsIgnoreCase("Y"))
					{   
						status="-Deposit date ";
						//TempQuery =	TempQuery.replaceAll( "DEP_DATE,'DD/MM/YYYY'","DEP_DATE,'MM/DD/YYYY'");
					}
					
					//CHEQUE_DT
					//qry=" SELECT (CASE when substr(CHEQUE_DT,instr(CHEQUE_DT,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					qry= "SELECT (CASE when substr(CHEQUE_DT,instr(CHEQUE_DT,'/',1,1)+1,instr(CHEQUE_DT,'/',1,2)-instr(CHEQUE_DT,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					cols[0]=Integer.toString(batchid);
					val=Runner.multipleSelection(qry,cols);
					String resultchequedate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultchequedate="Y";
							break;
						}
					}
					
					if(resultchequedate.equalsIgnoreCase("Y"))
					{   
						status="-Cheque date ";
						//TempQuery =	TempQuery.replaceAll( "CHEQUE_DT,'DD/MM/YYYY'","CHEQUE_DT,'MM/DD/YYYY'");
					}
					LogManager.push("Master Table For SCB BANK");
					query="insert into " +MastertableName + "( BANK_NO , " + MasterQuery + ") " +
					" (select SCBBANKSEQ.nextval," + TempQuery+ " from " + TempTable + tables + " where "
					+ TempTable + ".validate_status='Y' and batchid="+batchid+" and " + TempTable + ".active='1'" + condition +  " )";
					LogManager.push("This is SCB bank"+query);
					try {
						this.mytemplate.execute(query);
					} 
					catch (Exception e) {
					String error = e.toString(); 
					if(error.contains("not a valid month")){
						status="Invalid date Format "+status;
						LogManager.push("Exception in date format");
					} else if(error.contains("invalid") || error.contains("invalid number")){
						status="Invalid number ";
						LogManager.push("Exception in number");
					} else 
						status = e.getMessage();
						e.printStackTrace();
					}
					masterUpdateProcess(MastertableName, "cheque_no", "chq_amount","batchid="+batchid,"cr_dr ='D'","cheque_no","chq_amount", "status", "D", "bank_no","cr_dr","C","D");
					
					/*//Credit  Debit status for SCB
				    String querysel="select  CHEQUE_NO, CHQ_AMOUNT from "+MastertableName+" where batchid="+batchid+" and cheque_no in ( select  cheque_no from  "+MastertableName+" where batchid="+batchid+" and cr_dr ='D' ) and CHQ_AMOUNT in ( select  CHQ_AMOUNT from  "+MastertableName+" where batchid="+batchid+"  and cr_dr ='D') group by cheque_no,CHQ_AMOUNT having count(*)>1 ";
	                LogManager.push("query to sel repeated cheques scb:"+querysel);
				    List list = this.mytemplate.query(querysel, new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							return rset.getString(1)+"_"+rset.getString(2);
						}
							
					});
				    LogManager.push("List size"+list.size());
				    if(list.size()>0){
					LogManager.push("list size of cheque nos repeated"+list.size());
					LogManager.push("Cheque no repeated SCB"+list.get(0));
					String update="";
					String chqdetail[]=new String [2];
					
					for(int i=0;i<list.size();i++)
					{
					    chqdetail= list.get(i).toString().split("_");	
						LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
						update="update  "+MastertableName+" set status='D' where cheque_no='"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+" and batchid="+batchid;
						LogManager.push("Update for repeated cheque's status SCB:"+ update);
						this.mytemplate.update(update);
						update="select unique (case when((SELECT SUM (CHQ_AMOUNT) FROM   "+MastertableName+" WHERE   cr_dr = 'C'   AND cheque_no = '"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+" and batchid="+batchid+")  - (SELECT   SUM (CHQ_AMOUNT) FROM   "+MastertableName+"  WHERE   batchid="+batchid+" and cr_dr = 'D' AND cheque_no = '"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from "+MastertableName+" where batchid="+batchid+" and cheque_no='"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1];
	       				LogManager.push("update for status:"+update);
						Object result=this.mytemplate.queryForObject(update,new RowMapper() {
							public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							return rset.getString("status");
							}} );
						LogManager.push("result.toString()"+result.toString());
						if(result.toString().equalsIgnoreCase("C"))
						{
						update="update "+MastertableName+" set status='C' where batchid="+batchid+" and cheque_no='"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+" and bank_no=(select max(bank_no) from "+MastertableName+" where batchid="+batchid+" and cheque_no ='"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+")";
						LogManager.push("Update for max cheque's status SCB:"+ update);
						this.mytemplate.update(update);
						}
					}
				    }*/
				}
				else if(typeid==106)
				{
					LogManager.push("ENTER AXIS BANK File Upload------------>"+MastertableName);
					//DEPOSIT_DATE
					//String qry=" SELECT (CASE when substr(CREDIT_DEBIT_DT,instr(CREDIT_DEBIT_DT,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					String qry= "SELECT (CASE when substr(DEPOSIT_DATE,instr(DEPOSIT_DATE,'/',1,1)+1,instr(DEPOSIT_DATE,'/',1,2)-instr(DEPOSIT_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_AXIS_BANK where validate_status='Y' and batchid=?";
					String cols[]=new String[1];
					cols[0]=Integer.toString(batchid);
					String[][] val=Runner.multipleSelection(qry,cols);
					String resultdepositdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultdepositdate="Y";
							break;
						}
					}
					LogManager.push("resultdepositdate::"+resultdepositdate);
					
					if(resultdepositdate.equalsIgnoreCase("Y"))
					{   
						status="- Deposit date ";
						//TempQuery =	TempQuery.replaceAll( "DEPOSIT_DATE,'DD/MM/YYYY'","DEPOSIT_DATE,'MM/DD/YYYY'");
					}
					
					//CR_DATE
					//qry=" SELECT (CASE when substr(PICKUPDT,instr(PICKUPDT,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					qry= "SELECT (CASE when substr(CR_DATE,instr(CR_DATE,'/',1,1)+1,instr(CR_DATE,'/',1,2)-instr(CR_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_AXIS_BANK where validate_status='Y' and batchid=?";
					cols[0]=Integer.toString(batchid);
					val=Runner.multipleSelection(qry,cols);
					String resultcrdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultcrdate="Y";
							break;
						}
					}
					LogManager.push("resultcrdate::"+resultcrdate);
					
					if(resultcrdate.equalsIgnoreCase("Y"))
					{   
						status="- CR date ";
						//TempQuery =	TempQuery.replaceAll( "CR_DATE,'DD/MM/YYYY'","CR_DATE,'MM/DD/YYYY'");
					}
					
					//RTN_DATE
					//qry=" SELECT (CASE when substr(DEP_DATE,instr(DEP_DATE,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					qry= "SELECT (CASE when substr(RTN_DATE,instr(RTN_DATE,'/',1,1)+1,instr(RTN_DATE,'/',1,2)-instr(RTN_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_AXIS_BANK where validate_status='Y' and batchid=?";
					cols[0]=Integer.toString(batchid);
					val=Runner.multipleSelection(qry,cols);
					String resultrtndate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultrtndate="Y";
							break;
						}
					}
					
					if(resultrtndate.equalsIgnoreCase("Y"))
					{   
						status="- Rtn date ";
						//TempQuery =	TempQuery.replaceAll( "RTN_DATE,'DD/MM/YYYY'","RTN_DATE,'MM/DD/YYYY'");
					}
					
					//INST_DATE
					//qry=" SELECT (CASE when substr(CHEQUE_DT,instr(CHEQUE_DT,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_SCB_BANK where validate_status='Y' and batchid=?";
					qry= "SELECT (CASE when substr(INST_DATE,instr(INST_DATE,'/',1,1)+1,instr(INST_DATE,'/',1,2)-instr(INST_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM TEMP_AXIS_BANK where validate_status='Y' and batchid=?";
					cols[0]=Integer.toString(batchid);
					val=Runner.multipleSelection(qry,cols);
					String resultinstdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultinstdate="Y";
							break;
						}
					}
					
					if(resultinstdate.equalsIgnoreCase("Y"))
					{   
						status="- Inst date ";
						//TempQuery =	TempQuery.replaceAll("INST_DATE,'DD/MM/YYYY'","INST_DATE,'MM/DD/YYYY'");
					}
					LogManager.push("Master Table For AXIS BANK");
					query="insert into " +MastertableName + "( BANK_NO , " + MasterQuery + ") " +
					" (select AXISBANKSEQ.nextval," + TempQuery+ " from " + TempTable + tables + " where "
					+ TempTable + ".validate_status='Y' and batchid="+batchid+" and " + TempTable + ".active='1'" + condition +  ")";
					LogManager.push("This is AXIS bank:::::"+query);
					try {
							this.mytemplate.execute(query);
						} 
					catch (Exception e) {
						String error = e.toString(); 
						if(error.contains("not a valid month")){
							status="Invalid date Format "+status;
							LogManager.push("Exception in date format");
						} else if(error.contains("invalid") || error.contains("invalid number")){
							status="Invalid number ";
							LogManager.push("Exception in number");
						} else
							status = e.getMessage();
						e.printStackTrace();
						}
					masterUpdateProcess(MastertableName, "inst_no", "instrument_amount","batchid="+batchid,"type ='RTN'","inst_no","instrument_amount", "status", "RTN", "bank_no","type","COL","RTN");
					
					/*//Credit  Debit status for axis
				    String querysel="select  INST_NO, INSTRUMENT_AMOUNT from "+MastertableName+" where batchid="+batchid+" and INST_NO in ( select  INST_NO from  "+MastertableName+" where batchid="+batchid+" and type ='RTN' ) and INSTRUMENT_AMOUNT in ( select  INSTRUMENT_AMOUNT from  "+MastertableName+" where batchid="+batchid+"  and type ='RTN') group by INST_NO,INSTRUMENT_AMOUNT having count(*)>1 ";
	                LogManager.push("query to sel repeated cheques AXIS:"+querysel);
				    List list = this.mytemplate.query(querysel, new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							return rset.getString(1)+"_"+rset.getString(2);
						}
							
					});
				    LogManager.push("List size"+list.size());
				    if(list.size()>0){
					LogManager.push("list size of cheque nos repeated"+list.size());
					LogManager.push("Cheque no repeated AXIS"+list.get(0));
					String update="";
					String chqdetail[]=new String [2];
					
					for(int i=0;i<list.size();i++)
					{
					    chqdetail= list.get(i).toString().split("_");	
						LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
						update="update  "+MastertableName+" set status='D' where INST_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid;
						LogManager.push("Update for repeated cheque's status AXIS:"+ update);
						this.mytemplate.update(update);
						update="select unique (case when((SELECT SUM (INSTRUMENT_AMOUNT) FROM   "+MastertableName+" WHERE   type = 'COL'   AND INST_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid+")  - (SELECT   SUM (INSTRUMENT_AMOUNT) FROM   "+MastertableName+"  WHERE   batchid="+batchid+" and type = 'RTN' AND INST_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from "+MastertableName+" where batchid="+batchid+" and INST_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1];
	       				LogManager.push("update for status:"+update);
						Object result=this.mytemplate.queryForObject(update,new RowMapper() {
							public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							return rset.getString("status");
							}} );
						LogManager.push("result.toString()"+result.toString());
						if(result.toString().equalsIgnoreCase("C"))
						{
						update="update "+MastertableName+" set status='C' where batchid="+batchid+" and INST_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and bank_no=(select max(bank_no) from "+MastertableName+" where batchid="+batchid+" and INST_NO ='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+")";
						LogManager.push("Update for max cheque's status AXIS:"+ update);
						this.mytemplate.update(update);
						}
					}
				    }*/
				}
				else if (typeid==103)
				{
			      //VAL_DT checking for date format
					String qry= "SELECT (CASE when substr(VAL_DT,instr(VAL_DT,'/',1,1)+1,instr(VAL_DT,'/',1,2)-instr(VAL_DT,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hdfc_bank where validate_status='Y' and batchid=?";
					
					//String qry= "SELECT (CASE when  substr(VAL_DT,1,instr(VAL_DT,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hdfc_bank where validate_status='Y' and batchid=?";
					String cols[]= new String [1];
					cols[0]=Integer.toString(batchid);
					String[][] val=Runner.multipleSelection(qry, cols);
					String resultvaldate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultvaldate="Y";
							break;
						}
					}
					LogManager.push("resultvaldate::"+resultvaldate);
					
					if(resultvaldate.equalsIgnoreCase("Y"))
					{   
						status="- Val date ";
						//TempQuery =	TempQuery.replaceAll( "VAL_DT,'MM/DD/YYYY'","VAL_DT,'DD/MM/YYYY'");
					}
					//checking POST_DT
					qry= "SELECT (CASE when substr(POST_DT,instr(POST_DT,'/',1,1)+1,instr(POST_DT,'/',1,2)-instr(POST_DT,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hdfc_bank where validate_status='Y' and batchid=?";
					
					//qry= "SELECT (CASE when substr(POST_DT,1,instr(POST_DT,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hdfc_bank where validate_status='Y' and batchid=?";
				 
					cols[0]=Integer.toString(batchid);
					 val=Runner.multipleSelection(qry, cols);
					String resultpostdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultpostdate="Y";
							break;
						}
					}
					
					if(resultpostdate.equalsIgnoreCase("Y"))
					{   
						status="- Post date ";
						//TempQuery =	TempQuery.replaceAll( "POST_DT,'MM/DD/YYYY'","POST_DT,'DD/MM/YYYY'");
					}
					
			        //checking PKUP_DT
					qry= "SELECT (CASE when substr(PKUP_DT,instr(PKUP_DT,'/',1,1)+1,instr(PKUP_DT,'/',1,2)-instr(PKUP_DT,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hdfc_bank where validate_status='Y' and batchid=?";
					
					cols[0]=Integer.toString(batchid);
					 val=Runner.multipleSelection(qry, cols);
					String resultpkupdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultpkupdate="Y";
							break;
						}
					}
					
					if(resultpkupdate.equalsIgnoreCase("Y"))
					{   
						status="- Pkup date ";
						//TempQuery =	TempQuery.replaceAll( "PKUP_DT,'MM/DD/YYYY'","PKUP_DT,'DD/MM/YYYY'");
					}
					
					//DEPOSIT_DATE
					qry= "SELECT (CASE when substr(DEPOSIT_DATE,instr(DEPOSIT_DATE,'/',1,1)+1,instr(DEPOSIT_DATE,'/',1,2)-instr(DEPOSIT_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hdfc_bank where validate_status='Y' and batchid=?";
					 
					cols[0]=Integer.toString(batchid);
					 val=Runner.multipleSelection(qry, cols);
					String resultdepositdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultdepositdate="Y";
							break;
						}
					}
					
					if(resultdepositdate.equalsIgnoreCase("Y"))
					{   
						status="- Deposit date ";
						//TempQuery =	TempQuery.replaceAll( "DEPOSIT_DATE,'MM/DD/YYYY'","DEPOSIT_DATE,'DD/MM/YYYY'");
					}
					
					// INST_DT checking
					qry= "SELECT (CASE when substr(INST_DT,instr(INST_DT,'/',1,1)+1,instr(INST_DT,'/',1,2)-instr(INST_DT,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hdfc_bank where validate_status='Y' and batchid=?";
					 
					cols[0]=Integer.toString(batchid);
					 val=Runner.multipleSelection(qry, cols);
					String resultinstdate="N";
					for(int i=0;i<val.length;i++)
					{
						if(val[i][0].equalsIgnoreCase("Y"))
						{
							resultinstdate="Y";
							break;
						}
					}
					
					if(resultinstdate.equalsIgnoreCase("Y"))
					{   
						status="- Inst date ";
						//TempQuery =	TempQuery.replaceAll( "INST_DT,'MM/DD/YYYY'","INST_DT,'DD/MM/YYYY'");
					}
					
					query = "insert into " +MastertableName + "( BANK_NO , " + MasterQuery + ") " +
					" (select HDFCBANKSEQ.nextval," + TempQuery+ " from " + TempTable + tables + " where "	
					+ TempTable + ".validate_status='Y' and batchid="+batchid+" and " + TempTable + ".active='1'" + condition +  " )";
					LogManager.push("This is HDFC "+query);
					
					try {
						this.mytemplate.execute(query);
					} 
					catch (Exception e) {
						String error = e.toString(); 
						if(error.contains("not a valid month")){
							status="Invalid date Format "+status;
							LogManager.push("Exception in date format");
						} else if(error.contains("invalid") || error.contains("invalid number")){
							status="Invalid number ";
							LogManager.push("Exception in number");
						} else
							status = e.getMessage();
						e.printStackTrace();
					}
					
					masterUpdateProcess(MastertableName, "instrument_no", "instrument_amount","batchid="+batchid,"dr_cr ='D'","instrument_no","instrument_amount", "status", "D", "bank_no","dr_cr","C","D");
					
				    /*String querysel="select  INSTRUMENT_NO,INSTRUMENT_AMOUNT from hdfc_bank where batchid="+batchid+" and INSTRUMENT_NO in ( select  INSTRUMENT_NO from hdfc_bank where batchid="+batchid+"  and dr_cr ='D' ) and INSTRUMENT_AMOUNT in (select  INSTRUMENT_AMOUNT from hdfc_bank where batchid="+batchid+" and dr_cr ='D') group by INSTRUMENT_NO,INSTRUMENT_AMOUNT having count(*)>1 "; 
	                LogManager.push("query to sel repeated cheques hdfc:"+querysel);
				    List list = this.mytemplate.query(querysel, new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							return rset.getString(1)+"_"+rset.getString(2);
						}
							
					});
				    String chqdetail[]=new String [2];
				    if(list.size()>0){
				
							
					LogManager.push("list size of cheque nos repeated"+list.size());
					LogManager.push("Cheque no repeated"+list.get(0));
					String update="";
					for(int i=0;i<list.size();i++)
					{
					    chqdetail= list.get(i).toString().split("_");	
						LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
						update="update hdfc_bank set status='D' where INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid;
						LogManager.push("Update for repeated cheque's status hdfc_bank:"+ update);
						this.mytemplate.update(update);
						update="select unique (case when((SELECT SUM (INSTRUMENT_AMOUNT) FROM   hdfc_bank WHERE   DR_CR = 'C'   AND INSTRUMENT_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid+")  - (SELECT   SUM (INSTRUMENT_AMOUNT) FROM   hdfc_bank  WHERE   batchid="+batchid+" and DR_CR = 'D' AND INSTRUMENT_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from hdfc_bank where batchid="+batchid+" and INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1];
	       				LogManager.push("update for status:"+update);
						Object result=this.mytemplate.queryForObject(update,new RowMapper() {
							public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							return rset.getString("status");
							}} );
						LogManager.push("result.toString()"+result.toString());
						if(result.toString().equalsIgnoreCase("C"))
						{
						update="update hdfc_bank set status='C' where batchid="+batchid+" and INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and bank_no=(select max(bank_no) from hdfc_bank where batchid="+batchid+" and INSTRUMENT_NO ='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+")";
						LogManager.push("Update for max cheque's status citi:"+ update);
						this.mytemplate.update(update);
						}
					}
				    }*/
				}
				else if(typeid==108) {
					//MONTH checking for date format
					String qry= "SELECT (CASE when substr(MONTH,instr(MONTH,'/',1,1)+1,instr(MONTH,'/',1,2)-instr(MONTH,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hsbc_bank where validate_status='Y' and batchid=?";
					//String qry= "SELECT (CASE when  substr(MONTH,1,instr(MONTH,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hsbc_bank where validate_status='Y' and batchid=?";
					String monthcols[]= new String [1];
					monthcols[0]=Integer.toString(batchid);
					String[][] monthval=Runner.multipleSelection(qry, monthcols);
					String resultMonth="N";
					for(int i=0;i<monthval.length;i++)
					{
						if(monthval[i][0].equalsIgnoreCase("Y"))
						{
							resultMonth="Y";
							break;
						}
					}
					LogManager.push("resultMonth::"+resultMonth);
					if(resultMonth.equalsIgnoreCase("Y"))
					{   
						status="- MONTH date ";
						//TempQuery =	TempQuery.replaceAll( "MONTH,'MM/DD/YYYY'","MONTH,'DD/MM/YYYY'");
					}
				      //DATE_OF_ENTRY checking for date format
						qry= "SELECT (CASE when substr(DATE_OF_ENTRY,instr(DATE_OF_ENTRY,'/',1,1)+1,instr(DATE_OF_ENTRY,'/',1,2)-instr(DATE_OF_ENTRY,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hsbc_bank where validate_status='Y' and batchid=?";
						
						//String qry= "SELECT (CASE when  substr(DATE_OF_ENTRY,1,instr(DATE_OF_ENTRY,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hsbc_bank where validate_status='Y' and batchid=?";
						String cols[]= new String [1];
						cols[0]=Integer.toString(batchid);
						String[][] val=Runner.multipleSelection(qry, cols);
						String resultvaldate="N";
						for(int i=0;i<val.length;i++)
						{
							if(val[i][0].equalsIgnoreCase("Y"))
							{
								resultvaldate="Y";
								break;
							}
						}
						LogManager.push("resultvaldate::"+resultvaldate);
						
						if(resultvaldate.equalsIgnoreCase("Y"))
						{   
							status="- Date of Entry date ";
							//TempQuery =	TempQuery.replaceAll( "DATE_OF_ENTRY,'MM/DD/YYYY'","DATE_OF_ENTRY,'DD/MM/YYYY'");
						}
						
						//checking POST_DATE
						qry= "SELECT (CASE when substr(POST_DATE,instr(POST_DATE,'/',1,1)+1,instr(POST_DATE,'/',1,2)-instr(POST_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hsbc_bank where validate_status='Y' and batchid=?";
						
						//qry= "SELECT (CASE when substr(POST_DATE,1,instr(POST_DATE,'/',1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hsbc_bank where validate_status='Y' and batchid=?";
					 
						cols[0]=Integer.toString(batchid);
						 val=Runner.multipleSelection(qry, cols);
						String resultpostdate="N";
						for(int i=0;i<val.length;i++)
						{
							if(val[i][0].equalsIgnoreCase("Y"))
							{
								resultpostdate="Y";
								break;
							}
						}
						if(resultpostdate.equalsIgnoreCase("Y"))
						{   
							status="- Post date ";
							//TempQuery =	TempQuery.replaceAll( "POST_DATE,'MM/DD/YYYY'","POST_DATE,'DD/MM/YYYY'");
						}
						
				        //checking PICKUP_DATE
						qry= "SELECT (CASE when substr(PICKUP_DATE,instr(PICKUP_DATE,'/',1,1)+1,instr(PICKUP_DATE,'/',1,2)-instr(PICKUP_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hsbc_bank where validate_status='Y' and batchid=?";
						
						cols[0]=Integer.toString(batchid);
						 val=Runner.multipleSelection(qry, cols);
						String resultpkupdate="N";
						for(int i=0;i<val.length;i++)
						{
							if(val[i][0].equalsIgnoreCase("Y"))
							{
								resultpkupdate="Y";
								break;
							}
						}
						
						if(resultpkupdate.equalsIgnoreCase("Y"))
						{   
							status="- Pickup date ";
							//TempQuery =	TempQuery.replaceAll( "PICKUP_DATE,'MM/DD/YYYY'","PICKUP_DATE,'DD/MM/YYYY'");
						}
						
						//DEPOSIT_DATE
						qry= "SELECT (CASE when substr(DEPOSIT_DATE,instr(DEPOSIT_DATE,'/',1,1)+1,instr(DEPOSIT_DATE,'/',1,2)-instr(DEPOSIT_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hsbc_bank where validate_status='Y' and batchid=?";
						 
						cols[0]=Integer.toString(batchid);
						 val=Runner.multipleSelection(qry, cols);
						String resultdepositdate="N";
						for(int i=0;i<val.length;i++)
						{
							if(val[i][0].equalsIgnoreCase("Y"))
							{
								resultdepositdate="Y";
								break;
							}
						}
						
						if(resultdepositdate.equalsIgnoreCase("Y"))
						{   
							status="- Deposit date ";
							//TempQuery =	TempQuery.replaceAll( "DEPOSIT_DATE,'MM/DD/YYYY'","DEPOSIT_DATE,'DD/MM/YYYY'");
						}
						
						// INSTRUMENT_DATE checking
						qry= "SELECT (CASE when substr(INSTRUMENT_DATE,instr(INSTRUMENT_DATE,'/',1,1)+1,instr(INSTRUMENT_DATE,'/',1,2)-instr(INSTRUMENT_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END) FROM temp_hsbc_bank where validate_status='Y' and batchid=?";
						 
						cols[0]=Integer.toString(batchid);
						 val=Runner.multipleSelection(qry, cols);
						String resultinstdate="N";
						for(int i=0;i<val.length;i++)
						{
							if(val[i][0].equalsIgnoreCase("Y"))
							{
								resultinstdate="Y";
								break;
							}
						}
						
						if(resultinstdate.equalsIgnoreCase("Y"))
						{   
							status="- Instrument date ";
							//TempQuery =	TempQuery.replaceAll( "INSTRUMENT_DATE,'MM/DD/YYYY'","INSTRUMENT_DATE,'DD/MM/YYYY'");
						}
						
						query = "insert into " +MastertableName + "( BANK_NO , " + MasterQuery + ") " +
						" (select hsbcBANKSEQ.nextval," + TempQuery+ " from " + TempTable + tables + " where "	
						+ TempTable + ".validate_status='Y' and batchid="+batchid+" and " + TempTable + ".active='1'" + condition +  " )";
						LogManager.push("This is hsbc "+query);
						
						try {
							this.mytemplate.execute(query);
						} 
						catch (Exception e) {
							String error = e.toString(); 
							if(error.contains("not a valid month")){
								status="Invalid date Format "+status;
								LogManager.push("Exception in date format");
							} else if(error.contains("invalid") || error.contains("invalid number")){
								status="Invalid number ";
								LogManager.push("Exception in number");
							} else
								status = e.getMessage();
							e.printStackTrace();
						}
						masterUpdateProcess(MastertableName, "instrument_no", "instrument_amount","batchid="+batchid,"debit_credit ='D'","instrument_no","instrument_amount", "status", "D", "bank_no","debit_credit","C","D");
					    
						/*String querysel="select  INSTRUMENT_NO,INSTRUMENT_AMOUNT from hsbc_bank where batchid="+batchid+" and INSTRUMENT_NO in ( select  INSTRUMENT_NO from hsbc_bank where batchid="+batchid+"  and DEBIT_CREDIT ='D' ) and INSTRUMENT_AMOUNT in (select  INSTRUMENT_AMOUNT from hsbc_bank where batchid="+batchid+" and DEBIT_CREDIT ='D') group by INSTRUMENT_NO,INSTRUMENT_AMOUNT having count(*)>1 "; 
		                LogManager.push("query to sel repeated cheques hsbc:"+querysel);
					    List list = this.mytemplate.query(querysel, new RowMapper() {
							public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
								return rset.getString(1)+"_"+rset.getString(2);
							}
								
						});
					    String chqdetail[]=new String [2];
					    if(list.size()>0){
					
								
						LogManager.push("list size of cheque nos repeated"+list.size());
						LogManager.push("Cheque no repeated"+list.get(0));
						String update="";
						for(int i=0;i<list.size();i++)
						{
						    chqdetail= list.get(i).toString().split("_");	
							LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
							update="update hsbc_bank set status='D' where INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid;
							LogManager.push("Update for repeated cheque's status hsbc_bank:"+ update);
							this.mytemplate.update(update);
							update="select unique (case when((SELECT SUM (INSTRUMENT_AMOUNT) FROM   hsbc_bank WHERE   DEBIT_CREDIT = 'C'   AND INSTRUMENT_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid+")  - (SELECT   SUM (INSTRUMENT_AMOUNT) FROM   hsbc_bank  WHERE   batchid="+batchid+" and DEBIT_CREDIT = 'D' AND INSTRUMENT_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from hsbc_bank where batchid="+batchid+" and INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1];
		       				LogManager.push("update for status:"+update);
							Object result=this.mytemplate.queryForObject(update,new RowMapper() {
								public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
								return rset.getString("status");
								}} );
							LogManager.push("result.toString()"+result.toString());
							if(result.toString().equalsIgnoreCase("C"))
							{
							update="update hsbc_bank set status='C' where batchid="+batchid+" and INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and bank_no=(select max(bank_no) from hsbc_bank where batchid="+batchid+" and INSTRUMENT_NO ='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+")";
							LogManager.push("Update for max cheque's status citi:"+ update);
							this.mytemplate.update(update);
							}
						}
					}*/
				}
				else if(typeid==115) {

					//VAL_DT checking for date format
					String qry= "SELECT (CASE WHEN SUBSTR(VAL_DT,INSTR(VAL_DT,'/',1,1)+1,INSTR(VAL_DT,'/',1,2)-INSTR(VAL_DT,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END)VAL_DT FROM TEMP_KOTAK_BANK WHERE VALIDATE_STATUS='Y' AND BATCHID=?";
					
					Object cols[]= new Object[1];
					cols[0]=Integer.toString(batchid);
					String resultvaldate="N";
					
					List<Map<String,Object>> val = this.mytemplate.queryForList(qry,cols);
					for(int i=0 ; i<val.size() ; i++) {
						Map<String,Object> tempMap = (Map<String,Object>) val.get(i);
						String valTemp = tempMap.get("VAL_DT")==null?"":tempMap.get("VAL_DT").toString();
						if("Y".equalsIgnoreCase(valTemp)) {
							resultvaldate="Y";
							break;
						}
					}
					
					LogManager.info("resultvaldate::"+resultvaldate);
					
					if(resultvaldate.equalsIgnoreCase("Y"))
					{   
						status="- Val date ";
					}
					//checking POST_DT
					qry= "SELECT (CASE WHEN SUBSTR(POST_DT,INSTR(POST_DT,'/',1,1)+1,INSTR(POST_DT,'/',1,2)-INSTR(POST_DT,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END)POST_DT FROM TEMP_KOTAK_BANK WHERE VALIDATE_STATUS='Y' AND BATCHID=?";
				 
					cols[0]=Integer.toString(batchid);
					String resultpostdate="N";
					
					val = this.mytemplate.queryForList(qry,cols);
					for(int i=0 ; i<val.size() ; i++) {
						Map<String,Object> tempMap = (Map<String,Object>) val.get(i);
						String valTemp = tempMap.get("POST_DT")==null?"":tempMap.get("POST_DT").toString();
						if("Y".equalsIgnoreCase(valTemp)) {
							resultvaldate="Y";
							break;
						}
					}
					
					if(resultpostdate.equalsIgnoreCase("Y"))
					{   
						status="- Post date ";
					}
					
			        //checking PKUP_DT
					qry= "SELECT (CASE WHEN SUBSTR(PKUP_DT,INSTR(PKUP_DT,'/',1,1)+1,INSTR(PKUP_DT,'/',1,2)-INSTR(PKUP_DT,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END)PKUP_DT FROM TEMP_KOTAK_BANK WHERE VALIDATE_STATUS='Y' AND BATCHID=?";
					
					cols[0]=Integer.toString(batchid);
					String resultpkupdate="N";
					
					val = this.mytemplate.queryForList(qry,cols);
					for(int i=0 ; i<val.size() ; i++) {
						Map<String,Object> tempMap = (Map<String,Object>) val.get(i);
						String valTemp = tempMap.get("PKUP_DT")==null?"":tempMap.get("PKUP_DT").toString();
						if("Y".equalsIgnoreCase(valTemp)) {
							resultpkupdate="Y";
							break;
						}
					}
					
					if(resultpkupdate.equalsIgnoreCase("Y"))
					{   
						status="- Pkup date ";
					}
					
					//DEPOSIT_DATE
					qry= "SELECT (CASE WHEN SUBSTR(DEPOSIT_DATE,INSTR(DEPOSIT_DATE,'/',1,1)+1,INSTR(DEPOSIT_DATE,'/',1,2)-INSTR(DEPOSIT_DATE,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END)DEPOSIT_DATE FROM TEMP_KOTAK_BANK WHERE VALIDATE_STATUS='Y' AND BATCHID=?";
					 
					cols[0]=Integer.toString(batchid);
					String resultdepositdate="N";
					
					val = this.mytemplate.queryForList(qry,cols);
					for(int i=0 ; i<val.size() ; i++) {
						Map<String,Object> tempMap = (Map<String,Object>) val.get(i);
						String valTemp = tempMap.get("DEPOSIT_DATE")==null?"":tempMap.get("DEPOSIT_DATE").toString();
						if("Y".equalsIgnoreCase(valTemp)) {
							resultdepositdate="Y";
							break;
						}
					}
					
					if(resultdepositdate.equalsIgnoreCase("Y"))
					{   
						status="- Deposit date ";
					}
					
					// INST_DT checking
					qry= "SELECT (CASE WHEN SUBSTR(INST_DT,INSTR(INST_DT,'/',1,1)+1,INSTR(INST_DT,'/',1,2)-INSTR(INST_DT,'/',1,1)-1)>12 THEN 'Y' ELSE 'N' END)INST_DT FROM TEMP_KOTAK_BANK WHERE VALIDATE_STATUS='Y' AND BATCHID=?";
					 
					cols[0]=Integer.toString(batchid);
					String resultinstdate="N";
					
					val = this.mytemplate.queryForList(qry,cols);
					for(int i=0 ; i<val.size() ; i++) {
						Map<String,Object> tempMap = (Map<String,Object>) val.get(i);
						String valTemp = tempMap.get("INST_DT")==null?"":tempMap.get("INST_DT").toString();
						if("Y".equalsIgnoreCase(valTemp)) {
							resultdepositdate="Y";
							break;
						}
					}
					
					if(resultinstdate.equalsIgnoreCase("Y"))
					{   
						status="- Inst date ";
					}
					
					query = "insert into " +MastertableName + "( BANK_NO , " + MasterQuery + ") " +
					" (select KOTAKBANKSEQ.nextval," + TempQuery+ " from " + TempTable + tables + " where "	
					+ TempTable + ".validate_status='Y' and batchid="+batchid+" and " + TempTable + ".active='1'" + condition +  " )";
					LogManager.info("This is KOTAK "+query);
					
					try {
						this.mytemplate.execute(query);
					} 
					catch (Exception e) {
						String error = e.toString(); 
						if(error.contains("not a valid month")){
							status="Invalid date Format "+status;
							LogManager.info("Exception in date format");
						} 
						else if(error.contains("invalid") || error.contains("invalid number")){
							status="Invalid number ";
							LogManager.info("Exception in number");
						} 
						else {
							status = e.getMessage();
						}
						e.printStackTrace();
					}
					
					masterUpdateProcess(MastertableName, "instrument_no", "instrument_amount","batchid="+batchid,"DEBIT_CREDIT ='D'","instrument_no","instrument_amount", "status", "D", "bank_no","dr_cr","C","D");
				
				}
				else if (typeid==104)
				{
					query = "insert into " +MastertableName + "( "+ MasterQuery + ") " +
					" (select " + TempQuery+ " from " + TempTable + tables + " where "	
					+ TempTable + ".validate_status='Y' and batchid="+batchid+" and " + TempTable + ".active='1'" + condition +  " )";
					LogManager.push("This is receipt nos"+query);
					try {
						this.mytemplate.execute(query);
					} 
					catch (Exception e) {
					String error = e.toString(); 
					if(error.contains("not a valid month")){
						status="Invalid date Format "+status;
						LogManager.push("Exception in date format");
					} else if(error.contains("invalid") || error.contains("invalid number")){
						status="Invalid number ";
						LogManager.push("Exception in number");
					} else
						status = e.getMessage();
					e.printStackTrace();
					}		
				}
				else if (typeid==107)
				{
					query = "insert into " +MastertableName + "( "+ MasterQuery + ") " +
					" (select " + TempQuery+ " from " + TempTable + tables + " where "	
					+ TempTable + ".validate_status='Y' and batchid="+batchid+" and " + TempTable + ".active='1'" + condition +  " )";
					LogManager.push("This is receipt reversals"+query);
					try {
						this.mytemplate.execute(query);
					} 
					catch (Exception e) {
						e.printStackTrace();
					}		
				}
				else if(typeid==109){
					
					query = "insert into " +MastertableName + "( "+ MasterQuery + ") " +
					" (select " + TempQuery+ " from " + TempTable + tables + " where "	
					+ TempTable + ".validate_status='Y' and batchid="+batchid+" and " + TempTable + ".active='1'" + condition +  " )";
					LogManager.push("This is receipt reversals"+query);
					try {
						this.mytemplate.execute(query);
					    this.mytemplate.update("insert into transaction_details(TRANSACTION_NO,FILE_TYPE,TOTAL_RECORDS,DUPLICATES,TRANSACTION_DATE) values(?,'PN',(select count(*) from temp_policy_numbers where batchid=?),(select count(*) from temp_policy_numbers where batchid=? and validate_status='D'),sysdate)",new Object[]{batchid,batchid,batchid});
				     } 
					catch (Exception e) {
						e.printStackTrace();
					}	
				}
				String qry = "delete from  " + TempTable + " where VALIDATE_STATUS='Y' and batchid = " + batchid;
				this.mytemplate.execute(qry);
				System.out.println("delete after the master movement " + qry);		
				
			}
			LogManager.push("moveToMaster() method- xgenStatus" + xgenStatus
					+ "status" + status);
				this.mytemplate
						.execute("update TTRN_UPLOAD set upload_status='C' where batchid="
								+ batchid + " and upload_status!='E'");
			LogManager.push("moveToMaster() method- Exit");
			
			 // Should be the last statement
		} catch (Exception e) {
			e.printStackTrace();
		}
    LogManager.push("------------------Should be the last statement-----------------");
		return status;
	}
	
	
	public String collectPolicyStatus(final int type_id,final int mfr_id,final String TempTable) {
		LogManager.push("collectPolicyStatus() method-entry");
		
		query = "select COLUMN_NAME,COLUMN_VALUE from TMAS_POLICY_STATUS where type_id="+type_id+" and mfr_id="+mfr_id+" and active='1'";
		Map values;
		final StringBuffer retValue=new StringBuffer(16);
		final List list = this.mytemplate.queryForList(query);
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				values = (Map) list.get(i);
				//retValue.append(" and "+TempTable+"."+((String) values.get("COLUMN_NAME"))+"='"+((String) values.get("COLUMN_VALUE"))+"'");
				retValue.append(" and "+((String) values.get("COLUMN_NAME"))+"='"+((String) values.get("COLUMN_VALUE"))+"'");
				
			
			}
		}
		LogManager.push("collectPolicyStatus() method- Exit");
		
		 // Should be the last statement
		return retValue.toString();
	}

	
	public boolean cancellationUpdate(final String MastertableName, final int mfr_id,
			final int batchid) {
		this.mytemplate
				.execute("update TTRN_NEWRENEWAL a set a.policy_status='C' where a.policyno in"
						+ "(select distinct b.policyno from "
						+ MastertableName
						+ " b where a.policyno=b.policyno "
						+ "and b.active=1) and a.active='1' and a.policy_status is null");

		this.mytemplate
				.execute("update TTRN_ENDORSEMENT a set a.policy_status='C' where a.policyno in"
						+ "(select distinct b.policyno from "
						+ MastertableName
						+ " b where a.policyno=b.policyno "
						+ "and b.active=1) and a.active='1' and a.policy_status is null");

		query = "update " + MastertableName + " set mfr_id=" + mfr_id
				+ " where batchid=" + batchid;
		this.mytemplate.execute(query);

		return true;
	}
	
	public String[][] pendingBatchIds(final String nrRawMasTable, final String enRawMasTable) {

		LogManager.push("pendingBatchIds method()");
		String result[][] = new String[0][0];
		String[] args = new String[2];
		try{
			query = "select batchid from "+nrRawMasTable+" where active=? union select batchid from "+enRawMasTable+" where active=?";
			args[0]="1";
			args[1]="1";
			result = Runner.multipleSelection(query,args);
		}
		catch(Exception e){
			LogManager.debug(e);
			e.printStackTrace();
		}
		
		return result;
	}
	
	public String[][] pendingCount(final String nrRawMasTable, final String enRawMasTable) {

		LogManager.push("pendingBatchIds method()");
		String result[][] = new String[0][0];
		String[] args = new String[2];
		try{
			query = "select (select count(batchid) from "+nrRawMasTable+" where active=?)+(select count(batchid) from "+enRawMasTable+" where active=?) from dual";
			args[0]="1";
			args[1]="1";
			result = Runner.multipleSelection(query,args);
		}
		catch(Exception e){
			LogManager.debug(e);
			e.printStackTrace();
		}
		
		return result;
	}
	
// added by sundaram
	public String writeFiles(final String pathh,final String valuee) {
		final String path = pathh;
		final String value = valuee;
		boolean sts = false;
		LogManager.push("path is" + path);
		LogManager.push("values is" + value);
		final File file = new File(path);
		try {
			final boolean exist = file.createNewFile();
			if (!exist) {
				final FileOutputStream fop = new FileOutputStream(file);
				fop.write(value.getBytes());
				fop.flush();
				fop.close();
				LogManager.push("The data has been written");
				LogManager.push("File created successfully.");
				sts = true;
			} else {
				LogManager.push("File already exists.");
				final FileOutputStream fop = new FileOutputStream(file);
				fop.write(value.getBytes());
				fop.flush();
				fop.close();
				LogManager.push("The data has been written");
				LogManager.push("File created successfully.");
				sts = true;
			}
		} catch (FileNotFoundException e) {
			LogManager.push("WriteFiles"+e.getMessage());
		} catch (IOException e) {
			LogManager.push("WriteFiles"+e.getMessage());
		}
		if (sts)
			return "success"; 
		else
			return "failure"; 
	}

	public void deleteTempRecords(int batchid, String type) throws MotorBaseException {
		String table="";
		if(type.equalsIgnoreCase("101"))
			table = "TEMP_RECEIPT_MASTER";	
		else if (type.equalsIgnoreCase("102"))
			table = "TEMP_CITI_BANK";
		else if (type.equalsIgnoreCase("103"))
			table = "TEMP_HDFC_BANK";
		else if (type.equalsIgnoreCase("104"))
			table = "TEMP_RECEIPT_NUMBERS";
		else if(type.equalsIgnoreCase("105"))
			table = "TEMP_SCB_BANK";
		else if(type.equalsIgnoreCase("106"))
			table = "TEMP_AXIS_BANK";
		else if(type.equalsIgnoreCase("107"))
			table = "TEMP_RECEIPT_REVERSAL";
		else if(type.equalsIgnoreCase("108"))
			table = "TEMP_HSBC_BANK";
		else if (type.equalsIgnoreCase("115"))
			table = "TEMP_KOTAK_BANK";
		else if(type.equalsIgnoreCase("109"))
			table = "TEMP_POLICY_NUMBERS";
		
		LogManager.push("current TAble is "+table);
		
		query="DELETE FROM "+table+" WHERE BATCHID="+batchid;
		LogManager.push(query);
		this.mytemplate.execute(query);
		
		query="DELETE FROM TRANSACTION_DETAILS WHERE TOTAL_RECORDS=0 and INVALID=0";
		LogManager.push("End Of The Delete Query " + query);
		this.mytemplate.execute(query);
	}

	public void updateBankRecords(UploadVB uploadVBMaster) throws MotorBaseException, CommonBaseException {
		String query2="";
		int typeId = uploadVBMaster.getTypeID();
		int batchid = uploadVBMaster.getBatchID();
		LogManager.push("Type id inside update banks::"+typeId);
		if(typeId==101)
		{
			LogManager.push("Inside Transaction details RECEIPT ");
		final String query1 = getQuery(DBConstants.TRANSACTION_DETAILS);
		final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_ERRORCOUNT)	;
		final String query4=getQuery(DBConstants.TRANSACTION_DETAILS_PAYMENTCOUNT)	;
				
		List list2 = new ArrayList();
		Object[] arg1;
		arg1 = new Object[1];
		arg1[0] = batchid;
		int count=this.mytemplate.queryForInt(query1,arg1);
		
		int ecount=this.mytemplate.queryForInt(query3, arg1);
		
		int paymentcount=this.mytemplate.queryForInt(query4, arg1);
		//int ecount=this.mytemplate.queryForInt(query3);
		list2.add(count+ecount+paymentcount);
		list2.add(ecount);
		list2.add(count);
		list2.add(paymentcount);
		//Setting status c for cheque no not exists
		String query6 = getQuery(DBConstants.RECEIPT_MASTER_UPDATE);
		this.mytemplate.update(query6);	
		
		//deleting temp records
		
		Object[] arg;
		arg = new Object[7];
		arg[0] = batchid;
		arg[1] = "R";
		arg[2] = count+ecount;
		arg[3] = ecount;
		arg[4] = batchid;
		arg[5] = batchid;
		arg[6] = batchid;
		
		//insertion of RECEIPT transaction
		String query7 = getQuery(DBConstants.TRANSACTION_INSERT3);
		this.mytemplate.update(query7,arg);
		
	}
		else if(typeId==105)
		{
			LogManager.push("Inside Transaction details SCB BANK");
			try{
			final String query1 = getQuery(DBConstants.TRANSACTION_DETAILS4);
			Object[] arg1;
			arg1 = new Object[1];
			arg1[0] = batchid;
			LogManager.push("====================>1");
			final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_ERRORCOUNT4);
			
			List list = new ArrayList();
			int count=this.mytemplate.queryForInt(query1, arg1);
			int ecount=this.mytemplate.queryForInt(query3, arg1);
			list.add(count+ecount);
			list.add(ecount);
			list.add(count);
			
			Object[] arg;
			arg = new Object[7];
			arg[0] = batchid;
			arg[1] = "SCB";
			arg[2] = count+ecount;
			arg[3] = ecount;
			arg[4] = batchid;
			arg[5] = batchid;
			arg[6] = batchid;
			//Setting status c for cheque no not exists
			LogManager.push("====================>2");
			String query6 = getQuery(DBConstants.SCB_MASTER_UPDATE);
			LogManager.push("====================>2");
			LogManager.push("-=============>query6"+query6);
			this.mytemplate.update(query6);
			LogManager.push("----------------->query6"+query6);
			
			//insertion of scb bank transaction
			LogManager.push("====================>3");
			String query7 = getQuery(DBConstants.TRANSACTION_INSERT4);
			LogManager.push("-=============>query7"+query7);
			this.mytemplate.update(query7,arg);
			LogManager.push("----------------->query7"+query7);
			
			//temp record delete
			LogManager.push("====================>4");
			String query5 = getQuery(DBConstants.SCB_MASTER_DELETE);
			LogManager.push("-=============>query5"+query5);
			this.mytemplate.update(query5);
			LogManager.push("--------------->query5"+query5);
			LogManager.push("UploadDAOImpl gettransactiondetailciti() Method - Exit"+list);
			
		
				}
				catch(Exception e){
					LogManager.debug(e);
					throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
				}
			
				LogManager.push("====================>EXIT========================>");
		}
		
		else if(typeId==106)
		{
			LogManager.push("Inside Transaction details AXIS BANK");
			try{
			final String query1 = getQuery(DBConstants.TRANSACTION_DETAILS5);
			Object[] arg1;
			arg1 = new Object[1];
			arg1[0] = batchid;
			LogManager.push("====================>1");
			final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_ERRORCOUNT5);
			
			List list = new ArrayList();
			int count=this.mytemplate.queryForInt(query1, arg1);
			int ecount=this.mytemplate.queryForInt(query3, arg1);
			list.add(count+ecount);
			list.add(ecount);
			list.add(count);
			
			Object[] arg;
			arg = new Object[7];
			arg[0] = batchid;
			arg[1] = "AXB";
			arg[2] = count+ecount;
			arg[3] = ecount;
			arg[4] = batchid;
			arg[5] = batchid;
			arg[6] = batchid;
			//Setting status c for cheque no not exists
			LogManager.push("====================>2");
			String query6 = getQuery(DBConstants.AXIS_MASTER_UPDATE);
			
			this.mytemplate.update(query6);
			LogManager.push("----------------->query6"+query6);
			
			//insertion of scb bank transaction
			LogManager.push("====================>3");
			String query7 = getQuery(DBConstants.TRANSACTION_INSERT5);
			
			this.mytemplate.update(query7,arg);
			LogManager.push("----------------->query7"+query7);
			
			//temp record delete
			LogManager.push("====================>4");
			String query5 = getQuery(DBConstants.AXIS_MASTER_DELETE);
			
			this.mytemplate.update(query5);
			LogManager.push("--------------->query5"+query5);
			LogManager.push("UploadDAOImpl gettransactiondetailciti() Method - Exit"+list);
			
		
				}
				catch(Exception e){
					LogManager.debug(e);
					throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
				}
			
				LogManager.push("====================>EXIT AXIS========================>");
		}
		else if(typeId==102)
		{
		LogManager.push("Inside Transaction details CITI BANK");
		try{
		final String query1 = getQuery(DBConstants.TRANSACTION_DETAILS2);
		Object[] arg1;
		arg1 = new Object[1];
		arg1[0] = batchid;
		
		final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_ERRORCOUNT2)	;
		List list = new ArrayList();
		int count=this.mytemplate.queryForInt(query1, arg1);
		int ecount=this.mytemplate.queryForInt(query3, arg1);
		list.add(count+ecount);
		list.add(ecount);
		list.add(count);
		
		Object[] arg;
		arg = new Object[7];
		arg[0] = batchid;
		arg[1] = "CIT";
		arg[2] = count+ecount;
		arg[3] = ecount;
		arg[4] = batchid;
		arg[5] = batchid;
		arg[6] = batchid;
		
		//Setting status c for cheque no not exists
		String query6 = getQuery(DBConstants.CITI_MASTER_UPDATE);
		this.mytemplate.update(query6);	
		
		//insertion of citi bank transaction
		String query7 = getQuery(DBConstants.TRANSACTION_INSERT1);
		int transactionCount= this.mytemplate.update(query7,arg);
		LogManager.push("Transaction count "+transactionCount);
		LogManager.push(arg[0]+"\t"+arg[1]+"\t"+arg[2]+"\t"+arg[3]+"\t"+arg[4]+"\t"+arg[5]+"\t"+arg[6]);
		//temp record delete
		String query5 = getQuery(DBConstants.CITI_MASTER_DELETE);
		this.mytemplate.update(query5);
		LogManager.push("UploadDAOImpl gettransactiondetailciti() Method - Exit"+list);
		
	
			}
			catch(Exception e){
				/*LogManager.debug(e);
				throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);*/
				e.printStackTrace();
			}
		
		}
		else if(typeId==103)
			{
			LogManager.push("Inside Transaction details HDFC BANK");
			try{
				final String query1 = getQuery(DBConstants.TRANSACTION_DETAILS3);
				final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_ERRORCOUNT3)	;
				
				List list = new ArrayList();
				
				Object[] arg1;
				arg1 = new Object[1];
				arg1[0] = batchid;
				int count1=this.mytemplate.queryForInt(query1, arg1);
				int ecount1=this.mytemplate.queryForInt(query3, arg1);
				list.add(count1+ecount1);
				//list.add(list3);
				list.add(ecount1);
				list.add(count1);
				Object[] arg;
				arg = new Object[7];
				arg[0] = batchid;
				arg[1] = "HDB";
				arg[2] = count1+ecount1;
				arg[3] = ecount1;
				arg[4] = batchid;
				arg[5] = batchid;
				arg[6] = batchid;
				LogManager.push("transaction id while inserting"+batchid);
				//Setting status c for cheque no not exists
				String query6 = getQuery(DBConstants.HDFC_MASTER_UPDATE);
				this.mytemplate.update(query6);	
				
				//insertion of citi bank transaction
				String query7 = getQuery(DBConstants.TRANSACTION_INSERT2);
				this.mytemplate.update(query7,arg);
				
				//delete temp
				String query5 = getQuery(DBConstants.HDFC_MASTER_DELETE);
				this.mytemplate.update(query5);
				
				LogManager.push("UploadDAOImpl gettransactiondetailhdfc() Method - Exit"+list);
				
				
			}
			
		catch(Exception e){
			LogManager.debug(e);
			throw new CommonBaseException(e,CommonExceptionConstants.DATABASE_ERROR);
		}
		}
		else if(typeId==115) {
			LogManager.info("Inside Transaction details KOTAK BANK");
			try {
				final String query1 = getQuery(DBConstants.TRANSACTION_DETAILS_KOTAK);
				final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_ERRORCOUNT3)	;
				
				List<Integer> list = new ArrayList<Integer>();
				
				Object[] arg1;
				arg1 = new Object[1];
				arg1[0] = batchid;
				int count1=this.mytemplate.queryForInt(query1, arg1);
				int ecount1=this.mytemplate.queryForInt(query3, arg1);
				list.add(count1+ecount1);
				//list.add(list3);
				list.add(ecount1);
				list.add(count1);
				Object[] arg;
				arg = new Object[7];
				arg[0] = batchid;
				arg[1] = "KOT";
				arg[2] = count1+ecount1;
				arg[3] = ecount1;
				arg[4] = batchid;
				arg[5] = batchid;
				arg[6] = batchid;
				LogManager.info("transaction id while inserting"+batchid);
				//Setting status c for cheque no not exists
				String query6 = getQuery(DBConstants.KOTAK_MASTER_UPDATE);
				this.mytemplate.update(query6);	
				
				//insertion of citi bank transaction
				String query7 = getQuery(DBConstants.TRANSACTION_INSERT115);
				this.mytemplate.update(query7,arg);
				
				//delete temp
				String query5 = getQuery(DBConstants.KOTAK_MASTER_DELETE);
				this.mytemplate.update(query5);
				
				LogManager.info("UploadDAOImpl gettransactiondetailKotak() Method - Exit"+list);
			}
			catch(Exception e){
				LogManager.debug("Exception @ " + e + " } ");
			}
			
		}
		else if(typeId==108)
			{
			LogManager.push("Inside Transaction details HSBC BANK");
			try{
				//get the no of valid records from hsbc_bank table. 
				final String query1 = getQuery(DBConstants.HSBC_DETAILS3);
				//get the no of records which is having the status 'E','D' from dup_hsbc_bank table. 
				final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_HSBCCOUNT3)	;
				List list = new ArrayList();
				Object[] arg1 = new Object[1];
				arg1[0] = batchid;
				int count1=this.mytemplate.queryForInt(query1, arg1);
				int ecount1=this.mytemplate.queryForInt(query3, arg1);
				list.add(count1+ecount1);
				//list.add(list3);
				list.add(ecount1);
				list.add(count1);
				Object[] arg = new Object[7];
				arg[0] = batchid;
				arg[1] = "HSB";
				arg[2] = count1+ecount1;
				arg[3] = ecount1;
				arg[4] = batchid;
				arg[5] = batchid;
				arg[6] = batchid;
				LogManager.push("transaction id while inserting"+batchid);
				//Setting status CN for cheque no not exists in hsbc_bank table
				String query6 = getQuery(DBConstants.HSBC_MASTER_UPDATE);
				this.mytemplate.update(query6);	
				
				//insertion of hsbc bank transaction
				String query7 = getQuery(DBConstants.TRANSACTION_INSERT_HSBC);
				this.mytemplate.update(query7,arg);
				
				//delete temp
				String query5 = getQuery(DBConstants.HSBC_MASTER_DELETE);
				this.mytemplate.update(query5);
				
				LogManager.push("UploadDAOImpl gettransactiondetailhsbc() Method - Exit"+list);
			}
		catch(Exception e){
			LogManager.debug(e);
			throw new CommonBaseException(e,CommonExceptionConstants.DATABASE_ERROR);
		}
		}
		else if(typeId==104)
		{
			LogManager.push("Inside get transaction");
			final String query1 = getQuery(DBConstants.TRANSACTION_RECEIPTNO_DETAILS);
			LogManager.push(query1);
			final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_RECEIPT_ERRORCOUNT);
			LogManager.push(query3);		
			List list = new ArrayList();
			Object[] arg1;
			arg1 = new Object[1];
			arg1[0] = batchid;
			int count=this.mytemplate.queryForInt(query1,arg1);
			int ecount=this.mytemplate.queryForInt(query3,arg1);
			list.add(count+ecount);
			list.add(ecount);
			list.add(count);
			final String queryrectdetails="SELECT COUNT(*) AS COUNT FROM TEMP_RECEIPT_NUMBERS WHERE TRANS_SOURCE!='RECT' AND BATCHID="+batchid+" UNION ALL SELECT COUNT(*)  FROM RECEIPT_NUMBERS RN WHERE RN.BANK_NO IS NOT NULL AND BATCHID="+batchid+" UNION ALL SELECT COUNT(*)  FROM RECEIPT_NUMBERS RN WHERE 'RECT'=RN.TRANS_SOURCE AND RN.BANK_NO IS NULL AND BATCHID="+batchid;
		    List res=this.mytemplate.queryForList(queryrectdetails);
			LogManager.push("res,res.size"+res+","+res.size());
			for(int i=0;i<res.size();i++){
				final Map temp = (Map)res.get(i);
				LogManager.push("res.get(i):"+res.get(i));
				LogManager.push("temp.get(COUNT):"+temp.get("COUNT"));
				list.add(temp.get("COUNT"));
			}
	       
			//deleting temp records
			//String query5 = getQuery(DBConstants.MASTER_RECEIPTNO_DELETE);
			//this.mytemplate.update(query5);
			
			Object[] arg;
			arg = new Object[4];
			arg[0] = batchid;
			arg[1] = "RN";
			arg[2] = count+ecount;
			arg[3] = ecount;
					
			//insertion of RECEIPT NO transaction
			String query7 = getQuery(DBConstants.TRANSACTION_RECEIPT_INSERT3);
			LogManager.push(query7);
			this.mytemplate.update(query7,arg);
			LogManager.push(list.size()+":"+list);
			
		}
		else if(typeId==107)
		{
			LogManager.push("Inside get transaction");
			final String query1 = getQuery(DBConstants.TRANSACTION_RECEIPTREVERSAL_DETAILS);
			LogManager.push(query1);
				
			List list = new ArrayList();
			Object[] arg1;
			arg1 = new Object[1];
			arg1[0] = batchid;
			int count=this.mytemplate.queryForInt(query1,arg1);
			list.add(count);
			list.add(0);
			list.add(count);
			final String queryrectdetails="SELECT COUNT(*) AS COUNT FROM TEMP_RECEIPT_REVERSAL WHERE  BATCHID="+batchid;
		    List res=this.mytemplate.queryForList(queryrectdetails);
			LogManager.push("res,res.size"+res+","+res.size());
			for(int i=0;i<res.size();i++){
				final Map temp = (Map)res.get(i);
				LogManager.push("res.get(i):"+res.get(i));
				LogManager.push("temp.get(COUNT):"+temp.get("COUNT"));
				list.add(temp.get("COUNT"));
			}
	       
			//deleting temp records
			//String query5 = getQuery(DBConstants.MASTER_RECEIPTNO_DELETE);
			//this.mytemplate.update(query5);
			
			Object[] arg;
			arg = new Object[4];
			arg[0] = batchid;
			arg[1] = "RR";
			arg[2] = count;
			arg[3] = 0;
					
			//insertion of RECEIPT NO transaction
			String query7 = getQuery(DBConstants.TRANSACTION_RECEIPT_INSERT3);
			LogManager.push(query7);
			this.mytemplate.update(query7,arg);
			LogManager.push(list.size()+":"+list);
			
		}
		
	 	try{
			if(typeId==102){
				
			//	query="INSERT INTO CITI_BANK (BANK_NO,CLIENT_CODE,DEPOSIT_DATE,PRODUCT,CREDIT_DEBIT_DATE,LOCATION,CHEQUE_NO,CHEQUE_AMT,TYPE_CRDR,NARRATION,CBP_NO,DEPSLIPNO,CUSTOMERREF,DEPOSIT_AMT,DWE_BANK_CODE,CHECK_DATA,COVERNOTENO,BANK_NAME,PICK_POINT_NAME,PKUP_POINT_CODE,REMARKS,TRANSACTION_ID) SELECT CITIBANKSEQ.NEXTVAL,CLIENT_CODE,DEPOSIT_DATE,PRODUCT,CREDIT_DEBIT_DATE,LOCATION,CHEQUE_NO,CHEQUE_AMT,TYPE_CRDR,NARRATION,CBP_NO,DEPSLIPNO,CUSTOMERREF,DEPOSIT_AMT,DWE_BANK_CODE,CHECK_DATA,COVERNOTENO,BANK_NAME,PICK_POINT_NAME,PKUP_POINT_CODE,REMARKS,TRANSACTION_ID FROM  TEMP_CITI_BANK T WHERE T.STATUS='Y'";
				//query = getQuery(DBConstants.CITI_MASTER_INSERT);
				query2 = getQuery(DBConstants.CITI_DUPLICATES_INSERT);
				Object[] arg2;
				arg2 = new Object[1];
				arg2[0] = batchid;
				//this.mytemplate.update(query, arg2);
				this.mytemplate.execute(query2);
				/*String updat="update citi_bank set CHEQUE_AMT=-(CHEQUE_AMT) where CHEQUE_AMT like '-%' and BATCHID="+batchid;
			    LogManager.push("Update for min status:"+ updat);
				this.mytemplate.update(updat);*/
				//Selecting cheque no, cheque amt having debit and credit 
				//records filtered by debit 
			    String querysel="select  cheque_no, cheque_amt from citi_bank where batchid="+batchid+" and cheque_no in ( select  cheque_no from citi_bank where batchid="+batchid+" and type_crdr ='D' ) and cheque_amt in ( select  cheque_amt from citi_bank where batchid="+batchid+"  and type_crdr ='D') group by cheque_no,cheque_amt having count(*)>1 ";
                LogManager.push("query to sel repeated cheques citi:"+querysel);
			    List list = this.mytemplate.query(querysel, new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString(1)+"_"+rset.getString(2);
					}
						
				});
			    LogManager.push("List size"+list.size());
			    if(list.size()>0){
				LogManager.push("list size of cheque nos repeated"+list.size());
				LogManager.push("Cheque no repeated citi"+list.get(0));
				String update="";
				String chqdetail[]=new String [2];
				
				for(int i=0;i<list.size();i++)
				{
				    chqdetail= list.get(i).toString().split("_");	
					LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
					update="update citi_bank set status='D' where cheque_no='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+" and batchid="+batchid;
					LogManager.push("Update for repeated cheque's status citi:"+ update);
					this.mytemplate.update(update);
					update="update citi_bank set DISHONOUR_TYPE='-77777' where cheque_no='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+" and CREDIT_DEBIT_DATE!=(select max(CREDIT_DEBIT_DATE) from citi_bank where cheque_no='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+") and batchid="+batchid;
					LogManager.push("Update for returned cheque's status AXIS_BANK:"+ update);
					this.mytemplate.update(update);
					update="select unique (case when((SELECT SUM (CHEQUE_AMT) FROM   CITI_BANK WHERE   type_crdr = 'C'   AND cheque_no = '"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+" and batchid="+batchid+")  - (SELECT   SUM (CHEQUE_AMT) FROM   CITI_BANK  WHERE   batchid="+batchid+" and type_crdr = 'D' AND cheque_no = '"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from citi_bank where batchid="+batchid+" and cheque_no='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1];
       				LogManager.push("update for status:"+update);
					Object result=this.mytemplate.queryForObject(update,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString("status");
						}} );
					LogManager.push("result.toString()"+result.toString().trim());
					if(result.toString().trim().equalsIgnoreCase("C"))
					{
					update="update citi_bank set status='C' where batchid="+batchid+" and cheque_no='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+" AND TYPE_CRDR='COL' and CREDIT_DEBIT_DATE=(select max(CREDIT_DEBIT_DATE) from citi_bank where batchid="+batchid+" and cheque_no ='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+")";
					LogManager.push("Update for max cheque's status citi:"+ update);
					this.mytemplate.update(update);
					}
				}
			    }			
				/*String updat1="update citi_bank set CHEQUE_AMT=-(CHEQUE_AMT) where batchid="+batchid+" and STATUS='D' and TYPE_CRDR='D' and cheque_amt not like '-%'";
				LogManager.push("Update for min status:"+ updat1);
				this.mytemplate.update(updat1);*/
				
					
			}
			
			else if(typeId==105)
			{
				
				LogManager.push("Method Enter SUB_Duplicate_insert");
				query=getQuery(DBConstants.SCB_DUPLICATES_INSERT);
				LogManager.push("Query for type Id ===>"+query);
				Object[] arg2=new Object[1];
				arg2[0]=batchid;
				this.mytemplate.execute(query);
				LogManager.push("================>ExitQuery for type Id ============>");
				 String querysel="select  CHEQUE_NO,CHQ_AMOUNT from SCB_BANK where batchid="+batchid+" and CHEQUE_NO in ( select  CHEQUE_NO from SCB_BANK where batchid="+batchid+" and CR_DR='D' ) and CHQ_AMOUNT in ( select  CHQ_AMOUNT from SCB_BANK where batchid="+batchid+"  and CR_DR ='D') group by CHEQUE_NO,CHQ_AMOUNT having count(*)>1 ";
	                LogManager.push("query to sel repeated cheques SCB_BANK:"+querysel);
	                List list = this.mytemplate.query(querysel, new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							return rset.getString(1)+"_"+rset.getString(2);
						}
							
					});
				    LogManager.push("List size"+list.size());
				    if(list.size()>0){
					LogManager.push("list size of cheque nos repeated"+list.size());
					LogManager.push("Cheque no repeated citi"+list.get(0));
					String update="";
					String chqdetail[]=new String [2];
					
					
					for(int i=0;i<list.size();i++)
					{
					    chqdetail= list.get(i).toString().split("_");	
						LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
						update="update SCB_BANK set status='D' where CHEQUE_NO='"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+" and batchid="+batchid;
						LogManager.push("Update for repeated cheque's status SCB:"+ update);
						this.mytemplate.update(update);
						update="update SCB_BANK set DISHONOUR_TYPE='-77777' where CHEQUE_NO='"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+" and CREDIT_DEBIT_DT!=(select max(CREDIT_DEBIT_DT) from scb_bank where CHEQUE_NO='"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+") and batchid="+batchid;
						LogManager.push("Update for returned cheque's status AXIS_BANK:"+ update);
						this.mytemplate.update(update);
						update="select unique (case when((SELECT SUM (CHQ_AMOUNT) FROM   SCB_BANK WHERE   CR_DR = 'C'   AND CHEQUE_NO = '"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+" and batchid="+batchid+")  - (SELECT   SUM (CHQ_AMOUNT) FROM   SCB_BANK  WHERE   batchid="+batchid+" and CR_DR = 'D' AND CHEQUE_NO = '"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from SCB_BANK where batchid="+batchid+" and CHEQUE_NO='"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1];
	       				LogManager.push("update for status:"+update);
						Object result=this.mytemplate.queryForObject(update,new RowMapper() {
							public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							return rset.getString("status");
							}} );
						LogManager.push("result.toString()"+result.toString());
						if(result.toString().trim().equalsIgnoreCase("C"))
						{
						update="update SCB_BANK set status='C' where batchid="+batchid+" and CHEQUE_NO='"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+" AND CR_DR='COL' and CREDIT_DEBIT_DT=(select max(CREDIT_DEBIT_DT) from SCB_BANK where batchid="+batchid+" and CHEQUE_NO ='"+chqdetail[0]+"' and CHQ_AMOUNT="+chqdetail[1]+")";
						LogManager.push("Update for max cheque's status SCB_BANK:"+ update);
						this.mytemplate.update(update);
						}
					}
				    }	
				    LogManager.push("Query insert-->"+query);
					
					LogManager.push("Query duplicate insert-->"+query2);
				}

			else if(typeId==106)
			{
				try{
			 	LogManager.push("AXIS_BANK Query insert-->"+query);
				String updateStatus="update axis_bank set status='C' where type='COL' and batchid="+batchid;
				LogManager.push("Update COL "+updateStatus);
			 	this.mytemplate.update(updateStatus);
				String updateStatus2="update axis_bank set status='D' where type='RTN' and batchid="+batchid;
				LogManager.push("Update RTN "+updateStatus2);
				this.mytemplate.update(updateStatus2);
				LogManager.push("AXIS_BANK Query duplicate insert-->"+query2);
				LogManager.push("AXIS Method Enter SUB_Duplicate_insert");
				query=getQuery(DBConstants.AXIS_DUPLICATES_INSERT);
				LogManager.push("Query for type Id ===>"+query);
				Object[] arg2=new Object[1];
				arg2[0]=batchid;
				this.mytemplate.execute(query);
				LogManager.push("================>ExitQuery for type Id Axis Changed ============>");
				 String querysel="select  INST_NO,INSTRUMENT_AMOUNT from AXIS_BANK where batchid="+batchid+" and INST_NO in ( select  INST_NO from AXIS_BANK where batchid="+batchid+" and TYPE='RTN' ) and INSTRUMENT_AMOUNT in ( select  INSTRUMENT_AMOUNT from AXIS_BANK where batchid="+batchid+"  and TYPE='RTN') group by INST_NO,INSTRUMENT_AMOUNT having count(*)>1 ";
	                LogManager.push("query to sel repeated cheques AXIS_BANK:"+querysel);
	                List list = this.mytemplate.query(querysel, new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							return rset.getString(1)+"_"+rset.getString(2);
						}
							
					});
				    LogManager.push("List size"+list.size());
				    if(list.size()>0){
					LogManager.push("list size of cheque nos repeated"+list.size());
					LogManager.push("Cheque no repeated axis"+list.get(0));
					String update="";
					String chqdetail[]=new String [2];
					
					
					for(int i=0;i<list.size();i++)
					{
					    chqdetail= list.get(i).toString().split("_");	
						LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
						update="update AXIS_BANK set status='D' , DISHONOUR_TYPE='' where INST_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid;
						LogManager.push("Update for repeated cheque's status AXIS_BANK:"+ update);
						this.mytemplate.update(update);
						update="update AXIS_BANK set DISHONOUR_TYPE='-77777' where INST_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and Cr_Date!=(select max(Cr_Date) from axis_bank where INST_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+") and batchid="+batchid;
						LogManager.push("Update for returned cheque's status AXIS_BANK:"+ update);
						this.mytemplate.update(update);
						update="select unique (case when((SELECT SUM (INSTRUMENT_AMOUNT) FROM AXIS_BANK WHERE TYPE = 'COL'   AND INST_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid+") - (SELECT   SUM (INSTRUMENT_AMOUNT) FROM AXIS_BANK WHERE batchid="+batchid+" and TYPE = 'RTN' AND INST_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from AXIS_BANK where batchid="+batchid+" and INST_NO='"+chqdetail[0]+"' and instrument_amount="+chqdetail[1];
	       				LogManager.push("update for status:"+update);
						Object result=this.mytemplate.queryForObject(update,new RowMapper() {
							public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							return rset.getString("status");
							}} );
						LogManager.push("result.toString()"+result.toString());
						if(result.toString().trim().equalsIgnoreCase("C"))
						{
						update="update AXIS_BANK set status='C'  where batchid="+batchid+" and INST_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and TYPE='COL' and Cr_Date=(select max(Cr_Date) from AXIS_BANK where batchid="+batchid+" and INST_NO ='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+")";
						LogManager.push("Update for max cheque's status AXIS_BANK:"+ update);
						this.mytemplate.update(update);
						}
					}
				    }	
				}catch(Exception e){
					LogManager.debug(e);
				}
				}
			else if(typeId==103)
			{
				
			//	query = getQuery(DBConstants.HDFC_MASTER_INSERT);
				query2 = getQuery(DBConstants.HDFC_DUPLICATES_INSERT);
				Object[] arg2;
				arg2 = new Object[1];
				arg2[0] = batchid;
				//this.mytemplate.update(query, arg2);
				//this.mytemplate.execute(query);
				this.mytemplate.execute(query2);

			    String querysel="select  INSTRUMENT_NO,INSTRUMENT_AMOUNT from hdfc_bank where batchid="+batchid+" and INSTRUMENT_NO in ( select  INSTRUMENT_NO from hdfc_bank where batchid="+batchid+"  and dr_cr ='D' ) and INSTRUMENT_AMOUNT in (select  INSTRUMENT_AMOUNT from hdfc_bank where batchid="+batchid+" and dr_cr ='D') group by INSTRUMENT_NO,INSTRUMENT_AMOUNT having count(*)>1 "; 
                LogManager.push("query to sel repeated cheques hdfc:"+querysel);
			    List list = this.mytemplate.query(querysel, new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString(1)+"_"+rset.getString(2);
					}
						
				});
			    String chqdetail[]=new String [2];
			    if(list.size()>0){
			
						
				LogManager.push("list size of cheque nos repeated"+list.size());
				LogManager.push("Cheque no repeated"+list.get(0));
				String update="";
				for(int i=0;i<list.size();i++)
				{
				    
				    chqdetail= list.get(i).toString().split("_");	
					LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
					update="update hdfc_bank set status='D' where INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid;
					LogManager.push("Update for repeated cheque's status hdfc_bank:"+ update);
					this.mytemplate.update(update);
					update="update hdfc_bank set DISHONOUR_TYPE='-77777' where INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and POST_DT!=(select max(POST_DT) from hdfc_bank where INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+") and batchid="+batchid;
					LogManager.push("Update for returned cheque's status HDFC_BANK:"+ update);
					this.mytemplate.update(update);
					
					update="select unique (case when((SELECT SUM (INSTRUMENT_AMOUNT) FROM   hdfc_bank WHERE   DR_CR = 'C'   AND INSTRUMENT_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid+")  - (SELECT   SUM (INSTRUMENT_AMOUNT) FROM   hdfc_bank  WHERE   batchid="+batchid+" and DR_CR = 'D' AND INSTRUMENT_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from hdfc_bank where batchid="+batchid+" and INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1];
       				LogManager.push("update for status:"+update);
					Object result=this.mytemplate.queryForObject(update,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString("status");
						}} );
					LogManager.push("result.toString()"+result.toString()); 
					if(result.toString().trim().equalsIgnoreCase("C"))
					{
					update="update hdfc_bank set status='C' where batchid="+batchid+" and INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" AND DR_CR='COL' and POST_DT=(select max(POST_DT) from hdfc_bank where batchid="+batchid+" and INSTRUMENT_NO ='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+")";
					LogManager.push("Update for max cheque's status citi:"+ update);
					this.mytemplate.update(update);
					}
						   
				}
			    }			
			}
			else if(typeId==108)
			{
				
			//	query = getQuery(DBConstants.HDFC_MASTER_INSERT);
				query2 = getQuery(DBConstants.HSBC_DUPLICATES_INSERT);
				Object[] arg2;
				arg2 = new Object[1];
				arg2[0] = batchid;
				//this.mytemplate.update(query, arg2);
				//this.mytemplate.execute(query);
				this.mytemplate.execute(query2);

			    String querysel="select  INSTRUMENT_NO,INSTRUMENT_AMOUNT from hsbc_bank where batchid="+batchid+" and INSTRUMENT_NO in ( select  INSTRUMENT_NO from hsbc_bank where batchid="+batchid+"  and DEBIT_CREDIT ='D' ) and INSTRUMENT_AMOUNT in (select  INSTRUMENT_AMOUNT from hsbc_bank where batchid="+batchid+" and DEBIT_CREDIT ='D') group by INSTRUMENT_NO,INSTRUMENT_AMOUNT having count(*)>1 "; 
                LogManager.push("query to sel repeated cheques hsbc:"+querysel);
			    List list = this.mytemplate.query(querysel, new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString(1)+"_"+rset.getString(2);
					}
						
				});
			    String chqdetail[]=new String [2];
			    if(list.size()>0){
			
						
				LogManager.push("list size of cheque nos repeated"+list.size());
				LogManager.push("Cheque no repeated"+list.get(0));
				String update="";
				for(int i=0;i<list.size();i++)
				{
				    chqdetail= list.get(i).toString().split("_");	
					LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
					update="update hsbc_bank set status='D' where INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid;
					LogManager.push("Update for repeated cheque's status hsbc_bank:"+ update);
					this.mytemplate.update(update);
					update="update hsbc_bank set DISHONOUR_TYPE='-77777' where INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and POST_DATE!=(select max(POST_DATE) from hsbc_bank where INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+") and batchid="+batchid;
					LogManager.push("Update for returned cheque's status HDFC_BANK:"+ update);
					this.mytemplate.update(update);
					update="select unique (case when((SELECT SUM (INSTRUMENT_AMOUNT) FROM   hsbc_bank WHERE   DEBIT_CREDIT = 'C'   AND INSTRUMENT_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and batchid="+batchid+")  - (SELECT   SUM (INSTRUMENT_AMOUNT) FROM   hsbc_bank  WHERE   batchid="+batchid+" and DEBIT_CREDIT = 'D' AND INSTRUMENT_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from hsbc_bank where batchid="+batchid+" and INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1];
       				LogManager.push("update for status:"+update);
					Object result=this.mytemplate.queryForObject(update,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString("status");
						}} );
					LogManager.push("result.toString()"+result.toString()); 
					if(result.toString().trim().equalsIgnoreCase("C"))
					{
					update="update hsbc_bank set status='C' where batchid="+batchid+" and INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" AND DEBIT_CREDIT='COL' and POST_DATE=(select max(POST_DATE) from hsbc_bank where batchid="+batchid+" and INSTRUMENT_NO ='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+")";
					LogManager.push("Update for max cheque's status citi:"+ update);
					this.mytemplate.update(update);
					}
				}
			    }			
			}
			LogManager.push("Query insert-->"+query);
			LogManager.push("Query duplicate insert-->"+query2);
		}catch(Exception e){
			LogManager.debug(e);
			throw new CommonBaseException(e,CommonExceptionConstants.DATABASE_ERROR);
		}
	}
 public int getProcess(String transId,String bankId) throws CommonBaseException {
		
		Connection con;
		int count=0;
		CallableStatement cs ;
	try {
		String status=(String)this.mytemplate.queryForObject("select processed from transaction_details where transaction_no="+transId, String.class);	
		if(status.equalsIgnoreCase("N"))	
		{
		try{
		
		this.mytemplate.update("update transaction_details set processed='P' where transaction_no="+transId);	
		con = this.mytemplate.getDataSource().getConnection();
		cs = con.prepareCall("{ call update_brnew(?,?,?)}");
		cs.setString(1, bankId);
		cs.setString(2, transId);
		cs.registerOutParameter(3, Types.INTEGER);
		LogManager.push("Process Procedure starting.......");
		cs.execute();
		LogManager.push("Process Procedure end.......");
		count=cs.getInt(3);
		}catch(Exception e)
		{
			LogManager.debug(e);
		}
		finally{
			this.mytemplate.update("update transaction_details set processed='N' where processed='P'");
		}
		}
	}catch(Exception e) {
		e.printStackTrace();
	}
		return count; 
	}
	
	/*public int getProcess(String transId,String bankId) throws CommonBaseException {
		LogManager.push("UploadDAOImpl getprocessdetail() Method - Enter");
		LogManager.push("Inside getProcess daoimpl:"+bankId);
		
		List list=getBankQueryData(bankId);
		final Map tempMap = (Map) list.get(0);
		String tableName = ((String)tempMap.get("TABLE_NAME"));
		String chequeNo = ((String)tempMap.get("CHEQUE_NO"));
		String chequeAmt = ((String)tempMap.get("CHEQUE_AMT"));
		String chequeStatus = ((String)tempMap.get("CHEQUE_STATUS"));
		LogManager.push("Inside getProcess BANK daoimpl:"+bankId);
		LogManager.push("TableName====>"+tableName);
		LogManager.push("TableId====>"+transId);
		String query1="SELECT COUNT(*) FROM "+tableName+" WHERE BATCHID="+transId;
		Object result=this.mytemplate.queryForObject(query1,new RowMapper() {
		public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
		return rset.getString(1);
		}} );
		
		String uploadCount=result.toString();
		LogManager.push("upload count>>>>>"+uploadCount);
		if(bankId.equalsIgnoreCase("CIT")){
			String updat="update citi_bank set CHEQUE_AMT=-(CHEQUE_AMT) where CHEQUE_AMT like '-%'and BATCHID ="+transId;
			LogManager.push("Update for min status:"+ updat);
			this.mytemplate.update(updat);
		}
		if(uploadCount.equalsIgnoreCase("0"))
		{
			String transacted="UPDATE TRANSACTION_DETAILS SET PROCESSED='Y', PROCESSED_TIME=SYSDATE WHERE TRANSACTION_NO ="+transId;
			LogManager.push("Update for transacted status:"+ transacted);
			this.mytemplate.update(transacted);
			return 0;
		}
		else
		{
			
			LogManager.push("UPDATING RECEIPT RECORDS");
			query="SELECT   MAX(R.RECEIPT_SL_NO) as RECEIPT, MAX(C.BANK_NO) BANKNO FROM   "+tableName+ 
			" C,RECEIPT_MASTER R  WHERE  C."+chequeNo+" = R.CHEQUE_NO AND TO_NUMBER(C."+chequeAmt+
			") = TO_NUMBER(R.AMOUNT) AND R.BANK_NO IS NULL  AND R.BANK_CODE IN (SELECT   BANK_ACCOUNT_CODE  " +
			" FROM   BANK_ACCOUNT_CODE  WHERE   BANK_ID = '"+bankId+"' AND STATUS = 'Y')   AND " +
			"R.PAYMENT_TYPE = 'CHQ' AND R.TRANS_SOURCE='RECT'  AND C.RECEIPT_SL_NO IS  NULL  AND " +
			"C.BATCHID = "+transId+" group by C.BANK_NO";
			int count1=0;
			int count2=0;
			try{
				final List list1 = this.mytemplate.queryForList(query);
				if(list1!=null && list1.size()>0){
					for(int i=0;i<list1.size();i++){
						final Map temp = (Map)list1.get(i);
						query="update receipt_master set bank_no="+temp.get("BANKNO")+",transaction_bank_id="+transId+" where receipt_sl_no="+temp.get("RECEIPT"); 
						count1+=this.mytemplate.update(query);
						
						query="update "+tableName+ " set receipt_sl_no="+temp.get("RECEIPT")+" where bank_no="+temp.get("BANKNO")+" and BATCHID="+transId; 
						count2+=this.mytemplate.update(query);
					}
				}
			}catch(Exception e){
				LogManager.debug(e);
			}
			
			LogManager.push("count1:>>>"+count1);
			//**Update for bank record**//*
			//Commented by muthu
			  LogManager.push("UPDATING BANK RECORDS");
			 query="SELECT MAX(R.RECEIPT_SL_NO) AS RECEIPT,C.BANK_NO AS BANKNO FROM RECEIPT_MASTER R,"+tableName+ " C WHERE C."+chequeNo+" = R.CHEQUE_NO AND TO_NUMBER(C."+chequeAmt+") = TO_NUMBER(R.AMOUNT) AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankId+"' AND STATUS='Y')AND R.PAYMENT_TYPE='CHQ' AND R.TRANS_SOURCE='RECT'  AND R.BANK_NO IS NOT NULL AND C.RECEIPT_SL_NO IS NULL AND R.TRANSACTION_BANK_ID ="+transId+" AND C.BATCHID="+transId+" group by c.bank_no" ;
			 try{
					
					final List list1 = this.mytemplate.queryForList(query);
					if(list1!=null && list1.size()>0){
						for(int i=0;i<list1.size();i++){
							final Map temp = (Map)list1.get(i);
							String qury="update "+tableName+ " set receipt_sl_no="+temp.get("RECEIPT")+" where bank_no="+temp.get("BANKNO")+" and BATCHID="+transId; 
							count2+=this.mytemplate.update(qury);
							
						}
					}
				}catch(Exception e){
					LogManager.debug(e);
				}//end comment by muthu
			LogManager.push("count2:>>>"+count2);
			
			if(bankId.equalsIgnoreCase("CIT")){
				String updat1="update citi_bank set CHEQUE_AMT=-(CHEQUE_AMT) where TYPE_CRDR='D' and " +
				"cheque_amt not like '-%' and BATCHID ="+transId;
				LogManager.push("Update for min status:"+ updat1);
				this.mytemplate.update(updat1);
			}
			String transacted="UPDATE TRANSACTION_DETAILS SET PROCESSED='Y',PROCESSED_TIME=SYSDATE WHERE " +
					"TRANSACTION_NO ="+transId;
			LogManager.push("Update for transacted status non duplicates:"+ transacted);
			this.mytemplate.update(transacted);
			
             //GETTING COUNTS FOR NO. OF RECORDS MATCHED
			query ="SELECT COUNT(*) FROM "+tableName+ " WHERE RECEIPT_SL_NO IS NOT NULL AND BATCHID="+transId;
			LogManager.push("Query for matched col:==>"+query);
			int count=this.mytemplate.queryForInt(query);
			//start of actual cheque no and amt matching start
			query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y'";
			LogManager.push("BANKS POPUP:"+query1);
			
	        List listbank;
			
			listbank =this.mytemplate.query(query1,new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				return rset.getString("BANK_ID");
			}} );
			final String tableName1[]=new String[listbank.size()];
			final String chequeStatus1[]=new String[listbank.size()];
			final String chequeNo1[]=new String[listbank.size()];
			final String chequeAmt1[]=new String[listbank.size()];
			final String bankName1[]=new String[listbank.size()];
			final String receipt1[]=new String[listbank.size()];
			final String reason1[]=new String[listbank.size()];
			final String bankIds1[]=new String[listbank.size()];
		
			for(int k=0;k<listbank.size();k++)
			{
				bankIds1[k]=listbank.get(k).toString();
				List blist=getBankQueryData(listbank.get(k).toString());
				final Map tempMap1 = (Map)blist.get(0);
				tableName1[k]=((String)tempMap1.get("TABLE_NAME"));
				chequeStatus1[k]=((String)tempMap1.get("CHEQUE_STATUS"));
				reason1[k]=((String)tempMap1.get("REASON"));
				chequeNo1[k]=((String)tempMap1.get("CHEQUE_NO"));
			 	chequeAmt1[k]=((String)tempMap1.get("CHEQUE_AMT"));
			    bankName1[k]=(String)tempMap1.get("BANK_NAME");
				receipt1[k]=((String)tempMap1.get("RECEIPT_NO"));
				LogManager.push("<--"+tableName1[k]+""+chequeStatus1[k]+"-->");
				// sbean.setBankName((String)tempMap.get("BANK_NAME"));
				
				//UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN BOTH FIELDS NOT NULL
				
				//Query for not matched actual cheque no and amt:
				query ="SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT FROM "+tableName1[k]+" WHERE " +
				"ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
				List values=this.mytemplate.queryForList(query);
				for(int i=0;i<values.size();i++)
				{
					LogManager.push("values " + values.get(i));
					final Map tempMap2 = (Map) values.get(i);
					final String actualchequeno=((String)tempMap2.get("ACTUAL_CHEQUE_NO").toString());
					final String actualchequeamt=((String)tempMap2.get("ACTUAL_CHEQUE_AMT").toString());
					LogManager.push("actualchequeno"+actualchequeno+"actualchequeamt"+actualchequeamt);
					
					//Query to find actual cheque exists
					query="UPDATE "+tableName1[k]+" B SET RECEIPT_SL_NO=(SELECT MAX(R.RECEIPT_SL_NO) FROM " +
					"RECEIPT_MASTER R WHERE R.CHEQUE_NO='"+actualchequeno+"' AND R.AMOUNT="+actualchequeamt+
					" AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+
					actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+
					bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE " +
					"B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+
					actualchequeamt+"";
					this.mytemplate.update(query);
					LogManager.push("Query for bank matched col BOTH NOT NULL:==>"+query+"count-->");
					
					query="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName1[k]+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO= '"+actualchequeno+"' AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND '"+actualchequeno+"'=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.PAYMENT_TYPE='CHQ' AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y')";
					this.mytemplate.update(query);
					LogManager.push("Query for receipt matched col BOTH NOT NULL:==>"+query+"count-->");				
				}
            
		        //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE AMT NOT NULL
				//Query for not matched actual cheque amt:
				String queryvalues2="SELECT ACTUAL_CHEQUE_AMT FROM "+tableName1[k]+" WHERE ACTUAL_CHEQUE_NO IS NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
				List values2=this.mytemplate.queryForList(queryvalues2);
				for(int i=0;i<values2.size();i++)
				{
					LogManager.push("values " + values2.get(i));
					final Map tempMap2 = (Map) values2.get(i);
					//final String actualchequeno=((String)tempMap2.get("ACTUAL_CHEQUE_NO"));
					final String actualchequeamt=((String)tempMap2.get("ACTUAL_CHEQUE_AMT").toString());
					LogManager.push("actualchequeamt"+actualchequeamt);
					//Query to match in bank table
					query="UPDATE "+tableName1[k]+" B SET RECEIPT_SL_NO=(SELECT MAX(R.RECEIPT_SL_NO) FROM " +
					"RECEIPT_MASTER R WHERE B."+chequeNo1[k]+"=R.CHEQUE_NO AND "+actualchequeamt+
					"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+
					actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE " +
					"WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND " +
					"B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL " +
					"AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
					this.mytemplate.update(query);
					LogManager.push("Query for bank matched col CHEQUE AMT NOT NULL:==>"+query+"count-->");
					
					//query to match in receipt
					query="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName1[k]+" C WHERE C."+chequeNo1[k]+"=R.CHEQUE_NO AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO IS NULL AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' ";
					this.mytemplate.update(query);
					LogManager.push("Query for receipt matched col CHEQUE AMT NOT NULL:==>"+query+"count-->");					
				}				
				
				//UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE NO NOT NULL
				//Query for not matched actual cheque no:
				String queryvalues3="SELECT ACTUAL_CHEQUE_NO FROM "+tableName1[k]+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS  NULL AND RECEIPT_SL_NO IS NULL";
				List values3=this.mytemplate.queryForList(queryvalues3);
				for(int i=0;i<values3.size();i++)
				{
					LogManager.push("values " + values3.get(i));
					final Map tempMap2 = (Map) values3.get(i);
					final String actualchequeno=((String)tempMap2.get("ACTUAL_CHEQUE_NO"));
					//final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT"));
					LogManager.push("actualchequeno"+actualchequeno);
					//Query to match in bank table
				    query="UPDATE "+tableName1[k]+" B SET RECEIPT_SL_NO=(SELECT MAX(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND B."+chequeAmt1[k]+"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT IS NULL";
					this.mytemplate.update(query);
					LogManager.push("Query for bank matched col CHEQUE NO NOT NULL:==>"+query+"count-->");
					
					//query to match in receipt
					query="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName1[k]+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND C."+chequeAmt1[k]+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO= '"+actualchequeno+"' AND C.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND '"+actualchequeno+"'=R.CHEQUE_NO AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ'";
					this.mytemplate.update(query);
					LogManager.push("Query for receipt matched col CHEQUE NO NOT NULL:==>"+query+"count-->");
				}
			 }
			
            //	end of process actual cheque no and cheque amount 
			//***end of actual cheque no and amt matching*//*
			String updatestat="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN STATUS IS NULL THEN "+
			chequeStatus+" ELSE STATUS END) AS "+chequeStatus+" FROM "+tableName+" C WHERE R.BANK_NO=C.BANK_NO AND " +
			"C.BATCHID ="+transId+" and R.TRANSACTION_BANK_ID ="+transId+") where TRANSACTION_BANK_ID="+transId;
			LogManager.push("Update for  status in receipt in excelupload:"+ updatestat);
			this.mytemplate.update(updatestat);
		return count;
		}
	}*/
	
	public Map getBankList(UploadForm form) throws CommonBaseException {
		LogManager.push("UploadDAOImpl getBankList() method enter");
		Map result = new HashMap();
		try{
			query = getQuery(DBConstants.BANK_LIST);
			final List list = this.mytemplate.queryForList(query);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					final Map temp = (Map)list.get(i);
					result.put(temp.get("BANK_ID"), temp.get("BANK_NAME"));
				}
			}
		}catch(Exception e){
			LogManager.debug(e);
		}
		LogManager.push("UploadDAOImpl getBankList() method exit");
		 // Should be the last statement
		return result;		
	}
	
	public List getBankDetail(String bankCode) throws CommonBaseException {
		 List table =null;
		try{
			query = getQuery(DBConstants.BANK_DETAILS);
			table =this.mytemplate.queryForList(query,new Object[]{bankCode});			
		}catch(Exception e){
			LogManager.debug(e);
		}
		LogManager.push("UploadDAOImpl getBankList() method exit");
		return table;
	}
	
	public List getBankQueryData(String bankCode) throws CommonBaseException {
		List table =null;
		try{
			query = getQuery(DBConstants.BANK_DETAILS_QUERY);
			table =this.mytemplate.queryForList(query,new Object[]{bankCode});
		}catch(Exception e){
			LogManager.debug(e);
		}
		LogManager.push("UploadDAOImpl getBankList() method exit");
		return table;
	}

	public void updateReversalReceipts(String transId) throws CommonBaseException {		
	try{
		query =" select CHEQUE_NO , AMOUNT from receipt_master where " +
		"cheque_no in (select cheque_no from receipt_master where trans_source='PYMT' and batchid="+transId+")  " +
		"and  trans_source='RECT' and  amount in (select amount from receipt_master where trans_source='PYMT' " +
		"and batchid="+transId+") group by cheque_no, amount ";
		LogManager.push("Receipt reversal::"+query);
        int count1=0;
		final List list1 = this.mytemplate.queryForList(query);
		if(list1!=null && list1.size()>0){
			for(int i=0;i<list1.size();i++){
				final Map temp = (Map)list1.get(i);
				//Updating payment receipt_reversal with the corresponding payment in status field
				query="update receipt_master set bank_no=-88888 ,status=(select receipt_no from " +
				"receipt_master where cheque_no='"+temp.get("CHEQUE_NO")+"' " +
				"and amount="+temp.get("AMOUNT")+" and trans_source='PYMT' ) " +
				"where receipt_sl_no=( select MIN(receipt_sl_no) " +
				"from receipt_master where cheque_no='"+temp.get("CHEQUE_NO")+"' " +
				"and amount="+temp.get("AMOUNT")+" and trans_source='RECT')"; 
				LogManager.push("qury for reversal"+i+">>"+query);
				count1+=this.mytemplate.update(query);
			}
		}
	}catch(Exception e){
		LogManager.debug(e);
	}
	LogManager.push("UploadDAOImpl getBankList() method exit");
	}

	public List getTransactedDetails(UploadForm uploadForm) {
		 LogManager.push("Enter UploadDAOImpl getTransactedDetails () ");

		final String param=uploadForm.getBankName();
		
		if(param.equalsIgnoreCase("CIT"))
			query=getQuery("maan.db.transaction.citi.details.rep");
		else if (param.equalsIgnoreCase("HDB"))
			query=getQuery("maan.db.transaction.hdfc.details.rep");
		else if (param.equalsIgnoreCase("HSB"))
			query=getQuery("maan.db.transaction.hsbc.details.rep");
		else if (param.equalsIgnoreCase("SCB"))
			query=getQuery("maan.db.transaction.scb.details.rep");
		else if (param.equalsIgnoreCase("AXB"))
			query=getQuery("maan.db.transaction.axis.details.rep");
		if(StringUtils.isNotBlank(query))
			query=query+" AND TRANSACTION_NO=? ORDER BY TRANSACTION_NO DESC";
		LogManager.push("getTransactedDetails query::"+query);
		
		List list=null;
		if(param.equalsIgnoreCase("RN"))
	   {
		 LogManager.push("Enter getTransactedDetails () REceipt NOs");
		 list = this.mytemplate.query(query,new RowMapper() {
			public Object mapRow(final ResultSet rset,final int idVal) throws SQLException {
			final TransactionVB tranVB=new TransactionVB();
			tranVB.setTransactionNo(rset.getString("TRANSACTION_NO"));
			tranVB.setTransactionNo2(rset.getString("TRANSACTION_NO"));
			tranVB.setTotalRecords(rset.getString("TOTAL_RECORDS"));
			tranVB.setDuplicates(rset.getString("DUPLICATES"));
			tranVB.setMatched(rset.getString("MATCHED"));
			tranVB.setPending(rset.getString("PENDING"));
			tranVB.setInvalid(rset.getString("INVALID"));
			tranVB.setTransdate(rset.getString("TDATE"));
			//tranVB.setReversals(rset.getString("REVERSALS"));
			return tranVB;
				}
			});
	   } else if(param.equalsIgnoreCase("RR")) {
		 LogManager.push("Enter getTransactedDetails () Receipt Reversals");
		 list = this.mytemplate.query(query,new RowMapper() {
			public Object mapRow(final ResultSet rset,final int idVal) throws SQLException {
			final TransactionVB tranVB=new TransactionVB();
			tranVB.setTransactionNo(rset.getString("TRANSACTION_NO"));
			tranVB.setTransactionNo2(rset.getString("TRANSACTION_NO"));
			tranVB.setTotalRecords(rset.getString("TOTAL_RECORDS"));
			tranVB.setAvailable(rset.getString("AVAIL"));
			tranVB.setMatched(rset.getString("MATCHED"));
			tranVB.setPending(Integer.toString(Integer.parseInt(rset.getString("TOTAL_RECORDS")) -Integer.parseInt(rset.getString("MATCHED"))-Integer.parseInt(rset.getString("AVAIL"))));
			tranVB.setPaymentsMatched(rset.getString("PAYMENTS"));
			return tranVB;
			}
			});
		}else if(param.equalsIgnoreCase("RT")){
			list = this.mytemplate.query(query,new Object[]{uploadForm.getTranId()},new RowMapper() {
			    public Object mapRow(final ResultSet rset,final int idVal) throws SQLException {
			    final TransactionVB tranVB=new TransactionVB();
				tranVB.setTransactionNo(rset.getString("TRANSACTION_NO"));
				tranVB.setTransactionNo2(rset.getString("TRANSACTION_NO"));
				tranVB.setTotalRecords(rset.getString("TOTAL_RECORDS"));
				tranVB.setDuplicates(rset.getString("DUPLICATES"));
				tranVB.setChequeexists(rset.getString("CHEQUE_EXISTS"));
				tranVB.setChequenotexists(rset.getString("CHEQUE_NOT_EXISTS"));
				tranVB.setMatched(rset.getString("MATCHED"));
				tranVB.setPending(rset.getString("PENDING"));
				tranVB.setProcessed(rset.getString("PROCESSED"));
				tranVB.setTransdate(rset.getString("TDATE"));
				tranVB.setExists(rset.getString("COUNTS"));
				tranVB.setCheckStatus(rset.getString("STATUS")==null?"":rset.getString("STATUS"));
				if(param.equalsIgnoreCase("RT"))
					tranVB.setPaymentRecords(rset.getString("PAYMENT")==null?"0":rset.getString("PAYMENT"));
				tranVB.setReversals(rset.getString("REVERSALS"));
				tranVB.setInvalid(rset.getString("INVALID"));
				return tranVB;
			}
			});
			
		}
	   
	   else {
		   LogManager.push("Enter getTransactedDetails()");
		   list = this.mytemplate.query(query,new Object[]{uploadForm.getTranId()},new RowMapper() {
			    public Object mapRow(final ResultSet rset,final int idVal) throws SQLException {
			    final TransactionVB tranVB=new TransactionVB();
				tranVB.setTransactionNo(rset.getString("TRANSACTION_NO"));
				tranVB.setTransactionNo2(rset.getString("TRANSACTION_NO"));
				tranVB.setTotalRecords(rset.getString("TOTAL_RECORDS"));
				tranVB.setDuplicates(rset.getString("DUPLICATES"));
				tranVB.setChequeexists(rset.getString("CHEQUE_EXISTS"));
				tranVB.setChequenotexists(rset.getString("CHEQUE_NOT_EXISTS"));
				tranVB.setMatched(rset.getString("MATCHED"));
				tranVB.setPending(rset.getString("PENDING"));
				tranVB.setProcessed(rset.getString("PROCESSED"));
				tranVB.setTransdate(rset.getString("TDATE"));
				tranVB.setExists(rset.getString("COUNTS"));
				tranVB.setCheckStatus(rset.getString("STATUS")==null?"":rset.getString("STATUS"));
				if(param.equalsIgnoreCase("R"))
					tranVB.setPaymentRecords(rset.getString("PAYMENT")==null?"0":rset.getString("PAYMENT"));
				tranVB.setReversals(rset.getString("REVERSALS"));
				tranVB.setInvalid(rset.getString("INVALID"));
				return tranVB;
			}
			});
		}	
		return list;	
	
	}

	public int getReProcess(String transId, String bankId) {
		Connection con;
		int count=0;
		CallableStatement cs ;
		
		//String status=(String)this.mytemplate.queryForObject("select processed from transaction_details where transaction_no="+transId, String.class);	
		//if(status.equalsIgnoreCase("N"))	
		//{
		try{
		
		this.mytemplate.update("update transaction_details set processed='P' where transaction_no="+transId);	
		con = this.mytemplate.getDataSource().getConnection();
		cs = con.prepareCall("{ call update_brnew(?,?,?)}");
		cs.setString(1, bankId);
		cs.setString(2, transId);
		cs.registerOutParameter(3, Types.INTEGER);
		LogManager.push("getReProcess Procedure starting.......");
		cs.execute();
		LogManager.push("getReProcess Procedure end.......");
		count=cs.getInt(3);
		}catch(Exception e)
		{
			LogManager.debug(e);
		}
		finally{
			this.mytemplate.update("update transaction_details set processed='N' where processed='P'");
		}
		//}
		return count; 
	}	

}
