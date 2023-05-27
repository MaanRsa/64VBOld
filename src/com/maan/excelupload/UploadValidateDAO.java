package com.maan.excelupload;

import java.util.List;

import com.maan.common.MotorBaseDAO;
import com.maan.common.exception.MotorBaseException;

public interface UploadValidateDAO extends MotorBaseDAO {
	public String[][] dataConvert(String colname[],int typeid,int mfr_id) throws MotorBaseException;
	public List collectFileFormatInfo(int mfr_id,int typeid)  throws MotorBaseException;
	public boolean createTempTable(String tableName) throws MotorBaseException;
	public UploadVB createTransaction(int tranId,int typeid,String filename,String path,int mfr_id,String loginid) throws MotorBaseException;
	public boolean insertRecords(List Queries,String MastertableName,String TempQuery,String TempTable,int batchid,String writeFilePath) throws MotorBaseException;
	public boolean validateRecords(String MastertableName, int batchid,int typeid,int mfr_id,String mainTable) throws MotorBaseException;
	public boolean writeUnknownColumn(long totalRows,String unknowncolumn,int batchid,String writeFilePath) throws MotorBaseException;
	public boolean validateManualMove(String MastertableName, int batchid,int typeid,int mfr_id) throws MotorBaseException;
	public List getTranIdList() throws MotorBaseException;
}
