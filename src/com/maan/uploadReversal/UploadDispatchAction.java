package com.maan.uploadReversal;

import java.util.Enumeration;
import java.util.List;

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

import com.maan.common.LogManager;
import com.maan.common.base.AbstractCommonBaseDispatchAction;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;
import com.maan.login.LoginForm;

public class UploadDispatchAction extends AbstractCommonBaseDispatchAction 
{
	public String transId;
	
	public ActionForward transactionDetails(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		ActionForward forward = null;
		final HttpSession session = request.getSession(true);
		final LoginForm loginForm = (LoginForm)session.getAttribute("loginForm");
		final UploadCB uploadCB = new UploadCB();
		LogManager.info("Receipt Reversal transactionDetails() of receipt nos Method - Enter");
		try{
			boolean edit= false;
			if(request.getParameter("transactionid")!=null){		
				transId=request.getParameter("transactionid");
			}
			else
			{
				transId=request.getParameter("bid");
			}
			LogManager.push("TRN ID ::"+transId);
			String editPaymentNo = request.getParameter("editPaymentNo")==null?"":request.getParameter("editPaymentNo");
			
			if(request.getParameter("requestfrom")==null)
			{try{
				uploadCB.updateReceipts(transId);
			}
			catch(Exception e){
				LogManager.push("Exception Occured:::"+e);
			}
			}
			LogManager.push("request:"+request.getParameter("requestfrom"));
			LogManager.push("Edit"+request.getParameter("editPaymentNo"));
			if( request.getParameter("requestfrom")==null || request.getParameter("requestfrom").toString().equalsIgnoreCase(""))
			{
				edit= true;
			
			}
			else
			{
				edit= false;
				
			}
			LogManager.push("Edit flag:"+edit);
			String checkedRecptNos="";
			String uncheckedRecptNos="";
			String param_name="";
			String param_value="";
			Enumeration en = request.getParameterNames();
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
				
			}
			if(checkedRecptNos.length()>0)
			{
				checkedRecptNos=checkedRecptNos.substring(0, checkedRecptNos.length()-1);
				LogManager.push("checked params:"+checkedRecptNos);
				
			}
			
			try {
	 			uploadCB.updateCheckedReceipts(transId,checkedRecptNos,uncheckedRecptNos);
	 				 			
			} catch (CommonBaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!editPaymentNo.equalsIgnoreCase("")){
				String amount[][]  = uploadCB.getPaymentAmount(editPaymentNo);
				request.setAttribute("EditPayment",editPaymentNo);
				request.setAttribute("Amount",amount[0][0]);
			}
			LogManager.push("trans id:"+transId);
			List receiptDetail = null;
			receiptDetail = uploadCB.getReceiptsDetail(transId,editPaymentNo,edit);
			request.setAttribute("ReceiptDetail", receiptDetail);
			request.setAttribute("PartToShow","ReceiptDetails");
		}catch(Exception e){
			
			LogManager.fatal(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		request.setAttribute("bid",transId );
		forward = mapping.findForward("uploaduser");
		return forward;
	}
	
	public ActionForward transactionPayments(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		ActionForward forward = null;
		final HttpSession session = request.getSession(true);
		final LoginForm loginForm = (LoginForm)session.getAttribute("loginForm");
		final UploadCB uploadCB = new UploadCB();
		LogManager.info("Receipt Reversal transactionDetails() of receipt nos Method - Enter");
		try{
			if(request.getParameter("transactionid")!=null){		
				transId=request.getParameter("transactionid");
			}
			else
			{
				transId=request.getParameter("bid");
			}
			
			LogManager.push("trans id:"+transId);
			final List receiptPayments = uploadCB.getReceiptPayments(transId);
			request.setAttribute("ReversalPayments", receiptPayments);
			request.setAttribute("PartToShow","ReversalPayments");
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		request.setAttribute("bid",transId );
		forward = mapping.findForward("uploaduser");
		return forward;
	}
	
	public ActionForward submitReceipts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws CommonBaseException {
		    UploadForm sbean = (UploadForm)form;
				    
			ActionErrors errors = new ActionErrors();
			UploadCB scb=new UploadCB();
			String forward="uploaduser";
			LogManager.push("Inside submitReceipts");
			String checkedRecptNos="";
			String uncheckedRecptNos="";
			Enumeration en = request.getParameterNames();
			String param_name="";
			String param_value="";
			boolean flag=false;
			transId=request.getParameter("transactionid");
			LogManager.push("TRANS ID ::"+transId);
			String editPayment = request.getParameter("editPaymentNo")==null?"":request.getParameter("editPaymentNo");
			String payment[][]  = new String[1][2];
			if(!editPayment.equalsIgnoreCase(""))
			payment = scb.getPaymentAmount(editPayment);
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
			
			
		 			List result = scb.updateSubmittedReceipts(transId,checkedRecptNos,uncheckedRecptNos);
		 			request.setAttribute("SelectedSum", result.get(0));
		 			request.setAttribute("SelectedNo", result.get(1));
		 			request.setAttribute("TransId",transId );
		 			
				LogManager.push("editPayment"+editPayment);
			if(!result.get(1).toString().equalsIgnoreCase("0"))
			{
				request.setAttribute("PartToShow","ReversalRecordsSelected");
				if((!editPayment.trim().equalsIgnoreCase("") ) ){
					//scb.updateEditedRecords(editPayment,transId);
					request.setAttribute("EditPayment",editPayment);
					request.setAttribute("Amount",payment[0][0]);
					request.setAttribute("PaymentNo",editPayment );
		 			request.setAttribute("PaymentDate",payment[0][1] );
		 			request.setAttribute("PaymentAmount",payment[0][0]);
					scb.updateEditedRecords(editPayment,transId);
					sbean.setPaymnetNo(editPayment);
					scb.updateReversals(sbean);
					request.setAttribute("PartToShow","ReversalSuccess");
					/*if(Integer.parseInt(amount)< Integer.parseInt(result.get(0).toString())){
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", "Selected Receipts Amount greater than  Payment Amount <br/>"));
						saveMessages(request,errors);
			            saveErrors(request,errors);
			            final List receiptDetail = scb.getReceiptsDetail(transId,editPayment,false);
						request.setAttribute("ReceiptDetail", receiptDetail);
						request.setAttribute("PartToShow","ReceiptDetails");
					}
					if(	(Integer.parseInt(amount)< Integer.parseInt(result.get(0).toString()))
							&& ((Integer.parseInt(result.get(0).toString())-(Integer.parseInt(amount))>100))
							||
						(Integer.parseInt(amount)> Integer.parseInt(result.get(0).toString()))
						&& ((Integer.parseInt(amount)- Integer.parseInt(result.get(0).toString())>100))
					){
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", "Receipts and Payment amount difference is greater than 100 "));
						saveMessages(request,errors);
			            saveErrors(request,errors);
			            final List receiptDetail = scb.getReceiptsDetail(transId,editPayment,false);
						request.setAttribute("ReceiptDetail", receiptDetail);
						request.setAttribute("PartToShow","ReceiptDetails");
					}*/
					
				}
			}
			else
			{
				//errors.add("Please Select Receipts to make Reversal",);
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", "Please Select Receipts to make Reversal"));
				saveMessages(request,errors);
	            saveErrors(request,errors);
	            
				if(!editPayment.equalsIgnoreCase("")){
				final List receiptDetail = scb.getReceiptsDetail(transId,editPayment,false);
				request.setAttribute("ReceiptDetail", receiptDetail);
				request.setAttribute("PartToShow","ReceiptDetails");
				request.setAttribute("EditPayment",editPayment);
				request.setAttribute("Amount",payment[0][0]);
				}
				else
				{
					final List receiptDetail = scb.getReceiptsDetail(transId,"",false);
					request.setAttribute("ReceiptDetail", receiptDetail);
					request.setAttribute("PartToShow","ReceiptDetails");
				
				}
				
			}
			
			//end
			
	    	request.setAttribute("bid",transId );
	        return mapping.findForward(forward);
	}
	
	public ActionForward submitPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws CommonBaseException {
		    UploadForm sbean = (UploadForm)form;
				    
			ActionErrors errors = new ActionErrors();
			UploadCB scb=new UploadCB();
			String forward="uploaduser";
			boolean exists = scb.checkPayment(sbean.getPaymnetNo()==null?"":sbean.getPaymnetNo());
			String editPayment = request.getParameter("editPaymentNo")==null?"":request.getParameter("editPaymentNo");
			
			if(editPayment.equalsIgnoreCase("")){
				if(exists)
				{
					errors = validate(sbean,false);
				}
				else
				{
					errors = validate(sbean,true);
				}
			}
			if(errors.isEmpty())
			{
				/*if(!editPayment.equalsIgnoreCase("")){
					scb.updateEditedRecords(editPayment,transId);
					request.setAttribute("EditPayment",editPayment);
					request.setAttribute("PartToShow","ReversalSuccess");
				}
				else{*/
					if(exists){
						scb.updateReversals(sbean);
					}
					else
					{
						scb.insertUpdateReversals(sbean);
					}
					request.setAttribute("PartToShow","ReversalSuccess");			
			//}
				LogManager.push("Inside error free");
				
				request.setAttribute("SelectedSum", request.getParameter("sum"));
		 		request.setAttribute("SelectedNo", request.getParameter("selected"));
		 		request.setAttribute("TransId",sbean.getTransactionid() );
	 			request.setAttribute("PaymentNo",sbean.getPaymnetNo() );
	 			request.setAttribute("PaymentDate",sbean.getPaymentDate() );
	 			request.setAttribute("PaymentAmount",sbean.getPaymentAmount());
	 			
	      		request.setAttribute("PartToShow","ReversalSuccess");
			}
			else
			{
				saveMessages(request,errors);
	            saveErrors(request,errors);
	            LogManager.push("Inside error ");
				request.setAttribute("SelectedSum", request.getParameter("sum"));
	 			request.setAttribute("SelectedNo", request.getParameter("selected"));
	 			request.setAttribute("TransId",transId );
	 			request.setAttribute("PaymentNo",sbean.getPaymnetNo() );
	 			request.setAttribute("PaymentDate",sbean.getPaymentDate() );
	 			request.setAttribute("PaymentAmount",sbean.getPaymentAmount());
	 			
	      		request.setAttribute("PartToShow","ReversalRecordsSelected");			
			}
			request.setAttribute("bid",transId );
	        return mapping.findForward(forward);
	}

	private ActionErrors validate(UploadForm sbean,boolean all) {
		ActionErrors errors = new ActionErrors(); 
		if(sbean.getPaymnetNo()==null || sbean.getPaymnetNo().equalsIgnoreCase(""))
		{
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", "Please Enter Payment Number <br/>"));
		}
		if(all)
		{
			if(sbean.getPaymentDate()==null || sbean.getPaymentDate().equalsIgnoreCase(""))
			{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", "Please Enter Payment Date <br/>"));
			}
			if(sbean.getPaymentAmount()==null || sbean.getPaymentAmount().equalsIgnoreCase(""))
			{
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", "Please Enter Payment Amount <br/>"));
			}
		}
		return errors;
	}
	
}