<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html locale="true">
<head>
<title>TransactionDetails</title>
<script>

function callpage(val)
{      
      var pts="<%=request.getAttribute("PartToShow")%>";
	    if("CitiTransactions"==pts ){
        var method;
		var initIndex,endIndex;	
		var page;
		var v = val.href;
		initIndex = v.indexOf("d-446779-p=");
		endIndex = v.indexOf("&", initIndex);
		if(initIndex!=-1)
		{
			page = v.substring(initIndex,endIndex+1);
		}
		method="citiTransactions";
	    document.transactionform.Pagination.value="Y";
		
		document.transactionform.action="<%=request.getContextPath() %>/transaction.do?"+page+"method="+method;
        document.transactionform.submit();
		return false;
	  }
	   else if ("HDFCTransactions"==pts){
	 
	   var method;
		var initIndex,endIndex;	
		var page;
		var v = val.href;
		initIndex = v.indexOf("d-446779-p=");
		endIndex = v.indexOf("&", initIndex);
		if(initIndex!=-1)
		{
			page = v.substring(initIndex,endIndex+1);
		}
		method="hdfcTransactions";
	    document.transactionform.Pagination.value="Y";
		
		document.transactionform.action="<%=request.getContextPath() %>/transaction.do?"+page+"method="+method;
        document.transactionform.submit();
		return false;
	  }
	  else if ("SCBTransactions"==pts){
	 
	   var method;
		var initIndex,endIndex;	
		var page;
		var v = val.href;
		initIndex = v.indexOf("d-446779-p=");
		endIndex = v.indexOf("&", initIndex);
		if(initIndex!=-1)
		{
			page = v.substring(initIndex,endIndex+1);
		}
		method="scbTransactions";
	    document.transactionform.Pagination.value="Y";
		
		document.transactionform.action="<%=request.getContextPath() %>/transaction.do?"+page+"method="+method;
        document.transactionform.submit();
		return false;
	  }
	   else if ("AXBTransactions"==pts){
	 
	   var method;
		var initIndex,endIndex;	
		var page;
		var v = val.href;
		initIndex = v.indexOf("d-446779-p=");
		endIndex = v.indexOf("&", initIndex);
		if(initIndex!=-1)
		{
			page = v.substring(initIndex,endIndex+1);
		}
		method="axisTransactions";
	    document.transactionform.Pagination.value="Y";
		
		document.transactionform.action="<%=request.getContextPath() %>/transaction.do?"+page+"method="+method;
        document.transactionform.submit();
		return false;
	  }
}
function checkAll(object)
{
	var elements=document.transactionform.elements;
	var tot=0;
	for(i=0; i<elements.length; i++)
		{
			var obj=document.transactionform.elements[i];
			if(obj.type=='checkbox')
			{
				if(object.checked==true)
				obj.checked=true;
				else
				obj.checked=false;
			}
		}
	
}
</script>
</head>
<body  >
<div align="center">
<font color="red"> <html:errors /> </font>
</div>
<html:form action="/transaction.do">

<logic:equal name="PartToShow" value="ReceiptTransactions" >
<table border="0" cellpadding="0" cellspacing="0" width="100%">
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

<logic:equal name="PartToShow" value="CitiTransactions" >
<div id="uploadsts1" style="display:none">
	<table align="center">
	
		<tr align="center">
			<td colspan="2"><font color="#488AC7" size="4pt"><b>Processing..</b></font></td>
		</tr>
		
		<tr>
		<input type="hidden" name="Pagination" />
			<td align="center"><img src="<%=request.getContextPath()%>/images/loader.gif" alt="Upload In Progress" /> </td>
		</tr>
	</table>
</div>

<table border="0" cellpadding="4" cellspacing="0" width="100%">

<logic:equal name="partToShowStatus" value="${requestScope.partToShowStatus}">
<tr><br/><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;CITI BANK TRANSACTIONS</td></tr>
<tr>
<td align="right" >
<logic:equal name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Processed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=citiTransactions&status=processed';document.transactionform.submit();"/>
</logic:equal>
<logic:notEqual name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Unprocessed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=citiTransactions&status=Unprocessed';document.transactionform.submit();"/>
</logic:notEqual>
</td>
</tr>
   <tr>
     <td align="center" colspan="2">
		<display:table name="${requestScope.partToShowStatus}" pagesize="10" requestURI="/transaction.do?method=citiTransactions" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	<logic:equal name="partToShowStatus" value="Unprocessed">
	 	<display:column sortable="true" style="width: 5%" title='<input type="checkbox" name="choosen" onclick="checkAll(this);"/>'  media="html">
   		<bean:define id="checkStatus" name="record" property="checkStatus"/>
   		
   		<bean:define id="processed" name="record" property="processed"/>
   		<input type="checkbox" name="checkbox${record.transactionNo}" value="${record.checkStatus}" <%=checkStatus.toString().trim().equalsIgnoreCase("Y")?"checked=checked":""%>  />
   		<!--<input type="hidden" name="hidden${record.transactionNo}" value="${record.checkStatus}"/> 
        --></display:column>
        </logic:equal>
        
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
		{%><font color="blue" ><a onclick='return countCiti(${record.transactionNo})' style="cursor: pointer;">${record.totalRecords}</a></font>
	 	<%} %>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Invalid Records"  class="formtxtc" >
				<a onclick="return invalidsCiti('${record.transactionNo}');" style="cursor: pointer;">
			
				<bean:write name="record" property="invalid" />
				</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Duplicates"  class="formtxtc" >
			<a onclick="return duplicatesCiti('${record.transactionNo}');" style="cursor: pointer;">
				<bean:write name="record" property="duplicates" />
			</a>
		</display:column><display:column sortable="true" style="text-align:center;" title="Cheque No. Exists" property="chequeexists" class="formtxtc"/>
		<display:column sortable="true" style="text-align:center;" title="Cheque No. Not Exists" class="formtxtc">
		<a onclick="return nochequesCiti('${record.transactionNo}');" style="cursor: pointer;">
				<bean:write name="record" property="chequenotexists" />
			</a>
	
		</display:column>
		
		<display:column sortable="true" style="text-align:center;" title="Reversal Records"  class="formtxtc" >
				<a onclick="return reversalsCiti('${record.transactionNo}');" style="cursor: pointer;">
			
				<bean:write name="record" property="reversals" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Matched" class="formtxtc">
		<bean:define id="transactionNo" name="record" property="transactionNo" />
			<a onclick="return callMatched('<%=transactionNo %>','CIT')" style="cursor: pointer;">
				<bean:write name="record" property="matched" />
			</a></display:column>
		
		<display:column sortable="true" style="text-align:center;" title="Pending" class="formtxtc">
		<bean:define id="transactionNo2" name="record" property="transactionNo2" />
			<a onclick="return callPending('<%=transactionNo2 %>','CIT')" style="cursor: pointer;">
				<bean:write name="record" property="pending" />
			</a></display:column>
		<display:column style="text-align:center;width:50px" title="Action" sortable="true" >
		<html:hidden property="processed" name="record" />	
		<bean:define id="processed" name="record" property="processed"/>
		<%if(processed.toString().equalsIgnoreCase("Y")) {%>
		<font color="blue" >Processed</font>
		<%} else
		{%>
		<logic:equal name="record" property="processed" value="P">
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processCiti('${record.transactionNo}');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
		</logic:equal> 
		<logic:greaterThan name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
	    <html:button  property="${record.transactionNo}" value="Process" styleId="start" onclick="return processCiti('${record.transactionNo}');"  style="background-color:red;" disabled='${transactionform.processCount>0}'/>
		</logic:equal>
		</logic:greaterThan>
		<logic:equal name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processCiti('${record.transactionNo}');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
		</logic:equal>
		</logic:equal>
	 	<%} %>
		
	 	</display:column>
		</display:table>
		<input type="hidden" name="status" value="${requestScope.status}"/>
	</td>
    
    </tr>
    
    <tr style="height:50px" align="center">
    <td> <logic:notEmpty name="Unprocessed"><html:button property="process2" value="Process Selected Records" onclick="return processCiti2();" styleClass="tbut" styleId="startAll" disabled="${transactionform.processCount>0}"/> 
    </logic:notEmpty>
	 </td>
    </tr>
    </logic:equal>
    </table>
</logic:equal>


<logic:equal name="PartToShow" value="AXBTransactions" >
<div id="uploadsts4" style="display:none">
	<table align="center">
	
		<tr align="center">
			<td colspan="2"><font color="#488AC7" size="4pt"><b>Processing..</b></font></td>
		</tr>
		
		<tr>
		<input type="hidden" name="Pagination" />
			<td align="center"><img src="<%=request.getContextPath()%>/images/loader.gif" alt="Upload In Progress" /> </td>
		</tr>
	</table>
</div>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;AXIS BANK TRANSACTIONS</td></tr> 
 <tr>
<td align="right" >
<logic:equal name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Processed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=axisTransactions&status=processed';document.transactionform.submit();"/>
</logic:equal>
<logic:notEqual name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Unprocessed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=axisTransactions&status=Unprocessed';document.transactionform.submit();"/>
</logic:notEqual>
</td>
</tr>
 
    <tr>
     <td align="center" colspan="2">
		<display:table name="${requestScope.partToShowStatus}" pagesize="10" requestURI="/transaction.do?method=axisTransactions" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	<logic:equal name="partToShowStatus" value="Unprocessed">
	 	<display:column sortable="true" style="width: 5%" title='<input type="checkbox" name="choosen" onclick="checkAll(this);"/>'  media="html">
   		<bean:define id="checkStatus" name="record" property="checkStatus"/>
   		
   		<input type="checkbox" name="checkbox${record.transactionNo}" value="${record.checkStatus}" <%=checkStatus.toString().trim().equalsIgnoreCase("Y")?"checked=checked":""%>  />
   		<!--<input type="hidden" name="hidden${record.transactionNo}" value="${record.checkStatus}"/> 
        --></display:column>
        </logic:equal>
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
		{%><font color="blue" ><a onclick='return countAXB(${record.transactionNo})' style="cursor: pointer;">${record.totalRecords}</a></font>
	 	<%} %>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Invalid Records"  class="formtxtc" >
				<a onclick="return invalidsAXB('${record.transactionNo}');" style="cursor: pointer;">
			
				<bean:write name="record" property="invalid" />
				</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Duplicates"  class="formtxtc" >
			<a onclick="return duplicatesAXB('${record.transactionNo}');" style="cursor: pointer;">
				<bean:write name="record" property="duplicates" />
			</a>
		</display:column><display:column sortable="true" style="text-align:center;" title="Cheque No. Exists" property="chequeexists" class="formtxtc"/>
		<display:column sortable="true" style="text-align:center;" title="Cheque No. Not Exists" class="formtxtc">
		<a onclick="return nochequesAXB('${record.transactionNo}');" style="cursor: pointer;">
				<bean:write name="record" property="chequenotexists" />
			</a>
	
		</display:column>
		
		<display:column sortable="true" style="text-align:center;" title="Reversal Records"  class="formtxtc" >
				<a onclick="return reversalsAXB('${record.transactionNo}');" style="cursor: pointer;">
			
				<bean:write name="record" property="reversals" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Matched" class="formtxtc">
		<bean:define id="transactionNo" name="record" property="transactionNo" />
			<a onclick="return callMatched('<%=transactionNo %>','AXB')" style="cursor: pointer;">
				<bean:write name="record" property="matched" />
			</a></display:column>
		
		<display:column sortable="true" style="text-align:center;" title="Pending" class="formtxtc">
		<bean:define id="transactionNo2" name="record" property="transactionNo2" />
			<a onclick="return callPending('<%=transactionNo2 %>','AXB')" style="cursor: pointer;">
				<bean:write name="record" property="pending" />
			</a></display:column>
		<display:column style="text-align:center;width:50px" title="Action" sortable="true" >
		<html:hidden property="processed" name="record" />	
		<bean:define id="processed" name="record" property="processed"/>
		<%if(processed.toString().equalsIgnoreCase("Y")) {%>
		<font color="blue" >Processed</font>
		<%} else
		{%>
		<logic:equal name="record" property="processed" value="P">
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processAXB('${record.transactionNo}');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
		</logic:equal> 
		<logic:greaterThan name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
	    <html:button  property="${record.transactionNo}" value="Process" styleId="start" onclick="return processAXB('${record.transactionNo}');"  style="background-color:red;" disabled='${transactionform.processCount>0}'/>
		</logic:equal>
		</logic:greaterThan>
		<logic:equal name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processAXB('${record.transactionNo}');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
		</logic:equal>
		</logic:equal> 
	 	<%} %>
		
	 	</display:column>
		
		</display:table>
		<input type="hidden" name="status" value="${requestScope.status}"/>
	</td>
    
    </tr>
    
    <tr style="height:50px" align="center">
    <td> <logic:notEmpty name="Unprocessed"><html:button property="process2" value="Process Selected Records" onclick="return processAXB2();" styleId="startAll" styleClass="tbut"/> 
    </logic:notEmpty>
	 </td>
    </tr>
    
    </table>

</logic:equal>






<logic:equal name="PartToShow" value="SCBTransactions" >
<div id="uploadsts3" style="display:none">
	<table align="center">
	
		<tr align="center">
			<td colspan="2"><font color="#488AC7" size="4pt"><b>Processing..</b></font></td>
		</tr>
		<input type="hidden" name="Pagination" />
		<tr>
			<td align="center"><img src="<%=request.getContextPath()%>/images/loader.gif" alt="Upload In Progress" /> </td>
		</tr>
	</table>
</div>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;SCB BANK TRANSACTIONS</td></tr> 
 <tr>
<td align="right" >
<logic:equal name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Processed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=scbTransactions&status=processed';document.transactionform.submit();"/>
</logic:equal>
<logic:notEqual name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Unprocessed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=scbTransactions&status=Unprocessed';document.transactionform.submit();"/>
</logic:notEqual>
</td>
</tr>
 
    <tr>
    <td align="center" colspan="2">
		<display:table name="${requestScope.partToShowStatus}" pagesize="10" requestURI="/transaction.do?method=scbTransactions" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	
	 		<logic:equal name="partToShowStatus" value="Unprocessed">
	 	<display:column sortable="true" style="width: 5%" title='<input type="checkbox" name="choosen" onclick="checkAll(this);"/>'  media="html">
   		<bean:define id="checkStatus" name="record" property="checkStatus"/>
   		
   		<input type="checkbox" name="checkbox${record.transactionNo}" value="${record.checkStatus}" <%=checkStatus.toString().trim().equalsIgnoreCase("Y")?"checked=checked":""%>  />
   		<!--<input type="hidden" name="hidden${record.transactionNo}" value="${record.checkStatus}"/> 
        --></display:column>
        </logic:equal>
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
		{%><font color="blue" ><a onclick='return countSCB(${record.transactionNo})' style="cursor: pointer;">${record.totalRecords}</a></font>
	 	<%} %>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Invalid Records"  class="formtxtc" >
				<a onclick="return invalidsSCB('${record.transactionNo}');" style="cursor: pointer;">
							<bean:write name="record" property="invalid" />
				</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Duplicates"  class="formtxtc" >
			<a onclick="return duplicatesSCB('${record.transactionNo}');" style="cursor: pointer;">
				<bean:write name="record" property="duplicates" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Cheque No. Exists" property="chequeexists" class="formtxtc"/>
		<display:column sortable="true" style="text-align:center;" title="Cheque No. Not Exists" class="formtxtc">
		<a onclick="return nochequesSCB('${record.transactionNo}');" style="cursor: pointer;">
				<bean:write name="record" property="chequenotexists" />
			</a>
	
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Reversal Records"  class="formtxtc" >
				<a onclick="return reversalsSCB('${record.transactionNo}');" style="cursor: pointer;">
				
				<bean:write name="record" property="reversals" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Matched" class="formtxtc">
		<bean:define id="transactionNo" name="record" property="transactionNo" />
			<a onclick="return callMatched('<%=transactionNo %>','SCB')" style="cursor: pointer;">
				<bean:write name="record" property="matched" />
			</a></display:column>
		<display:column sortable="true" style="text-align:center;" title="Pending" class="formtxtc">
		<bean:define id="transactionNo2" name="record" property="transactionNo2" />
			<a onclick="return callPending('<%=transactionNo2 %>','SCB')" style="cursor: pointer;">
				<bean:write name="record" property="pending" />
			</a></display:column>
		<display:column style="text-align:center;width:50px" title="Action" sortable="true" >
		<html:hidden property="processed" name="record" />	
		<bean:define id="processed" name="record" property="processed"/>
		<%if(processed.toString().equalsIgnoreCase("Y")) {%>
		<font color="blue" >Processed</font>
		<%} else
		{%>
		<logic:equal name="record" property="processed" value="P">
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processSCB('${record.transactionNo}');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
		</logic:equal> 
		<logic:greaterThan name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
	    <html:button  property="${record.transactionNo}" value="Process" styleId="start" onclick="return processSCB('${record.transactionNo}');"  style="background-color:red;" disabled='${transactionform.processCount>0}'/>
		</logic:equal>
		</logic:greaterThan>
		<logic:equal name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processSCB('${record.transactionNo}');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
		</logic:equal>
		</logic:equal>  
	 	<%} %>
		
	 	</display:column>
		</display:table>
				<input type="hidden" name="status" value="${requestScope.status}"/>
	</td>
    
    </tr>
    
    <tr style="height:50px" align="center">
    <td>
    <logic:notEmpty name="Unprocessed">
    <html:button property="process2" value="Process Selected Records" onclick="return processSCB2();" styleId="startAll" styleClass="tbut"/> 
    </logic:notEmpty>
	 </td>
    </tr>
    </table>
</logic:equal>

<logic:equal name="PartToShow" value="HSBCTransactions" >
<div id="uploadsts2" style="display:none">
    <table align="center">
    
        <tr align="center">
            <td colspan="2"><font color="#488AC7" size="4pt"><b>Processing..</b></font></td>
        </tr>
        <input type="hidden" name="Pagination" />
        <tr>
            <td align="center"><img src="<%=request.getContextPath()%>/images/loader.gif" alt="Upload In Progress" /> </td>
        </tr>
    </table>
</div>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;HSBC BANK TRANSACTIONS</td></tr>

<tr>
<td align="right" >
<logic:equal name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Processed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=hsbcTransactions&status=processed';document.transactionform.submit();"/>
</logic:equal>
<logic:notEqual name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Unprocessed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=hsbcTransactions&status=Unprocessed';document.transactionform.submit();"/>
</logic:notEqual>
</td>
</tr>
 
    <tr>
    <td align="center" colspan="2">
        <display:table name="${requestScope.partToShowStatus}" pagesize="10" requestURI="/transaction.do?method=hsbcTransactions" class="table" uid="row" id="record">
        <display:setProperty name="paging.banner.one_item_found" value="" />
        <display:setProperty name="paging.banner.one_items_found" value="" />
        <display:setProperty name="paging.banner.all_items_found" value="" />
        <display:setProperty name="paging.banner.some_items_found" value="" />
        <display:setProperty name="paging.banner.placement" value="bottom" />
        <display:setProperty name="paging.banner.onepage" value="" />
        <logic:equal name="partToShowStatus" value="Unprocessed">
        <display:column sortable="true" style="width: 5%" title='<input type="checkbox" name="choosen" onclick="checkAll(this);"/>'  media="html">
        <bean:define id="checkStatus" name="record" property="checkStatus"/>
        <input type="checkbox" name="checkbox${record.transactionNo}" value="${record.checkStatus}" <%=checkStatus.toString().trim().equalsIgnoreCase("Y")?"checked=checked":""%>  /><!--
        <input type="hidden" name="hidden${record.transactionNo}" value="${record.checkStatus}"/> 
        --></display:column>
        </logic:equal>
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
        {%><font color="blue" ><a onclick="return countHDB('${record.transactionNo}','HSB');" style="cursor: pointer;">${record.totalRecords}</a></font>
        <%} %>
        </display:column>
        <display:column sortable="true" style="text-align:center;" title="Invalid Records"  class="formtxtc" >
            <a onclick="return invalidsHDFC('${record.transactionNo}','HSB');" style="cursor: pointer;">
                        <bean:write name="record" property="invalid" />
            </a>
        </display:column>
        <display:column sortable="true" style="text-align:center;" title="Duplicates"  class="formtxtc" >
            <a onclick="return duplicatesHDB('${record.transactionNo}','HSB');" style="cursor: pointer;">
                <bean:write name="record" property="duplicates" />
            </a>
        </display:column>
        <display:column sortable="true" style="text-align:center;" title="Cheque No. Exists" property="chequeexists" class="formtxtc"/>
        <display:column sortable="true" style="text-align:center;" title="Cheque No. Not Exists" class="formtxtc">
        <a onclick="return nochequesHDFC('${record.transactionNo}','HSB');" style="cursor: pointer;">
                <bean:write name="record" property="chequenotexists" />
            </a>
        </display:column>
        <display:column sortable="true" style="text-align:center;" title="Reversal Records"  class="formtxtc" >
                <a onclick="return reversalsHDFC('${record.transactionNo}','HSB');" style="cursor: pointer;">
                <bean:write name="record" property="reversals" />
            </a>
        </display:column>
        <display:column sortable="true" style="text-align:center;" title="Matched" class="formtxtc">
        <bean:define id="transactionNo" name="record" property="transactionNo" />
            <a onclick="return callMatched('<%=transactionNo %>','HSB')" style="cursor: pointer;">
                <bean:write name="record" property="matched" />
            </a></display:column>
        <display:column sortable="true" style="text-align:center;" title="Pending" class="formtxtc">
        <bean:define id="transactionNo2" name="record" property="transactionNo2" />
            <a onclick="return callPending('<%=transactionNo2 %>','HSB')" style="cursor: pointer;">
                <bean:write name="record" property="pending" />
            </a></display:column>
        <display:column style="text-align:center;width:50px" title="Action" sortable="true" >
        <html:hidden property="processed" name="record" />  
        <bean:define id="processed" name="record" property="processed"/>
        <%if(processed.toString().equalsIgnoreCase("Y")) {%>
        <font color="blue" >Processed</font>
        <%} else
        {%>
        <logic:equal name="record" property="processed" value="P">
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processHDB('${record.transactionNo}','HSB');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
		</logic:equal> 
		<logic:greaterThan name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
	    <html:button  property="${record.transactionNo}" value="Process" styleId="start" onclick="return processHDB('${record.transactionNo}','HSB');"  style="background-color:red;" disabled='${transactionform.processCount>0}'/>
		</logic:equal>
		</logic:greaterThan>
		<logic:equal name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processHDB('${record.transactionNo}','HSB');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
		</logic:equal>
		</logic:equal>
         <%} %>
        </display:column>
        </display:table>
        
        		<input type="hidden" name="status" value="${requestScope.status}"/>
    </td>
    </tr>
    <tr style="height:50px" align="center">
    <td>
    <logic:notEmpty name="Unprocessed">
    <html:button property="process2" value="Process Selected Records" onclick="return processHDB2('HSB');" styleClass="tbut" styleId="startAll"/> 
    </logic:notEmpty>
     </td>
    </tr>
    </table>
</logic:equal>

<logic:equal name="PartToShow" value="HDFCTransactions" >
<div id="uploadsts2" style="display:none">
	<table align="center">
	
		<tr align="center">
			<td colspan="2"><font color="#488AC7" size="4pt"><b>Processing..</b></font></td>
		</tr>
		<input type="hidden" name="Pagination" />
		<tr>
			<td align="center"><img src="<%=request.getContextPath()%>/images/loader.gif" alt="Upload In Progress" /> </td>
		</tr>
	</table>
</div>
<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;HDFC BANK TRANSACTIONS</td></tr> 
 <tr>
<td align="right" >
<logic:equal name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Processed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=hdfcTransactions&status=processed';document.transactionform.submit();"/>
</logic:equal>
<logic:notEqual name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Unprocessed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=hdfcTransactions&status=Unprocessed';document.transactionform.submit();"/>
</logic:notEqual>
</td>
</tr>
 
    <tr>
    <td align="center" colspan="2">
		<display:table name="${requestScope.partToShowStatus}" pagesize="10" requestURI="/transaction.do?method=hdfcTransactions" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	<logic:equal name="partToShowStatus" value="Unprocessed">
	 	
	 	<display:column sortable="true" style="width: 5%" title='<input type="checkbox" name="choosen" onclick="checkAll(this);"/>'  media="html">
   		<bean:define id="checkStatus" name="record" property="checkStatus"/>
   		
   		<input type="checkbox" name="checkbox${record.transactionNo}" value="${record.checkStatus}" <%=checkStatus.toString().trim().equalsIgnoreCase("Y")?"checked=checked":""%>  />
   		<!--<input type="hidden" name="hidden${record.transactionNo}" value="${record.checkStatus}"/> 
        --></display:column>
        </logic:equal>
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
		{%><font color="blue" ><a onclick="return countHDB('${record.transactionNo}','HDB')" style="cursor: pointer;">${record.totalRecords}</a></font>
	 	<%} %>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Invalid Records"  class="formtxtc" >
				<a onclick="return invalidsHDFC('${record.transactionNo}','HDB');" style="cursor: pointer;">
							<bean:write name="record" property="invalid" />
				</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Duplicates"  class="formtxtc" >
			<a onclick="return duplicatesHDB('${record.transactionNo}','HDB');" style="cursor: pointer;">
				<bean:write name="record" property="duplicates" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Cheque No. Exists" property="chequeexists" class="formtxtc"/>
		<display:column sortable="true" style="text-align:center;" title="Cheque No. Not Exists" class="formtxtc">
		<a onclick="return nochequesHDFC('${record.transactionNo}', 'HDB');" style="cursor: pointer;">
				<bean:write name="record" property="chequenotexists" />
			</a>
	
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Reversal Records"  class="formtxtc" >
				<a onclick="return reversalsHDFC('${record.transactionNo}','HDB');" style="cursor: pointer;">
				
				<bean:write name="record" property="reversals" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Matched" class="formtxtc">
		<bean:define id="transactionNo" name="record" property="transactionNo" />
			<a onclick="return callMatched('<%=transactionNo %>','HDB')" style="cursor: pointer;">
				<bean:write name="record" property="matched" />
			</a></display:column>
		<display:column sortable="true" style="text-align:center;" title="Pending" class="formtxtc">
		<bean:define id="transactionNo2" name="record" property="transactionNo2" />
			<a onclick="return callPending('<%=transactionNo2 %>','HDB')" style="cursor: pointer;">
				<bean:write name="record" property="pending" />
			</a></display:column>
		<display:column style="text-align:center;width:50px" title="Action" sortable="true" >
		<html:hidden property="processed" name="record" />	
		<bean:define id="processed" name="record" property="processed"/>
		<%if(processed.toString().equalsIgnoreCase("Y")) {%>
		<font color="blue" >Processed</font>
		<%} else
		{%>
		<logic:equal name="record" property="processed" value="P">
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processHDB('${record.transactionNo}','HDB');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
		</logic:equal> 
		<logic:greaterThan name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
	    <html:button  property="${record.transactionNo}" value="Process" styleId="start" onclick="return processHDB('${record.transactionNo}','HDB');"  style="background-color:red;" disabled='${transactionform.processCount>0}'/>
		</logic:equal>
		</logic:greaterThan>
		<logic:equal name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processHDB('${record.transactionNo}','HDB');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
		</logic:equal>
		</logic:equal>
		<%} %>
		
	 	</display:column>
		</display:table>
		<input type="hidden" name="status" value="${requestScope.status}"/>
	</td>
    
    </tr>
    
    <tr style="height:50px" align="center">
    <td>
    <logic:notEmpty name="Unprocessed">
    <html:button property="process2" value="Process Selected Records" onclick="return processHDB2('HDB');" styleId="startAll" styleClass="tbut"/> 
    </logic:notEmpty>
	 </td>
    </tr>
    
    </table>

</logic:equal>



<logic:equal name="PartToShow" value="ReceiptRecords" >

<table border="0" cellpadding="0" cellspacing="0" width="60%" align="center">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;RECEIPT RECORDS DETAILS</td></tr> 
 
    <tr>
    <td align="center" colspan="2">
		&nbsp;
	</td>
    
    </tr>
     <tr>
    <td align="center" colspan="2">
		<display:table name="details" pagesize="10" requestURI="/transaction.do?method=getRecordsCount" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	
	 	<display:column style="text-align:center;" sortable="true" title="RECEIPT DATE" property="recorddates" class="formtxtc" />
	 	<display:column style="text-align:center;" sortable="true" title="NO. OF RECORDS" property="recordcounts" class="formtxtc" />
	 	
		</display:table>
	</td>
    
    </tr>
    
    <tr style="height:50px" align="center">
    <td><html:button property="back" value="Back" onclick="return goback();" styleClass="tbut"/> 
 
	 </td>
    </tr>
    
    </table>

 	
</logic:equal>

<logic:equal name="PartToShow" value="HSBCRecords" >

<table border="0" cellpadding="0" cellspacing="0" width="60%" align="center">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;HSBC BANK RECORDS DETAILS</td></tr> 
 
    <tr>
    <td align="center" colspan="2">
        &nbsp;
    </td>
    
    </tr>
     <tr>
    <td align="center" colspan="2">
        <display:table name="details" pagesize="10" requestURI="/transaction.do?method=getRecordsCount" class="table" uid="row" id="record">
        <display:setProperty name="paging.banner.one_item_found" value="" />
        <display:setProperty name="paging.banner.one_items_found" value="" />
        <display:setProperty name="paging.banner.all_items_found" value="" />
        <display:setProperty name="paging.banner.some_items_found" value="" />
        <display:setProperty name="paging.banner.placement" value="bottom" />
        <display:setProperty name="paging.banner.onepage" value="" />
        
        <display:column style="text-align:center;" sortable="true" title="DEPOSIT DATE" property="recorddates" class="formtxtc" />
        <display:column style="text-align:center;" sortable="true" title="NO. OF RECORDS" property="recordcounts" class="formtxtc" />
        
        </display:table>
    </td>
    
    </tr>
    
    <tr style="height:50px" align="center">
    <td><html:button property="back" value="Back" onclick="return goback();" styleClass="tbut"/> 
 
     </td>
    </tr>
    
    </table>
</logic:equal>

<logic:equal name="PartToShow" value="CitiRecords" >

<table border="0" cellpadding="0" cellspacing="0" width="60%" align="center">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;CITI BANK RECORDS DETAILS</td></tr> 
 
    <tr>
    <td align="center" colspan="2">
		&nbsp;
	</td>
    
    </tr>
     <tr>
    <td align="center" colspan="2">
		<display:table name="details" pagesize="10" requestURI="/transaction.do?method=getRecordsCount" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	
	 	<display:column style="text-align:center;" sortable="true" title="DEPOSIT DATE" property="recorddates" class="formtxtc" />
	 	<display:column style="text-align:center;" sortable="true" title="NO. OF RECORDS" property="recordcounts" class="formtxtc" />
	 	
		</display:table>
	</td>
    
    </tr>
    
    <tr style="height:50px" align="center">
    <td><html:button property="back" value="Back" onclick="return goback();" styleClass="tbut"/> 
 
	 </td>
    </tr>
    
    </table>
</logic:equal>

<logic:equal name="PartToShow" value="AXBRecords" >

<table border="0" cellpadding="0" cellspacing="0" width="60%" align="center">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;AXIS BANK RECORDS DETAILS</td></tr> 
 
    <tr>
    <td align="center" colspan="2">
		&nbsp;
	</td>
    
    </tr>
     <tr>
    <td align="center" colspan="2">
		<display:table name="details" pagesize="10" requestURI="/transaction.do?method=getRecordsCount" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	
	 	<display:column style="text-align:center;" sortable="true" title="DEPOSIT DATE" property="recorddates" class="formtxtc" />
	 	<display:column style="text-align:center;" sortable="true" title="NO. OF RECORDS" property="recordcounts" class="formtxtc" />
	 	
		</display:table>
	</td>
    
    </tr>
    
    <tr style="height:50px" align="center">
    <td><html:button property="back" value="Back" onclick="return goback();" styleClass="tbut"/> 
 
	 </td>
    </tr>
    
    </table>
</logic:equal>



<logic:equal name="PartToShow" value="SCBRecords" >

<table border="0" cellpadding="0" cellspacing="0" width="60%" align="center">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;SCB BANK RECORDS DETAILS</td></tr> 
 
    <tr>
    <td align="center" colspan="2">
		&nbsp;
	</td>
    
    </tr>
     <tr>
    <td align="center" colspan="2">
		<display:table name="details" pagesize="10" requestURI="/transaction.do?method=getRecordsCount" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	
	 	<display:column style="text-align:center;" sortable="true" title="DEPOSIT DATE" property="recorddates" class="formtxtc" />
	 	<display:column style="text-align:center;" sortable="true" title="NO. OF RECORDS" property="recordcounts" class="formtxtc" />
	 	
		</display:table>
	</td>
    
    </tr>
    
    <tr style="height:50px" align="center">
    <td><html:button property="back" value="Back" onclick="return goback();" styleClass="tbut"/> 
 
	 </td>
    </tr>
    
    </table>
</logic:equal>


<logic:equal name="PartToShow" value="HDFCRecords" >

<table border="0" cellpadding="0" cellspacing="0" width="60%" align="center">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;HDFC BANK RECORDS DETAILS</td></tr> 
 
    <tr>
    <td align="center" colspan="2">
		&nbsp;
	</td>
    
    </tr>
      <tr>
    <td align="center" colspan="2">
		<display:table name="details" pagesize="10" requestURI="/transaction.do?method=getRecordsCount" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	
	 	<display:column style="text-align:center;" sortable="true" title="DEPOSIT DATE" property="recorddates" class="formtxtc" />
	 	<display:column style="text-align:center;" sortable="true" title="NO. OF RECORDS" property="recordcounts" class="formtxtc" />
	 	
		</display:table>
	</td>
    
    </tr>
    
    <tr style="height:50px" align="center">
    <td><html:button property="back" value="Back" onclick="return goback();" styleClass="tbut"/> 
 
	 </td>
    </tr>
    
    </table></logic:equal>
    
    
<logic:equal name="PartToShow" value="ReceiptNosTransactions" >
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;Receipt NOs Transaction</td></tr> 

    <tr>
    <td align="center" colspan="2">
		<display:table name="details" pagesize="10" requestURI="/transaction.do?method=receiptNosTransactions" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	
	 	<display:column style="text-align:center;" sortable="true" title="Transaction No" property="transactionNo" class="formtxtc" />
	 	<display:column style="text-align:center;" sortable="true" title="Upload Date" property="transdate" class="formtxtc" />
	 	<display:column style="text-align:center;" sortable="true" title="Total Records"  class="formtxtc" >
			
		${record.totalRecords}
		
		</display:column>
				
		<display:column sortable="true" style="text-align:center;" title="Matched" class="formtxtc">
		<bean:define id="transactionNo" name="record" property="transactionNo" />
		
		<a href="#" onclick="return callResult('<%=transactionNo %>');">
				<bean:write name="record" property="matched" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Pending" class="formtxtc">
		<bean:define id="transactionNo2" name="record" property="transactionNo2" />
			<a onclick="return callNonMatchedResult('<%=transactionNo2 %>');" style="cursor: pointer;">
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
<logic:equal name="PartToShow" value="PolicyNosTransactions">
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr><br/><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;Policy Numbers Transaction</td></tr>
   <tr>
   <td align="center" colspan="2">
   <display:table name="policyList" pagesize="10" requestURI="/transaction.do?method=policyNosTransactions" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	
	 	<display:column style="text-align:center;" sortable="true" title="Transaction No" property="TRANSACTION_NO" class="formtxtc" />
	 	<display:column style="text-align:center;" sortable="true" title="Upload Date" property="TDATE" class="formtxtc" />
	 	<display:column style="text-align:center;" sortable="true" title="Total Records"  property="TOTAL_RECORDS" class="formtxtc" />
	 	<display:column sortable="true" style="text-align:center;" title="Realized" class="formtxtc">
		<bean:define id="transactionNo" name="record" property="TRANSACTION_NO" />
		<logic:greaterThan name="record" property="REALIZED" value="0">
		<a href="#" onclick="return callPolicy('<%=transactionNo %>','Realized');">
			<bean:write name="record" property="REALIZED" />
		</a>
		</logic:greaterThan>
		<logic:equal name="record" property="REALIZED" value="0">
		  <bean:write name="record" property="REALIZED" />
		</logic:equal>
		</display:column>
       <display:column sortable="true" style="text-align:center;" title="Returned" class="formtxtc">
		<bean:define id="transactionNo" name="record" property="TRANSACTION_NO" />
		<logic:greaterThan name="record" property="RETURNED" value="0">
		<a href="#" onclick="return callPolicy('<%=transactionNo %>','Returned');">
			<bean:write name="record" property="RETURNED" />
		</a>
		</logic:greaterThan>
		<logic:equal name="record" property="RETURNED" value="0">
		  <bean:write name="record" property="RETURNED" />
		</logic:equal>
		</display:column>
        <display:column sortable="true" style="text-align:center;" title="Not Known" class="formtxtc">
		<bean:define id="transactionNo" name="record" property="TRANSACTION_NO" />
		<logic:greaterThan name="record" property="NOT_KNOWN" value="0">
		<a href="#" onclick="return callPolicy('<%=transactionNo %>','Not Known');">
			<bean:write name="record" property="NOT_KNOWN" />
		</a>
		</logic:greaterThan>
		<logic:equal name="record" property="NOT_KNOWN" value="0">
		  <bean:write name="record" property="NOT_KNOWN" />
		</logic:equal>
		</display:column>
		<display:column style="text-align:center;" sortable="true" title="Not Available in Xgen"  property="NOT_AVAILABLE" class="formtxtc" />
		</display:table>
		</td>
</tr>
</table>
</logic:equal>


<logic:equal name="PartToShow" value="ReceiptReversalsTransactions" >
<table border="0" cellpadding="0" cellspacing="0" width="100%">
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;Receipt Reversals Transaction</td></tr> 

    <tr>
    <td align="center" colspan="2">
		<display:table name="details" pagesize="10" requestURI="/transaction.do?method=receiptReversalsTransactions" class="table" uid="row" id="record">
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	
	 	<display:column style="text-align:center;" sortable="true" title="Transaction No" property="transactionNo" class="formtxtc" />
	 	<display:column style="text-align:center;" sortable="true" title="Total Records"  class="formtxtc" >
		${record.totalRecords}
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Available" class="formtxtc">
		<bean:define id="transactionNo" name="record" property="transactionNo" />
		<a href="#" onclick="return callReversalsResult('<%=transactionNo %>');">
				<bean:write name="record" property="available" />
		</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Not Available"  property="pending" class="formtxtc" />
		<display:column sortable="true" style="text-align:center;" title="Matched"  property="matched" class="formtxtc" />
		<display:column sortable="true" style="text-align:center;" title="No of Payments Matched"   class="formtxtc" >
		<bean:define id="transactionNo" name="record" property="transactionNo" />
		<a href="#" onclick="return callReversalsEdit('<%=transactionNo %>');">
				<bean:write name="record" property="paymentsMatched" />
		</a>
		</display:column>
		</display:table>
	</td>
    
    </tr>
    
    <tr style="height:50px">
    <td></td>
    </tr>
    
    </table>

</logic:equal>
<div id="testdiv"></div>
<html:hidden property="method"/>
<html:hidden property="searchIn"/>
<html:hidden property="transactionNo" />
<html:hidden property="realised"/>
<html:hidden property="bankId"/>

</html:form>
<script type="text/javascript"> 
function callReceiptMatched(val1){
		document.transactionform.method.value = "goRealised";
      	document.transactionform.searchIn.value = "Receipt";
      	document.transactionform.transactionNo.value = val1;
      	document.transactionform.realised.value = "yes";
      	document.transactionform.bankId.value = "";
		document.transactionform.submit();	
   }
 function callReceiptPending(val1){
		document.transactionform.method.value = "goRealised";
    	document.transactionform.searchIn.value = "Receipt";
    	document.transactionform.transactionNo.value = val1;
    	document.transactionform.realised.value = "no";
    	document.transactionform.bankId.value = "";
		document.transactionform.submit();	
 }
function callMatched(val1,val2){
		document.transactionform.method.value = "goRealised";
      	document.transactionform.searchIn.value = "Bank";
      	document.transactionform.transactionNo.value = val1;
      	document.transactionform.realised.value = "yes";
      	document.transactionform.bankId.value = val2;
		document.transactionform.submit();	
   }
 
 function callPending(val1,val2){
      	document.transactionform.method.value = "goNotRealised";
      	document.transactionform.searchIn.value = "Bank";
      	document.transactionform.transactionNo.value = val1;
      	document.transactionform.realised.value = "no";
      	document.transactionform.bankId.value = val2;
		document.transactionform.submit();		
   }
   function processCiti(transId){
   
      	document.transactionform.method.value = "processRecords";
      	document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "CIT";
      	document.transactionform.submit();
      	document.getElementById("start").disabled=true;
      	document.getElementById("startAll").disabled=true;
      	if( document.getElementById("uploadsts1").style.display == 'none' )
			document.getElementById("uploadsts1").style.display = 'block';
		else
			document.getElementById("uploadsts1").style.display = 'none';
   }
    
  function processHDB(transId, bankId)
  {
  		document.transactionform.method.value = "processRecords";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= bankId;
      	document.getElementById("start").disabled=true;
      	document.getElementById("startAll").disabled=true;
      	document.transactionform.submit();
      	if( document.getElementById("uploadsts2").style.display == 'none' )
		document.getElementById("uploadsts2").style.display = 'block';
		else
		document.getElementById("uploadsts2").style.display = 'none';
  }
  
    function processSCB(transId)
  {
  		document.transactionform.method.value = "processRecords";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "SCB";
      	document.transactionform.submit();
      	document.getElementById("start").disabled=true;
      	document.getElementById("startAll").disabled=true;
      	if( document.getElementById("uploadsts3").style.display == 'none' )
		document.getElementById("uploadsts3").style.display = 'block';
		else
		document.getElementById("uploadsts3").style.display = 'none';
  }
  
   function processAXB(transId)
  {
  		document.transactionform.method.value = "processRecords";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "AXB";
      	document.transactionform.submit();
      	document.getElementById("start").disabled=true;
      	document.getElementById("startAll").disabled=true;
      	if( document.getElementById("uploadsts4").style.display == 'none' )
		document.getElementById("uploadsts4").style.display = 'block';
		else
		document.getElementById("uploadsts4").style.display = 'none';
  }
  
  function processHDB2(val){
      	document.transactionform.method.value = "processAllRecords";
        document.transactionform.bankId.value= val;
      	document.transactionform.submit();
      	if( document.getElementById("uploadsts2").style.display == 'none' )
			document.getElementById("uploadsts2").style.display = 'block';
		else
			document.getElementById("uploadsts2").style.display = 'none';
   }
   
    function processSCB2(val){
      	document.transactionform.method.value = "processAllRecords";
        document.transactionform.bankId.value= "SCB";
      	document.transactionform.submit();
      	if( document.getElementById("uploadsts3").style.display == 'none' )
			document.getElementById("uploadsts3").style.display = 'block';
		else
			document.getElementById("uploadsts3").style.display = 'none';
   }
   
    function processAXB2(val)
    {
      	document.transactionform.method.value = "processAllRecords";
        document.transactionform.bankId.value= "AXB";
      	document.transactionform.submit();
      	if( document.getElementById("uploadsts4").style.display == 'none' )
			document.getElementById("uploadsts4").style.display = 'block';
		else
			document.getElementById("uploadsts4").style.display = 'none';
   }
   
   function processCiti2(val){
      	document.transactionform.method.value = "processAllRecords";
      	document.transactionform.bankId.value= "CIT";
      	document.transactionform.submit();
      	if( document.getElementById("uploadsts1").style.display == 'none' )
			document.getElementById("uploadsts1").style.display = 'block';
		else
			document.getElementById("uploadsts1").style.display = 'none';
   }
   
   function duplicatesHDB(transId, bankId)
  {
  		document.transactionform.method.value = "goDuplicates";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= bankId;
      	document.transactionform.submit();
  }
    function duplicatesSCB(transId)
  {
  		document.transactionform.method.value = "goDuplicates";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "SCB";
      	document.transactionform.submit();
  }
  
   function duplicatesAXB(transId)
  {
  		document.transactionform.method.value = "goDuplicates";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "AXB";
      	document.transactionform.submit();
  }
  
  function duplicatesCiti(transId)
  {
  		document.transactionform.method.value = "goDuplicates";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "CIT";
      	document.transactionform.submit();
  }
  
  function duplicatesReceipt(transId)
  {
  		document.transactionform.method.value = "goDuplicates";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "";
      	document.transactionform.submit();
  }
  
  function invalidsHDFC(transId, bankId)
  {
  		document.transactionform.method.value = "goInvalids";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= bankId;
      	document.transactionform.submit();
  }
  
   function invalidsSCB(transId)
  {
  		document.transactionform.method.value = "goInvalids";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "SCB";
      	document.transactionform.submit();
  }
  
    function invalidsAXB(transId)
  {
  		document.transactionform.method.value = "goInvalids";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "AXB";
      	document.transactionform.submit();
  }
  
  function invalidsCiti(transId)
  {
  		document.transactionform.method.value = "goInvalids";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "CIT";
      	document.transactionform.submit();
  }
  
    function reversalsHDFC(transId, bankId)
  {
  		document.transactionform.method.value = "goReversals";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= bankId;
      	document.transactionform.submit();
  }
  
     function reversalsSCB(transId)
  {
  		document.transactionform.method.value = "goReversals";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "SCB";
      	document.transactionform.submit();
  }
  
 function reversalsAXB(transId)
  {
  		document.transactionform.method.value = "goReversals";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "AXB";
      	document.transactionform.submit();
  }
  
  function reversalsCiti(transId)
  {
  		document.transactionform.method.value = "goReversals";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "CIT";
      	document.transactionform.submit();
  }
  
  function nochequesCiti(transId)
  {
  		document.transactionform.method.value = "goNoCheques";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "CIT";
      	document.transactionform.submit();
  }
  
   function nochequesSCB(transId)
  {
  		document.transactionform.method.value = "goNoCheques";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "SCB";
      	document.transactionform.submit();
  }
  
  function nochequesAXB(transId)
  {
  		document.transactionform.method.value = "goNoCheques";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "AXB";
      	document.transactionform.submit();
  }
  
   function nochequesReceipt(transId)
  {
  		document.transactionform.method.value = "goNoCheques";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "";
      	document.transactionform.submit();
  }
  
  function nochequesHDFC(transId, bankId)
  {
  		document.transactionform.method.value = "goNoCheques";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= bankId;
      	document.transactionform.submit();
      	      	
  }
  function invalidReceipt(transId)
  {
  		document.transactionform.method.value = "goInvalids";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "";
      	document.transactionform.submit();
  }
  
  function reversalReceipts(transId)
  {
  		document.transactionform.method.value = "goReversals";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "";
      	document.transactionform.submit();
  }
  
   function paymentReceipt(transId)
  {
  		document.transactionform.method.value = "goReceiptPayments";
  		document.transactionform.transactionNo.value = transId;
      	document.transactionform.bankId.value= "";
      	document.transactionform.submit();
  }
  
  function countHDB(transId, bankId)
  {
   document.transactionform.method.value = "getRecordsCount";
   document.transactionform.transactionNo.value = transId;
   document.transactionform.bankId.value= bankId;
   document.transactionform.submit();
  }
  
   function countSCB(transId)
  {
   document.transactionform.method.value = "getRecordsCount";
   document.transactionform.transactionNo.value = transId;
   document.transactionform.bankId.value= "SCB";
   document.transactionform.submit();
  }
  
   function countAXB(transId)
  {
   document.transactionform.method.value = "getRecordsCount";
   document.transactionform.transactionNo.value = transId;
   document.transactionform.bankId.value= "AXB";
   document.transactionform.submit();
  }
  
  function countCiti(transId)
  {
   document.transactionform.method.value = "getRecordsCount";
   document.transactionform.transactionNo.value = transId;
   document.transactionform.bankId.value= "CIT";
   document.transactionform.submit();
  }
  
  function countReceipt(transId)
  {
   document.transactionform.method.value = "getRecordsCount";
   document.transactionform.transactionNo.value = transId;
   document.transactionform.bankId.value= "R";
   document.transactionform.submit();
  }
  
  function goback()
  {
    window.history.back();
  }
  
  function callResult(val){
   	  	window.location.href ='<%=request.getContextPath()%>/upload3.do?method=transactionDetails2&bid='+val;
      	return false;
   }
    function callReversalsResult(val){
   	  	window.location.href ='<%=request.getContextPath()%>/reversalReceipt.do?method=transactionDetails&editPaymentNo=&bid='+val;
      	return false;
   }
   function callReversalsEdit(val){
   	  	window.location.href ='<%=request.getContextPath()%>/reversalReceipt.do?method=transactionPayments&bid='+val;
      	return false;
   }
   function callNonMatchedResult(val){
   	  	window.location.href ='<%=request.getContextPath()%>/upload3.do?method=transactionDetails3&bid='+val;
      	return false;
   }
   function callPolicy(batch,status)
   {
  window.location.href ='<%=request.getContextPath()%>/upload3.do?method=realizedPolicy&batchid='+batch+'&status='+status;
      	return false;
   }   
function transDetails(val)
   {
	var URL = '<%=request.getContextPath()%>/transaction.do?method=transDetails&bid='+val;
	var windowName = "Details";
	var width  = screen.availWidth;
	var height = screen.availHeight;
	var w = 750;
	var h = 520;
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
   		
   </script>
</body>
</html:html>
