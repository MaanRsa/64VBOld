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
<html:html locale="true"/>
<head>
<title>KotakTransaction</title>
</head>
<body>
<div align="center">
<font color="red"> <html:errors /> </font>
</div>
<s:form id="transactionform" name="transactionform" theme="simple" action="/transaction.do" method="post">

<logic:equal name="PartToShow" value="KOTAKTransactions" >
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
<tr><BR /><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;KOTAK BANK TRANSACTIONS</td></tr> 
 <tr>
<td align="right" >
<logic:equal name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Processed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=kotakTransactions&status=processed';document.transactionform.submit();"/>
</logic:equal>
<logic:notEqual name="partToShowStatus" value="Unprocessed">
<input type="button" value="Go Unprocessed" class="tbut" onclick="document.transactionform.action = '<%=request.getContextPath()%>/transaction.do?method=kotakTransactions&status=Unprocessed';document.transactionform.submit();"/>
</logic:notEqual>
</td>
</tr>
 
    <tr>
    <td align="center" colspan="2">
		<display:table name="${requestScope.partToShowStatus}" pagesize="10" requestURI="/transaction.do?method=kotakTransactions" class="table" uid="row" id="record">
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
		{%><font color="blue" ><a onclick="return countKOT('${record.transactionNo}','KOT')" style="cursor: pointer;">${record.totalRecords}</a></font>
	 	<%} %>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Invalid Records"  class="formtxtc" >
				<a onclick="return invalidsKOT('${record.transactionNo}','KOT');" style="cursor: pointer;">
							<bean:write name="record" property="invalid" />
				</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Duplicates"  class="formtxtc" >
			<a onclick="return duplicatesKOT('${record.transactionNo}','KOT');" style="cursor: pointer;">
				<bean:write name="record" property="duplicates" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Cheque No. Exists" property="chequeexists" class="formtxtc"/>
		<display:column sortable="true" style="text-align:center;" title="Cheque No. Not Exists" class="formtxtc">
		<a onclick="return nochequesKOT('${record.transactionNo}', 'KOT');" style="cursor: pointer;">
				<bean:write name="record" property="chequenotexists" />
			</a>
	
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Reversal Records"  class="formtxtc" >
				<a onclick="return reversalsKOT('${record.transactionNo}','KOT');" style="cursor: pointer;">
				
				<bean:write name="record" property="reversals" />
			</a>
		</display:column>
		<display:column sortable="true" style="text-align:center;" title="Matched" class="formtxtc">
		<bean:define id="transactionNo" name="record" property="transactionNo" />
			<a onclick="return callMatched('<%=transactionNo %>','KOT')" style="cursor: pointer;">
				<bean:write name="record" property="matched" />
			</a></display:column>
		<display:column sortable="true" style="text-align:center;" title="Pending" class="formtxtc">
		<bean:define id="transactionNo2" name="record" property="transactionNo2" />
			<a onclick="return callPending('<%=transactionNo2 %>','KOT')" style="cursor: pointer;">
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
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processKOT('${record.transactionNo}','KOT');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
		</logic:equal> 
		<logic:greaterThan name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
	    <html:button  property="${record.transactionNo}" value="Process" styleId="start" onclick="return processKOT('${record.transactionNo}','KOT');"  style="background-color:red;" disabled='${transactionform.processCount>0}'/>
		</logic:equal>
		</logic:greaterThan>
		<logic:equal name="transactionform" property="processCount" value="0">
		<logic:equal name="record" property="processed" value="N">
		<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processKOT('${record.transactionNo}','KOT');" styleClass="tbut" disabled='${transactionform.processCount>0}'/>
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
    <html:button property="process2" value="Process Selected Records" onclick="return processKOT2('KOT');" styleId="startAll" styleClass="tbut"/> 
    </logic:notEmpty>
	 </td>
    </tr>
    
    </table>

</logic:equal>


</s:form>

</body>
<script type="text/javascript">

function countKOT(transId, bankId)
{
 document.transactionform.method.value = "getRecordsCount";
 document.transactionform.transactionNo.value = transId;
 document.transactionform.bankId.value= bankId;
 document.transactionform.submit();
}

function invalidsKOT(transId, bankId)
{
		document.transactionform.method.value = "goInvalids";
		document.transactionform.transactionNo.value = transId;
    	document.transactionform.bankId.value= bankId;
    	document.transactionform.submit();
}
function processKOT(transId, bankId)
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

function processKOT2(val){
  	document.transactionform.method.value = "processAllRecords";
    document.transactionform.bankId.value= val;
  	document.transactionform.submit();
  	if( document.getElementById("uploadsts2").style.display == 'none' )
		document.getElementById("uploadsts2").style.display = 'block';
	else
		document.getElementById("uploadsts2").style.display = 'none';
}

function duplicatesKOT(transId, bankId)
{
		document.transactionform.method.value = "goDuplicates";
		document.transactionform.transactionNo.value = transId;
    	document.transactionform.bankId.value= bankId;
    	document.transactionform.submit();
}

function nochequesKOT(transId, bankId)
{
		document.transactionform.method.value = "goNoCheques";
		document.transactionform.transactionNo.value = transId;
    	document.transactionform.bankId.value= bankId;
    	document.transactionform.submit();
    	      	
}
function reversalsKOT(transId, bankId)
{
		document.transactionform.method.value = "goReversals";
		document.transactionform.transactionNo.value = transId;
    	document.transactionform.bankId.value= bankId;
    	document.transactionform.submit();
}

</script>

</html>