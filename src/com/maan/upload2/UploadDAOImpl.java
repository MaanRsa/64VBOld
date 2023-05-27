package com.maan.upload2;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.RowMapper;

import com.maan.common.DBConstants;
import com.maan.common.LogManager;
import com.maan.common.base.CommonBaseDAOImpl;
import com.maan.common.exception.CommonBaseException;
import com.maan.common.exception.CommonExceptionConstants;
 
public class UploadDAOImpl extends CommonBaseDAOImpl implements UploadDAO {

	public int i=1;
	StringBuffer sb = new StringBuffer("");
	
	public List getTransactionDetails(final String transId, final String bankId) throws CommonBaseException{
		LogManager.info("UploadDAOImpl getTransactionDetails() Method - Enter");
		LogManager.push("inside gettransactiondetails");
		if(bankId.equalsIgnoreCase("CIT"))
		{
		try{
		final String query2 = getQuery(DBConstants.TRANSACTION_DETAILS2);
		Object[] arg1;
		arg1 = new Object[1];
		arg1[0] = transId;
		
		final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_ERRORCOUNT2)	;
		List list = new ArrayList();
		int count=this.mytemplate.queryForInt(query2, arg1);
		int ecount=this.mytemplate.queryForInt(query3, arg1);
		list.add(count+ecount);
		list.add(ecount);
		list.add(count);
		
		Object[] arg;
		arg = new Object[6];
		arg[0] = transId;
		arg[1] = bankId;
		arg[2] = count+ecount;
		arg[3] = ecount;
		arg[4] = transId;
		arg[5] = transId;
		//Setting status c for cheque no not exists
		String query6 = getQuery(DBConstants.CITI_MASTER_UPDATE);
		this.mytemplate.update(query6);	
		
		//insertion of citi bank transaction
		String query7 = getQuery(DBConstants.TRANSACTION_INSERT1);
		this.mytemplate.update(query7,arg);
		
		//temp record delete
		String query5 = getQuery(DBConstants.CITI_MASTER_DELETE);
		this.mytemplate.update(query5);
		LogManager.info("UploadDAOImpl gettransactiondetailciti() Method - Exit"+list);
		
		return list;
			}
			catch(Exception e){
				LogManager.fatal(e);
				throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
			}
		
		}
		else
			{try{
				final String query2 = getQuery(DBConstants.TRANSACTION_DETAILS3);
				final String query3=getQuery(DBConstants.TRANSACTION_DETAILS_ERRORCOUNT3)	;
				
				List list = new ArrayList();
				
				Object[] arg1;
				arg1 = new Object[1];
				arg1[0] = transId;
				int count1=this.mytemplate.queryForInt(query2, arg1);
				int ecount1=this.mytemplate.queryForInt(query3, arg1);
				list.add(count1+ecount1);
				//list.add(list3);
				list.add(ecount1);
				list.add(count1);
				Object[] arg;
				arg = new Object[6];
				arg[0] = transId;
				arg[1] = bankId;
				arg[2] = count1+ecount1;
				arg[3] = ecount1;
				arg[4] = transId;
				arg[5] = transId;
				LogManager.push("transaction id while inserting"+transId);
				//Setting status c for cheque no not exists
				String query6 = getQuery(DBConstants.HDFC_MASTER_UPDATE);
				this.mytemplate.update(query6);	
				
				//insertion of citi bank transaction
				String query7 = getQuery(DBConstants.TRANSACTION_INSERT2);
				this.mytemplate.update(query7,arg);
				
				//delete temp
				String query5 = getQuery(DBConstants.HDFC_MASTER_DELETE);
				this.mytemplate.update(query5);
				
				LogManager.info("UploadDAOImpl gettransactiondetailhdfc() Method - Exit"+list);
				
				return list;
			}
			catch(Exception e){
				LogManager.fatal(e);
				throw new CommonBaseException(e, CommonExceptionConstants.OTHER_ERROR);
			}
		}
		
	}
	// 64 VB
	public String processCsv(final String csvLoc,final String insCompanyId,HttpServletRequest request,UploadForm form) throws CommonBaseException{
		LogManager.info("UploadDAOImpl processCsv() Method - Enter");
		final List list = new ArrayList();
		boolean duplicateStatus = false;
		int count = 0;
		int totColsHeader = 0;
		String bankcode=form.getBankname();
		List bankdetails=getBankDetail(bankcode);
		LogManager.push("bankDetails:"+ bankdetails);
		Map val=(Map)bankdetails.get(0);
		String tableName=String.valueOf(val.get("TABLE_NAME"));
		form.setTablename(tableName);
		String result = "";
		String insertQuery = "INSERT INTO TEMP_"+tableName+"(";
		String data = "";
		String header = "";
		String columns = "";
		String transId = "";
		String[] col = new String[0];
		
		//LogManager.push("banktable value:"+val.get("TABLE_NAME"));
		//LogManager.push("Inside UploadDaoImpl bankcode:"+bankcode);
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
					final Map dbDetails = getDbColumns(tableName);
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
					data = data.replaceAll("\t", "','");
					String temp = data.replaceAll("','", "");
					temp.replaceAll(" ", "");
					//LogManager.info("Row Empty Checking length==>"+(temp.trim()).length());
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
						
						final String query  = insertQuery + "VALUES('" + data + "',?)" ;
				       	this.mytemplate.update(query,new Object[]{transId});
					}					
				}
			}
			dis.close();
			bis.close();
			fis.close();
			if((result.trim()).length()<=0){
				LogManager.push(" UPDATE TEMP  BANK RECORDS ");
				//updating temp record status
				updateTempRecords(transId,tableName);
				//moving non duplicate from temp to master
			    moveToMaster(transId,tableName);
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
	private Map getDbColumns(final String tablename){
		final Map result = new HashMap();
		LogManager.push("DB COLUMNS new"+tablename);
		//String dbtable ="CITI_BANK_MAP_MASTER";
	    String columnmapquery="SELECT LOWER(EXCEL_HEADER_NAME) EXCEL_HEADER_NAME,DB_COLUMN_NAME FROM "+tablename+"_MAP_MASTER WHERE ACTIVE='Y'";
		//LogManager.push("DB table:-->"+dbtable);
		final List list = this.mytemplate.queryForList(columnmapquery);
		LogManager.push("list.size()------------>"+list.size());
		for(int i=0;i<list.size();i++){
			final Map temp = (Map)list.get(i);
			result.put((String)temp.get("EXCEL_HEADER_NAME"),(String)temp.get("DB_COLUMN_NAME"));
		}
		LogManager.push("RESULT--->"+result);
		return result;
	}
	
	private void updateTempRecords(final String transId,final String tableName) throws CommonBaseException{
		try{
			if(tableName.equalsIgnoreCase("CITI_BANK")){
				
			//String qury ="UPDATE TEMP_CITI_BANK SET CHEQUE_AMT=-(CHEQUE_AMT) WHERE CHEQUE_AMT LIKE '-%'";
			//LogManager.push("Query for matched col:==>"+qury);
			//this.mytemplate.update(qury);
			//SETTING STATUS Y
			String query;
			query = getQuery(DBConstants.DUPLICATE_CITI_BANK_UPDATE2);
			this.mytemplate.update(query);
			
			//DUPLICATE RECORDS
			
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
			else
			{
				String qury ="UPDATE TEMP_HDFC_BANK SET INSTRUMENT_AMOUNT=-(INSTRUMENT_AMOUNT) WHERE INSTRUMENT_AMOUNT LIKE '-%'";
				LogManager.push("Query for matched col:==>"+qury);
				this.mytemplate.update(qury);
				
				String query;
				query = getQuery(DBConstants.DUPLICATE_HDFC_BANK_UPDATE2);
				this.mytemplate.update(query);
				
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
			
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e,CommonExceptionConstants.DATABASE_ERROR);
		}
	}
	
	private void moveToMaster(final String transId,final String tableName) throws CommonBaseException{
		String query="";
		String query2="";
	 	try{
			
			if(tableName.equalsIgnoreCase("CITI_BANK")){
				
			//	query="INSERT INTO CITI_BANK (BANK_NO,CLIENT_CODE,DEPOSIT_DATE,PRODUCT,CREDIT_DEBIT_DATE,LOCATION,CHEQUE_NO,CHEQUE_AMT,TYPE_CRDR,NARRATION,CBP_NO,DEPSLIPNO,CUSTOMERREF,DEPOSIT_AMT,DWE_BANK_CODE,CHECK_DATA,COVERNOTENO,BANK_NAME,PICK_POINT_NAME,PKUP_POINT_CODE,REMARKS,TRANSACTION_ID) SELECT CITIBANKSEQ.NEXTVAL,CLIENT_CODE,DEPOSIT_DATE,PRODUCT,CREDIT_DEBIT_DATE,LOCATION,CHEQUE_NO,CHEQUE_AMT,TYPE_CRDR,NARRATION,CBP_NO,DEPSLIPNO,CUSTOMERREF,DEPOSIT_AMT,DWE_BANK_CODE,CHECK_DATA,COVERNOTENO,BANK_NAME,PICK_POINT_NAME,PKUP_POINT_CODE,REMARKS,TRANSACTION_ID FROM  TEMP_CITI_BANK T WHERE T.STATUS='Y'";
				query = getQuery(DBConstants.CITI_MASTER_INSERT);
				query2 = getQuery(DBConstants.CITI_DUPLICATES_INSERT);
				Object[] arg1;
				arg1 = new Object[1];
				arg1[0] = transId;
				this.mytemplate.update(query, arg1);
				this.mytemplate.execute(query2);
				String updat="update citi_bank set CHEQUE_AMT=-(CHEQUE_AMT) where CHEQUE_AMT like '-%' and TRANSACTION_ID="+transId;
			    LogManager.push("Update for min status:"+ updat);
				this.mytemplate.update(updat);
				//Selecting cheque no, cheque amt having debit and credit 
				//records filtered by debit 
			    String querysel="select  cheque_no, cheque_amt from citi_bank where transaction_id="+transId+" and cheque_no in ( select  cheque_no from citi_bank where transaction_id="+transId+" and type_crdr ='D' ) and cheque_amt in ( select  cheque_amt from citi_bank where transaction_id="+transId+"  and type_crdr ='D') group by cheque_no,cheque_amt having count(*)>1 ";
                LogManager.push("query to sel repeated cheques citi:"+querysel);
			    List list = this.mytemplate.query(querysel, new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString(1)+"_"+rset.getString(2);
					}
						
				});
			    LogManager.push("List size"+list.size());
			    if(list.size()>0){
				LogManager.push("list size of cheque nos repeated"+list.size());
				LogManager.push("Cheque no repeated citi"+list.get(0));
				String update="";
				String chqdetail[]=new String [2];
				
				for(int i=0;i<list.size();i++)
				{
				    chqdetail= list.get(i).toString().split("_");	
					LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
					update="update citi_bank set status='D' where cheque_no='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+" and transaction_id="+transId;
					LogManager.push("Update for repeated cheque's status citi:"+ update);
					this.mytemplate.update(update);
					update="select unique (case when((SELECT SUM (CHEQUE_AMT) FROM   CITI_BANK WHERE   type_crdr = 'C'   AND cheque_no = '"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+" and transaction_id="+transId+")  - (SELECT   SUM (CHEQUE_AMT) FROM   CITI_BANK  WHERE   transaction_id="+transId+" and type_crdr = 'D' AND cheque_no = '"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from citi_bank where transaction_id="+transId+" and cheque_no='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1];
       				LogManager.push("update for status:"+update);
					Object result=this.mytemplate.queryForObject(update,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString("status");
						}} );
					LogManager.push("result.toString()"+result.toString());
					if(result.toString().equalsIgnoreCase("C"))
					{
					update="update citi_bank set status='C' where transaction_id="+transId+" and cheque_no='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+" and bank_no=(select max(bank_no) from citi_bank where transaction_id="+transId+" and cheque_no ='"+chqdetail[0]+"' and cheque_amt="+chqdetail[1]+")";
					LogManager.push("Update for max cheque's status citi:"+ update);
					this.mytemplate.update(update);
					}
				}
			    }			
				String updat1="update citi_bank set CHEQUE_AMT=-(CHEQUE_AMT) where transaction_id="+transId+" and TYPE_CRDR='D' and cheque_amt not like '-%'";
				LogManager.push("Update for min status:"+ updat1);
				this.mytemplate.update(updat1);
				
					
			}
			else 
			{
				
				query = getQuery(DBConstants.HDFC_MASTER_INSERT);
				query2 = getQuery(DBConstants.HDFC_DUPLICATES_INSERT);
				Object[] arg1;
				arg1 = new Object[1];
				arg1[0] = transId;
				this.mytemplate.update(query, arg1);
				//this.mytemplate.execute(query);
				this.mytemplate.execute(query2);

			    String querysel="select  INSTRUMENT_NO,INSTRUMENT_AMOUNT from hdfc_bank where transaction_id="+transId+" and INSTRUMENT_NO in ( select  INSTRUMENT_NO from hdfc_bank where transaction_id="+transId+"  and dr_cr ='D' ) and INSTRUMENT_AMOUNT in (select  INSTRUMENT_AMOUNT from hdfc_bank where transaction_id="+transId+" and dr_cr ='D') group by INSTRUMENT_NO,INSTRUMENT_AMOUNT having count(*)>1 "; 
                LogManager.push("query to sel repeated cheques hdfc:"+querysel);
			    List list = this.mytemplate.query(querysel, new RowMapper() {
					public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString(1)+"_"+rset.getString(2);
					}
						
				});
			    String chqdetail[]=new String [2];
			    if(list.size()>0){
			
						
				LogManager.push("list size of cheque nos repeated"+list.size());
				LogManager.push("Cheque no repeated"+list.get(0));
				String update="";
				for(int i=0;i<list.size();i++)
				{
				    
				    chqdetail= list.get(i).toString().split("_");	
					LogManager.push("Cheque no repeated"+i+"==>"+list.get(i));
					update="update hdfc_bank set status='D' where INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and transaction_id="+transId;
					LogManager.push("Update for repeated cheque's status hdfc_bank:"+ update);
					this.mytemplate.update(update);
					update="select unique (case when((SELECT SUM (INSTRUMENT_AMOUNT) FROM   hdfc_bank WHERE   DR_CR = 'C'   AND INSTRUMENT_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and transaction_id="+transId+")  - (SELECT   SUM (INSTRUMENT_AMOUNT) FROM   hdfc_bank  WHERE   transaction_id="+transId+" and DR_CR = 'D' AND INSTRUMENT_NO = '"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+"))>0 then 'C' else 'D' end) as status from hdfc_bank where transaction_id="+transId+" and INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1];
       				LogManager.push("update for status:"+update);
					Object result=this.mytemplate.queryForObject(update,new RowMapper() {
						public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
						return rset.getString("status");
						}} );
					LogManager.push("result.toString()"+result.toString());
					if(result.toString().equalsIgnoreCase("C"))
					{
					update="update hdfc_bank set status='C' where transaction_id="+transId+" and INSTRUMENT_NO='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+" and bank_no=(select max(bank_no) from hdfc_bank where transaction_id="+transId+" and INSTRUMENT_NO ='"+chqdetail[0]+"' and INSTRUMENT_AMOUNT="+chqdetail[1]+")";
					LogManager.push("Update for max cheque's status citi:"+ update);
					this.mytemplate.update(update);
					}
						
				   
				}
			    }			
			}
			LogManager.push("Query insert-->"+query);
						
			LogManager.push("Query duplicate insert-->"+query2);
			
			
		}catch(Exception e){
			LogManager.fatal(e);
			throw new CommonBaseException(e,CommonExceptionConstants.DATABASE_ERROR);
		}
	}
	
	public int getProcess(String transId,String bankId) throws CommonBaseException {
		LogManager.info("UploadDAOImpl getprocessdetail() Method - Enter");
		LogManager.push("Inside getProcess daoimpl:"+bankId);
		List list=getBankQueryData(bankId);
		final Map tempMap = (Map) list.get(0);
		String tableName = ((String)tempMap.get("TABLE_NAME"));
		String chequeNo = ((String)tempMap.get("CHEQUE_NO"));
		String chequeAmt = ((String)tempMap.get("CHEQUE_AMT"));
		String chequeStatus = ((String)tempMap.get("CHEQUE_STATUS"));
		//String tableName=String.valueOf(val.get("TABLE_NAME"));
				
		LogManager.push("Inside getProcess BANK daoimpl:"+bankId);
		String query1="SELECT COUNT(*) FROM "+tableName+" WHERE BATCHID="+transId;
		Object result=this.mytemplate.queryForObject(query1,new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
			return rset.getString(1);
			}} );
		
		String uploadCount=result.toString();
		//LogManager.push("bank id from "+result.toString());
		LogManager.push("upload count>>>>>"+uploadCount);
		if(bankId.equalsIgnoreCase("CIT")){
			
		String updat="update citi_bank set CHEQUE_AMT=-(CHEQUE_AMT) where CHEQUE_AMT like '-%'and BATCHID ="+transId;
		LogManager.push("Update for min status:"+ updat);
		this.mytemplate.update(updat);
		}
		if(uploadCount.equalsIgnoreCase("0"))
		{
			
		String transacted="UPDATE TRANSACTION_DETAILS SET PROCESSED='Y', PROCESSED_TIME=SYSDATE WHERE TRANSACTION_NO ="+transId;
		LogManager.push("Update for transacted status:"+ transacted);
		this.mytemplate.update(transacted);
		return 0;
		}
		else
		{
			/****
			 * Added query 
			**/
			LogManager.push("UPDATING RECEIPT RECORDS");
			String query="SELECT   MAX(R.RECEIPT_SL_NO) as RECEIPT, MAX(C.BANK_NO) BANKNO FROM   "+tableName+ 
			" C,RECEIPT_MASTER R  WHERE  C."+chequeNo+" = R.CHEQUE_NO AND C."+chequeAmt+
			" = R.AMOUNT AND R.BANK_NO IS NULL  AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE  " +
			"WHERE BANK_ID = '"+bankId+"' AND STATUS = 'Y') AND R.PAYMENT_TYPE = 'CHQ' AND R.TRANS_SOURCE='RECT'" +
			" AND C.RECEIPT_SL_NO IS  NULL  AND C.BATCHID = "+transId+" group by C.BANK_NO";
			int count1=0, count2=0, count = 0;
			try {
			try{
				final List list1 = this.mytemplate.queryForList(query);
				if(list1!=null && list1.size()>0){
					for(int i=0;i<list1.size();i++){
						final Map temp = (Map)list1.get(i);
						//LogManager.push("Count :"+i+"::"+temp.get("RECEIPT")+"::"+temp.get("BANKNO") );
						String qury="update receipt_master set bank_no="+temp.get("BANKNO")+",transaction_bank_id="+transId+" where receipt_sl_no="+temp.get("RECEIPT"); 
						//LogManager.push("qury"+i+">>"+qury);
						count1+=this.mytemplate.update(qury);
					}
				}
			}catch(Exception e){
				LogManager.fatal(e);
				e.printStackTrace();
			}
			
			LogManager.push("count1:>>>"+count1);
			/**Update for bank record**/
			LogManager.push("UPDATING BANK RECORDS");
			 query="SELECT MAX(R.RECEIPT_SL_NO) AS RECEIPT,C.BANK_NO AS BANKNO FROM RECEIPT_MASTER R,"+tableName+ 
			 " C WHERE C."+chequeNo+" = R.CHEQUE_NO AND C."+chequeAmt+" = R.AMOUNT AND R.BANK_CODE IN " +
			 "(SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankId+"' AND STATUS='Y')AND " +
			 "R.PAYMENT_TYPE='CHQ' AND R.BANK_NO IS NOT NULL AND C.RECEIPT_SL_NO IS NULL AND R.TRANS_SOURCE='RECT'" +
			 " AND R.TRANSACTION_BANK_ID ="+transId+" AND C.BATCHID="+transId+" group by c.bank_no" ;
			 try{
					final List list1 = this.mytemplate.queryForList(query);
					if(list1!=null && list1.size()>0){
						for(int i=0;i<list1.size();i++){
							final Map temp = (Map)list1.get(i);
							//LogManager.push("Count :"+i+"::"+temp.get("RECEIPT")+"::"+temp.get("BANKNO") );
							String qury="update "+tableName+ " set receipt_sl_no="+temp.get("RECEIPT")+" where bank_no="+temp.get("BANKNO")+" and BATCHID="+transId; 
							//LogManager.push("qury"+i+">>"+qury);
							count2+=this.mytemplate.update(qury);
						}
					}
				}catch(Exception e){
					LogManager.fatal(e);
					e.printStackTrace();
				}
				LogManager.push("count2:>>>"+count2);
			if(bankId.equalsIgnoreCase("CIT")){
				String updat1="update citi_bank set CHEQUE_AMT=-(CHEQUE_AMT) where TYPE_CRDR='D' and cheque_amt not like '-%' and BATCHID ="+transId;
				LogManager.push("Update for min status:"+ updat1);
				this.mytemplate.update(updat1);
				}
				
			String transacted="UPDATE TRANSACTION_DETAILS SET PROCESSED='Y',PROCESSED_TIME=SYSDATE WHERE TRANSACTION_NO ="+transId;
			LogManager.push("Update for transacted status non duplicates:"+ transacted);
			this.mytemplate.update(transacted);
			
             //GETTING COUNTS FOR NO. OF RECORDS MATCHED
			String qury ="SELECT COUNT(*) FROM "+tableName+ " WHERE RECEIPT_SL_NO IS NOT NULL AND BATCHID="+transId;
			LogManager.push("Query for matched col:==>"+qury);
			count=this.mytemplate.queryForInt(qury);
			
			// start of actual cheque no and amt matching
			query1="SELECT UNIQUE BANK_ID FROM BANK_MASTER WHERE STATUS='Y'";
			LogManager.push("BANKS POPUP:"+query1);			
	        List listbank;
			
			listbank =this.mytemplate.query(query1,new RowMapper() {
			public Object mapRow(final ResultSet rset,final int arg) throws SQLException {
				return rset.getString("BANK_ID");
			}} );
			final String tableName1[]=new String[listbank.size()];
			final String chequeStatus1[]=new String[listbank.size()];
			final String chequeNo1[]=new String[listbank.size()];
			final String chequeAmt1[]=new String[listbank.size()];
			final String bankName1[]=new String[listbank.size()];
			final String receipt1[]=new String[listbank.size()];
			final String reason1[]=new String[listbank.size()];
			final String bankIds1[]=new String[listbank.size()];
			LogManager.push("listbank.size() " + listbank.size());
			for(int k=0;k<listbank.size();k++)
			{
				bankIds1[k]=listbank.get(k).toString();
				LogManager.push("bank"+i+":"+listbank.get(k).toString());
				List blist=getBankQueryData(listbank.get(k).toString());
				final Map tempMap1 = (Map)blist.get(0);
				 tableName1[k]=((String)tempMap1.get("TABLE_NAME"));
				 chequeStatus1[k]=((String)tempMap1.get("CHEQUE_STATUS"));
				 reason1[k]=((String)tempMap1.get("REASON"));
				 chequeNo1[k]=((String)tempMap1.get("CHEQUE_NO"));
			 	 chequeAmt1[k]=((String)tempMap1.get("CHEQUE_AMT"));
			     bankName1[k]=(String)tempMap1.get("BANK_NAME");
				 receipt1[k]=((String)tempMap1.get("RECEIPT_NO"));
				 LogManager.push("<--"+tableName1[k]+""+chequeStatus1[k]+"-->");
				// sbean.setBankName((String)tempMap.get("BANK_NAME"));
			
			//UPDATING RECEIPT_SL_NO OF BANK  FOR MATCHED RECORDS WHEN BOTH FIELDS NOT NULL
			//Query for not matched actual cheque no and amt:
			String queryvalues="SELECT ACTUAL_CHEQUE_NO,ACTUAL_CHEQUE_AMT FROM "+tableName1[k]+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
			List values=this.mytemplate.queryForList(queryvalues);
			for(int i=0;i<values.size();i++)
			{
				LogManager.push("values " + values.get(i));
				final Map tempMap2 = (Map) values.get(i);
				final String actualchequeno=((String)tempMap2.get("ACTUAL_CHEQUE_NO").toString());
				final String actualchequeamt=((String)tempMap2.get("ACTUAL_CHEQUE_AMT").toString());
				LogManager.push("actualchequeno"+actualchequeno+"actualchequeamt"+actualchequeamt);
				//Query to find actual cheque exists
				 qury="UPDATE "+tableName1[k]+" B SET RECEIPT_SL_NO=(SELECT MAX(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE R.CHEQUE_NO='"+actualchequeno+"' AND R.AMOUNT="+actualchequeamt+" AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
				this.mytemplate.update(qury);
				LogManager.push("Query for bank matched col BOTH NOT NULL:==>"+qury+"count-->");
				
				qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName1[k]+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO= '"+actualchequeno+"' AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND '"+actualchequeno+"'=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.PAYMENT_TYPE='CHQ' AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y')";
				this.mytemplate.update(qury);
				LogManager.push("Query for receipt matched col BOTH NOT NULL:==>"+qury+"count-->");
			}
            
	        //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE AMT NOT NULL
			//Query for not matched actual cheque amt:
			String queryvalues2="SELECT ACTUAL_CHEQUE_AMT FROM "+tableName1[k]+" WHERE ACTUAL_CHEQUE_NO IS NULL AND ACTUAL_CHEQUE_AMT IS NOT NULL AND RECEIPT_SL_NO IS NULL";
			List values2=this.mytemplate.queryForList(queryvalues2);
			for(int i=0;i<values2.size();i++)
			{
				LogManager.push("values " + values2.get(i));
				final Map tempMap2 = (Map) values2.get(i);
				//final String actualchequeno=((String)tempMap2.get("ACTUAL_CHEQUE_NO"));
				final String actualchequeamt=((String)tempMap2.get("ACTUAL_CHEQUE_AMT").toString());
				LogManager.push("actualchequeamt"+actualchequeamt);
				//Query to match in bank table
				 qury="UPDATE "+tableName1[k]+" B SET RECEIPT_SL_NO=(SELECT MAX(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE B."+chequeNo1[k]+"=R.CHEQUE_NO AND "+actualchequeamt+"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO IS NULL AND B.ACTUAL_CHEQUE_AMT ="+actualchequeamt+"";
				this.mytemplate.update(qury);
				LogManager.push("Query for bank matched col CHEQUE AMT NOT NULL:==>"+qury+"count-->");
				
				//query to match in receipt
				qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName1[k]+" C WHERE C."+chequeNo1[k]+"=R.CHEQUE_NO AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO IS NULL AND C.ACTUAL_CHEQUE_AMT ="+actualchequeamt+" AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND "+actualchequeamt+" =R.AMOUNT AND R.BANK_NO IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' ";
				this.mytemplate.update(qury);
				LogManager.push("Query for receipt matched col CHEQUE AMT NOT NULL:==>"+qury+"count-->");
				
			}
			
			 //UPDATING RECEIPT_SL_NO OF BANK FOR MATCHED RECORDS WHEN CHEQUE NO NOT NULL
			//Query for not matched actual cheque no:
			String queryvalues3="SELECT ACTUAL_CHEQUE_NO FROM "+tableName1[k]+" WHERE ACTUAL_CHEQUE_NO IS NOT NULL AND ACTUAL_CHEQUE_AMT IS  NULL AND RECEIPT_SL_NO IS NULL";
			List values3=this.mytemplate.queryForList(queryvalues3);
			for(int i=0;i<values3.size();i++)
			{
				LogManager.push("values " +values3.get(i));
				final Map tempMap2 = (Map) values3.get(i);
				final String actualchequeno=((String)tempMap2.get("ACTUAL_CHEQUE_NO"));
				//final String actualchequeamt=((String)tempMap1.get("ACTUAL_CHEQUE_AMT"));
				LogManager.push("actualchequeno"+actualchequeno);
				//Query to match in bank table
			    qury="UPDATE "+tableName1[k]+" B SET RECEIPT_SL_NO=(SELECT MAX(R.RECEIPT_SL_NO) FROM RECEIPT_MASTER R WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND B."+chequeAmt1[k]+"=R.AMOUNT AND R.BANK_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND B.RECEIPT_SL_NO IS NULL ) WHERE B.RECEIPT_SL_NO IS NULL AND B.ACTUAL_CHEQUE_NO ='"+actualchequeno+"' AND B.ACTUAL_CHEQUE_AMT IS NULL";
				this.mytemplate.update(qury);
				LogManager.push("Query for bank matched col CHEQUE NO NOT NULL:==>"+qury+"count-->");
				
				//query to match in receipt
				qury="UPDATE RECEIPT_MASTER R SET BANK_NO=(SELECT MAX(C.BANK_NO) FROM "+tableName1[k]+" C WHERE '"+actualchequeno+"'=R.CHEQUE_NO AND C."+chequeAmt1[k]+" =R.AMOUNT AND R.BANK_NO IS NULL AND C.ACTUAL_CHEQUE_NO= '"+actualchequeno+"' AND C.ACTUAL_CHEQUE_AMT IS NULL AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ' AND C.RECEIPT_SL_NO IS NOT NULL )WHERE R.BANK_NO IS NULL AND '"+actualchequeno+"'=R.CHEQUE_NO AND R.BANK_CODE IN (SELECT BANK_ACCOUNT_CODE FROM BANK_ACCOUNT_CODE WHERE BANK_ID='"+bankIds1[k]+"' AND STATUS='Y') AND R.PAYMENT_TYPE='CHQ'";
				this.mytemplate.update(qury);
				LogManager.push("Query for receipt matched col CHEQUE NO NOT NULL:==>"+qury+"count-->");
			}
		 }
             //	end of process actual cheque no and cheque amount 
			//end of actual cheque no and amt matching
			String updatestat="UPDATE RECEIPT_MASTER R SET CHEQUE_STATUS=(SELECT (CASE WHEN STATUS IS NULL THEN "+chequeStatus+" ELSE STATUS END) AS "+
			chequeStatus+" FROM "+tableName+" C WHERE R.BANK_NO=C.BANK_NO AND C.BATCHID ="+transId+" and R.TRANSACTION_BANK_ID ="+transId+") where TRANSACTION_BANK_ID="+transId;
			LogManager.push("Update for  status in receipt:"+ updatestat);
			this.mytemplate.update(updatestat);
			}catch(Exception e) {
				e.printStackTrace();
			}
		return count;
		}
	}
	
	public Map getBankList(UploadForm form) throws CommonBaseException {
		LogManager.push("UploadDAOImpl getBankList() method enter");
		Map result = new HashMap();
		try{
			final String query = getQuery(DBConstants.BANK_LIST);
			final List list = this.mytemplate.queryForList(query);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					final Map temp = (Map)list.get(i);
					result.put(temp.get("BANK_ID"), temp.get("BANK_NAME"));
				}
			}
		}catch(Exception e){
			LogManager.fatal(e);
		}
		LogManager.push("UploadDAOImpl getBankList() method exit");			
		 // Should be the last statement
		return result;
	}
	
	public List getBankDetail(String bankCode) throws CommonBaseException {
		List table =null;
		try{
			final String query = getQuery(DBConstants.BANK_DETAILS);
			 table =this.mytemplate.queryForList(query,new Object[]{bankCode});
			
		}catch(Exception e){
			LogManager.fatal(e);
		}LogManager.push("UploadDAOImpl getBankList() method exit");
		return table;
	}
	
	public List getBankQueryData(String bankCode) throws CommonBaseException {
		List table =null;
		try{
			final String query = getQuery(DBConstants.BANK_DETAILS_QUERY);
			 table =this.mytemplate.queryForList(query,new Object[]{bankCode});
			
		}catch(Exception e){
			LogManager.fatal(e);
		}LogManager.push("UploadDAOImpl getBankList() method exit");
		return table;
	}
	
}
