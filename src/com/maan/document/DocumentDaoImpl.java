package com.maan.document;

import java.util.HashMap;
import java.util.Map;

import com.maan.common.LogManager;
import com.maan.common.MotorBaseDAOImpl;
import com.maan.common.SQLExecution;
import com.maan.common.Validation;
import com.maan.common.Runner;


public class DocumentDaoImpl extends MotorBaseDAOImpl {
	
	private static final String EMPTY = "";
	private static final String PROCEDURENAME = "ProcedureName";
	private static final String COUNTPARAM = "count";
	private static final String PARAM1 = "param1";
	private static final String PARAM2 = "param2";
	private static final String PARAM3 = "param3";
	private static final String PARAM4 = "param4";
	private static final String PARAM5 = "param5";
	private static final String PARAM6 = "param6";
	private static final String PARAM7 = "param7";
	private transient static final String ENTER = "- Enter";
	private transient static final String EXIT = "- Exit";
	private static final String INFORMATIONID= "informationId";
	private static final String INFORMATIONVALUE= "informationValue";
	private String productId = EMPTY;
	private String bussinessTypeId = EMPTY;
	private Map hsDocInfo = null;
	private Map hsRelIds= null;
	private String[][] infoValues = new String[0][0];
	private String xgenPolicyNo;
	private String endorsementNo;
	private String amendId = EMPTY;
	private String mfrId = EMPTY;
	
	public String getMfrId() {
		return mfrId;
	}

	public void setMfrId(final String mfrId) {
		this.mfrId = mfrId;
	}

	public String getAmendId() {
		return amendId;
	}

	public void setAmendId(final String amendId) {
		this.amendId = amendId;
	}

	public String getBussinessTypeId() {
		return bussinessTypeId;
	}

	public void setBussinessTypeId(final String bussinessTypeId) {
		this.bussinessTypeId = bussinessTypeId;
	}

	public String getEndorsementNo() {
		return endorsementNo;
	}

	public void setEndorsementNo(final String endorsementNo) {
		this.endorsementNo = endorsementNo;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(final String productId) {
		this.productId = productId;
	}

	public Map getHsDocInfo() {
		return hsDocInfo;
	}

	public void setHsDocInfo(final Map hsDocInfo) {
		this.hsDocInfo = hsDocInfo;
	}

	public Map getHsRelIds() {
		return hsRelIds;
	}

	public void setHsRelIds(final Map hsRelIds) {
		this.hsRelIds = hsRelIds;
	}

	public String[][] getInfoValues() {
		return infoValues;
	}

	public void setInfoValues(final String[][] infoValues) {
		this.infoValues = infoValues;
	}

	public String getXgenPolicyNo() {
		return xgenPolicyNo;
	}

	public void setXgenPolicyNo(final String xgenPolicyNo) {
		this.xgenPolicyNo = xgenPolicyNo;
	}

	public String[][] getMotorPolDetail(final String mfrId,final String policyNumber,final String nrMasTable,final String enMasTable,final String startNumber,final String endNumber) {
		String[][] result = new String[0][0];
		String query = EMPTY;
		String[] args= new String[4];
		query = "select * from( select tranDetails.*,rownum rnum1 from (" +
				"select POLICYNO,XGEN_POLICY_NUMBER,XGEN_PRODUCT_CODE,CLIENTCODE,DEALERCODE,ENDORSEMENTNO,XGEN_PRODUCT_ID from "+enMasTable+" where upper(POLICYNO)=upper(?) union " +
				"select POLICYNO,XGEN_POLICY_NUMBER,XGEN_PRODUCT_CODE,CLIENTCODE,POLICYISSUINGDEALERCODE,'000',XGEN_PRODUCT_ID from "+nrMasTable+" where upper(POLICYNO)=upper(?) order by ENDORSEMENTNO desc) tranDetails where rownum <= to_number(?)) where rnum1 >= to_number(?)";
		args[0]=policyNumber;
		args[1]=policyNumber;
		args[2]=endNumber;
		args[3]=startNumber;
		
		result = Runner.multipleSelection(query,args);
		//LogManager.push("result length"+result.length);
		return result;
	}
	
	public String[][] getXgenPolDetail(final String mfrId,final String policyNumber,final String nrMasTable,final String enMasTable,final String startNumber,final String endNumber) {
		String[][] result = new String[0][0];
		String query = EMPTY;
		String[] args= new String[4];
		query = "select * from( select tranDetails.*,rownum rnum1 from (" +
				"select POLICYNO,XGEN_POLICY_NUMBER,XGEN_PRODUCT_CODE,CLIENTCODE,DEALERCODE,ENDORSEMENTNO,XGEN_PRODUCT_ID from "+enMasTable+" where upper(XGEN_POLICY_NUMBER)=upper(?) union " +
				"select POLICYNO,XGEN_POLICY_NUMBER,XGEN_PRODUCT_CODE,CLIENTCODE,POLICYISSUINGDEALERCODE,'000',XGEN_PRODUCT_ID from "+nrMasTable+" where upper(XGEN_POLICY_NUMBER)=upper(?)) tranDetails where rownum <= to_number(?)) where rnum1 >= to_number(?)";
		args[0]=policyNumber;
		args[1]=policyNumber;
		args[2]=endNumber;
		args[3]=startNumber;
		result = Runner.multipleSelection(query,args);
		//LogManager.push("result length"+result.length);
		return result;
	}
	
	public String getBusinessTypeId(final String xgenPolNumber) {
		String user[][] = new String[0][0];
		final HashMap hsProcedure = new HashMap();
		try{
				hsProcedure.put(COUNTPARAM, "2");
				hsProcedure.put(PROCEDURENAME, "spGetBussTypeId");
				hsProcedure.put(PARAM1, xgenPolNumber);
				//user = SQLExecution.selection(hsProcedure);
		}
		catch(Exception e){
			LogManager.debug(e);
			//e.printStackTrace();
		}	
		return (user[0][0]==null?"101":user[0][0]);
	}
	
	public String[][] getFullInfoDetailDetails(final String policyNumber,final String mfrId,final String endorsementNo) {
		String user[][] = new String[0][0];
		
		try{
			final HashMap hsProcedure = new HashMap();
			hsProcedure.put(COUNTPARAM, "4");
			hsProcedure.put(PROCEDURENAME, "spFullDiscrepancy");
			hsProcedure.put(PARAM1, policyNumber);
			hsProcedure.put(PARAM2, mfrId);
			hsProcedure.put(PARAM3, endorsementNo);
			//user = SQLExecution.selection(hsProcedure);	
		}
		catch(Exception e){
			LogManager.debug(e);
		}
		
		return user;
	}
	
	public String[][] getDocumentTypeInfoDetails(final String productIdSP) {

		LogManager.push("getDocumentTypeInfoDetails method()");
		LogManager.debug(ENTER);

		String user[][] = new String[0][0];
		
		try{
			final HashMap hsProcedure = new HashMap();
			hsProcedure.put("count", "2");
			hsProcedure.put(PROCEDURENAME, "spGetDocTypeInfoDetails");
			hsProcedure.put("param1", productIdSP);
			//user = SQLExecution.selection(hsProcedure);
		
		}
		catch(Exception e){
			LogManager.debug(e);
		}

		LogManager.debug(EXIT);
		LogManager.popRemove();

		return user;
	}

	public String[][] getDocumentTypeInfoDetailDetails(final String documentId,final String productId,final String bussinessTypeId) {

		LogManager.push("getDocumentTypeInfoDetailDetails method()");
		LogManager.debug(ENTER);
		
		String user[][] = new String[0][0];
		
		final Map hsProcedure = new HashMap();
		
		try{

			hsProcedure.put(COUNTPARAM, "4");
			hsProcedure.put(PROCEDURENAME, "spGetDocTypeInfoDetailDetails");
			//LogManager.push("documentId "+documentId);
			hsProcedure.put(PARAM1, documentId);
			//LogManager.push("productId "+productId);
			hsProcedure.put(PARAM2, productId);
			//LogManager.push("bussinessTypeId "+bussinessTypeId);
			hsProcedure.put(PARAM3, bussinessTypeId);
			//user = SQLExecution.selection((HashMap) hsProcedure);
		}
		catch(Exception e){
			LogManager.debug(e);
		}

		LogManager.debug(EXIT);
		LogManager.popRemove();

		return user;
	}
	
	public String[][] getTextDiscValues(final String informationId)
	{
		
		String user[][] = new String[0][0];
		try{
			
			final HashMap hsProcedure = new HashMap();
			hsProcedure.put(COUNTPARAM, "5");
			hsProcedure.put(PROCEDURENAME, "spGetTextDiscValue");
			hsProcedure.put(PARAM1, xgenPolicyNo);
			hsProcedure.put(PARAM2, mfrId);
			hsProcedure.put(PARAM3, endorsementNo);
			hsProcedure.put(PARAM4, informationId);
			//user = SQLExecution.selection(hsProcedure);
		
		}
		catch(Exception e){
			LogManager.debug(e);
		}
		
		return user;
	}
	
	public String[][] getInfoValue(final String informationId) {
		String user[][] = new String[0][0];
		try{
			final HashMap hsProcedure = new HashMap();
			hsProcedure.put(COUNTPARAM, "2");
			hsProcedure.put(PROCEDURENAME, "spGetInfoValue");
			hsProcedure.put(PARAM1, informationId);
			//user = SQLExecution.selection(hsProcedure);
		}
		catch(Exception e){
			LogManager.debug(e);
		}
		
		return user;
	}
	
//	Changes done for Performance Issue - 13-Nov-2009
	public StringBuffer validateInsureDetails(final Map hsProposalTrans)
	{
		//String informationId = EMPTY;
		//String informationValue = EMPTY;
		String dayDifference = EMPTY;
		//float daysDiff = 0;
		final StringBuffer error = new StringBuffer(100);	
		try{

			final String informationId = (String)hsProposalTrans.get(INFORMATIONID);
			String informationValue = (String)hsProposalTrans.get(INFORMATIONVALUE);
			informationValue = informationValue.trim() == null?EMPTY:informationValue.trim();
			if(!(EMPTY.equals(informationValue)))
			{
					final String[][] relationValues = getRelationValues(productId,bussinessTypeId,informationId);
					String infochkValue = EMPTY;
					for(int rel=0;rel<relationValues.length;rel++)
					{
						infochkValue = getInputValue(relationValues[rel][1]);
					}
					/*if(!(EMPTY.equals(informationValue)))
					{*/
						if(informationValue.length() < 10 || informationValue.length() > 10)
						{
							error.append("CoverNote Number should be 10 Character <br>");
						}
						else
						{
							String firstThreeChars = EMPTY;
							String result = EMPTY;
							String result2 = EMPTY;
							String restChars = EMPTY;
							
							firstThreeChars = informationValue.substring(0, 3);
							restChars = informationValue.substring(3, 10);
							//LogManager.push("firstThreeChars==>"+firstThreeChars);
							//LogManager.push("restChars==>"+restChars);
							final Validation validObj = new Validation();
							result = validObj.validateStringValue(firstThreeChars);
							result2 = validObj.validInteger(restChars);
							if("Invalid".equalsIgnoreCase(result) || "Invalid".equalsIgnoreCase(result2))
							{
								error.append("CoverNote Number First three characters should be Alphabet and the rest should be numeric<br>");
							}
							
						}
					//}
					/*else if(!(EMPTY.equals(informationValue)) && (!(EMPTY.equals(infochkValue))))
					{*/
						if(!(EMPTY.equals(infochkValue)))
						{
							dayDifference = validateWithCurrentDate(infochkValue)==null?EMPTY:validateWithCurrentDate(infochkValue);
							if(EMPTY.equals(dayDifference))
							{
								error.append("Please select Valid CoverNote Issue Date <br>");
								//LogManager.push("Please select Valid CoverNote Issue Date");
							}
					}
					
					
				}		
			}
			
	catch(Exception e)
	{	
		LogManager.debug(e);
	}
		return error;
	}
//	validateInsureDetails
	public String validateWithCurrentDate(final String coverDate)
	{
		String dayDiff = EMPTY;
		String[][] dayDiffDetail = new String[0][0];
		String dayDiffQuery = EMPTY;
		dayDiffQuery = "select (sysdate - to_date('"+coverDate+"','dd-mm-YYYY')) from dual";
		try {
			//Runner runPack = new Runner();	
			dayDiffDetail = Runner.multipleSelection(dayDiffQuery,new String[0]);
			if(dayDiffDetail != null)
			{
				dayDiff = dayDiffDetail[0][0] == null?EMPTY:dayDiffDetail[0][0];
			}
		} catch (Exception ex) {
			LogManager.debug(ex);
			dayDiff = EMPTY;
		} 
		
		return dayDiff;
	}
	
	public  String[][] getRelationValues(final String productId,final String bussinessTypeId,final String informationId){
		String[][] user = new String[0][0];

		//String proName = "spGetRelationValues";
		try{
			final HashMap hsProcedure = new HashMap();
				hsProcedure.put(COUNTPARAM, "4");
				hsProcedure.put(PROCEDURENAME, "spGetRelationValues");
				hsProcedure.put(PARAM1, productId);
				hsProcedure.put(PARAM2, bussinessTypeId);
				hsProcedure.put(PARAM3, informationId);
				//user = SQLExecution.selection(hsProcedure);
		}
		catch(Exception e)
		{
			LogManager.debug(e); //e.printStackTrace();
		}
		
		return user;
	}
	public String getInputValue(final String relId)
	{
		String infoVal = EMPTY;
		for(int inf=0;inf<infoValues.length;inf++)
		{
			if(infoValues[inf][0].equalsIgnoreCase(relId)){
				infoVal = infoValues[inf][1];
				break;
			}
				
		}
		return infoVal;
	}
	
	/*public void setRelationIds(final String documentTypeId)
	{
		String relationIds = EMPTY;
		hsRelIds = new HashMap();
		String[] cols = new String[3];
		String[][] returnValue = new String[0][0];
		//Runner runPack = new Runner();
		try
		{
			relationIds = "select distinct informationId,INFORMATIONIDCHK from TBLMASINFORELATION where productid=? and businesstypeid=? and DOCUMENTTYPEID=?";
			cols[0]=productId;
			cols[1]=bussinessTypeId;
			cols[2]=documentTypeId;
			//returnValue=Runner.multipleSelection(relationIds, cols);
			if(returnValue != null && returnValue.length>0)
			{
				for(int rel=0;rel<returnValue.length;rel++)
				{
					hsRelIds.put(returnValue[rel][0], returnValue[rel][1]);
				}
				setHsRelIds(hsRelIds);
			}
		}
		catch(Exception e)
		{
			LogManager.debug(e);
		}
	}*/
	
	public String manipulateInformation(final Map hsProposalTrans) {

		String returnValue = EMPTY;
		String[][] user = new String[0][0];
		try {
			final Map hashMap = new HashMap();
			hashMap.put(COUNTPARAM, "9");
			hashMap.put(PROCEDURENAME, "spManipulateDiscrepancy");
			hashMap.put(PARAM1, xgenPolicyNo);
			hashMap.put(PARAM2, endorsementNo);
			hashMap.put(PARAM3, (String)hsProposalTrans.get("informationId"));
			hashMap.put(PARAM4, (String)hsProposalTrans.get("informationValue"));
			hashMap.put(PARAM5, "Y");
			hashMap.put(PARAM6, amendId);
			hashMap.put(PARAM7, mfrId);
			//LogManager.push("hashMap values=="+hashMap);
			//user = SQLExecution.insertion((HashMap) hashMap);
			if(user.length>0 && user[0][0]!= null)
			{
				returnValue = "Document Details are inserted Successfully";
			}
			else
			{
				returnValue = "Error in Data.Please try again";
			}
		} catch (Exception ex) {
			LogManager.debug(ex);
		}
		return returnValue;
	}
}