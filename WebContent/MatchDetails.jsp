<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html >
  <head>
    <title>Search.jsp</title>

  </head>
  
  <body >
<table  align="center" width="98%" >
    <tr><br /><td colspan="2"  class="tbn" height="20" align="left">Bank Detail</td></tr> 
 <%int bankamt=0; %>
  	<logic:iterate  name="BankDetail" id="result">
		<tr>
		<td width="130px">&nbsp;&nbsp;Bank Name  </td><td align="left"> :
		<b><bean:write name="result" property="bankName"/></b>
		</td>
		</tr>
		<tr>
		<td width="130px">&nbsp;&nbsp;Deposit Date  </td><td align="left"> :
		<b><bean:write name="result" property="depositDate"/></b>
		</td>
		</tr><tr>
		<td width="130px">&nbsp;&nbsp;Deposit No  </td><td align="left"> :
		<b><bean:write name="result" property="depositNo"/></b>
		</td>
		</tr>
		<tr>
		<td width="130px">&nbsp;&nbsp;Bank Amount  </td><td align="left"> :
		<b><bean:write name="result" property="bankAmount"/></b>
		<bean:define id="bankval" name="result" property="bankAmount"></bean:define>
		</td>
		</tr>
		<%bankamt=Integer.parseInt(bankval.toString()); %>
	</logic:iterate>
    
	
</table>

 <table  align="center" width="98%">
    <tr><br /><td colspan="5"  class="tbn" height="20" align="left">Matched Receipts</td></tr> 
     <tr>
     <td class="tbn">Receipt No</td><td class="tbn">Receipt Amount </td><td class="tbn">Receipt AG Name </td><td class="tbn">Product Code</td><td class="tbn"> Branch Code</td>
     </tr>
     <%int val=0; %>
  	<logic:iterate  name="Receipts" id="result">
		<tr>
		<td ><b><bean:write name="result" property="receiptNo"/></b></td>
		<bean:define id="receiptAmount" name="result" property="receiptAmount"/>
		<%val+=Integer.parseInt(receiptAmount.toString());%>
		<td align="center"> <b><bean:write name="result" property="receiptAmount"/></b></td>
		
		<td ><b><bean:write name="result" property="receiptAGName"/></b></td>
		
		<td > <b><bean:write name="result" property="receiptProduct"/></b></td>
		
		<td ><b><bean:write name="result" property="receiptBranchCode"/></b></td>
		
		</tr>
	</logic:iterate>
	<tr>
	<td>&nbsp;</td>
	</tr>
    <tr>
    <td> <b>Total:</b></td><td align="center"><b> <%=val %></b></td>
    <td><b>Difference:<b>&nbsp;&nbsp;<b><%= bankamt-val %></b></td>
    </tr>
  	<tr>
   <td colspan = "5" align="center"><br />
   	      <input type="button" name="Cancel" value="Close" class="tbut" onclick = "javascript:window.close()">
	</td>  	   			
	</tr>
</table>

</body>
</html>