package com.maan.excelupload;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.maan.common.LogManager;
import com.maan.common.MotorDaoFactory;
import com.maan.common.base.CommonDaoFactory;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.MotorBaseException;

import csvconverter.maan.common.CSVConverter;

public class uploadCB {
	
	 private static String REGEX = "[a-z,A-Z,0-9,@,#,$,%,^,&,~,/,:,.,'\t',' ',-]";
	 private static String REPLACE = "";
	 
	 public UploadVB collectFileFormatInfo(final String fileName,final int mfr_id,final int typeid)throws MotorBaseException 
	{
		List list;
		Map values;
		final StringBuffer fileformatsin=new StringBuffer(16);String fileextension="",xfilename="";
		LogManager.push("collectFileFormatInfo() method -Enter");
		LogManager.logEnter();
		final UploadValidateDAO uploadValidateDAO = (UploadValidateDAOImpl) MotorDaoFactory.getDAO(UploadValidateDAO.class.getName());
		final UploadVB uploadVB =new UploadVB();
		list=uploadValidateDAO.collectFileFormatInfo(mfr_id, typeid);
		if (!list.isEmpty()) {
			uploadVB.setUploadstatus(false);
			for (int i = 0; i < list.size(); i++) {
				values = (Map) list.get(i);
				fileformatsin.append((String) values.get("FILE_FORMAT"));
				uploadVB.setUpload_option((String) values.get("UPLOAD_OPTION"));
				final int fileid=fileName.lastIndexOf('.');
				fileextension=fileName.substring(fileid+1, fileName.length()).trim();
				xfilename=((String) values.get("FILE_FORMAT")).trim();
				
                if(fileextension.equalsIgnoreCase(xfilename))
                {
                	LogManager.push("file format1"+fileextension+" ext1"+(String) values.get("FILE_FORMAT"));
                	uploadVB.setField_separater((String) values.get("FIELD_SEPARATER"));
                	uploadVB.setFileformat((String) values.get("FILE_FORMAT"));
                	uploadVB.setMfrcode((String) values.get("MFR_CODE"));
                	uploadVB.setUploadstatus(true);
                }
					}
		}
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		LogManager.push("collectFileFormatInfo() method -Exit");
		return uploadVB;
	}

	 public List getTranIdList() throws MotorBaseException {
		final UploadValidateDAO uploadValidateDAO = (UploadValidateDAOImpl) MotorDaoFactory.getDAO(UploadValidateDAO.class.getName());
		List list = uploadValidateDAO.getTranIdList();
		return list;
	 }
	 
	public UploadVB convertFile(final String source,final String destination,final String fileName,final int typeid,final int mfr_id,final boolean convertStatus,final String loginid)
			throws MotorBaseException {
		
		LogManager.push("convertFile() method --------------------Enter");		
		UploadVB uploadVB =new UploadVB();
		final UploadValidateDAO uploadValidateDAO = (UploadValidateDAOImpl) MotorDaoFactory.getDAO(UploadValidateDAO.class.getName());
		if(convertStatus)
        {
			try {
				
				CSVConverter.csvConvertion(source, destination, "dd/MM/yyyy","\t");
				LogManager.push("New csv converter");
				final File file = new File(source);
				file.delete();
			} catch (Exception e) {
				LogManager.push(e.getMessage());
				e.printStackTrace();
			}
        }
		uploadVB=uploadValidateDAO.createTransaction(0,typeid, fileName, destination, mfr_id,loginid);
		LogManager.push("convertFile() method --------------------Exit");
		return uploadVB;
	}
	
	public void validateRecords(String master_table, int  batchid, int typeid, int mfr_id) throws MotorBaseException {
		LogManager.push("validateRecords Enter");
		final UploadValidateDAO uploadValidateDAO = (UploadValidateDAOImpl) MotorDaoFactory.getDAO(UploadValidateDAO.class.getName());
		uploadValidateDAO.validateRecords(master_table, batchid, typeid,mfr_id,"");
		LogManager.push("validateRecords Exit");		
	}
	
	public void createTransaction(int tranId,int typeid,String fileName,String destination,int mfr_id,String loginid) throws MotorBaseException {
		LogManager.push("createTransaction Enter");
		final UploadValidateDAO uploadValidateDAO = (UploadValidateDAOImpl) MotorDaoFactory.getDAO(UploadValidateDAO.class.getName());
		uploadValidateDAO.createTransaction(tranId, typeid, fileName, destination, mfr_id,loginid);
		LogManager.push("createTransaction Exit");		
	}
	
	public UploadVB startUpdate(final String destination,final UploadVB uploadVBMaster,final String writeFilePath,final String fieldSeprator,final boolean convertStatus) throws MotorBaseException
	{
		LogManager.push("startUpdate() method----------------------Enter ");
		UploadVB uploadVB =new UploadVB();
		final UploadValidateDAO uploadValidateDAO = (UploadValidateDAOImpl) MotorDaoFactory.getDAO(UploadValidateDAO.class.getName());
		final UploadDAO uploadDAO = (UploadDAOImpl) MotorDaoFactory.getDAO(UploadDAO.class.getName());
		StringBuffer status = new StringBuffer();
		String temptbl = "", sql = "",stsTemp="", tempString = "", tempqry,unknowncolumn="",mainqry="",masterqry,INPUT="",tempMasterTbl="";
		final String master_table=uploadVBMaster.getMasterTempTbl();
		final String main_table=uploadVBMaster.getMastterTbl();
		final int batchid=uploadVBMaster.getBatchID();
		final int typeid=uploadVBMaster.getTypeID();
		final int mfr_id=uploadVBMaster.getManufactureID();
		final StringBuffer error = new StringBuffer(16);
		StringBuffer spchar=new StringBuffer(16);
		final int headerlineno=uploadVBMaster.getHeaderlineno();
		boolean ucolstatus = true;
		final List queries=new ArrayList(); 
		long LinesProcessed = 0l;
		LogManager.push("Destination file==>"+destination);
		final File file = new File(destination);		
		int columns = 0, tempColumns = 0, numberOfCommas = 0, lines=0;
		final ArrayList list = new ArrayList();
		boolean isTest = false;
		String dataQuery="";
		
		try {
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			DataInputStream dis = null;
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			while (dis.available() != 0) {
				tempString = dis.readLine();
				if(convertStatus)
					numberOfCommas = tempString.replaceAll("[^\t]", "").length();
				else
					numberOfCommas = tempString.replaceAll("[^"+fieldSeprator+"]", "").length();
			
				if (columns == 0) 
					columns = numberOfCommas; 
				if (tempColumns == 0 && numberOfCommas == columns) 
					tempColumns = numberOfCommas;
				if (columns <= tempColumns) {
					tempColumns = 0;
					isTest = true;
					status.append(tempString);
				} else if (tempColumns < columns) {
					tempColumns += numberOfCommas;
					if (columns <= tempColumns) {
						tempColumns = 0;
						isTest = true;
					}
				}
				if (isTest) {
					if (numberOfCommas < columns && status.length() != 0) {
						status.append(tempString);
					}
					
					list.add(status);
					status = new StringBuffer();
					isTest = false;
					tempString = "";
				}
				if (!isTest) {
					status.append(tempString);
				}
			}
		 
			for (int i = 0; i < list.size(); i++) {
			if (unknowncolumn.length() > 0)
					{ ucolstatus = false;}

				status = (StringBuffer)list.get(i);
				LinesProcessed++;
				if (LinesProcessed == headerlineno ) {
					uploadVB = uploadFields(status.toString(),typeid,mfr_id,fieldSeprator,convertStatus);
			 
					temptbl=uploadVB.getTempTableName();
					tempqry=uploadVB.getTempqry();
					mainqry=uploadVB.getMainqry();
					masterqry=uploadVB.getMasterqry();
					unknowncolumn=uploadVB.getUnknowncolumn();
					//tempMasterTbl = uploadVB.getMasterTempTbl();
					
					
					
				}
				stsTemp=status.toString();
				stsTemp = stsTemp.replaceAll("\'", "");
				stsTemp = stsTemp.replaceAll("\"", "");
				stsTemp = stsTemp.replaceAll("¤", "");
				INPUT=stsTemp;
				Pattern p = Pattern.compile(REGEX);
		        Matcher m = p.matcher(INPUT); // get a matcher object
		        INPUT = m.replaceAll(REPLACE);
		        INPUT=INPUT.trim();
		        if(INPUT.length()>0)
		        {
			        try {
						for(char s : INPUT.toCharArray())
						{ 
							if(s=='\\'){
							LogManager.push("checked");
							spchar.append("\\\\,");
							}
							else if(s=='[')
							{
								
							}
							else if(s==']')
							{
								
							}
							else{
								spchar.append(s+",");
							}
						   	}
							try {
								Matcher m1;
								if(spchar.length()>0){
									Pattern p1 = Pattern.compile("["+spchar.substring(0,spchar.length()-1)+"]");
									m1 = p1.matcher(stsTemp);
									stsTemp = m1.replaceAll(REPLACE);
								}
							} catch (Exception e) {
								LogManager.push("Error in spl char removal ");
								e.printStackTrace();
							}
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
				if(convertStatus)
				{
					stsTemp = stsTemp.replaceAll("\t", "'),trim('");
				}
				else
				{
					if(mfr_id==103)
						stsTemp=stsTemp.substring(0, stsTemp.length()-2);
					stsTemp = stsTemp.replaceAll(fieldSeprator, "'),trim('");	
				}
				
				int end=stsTemp.lastIndexOf('\'');
				if(end==-1)
					end=1;

				String firstData = "", lastData = "";
				if(stsTemp.length()>0 && stsTemp != null)
				{
					firstData=stsTemp.substring(0, end);
					lastData=stsTemp.substring(end,stsTemp.length());
				}
				if(lastData.contains(",")){
					lastData=lastData.replaceAll(",", "");
				}
			
				stsTemp=firstData+lastData;
				status = new StringBuffer(stsTemp + "')," + batchid + ",'1','N'");
			try {
				if (LinesProcessed >headerlineno && ucolstatus ) {
					//sql = "insert into " + temptbl + " values (trim('"+ status+ ")";
					sql = "insert into " + master_table + "(" + mainqry+ ")  values (trim('"+ status+ ")";
					queries.add(lines, sql);
					lines++;
				}
				} catch (Exception e) {
					LogManager.push("prob in insert temp table"+e.getMessage());
					LogManager.push("status "+ status);
					error.append("<br>Problem in uploading excel file line no - ");error.append(LinesProcessed);
				}
			}
			
			LogManager.push("-------Comma Remove"+status);
			fis.close();
			bis.close();
			dis.close();
		} catch (FileNotFoundException e) {
			status = new StringBuffer();
			e.printStackTrace();
			LogManager.push("File not found Exception is MoveMasterRecords() method "+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			LogManager.debug(e);
			LogManager.push("IO Exception is  MoveMasterRecords() method "+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			LogManager.push("General Exception is  MoveMasterRecords() method "+e.getMessage());
		}
		LogManager.push("Here is funda==========unknowncolumn============>>"+unknowncolumn);
		uploadVBMaster.setUnknowncolumn(unknowncolumn);
		if (unknowncolumn.length() > 0)
			ucolstatus = false;
		
		if(ucolstatus)
		{
			LogManager.push("table name "+ master_table + " mainqry " + mainqry);
			LogManager.push("temptbl " + temptbl + " batchid " + batchid + " writeFilePath " + writeFilePath);
			boolean insertsts = uploadValidateDAO.insertRecords(queries, master_table, mainqry,temptbl,batchid,writeFilePath);
			if(insertsts)
				uploadValidateDAO.validateRecords(master_table, batchid, typeid,mfr_id,main_table);
		}
		else
			uploadValidateDAO.writeUnknownColumn(LinesProcessed,unknowncolumn,batchid,writeFilePath);
		
	 	final UploadVB uploadTran=uploadDAO.collectTransactionInfo(batchid, false);
	 	uploadVBMaster.setTotalRecords(uploadTran.getTotalRecords());
	 	uploadVBMaster.setUploaded(uploadTran.getUploaded());
	 	uploadVBMaster.setPending(uploadTran.getPending());
	 	uploadVBMaster.setUploadTransts(uploadTran.getUploadTransts());
	 	uploadVBMaster.setXgenStatus(uploadTran.getXgenStatus());
	 	LogManager.push("startUpdate() method----------------------Exit ");
	 	return uploadVBMaster;
	}
			
	public UploadVB collectTransactionInfo(final int batchid,final boolean status)throws MotorBaseException {
		LogManager.push("collectTransactionInfo() method----------------------Enter ");
		
		UploadDAO uploadDAO = (UploadDAOImpl) MotorDaoFactory.getDAO(UploadDAO.class.getName());
		UploadVB uploadTran=uploadDAO.collectTransactionInfo(batchid, false);
		
		LogManager.push("collectTransactionInfo() method----------------------Exit ");
		return uploadTran;
	}
	
	public UploadVB moveMasterRecords(final int batchid)  throws MotorBaseException
	{
		LogManager.push("MoveMasterRecords() method----------------------Enter ");
		LogManager.logEnter();
		UploadVB uploadVBMaster=new UploadVB();
		String status="true";
		//MOVING MASTER TEMP TABLE TO MASTER TABLE
		final UploadDAO uploadDAO = (UploadDAOImpl) MotorDaoFactory.getDAO(UploadDAO.class.getName());
		uploadVBMaster=uploadDAO.collectTransactionInfo(batchid, true);
		uploadVBMaster.setBatchID(batchid);
		String MasterQry=null;
		String TempQry=null;
		 
        MasterQry=uploadVBMaster.getMainqry() + "BATCHID,ACTIVE,validate_status";
        TempQry=uploadVBMaster.getTempqry()+ uploadVBMaster.getMasterTempTbl()+".BATCHID,"+uploadVBMaster.getMasterTempTbl()+".ACTIVE,"+uploadVBMaster.getMasterTempTbl()+".validate_status";
    	LogManager.push("MoveMasterRecords  MasterQry====================>"+MasterQry);
		LogManager.push("MoveMasterRecords  TempQry====================>"+TempQry);
		LogManager.push("uploadVBMaster.getMastterTbl()" +uploadVBMaster.getMastterTbl());
		LogManager.push("uploadVBMaster.getMasterTempTbl()"+uploadVBMaster.getMasterTempTbl());
		LogManager.push("batchid"+batchid);
		LogManager.push("uploadVBMaster.getTypeID()"+uploadVBMaster.getTypeID());
		LogManager.push("uploadVBMaster.getXgenStatus()"+uploadVBMaster.getXgenStatus());
		LogManager.push("uploadVBMaster.getManufactureID()"+uploadVBMaster.getManufactureID());
		LogManager.push("uploadVBMaster.getManufactureID()"+uploadVBMaster.getManufactureID());
		LogManager.push("uploadVBMaster.getCondition()"+uploadVBMaster.getCondition());
		LogManager.push("uploadVBMaster.getTables()"+uploadVBMaster.getTables());
		LogManager.push("uploadVBMaster.getQueries()"+uploadVBMaster.getQueries());
		status=uploadDAO.moveToMaster(MasterQry,uploadVBMaster.getMastterTbl(),TempQry,uploadVBMaster.getMasterTempTbl(),batchid,uploadVBMaster.getTypeID(),uploadVBMaster.getXgenStatus(),uploadVBMaster.getManufactureID(),uploadVBMaster.getCondition(),uploadVBMaster.getTables(),uploadVBMaster.getQueries());
		LogManager.push("Status in CB ::"+status); 
		if(status.contains("Invalid date Format")){
			 uploadVBMaster.setInvalid(status);
		}
		else{
			uploadVBMaster=uploadDAO.collectTransactionInfo(batchid, false);
		}
		uploadVBMaster.setBatchID(batchid);
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		LogManager.push("MoveMasterRecords() method----------------------Exit ");
		return uploadVBMaster;
	}
	
	public UploadVB deleteTempRecords(final int batchid, String type)  throws MotorBaseException
	{
		LogManager.push("TEmpREcords Delete() method----------------------Enter ");
		LogManager.logEnter();
		UploadVB uploadVBMaster=new UploadVB();
		boolean status=true;
		
		final UploadDAO uploadDAO = (UploadDAOImpl) MotorDaoFactory.getDAO(UploadDAO.class.getName());
		//Deleting Temp Records
		uploadDAO.deleteTempRecords(batchid, type);
		
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		LogManager.push("TEmpREcords Delete() method----------------------Exit ");
		return uploadVBMaster;
	}
	
	
		
	public UploadVB uploadFields(final String heading,final int typeid,final int mfr_id,final String fieldSeprator,final boolean convertStatus) throws MotorBaseException{
		
		LogManager.push("UploadFields() method---------------------------Enter ");
		LogManager.logEnter();
		final UploadVB uploadVB=new UploadVB();
		
		final StringBuffer tempqry=new StringBuffer(16),unknowncolumn=new StringBuffer(16),mainqry=new StringBuffer(16),masterqry=new StringBuffer(16);
		
		int total=0;
		int NoofColumns = 0, mstate = 0, qstate = 0;
		String TempTable = "", TempTable_Name = "";
		LogManager.push("the header fieldSeprator" + fieldSeprator);
		StringTokenizer stoken=null,stoken1=null;
        if(convertStatus)
        	{ stoken= new StringTokenizer(heading, "\t"); }
        else
        	{ stoken = new StringTokenizer(heading, fieldSeprator);}
        
		while (stoken.hasMoreTokens()) 
		{
			stoken.nextToken();
			NoofColumns++;
		}
		String[] columnsFirst = new String[NoofColumns];
		String[] columns = new String[NoofColumns];
		String[][] columnexcel = new String[NoofColumns][4];
		String[] knowncolumn = new String[NoofColumns];
		
		if(convertStatus)
			{ stoken1= new StringTokenizer(heading, "\t");}
	        else
	        	{ stoken1 = new StringTokenizer(heading, fieldSeprator);}
		
		
		while (stoken1.hasMoreTokens()) {
			columnsFirst[mstate] = stoken1.nextToken().trim();
			mstate++;
		}

		columns = removeDupXlColumns(columnsFirst);
	
		
		final UploadValidateDAO uploadValidateDAO = (UploadValidateDAOImpl) MotorDaoFactory.getDAO(UploadValidateDAO.class.getName());
		columnexcel = uploadValidateDAO.dataConvert(columns, typeid,mfr_id);
		
		
		LogManager.push("column excel length" + columnexcel.length);
		tempqry.append('(');
		for (int x = 0; x < columnexcel.length; x++) {
			if (columnexcel[x][0] == null) {
				unknowncolumn.append(" " + columns[x] + " ,");
				tempqry.append("uncolumn" + x + " varchar2(500),");
			} 
			else {
				total++;
				knowncolumn[qstate] = columnexcel[x][0];
				
				tempqry.append((columnexcel[x][0].trim()) + " varchar2(500),");
				mainqry.append((columnexcel[x][0].trim()) + ",");
				
                
				
				if ("DATE".equalsIgnoreCase(columnexcel[x][2])) {
					
					
					LogManager.push("Inside Date Fields query:::>>>>"+"to_date("+columnexcel[x][0].trim()+",'"+columnexcel[x][3].trim()+"')"+",");
					 masterqry.append(("to_date("+columnexcel[x][0].trim()+",'"+columnexcel[x][3].trim()+"')")+",");
					
				}else {
					masterqry.append((columnexcel[x][0].trim()) + ",");
				}
				qstate++;
			}
		}

	
		tempqry.append("BATCHID number(10),ACTIVE char(1),validate_status VARCHAR2(50))");
		mainqry.append("BATCHID,ACTIVE,validate_status");
		masterqry.append("BATCHID,ACTIVE,validate_status");
		// mainqry=mainqry.substring(0,(mainqry.length() - 1)) ;
//		LogManager.push("tempqry" + tempqry);
		LogManager.push("mainqry" + mainqry);
		LogManager.push("masterqry" + masterqry);
		LogManager.push("unknowncolumns are " + unknowncolumn);

		
/*		if (unknowncolumn.length() <= 0) {
			
				final Calendar cal = Calendar.getInstance();
				final SimpleDateFormat sdf = new SimpleDateFormat(
						"'on'ddMMMyyyyhhmmss");
				
				final String date = sdf.format(cal.getTime());
				TempTable_Name = "Temp_RSA" + date;
				TempTable = "Temp_RSA" + date + tempqry;
				//LogManager.push("TempTable is"+TempTable);
				uploadValidateDAO.createTempTable(TempTable);
								
			
		}*/// unknowncolumn.length<=0
		
		uploadVB.setMainqry(mainqry.toString());
		uploadVB.setTempqry(tempqry.toString());
		uploadVB.setMasterqry(masterqry.toString());
		uploadVB.setUnknowncolumn(unknowncolumn.toString());
		uploadVB.setTempTable_Name(TempTable_Name);
		
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		LogManager.push("UploadFields() method---------------------------Exit ");
		return uploadVB;
	}
	
	
	public String[] removeDupXlColumns(final String xlcolumn[]) {
	
		LogManager.push("removeDupXlColumns() method-----------------------Enter ");
		LogManager.logEnter();
		final ArrayList list = new ArrayList();
		String colname = "";

		for (int i = 0; i < xlcolumn.length; i++)
		{
			boolean dupColumnSts = true;
			int sincr = 1;
			colname = xlcolumn[i].toUpperCase();

			while (dupColumnSts) {
			 

				if (i == 0) {
					list.add(colname);
					break;
				}

				final boolean dupColContain = list.contains(colname);
			 
				if (!dupColContain) // if val not exist in array list
				{
					list.add(colname);
					dupColumnSts = false;
				} else {
					colname = (xlcolumn[i] + "_" + sincr).toUpperCase();
					sincr++;
					dupColumnSts = true;
				}// else
			}// while

		}// for

		final String[] newXlcolumn = (String[]) list.toArray(new String[0]);
		for(int a=0;a<newXlcolumn.length;a++){
		 
		}
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
	 
		return newXlcolumn;
	}
	
	/* For Manual Trigger - Mathi dated@03-06-2010 */
	public UploadVB validateManualMove(final String mastertableName, final int batchid,
			final int typeid,final int mfr_id)  throws MotorBaseException
	{
		LogManager.push("validateManualMove() method--------------------------Enter ");
		LogManager.logEnter();
		UploadVB uploadVBMaster=new UploadVB();
		boolean status=true;
		//MOVING MASTER TEMP TABLE TO MASTER TABLE
		final UploadValidateDAO uploadValidateDAO = (UploadValidateDAOImpl) MotorDaoFactory.getDAO(UploadValidateDAO.class.getName());
		status=uploadValidateDAO.validateManualMove(mastertableName, batchid, typeid, mfr_id);
		uploadVBMaster.setBatchID(batchid);
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		LogManager.push("validateManualMove() method--------------------------Exit ");
		return uploadVBMaster;
	}
	
	/*Get Master Raw Tables */
	public String[][] pendingBatchIds(final String nrRawMasTable, final String enRawMasTable)  throws MotorBaseException
	{
		String[][] result = new String[0][0];
		LogManager.push("pendingBatchIds() method-----------------------------Enter ");
		LogManager.logEnter();
		final UploadDAO uploadDAO = (UploadDAOImpl) MotorDaoFactory.getDAO(UploadDAO.class.getName());
		result = uploadDAO.pendingBatchIds(nrRawMasTable, enRawMasTable);
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		LogManager.push("pendingBatchIds() method-----------------------------Exit ");
		return result;
	}
	
	/*Get Master Pending Count */
	public String[][] pendingBatchCount(final String nrRawMasTable, final String enRawMasTable)  throws MotorBaseException
	{
		String[][] result = new String[0][0];
		LogManager.push("pendingBatchCount() method--------------------------------Enter ");
		LogManager.logEnter();
		final UploadDAO uploadDAO = (UploadDAOImpl) MotorDaoFactory.getDAO(UploadDAO.class.getName());
		result = uploadDAO.pendingCount(nrRawMasTable, enRawMasTable);
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		LogManager.push("pendingBatchCount() method--------------------------------Exit ");
		return result;
	}
	
	public void updateBankRecords(UploadVB uploadVBMaster) {
	try {
			String[][] result = new String[0][0];
			LogManager.push("updateBankRecords() method-----------------------Enter ");
			LogManager.logEnter();
			
			final UploadDAO uploadDAO = (UploadDAOImpl) MotorDaoFactory.getDAO(UploadDAO.class.getName());
			uploadDAO.updateBankRecords(uploadVBMaster);
			LogManager.logExit();
			LogManager.popRemove(); // Should be the last statement
			}
		catch (Exception e) {
			LogManager.push(e.getMessage());
			e.printStackTrace();
		}
		LogManager.push("updateBankRecords() method-----------------------Exit ");
		
	}
	public int getProcess( final String transId, final String bankId) throws CommonBaseException{
		LogManager.push("UploadCB getprocessDetails() Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		LogManager.push("dao----:::"+UploadDAO.class.getName());
		final int result = uploadDAO.getProcess(transId,bankId);
		LogManager.push("UploadCB getProcessDetails() Method - Exit");
		return result;
	}
	
	public void updateReversalReceipts(String transId)  throws CommonBaseException{
		LogManager.push("UploadCB updateReversalReceipts() receiptnos Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
	     uploadDAO.updateReversalReceipts(transId);
		LogManager.push("UploadCB updateReversalReceipts() receiptnos Method - Exit");
	}

	public Map getBankList(UploadForm uploadForm) throws CommonBaseException{
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		return uploadDAO.getBankList(uploadForm);
	}

	public List getTransactedDetails(UploadForm uploadForm) throws CommonBaseException{
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		return uploadDAO.getTransactedDetails(uploadForm);
	}

	public int getReProcess(String transId, String bankId) throws CommonBaseException {
		LogManager.push("UploadCB getReProcess() Method - Enter");
		final UploadDAO uploadDAO = (UploadDAOImpl) CommonDaoFactory.getDAO(UploadDAO.class.getName());
		LogManager.push("dao----:::"+UploadDAO.class.getName());
		final int result = uploadDAO.getReProcess(transId,bankId);
		LogManager.push("UploadCB getReProcess() Method - Exit");
		return result;
	}
	
}
