package com.maan.upload;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;

public class UploadDAOImpl extends CommonBaseDAOImpl implements UploadDAO {
 
	public int i=1;
	StringBuffer sb = new StringBuffer("");
	
	public List getTransactionDetails(final String transId, final String insCompanyId) throws CommonBaseException{
		LogManager.info("UploadDAOImpl getTransactionDetails() Method - Enter");
		
		final String query2 = getQuery(DBConstants.TRANSACTION_DETAILS);
		final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_ERRORCOUNT)	;
		final String query4=getQuery(DBConstants.TRANSACTION_DETAILS_PAYMENTCOUNT)	;
				
		List list = new ArrayList();
		Object[] arg1;
		arg1 = new Object[1];
		arg1[0] = transId;
		int count=this.mytemplate.queryForInt(query2,arg1);
		
		int ecount=this.mytemplate.queryForInt(query3, arg1);
		
		int paymentcount=this.mytemplate.queryForInt(query4, arg1);
		//int ecount=this.mytemplate.queryForInt(query3);
		list.add(count+ecount+paymentcount);
		list.add(ecount);
		list.add(count);
		list.add(paymentcount);
		//Setting status c for cheque no not exists
		String query6 = getQuery(DBConstants.RECEIPT_MASTER_UPDATE);
		this.mytemplate.update(query6);	
		
		//deleting temp records
		String query5 = getQuery(DBConstants.MASTER_DELETE);
		this.mytemplate.update(query5);
		Object[] arg;
		arg = new Object[6];
		arg[0] = transId;
		arg[1] = "R";
		arg[2] = count+ecount;
		arg[3] = ecount;
		arg[4] = transId;
		arg[5] = transId;
		//insertion of RECEIPT transaction
		String query7 = getQuery(DBConstants.TRANSACTION_INSERT3);
		this.mytemplate.update(query7,arg);
		
		return list;
	}
	// 64 VB
	public String processCsv(final String csvLoc,final String insCompanyId,HttpServletRequest request) throws CommonBaseException{
		LogManager.info("UploadDAOImpl processCsv() Method - Enter");
		final List list = new ArrayList();
		boolean duplicateStatus = false;
		
		int count = 0;
		int totColsHeader = 0;
		String result = "";
		String insertQuery = "INSERT INTO TEMP_RECEIPT_MASTER (";
		String data = "";
		String header = "";
		String columns = "";
		String transId = "";
		String[] col = new String[0];
		try{
			final FileInputStream fis = new FileInputStream(csvLoc);
			final BufferedInputStream bis = new BufferedInputStream(fis);
			final DataInputStream dis = new DataInputStream(bis);
			boolean cont = true;
			while(dis.available() != 0){
				count++;
				if(count==2){
					transId = String.valueOf(this.mytemplate.queryForInt(getQuery(DBConstants.TRANSACTION_ID_SEQ)));
				}
				if(count==1){
					header = dis.readLine();
					LogManager.info("Excel Header==>"+header);
					if((header.replaceAll("\t", "").trim()).length()<=0){
						LogManager.info("Inside Header Missing==>"+header);
						result="missing";
						break;
					}
					StringTokenizer cols = new StringTokenizer(header,"\t");
					while(cols.hasMoreTokens()){
						String temp = cols.nextToken().trim();
						if(temp==null || temp.length()==0){
							duplicateStatus=true;
							break;
						}
						if(list.contains(temp)){
							duplicateStatus=true;
							break;
						}else{
							list.add(temp.toLowerCase());
						}
					}
					if(duplicateStatus){
						result = "duplicate";
						break;
					}
					final Map dbDetails = getDbColumns(insCompanyId);
					Object[] colsList = (dbDetails.keySet()).toArray();
					LogManager.push("list.size()"+list.size()+"colsList.length--->"+colsList.length);
					col = new String[list.size()];
					boolean missingStatus = false;
					if(list.size()>colsList.length){
						result = "extra";
						break;
					}
					for(int i=0;i<colsList.length;i++){
						if(list.contains(colsList[i])){
							col[list.indexOf(colsList[i])] = (String)dbDetails.get(colsList[i]); 
						}else{
							missingStatus = true;
							break;
						}
					}
					if(missingStatus){
						result="missing";
						break;
					}else{
						if(col.length>1){
							columns = col[0];
							for(int i=1;i<col.length;i++){
								columns = columns + "," + col[i];
							}
						}
					}
					totColsHeader = (header.split("\t")).length;
					LogManager.info("totColsHeader----->>>"+totColsHeader);
					insertQuery = insertQuery + columns + ",TRANSACTION_ID)";
					LogManager.info("insertQuery==>"+insertQuery);
				}else{
					data = dis.readLine();
					data = data.replaceAll("'", "''");
					data = data.replaceAll("\"", "");
					data = data.replaceAll("\n", "");
					
					data = data.replaceAll("\t", "','");
					
					
					String temp = data.replaceAll("','", "");
					temp.replaceAll(" ", "");
					LogManager.info("Row Empty Checking length==>"+(temp.trim()).length());
					if((temp.trim()).length()==0){
						cont = false;
						break;
					}
					int testCount = 0;
					int length = 0;
					String temp1 = data; 
					while(temp1.contains("','")){
						if((testCount>=(totColsHeader-1)) && length==0){
							String substr = temp1.substring(0,temp1.indexOf("','"));
							length = substr.length();
						}
						temp1 = temp1.replaceFirst("','", "");
						testCount++;
					}
					LogManager.info("TotalColumns in Data----->" + (testCount+1)+"totColsHeader------->"+totColsHeader);
					if(testCount>(totColsHeader-1)){
						LogManager.info("Total length ----->" + (length+((totColsHeader-1)*3)));
						LogManager.info("Substring value ----->" + data.substring(0,(length+((totColsHeader-1)*3))));
						data = data.substring(0,(length+((totColsHeader-1)*3)));
						
					}					
					if(cont){
						LogManager.push("----"+insertQuery + "VALUES('" + data + "',?)) ");  
						final String query  = insertQuery + "VALUES('" + data + "',?)" ;
						 this.mytemplate.update(query,new Object[]{transId});
						 
					}					
				}
			}
			dis.close();
			bis.close();
			fis.close();
			if((result.trim()).length()<=0){
				LogManager.push(" UPDATE TEMP RECEIPT RECORDS");
				updateTempRecords(transId);
			    moveToMaster(transId);
				result = transId;				
			}
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
		}
		LogManager.info("UploadDAOImpl processCsv() Method - Exit"+result);
		return result;
	}
	
	// 64 vb 
	private Map getDbColumns(final String insCompanyId){
		final Map result = new HashMap();
		LogManager.push("DB COLUMNS"+insCompanyId);
		final String query = getQuery(DBConstants.UPLOAD_DB_COLUMNS);
		
		final List list = this.mytemplate.queryForList(query,new Object[]{insCompanyId});
		LogManager.push("list.size()------------>"+list.size());
		for(int i=0;i<list.size();i++){
			final Map temp = (Map)list.get(i);
			result.put((String)temp.get("EXCEL_HEADER_NAME"),(String)temp.get("DB_COLUMN_NAME"));
		}
		LogManager.push("RESULT--->"+result);
		return result;
	}
	
	private void updateTempRecords(final String transId) throws CommonBaseException{
		try{
			
			
			String query2;
			query2 = getQuery(DBConstants.DUPLICATE_UPDATE2);
			this.mytemplate.update(query2);
			
			String query;
			query = getQuery(DBConstants.DUPLICATE_UPDATE);
			this.mytemplate.update(query);
			
			String query1;
			query1 = getQuery(DBConstants.DUPLICATE_UPDATE1);
			this.mytemplate.update(query1);
			
			String query3;
			query1 = getQuery(DBConstants.DUPLICATE_UPDATE3);
			this.mytemplate.update(query1);
						
			
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e,CommonExceptionConstants.DATABASE_ERROR);
		}
	}
	
	private void moveToMaster(final String transId) throws CommonBaseException{
		
		try{
			String query = getQuery(DBConstants.MASTER_INSERT);
			String query2="";
			query2 = getQuery(DBConstants.MASTER_DUPLICATES_INSERT);
			
			LogManager.push("Query insert-->"+query);
			Object[] arg1;
			arg1 = new Object[1];
			arg1[0] = transId;
			this.mytemplate.update(query, arg1);
			//this.mytemplate.execute(query);
			LogManager.push("Query dup insert-->"+query2);
			this.mytemplate.execute(query2);
			//query = getQuery(DBConstants.MASTER_DELETE);
			//this.mytemplate.update(query);
			
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e,CommonExceptionConstants.DATABASE_ERROR);
		}
	}
	
	public List getDbColumnsForDownload(final String insCompanyId,final String masterTable){
		final String query = getQuery(DBConstants.UPLOAD_DB_COLUMNS);
		final List list = this.mytemplate.queryForList(query,new Object[]{insCompanyId,masterTable});
		return list;
	}
	
}
