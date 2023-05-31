package com.maan.cash;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.maan.admin.usermanipulation.UserManipulationVB;
import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.Runner;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.use.CommonCB;
import com.maan.common.use.CommonDispatchForm;
import com.maan.login.LoginForm;
import com.maan.search.*;

public class CashDAOImpl extends CommonBaseDAOImpl implements CashDAO {

	public int i=1;
	
	public Map getBankList(CashFormBean form) throws CommonBaseException {
		LogManager.push("CashDAoImpl getBankList() method enter");
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
		LogManager.push("CashDAoImpl getBankList() method exit");
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	
	}
	
	public List getBranchList(CashFormBean form) throws CommonBaseException {
		LogManager.push("CashDAoImpl getBranchList() method enter");
		LogManager.logEnter();
		List result = new ArrayList();
		try{
			final String query = getQuery(DBConstants.BRANCH_LIST);
			final List list = this.mytemplate.queryForList(query);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					final Map tempMap = (Map) list.get(i);
					CashVB cashVB = new CashVB();
					cashVB.setBranchCode((String)tempMap.get("BRANCH_CODE"));
					cashVB.setBranchName((String)tempMap.get("BRANCH_NAME"));
					result.add(cashVB);
				}
			}
		}catch(Exception e){
			LogManager.fatal(e);
		}
		LogManager.push("CashDAoImpl getBranchList() method exit");
		LogManager.logExit();
		LogManager.popRemove();
		return result;
	
	}
	
	public List getSearchList(CashFormBean sbean) throws CommonBaseException {
		String query="";
		String query3="";
		String bankcode=sbean.getBankId();
		String formChequeNo=sbean.getTransactionDate();
		SearchDAOImpl sdao=new SearchDAOImpl();
		List blist=	sdao.getBankQueryData(bankcode);
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
		final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
		final String receipt=((String)tempMap.get("RECEIPT_NO"));
		sbean.setBankName((String)tempMap.get("BANK_NAME"));
	    String depdate = "";
		String location = "";
		String depdtformat = "";
		if(bankcode.trim().equalsIgnoreCase("CIT"))
		{
			location="LOCATION";
			depdate="DEPOSIT_DATE";
			depdtformat="DD/MM/YYYY";
		}
		else if(bankcode.trim().equalsIgnoreCase("SCB"))
		{
			location="PICKUPLOC";
			depdate="DEPOSIT_DATE";
			depdtformat="DD/MM/YYYY";
		}
		else if(bankcode.trim().equalsIgnoreCase("AXB"))
		{
			location="LOCATION_NAME";
			depdate="DEPOSIT_DATE";
			depdtformat="DD/MM/YYYY";
		}
		else if(bankcode.trim().equalsIgnoreCase("HSB"))
		{
			location="CLEARING_LOC";
			depdate="POST_DATE";
			depdtformat="DD/MM/YYYY";
		}
		else
		{
			location="CL_LOC";
			depdate="POST_DT";
			depdtformat="DD/MM/YYYY";
		}
		List list=null;
	    try{String listquery="";
	    	if(bankcode.trim().equalsIgnoreCase("CIT")){
	    		listquery="SELECT RECEIPT_SL_NO,BANK_NO,"+receipt+",TO_CHAR("+depdate+",'DD/MM/YYYY') AS DEPOSIT_DATE,"+location+" AS LOCATION,"+chequeAmt+" ,( SELECT RECEIPT_TOTAL_AMOUNT FROM CASH_TRANSACTION C WHERE C.CASH_TRANSACTION_ID=B.CASH_TRANSACTION_ID) AS MATCHED FROM "+tableName+" B WHERE "+depdate+"=TO_DATE('"+sbean.getTransactionDate()+"','DD/MM/YYYY') AND ("+chequeNo+" IS NULL OR "+chequeNo+"='' OR NARRATION='CASH DEPOSIT')";
	        }
	    	else if(bankcode.trim().equalsIgnoreCase("HDB")){
		    	listquery="SELECT RECEIPT_SL_NO,BANK_NO,"+receipt+",TO_CHAR("+depdate+",'DD/MM/YYYY') AS DEPOSIT_DATE,"+location+" AS LOCATION,"+chequeAmt+" ,( SELECT RECEIPT_TOTAL_AMOUNT FROM CASH_TRANSACTION C WHERE C.CASH_TRANSACTION_ID=B.CASH_TRANSACTION_ID) AS MATCHED FROM "+tableName+" B WHERE "+depdate+"=TO_DATE('"+sbean.getTransactionDate()+"','DD/MM/YYYY') AND ("+chequeNo+" IS NULL OR "+chequeNo+"='')";
		    }
	    	else if(bankcode.trim().equalsIgnoreCase("HSB")){
		    	listquery="SELECT RECEIPT_SL_NO,BANK_NO,"+receipt+",TO_CHAR("+depdate+",'DD/MM/YYYY') AS DEPOSIT_DATE,"+location+" AS LOCATION,"+chequeAmt+" ,( SELECT RECEIPT_TOTAL_AMOUNT FROM CASH_TRANSACTION C WHERE C.CASH_TRANSACTION_ID=B.CASH_TRANSACTION_ID) AS MATCHED FROM "+tableName+" B WHERE "+depdate+"=TO_DATE('"+sbean.getTransactionDate()+"','DD/MM/YYYY') AND ("+chequeNo+" IS NULL OR "+chequeNo+"='')";
		    }
	    	else if(bankcode.trim().equalsIgnoreCase("KOT")){
		    	listquery="SELECT RECEIPT_SL_NO,BANK_NO,"+receipt+",TO_CHAR("+depdate+",'DD/MM/YYYY') AS DEPOSIT_DATE,"+location+" AS LOCATION,"+chequeAmt+" ,( SELECT RECEIPT_TOTAL_AMOUNT FROM CASH_TRANSACTION C WHERE C.CASH_TRANSACTION_ID=B.CASH_TRANSACTION_ID) AS MATCHED FROM "+tableName+" B WHERE "+depdate+"=TO_DATE('"+sbean.getTransactionDate()+"','DD/MM/YYYY') AND ("+chequeNo+" IS NULL OR "+chequeNo+"='')";
		    }
	    	else if(bankcode.trim().equalsIgnoreCase("SCB")){
		    	listquery="SELECT RECEIPT_SL_NO,BANK_NO,"+receipt+",TO_CHAR("+depdate+",'DD/MM/YYYY') AS DEPOSIT_DATE,"+location+" AS LOCATION,"+chequeAmt+" ,( SELECT RECEIPT_TOTAL_AMOUNT FROM CASH_TRANSACTION C WHERE C.CASH_TRANSACTION_ID=B.CASH_TRANSACTION_ID) AS MATCHED FROM "+tableName+" B WHERE "+depdate+"=TO_DATE('"+sbean.getTransactionDate()+"','DD/MM/YYYY') AND ("+chequeNo+" IS NULL OR "+chequeNo+"='')";
		    }
	    	else if(bankcode.trim().equalsIgnoreCase("AXB")){
		    	listquery="SELECT RECEIPT_SL_NO,BANK_NO,"+receipt+",TO_CHAR("+depdate+",'DD/MM/YYYY') AS DEPOSIT_DATE,"+location+" AS LOCATION,"+chequeAmt+" ,( SELECT RECEIPT_TOTAL_AMOUNT FROM CASH_TRANSACTION C WHERE C.CASH_TRANSACTION_ID=B.CASH_TRANSACTION_ID) AS MATCHED FROM "+tableName+" B WHERE "+depdate+"=TO_DATE('"+sbean.getTransactionDate()+"','DD/MM/YYYY') AND ("+chequeNo+" IS NULL OR "+chequeNo+"='')";
		    }
			LogManager.push(listquery);
			 list = (ArrayList)this.mytemplate.query(listquery, new RowMapper() {
				public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
					final CashVB cashVB = new CashVB();
				    cashVB.setBankAmount(rs.getString(chequeAmt));
				    cashVB.setDepositDate(rs.getString("DEPOSIT_DATE"));
				    cashVB.setDepositNo(rs.getString(receipt));
				    cashVB.setLocation(rs.getString("LOCATION"));
				    cashVB.setBankNo(rs.getString("BANK_NO"));
				    cashVB.setMatchedAmount(rs.getString("MATCHED"));
				    cashVB.setBankreceipt(rs.getString("RECEIPT_SL_NO")==null?"":rs.getString("RECEIPT_SL_NO"));
				    return cashVB;
				}
			});			
			LogManager.logExit();
			LogManager.push("CashDAOImpl getCashDetails() method  Ends");
			LogManager.popRemove(); 
			return list;
		}
		catch(Exception e)
		{
			LogManager.push("Exception In CashDAOImpl - cashResult cashdao(): "+e);
		}
		LogManager.push("QUERY executed "+query3);		
		return list; 	
	} 
	
	public List getReceiptList(CashFormBean sbean) throws CommonBaseException {
		
		List list=null;
		try {
			String query3="";
			String bankcode=sbean.getBankId();
			String transdate=sbean.getTransactionDate();
			String bankNo = sbean.getBankno();
			SearchDAOImpl sdao=new SearchDAOImpl();
			List blist=	sdao.getBankQueryData(bankcode);
			final Map tempMap = (Map) blist.get(0);
			final String tableName=((String)tempMap.get("TABLE_NAME"));
			final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
			final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
			final String receipt=((String)tempMap.get("RECEIPT_NO"));
			sbean.setBankName((String)tempMap.get("BANK_NAME"));
			String location="";
			if(bankcode.trim().equalsIgnoreCase("CIT"))
			{
				location="LOCATION";
			}
			else if(bankcode.trim().equalsIgnoreCase("SCB"))
			{
				location="PICKUPLOC";
			}
			else if(bankcode.trim().equalsIgnoreCase("AXB"))
			{
				location="LOCATION_NAME";
			}
			else if(bankcode.trim().equalsIgnoreCase("HSB"))
			{
				location="PICKUP_LOCATION";
			}
			else
			{
				location="PKUP_LOC";
			}
			LogManager.push("transdate:bankNo:"+transdate+":"+bankNo);
			String query="Select "+chequeAmt+","+location+" AS LOCATION FROM "+tableName+" WHERE BANK_NO="+bankNo;
			Object objamt=this.mytemplate.queryForObject(query,new RowMapper(){
				public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
					 
				    return (rs.getString(chequeAmt)+"~"+rs.getString("LOCATION"));
			}})	;
			LogManager.push(objamt.toString());
			String valobj[] = objamt.toString().split("~"); 
			String bankAmount = valobj[0];
			String bankLocation = valobj[1];
			sbean.setBankAmount(bankAmount);
			sbean.setBankLocation(bankLocation);
			
			//Query to get deductable amount 
			String qery="SELECT CASH_MINUS,CASH_PLUS FROM BANK_MASTER WHERE BANK_ID='"+bankcode+"'";
			Object objcashamt=this.mytemplate.queryForObject(qery,new RowMapper(){
				public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
					 
				    return (rs.getString("CASH_MINUS")+"~"+rs.getString("CASH_PLUS"));
			}})	;
			String cashvalues[] = objcashamt.toString().split("~");
			int cashminus = Integer.parseInt(cashvalues[0]);
			int cashplus = Integer.parseInt(cashvalues[1]);
   
			//Calculation for final amount and it is limit to select receipt amount  
			int graceamt = cashminus;
			int actualamt = Integer.parseInt(sbean.getBankAmount());
			int maxamt=actualamt +cashplus;
			int minamount = actualamt-graceamt;
   
			//total amount selected
			sbean.setMaxamount(maxamt);
			sbean.setMinamount(minamount);
			CommonCB commonCB = new CommonCB();
			//String branchCodes = commonCB.stringArrayToString(sbean.getBranchCode(), ",");
			String branchCodes=sbean.getBranchCodes();
			//branchCodes = "'" + branchCodes.replaceAll(",", "','") + "'";
			
			list = null;
			try{
				LogManager.push("Opertion inside DAOIMPL::>"+sbean.getOperation());
			 String listquery="";
				if(!sbean.getOperation().equalsIgnoreCase("Edit")){
				 listquery="select RECEIPT_SL_NO,AMOUNT,RECEIPT_AG_NAME,to_char(RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE,RECEIPT_NO,TRANS_SOURCE,BRANCH_NAME,AMOUNT,REMARKS from RECEIPT_MASTER, BRANCH_MASTER where bank_no is null and amount<="+maxamt+" and receipt_date<to_date('"+transdate+"','DD/MM/YYYY') and bank_code in(select bank_account_code from bank_account_code where bank_id='"+bankcode+"' and status='Y') AND PAYMENT_TYPE='CASH' and RECEIPT_BRANCH_CODE = BRANCH_CODE AND RECEIPT_BRANCH_CODE IN (" + branchCodes +" ) order by RECEIPT_BRANCH_CODE ASC,RECEIPT_DATE ASC,RECEIPT_NO ASC";
				}
				else
				{
			      listquery="select RECEIPT_SL_NO,AMOUNT,RECEIPT_AG_NAME,to_char(RECEIPT_DATE,'dd/mm/yyyy') RECEIPT_DATE," +
			      		"RECEIPT_NO,TRANS_SOURCE,BRANCH_NAME,AMOUNT,REMARKS from " +
			      		"RECEIPT_MASTER, BRANCH_MASTER where bank_no is null " +
			      		"and amount<="+maxamt+" and receipt_date<to_date('"+transdate+"','DD/MM/YYYY') " +
			      		"and bank_code in(select bank_account_code from bank_account_code where bank_id='"+bankcode+"' and status='Y') " +
			      		"AND PAYMENT_TYPE='CASH' " +
			      		"AND RECEIPT_BRANCH_CODE IN (" + branchCodes +" ) " +
			      		"UNION select RECEIPT_SL_NO,AMOUNT,RECEIPT_AG_NAME,to_char(RECEIPT_DATE,'dd/mm/yyyy') RECEIPT_DATE," +
			      		"RECEIPT_NO,TRANS_SOURCE,BRANCH_NAME,AMOUNT,REMARKS from " +
			      		"RECEIPT_MASTER, BRANCH_MASTER where  RECEIPT_BRANCH_CODE = BRANCH_CODE AND RECEIPT_BRANCH_CODE IN ("+branchCodes+") AND bank_no="+sbean.getBankno() +" ORDER BY REMARKS ASC,BRANCH_NAME ASC,RECEIPT_DATE ASC,RECEIPT_NO ASC";
				}
				
				try {
					LogManager.push("Query to get receipt records by bankno date:"+listquery);
					 list = (ArrayList)this.mytemplate.query(listquery, new RowMapper() {
						public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
							final CashVB cashVB = new CashVB();
						    cashVB.setReceiptAmount(rs.getInt("AMOUNT"));
						    cashVB.setReceiptAGName(rs.getString("RECEIPT_AG_NAME"));
						    cashVB.setReceiptDate(rs.getString("RECEIPT_DATE"));
						    cashVB.setReceiptNo(rs.getString("RECEIPT_NO"));
						    cashVB.setTranSource(rs.getString("TRANS_SOURCE"));
						    cashVB.setReceiptBranchCode(rs.getString("BRANCH_NAME"));
						    cashVB.setReceiptAmount(rs.getInt("AMOUNT"));
						    cashVB.setStatus(rs.getString("REMARKS")==null?"":rs.getString("REMARKS"));
						    cashVB.setReceiptslno(rs.getString("RECEIPT_SL_NO"));
						    return cashVB;
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				LogManager.logExit();
				LogManager.push("CashDAOImpl getCashDetails() method  Ends");
				LogManager.popRemove(); 
				return list;
			}
			catch(Exception e)
			{
				LogManager.push("Exception In CashDAOImpl - cashResult cashdao(): "+e);
				}
			LogManager.push("QUERY executed "+query3);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LogManager.push("check");
		}
		return list; 
	}

	public String updateSelected(String checkedRecptNos,String uncheckedRecptNos) throws CommonBaseException {
		String queryupdate2="UPDATE RECEIPT_MASTER SET REMARKS='' WHERE RECEIPT_SL_NO IN("+uncheckedRecptNos+")";
	    if(!uncheckedRecptNos.equalsIgnoreCase("")){
	    	LogManager.push(queryupdate2+"Uncheckd:"+uncheckedRecptNos);
	       this.mytemplate.update(queryupdate2);
	    }
	    LogManager.push(queryupdate2);
	    String queryupdate="UPDATE RECEIPT_MASTER SET REMARKS='Y' WHERE RECEIPT_SL_NO IN("+checkedRecptNos+")";
	    if(!checkedRecptNos.equalsIgnoreCase("")){
	    this.mytemplate.update(queryupdate);
	    LogManager.push(queryupdate+"Checkd:"+checkedRecptNos);
	    }
	    LogManager.push(queryupdate);	   
	   String sum = "0";
	   return sum;
	}
	
    public String updateAsSelected(CashFormBean sbean) throws CommonBaseException {
		
    	String sum="0";
		String bankno = sbean.getBankno();
			
	    String queryupdate="UPDATE RECEIPT_MASTER SET REMARKS='Y' WHERE BANK_NO="+bankno;
	    this.mytemplate.update(queryupdate);
	    		LogManager.push("UPDATE RECEIPT_MASTER SET REMARKS='Y' WHERE BANK_NO="+bankno);
	    String querysum = "SELECT SUM(AMOUNT) AS SUM FROM RECEIPT_MASTER WHERE BANK_NO="+bankno;
	    LogManager.push("SUM Query::"+querysum);
	    Object objsumamt=this.mytemplate.queryForObject(querysum,new RowMapper(){
			public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
				 
			    return (rs.getString("SUM"));
		}})	;   
	    sum = objsumamt.toString();
	    return sum;
		
	}
 public String updateUnSelected(CashFormBean sbean) throws CommonBaseException {
		
	    String queryupdate1="UPDATE RECEIPT_MASTER SET REMARKS='' WHERE REMARKS='Y' AND BANK_NO IS NOT NULL";
	    this.mytemplate.update(queryupdate1);
	    String queryupdate="UPDATE RECEIPT_MASTER SET REMARKS='' WHERE REMARKS='Y' AND BANK_NO IS NOT NULL";
	    this.mytemplate.update(queryupdate);
	 
	    String sum="0";
	    LogManager.push(queryupdate);
	    return sum;
		
	}
 public String getsum() throws CommonBaseException {
	 String sum="0";
	  String qry="select count(*) from receipt_master where remarks='Y'";
	    String result=Runner.singleSelection(qry);
	    if(!result.equalsIgnoreCase("0")){
	     qry="select sum(amount) from receipt_master where remarks='Y'";
	     result=Runner.singleSelection(qry);
	    sum=result;
	    }
	    else
	    {
	    	sum="0";
	    }
	//    LogManager.push(qry);
	    return sum;
		
	}
    public void updateReceipt() throws CommonBaseException {
		
	    String queryupdate="UPDATE RECEIPT_MASTER SET REMARKS='' WHERE REMARKS IS NOT NULL";
	    
	    this.mytemplate.update(queryupdate);
	    
		
	}
      public void setEmpty() throws CommonBaseException {
		
	    String queryupdate="UPDATE RECEIPT_MASTER SET REMARKS='' WHERE REMARKS IS NOT NULL";
	    
	    this.mytemplate.update(queryupdate);
	    
		
	}
      public int getCount() throws CommonBaseException {
  		
  	    String query="SELECT COUNT(*) FROM RECEIPT_MASTER WHERE REMARKS IS NOT NULL";
  	    
  	    String count=Runner.singleSelection(query);
  	    
  	   LogManager.push("Count::>"+count);
  	    return Integer.parseInt(count);
  	    
  		
  	}
	public boolean checkvalid(CashFormBean sbean,LoginForm loginForm) throws CommonBaseException {
		LogManager.push("Enters check validation");
		LogManager.push("Bank amount to be checked:"+sbean.getBankAmount());
	    boolean flag=false;
		try {
			String bankno = sbean.getBankno();
			String bankcode = sbean.getBankId();
			String branch = sbean.getBankLocation();
			SearchDAOImpl sdao=new SearchDAOImpl();
			List blist=	sdao.getBankQueryData(bankcode);
			final Map tempMap = (Map) blist.get(0);
			final String tableName=((String)tempMap.get("TABLE_NAME"));
			sbean.setBankName((String)tempMap.get("BANK_NAME"));
			
			//Query to get deductable amount 
			String qery="SELECT CASH_MINUS,CASH_PLUS FROM BANK_MASTER WHERE BANK_ID='"+bankcode+"'";
			Object objcashamt=this.mytemplate.queryForObject(qery,new RowMapper(){
				public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
				    return (rs.getString("CASH_MINUS")+"~"+rs.getString("CASH_PLUS"));
			}})	;
			String cashvalues[] = objcashamt.toString().split("~");
			int cashminus = Integer.parseInt(cashvalues[0]);
			int cashplus = Integer.parseInt(cashvalues[1]);
			String qery2="SELECT SUM(AMOUNT) AS TOTAL FROM RECEIPT_MASTER WHERE REMARKS='Y'";
			Object objtotamt=this.mytemplate.queryForObject(qery2,new RowMapper(){
				public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
				return (rs.getString("TOTAL"));
			}})	;
			String qery3="SELECT count(*) as COUNT FROM RECEIPT_MASTER WHERE REMARKS='Y'";
			Object objcount=this.mytemplate.queryForObject(qery3,new RowMapper(){
				public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
				return (rs.getString("COUNT"));
			}})	;
			String  qery4="SELECT CASH_TRANSACTION_ID AS CASHID FROM "+tableName+" where bank_no="+bankno;
			Object objcashid=this.mytemplate.queryForObject(qery4,new RowMapper(){
				public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
				return (rs.getString("CASHID"));
			}})	;
			//Calculation for final amount and it is limit to select receipt amount  
			int graceamt = cashminus;
			int actualamt = Integer.parseInt(sbean.getBankAmount());
			int maxamt=actualamt +cashplus;
			int minamount = actualamt-graceamt;
   
			//total amount selected
   
			int totamt = Integer.parseInt(objtotamt.toString());
			int count = Integer.parseInt(objcount.toString());
			int difference = totamt - actualamt; 
			String diff = Integer.toString(difference);
			diff = diff.replaceAll("-", "");
			sbean.setMaxamount(maxamt);
			sbean.setMinamount(minamount);
			LogManager.push("maxamt: minamount: totamt==> "+maxamt +":"+ minamount +":"+  totamt);
			LogManager.push("Grace amount:"+graceamt);
			    
			if((totamt>=minamount && totamt<=maxamt)  )
			{
				LogManager.push("Matched Records");
				if(sbean.getOperation().trim().equalsIgnoreCase("Edit"))
				{
					String queryupdate="UPDATE RECEIPT_MASTER SET BANK_NO=NULL,STATUS='CN',CHEQUE_STATUS=NULL WHERE BANK_NO="+sbean.getBankno();
				    LogManager.push("Query for Updating bank_no null"+queryupdate);
			    	this.mytemplate.update(queryupdate);
				    
			    	 queryupdate="UPDATE "+tableName+" SET RECEIPT_SL_NO=NULL WHERE BANK_NO="+sbean.getBankno();
				    LogManager.push("Query for Updating receipt_no null"+queryupdate);
			    	this.mytemplate.update(queryupdate);
			    	if(objcashid.toString()!=null)
			    	{
			    	queryupdate="UPDATE CASH_TRANSACTION SET DIFFERENCE ="+diff+" , NO_OF_RECEIPTS="+count+" , DATE_OF_TRANSACTION=TO_CHAR(SYSDATE) ,USER_NAME= '"+loginForm.getUserId()+"',RECEIPT_TOTAL_AMOUNT="+totamt+" WHERE CASH_TRANSACTION_ID="+objcashid.toString();
				    LogManager.push("Query for Updating receipt_no null"+queryupdate);
			    	this.mytemplate.update(queryupdate);
			    	}
			    	 
				}
				//Query for Updating bank_no of selected valid receipts
				String queryupdate="UPDATE RECEIPT_MASTER SET BANK_NO='"+bankno+"' ,STATUS='C',CHEQUE_STATUS='C' WHERE REMARKS ='Y'";
			    LogManager.push("Query for Updating bank_no"+queryupdate);
				this.mytemplate.update(queryupdate);
			    
			    //Query for Updating receipt sl no of bank record selected
				if(!sbean.getOperation().trim().equalsIgnoreCase("Edit"))
				{
				 String qry="SELECT CASH_SEQ.NEXTVAL AS CASHSEQ FROM DUAL";
				    Object objseq=this.mytemplate.queryForObject(qry,new RowMapper(){
						public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
						return (rs.getString("CASHSEQ"));
					}})	;
				    String seq = objseq.toString();
			    queryupdate="UPDATE "+tableName+" SET CASH_TRANSACTION_ID="+seq+" ,RECEIPT_SL_NO=(SELECT MAX(RECEIPT_SL_NO) FROM RECEIPT_MASTER WHERE REMARKS = 'Y'),STATUS='C' WHERE BANK_NO='"+bankno+"'";
			    LogManager.push("Query for Updating receipt sl no"+queryupdate);
			    this.mytemplate.update(queryupdate);
			    
			    String insquery="INSERT INTO CASH_TRANSACTION " +
				"(CASH_TRANSACTION_ID, BANK_CODE, BANK_AMOUNT,RECEIPT_TOTAL_AMOUNT," +
				" DIFFERENCE, BRANCH_NAME, NO_OF_RECEIPTS," +
				" DATE_OF_TRANSACTION, USER_NAME)" +
				" values("+seq+",'"+bankcode+"',"+actualamt+","+totamt+"," +
				diff+",'"+branch+"',"+count+"," +
				"TO_CHAR(SYSDATE),'"+loginForm.getUserId()+"')";
			    this.mytemplate.execute(insquery);
				}
				else
				{
					 queryupdate="UPDATE "+tableName+" SET RECEIPT_SL_NO=(SELECT MAX(RECEIPT_SL_NO) FROM RECEIPT_MASTER WHERE REMARKS = 'Y'),STATUS='C' WHERE BANK_NO='"+bankno+"'";
			 	    LogManager.push("Query for Updating receipt sl no"+queryupdate);
			 	    this.mytemplate.update(queryupdate);
				}
			    queryupdate="UPDATE RECEIPT_MASTER SET REMARKS='' WHERE REMARKS IS NOT NULL";
			    this.mytemplate.update(queryupdate);
			    flag= true;
			}
			else
			{	   
				LogManager.push("Amount Exceeds/fall behind the limit");
				flag= false;
			}
		} 
		catch(Exception e)
		{
		e.printStackTrace();	
		}
	     return flag;
	}

	
	public List getMatchDetail(String bankval,CashFormBean sbean) throws CommonBaseException {
		String query="";
		String query3="";
		String bankcode=sbean.getBankId();
		String formChequeNo=sbean.getTransactionDate();
		SearchDAOImpl sdao=new SearchDAOImpl();
		List blist=	sdao.getBankQueryData(bankcode);
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
		final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
		final String receipt=((String)tempMap.get("RECEIPT_NO"));
		sbean.setBankName((String)tempMap.get("BANK_NAME"));
		final String bname = sbean.getBankName();
	    String depdate = "";
		String location = "";
		String depdtformat = "";
		if(bankcode.trim().equalsIgnoreCase("CIT"))
		{
			location="LOCATION";
			depdate="DEPOSIT_DATE";
			depdtformat="DD-MON-YY";
		}
		else if(bankcode.trim().equalsIgnoreCase("SCB"))
		{
			location="PICKUPLOC";
			depdate="DEPOSIT_DATE";
			depdtformat="DD-MON-YY";
		}
		else if(bankcode.trim().equalsIgnoreCase("AXB"))
		{
			location="LOCATION_NAME";
			depdate="DEPOSIT_DATE";
			depdtformat="DD-MON-YY";
		}
		else if(bankcode.trim().equalsIgnoreCase("HSB"))
		{
			location="PICKUP_LOCATION";
			depdate="DEPOSIT_DATE";
			depdtformat="DD-MON-YY";
		}
		else
		{
			location="PKUP_LOC";
			depdate="DEPOSIT_DATE";
			depdtformat="DD-MON-YY";
		}
				
		List list=null;
	    try{
	    	final String listquery="SELECT RECEIPT_SL_NO,BANK_NO,"+receipt+",TO_CHAR(TO_DATE("+depdate+",'"+depdtformat+"'),'DD/MM/YYYY') AS DEPOSIT_DATE,"+location+" AS LOCATION,"+chequeAmt+" FROM "+tableName+" WHERE BANK_NO="+bankval;
			LogManager.push(listquery);
			 list = (ArrayList)this.mytemplate.query(listquery, new RowMapper() {
				public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
					
					final CashVB cashVB = new CashVB();
				    cashVB.setBankAmount(rs.getString(chequeAmt));
				    cashVB.setDepositDate(rs.getString("DEPOSIT_DATE"));
				    cashVB.setDepositNo(rs.getString(receipt));
				    cashVB.setLocation(rs.getString("LOCATION"));
				    cashVB.setBankNo(rs.getString("BANK_NO"));
				    cashVB.setBankName(bname);
				    cashVB.setBankreceipt(rs.getString("RECEIPT_SL_NO")==null?"":rs.getString("RECEIPT_SL_NO"));
				    return cashVB;
				}
			});
			
			LogManager.logExit();
			LogManager.push("CashDAOImpl getCashDetails() method  Ends");
			LogManager.popRemove(); 
			
		}
		catch(Exception e)
		{
			LogManager.push("Exception In CashDAOImpl - cashResult cashdao(): "+e);
			}
		LogManager.push("Exits bank detail");
		return list; 
	
	}

	public List getReceiptMatchDetail(String bankval,CashFormBean sbean) throws CommonBaseException {
		String query="";
		String query3="";
		String bankcode=sbean.getBankId();
		String formChequeNo=sbean.getTransactionDate();
		LogManager.push("Enters getReceiptMatchDetail");
		SearchDAOImpl sdao=new SearchDAOImpl();
		List blist=	sdao.getBankQueryData(bankcode);
		final Map tempMap = (Map) blist.get(0);
		final String tableName=((String)tempMap.get("TABLE_NAME"));
		final String chequeNo=((String)tempMap.get("CHEQUE_NO"));
		final String chequeAmt=((String)tempMap.get("CHEQUE_AMT"));
		final String receipt=((String)tempMap.get("RECEIPT_NO"));
		sbean.setBankName((String)tempMap.get("BANK_NAME"));
	    String depdate = "";
		String location = "";
		String depdtformat = "";
		if(bankcode.trim().equalsIgnoreCase("CIT"))
		{
			location="LOCATION";
			depdate="DEPOSIT_DATE";
			depdtformat="DD/MM/YYYY";
		}
		else if(bankcode.trim().equalsIgnoreCase("SCB"))
		{
		location="PICKUPLOC";
		depdate="DEPOSIT_DATE";
		depdtformat="DD/MM/YYYY";
		}
		else if(bankcode.trim().equalsIgnoreCase("AXB"))
		{
		location="LOCATION_NAME";
		depdate="DEPOSIT_DATE";
		depdtformat="DD/MM/YYYY";
		}
		else if(bankcode.trim().equalsIgnoreCase("HSB"))
		{
		location="PICKUP_LOCATION";
		depdate="DEPOSIT_DATE";
		depdtformat="DD/MM/YYYY";
		}
		else
		{
			location="PKUP_LOC";
			depdate="DEPOSIT_DATE";
			depdtformat="DD/MM/YYYY";
		}
		 List list=null;
		    try{
		    	final String listquery="select PRODUCT_CODE,RECEIPT_SL_NO,AMOUNT,RECEIPT_AG_NAME,RECEIPT_DATE,RECEIPT_NO,TRANS_SOURCE,RECEIPT_BRANCH_CODE,AMOUNT,REMARKS from receipt_master where bank_code in(select bank_account_code from bank_account_code where bank_id='"+bankcode+"' and status='Y') and bank_no="+bankval+" and PAYMENT_TYPE='CASH'";
				LogManager.push("Query to get receipt records matched by bankno :"+listquery);
				 list = (ArrayList)this.mytemplate.query(listquery, new RowMapper() {
					public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
						final CashVB cashVB = new CashVB();
					    cashVB.setReceiptAmount(rs.getInt("AMOUNT"));
					    cashVB.setReceiptAGName(rs.getString("RECEIPT_AG_NAME"));
					    cashVB.setReceiptDate(rs.getString("RECEIPT_DATE"));
					    cashVB.setReceiptNo(rs.getString("RECEIPT_NO"));
					    cashVB.setTranSource(rs.getString("TRANS_SOURCE"));
					    cashVB.setReceiptBranchCode(rs.getString("RECEIPT_BRANCH_CODE"));
					    cashVB.setStatus(rs.getString("REMARKS")==null?"":rs.getString("REMARKS"));
					    cashVB.setReceiptslno(rs.getString("RECEIPT_SL_NO"));
					    cashVB.setReceiptProduct(rs.getString("PRODUCT_CODE"));
					    return cashVB;
					}
				});
				
				LogManager.logExit();
				LogManager.push("CashDAOImpl getCashDetails() method  Ends");
				LogManager.popRemove(); 
				return list;
		}
		catch(Exception e)
		{
			LogManager.push("Exception In CashDAOImpl - cashResult cashdao(): "+e);
			}
		LogManager.push("QUERY executed "+query3);
		
		return list; 
	
	}

	public String getBankId(String bankName) {
		String bankId="";
		try{
			bankId=(String)this.mytemplate.queryForObject("select bank_id from bank_master where upper(bank_name)=upper(?)",new Object[]{bankName}, String.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return bankId;
	}
}
