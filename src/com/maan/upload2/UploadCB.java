package com.maan.upload2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;


public class UploadCB extends CommonBaseCB {

	public String processCsv(final String csvLoc,final String insCompanyId,HttpServletRequest request,UploadForm uploadForm) throws CommonBaseException{
		LogManager.info("UploadCB processCsv() Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		final String result = uploadDAO.processCsv(csvLoc,insCompanyId,request,uploadForm);
		LogManager.info("UploadCB processCsv() Method - Exit");
		return result;
	}
	
	public List getTransactionDetails(final String transId, final String bankId) throws CommonBaseException{
		LogManager.info("UploadCB getTransactionDetails() Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		final List result = uploadDAO.getTransactionDetails(transId,bankId);
		LogManager.info("UploadCB getTransactionDetails() Method - Exit");
		return result;
	}
	public int getProcess( final String transId, final String bankId) throws CommonBaseException{
		LogManager.info("UploadCB getprocessDetails() Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		LogManager.push("dao----:::"+UploadDAO.class.getName());
		final int result = uploadDAO.getProcess(transId,bankId);
		LogManager.info("UploadCB getProcessDetails() Method - Exit");
		return result;
	}
	
	public Map getBankList(final UploadForm form) throws CommonBaseException{
		LogManager.info(" getbankList() Method - Enter");
		Map result = new HashMap();
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		result = uploadDAO.getBankList(form);
		LogManager.info("getbankList() Method - Exit");
		return result;
	}

	
	
}
