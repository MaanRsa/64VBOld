package com.maan.cash;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.maan.common.exception.CommonExceptionConstants;
import com.maan.login.LoginForm;
import com.maan.upload.UploadCB;
import com.maan.upload.UploadForm;

 
public class CashDispatchAction extends DispatchAction {

	private String forward;
	CashCB scb=new CashCB();
	private String validateResult="";
	private ActionErrors errors = new ActionErrors();
	final com.maan.common.Validation validate = new com.maan.common.Validation();
	
	public ActionForward cashSearch(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Cash Search Start");
		ActionForward forward= null;
		final CashCB sCB = new CashCB();
		CashFormBean sForm=( CashFormBean)form;
	    sForm.setBankId("select");
	    sForm.setTransactionDate("");
	    sForm.setBankno("");
	    sForm.setBankAmount("");
	    sForm.setBankLocation("");
	    sForm.setSumAmount(0);
		final Map bankList = sCB.getBankList(sForm);
		request.setAttribute("BankList", bankList);
	    request.setAttribute("PartToShow","CashSearch");
		forward=mapping.findForward("cashdetail"); 		
		return forward;
	}

	public ActionForward submitSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		    CashFormBean sbean = (CashFormBean)form;
			ActionErrors errors = new ActionErrors();
			CashCB scb=new CashCB();
			sbean.setSumAmount(0);			
			forward="cashdetail";
			LogManager.push("Inside searchsubmit");
			List list = null;
			 sbean.setBanknoedit("");
	 	     sbean.setOperation("");
	 	     sbean.setBankno("");
	       validateResult=scb.validateCashInfo(sbean,validateResult);
			
	          if("".equalsIgnoreCase(validateResult))
	            {
	            	try{
	            		LogManager.push("Search Option: "+sbean.getBankId());
	            		list=scb.getSearchList(sbean);
	            	//	banklist=scb.getBankSearchList(sbean);
	            	//	receiptlist=scb.getReceiptSearchList(sbean);
	            		LogManager.push("Bank LIST retrieved size:"+list);
	            		//LogManager.push("Receipt LIST retrieved size:"+list);
	            		scb.updateReceipt();
	            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	            	
	            	request.setAttribute("PartToShow","SearchResult");
	            	request.setAttribute("searchResult", list);
	            	request.setAttribute("Bank",sbean.getBankId() );
	            	
	            }
	            else
	            {
	            	try{
	            	final Map bankList = scb.getBankList(sbean);
	    			request.setAttribute("BankList", bankList);
	                 }
		            catch(Exception e)
		            {
		            e.printStackTrace();	
		            }
	            	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {validateResult}));
	            	request.setAttribute("PartToShow","CashSearch");
	            	validateResult="";
	            	saveMessages(request,errors);
		            saveErrors(request,errors);
	             }
	     		
			
			return mapping.findForward(forward);
	}

	public ActionForward submitBankId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		    CashFormBean sbean = (CashFormBean)form;
		    String bankno=sbean.getBankno();
		    LogManager.push("Submitted bankno"+bankno);
		    LogManager.push("Operation::>"+sbean.getOperation());
		    LogManager.push("search::>"+sbean.getSearch());
		    LogManager.push("Edit bank no"+sbean.getBanknoedit());
		    boolean edit = sbean.getOperation().trim().equalsIgnoreCase("Edit");
		    if(edit)
		    {
		    	sbean.setBankno(sbean.getBanknoedit());
		    }
		    
			ActionErrors errors = new ActionErrors();
			CashCB scb=new CashCB();
			if(sbean.getSearch().equalsIgnoreCase("first"))
		    {
		    	try {
					scb.setEmpty();
					sbean.setSumAmount(0);
				} catch (CommonBaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }
			/*CommonCB commoncb=new CommonCB();
			String branchCode =  sbean.getBranchCode();*/
			int noOfBranchCode=0;
			if(sbean.getBranchCode()!=null)
				noOfBranchCode = sbean.getBranchCode().length;
			forward="cashdetail";
			LogManager.push("Inside submitBankId");
			//LogManager.push("Operation at show receipt "+sbean.getOperation());
			List list = null;
			if(!bankno.equalsIgnoreCase("") && noOfBranchCode > 0 )
			{
			//start
			String checkedRecptNos="";
			String uncheckedRecptNos="";
			Enumeration en = request.getParameterNames();
			String param_name="";
			String param_value="";
			boolean flag=false;
			
			int i=0;
			while(en.hasMoreElements())
			{
				param_name=en.nextElement().toString();
				LogManager.push((++i)+"########param_name==>>"+param_name);
				if(param_name.indexOf("checkbox")!=-1)
					checkedRecptNos+="'"+param_name.replaceAll("checkbox", "")+"',";
				if(param_name.indexOf("hidden")!=-1)
					uncheckedRecptNos+="'"+param_name.replaceAll("hidden", "")+"',";
			}
			if(uncheckedRecptNos.length()>0)
			{
				uncheckedRecptNos=uncheckedRecptNos.substring(0, uncheckedRecptNos.length()-1);
				LogManager.push("unchecked params:"+uncheckedRecptNos);
				
				flag=true;
				
			}
			if(checkedRecptNos.length()>0)
			{
				checkedRecptNos=checkedRecptNos.substring(0, checkedRecptNos.length()-1);
				LogManager.push("checked params:"+checkedRecptNos);
				flag=true;
				
			}
			if(sbean.getSearch().equalsIgnoreCase("first"))
			{
				LogManager.push("Checked branches "+checkedRecptNos);
				sbean.setBranchCodes(checkedRecptNos);//branchName method is used to select the receipts only from checked branches.
			}
			
			//end

	    	try{
	    		if(flag&&sbean.getSearch().equalsIgnoreCase("second")){
	    			scb.updateSelected(checkedRecptNos,uncheckedRecptNos);
	    		}
	    		if(edit && !flag)
	    		{
	    			LogManager.push("Enters selection");
	    			String sum=scb.updateAsSelected(sbean);
	    			sbean.setSumAmount(Integer.parseInt(sum));
	    		}
	    	
	    		LogManager.push("Search Option: "+sbean.getBankId());
	    		list=scb.getReceiptList(sbean);
	    	//	banklist=scb.getBankSearchList(sbean);
	    	//	receiptlist=scb.getReceiptSearchList(sbean);
	    		LogManager.push("Bank LIST retrieved size:"+list);
	    		//LogManager.push("Receipt LIST retrieved size:"+list);
	    	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
	    	
	    	request.setAttribute("PartToShow","ReceiptResult");
	        
			request.setAttribute("receiptResult", list);
	       }
			//validation fails
			else
            {
				
				if(noOfBranchCode <= 0 ) {
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("branch.select.atleastone"));
					List branchList = sbean.getBranchList();
					request.setAttribute("branchList",branchList);
					request.setAttribute("PartToShow","BranchDetails");
				} else {
				try{
            		LogManager.push("Search Option: "+sbean.getBankId());
            		list=scb.getSearchList(sbean);
            	//	banklist=scb.getBankSearchList(sbean);
            	//	receiptlist=scb.getReceiptSearchList(sbean);
            		LogManager.push("Bank LIST retrieved size:"+list);
            		//LogManager.push("Receipt LIST retrieved size:"+list);
            		scb.updateReceipt();
            	}catch(Exception e){LogManager.push("Exception In SearchDispatcAction - searchInit(): "+e);}
            	
            	request.setAttribute("PartToShow","SearchResult");
            	request.setAttribute("searchResult", list);
            	request.setAttribute("Bank",sbean.getBankId() );
            	errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.selectbank", new Object[] {"Select One Record"}));
            	//request.setAttribute("PartToShow","CashSearch");
            	validateResult="";
				}
            	saveMessages(request,errors);
	            saveErrors(request,errors);
             }
			return mapping.findForward(forward);
	}
	
	public ActionForward showBranchDetails( ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LogManager.info("Cash Search Start");
		ActionForward forward= null;
		final CashCB sCB = new CashCB();
		CashFormBean sForm=(CashFormBean)form;
		LogManager.push("Operation::>"+sForm.getOperation());
		 LogManager.push("Edit bank no"+sForm.getBanknoedit());
		 LogManager.push("search"+sForm.getSearch()+request.getParameter("search"));
		LogManager.push("Bankno::>"+sForm.getBankno());
		String bankno = sForm.getBankno();
		 boolean edit = sForm.getOperation().trim().equalsIgnoreCase("Edit");
		    if(edit)
		    {
		    	sForm.setBankno(sForm.getBanknoedit());
		    }
		    if((!(edit && bankno.equalsIgnoreCase(""))) || edit)
		    {
				//sForm.setBankno(bankno);
				//LogManager.push("Bankno::>"+request.getAttribute("bankId"));
				try {
				    final List branchList = sCB.getBranchList(sForm);
				    sForm.setBranchCode(null);
				    sForm.setBranchList( branchList );
				    request.setAttribute("branchList",branchList);
				} catch (Exception e) {
					LogManager.push(e.getMessage());
				}
		    }
   		request.setAttribute("PartToShow","BranchDetails");
		forward=mapping.findForward("cashdetail"); 		
		return forward;
	}
	public ActionForward submitReceipt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws CommonBaseException {
		    CashFormBean sbean = (CashFormBean)form;
		    LogManager.push("Submitted bankno"+sbean.getBankno());
			ActionErrors errors = new ActionErrors();
			final HttpSession session = request.getSession(true);
			final LoginForm loginForm = (LoginForm)session.getAttribute("loginForm");
			
			boolean flagvalid=true;
			CashCB scb=new CashCB();
			forward="cashdetail";
			boolean valid=false;
			LogManager.push("Inside submitRecipt");
			List list = new ArrayList();
			//start
			String checkedRecptNos="";
			String uncheckedRecptNos="";
			Enumeration en = request.getParameterNames();
			String param_name="";
			String param_value="";
			boolean flag=false;
			boolean edit = sbean.getOperation().trim().equalsIgnoreCase("Edit");
			 			
			while(en.hasMoreElements())
			{
				param_name=en.nextElement().toString();
				LogManager.push("123########param_name==>>"+param_name);
				if(param_name.indexOf("checkbox")!=-1)
					checkedRecptNos+="'"+param_name.replaceAll("checkbox", "")+"',";
				if(param_name.indexOf("hidden")!=-1)
					uncheckedRecptNos+="'"+param_name.replaceAll("hidden", "")+"',";
			}
			if(uncheckedRecptNos.length()>0)
			{
				uncheckedRecptNos=uncheckedRecptNos.substring(0, uncheckedRecptNos.length()-1);
				LogManager.push("params:"+uncheckedRecptNos);
				flag=true;
			}
			if(checkedRecptNos.length()>0)
			{
				checkedRecptNos=checkedRecptNos.substring(0, checkedRecptNos.length()-1);
				flag=true;
			}
			else
			{
				flagvalid=false;
				//scb.setEmpty();
			}
			//end
	    	try{
	    		if(flag || flagvalid){
	    			String sum = scb.updateSelected(checkedRecptNos,uncheckedRecptNos);
	    			sbean.setSumAmount(Integer.parseInt(sum));
	    		}
	    		else
	    		{
	    			String sum =	scb.updateUnSelected(sbean);
	    			sbean.setSumAmount(Integer.parseInt(sum));
	    		}
	    		LogManager.push("Search Option: "+sbean.getBankId());
	    		list=scb.getReceiptList(sbean);
	    	//	banklist=scb.getBankSearchList(sbean);
	    	//	receiptlist=scb.getReceiptSearchList(sbean);
	    		LogManager.push("Bank LIST retrieved size:"+list);
	    		//LogManager.push("Receipt LIST retrieved size:"+list);
	    		int count=scb.getCount();
				if(count>0){
				flagvalid=true;
				}
				else
				{
				 flagvalid=false;
				}
	    		if(flagvalid)
	    		{
	    		valid=scb.checkvalid(sbean,loginForm);
	    		}
	    		else
	    		{
	    		 valid=false;
	    		}
	    	    if(valid)
	 	    	{
	    	     LogManager.push("Inside valid");
	    		 sbean.setBankno("");
	    		 sbean.setBankAmount("");
	    		 sbean.setBankLocation("");
    			 final Map bankList = scb.getBankList(sbean);
    			 request.setAttribute("BankList", bankList);
	    		 request.setAttribute("PartToShow","Success");
	    		 sbean.setSearch("first");
	    		 //forward="bankdetail";
	    		 sbean.setBanknoedit("");
		 	     sbean.setOperation("");
	 	    	}
	    	 else
	    	 {
	 	    	request.setAttribute("PartToShow","ReceiptResult");
	 	        request.setAttribute("receiptResult", list);
	 	        LogManager.push("flagvalid:"+flagvalid);
	 	        
	 	       if(flagvalid)
	 	       {
	 	    	/*uncheckedRecptNos=uncheckedRecptNos+","+checkedRecptNos;
	 	    	checkedRecptNos="";
	 	    	scb.updateSelected(checkedRecptNos, uncheckedRecptNos);   
	 	    	list=scb.getReceiptList(sbean);
	 	    	request.setAttribute("receiptResult", list);
	 	    	sbean.setSumAmount(0);*/
	 	        request.setAttribute("checkstatus", "Selected Amount not with in the range limit ");
	 	       }
	 	       else
	 	       {
	 	    	  request.setAttribute("checkstatus", "Please Select Receipts");
		 	       
	 	       }
	    	 }
	    	}catch(Exception e){LogManager.push("Exception In CashDispatcAction - submitReceipt(): "+e);}
            
            try{
            	String sum=scb.getsum();
            	sbean.setSumAmount(Integer.parseInt(sum));
            	
            }
            catch
            (Exception e){LogManager.push("Exception In CashDispatcAction getsum()- submitReceipt(): "+e);}
            
			return mapping.findForward(forward);
	}
	
	public ActionForward gomatchdetail(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("go receipt detail");
		ActionForward forward = null;
		final CashCB sCB = new CashCB();
		String bankval=request.getParameter("bid")==null?((String)request.getAttribute("bid")):request.getParameter("bid");
		LogManager.push(bankval);
		CashFormBean sbean = (CashFormBean)form;
		if("select".equalsIgnoreCase(sbean.getBankId()) && (null!=sbean.getBankName() && !"".equalsIgnoreCase(sbean.getBankName()))){
			sbean.setBankId(sCB.getBankId(sbean.getBankName()));
		}
		ArrayList listbankmatch =(ArrayList)sCB.getMatchDetail(bankval,sbean);
		ArrayList listreceipt = (ArrayList)sCB.getReceiptMatchDetail(bankval, sbean);
		request.setAttribute("BankDetail", listbankmatch);
		request.setAttribute("Receipts", listreceipt);
		LogManager.push("Retrieval finished");
		forward = mapping.findForward("matchdetail"); 		
		return forward;
	}
	
	private ActionErrors validateForm(CashFormBean sbean,final String[] chequeNo,final String[] chequeAmt ) throws CommonBaseException{
		final UploadCB uploadCB = new UploadCB();
		ActionErrors errors = new ActionErrors();
		
		String result;
		for(int i=0;i<chequeNo.length;i++)
		{
			if(isString(chequeNo[i]) && chequeNo[i]!=null && !chequeNo[i].equalsIgnoreCase("") )
			{
					//result="Enter Cheque no. in numbers only";
					errors.add("cashFormBean", new ActionError("cash.update.chequeno.invalid"));			
					
			}
			 if(isString(chequeAmt[i]) && chequeAmt[i]!=null && !chequeAmt[i].equalsIgnoreCase(""))
			{
					//result="Enter Cheque amount in numbers only";
					errors.add("cashFormBean", new ActionError("cash.update.chequeamt.invalid"));			
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