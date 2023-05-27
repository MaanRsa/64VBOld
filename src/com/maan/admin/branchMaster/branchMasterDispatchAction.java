package com.maan.admin.branchMaster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
//import org.springframework.beans.BeanUtils;


import org.apache.commons.beanutils.BeanUtils; 

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

import com.maan.common.AbstractMotorBaseDispatchAction;
import com.maan.common.LogManager;
import com.maan.common.exception.MotorBaseException;


public class branchMasterDispatchAction extends AbstractMotorBaseDispatchAction
{
	ActionForward forward;
	private final static String PATH="branchMaster";
	private final static String SHOW="page";
	public ActionForward InitBranch(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws MotorBaseException
	{
		
		LogManager.push("====>Method Enter Into InitBranch====>");
		
		final branchMasterForm cForm=(branchMasterForm) form;
		cForm.setBranch_Code("");
		cForm.setBranch_Name("");
		branchMasterBean beanObj=new branchMasterBean();
		final branchMasterCB sCB=new branchMasterCB();
	    List list=sCB.getBranchDetails();
		request.setAttribute("lists",list);
		request.setAttribute(SHOW,"Display");
		LogManager.push("====>Method Exit====>");
		forward=mapping.findForward(PATH);
		
		return forward;
	}
	public ActionForward addNewBankMaster(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws MotorBaseException
	{
		
		
		LogManager.push("====>Method addNewBankMaster Exit====>");
		request.setAttribute(SHOW,"addNew");
		LogManager.push("====>Method Exit====>");
		forward=mapping.findForward(PATH);
		return forward;
	}
	public ActionForward InsertBranchMaster(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws MotorBaseException
	{
		LogManager.push("Method Enter Into InsertBranchMaster");
		LogManager.push("---Method into InsertBranchMaster----");
		 branchMasterForm formObj=(branchMasterForm) form;
		 /*formObj.setBranch_Code("");
		 formObj.setBranch_Name("");
		*/ branchMasterBean beanObj=new branchMasterBean();
		final branchMasterCB sCB=new branchMasterCB();
		ActionErrors errors = new ActionErrors();
		final String branchName=formObj.getBranch_Name(); 
		final String branchCode=formObj.getBranch_Code();
		errors = validation(branchName,"bName",errors);
		errors = validation(branchCode,"bCode",errors);
		if(errors.isEmpty())
		{
			LogManager.push("Method Enter Into Insert Block");
			
			try 
			{
				BeanUtils.copyProperties(beanObj,formObj);
			}
			catch (IllegalAccessException e) 
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e) 
			{
				e.printStackTrace();
			}
		   /* LogManager.push("===>"+beanObj.getBranch_Name());
		    LogManager.push("===>SS"+beanObj.getBranch_Code());*/
		    sCB.getInsertBranch(beanObj);
			List list=sCB.getBranchDetails();
			request.setAttribute("lists",list);
			request.setAttribute(SHOW,"Display");
		}
		else
		{
			saveMessages(request, errors);
			saveErrors(request, errors);
			request.setAttribute(SHOW,"addNew");
		}
		forward = mapping.findForward(PATH);
		LogManager.push("---Method into InsertBranchMaster----Exit");
		return forward;
	}
	private ActionErrors validation(String value, String field, ActionErrors errors) 
	{
		try
		{
			LogManager.push("Method Enter Into Validation Block");
			LogManager.push("Method Enter Into Validation Block");
			if(value == null || value.length()<1)
			{
				errors.add(field,new ActionError("error.branchmaster." +field+ ".required"));
			}
			
		}
		catch (Exception exe)
		{
			LogManager.debug(exe);
		}
		finally {
			LogManager.debug("BranchMasterForm Controller validation- Exit");
			LogManager.popRemove();
		}
		return errors;
	}
	
	public ActionForward EditBranchMaster(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws MotorBaseException
	{
		LogManager.push("Method Enter Into EditBranchMaster");
		System.out.print("Method Enter Into EditBranchMaster:::"+request.getParameter("branch_Id"));
		String branchId=request.getParameter("branch_Id")==null?"":request.getParameter("branch_Id");
		final branchMasterForm formObj=(branchMasterForm) form;
		final branchMasterCB sCB=new branchMasterCB();
		List list=(List)sCB.getEditBranch(branchId);
		 Map mp;
		 mp=(Map)list.get(0);
		 formObj.setBranch_Name((String)mp.get("BRANCH_NAME")+"");
		 formObj.setBranch_Code((String)mp.get("BRANCH_CODE")+"");
		 formObj.setBranch_Id((BigDecimal)mp.get("BRANCH_ID")+"");
		 LogManager.push("=======================>"+formObj.getBranch_Id());
		 request.setAttribute(SHOW,"Edit");
		 LogManager.push("===>EditBranchMaster------->Exit===>");
		 forward=mapping.findForward(PATH);
	 	 return forward;
	}
	public ActionForward UpdateBranchMaster(final ActionMapping mapping,final ActionForm form,final HttpServletRequest request,final HttpServletResponse response)throws MotorBaseException
	{
		LogManager.push("Method Enter Into UpdateBranchMaster");
		LogManager.push("---Method into UpdateBranchMaster----");
		 branchMasterForm formObj=(branchMasterForm) form;
		 branchMasterBean beanObj=new branchMasterBean();
		final branchMasterCB sCB=new branchMasterCB();
		ActionErrors errors = new ActionErrors();
		final String branchName=formObj.getBranch_Name(); 
		final String branchCode=formObj.getBranch_Name();
		errors = validation(branchName,"bName",errors);
		errors = validation(branchCode,"bCode",errors);
		if(errors.isEmpty())
		{
			LogManager.push("Method Enter Into Insert Block");
			
		    //BeanUtils.copyProperties(
			try {
				BeanUtils.copyProperties(beanObj,formObj);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 LogManager.push("===>"+beanObj.getBranch_Name());
			    LogManager.push("===>SS"+beanObj.getBranch_Code());
			    sCB.getUpdateBranch(beanObj);
				List list=sCB.getBranchDetails();
				request.setAttribute("lists",list);
				request.setAttribute(SHOW,"Display");
		}
			else
			{
				saveMessages(request, errors);
				saveErrors(request, errors);
				request.setAttribute(SHOW,"Edit");
			}
		forward = mapping.findForward(PATH);
		return forward;
	}
}
