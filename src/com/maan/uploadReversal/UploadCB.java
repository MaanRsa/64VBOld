package com.maan.uploadReversal;

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

	public List getReversalTransactionDetails(final String transId) throws CommonBaseException{
		LogManager.info("UploadCB getTransactionDetails() Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		final List result = uploadDAO.getReversalTransactionDetails(transId);
		LogManager.info("UploadCB getTransactionDetails() Method - Exit");
		return result;
	}
	
	public List updateSubmittedReceipts(final String transId,final String checked, final String unchecked) throws CommonBaseException{
		LogManager.info("UploadCB updateSubmittedReceipts() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		List sum = uploadDAO.updateSubmittedReceipts(transId,checked,unchecked);
		LogManager.info("UploadCB updateSubmittedReceipts() receiptnos Method - Exit");
	    return sum;
	}
	
	public void updateCheckedReceipts(final String transId,final String checked, final String unchecked) throws CommonBaseException{
		LogManager.info("UploadCB updateCheckedReceipts() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		 uploadDAO.updateCheckedReceipts(transId,checked,unchecked);
		LogManager.info("UploadCB updateCheckedReceipts() receiptnos Method - Exit");
	}
	
	public List getReceiptsDetail(final String transId,final String paymentNo, boolean edit) throws CommonBaseException{
		LogManager.info("UploadCB getReceiptsDetail() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
	    List list= uploadDAO.getReceiptsDetail(transId,paymentNo,edit);
		LogManager.info("UploadCB getReceiptsDetail() receiptnos Method - Exit");
	   return list;
	}

	public void updateReceipts(String transId) throws CommonBaseException{
		LogManager.info("UploadCB updateReceipts() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		 uploadDAO.updateReceipts(transId);
		LogManager.info("UploadCB updateReceipts() receiptnos Method - Exit");
	}

	public boolean checkPayment(String paymnetNo)throws CommonBaseException{
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		boolean exists =  uploadDAO.checkPayment(paymnetNo);
		LogManager.info("UploadCB updateReceipts() receiptnos Method - Exit");
		return exists;
	}

	public void updateReversals(UploadForm sbean)throws CommonBaseException{
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		uploadDAO.updateReversals(sbean);
		LogManager.info("UploadCB updateReversals() receiptnos Method - Exit");
	}

	public void insertUpdateReversals(UploadForm sbean)throws CommonBaseException{
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		uploadDAO.insertUpdateReversals(sbean);
		LogManager.info("UploadCB updateReversals() receiptnos Method - Exit");
	}

	public List getReceiptPayments(String transId)throws CommonBaseException{
		LogManager.info("UploadCB getReceiptPayments() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
	    List list= uploadDAO.getReceiptPayments(transId);
		LogManager.info("UploadCB getReceiptPayments() receiptnos Method - Exit");
	   return list;
	}

	public void updateEditedRecords(String editPayment, String transId)throws CommonBaseException{
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		uploadDAO.updateEditedRecords(editPayment,transId);
		LogManager.info("UploadCB updateEditedRecords() receiptnos Method - Exit");
	}

	public String[][] getPaymentAmount(String editPaymentNo)throws CommonBaseException{
		LogManager.info("UploadCB getPaymentAmount() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
	    String  amount[][]= uploadDAO.getPaymentAmount(editPaymentNo);
		LogManager.info("UploadCB getPaymentAmount() receiptnos Method - Exit");
		return amount;
	}
	
}
