package com.maan.admin.bankRejection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.maan.admin.master.error.ErrorDAO;
import com.maan.admin.master.error.ErrorVB;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;

public class BankRejectionDAOImpl extends CommonBaseDAOImpl implements BankRejectionDAO {
	
	public List getBankRejectionDetails() throws CommonBaseException
	{
		LogManager.push("BankRejectionDAOImpl getMethodDetails() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
				
		final String query="select REJECTION_ID,REJECTION_TYPE_DESC,REJECTION_TYPE_ID,STATUS FROM REJECTION_MASTER ";
		
		 list = (ArrayList)this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rs, final int identity) throws SQLException {
				
				final BankRejectionVB bankRejectionVB = new BankRejectionVB();
				
				bankRejectionVB.setStatus(rs.getString("STATUS").equalsIgnoreCase("Y ")?"Yes":"No");
				bankRejectionVB.setRejectionid(rs.getString("REJECTION_ID"));
				bankRejectionVB.setRejectiontypedesc(rs.getString("REJECTION_TYPE_DESC"));
				bankRejectionVB.setRejectiontypeid(rs.getString("REJECTION_TYPE_ID"));
				return bankRejectionVB;
			}
		});
		
		LogManager.logExit();
		LogManager.push("BankRejectionDAOImpl getBankDetails() method  Ends");
		LogManager.popRemove(); 
		return list;
	}

	public int insertBankRejectionDetails(BankRejectionVB sVB)throws CommonBaseException
	{
		
		LogManager.push("BankRejectionDAOImpl insertBankRejectionDetails() method Starts");
		LogManager.logEnter();
		//ArrayList list=null;
		final String rejectiontypeid=sVB.getRejectiontypeid();
		final String rejectiontypedesc=sVB.getRejectiontypedesc();
		final String active=sVB.getStatus();
		
		final String countquery="SELECT COUNT(*) FROM REJECTION_MASTER WHERE REJECTION_TYPE_ID='"+rejectiontypeid+"'";
		Object countresult=this.mytemplate.queryForObject(countquery, new RowMapper(){
			public Object mapRow(ResultSet rs,int arg) throws SQLException
			{
				return rs.getString(1);
			}
		});
		
		LogManager.push("count res REJ:"+countresult.toString());
		if(countresult.toString().equalsIgnoreCase("0"))
		{
		final String query="INSERT INTO REJECTION_MASTER (REJECTION_ID,REJECTION_TYPE_DESC,REJECTION_TYPE_ID,STATUS) VALUES (REJECTION_SEQUENCE.NEXTVAL,'"+rejectiontypedesc+"','"+rejectiontypeid+"','"+active+"')";
		int result=this.mytemplate.update(query);
		LogManager.logExit();
		LogManager.push("BankRejectionDAOImpl insertBankRejectionDetails() method  Ends");
		LogManager.popRemove(); 
		return result;
		}
		else
		{
			return 2;
		}
		
		
	}

	public List getEditBankRejectionDetails(String rejectid)throws CommonBaseException
	{
		LogManager.push("BankRejectionDAOImpl getEditBankRejectionDetails() method Starts");
		LogManager.logEnter();
		List list=null;
		

		final String query="select REJECTION_ID,REJECTION_TYPE_DESC,REJECTION_TYPE_ID,STATUS from REJECTION_MASTER where rejection_id='"+rejectid+"'" ;
		LogManager.push("edit query:"+query);
		list = (List)this.mytemplate.queryForList(query);
		
		LogManager.logExit();
		LogManager.push("BankRejectionDAOImpl getEditBankRejectionDetails() method  Ends");
		LogManager.popRemove(); 
		
		return list ;
	}
	
	public int updateBankRejectionDetails(BankRejectionVB sVB,String rejectid)throws CommonBaseException
	{
		
		LogManager.push("BankRejectionDAOImpl updateBankRejectionDetails() method Starts");
		LogManager.logEnter();
		ArrayList list=null;
		
		final String rejecttypedesc=sVB.getRejectiontypedesc();
		final String rejecttypeid=sVB.getRejectiontypeid();
		final String active=sVB.getStatus();
		
		final String query="update REJECTION_MASTER set REJECTION_TYPE_DESC='"+rejecttypedesc+"',REJECTION_TYPE_ID='"+rejecttypeid+"',STATUS='"+active+"' where REJECTION_ID='"+rejectid+"'";
		int result=this.mytemplate.update(query);
		
		LogManager.logExit();
		LogManager.push("BankRejectionDAOImpl updateBankRejectionDetails() method  Ends");
		LogManager.popRemove(); 
		return result;
		
		
	}

	

}
