package com.maan.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.jdbc.core.RowMapper;

import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.Runner;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;

public class SearchDAOImpl extends CommonBaseDAOImpl implements SearchDAO {

	public int i=1;
	public List getSearchList(SearchFormBean sbean) throws CommonBaseException {
	    LogManager.push("Inside getsearchlist:");
		String query="";
		String query2="";
		String query3="";
		String bankcode=sbean.getBankId();
		String searchIn=sbean.getSearchIn();
		String formChequeNo=sbean.getChequeNo();
		String formreceipt=sbean.getReceiptNo();
		String transId=sbean.getTransactionNo();
		LogManager.push("getSearchList search in:"+searchIn);
		String formChequeAmt=sbean.getChequeAmount();
		
		String realise=sbean.getRealised();
		String receiptrev="RECEIPT_SL_NO!=-99999 AND ";
		if(realise.equalsIgnoreCase("yes")){
			realise="NOT";
			
		}else 
		{
			realise="";
			receiptrev="";
			
		}
		
		//String tableName=String.valueOf(val.get("TABLE_NAME"));
	    List list=null;
	    LogManager.push("transaction code"+transId+"formchequeno:"+formChequeNo);
		
		try{
			LogManager.push("transaction code"+transId+"formchequeno:"+formChequeNo);
			//SEARCH BY TRANSACTIONS
			if((!transId.equalsIgnoreCase("")) )
			{
				LogManager.push("Inside transaction code"+transId);
				if(searchIn.equalsIgnoreCase("Bank"))
				{	
					List blist=getBankQueryData(bankcode);
					final Map tempMap = (Map) blist.get(0);
					final String tableName=((String)tempMap.get("TABLE_NAME"));
					final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
					final String reason=((String)tempMap.get("REASON"));
					final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
					final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
					final String receipt=((String)tempMap.get("RECEIPT_NO"));
					sbean.setBankName((String)tempMap.get("BANK_NAME"));
					final String bankName=(String)tempMap.get("BANK_NAME");
					LogManager.push("Bank:__>"+sbean.getBankName());
					 query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+" FROM "+tableName+" T WHERE "+receiptrev+"  RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo+" IS NOT NULL AND BATCHID="+transId+"  AND "+chequeStatus+" NOT LIKE 'H' ORDER BY 7 DESC, "+chequeNo+" DESC";
							
					LogManager.push("Query for individula bank"+query);
					list = this.mytemplate.query(query, new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setChequeNo(rset.getString(chequeNo));
							sVB.setChequeAmount(rset.getString(chequeAmt));
							//System.out.print(rset.getString(chequeStatus).trim()+"-");
							if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							if(rset.getString("REJECTION_TYPE")!=null){
							sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
							}
							else
							{
								sVB.setReason(rset.getString(reason));
								
							}
							sVB.setBankName(bankName);
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setBankNo1(rset.getString("BANK_NO"));
							sVB.setBankNo2(rset.getString("BANK_NO"));
							sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
							sVB.setActualChequeAmount(rset.getString("ACT_AMT"));
							sVB.setActualChequeNo(rset.getString("ACT_NO"));
							sVB.setReceipt(rset.getString(receipt));
							return sVB;
						}
									
					});
				}
				else
				{
					
					query="SELECT RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID IN (SELECT B.BANK_ID FROM RECEIPT_MASTER A,BANK_ACCOUNT_CODE B WHERE A.BANK_CODE=B.BANK_ACCOUNT_CODE AND A.RECEIPT_SL_NO = R.RECEIPT_SL_NO) ) AS BANK_NAME,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE "+receiptrev+"   R.BANK_NO IS "+realise+" NULL AND BATCHID ="+transId+"  AND CHEQUE_NO IS NOT NULL ORDER BY 5 DESC,R.CHEQUE_NO DESC ";
				
					LogManager.push("Query for not selecting bank else block "+query);
					list = this.mytemplate.query(query,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setBankName(rset.getString("BANK_NAME"));
							sVB.setChequeNo(rset.getString("CHEQUE_NO"));
							sVB.setChequeAmount(rset.getString("AMOUNT"));
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
							sVB.setReceipt(rset.getString("RECEIPT_NO"));
							if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							else 
							{
								sVB.setRealisation("Not Known");
							}
							return sVB;
						}
						
						
					});
				}
					
				
			}
           //SEARCH BY VALUES GIVEN
			
			else
		{
			//Search in all banks	
			if(bankcode.equalsIgnoreCase("all"))
			{
				String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
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
					 LogManager.push("<--"+reason[i]+""+chequeNo[i]+"-->");
					 LogManager.push("<--"+chequeAmt[i]+""+bankName[i]+"-->");
					 LogManager.push("<--"+receipt[i]+"--->");
					// sbean.setBankName((String)tempMap.get("BANK_NAME"));
					}
					
					//-----start---
					LogManager.push("Inside search code");
					String[] arg = new String[15];
					LogManager.push("inside blocks::::");
					//Search in bank statement 
				if(searchIn.equalsIgnoreCase("Bank"))
				{	
					
					if(sbean.getSearchFor().equalsIgnoreCase("exact")){
						if(!formChequeNo.equalsIgnoreCase("") )
						{
							arg[0]="AND "+chequeNo[0]+"='"+formChequeNo+"' ";
							arg[3]="AND "+chequeNo[1]+"='"+formChequeNo+"' ";
							arg[6]="AND "+chequeNo[2]+"='"+formChequeNo+"' ";
							arg[9]="AND "+chequeNo[3]+"='"+formChequeNo+"' ";
							arg[12]="AND "+chequeNo[4]+"='"+formChequeNo+"' ";
						 }
						else if(formChequeNo.equalsIgnoreCase(""))
						{
							arg[0]="";
							arg[3]="";
							arg[6]="";
							arg[9]="";
							arg[12]="";
						}
						if(!formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]="AND "+"trunc("+chequeAmt[0]+")"+"="+"trunc("+formChequeAmt+") ";
							arg[4]="AND "+"trunc("+chequeAmt[1]+")"+"="+"trunc("+formChequeAmt+") ";
							arg[7]="AND "+"trunc("+chequeAmt[2]+")"+"="+"trunc("+formChequeAmt+") ";
							arg[10]="AND "+"trunc("+chequeAmt[3]+")"+"="+"trunc("+formChequeAmt+") ";
							arg[13]="AND "+"trunc("+chequeAmt[4]+")"+"="+"trunc("+formChequeAmt+") ";
						}
						else if (formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]="";
							arg[4]="";
							arg[7]="";
							arg[10]="";
							arg[13]="";
						
						}
						if(!formreceipt.equalsIgnoreCase(""))
						{
							arg[2]="AND "+receipt[0]+"='"+formreceipt+"' ";
							arg[5]="AND "+receipt[1]+"='"+formreceipt+"' ";
							arg[8]="AND "+receipt[2]+"='"+formreceipt+"' ";
							arg[11]="AND "+receipt[3]+"='"+formreceipt+"' ";
							arg[14]="AND "+receipt[4]+"='"+formreceipt+"' ";
						 }
						else if(formreceipt.equalsIgnoreCase(""))
						{
							arg[2]="";
							arg[5]="";
							arg[8]="";
							arg[11]="";
							arg[14]="";
						 }
						query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[0]
									+ "') AS BANK,"
									+ chequeNo[0]
									+ ", "
									+ chequeAmt[0]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[0]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[0]
									+ ","
									+ reason[0]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[0]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[0]
									+ " FROM "
									+ tableName[0]
									+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ "AND "
									+ chequeStatus[0]
									+ " NOT LIKE 'H' " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[1]
									+ "') AS BANK,"
									+ chequeNo[1]
									+ ","
									+ chequeAmt[1]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[1]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[1]
									+ ","
									+ reason[1]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[1]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[1]
									+ " FROM "
									+ tableName[1]
									+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[3]
									+ arg[4]
									+ arg[5]
									+ "AND "
									+ chequeStatus[1]
									+ " NOT LIKE 'H' " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
										+ tableName[2]
										+ "') AS BANK,"
										+ chequeNo[2]
										+ ","
										+ chequeAmt[2]
										+ ",(CASE WHEN STATUS IS NULL THEN "
										+ chequeStatus[2]
										+ " ELSE STATUS END) AS "
										+ chequeStatus[2]
										+ ","
										+ reason[2]
										+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
										+ reason[2]
										+ ")) AS REJECTION_TYPE,"
										+ receipt[2]
										+ " FROM "
										+ tableName[2]
										+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
										+ realise
										+ " NULL "
										+ arg[6]
										+ arg[7]
										+ arg[8]
										+ "AND "
										+ chequeStatus[2]
										+ " NOT LIKE 'H' "+
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
										+ tableName[3]
										+ "') AS BANK,"
										+ chequeNo[3]
										+ ","
										+ chequeAmt[3]
										+ ",(CASE WHEN STATUS IS NULL THEN "
										+ chequeStatus[3]
										+ " ELSE STATUS END) AS "
										+ chequeStatus[3]
										+ ","
										+ reason[3]
										+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
										+ reason[3]
										+ ")) AS REJECTION_TYPE,"
										+ receipt[3]
										+ " FROM "
										+ tableName[3]
										+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
										+ realise
										+ " NULL "
										+ arg[9]
										+ arg[10]
										+ arg[11]
										+ "AND "
										+ chequeStatus[3]
										+ " NOT LIKE 'H' " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
										+ tableName[4]
										+ "') AS BANK,"
										+ chequeNo[4]
										+ ","
										+ chequeAmt[4]
										+ ",(CASE WHEN STATUS IS NULL THEN "
										+ chequeStatus[4]
										+ " ELSE STATUS END) AS "
										+ chequeStatus[4]
										+ ","
										+ reason[4]
										+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
										+ reason[4]
										+ ")) AS REJECTION_TYPE,"
										+ receipt[4]
										+ " FROM "
										+ tableName[4]
										+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
										+ realise
										+ " NULL "
										+ arg[12]
										+ arg[13]
										+ arg[14]
										+ "AND "
										+ chequeStatus[4]
										+ " NOT LIKE 'H' " +
									"ORDER BY DEPOSIT_DATE DESC ,4 DESC ";

								LogManager.push("query in if block bank file only"+query);
				    }
					else
					{
						arg[0]="'%"+formChequeNo+"%'";
						arg[1]="'"+formChequeAmt+"%'";
						arg[2]="'"+formreceipt+"%'";
						arg[3]="'%"+formChequeNo+"%'";
						arg[4]="'"+formChequeAmt+"%'";
						arg[5]="'"+formreceipt+"%'";
						arg[6]="'%"+formChequeNo+"%'";
						arg[7]="'"+formChequeAmt+"%'";
						arg[8]="'"+formreceipt+"%'";
						arg[9]="'%"+formChequeNo+"%'";
						arg[10]="'"+formChequeAmt+"%'";
						arg[11]="'"+formreceipt+"%'";
						arg[12]="'%"+formChequeNo+"%'";
						arg[13]="'"+formChequeAmt+"%'";
						arg[14]="'"+formreceipt+"%'";
						
						String condition=" where to_date(Deposit_date,'dd/mm/yyyy') between  add_months(to_date(sysdate,'dd/mm/rrrr'),-6) and to_date('"+sbean.getFromDate()+"','dd/mm/yyyy')";

						query="select * from (SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[0]+"') AS BANK,"+chequeNo[0]+", "+chequeAmt[0]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[0]+" ELSE STATUS END) AS "+chequeStatus[0]+","+reason[0]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[0]+")) AS REJECTION_TYPE,"+receipt[0]+" FROM "+tableName[0]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[0]+" LIKE "+arg[0]+" AND "+chequeAmt[0]+" LIKE "+arg[1]+" AND "+receipt[0]+" LIKE "+arg[2]+" AND "+chequeStatus[0]+" NOT LIKE 'H' " +
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[1]+"') AS BANK,"+chequeNo[1]+","+chequeAmt[1]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[1]+" ELSE STATUS END) AS "+chequeStatus[1]+","+reason[1]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[1]+")) AS REJECTION_TYPE,"+receipt[1]+" FROM "+tableName[1]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[1]+" LIKE "+arg[3]+" AND "+chequeAmt[1]+" LIKE "+arg[4]+" AND "+receipt[1]+" LIKE "+arg[5]+" AND "+chequeStatus[1]+" NOT LIKE 'H' " +
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[2]+"') AS BANK,"+chequeNo[2]+","+chequeAmt[2]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[2]+" ELSE STATUS END) AS "+chequeStatus[2]+","+reason[2]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[2]+")) AS REJECTION_TYPE,"+receipt[2]+" FROM "+tableName[2]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[2]+" LIKE "+arg[6]+" AND "+chequeAmt[2]+" LIKE "+arg[7]+" AND "+receipt[2]+" LIKE "+arg[8]+" AND "+chequeStatus[2]+" NOT LIKE 'H' "+
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[3]+"') AS BANK,"+chequeNo[3]+","+chequeAmt[3]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[3]+" ELSE STATUS END) AS "+chequeStatus[3]+","+reason[3]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[3]+")) AS REJECTION_TYPE,"+receipt[3]+" FROM "+tableName[3]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[3]+" LIKE "+arg[9]+" AND "+chequeAmt[3]+" LIKE "+arg[10]+" AND "+receipt[3]+" LIKE "+arg[11]+" AND "+chequeStatus[3]+" NOT LIKE 'H' "+
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[4]+"') AS BANK,"+chequeNo[4]+","+chequeAmt[4]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[4]+" ELSE STATUS END) AS "+chequeStatus[4]+","+reason[4]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[4]+")) AS REJECTION_TYPE,"+receipt[4]+" FROM "+tableName[4]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[4]+" LIKE "+arg[12]+" AND "+chequeAmt[4]+" LIKE "+arg[13]+" AND "+receipt[4]+" LIKE "+arg[14]+" AND "+chequeStatus[4]+" NOT LIKE 'H' "+
							    "ORDER BY 4 DESC , 8 DESC) "+condition;
						
						LogManager.push("query in else block bank file only"+query);
						
					}
					LogManager.push("Query bank file only"+query);
					LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
					list = this.mytemplate.query(query,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setBankName(rset.getString(1));
							sVB.setChequeNo(rset.getString(2));
							sVB.setChequeAmount(rset.getString(3));
							//System.out.print(rset.getString(chequeStatus).trim()+"-");
							if(rset.getString(4).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if(rset.getString(4).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							if(rset.getString(11)!=null){
								sVB.setReason(rset.getString(5)+"("+rset.getString(11)+")");
							}
							else
							{
								sVB.setReason(rset.getString(5));
								
							}
							//sVB.setBankName(bankName);
							sVB.setReceiptNo(rset.getString(6));
							sVB.setBankNo1(rset.getString(7));
							sVB.setBankNo2(rset.getString(7));
							sVB.setDepositDate(rset.getString(8));
							sVB.setActualChequeAmount(rset.getString(10));
							sVB.setActualChequeNo(rset.getString(9));
							sVB.setReceipt(rset.getString(12));
							
							return sVB;
						}
									
					});
				}
				//start for both receipt & bank  
				else if(searchIn.equalsIgnoreCase("Both"))
				{	
					
					if(sbean.getSearchFor().equalsIgnoreCase("exact")){
						if(!formChequeNo.equalsIgnoreCase("") )
						{
							arg[0]="AND "+chequeNo[0]+"='"+formChequeNo+"' ";
							arg[3]="AND "+chequeNo[1]+"='"+formChequeNo+"' ";
							arg[6]="AND "+chequeNo[2]+"='"+formChequeNo+"' ";
							arg[9]="AND "+chequeNo[3]+"='"+formChequeNo+"' ";
							arg[12]="AND "+chequeNo[4]+"='"+formChequeNo+"' ";
						}
						else if(formChequeNo.equalsIgnoreCase(""))
						{
							arg[0]="";
							arg[3]="";
							arg[6]="";
							arg[9]="";
							arg[12]="";
						}
						if(!formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]="AND "+"trunc("+chequeAmt[0]+")"+"="+"trunc("+formChequeAmt+") ";
							arg[4]="AND "+"trunc("+chequeAmt[1]+")"+"="+"trunc("+formChequeAmt+") ";
							arg[7]="AND "+"trunc("+chequeAmt[2]+")"+"="+"trunc("+formChequeAmt+") ";
							arg[10]="AND "+"trunc("+chequeAmt[3]+")"+"="+"trunc("+formChequeAmt+") ";
							arg[13]="AND "+"trunc("+chequeAmt[4]+")"+"="+"trunc("+formChequeAmt+") ";
						}
						else if (formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]="";
							arg[4]="";
							arg[7]="";
							arg[10]="";
							arg[13]="";
						}
						if(!formreceipt.equalsIgnoreCase(""))
						{
							arg[2]="AND "+receipt[0]+"='"+formreceipt+"' ";
							arg[5]="AND "+receipt[1]+"='"+formreceipt+"' ";
							arg[8]="AND "+receipt[2]+"='"+formreceipt+"' ";
							arg[11]="AND "+receipt[3]+"='"+formreceipt+"' ";
							arg[14]="AND "+receipt[4]+"='"+formreceipt+"' ";
						 }
						else if(formreceipt.equalsIgnoreCase(""))
						{
							arg[2]="";
							arg[5]="";
							arg[8]="";
							arg[11]="";
							arg[14]="";
						 }
						query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[0]
									+ "') AS BANK,"
									+ chequeNo[0]
									+ ", "
									+ chequeAmt[0]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[0]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[0]
									+ ","
									+ reason[0]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[0]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[0]
									+ ",'Bank' AS FROM_TABLE FROM "
									+ tableName[0]
									+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ "AND "
									+ chequeStatus[0]
									+ " NOT LIKE 'H' UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[1]
									+ "') AS BANK,"
									+ chequeNo[1]
									+ ","
									+ chequeAmt[1]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[1]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[1]
									+ ","
									+ reason[1]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[1]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[1]
									+ " ,'Bank' AS FROM_TABLE FROM "
									+ tableName[1]
									+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[3]
									+ arg[4]
									+ arg[5]
									+ "AND "
									+ chequeStatus[1]
									+ " NOT LIKE 'H' " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
										+ tableName[2]
										+ "') AS BANK,"
										+ chequeNo[2]
										+ ","
										+ chequeAmt[2]
										+ ",(CASE WHEN STATUS IS NULL THEN "
										+ chequeStatus[2]
										+ " ELSE STATUS END) AS "
										+ chequeStatus[2]
										+ ","
										+ reason[2]
										+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
										+ reason[2]
										+ ")) AS REJECTION_TYPE,"
										+ receipt[2]
										+ " ,'Bank' AS FROM_TABLE FROM "
										+ tableName[2]
										+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
										+ realise
										+ " NULL "
										+ arg[6]
										+ arg[7]
										+ arg[8]
										+ "AND "
										+ chequeStatus[2]
										+ " NOT LIKE 'H' " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
										+ tableName[3]
										+ "') AS BANK,"
										+ chequeNo[3]
										+ ","
										+ chequeAmt[3]
										+ ",(CASE WHEN STATUS IS NULL THEN "
										+ chequeStatus[3]
										+ " ELSE STATUS END) AS "
										+ chequeStatus[3]
										+ ","
										+ reason[3]
										+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
										+ reason[3]
										+ ")) AS REJECTION_TYPE,"
										+ receipt[3]
										+ " ,'Bank' AS FROM_TABLE FROM "
										+ tableName[3]
										+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
										+ realise
										+ " NULL "
										+ arg[9]
										+ arg[10]
										+ arg[11]
										+ "AND "
										+ chequeStatus[3]
										+ " NOT LIKE 'H' "+
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
										+ tableName[4]
										+ "') AS BANK,"
										+ chequeNo[4]
										+ ","
										+ chequeAmt[4]
										+ ",(CASE WHEN STATUS IS NULL THEN "
										+ chequeStatus[4]
										+ " ELSE STATUS END) AS "
										+ chequeStatus[4]
										+ ","
										+ reason[4]
										+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
										+ reason[4]
										+ ")) AS REJECTION_TYPE,"
										+ receipt[4]
										+ " ,'Bank' AS FROM_TABLE FROM "
										+ tableName[4]
										+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
										+ realise
										+ " NULL "
										+ arg[12]
										+ arg[13]
										+ arg[14]
										+ "AND "
										+ chequeStatus[4]
										+ " NOT LIKE 'H'" ;

					
				    }
					else
					{
						arg[0]="'%"+formChequeNo+"%'";
						arg[1]="'"+formChequeAmt+"%'";
						arg[2]="'"+formreceipt+"%'";
						arg[3]="'%"+formChequeNo+"%'";
						arg[4]="'"+formChequeAmt+"%'";
						arg[5]="'"+formreceipt+"%'";
						arg[6]="'%"+formChequeNo+"%'";
						arg[7]="'"+formChequeAmt+"%'";
						arg[8]="'"+formreceipt+"%'";
						arg[9]="'%"+formChequeNo+"%'";
						arg[10]="'"+formChequeAmt+"%'";
						arg[11]="'"+formreceipt+"%'";
						arg[12]="'%"+formChequeNo+"%'";
						arg[13]="'"+formChequeAmt+"%'";
						arg[14]="'"+formreceipt+"%'";
						
						String condition=" where to_date(Deposit_date,'dd/mm/yyyy') between  add_months(to_date(sysdate,'dd/mm/rrrr'),-6) and to_date('"+sbean.getFromDate()+"','dd/mm/yyyy')";

						query="select * from ( SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[0]+"') AS BANK,"+chequeNo[0]+", "+chequeAmt[0]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[0]+" ELSE STATUS END) AS "+chequeStatus[0]+","+reason[0]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[0]+")) AS REJECTION_TYPE,"+receipt[0]+",'Bank' AS FROM_TABLE FROM "+tableName[0]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[0]+" LIKE "+arg[0]+" AND "+chequeAmt[0]+" LIKE "+arg[1]+" AND "+receipt[0]+" LIKE "+arg[2]+" AND "+chequeStatus[0]+" NOT LIKE 'H' " +
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[1]+"') AS BANK,"+chequeNo[1]+","+chequeAmt[1]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[1]+" ELSE STATUS END) AS "+chequeStatus[1]+","+reason[1]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[1]+")) AS REJECTION_TYPE,"+receipt[1]+" ,'Bank' AS FROM_TABLE FROM "+tableName[1]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[1]+" LIKE "+arg[3]+" AND "+chequeAmt[1]+" LIKE "+arg[4]+" AND "+receipt[1]+" LIKE "+arg[5]+" AND "+chequeStatus[1]+" NOT LIKE 'H' " +
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[2]+"') AS BANK,"+chequeNo[2]+","+chequeAmt[2]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[2]+" ELSE STATUS END) AS "+chequeStatus[2]+","+reason[2]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[2]+")) AS REJECTION_TYPE,"+receipt[2]+" ,'Bank' AS FROM_TABLE FROM "+tableName[2]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[2]+" LIKE "+arg[6]+" AND "+chequeAmt[2]+" LIKE "+arg[7]+" AND "+receipt[2]+" LIKE "+arg[8]+" AND "+chequeStatus[2]+" NOT LIKE 'H' " +
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[3]+"') AS BANK,"+chequeNo[3]+","+chequeAmt[3]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[3]+" ELSE STATUS END) AS "+chequeStatus[3]+","+reason[3]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[3]+")) AS REJECTION_TYPE,"+receipt[3]+" ,'Bank' AS FROM_TABLE FROM "+tableName[3]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[3]+" LIKE "+arg[9]+" AND "+chequeAmt[3]+" LIKE "+arg[10]+" AND "+receipt[3]+" LIKE "+arg[11]+" AND "+chequeStatus[3]+" NOT LIKE 'H' "+
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[4]+"') AS BANK,"+chequeNo[4]+","+chequeAmt[4]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[4]+" ELSE STATUS END) AS "+chequeStatus[4]+","+reason[4]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[4]+")) AS REJECTION_TYPE,"+receipt[4]+" ,'Bank' AS FROM_TABLE FROM "+tableName[4]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[4]+" LIKE "+arg[12]+" AND "+chequeAmt[4]+" LIKE "+arg[13]+" AND "+receipt[4]+" LIKE "+arg[14]+" AND "+chequeStatus[4]+" NOT LIKE 'H') "+condition;
						
					}
					LogManager.push("Query"+query);
					//receipt queries start
					if(sbean.getSearchFor().equalsIgnoreCase("exact")){
						if(!formChequeNo.equalsIgnoreCase("") )
						{
							arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"' ";
							
						 }
						else if(formChequeNo.equalsIgnoreCase(""))
						{
							arg[0]="";
						}
						if(!formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]="AND trunc(R.AMOUNT)="+"trunc("+formChequeAmt+") ";
						}
						else if (formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]="";
						}
						if(!formreceipt.equalsIgnoreCase(""))
						{
							arg[2]="AND RECEIPT_NO='"+formreceipt+"' ";
				 		 }
						else if(formreceipt.equalsIgnoreCase(""))
						{
							arg[2]="";
						 }
						query2="UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[0]+" FROM "+tableName[0]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[0]+" FROM "+tableName[0]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[0]+"' AND STATUS='Y')   " +
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[1]+" FROM "+tableName[1]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[1]+" FROM "+tableName[1]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no,'Receipt' AS FROM_TABLE  FROM RECEIPT_MASTER R WHERE  R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[1]+"' AND STATUS='Y')  " +
										"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[2]+" FROM "+tableName[2]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[2]+" FROM "+tableName[2]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no,'Receipt' AS FROM_TABLE  FROM RECEIPT_MASTER R WHERE  R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[2]+"' AND STATUS='Y')  " +
										"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[3]+" FROM "+tableName[3]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[3]+" FROM "+tableName[3]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no,'Receipt' AS FROM_TABLE  FROM RECEIPT_MASTER R WHERE  R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[3]+"' AND STATUS='Y')  " +
										"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[4]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[4]+" FROM "+tableName[4]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[4]+" FROM "+tableName[4]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no,'Receipt' AS FROM_TABLE  FROM RECEIPT_MASTER R WHERE  R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[4]+"' AND STATUS='Y')  " +
										"ORDER BY 8 DESC,2";
						LogManager.push("Query two in if"+query2);
						}
					
					else
					{
					arg[0]="'%"+formChequeNo+"%'";
					arg[1]="'"+formChequeAmt+"%'";
					arg[2]="'"+formreceipt+"%'";
					query2="UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[0]+" FROM "+tableName[0]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[0]+" FROM "+tableName[0]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no,'Receipt' AS FROM_TABLE  FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[0]+"' AND STATUS='Y') " +
						   "UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[1]+" FROM "+tableName[1]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[1]+" FROM "+tableName[1]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[1]+"' AND STATUS='Y') " +
						   "UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[2]+" FROM "+tableName[2]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[2]+" FROM "+tableName[2]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[2]+"' AND STATUS='Y') " +
						   "UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[3]+" FROM "+tableName[3]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[3]+" FROM "+tableName[3]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[3]+"' AND STATUS='Y') " +
						   "UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[4]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[4]+" FROM "+tableName[4]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[4]+" FROM "+tableName[4]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[4]+"' AND STATUS='Y') " +
								"ORDER BY 8 DESC,2";
					LogManager.push("Query two in else"+query2);
					}
					LogManager.push("Query fianl"+query+query2);
					query3=query+query2;
					//receipt queries end
					LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
					list = this.mytemplate.query(query3,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setBankName(rset.getString(1));
							sVB.setChequeNo(rset.getString(2));
							sVB.setChequeAmount(rset.getString(3));
							//System.out.print(rset.getString(chequeStatus).trim()+"-");
							if(rset.getString(4)!=null){
							if(rset.getString(4).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if(rset.getString(4).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							else if(rset.getString(4).trim().equalsIgnoreCase("R"))
							{
								sVB.setRealisation("Reversal");
							}
							}
							else
							{
								sVB.setRealisation("Not known");
							}
							if(rset.getString(9)!=null){
								sVB.setReason(rset.getString(5)+"("+rset.getString(9)+")");
							}
							else
							{
								sVB.setReason(rset.getString(5));
								
							}
							//sVB.setBankName(bankName);
							sVB.setReceiptNo(rset.getString(6));
							sVB.setBankNo1(rset.getString(7));
							sVB.setBankNo2(rset.getString(7));
							sVB.setDepositDate(rset.getString(8));
							//sVB.setActualChequeAmount(rset.getString(9));
							//sVB.setActualChequeNo(rset.getString(10));
							sVB.setReceipt(rset.getString(10));
							sVB.setFrom(rset.getString("FROM_TABLE"));
							
							return sVB;
						}
									
					});
				}
				//end  for both receipt & bank
				
				
				//Search in Receipt statement
				else
				{
					if(sbean.getSearchFor().equalsIgnoreCase("exact")){
						if(!formChequeNo.equalsIgnoreCase("") )
						{
							arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"' ";
							
						}
						else if(formChequeNo.equalsIgnoreCase(""))
						{
							arg[0]="";
						}
						if(!formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]="AND trunc(R.AMOUNT)="+"trunc("+formChequeAmt+") ";
						}
						else if (formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]="";
						}
						if(!formreceipt.equalsIgnoreCase(""))
						{
							arg[2]="AND RECEIPT_NO='"+formreceipt+"' ";
				 		}
						else if(formreceipt.equalsIgnoreCase(""))
						{
							arg[2]="";
						}
						/*query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[0]+"' AND STATUS='Y')   UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[1]+"' AND STATUS='Y') " +
						"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[2]+"' AND STATUS='Y')  "+
						"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[3]+"' AND STATUS='Y')  ORDER BY 6 DESC,RECEIPT_SL_NO ";
				} query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
					+ bankIds[0]
								+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||'- ','RECEIPT',BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE  ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
								
								
								
								+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
								+ bankIds[0]
								+ "' AND STATUS='Y')   UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
								+ bankIds[1]
								+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT',BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
								
								+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
								+ bankIds[1] + "' AND STATUS='Y') " +
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[2]
									+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT',BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
									
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[2] + "' AND STATUS='Y')"+
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[3]
									+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT',BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
									
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[3] + "' AND STATUS='Y')"
									+"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[4]
												+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,R.CHEQUE_STATUS,RECEIPT_NO,R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT',BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE ( (cheque_status is null) or (status is not null or cheque_status is not null) ) "
												
												+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
												+ bankIds[4] + "' AND STATUS='Y')"+
								"ORDER BY 1,4 DESC,2";
						
						
						
						*/
						query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
							+ bankIds[0]
							+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,DECODE(MANUAL_UPDATE,'Y','Y',CHEQUE_STATUS) CHEQUE_STATUS,RECEIPT_NO,R.RECEIPT_SL_NO," +
							"R.BANK_NO||'- '||R.RECEIPT_SL_NO||'- ','RECEIPT'," +
							"R.BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE " +
							"(cheque_status is null) or (status is not null or cheque_status is not null) "
							+ " AND " +
							"R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
							+ bankIds[0]
							+ "' AND STATUS='Y')"
							+arg[0] 
							+arg[1]
							+arg[2]
							+     
							
							" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
							+ bankIds[1]
							+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,DECODE(MANUAL_UPDATE,'Y','Y',CHEQUE_STATUS) CHEQUE_STATUS,RECEIPT_NO,R.RECEIPT_SL_NO," +
							"R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT'," +
							"R.BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE " +
							"(cheque_status is null) or (status is not null or cheque_status is not null) "
							+ " AND " +
						    "R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
							+ bankIds[1] + "' AND STATUS='Y') " 
							+arg[0] 
							+arg[1]
							+arg[2]
							+
							
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
							+ bankIds[2]
							+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,DECODE(MANUAL_UPDATE,'Y','Y',CHEQUE_STATUS) CHEQUE_STATUS,RECEIPT_NO,R.RECEIPT_SL_NO," +
							"R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT'," +
							"R.BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE " +
							"(cheque_status is null) or (status is not null or cheque_status is not null) "
							+ " AND " +
							"R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
							+ bankIds[2] + "' AND STATUS='Y')"	
							+arg[0] 
							+arg[1]
							+arg[2]
							+
							
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
							+ bankIds[3]
							+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,DECODE(MANUAL_UPDATE,'Y','Y',CHEQUE_STATUS) CHEQUE_STATUS,RECEIPT_NO,R.RECEIPT_SL_NO," +
							"R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT'," +
							"R.BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE " +
							"(cheque_status is null) or (status is not null or cheque_status is not null) "
							+ " AND " +
							"R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
							+ bankIds[3] + "' AND STATUS='Y')"
							+arg[0] 
							+arg[1]
							+arg[2]
							+
							
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
							+ bankIds[4]
							+ "') AS BANK,R.CHEQUE_NO,R.AMOUNT,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,DECODE(MANUAL_UPDATE,'Y','Y',CHEQUE_STATUS) CHEQUE_STATUS,RECEIPT_NO,R.RECEIPT_SL_NO," +
							"R.BANK_NO||'- '||R.RECEIPT_SL_NO||' -','RECEIPT'," +
							"R.BANK_NO,TRANS_SOURCE,R.MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE " +
							"(cheque_status is null) or (status is not null or cheque_status is not null) "
							+ " AND " +
							"R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
							+ bankIds[4] + "' AND STATUS='Y')"
							+arg[0] 
							+arg[1]
							+arg[2]
							+
							"ORDER BY 1,4 DESC,2";
						LogManager.push("Query for receipt file if block"+query);
						}
					
					
					else
					{
					arg[0]="'%"+formChequeNo+"%'";
					arg[1]="'"+formChequeAmt+"%'";
					arg[2]="'"+formreceipt+"%'";
					query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[0]+"' AND STATUS='Y') "+
					"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[1]+"' AND STATUS='Y')  " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[2]+"' AND STATUS='Y') " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[3]+"' AND STATUS='Y') " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[4]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[4]+"' AND STATUS='Y') " +
									"ORDER BY RECEIPT_SL_NO, 6 DESC";
					}
					LogManager.push("Query"+query);
					list = this.mytemplate.query(query,  new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setChequeNo(rset.getString("CHEQUE_NO"));
							sVB.setChequeAmount(rset.getString("AMOUNT"));
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setBankName(rset.getString("BANK"));
							sVB.setReceipt(rset.getString("RECEIPT_NO"));
							
							sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
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
							return sVB;
						}});
				}
			}	
			//------end-------------
					
			
			//not searched for all banks
			else
			{
					LogManager.push("Inside search code");
					String[] arg = new String[3];
					LogManager.push("inside blocks::::");
					List blist=getBankQueryData(bankcode);
					final Map tempMap = (Map) blist.get(0);
					final String tableName=((String)tempMap.get("TABLE_NAME"));
					final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
					final String reason=((String)tempMap.get("REASON"));
					final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
					final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
					final String bankName=(String)tempMap.get("BANK_NAME");
					final String receipt=((String)tempMap.get("RECEIPT_NO"));
					
					sbean.setBankName((String)tempMap.get("BANK_NAME"));
					//Search in bank statement 
				if(searchIn.equalsIgnoreCase("Bank"))
				{	
					
					if(sbean.getSearchFor().equalsIgnoreCase("exact")){
					if(!formChequeNo.equalsIgnoreCase("") )
					{
						arg[0]=" AND "+chequeNo+"='"+formChequeNo+"' ";
					 }
					else if(formChequeNo.equalsIgnoreCase(""))
					{
						arg[0]="";
					}
					if(!formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="AND "+"trunc("+chequeAmt+")="+"trunc("+formChequeAmt+") ";
					}
					else if (formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="";
					}
					if(!formreceipt.equalsIgnoreCase(""))
					{
						arg[2]="AND "+receipt+"='"+formreceipt+"' ";
					 }
					else if(formreceipt.equalsIgnoreCase(""))
					{
						arg[2]=" ";
					 } 
					query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+" FROM "+tableName+" T WHERE RECEIPT_SL_NO !=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND "+chequeStatus+" NOT LIKE 'H' ORDER BY 7 DESC, "+chequeStatus+" DESC";
					
				    }
					else
					{
						arg[0]="'%"+formChequeNo+"%'";
						arg[1]="'"+formChequeAmt+"%'";
						arg[2]="'"+formreceipt+"%'";
						String condition=" where to_date(Deposit_date,'dd/mm/yyyy') between  add_months(to_date(sysdate,'dd/mm/rrrr'),-6) and to_date('"+sbean.getFromDate()+"','dd/mm/yyyy')";

						query="select * from (SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+" FROM "+tableName+" T WHERE RECEIPT_SL_NO !=-99999  AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo+" LIKE "+arg[0]+" AND "+chequeAmt+" LIKE "+arg[1]+" AND "+receipt+" LIKE "+arg[2]+" AND "+chequeStatus+" NOT LIKE 'H' ORDER BY 7 DESC, "+chequeStatus+" DESC) "+condition;
						
					}
					LogManager.push("Query"+query);
					LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
					list = this.mytemplate.query(query,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setBankName(bankName);
							sVB.setChequeNo(rset.getString(chequeNo));
							sVB.setChequeAmount(rset.getString(chequeAmt));
							//System.out.print(rset.getString(chequeStatus).trim()+"-");
							if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
							{
								sVB.setRealisation("Reversal");
							}
							else 
							{
								sVB.setRealisation("Not Known");
							}
							if(rset.getString("REJECTION_TYPE")!=null){
								sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
							}
							else
							{
								sVB.setReason(rset.getString(reason));
								
							}
							sVB.setBankName(bankName);
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setBankNo1(rset.getString("BANK_NO"));
							sVB.setBankNo2(rset.getString("BANK_NO"));
							sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
							sVB.setActualChequeAmount(rset.getString("ACT_AMT"));
							sVB.setActualChequeNo(rset.getString("ACT_NO"));
							sVB.setReceipt(rset.getString(receipt));
							
							return sVB;
						}
									
					});
				}
				//start both receipt & bank 
				if(searchIn.equalsIgnoreCase("Both"))
				{	
					
					if(sbean.getSearchFor().equalsIgnoreCase("exact")){
					if(!formChequeNo.equalsIgnoreCase("") )
					{
						arg[0]=" AND "+chequeNo+"='"+formChequeNo+"' ";
					 }
					else if(formChequeNo.equalsIgnoreCase(""))
					{
						arg[0]="";
					}
					if(!formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="AND "+"trunc("+chequeAmt+")="+"trunc("+formChequeAmt+") ";
					}
					else if (formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="";
					}
					if(!formreceipt.equalsIgnoreCase(""))
					{
						arg[2]="AND "+receipt+"='"+formreceipt+"' ";
					 }
					else if(formreceipt.equalsIgnoreCase(""))
					{
						arg[2]=" ";
					 } 
					query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+" ,'Bank' AS FROM_TABLE FROM "+tableName+" T WHERE RECEIPT_SL_NO !=-99999  AND RECEIPT_SL_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND "+chequeStatus+" NOT LIKE 'H' ";
					
				    }
					else
					{
						arg[0]="'%"+formChequeNo+"%'";
						arg[1]="'"+formChequeAmt+"%'";
						arg[2]="'"+formreceipt+"%'";
						String condition=" where to_date(Deposit_date,'dd/mm/yyyy') between  add_months(to_date(sysdate,'dd/mm/rrrr'),-6) and to_date('"+sbean.getFromDate()+"','dd/mm/yyyy')";

						query="select * from (SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+" ,'Bank' AS FROM_TABLE FROM "+tableName+" T WHERE RECEIPT_SL_NO !=-99999  AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo+" LIKE "+arg[0]+" AND "+chequeAmt+" LIKE "+arg[1]+" AND "+receipt+" LIKE "+arg[2]+" AND "+chequeStatus+" NOT LIKE 'H') "+condition;
						
					}
					LogManager.push("Query"+query);
					LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
					//receipt starts 
					if(sbean.getSearchFor().equalsIgnoreCase("exact")){
						if(!formChequeNo.equalsIgnoreCase("") )
						{
							arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"'";
						 }
						else if(formChequeNo.equalsIgnoreCase(""))
						{
							arg[0]=" ";
						}
						if(!formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]=" AND trunc(R.AMOUNT)=trunc('"+formChequeAmt+"')";
						}
						else if (formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]=" ";
						}
						if(!formreceipt.equalsIgnoreCase(""))
						{
							arg[2]=" AND RECEIPT_NO='"+formreceipt+"'";
						 }
						else if(formreceipt.equalsIgnoreCase(""))
						{
							arg[2]=" ";
						 }
						
						//query2="UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID=(SELECT BANK_ID FROM BANK_ACCOUNT_CODE WHERE BANK_ACCOUNT_CODE=R.BANK_CODE)) AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY R.RECEIPT_SL_NO";
						query2="UNION ALL SELECT R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,'',0,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888  AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 7 DESC, 1";	
					}
					
					else
					{
					arg[0]="'%"+formChequeNo+"%'";
					arg[1]="'"+formChequeAmt+"%'";
					arg[2]="'"+formreceipt+"%'";
					query2=" UNION ALL SELECT R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,'',0,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888  AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 7 DESC,1";
					}
					//receipt ends 
					query=query+query2;
					list = this.mytemplate.query(query,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setBankName(bankName);
							sVB.setChequeNo(rset.getString(chequeNo));
							sVB.setChequeAmount(rset.getString(chequeAmt));
							//System.out.print(rset.getString(chequeStatus).trim()+"-");
							if(rset.getString(chequeStatus)!=null){
							if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
							{
								sVB.setRealisation("Reversal");
							}
							else 
							{
								sVB.setRealisation("Not Known");
							}
							}
							else
							{
								sVB.setRealisation("Not known");
							}
							if(rset.getString("REJECTION_TYPE")!=null){
								sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
							}
							else
							{
								sVB.setReason(rset.getString(reason));
								
							}
							sVB.setBankName(bankName);
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setBankNo1(rset.getString("BANK_NO"));
							sVB.setBankNo2(rset.getString("BANK_NO"));
							sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
							//sVB.setActualChequeAmount(rset.getString("ACT_AMT"));
							//sVB.setActualChequeNo(rset.getString("ACT_NO"));
							sVB.setReceipt(rset.getString(receipt));
							sVB.setFrom(rset.getString("FROM_TABLE"));
							return sVB;
						}
									
					});
				}
				//ends both receipt & bank
				//Search in Receipt statement
				else if(searchIn.equalsIgnoreCase("Receipt"))
				{
					if(sbean.getSearchFor().equalsIgnoreCase("exact")){
						if(!formChequeNo.equalsIgnoreCase("") )
						{
							arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"'";
						 }
						else if(formChequeNo.equalsIgnoreCase(""))
						{
							arg[0]=" ";
						}
						if(!formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]=" AND trunc(R.AMOUNT)=trunc('"+formChequeAmt+"')";
						}
						else if (formChequeAmt.equalsIgnoreCase("") )
						{
							arg[1]=" ";
						}
						if(!formreceipt.equalsIgnoreCase(""))
						{
							arg[2]=" AND RECEIPT_NO='"+formreceipt+"'";
						 }
						else if(formreceipt.equalsIgnoreCase(""))
						{
							arg[2]=" ";
						 }
						query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID=(SELECT BANK_ID FROM BANK_ACCOUNT_CODE WHERE BANK_ACCOUNT_CODE=R.BANK_CODE)) AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888  AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 6 desc,R.RECEIPT_SL_NO";
						}
					
					else
					{
					arg[0]="'%"+formChequeNo+"%'";
					arg[1]="'"+formChequeAmt+"%'";
					arg[2]="'"+formreceipt+"%'";
					query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID=(SELECT BANK_ID FROM BANK_ACCOUNT_CODE WHERE BANK_ACCOUNT_CODE=R.BANK_CODE)) AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888  AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') ORDER BY  6 DESC,R.RECEIPT_SL_NO";
					}
					LogManager.push("Query for Receipt"+query);
					list = this.mytemplate.query(query,  new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setChequeNo(rset.getString("CHEQUE_NO"));
							sVB.setChequeAmount(rset.getString("AMOUNT"));
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setBankName(rset.getString("BANK"));
							sVB.setReceipt(rset.getString("RECEIPT_NO"));
							if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
							{
								sVB.setRealisation("Reversal");
							}
							else 
							{
								sVB.setRealisation("Not Known");
							}
							sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
							return sVB;
						}});
				}
				}	
			    
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LogManager.push("Exception In SearchDAOImpl - searchResult searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+query);
		return list; 	
	}
	
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
	
	
	public List getMatchedList(SearchFormBean sbean,String sid) throws CommonBaseException {
		List list=null;
		try {
			
			String bankcode=sbean.getBankId();
			LogManager.push("Enters getMatchedList");
			List nonMatchedList=sbean.getNonmatchedlist();
			String bankNo="";
			
				
			String querydel="DELETE FROM TEMP_NONMATCHED WHERE SESSION_ID='"+sid+"'";
			Runner.updation(querydel);
			LogManager.push("Query deleted:::::::::"+querydel);
			if(sbean.getNonmatchedlist()!=null){
				SearchVB nonMatchedVB = (SearchVB)sbean.getNonmatchedlist().get(0);
				bankNo=nonMatchedVB.getBankNo1();
				if(sbean.getNonmatchedlist().size()>0){
					for(int i=0;i<sbean.getNonmatchedlist().size();i++)
					{
					    nonMatchedVB = (SearchVB)sbean.getNonmatchedlist().get(i);
						//LogManager.push("i::>"+i+"bankno::>"+nonMatchedVB.getBankNo1());
						
						String queryinstemp="INSERT INTO TEMP_NONMATCHED VALUES("+nonMatchedVB.getBankNo1()+",'"+sid+"')";
						Runner.inserion(queryinstemp);
						bankNo=bankNo+", "+nonMatchedVB.getBankNo1();
					}
				}
			}
			LogManager.push("BankNoin"+ bankNo);
			String BankIn="";
			if(sbean.getNonmatchedlist()!=null)
			{
				//BankIn = " AND B.BANK_NO IN (" +bankNo +")";
				BankIn = " AND B.BANK_NO =T.BANKNO AND T.SESSION_ID='"+sid+"'";
			}
			if(bankcode.equalsIgnoreCase("all"))
			{
				//start
				String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
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
			
					for(int k=0;k<listbank.size();k++)
					{
					bankIds[k]=listbank.get(k).toString();
					LogManager.push("bank"+i+":"+listbank.get(k).toString());
					List blist=getBankQueryData(listbank.get(k).toString());
					final Map tempMap = (Map)blist.get(0);
					 tableName[k]=((String)tempMap.get("TABLE_NAME"));
					 chequeStatus[k]=((String)tempMap.get("CHEQUE_STATUS"));
					 reason[k]=((String)tempMap.get("REASON"));
					 chequeNo[k]=((String)tempMap.get("CHEQUE_NO"));
				 	 chequeAmt[k]=((String)tempMap.get("CHEQUE_AMT"));
				     bankName[k]=(String)tempMap.get("BANK_NAME");
					 receipt[k]=((String)tempMap.get("RECEIPT_NO"));
					 LogManager.push("<--"+tableName[k]+""+chequeStatus[k]+"-->");
					// sbean.setBankName((String)tempMap.get("BANK_NAME"));
					
			
				
				//UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN BOTH FIELDS NOT NULL
				//Query for not matched actual cheque no and amt:
				
			    //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE AMT NOT NULL
				//Query for not matched actual cheque amt:
				/*String queryvalues2="SELECT ACTUAL_CHEQUE_AMT FROM "+tableName[k]+" WHERE ACTUAL_CHEQUE_NO IS NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
				List values2=this.mytemplate.queryForList(queryvalues2);
				for(int i=0;i<values2.size();i++)
				{
					LogManager.push(values2.get(i));
					final Map tempMap1 = (Map) values2.get(i);
					//final String actualchequeno=((String)tempMap1.get("ACTUAL_CHEQUE_NO"));
					final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT").toString());
					LogManager.push("actualchequeamt"+actualchequeamt);
					//Query to match in bank table
					String qury="UPDATE "+tableName[k]+" B SET RECEIPT_SL_NO=(SELECT MIN(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE B."+chequeNo[k]+"=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
					this.mytemplate.update(qury);
					LogManager.push("Query for bank matched col CHEQUE AMT NOT NULL:==>"+qury+"count-->");
					
					//query to match in receipt
					qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName[k]+" C WHERE C."+chequeNo[k]+"=R.CHEQUE_NO AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO IS NULL AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' ";
					this.mytemplate.update(qury);
					LogManager.push("Query for receipt matched col CHEQUE AMT NOT NULL:==>"+qury+"count-->");
					
				}*/
				
				 //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE NO NOT NULL
				//Query for not matched actual cheque no:
				/*String queryvalues3="SELECT ACTUAL_CHEQUE_NO FROM "+tableName[k]+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS  NULL AND RECEIPT_SL_NO IS NULL";
				List values3=this.mytemplate.queryForList(queryvalues3);
				for(int i=0;i<values3.size();i++)
				{
					LogManager.push(values3.get(i));
					final Map tempMap1 = (Map) values3.get(i);
					final String actualchequeno=((String)tempMap1.get("ACTUAL_CHEQUE_NO"));
					//final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT"));
					LogManager.push("actualchequeno"+actualchequeno);
					//Query to match in bank table
					String qury="UPDATE "+tableName[k]+" B SET RECEIPT_SL_NO=(SELECT MIN(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND B."+chequeAmt[k]+"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT IS NULL";
					this.mytemplate.update(qury);
					LogManager.push("Query for bank matched col CHEQUE NO NOT NULL:==>"+qury+"count-->");
					
					//query to match in receipt
					qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName[k]+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND C."+chequeAmt[k]+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO= '"+actualchequeno+"' AND C.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND '"+actualchequeno+"'=R.CHEQUE_NO AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ'";
					this.mytemplate.update(qury);
					LogManager.push("Query for receipt matched col CHEQUE NO NOT NULL:==>"+qury+"count-->");
					
				}*/
			 }
				//end
					
					for(int k=0;k<listbank.size();k++)
					{
					bankIds[k]=listbank.get(k).toString();
					LogManager.push("bank"+i+":"+listbank.get(k).toString());
					List blist=getBankQueryData(listbank.get(k).toString());
					final Map tempMap = (Map)blist.get(0);
					 tableName[k]=((String)tempMap.get("TABLE_NAME"));
					 chequeStatus[k]=((String)tempMap.get("CHEQUE_STATUS"));
					 reason[k]=((String)tempMap.get("REASON"));
					 chequeNo[k]=((String)tempMap.get("CHEQUE_NO"));
				 	 chequeAmt[k]=((String)tempMap.get("CHEQUE_AMT"));
				     bankName[k]=(String)tempMap.get("BANK_NAME");
					 receipt[k]=((String)tempMap.get("RECEIPT_NO"));
					 LogManager.push("<--"+tableName[k]+""+chequeStatus[k]+"-->");
					// sbean.setBankName((String)tempMap.get("BANK_NAME"));
					}
					
					
					final String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[0]+",B."+chequeAmt[0]+",B."+reason[0]+",B."+chequeStatus[0]+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[0]+")) AS REJECTION_TYPE,"+receipt[0]+"  FROM "+tableName[0]+" B , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO IS NOT NULL AND B.RECEIPT_SL_NO!='-99999' "+BankIn+" " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[1]+",B."+chequeAmt[1]+",B."+reason[1]+",B."+chequeStatus[1]+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[1]+")) AS REJECTION_TYPE,"+receipt[1]+"  FROM "+tableName[1]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO  IS NOT NULL AND B.RECEIPT_SL_NO!='-99999' "+BankIn+" " +
							" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[2]+",B."+chequeAmt[2]+",B."+reason[2]+",B."+chequeStatus[2]+",B.RECEIPT_SL_NO,to_char(B.POST_DT,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[2]+")) AS REJECTION_TYPE,"+receipt[2]+"  FROM "+tableName[2]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO  IS NOT NULL AND B.RECEIPT_SL_NO!='-99999' "+BankIn + 
							 " UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[3]+",B."+chequeAmt[3]+",B."+reason[3]+",B."+chequeStatus[3]+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[3]+")) AS REJECTION_TYPE,"+receipt[3]+"  FROM "+tableName[3]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO  IS NOT NULL AND B.RECEIPT_SL_NO!='-99999' "+BankIn +
							 " UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[4]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[4]+",B."+chequeAmt[4]+",B."+reason[4]+",B."+chequeStatus[4]+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[4]+")) AS REJECTION_TYPE,"+receipt[4]+"  FROM "+tableName[4]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO  IS NOT NULL AND B.RECEIPT_SL_NO!='-99999' "+BankIn;
					LogManager.push("Query for BANK LIST matched col test:==>"+query+"count-->");
					list = this.mytemplate.query(query, new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setChequeNo(rset.getString(chequeNo[0]));
							sVB.setChequeAmount(rset.getString(chequeAmt[0]));
							//System.out.print(rset.getString(chequeStatus).trim()+"-");
							if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("R"))
							{
								sVB.setRealisation("Reversal");
							}
							sVB.setReason(rset.getString(reason[0]));
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
							sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
							sVB.setActualChequeAmount((String)rset.getString("ACTUAL_CHEQUE_AMT").toString());
							sVB.setReceipt(rset.getString(receipt[0]));
							sVB.setBankName(rset.getString("BANK"));
							return sVB;
						}
									
					});
					
					
			}
			else
			{
					
			List blist=getBankQueryData(bankcode);
			final Map tempMap = (Map) blist.get(0);
			final String tableName=((String)tempMap.get("TABLE_NAME"));
			final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
			final String reason=((String)tempMap.get("REASON"));
			final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
			final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
			final String receipt=((String)tempMap.get("RECEIPT_NO"));
			
			/*//String qeury1="SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT FROM "+tableName+""
			
			//GETTING COUNTS FOR NO. OF RECORDS MATCHED
			String qury ="SELECT count(*) FROM RECEIPT_MASTER R,"+tableName+" B WHERE B.ACTUAL_CHEQUE_NO=R.CHEQUE_NO AND B.ACTUAL_CHEQUE_AMT=R.AMOUNT AND B.ACTUAL_CHEQUE_NO IS NOT NULL AND B.ACTUAL_CHEQUE_AMT IS NOT NULL  AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' UNION ALL SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",R.RECEIPT_SL_NO,B.DEPOSIT_DATE FROM RECEIPT_MASTER R,"+tableName+" B WHERE B.ACTUAL_CHEQUE_NO=R.CHEQUE_NO AND B.ACTUAL_CHEQUE_AMT=R.AMOUNT AND B."+chequeNo+" IS  NULL AND B.ACTUAL_CHEQUE_AMT IS NOT NULL  AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' UNION ALL SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",R.RECEIPT_SL_NO,B.DEPOSIT_DATE FROM RECEIPT_MASTER R,"+tableName+" B WHERE B.ACTUAL_CHEQUE_NO=R.CHEQUE_NO AND B."+chequeAmt+"=R.AMOUNT AND B.ACTUAL_CHEQUE_NO IS NOT NULL AND B.ACTUAL_CHEQUE_AMT IS NULL  AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ'";
			int count=this.mytemplate.queryForInt(qury);
			LogManager.push("Query for AFTER CHANGE matched col:==>"+qury+"count-->"+count);
			
			
			//UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN BOTH FIELDS NOT NULL
			//Query for not matched actual cheque no and amt:
			String queryvalues="SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT FROM "+tableName+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
			LogManager.push("Query to get list for both not null:"+queryvalues);
			
			List values=this.mytemplate.queryForList(queryvalues);
			for(int i=0;i<values.size();i++)
			{
				LogManager.push(values.get(i));
				final Map tempMap1 = (Map) values.get(i);
				final String actualchequeno=((String)tempMap1.get("ACTUAL_CHEQUE_NO"));
				final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT").toString());
				LogManager.push("actualchequeno"+actualchequeno+"actualchequeamt"+actualchequeamt);
				//Query to find actual cheque exists
				String qury="UPDATE "+tableName+" B SET RECEIPT_SL_NO=(SELECT MIN(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE R.CHEQUE_NO='"+actualchequeno+"' AND R.AMOUNT="+actualchequeamt+" AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
				this.mytemplate.update(qury);
				LogManager.push("Query for bank matched col BOTH NOT NULL:==>"+qury+"count-->");
				
				qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' ";
				this.mytemplate.update(qury);
				LogManager.push("Query for receipt matched col BOTH NOT NULL:==>"+qury+"count-->");
				
			}

			//UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE AMT NOT NULL
			//Query for not matched actual cheque amt:
			String queryvalues2="SELECT ACTUAL_CHEQUE_AMT FROM "+tableName+" WHERE ACTUAL_CHEQUE_NO IS NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
			LogManager.push("Query to get list for cheque amt not null:"+queryvalues2);
			
			List values2=this.mytemplate.queryForList(queryvalues2);
			for(int i=0;i<values2.size();i++)
			{
				LogManager.push(values2.get(i));
				final Map tempMap1 = (Map) values2.get(i);
				//final String actualchequeno=((String)tempMap1.get("ACTUAL_CHEQUE_NO"));
				final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT").toString());
				LogManager.push("actualchequeamt"+actualchequeamt);
				//Query to match in bank table
				String qury="UPDATE "+tableName+" B SET RECEIPT_SL_NO=(SELECT MIN(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE B."+chequeNo+"=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
				this.mytemplate.update(qury);
				LogManager.push("Query for bank matched col CHEQUE AMT NOT NULL:==>"+qury+"count-->");
				
				//query to match in receipt
				qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName+" C WHERE C."+chequeNo+"=R.CHEQUE_NO AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO IS NULL AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND "+actualchequeamt+" =R.AMOUNT ";
				this.mytemplate.update(qury);
				LogManager.push("Query for receipt matched col CHEQUE AMT NOT NULL:==>"+qury+"count-->");
				
			}
			
			 //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE NO NOT NULL
			//Query for not matched actual cheque no:
			String queryvalues3="SELECT ACTUAL_CHEQUE_NO FROM "+tableName+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS  NULL AND RECEIPT_SL_NO IS NULL";
			LogManager.push("Query to get list for cheque no not null:"+queryvalues3);
			List values3=this.mytemplate.queryForList(queryvalues3);
			for(int i=0;i<values3.size();i++)
			{
				LogManager.push(values3.get(i));
				final Map tempMap1 = (Map) values3.get(i);
				final String actualchequeno=((String)tempMap1.get("ACTUAL_CHEQUE_NO"));
				//final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT"));
				LogManager.push("actualchequeno"+actualchequeno);
				//Query to match in bank table
				String qury="UPDATE "+tableName+" B SET RECEIPT_SL_NO=(SELECT MAX(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND B."+chequeAmt+"=R.AMOUNT AND R.BANK_NO IS NULL AND '"+actualchequeno+"'=B.ACTUAL_CHEQUE_NO  AND B.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND '"+actualchequeno+"'=B.ACTUAL_CHEQUE_NO  AND B.ACTUAL_CHEQUE_AMT IS NULL";
				this.mytemplate.update(qury);
				LogManager.push("Query for bank matched col CHEQUE NO NOT NULL:==>"+qury+"count-->");
				
				//query to match in receipt
				qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND C."+chequeAmt+" =R.AMOUNT AND R.BANK_NO IS NULL AND '"+actualchequeno+"'=C.ACTUAL_CHEQUE_NO AND C.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND  '"+actualchequeno+"'=R.CHEQUE_NO";
				this.mytemplate.update(qury);
				LogManager.push("Query for receipt matched col CHEQUE NO NOT NULL:==>"+qury+"count-->");
				
			}
*/		final String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankcode+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",B.RECEIPT_SL_NO,B.DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason+")) AS REJECTION_TYPE,"+receipt+"  FROM "+tableName+" B , TEMP_NONMATCHED T  WHERE (B.ACTUAL_CHEQUE_NO IS NOT NULL OR B.ACTUAL_CHEQUE_AMT IS NOT NULL ) AND B.RECEIPT_SL_NO IS NOT NULL " +BankIn;
			LogManager.push("Query for BANK LIST matched col:==>"+query+"count-->");
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					SearchVB sVB = new SearchVB();
					sVB.setChequeNo(rset.getString(chequeNo));
					sVB.setChequeAmount((String)rset.getString(chequeAmt).toString());
					//System.out.print(rset.getString(chequeStatus).trim()+"-");
					if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
					{
						sVB.setRealisation("Realized");
					}
					else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
					{
						sVB.setRealisation("Returned");
					}
					else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
					{
						sVB.setRealisation("Reversal");
					}
					else 
					{
						sVB.setRealisation("Not Known");
					}
					sVB.setReason(rset.getString(reason));
					sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
					sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
					sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
					sVB.setActualChequeAmount(rset.getString("ACTUAL_CHEQUE_AMT"));
					sVB.setReceipt(rset.getString(receipt));
					sVB.setBankName(rset.getString("BANK"));
					return sVB;
				}
							
			});
			
					}
			/*try
			{
			String updatestat="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN C.STATUS IS NULL THEN TYPE_CRDR ELSE STATUS END) AS TYPE_CRDR FROM CITI_BANK C WHERE R.BANK_NO=C.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL  AND (C.ACTUAL_CHEQUE_NO IS NOT NULL OR C.ACTUAL_CHEQUE_AMT IS NOT NULL )) where r.bank_no is not null and r.bank_code in (select bank_account_code from bank_account_code where bank_id='CIT')";
			LogManager.push("Update for  status in receipt:"+ updatestat);
			//Runner.updation(updatestat);
			int i=this.mytemplate.update(updatestat);
			LogManager.push(i);
			updatestat="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN C.STATUS IS NULL THEN DR_CR ELSE STATUS END) AS DR_CR FROM HDFC_BANK C WHERE R.BANK_NO=C.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL AND (C.ACTUAL_CHEQUE_NO IS NOT NULL OR C.ACTUAL_CHEQUE_AMT IS NOT NULL )) where r.bank_no is not null and r.bank_code in (select bank_account_code from bank_account_code where bank_id='HDB')";
			LogManager.push("Update for  status in receipt:"+ updatestat);
			i=this.mytemplate.update(updatestat);
			LogManager.push(i);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}*/
			//REMOVE AFTER OPERATION
			//qury="UPDATE "+tableName+" SET ACTUAL_CHEQUE_NO=NULL,ACTUAL_CHEQUE_AMT=NULL WHERE RECEIPT_SL_NO IS NULL";
			//this.mytemplate.update(qury);
			//LogManager.push("Query for receipt matched col:==>"+qury+"count-->");
			LogManager.push("Exit matched list");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return list;
	}
	
	public List getNotRealisedList(SearchFormBean sbean) throws CommonBaseException {
		String query="";
		String bankcode=sbean.getBankId();
		String searchIn=sbean.getSearchIn();
		String formChequeNo=sbean.getChequeNo();
		String formChequeAmt=sbean.getChequeAmount();
		List blist=getBankQueryData(bankcode);
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
		final String reason=((String)tempMap.get("REASON"));
		final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
		final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
		final String receipt=((String)tempMap.get("RECEIPT_NO"));
		
		String realise="";
		
		sbean.setBankName((String)tempMap.get("BANK_NAME"));
		//String tableName=String.valueOf(val.get("TABLE_NAME"));
	    List list=null;
		try{
			Object[] arg = new Object[2];
			
		
		if(searchIn.equalsIgnoreCase("Bank"))
		{	
			if(sbean.getSearchFor().equalsIgnoreCase("exact")){
			arg[0]=formChequeNo;
			arg[1]="'"+formChequeAmt+"%'";
			
			
			query="SELECT "+chequeNo+","+chequeAmt+","+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,"+receipt+" FROM "+tableName+" WHERE RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo+"="+arg[0]+" AND "+chequeAmt+"LIKE "+arg[1]+" ORDER BY 7 DESC,RECEIPT_SL_NO";
			
			}
			else
			{
			arg[0]="%"+formChequeNo+"%";
			arg[1]="'"+formChequeAmt+"%'";
			query="SELECT "+chequeNo+","+chequeAmt+","+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,"+receipt+" FROM "+tableName+" WHERE RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo+" LIKE "+arg[0]+" AND "+chequeAmt+" LIKE "+arg[1]+" ORDER BY 7 DESC,RECEIPT_SL_NO";
			}
			LogManager.push("Query"+query);
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					SearchVB sVB = new SearchVB();
					sVB.setChequeNo(rset.getString(chequeNo));
					sVB.setChequeAmount(rset.getString(chequeAmt));
					//System.out.print(rset.getString(chequeStatus).trim()+"-");
					if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
					{
						sVB.setRealisation("Realized");
					}
					else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
					{
						sVB.setRealisation("Returned");
					}
					else 
					{
						sVB.setRealisation("Not Known");
					}
					sVB.setReason(rset.getString(reason));
					sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
					sVB.setBankNo1(rset.getString("BANK_NO"));
					sVB.setBankNo2(rset.getString("BANK_NO"));
					sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
					sVB.setReceipt(rset.getString(receipt));
					
					return sVB;
				}
							
			});
		}
		else
		{
			if(sbean.getSearchFor().equalsIgnoreCase("exact")){
			arg[0]=formChequeNo;
			arg[1]="'"+formChequeAmt+"%'";
			query="SELECT RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO="+arg[0]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') ORDER BY 5 DESC,R.RECEIPT_SL_NO";
			}
			else
			{
			arg[0]="%"+formChequeNo+"%";
			arg[1]="'"+formChequeAmt+"%'";
			query="SELECT RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') ORDER BY 5 DESC, R.RECEIPT_SL_NO";
			}
			LogManager.push("Query"+query);
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					SearchVB sVB = new SearchVB();
					sVB.setChequeNo(rset.getString("CHEQUE_NO"));
					sVB.setChequeAmount(rset.getString("AMOUNT"));
					sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
					sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
					sVB.setReceipt(rset.getString("RECEIPT_NO"));
					sVB.setBankNo1(rset.getString("BANK_NO"));
					sVB.setBankNo2(rset.getString("BANK_NO"));
					return sVB;
				}
							
			});
		}
			
		
		}
		catch(Exception e)
		{
			LogManager.push("Exception In SearchDispatcAction - searchInit searchdao(): "+e);
			}
		return list; 
	}
	
	public void updateChequeDetails(final List list, final SearchFormBean sForm) throws CommonBaseException{
		LogManager.push("SearchDAOImpl updateChequeDetails() method");
		LogManager.push("SearchDAOImpl updateChequeDetails() method");
		LogManager.logEnter();
		String bankcode=sForm.getBankId();
		
		if(bankcode.equalsIgnoreCase("all"))
		{
			if(list != null && list.size()>0){
			LogManager.push("Bank code inside code "+bankcode+list.size());
			String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
			List listbank;
			listbank =this.mytemplate.query(query1,new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				return rset.getString("BANK_ID");
			}} );
			final String tableName[]=new String[listbank.size()];
			final String chequeStatus[]=new String[listbank.size()];
			final String bankName[]=new String[listbank.size()];
			final String bankIds[]=new String[listbank.size()];
		 	for(int i=0;i<listbank.size();i++)
			{
			bankIds[i]=listbank.get(i).toString();
			LogManager.push("bank"+i+":"+listbank.get(i).toString());
			List blist=getBankQueryData(listbank.get(i).toString());
			final Map tempMap = (Map)blist.get(0);
			 tableName[i]=((String)tempMap.get("TABLE_NAME"));
			
		    bankName[i]=(String)tempMap.get("BANK_NAME");
			
			 LogManager.push("<--"+tableName[i]+""+chequeStatus[i]+"--**");
			// sbean.setBankName(bankName[i]);
			
			
			 try{
				 
				
				    for(int k=0;k<list.size();k++){
				    	 final SearchVB svb = (SearchVB)list.get(k);
						
							final String updateQuery = "UPDATE "+tableName[i]+" SET ACT_AMT=? ,ACT_NO=?,CASH_TRANSACTION_ID=? WHERE BANK_NO=? ";
							final Object[] arg = new Object[4];
							arg[0] = svb.getActualChequeAmount()==null ? "":svb.getActualChequeAmount();
							arg[1] = svb.getActualChequeNo()==null ? "" : svb.getActualChequeNo() ;
							arg[3] = svb.getBankNo1()== null ? "" : svb.getBankNo1();
							LogManager.push("Bank No in Updation::>"+svb.getBankNo1());
							LogManager.push("svb.getReversal()"+svb.getReversal());
							if(svb.getReversal()!=null){
							if(svb.getReversal().equalsIgnoreCase("R"))
							{
							arg[2] = "-99999";
							
							}
							else
							{
							 arg[2] = "";	
							
							}
							}
							else
							{
								 arg[2] = "";	
									
							}
							LogManager.push("arg[0]"+arg[0]);
							LogManager.push("arg[2]"+arg[2]);
							this.mytemplate.update(updateQuery, arg);
							System.out.print("Update query-->"+updateQuery);
						
	                          					
						
							
					}
				    
				    LogManager.push("break point");
				}catch(Exception e){
					LogManager.fatal(e);
					
				}
				finally
				{
					LogManager.push("end");
				}
			}
			 }
		
		}
		else
		{
		if(list != null && list.size()>0){
		List blist=getBankQueryData(bankcode);
		final SearchVB svb1 = (SearchVB)list.get(0);
		LogManager.push("Cheque amount actual:"+svb1.getActualChequeAmount());
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		try{
			
		    for(int i=0;i<list.size();i++){
				final SearchVB svb = (SearchVB)list.get(i);
				
					final String updateQuery = "UPDATE "+tableName+" SET ACT_AMT=? ,ACT_NO=?,CASH_TRANSACTION_ID=? WHERE BANK_NO=? ";
					System.out.print("Update query-->"+updateQuery);
					final Object[] arg = new Object[4];
					arg[0] = svb.getActualChequeAmount()==null ? "":svb.getActualChequeAmount();
					arg[1] = svb.getActualChequeNo()==null ? "" : svb.getActualChequeNo() ;
					arg[3] = svb.getBankNo1();
					LogManager.push("Bank No in Updation::>"+svb.getBankNo1());
					if(svb.getReversal()!=null){
						if(svb.getReversal().equalsIgnoreCase("R"))
						{
						arg[2] = "-99999";
						
						}
						else
						{
						 arg[2] = "";	
						
						}
						}
						else
						{
							 arg[2] = "";	
								
						}
					this.mytemplate.update(updateQuery, arg);
				
					
			}
			
		}catch(Exception e){
			LogManager.fatal(e);
			
		}
	    }
		}
		LogManager.push("SearchDAOImpl updateChequeDetails() method");
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		
	}
	
	
	public List getRealisedTransactionList(SearchFormBean sbean) throws CommonBaseException {
		String query="";
		String bankcode=sbean.getBankId();
		String searchIn=sbean.getSearchIn();
		String transId=sbean.getTransactionNo();
		List blist=getBankQueryData(bankcode);
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
		final String reason=((String)tempMap.get("REASON"));
		final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
		final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
		final String receipt=((String)tempMap.get("RECEIPT_NO"));
		String realise=sbean.getRealised();
		if(realise.equalsIgnoreCase("yes")){
			realise="NOT";
		}else 
		{
			realise="";
		}
		sbean.setBankName((String)tempMap.get("BANK_NAME"));
		//String tableName=String.valueOf(val.get("TABLE_NAME"));
	    List list=null;
		try{
			
		if(searchIn.equalsIgnoreCase("Bank"))
		{	
			
			query="SELECT "+chequeNo+","+chequeAmt+","+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,"+receipt+",CASH_TRANSACTION_ID FROM "+tableName+" WHERE RECEIPT_SL_NO IS "+realise+" NULL AND BATCHID="+transId+" ORDER BY RECEIPT_SL_NO";
					
			LogManager.push("Query"+query);
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					SearchVB sVB = new SearchVB();
					sVB.setChequeNo(rset.getString(chequeNo));
					sVB.setChequeAmount(rset.getString(chequeAmt));
					//System.out.print(rset.getString(chequeStatus).trim()+"-");
					if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
					{
						sVB.setRealisation("Realized");
					}
					else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
					{
						sVB.setRealisation("Returned");
					}
					else 
					{
						sVB.setRealisation("Not Known");
					}
					if(rset.getString("REJECTION_TYPE")!=null){
						sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
					}
					else
					{
						sVB.setReason(rset.getString(reason));
						
					}sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
					sVB.setBankNo1(rset.getString("BANK_NO"));
					sVB.setBankNo2(rset.getString("BANK_NO"));
					sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
					sVB.setActualChequeAmount(rset.getString("ACT_AMT"));
					sVB.setActualChequeNo(rset.getString("ACT_NO"));
					sVB.setReceipt(rset.getString(receipt));
					if(rset.getString("CASH_TRANSACTION_ID")!=null){
						if(rset.getString("CASH_TRANSACTION_ID").equalsIgnoreCase("-99999"))
							sVB.setReversal("R");
						else
								sVB.setReversal("");
						}
						else
						{
							sVB.setReversal("");
						}
					return sVB;
				}
							
			});
		}
		else
		{
			
			
			query="SELECT RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL AND BATCHID ="+transId+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') ORDER BY R.RECEIPT_SL_NO";
		
			LogManager.push("Query"+query);
			list = this.mytemplate.query(query,new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					SearchVB sVB = new SearchVB();
					sVB.setChequeNo(rset.getString("CHEQUE_NO"));
					sVB.setChequeAmount((String)rset.getString("AMOUNT").toString());
					sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
					sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
					sVB.setBankNo1(rset.getString("BANK_NO"));
					sVB.setBankNo2(rset.getString("BANK_NO"));
					sVB.setReceipt(rset.getString("RECEIPT_NO"));
					if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("C"))
					{
						sVB.setRealisation("Realized");
					}
					else if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("D"))
					{
						sVB.setRealisation("Returned");
					}
					else 
					{
						sVB.setRealisation("Not Known");
					}
					return sVB;
				}
				
				
			});
		}
			
		
		}
		catch(Exception e)
		{
			LogManager.push("Exception In SearchDispatcAction - searchInit searchdao(): "+e);
			}
		LogManager.push("QUERY executed "+query);
		
		return list; 
	
	}
	public List getNotMatchedList(SearchFormBean sbean,String sid) throws CommonBaseException {
		List list=null;
		try {
			List nonMatchedList=sbean.getNonmatchedlist();
			String bankNo="";
			
			String querydel="DELETE FROM TEMP_NONMATCHED WHERE SESSION_ID='"+sid+"'";
			Runner.updation(querydel);
			LogManager.push("Query deleted:::::::::"+querydel);
			if(sbean.getNonmatchedlist()!=null){
				SearchVB nonMatchedVB = (SearchVB)sbean.getNonmatchedlist().get(0);
				bankNo=nonMatchedVB.getBankNo1();
				if(sbean.getNonmatchedlist().size()>0){
					for(int i=0;i<sbean.getNonmatchedlist().size();i++)
					{
					    nonMatchedVB = (SearchVB)sbean.getNonmatchedlist().get(i);
						//LogManager.push("i::>"+i+"bankno::>"+nonMatchedVB.getBankNo1());
						
						String queryinstemp="INSERT INTO TEMP_NONMATCHED VALUES("+nonMatchedVB.getBankNo1()+",'"+sid+"')";
						Runner.inserion(queryinstemp);
						bankNo=bankNo+", "+nonMatchedVB.getBankNo1();
					}
				}
			}
			LogManager.push("BankNoin"+ bankNo);
			String BankIn="";
			if(sbean.getNonmatchedlist()!=null)
			{
				//BankIn = " AND B.BANK_NO IN (" +bankNo +")";
				BankIn = " AND B.BANK_NO =T.BANKNO AND T.SESSION_ID='"+sid+"'";
			}
			
				String bankcode=sbean.getBankId();
				if(bankcode.equalsIgnoreCase("all"))
				{
					String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
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
					
					for(int k=0;k<listbank.size();k++)
					{
					bankIds[k]=listbank.get(k).toString();
					LogManager.push("bank"+i+":"+listbank.get(k).toString());
					List blist=getBankQueryData(listbank.get(k).toString());
					final Map tempMap = (Map)blist.get(0);
					 tableName[k]=((String)tempMap.get("TABLE_NAME"));
					 chequeStatus[k]=((String)tempMap.get("CHEQUE_STATUS"));
					 reason[k]=((String)tempMap.get("REASON"));
					 chequeNo[k]=((String)tempMap.get("CHEQUE_NO"));
				 	 chequeAmt[k]=((String)tempMap.get("CHEQUE_AMT"));
				     bankName[k]=(String)tempMap.get("BANK_NAME");
					 receipt[k]=((String)tempMap.get("RECEIPT_NO"));
					 LogManager.push("<--"+tableName[k]+""+chequeStatus[k]+"-->");
					// sbean.setBankName((String)tempMap.get("BANK_NAME"));
					}
					final String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[0]+",B."+chequeAmt[0]+",B."+reason[0]+",B."+chequeStatus[0]+",B.RECEIPT_SL_NO,TO_CHAR(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[0]+")) AS REJECTION_TYPE,"+receipt[0]+" ,CASH_TRANSACTION_ID FROM "+tableName[0]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND (B.RECEIPT_SL_NO IS NULL ) "+BankIn+" " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[1]+",B."+chequeAmt[1]+",B."+reason[1]+",B."+chequeStatus[1]+",B.RECEIPT_SL_NO,TO_CHAR(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[1]+")) AS REJECTION_TYPE,"+receipt[1]+" ,CASH_TRANSACTION_ID FROM "+tableName[1]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND (B.RECEIPT_SL_NO  IS  NULL ) "+BankIn+" " +
							" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[2]+",B."+chequeAmt[2]+",B."+reason[2]+",B."+chequeStatus[2]+",B.RECEIPT_SL_NO,TO_CHAR(B.POST_DT,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[2]+")) AS REJECTION_TYPE,"+receipt[2]+" ,CASH_TRANSACTION_ID FROM "+tableName[2]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND (B.RECEIPT_SL_NO  IS  NULL ) "+BankIn+" " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[3]+",B."+chequeAmt[3]+",B."+reason[3]+",B."+chequeStatus[3]+",B.RECEIPT_SL_NO,TO_CHAR(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[3]+")) AS REJECTION_TYPE,"+receipt[3]+" ,CASH_TRANSACTION_ID FROM "+tableName[3]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND (B.RECEIPT_SL_NO  IS  NULL ) "+BankIn+" " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[4]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[4]+",B."+chequeAmt[4]+",B."+reason[4]+",B."+chequeStatus[4]+",B.RECEIPT_SL_NO,TO_CHAR(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[4]+")) AS REJECTION_TYPE,"+receipt[4]+" ,CASH_TRANSACTION_ID FROM "+tableName[4]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND (B.RECEIPT_SL_NO  IS  NULL ) "+BankIn+" " +
									"ORDER BY 9 DESC";
					LogManager.push("Query for BANK LIST matched col:==>"+query+"count-->");
					list = this.mytemplate.query(query, new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setChequeNo(rset.getString(chequeNo[0]));
							sVB.setChequeAmount((String)rset.getString(chequeAmt[0]).toString());
							//System.out.print(rset.getString(chequeStatus).trim()+"-");
							if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("R"))
							{
								sVB.setRealisation("Reversal");
							}
							else 
							{
								sVB.setRealisation("Not Known");
							}
							sVB.setReason(rset.getString(reason[0]));
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
							sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
							sVB.setActualChequeAmount(rset.getString("ACTUAL_CHEQUE_AMT"));
							sVB.setReceipt(rset.getString(receipt[0]));
							sVB.setBankName(rset.getString("BANK"));
							if(rset.getString("CASH_TRANSACTION_ID")!=null){
								if(rset.getString("CASH_TRANSACTION_ID").equalsIgnoreCase("-99999"))
									sVB.setReversal("R");
								else
										sVB.setReversal("");
								}
								else
								{
									sVB.setReversal("");
								}
							return sVB;
						}
									
					});
				}
				else
				{
			
				List blist=getBankQueryData(bankcode);
				final Map tempMap = (Map) blist.get(0);
				final String tableName=((String)tempMap.get("TABLE_NAME"));
				final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
				final String reason=((String)tempMap.get("REASON"));
				final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
				final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
				final String receipt=((String)tempMap.get("RECEIPT_NO"));
				
				String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankcode+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",B.DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason+")) AS REJECTION_TYPE,"+receipt+" ,CASH_TRANSACTION_ID ,B.RECEIPT_SL_NO FROM "+tableName+" B , TEMP_NONMATCHED T  WHERE (B.ACTUAL_CHEQUE_NO IS NOT NULL OR B.ACTUAL_CHEQUE_AMT IS NOT NULL ) AND (B.RECEIPT_SL_NO IS NULL ) "+BankIn+" ORDER BY 8 DESC" ;
				LogManager.push("Query for BANK LIST matched col:==>"+query+"count-->");
				list = this.mytemplate.query(query, new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						sVB.setChequeNo(rset.getString(chequeNo));
						sVB.setChequeAmount((String)rset.getString(chequeAmt).toString());
						//System.out.print(rset.getString(chequeStatus).trim()+"-");
						if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
						{
							sVB.setRealisation("Realized");
						}
						else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
						{
							sVB.setRealisation("Returned");
						}
						else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
						{
							sVB.setRealisation("Reversal");
						}
						else 
						{
							sVB.setRealisation("Not Known");
						}
						if(rset.getString("REJECTION_TYPE")!=null){
							sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
							}
						else
						{
							sVB.setReason(rset.getString(reason));
							
						}sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
						sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
						sVB.setActualChequeAmount(rset.getString("ACTUAL_CHEQUE_AMT"));
						sVB.setReceipt(rset.getString(receipt));
						sVB.setBankName(rset.getString("BANK"));
						if(rset.getString("CASH_TRANSACTION_ID")!=null){
							if(rset.getString("CASH_TRANSACTION_ID").equalsIgnoreCase("-99999"))
								sVB.setReversal("R");
							else
									sVB.setReversal("");
							}
							else
							{
								sVB.setReversal("");
							}
						return sVB;
					}
								
				});
				
				}
				
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public void getReceiptDetail(ReceiptDetails cForm) throws CommonBaseException {
		LogManager.push("SearchDAoImpl getReceiptDetail() method enter");
		LogManager.logEnter();
		
		String query1="SELECT unique B.BANK_ID FROM RECEIPT_MASTER A,BANK_ACCOUNT_CODE B WHERE A.BANK_CODE=B.BANK_ACCOUNT_CODE AND A.RECEIPT_SL_NO = "+cForm.getReceiptNo();
		LogManager.push("RECEIPT POPUP:"+query1);
		Object result=null;
		try
		{
		 result=this.mytemplate.queryForObject(query1,new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			return rset.getString("BANK_ID");
			}} );
		}
		catch(Exception e)
		{
			System.out.println("-----------Bank Id is not valilable for this recipt no"+cForm.getReceiptNo());
		}
		if(result!=null)
		{
			
			cForm.setBankAvail("Y");
		//LogManager.push("bank id from "+result.toString());
		LogManager.push("Bank no>>>>>"+result.toString());
		List blist=getBankQueryData(result.toString());
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
		final String reason=((String)tempMap.get("REASON"));
		final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
		final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
		
		try{
			Map map;
			//final String query = "SELECT A.RECEIPT_NO,A.CHEQUE_NO AS CHEQUE,A.AMOUNT,(SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID=B.BANK_ID) AS BANK_NAME,TO_CHAR(A.RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,A.RECEIPT_AG_NAME,C."+chequeNo+",C."+chequeAmt+",C.ACTUAL_CHEQUE_NO ACT_NO,C.ACTUAL_CHEQUE_AMT ACT_AMT,C.BATCHID,C."+reason+" FROM RECEIPT_MASTER A,BANK_ACCOUNT_CODE B,"+tableName+" C WHERE A.BANK_NO=C.BANK_NO AND A.BANK_CODE = B.BANK_ACCOUNT_CODE AND A.RECEIPT_SL_NO=? AND ( C."+chequeStatus+" like 'D%'OR C."+chequeStatus+" like'C%')";
			final String query = "SELECT A.RECEIPT_NO,A.CHEQUE_NO AS CHEQUE,A.AMOUNT,BANK_NAME_AND_LOC AS BANK_NAME,TO_CHAR(A.RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,A.RECEIPT_AG_NAME,C."+chequeNo+",C."+chequeAmt+",C.ACTUAL_CHEQUE_NO ACT_NO,C.ACTUAL_CHEQUE_AMT ACT_AMT,C.BATCHID,C."+reason+" FROM RECEIPT_MASTER A,BANK_ACCOUNT_CODE B,"+tableName+" C WHERE A.BANK_NO=C.BANK_NO AND A.BANK_CODE = B.BANK_ACCOUNT_CODE AND A.RECEIPT_SL_NO=? AND ( C."+chequeStatus+" like 'D%'OR C."+chequeStatus+" like'C%' OR C."+chequeStatus+" like 'R%')";
			LogManager.push("Query executed:-->"+query+"receiptno:>>>"+cForm.getReceiptNo());
			map = this.mytemplate.queryForMap(query,new Object[]{ cForm.getReceiptNo() });
			cForm.setReceipt((String)(map.get("RECEIPT_NO")==null?"":map.get("RECEIPT_NO") + ""));
			cForm.setChequeAmount((String)(map.get("AMOUNT")==null?"":map.get("AMOUNT") + ""));
			cForm.setChequeNo((String)(map.get("CHEQUE")==null?"":map.get("CHEQUE") + ""));
			cForm.setTransactionNo((String)(map.get("BATCHID")==null?"":map.get("BATCHID") + ""));
			cForm.setBankName((String)(map.get("BANK_NAME")==null?"":map.get("BANK_NAME") + ""));
			cForm.setReason((String)(map.get(reason)==null?"":map.get(reason) + ""));
			cForm.setActualChequeNo((String)(map.get("ACT_NO")==null?"":map.get("ACT_NO") + ""));
			cForm.setActualChequeAmount((String)(map.get("ACT_AMT")==null?"":map.get("ACT_AMT") + ""));
			cForm.setBankChequeNo((String)(map.get(chequeNo)==null?"":map.get(chequeNo) + ""));
			cForm.setBankChequeAmt((String)(map.get(chequeAmt)==null?"":map.get(chequeAmt) + ""));
			cForm.setReceiptDate((String)(map.get("RECEIPT_DATE")==null?"":map.get("RECEIPT_DATE") + ""));
			cForm.setName((String)(map.get("RECEIPT_AG_NAME")==null?"":map.get("RECEIPT_AG_NAME") + ""));
			//LogManager.push("cForm.getChequeAmount-->"+cForm.getChequeAmount());
			
		   }catch(Exception e){
			LogManager.fatal(e);
		}
		}
		else
		{
			cForm.setBankAvail("N");
		}
		LogManager.push("SearchDAoImpl getReceiptDetail() method exit");
		LogManager.logExit();
		LogManager.popRemove(); 
		
		
	}
   
	public List getNotRealizedList(SearchFormBean sbean) throws CommonBaseException {
		  LogManager.push("Inside getnotrealizedlist:");
			
		String query="";
		String bankcode=sbean.getBankId();
		String searchIn=sbean.getSearchIn();
		String formChequeNo=sbean.getChequeNo();
		String formChequeAmt=sbean.getChequeAmount();
		String formreceipt=sbean.getReceiptNo();
		LogManager.push("getSearchList formChequeNo "+formChequeNo+ "formreceipt:"+formreceipt);
		LogManager.push("getSearchList search in:"+searchIn);
		String chequeamts=sbean.getActualChequeAmount();
		LogManager.push("sbean.getactualchequeamt"+chequeamts);
		String transId=sbean.getTransactionNo();
		LogManager.push("getSearchList search in:"+searchIn);
		String transSource=" AND R.TRANS_SOURCE='RECT' ";
		String realise="";
		
		
		//String tableName=String.valueOf(val.get("TABLE_NAME"));
	    List list=null;
	    LogManager.push("transaction code"+transId+"formchequeno:"+formChequeNo);
		if(!transId.equalsIgnoreCase(""))
		{
			LogManager.push("not empty");
		}
		else
		{
			LogManager.push("empty");
		}
		try{
			LogManager.push("transaction code"+transId+"formchequeno:"+formChequeNo);
			//SEARCH BY TRANSACTIONS
			if((!transId.equalsIgnoreCase("")))
			{
				LogManager.push("Inside transaction 1 code"+transId);
				if(searchIn.equalsIgnoreCase("Bank"))
				{	
					List blist=getBankQueryData(bankcode);
					final Map tempMap = (Map) blist.get(0);
					final String tableName=((String)tempMap.get("TABLE_NAME"));
					final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
					final String reason=((String)tempMap.get("REASON"));
					final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
					final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
					final String bankName=(String)tempMap.get("BANK_NAME");
					final String receipt=((String)tempMap.get("RECEIPT_NO"));
					
					sbean.setBankName((String)tempMap.get("BANK_NAME"));
					 query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+",CASH_TRANSACTION_ID FROM "+tableName+" T WHERE RECEIPT_SL_NO IS "+realise+" NULL and DISHONOUR_TYPE is null AND "+chequeNo+" IS NOT NULL AND BATCHID="+transId+"  AND "+chequeStatus+" NOT LIKE 'H'  ORDER BY 7 DESC,"+chequeStatus+" DESC";
							
					LogManager.push("Query"+query);
					list = this.mytemplate.query(query, new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setChequeNo(rset.getString(chequeNo));
							sVB.setChequeAmount(rset.getString(chequeAmt));
							//System.out.print(rset.getString(chequeStatus).trim()+"-");
							if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
							{
								sVB.setRealisation("Reversal");
							}
							else 
							{
								sVB.setRealisation("Not Known");
							}
							if(rset.getString("REJECTION_TYPE")!=null){
								sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
							}
							else
							{
								sVB.setReason(rset.getString(reason));
								
							}
							sVB.setReceipt(rset.getString(receipt));
							sVB.setBankName(bankName);
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setBankNo1(rset.getString("BANK_NO"));
							sVB.setBankNo2(rset.getString("BANK_NO"));
							sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
							sVB.setActualChequeAmount(rset.getString("ACT_AMT"));
							sVB.setActualChequeNo(rset.getString("ACT_NO"));
							if(rset.getString("CASH_TRANSACTION_ID")!=null){
								if(rset.getString("CASH_TRANSACTION_ID").equalsIgnoreCase("-99999"))
									sVB.setReversal("R");
								else
										sVB.setReversal("");
								}
								else
								{
									sVB.setReversal("");
								}
							i++;
							return sVB;
						}
									
					});
				}
				else
				{
					
					
					query="SELECT R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID IN (SELECT B.BANK_ID FROM RECEIPT_MASTER A,BANK_ACCOUNT_CODE B WHERE A.BANK_CODE=B.BANK_ACCOUNT_CODE AND A.RECEIPT_SL_NO = R.RECEIPT_SL_NO) ) AS BANK_NAME,RECEIPT_NO,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL AND BATCHID ="+transId+"  AND CHEQUE_NO IS NOT NULL ORDER BY R.RECEIPT_SL_NO";
				
					LogManager.push("Query"+query);
					list = this.mytemplate.query(query,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							
							sVB.setBankName(rset.getString("BANK_NAME"));
							sVB.setChequeNo(rset.getString("CHEQUE_NO"));
							sVB.setChequeAmount(rset.getString("AMOUNT"));
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
							sVB.setReceipt(rset.getString("RECEIPT_NO"));
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
				}
					
				
			}
			//SEARCH BY VALUES GIVEN
			else
			{
				/**Start
				 * 
				 */
//				Search in all banks	
				if(bankcode.equalsIgnoreCase("all"))
				{
					String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
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
						String[] arg = new String[15];
						LogManager.push("inside blocks::::");
						//Search in bank statement 
					if(searchIn.equalsIgnoreCase("Bank"))
					{	
						
						if(sbean.getSearchFor().equalsIgnoreCase("exact")){
							if(!formChequeNo.equalsIgnoreCase("") )
							{
								arg[0]="AND "+chequeNo[0]+"='"+formChequeNo+"' ";
								arg[3]="AND "+chequeNo[1]+"='"+formChequeNo+"' ";
								arg[6]="AND "+chequeNo[2]+"='"+formChequeNo+"' ";
								arg[9]="AND "+chequeNo[3]+"='"+formChequeNo+"' ";
								arg[12]="AND "+chequeNo[4]+"='"+formChequeNo+"' ";
							 }
							else if(formChequeNo.equalsIgnoreCase(""))
							{
								arg[0]="";
								arg[3]="";
								arg[6]="";
								arg[9]="";
								arg[12]="";
							}
							if(!formChequeAmt.equalsIgnoreCase("") )
							{
								arg[1]="AND "+"trunc("+chequeAmt[0]+")"+"="+"trunc("+formChequeAmt+") ";
								arg[4]="AND "+"trunc("+chequeAmt[1]+")"+"="+"trunc("+formChequeAmt+") ";
								arg[7]="AND "+"trunc("+chequeAmt[2]+")"+"="+"trunc("+formChequeAmt+") ";
								arg[10]="AND "+"trunc("+chequeAmt[3]+")"+"="+"trunc("+formChequeAmt+") ";
								arg[13]="AND "+"trunc("+chequeAmt[4]+")"+"="+"trunc("+formChequeAmt+") ";
							}
							else if (formChequeAmt.equalsIgnoreCase("") )
							{
								arg[1]="";
								arg[4]="";
								arg[7]="";
								arg[10]="";
								arg[13]="";
							}
							if(!formreceipt.equalsIgnoreCase(""))
							{
								arg[2]="AND "+receipt[0]+"='"+formreceipt+"' ";
								arg[5]="AND "+receipt[1]+"='"+formreceipt+"' ";
								arg[8]="AND "+receipt[2]+"='"+formreceipt+"' ";
								arg[11]="AND "+receipt[3]+"='"+formreceipt+"' ";
								arg[14]="AND "+receipt[4]+"='"+formreceipt+"' ";
							 }
							else if(formreceipt.equalsIgnoreCase(""))
							{
								arg[2]="";
								arg[5]="";
								arg[8]="";
								arg[11]="";
								arg[14]="";
							 }
							query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[0]
									+ "') AS BANK, "
									+ chequeNo[0]
									+ ","
									+ chequeAmt[0]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[0]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[0]
									+ ","
									+ reason[0]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[0]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[0]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[0]
									+ " T WHERE RECEIPT_SL_NO IS "
									+ realise
									+ " NULL and DISHONOUR_TYPE is null "
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ "AND "
									+ chequeStatus[0]
									+ " NOT LIKE 'H' UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[1]
									+ "') AS BANK,"
									+ chequeNo[1]
									+ ","
									+ chequeAmt[1]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[1]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[1]
									+ ","
									+ reason[1]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[1]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[1]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[1]
									+ " T WHERE RECEIPT_SL_NO IS "
									+ realise
									+ " NULL and DISHONOUR_TYPE is null "
									+ arg[3]
									+ arg[4]
									+ arg[5]
									+ "AND "
									+ chequeStatus[1]
									+ " NOT LIKE 'H' " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[2]
									+ "') AS BANK,"
									+ chequeNo[2]
									+ ","
									+ chequeAmt[2]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[2]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[2]
									+ ","
									+ reason[2]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[2]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[2]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[2]
									+ " T WHERE RECEIPT_SL_NO IS "
									+ realise
									+ " NULL and DISHONOUR_TYPE is null "
									+ arg[6]
									+ arg[7]
									+ arg[8]
									+ "AND "
									+ chequeStatus[2]
									+ " NOT LIKE 'H' " +
							" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[3]
									+ "') AS BANK,"
									+ chequeNo[3]
									+ ","
									+ chequeAmt[3]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[3]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[3]
									+ ","
									+ reason[3]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[3]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[3]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[3]
									+ " T WHERE RECEIPT_SL_NO IS "
									+ realise
									+ " NULL and DISHONOUR_TYPE is null "
									+ arg[9]
									+ arg[10]
									+ arg[11]
									+ "AND "
									+ chequeStatus[3]
									+ " NOT LIKE 'H' " +
							  " UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[4]
									+ "') AS BANK,"
									+ chequeNo[4]
									+ ","
									+ chequeAmt[4]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[4]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[4]
									+ ","
									+ reason[4]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[4]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[4]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[4]
									+ " T WHERE RECEIPT_SL_NO IS "
									+ realise
									+ " NULL and DISHONOUR_TYPE is null "
									+ arg[12]
									+ arg[13]
									+ arg[14]
									+ "AND "
									+ chequeStatus[4]
									+ " NOT LIKE 'H' " +
									
											"ORDER BY 8 DESC, 4 DESC";

						
					    }
						else
						{
							arg[0]="'%"+formChequeNo+"%'";
							arg[1]="'"+formChequeAmt+"%'";
							arg[2]="'"+formreceipt+"%'";
							arg[3]="'%"+formChequeNo+"%'";
							arg[4]="'"+formChequeAmt+"%'";
							arg[5]="'"+formreceipt+"%'";
							arg[6]="'%"+formChequeNo+"%'";
							arg[7]="'"+formChequeAmt+"%'";
							arg[8]="'"+formreceipt+"%'";
							arg[9]="'%"+formChequeNo+"%'";
							arg[10]="'"+formChequeAmt+"%'";
							arg[11]="'"+formreceipt+"%'";
							arg[12]="'%"+formChequeNo+"%'";
							arg[13]="'"+formChequeAmt+"%'";
							arg[14]="'"+formreceipt+"%'";
							
							String condition=" where to_date(Deposit_date,'dd/mm/yyyy') between  add_months(to_date(sysdate,'dd/mm/rrrr'),-6) and to_date('"+sbean.getFromDate()+"','dd/mm/yyyy')";

							query = "select * from (SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[0]
									+ "') AS BANK,"
									+ chequeNo[0]
									+ ","
									+ chequeAmt[0]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[0]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[0]
									+ ","
									+ reason[0]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[0]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[0]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[0]
									+ " T WHERE RECEIPT_SL_NO IS "
									+ realise
									+ " NULL and DISHONOUR_TYPE is null AND "
									+ chequeNo[0]
									+ " LIKE "
									+ arg[0]
									+ " AND "
									+ chequeAmt[0]
									+ " LIKE "
									+ arg[1]
									+ " AND "
									+ receipt[0]
									+ " LIKE "
									+ arg[2]
									+ " AND "
									+ chequeStatus[0]
									+ " NOT LIKE 'H' UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[1]
									+ "') AS BANK,"
									+ chequeNo[1]
									+ ","
									+ chequeAmt[1]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[1]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[1]
									+ ","
									+ reason[1]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[1]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[1]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[1]
									+ " T WHERE RECEIPT_SL_NO IS "
									+ realise
									+ " NULL and DISHONOUR_TYPE is null AND "
									+ chequeNo[1]
									+ " LIKE "
									+ arg[3]
									+ " AND "
									+ chequeAmt[1]
									+ " LIKE "
									+ arg[4]
									+ " AND "
									+ receipt[1]
									+ " LIKE "
									+ arg[5]
									+ " AND "
									+ chequeStatus[1]
									+ " NOT LIKE 'H' " +
									" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[2]
									+ "') AS BANK,"
									+ chequeNo[2]
									+ ","
									+ chequeAmt[2]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[2]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[2]
									+ ","
									+ reason[2]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[2]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[2]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[2]
									+ " T WHERE RECEIPT_SL_NO IS "
									+ realise
									+ " NULL and DISHONOUR_TYPE is null AND "
									+ chequeNo[2]
									+ " LIKE "
									+ arg[6]
									+ " AND "
									+ chequeAmt[2]
									+ " LIKE "
									+ arg[7]
									+ " AND "
									+ receipt[2]
									+ " LIKE "
									+ arg[8]
									+ " AND "
									+ chequeStatus[2]
									+ " NOT LIKE 'H'" 
                                 +" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[3]
									+ "') AS BANK,"
									+ chequeNo[3]
									+ ","
									+ chequeAmt[3]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[3]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[3]
									+ ","
									+ reason[3]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[3]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[3]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[3]
									+ " T WHERE RECEIPT_SL_NO IS "
									+ realise
									+ " NULL and DISHONOUR_TYPE is null AND "
									+ chequeNo[3]
									+ " LIKE "
									+ arg[9]
									+ " AND "
									+ chequeAmt[3]
									+ " LIKE "
									+ arg[10]
									+ " AND "
									+ receipt[3]
									+ " LIKE "
									+ arg[11]
									+ " AND "
									+ chequeStatus[3]
									+ " NOT LIKE 'H' " +
							" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[4]
									+ "') AS BANK,"
									+ chequeNo[4]
									+ ","
									+ chequeAmt[4]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[4]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[4]
									+ ","
									+ reason[4]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[4]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[4]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[4]
									+ " T WHERE RECEIPT_SL_NO IS "
									+ realise
									+ " NULL and DISHONOUR_TYPE is null AND "
									+ chequeNo[4]
									+ " LIKE "
									+ arg[12]
									+ " AND "
									+ chequeAmt[4]
									+ " LIKE "
									+ arg[13]
									+ " AND "
									+ receipt[4]
									+ " LIKE "
									+ arg[14]
									+ " AND "
									+ chequeStatus[4]
									+ " NOT LIKE 'H' " +		
									
									"ORDER BY 8 DESC,4 DESC) "+condition;
							
						}
						LogManager.push("Query"+query);
						LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
						list = this.mytemplate.query(query,new RowMapper() {
							public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
								SearchVB sVB = new SearchVB();
								sVB.setBankName(rset.getString(1));
								sVB.setChequeNo(rset.getString(2));
								sVB.setChequeAmount(rset.getString(3));
								//System.out.print(rset.getString(chequeStatus).trim()+"-");
								if(rset.getString(4).trim().equalsIgnoreCase("C"))
								{
									sVB.setRealisation("Realized");
								}
								else if(rset.getString(4).trim().equalsIgnoreCase("D"))
								{
									sVB.setRealisation("Returned");
								}
								else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("R"))
								{
									sVB.setRealisation("Reversal");
								}
								else 
								{
									sVB.setRealisation("Not Known");
								}
								if(rset.getString(11)!=null){
									sVB.setReason(rset.getString(5)+"("+rset.getString(11)+")");
								}
								else
								{
									sVB.setReason(rset.getString(5));
									
								}
								//sVB.setBankName(bankName);
								sVB.setReceiptNo(rset.getString(6));
								sVB.setBankNo1(rset.getString(7));
								sVB.setBankNo2(rset.getString(7));
								sVB.setDepositDate(rset.getString(8));
								sVB.setActualChequeAmount(rset.getString(10));
								sVB.setActualChequeNo(rset.getString(9));
								sVB.setReceipt(rset.getString(12));
								if(rset.getString("CASH_TRANSACTION_ID")!=null){
									if(rset.getString("CASH_TRANSACTION_ID").equalsIgnoreCase("-99999"))
										sVB.setReversal("R");
									else
											sVB.setReversal("");
									}
									else
									{
										sVB.setReversal("");
									}
								return sVB;
							}
										
						});
					}
					//Search in Receipt statement
					else
					{
						if(sbean.getSearchFor().equalsIgnoreCase("exact")){
							if(!formChequeNo.equalsIgnoreCase("") )
							{
								arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"' ";
								
							 }
							else if(formChequeNo.equalsIgnoreCase(""))
							{
								arg[0]="";
							}
							if(!formChequeAmt.equalsIgnoreCase("") )
							{
								arg[1]="AND trunc(R.AMOUNT)=trunc("+formChequeAmt+") ";
							}
							else if (formChequeAmt.equalsIgnoreCase("") )
							{
								arg[1]="";
							}
							if(!formreceipt.equalsIgnoreCase(""))
							{
								arg[2]="AND RECEIPT_NO='"+formreceipt+"' ";
							 }
							else if(formreceipt.equalsIgnoreCase(""))
							{
								arg[2]="";
							 }
							query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[0]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL "
									+ transSource
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[0]
									+ "' AND STATUS='Y')   UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[1]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL "
									+ transSource
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[1]
									+ "' AND STATUS='Y')  " +
								" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[2]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL "
									+ transSource
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[2]
									+ "' AND STATUS='Y') " +
								" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[3]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL "
									+ transSource
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[3]
									+ "' AND STATUS='Y') " +
								" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[4]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL "
									+ transSource
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[4]
									+ "' AND STATUS='Y') " +		
									
									"ORDER BY 6 DESC,RECEIPT_SL_NO";
							}
						
						else
						{
						arg[0]="'%"+formChequeNo+"%'";
						arg[1]="'"+formChequeAmt+"%'";
						arg[2]="'"+formreceipt+"%'";
						query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[0]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,MANUAL_UPDATE  FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL AND ( R.CHEQUE_NO LIKE "
									+ arg[0]
									+ " or r.cheque_no is null ) AND R.RECEIPT_NO LIKE "
									+ arg[2]
									+ " AND R.AMOUNT LIKE "
									+ arg[1]
                                    + transSource									      
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[0]
									+ "' AND STATUS='Y')  " +
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[1]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL AND ( R.CHEQUE_NO LIKE "
									+ arg[0]
									+ " or r.cheque_no is null ) AND R.RECEIPT_NO LIKE "
									+ arg[2]
									+ " AND R.AMOUNT LIKE "
									+ arg[1]
									+ transSource      
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[1]
									+ "' AND STATUS='Y')  " +
								" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[2]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL AND( R.CHEQUE_NO LIKE "
									+ arg[0]
									+ " or r.cheque_no is null ) AND R.RECEIPT_NO LIKE "
									+ arg[2]
									+ " AND R.AMOUNT LIKE "
									+ arg[1]
									+ transSource      
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[2]
									+ "' AND STATUS='Y') " +
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[3]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL AND ( R.CHEQUE_NO LIKE "
									+ arg[0]
									+ " or r.cheque_no is null ) AND R.RECEIPT_NO LIKE "
									+ arg[2]
									+ " AND R.AMOUNT LIKE "
									+ arg[1]
									+ transSource      
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[3]
									+ "' AND STATUS='Y') " +
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[4]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,MANUAL_UPDATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL AND ( R.CHEQUE_NO LIKE "
									+ arg[0]
									+ " or r.cheque_no is null ) AND R.RECEIPT_NO LIKE "
									+ arg[2]
									+ " AND R.AMOUNT LIKE "
									+ arg[1]
									+ transSource      
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[4]
									+ "' AND STATUS='Y') " +
									"ORDER BY 6 DESC,RECEIPT_SL_NO";
						}
						LogManager.push("Query"+query);
						list = this.mytemplate.query(query,  new RowMapper() {
							public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
								SearchVB sVB = new SearchVB();
								sVB.setChequeNo(rset.getString("CHEQUE_NO"));
								
								sVB.setChequeAmount((String)rset.getString("AMOUNT").toString());
								sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
								sVB.setBankName(rset.getString("BANK"));
								sVB.setReceipt(rset.getString("RECEIPT_NO"));
								if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("C"))
								{
									sVB.setRealisation("Realized");
								}
								else if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("D"))
								{
									sVB.setRealisation("Returned");
								}
								else if((rset.getString("MANUAL_UPDATE")==null?"":rset.getString("MANUAL_UPDATE")).trim().equalsIgnoreCase("Y"))
								{
									sVB.setRealisation("Manual Realized");
								}
								
								else 
								{
									sVB.setRealisation("Not Known");
								}
								sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
								return sVB;
							}});
					}
				}	
				//------end-------------
						
				
				//not searched for all banks
				else
				{
				/*End
				 * 
				 */
				List blist=getBankQueryData(bankcode);
				final Map tempMap = (Map) blist.get(0);
				final String tableName=((String)tempMap.get("TABLE_NAME"));
				final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
				final String reason=((String)tempMap.get("REASON"));
				final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
				final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
				final String bankName=(String)tempMap.get("BANK_NAME");
				final String receipt=((String)tempMap.get("RECEIPT_NO"));
				
				sbean.setBankName((String)tempMap.get("BANK_NAME"));
				LogManager.push("Inside search code");
			String[] arg = new String[4];
			
		
		if(searchIn.equalsIgnoreCase("Bank"))
		{	
			if(sbean.getSearchFor().equalsIgnoreCase("exact")){
				if(!formChequeNo.equalsIgnoreCase("") )
				{
					arg[0]=" AND "+chequeNo+"='"+formChequeNo+"'";
				 }
				else if(formChequeNo.equalsIgnoreCase(""))
				{
					arg[0]=" ";
				}
				if(!formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]=" AND "+"trunc("+chequeAmt+")=trunc('"+formChequeAmt+"')";
				}
				else if (formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]=" ";
				}
				if(!formreceipt.equalsIgnoreCase(""))
				{
					arg[2]=" AND "+receipt+"='"+formreceipt+"'";
				 }
				else if(formreceipt.equalsIgnoreCase(""))
				{
					arg[2]=" ";
				 }
				query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+",CASH_TRANSACTION_ID FROM "+tableName+" T WHERE RECEIPT_SL_NO IS "+realise+" NULL and DISHONOUR_TYPE is null "+arg[0]+arg[1]+arg[2]+"  AND "+chequeStatus+" NOT LIKE 'H' ORDER BY 7 DESC,"+chequeStatus+" DESC";
				
			}
			else
			{
			arg[0]="'%"+formChequeNo+"%'";
			arg[1]="'"+formChequeAmt+"%'";
			arg[2]="'"+formreceipt+"%'";
			String condition=" where to_date(Deposit_date,'dd/mm/yyyy') between  add_months(to_date(sysdate,'dd/mm/rrrr'),-6) and to_date('"+sbean.getFromDate()+"','dd/mm/yyyy')";

			query="select * from (SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+",CASH_TRANSACTION_ID FROM "+tableName+" T WHERE RECEIPT_SL_NO IS "+realise+" NULL and DISHONOUR_TYPE is null AND "+chequeNo+" LIKE "+arg[0]+" AND "+chequeAmt+" LIKE "+arg[1]+" AND "+receipt+" LIKE "+arg[2]+"  AND "+chequeStatus+" NOT LIKE 'H'  ORDER BY 7 DESC,"+chequeStatus+" DESC) "+condition;
			}
			LogManager.push("Query"+query);
			LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
			
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					SearchVB sVB = new SearchVB();
					sVB.setChequeNo(rset.getString(chequeNo));
					sVB.setChequeAmount(rset.getString(chequeAmt));
					//System.out.print(rset.getString(chequeStatus).trim()+"-");
					if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
					{
						sVB.setRealisation("Realized");
					}
					else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
					{
						sVB.setRealisation("Returned");
					}
					else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
					{
						sVB.setRealisation("Reversal");
					}
					else 
					{
						sVB.setRealisation("Not Known");
					}
					if(rset.getString("REJECTION_TYPE")!=null){
					sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
					}
					else
					{
						sVB.setReason(rset.getString(reason));
						
					}
					sVB.setBankName(bankName);
					
					sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
					sVB.setBankNo1(rset.getString("BANK_NO"));
					sVB.setBankNo2(rset.getString("BANK_NO"));
					sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
					sVB.setActualChequeAmount(rset.getString("ACT_AMT"));
					sVB.setActualChequeNo(rset.getString("ACT_NO"));
					if(rset.getString("CASH_TRANSACTION_ID")!=null){
					if(rset.getString("CASH_TRANSACTION_ID").equalsIgnoreCase("-99999"))
						sVB.setReversal("R");
					else
							sVB.setReversal("");
					}
					else
					{
						sVB.setReversal("");
					}
					sVB.setReceipt(rset.getString(receipt));
					i++;
					return sVB;
				}
							
			});
		}
		else
		{
			if(sbean.getSearchFor().equalsIgnoreCase("exact")){
				if(!formChequeNo.equalsIgnoreCase("") )
				{
					arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"'";
				 }
				else if(formChequeNo.equalsIgnoreCase(""))
				{
					arg[0]=" ";
				}
				if(!formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]=" AND trunc(R.AMOUNT)=trunc('"+formChequeAmt+"')";
				}
				else if (formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]=" ";
				}
				if(!formreceipt.equalsIgnoreCase(""))
				{
					arg[2]=" AND R.RECEIPT_NO='"+formreceipt+"'";
				 }
				else if(formreceipt.equalsIgnoreCase(""))
				{
					arg[2]=" ";
				 }
				query="SELECT R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,RECEIPT_NO,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL "+transSource+" "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 4 DESC,R.RECEIPT_SL_NO";
				
			}
			else
			{
			arg[0]="'%"+formChequeNo+"%'";
			arg[1]="'"+formChequeAmt+"%'";
			arg[2]="'"+formreceipt+"%'";
			query="SELECT R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,RECEIPT_NO,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL "+transSource+" AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 4 DESC,R.RECEIPT_SL_NO";
			}
			//query="SELECT R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,RECEIPT_NO FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE '4%' AND R.AMOUNT LIKE '%' AND R.RECEIPT_NO LIKE '%' AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY R.RECEIPT_SL_NO";
			
			LogManager.push("Query"+query);
			LogManager.push(arg[0]+":"+""+arg[1]+":"+arg[2]);
			
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int args) throws SQLException {
					SearchVB sVB = new SearchVB();
					sVB.setBankName(bankName);
					sVB.setChequeNo(rset.getString("CHEQUE_NO"));
					sVB.setChequeAmount(rset.getString("AMOUNT"));
					sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
					sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
					sVB.setReceipt(rset.getString("RECEIPT_NO"));
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
		}
	    }
			}
		}
		catch(Exception e)
		{
			LogManager.push("Exception In SearchDAOImpl - searchResult searchdao(): "+e);
			e.printStackTrace();
		}
		LogManager.push("QUERY executed "+query);
		//LogManager.push("Returning list size"+list.size());
		return list; 	
	}
	
	public List getBankDuplicates(String transid, String bankid) throws CommonBaseException {
		
		  List list=null;
		  List blist=getBankQueryData(bankid);
			final Map tempMap = (Map) blist.get(0);
			final String tableName=((String)tempMap.get("TABLE_NAME"));
			final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
			//final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
		    //final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
		  String  query="SELECT * FROM DUP_"+tableName+" WHERE TRANSACTION_ID="+transid +" AND STATUS IN ('D','E') ORDER BY 3 DESC, "+chequeNo;
		  LogManager.push("Query executed:"+query);
		  /* list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					SearchVB sVB = new SearchVB();
					sVB.setChequeNo(rset.getString(chequeNo));
					sVB.setChequeAmount(rset.getString(chequeAmt));
					sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
					sVB.setCheckStatus(chequeStatus);
					return sVB;
				}
							
			});*/
		  if(bankid.equalsIgnoreCase("CIT")){
			  list = this.mytemplate.query(query, new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						InvalidVB sVB = new InvalidVB();
											
						sVB.setC_clientcode(rset.getString("CLIENT_CODE"));
						sVB.setC_depositdate(rset.getString("DEPOSIT_DATE"));
						sVB.setC_product(rset.getString("PRODUCT"));
						sVB.setC_creditdebitdate(rset.getString("CREDIT_DEBIT_DATE"));
						sVB.setC_location(rset.getString("LOCATION"));
						sVB.setC_chequeno(rset.getString("CHEQUE_NO"));
						sVB.setC_chequeamt(rset.getString("CHEQUE_AMT"));
						sVB.setC_typecrdr(rset.getString("TYPE_CRDR"));
						sVB.setC_narration(rset.getString("NARRATION"));
						sVB.setC_cbpno(rset.getString("CBP_NO"));
						sVB.setC_depslipno(rset.getString("DEPSLIPNO"));
						sVB.setC_customerref(rset.getString("CUSTOMERREF"));
						sVB.setC_depositamt(rset.getString("DEPOSIT_AMT"));
						sVB.setC_dwebankcode(rset.getString("DWE_BANK_CODE"));
						sVB.setC_checkdata(rset.getString("CHECK_DATA"));
						sVB.setC_covernoteno(rset.getString("COVERNOTENO"));
						sVB.setC_bankname(rset.getString("BANK_NAME"));
						sVB.setC_pickpointname(rset.getString("PICK_POINT_NAME"));
						sVB.setC_pkuppointcode(rset.getString("PKUP_POINT_CODE"));
						sVB.setC_remarks(rset.getString("REMARKS"));
						return sVB;
					}
								
				});
			  }
			  else if(bankid.equalsIgnoreCase("SCB")){
				  list = this.mytemplate.query(query, new RowMapper() {
				  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				  InvalidVB sVB = new InvalidVB();
				  sVB.setS_customername(rset.getString("CUSTOMER_NAME"));
				  sVB.setS_enttype(rset.getString("ENT_TYPE"));
				  sVB.setS_crdr(rset.getString("CR_DR"));
				  sVB.setS_entamount(rset.getString("ENT_AMOUNT"));
				  sVB.setS_creditdebitdate(rset.getString("CREDIT_DEBIT_DT"));
				  sVB.setS_product(rset.getString("PRODUCT"));
				  sVB.setS_pickuploc(rset.getString("PICKUPLOC"));
				  sVB.setS_pickuppoint(rset.getString("PICKUP_POINT"));
				  sVB.setS_pickupdt(rset.getString("PICKUPDT"));
				  sVB.setS_depositno(rset.getString("DEPOSIT_NO"));
				  sVB.setS_depdate(rset.getString("DEPOSIT_DATE"));
				  sVB.setS_depamount(rset.getString("DEP_AMOUNT"));
				  sVB.setS_payorderno(rset.getString("PAY_ORDER_NO"));
				  sVB.setS_chequeno(rset.getString("CHEQUE_NO"));
				  sVB.setS_chequeamt(rset.getString("CHQ_AMOUNT"));
				  sVB.setS_chequedate(rset.getString("CHEQUE_DT"));
				  sVB.setS_draweebank(rset.getString("DRAWEE_BANK"));
				  sVB.setS_drawer(rset.getString("DRAWER"));
				  sVB.setS_drawnon(rset.getString("DRAWNON"));
				  sVB.setS_reason(rset.getString("REASON"));
				  sVB.setS_enrichmentno(rset.getString("ENRICHMENT_NO"));
				  sVB.setS_enrichmentremarks(rset.getString("ENRICHMENT_REMARK"));
				  return sVB;
				  }
				  });
						
			  }
			  
			  else if(bankid.equalsIgnoreCase("HDB")){
				  list = this.mytemplate.query(query, new RowMapper() {
					  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				
				      InvalidVB sVB = new InvalidVB();
					  sVB.setH_month(rset.getString("MONTH"));
					  sVB.setH_rowtype(rset.getString("ROW_TYPE"));
					  sVB.setH_entryid(rset.getString("ENTRY_ID"));
					  sVB.setH_typeofen(rset.getString("TYPE_OF_EN"));
					  sVB.setH_drcr(rset.getString("DR_CR"));
					  sVB.setH_entryamt(rset.getString("ENTRY_AMT"));
					  sVB.setH_valdt(rset.getString("VAL_DT"));
					  sVB.setH_postdt(rset.getString("POST_DT"));
					  sVB.setH_prodcode(rset.getString("PROD_CODE"));
					  sVB.setH_pkuploc(rset.getString("PKUP_LOC"));
					  sVB.setH_pkuppt(rset.getString("PKUP_PT"));
					  sVB.setH_pkupdt(rset.getString("PKUP_DT"));
					  sVB.setH_depositslipno(rset.getString("DEPOSIT_SLIP_NO"));
					  sVB.setH_depositdate(rset.getString("DEPOSIT_DATE"));
					  sVB.setH_deptamt(rset.getString("DEPT_AMT"));
					  sVB.setH_noofinst(rset.getString("NO_OF_INST"));
					  sVB.setH_deptrmk(rset.getString("DEPT_RMK"));
					  sVB.setH_instrumentno(rset.getString("INSTRUMENT_NO"));
					  sVB.setH_draweebk(rset.getString("DRAWEE_BK"));
					  sVB.setH_clloc(rset.getString("CL_LOC"));
					  sVB.setH_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
					  sVB.setH_instdt(rset.getString("INST_DT"));
					  sVB.setH_drawernam(rset.getString("DRAWER_NAM"));
					  sVB.setH_dealcode(rset.getString("DEAL_CODE"));
					  sVB.setH_dealname(rset.getString("DEAL_NAME"));
					  sVB.setH_drawer(rset.getString("DRAWER"));
					  sVB.setH_policyno(rset.getString("POLICY_NO"));
					  sVB.setH_returnrsn(rset.getString("RETURN_RSN"));
					 
					  return sVB;
						  }
					});
				   }
		  		
			  else if(bankid.equalsIgnoreCase("KOT")){
				  list = this.mytemplate.query(query, new RowMapper() {
					  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				      InvalidVB sVB = new InvalidVB();
				      sVB.setK_clcode1(rset.getString("CLCODE1"));
				      sVB.setK_customerno(rset.getString("CUSTOMER_NO"));
				      sVB.setK_clientname(rset.getString("CLIENT_NAME"));
				      sVB.setK_crdr1(rset.getString("CR_DR1"));
				      sVB.setK_valuedate1(rset.getString("VALUE_DATE1"));
				      sVB.setK_locationname(rset.getString("LOCATION_NAME"));
				      sVB.setK_pickuploccode(rset.getString("PICKUP_LOC_CODE"));
				      sVB.setK_pickupdate(rset.getString("PICKUP_DATE"));
				      sVB.setK_pickuploc(rset.getString("PICK_UP_LOC"));
				      sVB.setK_depositdate(rset.getString("DEPOSIT_DATE"));
				      sVB.setK_depositslipno(rset.getString("DEPOSIT_SLIP_NO"));
				      sVB.setK_totalnstrument(rset.getString("TOTAL_INSTRUMENT"));
				      sVB.setK_totalamount(rset.getString("TOTAL_AMOUNT"));
				      sVB.setK_instrumentno(rset.getString("INSTRUMENT_NO"));
				      sVB.setK_instrumentamt(rset.getString("INSTRUMENT_AMT"));
				      sVB.setK_instrumentdate(rset.getString("INSTRUMENT_DATE"));
				      sVB.setK_draweebank(rset.getString("DRAWEE_BANK"));
				      sVB.setK_draweebranch(rset.getString("DRAWEE_BRANCH"));
				      sVB.setK_debitamnt1(rset.getString("DEBIT_AMNT1"));
				      sVB.setK_creditamnt1(rset.getString("CREDIT_AMNT1"));
				      sVB.setK_netamnt1(rset.getString("NETAMNT1"));
				      sVB.setK_debtoracctno1(rset.getString("DEBTOR_ACCT_NO1"));
				      sVB.setK_drawername(rset.getString("DRAWER_NAME"));
				      sVB.setK_remarks(rset.getString("REMARKS"));
				      sVB.setK_productcode(rset.getString("PRODUCT_CODE"));
				      sVB.setK_reason(rset.getString("REASON"));
				      sVB.setK_pickuppointcode(rset.getString("PICKUP_POINT_CODE"));
				      sVB.setK_pickuppointdesp(rset.getString("PICKUP_POINT_DESP"));
				      sVB.setK_dealerref(rset.getString("DEALER_REF"));
				      sVB.setK_clientarrangement(rset.getString("CLIENT_ARRANGEMENT"));
				      sVB.setK_enrichmentvalue1(rset.getString("ENRICHMENT_VALUE1"));
				      sVB.setK_enrichmentvalue2(rset.getString("ENRICHMENT_VALUE2"));
				      sVB.setK_enrichmentvalue3(rset.getString("ENRICHMENT_VALUE3"));
				      sVB.setK_enrichmentvalue4(rset.getString("ENRICHMENT_VALUE4"));
				      sVB.setK_enrichmentvalue5(rset.getString("ENRICHMENT_VALUE5"));
				      sVB.setK_enrichmentvalue6(rset.getString("ENRICHMENT_VALUE6"));
				      sVB.setK_enrichmentvalue7(rset.getString("ENRICHMENT_VALUE7"));
				      sVB.setK_enrichmentvalue8(rset.getString("ENRICHMENT_VALUE8"));
				      sVB.setK_enrichmentvalue9(rset.getString("ENRICHMENT_VALUE9"));
				      sVB.setK_enrichmentvalue10(rset.getString("ENRICHMENT_VALUE10"));
				      sVB.setK_enrichmentvalue11(rset.getString("ENRICHMENT_VALUE11"));
				      sVB.setK_enrichmentvalue12(rset.getString("ENRICHMENT_VALUE12"));
				      sVB.setK_enrichmentvalue13(rset.getString("ENRICHMENT_VALUE13"));
				      sVB.setK_enrichmentvalue14(rset.getString("ENRICHMENT_VALUE14"));
				      sVB.setK_enrichmentvalue15(rset.getString("ENRICHMENT_VALUE15"));
				      sVB.setK_handoffevent(rset.getString("HANDOFF_EVENT"));
				      sVB.setK_transactionjournalnmbr(rset.getString("TRANSACTION_JOURNAL_NMBR"));
				      sVB.setK_depositremarks(rset.getString("DEPOSIT_REMARKS"));
				      sVB.setK_entrydate(rset.getString("ENTRY_DATE"));
				      sVB.setK_validatestatus(rset.getString("VALIDATE_STATUS"));
				      sVB.setK_active(rset.getString("ACTIVE"));
				      sVB.setK_batchid(rset.getString("BATCHID"));
				      sVB.setK_correctchqamt(rset.getString("CORRECT_CHQ_AMT"));
				      sVB.setK_returnreason(rset.getString("RETURN_REASON"));
				      sVB.setK_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
				      sVB.setK_noofinstruments(rset.getString("NO_OF_INSTRUMENTS"));
				      sVB.setK_depositamount(rset.getString("DEPOSIT_AMOUNT"));
				      sVB.setK_pickuppoint(rset.getString("PICKUP_POINT"));
				      sVB.setK_pickuplocation(rset.getString("PICKUP_LOCATION"));
				      sVB.setK_debitcredit(rset.getString("DEBIT_CREDIT"));
				      sVB.setK_txnjournalno(rset.getString("TXN_JOURNAL_NO"));
				      sVB.setK_month(rset.getString("MONTH"));
				      sVB.setK_postdt(rset.getString("POST_DT"));
				      sVB.setK_drcr(rset.getString("DR_CR"));
				      sVB.setK_entryid(rset.getString("ENTRY_ID"));
				      return sVB;
					 
						  }
					});
				   }
		  
		  
			  else if(bankid.equalsIgnoreCase("AXB")){
				  list = this.mytemplate.query(query, new RowMapper() {
				  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				  InvalidVB sVB = new InvalidVB();
				  sVB.setA_type(rset.getString("TYPE"));
				  sVB.setA_cuscode(rset.getString("CUS_CODE"));
				  sVB.setA_locationname(rset.getString("LOCATION_NAME"));
				  sVB.setA_depositdate(rset.getString("DEPOSIT_DATE"));
				  sVB.setA_crdate(rset.getString("CR_DATE"));
				  sVB.setA_rtndate(rset.getString("RTN_DATE"));
				  sVB.setA_slipno(rset.getString("SLIP_NO"));
				  sVB.setA_nofins(rset.getString("NOF_INS"));
				  sVB.setA_slipamount(rset.getString("SLIP_AMOUNT"));
				  sVB.setA_instno(rset.getString("INST_NO"));
				  sVB.setA_instdate(rset.getString("INST_DATE"));
				  sVB.setA_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
				  sVB.setA_iadditionalinfo1(rset.getString("I_ADDITIONAL_INFO1"));
				  sVB.setA_iadditionalinfo2(rset.getString("I_ADDITIONAL_INFO2"));
				  sVB.setA_rtnamt(rset.getString("RTN_AMT"));
				  sVB.setA_returnreason(rset.getString("RETURN_REASON"));
				  sVB.setA_drnbk(rset.getString("DRN_BK"));
				  sVB.setA_drwnbrnchname(rset.getString("DRWN_BRNCH_NAME"));
				  return sVB;
				  }
				  });
						
			  }else if(bankid.equalsIgnoreCase("HSB")){
				  list = this.mytemplate.query(query, new RowMapper() {
					  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				      InvalidVB sVB = new InvalidVB();
				      
				      //D.DATE_OF_ENTRY, D.CORRECT_CHQ_NO, D.CORRECT_CHQ_AMT, D.RSA_POLICY_NO, 
				      
					  sVB.setH_month(rset.getString("MONTH"));
					  sVB.setH_rowtype(rset.getString("RECORD_IDENTIFIER"));
					  sVB.setH_entryid(rset.getString("TXN_JOURNAL_NO"));
					  sVB.setH_typeofen(rset.getString("TYPE_OF_ENTRY"));
					  sVB.setH_drcr(rset.getString("DEBIT_CREDIT"));
					  sVB.setH_entryamt(rset.getString("ENTRY_AMOUNT"));
					  /*sVB.setH_valdt(rset.getString("VAL_DT"));//
					  sVB.setH_dealcode(rset.getString("DEAL_CODE"));//
					  sVB.setH_dealname(rset.getString("DEAL_NAME"));//
					  sVB.setH_drawer(rset.getString("DRAWER"));//
*/					  sVB.setH_postdt(rset.getString("POST_DATE"));
					  sVB.setH_prodcode(rset.getString("PRODUCT_CODE"));
					  sVB.setH_pkuploc(rset.getString("PICKUP_LOCATION"));
					  sVB.setH_pkuppt(rset.getString("PICKUP_POINT"));
					  sVB.setH_pkupdt(rset.getString("PICKUP_DATE"));
					  sVB.setH_depositslipno(rset.getString("DEPOSIT_SLIP_NO"));
					  sVB.setH_depositdate(rset.getString("DEPOSIT_DATE"));
					  sVB.setH_deptamt(rset.getString("DEPOSIT_AMOUNT"));
					  sVB.setH_noofinst(rset.getString("NO_OF_INSTRUMENTS"));
					  sVB.setH_deptrmk(rset.getString("DEPOSIT_REMARKS"));
					  sVB.setH_instrumentno(rset.getString("INSTRUMENT_NO"));
					  sVB.setH_draweebk(rset.getString("DRAWEE_BANK"));
					  sVB.setH_clloc(rset.getString("CLEARING_LOC"));
					  sVB.setH_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
					  sVB.setH_instdt(rset.getString("INSTRUMENT_DATE"));
					  sVB.setH_drawernam(rset.getString("DRAWER_NAME"));
					  sVB.setH_policyno(rset.getString("MI_POLICY_NO"));
					  sVB.setH_returnrsn(rset.getString("RETURN_REASON"));
					  return sVB;
						  }
					});
			   }
		  
		return list;
	}
	public List getReceiptDuplicates(String transid) throws CommonBaseException {
		
		  List list=null;
		  
		  String  query="SELECT CHEQUE_NO, CHEQUE_DATE, RECEIPT_NO, RECEIPT_DATE, AMOUNT, BANK_NAME_AND_LOC, REASON, RECEIPT_ENTRY_ID, BANK_CODE, RECEIPT_AG_NAME, RECEIPT_AG_CODE,PRODUCT_CODE, RECEIPT_BRANCH_CODE, PARTICULARS, TRANS_SOURCE, CURRENT_BALANCE, DELAY_IN_DAYS,EXCHANGE_CURR_RATE, WTFOFF_1, WTFOFF_2, WTFOFF_3, DUE_DATE, PAYMENT_TYPE, CREDIT_CARD_NO,CREDIT_CARD_TYPE, CREDIT_CARD_BANK, CREDIT_CARD_EXPIRY, TRANSACTION_REFERENCE, CHANNEL,SUBCHANNEL, STATUS FROM DUP_RECEIPT_MASTER WHERE TRANSACTION_ID='"+transid+"'AND STATUS IN('D','E') ORDER BY 3 DESC, CHEQUE_NO";
		  LogManager.push("Query executed:"+query);
			
		  list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					InvalidVB sVB = new InvalidVB();
					
					sVB.setR_chequeno(rset.getString("CHEQUE_NO"));
					sVB.setR_chequedate(rset.getString("CHEQUE_DATE"));
					sVB.setR_receiptno(rset.getString("RECEIPT_NO"));
					sVB.setR_receiptdate(rset.getString("RECEIPT_DATE"));
					sVB.setR_amount(rset.getString("AMOUNT"));
					sVB.setR_banknameandloc(rset.getString("BANK_NAME_AND_LOC"));
					sVB.setR_reason(rset.getString("REASON"));
					sVB.setR_receiptentryid(rset.getString("RECEIPT_ENTRY_ID"));
					sVB.setR_bankcode(rset.getString("BANK_CODE"));
					sVB.setR_receiptagname(rset.getString("RECEIPT_AG_NAME"));
					sVB.setR_receiptagcode(rset.getString("RECEIPT_AG_CODE"));
					sVB.setR_productcode(rset.getString("PRODUCT_CODE"));
					sVB.setR_receiptbranchcode(rset.getString("RECEIPT_BRANCH_CODE"));
					sVB.setR_particulars(rset.getString("PARTICULARS"));
					sVB.setR_transsource(rset.getString("TRANS_SOURCE"));
					sVB.setR_currentbalance(rset.getString("CURRENT_BALANCE"));
					sVB.setR_delayindays(rset.getString("DELAY_IN_DAYS"));
					sVB.setR_exchangecurrrate(rset.getString("EXCHANGE_CURR_RATE"));
					sVB.setR_wtfoff1(rset.getString("WTFOFF_1"));
					sVB.setR_wtfoff2(rset.getString("WTFOFF_2"));
					sVB.setR_wtfoff3(rset.getString("WTFOFF_3"));
					sVB.setR_duedate(rset.getString("DUE_DATE"));
					sVB.setR_paymenttype(rset.getString("PAYMENT_TYPE"));
					sVB.setR_creditcardno(rset.getString("CREDIT_CARD_NO"));
					sVB.setR_creditcardtype(rset.getString("CREDIT_CARD_TYPE"));
					sVB.setR_creditcardbank(rset.getString("CREDIT_CARD_BANK"));
					sVB.setR_creditcardexpiry(rset.getString("CREDIT_CARD_EXPIRY"));
					sVB.setR_transactionreference(rset.getString("TRANSACTION_REFERENCE"));
					sVB.setR_channel(rset.getString("CHANNEL"));
					sVB.setR_subchannel(rset.getString("SUBCHANNEL"));
					return sVB;
				}
							
			});
		return list;
	}
	public List getReceiptInvalids(String transid) throws CommonBaseException {
		List list=null;
	
		String  query="SELECT CHEQUE_NO, CHEQUE_DATE, RECEIPT_NO, RECEIPT_DATE, AMOUNT, BANK_NAME_AND_LOC, REASON, RECEIPT_ENTRY_ID, BANK_CODE, RECEIPT_AG_NAME, RECEIPT_AG_CODE,PRODUCT_CODE, RECEIPT_BRANCH_CODE, PARTICULARS, TRANS_SOURCE, CURRENT_BALANCE, DELAY_IN_DAYS,EXCHANGE_CURR_RATE, WTFOFF_1, WTFOFF_2, WTFOFF_3, DUE_DATE, PAYMENT_TYPE, CREDIT_CARD_NO,CREDIT_CARD_TYPE, CREDIT_CARD_BANK, CREDIT_CARD_EXPIRY, TRANSACTION_REFERENCE, CHANNEL,SUBCHANNEL, STATUS FROM DUP_RECEIPT_MASTER WHERE TRANSACTION_ID='"+transid+"' AND STATUS NOT IN('D','E','P') ORDER BY 4 DESC,CHEQUE_NO";
		//String query="";  
		LogManager.push("Query executed:"+query);
			
		  list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					InvalidVB sVB = new InvalidVB();
					
					//sVB.setChequeNo(rset.getString("CHEQUE_NO"));
					//sVB.setChequeAmount(rset.getString("AMOUNT"));
					//sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
					//sVB.setReasons(rset.getString("STATUS"));
					
					sVB.setR_chequeno(rset.getString("CHEQUE_NO")==null?"":rset.getString("CHEQUE_NO"));
					sVB.setR_chequedate(rset.getString("CHEQUE_DATE")==null?"":rset.getString("CHEQUE_DATE"));
					sVB.setR_receiptno(rset.getString("RECEIPT_NO")==null?"":rset.getString("RECEIPT_NO"));
					sVB.setR_receiptdate(rset.getString("RECEIPT_DATE")==null?"":rset.getString("RECEIPT_DATE"));
					sVB.setR_amount(rset.getString("AMOUNT")==null?"":rset.getString("AMOUNT"));
					sVB.setR_banknameandloc(rset.getString("BANK_NAME_AND_LOC")==null?"":rset.getString("BANK_NAME_AND_LOC"));
					sVB.setR_reason(rset.getString("REASON")==null?"":rset.getString("REASON"));
					sVB.setR_receiptentryid(rset.getString("RECEIPT_ENTRY_ID")==null?"":rset.getString("RECEIPT_ENTRY_ID"));
					sVB.setR_bankcode(rset.getString("BANK_CODE")==null?"":rset.getString("BANK_CODE"));
					sVB.setR_receiptagname(rset.getString("RECEIPT_AG_NAME")==null?"":rset.getString("RECEIPT_AG_NAME"));
					sVB.setR_receiptagcode(rset.getString("RECEIPT_AG_CODE")==null?"":rset.getString("RECEIPT_AG_CODE"));
					sVB.setR_productcode(rset.getString("PRODUCT_CODE")==null?"":rset.getString("PRODUCT_CODE"));
					sVB.setR_receiptbranchcode(rset.getString("RECEIPT_BRANCH_CODE")==null?"":rset.getString("RECEIPT_BRANCH_CODE"));
					sVB.setR_particulars(rset.getString("PARTICULARS")==null?"":rset.getString("PARTICULARS"));
					sVB.setR_transsource(rset.getString("TRANS_SOURCE")==null?"":rset.getString("TRANS_SOURCE"));
					sVB.setR_currentbalance(rset.getString("CURRENT_BALANCE")==null?"":rset.getString("CURRENT_BALANCE"));
					sVB.setR_delayindays(rset.getString("DELAY_IN_DAYS")==null?"":rset.getString("DELAY_IN_DAYS"));
					sVB.setR_exchangecurrrate(rset.getString("EXCHANGE_CURR_RATE")==null?"":rset.getString("EXCHANGE_CURR_RATE"));
					sVB.setR_wtfoff1(rset.getString("WTFOFF_1")==null?"":rset.getString("WTFOFF_1"));
					sVB.setR_wtfoff2(rset.getString("WTFOFF_2")==null?"":rset.getString("WTFOFF_2"));
					sVB.setR_wtfoff3(rset.getString("WTFOFF_3")==null?"":rset.getString("WTFOFF_3"));
					sVB.setR_duedate(rset.getString("DUE_DATE")==null?"":rset.getString("DUE_DATE"));
					sVB.setR_paymenttype(rset.getString("PAYMENT_TYPE")==null?"":rset.getString("PAYMENT_TYPE"));
					sVB.setR_creditcardno(rset.getString("CREDIT_CARD_NO")==null?"":rset.getString("CREDIT_CARD_NO"));
					sVB.setR_creditcardtype(rset.getString("CREDIT_CARD_TYPE")==null?"":rset.getString("CREDIT_CARD_TYPE"));
					sVB.setR_creditcardbank(rset.getString("CREDIT_CARD_BANK")==null?"":rset.getString("CREDIT_CARD_BANK"));
					sVB.setR_creditcardexpiry(rset.getString("CREDIT_CARD_EXPIRY")==null?"":rset.getString("CREDIT_CARD_EXPIRY"));
					sVB.setR_transactionreference(rset.getString("TRANSACTION_REFERENCE")==null?"":rset.getString("TRANSACTION_REFERENCE"));
					sVB.setR_channel(rset.getString("CHANNEL")==null?"":rset.getString("CHANNEL"));
					sVB.setR_subchannel(rset.getString("SUBCHANNEL")==null?"":rset.getString("SUBCHANNEL"));
					String reqVal="";
					String val[]=rset.getString("STATUS").split(",");
				        
					LogManager.push("val[0]"+val[0]);
					if(val.length>1)
					{
						reqVal=val[1];
						
						
					}
					else
					{
						reqVal=val[0];
					}
					//LogManager.push(reqVal+"<--reqVAl");
					String value[]= new String [2];
					StringTokenizer st = new StringTokenizer(reqVal,"*");
				   int i=0;
					while (st.hasMoreTokens()) {
				    	 value[i]=st.nextToken();
				         i++;
				     }
					//LogManager.push("value[0]"+value[0]);
					//LogManager.push("value[1]"+value[1]);
					String query1="SELECT EXCEL_HEADER_NAME FROM TMAS_VALIDATION WHERE VALID_ID="+value[0];
					LogManager.push("RECEIPT POPUP:"+query1);
					
					String name=Runner.singleSelection(query1);
					String reason="";
					if(value[1].equalsIgnoreCase("D"))
					{
						reason = "Invalid Date Format";
					}
					else if (value[1].equalsIgnoreCase("M"))
					{
						reason = "Not Exists";
					}
					else if (value[1].equalsIgnoreCase("R"))
					{
						reason = "Not Exists in Master";
					}
					else if (value[1].equalsIgnoreCase("NU"))
					{
						reason = "Invalid Number";
					}
					else if (value[1].equalsIgnoreCase("PAY"))
					{
						reason = "Invalid ";
					}
					else if (value[1].equalsIgnoreCase("TRA"))
					{
						reason = "Invalid ";
					}
					else
					{
						reason = " ";
					}
					
					sVB.setR_validatestatus(name.toUpperCase()+" - "+reason );
					
					return sVB;
				}
							
			});
		return list;
	}
	public List getBankInvalids(String transid, String bankid) throws CommonBaseException {
		
		  List list=null;
		  List blist=getBankQueryData(bankid);
			final Map tempMap = (Map) blist.get(0);
			final String tableName=((String)tempMap.get("TABLE_NAME"));
			final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
			final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
		    final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
		  String  query="SELECT * FROM DUP_"+tableName+" WHERE TRANSACTION_ID="+transid +" AND STATUS NOT IN('D','E','P') ORDER BY "+chequeNo;
		  LogManager.push("BANK INVALIDS Query executed:===============>"+query);
		  if(bankid.equalsIgnoreCase("CIT")){
		  list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					InvalidVB sVB = new InvalidVB();
					/*sVB.setChequeNo(rset.getString(chequeNo));
					sVB.setChequeAmount(rset.getString(chequeAmt));
					sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
					sVB.setCheckStatus(chequeStatus);
					sVB.setReasons(rset.getString("STATUS"));
					return sVB;*/
					
					sVB.setC_clientcode(rset.getString("CLIENT_CODE"));
					sVB.setC_depositdate(rset.getString("DEPOSIT_DATE"));
					sVB.setC_product(rset.getString("PRODUCT"));
					sVB.setC_creditdebitdate(rset.getString("CREDIT_DEBIT_DATE"));
					sVB.setC_location(rset.getString("LOCATION"));
					sVB.setC_chequeno(rset.getString("CHEQUE_NO"));
					sVB.setC_chequeamt(rset.getString("CHEQUE_AMT"));
					sVB.setC_typecrdr(rset.getString("TYPE_CRDR"));
					sVB.setC_narration(rset.getString("NARRATION"));
					sVB.setC_cbpno(rset.getString("CBP_NO"));
					sVB.setC_depslipno(rset.getString("DEPSLIPNO"));
					sVB.setC_customerref(rset.getString("CUSTOMERREF"));
					sVB.setC_depositamt(rset.getString("DEPOSIT_AMT"));
					sVB.setC_dwebankcode(rset.getString("DWE_BANK_CODE"));
					sVB.setC_checkdata(rset.getString("CHECK_DATA"));
					sVB.setC_covernoteno(rset.getString("COVERNOTENO"));
					sVB.setC_bankname(rset.getString("BANK_NAME"));
					sVB.setC_pickpointname(rset.getString("PICK_POINT_NAME"));
					sVB.setC_pkuppointcode(rset.getString("PKUP_POINT_CODE"));
					sVB.setC_remarks(rset.getString("REMARKS"));
					String reqVal="";
					String val[]=rset.getString("STATUS").split(",");
				        
					StringBuffer reasons=new StringBuffer();
				    StringBuffer names=new StringBuffer();
					String name="";
					String reason="";
					LogManager.push("val[0]"+val[0]);
					int k=0;
					if(val.length>1)
						k=1;
					for(int j=k;j<val.length;j++)
					{
					reqVal=val[j];
					LogManager.push(reqVal+"<--reqVAl");
					String value[]= new String [2];
					StringTokenizer st = new StringTokenizer(reqVal,"*");
				   int i=0;
					while (st.hasMoreTokens()) {
				    	 value[i]=st.nextToken().trim();
				         i++;
				     }
					LogManager.push("value[0]"+value[0]);
					LogManager.push("value[1]"+value[1]);
					String query1="SELECT EXCEL_HEADER_NAME FROM TMAS_VALIDATION WHERE VALID_ID="+value[0];
					LogManager.push("RECEIPT POPUP:"+query1);
					
					name=Runner.singleSelection(query1);
					
					if(value[1].equalsIgnoreCase("D"))
					{
						reason = "Invalid Date Format";
					}
					else if (value[1].equalsIgnoreCase("M"))
					{
						reason = "Not Exists";
					}
					else if (value[1].equalsIgnoreCase("R"))
					{
						reason = "Not Exists in Master";
					}
					else if (value[1].equalsIgnoreCase("NU"))
					{
						reason = "Invalid Number";
					}
					else if (value[1].equalsIgnoreCase("DEP"))
					{
						reason = "Not Exists";
					}
					else if (value[1].trim().equalsIgnoreCase("TYP"))
					{
						reason = "Invalid";
					}
					else 
					{
						reason = " ";
					}
					
					if(k==0)
						reasons.append(name+"-"+reason);
					if(j==1)
					    reasons.append(name+"-"+reason);
					if(j>1&&j<val.length)
						reasons.append(","+name+"-"+reason);
					
 					LogManager.push("name "+name+"\t"+"reason "+reason);
					}
					LogManager.push("reasons "+reasons);
					
					sVB.setC_validatestatus(reasons.toString());
					LogManager.push(name.toUpperCase()+" - "+reason );
					
					return sVB;
				}
							
			});
		  }
		  else if(bankid.equalsIgnoreCase("SCB")){
			  list = this.mytemplate.query(query, new RowMapper() {
			  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			  InvalidVB sVB = new InvalidVB();
			  sVB.setS_customername(rset.getString("CUSTOMER_NAME"));
			  sVB.setS_enttype(rset.getString("ENT_TYPE"));
			  sVB.setS_crdr(rset.getString("CR_DR"));
			  sVB.setS_entamount(rset.getString("ENT_AMOUNT"));
			  sVB.setS_creditdebitdate(rset.getString("CREDIT_DEBIT_DT"));
			  sVB.setS_product(rset.getString("PRODUCT"));
			  sVB.setS_pickuploc(rset.getString("PICKUPLOC"));
			  sVB.setS_pickuppoint(rset.getString("PICKUP_POINT"));
			  sVB.setS_pickupdt(rset.getString("PICKUPDT"));
			  sVB.setS_depositno(rset.getString("DEPOSIT_NO"));
			  sVB.setS_depdate(rset.getString("DEPOSIT_DATE"));
			  sVB.setS_depamount(rset.getString("DEP_AMOUNT"));
			  sVB.setS_payorderno(rset.getString("PAY_ORDER_NO"));
			  sVB.setS_chequeno(rset.getString("CHEQUE_NO"));
			  sVB.setS_chequeamt(rset.getString("CHQ_AMOUNT"));
			  sVB.setS_chequedate(rset.getString("CHEQUE_DT"));
			  sVB.setS_draweebank(rset.getString("DRAWEE_BANK"));
			  sVB.setS_drawer(rset.getString("DRAWER"));
			  sVB.setS_drawnon(rset.getString("DRAWNON"));
			  sVB.setS_reason(rset.getString("REASON"));
			  sVB.setS_enrichmentno(rset.getString("ENRICHMENT_NO"));
			  sVB.setS_enrichmentremarks(rset.getString("ENRICHMENT_REMARK"));
			   String reqVal="";
			   String val[]=rset.getString("STATUS").split(",");
			        
			   StringBuffer reasons=new StringBuffer();
			    StringBuffer names=new StringBuffer();
				String name="";
				String reason="";
				LogManager.push("val[0]"+val[0]);
				int k=0;
				if(val.length>1)
					k=1;
				for(int j=k;j<val.length;j++)
				{
				reqVal=val[j];
				LogManager.push(reqVal+"<--reqVAl");
				String value[]= new String [2];
				StringTokenizer st = new StringTokenizer(reqVal,"*");
			   int i=0;
				while (st.hasMoreTokens()) {
			    	 value[i]=st.nextToken().trim();
			         i++;
			     }
				LogManager.push("value[0]"+value[0]);
				LogManager.push("value[1]"+value[1]);
				String query1="SELECT EXCEL_HEADER_NAME FROM TMAS_VALIDATION WHERE VALID_ID="+value[0];
				LogManager.push("RECEIPT POPUP:"+query1);
				
				name=Runner.singleSelection(query1);
				
				if(value[1].equalsIgnoreCase("D"))
				{
					reason = "Invalid Date Format";
				}
				else if (value[1].equalsIgnoreCase("M"))
				{
					reason = "Not Exists";
				}
				else if (value[1].equalsIgnoreCase("R"))
				{
					reason = "Not Exists in Master";
				}
				else if (value[1].equalsIgnoreCase("NU"))
				{
					reason = "Invalid Number";
				}
				else if (value[1].equalsIgnoreCase("DEP"))
				{
					reason = "Not Exists";
				}
				else if (value[1].trim().equalsIgnoreCase("TYP"))
				{
					reason = "Invalid";
				}
				else 
				{
					reason = " ";
				}
				
				if(k==0)
					reasons.append(name+"-"+reason);
				if(j==1)
				    reasons.append(name+"-"+reason);
				if(j>1&&j<val.length)
					reasons.append(","+name+"-"+reason);
				
				LogManager.push("name "+name+"\t"+"reason "+reason);
				}
				LogManager.push("reasons "+reasons);
				
				sVB.setS_validatestatus(reasons.toString());
				LogManager.push(name.toUpperCase()+" - "+reason );
				
				return sVB;
			  }
			  });
		  } else if(bankid.equalsIgnoreCase("HDB")){
			  list = this.mytemplate.query(query, new RowMapper() {
				  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			      InvalidVB sVB = new InvalidVB();
				  sVB.setH_month(rset.getString("MONTH"));
				  sVB.setH_rowtype(rset.getString("ROW_TYPE"));
				  sVB.setH_entryid(rset.getString("ENTRY_ID"));
				  sVB.setH_typeofen(rset.getString("TYPE_OF_EN"));
				  sVB.setH_drcr(rset.getString("DR_CR"));
				  sVB.setH_entryamt(rset.getString("ENTRY_AMT"));
				  sVB.setH_valdt(rset.getString("VAL_DT"));
				  sVB.setH_postdt(rset.getString("POST_DT"));
				  sVB.setH_prodcode(rset.getString("PROD_CODE"));
				  sVB.setH_pkuploc(rset.getString("PKUP_LOC"));
				  sVB.setH_pkuppt(rset.getString("PKUP_PT"));
				  sVB.setH_pkupdt(rset.getString("PKUP_DT"));
				  sVB.setH_depositslipno(rset.getString("DEPOSIT_SLIP_NO"));
				  sVB.setH_depositdate(rset.getString("DEPOSIT_DATE"));
				  sVB.setH_deptamt(rset.getString("DEPT_AMT"));
				  sVB.setH_noofinst(rset.getString("NO_OF_INST"));
				  sVB.setH_deptrmk(rset.getString("DEPT_RMK"));
				  sVB.setH_instrumentno(rset.getString("INSTRUMENT_NO"));
				  sVB.setH_draweebk(rset.getString("DRAWEE_BK"));
				  sVB.setH_clloc(rset.getString("CL_LOC"));
				  sVB.setH_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
				  sVB.setH_instdt(rset.getString("INST_DT"));
				  sVB.setH_drawernam(rset.getString("DRAWER_NAM"));
				  sVB.setH_dealcode(rset.getString("DEAL_CODE"));
				  sVB.setH_dealname(rset.getString("DEAL_NAME"));
				  sVB.setH_drawer(rset.getString("DRAWER"));
				  sVB.setH_policyno(rset.getString("POLICY_NO"));
				  sVB.setH_returnrsn(rset.getString("RETURN_RSN"));
				  String reqVal="";
					String val[]=rset.getString("STATUS").split(",");
				   
					StringBuffer reasons=new StringBuffer();
				    StringBuffer names=new StringBuffer();
					String name="";
					String reason="";
					LogManager.push("val[0]"+val[0]);
					int k=0;
					if(val.length>1)
						k=1;
					for(int j=k;j<val.length;j++)
					{
					reqVal=val[j];
					LogManager.push(reqVal+"<--reqVAl");
					String value[]= new String [2];
					StringTokenizer st = new StringTokenizer(reqVal,"*");
				   int i=0;
					while (st.hasMoreTokens()) {
				    	 value[i]=st.nextToken().trim();
				         i++;
				     }
					LogManager.push("value[0]"+value[0]);
					LogManager.push("value[1]"+value[1]);
					String query1="SELECT EXCEL_HEADER_NAME FROM TMAS_VALIDATION WHERE VALID_ID="+value[0];
					LogManager.push("RECEIPT POPUP:"+query1);
					
					name=Runner.singleSelection(query1);
					
					if(value[1].equalsIgnoreCase("D"))
					{
						reason = "Invalid Date Format";
					}
					else if (value[1].equalsIgnoreCase("M"))
					{
						reason = "Not Exists";
					}
					else if (value[1].equalsIgnoreCase("R"))
					{
						reason = "Not Exists in Master";
					}
					else if (value[1].equalsIgnoreCase("NU"))
					{
						reason = "Invalid Number";
					}
					else if (value[1].equalsIgnoreCase("DEP"))
					{
						reason = "Not Exists";
					}
					else if (value[1].trim().equalsIgnoreCase("TYP"))
					{
						reason = "Invalid";
					}
					else 
					{
						reason = " ";
					}
					if(k==0)
						reasons.append(name+"-"+reason);
					if(j==1)
					    reasons.append(name+"-"+reason);
					if(j>1&&j<val.length)
						reasons.append(","+name+"-"+reason);
					
 					LogManager.push("name "+name+"\t"+"reason "+reason);
					}
					LogManager.push("reasons "+reasons);
					
					sVB.setH_validatestatus(reasons.toString());
					LogManager.push(name.toUpperCase()+" - "+reason );
				  return sVB;
					  }
				});
			   }else if(bankid.equalsIgnoreCase("KOT")){
					  list = this.mytemplate.query(query, new RowMapper() {
						  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					      InvalidVB sVB = new InvalidVB();
					      sVB.setK_clcode1(rset.getString("CLCODE1"));
					      sVB.setK_customerno(rset.getString("CUSTOMER_NO"));
					      sVB.setK_clientname(rset.getString("CLIENT_NAME"));
					      sVB.setK_crdr1(rset.getString("CR_DR1"));
					      sVB.setK_valuedate1(rset.getString("VALUE_DATE1"));
					      sVB.setK_locationname(rset.getString("LOCATION_NAME"));
					      sVB.setK_pickuploccode(rset.getString("PICKUP_LOC_CODE"));
					      sVB.setK_pickupdate(rset.getString("PICKUP_DATE"));
					      sVB.setK_pickuploc(rset.getString("PICK_UP_LOC"));
					      sVB.setK_depositdate(rset.getString("DEPOSIT_DATE"));
					      sVB.setK_depositslipno(rset.getString("DEPOSIT_SLIP_NO"));
					      sVB.setK_totalnstrument(rset.getString("TOTAL_INSTRUMENT"));
					      sVB.setK_totalamount(rset.getString("TOTAL_AMOUNT"));
					      sVB.setK_instrumentno(rset.getString("INSTRUMENT_NO"));
					      sVB.setK_instrumentamt(rset.getString("INSTRUMENT_AMT"));
					      sVB.setK_instrumentdate(rset.getString("INSTRUMENT_DATE"));
					      sVB.setK_draweebank(rset.getString("DRAWEE_BANK"));
					      sVB.setK_draweebranch(rset.getString("DRAWEE_BRANCH"));
					      sVB.setK_debitamnt1(rset.getString("DEBIT_AMNT1"));
					      sVB.setK_creditamnt1(rset.getString("CREDIT_AMNT1"));
					      sVB.setK_netamnt1(rset.getString("NETAMNT1"));
					      sVB.setK_debtoracctno1(rset.getString("DEBTOR_ACCT_NO1"));
					      sVB.setK_drawername(rset.getString("DRAWER_NAME"));
					      sVB.setK_remarks(rset.getString("REMARKS"));
					      sVB.setK_productcode(rset.getString("PRODUCT_CODE"));
					      sVB.setK_reason(rset.getString("REASON"));
					      sVB.setK_pickuppointcode(rset.getString("PICKUP_POINT_CODE"));
					      sVB.setK_pickuppointdesp(rset.getString("PICKUP_POINT_DESP"));
					      sVB.setK_dealerref(rset.getString("DEALER_REF"));
					      sVB.setK_clientarrangement(rset.getString("CLIENT_ARRANGEMENT"));
					      sVB.setK_enrichmentvalue1(rset.getString("ENRICHMENT_VALUE1"));
					      sVB.setK_enrichmentvalue2(rset.getString("ENRICHMENT_VALUE2"));
					      sVB.setK_enrichmentvalue3(rset.getString("ENRICHMENT_VALUE3"));
					      sVB.setK_enrichmentvalue4(rset.getString("ENRICHMENT_VALUE4"));
					      sVB.setK_enrichmentvalue5(rset.getString("ENRICHMENT_VALUE5"));
					      sVB.setK_enrichmentvalue6(rset.getString("ENRICHMENT_VALUE6"));
					      sVB.setK_enrichmentvalue7(rset.getString("ENRICHMENT_VALUE7"));
					      sVB.setK_enrichmentvalue8(rset.getString("ENRICHMENT_VALUE8"));
					      sVB.setK_enrichmentvalue9(rset.getString("ENRICHMENT_VALUE9"));
					      sVB.setK_enrichmentvalue10(rset.getString("ENRICHMENT_VALUE10"));
					      sVB.setK_enrichmentvalue11(rset.getString("ENRICHMENT_VALUE11"));
					      sVB.setK_enrichmentvalue12(rset.getString("ENRICHMENT_VALUE12"));
					      sVB.setK_enrichmentvalue13(rset.getString("ENRICHMENT_VALUE13"));
					      sVB.setK_enrichmentvalue14(rset.getString("ENRICHMENT_VALUE14"));
					      sVB.setK_enrichmentvalue15(rset.getString("ENRICHMENT_VALUE15"));
					      sVB.setK_handoffevent(rset.getString("HANDOFF_EVENT"));
					      sVB.setK_transactionjournalnmbr(rset.getString("TRANSACTION_JOURNAL_NMBR"));
					      sVB.setK_depositremarks(rset.getString("DEPOSIT_REMARKS"));
					      sVB.setK_entrydate(rset.getString("ENTRY_DATE"));
					      sVB.setK_validatestatus(rset.getString("VALIDATE_STATUS"));
					      sVB.setK_active(rset.getString("ACTIVE"));
					      sVB.setK_batchid(rset.getString("BATCHID"));
					      sVB.setK_correctchqamt(rset.getString("CORRECT_CHQ_AMT"));
					      sVB.setK_returnreason(rset.getString("RETURN_REASON"));
					      sVB.setK_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
					      sVB.setK_noofinstruments(rset.getString("NO_OF_INSTRUMENTS"));
					      sVB.setK_depositamount(rset.getString("DEPOSIT_AMOUNT"));
					      sVB.setK_pickuppoint(rset.getString("PICKUP_POINT"));
					      sVB.setK_pickuplocation(rset.getString("PICKUP_LOCATION"));
					      sVB.setK_debitcredit(rset.getString("DEBIT_CREDIT"));
					      sVB.setK_txnjournalno(rset.getString("TXN_JOURNAL_NO"));
					      sVB.setK_month(rset.getString("MONTH"));
					      sVB.setK_postdt(rset.getString("POST_DT"));
					      sVB.setK_drcr(rset.getString("DR_CR"));
					      sVB.setK_entryid(rset.getString("ENTRY_ID"));
						  
						  String reqVal="";
							String val[]=rset.getString("STATUS").split(",");
						        
							StringBuffer reasons=new StringBuffer();
						    
							String name="";
							String reason="";
							LogManager.push("val[0]"+val[0]);
							int k=0;
							if(val.length>1)
							k=1;
							for(int j=k;j<val.length;j++)
							{
							reqVal=val[j];
							LogManager.push(reqVal+"<--reqVAl");
							String value[]= new String [2];
							StringTokenizer st = new StringTokenizer(reqVal,"*");
						   int i=0;
							while (st.hasMoreTokens()) {
						    	 value[i]=st.nextToken().trim();
						         i++;
						     }
							LogManager.push("value[0]"+value[0]);
							LogManager.push("value[1]"+value[1]);
							String query1="SELECT EXCEL_HEADER_NAME FROM TMAS_VALIDATION WHERE VALID_ID="+value[0];
							LogManager.push("RECEIPT POPUP:"+query1);
							
							name=Runner.singleSelection(query1);
							
							if(value[1].equalsIgnoreCase("D"))
							{
								reason = "Invalid Date Format";
							}
							else if (value[1].equalsIgnoreCase("M"))
							{
								reason = "Not Exists";
							}
							else if (value[1].equalsIgnoreCase("R"))
							{
								reason = "Not Exists in Master";
							}
							else if (value[1].equalsIgnoreCase("NU"))
							{
								reason = "Invalid Number";
							}
							else if (value[1].equalsIgnoreCase("DEP"))
							{
								reason = "Not Exists";
							}
							else if (value[1].trim().equalsIgnoreCase("TYP"))
							{
								reason = "Invalid";
							}
							else 
							{
								reason = " ";
							}
							if(k==0)
								reasons.append(name+"-"+reason);
							if(j==1)
							    reasons.append(name+"-"+reason);
							if(j>1&&j<val.length)
								reasons.append(","+name+"-"+reason);
							
		 					LogManager.push("name "+name+"\t"+"reason "+reason);
							}
							LogManager.push("reasons "+reasons);
							
							sVB.setH_validatestatus(reasons.toString());
							LogManager.push(name.toUpperCase()+" - "+reason );
						  return sVB;
							  }
						});
				   } else if(bankid.equalsIgnoreCase("HSB")){
				  list = this.mytemplate.query(query, new RowMapper() {
					  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				      InvalidVB sVB = new InvalidVB();
				      
				      //D.DATE_OF_ENTRY, D.CORRECT_CHQ_NO, D.CORRECT_CHQ_AMT, D.RSA_POLICY_NO, 
				      
					  sVB.setH_month(rset.getString("MONTH"));
					  sVB.setH_rowtype(rset.getString("RECORD_IDENTIFIER"));
					  sVB.setH_entryid(rset.getString("TXN_JOURNAL_NO"));
					  sVB.setH_typeofen(rset.getString("TYPE_OF_ENTRY"));
					  sVB.setH_drcr(rset.getString("DEBIT_CREDIT"));
					  sVB.setH_entryamt(rset.getString("ENTRY_AMOUNT"));
	                  sVB.setH_dateOfEntry(rset.getString("DATE_OF_ENTRY"));
					  sVB.setH_postdt(rset.getString("POST_DATE"));
					  sVB.setH_prodcode(rset.getString("PRODUCT_CODE"));
					  sVB.setH_pkuploc(rset.getString("PICKUP_LOCATION"));
					  sVB.setH_pkuppt(rset.getString("PICKUP_POINT"));
					  sVB.setH_pkupdt(rset.getString("PICKUP_DATE"));
					  sVB.setH_depositslipno(rset.getString("DEPOSIT_SLIP_NO"));
					  sVB.setH_depositdate(rset.getString("DEPOSIT_DATE"));
					  sVB.setH_deptamt(rset.getString("DEPOSIT_AMOUNT"));
					  sVB.setH_noofinst(rset.getString("NO_OF_INSTRUMENTS"));
					  sVB.setH_deptrmk(rset.getString("DEPOSIT_REMARKS"));
					  sVB.setH_instrumentno(rset.getString("INSTRUMENT_NO"));
					  sVB.setH_draweebk(rset.getString("DRAWEE_BANK"));
					  sVB.setH_clloc(rset.getString("CLEARING_LOC"));
					  sVB.setH_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
					  sVB.setH_instdt(rset.getString("INSTRUMENT_DATE"));
					  sVB.setH_drawernam(rset.getString("DRAWER_NAME"));
					  sVB.setH_policyno(rset.getString("MI_POLICY_NO"));
					  sVB.setH_returnrsn(rset.getString("RETURN_REASON"));
					  
					  String reqVal="";
						String val[]=rset.getString("STATUS").split(",");
					        
						StringBuffer reasons=new StringBuffer();
					    
						String name="";
						String reason="";
						LogManager.push("val[0]"+val[0]);
						int k=0;
						if(val.length>1)
						k=1;
						for(int j=k;j<val.length;j++)
						{
						reqVal=val[j];
						LogManager.push(reqVal+"<--reqVAl");
						String value[]= new String [2];
						StringTokenizer st = new StringTokenizer(reqVal,"*");
					   int i=0;
						while (st.hasMoreTokens()) {
					    	 value[i]=st.nextToken().trim();
					         i++;
					     }
						LogManager.push("value[0]"+value[0]);
						LogManager.push("value[1]"+value[1]);
						String query1="SELECT EXCEL_HEADER_NAME FROM TMAS_VALIDATION WHERE VALID_ID="+value[0];
						LogManager.push("RECEIPT POPUP:"+query1);
						
						name=Runner.singleSelection(query1);
						
						if(value[1].equalsIgnoreCase("D"))
						{
							reason = "Invalid Date Format";
						}
						else if (value[1].equalsIgnoreCase("M"))
						{
							reason = "Not Exists";
						}
						else if (value[1].equalsIgnoreCase("R"))
						{
							reason = "Not Exists in Master";
						}
						else if (value[1].equalsIgnoreCase("NU"))
						{
							reason = "Invalid Number";
						}
						else if (value[1].equalsIgnoreCase("DEP"))
						{
							reason = "Not Exists";
						}
						else if (value[1].trim().equalsIgnoreCase("TYP"))
						{
							reason = "Invalid";
						}
						else 
						{
							reason = " ";
						}
						if(k==0)
							reasons.append(name+"-"+reason);
						if(j==1)
						    reasons.append(name+"-"+reason);
						if(j>1&&j<val.length)
							reasons.append(","+name+"-"+reason);
						
	 					LogManager.push("name "+name+"\t"+"reason "+reason);
						}
						LogManager.push("reasons "+reasons);
						
						sVB.setH_validatestatus(reasons.toString());
						LogManager.push(name.toUpperCase()+" - "+reason );
					  return sVB;
						  }
					});
			   } else if(bankid.equalsIgnoreCase("AXB")){
			  list = this.mytemplate.query(query, new RowMapper() {
			  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			  InvalidVB sVB = new InvalidVB();
			  sVB.setA_type(rset.getString("TYPE"));
			  sVB.setA_cuscode(rset.getString("CUS_CODE"));
			  sVB.setA_locationname(rset.getString("LOCATION_NAME"));
			  sVB.setA_depositdate(rset.getString("DEPOSIT_DATE"));
			  sVB.setA_crdate(rset.getString("CR_DATE"));
			  sVB.setA_rtndate(rset.getString("RTN_DATE"));
			  sVB.setA_slipno(rset.getString("SLIP_NO"));
			  sVB.setA_nofins(rset.getString("NOF_INS"));
			  sVB.setA_slipamount(rset.getString("SLIP_AMOUNT"));
			  sVB.setA_instno(rset.getString("INST_NO"));
			  sVB.setA_instdate(rset.getString("INST_DATE"));
			  sVB.setA_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
			  sVB.setA_iadditionalinfo1(rset.getString("I_ADDITIONAL_INFO1"));
			  sVB.setA_iadditionalinfo2(rset.getString("I_ADDITIONAL_INFO2"));
			  sVB.setA_rtnamt(rset.getString("RTN_AMT"));
			  sVB.setA_returnreason(rset.getString("RETURN_REASON"));
			  sVB.setA_drnbk(rset.getString("DRN_BK"));
			  sVB.setA_drwnbrnchname(rset.getString("DRWN_BRNCH_NAME"));
			  String reqVal="";
			  String val[]=rset.getString("STATUS").split(",");
			        
			  StringBuffer reasons=new StringBuffer();
			    StringBuffer names=new StringBuffer();
				String name="";
				String reason="";
				LogManager.push("val[0]"+val[0]);
				int k=0;
				if(val.length>1)
					k=1;
				for(int j=k;j<val.length;j++)
				{
				reqVal=val[j];
				LogManager.push(reqVal+"<--reqVAl");
				String value[]= new String [2];
				StringTokenizer st = new StringTokenizer(reqVal,"*");
			   int i=0;
				while (st.hasMoreTokens()) {
			    	 value[i]=st.nextToken().trim();
			         i++;
			     }
				LogManager.push("value[0]"+value[0]);
				LogManager.push("value[1]"+value[1]);
				String query1="SELECT EXCEL_HEADER_NAME FROM TMAS_VALIDATION WHERE VALID_ID="+value[0];
				LogManager.push("RECEIPT POPUP:"+query1);
				
				name=Runner.singleSelection(query1);
				
				if(value[1].equalsIgnoreCase("D"))
				{
					reason = "Invalid Date Format";
				}
				else if (value[1].equalsIgnoreCase("M"))
				{
					reason = "Not Exists";
				}
				else if (value[1].equalsIgnoreCase("R"))
				{
					reason = "Not Exists in Master";
				}
				else if (value[1].equalsIgnoreCase("NU"))
				{
					reason = "Invalid Number";
				}
				else if (value[1].equalsIgnoreCase("DEP"))
				{
					reason = "Not Exists";
				}
				else if (value[1].trim().equalsIgnoreCase("TYP"))
				{
					reason = "Invalid";
				}
				else 
				{
					reason = " ";
				}
				if(k==0)
					reasons.append(name+"-"+reason);
				if(j==1)
				    reasons.append(name+"-"+reason);
				if(j>1&&j<val.length)
					reasons.append(","+name+"-"+reason);
				
				LogManager.push("name "+name+"\t"+"reason "+reason);
				}
				LogManager.push("reasons "+reasons);
				
				sVB.setA_validatestatus(reasons.toString());
				LogManager.push(name.toUpperCase()+" - "+reason );
				
				return sVB;
			  }
			  });
					
		  }
		  return list;
	}
	public List getReceiptPayments(String transid) throws CommonBaseException {
		List list=null;
		
		String  query="SELECT CHEQUE_NO, TO_CHAR(CHEQUE_DATE,'DD/MM/YYYY') CHEQUE_DATE, RECEIPT_NO, TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE, AMOUNT FROM RECEIPT_MASTER WHERE BATCHID='"+transid+"' AND TRANS_SOURCE='PYMT' ORDER BY 4 DESC,CHEQUE_NO";
		//String query="";  
		LogManager.push("Query executed:"+query);
			
		  list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					InvalidVB sVB = new InvalidVB();
					
					sVB.setR_chequeno(rset.getString("CHEQUE_NO"));
					sVB.setR_chequedate(rset.getString("CHEQUE_DATE"));
					sVB.setR_receiptno(rset.getString("RECEIPT_NO"));
					sVB.setR_receiptdate(rset.getString("RECEIPT_DATE"));
					sVB.setR_amount(rset.getString("AMOUNT"));
										
					return sVB;
				}
							
			});
		return list;
	}
	
	
	
	
public void updateReverse(SearchFormBean sbean) throws CommonBaseException {
		
		String bankcode=sbean.getBankId();
		String searchIn=sbean.getSearchIn();
		
		 String query="UPDATE CITI_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="UPDATE HDFC_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="UPDATE HSBC_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="UPDATE SCB_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="UPDATE AXIS_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		  query="update citi_bank c1 set actual_cheque_no=(select act_no from citi_bank c2 where c1.bank_no=c2.bank_no),actual_cheque_amt=(select act_amt from citi_bank c2 where c1.bank_no=c2.bank_no) where act_no is not null or act_amt is not null";
		 this.mytemplate.update(query);
		 
		 query="update citi_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
		 this.mytemplate.update(query);
		 
		 query="update hdfc_bank c1 set actual_cheque_no=(select act_no from hdfc_bank c2 where c1.bank_no=c2.bank_no),actual_cheque_amt=(select act_amt from hdfc_bank c2 where c1.bank_no=c2.bank_no) where act_no is not null or act_amt is not null";
		 this.mytemplate.update(query);
		 
		 query="update hdfc_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
		 this.mytemplate.update(query);
		 
		 query="update hsbc_bank c1 set actual_cheque_no=(select act_no from hsbc_bank c2 where c1.bank_no=c2.bank_no),actual_cheque_amt=(select act_amt from hsbc_bank c2 where c1.bank_no=c2.bank_no) where act_no is not null or act_amt is not null";
		 this.mytemplate.update(query);
		 
		 query="update hsbc_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
		 this.mytemplate.update(query);
		 
		 query="update scb_bank c1 set actual_cheque_no=(select act_no from scb_bank c2 where c1.bank_no=c2.bank_no),actual_cheque_amt=(select act_amt from scb_bank c2 where c1.bank_no=c2.bank_no) where act_no is not null or act_amt is not null";
		 this.mytemplate.update(query);
		 
		 query="update scb_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
		 this.mytemplate.update(query);
		 
		 query="update axis_bank c1 set actual_cheque_no=(select act_no from axis_bank c2 where c1.bank_no=c2.bank_no),actual_cheque_amt=(select act_amt from axis_bank c2 where c1.bank_no=c2.bank_no) where act_no is not null or act_amt is not null";
		 this.mytemplate.update(query);
		 
		 query="update axis_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
		 this.mytemplate.update(query);
	}


public void updateReverseReversal(SearchFormBean sbean) throws CommonBaseException {
		
		String bankcode=sbean.getBankId();
		String searchIn=sbean.getSearchIn();
		
		 String query="UPDATE CITI_BANK SET RECEIPT_SL_NO='',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID is null AND RECEIPT_SL_NO='-99999'  ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 query="UPDATE HDFC_BANK SET RECEIPT_SL_NO='',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID is null AND RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 query="UPDATE HSBC_BANK SET RECEIPT_SL_NO='',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID is null AND RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 query="UPDATE SCB_BANK SET RECEIPT_SL_NO='',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID is null AND RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 query="UPDATE AXIS_BANK SET RECEIPT_SL_NO='',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID is null AND RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 query="UPDATE CITI_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 query="UPDATE HDFC_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
		 this.mytemplate.update(query);
		 query="UPDATE HSBC_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 query="UPDATE SCB_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 query="UPDATE AXIS_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
	}


	public void updatefields(SearchFormBean sbean) throws CommonBaseException {
		
		String bankcode=sbean.getBankId();
		String searchIn=sbean.getSearchIn();
		LogManager.push("Inside Update Fields::::");
		String  query="update citi_bank set CASH_TRANSACTION_ID=null  where CASH_TRANSACTION_ID is not null";
		this.mytemplate.update(query);
			 
		query="update hdfc_bank set CASH_TRANSACTION_ID=null  where CASH_TRANSACTION_ID is not null ";
		this.mytemplate.update(query);
		
		query="update hsbc_bank set CASH_TRANSACTION_ID=null  where CASH_TRANSACTION_ID is not null ";
		this.mytemplate.update(query);
			
		query="update scb_bank set CASH_TRANSACTION_ID=null  where CASH_TRANSACTION_ID is not null ";
		this.mytemplate.update(query);
			
		query="update axis_bank set CASH_TRANSACTION_ID=null  where CASH_TRANSACTION_ID is not null ";
		this.mytemplate.update(query);
		
		 query="UPDATE CITI_BANK SET CASH_TRANSACTION_ID='-99999' WHERE RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="UPDATE HDFC_BANK SET CASH_TRANSACTION_ID='-99999' WHERE RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="UPDATE HSBC_BANK SET CASH_TRANSACTION_ID='-99999' WHERE RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="UPDATE SCB_BANK SET CASH_TRANSACTION_ID='-99999' WHERE RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="UPDATE AXIS_BANK SET CASH_TRANSACTION_ID='-99999' WHERE RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="update citi_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
		this.mytemplate.update(query);
		 
		 query="update hdfc_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
		this.mytemplate.update(query);
		
		query="update hsbc_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
		this.mytemplate.update(query);
			
		query="update scb_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
		this.mytemplate.update(query);
        
		query="update axis_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
		this.mytemplate.update(query);
		
		 query="update citi_bank c1 set act_no=(select actual_cheque_no from citi_bank c2 where c1.bank_no=c2.bank_no),act_amt=(select actual_cheque_amt from citi_bank c2 where c1.bank_no=c2.bank_no) where actual_cheque_no is not null or actual_cheque_amt is not null";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);

		 query="update hdfc_bank c1 set act_no=(select actual_cheque_no from hdfc_bank c2 where c1.bank_no=c2.bank_no),act_amt=(select actual_cheque_amt from hdfc_bank c2 where c1.bank_no=c2.bank_no) where actual_cheque_no is not null or actual_cheque_amt is not null";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="update hsbc_bank c1 set act_no=(select actual_cheque_no from hdfc_bank c2 where c1.bank_no=c2.bank_no),act_amt=(select actual_cheque_amt from hdfc_bank c2 where c1.bank_no=c2.bank_no) where actual_cheque_no is not null or actual_cheque_amt is not null";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="update scb_bank c1 set act_no=(select actual_cheque_no from scb_bank c2 where c1.bank_no=c2.bank_no),act_amt=(select actual_cheque_amt from scb_bank c2 where c1.bank_no=c2.bank_no) where actual_cheque_no is not null or actual_cheque_amt is not null";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 
		 query="update axis_bank c1 set act_no=(select actual_cheque_no from axis_bank c2 where c1.bank_no=c2.bank_no),act_amt=(select actual_cheque_amt from axis_bank c2 where c1.bank_no=c2.bank_no) where actual_cheque_no is not null or actual_cheque_amt is not null";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
	}

	public void updateReversalFields(SearchFormBean sbean) throws CommonBaseException {
		 String bankcode=sbean.getBankId();
		 String searchIn=sbean.getSearchIn();
		 LogManager.push("Inside Update Fields::::");
		 String  query="update citi_bank set CASH_TRANSACTION_ID=null  where CASH_TRANSACTION_ID is not null";
		 this.mytemplate.update(query);
		 
		 query="update hdfc_bank set CASH_TRANSACTION_ID=null  where CASH_TRANSACTION_ID is not null ";
		 this.mytemplate.update(query);
		
		 query="update hsbc_bank set CASH_TRANSACTION_ID=null  where CASH_TRANSACTION_ID is not null ";
		 this.mytemplate.update(query);
		
		 query="update scb_bank set CASH_TRANSACTION_ID=null  where CASH_TRANSACTION_ID is not null ";
		 this.mytemplate.update(query);
		
		 query="update axis_bank set CASH_TRANSACTION_ID=null  where CASH_TRANSACTION_ID is not null ";
		 this.mytemplate.update(query);
			
		 query="UPDATE CITI_BANK SET CASH_TRANSACTION_ID='-99999' WHERE RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 query="UPDATE HDFC_BANK SET CASH_TRANSACTION_ID='-99999' WHERE RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 query="UPDATE HSBC_BANK SET CASH_TRANSACTION_ID='-99999' WHERE RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 query="UPDATE SCB_BANK SET CASH_TRANSACTION_ID='-99999' WHERE RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
		 query="UPDATE AXIS_BANK SET CASH_TRANSACTION_ID='-99999' WHERE RECEIPT_SL_NO='-99999' ";
		 this.mytemplate.update(query);
		 LogManager.push("Query ::"+ query);
	}

 public void updateReverse2(SearchFormBean sbean) throws CommonBaseException {
	
	String bankcode=sbean.getBankId();
	String searchIn=sbean.getSearchIn();
	
 /*String query="UPDATE CITI_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
	 this.mytemplate.update(query);
	 LogManager.push("Query ::"+ query);
	 query="UPDATE HDFC_BANK SET RECEIPT_SL_NO='-99999',CASH_TRANSACTION_ID='' WHERE CASH_TRANSACTION_ID='-99999' ";
	 this.mytemplate.update(query);
	 LogManager.push("Query ::"+ query);
	 

	  query="update citi_bank c1 set actual_cheque_no=(select act_no from citi_bank c2 where c1.bank_no=c2.bank_no),actual_cheque_amt=(select act_amt from citi_bank c2 where c1.bank_no=c2.bank_no) where act_no is not null or act_amt is not null";
	 this.mytemplate.update(query);
	 
	 query="update citi_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
	 //this.mytemplate.update(query);
	 
	 query="update hdfc_bank set act_no=null ,act_amt=null where act_no is not null or act_amt is not null";
	//this.mytemplate.update(query);
	 
	 query="update hdfc_bank c1 set actual_cheque_no=(select act_no from hdfc_bank c2 where c1.bank_no=c2.bank_no),actual_cheque_amt=(select act_amt from hdfc_bank c2 where c1.bank_no=c2.bank_no) where act_no is not null or act_amt is not null";
	 this.mytemplate.update(query);*/
	
}
 
 	public List getReversalList(SearchFormBean sbean) throws CommonBaseException {
	  	LogManager.push("Inside getReversalList:");
		String query="";
		String bankcode=sbean.getBankId();
		String searchIn=sbean.getSearchIn();
		String formChequeNo=sbean.getChequeNo();
		String formChequeAmt=sbean.getChequeAmount();
		String formreceipt=sbean.getReceiptNo();
		LogManager.push("getSearchList formChequeNo "+formChequeNo+ "formreceipt:"+formreceipt);
		LogManager.push("getSearchList search in:"+searchIn);
		String chequeamts=sbean.getActualChequeAmount();
		LogManager.push("sbean.getactualchequeamt"+chequeamts);
		String transId=sbean.getTransactionNo();
		LogManager.push("getSearchList search in:"+searchIn);
		String realise="NOT";
		
		//String tableName=String.valueOf(val.get("TABLE_NAME"));
	    List list=null;
	    LogManager.push("transaction code"+transId+"formchequeno:"+formChequeNo);
		if(!transId.equalsIgnoreCase(""))
		{
			LogManager.push("not empty");
		}
		else
		{
			LogManager.push("empty");
		}
		try{
			LogManager.push("transaction code"+transId+"formchequeno:"+formChequeNo);
			//SEARCH BY TRANSACTIONS
			if((!transId.equalsIgnoreCase("")))
			{
				LogManager.push("Inside transaction 1 code"+transId);
				if(searchIn.equalsIgnoreCase("Bank"))
				{	
					List blist=getBankQueryData(bankcode);
					final Map tempMap = (Map) blist.get(0);
					final String tableName=((String)tempMap.get("TABLE_NAME"));
					final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
					final String reason=((String)tempMap.get("REASON"));
					final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
					final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
					final String bankName=(String)tempMap.get("BANK_NAME");
					final String receipt=((String)tempMap.get("RECEIPT_NO"));
					
					sbean.setBankName((String)tempMap.get("BANK_NAME"));
					query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+",CASH_TRANSACTION_ID FROM "+tableName+" T WHERE RECEIPT_SL_NO='-99999' AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo+" IS NOT NULL AND BATCHID="+transId+"  AND "+chequeStatus+" NOT LIKE 'H'  ORDER BY 7 DESC,"+chequeStatus+" DESC";
							
					LogManager.push("Query"+query);
					list = this.mytemplate.query(query, new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setChequeNo(rset.getString(chequeNo));
							sVB.setChequeAmount(rset.getString(chequeAmt));
							//System.out.print(rset.getString(chequeStatus).trim()+"-");
							if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
							{
								sVB.setRealisation("Reversal");
							}
							else 
							{
								sVB.setRealisation("Not Known");
							}
							if(rset.getString("REJECTION_TYPE")!=null){
								sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
							}
							else
							{
								sVB.setReason(rset.getString(reason));
							}
							sVB.setReceipt(rset.getString(receipt));
							sVB.setBankName(bankName);
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setBankNo1(rset.getString("BANK_NO"));
							sVB.setBankNo2(rset.getString("BANK_NO"));
							sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
							sVB.setActualChequeAmount(rset.getString("ACT_AMT"));
							sVB.setActualChequeNo(rset.getString("ACT_NO"));
							if(rset.getString("CASH_TRANSACTION_ID")!=null){
								if(rset.getString("CASH_TRANSACTION_ID").equalsIgnoreCase("-99999"))
									sVB.setReversal("R");
								else
										sVB.setReversal("");
								}
								else
								{
									sVB.setReversal("");
								}
							i++;
							return sVB;
						}
									
					});
				}
				else
				{
					query="SELECT R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID IN (SELECT B.BANK_ID FROM RECEIPT_MASTER A,BANK_ACCOUNT_CODE B WHERE A.BANK_CODE=B.BANK_ACCOUNT_CODE AND A.RECEIPT_SL_NO = R.RECEIPT_SL_NO) ) AS BANK_NAME,RECEIPT_NO,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL AND BATCHID ="+transId+"   ORDER BY 4 DESC,R.RECEIPT_SL_NO";
					LogManager.push("Query test"+query);
					list = this.mytemplate.query(query,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setBankName(rset.getString("BANK_NAME"));
							sVB.setChequeNo(rset.getString("CHEQUE_NO"));
							sVB.setChequeAmount(rset.getString("AMOUNT"));
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
							sVB.setReceipt(rset.getString("RECEIPT_NO"));
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
				}
			}
			//SEARCH BY VALUES GIVEN
			else
			{
				/**Start
				 * 
				 */
//				Search in all banks	
				if(bankcode.equalsIgnoreCase("all"))
				{
					String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
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
						String[] arg = new String[15];
						LogManager.push("inside blocks::::");
						//Search in bank statement 
					if(searchIn.equalsIgnoreCase("Bank"))
					{	
						if(sbean.getSearchFor().equalsIgnoreCase("exact")){
							if(!formChequeNo.equalsIgnoreCase("") )
							{
								arg[0]="AND "+chequeNo[0]+"='"+formChequeNo+"' ";
								arg[3]="AND "+chequeNo[1]+"='"+formChequeNo+"' ";
								arg[6]="AND "+chequeNo[2]+"='"+formChequeNo+"' ";
								arg[9]="AND "+chequeNo[3]+"='"+formChequeNo+"' ";
								arg[12]="AND "+chequeNo[4]+"='"+formChequeNo+"' ";
							 }
							else if(formChequeNo.equalsIgnoreCase(""))
							{
								arg[0]="";
								arg[3]="";
								arg[6]="";
								arg[9]="";
								arg[12]="";
							}
							if(!formChequeAmt.equalsIgnoreCase("") )
							{
								arg[1]="AND "+"trunc("+chequeAmt[0]+")"+"="+"trunc("+formChequeAmt+") ";
								arg[4]="AND "+"trunc("+chequeAmt[1]+")"+"="+"trunc("+formChequeAmt+") ";
								arg[7]="AND "+"trunc("+chequeAmt[2]+")"+"="+"trunc("+formChequeAmt+") ";
								arg[10]="AND "+"trunc("+chequeAmt[3]+")"+"="+"trunc("+formChequeAmt+") ";
								arg[13]="AND "+"trunc("+chequeAmt[4]+")"+"="+"trunc("+formChequeAmt+") ";
							}
							else if (formChequeAmt.equalsIgnoreCase("") )
							{
								arg[1]="";
								arg[4]="";
								arg[7]="";
								arg[10]="";
								arg[13]="";
							}
							if(!formreceipt.equalsIgnoreCase(""))
							{
								arg[2]="AND "+receipt[0]+"='"+formreceipt+"' ";
								arg[5]="AND "+receipt[1]+"='"+formreceipt+"' ";
								arg[8]="AND "+receipt[2]+"='"+formreceipt+"' ";
								arg[11]="AND "+receipt[3]+"='"+formreceipt+"' ";
								arg[14]="AND "+receipt[4]+"='"+formreceipt+"' ";
							 }
							else if(formreceipt.equalsIgnoreCase(""))
							{
								arg[2]="";
								arg[5]="";
								arg[8]="";
								arg[11]="";
								arg[14]="";
							 }
							query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[0]
									+ "') AS BANK, "
									+ chequeNo[0]
									+ ","
									+ chequeAmt[0]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[0]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[0]
									+ ","
									+ reason[0]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[0]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[0]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[0]
									+ " T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ "AND "
									+ chequeStatus[0]
									+ " NOT LIKE 'H' " +
							   "UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[1]
									+ "') AS BANK,"
									+ chequeNo[1]
									+ ","
									+ chequeAmt[1]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[1]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[1]
									+ ","
									+ reason[1]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[1]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[1]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[1]
									+ " T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[3]
									+ arg[4]
									+ arg[5]
									+ "AND "
									+ chequeStatus[1]
									+ " NOT LIKE 'H' " +
							   " UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[2]
									+ "') AS BANK,"
									+ chequeNo[2]
									+ ","
									+ chequeAmt[2]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[2]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[2]
									+ ","
									+ reason[2]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[2]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[2]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[2]
									+ " T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[6]
									+ arg[7]
									+ arg[8]
									+ "AND "
									+ chequeStatus[2]
									+ " NOT LIKE 'H' " +
								" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[3]
									+ "') AS BANK,"
									+ chequeNo[3]
									+ ","
									+ chequeAmt[3]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[3]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[3]
									+ ","
									+ reason[3]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[3]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[3]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[3]
									+ " T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[9]
									+ arg[10]
									+ arg[11]
									+ "AND "
									+ chequeStatus[3]
									+ " NOT LIKE 'H' " +
								" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[4]
									+ "') AS BANK,"
									+ chequeNo[4]
									+ ","
									+ chequeAmt[4]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[4]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[4]
									+ ","
									+ reason[4]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[4]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[4]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[4]
									+ " T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[12]
									+ arg[13]
									+ arg[14]
									+ "AND "
									+ chequeStatus[4]
									+ " NOT LIKE 'H' " +			
									
									"ORDER BY 8 DESC, 4 DESC";
					    }
						else
						{
							arg[0]="'%"+formChequeNo+"%'";
							arg[1]="'"+formChequeAmt+"%'";//this is executing
							arg[2]="'"+formreceipt+"%'";
							arg[3]="'%"+formChequeNo+"%'";
							arg[4]="'"+formChequeAmt+"%'";
							arg[5]="'"+formreceipt+"%'";
							arg[6]="'%"+formChequeNo+"%'";
							arg[7]="'"+formChequeAmt+"%'";
							arg[8]="'"+formreceipt+"%'";
							arg[9]="'%"+formChequeNo+"%'";
							arg[10]="'"+formChequeAmt+"%'";
							arg[11]="'"+formreceipt+"%'";
							arg[12]="'%"+formChequeNo+"%'";
							arg[13]="'"+formChequeAmt+"%'";
							arg[14]="'"+formreceipt+"%'";
							query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[0]
									+ "') AS BANK,"
									+ chequeNo[0]
									+ ","
									+ chequeAmt[0]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[0]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[0]
									+ ","
									+ reason[0]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[0]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[0]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[0]
									+ " T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL AND "
									+ chequeNo[0]
									+ " LIKE "
									+ arg[0]
									+ " AND "
									+ chequeAmt[0]
									+ " LIKE "
									+ arg[1]
									+ " AND "
									+ receipt[0]
									+ " LIKE "
									+ arg[2]
									+ " AND "
									+ chequeStatus[0]
									+ " NOT LIKE 'H' UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[1]
									+ "') AS BANK,"
									+ chequeNo[1]
									+ ","
									+ chequeAmt[1]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[1]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[1]
									+ ","
									+ reason[1]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[1]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[1]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[1]
									+ " T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL AND "
									+ chequeNo[1]
									+ " LIKE "
									+ arg[3]
									+ " AND "
									+ chequeAmt[1]
									+ " LIKE "
									+ arg[4]
									+ " AND "
									+ receipt[1]
									+ " LIKE "
									+ arg[5]
									+ " AND "
									+ chequeStatus[1]
									+ " NOT LIKE 'H' " +
							" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[2]
									+ "') AS BANK,"
									+ chequeNo[2]
									+ ","
									+ chequeAmt[2]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[2]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[2]
									+ ","
									+ reason[2]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[2]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[2]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[2]
									+ " T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL AND "
									+ chequeNo[2]
									+ " LIKE "
									+ arg[6]
									+ " AND "
									+ chequeAmt[2]
									+ " LIKE "
									+ arg[7]
									+ " AND "
									+ receipt[2]
									+ " LIKE "
									+ arg[8]
									+ " AND "
									+ chequeStatus[2]
									+ " NOT LIKE 'H'" +
							" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[3]
									+ "') AS BANK,"
									+ chequeNo[3]
									+ ","
									+ chequeAmt[3]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[3]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[3]
									+ ","
									+ reason[3]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[3]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[3]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[3]
									+ " T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL AND "
									+ chequeNo[3]
									+ " LIKE "
									+ arg[9]
									+ " AND "
									+ chequeAmt[3]
									+ " LIKE "
									+ arg[10]
									+ " AND "
									+ receipt[3]
									+ " LIKE "
									+ arg[11]
									+ " AND "
									+ chequeStatus[3]
									+ " NOT LIKE 'H'  " +
							" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[4]
									+ "') AS BANK,"
									+ chequeNo[4]
									+ ","
									+ chequeAmt[4]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[4]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[4]
									+ ","
									+ reason[4]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[4]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[4]
									+ " ,CASH_TRANSACTION_ID FROM "
									+ tableName[4]
									+ " T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL AND "
									+ chequeNo[4]
									+ " LIKE "
									+ arg[12]
									+ " AND "
									+ chequeAmt[4]
									+ " LIKE "
									+ arg[13]
									+ " AND "
									+ receipt[4]
									+ " LIKE "
									+ arg[14]
									+ " AND "
									+ chequeStatus[4]
									+ " NOT LIKE 'H'  " +				
									
									"ORDER BY 8 DESC,4 DESC";
						}
						LogManager.push("Query"+query);
						LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
						list = this.mytemplate.query(query,new RowMapper() {
							public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
								SearchVB sVB = new SearchVB();
								sVB.setBankName(rset.getString(1));
								sVB.setChequeNo(rset.getString(2));
								sVB.setChequeAmount(rset.getString(3));
								//System.out.print(rset.getString(chequeStatus).trim()+"-");
								if(rset.getString(4).trim().equalsIgnoreCase("C"))
								{
									sVB.setRealisation("Realized");
								}
								else if(rset.getString(4).trim().equalsIgnoreCase("D"))
								{
									sVB.setRealisation("Returned");
								}
								else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("R"))
								{
									sVB.setRealisation("Reversal");
								}
								else 
								{
									sVB.setRealisation("Not Known");
								}
								if(rset.getString(11)!=null){
									sVB.setReason(rset.getString(5)+"("+rset.getString(11)+")");
								}
								else
								{
									sVB.setReason(rset.getString(5));
								}
								//sVB.setBankName(bankName);
								sVB.setReceiptNo(rset.getString(6));
								sVB.setBankNo1(rset.getString(7));
								sVB.setBankNo2(rset.getString(7));
								sVB.setDepositDate(rset.getString(8));
								sVB.setActualChequeAmount(rset.getString(10));
								sVB.setActualChequeNo(rset.getString(9));
								sVB.setReceipt(rset.getString(12));
								if(rset.getString("CASH_TRANSACTION_ID")!=null){
									if(rset.getString("CASH_TRANSACTION_ID").equalsIgnoreCase("-99999"))
										sVB.setReversal("R");
									else
											sVB.setReversal("");
									}
									else
									{
										sVB.setReversal("");
									}
								return sVB;
							}
						});
					}
					//Search in Receipt statement
					else
					{
						if(sbean.getSearchFor().equalsIgnoreCase("exact")){
							if(!formChequeNo.equalsIgnoreCase("") )
							{
								arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"' ";
							 }
							else if(formChequeNo.equalsIgnoreCase(""))
							{
								arg[0]="";
							}
							if(!formChequeAmt.equalsIgnoreCase("") )
							{
								arg[1]="AND trunc(R.AMOUNT)=trunc("+formChequeAmt+") ";
							}
							else if (formChequeAmt.equalsIgnoreCase("") )
							{
								arg[1]="";
							}
							if(!formreceipt.equalsIgnoreCase(""))
							{
								arg[2]="AND RECEIPT_NO='"+formreceipt+"' ";
							 }
							else if(formreceipt.equalsIgnoreCase(""))
							{
								arg[2]="";
							 }
							query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[0]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL "
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[0]
									+ "' AND STATUS='Y')   UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[1]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL "
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[1]
									+ "' AND STATUS='Y')  " +
								 "  UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[2]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL "
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[2]
									+ "' AND STATUS='Y') " +
								" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[3]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL "
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[3]
									+ "' AND STATUS='Y') " +
							   " UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[4]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL "
									+ arg[0]
									+ arg[1]
									+ arg[2]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[4]
									+ "' AND STATUS='Y') " +			
									"ORDER BY 6 DESC,RECEIPT_SL_NO";
						}
						else
						{
						arg[0]="'%"+formChequeNo+"%'";
						arg[1]="'"+formChequeAmt+"%'";
						arg[2]="'"+formreceipt+"%'";
						query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[0]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL AND (R.CHEQUE_NO LIKE "
									+ arg[0]
									+ " or r.cheque_no is null) AND R.RECEIPT_NO LIKE "
									+ arg[2]
									+ " AND R.AMOUNT LIKE "
									+ arg[1]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[0]
									+ "' AND STATUS='Y')  UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[1]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL AND (R.CHEQUE_NO LIKE "
									+ arg[0]
									+ " or r.cheque_no is null) AND R.RECEIPT_NO LIKE "
									+ arg[2]
									+ " AND R.AMOUNT LIKE "
									+ arg[1]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[1]
									+ "' AND STATUS='Y') " +
								" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[2]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL (R.CHEQUE_NO LIKE "
									+ arg[0]
									+ " or r.cheque_no is null) AND R.RECEIPT_NO LIKE "
									+ arg[2]
									+ " AND R.AMOUNT LIKE "
									+ arg[1]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[2]
									+ "' AND STATUS='Y') " +
								" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[3]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL AND (R.CHEQUE_NO LIKE "
									+ arg[0]
									+ " or r.cheque_no is null) AND R.RECEIPT_NO LIKE "
									+ arg[2]
									+ " AND R.AMOUNT LIKE "
									+ arg[1]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[3]
									+ "' AND STATUS='Y') " +
								" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"
									+ bankIds[4]
									+ "') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "
									+ realise
									+ " NULL AND (R.CHEQUE_NO LIKE "
									+ arg[0]
									+ " or r.cheque_no is null) AND R.RECEIPT_NO LIKE "
									+ arg[2]
									+ " AND R.AMOUNT LIKE "
									+ arg[1]
									+ " AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"
									+ bankIds[4]
									+ "' AND STATUS='Y') " +
								
									"ORDER BY 6 DESC,RECEIPT_SL_NO";
						}
						LogManager.push("Query"+query);
						list = this.mytemplate.query(query,  new RowMapper() {
							public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
								SearchVB sVB = new SearchVB();
								sVB.setChequeNo(rset.getString("CHEQUE_NO"));
								
								sVB.setChequeAmount((String)rset.getString("AMOUNT").toString());
								sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
								sVB.setBankName(rset.getString("BANK"));
								sVB.setReceipt(rset.getString("RECEIPT_NO"));
								if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("C"))
								{
									sVB.setRealisation("Realized");
								}
								else if((rset.getString("CHEQUE_STATUS")==null?"":rset.getString("CHEQUE_STATUS")).trim().equalsIgnoreCase("D"))
								{
									sVB.setRealisation("Returned");
								}
								else 
								{
									sVB.setRealisation("Not Known");
								}
								sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
								return sVB;
							}});
					}
				}	
				//------end-------------
				//not searched for all banks
				else
				{
				/*End
				 * 
				 */
				List blist=getBankQueryData(bankcode);
				final Map tempMap = (Map) blist.get(0);
				final String tableName=((String)tempMap.get("TABLE_NAME"));
				final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
				final String reason=((String)tempMap.get("REASON"));
				final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
				final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
				final String bankName=(String)tempMap.get("BANK_NAME");
				final String receipt=((String)tempMap.get("RECEIPT_NO"));
				sbean.setBankName((String)tempMap.get("BANK_NAME"));
				LogManager.push("Inside search code");
				String[] arg = new String[3];
		if(searchIn.equalsIgnoreCase("Bank"))
		{	
			if(sbean.getSearchFor().equalsIgnoreCase("exact")){
				if(!formChequeNo.equalsIgnoreCase("") )
				{
					arg[0]=" AND "+chequeNo+"='"+formChequeNo+"'";
				 }
				else if(formChequeNo.equalsIgnoreCase(""))
				{
					arg[0]=" ";
				}
				if(!formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]=" AND "+"trunc("+chequeAmt+")=trunc('"+formChequeAmt+"')";
				}
				else if (formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]=" ";
				}
				if(!formreceipt.equalsIgnoreCase(""))
				{
					arg[2]=" AND "+receipt+"='"+formreceipt+"'";
				 }
				else if(formreceipt.equalsIgnoreCase(""))
				{
					arg[2]=" ";
				 }
				query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+",CASH_TRANSACTION_ID FROM "+tableName+" T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+"  AND "+chequeStatus+" NOT LIKE 'H' ORDER BY 7 DESC,"+chequeStatus+" DESC";
			}
			else
			{
				arg[0]="'%"+formChequeNo+"%'";
				arg[1]="'"+formChequeAmt+"%'";
				arg[2]="'"+formreceipt+"%'";
				query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+",CASH_TRANSACTION_ID FROM "+tableName+" T WHERE RECEIPT_SL_NO = -99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo+" LIKE "+arg[0]+" AND "+chequeAmt+" LIKE "+arg[1]+" AND "+receipt+" LIKE "+arg[2]+"  AND "+chequeStatus+" NOT LIKE 'H'  ORDER BY 7 DESC,"+chequeStatus+" DESC";
			}
			LogManager.push("Query"+query);
			LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
			
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					SearchVB sVB = new SearchVB();
					sVB.setChequeNo(rset.getString(chequeNo));
					sVB.setChequeAmount(rset.getString(chequeAmt));
					//System.out.print(rset.getString(chequeStatus).trim()+"-");
					if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
					{
						sVB.setRealisation("Realized");
					}
					else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
					{
						sVB.setRealisation("Returned");
					}
					else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
					{
						sVB.setRealisation("Reversal");
					}
					else 
					{
						sVB.setRealisation("Not Known");
					}
					if(rset.getString("REJECTION_TYPE")!=null){
					sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
					}
					else
					{
						sVB.setReason(rset.getString(reason));
					}
					sVB.setBankName(bankName);
					sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
					sVB.setBankNo1(rset.getString("BANK_NO"));
					sVB.setBankNo2(rset.getString("BANK_NO"));
					sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
					sVB.setActualChequeAmount(rset.getString("ACT_AMT"));
					sVB.setActualChequeNo(rset.getString("ACT_NO"));
					if(rset.getString("CASH_TRANSACTION_ID")!=null){
					if(rset.getString("CASH_TRANSACTION_ID").equalsIgnoreCase("-99999"))
						sVB.setReversal("R");
					else
							sVB.setReversal("");
					}
					else
					{
						sVB.setReversal("");
					}
					sVB.setReceipt(rset.getString(receipt));
					i++;
					return sVB;
				}
			});
		}
		else
		{
			if(sbean.getSearchFor().equalsIgnoreCase("exact")){
				if(!formChequeNo.equalsIgnoreCase("") )
				{
					arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"'";
				 }
				else if(formChequeNo.equalsIgnoreCase(""))
				{
					arg[0]=" ";
				}
				if(!formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]=" AND trunc(R.AMOUNT)=trunc('"+formChequeAmt+"')";
				}
				else if (formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]=" ";
				}
				if(!formreceipt.equalsIgnoreCase(""))
				{
					arg[2]=" AND R.RECEIPT_NO='"+formreceipt+"'";
				 }
				else if(formreceipt.equalsIgnoreCase(""))
				{
					arg[2]=" ";
				 }
				query="SELECT R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,RECEIPT_NO,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 4 DESC,R.RECEIPT_SL_NO";
			}
			else
			{
			arg[0]="'%"+formChequeNo+"%'";
			arg[1]="'"+formChequeAmt+"%'";
			arg[2]="'"+formreceipt+"%'";
			query="SELECT R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,RECEIPT_NO,CHEQUE_STATUS FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 4 DESC,R.RECEIPT_SL_NO";
			}
			//query="SELECT R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,RECEIPT_NO FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE '4%' AND R.AMOUNT LIKE '%' AND R.RECEIPT_NO LIKE '%' AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY R.RECEIPT_SL_NO";
			
			LogManager.push("Query"+query);
			LogManager.push(arg[0]+":"+""+arg[1]+":"+arg[2]);
			
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int args) throws SQLException {
					SearchVB sVB = new SearchVB();
					sVB.setBankName(bankName);
					sVB.setChequeNo(rset.getString("CHEQUE_NO"));
					sVB.setChequeAmount(rset.getString("AMOUNT"));
					sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
					sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
					sVB.setReceipt(rset.getString("RECEIPT_NO"));
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
		}
	    }
		}
		}
		catch(Exception e)
		{
			LogManager.push("Exception In SearchDAOImpl - getReversalList searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+query);
		//LogManager.push("Returning list size"+list.size());
		return list; 
	}
 	
 	public void updateReversalDetails(final List list, final SearchFormBean sForm) throws CommonBaseException{
		LogManager.push("SearchDAOImpl updateReversalDetails() method");
		LogManager.logEnter();
		String bankcode=sForm.getBankId();
		LogManager.push("Bank code"+bankcode+list.size());
		
		if(bankcode.equalsIgnoreCase("all"))
		{
			LogManager.push("Bank code inside code "+bankcode+list.size());
			if(list.size()>0){
			String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
			List listbank;
			listbank =this.mytemplate.query(query1,new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				return rset.getString("BANK_ID");
			}} );
			final String tableName[]=new String[listbank.size()];
			final String chequeStatus[]=new String[listbank.size()];
			final String bankName[]=new String[listbank.size()];
			final String bankIds[]=new String[listbank.size()];
		 	for(int i=0;i<listbank.size();i++)
			{
			bankIds[i]=listbank.get(i).toString();
			LogManager.push("bank"+i+":"+listbank.get(i).toString());
			List blist=getBankQueryData(listbank.get(i).toString());
			final Map tempMap = (Map)blist.get(0);
			 tableName[i]=((String)tempMap.get("TABLE_NAME"));
			
		    bankName[i]=(String)tempMap.get("BANK_NAME");
			
			 LogManager.push("<--"+tableName[i]+""+chequeStatus[i]+"--**");
			// sbean.setBankName(bankName[i]);
			 try{
				    for(int k=0;k<list.size();k++){
				    	 final SearchVB svb = (SearchVB)list.get(k);
							final String updateQuery = "UPDATE "+tableName[i]+" SET CASH_TRANSACTION_ID=? WHERE BANK_NO=? ";
							final Object[] arg = new Object[2];
							arg[1] = svb.getBankNo1();
							LogManager.push("Bank No in Updation::>"+svb.getBankNo1());
							LogManager.push("svb.getReversal()"+svb.getReversal());
							if(svb.getReversal()!=null){
							if(svb.getReversal().equalsIgnoreCase("R"))
							{
								arg[0] = "-99999";
							}
							else
							{
								arg[0] = "";	
							}
							}
							else
							{
								 arg[0] = "";	
							}
							LogManager.push("arg[0]"+arg[0]);
							this.mytemplate.update(updateQuery, arg);
							System.out.print("Update query-->"+updateQuery);
					}
				}catch(Exception e){
					LogManager.fatal(e);
				}
				finally
				{
					LogManager.push("end");
				}
			}
			 }
		}
		else
		{
		if(list.size()>0){
		List blist=getBankQueryData(bankcode);
		final SearchVB svb1 = (SearchVB)list.get(0);
		//LogManager.push("Cheque amount actual:"+svb1.getActualChequeAmount());
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		try{
		    for(int i=0;i<list.size();i++){
				final SearchVB svb = (SearchVB)list.get(i);
					final String updateQuery = "UPDATE "+tableName+" SET CASH_TRANSACTION_ID=? WHERE BANK_NO=? ";
					System.out.print("Update query-->"+updateQuery);
					final Object[] arg = new Object[2];
					arg[1] = svb.getBankNo1();
					LogManager.push("Bank No in Updation::>"+svb.getBankNo1());
					if(svb.getReversal()!=null){
						if(svb.getReversal().equalsIgnoreCase("R"))
						{
						arg[0] = "-99999";
						}
						else
						{
						 arg[0] = "";	
						}
						}
						else
						{
							 arg[0] = "";	
						}
					this.mytemplate.update(updateQuery, arg);
			}
		}catch(Exception e){
			LogManager.fatal(e);
		}
	    }
		}
		LogManager.push("SearchDAOImpl updateReversalDetails() method");
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
	}
 	
 	public List getBankNocheqeus(String transid, String bankid) throws CommonBaseException {
		
	  List list=null;
	  if(!bankid.equalsIgnoreCase(""))
	  {
		  
	  
		  List blist=getBankQueryData(bankid);
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
		final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
	    final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
	    final String depno=((String)tempMap.get("RECEIPT_NO"));
	     
	    if(bankid.equalsIgnoreCase("CIT")){
			  String  query="SELECT to_char(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,CHEQUE_AMT,"+depno+" FROM "+tableName+" WHERE BATCHID="+transid +" AND "+chequeNo+" is null ORDER BY "+depno+" DESC";
			  LogManager.push("Query executed:"+query);
		      list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					InvalidVB sVB = new InvalidVB();
					
					sVB.setC_depositdate(rset.getString("DEPOSIT_DATE"));
					sVB.setC_chequeamt(rset.getString("CHEQUE_AMT"));
					sVB.setC_depslipno(rset.getString("DEPSLIPNO"));
					
					return sVB;
				}
							
			});
		  }
		  else if(bankid.equalsIgnoreCase("HDB")){
			  String  query="SELECT to_char(POST_DT,'DD/MM/YYYY') POST_DT,INSTRUMENT_AMOUNT,"+depno+" FROM "+tableName+" WHERE BATCHID="+transid +" AND "+chequeNo+" is null ORDER BY "+depno+" DESC";
			  LogManager.push("Query executed:"+query);
		    
			  list = this.mytemplate.query(query, new RowMapper() {
				  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			
			      InvalidVB sVB = new InvalidVB();
				 
				 
				  sVB.setH_postdt(rset.getString("POST_DT"));
				  sVB.setH_depositslipno(rset.getString("DEPOSIT_SLIP_NO"));
				  sVB.setH_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
				 
				
				  return sVB;
					  }
				});
			   }
		  else if(bankid.equalsIgnoreCase("KOT")){
			  String  query="SELECT to_char(POST_DT,'DD/MM/YYYY') POST_DT,INSTRUMENT_AMOUNT,"+depno+" FROM "+tableName+" WHERE BATCHID="+transid +" AND "+chequeNo+" is null ORDER BY "+depno+" DESC";
			  LogManager.push("Query executed:"+query);
		    
			  list = this.mytemplate.query(query, new RowMapper() {
				  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			
			      InvalidVB sVB = new InvalidVB();
				 
				 
				  sVB.setK_postdt(rset.getString("POST_DT"));
				  sVB.setK_depositslipno(rset.getString("DEPOSIT_SLIP_NO"));
				  sVB.setK_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
				 
				
				  return sVB;
					  }
				});
			   }
	     
		  else if(bankid.equalsIgnoreCase("SCB")){
			  String  query="SELECT to_char(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,CHQ_AMOUNT,"+depno+" FROM "+tableName+" WHERE BATCHID="+transid +" AND "+chequeNo+" is null ORDER BY "+depno+" DESC";
			  LogManager.push("Query executed: testqry::"+query);
		    
			  list = this.mytemplate.query(query, new RowMapper() {
				  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			
			      InvalidVB sVB = new InvalidVB();
				 
				 
				  sVB.setS_depdate(rset.getString("DEPOSIT_DATE"));
				  sVB.setS_depositno(rset.getString("DEPOSIT_NO"));
				  sVB.setS_chequeamt(rset.getString("CHQ_AMOUNT"));
				 
				
				  return sVB;
					  }
				});
			   }
		  else if(bankid.equalsIgnoreCase("AXB")){
			  
			  String  query="SELECT to_char(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,INSTRUMENT_AMOUNT,"+depno+" FROM "+tableName+" WHERE BATCHID="+transid +" AND "+chequeNo+" is null ORDER BY "+depno+" DESC";
			  LogManager.push("Query executed: testqry::"+query);
		    
			  list = this.mytemplate.query(query, new RowMapper() {
				  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			
			      InvalidVB sVB = new InvalidVB();
				 
				 
				  sVB.setA_depositdate(rset.getString("DEPOSIT_DATE"));
				  sVB.setA_slipno(rset.getString("SLIP_NO"));
				  sVB.setA_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
				 
				
				  return sVB;
					  }
				});
			   }
	  }
	 
	 
	  else if(bankid.equalsIgnoreCase("")){
		  String  query="SELECT to_char(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,AMOUNT,RECEIPT_NO FROM RECEIPT_MASTER WHERE BATCHID="+transid +" AND CHEQUE_NO is null ORDER BY RECEIPT_NO DESC";
		  LogManager.push("Query executed:"+query);
	    
		  list = this.mytemplate.query(query, new RowMapper() {
			  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
		
		      InvalidVB sVB = new InvalidVB();
			 
			 
			  sVB.setH_postdt(rset.getString("RECEIPT_DATE"));
			  sVB.setH_depositslipno(rset.getString("RECEIPT_NO"));
			  sVB.setH_instrumentamount(rset.getString("AMOUNT"));
			 
			
			  return sVB;
				  }
			});
		   }
	  return list;
}
public List getReversalUpdatedList(String[] bankNos,SearchFormBean sbean,String sid) throws CommonBaseException {
	
	List list=null;
	Set<String> set=new HashSet<String>();
	try {
		
		String bankNo="";
		String querydel="DELETE FROM TEMP_NONMATCHED WHERE SESSION_ID='"+sid+"'";
		Runner.updation(querydel);
		LogManager.push("Query deleted:::::::::"+querydel);
		for(int i=1;i<bankNos.length;i++)
		{
			set.add(bankNos[i]);
					
		}
		
		for(Iterator<String> it=set.iterator();it.hasNext();)
		{
			
			String value=it.next();
			String queryinstemp="INSERT INTO TEMP_NONMATCHED VALUES('"+value+"','"+sid+"')";
			Runner.inserion(queryinstemp);
		}
		
		LogManager.push("BankNoin"+ bankNo);
		String BankIn="";
		if(bankNos.length>0)
		{
			//BankIn = " AND B.BANK_NO IN (" +bankNo +")";
			BankIn = " AND B.BANK_NO =T.BANKNO AND T.SESSION_ID='"+sid+"'";
		}
		
		String bankcode=sbean.getBankId();
		LogManager.push("Enters getMatchedList");

		if(bankcode.equalsIgnoreCase("all"))
		{
			//start
			String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
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
		
				for(int k=0;k<listbank.size();k++)
				{
				bankIds[k]=listbank.get(k).toString();
				LogManager.push("bank"+i+":"+listbank.get(k).toString());
				List blist=getBankQueryData(listbank.get(k).toString());
				final Map tempMap = (Map)blist.get(0);
				 tableName[k]=((String)tempMap.get("TABLE_NAME"));
				 chequeStatus[k]=((String)tempMap.get("CHEQUE_STATUS"));
				 reason[k]=((String)tempMap.get("REASON"));
				 chequeNo[k]=((String)tempMap.get("CHEQUE_NO"));
			 	 chequeAmt[k]=((String)tempMap.get("CHEQUE_AMT"));
			     bankName[k]=(String)tempMap.get("BANK_NAME");
				 receipt[k]=((String)tempMap.get("RECEIPT_NO"));
				 LogManager.push("<--"+tableName[k]+""+chequeStatus[k]+"-->");
				// sbean.setBankName((String)tempMap.get("BANK_NAME"));
		 }//end
				for(int k=0;k<listbank.size();k++)
				{
				bankIds[k]=listbank.get(k).toString();
				LogManager.push("bank"+i+":"+listbank.get(k).toString());
				List blist=getBankQueryData(listbank.get(k).toString());
				final Map tempMap = (Map)blist.get(0);
				 tableName[k]=((String)tempMap.get("TABLE_NAME"));
				 chequeStatus[k]=((String)tempMap.get("CHEQUE_STATUS"));
				 reason[k]=((String)tempMap.get("REASON"));
				 chequeNo[k]=((String)tempMap.get("CHEQUE_NO"));
			 	 chequeAmt[k]=((String)tempMap.get("CHEQUE_AMT"));
			     bankName[k]=(String)tempMap.get("BANK_NAME");
				 receipt[k]=((String)tempMap.get("RECEIPT_NO"));
				 LogManager.push("<--"+tableName[k]+""+chequeStatus[k]+"-->");
				// sbean.setBankName((String)tempMap.get("BANK_NAME"));
				}
				final String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,B."+chequeNo[0]+",B."+chequeAmt[0]+",B."+reason[0]+",B."+chequeStatus[0]+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'dd/mm/yyyy') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[0]+")) AS REJECTION_TYPE,"+receipt[0]+"  FROM "+tableName[0]+" B ,TEMP_NONMATCHED T WHERE  B.RECEIPT_SL_NO IS NOT NULL AND B.RECEIPT_SL_NO=-99999 "+BankIn+" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,B."+chequeNo[1]+",B."+chequeAmt[1]+",B."+reason[1]+",B."+chequeStatus[1]+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'dd/mm/yyyy') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[1]+")) AS REJECTION_TYPE,"+receipt[1]+"  FROM "+tableName[1]+" B ,TEMP_NONMATCHED T WHERE  B.RECEIPT_SL_NO=-99999 AND B.RECEIPT_SL_NO  IS NOT NULL "+BankIn+" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,B."+chequeNo[2]+",B."+chequeAmt[2]+",B."+reason[2]+",B."+chequeStatus[2]+",B.RECEIPT_SL_NO,to_char(B.POST_DT,'dd/mm/yyyy') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[2]+")) AS REJECTION_TYPE,"+receipt[2]+"  FROM "+tableName[2]+" B ,TEMP_NONMATCHED T WHERE  B.RECEIPT_SL_NO=-99999 AND B.RECEIPT_SL_NO  IS NOT NULL "+BankIn +
				" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,B."+chequeNo[3]+",B."+chequeAmt[3]+",B."+reason[3]+",B."+chequeStatus[3]+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'dd/mm/yyyy') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[3]+")) AS REJECTION_TYPE,"+receipt[3]+"  FROM "+tableName[3]+" B ,TEMP_NONMATCHED T WHERE  B.RECEIPT_SL_NO=-99999 AND B.RECEIPT_SL_NO  IS NOT NULL "+BankIn;
				LogManager.push("Query for BANK LIST matched col:==>"+query+"count-->");
				list = this.mytemplate.query(query, new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						sVB.setChequeNo(rset.getString(chequeNo[0]));
						sVB.setChequeAmount(rset.getString(chequeAmt[0]));
						//System.out.print(rset.getString(chequeStatus).trim()+"-");
						sVB.setRealisation("Reversal");
						sVB.setReason(rset.getString(reason[0]));
						sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
						sVB.setReceipt(rset.getString(receipt[0]));
						sVB.setBankName(rset.getString("BANK"));
						return sVB;
					}
				});
		}
		else
		{
		List blist=getBankQueryData(bankcode);
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
		final String reason=((String)tempMap.get("REASON"));
		final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
		final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
		final String receipt=((String)tempMap.get("RECEIPT_NO"));
		
		//String qeury1="SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT FROM "+tableName+""
		//GETTING COUNTS FOR NO. OF RECORDS MATCHED
		/*String qury ="SELECT count(*) FROM RECEIPT_MASTER R,"+tableName+" B WHERE B.ACTUAL_CHEQUE_NO=R.CHEQUE_NO AND B.ACTUAL_CHEQUE_AMT=R.AMOUNT AND B.ACTUAL_CHEQUE_NO IS NOT NULL AND B.ACTUAL_CHEQUE_AMT IS NOT NULL  AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' UNION ALL SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",R.RECEIPT_SL_NO,B.DEPOSIT_DATE FROM RECEIPT_MASTER R,"+tableName+" B WHERE B.ACTUAL_CHEQUE_NO=R.CHEQUE_NO AND B.ACTUAL_CHEQUE_AMT=R.AMOUNT AND B."+chequeNo+" IS  NULL AND B.ACTUAL_CHEQUE_AMT IS NOT NULL  AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' UNION ALL SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",R.RECEIPT_SL_NO,B.DEPOSIT_DATE FROM RECEIPT_MASTER R,"+tableName+" B WHERE B.ACTUAL_CHEQUE_NO=R.CHEQUE_NO AND B."+chequeAmt+"=R.AMOUNT AND B.ACTUAL_CHEQUE_NO IS NOT NULL AND B.ACTUAL_CHEQUE_AMT IS NULL  AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ'";
		int count=this.mytemplate.queryForInt(qury);
		LogManager.push("Query for AFTER CHANGE matched col:==>"+qury+"count-->"+count);
		*/
		//UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN BOTH FIELDS NOT NULL
		//Query for not matched actual cheque no and amt:
		
		final String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankcode+"') AS BANK,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'dd/mm/yyyy') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason+")) AS REJECTION_TYPE,"+receipt+"  FROM "+tableName+" B ,TEMP_NONMATCHED T WHERE  B.RECEIPT_SL_NO=-99999 AND B.RECEIPT_SL_NO IS NOT NULL "+BankIn ;
		LogManager.push("Query for BANK LIST matched test2 col:==>"+query+"count-->");
		list = this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				SearchVB sVB = new SearchVB();
				sVB.setChequeNo(rset.getString(chequeNo));
				sVB.setChequeAmount((String)rset.getString(chequeAmt).toString());
				//System.out.print(rset.getString(chequeStatus).trim()+"-");
				sVB.setRealisation("Reversal");
				sVB.setReason(rset.getString(reason));
				sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
				sVB.setReceipt(rset.getString(receipt));
				sVB.setBankName(rset.getString("BANK"));
				return sVB;
			}						
		});
		}
		
		//REMOVE AFTER OPERATION
		//qury="UPDATE "+tableName+" SET ACTUAL_CHEQUE_NO=NULL,ACTUAL_CHEQUE_AMT=NULL WHERE RECEIPT_SL_NO IS NULL";
		//this.mytemplate.update(qury);
		//LogManager.push("Query for receipt matched col:==>"+qury+"count-->");
		LogManager.push("Exit reversal updated list");
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}return list;
}

	public void processActuals(SearchFormBean sbean) throws CommonBaseException {

	List list=null;
	String bankcode=sbean.getBankId();
	LogManager.push("Enters processActuals");
	//if(bankcode.equalsIgnoreCase("all"))
	//{
		//start
		String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
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
		for(int k=0;k<listbank.size();k++)
		{
			bankIds[k]=listbank.get(k).toString();
			LogManager.push("bank"+i+":"+listbank.get(k).toString());
			List blist=getBankQueryData(listbank.get(k).toString());
			final Map tempMap = (Map)blist.get(0);
			 tableName[k]=((String)tempMap.get("TABLE_NAME"));
			 chequeStatus[k]=((String)tempMap.get("CHEQUE_STATUS"));
			 reason[k]=((String)tempMap.get("REASON"));
			 chequeNo[k]=((String)tempMap.get("CHEQUE_NO"));
		 	 chequeAmt[k]=((String)tempMap.get("CHEQUE_AMT"));
		     bankName[k]=(String)tempMap.get("BANK_NAME");
			 receipt[k]=((String)tempMap.get("RECEIPT_NO"));
			 LogManager.push("<--"+tableName[k]+""+chequeStatus[k]+"-->");
			// sbean.setBankName((String)tempMap.get("BANK_NAME"));
		//UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN BOTH FIELDS NOT NULL
		//Query for not matched actual cheque no and amt:
		String queryvalues="SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT FROM "+tableName[k]+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL and DISHONOUR_TYPE is null";
		List values=this.mytemplate.queryForList(queryvalues);
		for(int i=0;i<values.size();i++)
		{
			LogManager.push("values " +values.get(i));
			final Map tempMap1 = (Map) values.get(i);
			final String actualchequeno=((String)tempMap1.get("ACTUAL_CHEQUE_NO").toString());
			final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT").toString());
			LogManager.push("actualchequeno"+actualchequeno+"actualchequeamt"+actualchequeamt);
			//Query to find actual cheque exists
			String qury="UPDATE "+tableName[k]+" B SET RECEIPT_SL_NO=(SELECT MIN(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE R.CHEQUE_NO='"+actualchequeno+"' AND R.AMOUNT="+actualchequeamt+" AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
			this.mytemplate.update(qury);
			LogManager.push("Query for bank matched col BOTH NOT NULL:==>"+qury+"count-->");
			
			qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName[k]+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO= '"+actualchequeno+"' AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND '"+actualchequeno+"'=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.PAYMENT_TYPE='CHQ' AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y')";
			this.mytemplate.update(qury);
			LogManager.push("Query for receipt matched col BOTH NOT NULL:==>"+qury+"count-->");
		}
        //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE AMT NOT NULL
		//Query for not matched actual cheque amt:
		String queryvalues2="SELECT ACTUAL_CHEQUE_AMT FROM "+tableName[k]+" WHERE ACTUAL_CHEQUE_NO IS NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
		List values2=this.mytemplate.queryForList(queryvalues2);
		for(int i=0;i<values2.size();i++)
		{
			LogManager.push("values " + values2.get(i));
			final Map tempMap1 = (Map) values2.get(i);
			//final String actualchequeno=((String)tempMap1.get("ACTUAL_CHEQUE_NO"));
			final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT").toString());
			LogManager.push("actualchequeamt"+actualchequeamt);
			//Query to match in bank table
			String qury="UPDATE "+tableName[k]+" B SET RECEIPT_SL_NO=(SELECT MIN(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE B."+chequeNo[k]+"=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
			this.mytemplate.update(qury);
			LogManager.push("Query for bank matched col CHEQUE AMT NOT NULL:==>"+qury+"count-->");
			
			//query to match in receipt
			qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName[k]+" C WHERE C."+chequeNo[k]+"=R.CHEQUE_NO AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO IS NULL AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' ";
			this.mytemplate.update(qury);
			LogManager.push("Query for receipt matched col CHEQUE AMT NOT NULL:==>"+qury+"count-->");			
		}
		 //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE NO NOT NULL
		//Query for not matched actual cheque no:
		String queryvalues3="SELECT ACTUAL_CHEQUE_NO FROM "+tableName[k]+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS  NULL AND RECEIPT_SL_NO IS NULL";
		List values3=this.mytemplate.queryForList(queryvalues3);
		for(int i=0;i<values3.size();i++)
		{
			LogManager.push("values " +values3.get(i));
			final Map tempMap1 = (Map) values3.get(i);
			final String actualchequeno=((String)tempMap1.get("ACTUAL_CHEQUE_NO"));
			//final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT"));
			LogManager.push("actualchequeno"+actualchequeno);
			//Query to match in bank table
			String qury="UPDATE "+tableName[k]+" B SET RECEIPT_SL_NO=(SELECT MIN(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND B."+chequeAmt[k]+"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT IS NULL";
			this.mytemplate.update(qury);
			LogManager.push("Query for bank matched col CHEQUE NO NOT NULL:==>"+qury+"count-->");
			
			//query to match in receipt
			qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName[k]+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND C."+chequeAmt[k]+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO= '"+actualchequeno+"' AND C.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND '"+actualchequeno+"'=R.CHEQUE_NO AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ'";
			this.mytemplate.update(qury);
			LogManager.push("Query for receipt matched col CHEQUE NO NOT NULL:==>"+qury+"count-->");
		}
	 }
		//end
			/*for(int k=0;k<listbank.size();k++)
			{
			bankIds[k]=listbank.get(k).toString();
			LogManager.push("bank"+i+":"+listbank.get(k).toString());
			List blist=getBankQueryData(listbank.get(k).toString());
			final Map tempMap = (Map)blist.get(0);
			 tableName[k]=((String)tempMap.get("TABLE_NAME"));
			 chequeStatus[k]=((String)tempMap.get("CHEQUE_STATUS"));
			 reason[k]=((String)tempMap.get("REASON"));
			 chequeNo[k]=((String)tempMap.get("CHEQUE_NO"));
		 	 chequeAmt[k]=((String)tempMap.get("CHEQUE_AMT"));
		     bankName[k]=(String)tempMap.get("BANK_NAME");
			 receipt[k]=((String)tempMap.get("RECEIPT_NO"));
			 LogManager.push("<--"+tableName[k]+""+chequeStatus[k]+"-->");
			// sbean.setBankName((String)tempMap.get("BANK_NAME"));
			}
			final String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[0]+",B."+chequeAmt[0]+",B."+reason[0]+",B."+chequeStatus[0]+",B.RECEIPT_SL_NO,B.DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[0]+")) AS REJECTION_TYPE,"+receipt[0]+"  FROM "+tableName[0]+" B WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO IS NOT NULL UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[1]+",B."+chequeAmt[1]+",B."+reason[1]+",B."+chequeStatus[1]+",B.RECEIPT_SL_NO,B.DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[1]+")) AS REJECTION_TYPE,"+receipt[1]+"  FROM "+tableName[1]+" B WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO  IS NOT NULL";
			LogManager.push("Query for BANK LIST matched col:==>"+query+"count-->");
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
					SearchVB sVB = new SearchVB();
					sVB.setChequeNo(rset.getString(chequeNo[0]));
					sVB.setChequeAmount(rset.getString(chequeAmt[0]));
					//System.out.print(rset.getString(chequeStatus).trim()+"-");
					if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("C"))
					{
						sVB.setRealisation("Realized");
					}
					else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("D"))
					{
						sVB.setRealisation("Returned");
					}
					else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("R"))
					{
						sVB.setRealisation("Reversal");
					}
					sVB.setReason(rset.getString(reason[0]));
					sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
					sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
					sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
					sVB.setActualChequeAmount((String)rset.getString("ACTUAL_CHEQUE_AMT").toString());
					sVB.setReceipt(rset.getString(receipt[0]));
					sVB.setBankName(rset.getString("BANK"));
					return sVB;
				}
							
			});*/
			
			
	//}
	//else
	//{
			/*
	List blist=getBankQueryData(bankcode);
	final Map tempMap = (Map) blist.get(0);
	final String tableName=((String)tempMap.get("TABLE_NAME"));
	final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
	final String reason=((String)tempMap.get("REASON"));
	final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
	final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
	final String receipt=((String)tempMap.get("RECEIPT_NO"));
	
	//String qeury1="SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT FROM "+tableName+""
	
	//GETTING COUNTS FOR NO. OF RECORDS MATCHED
	String qury ="SELECT count(*) FROM RECEIPT_MASTER R,"+tableName+" B WHERE B.ACTUAL_CHEQUE_NO=R.CHEQUE_NO AND B.ACTUAL_CHEQUE_AMT=R.AMOUNT AND B.ACTUAL_CHEQUE_NO IS NOT NULL AND B.ACTUAL_CHEQUE_AMT IS NOT NULL  AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' UNION ALL SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",R.RECEIPT_SL_NO,B.DEPOSIT_DATE FROM RECEIPT_MASTER R,"+tableName+" B WHERE B.ACTUAL_CHEQUE_NO=R.CHEQUE_NO AND B.ACTUAL_CHEQUE_AMT=R.AMOUNT AND B."+chequeNo+" IS  NULL AND B.ACTUAL_CHEQUE_AMT IS NOT NULL  AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' UNION ALL SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",R.RECEIPT_SL_NO,B.DEPOSIT_DATE FROM RECEIPT_MASTER R,"+tableName+" B WHERE B.ACTUAL_CHEQUE_NO=R.CHEQUE_NO AND B."+chequeAmt+"=R.AMOUNT AND B.ACTUAL_CHEQUE_NO IS NOT NULL AND B.ACTUAL_CHEQUE_AMT IS NULL  AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ'";
	int count=this.mytemplate.queryForInt(qury);
	LogManager.push("Query for AFTER CHANGE matched col:==>"+qury+"count-->"+count);
	
	
	//UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN BOTH FIELDS NOT NULL
	//Query for not matched actual cheque no and amt:
	String queryvalues="SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT FROM "+tableName+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
	LogManager.push("Query to get list for both not null:"+queryvalues);
	
	List values=this.mytemplate.queryForList(queryvalues);
	for(int i=0;i<values.size();i++)
	{
		LogManager.push(values.get(i));
		final Map tempMap1 = (Map) values.get(i);
		final String actualchequeno=((String)tempMap1.get("ACTUAL_CHEQUE_NO"));
		final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT").toString());
		LogManager.push("actualchequeno"+actualchequeno+"actualchequeamt"+actualchequeamt);
		//Query to find actual cheque exists
		String qury="UPDATE "+tableName+" B SET RECEIPT_SL_NO=(SELECT MIN(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE R.CHEQUE_NO='"+actualchequeno+"' AND R.AMOUNT="+actualchequeamt+" AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
		this.mytemplate.update(qury);
		LogManager.push("Query for bank matched col BOTH NOT NULL:==>"+qury+"count-->");
		
		qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' ";
		this.mytemplate.update(qury);
		LogManager.push("Query for receipt matched col BOTH NOT NULL:==>"+qury+"count-->");
		
	}

    //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE AMT NOT NULL
	//Query for not matched actual cheque amt:
	String queryvalues2="SELECT ACTUAL_CHEQUE_AMT FROM "+tableName+" WHERE ACTUAL_CHEQUE_NO IS NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
	LogManager.push("Query to get list for cheque amt not null:"+queryvalues2);
	
	List values2=this.mytemplate.queryForList(queryvalues2);
	for(int i=0;i<values2.size();i++)
	{
		LogManager.push(values2.get(i));
		final Map tempMap1 = (Map) values2.get(i);
		//final String actualchequeno=((String)tempMap1.get("ACTUAL_CHEQUE_NO"));
		final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT").toString());
		LogManager.push("actualchequeamt"+actualchequeamt);
		//Query to match in bank table
		String qury="UPDATE "+tableName+" B SET RECEIPT_SL_NO=(SELECT MIN(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE B."+chequeNo+"=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
		this.mytemplate.update(qury);
		LogManager.push("Query for bank matched col CHEQUE AMT NOT NULL:==>"+qury+"count-->");
		
		//query to match in receipt
		qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName+" C WHERE C."+chequeNo+"=R.CHEQUE_NO AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO IS NULL AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND "+actualchequeamt+" =R.AMOUNT ";
		this.mytemplate.update(qury);
		LogManager.push("Query for receipt matched col CHEQUE AMT NOT NULL:==>"+qury+"count-->");
		
	}
	
	 //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE NO NOT NULL
	//Query for not matched actual cheque no:
	String queryvalues3="SELECT ACTUAL_CHEQUE_NO FROM "+tableName+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS  NULL AND RECEIPT_SL_NO IS NULL";
	LogManager.push("Query to get list for cheque no not null:"+queryvalues3);
	List values3=this.mytemplate.queryForList(queryvalues3);
	for(int i=0;i<values3.size();i++)
	{
		LogManager.push(values3.get(i));
		final Map tempMap1 = (Map) values3.get(i);
		final String actualchequeno=((String)tempMap1.get("ACTUAL_CHEQUE_NO"));
		//final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT"));
		LogManager.push("actualchequeno"+actualchequeno);
		//Query to match in bank table
		String qury="UPDATE "+tableName+" B SET RECEIPT_SL_NO=(SELECT MAX(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND B."+chequeAmt+"=R.AMOUNT AND R.BANK_NO IS NULL AND '"+actualchequeno+"'=B.ACTUAL_CHEQUE_NO  AND B.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND '"+actualchequeno+"'=B.ACTUAL_CHEQUE_NO  AND B.ACTUAL_CHEQUE_AMT IS NULL";
		this.mytemplate.update(qury);
		LogManager.push("Query for bank matched col CHEQUE NO NOT NULL:==>"+qury+"count-->");
		
		//query to match in receipt
		qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND C."+chequeAmt+" =R.AMOUNT AND R.BANK_NO IS NULL AND '"+actualchequeno+"'=C.ACTUAL_CHEQUE_NO AND C.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND  '"+actualchequeno+"'=R.CHEQUE_NO";
		this.mytemplate.update(qury);
		LogManager.push("Query for receipt matched col CHEQUE NO NOT NULL:==>"+qury+"count-->");
		
	}
	final String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankcode+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",B.RECEIPT_SL_NO,B.DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason+")) AS REJECTION_TYPE,"+receipt+"  FROM "+tableName+" B WHERE (B.ACTUAL_CHEQUE_NO IS NOT NULL OR B.ACTUAL_CHEQUE_AMT IS NOT NULL ) AND B.RECEIPT_SL_NO IS NOT NULL " ;
	LogManager.push("Query for BANK LIST matched col:==>"+query+"count-->");
	list = this.mytemplate.query(query, new RowMapper() {
		public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			SearchVB sVB = new SearchVB();
			sVB.setChequeNo(rset.getString(chequeNo));
			sVB.setChequeAmount((String)rset.getString(chequeAmt).toString());
			//System.out.print(rset.getString(chequeStatus).trim()+"-");
			if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
			{
				sVB.setRealisation("Realized");
			}
			else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
			{
				sVB.setRealisation("Returned");
			}
			else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
			{
				sVB.setRealisation("Reversal");
			}
			sVB.setReason(rset.getString(reason));
			sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
			sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
			sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
			sVB.setActualChequeAmount(rset.getString("ACTUAL_CHEQUE_AMT"));
			sVB.setReceipt(rset.getString(receipt));
			sVB.setBankName(rset.getString("BANK"));
			return sVB;
		}
					
	});
	*/
			//}
	try
	{
	String updatestat="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN C.STATUS IS NULL THEN TYPE_CRDR ELSE STATUS END) AS TYPE_CRDR FROM CITI_BANK C WHERE R.BANK_NO=C.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL  AND (C.ACTUAL_CHEQUE_NO IS NOT NULL OR C.ACTUAL_CHEQUE_AMT IS NOT NULL )) where r.cheque_status is null and r.bank_no is not null and r.bank_code in (select bank_account_code from bank_account_code where bank_id='CIT')";
	LogManager.push("Update for  status in receipt:"+ updatestat);
	//Runner.updation(updatestat);
	int i=this.mytemplate.update(updatestat);
	LogManager.push("i" + i);
	updatestat="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN C.STATUS IS NULL THEN DR_CR ELSE STATUS END) AS DR_CR FROM HDFC_BANK C WHERE R.BANK_NO=C.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL AND (C.ACTUAL_CHEQUE_NO IS NOT NULL OR C.ACTUAL_CHEQUE_AMT IS NOT NULL )) where r.cheque_status is null and r.bank_no is not null and r.bank_code in (select bank_account_code from bank_account_code where bank_id='HDB')";
	LogManager.push("Update for  status in receipt:"+ updatestat);
	i=this.mytemplate.update(updatestat);
	LogManager.push("i" + i);
	
	updatestat="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN C.STATUS IS NULL THEN CR_DR ELSE STATUS END) AS CR_DR FROM SCB_BANK C WHERE R.BANK_NO=C.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL AND (C.ACTUAL_CHEQUE_NO IS NOT NULL OR C.ACTUAL_CHEQUE_AMT IS NOT NULL )) where r.cheque_status is null and r.bank_no is not null and r.bank_code in (select bank_account_code from bank_account_code where bank_id='SCB')";
	LogManager.push("Update for  status in receipt:"+ updatestat);
	i=this.mytemplate.update(updatestat);
	LogManager.push("i" + i);
	
	updatestat="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN C.STATUS IS NULL THEN TYPE ELSE STATUS END) AS TYPE FROM AXIS_BANK C WHERE R.BANK_NO=C.BANK_NO AND C.RECEIPT_SL_NO IS NOT NULL AND (C.ACTUAL_CHEQUE_NO IS NOT NULL OR C.ACTUAL_CHEQUE_AMT IS NOT NULL )) where r.cheque_status is null and r.bank_no is not null and r.bank_code in (select bank_account_code from bank_account_code where bank_id='AXB')";
	LogManager.push("Update for  status in receipt:"+ updatestat);
	i=this.mytemplate.update(updatestat);
	LogManager.push("i" + i);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	//REMOVE AFTER OPERATION
	//qury="UPDATE "+tableName+" SET ACTUAL_CHEQUE_NO=NULL,ACTUAL_CHEQUE_AMT=NULL WHERE RECEIPT_SL_NO IS NULL";
	//this.mytemplate.update(qury);
	//LogManager.push("Query for receipt matched col:==>"+qury+"count-->");
	LogManager.push("Exit update of actual process matched list");
	

}


 List actualcurrent(SearchFormBean sbean) throws CommonBaseException {

    LogManager.push("Inside actualcurrentMatched:");
	String query="";
	String query2="";
	String query3="";
	String bankcode=sbean.getBankId();
	String searchIn=sbean.getSearchIn();
	String formChequeNo=sbean.getChequeNo();
	String formreceipt=sbean.getReceiptNo();
	String transId=sbean.getTransactionNo();
	LogManager.push("getSearchList search in:"+searchIn);
	String formChequeAmt=sbean.getChequeAmount();
	
	String realise=sbean.getRealised();
	String receiptrev="RECEIPT_SL_NO!=-99999 AND ";
	if(realise.equalsIgnoreCase("yes")){
		realise="NOT";
		
	}else 
	{
		realise="";
		receiptrev="";
		
	}
	
	//String tableName=String.valueOf(val.get("TABLE_NAME"));
    List list=null;
    LogManager.push("formchequeno:"+formChequeNo);
	
	try{
	
       //SEARCH BY VALUES GIVEN
		
		
		//Search in all banks	
		if(bankcode.equalsIgnoreCase("all"))
		{
			String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
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
				String[] arg = new String[15];
				LogManager.push("inside blocks::::");
				//Search in bank statement 
			if(searchIn.equalsIgnoreCase("Bank"))
			{	
				
				if(sbean.getSearchFor().equalsIgnoreCase("exact")){
					if(!formChequeNo.equalsIgnoreCase("") )
					{
						arg[0]="AND "+chequeNo[0]+"='"+formChequeNo+"' ";
						arg[3]="AND "+chequeNo[1]+"='"+formChequeNo+"' ";
						arg[6]="AND "+chequeNo[2]+"='"+formChequeNo+"' ";
						arg[9]="AND "+chequeNo[3]+"='"+formChequeNo+"' ";
						arg[12]="AND "+chequeNo[4]+"='"+formChequeNo+"' ";
					 }
					else if(formChequeNo.equalsIgnoreCase(""))
					{
						arg[0]="";
						arg[3]="";
						arg[6]="";
						arg[9]="";
						arg[12]="";
					}
					if(!formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="AND "+"trunc("+chequeAmt[0]+")"+"="+"trunc("+formChequeAmt+") ";
						arg[4]="AND "+"trunc("+chequeAmt[1]+")"+"="+"trunc("+formChequeAmt+") ";
						arg[7]="AND "+"trunc("+chequeAmt[2]+")"+"="+"trunc("+formChequeAmt+") ";
						arg[10]="AND "+"trunc("+chequeAmt[3]+")"+"="+"trunc("+formChequeAmt+") ";
						arg[13]="AND "+"trunc("+chequeAmt[4]+")"+"="+"trunc("+formChequeAmt+") ";
					}
					else if (formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="";
						arg[4]="";
						arg[7]="";
						arg[10]="";
						arg[13]="";
					}
					if(!formreceipt.equalsIgnoreCase(""))
					{
						arg[2]="AND "+receipt[0]+"='"+formreceipt+"' ";
						arg[5]="AND "+receipt[1]+"='"+formreceipt+"' ";
						arg[8]="AND "+receipt[2]+"='"+formreceipt+"' ";
						arg[11]="AND "+receipt[3]+"='"+formreceipt+"' ";
						arg[14]="AND "+receipt[4]+"='"+formreceipt+"' ";
					 }
					else if(formreceipt.equalsIgnoreCase(""))
					{
						arg[2]="";
						arg[5]="";
						arg[8]="";
						arg[11]="";
						arg[14]="";
					 }
					query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[0]+"') AS BANK,"+chequeNo[0]+", "+chequeAmt[0]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[0]+" ELSE STATUS END) AS "+chequeStatus[0]+","+reason[0]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[0]+")) AS REJECTION_TYPE,"+receipt[0]+" FROM "+tableName[0]+" T WHERE RECEIPT_SL_NO!=-99999 AND (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+"AND "+chequeStatus[0]+" NOT LIKE 'H' " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[1]+"') AS BANK,"+chequeNo[1]+","+chequeAmt[1]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[1]+" ELSE STATUS END) AS "+chequeStatus[1]+","+reason[1]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[1]+")) AS REJECTION_TYPE,"+receipt[1]+" FROM "+tableName[1]+" T WHERE RECEIPT_SL_NO!=-99999  AND (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "+realise+" NULL "+arg[3]+arg[4]+arg[5]+"AND "+chequeStatus[1]+" NOT LIKE 'H' " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[2]+"') AS BANK,"+chequeNo[2]+","+chequeAmt[2]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[2]+" ELSE STATUS END) AS "+chequeStatus[2]+","+reason[2]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[2]+")) AS REJECTION_TYPE,"+receipt[2]+" FROM "+tableName[2]+" T WHERE RECEIPT_SL_NO!=-99999  AND (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "+realise+" NULL "+arg[6]+arg[7]+arg[8]+"AND "+chequeStatus[2]+" NOT LIKE 'H' " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[3]+"') AS BANK,"+chequeNo[3]+","+chequeAmt[3]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[3]+" ELSE STATUS END) AS "+chequeStatus[3]+","+reason[3]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[3]+")) AS REJECTION_TYPE,"+receipt[3]+" FROM "+tableName[3]+" T WHERE RECEIPT_SL_NO!=-99999  AND (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "+realise+" NULL "+arg[9]+arg[10]+arg[11]+"AND "+chequeStatus[3]+" NOT LIKE 'H' " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[4]+"') AS BANK,"+chequeNo[4]+","+chequeAmt[4]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[4]+" ELSE STATUS END) AS "+chequeStatus[4]+","+reason[4]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[4]+")) AS REJECTION_TYPE,"+receipt[4]+" FROM "+tableName[4]+" T WHERE RECEIPT_SL_NO!=-99999  AND (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "+realise+" NULL "+arg[12]+arg[13]+arg[14]+"AND "+chequeStatus[4]+" NOT LIKE 'H' " +
									"ORDER BY DEPOSIT_DATE DESC ,"+chequeStatus[0]+" DESC ";
			    }
				else
				{
					arg[0]="'%"+formChequeNo+"%'";
					arg[1]="'"+formChequeAmt+"%'";
					arg[2]="'"+formreceipt+"%'";
					arg[3]="'%"+formChequeNo+"%'";
					arg[4]="'"+formChequeAmt+"%'";
					arg[5]="'"+formreceipt+"%'";
					arg[6]="'%"+formChequeNo+"%'";
					arg[7]="'"+formChequeAmt+"%'";
					arg[8]="'"+formreceipt+"%'";
					arg[9]="'%"+formChequeNo+"%'";
					arg[10]="'"+formChequeAmt+"%'";
					arg[11]="'"+formreceipt+"%'";
					arg[12]="'%"+formChequeNo+"%'";
					arg[13]="'"+formChequeAmt+"%'";
					arg[14]="'"+formreceipt+"%'";
					query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
								+ tableName[0]
								+ "') AS BANK,"
								+ chequeNo[0]
								+ ", "
								+ chequeAmt[0]
								+ ",(CASE WHEN STATUS IS NULL THEN "
								+ chequeStatus[0]
								+ " ELSE STATUS END) AS "
								+ chequeStatus[0]
								+ ","
								+ reason[0]
								+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
								+ reason[0]
								+ ")) AS REJECTION_TYPE,"
								+ receipt[0]
								+ " FROM "
								+ tableName[0]
								+ " T WHERE RECEIPT_SL_NO!=-99999 AND (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "
								+ realise
								+ " NULL AND "
								+ chequeNo[0]
								+ " LIKE "
								+ arg[0]
								+ " AND "
								+ chequeAmt[0]
								+ " LIKE "
								+ arg[1]
								+ " AND "
								+ receipt[0]
								+ " LIKE "
								+ arg[2]
								+ " AND "
								+ chequeStatus[0]
								+ " NOT LIKE 'H' " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
								+ tableName[1]
								+ "') AS BANK,"
								+ chequeNo[1]
								+ ","
								+ chequeAmt[1]
								+ ",(CASE WHEN STATUS IS NULL THEN "
								+ chequeStatus[1]
								+ " ELSE STATUS END) AS "
								+ chequeStatus[1]
								+ ","
								+ reason[1]
								+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
								+ reason[1]
								+ ")) AS REJECTION_TYPE,"
								+ receipt[1]
								+ " FROM "
								+ tableName[1]
								+ " T WHERE RECEIPT_SL_NO!=-99999 AND (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "
								+ realise
								+ " NULL AND "
								+ chequeNo[1]
								+ " LIKE "
								+ arg[3]
								+ " AND "
								+ chequeAmt[1]
								+ " LIKE "
								+ arg[4]
								+ " AND "
								+ receipt[1]
								+ " LIKE "
								+ arg[5]
								+ " AND "
								+ chequeStatus[1]
								+ " NOT LIKE 'H' " +
							" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
								+ tableName[2]
								+ "') AS BANK,"
								+ chequeNo[2]
								+ ","
								+ chequeAmt[2]
								+ ",(CASE WHEN STATUS IS NULL THEN "
								+ chequeStatus[2]
								+ " ELSE STATUS END) AS "
								+ chequeStatus[2]
								+ ","
								+ reason[2]
								+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
								+ reason[2]
								+ ")) AS REJECTION_TYPE,"
								+ receipt[2]
								+ " FROM "
								+ tableName[2]
								+ " T WHERE RECEIPT_SL_NO!=-99999 AND (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "
								+ realise
								+ " NULL AND "
								+ chequeNo[2]
								+ " LIKE "
								+ arg[6]
								+ " AND "
								+ chequeAmt[2]
								+ " LIKE "
								+ arg[7]
								+ " AND "
								+ receipt[2]
								+ " LIKE "
								+ arg[8]
								+ " AND "
								+ chequeStatus[2]
								+ " NOT LIKE 'H' " +
							
						" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
								+ tableName[3]
								+ "') AS BANK,"
								+ chequeNo[3]
								+ ","
								+ chequeAmt[3]
								+ ",(CASE WHEN STATUS IS NULL THEN "
								+ chequeStatus[3]
								+ " ELSE STATUS END) AS "
								+ chequeStatus[3]
								+ ","
								+ reason[3]
								+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
								+ reason[3]
								+ ")) AS REJECTION_TYPE,"
								+ receipt[3]
								+ " FROM "
								+ tableName[3]
								+ " T WHERE RECEIPT_SL_NO!=-99999 AND (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "
								+ realise
								+ " NULL AND "
								+ chequeNo[3]
								+ " LIKE "
								+ arg[9]
								+ " AND "
								+ chequeAmt[3]
								+ " LIKE "
								+ arg[10]
								+ " AND "
								+ receipt[3]
								+ " LIKE "
								+ arg[11]
								+ " AND "
								+ chequeStatus[3]
								+ " NOT LIKE 'H' " +
										
						  " UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
								+ tableName[4]
								+ "') AS BANK,"
								+ chequeNo[4]
								+ ","
								+ chequeAmt[4]
								+ ",(CASE WHEN STATUS IS NULL THEN "
								+ chequeStatus[4]
								+ " ELSE STATUS END) AS "
								+ chequeStatus[4]
								+ ","
								+ reason[4]
								+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
								+ reason[4]
								+ ")) AS REJECTION_TYPE,"
								+ receipt[4]
								+ " FROM "
								+ tableName[4]
								+ " T WHERE RECEIPT_SL_NO!=-99999 AND (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "
								+ realise
								+ " NULL AND "
								+ chequeNo[4]
								+ " LIKE "
								+ arg[12]
								+ " AND "
								+ chequeAmt[4]
								+ " LIKE "
								+ arg[13]
								+ " AND "
								+ receipt[4]
								+ " LIKE "
								+ arg[14]
								+ " AND "
								+ chequeStatus[4]
								+ " NOT LIKE 'H' " +
								
								"ORDER BY 4"
								+ " DESC , 8 DESC";
					
				}
				LogManager.push("Query"+query);
				LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
				list = this.mytemplate.query(query,new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						sVB.setBankName(rset.getString(1));
						sVB.setChequeNo(rset.getString(2));
						sVB.setChequeAmount(rset.getString(3));
						//System.out.print(rset.getString(chequeStatus).trim()+"-");
						if(rset.getString(4).trim().equalsIgnoreCase("C"))
						{
							sVB.setRealisation("Realized");
						}
						else if(rset.getString(4).trim().equalsIgnoreCase("D"))
						{
							sVB.setRealisation("Returned");
						}
						else 
						{
							sVB.setRealisation("Not Known");
						}
						if(rset.getString(11)!=null){
							sVB.setReason(rset.getString(5)+"("+rset.getString(11)+")");
						}
						else
						{
							sVB.setReason(rset.getString(5));
							
						}
						//sVB.setBankName(bankName);
						sVB.setReceiptNo(rset.getString(6));
						sVB.setBankNo1(rset.getString(7));
						sVB.setBankNo2(rset.getString(7));
						sVB.setDepositDate(rset.getString(8));
						sVB.setActualChequeAmount(rset.getString(10));
						sVB.setActualChequeNo(rset.getString(9));
						sVB.setReceipt(rset.getString(12));
						
						return sVB;
					}
								
				});
			}
			
			//end  for both receipt & bank
			
			
			
		}	
		//------end-------------
				
		
		//not searched for all banks
		else
		{
				LogManager.push("Inside search code");
				String[] arg = new String[3];
				LogManager.push("inside blocks::::");
				List blist=getBankQueryData(bankcode);
				final Map tempMap = (Map) blist.get(0);
				final String tableName=((String)tempMap.get("TABLE_NAME"));
				final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
				final String reason=((String)tempMap.get("REASON"));
				final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
				final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
				final String bankName=(String)tempMap.get("BANK_NAME");
				final String receipt=((String)tempMap.get("RECEIPT_NO"));
				
				sbean.setBankName((String)tempMap.get("BANK_NAME"));
				//Search in bank statement 
			if(searchIn.equalsIgnoreCase("Bank"))
			{	
				
				if(sbean.getSearchFor().equalsIgnoreCase("exact")){
				if(!formChequeNo.equalsIgnoreCase("") )
				{
					arg[0]=" AND "+chequeNo+"='"+formChequeNo+"' ";
				 }
				else if(formChequeNo.equalsIgnoreCase(""))
				{
					arg[0]="";
				}
				if(!formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]="AND "+"trunc("+chequeAmt+")=trunc("+formChequeAmt+") ";
				}
				else if (formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]="";
				}
				if(!formreceipt.equalsIgnoreCase(""))
				{
					arg[2]="AND "+receipt+"='"+formreceipt+"' ";
				 }
				else if(formreceipt.equalsIgnoreCase(""))
				{
					arg[2]=" ";
				 } 
				query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+" FROM "+tableName+" T WHERE RECEIPT_SL_NO !=-99999 AND  (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND "+chequeStatus+" NOT LIKE 'H' ORDER BY 7 DESC, "+chequeStatus+" DESC";
			    }
				else
				{
					arg[0]="'%"+formChequeNo+"%'";
					arg[1]="'"+formChequeAmt+"%'";
					arg[2]="'"+formreceipt+"%'";
					query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+" FROM "+tableName+" T WHERE RECEIPT_SL_NO !=-99999  AND (ACTUAL_CHEQUE_NO IS NOT NULL OR  ACTUAL_CHEQUE_AMT IS NOT NULL )AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo+" LIKE "+arg[0]+" AND "+chequeAmt+" LIKE "+arg[1]+" AND "+receipt+" LIKE "+arg[2]+" AND "+chequeStatus+" NOT LIKE 'H' ORDER BY 7 DESC, "+chequeStatus+" DESC";
				}
				LogManager.push("Query"+query);
				LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
				list = this.mytemplate.query(query,new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						sVB.setBankName(bankName);
						sVB.setChequeNo(rset.getString(chequeNo));
						sVB.setChequeAmount(rset.getString(chequeAmt));
						//System.out.print(rset.getString(chequeStatus).trim()+"-");
						if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
						{
							sVB.setRealisation("Realized");
						}
						else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
						{
							sVB.setRealisation("Returned");
						}
						else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
						{
							sVB.setRealisation("Reversal");
						}
						else 
						{
							sVB.setRealisation("Not Known");
						}
						if(rset.getString("REJECTION_TYPE")!=null){
							sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
						}
						else
						{
							sVB.setReason(rset.getString(reason));
							
						}
						sVB.setBankName(bankName);
						sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
						sVB.setBankNo1(rset.getString("BANK_NO"));
						sVB.setBankNo2(rset.getString("BANK_NO"));
						sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
						sVB.setActualChequeAmount(rset.getString("ACTUAL_CHEQUE_AMT"));
						sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
						sVB.setReceipt(rset.getString(receipt));
						
						return sVB;
					}
								
				});
			}
			//start both receipt & bank 
			if(searchIn.equalsIgnoreCase("Both"))
			{	
				
				if(sbean.getSearchFor().equalsIgnoreCase("exact")){
				if(!formChequeNo.equalsIgnoreCase("") )
				{
					arg[0]=" AND "+chequeNo+"='"+formChequeNo+"' ";
				 }
				else if(formChequeNo.equalsIgnoreCase(""))
				{
					arg[0]="";
				}
				if(!formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]="AND "+"trunc("+chequeAmt+")=trunc("+formChequeAmt+") ";
				}
				else if (formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]="";
				}
				if(!formreceipt.equalsIgnoreCase(""))
				{
					arg[2]="AND "+receipt+"='"+formreceipt+"' ";
				 }
				else if(formreceipt.equalsIgnoreCase(""))
				{
					arg[2]=" ";
				 } 
				query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+" ,'Bank' AS FROM_TABLE FROM "+tableName+" T WHERE RECEIPT_SL_NO !=-99999  AND RECEIPT_SL_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND "+chequeStatus+" NOT LIKE 'H' ";
			    }
				else
				{
					arg[0]="'%"+formChequeNo+"%'";
					arg[1]="'"+formChequeAmt+"%'";
					arg[2]="'"+formreceipt+"%'";
					query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+" ,'Bank' AS FROM_TABLE FROM "+tableName+" T WHERE RECEIPT_SL_NO !=-99999  AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo+" LIKE "+arg[0]+" AND "+chequeAmt+" LIKE "+arg[1]+" AND "+receipt+" LIKE "+arg[2]+" AND "+chequeStatus+" NOT LIKE 'H' ";
				}
				LogManager.push("Query"+query);
				LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
				//receipt starts 
				if(sbean.getSearchFor().equalsIgnoreCase("exact")){
					if(!formChequeNo.equalsIgnoreCase("") )
					{
						arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"'";
					 }
					else if(formChequeNo.equalsIgnoreCase(""))
					{
						arg[0]=" ";
					}
					if(!formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]=" AND trunc(R.AMOUNT)=trunc('"+formChequeAmt+"')";
					}
					else if (formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]=" ";
					}
					if(!formreceipt.equalsIgnoreCase(""))
					{
						arg[2]=" AND RECEIPT_NO='"+formreceipt+"'";
					 }
					else if(formreceipt.equalsIgnoreCase(""))
					{
						arg[2]=" ";
					 }
					
					//query2="UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID=(SELECT BANK_ID FROM BANK_ACCOUNT_CODE WHERE BANK_ACCOUNT_CODE=R.BANK_CODE)) AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY R.RECEIPT_SL_NO";
					query2="UNION ALL SELECT R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,'',0,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888  AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 7 DESC, 1";	
				}
				
				else
				{
				arg[0]="'%"+formChequeNo+"%'";
				arg[1]="'"+formChequeAmt+"%'";
				arg[2]="'"+formreceipt+"%'";
				query2=" UNION ALL SELECT R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,'',0,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE FROM RECEIPT_MASTER R WHERE R.BANK_NO !=-88888  AND R.BANK_NO IS "+realise+" NULL AND (R.CHEQUE_NO LIKE "+arg[0]+" or r.cheque_no is null) AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 7 DESC,1";
				}
				//receipt ends 
				query=query+query2;
				list = this.mytemplate.query(query,new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						sVB.setBankName(bankName);
						sVB.setChequeNo(rset.getString(chequeNo));
						sVB.setChequeAmount(rset.getString(chequeAmt));
						//System.out.print(rset.getString(chequeStatus).trim()+"-");
						if(rset.getString(chequeStatus)!=null){
						if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
						{
							sVB.setRealisation("Realized");
						}
						else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
						{
							sVB.setRealisation("Returned");
						}
						else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
						{
							sVB.setRealisation("Reversal");
						}
						}
						else
						{
							sVB.setRealisation("Not known");
						}
						if(rset.getString("REJECTION_TYPE")!=null){
							sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
						}
						else
						{
							sVB.setReason(rset.getString(reason));
							
						}
						sVB.setBankName(bankName);
						sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
						sVB.setBankNo1(rset.getString("BANK_NO"));
						sVB.setBankNo2(rset.getString("BANK_NO"));
						sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
						//sVB.setActualChequeAmount(rset.getString("ACTUAL_CHEQUE_AMT"));
						//sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
						sVB.setReceipt(rset.getString(receipt));
						sVB.setFrom(rset.getString("FROM_TABLE"));
						return sVB;
					}
								
				});
			}
			//ends both receipt & bank
			//Search in Receipt statement			
			}	
	}
	catch(Exception e)
	{
		LogManager.push("Exception In SearchDAOImpl - searchResult searchdao(): "+e);
		e.printStackTrace();
	}
	LogManager.push("QUERY executed "+query);
	return list; 
}
 
public void deleteTemp(String sid) throws CommonBaseException {
	String deltemp="DELETE FROM TEMP_NONMATCHED WHERE SESSION_ID='"+sid+"'";
	Runner.updation(deltemp);
}


public List getReceiptReversals(String transid) throws CommonBaseException {
	List list=null;

	String  query="SELECT CHEQUE_NO, CHEQUE_DATE, RECEIPT_NO, TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE, AMOUNT, (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID=(SELECT UNIQUE BANK_ID  FROM BANK_ACCOUNT_CODE B WHERE B.BANK_ACCOUNT_CODE=R.BANK_CODE )) BANK_CODE,STATUS FROM RECEIPT_MASTER R WHERE BATCHID='"+transid+"' AND BANK_NO=-88888 ORDER BY 4 DESC,CHEQUE_NO";
	//String query="";  
	LogManager.push("Query executed:"+query);
		
	  list = this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				InvalidVB sVB = new InvalidVB();
				
				sVB.setR_chequeno(rset.getString("CHEQUE_NO"));
				sVB.setR_receiptno(rset.getString("RECEIPT_NO"));
				sVB.setR_receiptdate(rset.getString("RECEIPT_DATE"));
				sVB.setR_amount(rset.getString("AMOUNT"));
				sVB.setR_bankcode(rset.getString("BANK_CODE"));
				sVB.setR_receiptPayment(rset.getString("STATUS")==null?"":rset.getString("STATUS"));
				return sVB;
			}
						
		});
	return list;
}
public List getBankReversals(String transid, String bankid) throws CommonBaseException {
	
	  List list=null; 
	  List blist=getBankQueryData(bankid);
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
		final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
	    final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
	  String  query="";
	  LogManager.push("Query executed:"+query);
	  if(bankid.equalsIgnoreCase("CIT")){
		  query="SELECT TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,CHEQUE_NO,CHEQUE_AMT,DEPSLIPNO FROM "+tableName+" WHERE BATCHID="+transid +" AND RECEIPT_SL_NO=-99999 ORDER BY "+chequeNo;
	  list = this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				InvalidVB sVB = new InvalidVB();
				sVB.setC_depositdate(rset.getString("DEPOSIT_DATE"));
				sVB.setC_chequeno(rset.getString("CHEQUE_NO"));
				sVB.setC_chequeamt(rset.getString("CHEQUE_AMT"));
				sVB.setC_depslipno(rset.getString("DEPSLIPNO"));
				return sVB;
			}						
		});
	  }
	  else if(bankid.equalsIgnoreCase("HDB")){
		  query="SELECT TO_CHAR(POST_DT,'DD/MM/YYYY') POST_DT,INSTRUMENT_NO,INSTRUMENT_AMOUNT,DEPOSIT_SLIP_NO FROM "+tableName+" WHERE BATCHID="+transid +" AND RECEIPT_SL_NO=-99999 ORDER BY "+chequeNo;
		  list = this.mytemplate.query(query, new RowMapper() {
			  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
		      InvalidVB sVB = new InvalidVB();
			  sVB.setH_depositdate(rset.getString("POST_DT"));
			  sVB.setH_depositslipno(rset.getString("DEPOSIT_SLIP_NO"));
			  sVB.setH_instrumentno(rset.getString("INSTRUMENT_NO"));
			  sVB.setH_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
			  return sVB;
			  }
			});
		   }
	  else if(bankid.equalsIgnoreCase("KOT")){
		  query="SELECT TO_CHAR(POST_DT,'DD/MM/YYYY') POST_DT,INSTRUMENT_NO,INSTRUMENT_AMOUNT,DEPOSIT_SLIP_NO FROM "+tableName+" WHERE BATCHID="+transid +" AND RECEIPT_SL_NO=-99999 ORDER BY "+chequeNo;
		  list = this.mytemplate.query(query, new RowMapper() {
			  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
		      InvalidVB sVB = new InvalidVB();
			  sVB.setK_depositdate(rset.getString("POST_DT"));
			  sVB.setK_depositslipno(rset.getString("DEPOSIT_SLIP_NO"));
			  sVB.setK_instrumentno(rset.getString("INSTRUMENT_NO"));
			  sVB.setK_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
			  return sVB;
			  }
			});
		   }
	  else if(bankid.equalsIgnoreCase("AXB")){
		  query="SELECT TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,INST_NO,INSTRUMENT_AMOUNT,SLIP_NO FROM "+tableName+" WHERE BATCHID="+transid +" AND RECEIPT_SL_NO=-99999 ORDER BY "+chequeNo;
			 
		  list = this.mytemplate.query(query, new RowMapper() {
			  public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
		
		      InvalidVB sVB = new InvalidVB();
			
			  sVB.setA_depositdate(rset.getString("DEPOSIT_DATE"));
			  sVB.setA_slipno(rset.getString("SLIP_NO"));
			  sVB.setA_instno(rset.getString("INST_NO"));
			  sVB.setA_instrumentamount(rset.getString("INSTRUMENT_AMOUNT"));
			  return sVB;
				  }
			});
		   }
	  else if(bankid.equalsIgnoreCase("SCB")){
		  query="SELECT TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,CHEQUE_NO,CHQ_AMOUNT,DEPOSIT_NO FROM "+tableName+" WHERE BATCHID="+transid +" AND RECEIPT_SL_NO=-99999 ORDER BY "+chequeNo;
	  list = this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				InvalidVB sVB = new InvalidVB();				
				sVB.setS_depdate(rset.getString("DEPOSIT_DATE"));
				sVB.setS_chequeno(rset.getString("CHEQUE_NO"));
				sVB.setS_chequeamt(rset.getString("CHQ_AMOUNT"));
				sVB.setS_depositno(rset.getString("DEPOSIT_NO"));				
				return sVB;
			}						
		});
	  }
	  
	  return list;
}

public List getReceiptReversal(SearchFormBean sbean) throws CommonBaseException{
    LogManager.push("Inside getReceiptReversal:");
	String query="";
	String query2="";
	String query3="";
	String bankcode=sbean.getBankId();
	String searchIn=sbean.getSearchIn();
	String formChequeNo=sbean.getChequeNo();
	String formreceipt=sbean.getReceiptNo();
	String transId=sbean.getTransactionNo();
	LogManager.push("getReceiptReversal search in:"+searchIn);
	String formChequeAmt=sbean.getChequeAmount();	
	String realise=sbean.getRealised();
	String receiptrev="BANK_NO!=-88888 AND ";
	if(realise.equalsIgnoreCase("yes")){
		realise="NOT";		
	}else 
	{
		realise="";
		receiptrev="";
	}
	//String tableName=String.valueOf(val.get("TABLE_NAME"));
    List list=null;
    LogManager.push("transaction code"+transId+"formchequeno:"+formChequeNo);	
	try{
		LogManager.push("transaction code"+transId+"formchequeno:"+formChequeNo);
		//SEARCH BY TRANSACTIONS		
       //SEARCH BY VALUES GIVEN
		//Search in all banks	
		if(bankcode.equalsIgnoreCase("all"))
		{
			String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
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
				String[] arg = new String[15];
				LogManager.push("inside blocks::::");
				//Search in bank statement 
			
			//start for both receipt & bank  
			 if(searchIn.equalsIgnoreCase("Both"))
			{	
				
				if(sbean.getSearchFor().equalsIgnoreCase("exact")){
					if(!formChequeNo.equalsIgnoreCase("") )
					{
						arg[0]="AND "+chequeNo[0]+"='"+formChequeNo+"' ";
						arg[3]="AND "+chequeNo[1]+"='"+formChequeNo+"' ";
						arg[6]="AND "+chequeNo[2]+"='"+formChequeNo+"' ";
						arg[9]="AND "+chequeNo[3]+"='"+formChequeNo+"' ";
						arg[12]="AND "+chequeNo[4]+"='"+formChequeNo+"' ";
					 }
					else if(formChequeNo.equalsIgnoreCase(""))
					{
						arg[0]="";
						arg[3]="";
						arg[6]="";
						arg[9]="";
						arg[12]="";
					}
					if(!formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="AND "+"trunc("+chequeAmt[0]+")"+"="+"trunc("+formChequeAmt+") ";
						arg[4]="AND "+"trunc("+chequeAmt[1]+")"+"="+"trunc("+formChequeAmt+") ";
						arg[7]="AND "+"trunc("+chequeAmt[2]+")"+"="+"trunc("+formChequeAmt+") ";
						arg[10]="AND "+"trunc("+chequeAmt[3]+")"+"="+"trunc("+formChequeAmt+") ";
						arg[13]="AND "+"trunc("+chequeAmt[4]+")"+"="+"trunc("+formChequeAmt+") ";
					}
					else if (formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="";
						arg[4]="";
						arg[7]="";
						arg[10]="";
						arg[13]="";
					}
					if(!formreceipt.equalsIgnoreCase(""))
					{
						arg[2]="AND "+receipt[0]+"='"+formreceipt+"' ";
						arg[5]="AND "+receipt[1]+"='"+formreceipt+"' ";
						arg[8]="AND "+receipt[2]+"='"+formreceipt+"' ";
						arg[11]="AND "+receipt[3]+"='"+formreceipt+"' ";
						arg[14]="AND "+receipt[4]+"='"+formreceipt+"' ";
					 }
					else if(formreceipt.equalsIgnoreCase(""))
					{
						arg[2]="";
						arg[5]="";
						arg[8]="";
						arg[11]="";
						arg[14]="";
					 }
					query = "SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
								+ tableName[0]
								+ "') AS BANK,"
								+ chequeNo[0]
								+ ", "
								+ chequeAmt[0]
								+ ",(CASE WHEN STATUS IS NULL THEN "
								+ chequeStatus[0]
								+ " ELSE STATUS END) AS "
								+ chequeStatus[0]
								+ ","
								+ reason[0]
								+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
								+ reason[0]
								+ ")) AS REJECTION_TYPE,"
								+ receipt[0]
								+ ",'Bank' AS FROM_TABLE,'' status FROM "
								+ tableName[0]
								+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
								+ realise
								+ " NULL "
								+ arg[0]
								+ arg[1]
								+ arg[2]
								+ "AND "
								+ chequeStatus[0]
								+ " NOT LIKE 'H' UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
								+ tableName[1]
								+ "') AS BANK,"
								+ chequeNo[1]
								+ ","
								+ chequeAmt[1]
								+ ",(CASE WHEN STATUS IS NULL THEN "
								+ chequeStatus[1]
								+ " ELSE STATUS END) AS "
								+ chequeStatus[1]
								+ ","
								+ reason[1]
								+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
								+ reason[1]
								+ ")) AS REJECTION_TYPE,"
								+ receipt[1]
								+ " ,'Bank' AS FROM_TABLE,'' status FROM "
								+ tableName[1]
								+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
								+ realise
								+ " NULL "
								+ arg[3]
								+ arg[4]
								+ arg[5]
								+ "AND "
								+ chequeStatus[1]
								+ " NOT LIKE 'H' " +
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[2]
									+ "') AS BANK,"
									+ chequeNo[2]
									+ ","
									+ chequeAmt[2]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[2]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[2]
									+ ","
									+ reason[2]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[2]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[2]
									+ " ,'Bank' AS FROM_TABLE,'' status FROM "
									+ tableName[2]
									+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[6]
									+ arg[7]
									+ arg[8]
									+ "AND "
									+ chequeStatus[2]
									+ " NOT LIKE 'H'" +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[3]
									+ "') AS BANK,"
									+ chequeNo[3]
									+ ","
									+ chequeAmt[3]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[3]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[3]
									+ ","
									+ reason[3]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[3]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[3]
									+ " ,'Bank' AS FROM_TABLE,'' status FROM "
									+ tableName[3]
									+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[9]
									+ arg[10]
									+ arg[11]
									+ "AND "
									+ chequeStatus[3]
									+ " NOT LIKE 'H'"+
								"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"
									+ tableName[4]
									+ "') AS BANK,"
									+ chequeNo[4]
									+ ","
									+ chequeAmt[4]
									+ ",(CASE WHEN STATUS IS NULL THEN "
									+ chequeStatus[4]
									+ " ELSE STATUS END) AS "
									+ chequeStatus[4]
									+ ","
									+ reason[4]
									+ ",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."
									+ reason[4]
									+ ")) AS REJECTION_TYPE,"
									+ receipt[4]
									+ " ,'Bank' AS FROM_TABLE,'' status FROM "
									+ tableName[4]
									+ " T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "
									+ realise
									+ " NULL "
									+ arg[12]
									+ arg[13]
									+ arg[14]
									+ "AND "
									+ chequeStatus[4]
									+ " NOT LIKE 'H'";

				
			    }
				else
				{
					arg[0]="'%"+formChequeNo+"%'";
					arg[1]="'"+formChequeAmt+"%'";
					arg[2]="'"+formreceipt+"%'";
					arg[3]="'%"+formChequeNo+"%'";
					arg[4]="'"+formChequeAmt+"%'";
					arg[5]="'"+formreceipt+"%'";
					arg[6]="'%"+formChequeNo+"%'";
					arg[7]="'"+formChequeAmt+"%'";
					arg[8]="'"+formreceipt+"%'";
					arg[9]="'%"+formChequeNo+"%'";
					arg[10]="'"+formChequeAmt+"%'";
					arg[11]="'"+formreceipt+"%'";
					arg[12]="'%"+formChequeNo+"%'";
					arg[13]="'"+formChequeAmt+"%'";
					arg[14]="'"+formreceipt+"%'";
					query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[0]+"') AS BANK,"+chequeNo[0]+", "+chequeAmt[0]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[0]+" ELSE STATUS END) AS "+chequeStatus[0]+","+reason[0]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[0]+")) AS REJECTION_TYPE,"+receipt[0]+",'Bank' AS FROM_TABLE,'' status FROM "+tableName[0]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[0]+" LIKE "+arg[0]+" AND "+chequeAmt[0]+" LIKE "+arg[1]+" AND "+receipt[0]+" LIKE "+arg[2]+" AND "+chequeStatus[0]+" NOT LIKE 'H' " +
							" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[1]+"') AS BANK,"+chequeNo[1]+","+chequeAmt[1]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[1]+" ELSE STATUS END) AS "+chequeStatus[1]+","+reason[1]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[1]+")) AS REJECTION_TYPE,"+receipt[1]+" ,'Bank','' status AS FROM_TABLE FROM "+tableName[1]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[1]+" LIKE "+arg[3]+" AND "+chequeAmt[1]+" LIKE "+arg[4]+" AND "+receipt[1]+" LIKE "+arg[5]+" AND "+chequeStatus[1]+" NOT LIKE 'H' " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[2]+"') AS BANK,"+chequeNo[2]+","+chequeAmt[2]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[2]+" ELSE STATUS END) AS "+chequeStatus[2]+","+reason[2]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[2]+")) AS REJECTION_TYPE,"+receipt[2]+" ,'Bank','' status AS FROM_TABLE FROM "+tableName[2]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[2]+" LIKE "+arg[6]+" AND "+chequeAmt[2]+" LIKE "+arg[7]+" AND "+receipt[2]+" LIKE "+arg[8]+" AND "+chequeStatus[2]+" NOT LIKE 'H'"
							+"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[3]+"') AS BANK,"+chequeNo[3]+","+chequeAmt[3]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[3]+" ELSE STATUS END) AS "+chequeStatus[3]+","+reason[3]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[3]+")) AS REJECTION_TYPE,"+receipt[3]+" ,'Bank','' status AS FROM_TABLE FROM "+tableName[3]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[3]+" LIKE "+arg[9]+" AND "+chequeAmt[3]+" LIKE "+arg[10]+" AND "+receipt[3]+" LIKE "+arg[11]+" AND "+chequeStatus[3]+" NOT LIKE 'H'"
							+"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE TABLE_NAME='"+tableName[4]+"') AS BANK,"+chequeNo[4]+","+chequeAmt[4]+",(CASE WHEN STATUS IS NULL THEN "+chequeStatus[4]+" ELSE STATUS END) AS "+chequeStatus[4]+","+reason[4]+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason[4]+")) AS REJECTION_TYPE,"+receipt[4]+" ,'Bank','' status AS FROM_TABLE FROM "+tableName[4]+" T WHERE RECEIPT_SL_NO!=-99999 AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo[4]+" LIKE "+arg[12]+" AND "+chequeAmt[4]+" LIKE "+arg[13]+" AND "+receipt[4]+" LIKE "+arg[14]+" AND "+chequeStatus[4]+" NOT LIKE 'H'";
				}
				LogManager.push("Query"+query);
				//receipt queries start
				if(sbean.getSearchFor().equalsIgnoreCase("exact")){
					if(!formChequeNo.equalsIgnoreCase("") )
					{
						arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"' ";
					 }
					else if(formChequeNo.equalsIgnoreCase(""))
					{
						arg[0]="";
					}
					if(!formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="AND trunc(R.AMOUNT)=trunc("+formChequeAmt+") ";
					}
					else if (formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="";
					}
					if(!formreceipt.equalsIgnoreCase(""))
					{
						arg[2]="AND RECEIPT_NO='"+formreceipt+"' ";
			 		 }
					else if(formreceipt.equalsIgnoreCase(""))
					{
						arg[2]="";
					 }
					query2=" SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[0]+" FROM "+tableName[0]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[0]+" FROM "+tableName[0]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[0]+"' AND STATUS='Y')   " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[1]+" FROM "+tableName[1]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[1]+" FROM "+tableName[1]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no,'Receipt' AS FROM_TABLE,status  FROM RECEIPT_MASTER R WHERE  R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[1]+"' AND STATUS='Y')  " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[2]+" FROM "+tableName[2]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[2]+" FROM "+tableName[2]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no,'Receipt' AS FROM_TABLE,status  FROM RECEIPT_MASTER R WHERE  R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[2]+"' AND STATUS='Y')  " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[3]+" FROM "+tableName[3]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[3]+" FROM "+tableName[3]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no,'Receipt' AS FROM_TABLE,status  FROM RECEIPT_MASTER R WHERE  R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[3]+"' AND STATUS='Y')  " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[4]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[4]+" FROM "+tableName[4]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[4]+" FROM "+tableName[4]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no,'Receipt' AS FROM_TABLE,status  FROM RECEIPT_MASTER R WHERE  R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[4]+"' AND STATUS='Y')  " +
									"ORDER BY 8 DESC,2";
					}
				
				else
				{
				arg[0]="'%"+formChequeNo+"%'";
				arg[1]="'"+formChequeAmt+"%'";
				arg[2]="'"+formreceipt+"%'";
				query2=" SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[0]+" FROM "+tableName[0]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[0]+" FROM "+tableName[0]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no,'Receipt' AS FROM_TABLE,status  FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[0]+"' AND STATUS='Y')  " +
						"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[1]+" FROM "+tableName[1]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[1]+" FROM "+tableName[1]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[1]+"' AND STATUS='Y')  " +
					    " UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[2]+" FROM "+tableName[2]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[2]+" FROM "+tableName[2]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[2]+"' AND STATUS='Y') " +
					    "UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[3]+" FROM "+tableName[3]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[3]+" FROM "+tableName[3]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[3]+"' AND STATUS='Y')" +
					    "UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[4]+"') AS BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason[4]+" FROM "+tableName[4]+" WHERE BANK_NO=R.BANK_NO),R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason[4]+" FROM "+tableName[4]+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[4]+"' AND STATUS='Y')" +
							"ORDER BY 8 DESC,2";
				}
				LogManager.push("Query fianl"+query+query2);
				//query3=query2;
				//receipt queries end
				LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
				list = this.mytemplate.query(query2,new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						sVB.setBankName(rset.getString(1));
						sVB.setChequeNo(rset.getString(2));
						sVB.setChequeAmount(rset.getString(3));
						//System.out.print(rset.getString(chequeStatus).trim()+"-");
							sVB.setRealisation("Reversal");
						
						if(rset.getString(9)!=null){
							sVB.setReason(rset.getString(5)+"("+rset.getString(9)+")");
						}
						else
						{
							sVB.setReason(rset.getString(5));
							
						}
						//sVB.setBankName(bankName);
						sVB.setReceiptNo(rset.getString(6));
						sVB.setBankNo1(rset.getString(7));
						sVB.setBankNo2(rset.getString(7));
						sVB.setDepositDate(rset.getString(8));
						//sVB.setActualChequeAmount(rset.getString(9));
						//sVB.setActualChequeNo(rset.getString(10));
						sVB.setReceipt(rset.getString(10));
						sVB.setFrom(rset.getString("FROM_TABLE"));
						sVB.setPayment(rset.getString("STATUS"));
						return sVB;
					}
								
				});
			}
			//end  for both receipt & bank
			
			
			//Search in Receipt statement
			else
			{
				if(sbean.getSearchFor().equalsIgnoreCase("exact")){
					if(!formChequeNo.equalsIgnoreCase("") )
					{
						arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"' ";
						
					 }
					else if(formChequeNo.equalsIgnoreCase(""))
					{
						arg[0]="";
					}
					if(!formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="AND trunc(R.AMOUNT)=trunc("+formChequeAmt+") ";
					}
					else if (formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]="";
					}
					if(!formreceipt.equalsIgnoreCase(""))
					{
						arg[2]="AND RECEIPT_NO='"+formreceipt+"' ";
			 		 }
					else if(formreceipt.equalsIgnoreCase(""))
					{
						arg[2]="";
					 }
					query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[0]+"' AND STATUS='Y')   " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[1]+"' AND STATUS='Y') " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[2]+"' AND STATUS='Y')  "+
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[3]+"' AND STATUS='Y')  " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[4]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[4]+"' AND STATUS='Y')  " +
									"ORDER BY 6 DESC,RECEIPT_SL_NO ";
					}
				
				else
				{
				arg[0]="'%"+formChequeNo+"%'";
				arg[1]="'"+formChequeAmt+"%'";
				arg[2]="'"+formreceipt+"%'";
				query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[0]+"' AND STATUS='Y')  " +
						"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[1]+"' AND STATUS='Y')  " +
						" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[2]+"' AND STATUS='Y') " +
						"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[3]+"' AND STATUS='Y') " +
						"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[4]+"') AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888 AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds[4]+"' AND STATUS='Y') " +
								"ORDER BY RECEIPT_SL_NO, 6 DESC";
				}
				LogManager.push("Query"+query);
				list = this.mytemplate.query(query,  new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						sVB.setChequeNo(rset.getString("CHEQUE_NO"));
						sVB.setChequeAmount(rset.getString("AMOUNT"));
						sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
						sVB.setBankName(rset.getString("BANK"));
						sVB.setReceipt(rset.getString("RECEIPT_NO"));
						sVB.setPayment(rset.getString("STATUS"));
						sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
						sVB.setRealisation("Reversal");
						return sVB;
					}});
			}
		}	
		//------end-------------
				
		
		//not searched for all banks
		else
		{
				LogManager.push("Inside search code");
				String[] arg = new String[3];
				LogManager.push("inside blocks::::");
				List blist=getBankQueryData(bankcode);
				final Map tempMap = (Map) blist.get(0);
				final String tableName=((String)tempMap.get("TABLE_NAME"));
				final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
				final String reason=((String)tempMap.get("REASON"));
				final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
				final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
				final String bankName=(String)tempMap.get("BANK_NAME");
				final String receipt=((String)tempMap.get("RECEIPT_NO"));
				
				sbean.setBankName((String)tempMap.get("BANK_NAME"));
				//Search in bank statement 
			
			//start both receipt & bank 
			if(searchIn.equalsIgnoreCase("Both"))
			{	
				
				if(sbean.getSearchFor().equalsIgnoreCase("exact")){
				if(!formChequeNo.equalsIgnoreCase("") )
				{
					arg[0]=" AND "+chequeNo+"='"+formChequeNo+"' ";
				 }
				else if(formChequeNo.equalsIgnoreCase(""))
				{
					arg[0]="";
				}
				if(!formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]="AND "+chequeAmt+"="+formChequeAmt+" ";
				}
				else if (formChequeAmt.equalsIgnoreCase("") )
				{
					arg[1]="";
				}
				if(!formreceipt.equalsIgnoreCase(""))
				{
					arg[2]="AND "+receipt+"='"+formreceipt+"' ";
				 }
				else if(formreceipt.equalsIgnoreCase(""))
				{
					arg[2]=" ";
				 } 
				query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+" ,'Bank' AS FROM_TABLE FROM "+tableName+" T WHERE RECEIPT_SL_NO !=-99999  AND RECEIPT_SL_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND "+chequeStatus+" NOT LIKE 'H' ";
			    }
				else
				{
					arg[0]="'%"+formChequeNo+"%'";
					arg[1]="'"+formChequeAmt+"%'";
					arg[2]="'"+formreceipt+"%'";
					query="SELECT "+chequeNo+","+chequeAmt+", (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+chequeStatus+","+reason+",RECEIPT_SL_NO,BANK_NO,TO_CHAR(DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,ACT_NO,ACT_AMT,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(T."+reason+")) AS REJECTION_TYPE,"+receipt+" ,'Bank' AS FROM_TABLE FROM "+tableName+" T WHERE RECEIPT_SL_NO !=-99999  AND RECEIPT_SL_NO IS "+realise+" NULL AND "+chequeNo+" LIKE "+arg[0]+" AND "+chequeAmt+" LIKE "+arg[1]+" AND "+receipt+" LIKE "+arg[2]+" AND "+chequeStatus+" NOT LIKE 'H' ";
				}
				LogManager.push("Query"+query);
				LogManager.push(arg[0]+""+""+arg[1]+""+arg[2]);
				//receipt starts 
				if(sbean.getSearchFor().equalsIgnoreCase("exact")){
					if(!formChequeNo.equalsIgnoreCase("") )
					{
						arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"'";
					 }
					else if(formChequeNo.equalsIgnoreCase(""))
					{
						arg[0]=" ";
					}
					if(!formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]=" AND trunc(R.AMOUNT)=trunc('"+formChequeAmt+"')";
					}
					else if (formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]=" ";
					}
					if(!formreceipt.equalsIgnoreCase(""))
					{
						arg[2]=" AND RECEIPT_NO='"+formreceipt+"'";
					 }
					else if(formreceipt.equalsIgnoreCase(""))
					{
						arg[2]=" ";
					 }
					
					//query2="UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID=(SELECT BANK_ID FROM BANK_ACCOUNT_CODE WHERE BANK_ACCOUNT_CODE=R.BANK_CODE)) AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE FROM RECEIPT_MASTER R WHERE R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY R.RECEIPT_SL_NO";
					query2=" SELECT '"+bankName+"' BANK ,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO) "+reason+",R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,'',0,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888  AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 7 DESC, 1";	
				}
				
				else
				{
				arg[0]="'%"+formChequeNo+"%'";
				arg[1]="'"+formChequeAmt+"%'";
				arg[2]="'"+formreceipt+"%'";
				query2=" SELECT '"+bankName+"' BANK,R.CHEQUE_NO,R.AMOUNT,CHEQUE_STATUS,(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO) "+reason+",R.RECEIPT_SL_NO,BANK_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,'',0,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=(SELECT "+reason+" FROM "+tableName+" WHERE BANK_NO=R.BANK_NO)) AS REJECTION_TYPE,receipt_no ,'Receipt' AS FROM_TABLE,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888  AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 7 DESC,1";
				}
				//receipt ends 
				query=query2;
				list = this.mytemplate.query(query2,new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						//sVB.setBankName("BANK");
						sVB.setChequeNo(rset.getString("CHEQUE_NO"));
						sVB.setChequeAmount(rset.getString("AMOUNT"));
						sVB.setRealisation("Reversal");
						sVB.setBankName(rset.getString("BANK"));
						sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
						sVB.setBankNo1(rset.getString("BANK_NO"));
						sVB.setBankNo2(rset.getString("BANK_NO"));
						sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
						sVB.setReceipt(rset.getString("RECEIPT_NO"));
						sVB.setPayment(rset.getString("STATUS"));
						sVB.setFrom(rset.getString("FROM_TABLE"));
						return sVB;
					} 
								
				});
			}
			//ends both receipt & bank
			//Search in Receipt statement
			else
			{
				if(sbean.getSearchFor().equalsIgnoreCase("exact")){
					if(!formChequeNo.equalsIgnoreCase("") )
					{
						arg[0]=" AND R.CHEQUE_NO='"+formChequeNo+"'";
					 }
					else if(formChequeNo.equalsIgnoreCase(""))
					{
						arg[0]=" ";
					}
					if(!formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]=" AND trunc(R.AMOUNT)=trunc('"+formChequeAmt+"')";
					}
					else if (formChequeAmt.equalsIgnoreCase("") )
					{
						arg[1]=" ";
					}
					if(!formreceipt.equalsIgnoreCase(""))
					{
						arg[2]=" AND RECEIPT_NO='"+formreceipt+"'";
					 }
					else if(formreceipt.equalsIgnoreCase(""))
					{
						arg[2]=" ";
					 }
					query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID=(SELECT BANK_ID FROM BANK_ACCOUNT_CODE WHERE BANK_ACCOUNT_CODE=R.BANK_CODE)) AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status  FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888  AND R.BANK_NO IS "+realise+" NULL "+arg[0]+arg[1]+arg[2]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY 6 desc,R.RECEIPT_SL_NO";
					}
				
				else
				{
				arg[0]="'%"+formChequeNo+"%'";
				arg[1]="'"+formChequeAmt+"%'";
				arg[2]="'"+formreceipt+"%'";
				query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID=(SELECT BANK_ID FROM BANK_ACCOUNT_CODE WHERE BANK_ACCOUNT_CODE=R.BANK_CODE)) AS BANK,RECEIPT_NO,R.CHEQUE_NO,R.AMOUNT,R.RECEIPT_SL_NO,TO_CHAR(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,CHEQUE_STATUS,status FROM RECEIPT_MASTER R WHERE R.BANK_NO =-88888  AND R.BANK_NO IS "+realise+" NULL AND R.CHEQUE_NO LIKE "+arg[0]+" AND R.RECEIPT_NO LIKE "+arg[2]+" AND R.AMOUNT LIKE "+arg[1]+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankcode+"' AND STATUS='Y')  ORDER BY  6 DESC,R.RECEIPT_SL_NO";
				}
				LogManager.push("Query"+query);
				list = this.mytemplate.query(query,  new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						sVB.setChequeNo(rset.getString("CHEQUE_NO"));
						sVB.setChequeAmount(rset.getString("AMOUNT"));
						sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
						sVB.setBankName(rset.getString("BANK"));
						sVB.setReceipt(rset.getString("RECEIPT_NO"));
						sVB.setRealisation("Reversal");
						sVB.setPayment(rset.getString("STATUS"));
						sVB.setDepositDate(rset.getString("RECEIPT_DATE"));
						return sVB;
					}});
			}
			}	
		    
	
	
	}
	catch(Exception e)
	{
		LogManager.push("Exception In SearchDAOImpl - searchResult searchdao(): "+e);
		e.printStackTrace();
	}
	LogManager.push("QUERY executed "+query);
	return list; 
}
public void updateSelectedData(String[] bankNo,String[] actualChequeNo,String[] actualChequeAmount,String bankData)throws CommonBaseException{

	if(bankData.equalsIgnoreCase("reversal"))
	{
		for(String bank:bankNo)
		{
		LogManager.push("bank nos in updation for reversal "+bank);
		}
	}
	else
	{
	 
	LogManager.push("bank nos in updation for actual data "+bankNo.toString());
	LogManager.push("actual cheque nos "+actualChequeNo.toString()+" actual cheque amount "+actualChequeAmount.toString());
	}
}
public void updateBankReversalData(String bankNos,SearchFormBean sbean)throws CommonBaseException{
	LogManager.push("inside updateBankReversalData ");
	
	String bankId=sbean.getBankId();
	String tableName="";
	String bankNumbers[]=bankNos.split(",");
	String queryBank="";
	String query="";
	for(int i=1;i<bankNumbers.length;i++)
	{
		queryBank=queryBank+"'"+bankNumbers[i]+"'";
	}
	queryBank=queryBank.replaceAll("''", "','");
	
	if(!bankId.equalsIgnoreCase("All"))
	{
		Object[] arg = new Object[1];
		arg[0]=bankId;
	tableName=(String)this.mytemplate.queryForObject("select bank_name from bank_master where bank_id=?",arg,String.class);
	query="update "+tableName+" set RECEIPT_SL_NO=-99999 WHERE bank_no in ("+queryBank+")";
	int count=this.mytemplate.update(query);
	LogManager.push("query to update reversal data "+query);
	LogManager.push("No.of reversal records "+count);
	}
	else 
	{
		List bankList=this.mytemplate.queryForList("select table_name from bank_master where status='Y'");
		if(bankList.size()>0)
		{
		for(int i=0;i<bankList.size();i++)
		{
			Map temp=(Map)bankList.get(i);
			if(queryBank.length()>0)
			{
			query="update "+temp.get("TABLE_NAME")+" set RECEIPT_SL_NO=-99999 WHERE bank_no in ("+queryBank+")";
			int count=this.mytemplate.update(query);
			LogManager.push("Query to update reversal data "+query);
			LogManager.push("No.of reversal records "+count);
			}
	 	}
		
			
		}
	}
	LogManager.push("Bank nos for query "+queryBank);
 }
 public void updateBankActualData(String[] actualChequeNos,String[] actualChequeAmounts,String[] bankNos,SearchFormBean sbean)throws CommonBaseException{
	 
	 String bankId=sbean.getBankId();
	 String tableName="";
	 String query="";
	 List updatedList=new ArrayList();
		if (bankId.equalsIgnoreCase("All")) {
			List bankList = this.mytemplate
					.queryForList("select table_name from bank_master where status='Y'");
			if (bankList.size() > 0) {
				for (int i = 0; i < bankList.size(); i++) {
					Map temp = (Map) bankList.get(i);
					for (int j = 1; j < bankNos.length; j++) {
						if(actualChequeAmounts[j].trim().equalsIgnoreCase(""))
						    actualChequeAmounts[j]="null";
						if(actualChequeNos[j].trim().equalsIgnoreCase(""))
							actualChequeNos[j]="null";
						System.out.println(actualChequeAmounts[j]);
						query = "update " + temp.get("TABLE_NAME")
								+ " SET ACT_AMT=" + actualChequeAmounts[j]
								+ " ,ACT_NO=" + actualChequeNos[j]
								+ " where bank_no=" + bankNos[j];
						LogManager.push("query for updating actual data "
								+ query);
						int count = this.mytemplate.update(query);
						LogManager.push("updated records " + count);
					}
				}

			}
		}else
		{
		Object[] arg = new Object[1];
		arg[0]=bankId;
		tableName=(String)this.mytemplate.queryForObject("select bank_name from bank_master where bank_id=?",arg,String.class);
		  for(int i=1;i<bankNos.length;i++)
		  {
			  query="update "+tableName+" set ACT_AMT=" + actualChequeAmounts[i]+ " ,ACT_NO=" + actualChequeNos[i]+ " where bank_no=" + bankNos[i];
			  int count = this.mytemplate.update(query);
			  LogManager.push("updated records " + count);			                                                                    								
		  }
	 }
 }

 public List getNotMatchedActualList(String[] bankNos,SearchFormBean sbean,String sid) throws CommonBaseException {
	 List list=null;
	 
	   Set<String> set=new HashSet<String>();
		try {
			List nonMatchedList=sbean.getNonmatchedlist();
			String bankNo="";
			
			String querydel="DELETE FROM TEMP_NONMATCHED where session_id='"+sid+"'";
			Runner.updation(querydel);
			LogManager.push("Query deleted:::::::::"+querydel);
			
			
			for(int i=1;i<bankNos.length;i++)
			{
				set.add(bankNos[i]);
						
			}
			
			for(Iterator<String> it=set.iterator();it.hasNext();)
			{
				
				String value=it.next();
				String queryinstemp="INSERT INTO TEMP_NONMATCHED VALUES('"+value+"','"+sid+"')";
				Runner.inserion(queryinstemp);
			}
			
			LogManager.push("BankNoin"+ bankNo);
			String BankIn="";
			if(bankNos.length>0)
			{
				//BankIn = " AND B.BANK_NO IN (" +bankNo +")";
				BankIn = " AND B.BANK_NO =T.BANKNO AND T.SESSION_ID='"+sid+"'";
			}
			
			
				String bankcode=sbean.getBankId();
				if(bankcode.equalsIgnoreCase("all"))
				{
					String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
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
					
					for(int k=0;k<listbank.size();k++)
					{
					bankIds[k]=listbank.get(k).toString();
					LogManager.push("bank"+i+":"+listbank.get(k).toString());
					List blist=getBankQueryData(listbank.get(k).toString());
					final Map tempMap = (Map)blist.get(0);
					 tableName[k]=((String)tempMap.get("TABLE_NAME"));
					 chequeStatus[k]=((String)tempMap.get("CHEQUE_STATUS"));
					 reason[k]=((String)tempMap.get("REASON"));
					 chequeNo[k]=((String)tempMap.get("CHEQUE_NO"));
				 	 chequeAmt[k]=((String)tempMap.get("CHEQUE_AMT"));
				     bankName[k]=(String)tempMap.get("BANK_NAME");
					 receipt[k]=((String)tempMap.get("RECEIPT_NO"));
					 LogManager.push("<--"+tableName[k]+""+chequeStatus[k]+"-->");
					// sbean.setBankName((String)tempMap.get("BANK_NAME"));
					}
					final String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[0]+",B."+chequeAmt[0]+",B."+reason[0]+",B."+chequeStatus[0]+",B.RECEIPT_SL_NO,B.BANK_NO,TO_CHAR(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[0]+")) AS REJECTION_TYPE,"+receipt[0]+" ,CASH_TRANSACTION_ID FROM "+tableName[0]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND (B.RECEIPT_SL_NO IS NULL ) "+BankIn+" " +
							"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[1]+",B."+chequeAmt[1]+",B."+reason[1]+",B."+chequeStatus[1]+",B.RECEIPT_SL_NO,B.BANK_NO,TO_CHAR(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[1]+")) AS REJECTION_TYPE,"+receipt[1]+" ,CASH_TRANSACTION_ID FROM "+tableName[1]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND (B.RECEIPT_SL_NO  IS  NULL ) "+BankIn+" " +
							" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[2]+",B."+chequeAmt[2]+",B."+reason[2]+",B."+chequeStatus[2]+",B.RECEIPT_SL_NO,B.BANK_NO,TO_CHAR(B.POST_DT,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[2]+")) AS REJECTION_TYPE,"+receipt[2]+" ,CASH_TRANSACTION_ID FROM "+tableName[2]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND (B.RECEIPT_SL_NO  IS  NULL ) "+BankIn+" " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[3]+",B."+chequeAmt[3]+",B."+reason[3]+",B."+chequeStatus[3]+",B.RECEIPT_SL_NO,B.BANK_NO,TO_CHAR(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[3]+")) AS REJECTION_TYPE,"+receipt[3]+" ,CASH_TRANSACTION_ID FROM "+tableName[3]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND (B.RECEIPT_SL_NO  IS  NULL ) "+BankIn+" " +
									"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[4]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[4]+",B."+chequeAmt[4]+",B."+reason[4]+",B."+chequeStatus[4]+",B.RECEIPT_SL_NO,B.BANK_NO,TO_CHAR(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[4]+")) AS REJECTION_TYPE,"+receipt[4]+" ,CASH_TRANSACTION_ID FROM "+tableName[4]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND (B.RECEIPT_SL_NO  IS  NULL ) "+BankIn+" " +
									"ORDER BY 9 DESC";
					LogManager.push("Query for BANK LIST matched col:==>"+query+"count-->");
					list = this.mytemplate.query(query, new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
							SearchVB sVB = new SearchVB();
							sVB.setChequeNo(rset.getString(chequeNo[0]));
							sVB.setChequeAmount((String)rset.getString(chequeAmt[0]).toString());
							//System.out.print(rset.getString(chequeStatus).trim()+"-");
							if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("C"))
							{
								sVB.setRealisation("Realized");
							}
							else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("D"))
							{
								sVB.setRealisation("Returned");
							}
							else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("R"))
							{
								sVB.setRealisation("Reversal");
							}
							else 
							{
								sVB.setRealisation("Not Known");
							}
							sVB.setReason(rset.getString(reason[0]));
							sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
							sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
							sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
							sVB.setActualChequeAmount(rset.getString("ACTUAL_CHEQUE_AMT"));
							sVB.setReceipt(rset.getString(receipt[0]));
							sVB.setBankName(rset.getString("BANK"));
							sVB.setExcelBankNo(rset.getString("BANK_NO"));
							if(rset.getString("CASH_TRANSACTION_ID")!=null){
								if(rset.getString("CASH_TRANSACTION_ID").equalsIgnoreCase("-99999"))
									sVB.setReversal("R");
								else
										sVB.setReversal("");
								}
								else
								{
									sVB.setReversal("");
								}
							return sVB;
						}
									
					});
				}
				else
				{
			
				List blist=getBankQueryData(bankcode);
				final Map tempMap = (Map) blist.get(0);
				final String tableName=((String)tempMap.get("TABLE_NAME"));
				final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
				final String reason=((String)tempMap.get("REASON"));
				final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
				final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
				final String receipt=((String)tempMap.get("RECEIPT_NO"));
				
				String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankcode+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",B.DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason+")) AS REJECTION_TYPE,"+receipt+" ,CASH_TRANSACTION_ID ,B.RECEIPT_SL_NO,B.BANK_NO FROM "+tableName+" B , TEMP_NONMATCHED T  WHERE (B.ACTUAL_CHEQUE_NO IS NOT NULL OR B.ACTUAL_CHEQUE_AMT IS NOT NULL ) AND (B.RECEIPT_SL_NO IS NULL ) "+BankIn+" ORDER BY 8 DESC" ;
				LogManager.push("Query for BANK LIST matched col:==>"+query+"count-->");
				list = this.mytemplate.query(query, new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						sVB.setChequeNo(rset.getString(chequeNo));
						sVB.setChequeAmount((String)rset.getString(chequeAmt).toString());
						
						//System.out.print(rset.getString(chequeStatus).trim()+"-");
						if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
						{
							sVB.setRealisation("Realized");
						}
						else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
						{
							sVB.setRealisation("Returned");
						}
						else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
						{
							sVB.setRealisation("Reversal");
						}
						else 
						{
							sVB.setRealisation("Not Known");
						}
						if(rset.getString("REJECTION_TYPE")!=null){
							sVB.setReason(rset.getString(reason)+"("+rset.getString("REJECTION_TYPE")+")");
							}
						else
						{
							sVB.setReason(rset.getString(reason));
							
						}sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
						sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
						sVB.setActualChequeAmount(rset.getString("ACTUAL_CHEQUE_AMT"));
						sVB.setReceipt(rset.getString(receipt));
						sVB.setBankName(rset.getString("BANK"));
						sVB.setExcelBankNo(rset.getString("BANK_NO"));
						if(rset.getString("CASH_TRANSACTION_ID")!=null){
							if(rset.getString("CASH_TRANSACTION_ID").equalsIgnoreCase("-99999"))
								sVB.setReversal("R");
							else
									sVB.setReversal("");
							}
							else
							{
								sVB.setReversal("");
							}
						return sVB;
					}
								
				});
				
				}
				
				
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	 
	 
	return list;
 }

public List getActualMatchedList(String[] bankNos, SearchFormBean sbean,
		String sid) throws CommonBaseException {
	List list=null;
	Set<String> set=new HashSet<String>();
	try {
		
		String bankcode=sbean.getBankId();
		LogManager.push("Enters getMatchedList");
		
		String bankNo="";
		
			
		String querydel="DELETE FROM TEMP_NONMATCHED WHERE SESSION_ID='"+sid+"'";
		Runner.updation(querydel);
		LogManager.push("Query deleted:::::::::"+querydel);
		
		for(int i=1;i<bankNos.length;i++)
		{
			set.add(bankNos[i]);
					
		}
		
		for(Iterator<String> it=set.iterator();it.hasNext();)
		{
			
			String value=it.next();
			String queryinstemp="INSERT INTO TEMP_NONMATCHED VALUES('"+value+"','"+sid+"')";
			Runner.inserion(queryinstemp);
		}
		
		LogManager.push("BankNoin"+ bankNo);
		String BankIn="";
		if(bankNos.length>0)
		{
			
			BankIn = " AND B.BANK_NO =T.BANKNO AND T.SESSION_ID='"+sid+"'";
		}
		if(bankcode.equalsIgnoreCase("all"))
		{
			//start
			String query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y' ORDER BY 1 ASC";
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
		
				for(int k=0;k<listbank.size();k++)
				{
				bankIds[k]=listbank.get(k).toString();
				LogManager.push("bank"+i+":"+listbank.get(k).toString());
				List blist=getBankQueryData(listbank.get(k).toString());
				final Map tempMap = (Map)blist.get(0);
				 tableName[k]=((String)tempMap.get("TABLE_NAME"));
				 chequeStatus[k]=((String)tempMap.get("CHEQUE_STATUS"));
				 reason[k]=((String)tempMap.get("REASON"));
				 chequeNo[k]=((String)tempMap.get("CHEQUE_NO"));
			 	 chequeAmt[k]=((String)tempMap.get("CHEQUE_AMT"));
			     bankName[k]=(String)tempMap.get("BANK_NAME");
				 receipt[k]=((String)tempMap.get("RECEIPT_NO"));
				 LogManager.push("<--"+tableName[k]+""+chequeStatus[k]+"-->");
				
		 }
			
				
				for(int k=0;k<listbank.size();k++)
				{
				bankIds[k]=listbank.get(k).toString();
				LogManager.push("bank"+i+":"+listbank.get(k).toString());
				List blist=getBankQueryData(listbank.get(k).toString());
				final Map tempMap = (Map)blist.get(0);
				 tableName[k]=((String)tempMap.get("TABLE_NAME"));
				 chequeStatus[k]=((String)tempMap.get("CHEQUE_STATUS"));
				 reason[k]=((String)tempMap.get("REASON"));
				 chequeNo[k]=((String)tempMap.get("CHEQUE_NO"));
			 	 chequeAmt[k]=((String)tempMap.get("CHEQUE_AMT"));
			     bankName[k]=(String)tempMap.get("BANK_NAME");
				 receipt[k]=((String)tempMap.get("RECEIPT_NO"));
				 LogManager.push("<--"+tableName[k]+""+chequeStatus[k]+"-->");
				
				}
				
				
				final String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[0]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[0]+",B."+chequeAmt[0]+",B."+reason[0]+",B."+chequeStatus[0]+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[0]+")) AS REJECTION_TYPE,"+receipt[0]+"  FROM "+tableName[0]+" B , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO IS NOT NULL AND B.RECEIPT_SL_NO!='-99999' "+BankIn+" " +
						"UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[1]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[1]+",B."+chequeAmt[1]+",B."+reason[1]+",B."+chequeStatus[1]+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[1]+")) AS REJECTION_TYPE,"+receipt[1]+"  FROM "+tableName[1]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO  IS NOT NULL AND B.RECEIPT_SL_NO!='-99999' "+BankIn+" " +
						" UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[2]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[2]+",B."+chequeAmt[2]+",B."+reason[2]+",B."+chequeStatus[2]+",B.RECEIPT_SL_NO,to_char(B.POST_DT,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[2]+")) AS REJECTION_TYPE,"+receipt[2]+"  FROM "+tableName[2]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO  IS NOT NULL AND B.RECEIPT_SL_NO!='-99999' "+BankIn + 
						 " UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[3]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[3]+",B."+chequeAmt[3]+",B."+reason[3]+",B."+chequeStatus[3]+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[3]+")) AS REJECTION_TYPE,"+receipt[3]+"  FROM "+tableName[3]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO  IS NOT NULL AND B.RECEIPT_SL_NO!='-99999' "+BankIn +
				          " UNION ALL SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankIds[4]+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo[4]+",B."+chequeAmt[4]+",B."+reason[4]+",B."+chequeStatus[4]+",B.RECEIPT_SL_NO,to_char(B.DEPOSIT_DATE,'DD/MM/YYYY') DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason[4]+")) AS REJECTION_TYPE,"+receipt[4]+"  FROM "+tableName[4]+" B  , TEMP_NONMATCHED T WHERE (ACTUAL_CHEQUE_AMT IS NOT NULL OR ACTUAL_CHEQUE_NO IS NOT NULL )AND B.RECEIPT_SL_NO  IS NOT NULL AND B.RECEIPT_SL_NO!='-99999' "+BankIn;
				LogManager.push("Query for BANK LIST matched col test:==>"+query+"count-->");
				list = this.mytemplate.query(query, new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						SearchVB sVB = new SearchVB();
						sVB.setChequeNo(rset.getString(chequeNo[0]));
						sVB.setChequeAmount(rset.getString(chequeAmt[0]));
						//System.out.print(rset.getString(chequeStatus).trim()+"-");
						if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("C"))
						{
							sVB.setRealisation("Realized");
						}
						else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("D"))
						{
							sVB.setRealisation("Returned");
						}
						else if(rset.getString(chequeStatus[0]).trim().equalsIgnoreCase("R"))
						{
							sVB.setRealisation("Reversal");
						}
						sVB.setReason(rset.getString(reason[0]));
						sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
						sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
						sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
						sVB.setActualChequeAmount((String)rset.getString("ACTUAL_CHEQUE_AMT").toString());
						sVB.setReceipt(rset.getString(receipt[0]));
						sVB.setBankName(rset.getString("BANK"));
						return sVB;
					}
								
				});
				
				
		}
		else
		{
				
		List blist=getBankQueryData(bankcode);
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		final String chequeStatus=((String)tempMap.get("CHEQUE_STATUS"));
		final String reason=((String)tempMap.get("REASON"));
		final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
		final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
		final String receipt=((String)tempMap.get("RECEIPT_NO"));
		
				final String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER WHERE BANK_ID='"+bankcode+"') AS BANK,ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT,B."+chequeNo+",B."+chequeAmt+",B."+reason+",B."+chequeStatus+",B.RECEIPT_SL_NO,B.DEPOSIT_DATE,(SELECT UNIQUE REJECTION_TYPE FROM BANK_RETURN_MASTER WHERE REASON_DESC=UPPER(B."+reason+")) AS REJECTION_TYPE,"+receipt+"  FROM "+tableName+" B , TEMP_NONMATCHED T  WHERE (B.ACTUAL_CHEQUE_NO IS NOT NULL OR B.ACTUAL_CHEQUE_AMT IS NOT NULL ) AND B.RECEIPT_SL_NO IS NOT NULL " +BankIn;
		LogManager.push("Query for BANK LIST matched col:==>"+query+"count-->");
		list = this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				SearchVB sVB = new SearchVB();
				sVB.setChequeNo(rset.getString(chequeNo));
				sVB.setChequeAmount((String)rset.getString(chequeAmt).toString());
				
				if(rset.getString(chequeStatus).trim().equalsIgnoreCase("C"))
				{
					sVB.setRealisation("Realized");
				}
				else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("D"))
				{
					sVB.setRealisation("Returned");
				}
				else if(rset.getString(chequeStatus).trim().equalsIgnoreCase("R"))
				{
					sVB.setRealisation("Reversal");
				}
				else 
				{
					sVB.setRealisation("Not Known");
				}
				sVB.setReason(rset.getString(reason));
				sVB.setReceiptNo(rset.getString("RECEIPT_SL_NO"));
				sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
				sVB.setActualChequeNo(rset.getString("ACTUAL_CHEQUE_NO"));
				sVB.setActualChequeAmount(rset.getString("ACTUAL_CHEQUE_AMT"));
				sVB.setReceipt(rset.getString(receipt));
				sVB.setBankName(rset.getString("BANK"));
				return sVB;
			}
						
		});
		
				}
		
		LogManager.push("Exit matched list");
		
	} catch (Exception e) {
		
		e.printStackTrace();
	}return list;

	
}
 
}
