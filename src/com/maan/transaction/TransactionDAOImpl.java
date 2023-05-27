package com.maan.transaction;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;

public class TransactionDAOImpl extends CommonBaseDAOImpl implements TransactionDAO {

	String updateQry = "", selectQry = "";
	
	public List getTransactedDetails(final TransactionForm tForm) throws CommonBaseException
	{
		//final String query=getQuery(DBConstants.UPLOAD_GETRECORDSBASEDONSTATEDISTID);
		//final String query=getQuery(DBConstants.TRANSACTION_SEARCH_DETAILS);
		final String param=tForm.getTransaction();
		
		if(param.equalsIgnoreCase("R"))
			selectQry=getQuery(DBConstants.TRANSACTION_RECEIPT_DETAILS);
		else if(param.equalsIgnoreCase("CIT"))
			selectQry=getQuery(DBConstants.TRANSACTION_CITI_DETAILS);
		else if (param.equalsIgnoreCase("HDB"))
			selectQry=getQuery(DBConstants.TRANSACTION_HDFC_DETAILS);
		else if (param.equalsIgnoreCase("KOT"))
			selectQry=getQuery(DBConstants.TRANSACTION_KOTAK_DETAILS);
		else if (param.equalsIgnoreCase("HSB"))
			selectQry=getQuery(DBConstants.TRANSACTION_HSBC_DETAILS);
		else if (param.equalsIgnoreCase("SCB"))
			selectQry=getQuery(DBConstants.TRANSACTION_SCB_DETAILS);
		else if (param.equalsIgnoreCase("AXB"))
			selectQry=getQuery(DBConstants.TRANSACTION_AXB_DETAILS);
		else if(param.equalsIgnoreCase("RN"))
			selectQry=getQuery(DBConstants.TRANSACTION_RECEIPTNOS_DETAILS);
		else if(param.equalsIgnoreCase("RR"))
			selectQry=getQuery(DBConstants.TRANSACTION_RECEIPTREVERSALS_DETAILS);
		else if(param.equalsIgnoreCase("RT"))
			selectQry=getQuery(DBConstants.TRANSACTION_RECEIPT_DETAILS_TRANID);
		
		LogManager.push("Transact query::"+selectQry);
		
		List list=null;
		if(param.equalsIgnoreCase("RN"))
	   {
		 LogManager.push("Enter getTransactedDetails () REceipt NOs");
		 list = this.mytemplate.query(selectQry,new RowMapper() {
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
		 list = this.mytemplate.query(selectQry,new RowMapper() {
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
			list = this.mytemplate.query(selectQry,new Object[]{tForm.getTransactionNo()},new RowMapper() {
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
		   list = this.mytemplate.query(selectQry,new RowMapper() {
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
	
	/*public int getAllProcess(String bankId,String transId[]) throws CommonBaseException {
		LogManager.info("UploadDAOImpl getprocessdetail() Method - Enter");
		LogManager.push("Inside getProcess daoimpl:"+bankId);
		List list=getBankQueryData(bankId);
		final Map tempMap = (Map) list.get(0);
		String tableName = "", chequeNo = "", chequeAmt = "", chequeStatus = "";    
		
		tableName = ((String)tempMap.get("TABLE_NAME"));
		chequeNo = ((String)tempMap.get("CHEQUE_NO"));
		chequeAmt = ((String)tempMap.get("CHEQUE_AMT"));
		chequeStatus = ((String)tempMap.get("CHEQUE_STATUS"));
		
		//String tableName=String.valueOf(val.get("TABLE_NAME"));
		LogManager.push("Inside getProcess BANK daoimpl:"+bankId);
	    int processed=0;
	     String transquery="SELECT UNIQUE TRANSACTION_ID FROM "+tableName+" WHERE RECEIPT_SL_NO IS NULL";
		LogManager.push("Transactions to process:"+transquery);
		
		listtrans =this.mytemplate.query(transquery,new RowMapper() {
		public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			return rset.getString("TRANSACTION_ID");
		}} );
	    
	    
		//String transId[]=new String[listtrans.size()];
		for(int i=0;i<transId.length;i++)
		{
			LogManager.push("Processing for transaction:::"+transId[i]);
			   //  transId[i]=listtrans.get(i).toString();
				selectQry = "SELECT COUNT(*) FROM "+tableName+" WHERE BATCHID="+transId[i];
				Object result=this.mytemplate.queryForObject(selectQry,new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					return rset.getString(1);
				}} );
				String uploadCount=result.toString();
				//LogManager.push("bank id from "+result.toString());
				LogManager.push("upload count>>>>>"+uploadCount);
		
				if(bankId.equalsIgnoreCase("CIT")){
					updateQry="update " + tableName + " set CHEQUE_AMT=-(CHEQUE_AMT) where CHEQUE_AMT like '-%'and BATCHID ="+transId[i];
					LogManager.push("Update for min status:"+ updateQry);
					this.mytemplate.update(updateQry);
				}
				if(uploadCount.equalsIgnoreCase("0"))
				{
					LogManager.push("NO records");
					updateQry=getQuery(DBConstants.UPDATE_TRANSACTION_DETAILS);
					LogManager.push("Update for transacted status:"+ updateQry);
					this.mytemplate.update(updateQry,new Object[] {transId[i]});
					processed=0;
				}
				else
				{	
					//Added query 
					selectQry="SELECT MAX(R.RECEIPT_SL_NO) as RECEIPT, MAX(C.BANK_NO) BANKNO FROM "+tableName+ " C,RECEIPT_MASTER R  WHERE  C."+chequeNo+" = R.CHEQUE_NO AND C."+chequeAmt+" = R.AMOUNT AND R.BANK_NO IS NULL  AND R.BANK_CODE IN (SELECT   BANK_ACCOUNT_CODE   FROM   BANK_ACCOUNT_CODE  WHERE   BANK_ID = '"+bankId+"' AND STATUS = 'Y')   AND R.PAYMENT_TYPE = 'CHQ' AND R.TRANS_SOURCE='RECT' AND C.RECEIPT_SL_NO IS  NULL  AND C.BATCHID = "+transId[i]+" group by C.BANK_NO";
					LogManager.push("Query for recipt update select"+selectQry);
					int count1=0;
					int count2=0;
					try{ 						
						final List list1 = this.mytemplate.queryForList(selectQry);
						if(list1!=null && list1.size()>0){
							for(int j=0;j<list1.size();j++){
								final Map temp = (Map)list1.get(j);
								LogManager.push("Count :"+j+"::"+temp.get("RECEIPT")+"::"+temp.get("BANKNO") );
								String qury="update receipt_master set bank_no="+temp.get("BANKNO")+",transaction_bank_id="+transId[i]+" where receipt_sl_no="+temp.get("RECEIPT"); 
								//LogManager.push("qury"+j+">>"+qury);
								count1+=this.mytemplate.update(qury);
							}
						}
					}catch(Exception e){
						LogManager.fatal(e);
					}
					
					LogManager.push("count1:>>>"+count1);
					*//**Update for bank record**//*
					selectQry="SELECT MAX(R.RECEIPT_SL_NO) AS RECEIPT,C.BANK_NO AS BANKNO FROM RECEIPT_MASTER R,"+tableName+ " C WHERE C."+chequeNo+" = R.CHEQUE_NO AND C."+chequeAmt+" = R.AMOUNT AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankId+"' AND STATUS='Y')AND R.PAYMENT_TYPE='CHQ' AND R.BANK_NO IS NOT NULL AND C.RECEIPT_SL_NO IS NULL AND R.TRANS_SOURCE='RECT' AND R.TRANSACTION_BANK_ID ="+transId[i]+" AND C.BATCHID="+transId[i]+" group by c.bank_no" ;
					 LogManager.push("Query for bank update select"+selectQry);
					 try{
						final List list1 = this.mytemplate.queryForList(selectQry);
						if(list1!=null && list1.size()>0){
							for(int j=0;j<list1.size();j++){
								final Map temp = (Map)list1.get(j);
								LogManager.push("Count :"+j+"::"+temp.get("RECEIPT")+"::"+temp.get("BANKNO") );
								updateQry="update "+tableName+ " set receipt_sl_no="+temp.get("RECEIPT")+" where bank_no="+temp.get("BANKNO")+" and BATCHID="+transId[i]; 
								count2+=this.mytemplate.update(updateQry);
							}
						}
						}catch(Exception e){
							LogManager.fatal(e);
						}
						LogManager.push("count2:>>>"+count2);
					if(bankId.equalsIgnoreCase("CIT")){
						updateQry="update " + tableName + " set CHEQUE_AMT=-(CHEQUE_AMT) where TYPE_CRDR='D' and cheque_amt not like '-%' and BATCHID ="+transId[i];
                        LogManager.push("Update for min status:"+ updateQry);
						this.mytemplate.update(updateQry);
						
					}
					updateQry=getQuery(DBConstants.UPDATE_TRANSACTION_DETAILS);
					LogManager.push("Update for transacted status non duplicates:"+ updateQry);
					this.mytemplate.update(updateQry, new Object[] {transId[i]});
					
		             //GETTING COUNTS FOR NO. OF RECORDS MATCHED
					updateQry = "SELECT COUNT(*) FROM "+tableName+ " WHERE RECEIPT_SL_NO IS NOT NULL AND BATCHID="+transId[i];
					LogManager.push("Query for matched col:==>"+updateQry);
					int count=this.mytemplate.queryForInt(updateQry);
					
					updateQry = "UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+" FROM "+tableName+" C WHERE R.BANK_NO=C.BANK_NO AND C.BATCHID ="+transId[i]+" and R.TRANSACTION_BANK_ID ="+transId[i]+") where TRANSACTION_BANK_ID="+transId[i];
					LogManager.push("Update for  status in receipt:" + updateQry);
					this.mytemplate.update(updateQry);	
					processed += count;
					}
		}
		//start of actual cheque no and amt matching
		selectQry=getQuery(DBConstants.GET_ALL_BANKIDS);
		LogManager.push("BANKS POPUP:"+selectQry);
        List listbank;		
		listbank =this.mytemplate.query(selectQry,new RowMapper() {
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
				//LogManager.push("bank"+i+":"+listbank.get(k).toString());
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
			selectQry="SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT FROM "+tableName1[k]+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
			List values=this.mytemplate.queryForList(selectQry);
			for( int i=0;i<values.size();i++)
			{
				LogManager.push("values " + values.get(i));
				final Map tempMap2 = (Map) values.get(i);
				final String actualchequeno=((String)tempMap2.get("ACTUAL_CHEQUE_NO").toString());
				final String actualchequeamt=((String)tempMap2.get("ACTUAL_CHEQUE_AMT").toString());
				LogManager.push("actualchequeno"+actualchequeno+"actualchequeamt"+actualchequeamt);
				//Query to find actual cheque exists
				updateQry = "UPDATE "+tableName1[k]+" B SET RECEIPT_SL_NO=(SELECT MAX(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE R.CHEQUE_NO='"+actualchequeno+"' AND R.AMOUNT="+actualchequeamt+" AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
				this.mytemplate.update(updateQry);
				LogManager.push("Query for bank matched col BOTH NOT NULL:==>"+updateQry+"count-->");
				
				updateQry="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName1[k]+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO= '"+actualchequeno+"' AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND '"+actualchequeno+"'=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.PAYMENT_TYPE='CHQ' AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y')";
				this.mytemplate.update(updateQry);
				LogManager.push("Query for receipt matched col BOTH NOT NULL:==>"+updateQry+"count-->");
			}
            
	        //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE AMT NOT NULL
			//Query for not matched actual cheque amt:
			selectQry = "SELECT ACTUAL_CHEQUE_AMT FROM "+tableName1[k]+" WHERE ACTUAL_CHEQUE_NO IS NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
			List values2=this.mytemplate.queryForList(selectQry);
			for(int  i=0;i<values2.size();i++)
			{
				LogManager.push("values " +values2.get(i));
				final Map tempMap2 = (Map) values2.get(i);
				//final String actualchequeno=((String)tempMap2.get("ACTUAL_CHEQUE_NO"));
				final String actualchequeamt=((String)tempMap2.get("ACTUAL_CHEQUE_AMT").toString());
				LogManager.push("actualchequeamt"+actualchequeamt);
				//Query to match in bank table
				updateQry = "UPDATE "+tableName1[k]+" B SET RECEIPT_SL_NO=(SELECT MAX(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE B."+chequeNo1[k]+"=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
				this.mytemplate.update(updateQry);
				LogManager.push("Query for bank matched col CHEQUE AMT NOT NULL:==>"+updateQry+"count-->");
				
				//query to match in receipt
				updateQry="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName1[k]+" C WHERE C."+chequeNo1[k]+"=R.CHEQUE_NO AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO IS NULL AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' ";
				this.mytemplate.update(updateQry);
				LogManager.push("Query for receipt matched col CHEQUE AMT NOT NULL:==>"+updateQry+"count-->");
			}
			
			 //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE NO NOT NULL
			//Query for not matched actual cheque no:
			selectQry = "SELECT ACTUAL_CHEQUE_NO FROM "+tableName1[k]+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS  NULL AND RECEIPT_SL_NO IS NULL";
			List values3=this.mytemplate.queryForList(selectQry);
			for( int i=0;i<values3.size();i++)
			{
				LogManager.push("values " +values3.get(i));
				final Map tempMap2 = (Map) values3.get(i);
				final String actualchequeno=((String)tempMap2.get("ACTUAL_CHEQUE_NO"));
				//final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT"));
				LogManager.push("actualchequeno"+actualchequeno);
				//Query to match in bank table
			    updateQry ="UPDATE "+tableName1[k]+" B SET RECEIPT_SL_NO=(SELECT MAX(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND B."+chequeAmt1[k]+"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT IS NULL";
				this.mytemplate.update(updateQry );
				LogManager.push("Query for bank matched col CHEQUE NO NOT NULL:==>"+updateQry +"count-->");
				
				//query to match in receipt
				updateQry = "UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName1[k]+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND C."+chequeAmt1[k]+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO= '"+actualchequeno+"' AND C.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND '"+actualchequeno+"'=R.CHEQUE_NO AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ'";
				this.mytemplate.update(updateQry );
				LogManager.push("Query for receipt matched col CHEQUE NO NOT NULL:==>"+updateQry +"count-->");
			}
		 }
			try
			{
				updateQry ="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN C.STATUS IS NULL THEN TYPE_CRDR ELSE STATUS END) AS TYPE_CRDR FROM CITI_BANK C WHERE R.BANK_NO=C.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL  ) where r.bank_no is not null and r.bank_code in (select bank_account_code from bank_account_code where bank_id='CIT')";
                LogManager.push("Update for  status in receipt:"+ updateQry);
                //Runner.updation(updateQry);
                int i=this.mytemplate.update(updateQry);
                LogManager.push("i" + i);
                updateQry="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN C.STATUS IS NULL THEN DR_CR ELSE STATUS END) AS DR_CR FROM HDFC_BANK C WHERE R.BANK_NO=C.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL ) where r.bank_no is not null and r.bank_code in (select bank_account_code from bank_account_code where bank_id='HDB')";
                LogManager.push("Update for  status in receipt:"+ updateQry);
                i=this.mytemplate.update(updateQry);
                LogManager.push("i" + i);
                updateQry="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN C.STATUS IS NULL THEN CR_DR ELSE STATUS END) AS CR_DR FROM SCB_BANK C WHERE R.BANK_NO=C.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL ) where r.bank_no is not null and r.bank_code in (select bank_account_code from bank_account_code where bank_id='SCB')";
                LogManager.push("Update for  status in receipt:"+ updateQry);
                i=this.mytemplate.update(updateQry);
                LogManager.push("i" + i);
                updateQry="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN C.STATUS IS NULL THEN TYPE ELSE STATUS END) AS TYPE FROM AXIS_BANK C WHERE R.BANK_NO=C.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL ) where r.bank_no is not null and r.bank_code in (select bank_account_code from bank_account_code where bank_id='AXB')";
                LogManager.push("AXIS Update for  status in receipt:"+ updateQry);
                i=this.mytemplate.update(updateQry);
                LogManager.push("i" + i); 
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
         //	end of process actual cheque no and cheque amount 
		 // end of actual cheque no and amt matching
		return processed;
	}*/
	
	public int getAllProcess(String bankId,String transId[]) throws CommonBaseException {
		
		Connection con;
		int count=0;
		String temp="";
		for(int i=0;i<transId.length;i++)
		{
			temp=temp+","+transId[i];
		}
		String tranIds=temp.replaceFirst(",", "");
		
		this.mytemplate.update("update transaction_details set processed='P' where transaction_no in("+tranIds+")");
		CallableStatement cs ;
		try{
		con = this.mytemplate.getDataSource().getConnection();
		cs = con.prepareCall("{ call update_brnew(?,?,?)}");
		cs.setString(1, bankId);
		cs.setString(2, tranIds);
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
		return count;
	}
	
	public List getBankQueryData(String bankCode) throws CommonBaseException {
		List table =null;
		try{
			selectQry = getQuery(DBConstants.BANK_DETAILS_QUERY);
			table =this.mytemplate.queryForList(selectQry,new Object[]{bankCode});			
		}catch(Exception e){
			LogManager.fatal(e);
		}LogManager.push("TransDAOImpl getBankList() method exit");
		LogManager.logExit();
		LogManager.popRemove();
		return table;
	}

	public List getRecords(final TransactionForm tForm) throws CommonBaseException
	{
		//final String query=getQuery(DBConstants.UPLOAD_GETRECORDSBASEDONSTATEDISTID);
		//final String query=getQuery(DBConstants.TRANSACTION_SEARCH_DETAILS);
		String param=tForm.getBankId();
		String transId=tForm.getTransactionNo();
		LogManager.push("Param:transno:"+param+":"+transId);
		if(param.equalsIgnoreCase("R"))
			selectQry="select unique to_char(receipt_date,'DD/MM/YYYY') as rdate,count(*)  as rcount from receipt_master where  BATCHID='"+transId+"' group by receipt_date";
		else if(param.equalsIgnoreCase("CIT"))
			selectQry="select unique to_char(deposit_date,'DD/MM/YYYY')  as rdate,count(*)  as rcount from citi_bank where  BATCHID='"+transId+"' group by deposit_date";
		else if(param.equalsIgnoreCase("SCB"))
			selectQry="select unique to_char(DEPOSIT_DATE,'DD/MM/YYYY')  as rdate,count(*)  as rcount from SCB_BANK where  BATCHID='"+transId+"' group by DEPOSIT_DATE";
		else if(param.equalsIgnoreCase("AXB"))
			selectQry="select unique to_char(DEPOSIT_DATE,'DD/MM/YYYY')  as rdate,count(*)  as rcount from AXIS_BANK where  BATCHID='"+transId+"' group by DEPOSIT_DATE";
		else if(param.equalsIgnoreCase("HSB"))
			selectQry="select unique to_char(DEPOSIT_DATE,'DD/MM/YYYY')  as rdate,count(*)  as rcount from HSBC_BANK where  BATCHID='"+transId+"' group by DEPOSIT_DATE";
		else if(param.equalsIgnoreCase("KOT"))
			selectQry="select unique to_char(DEPOSIT_DATE,'DD/MM/YYYY')  as rdate,count(*)  as rcount from KOTAK_BANK where  BATCHID='"+transId+"' group by DEPOSIT_DATE";
		else
			selectQry="select unique to_char(deposit_date,'DD/MM/YYYY')  as rdate,count(*)  as rcount from hdfc_bank where  BATCHID='"+transId+"' group by deposit_date";
		
		LogManager.push("Enter getTransactedDetails()");
		List list = this.mytemplate.query(selectQry,new RowMapper() {
		public Object mapRow(final ResultSet rset,final int idVal) throws SQLException {
			final TransactionVB tranVB=new TransactionVB();
			tranVB.setRecordcounts(rset.getString("RCOUNT"));
			tranVB.setRecorddates(rset.getString("RDATE"));
			return tranVB;
		}
		});							
		return list;	
	}
	
	public void updateSelected(String checkedTransactNos, String uncheckedTransactNos) throws CommonBaseException {
		if(!uncheckedTransactNos.equalsIgnoreCase("")){
			//uncheckedTransactNos = uncheckedTransactNos.substring(1, uncheckedTransactNos.length()-1);
			LogManager.info("CHANGED " + uncheckedTransactNos);
			updateQry = "UPDATE TRANSACTION_DETAILS SET STATUS='' WHERE TRANSACTION_NO IN (" + uncheckedTransactNos + ")";
			LogManager.push(updateQry+"Uncheckd:"+uncheckedTransactNos);
	       this.mytemplate.update(updateQry);
	    }
		
	    if(!checkedTransactNos.equalsIgnoreCase("")){
	    	updateQry = "UPDATE TRANSACTION_DETAILS SET STATUS='Y' WHERE TRANSACTION_NO IN (" + checkedTransactNos + ")"; 
	    	
			LogManager.info("CHANGED " + checkedTransactNos);
	    	this.mytemplate.update(updateQry);
		    LogManager.push(updateQry+"Checkd:"+checkedTransactNos);
	    }
		
		/*if(!uncheckedTransactNos.equalsIgnoreCase("")){
			uncheckedTransactNos = uncheckedTransactNos.substring(1, uncheckedTransactNos.length()-1);
			LogManager.info("CHANGED " + uncheckedTransactNos);
			updateQry = getQuery(DBConstants.UPDATE_UPLOAD_UNCHECKED);
			LogManager.push(updateQry+"Uncheckd:"+uncheckedTransactNos);
	       this.mytemplate.update(updateQry, new Object[] {uncheckedTransactNos});
	    }
		
	    if(!checkedTransactNos.equalsIgnoreCase("")){
	    	updateQry = getQuery(DBConstants.UPDATE_UPLOAD_CHECKED); 
	    	checkedTransactNos = checkedTransactNos.substring(1, checkedTransactNos.length()-1);
			LogManager.info("CHANGED " + checkedTransactNos);
	    	this.mytemplate.update(updateQry, new Object[] {checkedTransactNos});
		    LogManager.push(updateQry+"Checkd:"+checkedTransactNos);
	    }*/
	}
	
	public void updateTransactions() throws CommonBaseException {
		updateQry = getQuery(DBConstants.UPDATE_UPLOAD_ALL);
	    this.mytemplate.update(updateQry);
	}
	
	public List getSelectedTransactions() throws CommonBaseException {	
		selectQry = getQuery(DBConstants.GET_SELECTED_TRANSACTION);
		List selected = this.mytemplate.queryForList(selectQry);
		return selected;
	}
	
	public List getTransactionDetails(String bid) throws CommonBaseException {		
		selectQry = getQuery(DBConstants.GET_UPLOADED_FILETYPE);
		Object result=this.mytemplate.queryForObject(selectQry,new Object[] {bid},new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			return rset.getString("FILE_TYPE");
		}} );
		
		//LogManager.push("bank id from "+result.toString());
		LogManager.push("FILE_TYPEo>>>>>"+result.toString());
		String fileType = result.toString();
		if(fileType.trim().equalsIgnoreCase("R"))
		{
			selectQry = getQuery(DBConstants.GET_TRANSACTION_DETAILS);
		}
		/*else if (fileType.trim().equalsIgnoreCase("CIT"))
		{
			query = "SELECT TO_CHAR(TRANSACTION_DATE,'DD/MM/YYYY')AS TDATE,TRANSACTION_NO,TOTAL_RECORDS,DUPLICATES,CHEQUE_EXISTS,CHEQUE_NOT_EXISTS,(SELECT COUNT(*) FROM CITI_BANK R1 WHERE R1.RECEIPT_SL_NO IS NOT NULL AND R1.BATCHID=T.TRANSACTION_NO AND RECEIPT_SL_NO!=-99999 AND (R1.CHEQUE_NO IS NOT NULL AND R1.CHEQUE_NO <> ' ')) AS MATCHED,(SELECT COUNT(*) FROM CITI_BANK R2 WHERE R2.RECEIPT_SL_NO IS NULL AND R2.BATCHID=T.TRANSACTION_NO AND (R2.CHEQUE_NO IS NOT NULL OR R2.CHEQUE_NO<>' ')) AS PENDING,PROCESSED,(SELECT COUNT(*) FROM CITI_BANK R WHERE R.BATCHID=TRANSACTION_NO) AS COUNTS,STATUS,(SELECT COUNT(*) FROM DUP_CITI_BANK WHERE TRANSACTION_ID=T.TRANSACTION_NO AND STATUS NOT IN ('D','E','P')) AS INVALID,(SELECT COUNT(*) FROM CITI_BANK C1 WHERE RECEIPT_SL_NO=-99999 AND C1.BATCHID=T.TRANSACTION_NO) AS REVERSALS FROM TRANSACTION_DETAILS T WHERE FILE_TYPE='CIT' AND TRANSACTION_NO = "+bid;
			
		}
		else if(fileType.trim().equalsIgnoreCase("HDB"))
		{
			query = "SELECT TO_CHAR(TRANSACTION_DATE,'DD/MM/YYYY')AS TDATE,TRANSACTION_NO,TOTAL_RECORDS,DUPLICATES,CHEQUE_EXISTS,CHEQUE_NOT_EXISTS,(SELECT COUNT(*) FROM HDFC_BANK R1 WHERE R1.RECEIPT_SL_NO IS NOT NULL AND R1.BATCHID=T.TRANSACTION_NO AND RECEIPT_SL_NO!=-99999 AND (R1.INSTRUMENT_NO IS NOT NULL AND R1.INSTRUMENT_NO NOT LIKE ' ')  ) AS MATCHED,(SELECT COUNT(*) FROM HDFC_BANK R2 WHERE R2.RECEIPT_SL_NO IS NULL AND R2.BATCHID=T.TRANSACTION_NO AND R2.INSTRUMENT_NO IS NOT NULL AND R2.INSTRUMENT_NO NOT LIKE ' ') AS PENDING,PROCESSED,(SELECT COUNT(*) FROM HDFC_BANK R WHERE R.BATCHID=TRANSACTION_NO) AS COUNTS,STATUS,(SELECT COUNT(*) FROM DUP_HDFC_BANK WHERE TRANSACTION_ID=T.TRANSACTION_NO AND STATUS NOT IN ('D','E','P')) AS INVALID,(SELECT COUNT(*) FROM HDFC_BANK C1 WHERE RECEIPT_SL_NO=-99999 AND C1.BATCHID=T.TRANSACTION_NO) AS REVERSALS FROM TRANSACTION_DETAILS T WHERE FILE_TYPE='HDB' AND TRANSACTION_NO = "+bid;
				
		}*/
		List selected = null;
		  LogManager.push("Enter getTransactionDetails()");
		  selected = this.mytemplate.query(selectQry, new Object[]{bid}, new RowMapper() {			 
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
					tranVB.setReversals(rset.getString("REVERSALS"));
					tranVB.setInvalid(rset.getString("INVALID"));
					return tranVB;
				}
			});
		  return selected;
	}

	public int getProcessCount() throws CommonBaseException {
		return this.mytemplate.queryForInt("select count(*) from transaction_details where processed='P'");
	}

	public List getPolicyNumbers() throws CommonBaseException {
		return this.mytemplate.queryForList("SELECT   TO_CHAR (TRANSACTION_DATE, 'DD/MM/YYYY') AS TDATE,TRANSACTION_NO,TOTAL_RECORDS, (SELECT   COUNT ( * ) FROM   POLICY_NUMBER PN WHERE   BATCHID = T.TRANSACTION_NO and UPPER(PN.STATUS)=UPPER('Realized')) AS Realized, (SELECT   COUNT ( * ) FROM   POLICY_NUMBER PN WHERE   BATCHID = T.TRANSACTION_NO and upper(pn.status)=upper('Returned')) AS Returned, (SELECT   COUNT ( * ) FROM   POLICY_NUMBER PN WHERE   BATCHID = T.TRANSACTION_NO and upper(pn.status)=upper('Not known')) AS Not_Known,(SELECT   COUNT ( * ) FROM   POLICY_NUMBER PN WHERE   BATCHID = T.TRANSACTION_NO and pn.status is null) as Not_available FROM   TRANSACTION_DETAILS T WHERE   FILE_TYPE = 'PN' ORDER BY   TRANSACTION_NO DESC");
	}
}
