package com.maan.admin.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;

public class BankDAOImpl extends CommonBaseDAOImpl implements BankDAO {
	
	private final static String BID="BANK_ID";
	private final static String BNAME="BANK_NAME";
	private final static String BTNAME= "TABLE_NAME";
	private final static String CHEQUENO= "CHEQUE_NO";
	private final static String CHEQUEAMT= "CHEQUE_AMT";
	private final static String CHEQUESTATUS= "CHEQUE_STATUS";
	private final static String REASON= "REASON";
	private final static String ACTIVE="STATUS";
	private final static String RECEIPT="RECEIPT_NO";
	public List getBankDetails() throws CommonBaseException
	{
		LogManager.push("BankDAOImpl getMethodDetails() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
		
		final String query="select BANK_ID,BANK_NAME,TABLE_NAME,CHEQUE_NO,CHEQUE_AMT,CHEQUE_STATUS,REASON,STATUS,RECEIPT_NO FROM BANK_MASTER order by BANK_NAME";
		
		 list = (ArrayList)this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
				
				final BankVB bankVB = new BankVB();
			    LogManager.push("rs.getString(ACTIVE)"+rs.getString(ACTIVE));
				LogManager.push("rs.getString(ACTIVE).equalsIgnoreCase(Y)"+rs.getString(ACTIVE).equalsIgnoreCase("Y "));
				bankVB.setActive(rs.getString(ACTIVE).equalsIgnoreCase("Y ")?"Yes":"No");
				bankVB.setChequeno(rs.getString(CHEQUENO));
				bankVB.setBankname(rs.getString(BNAME));
				bankVB.setBankid(rs.getString(BID));
				bankVB.setBanktable(rs.getString(BTNAME));
				bankVB.setReason(rs.getString(REASON));
				bankVB.setChequestatus(rs.getString(CHEQUESTATUS));
				bankVB.setChequeamt(rs.getString(CHEQUEAMT));
				bankVB.setReceiptNo(rs.getString(RECEIPT));
				return bankVB;
			}
		});
		
		LogManager.logExit();
		LogManager.push("BankDAOImpl getBankDetails() method  Ends");
		LogManager.popRemove(); 
		return list;
	}
	
	public int insertBankDetails(BankVB sVB)throws CommonBaseException
	{
		
		LogManager.push("BankDAOImpl insertBankDetails() method Starts");
		LogManager.logEnter();
		final String bankid=sVB.getBankid();
		final String bankname=sVB.getBankname();
		final String banktable=sVB.getBanktable();
		final String chequeno=sVB.getChequeno();
		final String active=sVB.getActive();
		final String chequeamt=sVB.getChequeamt();
		final String chequestatus=sVB.getChequestatus();
		final String reason=sVB.getReason();
		final String receiptNo=sVB.getReceiptNo();
		final String countquery="SELECT COUNT(*) FROM BANK_MASTER WHERE BANK_ID ='"+bankid+"'";
		Object countresult=this.mytemplate.queryForObject(countquery,new RowMapper(){
			public Object mapRow(ResultSet rs,int arg)throws SQLException{
				return rs.getString(1);
				
			}
		});
		LogManager.push("count :"+countresult.toString());
		if(countresult.toString().equalsIgnoreCase("0"))
		{
			LogManager.push("inside daoimpl:->>>>>chequeno:"+chequeno+"chequeamt:"+chequeamt+"chequestatus:"+chequestatus+"reason:"+reason);
			final String query="insert into BANK_MASTER(BANK_ID,BANK_NAME,TABLE_NAME,CHEQUE_NO,CHEQUE_AMT,CHEQUE_STATUS,REASON,STATUS,RECEIPT_NO)values('"+bankid+"','"+bankname+"','"+banktable+"','"+chequeno+"','"+chequeamt+"','"+chequestatus+"','"+reason+"','"+active+"','"+receiptNo+"')";
			int result=this.mytemplate.update(query);
			LogManager.logExit();
			LogManager.push("BankDAOImpl insertBankDetails() method  Ends");
			LogManager.popRemove(); 
			return result;
			
			
		}
		else
		{
			return 2;
		}
			
	}
	
	public List getEditBankDetails(String bankid)throws CommonBaseException
	{
		LogManager.push("BankDAOImpl getEditBankDetails() method Starts");
		LogManager.logEnter();
		List list=null;
		final String query="select BANK_ID,BANK_NAME,TABLE_NAME,CHEQUE_NO,CHEQUE_AMT,CHEQUE_STATUS,REASON,STATUS,RECEIPT_NO from BANK_MASTER where bank_id='"+bankid+"' ORDER BY BANK_NAME" ;
		LogManager.push("edit qry:"+query);
		list = (List)this.mytemplate.queryForList(query);
		LogManager.logExit();
		LogManager.push("BankDAOImpl getEditBankDetails() method  Ends");
		LogManager.popRemove(); 
		return list ;
	}
	
	public int updateBankDetails(BankVB sVB,String bankid)throws CommonBaseException
	{
		LogManager.push("BankDAOImpl updateBankDetails() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
		final String bankname=sVB.getBankname();
		final String banktable=sVB.getBanktable();
		final String chequeno=sVB.getChequeno();
		final String active=sVB.getActive();
		final String chequeamt=sVB.getChequeamt();
		final String chequestatus=sVB.getChequestatus();
		final String reason=sVB.getReason();
		final String receiptNo=sVB.getReceiptNo();
		final String query="update BANK_MASTER set BANK_NAME='"+bankname+"',TABLE_NAME='"+banktable+"',CHEQUE_NO='"+chequeno+"',CHEQUE_AMT='"+chequeamt+"',CHEQUE_STATUS='"+chequestatus+"',REASON='"+reason+"',STATUS='"+active+"',RECEIPT_NO='"+receiptNo+"' where BANK_ID='"+bankid+"'";
		int result=this.mytemplate.update(query);
		LogManager.logExit();
		LogManager.push("BankDAOImpl updateBankDetails() method  Ends");
		LogManager.popRemove(); 
		return result;
			
	}


}
