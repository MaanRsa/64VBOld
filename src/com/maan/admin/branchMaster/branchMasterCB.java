package com.maan.admin.branchMaster;

import java.util.*;

import com.maan.common.LogManager;
import com.maan.common.MotorBaseCB;
import com.maan.common.MotorDaoFactory;
import com.maan.common.exception.MotorBaseException;


public class branchMasterCB extends MotorBaseCB
{

	public List getBranchDetails()throws MotorBaseException
	{
		List list=null;
		LogManager.push("Method Enter Into branchMasterCB");
	   final branchMasterDAO branchDAO=(branchMasterDAOImpl) MotorDaoFactory.getDAO(branchMasterDAO.class.getName());
	   list=branchDAO.getBranchDetails();
	   LogManager.push("====>Method Enter Into branchMasterCB====>Exit");
	   return list;
	}

	public List getEditBranch(String branchId)throws MotorBaseException
	{
		   List list=null;
		   LogManager.push("Method Enter Into getEditBranchCB");
		   final branchMasterDAO branchDAO=(branchMasterDAOImpl) MotorDaoFactory.getDAO(branchMasterDAO.class.getName());
		   list=branchDAO.getEditBranch(branchId);
		   LogManager.push("====>Method Enter Into getEditBranchCB====>Exit");
		   return list;
   }

	public void getInsertBranch(branchMasterBean beanObj)throws MotorBaseException
	{
		  LogManager.push("Method Enter Into getInsertBranchCB");
		   final branchMasterDAO branchDAO=(branchMasterDAOImpl) MotorDaoFactory.getDAO(branchMasterDAO.class.getName());
		   branchDAO.getInsertBranch(beanObj);
		   LogManager.push("====>Method Enter Into getInsertBranchCB====>Exit");
	}

	public void getUpdateBranch(branchMasterBean beanObj)throws MotorBaseException
	{
		LogManager.push("Method Enter Intoget UpdateBranchCB");
		   final branchMasterDAO branchDAO=(branchMasterDAOImpl) MotorDaoFactory.getDAO(branchMasterDAO.class.getName());
		   branchDAO.getUpdatetBranch(beanObj);
		   LogManager.push("====>Method Enter Into getUpdateBranchCB====>Exit");
	}


	


		

}
