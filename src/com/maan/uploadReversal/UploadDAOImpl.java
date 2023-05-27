package com.maan.uploadReversal;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.jdbc.core.RowMapper;

import com.maan.cash.CashCB;
import com.maan.cash.CashFormBean;
import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.Runner;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;
import com.maan.login.LoginForm;



public class UploadDAOImpl extends CommonBaseDAOImpl implements UploadDAO {

	public int i=1;
	StringBuffer sb = new StringBuffer("");
	
	public List getTransactionDetails(final String transId) throws CommonBaseException{
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
		LogManager.push(list.size()+":"+list);
		LogManager.push("End Of Line Upload3");
		return list;
	}
	public List getReversalTransactionDetails(final String transId) throws CommonBaseException{
		LogManager.info("UploadDAOImpl getReversalTransactionDetails() Method - Enter");
		List list = new ArrayList();
			
		final String queryrectdetails="SELECT COUNT(*) AS COUNT FROM RECEIPT_REVERSAL WHERE  BATCHID="+transId+" UNION ALL select count(*) from receipt_reversal rr ,receipt_master rm where rr.batchid="+transId+" and rm.receipt_no=rr.receipt_no" ;
	    List res=this.mytemplate.queryForList(queryrectdetails);
		LogManager.push("res,res.size"+res+","+res.size());
		for(int i=0;i<res.size();i++){
			final Map temp = (Map)res.get(i);
			LogManager.push("res.get(i):"+res.get(i));
			LogManager.push("temp.get(COUNT):"+temp.get("COUNT"));
			list.add(temp.get("COUNT"));
		}
       
		LogManager.push(list.size()+":"+list);
		LogManager.push("End Of Line getReversalTransactionDetails");
		return list;
	}
	
	
	public List getReceiptsDetail(final String transId,final String editPayment,boolean edit) throws CommonBaseException{
		List list=null;
		try {
			LogManager.info("UploadDAOImpl getReceiptsReversalDetail() Method - Enter");
			//Query to retireve Receipts detail
			String query = "";
		
			String updat = "update receipt_reversal set active='D' where (active='1' or active is null) and batchid='"+transId+"'";
			this.mytemplate.update(updat);
			if(edit){
				String update = "UPDATE RECEIPT_REVERSAL SET ACTIVE='C' WHERE STATUS ='R' AND PAYMENT_NO='"+editPayment+"' AND BATCHID='"+transId+"'";
                 int g = this.mytemplate.update(update);
                 LogManager.push("Updation Sucess:"+g);
                update = "UPDATE RECEIPT_REVERSAL SET ACTIVE='D' WHERE PAYMENT_NO<>'"+editPayment+"' AND BATCHID='"+transId+"'";
                this.mytemplate.update(update);
                
                
				}
			
			if(editPayment.equalsIgnoreCase("") ){
			query = "SELECT RECEIPT_SL_NO,RM.RECEIPT_NO , TO_CHAR(RM.RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE  ,CHEQUE_NO , RECEIPT_AG_NAME ,AMOUNT,RR.ACTIVE ACTIVE FROM RECEIPT_REVERSAL RR, RECEIPT_MASTER RM WHERE RM.RECEIPT_NO = RR.RECEIPT_NO AND RR.STATUS IS NULL AND RR.BATCHID='"+transId+"'";
			}
			else {
				query = "SELECT RECEIPT_SL_NO,RM.RECEIPT_NO , TO_CHAR(RM.RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE  ,CHEQUE_NO , RECEIPT_AG_NAME ,AMOUNT,RR.ACTIVE ACTIVE FROM RECEIPT_REVERSAL RR, RECEIPT_MASTER RM WHERE RM.RECEIPT_NO = RR.RECEIPT_NO AND RR.STATUS IS NULL AND RR.BATCHID='"+transId+"'" +
						" UNION ALL SELECT RECEIPT_SL_NO,RM.RECEIPT_NO , TO_CHAR(RM.RECEIPT_DATE,'DD/MM/YYYY') RECEIPT_DATE  ,CHEQUE_NO , RECEIPT_AG_NAME ,AMOUNT,RR.ACTIVE ACTIVE FROM RECEIPT_REVERSAL RR, RECEIPT_MASTER RM WHERE RM.RECEIPT_NO = RR.RECEIPT_NO AND RR.STATUS ='R' AND RR.PAYMENT_NO='"+editPayment+"' AND RR.BATCHID='"+transId+"' order by 7 asc";
				LogManager.push("Receipt Selection::"+query);
				
			}
								
			LogManager.push("query for receiptdetails:" + query);
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset, final int args)
						throws SQLException {
					UploadVB uVB = new UploadVB();
					uVB.setReceiptNumber(rset.getString("RECEIPT_NO"));
					uVB.setReceiptDate(rset.getString("RECEIPT_DATE"));
					uVB.setChequeNumber(rset.getString("CHEQUE_NO"));
					uVB.setReceiptAGName(rset.getString("RECEIPT_AG_NAME"));
					uVB.setChequeAmount(rset.getString("AMOUNT"));
					uVB.setStatus(rset.getString("ACTIVE"));
					uVB.setReceiptslno(rset.getString("RECEIPT_SL_NO"));
					
					i++;
					return uVB;
				}

			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogManager.push("Exception in getReceiptsReversalDetail() for Receipt No.s Upload");
		}		
	   return list;
		
	}
	public void updateCheckedReceipts(String transId, String checked,
			String unchecked) throws CommonBaseException {
		LogManager.info("UploadDAOImpl updateSubmittedReceipts() Method - Enter success");
		//Query to update values of RECEIPT_REVERSAL table
		if(!unchecked.equalsIgnoreCase("")){
	    final String query1="UPDATE RECEIPT_REVERSAL SET ACTIVE='U' WHERE RECEIPT_NO IN("+unchecked+") AND BATCHID="+transId;
		LogManager.push("Query to update values of RECEIPT_REVERSAL UNCHECKED :"+query1);
	    this.mytemplate.update(query1);
		}
		if(!checked.equalsIgnoreCase("")){
	    final String query2="UPDATE RECEIPT_REVERSAL SET ACTIVE='C' WHERE RECEIPT_NO IN("+checked+")  AND  BATCHID="+transId;
		 LogManager.push("Query to update values of RECEIPT_REVERSAL CHECKED:"+query2);
	    this.mytemplate.update(query2);
		}
	   
	}
	
	public List updateSubmittedReceipts(String transId, String checked,
			String unchecked) throws CommonBaseException {
		LogManager.info("UploadDAOImpl updateSubmittedReceipts() Method - Enter success");
		//Query to update values of RECEIPT_REVERSAL table
		if(!unchecked.equalsIgnoreCase("")){
	    final String query1="UPDATE RECEIPT_REVERSAL SET ACTIVE='U' WHERE RECEIPT_NO IN("+unchecked+") AND BATCHID="+transId;
		LogManager.push("Query to update values of RECEIPT_REVERSAL UNCHECKED :"+query1);
	    this.mytemplate.update(query1);
		}
		if(checked.equalsIgnoreCase("")){
		checked="''";
		}
	    final String query2="UPDATE RECEIPT_REVERSAL SET ACTIVE='S' WHERE ( RECEIPT_NO IN("+checked+") OR ACTIVE='C') AND  BATCHID="+transId;
		 LogManager.push("Query to update values of RECEIPT_REVERSAL CHECKED:"+query2);
	    this.mytemplate.update(query2);
		
	    List result = new ArrayList();
	    String sumQuery = "select sum(rm.amount) from receipt_master rm ,receipt_reversal rr where rr.batchid= '"+transId+"' and rr.receipt_no= rm.receipt_no and upper(rr.active)='S'";
	    int sum = this.mytemplate.queryForInt(sumQuery);
	    LogManager.push("SUM"+ sum);
	    int count = this.mytemplate.queryForInt("select count(*) from receipt_master rm ,receipt_reversal rr where rr.batchid= '"+transId+"' and rr.receipt_no= rm.receipt_no and upper(rr.active)='S'");
	    LogManager.push("count"+ count);
	    result.add(Integer.toString(sum));
	    result.add(Integer.toString(count));
	    //updateReceipts(transId);
	    LogManager.info("UploadDAOImpl updateSubmittedReceipts() Method - End success");
		return result;
	}
	public void updateReceipts(String transId) throws CommonBaseException {
		// TODO Auto-generated method stub
		String updateReceipts = "update receipt_reversal set active='D' where active in ('C','U','S')  ";
		this.mytemplate.update(updateReceipts);
		
	}
	public boolean checkPayment(String paymnetNo) throws CommonBaseException {
		// TODO Auto-generated method stub
		boolean exists = false;
		int count = this.mytemplate.queryForInt("select count(*) from receipt_master where trim(trans_source)= 'PYMT' and  receipt_no='"+paymnetNo+"'");
		if(count>0)
			exists = true;
		return exists;
		}
	
	public void insertUpdateReversals(UploadForm sbean)
			throws CommonBaseException {
		// TODO Auto-generated method stub
		LogManager.push(" insertUpdateReversals Tran Id :"+sbean.getTransactionid());
		String updateReceipts = "update receipt_reversal set status = 'R' ,payment_no = '"+sbean.getPaymnetNo()+"' where active='S' and batchid='"+sbean.getTransactionid()+"' ";
		this.mytemplate.update(updateReceipts);
		 
		String insertReceiptPayment = "insert into  receipt_master (RECEIPT_SL_NO,RECEIPT_NO, RECEIPT_DATE,AMOUNT,BANK_CODE,TRANS_SOURCE,PAYMENT_TYPE) values((select nvl((max(RECEIPT_SL_NO)+1),1) from receipt_master),'"+sbean.getPaymnetNo()+"' ,to_date('"+sbean.getPaymentDate()+"','dd/mm/yyyy'),'"+sbean.getPaymentAmount()+"','R01','PYMT','Bank & Affinity')" ;
		this.mytemplate.execute(insertReceiptPayment);		
		
		 String updateReceiptMaster = "update receipt_master set status='"+sbean.getPaymnetNo()+"' , bank_no='-88888'  where receipt_no in(select receipt_no from receipt_reversal where status='R' and payment_no='"+sbean.getPaymnetNo()+"')" ;
		this.mytemplate.update(updateReceiptMaster);
				
		
	}
	public void updateReversals(UploadForm sbean) throws CommonBaseException {
		// TODO Auto-generated method stub
		LogManager.push(" updateReversals Tran Id :"+sbean.getTransactionid());
		// To up
		String updateReceipts = "update receipt_reversal set status = 'R',payment_no = '"+sbean.getPaymnetNo()+"' where active='S' and batchid='"+sbean.getTransactionid()+"' ";
		this.mytemplate.update(updateReceipts);
		
		String updateReceiptMaster = "update receipt_master set status='"+sbean.getPaymnetNo()+"' , bank_no='-88888'  where receipt_no in(select receipt_no from receipt_reversal where status='R' and payment_no='"+sbean.getPaymnetNo()+"')" ;
		this.mytemplate.update(updateReceiptMaster);
		
	}
	public List getReceiptPayments(final String transId) throws CommonBaseException {
		List list=null;
		try {
			LogManager.info("UploadDAOImpl getReceiptsReversalDetail() Method - Enter");
			//Query to retireve Receipts detail
			String query = "SELECT  payment_no,count(payment_no) coun FROM RECEIPT_REVERSAL RR, RECEIPT_MASTER RM WHERE RM.RECEIPT_NO = RR.RECEIPT_NO AND RR.STATUS = 'R' AND RR.BATCHID='"+transId+"' group by payment_no";
			LogManager.push("query for receiptdetails:" + query);
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(final ResultSet rset, final int args)
						throws SQLException {
					UploadVB uVB = new UploadVB();
					uVB.setPaymentNo(rset.getString("PAYMENT_NO"));
					uVB.setMatched(rset.getString("COUN"));
					uVB.setTransactionId(transId);
					i++;
					return uVB;
				}

			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			LogManager.push("Exception in getReceiptsReversalDetail() for Receipt No.s Upload");
		}		
	   return list;
	}
	public void updateEditedRecords(String editPayment, String transId)
			throws CommonBaseException {
		LogManager.info("UploadDAOImpl updateEditedRecords() Method - Enter");
		
		String updateReceiptMaster = "update receipt_master set status='' , bank_no=''  where receipt_no in(select receipt_no from receipt_reversal where status='R' and payment_no='"+editPayment+"')" ;
		this.mytemplate.update(updateReceiptMaster);
		
		String updateReceiptReversal = "update receipt_reversal set status='' , payment_no=''  where receipt_no in(select receipt_no from receipt_reversal where status='R' and active='U' and payment_no='"+editPayment+"')" ;
		this.mytemplate.update(updateReceiptReversal);
		
		LogManager.info("UploadDAOImpl updateEditedRecords() Method - Enter");
		
	}
	public String[][] getPaymentAmount(String editPaymentNo)
			throws CommonBaseException {
		LogManager.push("Edit payment"+editPaymentNo);
		String amountqry = "select nvl(amount,0),to_char(receipt_date,'dd/mm/yyyy' ) from receipt_master where receipt_no =? and trans_source='PYMT'" ;
		String amount[][] = Runner.multipleSelection(amountqry, new String[] {editPaymentNo});
		return amount;	
	}
	
}
