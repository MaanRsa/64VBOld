<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<html >
  <head>
    <title>Search.jsp</title>

  </head>
  
  <body >

<logic:equal name="receiptDetails" property="bankAvail" value="Y">

 <table  align="center" width="90%">
 	<tr class="tabin">
  			<td >Name:</td>
  			
  			<td class="header"><bean:write name="receiptDetails" property="name" /> </td>
		</tr>
   		<tr class="tabin">
  			<td >Receipt No:</td>
  			
  			<td class="header"><bean:write name="receiptDetails" property="receipt" /> </td>
		</tr>
		<logic:notEqual value="-" name="receiptDetails" property="chequeNo" >
	  		<logic:notEmpty name="receiptDetails" property="chequeNo" >
			 <tr class="tabin">
				<td>Receipt Cheque No:</td>
				<td class="header"><bean:write name="receiptDetails" property="chequeNo" /> </td>
	
			</tr>
			</logic:notEmpty>
			<tr class="tabin">
			  <td>Receipt Cheque Amount:</td>
			  <td class="header"><bean:write name="receiptDetails" property="chequeAmount" /> </td>
			</tr>
			<logic:notEmpty name="receiptDetails" property="bankChequeNo" >
			
			 <tr class="tabin">
				<td>Bank Cheque No:</td>
				<td class="header"><bean:write name="receiptDetails" property="bankChequeNo" /> </td>
	
			</tr>
			</logic:notEmpty>
			<tr class="tabin">
			  <td>Bank Cheque Amount:</td>
			  <td class="header"><bean:write name="receiptDetails" property="bankChequeAmt" /> </td>
			</tr>
			<logic:notEmpty name="receiptDetails" property="actualChequeAmount" >
			<tr class="tabin">
			  <td>Actual Cheque Amount:</td>
			  <td class="header"><bean:write name="receiptDetails" property="actualChequeAmount" /> </td>
			</tr>
			</logic:notEmpty>
			
			<logic:notEmpty name="receiptDetails" property="actualChequeNo" >
			<tr class="tabin">
			  <td>Actual Cheque No:</td>
			  <td class="header"><bean:write name="receiptDetails" property="actualChequeNo" /> </td>
			</tr>
			</logic:notEmpty>
	   </logic:notEqual>
	   <logic:equal value="-" name="receiptDetails" property="chequeNo" >
	   <tr class="tabin">
			  <td>Receipt Amount:</td>
			  <td class="header"><bean:write name="receiptDetails" property="chequeAmount" /> </td>
			</tr>
			
			<tr class="tabin">
			  <td>Bank  Amount:</td>
			  <td class="header"><bean:write name="receiptDetails" property="bankChequeAmt" /> </td>
			</tr>
	   </logic:equal>
	  	
			<tr class="tabin">
			  <td>Bank Name and Location:</td>
			  <td class="header"><bean:write name="receiptDetails" property="bankName" /> </td>
			</tr>
		
		<!--  <tr class="tabin">
		  <td>Reason:</td>
		  <td class="header"><bean:write name="receiptDetails" property="reason" /> </td>
		</tr>
		-->
		<tr class="tabin">
		  <td>Transaction No:</td>
		  <td class="header"><bean:write name="receiptDetails" property="transactionNo" /> </td>
		</tr>
		
		
	   <tr class="tabin">
		  <td>Receipt Date:</td>
		  <td class="header"><bean:write name="receiptDetails" property="receiptDate" /></td>
	  </tr>
	  
  	<tr>
   <td colspan = "2" align="center"><br />
   	      <input type="button" name="Cancel" value="Close" class="tbut" onclick = "javascript:window.close()">
	</td>  	   			
	</tr>

</table>
</logic:equal>
<logic:equal name="receiptDetails" property="bankAvail" value="N">
<div class="header">Bank details are not available.</div> 
</logic:equal>
</body>
</html>