package com.maan.excelupload;

import java.util.List;
import java.util.Map;

import com.maan.common.MotorBaseDAO;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.MotorBaseException;

public interface UploadDAO extends MotorBaseDAO {
	 String moveToMaster(String MasterQuery,String MastertableName,String TempQuery,String TempTable,int batchid,int typeid,String xgenStatus,int mfr_id,String condition,String tables,List queries) throws MotorBaseException;
	UploadVB collectTransactionInfo(int batchid, boolean status) throws MotorBaseException ;
	String[][] pendingBatchIds(final String nrRawMasTable, final String enRawMasTable);
	 String[][] pendingCount(final String nrRawMasTable, final String enRawMasTable);
	 void deleteTempRecords(int batchid, String type)throws MotorBaseException;
	void updateBankRecords(UploadVB uploadVBMaster)throws MotorBaseException, CommonBaseException;
	public int getProcess(final String transId,final String bankId) throws CommonBaseException;
	void updateReversalReceipts(String transId)throws CommonBaseException;
	public Map getBankList(UploadForm uploadForm) throws CommonBaseException;
	public List getTransactedDetails(UploadForm uploadForm);
	public int getReProcess(String transId, String bankId);
}
