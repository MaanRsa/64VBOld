package com.maan.admin.bankAcct;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.maan.admin.master.error.ErrorDAO;
import com.maan.admin.master.error.ErrorVB;
import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;

public class BankAcctDAOImpl extends CommonBaseDAOImpl implements BankAcctDAO {
	
	
		
	public List getBankAcctDetails() throws CommonBaseException
	{
		LogManager.push("BankDAOImpl getBankAcctDetails() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
		

		final String query="SELECT (SELECT BANK_NAME FROM BANK_MASTER A WHERE A.BANK_ID=B.BANK_ID ) AS BANK_ID,BANK_ACCOUNT_CODE,BANK_ACCOUNT_NUMBER,STATUS FROM BANK_ACCOUNT_CODE B WHERE BANK_ID IN (SELECT BANK_ID FROM BANK_MASTER WHERE STATUS='Y ')order by BANK_ID";
		
		 list = (ArrayList)this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
				
				final BankAcctVB bankAcctVB = new BankAcctVB();
				
				//cForm.setReceiptNo((String)(map.get("RECEIPT_NO")==null?"":map.get("RECEIPT_NO") + ""));
				bankAcctVB.setStatus(rs.getString("STATUS").equalsIgnoreCase("Y ")?"Yes":"No");
				bankAcctVB.setBankid(rs.getString("BANK_ID"));
				bankAcctVB.setBankAcctCode(rs.getString("BANK_ACCOUNT_CODE"));
				bankAcctVB.setBankAcctNo(rs.getBigDecimal("BANK_ACCOUNT_NUMBER"));
				return bankAcctVB;
			}
		});
		
		LogManager.logExit();
		LogManager.push("BankDAOImpl getBankAcctDetails() method  Ends");
		LogManager.popRemove(); 
		return list;
	}
	
	public int insertBankAcctDetails(BankAcctVB sVB)throws CommonBaseException
	{
		
		LogManager.push("BankDAOImpl insertBankAcctDetails() method Starts");
		LogManager.logEnter();
		final String bankid=sVB.getBankid();
		final String acctcode=sVB.getBankAcctCode();
		final BigDecimal acctno=sVB.getBankAcctNo();
		final String active=sVB.getStatus();
		final String valquery="SELECT COUNT(*) FROM BANK_ACCOUNT_CODE WHERE BANK_ACCOUNT_CODE='"+acctcode+"' OR BANK_ACCOUNT_NUMBER='"+acctno+"'";
		Object countresult=this.mytemplate.queryForObject(valquery, new RowMapper(){
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				
			return rset.getString(1);
		}
		});
		LogManager.push("count result:"+countresult.toString());
		if(countresult.toString().equalsIgnoreCase("0"))
			
		{
		final String query="INSERT INTO BANK_ACCOUNT_CODE (BANK_ID,BANK_ACCOUNT_CODE,BANK_ACCOUNT_NUMBER,STATUS) VALUES ('"+bankid+"','"+acctcode+"','"+acctno+"','"+active+"')";
		int result=this.mytemplate.update(query);
		LogManager.logExit();
		LogManager.push("BankDAOImpl insertBankAcctDetails() method  Ends");
		LogManager.popRemove(); 
		return result;
		}
		else
		return 2;
		
	}
	public List getEditBankAcctDetails(String bankid)throws CommonBaseException
	{
		LogManager.push("BankDAOImpl getEditBankAcctDetails() method Starts");
		LogManager.logEnter();
		List list=null;
		final String query="select BANK_ID,BANK_ACCOUNT_CODE,BANK_ACCOUNT_NUMBER,STATUS from BANK_ACCOUNT_CODE where BANK_ACCOUNT_CODE='"+bankid+"' ORDER BY BANK_ACCOUNT_CODE" ;
		LogManager.push(query);
		list = (List)this.mytemplate.queryForList(query);
		LogManager.logExit();
		LogManager.push("BankDAOImpl getEditBankAcctDetails() method  Ends");
		LogManager.popRemove(); 
		return list ;
	}
	
	
	
	public int updateBankAcctDetails(BankAcctVB sVB,String bankacctcode)throws CommonBaseException
	{
		
		LogManager.push("BankDAOImpl updateBankDetails() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
		
		final String bankid=sVB.getBankid();
		final BigDecimal acctno=sVB.getBankAcctNo();
		final String active=sVB.getStatus();
		
		final String query="UPDATE BANK_ACCOUNT_CODE set BANK_ID='"+bankid+"',BANK_ACCOUNT_NUMBER='"+acctno+"',STATUS='"+active+"' where BANK_ACCOUNT_CODE='"+bankacctcode+"'";
		
		LogManager.push("query updated:"+query);
		int result=this.mytemplate.update(query);
		
		LogManager.logExit();
		LogManager.push("BankDAOImpl updateBankDetails() method  Ends");
		LogManager.popRemove(); 
		return result;
		
		
	}


	public Map getBankList() throws CommonBaseException {
		LogManager.push("BankAcctDAoImpl getBankList() method enter");
		LogManager.logEnter();
		Map result = new HashMap();
		try{
			final String query = getQuery(DBConstants.BANK_LIST1);
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
		LogManager.push("BankAcctDAoImpl getBankList() method exit");
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

	

}
