package com.maan.reversal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import com.maan.common.LogManager;
import com.maan.common.exception.CommonBaseException;

public class ReversalDispatchAction extends DispatchAction {

	private String forward;
	ReversalCB scb=new ReversalCB();
	private String validateResult="";
	private ActionErrors errors = new ActionErrors();
	final com.maan.common.Validation validate = new com.maan.common.Validation();
	
	public ActionForward init(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Search Start");
		ActionForward forward = null;
		final String typeid=request.getParameter("typeid");
		if(typeid.equalsIgnoreCase("101"))
			request.setAttribute("PartToShow","ReceiptReversalSearch");
		else if(typeid.equalsIgnoreCase("102"))
			request.setAttribute("PartToShow","CitiReversalSearch");
		else if(typeid.equalsIgnoreCase("103"))
			request.setAttribute("PartToShow","HdfcReversalSearch");
		else if(typeid.equalsIgnoreCase("105"))
			request.setAttribute("PartToShow","ScbReversalSearch");
		else if(typeid.equalsIgnoreCase("106"))
			request.setAttribute("PartToShow","AxisReversalSearch");
		else if(typeid.equalsIgnoreCase("108"))
			request.setAttribute("PartToShow","HsbcReversalSearch");
		else if(typeid.equalsIgnoreCase("115"))
			request.setAttribute("PartToShow","KotakReversalSearch");

		final ReversalCB sCB = new ReversalCB();
		//ReversalFormBean sForm=new ReversalFormBean();
		//sForm.setFromDate("");
		//sForm.setToDate("");
		//sForm.reset(mapping, request);
		forward = mapping.findForward("reversaldetail"); 		
		return forward;
	}
	
	public ActionForward receiptSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			LogManager.push("receipt Search Enter");
			List list = new ArrayList();
			
	        validateResult=scb.validateSearchInfo(sbean,validateResult);
	          if("".equalsIgnoreCase(validateResult))
	            {
	        	  LogManager.push("success");
	            	try{
	            		list=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            	}catch(Exception e){
	            		LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);
	            	}
	            	request.setAttribute("PartToShow","ReceiptSearchResult");
	            	LogManager.push(request.getRequestURI());
	            }
	            else
	            {
	            	LogManager.push("Error");
	            	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {validateResult}));
	            	request.setAttribute("PartToShow","ReceiptReversalSearch");
	            }
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");			
				validateResult="";
				//sbean.setSearchResult(lists);
			
				request.setAttribute("searchResult", list);
				//request.setAttribute("receiptSearchResult", receiptlist);
				return mapping.findForward(forward);
	}
	
	public ActionForward receiptReversals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			 ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			LogManager.push("receipt Search Enter");
			List list = new ArrayList();
			 
	        	  LogManager.push("success");
	            	try{
	            		list=scb.getReceiptReversalsList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}	            	
	            	request.setAttribute("PartToShow","ReceiptReversals");	            	
	            	LogManager.push(request.getRequestURI());	           
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}
	
	public ActionForward citiSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			 ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			
			List list = new ArrayList();
			//List banklist = new ArrayList();
			//List receiptlist = new ArrayList();
			//errors = validateForm(sbean);
			
	        validateResult=scb.validateSearchInfo(sbean,validateResult);
			
	          if("".equalsIgnoreCase(validateResult))
	            {
	            	try{
	            		list=scb.getCitiSearchList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            	request.setAttribute("PartToShow","CitiSearchResult");
	            	request.setAttribute("PartToShow1","");
	            	LogManager.push(request.getRequestURI());
	            }
	            else
	            {
	            	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {validateResult}));
	            	request.setAttribute("PartToShow","CitiReversalSearch");
	            	sbean.setPartToShow("Search");
	            }
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}

	public ActionForward citiReversals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			 ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			LogManager.push("receipt Search Enter");
			List list = new ArrayList();
			 
	        	  LogManager.push("success");
	            	try{
	            		list=scb.getCitiReversalsList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	request.setAttribute("PartToShow","CitiReversals");
	            	LogManager.push(request.getRequestURI());
	            	
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}
	
	public ActionForward hdfcSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			
			List list = new ArrayList();
			//List banklist = new ArrayList();
			//List receiptlist = new ArrayList();
			//errors = validateForm(sbean);
			
	        validateResult=scb.validateSearchInfo(sbean,validateResult);
			
	          if("".equalsIgnoreCase(validateResult))
	            {
	            	try{
	            		list=scb.getHdfcSearchList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	request.setAttribute("PartToShow","HdfcSearchResult");
	            	request.setAttribute("PartToShow1","");
	            	LogManager.push(request.getRequestURI());
	            }
	            else
	            {
	            	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {validateResult}));
	            	request.setAttribute("PartToShow","HdfcReversalSearch");
	            	sbean.setPartToShow("Search");
	            }
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}
	
	
	public ActionForward kotakSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			
			List list = new ArrayList();
			//List banklist = new ArrayList();
			//List receiptlist = new ArrayList();
			//errors = validateForm(sbean);
			
	        validateResult=scb.validateSearchInfo(sbean,validateResult);
			
	          if("".equalsIgnoreCase(validateResult))
	            {
	            	try{
	            		list=scb.getKotakSearchList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	request.setAttribute("PartToShow","KotakSearchResult");
	            	request.setAttribute("PartToShow1","");
	            	LogManager.push(request.getRequestURI());
	            }
	            else
	            {
	            	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {validateResult}));
	            	request.setAttribute("PartToShow","KotakReversalSearch");
	            	sbean.setPartToShow("Search");
	            }
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}
	
	public ActionForward hsbcSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			
			List list = new ArrayList();
			//List banklist = new ArrayList();
			//List receiptlist = new ArrayList();
			//errors = validateForm(sbean);
			
	        validateResult=scb.validateSearchInfo(sbean,validateResult);
	          if("".equalsIgnoreCase(validateResult))
	            {
	            	try{
	            		list=scb.getHsbcSearchList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	request.setAttribute("PartToShow","HsbcSearchResult");
	            	request.setAttribute("PartToShow1","");
	            	LogManager.push(request.getRequestURI());
	            }
	            else
	            {
	            	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {validateResult}));
	            	request.setAttribute("PartToShow","HsbcReversalSearch");
	            	sbean.setPartToShow("Search");
	            }
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}
	
	public ActionForward hdfcReversals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			 ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			LogManager.push("receipt Search Enter");
			List list = new ArrayList();
			 
	        	  LogManager.push("success");
	            	try{
	            		list=scb.getHdfcReversalsList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            	request.setAttribute("PartToShow","HdfcReversals");
	            	LogManager.push(request.getRequestURI());
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}
	
	public ActionForward kotakReversals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			 ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			LogManager.push("receipt Search Enter");
			List list = new ArrayList();
			 
	        	  LogManager.push("success");
	            	try{
	            		list=scb.getKotakReversalsList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            	request.setAttribute("PartToShow","KotakReversals");
	            	LogManager.push(request.getRequestURI());
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}
	
	public ActionForward hsbcReversals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			 ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			LogManager.push("receipt Search Enter");
			List list = new ArrayList();
			 
	        	  LogManager.push("success");
	            	try{
	            		list=scb.getHsbcReversalsList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            	request.setAttribute("PartToShow","HsbcReversals");
	            	LogManager.push(request.getRequestURI());
	            	
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}
	
	public ActionForward scbSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			 ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			
			List list = new ArrayList();
			//List banklist = new ArrayList();
			//List receiptlist = new ArrayList();
			//errors = validateForm(sbean);
			
	        validateResult=scb.validateSearchInfo(sbean,validateResult);
			
	          if("".equalsIgnoreCase(validateResult))
	            {
	            	try{
	            		list=scb.getScbSearchList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            	request.setAttribute("PartToShow","ScbSearchResult");
	            	request.setAttribute("PartToShow1","");
	            	LogManager.push(request.getRequestURI());
	            }
	            else
	            {
	            	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {validateResult}));
	            	request.setAttribute("PartToShow","ScbReversalSearch");
	            	sbean.setPartToShow("Search");
	            }
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}

	public ActionForward scbReversals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			 ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			LogManager.push("receipt Search Enter");
			List list = new ArrayList();
			 
	        	  LogManager.push("success");
	            	try{
	            		list=scb.getScbReversalsList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	request.setAttribute("PartToShow","ScbReversals");
	            	LogManager.push(request.getRequestURI());
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}

	public ActionForward axisSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			 ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			
			List list = new ArrayList();
			//List banklist = new ArrayList();
			//List receiptlist = new ArrayList();
			//errors = validateForm(sbean);
			
	        validateResult=scb.validateSearchInfo(sbean,validateResult);
	          if("".equalsIgnoreCase(validateResult))
	            {
	            	try{
	            		list=scb.getAxisSearchList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	request.setAttribute("PartToShow","AxisSearchResult");
	            	request.setAttribute("PartToShow1","");
	            	LogManager.push(request.getRequestURI());
	            }
	            else
	            {
	            	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {validateResult}));
	            	request.setAttribute("PartToShow","ScbReversalSearch");
	            	sbean.setPartToShow("Search");
	            }
	            saveMessages(request,errors);
	            saveErrors(request,errors);
	            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}

	public ActionForward axisReversals(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			ReversalFormBean sbean = (ReversalFormBean)form;
			ActionErrors errors = new ActionErrors();
			ReversalCB scb=new ReversalCB();
			forward="reversaldetail";
			LogManager.push("receipt Search Enter");
			List list = new ArrayList();
        	LogManager.push("success");
        	try{
        		list=scb.getAxisReversalsList(sbean);
        	//	banklist=scb.getBankSearchList(sbean);
        	//	receiptlist=scb.getReceiptSearchList(sbean);
        		LogManager.push("Bank LIST retrieved size:"+list);
        		//LogManager.push("Receipt LIST retrieved size:"+list);
        	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
        	request.setAttribute("PartToShow","AxisReversals");
        	LogManager.push(request.getRequestURI());
            saveMessages(request,errors);
            saveErrors(request,errors);
            //sbean.setFromJsp("");
			
			validateResult="";
			//sbean.setSearchResult(lists);
			
			request.setAttribute("searchResult", list);
			//request.setAttribute("receiptSearchResult", receiptlist);
			
			return mapping.findForward(forward);
	}

	private ActionErrors validateForm(ReversalFormBean sbean,final String[] chequeNo,final String[] chequeAmt ) throws CommonBaseException{
		ActionErrors errors = new ActionErrors();
		String result;
		for(int i=0;i<chequeNo.length;i++)
		{
			if(isString(chequeNo[i]) && chequeNo[i]!=null && chequeNo[i]!="" )
			{
					//result="Enter Cheque no. in numbers only";
					errors.add("searchFormBean", new ActionError("search.update.chequeno.invalid"));			
			}
			 if(isString(chequeAmt[i]) && chequeAmt[i]!=null && chequeAmt[i]!="")
			{
					//result="Enter Cheque amount in numbers only";
					errors.add("searchFormBean", new ActionError("search.update.chequeamt.invalid"));			
			}
		}
		return errors;
	}
	
	public boolean isString(String arg)
	{
		boolean result=true;
		String pattern ="[0-9]+";
		Pattern p=Pattern.compile(pattern);
		Matcher m=p.matcher(arg);
		if(m.matches())
		result=false;
		return result;
	}
	
}