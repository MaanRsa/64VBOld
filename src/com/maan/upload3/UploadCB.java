package com.maan.upload3;

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
	
		
	public List getTransactionDetails(final String transId, int i) throws CommonBaseException{
		LogManager.info("UploadCB getTransactionDetails() Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		final List result = uploadDAO.getTransactionDetails(transId,i);
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
	public void updateReceiptsDetail(final String transId,String realizeStatus) throws CommonBaseException{
		LogManager.info("UploadCB getReceiptsDetail() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
	     uploadDAO.updateReceiptsDetail(transId,realizeStatus);
		LogManager.info("UploadCB getReceiptsDetail() receiptnos Method - Exit");
	
	}

	public List getReceiptsDetail(final String transId) throws CommonBaseException{
		LogManager.info("UploadCB getReceiptsDetail() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
	    List list= uploadDAO.getReceiptsDetail(transId);
		LogManager.info("UploadCB getReceiptsDetail() receiptnos Method - Exit");
	   return list;
	}
	public List getReceiptsDetailNotMatched(final String transId) throws CommonBaseException{
		LogManager.info("UploadCB getReceiptsDetail() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
	    List list= uploadDAO.getReceiptsDetailNotMatched(transId);
		LogManager.info("UploadCB getReceiptsDetail() receiptnos Method - Exit");
	   return list;
	}


	public void updatePolicyDetails(String transId) throws CommonBaseException{
		LogManager.info("UploadCB updatePolicyDetails() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		uploadDAO.updatePolicyDetails(transId);
	}


	public List getPolicies(String transId, String status) throws CommonBaseException{
		LogManager.info("UploadCB updatePolicyDetails() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		return uploadDAO.getPolicies(transId,status);
	}


	
	
}
