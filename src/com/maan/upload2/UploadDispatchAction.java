package com.maan.upload2;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.struts.upload.FormFile;

import com.maan.common.LogManager;
import com.maan.common.base.AbstractCommonBaseDispatchAction;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;
import com.maan.login.LoginForm;
//import com.maan.transaction.TransactionForm;

public class UploadDispatchAction extends AbstractCommonBaseDispatchAction 
{
	public static String checkedItems[][]=new String[0][0];
	public int spage=1;
	public int value;
	public String transId;
	
	public ActionForward upload(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("UploadDispatchAction Upload() Method - Enter");
		ActionForward forward = null;
		final UploadForm uploadForm = (UploadForm) form;
		request.setAttribute("PartToShow","Upload");
		final UploadCB uploadCB = new UploadCB();
		final Map bankList = uploadCB.getBankList(uploadForm);
		request.setAttribute("BankList", bankList);
		forward = mapping.findForward("uploaduser");
		LogManager.info("UploadDispatchAction Upload() Method - Exit");
		return forward;
	}
	
	public ActionForward uploadsubmit(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("UploadDispatchAction uploadsubmit() Method - Enter");
		ActionForward forward = mapping.findForward("uploaduser");
		ActionErrors errors = new ActionErrors();
		final HttpSession session = request.getSession(true);
		final UploadForm uploadForm = (UploadForm) form;
		final LoginForm loginForm = (LoginForm)session.getAttribute("loginForm");
		final UploadCB uploadCB = new UploadCB();
		final Map bankList = uploadCB.getBankList(uploadForm);
		request.setAttribute("BankList", bankList);
		final String filePath = getServlet().getServletContext().getRealPath("/")+"64VBUploadFiles";
		FormFile myFile = null;
		String fileName = null;
		String bankCode=uploadForm.getBankname();
		System.out.print("Bank code----------->"+bankCode);
		LogManager.push("loginForm.getInsCompanyId()"+loginForm.getInsCompanyId());
		errors = validateForm(uploadForm);
		if(errors.isEmpty()){
			try{
				myFile = uploadForm.getUploadFile();
				fileName = myFile.getFileName();
				final Calendar cal = Calendar.getInstance();
				final  SimpleDateFormat sdf = new SimpleDateFormat("'on'ddMMMyyyy h.mm.ss a");
				final String date = sdf.format(cal.getTime());
				int index = fileName.lastIndexOf("."); 
				final String ext = fileName.substring(index+1).trim();
				if("xls".equalsIgnoreCase(ext)){
					final String tempFileName = fileName.toLowerCase();
					index = tempFileName.lastIndexOf("xls");
					String onlyName = (tempFileName.substring(0,index-1).replaceAll(" ", "")).trim();
					onlyName = onlyName + date;
					fileName = onlyName + ".xls";
					final String fileLoc = filePath+"\\"+fileName;
					final String csvLoc = filePath+"\\"+onlyName+".csv";
					final File copyFile = new File(fileLoc);
					FileOutputStream outStream = new FileOutputStream(copyFile);
					outStream.write(myFile.getFileData());
					outStream.flush();
					outStream.close();
					Process pr = Runtime.getRuntime().exec("C:\\CSVConverter.exe "+ fileLoc + "," + csvLoc);
					int csvresult = pr.waitFor();
					LogManager.info("CSV Convert Result ==> "+csvresult);
					final String status = uploadCB.processCsv(csvLoc,loginForm.getInsCompanyId(),request,uploadForm);
					if("missing".equalsIgnoreCase(status)){
						errors.add("upload", new ActionError("file.upload.file.missing"));
					}else if("duplicate".equalsIgnoreCase(status)){
						errors.add("upload", new ActionError("file.upload.file.duplicate"));
					}else if("extra".equalsIgnoreCase(status)){
						errors.add("upload", new ActionError("file.upload.file.extra"));
					}else{
						transId = status;
						forward = mapping.findForward("transDetails");
					}
				}
			}catch(Exception e){
				LogManager.fatal(e);
				throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
			}			
		}		
		if(!errors.isEmpty()){
			request.setAttribute("PartToShow","Upload");
			saveErrors(request,errors);
			saveMessages(request, errors);
		}
		LogManager.info("UploadDispatchAction uploadsubmit() Method - Exit");
		return forward;
	}
	
	public ActionForward transactionDetails(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		ActionForward forward = null;
		final HttpSession session = request.getSession(true);
		final LoginForm loginForm = (LoginForm)session.getAttribute("loginForm");
		final UploadCB uploadCB = new UploadCB();
		LogManager.info("UploadDispatchAction transactionDetails() Method - Enter");
		try{
			if(transId==null){				
				transId=request.getParameter("transactionid");
			}
			final UploadForm uploadForm = (UploadForm) form;
			
			String bankId=uploadForm.getBankname();
			final List result = uploadCB.getTransactionDetails(transId,bankId);
			//request.setAttribute("errors", request.getAttribute("errors"));
			request.setAttribute("TransactionID", transId);
			request.setAttribute("TotalRecords", result.get(0));
			//request.setAttribute("result", result.get(1));
			request.setAttribute("Errorcount",result.get(1));
			request.setAttribute("Uploadcount",result.get(2));
			request.setAttribute("PartToShow1","Process");
			if(result.get(2).toString().equalsIgnoreCase("0"))
			{
				request.setAttribute("PartToShow1","NoProcess");
			}
			request.setAttribute("PartToShow","UploadResult");
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		forward = mapping.findForward("uploaduser");
		return forward;
	}
	
	public ActionForward processRecords(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		ActionForward forward = null;
		//final HttpSession session = request.getSession(false);
		//final LoginForm loginForm = (LoginForm)session.getAttribute("loginForm");
		final UploadCB uploadCB = new UploadCB();
		final UploadForm uploadForm = (UploadForm) form;
		String bankId=uploadForm.getBankname();
		
		LogManager.push("Processing Bank Id:"+bankId);
		LogManager.info("UploadDispatchAction process() Method - Enter");
		try{
			if(transId==null){				
				transId=request.getParameter("transactionid");
			}
			LogManager.push("Process for trans id:"+transId);
			final int result = uploadCB.getProcess(transId,bankId);
			request.setAttribute("TotalRecords", result);
			request.setAttribute("PartToShow","ProcessResult");
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		forward = mapping.findForward("uploaduser");
		return forward;
	}
	
	private ActionErrors validateForm(UploadForm form) throws CommonBaseException{
		final UploadCB uploadCB = new UploadCB();
		ActionErrors errors = new ActionErrors();
		
		 if(form.getBankname().equalsIgnoreCase("select")){
			errors.add("upload", new ActionError("file.upload.bankname.required"));			
		}
		 else if(form.getUploadFile().getFileName().length() <= 0){
			errors.add("upload", new ActionError("file.upload.file.required"));			
		}
		return errors;
	}
	
}