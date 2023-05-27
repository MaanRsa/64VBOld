<%@ page isELIgnored="false"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template"
	prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="../../c.tld" prefix="c"%>

<html>
<head>
<link href="${pageContext.request.contextPath}/css/table-design.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function submitFun(val)
{
  if(val=='Advance')
  {
  document.getElementById('requestFrom').value='init';
  document.getElementById('report').action="<%=request.getContextPath()%>/reportsAction.do?method=Advance";
  }
  else{
       document.getElementById('paymentType').value=val;
       document.getElementById('requestFrom').value=val;
       document.getElementById('report').action="<%=request.getContextPath()%>/reportsAction.do?method=reportsList";
      }
 document.getElementById('report').submit();
return true;
}
function advanceFun()
{
document.getElementById('report').action="<%=request.getContextPath()%>/reportsAction.do?method=Advance&requestFrom='final'";
document.getElementById('report').submit();
}
</script>

</head>
<body>
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

<html:form action="reportsAction.do" styleId="report">
<br/>
<logic:equal name="reportsForm" property="display" value="report">
<table>
<tr>
<td>
<logic:notEqual name="reportsForm" property="paymentType" value="CASH">
<div class="newheader" >&nbsp;CHEQUE DETAILS FOR THE PERIOD</div>
</logic:notEqual>
<logic:equal name="reportsForm" property="paymentType" value="CASH">
<div class="newheader" >&nbsp;CASH DETAILS FOR THE PERIOD</div>
</logic:equal>
</td>
<td>
Start Date : <bean:write name="reportsForm" property="startDate"/>
</td>
<td>
End Date : <bean:write name="reportsForm" property="endDate"/>
</td>
</tr>
</table>
<br/>
<div align="right">
<logic:notEqual name="reportsForm" property="paymentType" value="CASH">
<html:button property="paymentType" value="Cash" onclick="return submitFun('CASH')"/>
</logic:notEqual>
<logic:equal name="reportsForm" property="paymentType" value="CASH">
<html:button property="paymentType" value="Cheque" onclick="return submitFun('CHQ')"/>
</logic:equal>
<input type="button" name="method" value="Advance" onclick="return submitFun('Advance');"/>
</div>
<table width="100%">
<tr>
<td>
<div class="tablestyle">
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
	 <tr>
     	<Td colspan="2" align="center" style="background-color: #b9c9fe;">Citi Bank</Td>
	</tr>
	<tr>
		<td width="40%">Total Records</td>
		<td align="center"><bean:write name="reportsForm" property="citiTotal"/></td>
		</tr>
		<tr>
		<td>Matched</td>
		<td><bean:write name="reportsForm" property="citiMatched"/></td>
	</tr>	
		<tr>
		<td>Unmatched</td>
		<td align="center"><bean:write name="reportsForm" property="citiNonMatched"/></td>
		</tr>
		<logic:equal name="reportsForm" property="paymentType" value="CHQ">
		<tr>
		<td>Reversal Cheques</td>
		<td align="center"><bean:write name="reportsForm" property="citiReversal"/></td>
		</tr>
		</logic:equal>
		<tr>
		<td>Unmatched Amount</td>
		<td align="center"><bean:write name="reportsForm" property="citiUnmatchedAmount"/></td>
		</tr>
		</table>
		
</div>

</td>
<td>
<div class="tablestyle">
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
	 <tr>
     	<Td colspan="2" align="center" style="background-color: #b9c9fe;">Axis Bank</Td>
		
		</tr>
	<tr>
		<td width="40%">Total Records</td>
		<td align="center"><bean:write name="reportsForm" property="axisTotal"/></td>
		</tr>
		<tr>
		<td>Matched</td>
		<td><bean:write name="reportsForm" property="axisMatched"/></td>
			
		</tr>	
		<tr>
		<td>Unmatched</td>
		<td align="center"><bean:write name="reportsForm" property="axisNonMatched"/></td>
		</tr>
		<logic:equal name="reportsForm" property="paymentType" value="CHQ">
		<tr>
		<td>Reversal Cheques</td>
		<td align="center"><bean:write name="reportsForm" property="axisReversal"/></td>
		</tr>
		</logic:equal>
		<tr>
		<td>Unmatched Amount</td>
		<td align="center"><bean:write name="reportsForm" property="axisUnmatchedAmount"/></td>
		</tr>
		
		</table>
		
</div>
</td>
</tr>
<tr>
<td>
<div class="tablestyle">
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
	 <tr>
     	<Td colspan="2" align="center" style="background-color: #b9c9fe;">Hdfc Bank</Td>
		
		</tr>
	  <tr>
		<td width="40%">Total Records</td>
		<td align="center"><bean:write name="reportsForm" property="hdfcTotal"/></td>
		</tr>
		<tr>
		<td>Matched</td>
		<td><bean:write name="reportsForm" property="hdfcMatched"/></td>
			
		</tr>	
		<tr>
		<td>Unmatched</td>
		<td align="center"><bean:write name="reportsForm" property="hdfcNonMatched"/></td>
		</tr>
		<logic:equal name="reportsForm" property="paymentType" value="CHQ">
		<tr>
		<td>Reversal Cheques</td>
		<td align="center"><bean:write name="reportsForm" property="hdfcReversal"/></td>
		</tr>
		</logic:equal>
		<tr>
		<td>Unmatched Amount</td>
		<td align="center"><bean:write name="reportsForm" property="hdfcUnmatchedAmount"/></td>
		</tr>
		</table>
		
</div>
</td>
<td>
<div class="tablestyle">
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
	 <tr>
     	<Td colspan="2" align="center" style="background-color: #b9c9fe;">Hsbc Bank</Td>
		
		</tr>
	    <tr>
		<td width="40%">Total Records</td>
		<td align="center"><bean:write name="reportsForm" property="hsbcTotal"/></td>
		</tr>
		<tr>
		<td>Matched</td>
		<td><bean:write name="reportsForm" property="hsbcMatched"/></td>
			
		</tr>	
		<tr>
		<td>Unmatched</td>
		<td align="center"><bean:write name="reportsForm" property="hsbcNonMatched"/></td>
		</tr>
		<logic:equal name="reportsForm" property="paymentType" value="CHQ">
		<tr>
		<td>Reversal Cheques</td>
		<td align="center"><bean:write name="reportsForm" property="hsbcReversal"/></td>
		</tr>
		</logic:equal>
		<tr>
		<td>Unmatched Amount</td>
		<td align="center"><bean:write name="reportsForm" property="hsbcUnmatchedAmount"/></td>
		</tr>
		
</table>
		
</div>
</td>
</tr>
<tr>
<td>
<div class="tablestyle">
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
	 <tr>
     	<Td colspan="2" align="center" style="background-color: #b9c9fe;">Scb Bank</Td>
		
		</tr>
	    <tr>
		<td width="40%">Total Records</td>
		<td ><bean:write name="reportsForm" property="scbTotal"/></td>
		</tr>
		<tr>
		<td>Matched</td>
		<td><bean:write name="reportsForm" property="scbMatched"/></td>
		</tr>	
		<tr>
		<td>Unmatched</td>
		<td ><bean:write name="reportsForm" property="scbNonMatched"/></td>
		</tr>
		<logic:equal name="reportsForm" property="paymentType" value="CHQ">
		<tr>
		<td>Reversal Cheques</td>
		<td align="center"><bean:write name="reportsForm" property="scbReversal"/></td>
		</tr>
		</logic:equal>
		<tr>
		<td>Unmatched Amount</td>
		<td ><bean:write name="reportsForm" property="scbUnmatchedAmount"/></td>
		</tr>
		
		</table>
		
</div>
</td>
<td>
<div class="tablestyle">
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
	 <tr>
     	<Td colspan="2" align="center" style="background-color: #b9c9fe;">Receipt</Td>
		
		</tr>
	    <tr>
		<td width="40%">Total Records</td>
		<td align="center"><bean:write name="reportsForm" property="receiptTotal"/></td>
		</tr>
		<tr>
		<td>Matched</td>
		<td><bean:write name="reportsForm" property="receiptMatched"/></td>
			
		</tr>	
		<tr>
		<td>Unmatched</td>
		<td align="center"><bean:write name="reportsForm" property="receiptNonMatched"/></td>
		</tr>
		<tr>
		<td>Unmatched Amount</td>
		<td align="center"><bean:write name="reportsForm" property="receiptUnmatchedAmount"/></td>
		</tr>
		<logic:equal name="reportsForm" property="paymentType" value="CHQ">
		<tr>
			<td style="background: url(/images/tr-background.gif) no-repeat 0 0;">&nbsp;</td>
			<td style="background: url(/images/tr-background.gif) no-repeat 0 0;">&nbsp;</td>
		</tr>
		</logic:equal>
		</table>
		
</div>
</td>
</tr>

</table>
</logic:equal>
<logic:equal name="reportsForm" property="display" value="input">
<table align="center" width="70%">

<tr>
<td>
<div class="tablestyle">
<table>
<tr>
<td colspan="2" style="background-color: #b9c9fe;"> 
Report
</td>
</tr>
<tr>
<td>
Start Date:
</td>
<td>
<html:text  property="startDate" styleId="fromDate" 
							readonly="readonly"/>
						<a href="javascript:fromDate.popup();"
							onClick="document.getElementById('fromDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here to Pick up the date">
						</a> &nbsp;
</td>
</tr>
<tr>
<td>
End Date:
</td>
<td>
<html:text  property="endDate" styleId="toDate" 
							readonly="readonly"/>
						<a href="javascript:toDate.popup();"
							onClick="document.getElementById('toDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here to Pick up the date">
						</a> &nbsp;	
</td>
</tr>
<tr>
<td>
Payment Type:
</td>
<td>
<html:radio property="paymentType" value="${reportsForm.paymentType}"/>Cheque
<html:radio property="paymentType" value="CASH"/>Cash
</td>
</tr>
<tr>
<td colspan="2" style="text-align: center"><html:button property="method" value="Submit" onclick="return advanceFun()"/>
<html:hidden property="requestFrom" value="final"/>
</td>
</tr>
</table>
</div>
</td>
</tr>
</table>
<script>
var fromDate = new calendar1(document.forms[0].elements["fromDate"]);
fromDate.year_scroll = true;
fromDate.time_comp = false;

var toDate = new calendar1(document.forms[0].elements["toDate"]);
toDate.year_scroll = true;
toDate.time_comp = false;
</script>
</logic:equal>
<html:hidden property="paymentType" styleId="paymentType"/>
<html:hidden property="method" styleId="method"/>
<html:hidden property="requestFrom" styleId="requestFrom"/>
<html:hidden property="startDate"/>
<html:hidden property="endDate"/>
</html:form>
</body>
</html>