package com.maan.admin.bankError;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;

public class BankErrorDAOImpl extends CommonBaseDAOImpl implements BankErrorDAO {
	
	public List getBankErrorDetails() throws CommonBaseException
	{
		LogManager.push("BankErrorDAOImpl getBankErrorDetails() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
		final String query="select SL_NO,(SELECT BANK_NAME FROM BANK_MASTER A WHERE A.BANK_ID=B.BANK_ID) AS BANK_ID,REASON_DESC,(SELECT REJECTION_TYPE_DESC FROM REJECTION_MASTER C WHERE B.REJECTION_TYPE=C.REJECTION_TYPE_ID) REJECTION_TYPE,STATUS FROM BANK_RETURN_MASTER B order by B.SL_NO";
		//LogManager.push("Query for display errors"+query);
		 list = (ArrayList)this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
				
				final BankErrorVB bankErrorVB = new BankErrorVB();
				
				bankErrorVB.setActive(rs.getString("STATUS").equalsIgnoreCase("Y ")?"Yes":"No");
				bankErrorVB.setBankid(rs.getString("BANK_ID"));
				bankErrorVB.setReasondesc(rs.getString("REASON_DESC"));
				bankErrorVB.setRejectionType(rs.getString("REJECTION_TYPE"));
				bankErrorVB.setErrorid(rs.getString("SL_NO"));
				
				return bankErrorVB;
			}
		});
		
		LogManager.logExit();
		LogManager.push("BankErrorDAOImpl getBankErrorDetails() method  Ends");
		LogManager.popRemove(); 
		return list;
	}
	
	
	public int insertBankErrorDetails(BankErrorVB sVB)throws CommonBaseException
	{
		
		LogManager.push("BankErrorDAOImpl insertBankErrorDetails() method Starts");
		LogManager.logEnter();
		//ArrayList list=null;
		final String bankid=sVB.getBankid();
		final String reasondesc=sVB.getReasondesc();
		final String rejectionType=sVB.getRejectionType();
		final String active=sVB.getActive();
		final String countquery="SELECT COUNT(*) FROM BANK_RETURN_MASTER WHERE REASON_DESC='"+reasondesc+"' AND BANK_ID='"+bankid+"'";
		Object countresult=this.mytemplate.queryForObject(countquery, new RowMapper(){
			public Object mapRow(ResultSet rs,int arg) throws SQLException
			{
				return rs.getString(1);
			}
		});
		LogManager.push("count res:"+countresult.toString());
		if(countresult.toString().equalsIgnoreCase("0"))
		{
		final String query="INSERT INTO BANK_RETURN_MASTER (SL_NO,BANK_ID,REASON_DESC,REJECTION_TYPE,STATUS) VALUES(BANK_RETURN_SEQ.NEXTVAL,'"+bankid+"','"+reasondesc+"','"+rejectionType+"','"+active+"')";
		int result=this.mytemplate.update(query);
		LogManager.logExit();
		LogManager.push("BankErrorDAOImpl insertBankErrorDetails() method  Ends");
		LogManager.popRemove(); 
		return result;
		}
		else
		{
			return 2;
		}
		
		
	}
	
	
	public List getEditBankErrorDetails(String errorid)throws CommonBaseException
	{
		LogManager.push("BankErrorDAOImpl getEditBankErrorDetails() method Starts");
		LogManager.logEnter();
		List list=null;
		

		final String query="select BANK_ID,REASON_DESC,REJECTION_TYPE,STATUS FROM BANK_RETURN_MASTER where SL_NO='"+errorid+"' ORDER BY BANK_ID" ;
		
		list = (List)this.mytemplate.queryForList(query);
		
		LogManager.logExit();
		LogManager.push("BankErrorDAOImpl getEditBankErrorDetails() method  Ends");
		LogManager.popRemove(); 
		
		return list ;
	}

	public int updateBankErrorDetails(BankErrorVB sVB,String errorid)throws CommonBaseException
	{
		
		LogManager.push("BankErrorDAOImpl updateBankErrorDetails() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
				
		final String bankid=sVB.getBankid();
		final String reasondesc=sVB.getReasondesc();
		final String rejectionType=sVB.getRejectionType();
		final String active=sVB.getActive();
		final String query="UPDATE BANK_RETURN_MASTER SET BANK_ID='"+bankid+"',REASON_DESC='"+reasondesc+"',REJECTION_TYPE='"+rejectionType+"',STATUS='"+active+"' where SL_NO='"+errorid+"'";
		int result=this.mytemplate.update(query);
		
		LogManager.logExit();
		LogManager.push("BankErrorDAOImpl updateBankErrorDetails() method  Ends");
		LogManager.popRemove(); 
		return result;
		
		
	}

	public Map getBankRejectionList() throws CommonBaseException {
		LogManager.push("BankErrorDAoImpl getBankRejectionList() method enter");
		LogManager.logEnter();
		Map result = new HashMap();
		

		try{
			final String query = "SELECT REJECTION_TYPE_ID,REJECTION_TYPE_DESC FROM REJECTION_MASTER WHERE STATUS='Y '";
			final List list = this.mytemplate.queryForList(query);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					final Map temp = (Map)list.get(i);
					result.put(temp.get("REJECTION_TYPE_ID"), temp.get("REJECTION_TYPE_DESC"));
				}
			}
		}catch(Exception e){
			LogManager.fatal(e);
		}
		LogManager.push("BankErrorDAoImpl getBankRejectionList() method exit");
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}

}
