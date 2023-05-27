package com.maan.admin.branchMaster;

import com.maan.common.MotorBaseDAO;
import com.maan.common.exception.MotorBaseException;

import java.util.*;




public interface branchMasterDAO extends MotorBaseDAO
{

	List getBranchDetails()throws MotorBaseException;

	List getEditBranch(String branchId)throws MotorBaseException;

	void getInsertBranch(branchMasterBean beanObj)throws MotorBaseException;

	void getUpdatetBranch(branchMasterBean beanObj)throws MotorBaseException;

	



	

}
