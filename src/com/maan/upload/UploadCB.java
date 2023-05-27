package com.maan.upload;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseCB;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;

public class UploadCB extends CommonBaseCB {

	public String processCsv(final String csvLoc,final String insCompanyId,HttpServletRequest request) throws CommonBaseException{
		LogManager.info("UploadCB processCsv() Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		final String result = uploadDAO.processCsv(csvLoc,insCompanyId,request);
		LogManager.info("UploadCB processCsv() Method - Exit");
		return result;
	}
	
		
	public List getTransactionDetails(final String transId, final String insCompanyId) throws CommonBaseException{
		LogManager.info("UploadCB getTransactionDetails() Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		final List result = uploadDAO.getTransactionDetails(transId,insCompanyId);
		LogManager.info("UploadCB getTransactionDetails() Method - Exit");
		return result;
	}
	
	
	public List getDbColumnsForDownload(final String insCompanyId,final String masterTable) throws CommonBaseException
	{
		LogManager.push("getDbColumnsForDownload() method ");
		LogManager.logEnter();
		
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		final List result = uploadDAO.getDbColumnsForDownload(insCompanyId,masterTable);
		
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return result;
	}
	
	
}
