package com.maan.transaction;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.maan.common.LogManager;
import com.maan.common.base.AbstractCommonBaseDispatchAction;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;
import com.maan.excelupload.uploadCB;
import com.maan.upload2.UploadCB;

public class TransactionDispatchAction extends AbstractCommonBaseDispatchAction 
{
	String checkedTransactNos="", uncheckedTransactNos="",param_name="",param_value="";
	
	public ActionForward receiptTransactions(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("receiptTransactionsAction-Start");
		ActionForward forward = null;
		TransactionCB tCB=new TransactionCB();
		TransactionForm tForm=(TransactionForm)form;
		tForm.setTransaction("R");
		try
		{  
			final List list = tCB.getTransactedDetails(tForm);
			LogManager.push("List Size: "+list.size());
			request.setAttribute("details", list);
            LogManager.push("receiptTransactions Controller  method() - Enter========>"+list.size());
			
		}catch(Exception e)
		{
			e.printStackTrace();
			LogManager.debug(e);
		}
		request.setAttribute("PartToShow","ReceiptTransactions");
		forward = mapping.findForward("user"); 		
		return forward;
	}
	
	public ActionForward receiptNosTransactions(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Start");
		ActionForward forward = null;
		TransactionCB tCB=new TransactionCB();
		TransactionForm tForm=(TransactionForm)form;
		tForm.setTransaction("RN");
		try
		{  
			final List list = tCB.getTransactedDetails(tForm);
			LogManager.push("List Size: "+list.size());
			request.setAttribute("details", list);
            LogManager.push("receiptnosTransactions Controller  method() - Enter========>"+list.size());
			
		}catch(Exception e)
		{e.printStackTrace();
		LogManager.debug(e);
		}
		request.setAttribute("PartToShow","ReceiptNosTransactions");
		forward = mapping.findForward("user"); 		
		return forward;
	}
	public ActionForward policyNosTransactions(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		
		TransactionCB tCB=new TransactionCB();
		List policyNumbers=tCB.getPolicyNumbers();
		request.setAttribute("policyList", policyNumbers);
		request.setAttribute("PartToShow","PolicyNosTransactions");
		return mapping.findForward("user"); 		
	}
	public ActionForward citiTransactions(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Start");
		ActionForward forward = null;
		TransactionCB tCB=new TransactionCB();
		TransactionForm tForm=(TransactionForm)form;
		ActionErrors ae=new ActionErrors();
		tForm.setTransaction("CIT");
		String status=request.getParameter("status")==null?"":request.getParameter("status");
		
		boolean submit = false;
		LogManager.push(request.getParameter("Pagination")+"<=Pagination");	
		/*if(request.getParameter("Pagination")==null )
		{
			tCB.updateTransactions();	
		}*/
		
		int count=tCB.getProcessCount();
		tForm.setProcessCount(count);
		if(count>0&&!status.equalsIgnoreCase("processed"))
		{
		ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("search.error.display", new Object[] {"Please wait another transaction is in process </br>Refresh your browser after some time."}));
	    saveMessages(request,ae);
        saveErrors(request,ae);
		}
		if(request.getParameter("Pagination")!=null)
		{
		    if(request.getParameter("Pagination").toString().equalsIgnoreCase(""))
			{
		    	submit = true;
			}
		}		
		Enumeration en = request.getParameterNames();
		boolean flag=false;
	    int j=0;
		while(en.hasMoreElements())
		{			
			param_name=en.nextElement().toString();
			LogManager.push((++j)+"########param_name==>>"+param_name);
			if(param_name.indexOf("checkbox")!=-1)
				checkedTransactNos+="'"+param_name.replaceAll("checkbox", "")+"'";
			/*if(param_name.indexOf("hidden")!=-1)
				uncheckedTransactNos+="'"+param_name.replaceAll("hidden", "")+"'";*/
		}
		
		
		/*uncheckedTransactNos=uncheckedTransactNos.replaceAll("''","','");*/
		checkedTransactNos=checkedTransactNos.replaceAll("''","','");
		
		//LogManager.push("unchecked trans nos "+uncheckedTransactNos);
		LogManager.push("checked trans nos "+checkedTransactNos);
		
		if(checkedTransactNos.length()>0)
			flag=true;
		if(flag){
			tCB.updateSelected(checkedTransactNos,uncheckedTransactNos);
			checkedTransactNos="";
			uncheckedTransactNos="";
			
		LogManager.push("checked values after updation "+checkedTransactNos);
		}
		//end
		if(submit){
		    tCB.updateTransactions();
		}
		try
		{  
			final List list = tCB.getTransactedDetails(tForm);
			LogManager.push("List Size: "+list.size());
			for(int i=0;i<list.size();i++)
			{
			TransactionVB tVB=(TransactionVB) list.get(i);
			String process=tVB.getProcessed();
			LogManager.push("Process count:"+process);
			}
			List processed=new ArrayList();
			List unProcessed=new ArrayList();
			for(int i=0;i<list.size();i++)
			{
				TransactionVB transVB=(TransactionVB) list.get(i);
				if(transVB.getProcessed().equals("Y"))
					processed.add(transVB);
				else
					unProcessed.add(transVB);
			}
		
			if(status.equals("processed"))
			{
				request.setAttribute("partToShowStatus","Processed");
				request.setAttribute("Processed", processed);
				
				//request.setAttribute("name","processedDetails");
			}
			else 
			{
				request.setAttribute("partToShowStatus","Unprocessed");
				request.setAttribute("Unprocessed", unProcessed);
				
				//request.setAttribute("name","unProcessedDetails");
			}
			
			request.setAttribute("status",status);			
			
			request.setAttribute("details", list);			
            LogManager.push("receiptTransactions Controller  method() - Enter========>"+list.size());			
		}catch(Exception e)
		{
			e.printStackTrace();
			LogManager.debug(e);
		}
		request.setAttribute("PartToShow","CitiTransactions");
		forward = mapping.findForward("user"); 		
		return forward;
	}
	
	public ActionForward scbTransactions(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException 
	{
		LogManager.info("SCB Start");
		LogManager.push("SCB Method Enter Into TransactionSCB");
		ActionForward forward = null;
		TransactionCB tCB=new TransactionCB();
		ActionErrors ae=new ActionErrors();
		TransactionForm tForm=(TransactionForm)form;
		tForm.setTransaction("SCB");
		String status=request.getParameter("status")==null?"":request.getParameter("status");
		boolean submit = false;
		LogManager.push(request.getParameter("Pagination")+"<=Pagination");	
		int count=tCB.getProcessCount();
		tForm.setProcessCount(count);
		if(count>0&&!status.equalsIgnoreCase("processed"))
		{
			
		ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("search.error.display", new Object[] {"Please wait another transaction is in process </br>Refresh your browser after some time."}));
	    saveMessages(request,ae);
        saveErrors(request,ae);
		}
		
		if(request.getParameter("Pagination")==null )
		{
			tCB.updateTransactions();	
		}
		if(request.getParameter("Pagination")!=null)
		{
		    if(request.getParameter("Pagination").toString().equalsIgnoreCase(""))
			{
		    	submit = true;
			}
		}
		
		Enumeration en = request.getParameterNames();
		boolean flag=false;
	    int j=0;
		while(en.hasMoreElements())
		{
			param_name=en.nextElement().toString();
			LogManager.push((++j)+"SCB ########param_name==>>"+param_name);
			if(param_name.indexOf("checkbox")!=-1)
				checkedTransactNos+="'"+param_name.replaceAll("checkbox", "")+"'";
			/*if(param_name.indexOf("hidden")!=-1)
				uncheckedTransactNos+="'"+param_name.replaceAll("hidden", "")+"',";*/
		}
		/*if(uncheckedTransactNos.length()>0)
		{
			uncheckedTransactNos=uncheckedTransactNos.substring(0, uncheckedTransactNos.length()-1);
			LogManager.push("SCB unchecked params:"+uncheckedTransactNos);
			flag=true;
		}
		if(checkedTransactNos.length()>0)
		{
			checkedTransactNos=checkedTransactNos.substring(0, checkedTransactNos.length()-1);
			LogManager.push("SCB checked params:"+checkedTransactNos);
			flag=true;
		}*/
		
		checkedTransactNos=checkedTransactNos.replaceAll("''","','");
		
		if(checkedTransactNos.length()>0)
		flag=true;
		
		if(flag){
			tCB.updateSelected(checkedTransactNos,uncheckedTransactNos);
			uncheckedTransactNos="";
			checkedTransactNos="";
		}
		//end
		if(submit){
	    	tCB.updateTransactions();
		}
		try
		{  
			final List list = tCB.getTransactedDetails(tForm);
			LogManager.push("List Size: "+list.size());
			for(int i=0;i<list.size();i++)
			{
				TransactionVB tVB=(TransactionVB) list.get(i);
				String process=tVB.getProcessed();
				LogManager.push("SCB Process count:"+process);
			}
			
			List processed=new ArrayList();
			List unProcessed=new ArrayList();
			for(int i=0;i<list.size();i++)
			{
				TransactionVB transVB=(TransactionVB) list.get(i);
				if(transVB.getProcessed().equals("Y"))
					processed.add(transVB);
				else
					unProcessed.add(transVB);
			}
		
			if(status.equals("processed"))
			{
				request.setAttribute("partToShowStatus","Processed");
				request.setAttribute("Processed", processed);
				
				//request.setAttribute("name","processedDetails");
			}
			else 
			{
				request.setAttribute("partToShowStatus","Unprocessed");
				request.setAttribute("Unprocessed", unProcessed);
				
				//request.setAttribute("name","unProcessedDetails");
			}
			
			request.setAttribute("status",status);			
	
		request.setAttribute("details", list);
            LogManager.push("SCB receiptTransactions Controller  method() - Enter========>"+list.size());			
		}catch(Exception e)
		{
			e.printStackTrace();
			LogManager.debug(e);
		}
		request.setAttribute("PartToShow","SCBTransactions");
		forward = mapping.findForward("user"); 		
		return forward;
	}
	
	public ActionForward axisTransactions(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException 
	{
		LogManager.push("AXIS Method Enter Into TransactionAXIS");
		ActionForward forward = null;
		TransactionCB tCB=new TransactionCB();
		TransactionForm tForm=(TransactionForm)form;
		tForm.setTransaction("AXB");
		ActionErrors ae=new ActionErrors();
		String status=request.getParameter("status")==null?"":request.getParameter("status");
		boolean submit = false;
		LogManager.push(request.getParameter("Pagination")+"<=Pagination");	
		int count=tCB.getProcessCount();
		tForm.setProcessCount(count);
		if(count>0&&!status.equalsIgnoreCase("processed"))
		{
			
		ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("search.error.display", new Object[] {"Please wait another transaction is in process </br>Refresh your browser after some time."}));
	    saveMessages(request,ae);
        saveErrors(request,ae);
		}
		if(request.getParameter("Pagination")==null )
		{
			tCB.updateTransactions();	
		}
		if(request.getParameter("Pagination")!=null)
		{
		    if(request.getParameter("Pagination").toString().equalsIgnoreCase(""))
			{
		    	submit = true;
			}
		}
		
		Enumeration en = request.getParameterNames();
		boolean flag=false;
	    int j=0;
		while(en.hasMoreElements())
		{
			param_name=en.nextElement().toString();
			LogManager.push((++j)+"AXIS ########param_name==>>"+param_name);
			if(param_name.indexOf("checkbox")!=-1)
				checkedTransactNos+="'"+param_name.replaceAll("checkbox", "")+"'";
			/*if(param_name.indexOf("hidden")!=-1)
				uncheckedTransactNos+="'"+param_name.replaceAll("hidden", "")+"',";*/
		}
		/*if(uncheckedTransactNos.length()>0)
		{
			uncheckedTransactNos=uncheckedTransactNos.substring(0, uncheckedTransactNos.length()-1);
			LogManager.push("AXIS unchecked params:"+uncheckedTransactNos);
			flag=true;
		}
		if(checkedTransactNos.length()>0)
		{
			checkedTransactNos=checkedTransactNos.substring(0, checkedTransactNos.length()-1);
			LogManager.push("AXIS checked params:"+checkedTransactNos);
			flag=true;
		}*/
		checkedTransactNos=checkedTransactNos.replaceAll("''","','");
		if(checkedTransactNos.length()>0)
			flag=true;
		if(flag){
			tCB.updateSelected(checkedTransactNos,uncheckedTransactNos);
		}
		//end
		if(submit){
		    tCB.updateTransactions();
		}
		try
		{  
			final List list = tCB.getTransactedDetails(tForm);
			LogManager.push("AXIS List Size: "+list.size());
			for(int i=0;i<list.size();i++)
			{
			TransactionVB tVB=(TransactionVB) list.get(i);
			String process=tVB.getProcessed();
			LogManager.push("AXIS Process count:"+process);
			}
			
			List processed=new ArrayList();
			List unProcessed=new ArrayList();
			for(int i=0;i<list.size();i++)
			{
				TransactionVB transVB=(TransactionVB) list.get(i);
				if(transVB.getProcessed().equals("Y"))
					processed.add(transVB);
				else
					unProcessed.add(transVB);
			}
		
			if(status.equals("processed"))
			{
				request.setAttribute("partToShowStatus","Processed");
				request.setAttribute("Processed", processed);
				
				//request.setAttribute("name","processedDetails");
			}
			else 
			{
				request.setAttribute("partToShowStatus","Unprocessed");
				request.setAttribute("Unprocessed", unProcessed);
				
				//request.setAttribute("name","unProcessedDetails");
			}
			
			request.setAttribute("status",status);			
			request.setAttribute("details", list);
            LogManager.push("AXIS receiptTransactions Controller  method() - Enter========>"+list.size());
			
		}catch(Exception e)
		{e.printStackTrace();
		LogManager.debug(e);
		}
		request.setAttribute("PartToShow","AXBTransactions");
		forward = mapping.findForward("user"); 		
		return forward;
	}
	
	
	public ActionForward hdfcTransactions(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException 
	{
		LogManager.info("Start");
		ActionForward forward = null;
		TransactionCB tCB=new TransactionCB();
		TransactionForm tForm=(TransactionForm)form;
		tForm.setTransaction("HDB");
		ActionErrors ae=new ActionErrors();
		String status=request.getParameter("status")==null?"":request.getParameter("status");
		boolean submit = false;
		LogManager.push(request.getParameter("Pagination")+"<=Pagination");	
		int count=tCB.getProcessCount();
		tForm.setProcessCount(count);
		if(count>0&&!status.equalsIgnoreCase("processed"))
		{
			
		ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("search.error.display", new Object[] {"Please wait another transaction is in process </br>Refresh your browser after some time."}));
	    saveMessages(request,ae);
        saveErrors(request,ae);
		}
		
		if(request.getParameter("Pagination")==null )
		{
			tCB.updateTransactions();	
		}
		if(request.getParameter("Pagination")!=null)
		{
		    if(request.getParameter("Pagination").toString().equalsIgnoreCase(""))
			{
		    	submit = true;
			}
		}		
		Enumeration en = request.getParameterNames();
		boolean flag=false;
	    int j=0;
		while(en.hasMoreElements())
		{
			param_name=en.nextElement().toString();
			LogManager.push((++j)+"########param_name==>>"+param_name);
			if(param_name.indexOf("checkbox")!=-1)
				checkedTransactNos+="'"+param_name.replaceAll("checkbox", "")+"'";
			/*if(param_name.indexOf("hidden")!=-1)
				uncheckedTransactNos+="'"+param_name.replaceAll("hidden", "")+"',";*/
		}
		/*if(uncheckedTransactNos.length()>0)
		{
			uncheckedTransactNos=uncheckedTransactNos.substring(0, uncheckedTransactNos.length()-1);
			LogManager.push("unchecked params:"+uncheckedTransactNos);
			flag=true;
		}
		if(checkedTransactNos.length()>0)
		{
			checkedTransactNos=checkedTransactNos.substring(0, checkedTransactNos.length()-1);
			LogManager.push("checked params:"+checkedTransactNos);
			flag=true;
		}*/
		
		checkedTransactNos=checkedTransactNos.replaceAll("''","','");
		LogManager.push("checked trans nos "+checkedTransactNos);
		
		if(checkedTransactNos.length()>0)
			flag=true;
		if(flag){
			tCB.updateSelected(checkedTransactNos,uncheckedTransactNos);
			checkedTransactNos="";
			uncheckedTransactNos="";
		}
		//end
		if(submit){
	    	tCB.updateTransactions();
		}
		
		
		try
		{  
			final List list = tCB.getTransactedDetails(tForm);
			LogManager.push("List Size: "+list.size());
			for(int i=0;i<list.size();i++)
			{
				TransactionVB tVB=(TransactionVB) list.get(i);
				String process=tVB.getProcessed();
				LogManager.push("Process count:"+process);
			}
			//changes
			List processed=new ArrayList();
			List unProcessed=new ArrayList();
			for(int i=0;i<list.size();i++)
			{
				TransactionVB transVB=(TransactionVB) list.get(i);
				if(transVB.getProcessed().equals("Y"))
					processed.add(transVB);
				else
					unProcessed.add(transVB);
			}
		
			if(status.equals("processed"))
			{
				request.setAttribute("partToShowStatus","Processed");
				request.setAttribute("Processed", processed);
				
				//request.setAttribute("name","processedDetails");
			}
			else 
			{
				request.setAttribute("partToShowStatus","Unprocessed");
				request.setAttribute("Unprocessed", unProcessed);
				
				//request.setAttribute("name","unProcessedDetails");
			}
			
			request.setAttribute("status",status);			
		
			
			//changes
			request.setAttribute("details", list);
            LogManager.push("receiptTransactions Controller  method() - Enter========>"+list.size());			
		}catch(Exception e)
		{
			e.printStackTrace();
			LogManager.debug(e);
		}
		request.setAttribute("PartToShow","HDFCTransactions");
		forward = mapping.findForward("user"); 		
		return forward;
	}
	
	public ActionForward kotakTransactions(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException 
	{
		LogManager.info("Start");
		ActionForward forward = null;
		TransactionCB tCB=new TransactionCB();
		TransactionForm tForm=(TransactionForm)form;
		tForm.setTransaction("KOT");
		ActionErrors ae=new ActionErrors();
		String status=request.getParameter("status")==null?"":request.getParameter("status");
		boolean submit = false;
		LogManager.push(request.getParameter("Pagination")+"<=Pagination");	
		int count=tCB.getProcessCount();
		tForm.setProcessCount(count);
		if(count>0&&!status.equalsIgnoreCase("processed"))
		{
			
		ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("search.error.display", new Object[] {"Please wait another transaction is in process </br>Refresh your browser after some time."}));
	    saveMessages(request,ae);
        saveErrors(request,ae);
		}
		
		if(request.getParameter("Pagination")==null )
		{
			tCB.updateTransactions();	
		}
		if(request.getParameter("Pagination")!=null)
		{
		    if(request.getParameter("Pagination").toString().equalsIgnoreCase(""))
			{
		    	submit = true;
			}
		}		
		Enumeration en = request.getParameterNames();
		boolean flag=false;
	    int j=0;
		while(en.hasMoreElements())
		{
			param_name=en.nextElement().toString();
			LogManager.push((++j)+"########param_name==>>"+param_name);
			if(param_name.indexOf("checkbox")!=-1)
				checkedTransactNos+="'"+param_name.replaceAll("checkbox", "")+"'";
			/*if(param_name.indexOf("hidden")!=-1)
				uncheckedTransactNos+="'"+param_name.replaceAll("hidden", "")+"',";*/
		}
		/*if(uncheckedTransactNos.length()>0)
		{
			uncheckedTransactNos=uncheckedTransactNos.substring(0, uncheckedTransactNos.length()-1);
			LogManager.push("unchecked params:"+uncheckedTransactNos);
			flag=true;
		}
		if(checkedTransactNos.length()>0)
		{
			checkedTransactNos=checkedTransactNos.substring(0, checkedTransactNos.length()-1);
			LogManager.push("checked params:"+checkedTransactNos);
			flag=true;
		}*/
		
		checkedTransactNos=checkedTransactNos.replaceAll("''","','");
		LogManager.push("checked trans nos "+checkedTransactNos);
		
		if(checkedTransactNos.length()>0)
			flag=true;
		if(flag){
			tCB.updateSelected(checkedTransactNos,uncheckedTransactNos);
			checkedTransactNos="";
			uncheckedTransactNos="";
		}
		//end
		if(submit){
	    	tCB.updateTransactions();
		}
		
		
		try
		{  
			final List list = tCB.getTransactedDetails(tForm);
			LogManager.push("List Size: "+list.size());
			for(int i=0;i<list.size();i++)
			{
				TransactionVB tVB=(TransactionVB) list.get(i);
				String process=tVB.getProcessed();
				LogManager.push("Process count:"+process);
			}
			//changes
			List processed=new ArrayList();
			List unProcessed=new ArrayList();
			for(int i=0;i<list.size();i++)
			{
				TransactionVB transVB=(TransactionVB) list.get(i);
				if(transVB.getProcessed().equals("Y"))
					processed.add(transVB);
				else
					unProcessed.add(transVB);
			}
		
			if(status.equals("processed"))
			{
				request.setAttribute("partToShowStatus","Processed");
				request.setAttribute("Processed", processed);
				
				//request.setAttribute("name","processedDetails");
			}
			else 
			{
				request.setAttribute("partToShowStatus","Unprocessed");
				request.setAttribute("Unprocessed", unProcessed);
				
				//request.setAttribute("name","unProcessedDetails");
			}
			
			request.setAttribute("status",status);			
		
			
			//changes
			request.setAttribute("details", list);
            LogManager.push("receiptTransactions Controller  method() - Enter========>"+list.size());			
		}catch(Exception e)
		{
			e.printStackTrace();
			LogManager.debug(e);
		}
		request.setAttribute("PartToShow","KOTAKTransactions");
		forward = mapping.findForward("user"); 		
		return forward;
	}
	
	public ActionForward hsbcTransactions(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException 
	{
		LogManager.info("Start");
		ActionForward forward = null;
		TransactionCB tCB=new TransactionCB();
		TransactionForm tForm=(TransactionForm)form;
		ActionErrors ae=new ActionErrors();
		tForm.setTransaction("HSB");
		String status=request.getParameter("status")==null?"":request.getParameter("status");
		boolean submit = false;
		LogManager.push(request.getParameter("Pagination")+"<=Pagination");	
		int count=tCB.getProcessCount();
		tForm.setProcessCount(count);
		if(count>0&&!status.equalsIgnoreCase("processed"))
		{
			
		ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("search.error.display", new Object[] {"Please wait another transaction is in process </br>Refresh your browser after some time."}));
	    saveMessages(request,ae);
        saveErrors(request,ae);
		}
		if(request.getParameter("Pagination")==null )
		{
			tCB.updateTransactions();	
		}
		if(request.getParameter("Pagination")!=null)
		{
		    if(request.getParameter("Pagination").toString().equalsIgnoreCase(""))
			{
		    	submit = true;
			}
		}
		Enumeration en = request.getParameterNames();
		boolean flag=false;
	    int j=0;
		while(en.hasMoreElements())
		{
			param_name=en.nextElement().toString();
			LogManager.push((++j)+"########param_name==>>"+param_name);
			if(param_name.indexOf("checkbox")!=-1)
				checkedTransactNos+="'"+param_name.replaceAll("checkbox", "")+"'";
			/*if(param_name.indexOf("hidden")!=-1)
				uncheckedTransactNos+="'"+param_name.replaceAll("hidden", "")+"',";*/
		}
		/*if(uncheckedTransactNos.length()>0)
		{
			uncheckedTransactNos=uncheckedTransactNos.substring(0, uncheckedTransactNos.length()-1);
			LogManager.push("unchecked params:"+uncheckedTransactNos);
			flag=true;
		}
		if(checkedTransactNos.length()>0)
		{
			checkedTransactNos=checkedTransactNos.substring(0, checkedTransactNos.length()-1);
			LogManager.push("checked params:"+checkedTransactNos);
			flag=true;
		}*/
		
		checkedTransactNos=checkedTransactNos.replaceAll("''","','");
		
		if(checkedTransactNos.length()>0)
			flag=true;
		
		
		if(flag){
			tCB.updateSelected(checkedTransactNos,uncheckedTransactNos);
			checkedTransactNos="";
			uncheckedTransactNos="";
			}
		//end
		if(submit){
		    tCB.updateTransactions();
		}
		try
		{  
			final List list = tCB.getTransactedDetails(tForm);
			LogManager.push("List Size: "+list.size());
			for(int i=0;i<list.size();i++)
			{
				TransactionVB tVB=(TransactionVB) list.get(i);
				String process=tVB.getProcessed();
				LogManager.push("Process count:"+process);
			}
			List processed=new ArrayList();
			List unProcessed=new ArrayList();
			for(int i=0;i<list.size();i++)
			{
				TransactionVB transVB=(TransactionVB) list.get(i);
				if(transVB.getProcessed().equals("Y"))
					processed.add(transVB);
				else
					unProcessed.add(transVB);
			}
		
			if(status.equals("processed"))
			{
				request.setAttribute("partToShowStatus","Processed");
				request.setAttribute("Processed", processed);
				
				//request.setAttribute("name","processedDetails");
			}
			else 
			{
				request.setAttribute("partToShowStatus","Unprocessed");
				request.setAttribute("Unprocessed", unProcessed);
				
				//request.setAttribute("name","unProcessedDetails");
			}
			
			request.setAttribute("status",status);			
		
		
			
			
			
			
			request.setAttribute("details", list);
            LogManager.push("receiptTransactions Controller  method() - Enter========>"+list.size());			
		}catch(Exception e)
		{
			e.printStackTrace();
			LogManager.debug(e);
		}
		request.setAttribute("PartToShow","HSBCTransactions");
		forward = mapping.findForward("user"); 		
		return forward;
	}
	
	public ActionForward goRealised(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Realised Start");
		ActionForward forward = null;
		//request.setAttribute("PartToShow","HDFCTransactions");
		
		forward = mapping.findForward("realised"); 		
		return forward; 
	}
	
	public ActionForward goNotRealised(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Not Realised Start");
		ActionForward forward = null;
		//request.setAttribute("PartToShow","HDFCTransactions");
	 	forward = mapping.findForward("notRealised"); 		
		return forward;
	}
	public ActionForward goDuplicates(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Duplicates Start");
		ActionForward forward = null;
		forward = mapping.findForward("duplicates"); 		
		return forward;
	}
	
	
	public ActionForward processRecords(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		ActionForward forward = null;
    	final uploadCB uploadCB=new uploadCB();
    	TransactionForm tForm=(TransactionForm)form;
    	TransactionCB tCB=new TransactionCB();
    	ActionErrors ae=new ActionErrors();
		String bankId=request.getParameter("bankId");
		String transId=request.getParameter("transactionNo");
		LogManager.push("transaction:::::"+transId);
		LogManager.push("Processing Bank Id:"+bankId);
		LogManager.info("UploadDispatchAction process() Method - Enter");
		try{
			int count=tCB.getProcessCount();
			tForm.setProcessCount(count);
			if(count>0)
			{
				
			ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("search.error.display", new Object[] {"Please wait another transaction is in process </br>Refresh your browser after some time."}));
		    saveMessages(request,ae);
	        saveErrors(request,ae);
			}
			else{
			final int result = uploadCB.getProcess(transId,bankId);
			request.setAttribute("TotalRecords", result);
			}
			if(bankId.equalsIgnoreCase("CIT"))
			{
				citiTransactions(mapping, form, request, response);
				//request.setAttribute("PartToShow","CitiTransactions");
			}
			else if(bankId.equalsIgnoreCase("HDB"))
			{
				hdfcTransactions(mapping, form, request, response);
			}
			else if(bankId.equalsIgnoreCase("HSB"))
			{
				hsbcTransactions(mapping, form, request, response);
			}
			else if(bankId.equalsIgnoreCase("SCB"))
			{
				scbTransactions(mapping, form, request, response);
			}
			else if(bankId.equalsIgnoreCase("AXB"))
			{
				axisTransactions(mapping, form, request, response);
			}
			else if(bankId.equalsIgnoreCase("KOT"))
			{
				kotakTransactions(mapping, form, request, response);
			}
			
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		forward = mapping.findForward("user");
		return forward;
	}
	
	public ActionForward processAllRecords(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		ActionForward forward = null;
		//final HttpSession session = request.getSession(false);
		//final LoginForm loginForm = (LoginForm)session.getAttribute("loginForm");
		TransactionCB tCB=new TransactionCB();
		TransactionForm tForm=(TransactionForm)form;
		
		ActionErrors ae=new ActionErrors();
		String bankId=tForm.getBankId();
		LogManager.push("Processing Bank Id for all process:"+bankId);
		LogManager.info("UploadDispatchAction process() Method - Enter");
		try{
			Enumeration en = request.getParameterNames();
			boolean flag=false;
		    int j=0;			
			while(en.hasMoreElements())
			{
				param_name=en.nextElement().toString();
				LogManager.push((++j)+"########param_name==>>"+param_name);
				if(param_name.indexOf("checkbox")!=-1)
					checkedTransactNos+="'"+param_name.replaceAll("checkbox", "")+"'";
				if(param_name.indexOf("hidden")!=-1)
					uncheckedTransactNos+="'"+param_name.replaceAll("hidden", "")+"'";
			}
			/*if(uncheckedTransactNos.length()>0)
			{
				uncheckedTransactNos=uncheckedTransactNos.substring(0, uncheckedTransactNos.length()-1);
				LogManager.push("unchecked params:"+uncheckedTransactNos);
				flag=true;
			}
			if(checkedTransactNos.length()>0)
			{
				checkedTransactNos=checkedTransactNos.substring(0, checkedTransactNos.length()-1);
				LogManager.push("checked params:"+checkedTransactNos);
				flag=true;
			}*/
			checkedTransactNos=checkedTransactNos.replaceAll("''","','");
			
			if(checkedTransactNos.length()>0)
				flag=true;
			else
			{
				ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("search.error.display", new Object[] {"Please select atleast one record"}));
				 saveMessages(request,ae);
		            saveErrors(request,ae);
			}
			if(ae.isEmpty())
			{	
			if(flag){
				tCB.updateSelected(checkedTransactNos,uncheckedTransactNos);
				checkedTransactNos="";
				uncheckedTransactNos="";
			}
			List selected = tCB.getSelectedTransactions();
			
			tCB.updateTransactions();
			String val[]=new String[selected.size()];
			if(selected.size()>0)
			{
				for (int i=0; i<selected.size();i++)
				{
					Map values = (Map)selected.get(i);
					LogManager.push("Map value::>"+i+"::"+values.get("TRANSACTION_NO").toString());
					
					val[i] = values.get("TRANSACTION_NO").toString();
					//val[i]=values.keySet();
				}				
			}
			LogManager.push("Final List of transactionsactions::");
			for(int i=0;i<val.length;i++)
			{
				LogManager.push(val[i]);
			}
			LogManager.push("End of list");
			int count=tCB.getProcessCount();
			tForm.setProcessCount(count);
			if(count>0)
			{
				
			ae.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("search.error.display", new Object[] {"Please wait another transaction is in process </br>Refresh your browser after some time."}));
		    saveMessages(request,ae);
	        saveErrors(request,ae);
			}else{		
			  tCB.getAllProcess(bankId,val);
			}
					
			}
			if(bankId.equalsIgnoreCase("CIT"))
			{
				citiTransactions(mapping, form, request, response);
			//request.setAttribute("PartToShow","CitiTransactions");
			}
			else if(bankId.equalsIgnoreCase("HSB"))
			{
				hsbcTransactions(mapping, form, request, response);
			}
			else if(bankId.equalsIgnoreCase("HDB"))
			{
				hdfcTransactions(mapping, form, request, response);
			}
			else if(bankId.equalsIgnoreCase("SCB"))
			{
				scbTransactions(mapping, form, request, response);
			}
			else if(bankId.equalsIgnoreCase("AXB"))
			{
				axisTransactions(mapping, form, request, response);
			}
			else if(bankId.equalsIgnoreCase("KOT"))
			{
				kotakTransactions(mapping, form, request, response);
			}
			
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		forward = mapping.findForward("user");
		return forward;
	}
	
	public ActionForward getRecordsCount(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("getRecordsCount Start");
		ActionForward forward = null;
		TransactionCB tCB=new TransactionCB();
		TransactionForm tForm=(TransactionForm)form;
		String bankId=tForm.getBankId();
		LogManager.push(bankId);
		forward = mapping.findForward("user");
		if(bankId.equalsIgnoreCase("R"))
		{
		   List list=tCB.getRecords(tForm);
		   request.setAttribute("details", list);
		   request.setAttribute("PartToShow","ReceiptRecords");
		}
		else if (bankId.equalsIgnoreCase("CIT"))
		{
			List list=tCB.getRecords(tForm);
			request.setAttribute("details", list);
			request.setAttribute("PartToShow","CitiRecords");
		}
		else if (bankId.equalsIgnoreCase("SCB"))
		{
			List list=tCB.getRecords(tForm);
			request.setAttribute("details", list);
			request.setAttribute("PartToShow","SCBRecords");
		}
		else if (bankId.equalsIgnoreCase("AXB"))
		{
			List list=tCB.getRecords(tForm);
			request.setAttribute("details", list);
			request.setAttribute("PartToShow","AXBRecords");
		}else if (bankId.equalsIgnoreCase("HSB"))
		{
			List list=tCB.getRecords(tForm);
			request.setAttribute("details", list);
			request.setAttribute("PartToShow","HSBCRecords");
		}
		else if (bankId.equalsIgnoreCase("KOT"))
		{
			List list=tCB.getRecords(tForm);
			request.setAttribute("details", list);
			request.setAttribute("PartToShow","KOTRecords");
		}
		else
		{
			List list=tCB.getRecords(tForm);
			request.setAttribute("details", list);
			request.setAttribute("PartToShow","HDFCRecords");
		}		
		return forward;
	}
	
	public ActionForward goInvalids(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Invalids Start");
		ActionForward forward = null;
		forward = mapping.findForward("invalids"); 		
		return forward;
	}
	
	public ActionForward goReversals(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Reversals Start");
		ActionForward forward = null;
		forward = mapping.findForward("reversals"); 		
		return forward;
	}
	
	public ActionForward goNoCheques(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("goNoCheques Start");
		ActionForward forward = null;
		forward = mapping.findForward("nocheques"); 		
		return forward;
	}
	
	public ActionForward goReceiptPayments(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("goReceiptPayments Start");
		ActionForward forward = null;
		forward = mapping.findForward("payments"); 		
		return forward;
	}

	public ActionForward transDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
	  	ActionErrors errors = new ActionErrors();
		TransactionCB scb=new TransactionCB();
		TransactionForm tForm=(TransactionForm)form;
		String forward="transdetail";
		LogManager.push("Inside transDetails");
		List list = new ArrayList();
		String transid=request.getParameter("bid");
        try{
	 		LogManager.push("Trans id: "+transid);
	 		list=scb.getTransactionDetails(transid);
        }
        catch(Exception e){
        	LogManager.push("Exception In transDetails - transDetails(): "+e);
        }
		request.setAttribute("transResult", list);
		TransactionVB tVB=(TransactionVB)list.get(0);
		String pending=tVB.getPending();
		LogManager.push(pending);
    	return mapping.findForward(forward);
	}
	
	public ActionForward receiptReversalsTransactions(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("Start");
		ActionForward forward = null;
		TransactionCB tCB=new TransactionCB();
		TransactionForm tForm=(TransactionForm)form;
		tForm.setTransaction("RR");
		try
		{  
			final List list = tCB.getTransactedDetails(tForm);
			LogManager.push("List Size: "+list.size());
			request.setAttribute("details", list);
            LogManager.push("receiptnosTransactions Controller  method() - Enter========>"+list.size());
			
		}catch(Exception e)
		{
			e.printStackTrace();
			LogManager.debug(e);
		}
		request.setAttribute("PartToShow","ReceiptReversalsTransactions");
		forward = mapping.findForward("user"); 		
		return forward;
	}

}