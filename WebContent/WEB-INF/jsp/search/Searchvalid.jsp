<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%String reqmethod =request.getAttribute("method")==null?"":(String)request.getAttribute("method");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html locale="true"/>
  <head>
    <title>Search.jsp</title>
  </head>
  <body onload="checkActual()">

<logic:equal name="PartToShow" value="CitiBankInvalidsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">INVALID RECORDS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   	Bank Name: &nbsp;<%=request.getAttribute("bankName") %>
	
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr> 
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getInvalids" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="c_chequeno" media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="c_chequeamt" media="html"  />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="c_depositdate"  media="html" />
	    <display:column style="text-align:center;width:130px" sortable="true" title="Reason" property="c_validatestatus"   media="html"/>
		
		<display:column title="CLIENT CODE" property="c_clientcode" media="excel" /> 
		<display:column title="DEPOSIT DATE" property="c_depositdate" media="excel" />
		<display:column title="PRODUCT" property="c_product" media="excel" />
		<display:column title="CREDIT/DEBIT DATE" property="c_creditdebitdate" media="excel" />
		<display:column title="LOCATION" property="c_location" media="excel" />
		<display:column title="CHEQUE NO." property="c_chequeno" media="excel" />
		<display:column title="CHEQUE AMT." property="c_chequeamt" media="excel" />
		<display:column title="TYPE(CR/DR)" property="c_typecrdr" media="excel" />
		<display:column title="NARRATION" property="c_narration" media="excel" />
		<display:column title="CBP. NO." property="c_cbpno" media="excel" />
		<display:column title="DEP.SLIP NO." property="c_depslipno" media="excel" />
		<display:column title="CUSTOMER REF." property="c_customerref" media="excel" />
		<display:column title="DEPOSIT AMT." property="c_depositamt" media="excel" />
		<display:column title="DWE BANK CODE" property="c_dwebankcode" media="excel" />
		<display:column title="CHECK DATA" property="c_checkdata" media="excel" />
		<display:column title="COVER NOTE NO." property="c_covernoteno" media="excel" />
		<display:column title="BANK NAME" property="c_bankname" media="excel" />
		<display:column title="PICK POINT NAME" property="c_pickpointname" media="excel" />
		<display:column title="PKUP POINT CODE" property="c_pkuppointcode" media="excel" />
		<display:column title="REMARKS" property="c_remarks" media="excel" />
		<display:column title="REASON" property="c_validatestatus" media="excel" />
	
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />
	
		</display:table> 
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td></td>
    </tr>

  <tr>
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage2()" /></td>
   </tr>



    </table>
<br />
</logic:equal>

<logic:equal name="PartToShow" value="HDFCBankInvalidsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">INVALID RECORDS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   	Bank Name: &nbsp;<%=request.getAttribute("bankName") %>
	
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getInvalids" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="h_instrumentno" media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="h_instrumentamount"  media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="h_postdt"   media="html"/>
	    <display:column style="text-align:center;width:130px" sortable="true" title="DEP SLIP" property="h_depositslipno"  media="html"/>	
	    <display:column style="text-align:center;width:130px" sortable="true" title="Reason" property="h_validatestatus"  media="html" />


		<display:column title="MONTH" property="h_month" media="excel" />
		<display:column title="ROW_TYPE" property="h_rowtype" media="excel" />
		<display:column title="ENTRY_ID" property="h_entryid" media="excel" />
		<display:column title="TYPE_OF_EN" property="h_typeofen" media="excel" />
		<display:column title="DR_CR" property="h_drcr" media="excel" />
		<display:column title="ENTRY_AMT" property="h_entryamt" media="excel" />
		<display:column title="VAL_DT" property="h_valdt" media="excel" />
		<display:column title="POST_DT" property="h_postdt" media="excel" />
		<display:column title="PROD_CODE" property="h_prodcode" media="excel" />
		<display:column title="PKUP_LOC" property="h_pkuploc" media="excel" />
		<display:column title="PKUP_PT" property="h_pkuppt" media="excel" />
		<display:column title="PKUP_DT" property="h_pkupdt" media="excel" />
		<display:column title="DEPT_SLIP" property="h_depositslipno" media="excel" />
		<display:column title="DEPT_DT" property="h_depositdate" media="excel" />
		<display:column title="DEPT_AMT" property="h_deptamt" media="excel" />
		<display:column title="NO_OF_INST" property="h_noofinst" media="excel" />
		<display:column title="DEPT_RMK" property="h_deptrmk" media="excel" />
		<display:column title="INST_NO" property="h_instrumentno" media="excel" />
		<display:column title="DRAWEE_BK" property="h_draweebk" media="excel" />
		<display:column title="CL_LOC" property="h_clloc" media="excel" />
		<display:column title="INST_AMT" property="h_instrumentamount" media="excel" />
		<display:column title="INST_DT" property="h_instdt" media="excel" />
		<display:column title="DRAWER_NAM" property="h_drawernam" media="excel" />
		<display:column title="DEAL_CODE" property="h_dealcode" media="excel" />
		<display:column title="DEAL_NAME" property="h_dealname" media="excel" />
		<display:column title="DRAWER" property="h_drawer" media="excel" />
		<display:column title="POLICY_NO" property="h_policyno" media="excel" />
		<display:column title="RETURN_RSN" property="h_returnrsn" media="excel" />
		<display:column title="REASON" property="h_validatestatus" media="excel" />
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td></td>
    </tr>

  <tr>
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage2()" /></td>
   </tr>



    </table>
<br />
</logic:equal>

<logic:equal name="PartToShow" value="KOTBankInvalidsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">INVALID RECORDS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   	Bank Name: &nbsp;<%=request.getAttribute("bankName") %>
	
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getInvalids" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="k_instrumentno" media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="k_instrumentamount"  media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="k_postdt"   media="html"/>
	    <display:column style="text-align:center;width:130px" sortable="true" title="DEP SLIP" property="k_depositslipno"  media="html"/>	
	    <display:column style="text-align:center;width:130px" sortable="true" title="Reason" property="k_validatestatus"  media="html" />

		<display:column title="CLCODE1" property="k_clcode1" media="excel" />
		<display:column title="CUSTOMER_NO" property="k_customerno" media="excel" />
		<display:column title="CLIENT_NAME" property="k_clientname" media="excel" />
		<display:column title="CR_DR1" property="k_crdr1" media="excel" />
		<display:column title="VALUE_DATE1" property="k_valuedate1" media="excel" />
		<display:column title="LOCATION_NAME" property="k_locationname" media="excel" />
		<display:column title="PICKUP_LOC_CODE" property="k_pickuploccode" media="excel" />
		<display:column title="PICKUP_DATE" property="k_pickupdate" media="excel" />
		<display:column title="PICK_UP_LOC" property="k_pickuploc" media="excel" />
		<display:column title="DEPOSIT_DATE" property="k_depositdate" media="excel" />
		<display:column title="DEPOSIT_SLIP_NO" property="k_depositslipno" media="excel" />
		<display:column title="TOTAL_INSTRUMENT" property="k_totalnstrument" media="excel" />
		<display:column title="TOTAL_AMOUNT" property="k_totalamount" media="excel" />
		<display:column title="INSTRUMENT_NO" property="k_instrumentno" media="excel" />
		<display:column title="INSTRUMENT_AMT" property="k_instrumentamt" media="excel" />
		<display:column title="INSTRUMENT_DATE" property="k_instrumentdate" media="excel" />
		<display:column title="DRAWEE_BANK" property="k_draweebank" media="excel" />
		<display:column title="DRAWEE_BRANCH" property="k_draweebranch" media="excel" />
		<display:column title="DEBIT_AMNT1" property="k_debitamnt1" media="excel" />
		<display:column title="CREDIT_AMNT1" property="k_creditamnt1" media="excel" />
		<display:column title="NETAMNT1" property="k_netamnt1" media="excel" />
		<display:column title="DEBTOR_ACCT_NO1" property="k_debtoracctno1" media="excel" />
		<display:column title="DRAWER_NAME" property="k_drawername" media="excel" />
		<display:column title="REMARKS" property="k_remarks" media="excel" />
		<display:column title="PRODUCT_CODE" property="k_productcode" media="excel" />
		<display:column title="REASON" property="k_reason" media="excel" />
		<display:column title="PICKUP_POINT_CODE" property="k_pickuppointcode" media="excel" />
		<display:column title="PICKUP_POINT_DESP" property="k_pickuppointdesp" media="excel" />
		<display:column title="DEALER_REF" property="k_dealerref" media="excel" />
		<display:column title="CLIENT_ARRANGEMENT" property="k_clientarrangement" media="excel" />
		<display:column title="ENRICHMENT_VALUE1" property="k_enrichmentvalue1" media="excel" />
		<display:column title="ENRICHMENT_VALUE2" property="k_enrichmentvalue2" media="excel" />
		<display:column title="ENRICHMENT_VALUE3" property="k_enrichmentvalue3" media="excel" />
		<display:column title="ENRICHMENT_VALUE4" property="k_enrichmentvalue4" media="excel" />
		<display:column title="ENRICHMENT_VALUE5" property="k_enrichmentvalue5" media="excel" />
		<display:column title="ENRICHMENT_VALUE6" property="k_enrichmentvalue6" media="excel" />
		<display:column title="ENRICHMENT_VALUE7" property="k_enrichmentvalue7" media="excel" />
		<display:column title="ENRICHMENT_VALUE8" property="k_enrichmentvalue8" media="excel" />
		<display:column title="ENRICHMENT_VALUE9" property="k_enrichmentvalue9" media="excel" />
		<display:column title="ENRICHMENT_VALUE10" property="k_enrichmentvalue10" media="excel" />
		<display:column title="ENRICHMENT_VALUE11" property="k_enrichmentvalue11" media="excel" />
		<display:column title="ENRICHMENT_VALUE12" property="k_enrichmentvalue12" media="excel" />
		<display:column title="ENRICHMENT_VALUE13" property="k_enrichmentvalue13" media="excel" />
		<display:column title="ENRICHMENT_VALUE14" property="k_enrichmentvalue14" media="excel" />
		<display:column title="ENRICHMENT_VALUE15" property="k_enrichmentvalue15" media="excel" />
		<display:column title="HANDOFF_EVENT" property="k_handoffevent" media="excel" />
		<display:column title="TRANSACTION_JOURNAL_NMBR" property="k_transactionjournalnmbr" media="excel" />
		<display:column title="DEPOSIT_REMARKS" property="k_depositremarks" media="excel" />
		<display:column title="ENTRY_DATE" property="k_entrydate" media="excel" />
		<display:column title="VALIDATE_STATUS" property="k_validatestatus" media="excel" />
		<display:column title="ACTIVE" property="k_active" media="excel" />
		<display:column title="BATCHID" property="k_batchid" media="excel" />
		<display:column title="CORRECT_CHQ_AMT" property="k_correctchqamt" media="excel" />
		<display:column title="RETURN_REASON" property="k_returnreason" media="excel" />
		<display:column title="INSTRUMENT_AMOUNT" property="k_instrumentamount" media="excel" />
		<display:column title="NO_OF_INSTRUMENTS" property="k_noofinstruments" media="excel" />
		<display:column title="DEPOSIT_AMOUNT" property="k_depositamount" media="excel" />
		<display:column title="PICKUP_POINT" property="k_pickuppoint" media="excel" />
		<display:column title="PICKUP_LOCATION" property="k_pickuplocation" media="excel" />
		<display:column title="DEBIT_CREDIT" property="k_debitcredit" media="excel" />
		<display:column title="TXN_JOURNAL_NO" property="k_txnjournalno" media="excel" />
		<display:column title="MONTH" property="k_month" media="excel" />
		<display:column title="POST_DT" property="k_postdt" media="excel" />
		<display:column title="DR_CR" property="k_drcr" media="excel" />
		<display:column title="ENTRY_ID" property="k_entryid" media="excel" />
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td></td>
    </tr>

  <tr>
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage2()" /></td>
   </tr>



    </table>
<br />
</logic:equal>


<logic:equal name="PartToShow" value="SCBBankInvalidsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">INVALID RECORDS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   	Bank Name: &nbsp;<%=request.getAttribute("bankName") %>
	
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getInvalids" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="s_chequeno" media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="s_chequeamt"  media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="s_depdate"   media="html"/>
	    <display:column style="text-align:center;width:130px" sortable="true" title="DEP SLIP" property="s_depositno"  media="html"/>	
	    <display:column style="text-align:center;width:130px" sortable="true" title="Reason" property="s_validatestatus"  media="html" />


	    <display:column title="CustomerName" property="s_customername" media="excel" />
   	    <display:column title="Ent_type" property="s_enttype" media="excel" />
		<display:column title="Cr/Dr" property="s_crdr" media="excel" />
		<display:column title="Ent_Amount" property="s_entamount" media="excel" />
		<display:column title="Credit_DebitDt" property="s_creditdebitdate" media="excel" />
		<display:column title="Product" property="s_product" media="excel" />
		<display:column title="PickupLoc" property="s_pickuploc" media="excel" />
		<display:column title="PickupPoint" property="s_pickuppoint" media="excel" />
		<display:column title="Pickupdt" property="s_pickupdt" media="excel" />
		<display:column title="Depost#" property="s_depositno" media="excel" />
		<display:column title="Dep_date" property="s_depdate" media="excel" />
		<display:column title="Dep_Amount" property="s_depamount" media="excel" />
   	    <display:column title="PayOrderNo" property="s_payorderno" media="excel" />
		<display:column title="Chequeno" property="s_chequeno" media="excel" />
		<display:column title="ChequeDt" property="s_chequedate" media="excel" />
		<display:column title="DraweeBank" property="s_draweebank" media="excel" />
		<display:column title="Drawnon" property="s_drawnon" media="excel" />
		<display:column title="ChqAmount" property="s_chequeamt" media="excel" />
		<display:column title="Drawer" property="s_drawer" media="excel" />
		
		<display:column title="Reason" property="s_reason" media="excel" />
		<display:column title="EnrichmentNo" property="s_enrichmentno" media="excel" />
		<display:column title="EnrichmentRemark" property="s_enrichmentremarks" media="excel" />
		<display:column title="Invalid Reason" property="s_validatestatus" media="excel" />
		<display:column title="BANK NO" property="s_bank_no" media="excel" />
		<display:column title=" " property="c_bankname" media="excel" />
		<display:column title="PICK POINT NAME" property="c_pickpointname" media="excel" />
		<display:column title="PKUP POINT CODE" property="c_pkuppointcode" media="excel" />
		<display:column title="REMARKS" property="c_remarks" media="excel" />
		<display:column title="REASON" property="c_validatestatus" media="excel" />
	
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />
	
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td></td>
    </tr>

  <tr>
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage2()" /></td>
   </tr>



    </table>
<br />
</logic:equal>

<logic:equal name="PartToShow" value="AXBBankInvalidsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">INVALID RECORDS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   	Bank Name: &nbsp;<%=request.getAttribute("bankName") %>
	
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getInvalids" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="a_instno" media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="a_instrumentamount"  media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="a_depositdate"   media="html"/>
	    <display:column style="text-align:center;width:130px" sortable="true" title="DEP SLIP" property="a_slipno"  media="html"/>	
	    <display:column style="text-align:center;width:130px" sortable="true" title="Reason" property="a_validatestatus"  media="html" />


	    <display:column title="TYPE " property="a_type" media="excel" />
   	    <display:column title="CUS CODE" property="a_cuscode" media="excel" />
		<display:column title="LOCATION NAME " property="a_locationname" media="excel" />
		<display:column title="DEP DATE" property="a_depositdate" media="excel" />
		<display:column title="CR DATE" property="a_crdate" media="excel" />
		<display:column title="RTN DATE" property="a_rtndate" media="excel" />
		<display:column title="SLIP NO" property="a_slipno" media="excel" />
		<display:column title="NOF INS" property="a_nofins" media="excel" />
		<display:column title="SLIP AMOUNT" property="a_slipamount" media="excel" />
		<display:column title="INST NO" property="a_instno" media="excel" />
		<display:column title="INST DATE" property="a_instdate" media="excel" />
		<display:column title="INSTRUMENT AMOUNT" property="a_instrumentamount" media="excel" />
   	    <display:column title="I ADDITIONAL INFORMATION1" property="a_iadditionalinfo1" media="excel" />
		<display:column title="I ADDITIONAL INFORMATION2" property="a_iadditionalinfo2" media="excel" />
		<display:column title="RTN AMT" property="a_rtnamt" media="excel" />
		<display:column title="RETURN REASON" property="a_returnreason" media="excel" />
		<display:column title="DRN BK" property="a_drnbk" media="excel" />
		<display:column title="DRWN BRNCH NAME" property="a_drwnbrnchname" media="excel" />
		<display:column title="INVALID REASON" property="a_validatestatus" media="excel" />
		<%--<display:column title="" property="s_drawer" media="excel" />
		
		<display:column title="" property="s_reason" media="excel" />
		<display:column title="" property="s_enrichmentno" media="excel" />
		<display:column title="" property="s_enrichmentremarks" media="excel" />
		<display:column title="BANK NO" property="s_bank_no" media="excel" />
		<display:column title=" " property="c_bankname" media="excel" />
		<display:column title="PICK POINT NAME" property="c_pickpointname" media="excel" />
		<display:column title="PKUP POINT CODE" property="c_pkuppointcode" media="excel" />
		<display:column title="REMARKS" property="c_remarks" media="excel" />
		<display:column title="REASON" property="c_validatestatus" media="excel" />
	
		--%><display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td></td>
    </tr>

  <tr>
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage2()" /></td>
   </tr>

    </table>
<br />
</logic:equal>


<logic:equal name="PartToShow" value="HSBCBankInvalidsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">INVALID RECORDS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   	Bank Name: &nbsp;<%=request.getAttribute("bankName") %>
	
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getInvalids" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="h_instrumentno" media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="h_instrumentamount"  media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="h_postdt"   media="html"/>
	    <display:column style="text-align:center;width:130px" sortable="true" title="DEP SLIP" property="h_depositslipno"  media="html"/>	
	    <display:column style="text-align:center;width:130px" sortable="true" title="Reason" property="h_validatestatus"  media="html" />

<display:column title="Month" property="h_month" media="excel" />
		<display:column title="Record Identifier" property="h_rowtype" media="excel" />
		<display:column title="Txn. Journal No." property="h_entryid" media="excel" />
		<display:column title="Type of Entry" property="h_typeofen" media="excel" />
		<display:column title="Debit / Credit" property="h_drcr" media="excel" />
		<display:column title="Entry Amount" property="h_entryamt" media="excel" />
		<display:column title="Date of Entry" property="h_dateOfEntry" media="excel" />
		<display:column title="Post Date" property="h_postdt" media="excel" />
		<display:column title="Product Code" property="h_prodcode" media="excel" />
		<display:column title="Pickup Location" property="h_pkuploc" media="excel" />
		<display:column title="Pickup Point" property="h_pkuppt" media="excel" />
		<display:column title="Pickup Date" property="h_pkupdt" media="excel" />
		<display:column title="Deposit Slip No." property="h_depositslipno" media="excel" />
		<display:column title="Date of Deposit Slip" property="h_depositdate" media="excel" />
		<display:column title="Deposit Amount" property="h_deptamt" media="excel" />
		<display:column title="No. of Instruments" property="h_noofinst" media="excel" />
		<display:column title="Deposit Remarks" property="h_deptrmk" media="excel" />
		<display:column title="Instrument No." property="h_instrumentno" media="excel" />
		<display:column title="Drawee Bank" property="h_draweebk" media="excel" />
		<display:column title="Clearing Loc." property="h_clloc" media="excel" />
		<display:column title="Instrument Amount" property="h_instrumentamount" media="excel" />
		<display:column title="Instrument Date" property="h_instdt" media="excel" />
		<display:column title="Drawer Name" property="h_drawernam" media="excel" />
		<display:column title="MI Policy no" property="h_policyno" media="excel" />
		<display:column title="return reason" property="h_returnrsn" media="excel" />
		<display:column title="REASON" property="h_validatestatus" media="excel" />
		--><display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td></td>
    </tr>

  <tr>
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage2()" /></td>
   </tr>

    </table>
<br />
</logic:equal>

<logic:equal name="PartToShow" value="BankDuplicatesResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">DUPLICATE RECORDS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   	Bank Name: &nbsp;<%=request.getAttribute("bankName") %>
	
    </td></tr> 

</table>  
 <br />
 
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
  <%if(request.getAttribute("bankName").toString().equalsIgnoreCase("CITI BANK")) {%>

     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getDuplicates" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="c_chequeno" media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="c_chequeamt"   media="html"/>
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="c_depositdate"   media="html"/>
		<display:column title="CLIENT CODE" property="c_clientcode" media="excel" /> 
		<display:column title="DEPOSIT DATE" property="c_depositdate" media="excel" />
		<display:column title="PRODUCT" property="c_product" media="excel" />
		<display:column title="CREDIT/DEBIT DATE" property="c_creditdebitdate" media="excel" />
		<display:column title="LOCATION" property="c_location" media="excel" />
		<display:column title="CHEQUE NO." property="c_chequeno" media="excel" />
		<display:column title="CHEQUE AMT." property="c_chequeamt" media="excel" />
		<display:column title="TYPE(CR/DR)" property="c_typecrdr" media="excel" />
		<display:column title="NARRATION" property="c_narration" media="excel" />
		<display:column title="CBP. NO." property="c_cbpno" media="excel" />
		<display:column title="DEP.SLIP NO." property="c_depslipno" media="excel" />
		<display:column title="CUSTOMER REF." property="c_customerref" media="excel" />
		<display:column title="DEPOSIT AMT." property="c_depositamt" media="excel" />
		<display:column title="DWE BANK CODE" property="c_dwebankcode" media="excel" />
		<display:column title="CHECK DATA" property="c_checkdata" media="excel" />
		<display:column title="COVER NOTE NO." property="c_covernoteno" media="excel" />
		<display:column title="BANK NAME" property="c_bankname" media="excel" />
		<display:column title="PICK POINT NAME" property="c_pickpointname" media="excel" />
		<display:column title="PKUP POINT CODE" property="c_pkuppointcode" media="excel" />
		<display:column title="REMARKS" property="c_remarks" media="excel" />
		
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />	 	
		</display:table>
	</td>
    		
 	<%} %>
 	<%if(request.getAttribute("bankName").toString().equalsIgnoreCase("HDFC BANK")) {%>

     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getDuplicates" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="h_instrumentno"  media="html"/>
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="h_instrumentamount"   media="html"/>
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="h_postdt"  media="html" />
	
		<display:column title="MONTH" property="h_month" media="excel" />
		<display:column title="ROW_TYPE" property="h_rowtype" media="excel" />
		<display:column title="ENTRY_ID" property="h_entryid" media="excel" />
		<display:column title="TYPE_OF_EN" property="h_typeofen" media="excel" />
		<display:column title="DR_CR" property="h_drcr" media="excel" />
		<display:column title="ENTRY_AMT" property="h_entryamt" media="excel" />
		<display:column title="VAL_DT" property="h_valdt" media="excel" />
		<display:column title="POST_DT" property="h_postdt" media="excel" />
		<display:column title="PROD_CODE" property="h_prodcode" media="excel" />
		<display:column title="PKUP_LOC" property="h_pkuploc" media="excel" />
		<display:column title="PKUP_PT" property="h_pkuppt" media="excel" />
		<display:column title="PKUP_DT" property="h_pkupdt" media="excel" />
		<display:column title="DEPT_SLIP" property="h_depositslipno" media="excel" />
		<display:column title="DEPT_DT" property="h_depositdate" media="excel" />
		<display:column title="DEPT_AMT" property="h_deptamt" media="excel" />
		<display:column title="NO_OF_INST" property="h_noofinst" media="excel" />
		<display:column title="DEPT_RMK" property="h_deptrmk" media="excel" />
		<display:column title="INST_NO" property="h_instrumentno" media="excel" />
		<display:column title="DRAWEE_BK" property="h_draweebk" media="excel" />
		<display:column title="CL_LOC" property="h_clloc" media="excel" />
		<display:column title="INST_AMT" property="h_instrumentamount" media="excel" />
		<display:column title="INST_DT" property="h_instdt" media="excel" />
		<display:column title="DRAWER_NAM" property="h_drawernam" media="excel" />
		<display:column title="DEAL_CODE" property="h_dealcode" media="excel" />
		<display:column title="DEAL_NAME" property="h_dealname" media="excel" />
		<display:column title="DRAWER" property="h_drawer" media="excel" />
		<display:column title="POLICY_NO" property="h_policyno" media="excel" />
		<display:column title="RETURN_RSN" property="h_returnrsn" media="excel" />
	
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />	 	
		</display:table>
		</td>
    		
 	<%} %>
 	<%if(request.getAttribute("bankName").toString().equalsIgnoreCase("SCB BANK")) {%>

     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getDuplicates" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="s_chequeno"  media="html"/>
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="s_chequeamt"  media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="s_depdate"   media="html"/>
		
		<display:column title="CustomerName" property="s_customername" media="excel" />
   	    <display:column title="Ent_type" property="s_enttype" media="excel" />
		<display:column title="Cr/Dr" property="s_crdr" media="excel" />
		<display:column title="Ent_Amount" property="s_entamount" media="excel" />
		<display:column title="Credit_DebitDt" property="s_creditdebitdate" media="excel" />
		<display:column title="Product" property="s_product" media="excel" />
		<display:column title="PickupLoc" property="s_pickuploc" media="excel" />
		<display:column title="PickupPoint" property="s_pickuppoint" media="excel" />
		<display:column title="Pickupdt" property="s_pickupdt" media="excel" />
		<display:column title="Depost#" property="s_depositno" media="excel" />
		<display:column title="Dep_date" property="s_depdate" media="excel" />
		<display:column title="Dep_Amount" property="s_depamount" media="excel" />
   	    <display:column title="PayOrderNo" property="s_payorderno" media="excel" />
		<display:column title="Chequeno" property="s_chequeno" media="excel" />
		<display:column title="ChequeDt" property="s_chequedate" media="excel" />
		<display:column title="DraweeBank" property="s_draweebank" media="excel" />
		<display:column title="Drawnon" property="s_drawnon" media="excel" />
		<display:column title="ChqAmount" property="s_chequeamt" media="excel" />
		<display:column title="Drawer" property="s_drawer" media="excel" />
		<display:column title="Reason" property="s_reason" media="excel" />
		<display:column title="EnrichmentNo" property="s_enrichmentno" media="excel" />
		<display:column title="EnrichmentRemark" property="s_enrichmentremarks" media="excel" />
	
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />	 	
		</display:table>
	</td>
    		
 	<%} %>
 	<%if(request.getAttribute("bankName").toString().equalsIgnoreCase("AXIS BANK")) {%>

     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getDuplicates" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="a_instno"  media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="a_instrumentamount"  media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="a_depositdate"   media="html" />
		<display:column title="TYPE " property="a_type" media="excel" />
   	    <display:column title="CUS CODE" property="a_cuscode" media="excel" />
		<display:column title="LOCATION NAME " property="a_locationname" media="excel" />
		<display:column title="DEP DATE" property="a_depositdate" media="excel" />
		<display:column title="CR DATE" property="a_crdate" media="excel" />
		<display:column title="RTN DATE" property="a_rtndate" media="excel" />
		<display:column title="SLIP NO" property="a_slipno" media="excel" />
		<display:column title="NOF INS" property="a_nofins" media="excel" />
		<display:column title="SLIP AMOUNT" property="a_slipamount" media="excel" />
		<display:column title="INST NO" property="a_instno" media="excel" />
		<display:column title="INST DATE" property="a_instdate" media="excel" />
		<display:column title="INSTRUMENT AMOUNT" property="a_instrumentamount" media="excel" />
   	    <display:column title="I ADDITIONAL INFORMATION1" property="a_iadditionalinfo1" media="excel" />
		<display:column title="I ADDITIONAL INFORMATION2" property="a_iadditionalinfo2" media="excel" />
		<display:column title="RTN AMT" property="a_rtnamt" media="excel" />
		<display:column title="RETURN REASON" property="a_returnreason" media="excel" />
		<display:column title="DRN BK" property="a_drnbk" media="excel" />
		<display:column title="DRWN BRNCH NAME" property="a_drwnbrnchname" media="excel" />
	
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />	 	
		</display:table>
	</td>
    		
 	<%} %>
 	<%if(request.getAttribute("bankName").toString().equalsIgnoreCase("HSBC BANK")) {%>
 	<td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getInvalids" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="h_instrumentno" media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="h_instrumentamount"  media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="h_postdt"   media="html"/>
	    <display:column title="Month" property="h_month" media="excel" />
		<display:column title="Record Identifier" property="h_rowtype" media="excel" />
		<display:column title="Txn. Journal No." property="h_entryid" media="excel" />
		<display:column title="Type of Entry" property="h_typeofen" media="excel" />
		<display:column title="Debit / Credit" property="h_drcr" media="excel" />
		<display:column title="Entry Amount" property="h_entryamt" media="excel" />
		<display:column title="Date of Entry" property="h_dateOfEntry" media="excel" />
		<display:column title="Post Date" property="h_postdt" media="excel" />
		<display:column title="Product Code" property="h_prodcode" media="excel" />
		<display:column title="Pickup Location" property="h_pkuploc" media="excel" />
		<display:column title="Pickup Point" property="h_pkuppt" media="excel" />
		<display:column title="Pickup Date" property="h_pkupdt" media="excel" />
		<display:column title="Deposit Slip No." property="h_depositslipno" media="excel" />
		<display:column title="Date of Deposit Slip" property="h_depositdate" media="excel" />
		<display:column title="Deposit Amount" property="h_deptamt" media="excel" />
		<display:column title="No. of Instruments" property="h_noofinst" media="excel" />
		<display:column title="Deposit Remarks" property="h_deptrmk" media="excel" />
		<display:column title="Instrument No." property="h_instrumentno" media="excel" />
		<display:column title="Drawee Bank" property="h_draweebk" media="excel" />
		<display:column title="Clearing Loc." property="h_clloc" media="excel" />
		<display:column title="Instrument Amount" property="h_instrumentamount" media="excel" />
		<display:column title="Instrument Date" property="h_instdt" media="excel" />
		<display:column title="Drawer Name" property="h_drawernam" media="excel" />
		<display:column title="MI Policy no" property="h_policyno" media="excel" />
		<display:column title="return reason" property="h_returnrsn" media="excel" />
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		
		</display:table>
		</td>
	<%} %>
	<%if(request.getAttribute("bankName").toString().equalsIgnoreCase("AXIS BANK")) {%>

     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getDuplicates" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="a_instno"  media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="a_instrumentamount"  media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="a_depositdate"   media="html" />
		<display:column title="TYPE " property="a_type" media="excel" />
   	    <display:column title="CUS CODE" property="a_cuscode" media="excel" />
		<display:column title="LOCATION NAME " property="a_locationname" media="excel" />
		<display:column title="DEP DATE" property="a_depositdate" media="excel" />
		<display:column title="CR DATE" property="a_crdate" media="excel" />
		<display:column title="RTN DATE" property="a_rtndate" media="excel" />
		<display:column title="SLIP NO" property="a_slipno" media="excel" />
		<display:column title="NOF INS" property="a_nofins" media="excel" />
		<display:column title="SLIP AMOUNT" property="a_slipamount" media="excel" />
		<display:column title="INST NO" property="a_instno" media="excel" />
		<display:column title="INST DATE" property="a_instdate" media="excel" />
		<display:column title="INSTRUMENT AMOUNT" property="a_instrumentamount" media="excel" />
   	    <display:column title="I ADDITIONAL INFORMATION1" property="a_iadditionalinfo1" media="excel" />
		<display:column title="I ADDITIONAL INFORMATION2" property="a_iadditionalinfo2" media="excel" />
		<display:column title="RTN AMT" property="a_rtnamt" media="excel" />
		<display:column title="RETURN REASON" property="a_returnreason" media="excel" />
		<display:column title="DRN BK" property="a_drnbk" media="excel" />
		<display:column title="DRWN BRNCH NAME" property="a_drwnbrnchname" media="excel" />
	
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />	 	
		</display:table>
	</td>
    		
 	<%} %>
 	<%if(request.getAttribute("bankName").toString().equalsIgnoreCase("KOTAK BANK")) {%>

     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getDuplicates" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="k_instrumentno"  media="html"/>
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="k_instrumentamount"   media="html"/>
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="k_depositdate"  media="html" />
	
		<display:column title="CLCODE1" property="k_clcode1" media="excel" />
		<display:column title="CUSTOMER_NO" property="k_customerno" media="excel" />
		<display:column title="CLIENT_NAME" property="k_clientname" media="excel" />
		<display:column title="CR_DR1" property="k_crdr1" media="excel" />
		<display:column title="VALUE_DATE1" property="k_valuedate1" media="excel" />
		<display:column title="LOCATION_NAME" property="k_locationname" media="excel" />
		<display:column title="PICKUP_LOC_CODE" property="k_pickuploccode" media="excel" />
		<display:column title="PICKUP_DATE" property="k_pickupdate" media="excel" />
		<display:column title="PICK_UP_LOC" property="k_pickuploc" media="excel" />
		<display:column title="DEPOSIT_DATE" property="k_depositdate" media="excel" />
		<display:column title="DEPOSIT_SLIP_NO" property="k_depositslipno" media="excel" />
		<display:column title="TOTAL_INSTRUMENT" property="k_totalnstrument" media="excel" />
		<display:column title="TOTAL_AMOUNT" property="k_totalamount" media="excel" />
		<display:column title="INSTRUMENT_NO" property="k_instrumentno" media="excel" />
		<display:column title="INSTRUMENT_AMT" property="k_instrumentamt" media="excel" />
		<display:column title="INSTRUMENT_DATE" property="k_instrumentdate" media="excel" />
		<display:column title="DRAWEE_BANK" property="k_draweebank" media="excel" />
		<display:column title="DRAWEE_BRANCH" property="k_draweebranch" media="excel" />
		<display:column title="DEBIT_AMNT1" property="k_debitamnt1" media="excel" />
		<display:column title="CREDIT_AMNT1" property="k_creditamnt1" media="excel" />
		<display:column title="NETAMNT1" property="k_netamnt1" media="excel" />
		<display:column title="DEBTOR_ACCT_NO1" property="k_debtoracctno1" media="excel" />
		<display:column title="DRAWER_NAME" property="k_drawername" media="excel" />
		<display:column title="REMARKS" property="k_remarks" media="excel" />
		<display:column title="PRODUCT_CODE" property="k_productcode" media="excel" />
		<display:column title="REASON" property="k_reason" media="excel" />
		<display:column title="PICKUP_POINT_CODE" property="k_pickuppointcode" media="excel" />
		<display:column title="PICKUP_POINT_DESP" property="k_pickuppointdesp" media="excel" />
		<display:column title="DEALER_REF" property="k_dealerref" media="excel" />
		<display:column title="CLIENT_ARRANGEMENT" property="k_clientarrangement" media="excel" />
		<display:column title="ENRICHMENT_VALUE1" property="k_enrichmentvalue1" media="excel" />
		<display:column title="ENRICHMENT_VALUE2" property="k_enrichmentvalue2" media="excel" />
		<display:column title="ENRICHMENT_VALUE3" property="k_enrichmentvalue3" media="excel" />
		<display:column title="ENRICHMENT_VALUE4" property="k_enrichmentvalue4" media="excel" />
		<display:column title="ENRICHMENT_VALUE5" property="k_enrichmentvalue5" media="excel" />
		<display:column title="ENRICHMENT_VALUE6" property="k_enrichmentvalue6" media="excel" />
		<display:column title="ENRICHMENT_VALUE7" property="k_enrichmentvalue7" media="excel" />
		<display:column title="ENRICHMENT_VALUE8" property="k_enrichmentvalue8" media="excel" />
		<display:column title="ENRICHMENT_VALUE9" property="k_enrichmentvalue9" media="excel" />
		<display:column title="ENRICHMENT_VALUE10" property="k_enrichmentvalue10" media="excel" />
		<display:column title="ENRICHMENT_VALUE11" property="k_enrichmentvalue11" media="excel" />
		<display:column title="ENRICHMENT_VALUE12" property="k_enrichmentvalue12" media="excel" />
		<display:column title="ENRICHMENT_VALUE13" property="k_enrichmentvalue13" media="excel" />
		<display:column title="ENRICHMENT_VALUE14" property="k_enrichmentvalue14" media="excel" />
		<display:column title="ENRICHMENT_VALUE15" property="k_enrichmentvalue15" media="excel" />
		<display:column title="HANDOFF_EVENT" property="k_handoffevent" media="excel" />
		<display:column title="TRANSACTION_JOURNAL_NMBR" property="k_transactionjournalnmbr" media="excel" />
		<display:column title="DEPOSIT_REMARKS" property="k_depositremarks" media="excel" />
		<display:column title="ENTRY_DATE" property="k_entrydate" media="excel" />
		<display:column title="VALIDATE_STATUS" property="k_validatestatus" media="excel" />
		<display:column title="ACTIVE" property="k_active" media="excel" />
		<display:column title="BATCHID" property="k_batchid" media="excel" />
		<display:column title="CORRECT_CHQ_AMT" property="k_correctchqamt" media="excel" />
		<display:column title="RETURN_REASON" property="k_returnreason" media="excel" />
		<display:column title="INSTRUMENT_AMOUNT" property="k_instrumentamount" media="excel" />
		<display:column title="NO_OF_INSTRUMENTS" property="k_noofinstruments" media="excel" />
		<display:column title="DEPOSIT_AMOUNT" property="k_depositamount" media="excel" />
		<display:column title="PICKUP_POINT" property="k_pickuppoint" media="excel" />
		<display:column title="PICKUP_LOCATION" property="k_pickuplocation" media="excel" />
		<display:column title="DEBIT_CREDIT" property="k_debitcredit" media="excel" />
		<display:column title="TXN_JOURNAL_NO" property="k_txnjournalno" media="excel" />
		<display:column title="MONTH" property="k_month" media="excel" />
		<display:column title="POST_DT" property="k_postdt" media="excel" />
		<display:column title="DR_CR" property="k_drcr" media="excel" />
		<display:column title="ENTRY_ID" property="k_entryid" media="excel" />
	
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />	 	
		</display:table>
		</td>
    		
 	<%} %>
    </tr>
    
    <tr style="height:10px">
    <td></td>
    </tr>

  <tr>
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage2()" /></td>
   </tr>



    </table>
<br />
</logic:equal>

<logic:equal name="PartToShow" value="KOTBankReversalsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">REVERSAL RECORDS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   	Bank Name: &nbsp;<%=request.getAttribute("bankName") %>
	
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getReversals" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="k_instrumentno" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="k_instrumentamount"   />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="k_depositdate"   />
	    <display:column style="text-align:center;width:130px" sortable="true" title="Deposit Slip No." property="k_depositslipno"  />	
		
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />
	
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td></td>
    </tr>

  <tr>
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage2()" /></td>
   </tr>



    </table>
<br />
</logic:equal>

</body>
</html>