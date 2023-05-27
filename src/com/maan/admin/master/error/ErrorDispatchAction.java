package com.maan.admin.master.error;

//import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.maan.common.LogManager;
import com.maan.common.base.AbstractCommonBaseDispatchAction;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;
import com.maan.common.use.CommonCB;

public class ErrorDispatchAction extends AbstractCommonBaseDispatchAction 
{	
	private final static String PATH="path";
	private final static String EOVDS="EditOrViewErrorDetails";
	private final static String INSOPT="InsOpt";
	public ActionForward ErrorList(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException
	{
		 ActionForward forward = null;
		 final ErrorCB errorCB = new ErrorCB();
		 
		 try{
			 LogManager.debug("ErrorDispatchAction Controller ErrorList method()- Enter");
			 final List list = errorCB.getErrorListCB();
			 request.setAttribute("list",list);
			 request.setAttribute(PATH,"ErrorList");
	    	 forward = mapping.findForward(EOVDS);			 
		 }
		 catch (Exception e) {		    
				LogManager.debug(e);
				throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		 }
		 finally {
				LogManager.debug("ErrorDispatchAction Controller ErrorList method()- Exit");
				LogManager.popRemove();
		}
		return forward;	
	}	
	
	public ActionForward newError(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException
	{
		ActionForward forward = null;
		final ErrorForm errorForm = (ErrorForm)form;
		final CommonCB commoncb = new CommonCB();
		final HttpSession session = request.getSession(true);
		try{
			LogManager.debug("ErrorDispatchAction Controller newError method()- Enter");
			final int errorId = commoncb.getMaxID("ERROR_MASTER", "ERROR_ID");
			
			session.setAttribute(INSOPT,INSOPT);
			errorForm.setActive("");
			errorForm.setErrorCode("");
			errorForm.setErrorDesc("");
			errorForm.setErrorId(Integer.toString(errorId));
			errorForm.setMethod("");
			errorForm.setMode("add");
			errorForm.setSearchBy("");
			errorForm.setSearchValue("");
			errorForm.setUpdateErrorId("");
			
			request.setAttribute(PATH, "NewError");
			forward = mapping.findForward(EOVDS);		
			
		}	
		catch (Exception e) {		    
			LogManager.debug(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		finally {
			LogManager.debug("ErrorDispatchAction Controller newError method()- Exit");
			LogManager.popRemove();
		}
		
		return forward;
	}
	
	public ActionForward SubmitError(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException
	{		
	    ActionForward forward = null;
	    ErrorForm errorForm = (ErrorForm)form;
	    final ErrorVB  errorVB = new ErrorVB();
	    final ErrorCB errorCB = new ErrorCB();	    
	    final HttpSession session =request.getSession(true);
	    String result = "";
	    try{
	    	LogManager.debug("ErrorDispatchAction Controller SubmitError method()- Enter");
	    	
	    	ActionErrors error =  new ActionErrors();
	    	
	    	error =  validString(errorForm.getErrorCode(),"errorCode",error);
	    	error = validString(errorForm.getErrorDesc(),"errorDesc",error);
	    	error = validString(errorForm.getActive(),"active",error);
	    	if(error.isEmpty()){
	    		BeanUtils.copyProperties(errorVB, errorForm);
	    		
	    		if(INSOPT.equalsIgnoreCase((String)session.getAttribute(INSOPT))){
		    		if("add".equalsIgnoreCase(errorForm.getMode()) && !"0".equalsIgnoreCase(errorForm.getErrorId())){
		    			result = errorCB.insertErrorDetailsCB(errorVB);
		    			errorForm = new ErrorForm();
		    		}
		    		else if("update".equalsIgnoreCase(errorForm.getMode()) && !"0".equalsIgnoreCase(errorForm.getErrorId())){
		    			result = errorCB.updateErrorDetailsCB(errorVB);
		    			errorForm = new ErrorForm();
		    		}    
	    		}
	    		
	    		errorForm.setActive("");
				errorForm.setErrorCode("");
				errorForm.setErrorDesc("");
				errorForm.setErrorId("0");
				errorForm.setMethod("");
				errorForm.setMode("");
				errorForm.setSearchBy("");
				errorForm.setSearchValue("");
				errorForm.setUpdateErrorId("");
				
				errorForm = new ErrorForm();
	    		
				final List list = errorCB.getErrorListCB();
	    		request.setAttribute("ErrorTransactionResult",result);
	    		request.setAttribute("list",list);	
	    		request.setAttribute(PATH,"ErrorList");
	    		session.removeAttribute(INSOPT);
	    		forward = mapping.findForward(EOVDS);	 
	    	}
	    	else{
	    		saveMessages(request, error);
				saveErrors(request, error);
				request.setAttribute(PATH, "NewError");
				forward = mapping.findForward(EOVDS);		
	    	}	    	
	    }
	    catch (Exception e) {		    
			LogManager.debug(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		finally {
			LogManager.debug("ErrorDispatchAction Controller SubmitError method()- Exit");
			LogManager.popRemove();
		}	    
	    return forward;
	}
	
	
	public ActionForward ErrorSearch(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException
	{
		ActionForward forward = null;
		final ErrorForm errorForm = (ErrorForm)form;
		final ErrorCB errorCB = new ErrorCB();
		List list = null;
		try{
			LogManager.debug("ErrorDispatchAction Controller ErrorSearch method()- Enter");
			ActionErrors errors = new ActionErrors();
			
			errors = validString(errorForm.getSearchBy(),"searchBy",errors);
			errors = validString(errorForm.getSearchValue(),"searchValue",errors);
			
			if(errors.isEmpty()){
			      if("errorcode".equalsIgnoreCase(errorForm.getSearchBy())){
			    	 list =  errorCB.getSearchByErrorCodeCB(errorForm.getSearchValue());
			      }
			      else if("errorname".equalsIgnoreCase(errorForm.getSearchBy())){
			    	 list = errorCB.getSearchByErrorDescCB(errorForm.getSearchValue());     
			      }
			}
			else{
				list = errorCB.getErrorListCB();   		
	    		saveMessages(request, errors);
				saveErrors(request, errors);
			}	
			request.setAttribute("list",list);
			request.setAttribute(PATH, "ErrorList");
			forward = mapping.findForward(EOVDS);
		}
		catch (Exception e) {		    
			LogManager.debug(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		finally {
			LogManager.debug("ErrorDispatchAction Controller ErrorSearch method()- Exit");
			LogManager.popRemove();
		}
		return forward;
	}
	
	public ActionForward UpdateError(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException
	{
		 ActionForward forward = null;		 
		 final ErrorCB errorCB = new ErrorCB();
		 final ErrorForm errorForm = (ErrorForm)form;
		 final HttpSession session = request.getSession(true);
		 try{
			 LogManager.debug("ErrorDispatchAction Controller UpdateError method()- Enter");
			 session.setAttribute(INSOPT,INSOPT);
			 final List list =  errorCB.getEditErrorDetailsCB(errorForm.getUpdateErrorId());
			 final Iterator iterator = list.iterator();
			 while (iterator.hasNext()) {
				 final ErrorVB errorVB = (ErrorVB)iterator.next();	
					errorForm.setErrorId(errorVB.getErrorId());
					errorForm.setErrorCode(errorVB.getErrorCode());
					errorForm.setErrorDesc(errorVB.getErrorDesc());
					errorForm.setActive(errorVB.getActive());				
 			 }
			 errorForm.setMode("update");
			 request.setAttribute(PATH, "NewError");
			 forward = mapping.findForward(EOVDS);
			 
			 
		 }
		 catch (Exception e) {		    
				LogManager.debug(e);
				throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		 }
		 finally {
				LogManager.debug("ErrorDispatchAction Controller UpdateError method()- Exit");
				LogManager.popRemove();
		}
		 return forward;		 
	}
	
	private ActionErrors validString(final String value, final String field,final ActionErrors errors) {
		try {
			LogManager.push("ErrorDispatchAction Controller validString method() - Enter");
	
			if (value == null || "".equalsIgnoreCase(value) || "Select".equalsIgnoreCase(value) || "null".equalsIgnoreCase(value)) {				
				errors.add(field, new ActionError("error.error." + field));
			}	
		} 
		catch (Exception e) {
			LogManager.debug(e);	
		}
		finally {
			LogManager.debug("ErrorDispatchAction Controller validString method()- Exit");
			LogManager.popRemove();
		}	
		return errors;
	}
}