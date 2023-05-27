package com.maan.admin.branchMaster;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;


import com.maan.common.LogManager;
import com.maan.common.MotorBaseDAOImpl;
import com.maan.common.exception.MotorBaseException;



public class branchMasterDAOImpl extends MotorBaseDAOImpl implements branchMasterDAO
{

	public List getBranchDetails() throws MotorBaseException 
	{
		List list=null;
		LogManager.push("branchMasterDAOImpl GetBranchDetailsImpl() method Starts");
		LogManager.logEnter();
		LogManager.push("Method Enter Into GetBranchDetailsImpl");
		String query=" SELECT BRANCH_NAME,BRANCH_CODE,BRANCH_ID FROM BRANCH_MASTER ORDER BY BRANCH_NAME ";
		list=(List)this.mytemplate.query(query, new RowMapper() {
			public Object mapRow(final ResultSet rs, final int identity) throws SQLException 
			{
				branchMasterBean beanObj=new branchMasterBean();
				beanObj.setBranch_Name(rs.getString("BRANCH_NAME"));
				beanObj.setBranch_Code(rs.getString("BRANCH_CODE"));
				beanObj.setBranch_Id(rs.getString("BRANCH_ID"));
				return beanObj;
				
			}
			
		});
		LogManager.push("========>"+query);
		LogManager.push("=========>"+query);
		LogManager.logExit();
		LogManager.push("branchMasterDAOImpl GetBranchDetailsImpl() method  Ends");
		LogManager.popRemove(); 
		LogManager.push("===>Method Enter Into GetBranchDetailsImpl===>Exit");
		return list;
	}

	public List getEditBranch(String branchId) throws MotorBaseException 
	{
		List list=null;
		LogManager.push("branchMasterDAOImpl getEditBranchImpl() method Starts");
		LogManager.logEnter();
		LogManager.push("Method Enter Into GetBranchDetailsImpl");
	    String query="SELECT BRANCH_NAME,BRANCH_CODE,BRANCH_ID FROM BRANCH_MASTER  WHERE BRANCH_ID= '"+ branchId + "' ORDER BY BRANCH_NAME ";
	    LogManager.push("=========>"+query);
		LogManager.push("========>"+query);
		list=(List)this.mytemplate.queryForList(query);
		LogManager.logExit();
		LogManager.push("branchMasterDAOImpl getEditBranchImpl() method  Ends");
		LogManager.popRemove(); 
		LogManager.push("===>Method Enter Into getEditBranchImpl===>Exit");
		return list;
	}

	public void getInsertBranch(branchMasterBean beanObj) throws MotorBaseException 
	{
		LogManager.push("--->Method Enter Into GetInsertBranchImpl--->");
		LogManager.push("--->Method Enter Into GetInsertBranchImpl--->");
	    String max=" SELECT MAX(BRANCH_ID)+1 FROM  BRANCH_MASTER ";
	    int maxs=this.mytemplate.queryForInt(max);
	    LogManager.push("maxs====>"+maxs);
		String query=" INSERT INTO BRANCH_MASTER(BRANCH_NAME,BRANCH_CODE,BRANCH_ID) VALUES ('"+beanObj.getBranch_Name()+"','"+beanObj.getBranch_Code()+"','"+maxs+"')";
		LogManager.push("=====>"+query);
		this.mytemplate.execute(query);
		LogManager.push("--->Method Enter Into GetInsertBranchImpl--->");
		LogManager.push("--->Method Enter Into GetInsertBranchImpl--->");
		
	}

	public void getUpdatetBranch(branchMasterBean beanObj) throws MotorBaseException 
	{
		LogManager.push("--->Method Enter Into getUpdatetBranchImpl--->");
		LogManager.push("--->Method Enter Into getUpdatetBranchImpl--->"+beanObj.getBranch_Id());
		String query="UPDATE BRANCH_MASTER SET BRANCH_NAME='" +beanObj.getBranch_Name()+ "',BRANCH_CODE='" +beanObj.getBranch_Code()+ "' WHERE BRANCH_ID='" +beanObj.getBranch_Id()+ "'";
		this.mytemplate.execute(query);
		LogManager.push("--->Method Enter Into getUpdatetBranchImpl--->");
		LogManager.push("--->Method Enter Into getUpdatetBranchImpl--->");
		
	}

}
