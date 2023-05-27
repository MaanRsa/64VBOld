package com.maan.upload3;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.RowMapper;

import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;

public class UploadDAOImpl extends CommonBaseDAOImpl implements UploadDAO {

	public int i=1;
	StringBuffer sb = new StringBuffer("");
	
	public List getTransactionDetails(final String transId,int typeId) throws CommonBaseException{
		LogManager.info("UploadDAOImpl getTransactionDetails() Method - Enter");
		List list = new ArrayList();
		
		final String query2 = getQuery(DBConstants.TRANSACTION_RECEIPTNO_DETAILS);
		final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_RECEIPT_ERRORCOUNT);
		Object[] arg1;
		arg1 = new Object[1];
		arg1[0] = transId;	
		if("104".equalsIgnoreCase(String.valueOf(typeId))){
		int count=this.mytemplate.queryForInt(query2,arg1);
		int ecount=this.mytemplate.queryForInt(query3,arg1);
		list.add(count+ecount);
		list.add(ecount);
		list.add(count);
		
		final String queryrectdetails="SELECT COUNT(*) AS COUNT FROM TEMP_RECEIPT_NUMBERS WHERE TRANS_SOURCE!='RECT' AND BATCHID="+transId+" UNION ALL SELECT COUNT(*)  FROM RECEIPT_NUMBERS RN WHERE (RN.BANK_NO IS NOT NULL or RN.MANUAL_UPDATE IS NOT NULL) AND BATCHID="+transId+" UNION ALL SELECT COUNT(*)  FROM RECEIPT_NUMBERS RN WHERE 'RECT'=RN.TRANS_SOURCE AND (RN.BANK_NO IS NULL AND RN.MANUAL_UPDATE IS NULL) AND BATCHID="+transId;
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
		arg[0] = transId;
		arg[1] = "RN";
		arg[2] = count+ecount;
		arg[3] = ecount;
				
		//insertion of RECEIPT transaction
		//String query7 = getQuery(DBConstants.TRANSACTION_RECEIPT_INSERT3);
		//this.mytemplate.update(query7,arg);
		LogManager.push(list.size()+":"+list);
		LogManager.push("End Of Line Upload3");
		}
		else if(109==typeId){
			Map res=this.mytemplate.queryForMap(getQuery(DBConstants.POLICY_NUMBERS_COUNT),new Object[]{transId,transId,transId,transId});
			list.add(res.get("REALIZED"));
			list.add(res.get("RETURNED"));
			list.add(res.get("NOT_KNOWN"));
			list.add(res.get("NOT_AVAILABLE_IN_XGEN"));
	    }
		return list;
	}
	
	public List getNotMatchedTransactionDetails(final String transId) throws CommonBaseException{
		LogManager.info("UploadDAOImpl getTransactionDetails() Method - Enter");
		
		final String query2 = getQuery(DBConstants.TRANSACTION_RECEIPTNO_DETAILS);
		final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_RECEIPT_ERRORCOUNT);
		Object[] arg1;
		arg1 = new Object[1];
		arg1[0] = transId;	
		List list = new ArrayList();
		int count=this.mytemplate.queryForInt(query2,arg1);
		int ecount=this.mytemplate.queryForInt(query3,arg1);
		list.add(count+ecount);
		list.add(ecount);
		list.add(count);
		final String queryrectdetails="SELECT COUNT(*) AS COUNT FROM TEMP_RECEIPT_NUMBERS WHERE TRANS_SOURCE!='RECT' AND BATCHID="+transId+" UNION ALL SELECT COUNT(*)  FROM RECEIPT_NUMBERS RN WHERE RN.BANK_NO IS NOT NULL AND BATCHID="+transId+" UNION ALL SELECT COUNT(*)  FROM RECEIPT_NUMBERS RN WHERE 'RECT'=RN.TRANS_SOURCE AND RN.BANK_NO IS NULL AND BATCHID="+transId;
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
		arg[0] = transId;
		arg[1] = "RN";
		arg[2] = count+ecount;
		arg[3] = ecount;
				
		//insertion of RECEIPT transaction
		//String query7 = getQuery(DBConstants.TRANSACTION_RECEIPT_INSERT3);
		//this.mytemplate.update(query7,arg);
		//LogManager.push(list.size()+":"+list);
		return list;
	}
	public void updateReceiptsDetail(final String transId,String realizeStatus) throws CommonBaseException{
		LogManager.info("UploadDAOImpl getReceiptsDetail() Method - Enter success");
		LogManager.push("UploadDAOImpl getReceiptsDetail() Method - Enter success");
		
		 if(realizeStatus.equalsIgnoreCase("Y")){
		    	this.mytemplate.update(getQuery(DBConstants.UPDATE_RECEIPTS_MANUAL),new Object[]{transId});
		    }
		//Query to update values of RECEIPT NUMBERS table
	    final String query1="UPDATE RECEIPT_NUMBERS RN SET RN.RECEIPT_DATE=(SELECT RECEIPT_DATE FROM" +
	    		" RECEIPT_MASTER R WHERE R.RECEIPT_NO=RN.RECEIPT_NO AND (R.BANK_NO IS NOT NULL OR MANUAL_UPDATE IS NOT NULL))," +
	    		"RN.PAYMENT_METHOD=(SELECT PAYMENT_TYPE FROM RECEIPT_MASTER R WHERE R.RECEIPT_NO=" +
	    		"RN.RECEIPT_NO AND (R.BANK_NO IS NOT NULL OR MANUAL_UPDATE IS NOT NULL)) ,RN.CHEQUE_NO=(SELECT CHEQUE_NO FROM " +
	    		"RECEIPT_MASTER R WHERE R.RECEIPT_NO=RN.RECEIPT_NO AND (R.BANK_NO IS NOT NULL OR MANUAL_UPDATE IS NOT NULL))," +
	    		"RN.CHEQUE_DATE=(SELECT CHEQUE_DATE FROM RECEIPT_MASTER R WHERE " +
	    		"R.RECEIPT_NO=RN.RECEIPT_NO AND (R.BANK_NO IS NOT NULL OR MANUAL_UPDATE IS NOT NULL)),RN.CHEQUE_AMOUNT=(SELECT AMOUNT " +
	    		"FROM RECEIPT_MASTER R WHERE R.RECEIPT_NO=RN.RECEIPT_NO AND (R.BANK_NO IS NOT NULL OR MANUAL_UPDATE IS NOT NULL)) " +
	    		",RN.BANK_NO=(SELECT BANK_NO FROM RECEIPT_MASTER R WHERE R.RECEIPT_NO=RN.RECEIPT_NO " +
	    		"AND (R.BANK_NO IS NOT NULL OR MANUAL_UPDATE IS NOT NULL))  WHERE RN.TRANS_SOURCE='RECT' AND RN.BATCHID="+transId;
		LogManager.push("Query to update values of RECEIPT_NUMBERS TABLE1:"+query1);
	    this.mytemplate.update(query1);
	    /*final String query2 = "UPDATE RECEIPT_NUMBERS RN SET MANUAL_UPDATE=(SELECT RM.MANUAL_UPDATE FROM RECEIPT_MASTER RM WHERE RM.RECEIPT_NO=RN.RECEIPT_NO AND RN.BATCHID="+transId+"), RN.REALISATION_STATUS=" +
	    		"(SELECT TYPE_CRDR FROM CITI_BANK C WHERE C.BANK_NO=RN.BANK_NO " +
	    		"AND C.RECEIPT_SL_NO IS NOT NULL UNION SELECT H.DR_CR FROM HDFC_BANK H" +
	    		" WHERE H.BANK_NO=RN.BANK_NO AND H.RECEIPT_SL_NO IS NOT NULL UNION SELECT B.DEBIT_CREDIT FROM HSBC_BANK B " + 
	    		" WHERE B.BANK_NO = RN.BANK_NO AND B.RECEIPT_SL_NO IS NOT NULL UNION SELECT S.CR_DR FROM SCB_BANK S" +
	    		" WHERE S.BANK_NO=RN.BANK_NO AND S.RECEIPT_SL_NO IS NOT NULL UNION SELECT A.TYPE FROM AXIS_BANK A" +
	    		" WHERE A.BANK_NO=RN.BANK_NO AND A.RECEIPT_SL_NO IS NOT NULL ), " +
	    		"RN.REALISATION_DATE=(SELECT C.DEPOSIT_DATE FROM CITI_BANK C " +
	    		"WHERE C.BANK_NO=RN.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL UNION SELECT B.DEPOSIT_DATE " + 
	    		" FROM HSBC_BANK B WHERE B.BANK_NO = RN.BANK_NO AND B.RECEIPT_SL_NO IS NOT NULL " +
	    		"UNION SELECT H.DEPOSIT_DATE FROM HDFC_BANK H WHERE H.BANK_NO=RN.BANK_NO " +
	    		"AND H.RECEIPT_SL_NO IS NOT NULL UNION SELECT S.DEPOSIT_DATE FROM SCB_BANK S WHERE S.BANK_NO=RN.BANK_NO " +
	    		"AND S.RECEIPT_SL_NO IS NOT NULL UNION SELECT A.DEPOSIT_DATE FROM AXIS_BANK A WHERE A.BANK_NO=RN.BANK_NO " +
	    		"AND A.RECEIPT_SL_NO IS NOT NULL )," +
	    		"RN.CHEQUE_RETURN_REASON=(SELECT NARRATION FROM CITI_BANK C WHERE C.BANK_NO=RN.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL " +
	    		"UNION SELECT H.RETURN_RSN FROM HDFC_BANK H WHERE H.BANK_NO=RN.BANK_NO " +
	    		"AND H.RECEIPT_SL_NO IS NOT NULL UNION SELECT B.RETURN_REASON FROM HSBC_BANK B " + 
	    		" WHERE B.BANK_NO = RN.BANK_NO AND B.RECEIPT_SL_NO IS NOT NULL UNION SELECT S.REASON FROM SCB_BANK S WHERE S.BANK_NO=RN.BANK_NO " +
	    		"AND S.RECEIPT_SL_NO IS NOT NULL UNION SELECT A.RETURN_REASON FROM AXIS_BANK A WHERE A.BANK_NO=RN.BANK_NO " +
	    		"AND A.RECEIPT_SL_NO IS NOT NULL)" +
	    		"WHERE RN.TRANS_SOURCE='RECT' AND RN.BATCHID="
				+ transId;
	    LogManager.push("Query to update values of RECEIPT_NUMBERS TABLE2:"+query2);
	    this.mytemplate.update(query2);*/
	    try {
		    Connection con = this.mytemplate.getDataSource().getConnection();
		    CallableStatement cs = con.prepareCall("{ call Update_receipt_No(?,?)}");
			cs.setString(1, transId);
			cs.registerOutParameter(2, Types.VARCHAR);
			LogManager.info("call Update_receipt_No Procedure starting.......");
			cs.execute();
			LogManager.info("call Update_receipt_No Procedure end....... | Result==>" + cs.getString(2));
			
		}
	    catch(Exception exception) {
	    	LogManager.debug(exception);
	    }
	    updateTempRecords(transId);
	    
	}
	public List getReceiptsDetail(final String transId) throws CommonBaseException{
		List list=null;
		try {
			LogManager.info("UploadDAOImpl getReceiptsDetail() Method - Enter");
			//Query to retireve Receipts detail
			//String query = "SELECT RECEIPT_NO,MANUAL_REMARKS,to_char( RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE, TRANS_SOURCE, PAYMENT_METHOD, CHEQUE_NO,to_char( CHEQUE_DATE,'DD/MM/YYYY') CHEQUE_DATE, CHEQUE_AMOUNT, REALISATION_STATUS,MANUAL_UPDATE,to_char( REALISATION_DATE,'DD/MM/YYYY') REALISATION_DATE, CHEQUE_RETURN_CODE, CHEQUE_RETURN_REASON, TRANSACTION_ID, BANK_NO, UPLOAD_DATE, UPLOAD_TIME, VALIDATE_STATUS, ACTIVE, BATCHID," +
			String query = "Select * from (SELECT RECEIPT_NO,MANUAL_REMARKS,to_char( RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE, TRANS_SOURCE, PAYMENT_METHOD, CHEQUE_NO,to_char( CHEQUE_DATE,'DD/MM/YYYY') CHEQUE_DATE, CHEQUE_AMOUNT, REALISATION_STATUS,MANUAL_UPDATE,to_char( REALISATION_DATE,'DD/MM/YYYY') REALISATION_DATE, CHEQUE_RETURN_CODE, CHEQUE_RETURN_REASON, TRANSACTION_ID, BANK_NO, UPLOAD_DATE, UPLOAD_TIME, VALIDATE_STATUS, ACTIVE, BATCHID," +
					" (select ACCOUNT_NAME FROM BANK_ACCOUNT_CODE WHERE BANK_ACCOUNT_CODE=(SELECT distinct BANK_CODE FROM RECEIPT_MASTER R WHERE R.RECEIPT_NO=RN.RECEIPT_NO))BANK_NAME,"
					+ " (SELECT distinct BANK_CODE FROM RECEIPT_MASTER M WHERE M.RECEIPT_NO=RN.RECEIPT_NO) BANK_CODE,"
					+ " (SELECT distinct STATUS FROM RECEIPT_MASTER M WHERE M.RECEIPT_NO=RN.RECEIPT_NO) PAYMENT_RECEIPT"
					+ " FROM RECEIPT_NUMBERS RN WHERE BATCHID="
					+ transId
					+ " AND (BANK_NO IS NOT NULL OR RN.MANUAL_UPDATE IS NOT NULL) "
					+ " ) A Left outer join"
					+ " (Select TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,TO_CHAR(POST_DT,'DD/MM/YYYY') CREDIT_DEBIT_DATE, DRAWEE_BK DRAWEE_BANK, RN.RECEIPT_NO From Hdfc_Bank Hb,Receipt_Numbers Rn,Receipt_Master Rm"
					+ " Where   Rn.Batchid = "+ transId +" And  Hb.Receipt_Sl_No=Rm.Receipt_Sl_No And"
					+ " Hb.Bank_No=Rm.Bank_No And Rm.Receipt_No=Rn.Receipt_No"
					+ " union all"
					+ " Select TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,TO_CHAR(CREDIT_DEBIT_DATE,'DD/MM/YYYY') CREDIT_DEBIT_DATE, BANK_NAME DRAWEE_BANK, RN.RECEIPT_NO From CITI_BANK Hb,Receipt_Numbers Rn,Receipt_Master Rm"
					+ " Where   Rn.Batchid = "+ transId +" And  Hb.Receipt_Sl_No=Rm.Receipt_Sl_No And"
					+ " Hb.Bank_No=Rm.Bank_No And Rm.Receipt_No=Rn.Receipt_No"
					+ " union all"
					+ " Select TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,TO_CHAR(CREDIT_DEBIT_DT,'DD/MM/YYYY') CREDIT_DEBIT_DATE, DRAWEE_BANK DRAWEE_BANK, RN.RECEIPT_NO From SCB_BANK Hb,Receipt_Numbers Rn,Receipt_Master Rm"
					+ " Where   Rn.Batchid = "+ transId +" And  Hb.Receipt_Sl_No=Rm.Receipt_Sl_No And"
					+ " Hb.Bank_No=Rm.Bank_No And Rm.Receipt_No=Rn.Receipt_No"
					+ " union all"
					+ " Select TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,TO_CHAR(CR_DATE,'DD/MM/YYYY') CREDIT_DEBIT_DATE, DRN_BK DRAWEE_BANK, RN.RECEIPT_NO From AXIS_BANK Hb,Receipt_Numbers Rn,Receipt_Master Rm"
					+ " Where   Rn.Batchid = "+ transId +" And  Hb.Receipt_Sl_No=Rm.Receipt_Sl_No And"
					+ " Hb.Bank_No=Rm.Bank_No And Rm.Receipt_No=Rn.Receipt_No"
					+ " union all"
					+ " Select TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,TO_CHAR(POST_DATE,'DD/MM/YYYY') CREDIT_DEBIT_DATE, DRAWEE_BANK DRAWEE_BANK, RN.RECEIPT_NO From HSBC_BANK Hb,Receipt_Numbers Rn,Receipt_Master Rm"
					+ " Where   Rn.Batchid = "+ transId +" And  Hb.Receipt_Sl_No=Rm.Receipt_Sl_No And"
					+ " Hb.Bank_No=Rm.Bank_No And Rm.Receipt_No=Rn.Receipt_No) b"
					+ " on A.RECEIPT_NO=b.RECEIPT_NO"
					+ " ORDER BY REALISATION_STATUS DESC";
			LogManager.push("query for receiptdetails:" + query);
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset, final int args)
						throws SQLException {
					UploadVB uVB = new UploadVB();
					String bankNo=rset.getString("BANK_NO")==null?"":rset.getString("BANK_NO");
					String manual=rset.getString("MANUAL_UPDATE")==null?"":rset.getString("MANUAL_UPDATE");
					uVB.setReceiptNumber(rset.getString("RECEIPT_NO"));
					uVB.setReceiptDate(rset.getString("RECEIPT_DATE"));
					if (rset.getString("TRANS_SOURCE").trim().equalsIgnoreCase(
							"RECT")) {
						uVB.setTransSource("Receipt");
					} else {
						uVB.setTransSource(rset.getString("TRANS_SOURCE"));
					}
					String paymentmode=rset.getString("PAYMENT_METHOD")==null?"":rset.getString("PAYMENT_METHOD");
					if (paymentmode.equalsIgnoreCase("CHQ")) {
						uVB.setPaymentMethod("Cheque");
					} else {
						uVB.setPaymentMethod(paymentmode);
					}
					uVB.setChequeNumber(rset.getString("CHEQUE_NO"));
					uVB.setChequeDate(rset.getString("CHEQUE_DATE"));
					uVB.setChequeAmount(rset.getString("CHEQUE_AMOUNT"));
					uVB.setManualRemarks(rset.getString("MANUAL_REMARKS")==null?"":rset.getString("MANUAL_REMARKS"));
					uVB.setDraweeBankName(rset.getString("DRAWEE_BANK")==null?"":rset.getString("DRAWEE_BANK"));
					uVB.setDepositDate(rset.getString("DEPOSIT_DATE")==null?"":rset.getString("DEPOSIT_DATE"));
					uVB.setDepositCreditDate(rset.getString("CREDIT_DEBIT_DATE")==null?"":rset.getString("CREDIT_DEBIT_DATE"));
					if(rset.getString("REALISATION_STATUS")!=null)
					{
						if (rset.getString("REALISATION_STATUS").trim()
								.equalsIgnoreCase("C")) {
							uVB.setRealisationStatus("Realized");
						} else if (rset.getString("REALISATION_STATUS").trim()
								.equalsIgnoreCase("D")) {
							uVB.setRealisationStatus("Returned");
						}
						else if (rset.getString("REALISATION_STATUS").trim()
								.equalsIgnoreCase("RTN")) {
							uVB.setRealisationStatus("Returned");
						}
						else if (rset.getString("REALISATION_STATUS").trim()
								.equalsIgnoreCase("COL")) {
							uVB.setRealisationStatus("Realized");
						}
						
					}else if(manual.trim().equalsIgnoreCase("Y")){
						uVB.setRealisationStatus("Realized");
					}
					else
					{
						
						if(bankNo.equalsIgnoreCase("-88888"))
						{
				          uVB.setRealisationStatus("Reversed");
						}
						else
						{
							uVB.setRealisationStatus("Not known");
						}
			            
					}
					uVB.setRealisationDate(rset.getString("REALISATION_DATE")==null?"":rset.getString("REALISATION_DATE"));
					uVB.setChequeReturnReason(rset
							.getString("CHEQUE_RETURN_REASON"));
					uVB.setDateTime(rset.getString("UPLOAD_DATE") + " "
							+ rset.getString("UPLOAD_TIME"));
					uVB.setBankNo(bankNo.equalsIgnoreCase("-88888")?"":rset.getString("BANK_NO"));
					uVB.setBankCode(rset.getString("BANK_CODE")==null?"":rset.getString("BANK_CODE"));
					uVB.setBankName(rset.getString("BANK_NAME")==null?"":rset.getString("BANK_NAME"));
					if(bankNo.equalsIgnoreCase("-88888")){
					uVB.setPaymentReceipt(rset.getString("PAYMENT_RECEIPT")==null?"":rset.getString("PAYMENT_RECEIPT"));
					}
					i++;
					return uVB;
				}

			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogManager.push("Exception in getReceiptsDetail() for Receipt No.s Upload");
		}		
	   return list;
		
	}
	public List getReceiptsDetailNotMatched(final String transId) throws CommonBaseException{
		LogManager.info("UploadDAOImpl getReceiptsDetail() Method - Enter");
		//Query to retireve Receipts detail
	 	String query="SELECT RECEIPT_NO,to_char( RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE, TRANS_SOURCE, PAYMENT_METHOD, CHEQUE_NO,to_char( CHEQUE_DATE,'DD/MM/YYYY') CHEQUE_DATE, CHEQUE_AMOUNT, REALISATION_STATUS,to_char(REALISATION_DATE,'DD/MM/YYYY') REALISATION_DATE, CHEQUE_RETURN_CODE, CHEQUE_RETURN_REASON, TRANSACTION_ID, BANK_NO, UPLOAD_DATE, UPLOAD_TIME, VALIDATE_STATUS, ACTIVE, BATCHID," +
	 			" (select ACCOUNT_NAME FROM BANK_ACCOUNT_CODE WHERE BANK_ACCOUNT_CODE=(SELECT distinct BANK_CODE FROM RECEIPT_MASTER R WHERE R.RECEIPT_NO=RN.RECEIPT_NO))BANK_NAME," +
				" (SELECT distinct BANK_CODE FROM RECEIPT_MASTER M WHERE M.RECEIPT_NO=RN.RECEIPT_NO) BANK_CODE " +
				 " FROM RECEIPT_NUMBERS RN WHERE BATCHID="+transId+" AND (BANK_NO IS NULL AND MANUAL_UPDATE IS NULL) ORDER BY REALISATION_STATUS DESC";
		LogManager.push("query for receiptdetails:"+query);
		List list = this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int args) throws SQLException {
				UploadVB uVB = new UploadVB();
				uVB.setReceiptNumber(rset.getString("RECEIPT_NO"));
				uVB.setReceiptDate(rset.getString("RECEIPT_DATE"));
				if(rset.getString("TRANS_SOURCE").trim().equalsIgnoreCase("RECT"))
				{
					uVB.setTransSource("Receipt");
				}
				else
				{ 
				uVB.setTransSource(rset.getString("TRANS_SOURCE")); 
				}
				uVB.setRealisationStatus("Not Known");
				uVB.setDateTime(rset.getString("UPLOAD_DATE")+" "+ rset.getString("UPLOAD_TIME"));
				uVB.setBankCode(rset.getString("BANK_CODE")==null?"":rset.getString("BANK_CODE"));
				uVB.setBankName(rset.getString("BANK_NAME")==null?"":rset.getString("BANK_NAME"));
			
				i++;
				return uVB;
			}
				
		});
	   return list;
		
	}
	// 64 VB
	public String processCsv(final String csvLoc,final String insCompanyId,HttpServletRequest request) throws CommonBaseException{
		LogManager.info("UploadDAOImpl processCsv() Method - Enter");
		final List list = new ArrayList();
		boolean duplicateStatus = false;
		
		
		int count = 0;
		int totColsHeader = 0;
		String result = "";
		String insertQuery = "INSERT INTO TEMP_RECEIPT_NUMBERS (";
		String data = "";
		String header = "";
		String columns = "";
		String transId = "";
		String[] col = new String[0];
		try{
			final FileInputStream fis = new FileInputStream(csvLoc);
			final BufferedInputStream bis = new BufferedInputStream(fis);
			final DataInputStream dis = new DataInputStream(bis);
			boolean cont = true;
			while(dis.available() != 0){
				count++;
				if(count==2){
					transId = String.valueOf(this.mytemplate.queryForInt(getQuery(DBConstants.TRANSACTION_ID_SEQ)));
				}
				if(count==1){
					header = dis.readLine();
					LogManager.info("Excel Header==>"+header);
					if((header.replaceAll("\t", "").trim()).length()<=0){
						LogManager.info("Inside Header Missing==>"+header);
						result="missing";
						break;
					}
					StringTokenizer cols = new StringTokenizer(header,"\t");
					while(cols.hasMoreTokens()){
						String temp = cols.nextToken().trim();
						if(temp==null || temp.length()==0){
							duplicateStatus=true;
							break;
						}
						if(list.contains(temp)){
							duplicateStatus=true;
							break;
						}else{
							list.add(temp.toLowerCase());
						}
					}
					if(duplicateStatus){
						result = "duplicate";
						break;
					}
					final Map dbDetails = getDbColumns(insCompanyId);
					Object[] colsList = (dbDetails.keySet()).toArray();
					LogManager.push("list.size()"+list.size()+"colsList.length--->"+colsList.length);
					col = new String[list.size()];
					boolean missingStatus = false;
					if(list.size()>colsList.length){
						result = "extra";
						break;
					}
					for(int i=0;i<colsList.length;i++){
						if(list.contains(colsList[i])){
							col[list.indexOf(colsList[i])] = (String)dbDetails.get(colsList[i]); 
						}else{
							missingStatus = true;
							break;
						}
					}
					if(missingStatus){
						result="missing";
						break;
					}else{
						if(col.length>1){
							columns = col[0];
							for(int i=1;i<col.length;i++){
								columns = columns + "," + col[i];
							}
						}
					}
					totColsHeader = (header.split("\t")).length;
					LogManager.info("totColsHeader----->>>"+totColsHeader);
					insertQuery = insertQuery + columns + ",BATCHID)";
					LogManager.info("insertQuery==>"+insertQuery);
				}else{
					data = dis.readLine();
					data = data.replaceAll("'", "''");
					data = data.replaceAll("\"", "");
					data = data.replaceAll("\t", "','");
					
					
					String temp = data.replaceAll("','", "");
					temp.replaceAll(" ", "");
					LogManager.info("Row Empty Checking length==>"+(temp.trim()).length());
					if((temp.trim()).length()==0){
						cont = false;
						break;
					}
					int testCount = 0;
					int length = 0;
					String temp1 = data; 
					while(temp1.contains("','")){
						if((testCount>=(totColsHeader-1)) && length==0){
							String substr = temp1.substring(0,temp1.indexOf("','"));
							length = substr.length();
						}
						temp1 = temp1.replaceFirst("','", "");
						testCount++;
					}
					LogManager.info("TotalColumns in Data----->" + (testCount+1)+"totColsHeader------->"+totColsHeader);
					if(testCount>(totColsHeader-1)){
						LogManager.info("Total length ----->" + (length+((totColsHeader-1)*3)));
						LogManager.info("Substring value ----->" + data.substring(0,(length+((totColsHeader-1)*3))));
						data = data.substring(0,(length+((totColsHeader-1)*3)));
						
					}					
					if(cont){
						
						final String query  = insertQuery + "VALUES('" + data + "',?)" ;
						this.mytemplate.update(query,new Object[]{transId});
					}					
				}
			}
			dis.close();
			bis.close();
			fis.close();
			if((result.trim()).length()<=0){
				LogManager.push(" UPDATE TEMP RECEIPT RECORDS ");
				updateTempRecords(transId);
			    moveToMaster(transId);
				
				/*if(sb.length()>0)
				{
					request.setAttribute("Transactionid", transId);
					
				}*/
				//insertIntoTransactionTable(transId,insCompanyId,csvLoc);
				result = transId;				
			}
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		LogManager.info("UploadDAOImpl processCsv() Method - Exit"+result);
		return result;
	}
	// 64 vb 
	private Map getDbColumns(final String insCompanyId){
		final Map result = new HashMap();
		LogManager.push("DB COLUMNS"+insCompanyId);
		final String query = getQuery(DBConstants.UPLOAD_DB_COLUMNS4);
		
		final List list = this.mytemplate.queryForList(query);
		LogManager.push("list.size()------------>"+list.size());
		for(int i=0;i<list.size();i++){
			final Map temp = (Map)list.get(i);
			result.put((String)temp.get("EXCEL_HEADER_NAME"),(String)temp.get("DB_COLUMN_NAME"));
		}
		LogManager.push("RESULT--->"+result);
		return result;
	}
	
	private void updateTempRecords(final String transId) throws CommonBaseException{
		try{
			
			
			String query1 = "UPDATE RECEIPT_NUMBERS SET UPLOAD_DATE=SYSDATE,UPLOAD_TIME=SYSDATE WHERE BATCHID="+transId;
			this.mytemplate.update(query1);
			
			//String query2;
			//query2 = getQuery(DBConstants.RECEIPT_DUPLICATE_UPDATE2);
			//this.mytemplate.update(query2);
			
			/*String query;
			query = getQuery(DBConstants.RECEIPT_DUPLICATE_UPDATE);
			this.mytemplate.update(query);*/
					
			
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e,CommonExceptionConstants.DATABASE_ERROR);
		}
	}
	
	private void moveToMaster(final String transId) throws CommonBaseException{
		
		try{
			String query = getQuery(DBConstants.MASTER_RECEIPT_INSERT);
			LogManager.push("Query insert-->"+query);
			this.mytemplate.execute(query);
			
			//query = getQuery(DBConstants.MASTER_DELETE);
			//this.mytemplate.update(query);
			
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e,CommonExceptionConstants.DATABASE_ERROR);
		}
	}
	
	public List getDbColumnsForDownload(final String insCompanyId,final String masterTable){
		final String query = getQuery(DBConstants.UPLOAD_DB_COLUMNS);
		final List list = this.mytemplate.queryForList(query,new Object[]{insCompanyId,masterTable});
		return list;
	}

	public void updatePolicyDetails(String transId) throws CommonBaseException {
		try{
	   this.mytemplate.update(getQuery(DBConstants.UPDATE_POLICY_NUMBERS),new Object[]{transId});
		}catch(Exception e){
			LogManager.debug(e);
		}
	}

	public List getPolicies(String transId, String status)
			throws CommonBaseException {
		return this.mytemplate.queryForList("select POLICY_NUMBER,BANK_NAME,RETURN_REASON,TRANS_SOURCE,RECEIPT_NO,INSTRUMENT_NO,INSTRUMENT_AMOUNT,STATUS,to_char(REALIZED_DATE,'dd/mm/yyyy') REALIZED_DATE,to_char(CHEQUE_DATE,'dd/mm/yyyy') CHEQUE_DATE,RETURN_REASON from policy_number where batchid=? and status=?",new Object[]{transId,status});
    }
	
}
