package com.maan.user.search;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.use.CommonDispatchForm;


public class SearchDAOImpl extends CommonBaseDAOImpl implements SearchDAO {

	public int i=1;
	public List getSearchList(SearchFormBean sbean) throws CommonBaseException {
		String query="";
		String query3="";
		String bankcode=sbean.getBankId();
		String searchIn=sbean.getSearchIn();
		String formChequeNo=sbean.getChequeNo();
		String formreceipt=sbean.getReceiptNo();
		String transId=sbean.getTransactionNo();
		LogManager.push("getSearchList search in:"+searchIn);
		String formChequeAmt=sbean.getChequeAmount();
				
		//String tableName=String.valueOf(val.get("TABLE_NAME"));
	    List list=null;
	    LogManager.push("transaction code"+transId+"formchequeno:"+formChequeNo);
		
		try{
			LogManager.push("transaction code"+transId+"formchequeno:"+formChequeNo);
			//SEARCH BY TRANSACTIONS
			//Search in all banks	
			
				String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y'";
				LogManager.push("BANKS POPUP:"+query1);
				List listbank;
				
				listbank =this.mytemplate.query(query1,new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					return rset.getString("BANK_ID");
				}} );
					
					final String tableName[]=new String[listbank.size()];
					final String chequeStatus[]=new String[listbank.size()];
					final String chequeNo[]=new String[listbank.size()];
					final String chequeAmt[]=new String[listbank.size()];
					final String bankName[]=new String[listbank.size()];
					final String receipt[]=new String[listbank.size()];
					final String reason[]=new String[listbank.size()];
					final String bankIds[]=new String[listbank.size()];
					for(int i=0;i<listbank.size();i++)
					{
					bankIds[i]=listbank.get(i).toString();
					LogManager.push("bank"+i+":"+listbank.get(i).toString());
					List blist=getBankQueryData(listbank.get(i).toString());
					final Map tempMap = (Map)blist.get(0);
					 tableName[i]=((String)tempMap.get("TABLE_NAME"));
					 chequeStatus[i]=((String)tempMap.get("CHEQUE_STATUS"));
					 reason[i]=((String)tempMap.get("REASON"));
					 chequeNo[i]=((String)tempMap.get("CHEQUE_NO"));
				 	 chequeAmt[i]=((String)tempMap.get("CHEQUE_AMT"));
				     bankName[i]=(String)tempMap.get("BANK_NAME");
					 receipt[i]=((String)tempMap.get("RECEIPT_NO"));
					 LogManager.push("<--"+tableName[i]+""+chequeStatus[i]+"-->");
					// sbean.setBankName((String)tempMap.get("BANK_NAME"));
					}
					
					//-----start---
					LogManager.push("Inside search code");
					String[] arg = new String[9];
					LogManager.push("inside blocks::::");
					//Search in bank statement 
				
					
						if(!formChequeNo.equalsIgnoreCase("") )
						{
							arg[0]="AND "+chequeNo[0]+" LIKE '"+formChequeNo+"%' ";
							arg[3]="AND "+chequeNo[1]+" LIKE '"+formChequeNo+"%' ";
							arg[6]="AND "+chequeNo[2]+" LIKE '"+formChequeNo+"%' ";
						 }
						else if(formChequeNo.equalsIgnoreCase(""))
						{
							arg[0]="";
							arg[3]="";
							arg[6]="";
						}
						if(!formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]="AND ("+chequeAmt[0]+"="+formChequeAmt+" OR "+chequeAmt[0]+"='-"+formChequeAmt+"' )";
							arg[4]="AND ("+chequeAmt[1]+"="+formChequeAmt+" OR "+chequeAmt[1]+"='-"+formChequeAmt+"')";
							arg[7]="AND ("+chequeAmt[2]+"="+formChequeAmt+" OR "+chequeAmt[2]+"='-"+formChequeAmt+"')";
						}
						else if (formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]="";
							arg[4]="";
							arg[7]="";
						}
						if(!formreceipt.equalsIgnoreCase(""))
						{
							arg[2]="AND "+receipt[0]+"='"+formreceipt+"' ";
							arg[5]="AND "+receipt[1]+"='"+formreceipt+"' ";
							arg[8]="AND "+receipt[2]+"='"+formreceipt+"' ";
						 }
						else if(formreceipt.equalsIgnoreCase(""))
						{
							arg[2]="";
							arg[5]="";
							arg[8]="";
						 }
						query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
					+ tableName[0]
					+ "') AS BANK, "
					+ chequeNo[0]
					+ ","
					+ chequeAmt[0]
					+ ",DEPOSIT_DATE,(CASE WHEN STATUS IS NULL THEN "
					+ chequeStatus[0]
					+ " ELSE STATUS END) AS "
					+ chequeStatus[0]
					+ ","
					+ receipt[0]
					+ ",T.BANK_NO||'- '||T.RECEIPT_SL_NO||'- ','BANK' FROM "
					+ tableName[0]
					+ " T WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
					+ arg[0]
					+ arg[1]
					+ arg[2]
					+ " AND "
					+ chequeStatus[0]
					+ " NOT LIKE 'H' " +
							"" +
					" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
					+ tableName[1]
					+ "') AS BANK,"
					+ chequeNo[1]
					+ ","
					+ chequeAmt[1]
					+ ",DEPOSIT_DATE,(CASE WHEN STATUS IS NULL THEN "
					+ chequeStatus[1]
					+ " ELSE STATUS END) AS "
					+ chequeStatus[1]
					+ ","
					+ receipt[1]
					+ ",T.BANK_NO||'- '||T.RECEIPT_SL_NO||'- ','BANK' FROM "
					+ tableName[1]
					+ " T WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
					+ arg[3]
					+ arg[4]
					+ arg[5]
					+ " AND "
					+ chequeStatus[1]
					+ " NOT LIKE 'H'" +
				   " UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
					+ tableName[2]
					+ "') AS BANK,"
					+ chequeNo[2]
					+ ","
					+ chequeAmt[2]
					+ ",DEPOSIT_DATE,(CASE WHEN STATUS IS NULL THEN "
					+ chequeStatus[2]
					+ " ELSE STATUS END) AS "
					+ chequeStatus[2]
					+ ","
					+ receipt[2]
					+ ",T.BANK_NO||'- '||T.RECEIPT_SL_NO||'- ','BANK' FROM "
					+ tableName[2]
					+ " T WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
					+ arg[6]
					+ arg[7]
					+ arg[8]
					+ " AND "
					+ chequeStatus[2]
					+ " NOT LIKE 'H' order by 1,4 DESC,2 ";

					
					LogManager.push("Query"+query);
					LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
					
                        String[] arg1=new String[3];
					if(!formChequeNo.equalsIgnoreCase("") )
					{
						arg1[0]=" AND R.CHEQUE_NO LIKE '"+formChequeNo+"%' ";
						
					 }
					else if(formChequeNo.equalsIgnoreCase(""))
					{
						arg1[0]="";
					}
					if(!formChequeAmt.equalsIgnoreCase("") )
					{
						arg1[1]="AND R.AMOUNT="+formChequeAmt+" ";
					}
					else if (formChequeAmt.equalsIgnoreCase("") )
					{
						arg1[1]="";
					}
					if(!formreceipt.equalsIgnoreCase(""))
					{
						arg1[2]="AND RECEIPT_NO='"+formreceipt+"' ";
					 }
					else if(formreceipt.equalsIgnoreCase(""))
					{
						arg1[2]="";
					 }
					//String query2="UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||'- ','RECEIPT' FROM RECEIPT_MASTER R WHERE  ( status is not null or cheque_status is not null ) "+arg1[0]+arg1[1]+arg1[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[0]+"' AND STATUS='Y')   UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT' FROM RECEIPT_MASTER R WHERE ( status is not null or cheque_status is not null ) "+arg1[0]+arg1[1]+arg1[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[1]+"' AND STATUS='Y') ORDER BY 2";
					String query2 = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
					+ bankIds[0]
					+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||'- ','RECEIPT',BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE  ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
					+ arg1[0]
					+ arg1[1]
					+ arg1[2]
					+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
					+ bankIds[0]
					+ "' AND STATUS='Y')   UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
					+ bankIds[1]
					+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT',BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
					+ arg1[0]
					+ arg1[1]
					+ arg1[2]
					+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
					+ bankIds[1] + "' AND STATUS='Y') " +
					"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
						+ bankIds[2]
						+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT',BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
						+ arg1[0]
						+ arg1[1]
						+ arg1[2]
						+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
						+ bankIds[2] + "' AND STATUS='Y')"+
						"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
						+ bankIds[3]
						+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT',BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
						+ arg1[0]
						+ arg1[1]
						+ arg1[2]
						+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
						+ bankIds[3] + "' AND STATUS='Y')"
						+"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
						+ bankIds[4]
									+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT',BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
									+ arg1[0]
									+ arg1[1]
									+ arg1[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[4] + "' AND STATUS='Y')"
						+"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
						+bankIds[5]
									+"')AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT',BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
									+arg1[0]
									+arg1[1]
									+arg1[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[5] + "' AND STATUS='Y')"+
					"ORDER BY 1,4 DESC,2";
					
					query3=query2;
				LogManager.push("Query"+query2);
				list = this.mytemplate.query(query2,  new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						sVB.setChequeNo(rset.getString(2)==null?"":rset.getString(2));
						sVB.setChequeAmount(rset.getString(3));
						sVB.setBankName(rset.getString(1));
						sVB.setReceiptNo(rset.getString(6));
						if((rset.getString(5)==null?"":rset.getString(5)).trim().equalsIgnoreCase("C"))
						{
							sVB.setRealisation("Realized");
						}
						else if((rset.getString(5)==null?"":rset.getString(5)).trim().equalsIgnoreCase("D"))
						{
							sVB.setRealisation("Returned");
						}
						else if((rset.getString("MANUAL_UPDATE")==null?"":rset.getString("MANUAL_UPDATE")).equalsIgnoreCase("Y"))
						{
							sVB.setRealisation("Realized");
						}
						else 
						{
							sVB.setRealisation("Not Known");
						}
						if((rset.getString("TRANS_SOURCE")==null?"":rset.getString("TRANS_SOURCE")).trim().equalsIgnoreCase("PYMT"))
						{
							sVB.setRealisation("Payment");
						}
						if((rset.getString("TRANS_SOURCE")==null?"":rset.getString("TRANS_SOURCE")).trim().equalsIgnoreCase("RECT") && 
								((rset.getString("BANK_NO")==null?"":rset.getString("BANK_NO")).trim().equalsIgnoreCase("-88888"))
								)
						{
							sVB.setRealisation("Reversal");
						}
						if((rset.getString("TRANS_SOURCE")==null?"":rset.getString("TRANS_SOURCE")).trim().equalsIgnoreCase("JRNL"))
						{
							sVB.setRealisation("Realized");
						}
						
						sVB.setDepositDate(rset.getString(4));
						sVB.setBankNo1(rset.getString(7));
						sVB.setFromtable(rset.getString(8));
						return sVB;
					}});
				
			//------end-------------
		
		
		}
		catch(Exception e)
		{
			LogManager.push("Exception In SearchDAOImpl - searchResult searchdao(): "+e);
			}
		LogManager.push("QUERY executed "+query3);
		
		return list; 
	
	}
	public List getPolicySearchList(SearchFormBean sbean) throws CommonBaseException {
		String query="";
		LogManager.push("getPolicySearchList Enter::");
		String searchOption=sbean.getSearchOption();
		String searchValue=sbean.getSearchValue();
		String searchStatus="";
		String search=sbean.getSearch();
		if(searchOption.equalsIgnoreCase("Policy No"))
			searchStatus="P";
		else if(searchOption.equalsIgnoreCase("cheque No"))
		    searchStatus="C";
		else if(searchOption.equalsIgnoreCase("Receipt No"))
			searchStatus="R";
		
		List list=null;
		LogManager.push("Search Option :"+searchOption +"Search Value "+searchValue);
		try{
			if(!search.equalsIgnoreCase("true"))
			{
			String tempQuery="update receipt_master set status=null where status in ('Y','N')";
			this.mytemplate.update(tempQuery);
			}
				query = getQuery(DBConstants.POLICY_SEARCH_MIVBDATA);
				String polNo="";
				if(searchValue.length()>10)
					polNo = searchValue.substring(0, 10);
				else
					polNo = searchValue;
				if("P".equalsIgnoreCase(searchStatus)){
					query = query+" POLICY_NO = '"+polNo+"' AND ROWNUM=1 and nvl(webservice_status,'X')!='E'";
				}
				if("C".equalsIgnoreCase(searchStatus)){
					query = query+" INSTRUMENT_NO = '"+searchValue+"' AND ROWNUM=1 and nvl(webservice_status,'X')!='E'";
				}
				if("R".equalsIgnoreCase(searchStatus)){
					query = query+" RECEIPT_NO = '"+searchValue+"' AND ROWNUM=1 and nvl(webservice_status,'X')!='E'";
				}
				LogManager.push("Query for search in POLICY_SEARCH_MIVBDATA ==> "+query);
				list =this.mytemplate.queryForList(query);
				
			if(list.size()<=0 && list.isEmpty()){
				LogManager.push("Data Not found in MI_VB_DATA ");
				query = getQuery(DBConstants.POLICY_SEARCH_FUNCTION);
				LogManager.push("Query for search in POLICY_SEARCH_FUNCTION ==> "+query);
				list =this.mytemplate.queryForList(query,new Object[]{searchValue,searchStatus});
			}
	
		}catch(Exception e){
			LogManager.fatal(e);
		}LogManager.push("getPolicySearchList Exit::");
		LogManager.logExit();
		LogManager.popRemove();
		return list;
	}
	
	/*
	*/
	public List getBankQueryData(String bankCode) throws CommonBaseException {
		 List table =null;
		try{
			final String query = getQuery(DBConstants.BANK_DETAILS_QUERY2);
			 table =this.mytemplate.queryForList(query,new Object[]{bankCode});
			
		}catch(Exception e){
			LogManager.fatal(e);
		}LogManager.push("UploadDAOImpl getBankQueryData() method exit");
		LogManager.logExit();
		LogManager.popRemove();
		return table;
	}
	public Map getBankList(SearchFormBean form) throws CommonBaseException {
		LogManager.push("SearchDAoImpl getBankList() method enter");
		LogManager.logEnter();
		Map result = new HashMap();
		try{
			final String query = getQuery(DBConstants.BANK_LIST);
			final List list = this.mytemplate.queryForList(query);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					final Map temp = (Map)list.get(i);
					result.put(temp.get("BANK_ID"), temp.get("BANK_NAME"));
				}
			}
		}catch(Exception e){
			LogManager.fatal(e);
		}
		LogManager.push("SearchDAoImpl getBankList() method exit");
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	
	}
	
	public void getReceiptDetail(ReceiptDetails cForm) throws CommonBaseException {
		LogManager.push("SearchDAoImpl getReceiptDetail() method enter");
		LogManager.logEnter();
		String value=cForm.getReceiptNo();
		String bankreceipt[]=value.split("-");
		LogManager.push("bankreceipt[1]:"+bankreceipt[1]);
		String query1="";
		String receiptNo="";
		String depositDate="";
		String matched="";
		String status="";
		String receiptDate="";
		String bankId="";
	    Map map;
	   /* if(bankreceipt[1].trim().equalsIgnoreCase(""))
	    {
	    	String qrycount="SELECT COUNT(*) FROM CITI_BANK WHERE BANK_NO="+bankreceipt[0];
	    	Object result1=this.mytemplate.queryForObject(qrycount,new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				return rset.getString(1);
				}} );
	      if(result1.toString().equalsIgnoreCase("0"))
	      {
	    	  bankId="HDB";
	      }
	      else
	      {
	    	  bankId="CIT";
	      }
	    //query1="SELECT unique B.BANK_ID FROM RECEIPT_MASTER A,BANK_ACCOUNT_CODE B WHERE A.BANK_CODE=B.BANK_ACCOUNT_CODE AND A.RECEIPT_NO = "+bankreceipt[0];
	    }
	    else
	    {*/
	    if(value.contains("-88888")){
	    	query1="SELECT unique B.BANK_ID FROM RECEIPT_MASTER A,BANK_ACCOUNT_CODE B WHERE A.BANK_CODE=B.BANK_ACCOUNT_CODE AND A.RECEIPT_SL_NO = '"+bankreceipt[2]+"'";
		     LogManager.push("RECEIPT POPUP:"+query1);
				Object result=this.mytemplate.queryForObject(query1,new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					return rset.getString("BANK_ID");
					}} );
				bankId=result.toString();
				bankreceipt[1]=bankreceipt[2];
	    }
	    else{
	     query1="SELECT unique B.BANK_ID FROM RECEIPT_MASTER A,BANK_ACCOUNT_CODE B WHERE A.BANK_CODE=B.BANK_ACCOUNT_CODE AND A.RECEIPT_SL_NO = '"+bankreceipt[1]+"'";
	     LogManager.push("RECEIPT POPUP:"+query1);
			Object result=this.mytemplate.queryForObject(query1,new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				return rset.getString("BANK_ID");
				}} );
			bankId=result.toString();
	    }
	    //}
		
		
		//LogManager.push("bank id from "+result.toString());
		LogManager.push("Bank no>>>>>"+bankId);
		
		List blist=getBankQueryData(bankId);
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
		final String reason=((String)tempMap.get("REASON"));
		final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
		final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
		try{
		
		LogManager.push("bankreceipt[1].trim()==>"+bankreceipt[1].trim());
		//bank ane receiptnos not null
		if( !(bankreceipt[1].trim().equalsIgnoreCase("")) && !(bankreceipt[0].trim().equalsIgnoreCase("")) || (value.contains("-88888")))
		{   
			LogManager.push("bank ane receiptnos not null");
			matched="Yes";
			receiptNo=bankreceipt[1];
			String crDate="";
			if(tableName.equalsIgnoreCase("CITI_BANK"))
			crDate="CREDIT_DEBIT_DATE";	
			if(tableName.equalsIgnoreCase("AXIS_BANK"))
			crDate="CR_DATE";
			if(tableName.equalsIgnoreCase("HSBC_BANK"))
			crDate="POST_DATE";
			if(tableName.equalsIgnoreCase("SCB_BANK"))
			crDate="CREDIT_DEBIT_DT";	
			if(tableName.equalsIgnoreCase("HDFC_BANK"))
			crDate="POST_DT";	
				
			//String selqry="SELECT RECEIPT_AG_CODE,RECEIPT_AG_NAME,RECEIPT_BRANCH_CODE,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,RECEIPT_NO,PAYMENT_TYPE,CHEQUE_NO,TO_CHAR(CHEQUE_DATE,'DD/MM/YYYY') CHEQUE_DATE,AMOUNT,BANK_NAME_AND_LOC,PARTICULARS,CREDIT_CARD_TYPE,CREDIT_CARD_BANK,TRANSACTION_REFERENCE,(SELECT to_char(DEPOSIT_DATE,'DD/MM/YYYY') FROM "+tableName+" T WHERE T.BANK_NO=R.BANK_NO)AS DEPOSIT_DATE,(SELECT (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) FROM "+tableName+"  T2 WHERE T2.BANK_NO=R.BANK_NO ) AS STATUS,(SELECT "+reason+" FROM "+tableName+" T WHERE T.BANK_NO=R.BANK_NO)AS REASON,BANK_NO,TRANS_SOURCE FROM RECEIPT_MASTER R WHERE RECEIPT_SL_NO=?";
			String selqry="SELECT RECEIPT_AG_CODE,RECEIPT_AG_NAME,RECEIPT_BRANCH_CODE,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,RECEIPT_NO,PAYMENT_TYPE,CHEQUE_NO,TO_CHAR(CHEQUE_DATE,'DD/MM/YYYY') CHEQUE_DATE,AMOUNT,BANK_NAME_AND_LOC,PARTICULARS,CREDIT_CARD_TYPE,CREDIT_CARD_BANK,TRANSACTION_REFERENCE,(SELECT to_char("+crDate+",'DD/MM/YYYY') FROM "+tableName+" T WHERE T.BANK_NO=R.BANK_NO)AS DEPOSIT_DATE,(SELECT (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) FROM "+tableName+"  T2 WHERE T2.BANK_NO=R.BANK_NO ) AS STATUS,(SELECT "+reason+" FROM "+tableName+" T WHERE T.BANK_NO=R.BANK_NO)AS REASON,BANK_NO,TRANS_SOURCE FROM RECEIPT_MASTER R WHERE RECEIPT_SL_NO=?";
			LogManager.push("query executed for rec detail"+selqry+bankreceipt[1]);
			map = this.mytemplate.queryForMap(selqry,new Object[]{ bankreceipt[1].trim() });
								
			cForm.setAmount((String) map.get("AMOUNT").toString());
			cForm.setBanknameandloc((String)map.get("BANK_NAME_AND_LOC"));
			cForm.setChequedate((String)map.get("CHEQUE_DATE"));
			cForm.setChequeno((String)map.get("CHEQUE_NO"));
			cForm.setCreditcardbank((String)map.get("CREDIT_CARD_BANK"));
			cForm.setCreditcardtype((String)map.get("CREDIT_CARD_TYPE"));
			cForm.setParticulars((String)map.get("PARTICULARS"));
			cForm.setReceiptagcode((String)map.get("RECEIPT_AG_CODE"));
			cForm.setReceiptagname((String)map.get("RECEIPT_AG_NAME"));
			cForm.setReceiptbranchcode((String)map.get("RECEIPT_BRANCH_CODE"));
			cForm.setReceiptdate((String)map.get("RECEIPT_DATE"));
			cForm.setReceiptno((String)map.get("RECEIPT_NO"));
			cForm.setTransactionreference((String)(map.get("TRANSACTION_REFERENCE")==null?"":map.get("TRANSACTION_REFERENCE") ));
			cForm.setReason((String)map.get("REASON"));
			cForm.setDepositdate((String)map.get("DEPOSIT_DATE"));
			LogManager.push("BANK NO__:"+map.get("BANK_NO"));
		 String bankNo = 	map.get("BANK_NO")==null?"":map.get("BANK_NO").toString();
			if(((String)(map.get("PAYMENT_TYPE")==null?"":map.get("PAYMENT_TYPE") )).trim().equalsIgnoreCase("CARD"))
			{
				cForm.setPaymenttype("Credit");
			}
			else if(((String)(map.get("PAYMENT_TYPE")==null?"":map.get("PAYMENT_TYPE") )).trim().equalsIgnoreCase("CASH"))
			{
				cForm.setPaymenttype("Cash");
			}
		    else if(((String)(map.get("PAYMENT_TYPE")==null?"":map.get("PAYMENT_TYPE") )).trim().equalsIgnoreCase("CHQ"))
			{
				cForm.setPaymenttype("Cheque");
			}
			
			try {
				if(((String)(map.get("STATUS")==null?"":map.get("STATUS") )).trim().equalsIgnoreCase("C"))
				{
					cForm.setChequestatus("Realized");
				}
				else if(((String)(map.get("STATUS")==null?"":map.get("STATUS") )).trim().equalsIgnoreCase("D"))
				{
					cForm.setChequestatus("Returned");
				}
				else if(((String)(bankNo )).trim().equalsIgnoreCase("-88888"))
				{
					cForm.setChequestatus("Reversed");
					cForm.setDepositdate("");
				}
				else if(((String)(map.get("TRANS_SOURCE")==null?"":map.get("TRANS_SOURCE") )).trim().equalsIgnoreCase("PYMT"))
				{
					cForm.setChequestatus("Payment Receipt");
				}
				else
				{
					LogManager.push("BANK NO__:"+map.get("BANK_NO"));
					cForm.setChequestatus("Not known");
				}
				LogManager.push("map.get(TRANS_SOURCE"+map.get("TRANS_SOURCE"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			LogManager.push(cForm.getChequestatus()+"cForm.setChequestatus");
		}
		
		//Bankno null
		else if( !(bankreceipt[1].trim().equalsIgnoreCase("") )&& (bankreceipt[0].trim().equalsIgnoreCase("")))
		{
			LogManager.push("bank no null");
			matched="No";
			status="Not known";
			depositDate="";
			receiptNo=bankreceipt[1];
			
			String selqry="SELECT RECEIPT_AG_CODE,RECEIPT_AG_NAME,RECEIPT_BRANCH_CODE,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,RECEIPT_NO,PAYMENT_TYPE,CHEQUE_NO,TO_CHAR(CHEQUE_DATE,'DD/MM/YYYY') CHEQUE_DATE,AMOUNT,BANK_NAME_AND_LOC,PARTICULARS,CREDIT_CARD_TYPE,CREDIT_CARD_BANK,TRANSACTION_REFERENCE,MANUAL_UPDATE,TRANS_SOURCE,TO_CHAR(MANUAL_UPDATEDATE,'DD/MM/YYYY') MANUAL_UPDATEDATE FROM RECEIPT_MASTER R WHERE RECEIPT_SL_NO=?";
			LogManager.push("query executed for rec detail"+selqry+bankreceipt[1]);
			map = this.mytemplate.queryForMap(selqry,new Object[]{ bankreceipt[1].trim() });
			LogManager.push("map value"+map.get("CHEQUE_NO")+map.size());
			
			cForm.setAmount((String) map.get("AMOUNT").toString());
			cForm.setBanknameandloc((String)map.get("BANK_NAME_AND_LOC"));
			cForm.setChequedate((String)map.get("CHEQUE_DATE"));
			cForm.setChequeno((String)map.get("CHEQUE_NO"));
			cForm.setCreditcardbank((String)map.get("CREDIT_CARD_BANK"));
			cForm.setCreditcardtype((String)map.get("CREDIT_CARD_TYPE"));
			cForm.setParticulars((String)map.get("PARTICULARS"));
			
			if(((String)(map.get("PAYMENT_TYPE")==null?"":map.get("PAYMENT_TYPE") )).trim().equalsIgnoreCase("CARD"))
			{
				cForm.setPaymenttype("Credit");
			}
			else if(((String)(map.get("PAYMENT_TYPE")==null?"":map.get("PAYMENT_TYPE") )).trim().equalsIgnoreCase("CASH"))
			{
				cForm.setPaymenttype("Cash");
			}
		    else if(((String)(map.get("PAYMENT_TYPE")==null?"":map.get("PAYMENT_TYPE") )).trim().equalsIgnoreCase("CHQ"))
			{
				cForm.setPaymenttype("Cheque");
			}
		  
			cForm.setReceiptagcode((String)map.get("RECEIPT_AG_CODE"));
			cForm.setReceiptagname((String)map.get("RECEIPT_AG_NAME"));
			cForm.setReceiptbranchcode((String)map.get("RECEIPT_BRANCH_CODE"));
			cForm.setReceiptdate((String)map.get("RECEIPT_DATE"));
			cForm.setReceiptno((String)map.get("RECEIPT_NO"));
			cForm.setTransactionreference((String)map.get("TRANSACTION_REFERENCE"));
			cForm.setReason("");
			cForm.setDepositdate("");
			if(((String)(map.get("MANUAL_UPDATE")==null?"":map.get("MANUAL_UPDATE") )).trim().equalsIgnoreCase("Y"))
			{
				cForm.setChequestatus("Realized");
				cForm.setDepositdate((String)map.get("MANUAL_UPDATEDATE"));
			}
			
			else
				cForm.setChequestatus("");
			
			 if(((String)(map.get("TRANS_SOURCE")==null?"":map.get("TRANS_SOURCE") )).trim().equalsIgnoreCase("PYMT"))
			{
				cForm.setChequestatus("Payment Receipt");
			}		
			
		}
			
		   }catch(Exception e){
			LogManager.fatal(e);
		}
		  LogManager.push(cForm.getChequestatus()+"cForm.setChequestatus");
		 LogManager.push("CFORM rec no:-->"+cForm.getReceiptno());
				
		LogManager.push("CFORM cheque no:-->"+cForm.getChequeno());
		LogManager.push("SearchDAoImpl getReceiptDetail() method exit");
		LogManager.logExit();
		LogManager.popRemove(); 
		
		
	}
	public List doManualRealization(String searchOption,String searchValue)
	{
		List list=null;	
		try{
		String query="update receipt_master set MANUAL_UPDATE='Y',MANUAL_UPDATEDATE=sysdate where Status='Y'";
		int count=this.mytemplate.update(query);
		String tempQuery="update receipt_master set Status=null where Status in('Y','N')";
		this.mytemplate.update(tempQuery);
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return list;
	}
	public void updateChecked(String receiptNo, String checkStatus)
			throws CommonBaseException {
		String query="update receipt_master set Status='"+checkStatus+"' where RECEIPT_SL_NO='"+receiptNo+"'";
		this.mytemplate.update(query);
		
	}
	public List getManulRealizedList(String searchOption, String searchValue)
			throws CommonBaseException {
		 String searchStatus="";
		 List list=new ArrayList();
		if(searchOption.equalsIgnoreCase("Policy No"))
			searchStatus="P";
		else if(searchOption.equalsIgnoreCase("cheque No"))
		    searchStatus="C";
		else if(searchOption.equalsIgnoreCase("Receipt No"))
			searchStatus="R";
	    
		if(!searchOption.equalsIgnoreCase("Transaction No"))
		{
        final String manualSearch = getQuery(DBConstants.MANUAL_REALIZATION_SEARCH);
        list =this.mytemplate.queryForList(manualSearch,new Object[]{searchValue,searchStatus});
		}
		else
		{
			String realise="";
			String transId=searchValue;
			String query="SELECT R.CHEQUE_NO,R.AMOUNT CHEQUE_AMT,NVL(R.CHEQUE_STATUS,'Realized') CHEQUE_STATUS,TO_CHAR (CHEQUE_DATE, 'DD/MM/YYYY') CHEQUE_DATE,R.RECEIPT_SL_NO, (case when MANUAL_UPDATE='Y' THEN 'REALIZED' else 'UnRealized' end) as MANUAL_UPDATE,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID IN (SELECT B.BANK_ID FROM RECEIPT_MASTER A,BANK_ACCOUNT_CODE B WHERE A.BANK_CODE=B.BANK_ACCOUNT_CODE AND A.RECEIPT_SL_NO = R.RECEIPT_SL_NO) ) AS BANK_NAME,RECEIPT_NO FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL AND BATCHID ="+transId+"  AND CHEQUE_NO IS NOT NULL AND MANUAL_UPDATE ='Y' ORDER BY R.RECEIPT_SL_NO";
			list=this.mytemplate.queryForList(query);
			
		}
		
		return list;
	}
	public List getPendingList(SearchFormBean sbean) throws CommonBaseException {
		
		String realise="";
		String transId=sbean.getTransactionNo()==null?"":sbean.getTransactionNo();
		String query="SELECT R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,Status,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID IN (SELECT B.BANK_ID FROM RECEIPT_MASTER A,BANK_ACCOUNT_CODE B WHERE A.BANK_CODE=B.BANK_ACCOUNT_CODE AND A.RECEIPT_SL_NO = R.RECEIPT_SL_NO) ) AS BANK_NAME,RECEIPT_NO,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL AND BATCHID ="+transId+"  AND MANUAL_UPDATE IS NULL AND CHEQUE_NO IS NOT NULL ORDER BY R.RECEIPT_SL_NO";
		String pagination=sbean.getPagination();
		String tranId=sbean.getTransactionNo()==null?"":sbean.getTransactionNo();
		if(!pagination.equalsIgnoreCase("true"))
		{
			this.mytemplate.update("update receipt_master set Status=null where Status in ('Y','N') and batchid="+tranId);
		}
		 List list = this.mytemplate.query(query,new RowMapper() {
		public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			com.maan.search.SearchVB sVB = new com.maan.search.SearchVB();
			
			sVB.setBankName(rset.getString("BANK_NAME"));
			sVB.setChequeNo(rset.getString("CHEQUE_NO"));
			sVB.setChequeAmount(rset.getString("AMOUNT"));
			sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
			sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
			sVB.setReceipt(rset.getString("RECEIPT_NO"));
			sVB.setCheckStatus(rset.getString("Status")==null?"":rset.getString("Status"));
			if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("C"))
			{
				sVB.setRealisation("Realized");
			}
			else if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("D"))
			{
				sVB.setRealisation("Returned");
			}
			else if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("R"))
			{
				sVB.setRealisation("Reversal");
			}
			else 
			{
				sVB.setRealisation("Not Known");
			}
			i++;
			return sVB;
		}
		
	});

    return list;
		
	}
	
}