package com.maan.upload;

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

public class UploadDispatchAction extends AbstractCommonBaseDispatchAction 
{
	public static String checkedItems[][]=new String[0][0];
	public int spage=1;
	public int value;
	public String transId;
	
	public ActionForward upload(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		LogManager.info("UploadDispatchAction Upload() Method - Enter");
		ActionForward forward = null;
		request.setAttribute("PartToShow","Upload");
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
		final String filePath = getServlet().getServletContext().getRealPath("/")+"64VBUploadFiles";
		FormFile myFile = null;
		String fileName = null;
		LogManager.push("loginForm.getInsCompanyId()"+loginForm.getInsCompanyId());
		errors = validateForm(uploadForm,loginForm.getInsCompanyId());
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
					final String status = uploadCB.processCsv(csvLoc,loginForm.getInsCompanyId(),request);
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
			
			final List result = uploadCB.getTransactionDetails(transId,loginForm.getInsCompanyId());
			//request.setAttribute("errors", request.getAttribute("errors"));
			request.setAttribute("TransactionID", transId);
			request.setAttribute("TotalRecords", result.get(0));
			//request.setAttribute("result", result.get(1));
			request.setAttribute("Errorcount",result.get(1));
			request.setAttribute("Uploadcount",result.get(2));
			request.setAttribute("Paymentcount", result.get(3));
			request.setAttribute("PartToShow","UploadResult");
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		forward = mapping.findForward("uploaduser");
		return forward;
	}
	
	private ActionErrors validateForm(UploadForm form,final String insCompanyId) throws CommonBaseException{
		final UploadCB uploadCB = new UploadCB();
		ActionErrors errors = new ActionErrors();
		if(form.getUploadFile().getFileName().length() <= 0){
			errors.add("upload", new ActionError("file.upload.file.required"));			
		}else{
			final String fileName = form.getUploadFile().getFileName();
			final int index = fileName.lastIndexOf(".");
			final String ext = fileName.substring(index+1).trim();
			if(!"xls".equalsIgnoreCase(ext)){		
				errors.add("upload", new ActionError("file.upload.file.invalid"));
			}
		}
		return errors;
	}

}