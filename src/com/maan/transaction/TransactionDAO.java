package com.maan.transaction;

import java.util.HashMap;
import java.util.List;

import com.maan.common.exception.CommonBaseException;

public interface TransactionDAO {
	public int getAllProcess(final String bankId, String[] val) throws CommonBaseException;
	public List getTransactedDetails(final TransactionForm tForm) throws CommonBaseException;
	public List getRecords(TransactionForm form)throws CommonBaseException;
	public void updateSelected(String checkedTransactNos, String uncheckedTransactNos)throws CommonBaseException;
	public void updateTransactions()throws CommonBaseException;
	public List getSelectedTransactions()throws CommonBaseException;
	public List getTransactionDetails(String transid)throws CommonBaseException;
	public int getProcessCount()throws CommonBaseException;
	public List getPolicyNumbers()throws CommonBaseException;
}
