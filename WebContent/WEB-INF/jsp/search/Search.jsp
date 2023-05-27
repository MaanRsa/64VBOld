<%@ page isELIgnored ="false" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<%String reqmethod =request.getAttribute("method")==null?"":(String)request.getAttribute("method");%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html locale="true">
  <head>
    <title>Search.jsp</title>
<script>

</script>
  </head>
  
  <body onload="checkActual()">

<html:form action="/SearchDispatcAction.do" method="POST" >

<logic:messagesPresent>
<br />
<table  align="center" border="0" align="center" width="50%" cellpadding="1" cellspacing="1" >

<tr valign="top" align="center" style="height:5px">
<td colspan="2"> <font color="red"> <html:errors /> </font></td>
</tr>

<tr style="height:2px">
<td>&nbsp;</td>
</tr>
</table>
</logic:messagesPresent>

<logic:equal name="PartToShow" value="Search">

<br />
<table  align="center" border="0" align="center" width="45%" cellpadding="1" cellspacing="1" >


<tr><td><bean:message key="search.bankname" /></td>
<td align="left">
&nbsp;
<html:select property="bankId" name="searchFormBean" styleId="bankId"> 
<html:option value="all" >-All-</html:option>
<html:optionsCollection name="BankList" label="value" value="key" />
</html:select>
&nbsp;
</td>

</tr>
<tr><td><bean:message key="search.chequeno" /></td>
<td align="left">
&nbsp;
<html:text property="chequeNo" />
 &nbsp;
</td>
</tr>

<tr><td><bean:message key="search.searchfor" /></td>
<td align="left">
&nbsp;
<html:radio property="searchFor" value="exact" >Exact</html:radio>
<html:radio property="searchFor" value="similar">Similar</html:radio>
&nbsp;
</td>
</tr>

<tr><td><bean:message key="search.chequeamt" /></td>
<td align="left">
&nbsp;
<html:text property="chequeAmount" />
&nbsp;

</td>

</tr>
<tr><td><bean:message key="search.receiptno" /></td>
<td align="left">
&nbsp;
<html:text property="receiptNo" />
&nbsp;
</td>
</tr>
<tr>
<td>Deposit Date.</td>
<td align="left">
&nbsp;
<html:text  property="fromDate" styleId="fromDate" 
							readonly="readonly"/>
							
						<a href="javascript:fromDate.popup();"
							onClick="document.getElementById('fromDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here to Pick up the date">
						</a> &nbsp;
						<script>
							var fromDate = new calendar1(document.forms[0].elements["fromDate"]);
							fromDate.year_scroll = true;
							fromDate.time_comp = false;
						</script>
</td>
</tr>
<tr><td><bean:message key="search.searchin" /></td>
<td align="left">
&nbsp;
<html:radio property="searchIn" value="Receipt" >Receipt File</html:radio>
<html:radio property="searchIn" value="Bank">Bank File</html:radio>
<html:radio property="searchIn" value="Both">Both </html:radio>

&nbsp;

</td>

</tr>
<tr><td colspan="2" align="center">
<br />
<html:submit styleClass="tbut" value="Search" />

</td>

</tr>


</table>
<br />
</logic:equal>
<logic:equal name="PartToShow" value="SearchResult">


 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="10" align="left">MATCHED RECORDS</td></tr> 
 
   <tr style="height:15px">
    <td></td>
    </tr>
    <tr><td colspan="0"  height="10" align="left">&nbsp;&nbsp;You Searched For</td></tr> 
</table>
 <table cellpadding="5">
<tr>
 <td align="left"><span class="tb">Bank Reversal Records:</span></td>
 <td><span class="st"><bean:write name="searchFormBean" property="bankReversalSize"/></span></td>
 <td align="left"><span class="tb">Non Matched Receipt Records:</span></td>
 <td><span class="st"><bean:write name="searchFormBean" property="nonMathcedReceiptSize"/></span></td>
 <td><span class="tb">Non Matched Bank Records:</span></td>
 <td><span class="st"><bean:write name="searchFormBean" property="nonMatchedBankSize"/></span></td>
 </tr>
</table>
   
<table width="100%">
<tr><td>
 <table  align="center" cellpadding="5" cellspacing="0"  width="98%" style="border: 1px solid black">   
 
<tr>
<td align="right"><span class="tb">Bank Name: </span></td>
<td align="left"><span class="st">
<bean:define id="bank" name="searchFormBean" property="bankName"/>
<%if(bank.toString().equalsIgnoreCase("")) {%>
		All
		<%} else
		{%>	
<bean:write property="bankName" name="searchFormBean"/>
	 	<%} %>
	

 </span> </td>
<td align="left"><span class="tb">Cheque No.: </span>&nbsp;&nbsp;<bean:write property="chequeNo" name="searchFormBean"/></td>
</tr>

<tr>
<td align="right"><span class="tb">Cheque Amount: </span></td>
<td align="left"><span class="st"><bean:write property="chequeAmount" name="searchFormBean"/></span></td>
<td align="left"><span class="tb">Searched in: </span>&nbsp;&nbsp;<span class="st"><bean:write property="searchIn" name="searchFormBean"/> File</span></td>
</tr>

 </table>  
 </td>
 </tr>
 <tr>
 
 </tr>
 <tr><td align="right">
 <br />
 <logic:equal name="searchFormBean" property="searchIn" value="Bank" >
 <logic:empty name="searchFormBean" property="receiptNo"  >
  	 <html:button styleClass="tbut" property="goreversals" value="Bank Reversals" onclick = "document.searchFormBean.realised.value = 'no';
                document.searchFormBean.search.value = 'first';
                document.searchFormBean.method.value='reversalList';
                document.searchFormBean.bankId.value=document.getElementById('bankId').value;
                document.searchFormBean.receiptNo.value=document.getElementById('receiptNo').value;
                document.searchFormBean.searchFor.value=document.getElementById('searchFor').value;
                document.searchFormBean.searchIn.value='Bank';
                document.searchFormBean.submit();" />	&nbsp;	
    <html:button styleClass="tbut" property="gonotrealise" value="Non Matched Bank" onclick = "return notRealisedBank()" />
	<html:button styleClass="tbut" property="gonotrealise" value="Go For Bank Reversal" onclick = "return notRealisedBankReversal()" />                
  	 </logic:empty>
 </logic:equal>
  <logic:equal name="searchFormBean" property="searchIn" value="Both" >
  <logic:empty name="searchFormBean" property="receiptNo"  >
	 <html:button styleClass="tbut" property="goreversals" value="Bank Reversals" onclick = "return reversalBank()" />	&nbsp;	
	 </logic:empty>
	<html:button styleClass="tbut" property="gonotrealise" value="Non Matched Receipt" onclick = "return notRealisedReceipt()" />		&nbsp;	
	<html:button styleClass="tbut" property="gonotrealise" value="Non Matched Bank" onclick = "return notRealisedBank()" />
	<html:button styleClass="tbut" property="gonotrealise" value="Go For Bank Reversal" onclick = "return notRealisedBankReversal()" />
	
	</logic:equal>
	<script type="text/javascript">
           function notRealisedReceipt(){
                document.searchFormBean.realised.value = "no";
                document.searchFormBean.search.value = "first";
                document.searchFormBean.method.value="notRealizedList";
                //document.searchFormBean.bankId.value=document.getElementById('bankId').value;
                //document.searchFormBean.chequeNo.value=document.getElementById('chequeNo').value;
                //document.searchFormBean.chequeAmount.value=document.getElementById('chequeAmount').value;
                //document.searchFormBean.receiptNo.value=document.getElementById('receiptNo').value;
                //document.searchFormBean.searchFor.value=document.getElementById('searchFor').value;
                document.searchFormBean.searchIn.value = "Receipt";
                document.searchFormBean.submit();
           } 
           function notRealisedBank(){
                document.searchFormBean.realised.value = "no";
                document.searchFormBean.search.value = "first";
                document.searchFormBean.method.value="notRealizedList";
                //document.searchFormBean.bankId.value=document.getElementById('bankId').value;
               // document.searchFormBean.chequeNo.value=document.getElementById('chequeNo').value;
               // document.searchFormBean.chequeAmount.value=document.getElementById('chequeAmount').value;
                //document.searchFormBean.receiptNo.value=document.getElementById('receiptNo').value;
               // document.searchFormBean.searchFor.value=document.getElementById('searchFor').value;
                document.searchFormBean.searchIn.value='Bank';
                document.searchFormBean.bankData.value='actual';
                document.searchFormBean.submit();
           }
           function notRealisedBankReversal(){
                document.searchFormBean.realised.value = "no";
                document.searchFormBean.search.value = "first";
                document.searchFormBean.method.value="notRealizedList";
                //document.searchFormBean.bankId.value=document.getElementById('bankId').value;
                //document.searchFormBean.chequeNo.value=document.getElementById('chequeNo').value;
                //document.searchFormBean.chequeAmount.value=document.getElementById('chequeAmount').value;
                //document.searchFormBean.receiptNo.value=document.getElementById('receiptNo').value;
                //document.searchFormBean.searchFor.value=document.getElementById('searchFor').value;
                document.searchFormBean.searchIn.value='Bank';
                document.searchFormBean.bankData.value='reversal';
                document.searchFormBean.submit();
           } 
           function  reversalBank(){
                document.searchFormBean.realised.value = "no";
                document.searchFormBean.search.value = "first";
                document.searchFormBean.method.value="reversalList";
                //document.searchFormBean.bankId.value=document.getElementById(bankId").value;
                //document.searchFormBean.receiptNo.value=document.getElementById('receiptNo').value;
                //document.searchFormBean.searchFor.value=document.getElementById('searchFor').value;
                document.searchFormBean.searchIn.value='Bank';
                document.searchFormBean.submit();       
           } 
        </script>		

 <logic:equal name="searchFormBean" property="searchIn" value="Receipt" >
 
		<html:button styleClass="tbut" property="gonotrealise" value="Go Non Matched" onclick = "return notRealisedPage(this)" />		
		<script>
			function notRealisedPage(val){
		     	document.searchFormBean.realised.value = "no";
		     	document.searchFormBean.search.value = "first";
		     	document.searchFormBean.method.value="notRealizedList";
		     	//document.searchFormBean.bankId.value=document.getElementById('bankId').value;
		     	//document.searchFormBean.chequeNo.value=document.getElementById('chequeNo').value;
	            //document.searchFormBean.chequeAmount.value=document.getElementById('chequeAmount').value;
	            //document.searchFormBean.receiptNo.value=document.getElementById('receiptNo').value;
	            //document.searchFormBean.searchFor.value=document.getElementById('searchFor').value;
	            //document.searchFormBean.searchIn.value=document.getElementById('searchIn').value;
		     	document.searchFormBean.submit();
	   		} 
		</script>
 </logic:equal>
  	</td></tr>
 </table>
 
</logic:equal>
<logic:equal name="PartToShow" value="NotRealizedList">

 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="10" align="left" >NON MATCHED RECORDS<br /></td></tr> 
 
   <tr style="height:15px">
    <td></td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">&nbsp;&nbsp;You Searched For</td></tr> 
 
</table>   
<table width="100%">
<tr><td>
 <table  align="center" cellpadding="5" cellspacing="0"  width="98%" style="border: 1px solid black">   
 
<tr>	
<td align="right"><span class="tb">Bank Name: </span></td>
<td align="left"><span class="st">
<bean:define id="bank" name="searchFormBean" property="bankName"/>
<%if(bank.toString().equalsIgnoreCase("")) {%>
		All
		<%} else
		{%>	
<bean:write property="bankName" name="searchFormBean"/>
	 	<%} %>
</span> </td>
<td align="left"><span class="tb">Cheque No.: </span>&nbsp;&nbsp;<span class="st"><bean:write property="chequeNo" name="searchFormBean"/></span></td>
</tr>

<tr>
<td align="right"><span class="tb">Cheque Amount: </span></td>
<td align="left"><span class="st"><bean:write property="chequeAmount" name="searchFormBean"/> </span></td>
<td align="left"><span class="tb">Searched in: </span>&nbsp;&nbsp;<span class="st"><bean:write property="searchIn" name="searchFormBean"/> File</span></td>
</tr>

 </table> 
  <br /> 
 </td>
 </tr>
 <tr><td align="right"> <html:button styleClass="tbut" property="gorealise" value="Go Matched" onclick = "return realisedPage(this)" />		
  	</td></tr>
 </table>

</logic:equal>
<logic:equal name="PartToShow" value="SearchResult">

<table border="0" cellpadding="0" cellspacing="0" width="100%">
   <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="10" requestURI="/SearchDispatcAction.do?method=submitSearch" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
	 	<display:column sortable="true" style="text-align:center;width:150px" title="Bank" property="bankName" />
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="chequeNo"  />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmount" />
	 	<display:column style="text-align:center;width:100px" sortable="true" title="Deposit/Receipt Date" property="depositDate" />
		<display:column sortable="true" style="text-align:center;width:100px" title="Status" property="realisation" />
		
		<logic:equal name="PartToShow1" value="Bank" >
		<display:column style="text-align:center;width:300px" sortable="true" title="Reason" property="reason"  />
	 	</logic:equal>
		
		<logic:equal name="searchFormBean" property="searchIn" value="Both" >
		<display:column style="text-align:center;width:90px" sortable="true" title="From" property="from"  />
	 	</logic:equal>
		<display:column style="text-align:center;width:130px" sortable="true" title="Receipt/Deposit No" class="formtxtc" >
		<logic:notEmpty name="record" property="receipt">
		<bean:define id="receiptNo" name="record" property="receiptNo" />
		<bean:define id="receipt" name="record" property="receipt" />
		
		<a href="#" name="${record.receiptNo}" onclick="return callPopup('${record.receiptNo}&from=${record.from}&bid=${record.bankNo1}&chequeNo=${record.chequeNo}&bankName=${record.bankName}');"  style="cursor: pointer;" ><%=receipt %></a>   
		</logic:notEmpty>
		</display:column>	
		</display:table>
		

	</td>
    
    </tr>
    
    <tr style="height:10px">
    <td></td> 
    </tr>
    <!--<tr>
		<td colspan="3" align="center"> <br />
			<html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage()" />
		</td>
	</tr>
    --></table>
   
</logic:equal>

<logic:equal name="PartToShow" value="SearchResult">

<table border="0" cellpadding="0" cellspacing="0" width="100%">
 <logic:notEmpty name="reversalReceipt">
  <tr>
  <td>
   Receipt Reversal
  </td>
  </tr>
   <tr>
     <td align="center" colspan="2">
    
		<display:table name="reversalReceipt" pagesize="10" requestURI="/SearchDispatcAction.do?method=submitSearch" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
	 	<display:column sortable="true" style="text-align:center;width:150px" title="Bank" property="bankName" />
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="chequeNo"  />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmount" />
	 	<display:column style="text-align:center;width:100px" sortable="true" title="Receipt Date" property="depositDate" />
		<display:column sortable="true" style="text-align:center;width:100px" title="Status" property="realisation" />
		<display:column sortable="true" style="text-align:center;width:100px" title="Payment No" property="payment" />
		
		<logic:equal name="PartToShow1" value="Bank" >
		<display:column style="text-align:center;width:300px" sortable="true" title="Reason" property="reason"  />
	 	</logic:equal>
		
		<logic:equal name="searchFormBean" property="searchIn" value="Both" >
		<display:column style="text-align:center;width:90px" sortable="true" title="From" property="from"  />
	 	</logic:equal>
		<display:column style="text-align:center;width:130px" sortable="true" title="Receipt No" class="formtxtc" >
			<bean:define id="receipt" name="record" property="receipt" />
		<%=receipt %>   
		</display:column>	
		</display:table>
		

	</td>
    
    </tr>
    </logic:notEmpty>
    <tr style="height:10px">
    <td></td>
    </tr>
    <tr>
		<td colspan="3" align="center"> 
			<html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage()" />
		</td>
	</tr>
    </table>
    <br /><br />
</logic:equal>


<logic:equal name="PartToShow" value="NotRealizedList">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
        
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=notRealizedList" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
	 			<display:column sortable="true" style="text-align:center;width:170px" title="Bank" property="bankName" />
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="chequeNo"  />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmount"   />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit/Receipt Date" property="depositDate"   />
		<logic:notEqual name="PartToShow1" value="Bank" >
		<display:column style="text-align:center;width:130px" sortable="true" title="Receipt/Deposit No" class="formtxtc" property="receipt">
		</display:column>
		</logic:notEqual>
		<display:column sortable="true" style="text-align:center;width:130px" title="Status" property="realisation" />
		
		
		<logic:equal name="PartToShowBank" value="Bankactual" >
		<display:column sortable="true" style="text-align:center;width:400px" title="Reason" property="reason" />
		<display:column style="text-align:center;width:50px" title="Actual Cheque No." sortable="true" >
		<html:hidden property="bankNo1" name="record" />					
		<html:text name="record" property="actualChequeNo" maxlength="14"/> 
	 	</display:column>
	 	
		<display:column style="text-align:center;width:50px" title="Actual Cheque Amount" sortable="true" >
		<html:hidden property="bankNo2" name="record" />		
		<html:text name="record" property="actualChequeAmount"  maxlength="10" /> 
	 	
	 	</display:column>
	 	</logic:equal>
	 	<logic:equal name="PartToShowBank" value="Bankreversal">
	 	<display:column style="text-align:center;width:50px" title="Reversal" sortable="true" >
		<html:hidden property="bankNo2" name="record" />		
		<html:hidden property="bankNo1" name="record" />
		<html:select name="record" property="reversal" >
		<html:option value="N" > No</html:option>
		<html:option value="R"> Yes</html:option>
		
		
		</html:select> 
	 	</display:column>
	 	
	 	</logic:equal>	
	 	
		</display:table>
	</td>
   
    </tr>
 
    <tr style="height:10px">
    <td></td>
    </tr>
<logic:equal name="PartToShow1" value="Bank" >
  <tr>
  <logic:equal name="PartToShowBank" value="Bankreversal">
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return cancelPage()" />
	&nbsp;&nbsp;<input type="button" class="tbut" property="Process" value="Submit"  onclick="matchedResult('bankReversal')"/></td>
	</logic:equal>
	<logic:equal name="PartToShowBank" value="Bankactual">
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return cancelPage()" />
	&nbsp;&nbsp;<input type="button" class="tbut" property="Process" value="Submit"  onclick="matchedResult('bankactual')"/></td>
	</logic:equal>
	</tr>
</logic:equal>
<logic:notEqual name="PartToShow1" value="Bank" ><br />
  <tr>
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage()" /></td>
	</tr> 
</logic:notEqual>
 </table>
<br />
</logic:equal>
<logic:equal name="PartToShow" value="RealisedTransactionResult">

 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="10" align="left">MATCHED RECORDS</td></tr> 
   <tr style="height:15px">
    <td></td>
    </tr>
    <tr><td colspan="2"  height="10" align="left"> Transaction No:&nbsp;<bean:write name="searchFormBean" property="transactionNo"/>
 &nbsp;&nbsp;&nbsp;
   <logic:equal name="PartToShow1" value="Bank" >
   Bank Name:&nbsp;
   <%= request.getAttribute("Bank") %>
	</logic:equal></td></tr> 
</table>  
 <br />
 <table border="0" cellpadding="0" cellspacing="0" width="100%">
   <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="10" requestURI="/SearchDispatcAction.do?method=realisedTransaction" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
		<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="chequeNo"  />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmount"/>
	 	<logic:equal name="PartToShow1" value="Bank" >
	 	<display:column style="text-align:center;width:100px" sortable="true" title="Deposit Date" property="depositDate" />
	 	</logic:equal>
	 	<logic:notEqual name="PartToShow1" value="Bank" >
	 	<display:column style="text-align:center;width:100px" sortable="true" title="Receipt Date" property="depositDate" />
	 	
	 	</logic:notEqual>
		<display:column sortable="true" style="text-align:center;width:100px" title="Status" property="realisation" />
		
		<logic:equal name="PartToShow1" value="Bank" >
		<display:column style="text-align:center;width:300px" sortable="true" title="Reason" property="reason"  />
	 	
		</logic:equal>
		<logic:notEqual name="PartToShow1" value="Bank" >
		<display:column style="text-align:center;width:130px" sortable="true" title="Bank Name" class="formtxtc" property="bankName">
		</display:column>
		</logic:notEqual>
		<logic:equal name="PartToShow1" value="Bank" >
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Deposit No" class="formtxtc" >
		<logic:notEmpty property="receipt" name="record">
		<bean:define id="receiptNo" name="record" property="receiptNo" />
		<bean:define id="receipt" name="record" property="receipt" />
		<a href="#" name="${record.receiptNo}" onclick="return callPopup('${record.receiptNo}&from=transaction');"  style="cursor: pointer;" ><%=receipt %></a>   
		</logic:notEmpty>
		</display:column>
		</logic:equal>
		<logic:notEqual name="PartToShow1" value="Bank" >
		<display:column style="text-align:center;width:130px" sortable="true" title="Receipt No" class="formtxtc" >
		<bean:define id="receiptNo" name="record" property="receiptNo" />
		<bean:define id="receipt" name="record" property="receipt" />
		<a href="#" name="${record.receiptNo}" onclick="return callPopup('${record.receiptNo}&from=transaction');"  style="cursor: pointer;" ><%=receipt %></a>   
		</display:column>
	 	</logic:notEqual>
		</display:table>
	</td>
    </tr>
     <tr>
		<td colspan="3" align="center"> <br />
			<html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage2()" />
		</td>
	</tr>
    <tr style="height:10px">
    <td></td>
    </tr>
    </table>
</logic:equal>


<logic:equal name="PartToShow" value="NotRealizedTransactionList">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">NON MATCHED RECORDS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp;
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   <logic:equal name="PartToShow1" value="Bank" >
 	Bank Name: &nbsp;<bean:write property="bankName" name="searchFormBean"/>
	</logic:equal>
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=notRealizedTransactionList" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
	 	<logic:notEqual name="PartToShow1" value="Bank" >
		<display:column style="text-align:center;width:130px" sortable="true" title="Receipt No" class="formtxtc" property="receipt">
		</display:column>
		</logic:notEqual>
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="chequeNo"  />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmount"   />
	 	<logic:notEqual name="PartToShow1" value="Bank" >
		<display:column style="text-align:center;width:130px" sortable="true" title="Receipt Date" property="depositDate"   />
		</logic:notEqual>
		<logic:equal name="PartToShow1" value="Bank" >
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="depositDate"   />
		
		</logic:equal>
		<display:column sortable="true" style="text-align:center;width:130px" title="Status" property="realisation" />
		
		<logic:equal name="PartToShow1" value="Bank" >
		<display:column sortable="true" style="text-align:center;width:400px" title="Reason" property="reason" />
		<%--<display:column style="text-align:center;width:50px" title="Actual Cheque No." sortable="true" >
		<html:hidden property="bankNo1" name="record" />					
		<html:text name="record" property="actualChequeNo" maxlength="14" /> 
	 	</display:column>
	 	<display:column style="text-align:center;width:50px" title="Actual Cheque Amount" sortable="true" >
		<html:hidden property="bankNo2" name="record" />		
		<html:text name="record" property="actualChequeAmount"  maxlength="10" onkeyup="return checkAmt(this.value);"/> 
	 	</display:column>
	 	--%></logic:equal>	
	 	
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td></td>
    </tr>
<logic:equal name="PartToShow1" value="Bank" >
  <tr>
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage2()" /></td>
	<%--<html:button styleClass="tbut" property="Process" value="Submit"  onclick="matchedResult2()"/>--%>
	</tr>
</logic:equal>

<logic:notEqual name="PartToShow1" value="Bank" >
  <tr>
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage3()" /></td>
	</tr>
</logic:notEqual>

    </table>
<br />
</logic:equal>


<logic:equal name="PartToShow" value="MatchedResult">


<table border="0" cellpadding="0" cellspacing="0" width="100%">
 <tr><td>&nbsp;<br/></td></tr>
 <tr><td colspan="2" width="100%" class="tbn" height="20" align="left">MATCHED BY ACTUAL CHEQUE/AMOUNT </td></tr> 
   <tr><td>&nbsp;</td></tr>
   <tr><td align="right"> <html:button styleClass="tbut" property="gononmatched" value="Go NonMatched Actual Cheque/Amount " onclick = "return actualNonMatched(this)" />		
  <%-- <html:button styleClass="tbut" property="goupdatedreversals" value="Go Reversals" onclick = "return goMatchedReversals(this)" /> --%>		
  	</td></tr>
   <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="10" requestURI="/SearchDispatcAction.do?method=getActualMatched" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
	 	<display:column style="text-align:center;width:140px" sortable="true" title="Bank Name" property="bankName"  media="html"/>
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="chequeNo"  media="html"/>
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmount" media="html"/>
	 	<display:column style="text-align:center;width:100px" sortable="true" title="Actual Cheque No" property="actualChequeNo" media="html" />
	 	<display:column style="text-align:center;width:100px" sortable="true" title="Actual Cheque Amount" property="actualChequeAmount" media="html"/>
	 	<display:column style="text-align:center;width:100px" sortable="true" title="Deposit Date" property="depositDate" media="html"/>
		<display:column sortable="true" style="text-align:center;width:100px" title="Status" property="realisation" media="html"/>
		
		
		<display:column  title="Bank Name" property="bankName"  media="excel"/>
	 	<display:column  title="Cheque No" property="chequeNo"  media="excel"/>
	 	<display:column  title="Cheque Amount" property="chequeAmount" media="excel"/>
	 	<display:column  title="Actual Cheque No" property="actualChequeNo" media="excel"/>
	 	<display:column  title="Actual Cheque Amount" property="actualChequeAmount" media="excel"/>
	 	<display:column  title="Deposit Date" property="depositDate" media="excel"/>
		<display:column  title="Status" property="realisation" media="excel"/>
		 	
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<%--<display:setProperty name="export.pdf.filename"
			value="MatchedactualDetails.pdf" />--%>
		</display:table>
	</td>
    
    </tr>
    <tr>
		<td colspan="3" align="center"> <br />
			<html:submit styleClass="tbut" value="Go Search" style="width:70px" onclick="return cancelPage()" />
		</td>
	</tr>
    <tr style="height:50px">
    <td></td>
    </tr>
 </table>
    </logic:equal>
    <logic:equal name="PartToShow" value="ReversalUpdatedResult">
   
<table border="0" cellpadding="0" cellspacing="0" width="100%">
    
    <tr>
    <td colspan="2" width="100%" class="tbn" height="20" align="left">REVERSAL RECORDS</td></tr> 
 <tr><td>&nbsp;</td></tr>
  <%--<tr><td align="right"> <html:button styleClass="tbut" property="gomatched" value="Go Matched Actual Cheque/Amount " onclick = "return actualMatched(this)" />		
   <html:button styleClass="tbut" property="gononmatched" value="Go NonMatched Actual Cheque/Amount " onclick = "return actualNonMatched(this)" />		
  	</td></tr> --%>
   <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult2" pagesize="10" requestURI="/SearchDispatcAction.do?method=getUpdatedReversals"  excludedParams="method" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
	 	<display:column style="text-align:center;width:140px" sortable="true" title="Bank Name" property="bankName"  />
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="chequeNo" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmount" />
	 	<display:column style="text-align:center;width:100px" sortable="true" title="Deposit Date" property="depositDate" />
		
		<display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<%--<display:setProperty name="export.pdf.filename"
			value="ReversalDetails.pdf" />--%>
		</display:table>
	</td>
    <br /><BR />
    </tr>
    <tr>
		<td colspan="3" align="center"> <br />
			<html:submit styleClass="tbut" value="Go Search" style="width:70px" onclick="return cancelPage()" />
		</td>
	</tr>
    </table>
</logic:equal>

<logic:equal name="PartToShow" value="NonMatchedResult">

<table border="0" cellpadding="0" cellspacing="0" width="100%">
 
    <tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left">NON MATCHED RECORDS BY ACTUAL CHEQUE/AMOUNT </td></tr> 
 <tr><td>&nbsp;</td></tr>
  <tr><td align="right"> <html:button styleClass="tbut" property="gomatched" value="Go Matched Actual Cheque/Amount " onclick = "return actualMatched(this)" />		
   <%--<html:button styleClass="tbut" property="goupdatedreversals" value="Go Reversals" onclick = "return goMatchedReversals(this)" />--%>		
  	</td></tr>
   <tr>
     <td align="center" colspan="2">
		<display:table name="nonmatchedResult" pagesize="10" requestURI="/SearchDispatcAction.do?method=getActualNonMatched" excludedParams="method" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
	 	<display:column style="text-align:center;width:140px" sortable="true" title="Bank Name" property="bankName"  />
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="chequeNo"/>
	 	
	  	
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmount"/>
	 	<display:column style="text-align:center;width:100px" sortable="true" title="Actual Cheque No" property="actualChequeNo" />
	 	<display:column style="text-align:center;width:100px" sortable="true" title="Actual Cheque Amount" property="actualChequeAmount" />
	 	<display:column style="text-align:center;width:100px" sortable="true" title="Deposit Date" property="depositDate" />
		<display:column sortable="true" style="text-align:center;width:100px" title="Status" property="realisation" />
		<display:column sortable="true" style="text-align:center;width:200px" title="Reason" property="reason" />
		
	
	    <display:setProperty name="export.excel" value="true" />
		<display:setProperty name="export.csv" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<%--<display:setProperty name="export.pdf.filename"
			value="NonmatchedactualDetails.pdf" />--%>
		</display:table>
		
	</td>
    
    </tr>
   <tr>
		<td colspan="3" align="center"> <br />
			<html:submit styleClass="tbut" value="Go Search" style="width:70px" onclick="return cancelPage()" />
		</td>
	</tr>
    <tr style="height:50px">
    <td></td>
    </tr>
    
    </table>
</logic:equal>

<logic:equal name="PartToShow" value="ReceiptDuplicatesResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">DUPLICATE RECEIPT DETAILS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getDuplicates" class="table" uid="row" id="record" export="true">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="r_chequeno" media="html"  />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="r_amount"  media="html"  />
		<display:column style="text-align:center;width:130px" sortable="true" title="Receipt Date" property="r_receiptdate"  media="html"  />
	 	
	 	
		<display:column  title="Cheque No" property="r_chequeno" media="excel" />
		<display:column  title="Cheque Date" property="r_chequedate" media="excel" />
		<display:column  title="Receipt No" property="r_receiptno" media="excel" />
		<display:column  title="Receipt Date" property="r_receiptdate" media="excel" />
		<display:column  title="Amount" property="r_amount" media="excel" />
		<display:column  title="Bank Name and Loc" property="r_banknameandloc" media="excel" />
		<display:column  title="Reason" property="r_reason" media="excel" />
		<display:column  title="Receipt Entry ID" property="r_receiptentryid" media="excel" />
		<display:column  title="Bank code" property="r_bankcode" media="excel" />
		<display:column  title="Receipt AG Name" property="r_receiptagname" media="excel" />
		<display:column  title="Receipt AG Code" property="r_receiptagcode" media="excel" />
		<display:column  title="Product Code" property="r_productcode" media="excel" />
		<display:column  title="Receipt Branch Code" property="r_receiptbranchcode" media="excel" />
		<display:column  title="Particulars" property="r_particulars" media="excel" />
		<display:column  title="Trans Source" property="r_transsource" media="excel" />
		<display:column  title="Current Balance" property="r_currentbalance" media="excel" />
		<display:column  title="Delay in Days" property="r_delayindays" media="excel" />
		<display:column  title="Exchange Curr Rate" property="r_exchangecurrrate" media="excel" />
		<display:column  title="WtfOff 1" property="r_wtfoff1" media="excel" />
		<display:column  title="WtfOff 2" property="r_wtfoff2" media="excel" />
		<display:column  title="WtfOff 3" property="r_wtfoff3" media="excel" />
		<display:column  title="Due Date" property="r_duedate" media="excel" />
		<display:column  title="Payment Type" property="r_paymenttype" media="excel" />
		<display:column  title="Credit Card No" property="r_creditcardno" media="excel" />
		<display:column  title="Credit Card Type" property="r_creditcardtype" media="excel" />
		<display:column  title="Credit Card Bank" property="r_creditcardbank" media="excel" />
		<display:column  title="Credit Card Expiry" property="r_creditcardexpiry" media="excel" />
		<display:column  title="Transaction Reference" property="r_transactionreference" media="excel" />
		<display:column  title="Channel" property="r_channel" media="excel" />
		<display:column  title="SubChannel" property="r_subchannel" media="excel" />
			 		 	
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
		<%--<display:column title="BANK NO" property="s_bank_no" media="excel" />
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





<logic:equal name="PartToShow" value="ReceiptInvalidsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">INVALID RECEIPT DETAILS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   
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
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="r_chequeno"  media="html"/>
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="r_amount"   media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Receipt Date" property="r_receiptdate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Reason" property="r_validatestatus"  media="html"  />
	 	
	 	<display:column  title="Cheque No" property="r_chequeno" media="excel" />
		<display:column  title="Cheque Date" property="r_chequedate" media="excel" />
		<display:column  title="Receipt No" property="r_receiptno" media="excel" />
		<display:column  title="Receipt Date" property="r_receiptdate" media="excel" />
		<display:column  title="Amount" property="r_amount" media="excel" />
		<display:column  title="Bank Name and Loc" property="r_banknameandloc" media="excel" />
		<display:column  title="Reason" property="r_reason" media="excel" />
		<display:column  title="Receipt Entry ID" property="r_receiptentryid" media="excel" />
		<display:column  title="Bank code" property="r_bankcode" media="excel" />
		<display:column  title="Receipt AG Name" property="r_receiptagname" media="excel" />
		<display:column  title="Receipt AG Code" property="r_receiptagcode" media="excel" />
		<display:column  title="Product Code" property="r_productcode" media="excel" />
		<display:column  title="Receipt Branch Code" property="r_receiptbranchcode" media="excel" />
		<display:column  title="Particulars" property="r_particulars" media="excel" />
		<display:column  title="Trans Source" property="r_transsource" media="excel" />
		<display:column  title="Current Balance" property="r_currentbalance" media="excel" />
		<display:column  title="Delay in Days" property="r_delayindays" media="excel" />
		<display:column  title="Exchange Curr Rate" property="r_exchangecurrrate" media="excel" />
		<display:column  title="WtfOff 1" property="r_wtfoff1" media="excel" />
		<display:column  title="WtfOff 2" property="r_wtfoff2" media="excel" />
		<display:column  title="WtfOff 3" property="r_wtfoff3" media="excel" />
		<display:column  title="Due Date" property="r_duedate" media="excel" />
		<display:column  title="Payment Type" property="r_paymenttype" media="excel" />
		<display:column  title="Credit Card No" property="r_creditcardno" media="excel" />
		<display:column  title="Credit Card Type" property="r_creditcardtype" media="excel" />
		<display:column  title="Credit Card Bank" property="r_creditcardbank" media="excel" />
		<display:column  title="Credit Card Expiry" property="r_creditcardexpiry" media="excel" />
		<display:column  title="Transaction Reference" property="r_transactionreference" media="excel" />
		<display:column  title="Channel" property="r_channel" media="excel" />
		<display:column  title="SubChannel" property="r_subchannel" media="excel" />
		<display:column  title="Reason" property="r_validatestatus" media="excel" />
			 	
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


<logic:equal name="PartToShow" value="ReceiptPaymentsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">RECEIPT PAYMENT DETAILS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getReceiptPayments" class="table" uid="row" id="record" export="false">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="r_chequeno"  media="html"/>
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="r_amount"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Date" property="r_chequedate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Receipt Date" property="r_receiptdate"   media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Receipt No" property="r_receiptno"   media="html" />
	 	
	 	
	 	 	
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




<logic:equal name="PartToShow" value="ReversalList">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr><td colspan="2" width="100%" class="tbn" height="20" align="left">BANK REVERSALS</td><br /></tr> 
 
  <tr>
     <td align="center" colspan="2"><br />
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=reversalList" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	
	 	<display:column sortable="true" style="text-align:center;width:170px" title="Bank" property="bankName" />
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="chequeNo"  />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="chequeAmount"   />
		<display:column style="text-align:center;width:130px" sortable="true" title="DepositDate" property="depositDate"   />
	    	 	
	 	<display:column style="text-align:center;width:50px" title="Reversal" sortable="true" >
		
		<html:hidden property="bankNo1" name="record" />		
		<html:select name="record" property="reversal">
			<html:option value="N" > No</html:option>
			<html:option value="R"> Yes</html:option>
			
		</html:select> 
	 	</display:column>
	 	
		</display:table>
	</td>
   
    </tr>
 
    <tr style="height:10px">
    <td></td>
    </tr>

  <tr>
	<td align="center"><input type="button" class="tbut" value="Back" style="width:65px" onclick="return goBack()" />
	&nbsp;&nbsp;
	<logic:notEmpty name="searchResult">
	<input type="button" class="tbut" property="Process" value="Submit"  onclick="reversalResult()"/></td>
	</logic:notEmpty>
	
	</tr>

 </table>
<br />
</logic:equal>

 <logic:equal name="PartToShow" value="ReversalSuccess">
         <table align="center" height="300px">
         <tr><td>
	         <center><font color="green">
	           <b>Records Submitted Successfully</b>
			 </font>
			 <br />
	         <html:button property="btn" styleClass="tbut" value="OK"
								style="width:65px" onclick="return backSearch()" />
			</center>
			</td></tr>
			</table>
         </logic:equal>
         
         
  
<logic:equal name="PartToShow" value="CitiNochequesResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">CHEQUE NOT EXISTS RECORDS</td></tr> 
  	
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
<table border="0" cellpadding="0" cellspacing="0" width="75%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getNocheques" class="table" uid="row" id="record" >
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
		<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="c_chequeamt" media="html"  />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit/Receipt Date" property="c_depositdate"  media="html" />
	    <display:column style="text-align:center;width:130px" sortable="true" title="DEPSLIPNO." property="c_depslipno" media="html" />
	
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

<logic:equal name="PartToShow" value="SCBNochequesResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">CHEQUE NOT EXISTS RECORDS</td></tr> 
  	
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
<table border="0" cellpadding="0" cellspacing="0" width="75%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getNocheques" class="table" uid="row" id="record" >
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		
		<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="s_chequeamt" media="html"  />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit/Receipt Date" property="s_depdate"  media="html" />
	    <display:column style="text-align:center;width:130px" sortable="true" title="DEPSLIPNO." property="s_depositno" media="html" />
	
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


<logic:equal name="PartToShow" value="HDFCNochequesResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">CHEQUE NO. NOT EXISTS RECORDS</td></tr> 
  	
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
<table border="0" cellpadding="0" cellspacing="0" width="75%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getNocheques" class="table" uid="row" id="record" >
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	

	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="h_instrumentamount"  media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="h_postdt"   media="html"/>
	    <display:column style="text-align:center;width:130px" sortable="true" title="DEPSLIP No." property="h_depositslipno" media="html" />
		
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


<logic:equal name="PartToShow" value="ReceiptNochequesResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">CHEQUE NO. NOT EXISTS RECEIPTS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   
	
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="75%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getNocheques" class="table" uid="row" id="record" >
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
			 	

	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="h_instrumentamount"  media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Receipt Date" property="h_postdt"   media="html"/>
	    <display:column style="text-align:center;width:130px" sortable="true" title="Receipt No." property="h_depositslipno" media="html" />
		
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



<logic:equal name="PartToShow" value="CitiBankReversalsResult">
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
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="c_chequeno" media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="c_chequeamt" media="html"  />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="c_depositdate"  media="html" />
        <display:column style="text-align:center;width:130px" sortable="true" title="Deposit Slip No." property="c_depslipno" media="html" />
		
		<display:column title="DEPOSIT DATE" property="c_depositdate" media="excel" />
		<display:column title="CHEQUE NO." property="c_chequeno" media="excel" />
		<display:column title="CHEQUE AMT." property="c_chequeamt" media="excel" />
		<display:column title="DEP.SLIP NO." property="c_depslipno" media="excel" />
		
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

<logic:equal name="PartToShow" value="SCBBankReversalsResult">
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
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="s_chequeno" media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="s_chequeamt" media="html"  />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="s_depdate"  media="html" />
        <display:column style="text-align:center;width:130px" sortable="true" title="Deposit Slip No." property="s_depositno" media="html" />
		
		<display:column title="DEPOSIT DATE" property="s_depdate" media="excel" />
		<display:column title="CHEQUE NO." property="s_chequeno" media="excel" />
		<display:column title="CHEQUE AMT." property="s_chequeamt" media="excel" />
		<display:column title="DEP.SLIP NO." property="s_depositno" media="excel" />
		
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



<logic:equal name="PartToShow" value="HDFCBankReversalsResult">
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
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="h_instrumentno" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="h_instrumentamount"   />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="h_depositdate"   />
	    <display:column style="text-align:center;width:130px" sortable="true" title="Deposit Slip No." property="h_depositslipno"  />	
		
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
<logic:equal name="PartToShow" value="ReceiptReversalsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">RECEIPT REVERSALS DETAILS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="searchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   
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
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="r_chequeno"  media="html"/>
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="r_amount"   media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Receipt Date" property="r_receiptdate"   media="html" />
        <display:column style="text-align:center;width:130px" sortable="true"  title="Bank Name" property="r_bankcode" media="html" />
		<display:column style="text-align:center;width:130px" sortable="true" title="Payment No" property="r_receiptPayment" media="html" />
	
	 	
	 	<display:column  title="Cheque No" property="r_chequeno" media="excel" />
		<display:column  title="Receipt No" property="r_receiptno" media="excel" />
		<display:column  title="Receipt Date" property="r_receiptdate" media="excel" />
		<display:column  title="Amount" property="r_amount" media="excel" />
		<display:column  title="Bank Name" property="r_bankcode" media="excel" />
	    <display:column  title="Payment No" property="r_receiptPayment" media="excel" />
	
		
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

<logic:equal name="PartToShow" value="AXBNochequesResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">CHEQUE NOT EXISTS RECORDS</td></tr> 
  	
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
<table border="0" cellpadding="0" cellspacing="0" width="75%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="searchResult" pagesize="15" requestURI="/SearchDispatcAction.do?method=getNocheques" class="table" uid="row" id="record" >
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />     
		
		<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="a_instrumentamount" media="html"  />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit/Receipt Date" property="a_depositdate"  media="html" />
	    <display:column style="text-align:center;width:130px" sortable="true" title="DEPSLIPNO." property="a_slipno" media="html" />
	
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


<logic:equal name="PartToShow" value="AXBBankReversalsResult">
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
			 	
	 	<display:column style="text-align:center;width:120px" sortable="true" title="Cheque No" property="a_instno" media="html" />
	 	<display:column style="text-align:center;width:130px" sortable="true" title="Cheque Amount" property="a_instrumentamount" media="html"  />
		<display:column style="text-align:center;width:130px" sortable="true" title="Deposit Date" property="a_depositdate"  media="html" />
        <display:column style="text-align:center;width:130px" sortable="true" title="Deposit Slip No." property="a_slipno" media="html" />
		
		<display:column title="DEPOSIT DATE" property="a_depositdate" media="excel" />
		<display:column title="CHEQUE NO." property="a_instno" media="excel" />
		<display:column title="CHEQUE AMT." property="a_instrumentamount" media="excel" />
		<display:column title="DEP.SLIP NO." property="a_slipno" media="excel" />
		
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

<html:hidden title="" property="reqfrom"/>
<html:hidden property="search" />
<html:hidden property="method" value="submitSearch" />
<html:hidden title="" property="realised" value="yes" />
<html:hidden property="transactionNo" />
<html:hidden property="actualChequeNo" />
<html:hidden property="actualChequeAmount" />
<html:hidden property="bankId" styleId="bankId"/>
<html:hidden property="bankName" />
<html:hidden property="searchIn" />
<html:hidden property="bankNo1" />
<html:hidden property="bankNo2" />
<html:hidden property="chequeNo" />
<html:hidden property="chequeAmount" />
<html:hidden property="searchFor" />
<html:hidden property="reversal" />
<html:hidden property="receiptNo" />
<html:hidden property="bankData"/>
<html:hidden property="fromDate"/>

</html:form>
<script><!--

function goBack()
{
  window.history.back();
}

function callPopup(val)
   {
    var URL = '<%=request.getContextPath()%>/SearchDispatcAction.do?method=receiptDetails&receiptNo='+val;
	var windowName = "Details";
	var width  = screen.availWidth;
	var height = screen.availHeight;
	var w = 550;
	var h = 400;
	var features =
		'width='          + w +
		',height='		  + h +
		',left='		  + ((width - w - 10) * .5)  +
		',top='			  + ((height - h - 30) * .5) +
		',directories=no' +
		',location=no'	  +
		',menubar=no'	  +
		',scrollbars=yes' +
		',status=no'	  +
		',toolbar=no'	  +
		',resizable=no';
	var strOpen = window.open (URL, windowName, features);
	
	strOpen.focus();
	return false;
   }  
   		

   function checkNo(val)
   {
   
    var len = document.searchFormBean.actualChequeAmount.length;
	for(var i=0;i<len;i++){
    if(document.searchFormBean.actualChequeNo[i].value!=""){
     document.searchFormBean.reversal[i].value="N";
     document.searchFormBean.reversal[i].disabled=true;
	 }
	else if(document.searchFormBean.actualChequeNo[i].value=="" && document.searchFormBean.actualChequeAmount[i].value==""){
    
     document.searchFormBean.reversal[i].disabled=false;
	 }
	}
   }
   
   function checkAmt(val)
   {
   if(isNaN(val))
    alert("Enter cheque amount in numbers only");
   var len = document.searchFormBean.actualChequeAmount.length;
	
   	for(var i=0;i<len;i++){
    if(isNaN(document.searchFormBean.actualChequeAmount[i].value)){
    	 document.searchFormBean.actualChequeAmount[i].value="";
	 }
	  if(document.searchFormBean.actualChequeAmount[i].value!=""){
	   document.searchFormBean.reversal[i].value="N";
       document.searchFormBean.reversal[i].disabled=true;
	  }
	 else if(document.searchFormBean.actualChequeNo[i].value=="" && document.searchFormBean.actualChequeAmount[i].value==""){
	  
	     document.searchFormBean.reversal[i].disabled=false;
		 }
		}
  	}
  	function checkActual()
   {
   var len = document.searchFormBean.actualChequeAmount.length;
	
   	for(var i=0;i<len;i++){
    
	  if(document.searchFormBean.actualChequeAmount[i].value!="" || document.searchFormBean.actualChequeNo[i].value!=""){
	   /*document.searchFormBean.reversal[i].value="N";
       document.searchFormBean.reversal[i].disabled=true;*/
	  }
	 else if(document.searchFormBean.actualChequeNo[i].value=="" && document.searchFormBean.actualChequeAmount[i].value==""){
	          //document.searchFormBean.reversal[i].disabled=false;
		 }
		}
  	}
  	/*function checkReversal(val)
   {
    var len = document.searchFormBean.actualChequeAmount.length;
	for(var i=0;i<len;i++){
   
	  if(document.searchFormBean.reversal[i].value=="R"){
	  document.searchFormBean.actualChequeAmount[i].value="";
	     document.searchFormBean.actualChequeNo[i].value="";
	     document.searchFormBean.actualChequeAmount[i].diabled=true;
	     document.searchFormBean.actualChequeNo[i].disabled=true;
	  }
	 else if(document.searchFormBean.reversal[i].value!="R"){
	    
	     document.searchFormBean.actualChequeAmount[i].disabled=false;
	     document.searchFormBean.actualChequeNo[i].disabled=false;
		 }
		}
  	}*/
   
   function reversalResult()
   {
       	document.searchFormBean.realised.value = "no";
     	document.searchFormBean.search.value = "second";
     	document.searchFormBean.method.value="submitReversalDetails";
        document.searchFormBean.bankId.value=document.getElementById('bankId').value;
        document.searchFormBean.receiptNo.value=document.getElementById('receiptNo').value;
        document.searchFormBean.searchFor.value=document.getElementById('searchFor').value;
        document.searchFormBean.searchIn.value="Bank";     
       	document.searchFormBean.submit();
   }
   
   function backSearch(){
       document.searchFormBean.method.value = "search";
       document.searchFormBean.submit();	
   }
   function realisedPage(val){
      	document.searchFormBean.realised.value = "yes";
      	document.searchFormBean.method.value="submitSearch";
       	document.searchFormBean.submit();	
   }
  function matchedResult(val){
        document.searchFormBean.bankId.value=document.getElementById('bankId').value;
  		document.searchFormBean.transactionNo.value=document.getElementById('transactionNo').value;
  		if(val=='bankReversal')
  		document.searchFormBean.method.value = "submitBankReversalDetails";
  		else if(val=='bankactual')
        document.searchFormBean.method.value = "submitBankActualDetails";
      	document.searchFormBean.search.value = "second";
      	
       	document.searchFormBean.submit();	      	
   }
   function matchedResult2(){
        document.searchFormBean.bankId.value=document.getElementById('bankId').value;
        document.searchFormBean.transactionNo.value=document.getElementById('transactionNo').value;
        document.searchFormBean.method.value = "transactionSubmit";
      	document.searchFormBean.search.value = "second";
       	document.searchFormBean.submit();	
   }
 			
    function callpage(val){
	   var pts="<%=request.getAttribute("PartToShow")%>";
	
	 if("NotRealizedList"==pts ){
		var method;
		var v = val.href;
		var initIndex,endIndex;	
		var page;
		initIndex = v.indexOf("d-446779-p=");
		endIndex = v.indexOf("&", initIndex);
		if(initIndex!=-1){
			page = v.substring(initIndex,endIndex+1);
		}
		method="notRealizedList";
		document.searchFormBean.action="<%=request.getContextPath() %>/SearchDispatcAction.do?"+page+"method="+method+"&search='second'";
		document.searchFormBean.submit();
		return false;
	}	else if("ReversalList"==pts ){
		var method;
		var v = val.href;
		var initIndex,endIndex;	
		var page;
		initIndex = v.indexOf("d-446779-p=");
		endIndex = v.indexOf("&", initIndex);
		if(initIndex!=-1){
			page = v.substring(initIndex,endIndex+1);
		}
		method="reversalList";
		document.searchFormBean.action="<%=request.getContextPath() %>/SearchDispatcAction.do?"+page+"method="+method+"&search='second'";
		document.searchFormBean.submit();
		return false;
	}	else if("NotRealizedTransactionList"==pts ){
		var method;
		var v = val.href;
		var initIndex,endIndex;	
		var page;
		initIndex = v.indexOf("d-446779-p=");
		endIndex = v.indexOf("&", initIndex);
		if(initIndex!=-1){
			page = v.substring(initIndex,endIndex+1);
		}
		method="notRealizedTransactionList";
		document.searchFormBean.action="<%=request.getContextPath() %>/SearchDispatcAction.do?"+page+"method="+method;
		document.searchFormBean.submit();
		return false;
	}
}

function cancelPage(){
    window.location.href='<%=request.getContextPath() %>/SearchDispatcAction.do?method=search';
	return false;
}

function cancelPage2(){
	var method;
	var bankId = document.getElementById('bankId').value;
	if(bankId=="CIT")
	   method="citiTransactions";
	else if(bankId=="SCB")
	   method="scbTransactions";
	else if(bankId=="AXB")
	   method="axisTransactions";
	else if(bankId=="HDB")
	   method="hdfcTransactions";
	else if(bankId=="HSB")
	   method="hsbcTransactions";
	else
	   method="receiptTransactions";
	
	document.searchFormBean.action='<%=request.getContextPath() %>/transaction.do?method='+method;
	document.searchFormBean.submit();
	return false;
}

function cancelPage3(){
	document.searchFormBean.action='<%=request.getContextPath() %>/transaction.do?method=receiptTransactions';
	document.searchFormBean.submit();
	return false;
}
function actualNonMatched(val)
{
        document.searchFormBean.realised.value = "no";
     	document.searchFormBean.method.value="getActualNonMatched";
      	document.searchFormBean.submit();
}
function actualMatched(val)
{
        document.searchFormBean.realised.value = "no";
     	document.searchFormBean.method.value="getActualMatched";
        document.searchFormBean.bankId.value=document.getElementById('bankId').value;
        document.searchFormBean.chequeNo.value=document.getElementById('chequeNo').value;
        document.searchFormBean.chequeAmount.value=document.getElementById('chequeAmount').value;
        document.searchFormBean.receiptNo.value=document.getElementById('receiptNo').value;
        document.searchFormBean.searchFor.value=document.getElementById('searchFor').value;
        document.searchFormBean.searchIn.value=document.getElementById('searchIn').value;
      	document.searchFormBean.submit();
}
function goMatchedReversals(val)
{
        document.searchFormBean.realised.value = "no";
     	document.searchFormBean.method.value="getUpdatedReversals";
        document.searchFormBean.bankId.value=document.getElementById('bankId').value;
        document.searchFormBean.chequeNo.value=document.getElementById('chequeNo').value;
        document.searchFormBean.chequeAmount.value=document.getElementById('chequeAmount').value;
        document.searchFormBean.receiptNo.value=document.getElementById('receiptNo').value;
        document.searchFormBean.searchFor.value=document.getElementById('searchFor').value;
        document.searchFormBean.searchIn.value=document.getElementById('searchIn').value;
      	document.searchFormBean.submit();
}
    --></script>   
  </body>
</html:html>
