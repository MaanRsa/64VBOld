<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>


<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<html:html >
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  
    <title>PolicySearch.jsp</title> 
 
  </head>
  
  <body >

<html:form action="/UserSearchDispatcAction.do" method="post">

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

<tr><td><%--<bean:message key="search.PolicyNo" />--%></td>
<td align="left">
&nbsp;
<%--<html:text name="userSearchFormBean" property="policyNo" />--%>
Search By :
<html:select name="userSearchFormBean" property="searchOption">
<html:option value="select">--Select--</html:option>
<html:option value="Policy No">Policy No</html:option>
<html:option value="Receipt No">Receipt No</html:option>
<html:option value="Cheque No">Cheque No</html:option>
<html:option value="Transaction No">Transaction No</html:option>
</html:select>
<html:text name="userSearchFormBean" property="searchValue" />

&nbsp;
</td>
</tr>

<tr><td colspan="2" align="center">
<br />
<input type="submit" class="tbut" value="Search"  name="search" />
</td>
</tr>

</table>
<br />
</logic:equal>
<logic:equal name="PartToShow" value="SearchResult">


 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="10" align="left">SEARCH RESULT</td></tr> 
 </table>   

<table width="100%">
<tr><td>

 <table   align="center" cellpadding="0" cellspacing="0"  width="85%" >   
 
<tr>
<td align="center" ><span class="tb"><bean:write property="searchOption" name="userSearchFormBean"/>: </span>&nbsp;&nbsp;<bean:write property="searchValue" name="userSearchFormBean"/></td>
</tr>
 

 </table>  
 </td>
 </tr>
 <tr><td align="right">
 <br />  
	  	</td></tr>
	 </table>
 
  
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  
    <tr>
     <td align="center" colspan="2">
     
		<display:table name="searchResult" pagesize="20" requestURI="/UserSearchDispatcAction.do?method=policySearch&pagination=true"  class="table" uid="row" id="record" >
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		<logic:notEqual name="userSearchFormBean" property="searchOption" value="Policy No">
		<display:column sortable="true" style="width: 5%" title='<input type="checkbox" name="choosen" onclick="checkAll(this);"/>'  media="html">
   		<bean:define id="checkStatus" name="record" property="STATUS"/>
   		<logic:equal name="record" property="MANUAL_UPDATE" value="NOT REALIZED">
   		<input type="checkbox" name="checkbox${record.RECEIPT_SL_NO}" value="${record.checkStatus}" <%=checkStatus.toString().trim().equalsIgnoreCase("Y")?"checked=checked":""%>  onclick="checkOne(this)"/>
   		</logic:equal>
   		</display:column>
		</logic:notEqual>
	 	<display:column sortable="true" style="text-align:center;width:100px" title="Policy No" property="POLICY_NO" />
	 	<display:column style="text-align:center;width:80px" sortable="true" title="Bank Name" property="BANK_NAME"  />
	 	<display:column style="text-align:center;width:80px" sortable="true" title="Receipt No" property="RECEIPT_NO"/>
	 	<display:column style="text-align:center;width:80px" sortable="true" title="Cheque No" property="CHEQUE_NO" />
		<display:column style="text-align:center;width:170px" sortable="true" title="Cheque Amt" property="CHEQUE_AMT"  />
	    <display:column sortable="true" style="text-align:center;width:100px" title="Cheque Status" property="CHEQUE_STATUS" />
	    <display:column sortable="true" style="text-align:center;width:100px" title="Cheque Date" property="CHEQUE_DATE" />
	    <display:column sortable="true" style="text-align:center;width:100px" title="Realized Date" property="REALIZED_DATE" />
	    <display:column sortable="true" style="text-align:center;width:100px" title="Return Reason" property="RETURN_REASON" />
	    <display:column sortable="true" style="text-align:center;width:100px" title="Remarks" property="MANUAL_REMARKS" />
	    
	    </display:table>
	    
	</td>
    
    </tr>
    <tr style="height:10px">
    <td></td>
    </tr>
    <tr>
		<td colspan="3" align="center"> <br />
			<html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage()" />
			<logic:notEqual name="userSearchFormBean" property="searchOption" value="Policy No">
			<html:submit styleClass="tbut" value="Submit" style="width:65px" onclick=" return manualRealization(); "/>
		    </logic:notEqual>
		</td>
		
	</tr>
    </table>
    <br /><br />
</logic:equal>

<logic:equal name="PartToShow" value="RealizedList">

<table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="10" align="left">MANUALLY REALIZED LIST</td></tr> 
 </table>   
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  
    <tr>
     <td align="center" colspan="2">
     
		<display:table name="realizedList" pagesize="20" requestURI="/UserSearchDispatcAction.do?method=getManulRealizedList" excludedParams="method"   class="table" uid="row" id="record" >
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		<logic:notEqual name="userSearchFormBean" property="searchOption" value="Transaction No">
	 	<display:column sortable="true" style="text-align:center;width:100px" title="Policy No" property="POLICY_NO" />
	 	</logic:notEqual>
	 	<display:column style="text-align:center;width:80px" sortable="true" title="Bank Name" property="BANK_NAME"  />
	 	<display:column style="text-align:center;width:80px" sortable="true" title="Receipt No" property="RECEIPT_NO"/>
	 	<display:column style="text-align:center;width:80px" sortable="true" title="Cheque No" property="CHEQUE_NO" />
		<display:column style="text-align:center;width:170px" sortable="true" title="Cheque Amt" property="CHEQUE_AMT"  />
	    <display:column sortable="true" style="text-align:center;width:100px" title="Cheque Status" property="CHEQUE_STATUS" />
	    
	    <display:column sortable="true" style="text-align:center;width:100px" title="Cheque Date" property="CHEQUE_DATE" />
	    
	    
	    <display:column style="text-align:center;width:50px" title="Manual Realiztion" sortable="true" >
	      
	   <logic:equal name="record" property="MANUAL_UPDATE" value="REALIZED">
	      Realized 
	    </logic:equal>
	    </display:column>
	    </display:table>
	    
	</td>
    </tr>
    <tr style="height:10px">
    <td></td>
    </tr>
    <tr>
		<td colspan="3" align="center"> <br />
			<html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage()" />
			
		</td>
		
	</tr>
    </table>
</logic:equal>
<logic:equal name="PartToShow" value="ReceiptTransactions" >
<table border="0" cellpadding="0" cellspacing="4" width="100%">
 <tr>
<td align="center" ><span class="tb"><bean:write property="searchOption" name="userSearchFormBean"/>: </span>&nbsp;&nbsp;<bean:write property="searchValue" name="userSearchFormBean"/></td>
</tr>
 <tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;RECEIPT TRANSACTIONS</td></tr> 
   <tr>
    <td align="center" colspan="2">
		<display:table name="details" pagesize="10" requestURI="/transaction.do?method=receiptTransactions" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	
	 	<display:column style="text-align:center;" sortable="true" title="Transaction No" class="formtxtc" >
	 	<a >
					<bean:write name="record"  property="transactionNo" />
			</a>
		</display:column>
	 	<display:column style="text-align:center;" sortable="true" title="Upload Date" property="transdate" class="formtxtc" />
	 	<display:column style="text-align:center;" sortable="true" title="Valid Records"  class="formtxtc" >
	 	
		<bean:define id="exists" name="record" property="exists"/>
		<%if(exists.toString().equalsIgnoreCase("0")) {%>
		${record.totalRecords}
		<%} else
		{%><font color="blue" ><a onclick='return countReceipt(${record.transactionNo})' style="cursor: pointer;">${record.totalRecords}</a></font>
	 	<%} %>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Invalid Records"  class="formtxtc" >
			<a onclick="return invalidReceipt('${record.transactionNo}');" style="cursor: pointer;">
					<bean:write name="record" property="invalid" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Duplicates"  class="formtxtc" >
			<a onclick="return duplicatesReceipt('${record.transactionNo}');" style="cursor: pointer;">
				<bean:write name="record" property="duplicates" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="PYMT Type Records"  class="formtxtc" >
			<a onclick="return paymentReceipt('${record.transactionNo}');" style="cursor: pointer;">
				<bean:write name="record" property="paymentRecords" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Reversal Records"  class="formtxtc" >
				<a onclick="return reversalReceipts('${record.transactionNo}');" style="cursor: pointer;">
					<bean:write name="record" property="reversals" />
			     </a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Cheque No. Exists" property="chequeexists" class="formtxtc"/>
		
		<display:column sortable="true" style="text-align:center;" title="Cheque No. Not Exists" class="formtxtc">
		<a onclick="return nochequesReceipt('${record.transactionNo}');" style="cursor: pointer;">
				<bean:write name="record" property="chequenotexists" />
			</a>
	     </display:column>
		<display:column sortable="true" style="text-align:center;" title="Matched" class="formtxtc">
		<bean:define id="transactionNo" name="record" property="transactionNo" />
			<a onclick="return callReceiptMatched('<%=transactionNo %>')" style="cursor: pointer;">
				<bean:write name="record" property="matched" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Pending" class="formtxtc">
		<bean:define id="transactionNo2" name="record" property="transactionNo2" />
			<a onclick="return callReceiptPending('<%=transactionNo2 %>')" style="cursor: pointer;">
				<bean:write name="record" property="pending" />
			</a></display:column>
			
		</display:table>
	</td>
    
    </tr>
    
    <tr style="height:50px">
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
    <bean:write property="transactionNo" name="userSearchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
    
		<display:table name="pendingList" pagesize="20" requestURI="/UserSearchDispatcAction.do?method=goPending&pagination=true" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
		<display:column sortable="true" style="width: 5%" title='<input type="checkbox" name="choosen" onclick="checkAll(this);"/>'  media="html">
   		<bean:define id="checkStatus" name="record" property="checkStatus"/>
   		
   		<input type="checkbox" name="checkbox${record.receiptNo}" value="${record.checkStatus}" <%=checkStatus.toString().trim().equalsIgnoreCase("Y")?"checked=checked":""%>  onclick="checkOne(this)"/>
   		</display:column>
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
		
		</display:table>
	</td>
   
    </tr>
    
    <tr style="height:10px">
    <td></td>
    </tr>

<logic:notEqual name="PartToShow1" value="Bank" >
  <tr>
    
	<td align="center">
	<html:submit styleClass="tbut" value="Submit" style="width:65px" onclick=" return manualRealization(); "/>
	<html:submit styleClass="tbut" value="Back" style="width:65px" onclick="window.history.back();" /></td>
	
	</tr>
</logic:notEqual>

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
    <bean:write property="transactionNo" name="userSearchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="receiptInvalidsResult" pagesize="15" requestURI="/UserSearchDispatcAction.do?method=getInvalids" class="table" uid="row" id="record" export="true">
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
<logic:equal name="PartToShow" value="ReceiptDuplicatesResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">DUPLICATE RECEIPT DETAILS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="userSearchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="receiptDuplicatesResult" pagesize="15" requestURI="/UserSearchDispatcAction.do?method=getDuplicates" class="table" uid="row" id="record" export="true">
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
<logic:equal name="PartToShow" value="ReceiptReversalsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">RECEIPT REVERSALS DETAILS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="userSearchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="receiptReversalsResult" pagesize="15" requestURI="/UserSearchDispatcAction.do?method=getReversals" class="table" uid="row" id="record" export="true">
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
<logic:equal name="PartToShow" value="ReceiptNochequesResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">CHEQUE NO. NOT EXISTS RECEIPTS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="userSearchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   
	
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="75%" align="center">
  <tr>
     <td align="center" colspan="2">
		<display:table name="receiptNochequesResult" pagesize="15" requestURI="/UserSearchDispatcAction.do?method=getNocheques" class="table" uid="row" id="record" >
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
	<td align="center"><html:submit styleClass="tbut" value="Back" style="width:65px" onclick="window.history.back();" /></td>
   </tr>



    </table>
<br />
</logic:equal>

<logic:equal name="PartToShow" value="RealisedTransactionResult">

 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="10" align="left">MATCHED RECORDS</td></tr> 
   <tr style="height:15px">
    <td></td>
    </tr>
    <tr><td colspan="2"  height="10" align="left"> Transaction No:&nbsp;<bean:write name="userSearchFormBean" property="transactionNo"/>
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
		<display:table name="realisedTransactionResult" pagesize="10" requestURI="/UserSearchDispatcAction.do?method=goRealised" class="table" uid="row" id="record">
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
		<bean:define id="receiptNo" name="record" property="receiptNo" />
		<bean:define id="receipt" name="record" property="receipt" />
		
		<a href="#" name="${record.receiptNo}" onclick="return callPopup(this.name);"  style="cursor: pointer;" ><%=receipt %></a>   
		</display:column>
		</logic:equal>
		<logic:notEqual name="PartToShow1" value="Bank" >
		<display:column style="text-align:center;width:130px" sortable="true" title="Receipt No" class="formtxtc" >
		<bean:define id="receiptNo" name="record" property="receiptNo" />
		<bean:define id="receipt" name="record" property="receipt" />
		
		<a href="#" name="${record.receiptNo}" onclick="return callPopup(this.name);"  style="cursor: pointer;" ><%=receipt %></a>   
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
<logic:equal name="PartToShow" value="ReceiptPaymentsResult">
 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="20" align="left">RECEIPT PAYMENT DETAILS</td></tr> 
  	
   <tr style="height:15px">
    <td> &nbsp;</td>
    </tr>
    <tr><td colspan="2"  height="10" align="left">
    Transaction No:&nbsp; 
    <bean:write property="transactionNo" name="userSearchFormBean"/>
   &nbsp;&nbsp;&nbsp;
   
    </td></tr> 

</table>  
 <br />
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  <tr>
     <td align="center" colspan="2">
		<display:table name="receiptPaymentsResult" pagesize="15" requestURI="/UserSearchDispatcAction.do?method=getReceiptPayments" class="table" uid="row" id="record" export="false">
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






<html:hidden property="method"  value="policySearch"/>
<html:hidden property="searchValue"/>
<html:hidden property="searchOption"/>
<html:hidden property="transactionNo"/>
<html:hidden property="bankId" />
<html:hidden property="searchIn"/>
<html:hidden property="realised" />

</html:form>
<script><!--

function cancelPage(){
window.location.href='<%=request.getContextPath() %>/UserSearchDispatcAction.do?method=policysearch';

	return false;
}
function manualRealization()
{
  
  document.userSearchFormBean.method.value="manualRealization";
  
  document.userSearchFormBean.submit();
  
} 
function setValue(value,id)
{
	if(value=='Y')
	{
		document.getElementById("realisedPro~"+id).value=id;
	}
}
function  ajaxCheck(receiptNo,checkYN)
{
       
	   if (window.XMLHttpRequest)
	   {
	 	  xmlhttp=new XMLHttpRequest();
	   } 	
	   else
	   {
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	   }
	   xmlhttp.onreadystatechange=function()
		{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
		}
		}
		xmlhttp.open("POST",'<%=request.getContextPath()%>/UserSearchDispatcAction.do?method=ajaxCheck&receiptNo='+receiptNo+'&checkYN='+checkYN,true);
		xmlhttp.send();
}

/*function(val)
{
 document.userSearchFormBean.search.value=val;
 //document.userSearchFormBean.method.value="policySearch";
 document.userSearchFormBean.action="/UserSearchDispatcAction.do?method=policySearch";
  document.userSearchFormBean.submit();
}*/

 function countReceipt(transId)
  {
 
   document.userSearchFormBean.method.value = "getRecordsCount";
   document.userSearchFormBean.transactionNo.value = transId;
   document.userSearchFormBean.bankId.value= "R";
   document.userSearchFormBean.submit();
  }
  
function callReceiptPending(val1){

		document.userSearchFormBean.method.value = "goPending";
    	document.userSearchFormBean.searchIn.value = "Receipt";
    	document.userSearchFormBean.transactionNo.value = val1;
    	document.userSearchFormBean.realised.value = "no";
    	document.userSearchFormBean.bankId.value = "";
		document.userSearchFormBean.submit();	
 }  
 function callReceiptMatched(val1){
 
		document.userSearchFormBean.method.value = "goRealised";
      	document.userSearchFormBean.searchIn.value = "Receipt";
      	document.userSearchFormBean.transactionNo.value = val1;
      	document.userSearchFormBean.realised.value = "yes";
      	document.userSearchFormBean.bankId.value = "";
		document.userSearchFormBean.submit();	
   }
 function nochequesReceipt(transId)
  {
  		document.userSearchFormBean.method.value = "goNoCheques";
  		document.userSearchFormBean.transactionNo.value = transId;
      	document.userSearchFormBean.bankId.value= "";
      	document.userSearchFormBean.submit();
  }
  
 function invalidReceipt(transId)
  {
  		document.userSearchFormBean.method.value = "goInvalids";
  		document.userSearchFormBean.transactionNo.value = transId;
      	document.userSearchFormBean.bankId.value= "";
      	document.userSearchFormBean.submit();
  }
  
  function reversalReceipts(transId)
  {
  		document.userSearchFormBean.method.value = "goReversals";
  		document.userSearchFormBean.transactionNo.value = transId;
      	document.userSearchFormBean.bankId.value= "";
      	document.userSearchFormBean.submit();
  }
  
   function paymentReceipt(transId)
  {
  		document.userSearchFormBean.method.value = "goReceiptPayments";
  		document.userSearchFormBean.transactionNo.value = transId;
      	document.userSearchFormBean.bankId.value= "";
      	document.userSearchFormBean.submit();
  } 
  function duplicatesReceipt(transId)
  {
  		document.userSearchFormBean.method.value = "goDuplicates";
  		document.userSearchFormBean.transactionNo.value = transId;
      	document.userSearchFormBean.bankId.value= "";
      	document.userSearchFormBean.submit();
  }
  function checkAll(object)
{
    
	var elements=document.userSearchFormBean.elements;
	var tot=0;
	var checkStatus;
	
	for(i=0; i<elements.length; i++)
		{
			var obj=document.userSearchFormBean.elements[i];
			var objName=document.userSearchFormBean.elements[i].name;
			if(obj.type=='checkbox')
			{
				if(object.checked==true)
				{
				obj.checked=true;
				checkStatus='Y';
				}
				else
				{
				obj.checked=false;
				checkStatus='N';
				}
	       if (window.XMLHttpRequest)
	        {
	 	      xmlhttp=new XMLHttpRequest();
	         } 	
	       else
	      {
		   xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	       }
	
	    xmlhttp.open("POST",'<%=request.getContextPath()%>/UserSearchDispatcAction.do?method=ajaxCheck&checkYN='+checkStatus+'&name='+objName,true);
		xmlhttp.send();
	   		}
		  
		}

}

function checkOne(object)
{
   
    var checkStatus;
    var name=object.name;
    if(object.checked==true)
	{
	object.checked=true;
	checkStatus='Y';
	}
	else
	{
	object.checked=false;
	checkStatus='N';
	}
			
			
	  if (window.XMLHttpRequest)
	   {
	 	  xmlhttp=new XMLHttpRequest();
	   } 	
	   else
	   {
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	   }
	   if(object.type=='checkbox')
	   {
	    xmlhttp.open("POST",'<%=request.getContextPath()%>/UserSearchDispatcAction.do?method=ajaxCheck&checkYN='+checkStatus+'&name='+object.name,true);
		xmlhttp.send();
		}	
		
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
   		
--></script>
   
  </body>
</html:html>
