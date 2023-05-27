package com.maan.uploadReversal;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.maan.common.base.CommonBaseDAO;
import com.maan.common.exception.CommonBaseException;

public interface UploadDAO extends CommonBaseDAO {

	public List getReversalTransactionDetails(final String transId) throws CommonBaseException;
	public List getReceiptsDetail(final String transId, String paymentNo, boolean edit) throws CommonBaseException;
	public List updateSubmittedReceipts(String transId, String checked,String unchecked)throws CommonBaseException;
	public void updateCheckedReceipts(String transId, String checked,String unchecked)throws CommonBaseException;
	public void updateReceipts(String transId)throws CommonBaseException;
	public boolean checkPayment(String paymnetNo)throws CommonBaseException;
	public void updateReversals(UploadForm sbean)throws CommonBaseException;
	public void insertUpdateReversals(UploadForm sbean)throws CommonBaseException;
	public List getReceiptPayments(String transId)throws CommonBaseException;
	public void updateEditedRecords(String editPayment, String transId) throws CommonBaseException;
	public String[][] getPaymentAmount(String editPaymentNo)throws CommonBaseException;
	
}
