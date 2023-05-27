package com.maan.excelupload;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.CommonsMultipartRequestHandler;
import org.apache.struts.upload.FormFile;

import com.maan.common.AbstractMotorBaseDispatchAction;
import com.maan.common.LogManager;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;
import com.maan.common.exception.MotorBaseException;
import com.maan.common.exception.MotorExceptionConstants;
import com.maan.transaction.TransactionVB;
import com.maan.upload3.UploadCB;

public class uploadDispatchAction extends AbstractMotorBaseDispatchAction {
	
	public String transId;
	
	public ActionForward init(final ActionMapping mapping,	final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws MotorBaseException {
		LogManager.push("------------------Executed the init method in upladDispatchAction method---------------Enter**");
		ActionForward forward = null;
		final UploadForm uploadForm = (UploadForm) form;
		try {
			final org.apache.struts.upload.CommonsMultipartRequestHandler requestHandler=new CommonsMultipartRequestHandler() ;
			//X.handleRequest(request);
			LogManager.push("upload init method() - Enter");
			uploadForm.setMode("insert");
			String typeid = "", isDirect = "";
			typeid =request.getParameter("typeid")==null?(uploadForm.getUploadType()==null?"0":uploadForm.getUploadType()):request.getParameter("typeid");
			isDirect =request.getParameter("isDirect")==null?"":request.getParameter("isDirect");
			final uploadCB uploadcb = new uploadCB();
			if(!"".equalsIgnoreCase(isDirect)) {
				request.setAttribute("partToShow", "isDirectMovement");
				List list = uploadcb.getTranIdList();
				LogManager.push("tranid list size " + list.size());
				request.setAttribute("tranIdList", list);
			} else {
				request.setAttribute("partToShow", "upload");
				uploadForm.setIsDirect("");
			}
			
			if(typeid.equalsIgnoreCase("104")){
				uploadForm.setRealizeStatus("N");
			}
			uploadForm.setUploadType(typeid);
			request.setAttribute("typeid", typeid);
			saveToken(request);
			forward = mapping.findForward("main");

		} catch (Exception e) {
			LogManager.debug(e);
			throw new MotorBaseException(e, MotorExceptionConstants.OTHER_ERROR);
		} finally {
			LogManager.push("upload init method() - Exit");
			LogManager.popRemove();
		}
		LogManager.push("------------------Executed the init method in upladDispatchAction method---------------Exit**");
		return forward;
	}

	public ActionForward Upload(final ActionMapping mapping, final ActionForm form,	final HttpServletRequest request,final HttpServletResponse response)
			throws MotorBaseException {
		
		LogManager.push("SS------------------Executed the upload method in upladDispatchAction method---------------Enter**");
		ActionForward forward;
		final UploadForm uploadForm = (UploadForm) form;
		
		LogManager.push("Enters Upload Block");
		LogManager.push("Enters Upload Block1");
		if (isTokenValid(request)) {// avoid duplicate submit
			LogManager.push("Enters Upload Block2");
			final uploadCB uploadcb = new uploadCB();
			UploadVB uploadVBMaster=new UploadVB();
			UploadVB uploadFileSts=new UploadVB();
			final HttpSession session = request.getSession(false);
			final com.maan.login.LoginForm loginform=(com.maan.login.LoginForm)session.getAttribute("loginForm");
			String filePath, DesfilePath="";
			FormFile myFile = null;
			String fileName = null;
			String desfileName = null;
			String sourceLoc = null, desLoc = null;
			File fileToCreate = null;
			boolean convertStatus=true;
			final int typeid=Integer.parseInt(uploadForm.getUploadType()==null?"0":uploadForm.getUploadType());
			final int mfr_id=Integer.parseInt((String)session.getAttribute("mfrid")==null?"0":(String)session.getAttribute("mfrid"));
			LogManager.push("Enters Upload Block3");
			final ActionErrors errors = new ActionErrors();
			try {
				LogManager.push("uploadVBMaster.getTypeID() " + uploadVBMaster.getTypeID());
				LogManager.push("typeid " + typeid);
				
				if(!"".equalsIgnoreCase(uploadForm.getIsDirect())) {
					int tranId = Integer.parseInt(uploadForm.getIsDirect());
					uploadVBMaster.setBatchID(tranId);
					//uploadcb.validateRecords("TEMP_RECEIPT_MASTER", tranId, typeid, 0);
					
					uploadcb.createTransaction(tranId,typeid, "Direct", "Direct", mfr_id,loginform.getUserId());
					uploadcb.validateRecords("TEMP_RECEIPT_MASTER", tranId, typeid,mfr_id);
					
					uploadVBMaster=uploadcb.moveMasterRecords(uploadVBMaster.getBatchID());
					if(uploadVBMaster.getInvalid()!=null){
					if(uploadVBMaster.getInvalid().contains("Invalid date Format")){
						LogManager.push("Inside Controller ::");
						errors.add("fileformat", new ActionError("error.upload.option.invalidDate",new Object[]{uploadVBMaster.getInvalid()}));
						saveMessages(request, errors);
						saveErrors(request, errors);
						}
					}
					LogManager.push("-------Next Satatement -----");
					final StringBuffer status=new StringBuffer (" Transaction ID-"+uploadVBMaster.getBatchID()
					+"<br> Total No.of Rows-"+uploadVBMaster.getTotalRecords()
					+"<br> Uploaded-"+uploadVBMaster.getUploaded()
					+"<br> Not Uploaded-"+uploadVBMaster.getPending());
					uploadForm.setUploadType(String.valueOf(uploadVBMaster.getTypeID()));
					request.setAttribute("typeid", uploadForm.getUploadType());
					request.setAttribute("typename", uploadVBMaster.getPolicyType());
					request.setAttribute("batchid", String.valueOf(uploadVBMaster.getBatchID()));
					request.setAttribute("status", status.toString());
					status.append("<br> Upload Status-");
					LogManager.push("excep " + uploadVBMaster.getUploadTransts());
					String uploadTransts = uploadVBMaster.getUploadTransts()==null?"":uploadVBMaster.getUploadTransts();
					status.append((uploadTransts.equalsIgnoreCase("X")?"Master movement pending":uploadTransts));
					
					uploadcb.updateBankRecords(uploadVBMaster);
					LogManager.push("After updateBankRecords ");
                   //deleting temp records
					uploadcb.deleteTempRecords( uploadVBMaster.getBatchID(),uploadForm.getUploadType());
					LogManager.push("After deleteTempRecords ");
					String xgenStatus = uploadVBMaster.getXgenStatus()==null?"":uploadVBMaster.getXgenStatus();
					if(uploadForm.getUploadType().equalsIgnoreCase("101"))	
					{
						uploadcb.updateReversalReceipts(String.valueOf(uploadVBMaster.getBatchID()));
					}
					if(xgenStatus.equalsIgnoreCase("Y"))
					{
						request.setAttribute("partToShow", "moveXgen");
					}else
					{
						request.setAttribute("partToShow", "XgenStatus");
					}
					resetToken(request);
					saveToken(request);
					if (!errors.isEmpty())
					{
						request.setAttribute("typeid", uploadForm.getUploadType());
						saveMessages(request, errors);
						saveErrors(request, errors);
						
						request.setAttribute("partToShow", "upload");
						LogManager.push("================Page Forwar====================");
						forward = mapping.findForward("main");
					}
				} else {
					final org.apache.struts.upload.CommonsMultipartRequestHandler requestHandler=new CommonsMultipartRequestHandler() ;
					requestHandler.handleRequest(request);
					try
					{
						if (uploadForm.getUploadFile().getFileName().length() <= 0) {
							errors.add("uploadFile", new ActionError("error.upload.file.required"));
						} else {
							LogManager.push("Enters Upload Block4");
							myFile = uploadForm.getUploadFile();
							int fileSize=myFile.getFileSize();
							fileName = myFile.getFileName();
							filePath = getServlet().getServletContext()	.getRealPath("/")+ "64VBUploadFiles";
							DesfilePath = getServlet().getServletContext().getRealPath(	"/")+ "64VBUploadFiles";
							uploadFileSts=uploadcb.collectFileFormatInfo(fileName,mfr_id,typeid);
							if(fileSize>5242880)
							{
								errors.add("option",new ActionError("error.upload.option.filesize"));
							}
							if(uploadFileSts.getUploadoption()!=null)
							{
								if(!uploadFileSts.getUploadoption().equalsIgnoreCase("Y"))
									errors.add("option", new ActionError("error.upload.option.disabled"));

								if(!uploadFileSts.isUploadstatus())
								errors.add("fileformat", new ActionError("error.upload.option.fileformat"));
							}else
								errors.add("option", new ActionError("error.upload.option.notavailable"));
							
							if (errors.isEmpty()) {
								LogManager.push("Enters Upload Block5");
								final Calendar cal = Calendar.getInstance();
								final SimpleDateFormat sdf = new SimpleDateFormat("'on'ddMMMyyyy h.mm.ss a");
								final String date = sdf.format(cal.getTime());
								String fileextension="";
								final int fileid=fileName.lastIndexOf('.');
								fileextension=fileName.substring(fileid+1, fileName.length()).trim();
								if (fileextension.equalsIgnoreCase("xls")) {
									LogManager.push("Enters Upload Block6");
									final String TpfileName=fileName.toLowerCase(Locale.ENGLISH);
									final int index = TpfileName.lastIndexOf("xls");
									final String tempFileName = fileName.substring(0, index - 1);
									fileName = tempFileName + date + ".xls";
									desfileName = tempFileName + date + ".csv";
									LogManager.push("tempFileName is  ::" + tempFileName);
									sourceLoc = filePath + "\\" + fileName;
									desLoc = DesfilePath + "\\" + desfileName;
								 	LogManager.push("Source Location ::" + sourceLoc);
									LogManager.push("Destination Location ::" + desLoc);
									fileToCreate = new File(sourceLoc);
								} else if (fileextension.equalsIgnoreCase("csv")) {
									final String TpfileName=fileName.toLowerCase(Locale.ENGLISH);
									final int index = TpfileName.lastIndexOf("csv");
									final String tempFileName = fileName.substring(0, index - 1);
									desfileName = tempFileName + date + ".csv";
									desLoc = DesfilePath + "\\" + desfileName;
									fileToCreate = new File(desLoc);
									convertStatus=false;
									LogManager.push("Destination Location ::" + desLoc);
								}else if (fileextension.equalsIgnoreCase("txt")) {
									final String TpfileName=fileName.toLowerCase(Locale.ENGLISH);
									final int index = TpfileName.lastIndexOf("txt");
									final String tempFileName = fileName.substring(0, index - 1);
									desfileName = tempFileName + date + ".txt";
									desLoc = DesfilePath + "\\" + desfileName;
									fileToCreate = new File(desLoc);
									convertStatus=false;
									LogManager.push("Destination Location ::" + desLoc);
								} else {
									errors.add("fileformat", new ActionError("error.upload.option.fileformat"));
								}
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					if (errors.isEmpty()) {
						if (fileName.length() > 0) {
							final FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
							fileOutStream.write(myFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
							
						    LogManager.push("Level ====>1");
							uploadVBMaster=uploadcb.convertFile(sourceLoc, desLoc,desfileName,typeid,mfr_id,convertStatus,loginform.getUserId());
							LogManager.push("Level ====>1 Exit");
							LogManager.push("Level ====>2");
							uploadVBMaster=uploadcb.startUpdate(desLoc,uploadVBMaster,DesfilePath,uploadFileSts.getFieldseparater(),convertStatus);
							LogManager.push("Level ====>2Exit");
													
							LogManager.push("Unknown columns:"+uploadVBMaster.getUnknowncolumn());
							if(uploadVBMaster.getUnknowncolumn().length()>0){
								errors.add("option", new ActionError("error.upload.option.unknowns"));
								saveMessages(request, errors);
								saveErrors(request, errors);
							}
							LogManager.push("This is  masterQuery"+uploadVBMaster.getMainqry());
							uploadVBMaster=uploadcb.moveMasterRecords(uploadVBMaster.getBatchID());
							if(uploadVBMaster.getInvalid()!=null){
								if(uploadVBMaster.getInvalid().contains("Invalid date Format")){
									LogManager.push("Inside Controller ::");
									errors.add("fileformat", new ActionError("error.upload.option.invalidDate",new Object[]{uploadVBMaster.getInvalid()}));
									saveMessages(request, errors);
									saveErrors(request, errors);
								}
							}
							LogManager.push("-------Next Satatement -----");
							final StringBuffer status=new StringBuffer (" Transaction ID-"+uploadVBMaster.getBatchID()
							+"<br> Total No.of Rows-"+uploadVBMaster.getTotalRecords()
							+"<br> Uploaded-"+uploadVBMaster.getUploaded()
							+"<br> Not Uploaded-"+uploadVBMaster.getPending());
							uploadForm.setUploadType(String.valueOf(uploadVBMaster.getTypeID()));
							request.setAttribute("typeid", uploadForm.getUploadType());
							request.setAttribute("typename", uploadVBMaster.getPolicyType());
							request.setAttribute("batchid", String.valueOf(uploadVBMaster.getBatchID()));
							request.setAttribute("status", status.toString());
							status.append("<br> Upload Status-");
							String uploadTransts = uploadVBMaster.getUploadTransts()==null?"":uploadVBMaster.getUploadTransts();
							status.append((uploadTransts.equalsIgnoreCase("X")?"Master movement pending":uploadTransts));
							
							uploadcb.updateBankRecords(uploadVBMaster);
							LogManager.push("After updateBankRecords ");
	                        //deleting temp records
							uploadcb.deleteTempRecords( uploadVBMaster.getBatchID(),uploadForm.getUploadType());
							LogManager.push("After deleteTempRecords ");
							
							if(uploadForm.getUploadType().equalsIgnoreCase("104"))
							{
								LogManager.push("Another Methods");
								UploadCB ucB = new UploadCB();
								ucB.updateReceiptsDetail(String.valueOf(uploadVBMaster.getBatchID()),uploadForm.getRealizeStatus());
								final UploadCB udCB = new UploadCB();
								final List result = udCB.getTransactionDetails(String.valueOf(uploadVBMaster.getBatchID()),uploadVBMaster.getTypeID());
								//request.setAttribute("TransactionID", transId);
								request.setAttribute("TotalRecords", result.get(0));
								request.setAttribute("Errorcount",result.get(1));
								request.setAttribute("Uploadcount",result.get(2));
								request.setAttribute("NotRect",result.get(3));
								request.setAttribute("Matched",result.get(4));
								request.setAttribute("NotMatched",result.get(5));
								request.setAttribute("PartToShow","UploadResult");
							}
							if(uploadForm.getUploadType().equalsIgnoreCase("109")){
								final UploadCB udCB = new UploadCB();
								udCB.updatePolicyDetails(String.valueOf(uploadVBMaster.getBatchID()));
								final List result = udCB.getTransactionDetails(String.valueOf(uploadVBMaster.getBatchID()),uploadVBMaster.getTypeID());
								//request.setAttribute("TransactionID", transId);
								request.setAttribute("Realized", result.get(0));
								request.setAttribute("Returned", result.get(1));
								request.setAttribute("NotKnown", result.get(2));
								request.setAttribute("NotAvailable", result.get(3));
								
								request.setAttribute("PartToShow","UploadResult");
							}
							if(uploadForm.getUploadType().equalsIgnoreCase("107"))
							{
								LogManager.push("Another Methods");
								com.maan.uploadReversal.UploadCB ucB = new com.maan.uploadReversal.UploadCB();
								final List result = ucB.getReversalTransactionDetails(String.valueOf(uploadVBMaster.getBatchID()));
								request.setAttribute("TotalRecords", result.get(0));
								request.setAttribute("Errorcount","0");
								request.setAttribute("Uploadcount",result.get(0));
								request.setAttribute("Available",result.get(1));
								request.setAttribute("NotAvailable",Integer.toString(Integer.parseInt(result.get(0).toString())-Integer.parseInt(result.get(1).toString())));
								request.setAttribute("PartToShow","UploadResult");
							}
							if(uploadForm.getUploadType().equalsIgnoreCase("101"))	
							{
								uploadcb.updateReversalReceipts(String.valueOf(uploadVBMaster.getBatchID()));
							}
							if(uploadVBMaster.getXgenStatus().equalsIgnoreCase("Y"))
							{
								request.setAttribute("partToShow", "moveXgen");
							}else
							{
								request.setAttribute("partToShow", "XgenStatus");
							}
							resetToken(request);
							saveToken(request);
							if (!errors.isEmpty())
							{
								request.setAttribute("typeid", uploadForm.getUploadType());
								saveMessages(request, errors);
								saveErrors(request, errors);
								
								request.setAttribute("partToShow", "upload");
								LogManager.push("================Page Forwar====================");
								forward = mapping.findForward("main");
							}
						} 
					} else {
						LogManager.push("Controller uploadfile method() - error is there");
						request.setAttribute("typeid", uploadForm.getUploadType());
						saveMessages(request, errors);
						saveErrors(request, errors);
						
						request.setAttribute("partToShow", "upload");
						forward = mapping.findForward("main");
					}
				}
				forward = mapping.findForward("main");
			} catch (Exception e) {
				LogManager.debug(e);
				throw new MotorBaseException(e,	MotorExceptionConstants.Config_error);
			} finally {
				LogManager.push("Controller uploaddinehsfile method() - Exit");
				LogManager.popRemove();
			}
		} else {
			request.setAttribute("partToShow", "upload");
			forward = mapping.findForward("main");
		}
		return forward;
	}
	
	public ActionForward processRecords(final ActionMapping mapping,final ActionForm form, final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		ActionForward forward = null;
		//final HttpSession session = request.getSession(false);
		//final LoginForm loginForm = (LoginForm)session.getAttribute("loginForm");
		final uploadCB uploadCB = new uploadCB();
		final UploadForm uploadForm = (UploadForm) form;
		String bankId=request.getParameter("bankName");
		
		LogManager.push("Processing Bank Id:"+bankId);
		LogManager.push("UploadDispatchAction process() Method - Enter");
		try{
			transId=request.getParameter("batchid");
			LogManager.push("Process for trans id:"+transId);
			final int result = uploadCB.getProcess(transId,bankId);
			request.setAttribute("TotalRecords", result);
			request.setAttribute("PartToShow","ProcessResult");
		}catch(Exception e){
			LogManager.debug(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		forward = mapping.findForward("main");
		return forward;
	}
	
	public ActionForward receiptUpdate(final ActionMapping mapping, final ActionForm form,	
			final HttpServletRequest request,final HttpServletResponse response) throws MotorBaseException {
		LogManager.push("Enter uploadDispatchAction receiptUpdate() ");
		ActionForward forward = null;
		final UploadForm uploadForm = (UploadForm) form;
		try {
			final uploadCB uploadCB = new uploadCB();
			final ActionErrors errors = new ActionErrors();
			uploadForm.setBankName("select");
			String bankId=request.getParameter("bankName");
			String tranId=request.getParameter("tranId");
			String mode=request.getParameter("mode");
			if(StringUtils.isNotBlank(bankId))
				uploadForm.setBankName(bankId);
			if(StringUtils.isNotBlank(tranId))
				uploadForm.setTranId(tranId);
			
			final Map bankList = uploadCB.getBankList(uploadForm);
			request.setAttribute("BankList", bankList);
			request.setAttribute("PartToShow","searchProcess");
			if("list".equalsIgnoreCase(mode)){
				if(StringUtils.isBlank(uploadForm.getBankName()) || "select".equalsIgnoreCase(uploadForm.getBankName()))
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {"Please Select Bank Name</br>"}));
				if(StringUtils.isBlank(uploadForm.getTranId()))
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {"Please Enter Transaction Id</br>"}));
				if(StringUtils.isNotBlank(uploadForm.getTranId())){
					try{
						int val=Integer.parseInt(uploadForm.getTranId());
					}catch(Exception e){
						errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("search.error.display", new Object[] {"Please Enter Valid Transaction Id</br>"}));
					}
					
				}
					
				if(errors.isEmpty()){
					request.setAttribute("PartToShow","searchList");
					final List list = uploadCB.getTransactedDetails(uploadForm);
					List processed=new ArrayList();
					for(int i=0;i<list.size();i++)
					{
						TransactionVB tVB=(TransactionVB) list.get(i);
						processed.add(tVB);
						String process=tVB.getProcessed();
						LogManager.push("Process count:"+process);
					}
					request.setAttribute("Processed", processed);
				}else{
					uploadForm.setMode("");
					saveMessages(request, errors);
					saveErrors(request, errors);
					request.setAttribute("myerror", errors);
				}			
			}
			forward=mapping.findForward("receipt");
		} catch (Exception e) {
			e.printStackTrace();
		} 		
		return forward;
	}
	
	public ActionForward reprocessRecords(final ActionMapping mapping,final ActionForm form, 
			final HttpServletRequest request,final HttpServletResponse response) throws CommonBaseException {
		ActionForward forward = null;
		final uploadCB uploadCB = new uploadCB();
		final UploadForm uploadForm = (UploadForm) form;
		String bankId=request.getParameter("bankName");
		
		LogManager.push("reprocessRecords Bank Id:"+bankId);
		LogManager.push("UploadDispatchAction process() Method - Enter");
		try{
			transId=request.getParameter("tranId");
			LogManager.push("reprocessRecords for trans id:"+transId);
			final int result = uploadCB.getReProcess(transId,bankId);
			request.setAttribute("TotalRecords", result);
			request.setAttribute("PartToShow","ProcessResult");
		}catch(Exception e){
			LogManager.debug(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		forward = mapping.findForward("main");
		return forward;
	}
	
	}