package com.maan.reversal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;

public class ReversalDAOImpl extends CommonBaseDAOImpl implements ReversalDAO {

	public int i=1;
	private String selectQry = "";
	private Object[] arg;
	public List getReceiptSearchList(ReversalFormBean sbean) throws CommonBaseException {
	    List list=null;
	    arg = new Object[2];
		try{
			selectQry = getQuery(DBConstants.REVERSAL_RECEIPT_COUNT);
			arg[0] = sbean.getFromDate();
			arg[1] = sbean.getToDate();
			list = this.mytemplate.query(selectQry, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			ReceiptReversalVB sVB = new ReceiptReversalVB();
			sVB.setReceiptDate(rset.getString(1));
			sVB.setNoOfReversal(rset.getString(2));
			return sVB;
			}});				
			//------end-------------
		}
		catch(Exception e)
		{
			LogManager.push("Exception In SearchDAOImpl - searchResult searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+selectQry);
		return list; 	
	}
	
	public List getCitiSearchList(ReversalFormBean sbean) throws CommonBaseException {
	    List list=null;	    
	    arg = new Object[2]; 
	    try{
			selectQry=getQuery(DBConstants.REVERSAL_CITIBANK_COUNT);
			arg[0] = sbean.getFromDate();
			arg[1] = sbean.getToDate();
			LogManager.push("QUERY executed "+selectQry);
			list = this.mytemplate.query(selectQry, arg, new RowMapper() {
				public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			CitiReversalVB sVB = new CitiReversalVB();
			sVB.setDepositDate(rset.getString(1));
			sVB.setNoOfReversal(rset.getString(2));
			return sVB;
			}});				
		}
		catch(Exception e)
		{
			LogManager.push("Exception In SearchDAOImpl - searchResult searchdao(): "+e);
		}
		return list; 
	}

	public List getHdfcSearchList(ReversalFormBean sbean) throws CommonBaseException {
		List list=null;		
		arg = new Object[2]; 
		try{
			arg[0] = sbean.getFromDate();
			arg[1] = sbean.getToDate();
			selectQry=getQuery(DBConstants.REVERSAL_HDFCBANK_COUNT);
			list = this.mytemplate.query(selectQry, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			HdfcReversalVB sVB = new HdfcReversalVB();
			sVB.setDepositDate(rset.getString(1));
			sVB.setNoOfReversal(rset.getString(2));
			return sVB;
			}});
		}
		catch(Exception e)
		{
			LogManager.push("Exception In SearchDAOImpl - searchResult searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+selectQry);			
		return list; 
	}
	
	public List getKotakSearchList(ReversalFormBean sbean) throws CommonBaseException {
		List list=null;		
		arg = new Object[2]; 
		try{
			arg[0] = sbean.getFromDate();
			arg[1] = sbean.getToDate();
			selectQry=getQuery(DBConstants.REVERSAL_KOTAK_COUNT);
			list = this.mytemplate.query(selectQry, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			KotakReversalVB sVB = new KotakReversalVB();
			sVB.setDepositDate(rset.getString(1));
			sVB.setNoOfReversal(rset.getString(2));
			return sVB;
			}});
		}
		catch(Exception e)
		{
			LogManager.push("Exception In SearchDAOImpl - searchResult searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+selectQry);			
		return list; 
	}
	
	public List getHsbcSearchList(ReversalFormBean sbean) throws CommonBaseException {
		List list=null;
		arg = new Object[2]; 
		try{
			selectQry=getQuery(DBConstants.REVERSAL_HSBCBANK_COUNT);
			arg[0] = sbean.getFromDate();
			arg[1] = sbean.getToDate();
			list = this.mytemplate.query(selectQry, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			HdfcReversalVB sVB = new HdfcReversalVB();
			sVB.setDepositDate(rset.getString(1));
			sVB.setNoOfReversal(rset.getString(2));
			return sVB;
			}});
		}
		catch(Exception e)
		{
			LogManager.push("Exception In SearchDAOImpl - searchResult searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+selectQry);
		return list; 
	}
	
	public List getScbSearchList(ReversalFormBean sbean) throws CommonBaseException {
	    List list=null;
	    arg = new Object[2];
		try{
			selectQry=getQuery(DBConstants.REVERSAL_SCBANK_COUNT);
			arg[0] = sbean.getFromDate();
			arg[1] = sbean.getToDate();
			list = this.mytemplate.query(selectQry, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			CitiReversalVB sVB = new CitiReversalVB();
			sVB.setDepositDate(rset.getString(1));
			sVB.setNoOfReversal(rset.getString(2));
			return sVB;
			}});
		}
		catch(Exception e)
		{
			LogManager.push("Exception In SearchDAOImpl - searchResult searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+selectQry);		
		return list; 
	}

	public List getAxisSearchList(ReversalFormBean sbean) throws CommonBaseException {
	    List list=null;	  
	    arg = new Object[2];
		try{
			selectQry=getQuery(DBConstants.REVERSAL_AXISBANK_COUNT);
			arg[0] = sbean.getFromDate();
			arg[1] = sbean.getToDate();
			list = this.mytemplate.query(selectQry, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			CitiReversalVB sVB = new CitiReversalVB();
			sVB.setDepositDate(rset.getString(1));
			sVB.setNoOfReversal(rset.getString(2));
			return sVB;
			}});
		}
		catch(Exception e)
		{
			LogManager.push("Exception In getAxisSearchList - searchResult searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+selectQry);		
		return list; 
	}
	
	//HAVE TO BE VERIFIED
	public List getReceiptReversalsList(ReversalFormBean sbean) throws CommonBaseException {
		List list=null;
		arg = new Object[1]; 
		try{
			selectQry="select to_char(RECEIPT_DATE,'dd/mm/yyyy') RECEIPT_DATE,RECEIPT_NO,CHEQUE_NO,to_char( CHEQUE_DATE , 'dd/mm/yyyy') CHEQUE_DATE,AMOUNT,RECEIPT_AG_NAME,status from receipt_master where bank_no=-88888 and receipt_date=to_date('"+sbean.getDepositdate()+"','dd/mm/yyyy') order by receipt_no";
			arg[0] = sbean.getDepositdate();
			list = this.mytemplate.query(selectQry, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			ReceiptReversalVB sVB = new ReceiptReversalVB();
			sVB.setReceiptDate(rset.getString("RECEIPT_DATE"));
			sVB.setReceiptNo(rset.getString("RECEIPT_NO"));
			sVB.setChequeNo(rset.getString("CHEQUE_NO"));
			sVB.setChequeDate(rset.getString("CHEQUE_DATE"));
			sVB.setAmount(rset.getString("AMOUNT"));
			sVB.setReceiptAGName(rset.getString("RECEIPT_AG_NAME"));
			sVB.setReceiptPayment(rset.getString("STATUS")==null?"":rset.getString("STATUS"));
			return sVB;
			}});
		}
		catch(Exception e)
		{
			LogManager.push("Exception In ReversalDAOImpl - getReceiptReversalsList searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+ selectQry);
		return list; 
		
	}
	
	public List getCitiReversalsList(ReversalFormBean sbean) throws CommonBaseException {
		List list=null;		
		arg = new Object[1];
		try{
			selectQry="select to_char(DEPOSIT_DATE,'dd/mm/yyyy') DEPOSIT_DATE,DEPSLIPNO,CHEQUE_NO,CHEQUE_AMT from citi_bank where receipt_sl_no=-99999 and deposit_date=to_date(?,'dd/mm/yyyy') order by depslipno";
			arg[0] = sbean.getDepositdate(); 
			list = this.mytemplate.query(selectQry,arg,  new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			CitiReversalVB sVB = new CitiReversalVB();
			sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
			sVB.setDepSlipNo(rset.getString("DEPSLIPNO"));
			sVB.setChequeNo(rset.getString("CHEQUE_NO"));
			sVB.setChequeAmt(rset.getString("CHEQUE_AMT"));
			return sVB;
			}});
		}
		catch(Exception e)
		{
			LogManager.push("Exception In reVERSALDAOImpl - getCitiReversalsList searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+selectQry);		
		return list; 
	}
	
	public List getHdfcReversalsList(ReversalFormBean sbean) throws CommonBaseException {
		List list=null;
		arg = new Object[1];
		try{
			selectQry="select to_char(POST_DT,'dd/mm/yyyy') POST_DT,DEPOSIT_SLIP_NO,INSTRUMENT_NO,INSTRUMENT_AMOUNT from hdfc_bank where receipt_sl_no=-99999 and POST_DT=to_date(?,'dd/mm/yyyy') order by DEPOSIT_SLIP_NO";
			arg[0] = sbean.getDepositdate();
			list = this.mytemplate.query(selectQry,arg,  new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			HdfcReversalVB sVB = new HdfcReversalVB();
			sVB.setDepositDate(rset.getString("POST_DT"));
			sVB.setDepSlipNo(rset.getString("DEPOSIT_SLIP_NO"));
			sVB.setChequeNo(rset.getString("INSTRUMENT_NO"));
			sVB.setChequeAmt(rset.getString("INSTRUMENT_AMOUNT"));			
			return sVB;
			}});
		}
		catch(Exception e)
		{
			LogManager.push("Exception In reVERSALDAOImpl - getHdfcReversalsList searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+selectQry);		
		return list; 
	}
	
	public List getKotakReversalsList(ReversalFormBean sbean) throws CommonBaseException {
		List list=null;
		arg = new Object[1];
		try{
			selectQry="select to_char(POST_DT,'dd/mm/yyyy') POST_DT,DEPOSIT_SLIP_NO,INSTRUMENT_NO,INSTRUMENT_AMOUNT from kotak_bank where receipt_sl_no=-99999 and POST_DT=to_date(?,'dd/mm/yyyy') order by DEPOSIT_SLIP_NO";
			arg[0] = sbean.getDepositdate();
			list = this.mytemplate.query(selectQry,arg,  new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			KotakReversalVB sVB = new KotakReversalVB();
			sVB.setDepositDate(rset.getString("POST_DT"));
			sVB.setDepSlipNo(rset.getString("DEPOSIT_SLIP_NO"));
			sVB.setChequeNo(rset.getString("INSTRUMENT_NO"));
			sVB.setChequeAmt(rset.getString("INSTRUMENT_AMOUNT"));			
			return sVB;
			}});
		}
		catch(Exception e)
		{
			LogManager.push("Exception In reVERSALDAOImpl - getKotakReversalsList searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+selectQry);		
		return list; 
	}

	public List getHsbcReversalsList(ReversalFormBean sbean) throws CommonBaseException {
		List list=null;
		arg = new Object[1];
		try{
			selectQry="select to_char(POST_DATE,'dd/mm/yyyy') POST_DATE,DEPOSIT_SLIP_NO,INSTRUMENT_NO,INSTRUMENT_AMOUNT from hsbc_bank where receipt_sl_no=-99999 and POST_DATE=to_date(?,'dd/mm/yyyy') order by DEPOSIT_SLIP_NO";
			arg[0] = sbean.getDepositdate();
			list = this.mytemplate.query(selectQry, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			HsbcReversalVB sVB = new HsbcReversalVB();
			sVB.setDepositDate(rset.getString("POST_DATE"));
			sVB.setDepSlipNo(rset.getString("DEPOSIT_SLIP_NO"));
			sVB.setChequeNo(rset.getString("INSTRUMENT_NO"));
			sVB.setChequeAmt(rset.getString("INSTRUMENT_AMOUNT"));
			return sVB;
			}});
		}
		catch(Exception e)
		{
			LogManager.push("Exception In reVERSALDAOImpl - getHdfcReversalsList searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+selectQry);		
		return list; 
	}

	public List getScbReversalsList(ReversalFormBean sbean) throws CommonBaseException {
		List list=null;
		arg = new Object[1];
		try{
			selectQry = "select to_char(DEPOSIT_DATE,'dd/mm/yyyy') DEPOSIT_DATE,DEPOSIT_NO,CHEQUE_NO,CHQ_AMOUNT from scb_bank where receipt_sl_no=-99999 and deposit_date=to_date(?,'dd/mm/yyyy') order by DEPOSIT_NO";
			arg[0] = sbean.getDepositdate();
			list = this.mytemplate.query(selectQry, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			ScbReversalVB sVB = new ScbReversalVB();
			sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
			sVB.setDepSlipNo(rset.getString("DEPOSIT_NO"));
			sVB.setChequeNo(rset.getString("CHEQUE_NO"));
			sVB.setChequeAmt(rset.getString("CHQ_AMOUNT"));
			return sVB;
			}});
		}
		catch(Exception e)
		{
			LogManager.push("Exception In reVERSALDAOImpl - getSCBReversalsList searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+selectQry);
		LogManager.push("Exit scb reversal list");
		return list; 
	}

	
	public List getAxisReversalsList(ReversalFormBean sbean) throws CommonBaseException {
		List list=null;		
		arg = new Object[1];
		try{
			selectQry="select to_char(DEPOSIT_DATE,'dd/mm/yyyy') DEPOSIT_DATE,SLIP_NO,INST_NO,INSTRUMENT_AMOUNT from axis_bank where receipt_sl_no=-99999 and deposit_date=to_date(?,'dd/mm/yyyy') order by SLIP_NO";
			arg[0] = sbean.getDepositdate();
			list = this.mytemplate.query(selectQry, arg, new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			ScbReversalVB sVB = new ScbReversalVB();
			sVB.setDepositDate(rset.getString("DEPOSIT_DATE"));
			sVB.setDepSlipNo(rset.getString("SLIP_NO"));
			sVB.setChequeNo(rset.getString("INST_NO"));
			sVB.setChequeAmt(rset.getString("INSTRUMENT_AMOUNT"));
			return sVB;
			}});						
		}
		catch(Exception e)
		{
			LogManager.push("Exception In reVERSALDAOImpl - getSCBReversalsList searchdao(): "+e);
		}
		LogManager.push("QUERY executed "+ selectQry);
		LogManager.push("ExitgetAxisReversalsList list");
		return list; 
	}

}