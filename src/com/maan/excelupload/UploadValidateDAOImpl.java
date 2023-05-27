package com.maan.excelupload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.Runner;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.MotorBaseException;

public class UploadValidateDAOImpl extends CommonBaseDAOImpl  implements UploadValidateDAO {

	String query = "";
	Object arg[];
	
	public String[][] dataConvert(final String colname[], final int typeid, final int mfr_id)
			throws MotorBaseException {

		LogManager.push("DataConvert() method-Enter");
		Map values;
		List list;
		String dbcolumn[][] = new String[colname.length][4];
		query = getQuery(DBConstants.GET_UPLOAD_VALIDATION_DTLS);
		LogManager.push("DataConvert() method" + query);

		list = this.mytemplate.queryForList(query, new Object[] {typeid,mfr_id});
		LogManager.push("DataConvert() list" + list);
		if (!list.isEmpty()) {
			for (int i = 0; i < colname.length; i++) 
			{
				// colname[i]);
				for (int j = 0; j < list.size(); j++) {
					values = (Map) list.get(j);
					// Y for valid , D for Date field, M for Maximum length ,C
					// for Combination
					final String status = (String) values.get("ACTIVE");
					String Tempcolname=colname[i];
                    String Tempexcel=(String) values.get("EXCEL_HEADER_NAME");
                    Tempcolname=Tempcolname.replaceAll(" ", "");
                    Tempexcel=Tempexcel.replaceAll(" ", "");
		if (((Tempcolname).equalsIgnoreCase(Tempexcel))&& (("Y".equalsIgnoreCase(status)) || ("1".equalsIgnoreCase(status)))) {
						dbcolumn[i][0] = (String) values.get("DB_COLUMN_NAME");
						dbcolumn[i][1] = status;
						dbcolumn[i][2] = (String) values.get("DATA_TYPE") == null ? "": (String) values.get("DATA_TYPE");
						dbcolumn[i][3] = (String) values.get("DATA_FORMAT");
						values.put("ACTIVE", "0");
						list.add(j, values);
						// list.remove(j);
						break;
					}
				}
			}

		}
		LogManager.push("DataConvert() method list " + list.size());
		return dbcolumn;
	}
	
	public List getTranIdList() throws MotorBaseException {
		List list = new ArrayList();
		LogManager.push("getTranIdList() Enter");
		try {
			query = getQuery(DBConstants.GET_RECEIPT_BATCHID);
			list = this.mytemplate.query(query, new RowMapper() {
				public Object mapRow(java.sql.ResultSet rset,int arg)
						throws SQLException {
					UploadForm uVB = new UploadForm();
					uVB.setIsDirect(rset.getString("batchid")==null?"":rset.getString("batchid"));
					return uVB;
				}			});
		}catch(Exception e) {
			e.printStackTrace();
		}
		LogManager.push("getTranIdList() Exit");
		return list;
	}
	
	public List collectFileFormatInfo(final int mfr_id,final int typeid) {
		LogManager.push("collectFileFormatInfo() method");
		LogManager.logEnter();
		List list;
		query = "select FIELD_SEPARATER,UPLOAD_OPTION,MFR_CODE,FILE_FORMAT from "
				+ "TTRN_UD_FILE_FORMAT where mfr_id="
				+ mfr_id
				+ " and file_type=" + typeid + " and active='1'";
		LogManager.push("collectFileFormatInfo() query" + query);
		list = this.mytemplate.queryForList(query);
		LogManager.push("collectFileFormatInfo() exit method" + list.size());
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return list;
	}

	public synchronized UploadVB createTransaction(int tranId, final int typeid, final String filename,
		final String path,final int mfr_id,final String loginid) throws MotorBaseException {
		LogManager.push("CreateTransaction() method-Enter");
		LogManager.logEnter();
		UploadVB uploadVB = new UploadVB();

		if(tranId<=0)
			tranId = createBatchID();
			
		uploadVB = collectTypeInfo(typeid, mfr_id);
		query = "insert into TTRN_UPLOAD(BATCHID,TYPEID,FILE_ID,TRANSACTION_DATE,USER_NAME,FILE_NAME,PATH," +
		"MFR_ID,ACTIVE) VALUES(" + tranId + "," + typeid + ",1,sysdate,'" + loginid + "','" + filename + 
		"','" + path + "'," + mfr_id + ",'1')";
		this.mytemplate.execute(query);
		uploadVB.setBatchID(tranId);
		uploadVB.setManufactureID(mfr_id);
		uploadVB.setTypeID(typeid);

		int headerlineno = this.mytemplate.queryForInt("select nvl(headerlineno,1) from TMAS_POLICYTYPE where " +
		"POLICYTYPEID=" + typeid + " and MFR_ID=" + mfr_id);
		uploadVB.setHeaderlineno(headerlineno);

		LogManager.push("CreateTransaction() method-Exit");
		return uploadVB;
	}

	public int createBatchID() {
		final int batchid = this.mytemplate
				.queryForInt("select nvl(max(batchid),0)+1 from TTRN_UPLOAD");
		return batchid;
	}

	public UploadVB collectTypeInfo(final int typeid,final int mfr_id)
			throws MotorBaseException {
		LogManager.push("CollectTypeInfo() method - Enter");
		LogManager.logEnter();
		List list;
		Map values;
		final UploadVB uploadVB = new UploadVB();
		query = "select POLICYTYPE,MASTER_TEMP_TABLE,MASTER_TABLE,POLICYTYPEID from "+ " TMAS_POLICYTYPE where POLICYTYPEID="+ typeid+ " and mfr_id=" + mfr_id;		list = this.mytemplate.queryForList(query);
		if (!list.isEmpty()) {
			values = (Map) list.get(0);
			uploadVB.setMasterTempTbl((String) values.get("MASTER_TEMP_TABLE"));
			uploadVB.setMastterTbl((String) values.get("MASTER_TABLE"));
			uploadVB.setPolicyType((String) values.get("POLICYTYPE"));
			uploadVB.setTypeID(typeid);
			uploadVB.setManufactureID(mfr_id);
		}
		LogManager.push("CollectTypeInfo() method-Exit");
		return uploadVB;
	}

	public boolean createTempTable(final String tableName) throws MotorBaseException {
		query = "create table " + tableName;
		this.mytemplate.execute(query);
		return true;
	}
	
	public boolean insertRecords(final List Queries,final String MastertableName,final String TempQuery,final String TempTable, final int batchid,String writeFilePath) throws MotorBaseException {
		LogManager.push("InsertRecords() method-Enter");
		final StringBuffer error = new StringBuffer(16);
		int value = 0;
		boolean status = true;
     
		if (!Queries.isEmpty()) {
			for (value = 0; value < Queries.size(); value++) {
				try {
					this.mytemplate.execute((String) Queries.get(value));
				} catch (Exception e) {
					error.append("problem in row no =" + value + "<br>");
					LogManager.push("error" + error);
					status = false;
				}
			}
		}

		LogManager.push("MastertableName--" + MastertableName);
		if (MastertableName == null) {
			status = false;
			error.append("<br>Master table and Temp Table not found,Please verify the poliyctypemaster. MastertableName-- IS<br>"+MastertableName);
		}
		if (!status) {
/*			String dataQuery="";
			if(MastertableName.equalsIgnoreCase("temp_citi_bank"))
			dataQuery=TempQuery.replaceAll("CHEQUE_AMT", "ABS(CHEQUE_AMT)");
			else if(MastertableName.equalsIgnoreCase("temp_scb_bank"))
				dataQuery=TempQuery.replaceAll("CHQ_AMOUNT", "ABS(CHQ_AMOUNT)");
			else
				dataQuery=TempQuery.replaceAll("INSTRUMENT_AMOUNT", "ABS(INSTRUMENT_AMOUNT)");
			LogManager.push(">>>>>>>>"+dataQuery);
			
			query = "insert into " + MastertableName + "(" + TempQuery+ ")  (select " + dataQuery + " from " + TempTable + " where batchid = " + batchid + ")";
			LogManager.push("master query is" + query);
			this.mytemplate.execute(query);
		} else {*/
			writeFilePath = writeFilePath + "\\Error" + batchid + ".txt";
			writeFiles(writeFilePath, error.toString());
			query = getQuery(DBConstants.UPDATE_TRANSACTION_STATUS);
			arg = new Object[3];
			arg[0] = Queries.size();
			arg[1] = writeFilePath;
			arg[2] = batchid;
			LogManager.push("master query is" + query);
			this.mytemplate.update(query, arg);
		}

//		this.mytemplate.execute("drop table " + TempTable);
		LogManager.push("InsertRecords() method- Exit");
		return status;
	}

	public boolean writeUnknownColumn(final long totalRows, final String unknowncolumn,
			final int batchid, String writeFilePath) throws MotorBaseException {
		LogManager.push("writeUnknownColumn() method");
		LogManager.logEnter();
		writeFilePath = writeFilePath + "\\Unknown" + batchid + ".txt";
		writeFiles(writeFilePath, unknowncolumn);
		query = "update TTRN_UPLOAD set TOTAL_NO_OF_RECORDS="
				+ totalRows + ",UNKNOWN_COLUMNS='" + writeFilePath
				+ "',UPLOAD_STATUS='U' WHERE BATCHID=" + batchid;
		LogManager.push("master query is" + query);
		this.mytemplate.execute(query);

		LogManager.push("writeUnknownColumn() method- Exit");
		LogManager.logExit();
		LogManager.popRemove(); // Should be the last statement
		return true;
	}

	public boolean validateRecords(final String MastertableName, final int batchid,	final int typeid,final int mfr_id,final String mainTable)  throws MotorBaseException 
	{
       LogManager.push("validateRecords() method-Enter");
       List list;
       List masterlist;
       Map values;
       Map mastervalues;
  
       query = "select DB_COLUMN_NAME from TMAS_VALIDATION where mfr_id="+ mfr_id + " and type_id=" + typeid+ "   and active='1' and validation_status='Y'";
       LogManager.push("validateRecords() query -- query-----" + query);
       masterlist = this.mytemplate.queryForList(query);
         
       if (!masterlist.isEmpty()) {
			mastervalues = (Map) masterlist.get(0);
			final String primaryKey = (String) mastervalues.get("DB_COLUMN_NAME");
			LogManager.push("validateRecords() query -- primaryKey-----"+ primaryKey);
			query = "select VALID_ID,TYPE_ID,DB_COLUMN_NAME,MANDATORY_STATUS,FIELD_LENGTH,DATA_TYPE,DATA_FORMAT,REFERENCE_STATUS,REFERENCE_TABLE,REFERENCE_COLUMN,"
					+ "REFERENCE_CONDITION,CHECK_VALUE,CHECK_VALUE_COND,ACTIVE from TMAS_VALIDATION where type_id="
					+ typeid+ " and mfr_id="+ mfr_id+ " and active='1' AND VALIDATION_STATUS='Y'";
			LogManager.push("validateRecords() query --" + query);
			list = this.mytemplate.queryForList(query);
			
			if (!list.isEmpty() && primaryKey != null) {
			this.mytemplate.execute("update " + MastertableName+ " set validate_status='N' where active='1'");
			for (int j = 0; j < list.size(); j++) {
			values = (Map) list.get(j);
			final int VALID_ID = Integer.parseInt((java.math.BigDecimal) values	.get("VALID_ID") == null ? "0"	: ((java.math.BigDecimal) values.get("VALID_ID")).toString());
			final String DB_COLUMN_NAME = (String) values.get("DB_COLUMN_NAME");
			final String MANDATORY_STATUS = (String) values.get("MANDATORY_STATUS") == null ? "N": (String) values.get("MANDATORY_STATUS");
			final int FIELD_LENGTH = Integer.parseInt((java.math.BigDecimal) values	.get("FIELD_LENGTH") == null ? "0": ((java.math.BigDecimal) values.get("FIELD_LENGTH")).toString());
			final String DATA_TYPE = (String) values.get("DATA_TYPE");
			final String DATA_FORMAT = (String) values.get("DATA_FORMAT");
			String REFERENCE_TABLE = (String) values.get("REFERENCE_TABLE");
			final String REFERENCE_COLUMN = (String) values.get("REFERENCE_COLUMN");
			final String REFERENCE_CONDITION = (String) values.get("REFERENCE_CONDITION");
			final String CHECK_VALUE = (String) values.get("CHECK_VALUE");
			LogManager.push("validateRecords() CHECK_VALUE --" + CHECK_VALUE);
			final String CHECK_VALUE_COND = (String) values	.get("CHECK_VALUE_COND");
			if(VALID_ID == 1009) {
				query = "UPDATE TEMP_RECEIPT_MASTER RM SET RM.VALIDATE_STATUS = RM.VALIDATE_STATUS || ',1009*R' WHERE NOT EXISTS (SELECT NULL FROM BANK_ACCOUNT_CODE t WHERE t.BANK_ACCOUNT_CODE = RM.BANK_CODE and t.mfr_id=0) AND active = '1'";
				LogManager.push("RECEIPT BANK ACCCODE query --" + query);
				this.mytemplate.execute(query);
			} else{
				if (MANDATORY_STATUS.equalsIgnoreCase("Y"))
				{
					LogManager.push("MANDATORY_STATUS.equalsIgnoreCase('Y') condition"+VALID_ID);	
					query = "update " + MastertableName+ " set validate_status='" + VALID_ID+ "*M' where " + DB_COLUMN_NAME+ " is null and active='1'";
					LogManager.push("validateRecords()  query --" + query);
					this.mytemplate.execute(query);
				}
			}
					
		if (MANDATORY_STATUS.equalsIgnoreCase("P")) {
			LogManager.push("Entering Into MANDATORY_STATUS.equalsIgnoreCase('P')&& (typeid != 104 conditon ");	
			query = "update "+ MastertableName+ " set active='0',validate_status=validate_status||',"+ VALID_ID+ (REFERENCE_CONDITION.equalsIgnoreCase(" NOT IN")?"*O' ":"*X' ")
			+ "where "+ DB_COLUMN_NAME+ " "+ REFERENCE_CONDITION+ "  (select distinct "+ MastertableName+ "."+ DB_COLUMN_NAME+ " from "+ MastertableName
			+ (REFERENCE_TABLE.equalsIgnoreCase(MastertableName) ? "": (" ," + REFERENCE_TABLE))+ " where "+ REFERENCE_TABLE
			+ "."+ REFERENCE_COLUMN+ "="+ MastertableName+ "."+ DB_COLUMN_NAME+ (REFERENCE_TABLE.equalsIgnoreCase(MastertableName) ? " and "
			+ MastertableName+ ".batchid not in("+ batchid + "))": (" and " + MastertableName + ".active='1' )"))+ " and active='1' and batchid=" + batchid;
			LogManager.push("validateRecords() query --" + query);
			this.mytemplate.execute(query);
			REFERENCE_TABLE = MastertableName;
			
			query = "update "+ MastertableName+ " set active='0',validate_status=validate_status||',"+ VALID_ID+ "*X' "+ "where "+ DB_COLUMN_NAME+ " in  (select distinct "
			+ MastertableName+ "."+ DB_COLUMN_NAME+ " from "+ MastertableName+ (REFERENCE_TABLE.equalsIgnoreCase(MastertableName) ? "": (" ," + REFERENCE_TABLE)) + " where "
			+ REFERENCE_TABLE + "." + REFERENCE_COLUMN+ "=" + MastertableName + "." + DB_COLUMN_NAME+ " and " + MastertableName+ ".batchid not in(" + batchid + ") and "
			+ MastertableName + ".active='1' )"+ " and active='1' and batchid=" + batchid;
			LogManager.push("validateRecords() query --" + query);
			this.mytemplate.execute(query);
			
			REFERENCE_TABLE = (String) values.get("REFERENCE_TABLE");
			query = "update "+ MastertableName+ " set active='0',validate_status=validate_status||',"+ VALID_ID+ "*A' "+ "where "
			+ primaryKey+ " in  (SELECT (CASE WHEN COUNT("+primaryKey+")>1 THEN "+primaryKey+" ELSE NULL END) " +
		    "FROM "+MastertableName+"  where batchid="+batchid+" AND ACTIVE='1' GROUP BY "+primaryKey+" )"
			+ " and active='1' and batchid=" + batchid;
			LogManager.push("validateRecords() query --" + query);
			this.mytemplate.execute(query);
		}
		 
		if (FIELD_LENGTH > 0) {
			query = "update " + MastertableName+ " set validate_status=validate_status||',"+ VALID_ID + "*L'  where length("
			+ DB_COLUMN_NAME + ")>" + FIELD_LENGTH+ " and active='1'";
			LogManager.push("validateRecords() query --" + query);
			this.mytemplate.execute(query);
		}
	 
		if (DATA_TYPE != null) {
			if (DATA_TYPE.equalsIgnoreCase("number")) {
				query = "UPDATE " + MastertableName + " SET validate_status = validate_status || '," + VALID_ID 
				+"*NU' " + " WHERE  REGEXP_replace(" + DB_COLUMN_NAME + ",'[0-9]?(\\.[0-9]{0,})?','') is not null " + 
				" AND active = '1'";
				LogManager.push("number query --"+ query);
				this.mytemplate.execute(query);
			} // setting year validation for timestatmp and date
			else if (DATA_TYPE.equalsIgnoreCase("date")	|| DATA_TYPE.equalsIgnoreCase("timestamp")) {
		    String lenValue = "";
			if (DATA_FORMAT.equalsIgnoreCase("MM/DD/YYYY")|| DATA_FORMAT.equalsIgnoreCase("MM-DD-YYYY")|| 
				DATA_FORMAT.equalsIgnoreCase("DD/MM/YYYY")|| DATA_FORMAT.equalsIgnoreCase("DD-MM-YYYY")) {
						lenValue = DATA_TYPE
										.equalsIgnoreCase("timestamp") ? ">=4"
										: "=4";
								query = "update "
										+ MastertableName
										+ " set validate_status=validate_status||',"
										+ VALID_ID
										+ "*D' where "										
										+ DB_COLUMN_NAME
										+ " in (select case when (length(substr("
										+ DB_COLUMN_NAME
										+ ",1,"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1)-1))=2 or length(substr("
										+ DB_COLUMN_NAME
										+ ",1,"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,1)-1))=2 or length(substr("
										+ DB_COLUMN_NAME
										+ ",1,"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1)-1))=1 or length(substr("
										+ DB_COLUMN_NAME
										+ ",1,"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,1)-1))=1)  and (length(substr("
										+ DB_COLUMN_NAME
										+ ","
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1)+1,(instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,2)-"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1))-1))=2 or length(substr("
										+ DB_COLUMN_NAME
										+ ","
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,1)+1,(instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,2)-instr"
										+ "("
										+ DB_COLUMN_NAME
										+ ",'-',1,1))-1))=2 or length(substr("
										+ DB_COLUMN_NAME
										+ ","
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1)+1,(instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,2)-"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1))-1))=1 or length(substr("
										+ DB_COLUMN_NAME
										+ ","
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,1)+1,(instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,2)-instr"
										+ "("
										+ DB_COLUMN_NAME
										+ ",'-',1,1))-1))=1) and (length(substr("
										+ DB_COLUMN_NAME + "," + "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,2)+1,length("
										+ DB_COLUMN_NAME + ")))" + lenValue
										+ " or length" + "(substr("
										+ DB_COLUMN_NAME + ",instr("
										+ DB_COLUMN_NAME + ",'-',1,2)+1,"
										+ "length(" + DB_COLUMN_NAME + ")))"
										+ lenValue + ") then 'no' else "
										+ DB_COLUMN_NAME + " end from "
										+ MastertableName
										+ " where active='1' and "
										+ DB_COLUMN_NAME
										+ " is not null) and active='1' ";
							} else if (DATA_FORMAT
									.equalsIgnoreCase("YYYY/MM/DD")
									|| DATA_FORMAT
											.equalsIgnoreCase("YYYY-MM-DD")) {

								lenValue = DATA_TYPE
										.equalsIgnoreCase("timestamp") ? ">=2"
										: "=2";
								query = "update "
										+ MastertableName
										+ " set validate_status=validate_status||',"
										+ VALID_ID
										+ "*D1' where "
										
										+ primaryKey
										+ " in (select case when (length(substr("
										+ DB_COLUMN_NAME
										+ ",1,"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1)-1))=4 or length(substr("
										+ DB_COLUMN_NAME
										+ ",1,"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,1)-1))=4)  and (length(substr("
										+ DB_COLUMN_NAME
										+ ","
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1)+1,(instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,2)-"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1))-1))=2 or length(substr("
										+ DB_COLUMN_NAME
										+ ","
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,1)+1,(instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,2)-instr"
										+ "("
										+ DB_COLUMN_NAME
										+ ",'-',1,1))-1))=2) and (length(substr("
										+ DB_COLUMN_NAME + "," + "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,2)+1,length("
										+ DB_COLUMN_NAME + ")))" + lenValue
										+ " or length" + "(substr("
										+ DB_COLUMN_NAME + ",instr("
										+ DB_COLUMN_NAME + ",'-',1,2)+1,"
										+ "length(" + DB_COLUMN_NAME + ")))"
										+ lenValue + ") then 'no' else "
										+ primaryKey + " end from "
										+ MastertableName
										+ " where active='1' and "
										+ DB_COLUMN_NAME
										+ " is not null) and active='1' ";
							} else if (DATA_FORMAT
									.equalsIgnoreCase("DD/MON/YYYY")
									|| DATA_FORMAT
											.equalsIgnoreCase("DD-MON-YYYY")) {

								lenValue = DATA_TYPE
										.equalsIgnoreCase("timestamp") ? ">=4"
										: "=4";
								query = "update "
										+ MastertableName
										+ " set validate_status=validate_status||',"
										+ VALID_ID
										+ "*D2' where "
										
										+ primaryKey
										+ " in (select case when (length(substr("
										+ DB_COLUMN_NAME
										+ ",1,"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1)-1))=2 or length(substr("
										+ DB_COLUMN_NAME
										+ ",1,"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,1)-1))=2)  and (length(substr("
										+ DB_COLUMN_NAME
										+ ","
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1)+1,(instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,2)-"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1))-1))=3 or length(substr("
										+ DB_COLUMN_NAME
										+ ","
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,1)+1,(instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,2)-instr"
										+ "("
										+ DB_COLUMN_NAME
										+ ",'-',1,1))-1))=3) and (length(substr("
										+ DB_COLUMN_NAME + "," + "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,2)+1,length("
										+ DB_COLUMN_NAME + ")))" + lenValue
										+ " or length" + "(substr("
										+ DB_COLUMN_NAME + ",instr("
										+ DB_COLUMN_NAME + ",'-',1,2)+1,"
										+ "length(" + DB_COLUMN_NAME + ")))"
										+ lenValue + ") then 'no' else "
										+ primaryKey + " end from "
										+ MastertableName
										+ " where active='1' and "
										+ DB_COLUMN_NAME
										+ " is not null) and active='1' ";
							} else if (DATA_FORMAT
									.equalsIgnoreCase("MON/DD/YYYY")
									|| DATA_FORMAT
											.equalsIgnoreCase("MON-DD-YYYY")) {

								lenValue = DATA_TYPE
										.equalsIgnoreCase("timestamp") ? ">=4"
										: "=4";
								query = "update "
										+ MastertableName
										+ " set validate_status=validate_status||',"
										+ VALID_ID
										+ "*D3' where "
										
										+ primaryKey
										+ " in (select case when (length(substr("
										+ DB_COLUMN_NAME
										+ ",1,"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1)-1))=3 or length(substr("
										+ DB_COLUMN_NAME
										+ ",1,"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,1)-1))=3)  and (length(substr("
										+ DB_COLUMN_NAME
										+ ","
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1)+1,(instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,2)-"
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,1))-1))=2 or length(substr("
										+ DB_COLUMN_NAME
										+ ","
										+ "instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,1)+1,(instr("
										+ DB_COLUMN_NAME
										+ ",'-',1,2)-instr"
										+ "("
										+ DB_COLUMN_NAME
										+ ",'-',1,1))-1))=2) and (length(substr("
										+ DB_COLUMN_NAME + "," + "instr("
										+ DB_COLUMN_NAME
										+ ",'/',1,2)+1,length("
										+ DB_COLUMN_NAME + ")))" + lenValue
										+ " or length" + "(substr("
										+ DB_COLUMN_NAME + ",instr("
										+ DB_COLUMN_NAME + ",'-',1,2)+1,"
										+ "length(" + DB_COLUMN_NAME + ")))"
										+ lenValue + ") then 'no' else "
										+ primaryKey + " end from "
										+ MastertableName
										+ " where active='1' and "
										+ DB_COLUMN_NAME
										+ " is not null) and active='1' ";
							}
							LogManager.push("validateRecords() query --"
									+ query);
							this.mytemplate.execute(query);

						}
					}
				
					if (REFERENCE_TABLE != null
							&& MANDATORY_STATUS.equalsIgnoreCase("Y")) {
						query = "update " + MastertableName
								+ " set validate_status=validate_status||',"
								+ VALID_ID + "*R' " + "where " + DB_COLUMN_NAME
								+ " " + REFERENCE_CONDITION
								+ "  (select distinct " + MastertableName + "."
								+ DB_COLUMN_NAME + " from " + MastertableName
								+ " ," + REFERENCE_TABLE + " where "
								+ REFERENCE_TABLE + "." + REFERENCE_COLUMN
								+ "=" + MastertableName + "." + DB_COLUMN_NAME
								+ " and " + MastertableName
								+ ".active='1' and " + REFERENCE_TABLE
								+ ".mfr_id=" + mfr_id + " ) and active='1'";
						LogManager.push("validateRecords() query --" + query);
						this.mytemplate.execute(query);
					}
			
					if (CHECK_VALUE != null) {
						query = "update " + MastertableName
						+ " set validate_status=validate_status||',"
						+ VALID_ID + "*V' " + "where " + DB_COLUMN_NAME+" "
						+ CHECK_VALUE_COND + " (" + CHECK_VALUE
						+ ")  and active='1'";
						LogManager.push("validateRecords() values check -- i am here");
						LogManager.push("validateRecords() query --" + query);
						this.mytemplate.execute(query);
					}
					}
			}
			
			//have to check this if the configured field in tmas is null then delete from temptable
	         Object obj[] = new Object[1];
	         obj[0] = Integer.toString(batchid);
	         
	         String queryCols = "select DB_COLUMN_NAME from TMAS_VALIDATION where type_id=?   and active='1'";
	         String queryColvals[][] = Runner.multipleSelection(queryCols,new String[]{Integer.toString(typeid)});
	         
             String temptable = Runner.singleSelection("select master_temp_table from tmas_policytype where policytypeid='"+typeid+"'");	         
			 
             StringBuffer querynullCheck = new StringBuffer( "delete from "+temptable+" where  ");
             for(int k=0;k<queryColvals.length;k++)
             {
            	 if(k==0)
            	 querynullCheck.append(" "+queryColvals[k][0]+" is null ");
            	 else
            	 querynullCheck.append(" and "+queryColvals[k][0]+" is null ");	
             }
             
             querynullCheck.append(" and batchid = " + batchid);
             //To clear null value rows
             LogManager.push("Query null"+querynullCheck);
             Runner.updation(querynullCheck.toString());
             
             if(typeid==101){
 				LogManager.push("Receipt Upload dup");
 				query = "UPDATE TEMP_RECEIPT_MASTER SET PAYMENT_TYPE =UPPER(PAYMENT_TYPE) , TRANS_SOURCE=UPPER(TRANS_SOURCE)";
 				this.mytemplate.update(query);
 				
 				query = "UPDATE TEMP_RECEIPT_MASTER SET VALIDATE_STATUS ='N,1023*PAY' WHERE PAYMENT_TYPE NOT IN ('CASH','CHQ','BD') ";
 				this.mytemplate.update(query);
 				LogManager.push("Check Receipt payment type invalid:"+query);
 				
 				query = "UPDATE TEMP_RECEIPT_MASTER SET VALIDATE_STATUS ='N,1015*TRA' WHERE TRANS_SOURCE NOT IN ('RECT','PYMT') ";
 				this.mytemplate.update(query);
 				LogManager.push("Check Receipt payment type invalid:"+query);
 		         query = getQuery(DBConstants.DUPLICATE_UPDATE);
 				LogManager.push("Query for Duplicate:"+query);
 				this.mytemplate.update(query);
 				
 				query = getQuery(DBConstants.DUPLICATE_UPDATE1);
 				LogManager.push("Query for Exists:"+query);
 				this.mytemplate.update(query);
 			}
 			else if(typeid==102)
 			{
 				query = "UPDATE TEMP_CITI_BANK SET VALIDATE_STATUS ='N,1037*DEP' WHERE (CHEQUE_NO IS  NULL OR CHEQUE_NO LIKE ' ') AND DEPOSIT_DATE IS NULL ";
 				this.mytemplate.update(query);
 				LogManager.push("Check cheque empty not having dept date:"+query);
 				
 				query = "UPDATE TEMP_CITI_BANK SET TYPE_CRDR=UPPER(TYPE_CRDR)";
 				this.mytemplate.update(query);
 				
 				query = "UPDATE TEMP_CITI_BANK SET VALIDATE_STATUS ='N,1043*TYP' WHERE UPPER(TYPE_CRDR) NOT IN ('D','C') ";
 				this.mytemplate.update(query);
 				LogManager.push("Check cheque  not having valid type:"+query);
 				LogManager.push("Citi Bank Upload dup");
 				
 				query = getQuery(DBConstants.DUPLICATE_CITI_BANK_UPDATE);
 			    this.mytemplate.update(query);
 				LogManager.push("Check duplicate with in temp:"+query);
 				
 				//EMPTY CHEQUE_NO RECORDS DUPLICATE & WITH MASTER
 				query = getQuery(DBConstants.DUPLICATE_CITI_BANK_UPDATE3);
 				this.mytemplate.update(query);
 				
 				query = getQuery(DBConstants.DUPLICATE_CITI_BANK_UPDATE4);
 				this.mytemplate.update(query);
 				
 				//COMPARING DUPLICATE WITH MASTER
 				query = getQuery(DBConstants.DUPLICATE_CITI_BANK_UPDATE1);
 				this.mytemplate.update(query);
 				LogManager.push("Check duplicate with master:"+query);
 			
 			}
 			else if(typeid==103)
 			{
 			    query = "UPDATE TEMP_HDFC_BANK SET VALIDATE_STATUS ='N,1058*DEP' WHERE (INSTRUMENT_NO IS  NULL OR INSTRUMENT_NO LIKE ' ') AND POST_DT IS NULL ";
 				this.mytemplate.update(query);
 				LogManager.push("Check cheque empty not having dept date:"+query);
 				
 				query = "UPDATE TEMP_HDFC_BANK SET DR_CR=UPPER(DR_CR) ";
 				this.mytemplate.update(query);
 				
 				query = "UPDATE TEMP_HDFC_BANK SET VALIDATE_STATUS ='N,1063*TYP' WHERE DR_CR NOT IN ('C','D') ";
 				this.mytemplate.update(query);
 				LogManager.push("Check cheque empty not having dept date:"+query);
 				
 				///query = getQuery(DBConstants.DUPLICATE_HDFC_BANK_UPDATE2);
 				//this.mytemplate.update(query);
 				
 				//DUPLICATE RECORDS
 				query = getQuery(DBConstants.DUPLICATE_HDFC_BANK_UPDATE);
 				this.mytemplate.update(query);
 				LogManager.push("Check duplicate with in temp:"+query);
 				
 				//EMPTY CHEQUE_NO RECORDS DUPLICATE & WITH MASTER
 				//query = getQuery(DBConstants.DUPLICATE_HDFC_BANK_UPDATE3);
 				//this.mytemplate.update(query);
 				
 				//query = getQuery(DBConstants.DUPLICATE_HDFC_BANK_UPDATE4);
 				//this.mytemplate.update(query);
 			  
 				//COMPARING DUPLICATE WITH MASTER
 				query = getQuery(DBConstants.DUPLICATE_HDFC_BANK_UPDATE1);
 				this.mytemplate.update(query);
 				LogManager.push("Check duplicate with master:"+query);
 			}
 			else if(typeid==105)
 			{
 				LogManager.push("SCB Bank Upload dup");
 				query = "UPDATE TEMP_SCB_BANK SET VALIDATE_STATUS ='N,1096*DEP' WHERE (CHEQUE_NO IS  NULL OR CHEQUE_NO LIKE ' ') AND DEPOSIT_DATE IS NULL ";
 				this.mytemplate.update(query);
 				LogManager.push("--------------query4"+query);
 				LogManager.push("Check cheque empty not having dept date:"+query);
 				LogManager.push("============>Exit validateRecords SCB Upload Duplication==========>");
 			
 				query = "UPDATE TEMP_SCB_BANK SET CR_DR=UPPER(CR_DR)";
 				this.mytemplate.update(query);
 				
 				query = "UPDATE TEMP_SCB_BANK SET VALIDATE_STATUS ='N,1088*TYP' WHERE CR_DR NOT IN ('D','C') ";
 				this.mytemplate.update(query);
 				LogManager.push("Check cheque  not having valid type:"+query);
 			
 				query = getQuery(DBConstants.DUPLICATE_SCB_BANK_UPDATE);
 			    this.mytemplate.update(query);
 				LogManager.push("Check duplicate with in temp:"+query);
 				
 				//EMPTY CHEQUE_NO RECORDS DUPLICATE & WITH MASTER
 				query = getQuery(DBConstants.DUPLICATE_SCB_BANK_UPDATE3);
 				this.mytemplate.update(query);
 				
 				query = getQuery(DBConstants.DUPLICATE_SCB_BANK_UPDATE4);
 				this.mytemplate.update(query);
 				
 				//COMPARING DUPLICATE WITH MASTER
 				query = getQuery(DBConstants.DUPLICATE_SCB_BANK_UPDATE1);
 				this.mytemplate.update(query);
 				LogManager.push("Check duplicate with master:"+query);
 			}
 			else if(typeid==106)
 			{
 				LogManager.push("Axis Bank Upload dup");
 				query = "UPDATE TEMP_AXIS_BANK SET VALIDATE_STATUS ='N,1111*DEP' WHERE (INST_NO IS  NULL OR INST_NO LIKE ' ') AND DEPOSIT_DATE IS NULL ";
 				this.mytemplate.update(query);
 				LogManager.push("--------------query4"+query);
 				LogManager.push("Check cheque empty not having dept date:"+query);
 				LogManager.push("============>Exit validateRecords AXIS Upload Duplication==========>");
 			
 				query = "UPDATE TEMP_AXIS_BANK SET TYPE=UPPER(TYPE)";
 				this.mytemplate.update(query);
 				
 				query = "UPDATE TEMP_AXIS_BANK SET VALIDATE_STATUS ='N,1108*TYP' WHERE TYPE NOT IN ('COL','RTN') ";
 				this.mytemplate.update(query);
 				LogManager.push("Check cheque  not having valid type:"+query);
 				query = getQuery(DBConstants.DUPLICATE_AXIS_BANK_UPDATE);
 			    this.mytemplate.update(query);
 				LogManager.push("Check duplicate with in temp:"+query);
 				
 				//EMPTY CHEQUE_NO RECORDS DUPLICATE & WITH MASTER
 				query = getQuery(DBConstants.DUPLICATE_AXIS_BANK_UPDATE3);
 				this.mytemplate.update(query);
 				
 				
 				query = getQuery(DBConstants.DUPLICATE_AXIS_BANK_UPDATE4);
 				this.mytemplate.update(query);
 				
 				//COMPARING DUPLICATE WITH MASTER
 				query = getQuery(DBConstants.DUPLICATE_AXIS_BANK_UPDATE1);
 				this.mytemplate.update(query);
 				LogManager.push("Check duplicate with master:"+query);
 			} 
 			else if(typeid==107) {
 				String updateQry = "update " + MastertableName + " tr set validate_status = (select (case when bank_no is not null then 'I' else tr.validate_status end) status " +
 						"from receipt_master where receipt_no = tr.receipt_no) where batchid = " + batchid ;
 				this.mytemplate.execute(updateQry);
 			}
 			else if(typeid==108)
 			{
 				query = "UPDATE TEMP_HSBC_BANK SET DEBIT_CREDIT=UPPER(DEBIT_CREDIT) ";
 				this.mytemplate.update(query);
 				
 				/*query = "UPDATE TEMP_HSBC_BANK SET VALIDATE_STATUS ='N,1063*TYP' WHERE DEBIT_CREDIT NOT IN ('C','D') ";
 				this.mytemplate.update(query);
 				LogManager.push("Check cheque empty not having dept date:"+query);*/
 				
 				//DUPLICATE RECORDS
 				query = getQuery(DBConstants.DUPLICATE_HSBC_BANK_UPDATE);
 				this.mytemplate.update(query);
 				LogManager.push("Check duplicate with in temp:"+query);

 				//DUPLICATE RECORDS
 				query = getQuery(DBConstants.DUPLICATE_HSBC_BANK_UPDATE1);
 				this.mytemplate.update(query);
 				LogManager.push("Check duplicate with in temp:"+query);
 			}
 			else if(typeid==115) {
 				
 				query = "UPDATE TEMP_KOTAK_BANK SET VALIDATE_STATUS ='N,1058*DEP' WHERE (INSTRUMENT_NO IS  NULL OR INSTRUMENT_NO LIKE ' ') AND POST_DT IS NULL ";
 				this.mytemplate.update(query);
 				LogManager.info("Check cheque empty not having dept date:"+query);
 				
 				query = "UPDATE TEMP_KOTAK_BANK SET Debit_Credit=UPPER(Debit_Credit) ";
 				this.mytemplate.update(query);
 				
 				query = "UPDATE TEMP_KOTAK_BANK SET VALIDATE_STATUS ='N,1063*TYP' WHERE Debit_Credit NOT IN ('C','D') ";
 				this.mytemplate.update(query);
 				LogManager.info("Check cheque empty not having dept date:"+query);
 				
 				//DUPLICATE RECORDS
 				query = getQuery(DBConstants.DUPLICATE_KOTAK_BANK_UPDATE);
 				this.mytemplate.update(query);
 				LogManager.info("Check duplicate with in temp:"+query);
 			  
 				//COMPARING DUPLICATE WITH MASTER
 				query = getQuery(DBConstants.DUPLICATE_KOTAL_BANK_UPDATE1);
 				this.mytemplate.update(query);
 				LogManager.info("Check duplicate with master:"+query);
 			}
 	   }

		//this.mytemplate.execute(" update " +MastertableName+ " set VALIDATE_STATUS='D' WHERE  STATUS IN('D','E','P')" );
		//LogManager.push("Update for validate records hari::"+" update " +MastertableName+ " set VALIDATE_STATUS='D' WHERE  STATUS  IN('D','E','P')" );	
		query = " update " +MastertableName+ " set VALIDATE_STATUS='Y' WHERE VALIDATE_STATUS='N' "; 
		LogManager.push("query " + query);
		this.mytemplate.execute(query);
		
		//query to update ttrn_upload for uploaded details
		String updateQry = "update TTRN_UPLOAD set total_no_of_records=(select count(*) from " + MastertableName 
		+ " where  batchid=" + batchid + "),uploaded_count=(select count(*) " + "from " + MastertableName + 
		" where  batchid=" + batchid + "  and validate_status in('Y') and active='1'),"
		+ "pending_count=(select count(*) from " + MastertableName + " where  batchid="
		+ batchid + "  and " + "validate_status not in('Y')),upload_status='X' where batchid=" + batchid;
		LogManager.push("updateQry " + updateQry);
		this.mytemplate.execute(updateQry);
		
		Object obj[] = new Object[1];
        obj[0] = Integer.toString(batchid);
        if(typeid==101)
        {
			String query2 = getQuery(DBConstants.MASTER_DUPLICATES_INSERT);
			LogManager.push("Query for Payment Records:"+query2);
			LogManager.push("batchid " + batchid);
			this.mytemplate.update(query2,new Object[] {batchid});
        }
        else if(typeid==102)
        {
        	String  query2 = getQuery(DBConstants.CITI_DUPLICATES_INSERT);
			this.mytemplate.execute(query2);
        }
        else if(typeid==105)
        {
        	 LogManager.push("GGGGGGGGGGGGDuplicate Table ---------------------------->");
        	 String query2=getQuery(DBConstants.SCB_DUPLICATES_INSERT);
        	 this.mytemplate.execute(query2);
        }
        else if(typeid==106)
        {
        	 LogManager.push("AXIS DUPLICAT VALIDATIONIMPL this ValidateDAO");
        	 LogManager.push("AXIS Duplicate Table ---------------------------->");
        	 String query2=getQuery(DBConstants.AXIS_DUPLICATES_INSERT);
        	 this.mytemplate.execute(query2);
        } 
        else if(typeid==103)
        {
        	String  query2 = getQuery(DBConstants.HDFC_DUPLICATES_INSERT);
			this.mytemplate.execute(query2);
        }
        else if(typeid==108)
        {
        	String  query2 = getQuery(DBConstants.HSBC_DUPLICATES_INSERT);
			this.mytemplate.execute(query2);
        }
        else if(typeid==115) {
        	String  query2 = getQuery(DBConstants.KOTAK_DUPLICATES_INSERT);
			this.mytemplate.execute(query2);
        }
        
        else if(typeid==104)
        {
        	 LogManager.push("type 104");
        	//String  query2 = getQuery(DBConstants.RECEIPTNUMBERS_DUPLICATES_INSERT);
			//this.mytemplate.execute(query2);
        }		
		LogManager.push("validrecords() method exit");
		return true;
	}

	public String writeFiles(final String pathh,final String valuee) {
		final String path = pathh;
		final String value = valuee;
		boolean sts = false;
		LogManager.push("path is" + path);
		LogManager.push("values is" + value);

		
		final File file = new File(path);
		try {

			final boolean exist = file.createNewFile();
			if (!exist) {

				final FileOutputStream fop = new FileOutputStream(file);
				fop.write(value.getBytes());
				fop.flush();
				fop.close();
				LogManager.push("The data has been written");
				LogManager.push("File created successfully.");
				sts = true;

			} else {
				LogManager.push("File already exists.");
				final FileOutputStream fop = new FileOutputStream(file);
				fop.write(value.getBytes());
				fop.flush();
				fop.close();
				LogManager.push("The data has been written");
				LogManager.push("File created successfully.");
				sts = true;

			}

		} catch (FileNotFoundException e) {
			LogManager.push("WriteFiles"+e.getMessage());
		} catch (IOException e) {
			LogManager.push("WriteFiles"+e.getMessage());
		}
		if (sts)
			{ return "success"; }
		else
			{ return "failure"; }
	}

	public boolean validateManualMove(final String MastertableName, final int batchid,
			final int typeid,final int mfr_id) throws MotorBaseException {
		LogManager.push("validateRecords() method");
		LogManager.logEnter();
		List list;
		List masterlist;
		Map values;
		Map mastervalues;

		query = "select DB_COLUMN_NAME from TMAS_VALIDATION where mfr_id="
				+ mfr_id + " and type_id=" + typeid
				+ "  and MANDATORY_STATUS='P' and active='1'";
		LogManager.push("validateRecords() query -- query-----" + query);
		masterlist = this.mytemplate.queryForList(query);

		if (!masterlist.isEmpty()) {
			mastervalues = (Map) masterlist.get(0);
			final String primaryKey = (String) mastervalues.get("DB_COLUMN_NAME");
			LogManager.push("validateRecords() query -- primaryKey-----"
					+ primaryKey);

			query = "select VALID_ID,TYPE_ID,DB_COLUMN_NAME,MANDATORY_STATUS,FIELD_LENGTH,DATA_TYPE,DATA_FORMAT,REFERENCE_STATUS,REFERENCE_TABLE,REFERENCE_COLUMN,"
					+ "REFERENCE_CONDITION,CHECK_VALUE,CHECK_VALUE_COND,ACTIVE from TMAS_VALIDATION where type_id="
					+ typeid
					+ " and mfr_id="
					+ mfr_id
					+ " and active='1' AND VALIDATION_STATUS='Y'";
			LogManager.push("validateRecords() query --" + query);
			list = this.mytemplate.queryForList(query);
			if (!list.isEmpty() && primaryKey != null) {

				this.mytemplate.execute("update " + MastertableName
						+ " set validate_status='N' where active='1'");

				for (int j = 0; j < list.size(); j++) {
					values = (Map) list.get(j);

					final int VALID_ID = Integer
							.parseInt((java.math.BigDecimal) values
									.get("VALID_ID") == null ? "0"
									: ((java.math.BigDecimal) values
											.get("VALID_ID")).toString());
					final String DB_COLUMN_NAME = (String) values
							.get("DB_COLUMN_NAME");
					final String MANDATORY_STATUS = (String) values
							.get("MANDATORY_STATUS") == null ? "N"
							: (String) values.get("MANDATORY_STATUS");
					final int FIELD_LENGTH = Integer
							.parseInt((java.math.BigDecimal) values
									.get("FIELD_LENGTH") == null ? "0"
									: ((java.math.BigDecimal) values
											.get("FIELD_LENGTH")).toString());
					final String DATA_TYPE = (String) values.get("DATA_TYPE");
					final String DATA_FORMAT = (String) values.get("DATA_FORMAT");
					// String REFERENCE_STATUS = (String)
					// values.get("REFERENCE_STATUS");
					String REFERENCE_TABLE = (String) values
							.get("REFERENCE_TABLE");
					final String REFERENCE_COLUMN = (String) values
							.get("REFERENCE_COLUMN");
					final String REFERENCE_CONDITION = (String) values
							.get("REFERENCE_CONDITION");
					final String CHECK_VALUE = (String) values.get("CHECK_VALUE");
					final String CHECK_VALUE_COND = (String) values
							.get("CHECK_VALUE_COND");
					
//					 reference table validation R
					if (REFERENCE_TABLE != null
							&& MANDATORY_STATUS.equalsIgnoreCase("Y")) {
						query = "update " + MastertableName
								+ " set validate_status=validate_status||',"
								+ VALID_ID + "*R' " + "where " + DB_COLUMN_NAME
								+ " " + REFERENCE_CONDITION
								+ "  (select distinct " + MastertableName + "."
								+ DB_COLUMN_NAME + " from " + MastertableName
								+ " ," + REFERENCE_TABLE + " where "
								+ REFERENCE_TABLE + "." + REFERENCE_COLUMN
								+ "=" + MastertableName + "." + DB_COLUMN_NAME
								+ " and " + MastertableName
								+ ".active='1' and " + REFERENCE_TABLE
								+ ".mfr_id=" + mfr_id + " ) and active='1'";
						LogManager.push("validateRecords() query --" + query);
						this.mytemplate.execute(query);
					}
	}//end of loop
				
			}
		}
		return true;
	}
	
// added by sundaram
	
	public void validateLengthColumn(final String tempTable,int typeid,int mfr_id,final String mainTable )
	{
			
	Map values;
	String query1 = "select VALID_ID,TYPE_ID,DB_COLUMN_NAME,DATA_TYPE,"
		+ "ACTIVE from TMAS_VALIDATION where type_id="
		+ typeid
		+ " and mfr_id="
		+ mfr_id
		+ " and active='1' AND VALIDATION_STATUS='N' AND DATA_FORMAT IS NULL";
		LogManager.push("validateLengthColumn() query --" + query1);
		List list = this.mytemplate.queryForList(query1);
		if (!list.isEmpty()) {

			for (int j = 0; j < list.size(); j++) {
				values = (Map) list.get(j);

				final int VALID_ID = Integer
						.parseInt((java.math.BigDecimal) values
								.get("VALID_ID") == null ? "0"
								: ((java.math.BigDecimal) values
										.get("VALID_ID")).toString());
				
				final String DB_COLUMN_NAME = (String) values
						.get("DB_COLUMN_NAME");
				
				String lengthQuery="SELECT DATA_TYPE,DATA_LENGTH,DATA_PRECISION"
							+" FROM SYS.ALL_TAB_COLUMNS where table_name='"+mainTable+"' and column_name='"+DB_COLUMN_NAME.trim()+"'";
				
				List lengthList = this.mytemplate.queryForList(lengthQuery);
				if(!lengthList.isEmpty())
					{
					
				
					Map lengthValues = (Map) lengthList.get(0);
					final String dataType=(String) lengthValues.get("DATA_TYPE");
							
					 int dataLength=Integer.parseInt((java.math.BigDecimal) lengthValues
							.get("DATA_LENGTH") == null ? "0"
							: ((java.math.BigDecimal) lengthValues
									.get("DATA_LENGTH")).toString());
					
					if (dataType.equalsIgnoreCase("number")) {
						dataLength=Integer.parseInt((java.math.BigDecimal) lengthValues
								.get("DATA_PRECISION") == null ? "0"
								: ((java.math.BigDecimal) lengthValues
										.get("DATA_PRECISION")).toString());
					}
					
					
					if (dataLength > 0) {
						query1 = "update "
								+ tempTable+" set validate_status=validate_status||',"
								+ VALID_ID + "*L'  where length(trim("
								+ DB_COLUMN_NAME + "))>" + dataLength+" and active='1'";
						this.mytemplate.execute(query1);
					}
				}
			}
		}
		LogManager.push("validateLengthColumn() Method Ends");
	}
}
