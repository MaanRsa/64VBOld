package com.maan.transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import com.maan.common.LogManager;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;
import com.maan.upload2.UploadDAO;
import com.maan.upload2.UploadDAOImpl;

public class TransactionCB {

	public List getTransactedDetails(final TransactionForm tForm) throws CommonBaseException
	{
		List list=null;
		LogManager.info("TransactionCB getstatusUpdationRecord Method - Enter");
		final TransactionDAO transDAO = (TransactionDAOImpl) CommonDaoFactory.getDAO(TransactionDAO.class.getName());
		list = transDAO.getTransactedDetails(tForm);
		LogManager.info("TransactionCB getstatusUpdationRecord Method - Enter");
		return list;
	}
	
	public int getAllProcess( final String bankId, String[] val) throws CommonBaseException{
		LogManager.info("TransactionCB getAllProcess() Method - Enter");
		final TransactionDAO transDAO = (TransactionDAOImpl) CommonDaoFactory.getDAO(TransactionDAO.class.getName());
		LogManager.push("dao----:::"+TransactionDAO.class.getName());
		final int result = transDAO.getAllProcess(bankId,val);
		LogManager.info("TransactionCB getAllProcess() Method - Exit");
		return result;
	}
	public List getRecords(final TransactionForm tForm) throws CommonBaseException
	{
		List list=null;
		LogManager.info("TransactionCB getRecords Method - Enter");
		final TransactionDAO transDAO = (TransactionDAOImpl) CommonDaoFactory.getDAO(TransactionDAO.class.getName());
		list = transDAO.getRecords(tForm);
		LogManager.info("TransactionCB getRecords Method - Exit");
		return list;
	}
	

	public void updateSelected(String checkedTransactNos,String uncheckedTransactNos)throws CommonBaseException {
		LogManager.info("TransactionCB updateSelected Method - Enter");
		TransactionDAO tdao=(TransactionDAOImpl)CommonDaoFactory.getDAO(TransactionDAO.class.getName());
		tdao.updateSelected(checkedTransactNos,uncheckedTransactNos);
		LogManager.info("TransactionCB updateSelected Method - Exit");
	}

	public void updateTransactions() throws CommonBaseException {
		LogManager.info("TransactionCB updateTransactions Method - Enter");
		TransactionDAO tdao=(TransactionDAOImpl)CommonDaoFactory.getDAO(TransactionDAO.class.getName());
		tdao.updateTransactions();
		LogManager.info("TransactionCB updateTransactions Method - Exit");
	}

	public List getSelectedTransactions()throws CommonBaseException {
		LogManager.info("TransactionCB updateTransactions Method - Enter");
		TransactionDAO tdao=(TransactionDAOImpl)CommonDaoFactory.getDAO(TransactionDAO.class.getName());
		List selected = tdao.getSelectedTransactions();
		LogManager.info("TransactionCB updateTransactions Method - Exit");
	    return selected;
		
	}

	public List getTransactionDetails(String transid) throws CommonBaseException {
		LogManager.info("TransactionCB getTransactionDetails Method - Enter");
		TransactionDAO tdao=(TransactionDAOImpl)CommonDaoFactory.getDAO(TransactionDAO.class.getName());
		List selected = tdao.getTransactionDetails(transid);
		LogManager.info("TransactionCB getTransactionDetails Method - Exit");
	    return selected;
		
	}

	public int getProcessCount() throws CommonBaseException{
		LogManager.info("TransactionCB getProcessCount Method - Enter");
		TransactionDAO tdao=(TransactionDAOImpl)CommonDaoFactory.getDAO(TransactionDAO.class.getName());
		int i=tdao.getProcessCount();
		LogManager.info("TransactionCB getProcessCount Method - Exit");
	    return i;
	}

	public List getPolicyNumbers() throws CommonBaseException{
		LogManager.info("TransactionCB getPolicyNumbers Method - Enter");
		TransactionDAO tdao=(TransactionDAOImpl)CommonDaoFactory.getDAO(TransactionDAO.class.getName());
		return tdao.getPolicyNumbers();
	}
}
