<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>


<html >
  <head>
    <title>Report.jsp</title>

  </head>
  
  <body >
	<table  border="0" align="center" width="90%">
	
    <tr><td colspan="4" width="100%" class="tbn" height="10" align="left">RECEIPT DETAILS</td></tr> 
 
   		<tr class="tabin">
			<td >Agent Code:</td>
			<td class="header"><bean:write name="receiptDetails2" property="receiptagcode" /> </td>
			<td >Agent Name:</td>
			<td class="header"><bean:write name="receiptDetails2" property="receiptagname" /> </td>
		</tr>
		
		<tr class="tabin">
			<td >Receipt Branch:</td>
			<td class="header"><bean:write name="receiptDetails2" property="receiptbranchcode" /> </td>
			<td >Receipt Date:</td>
			<td class="header"><bean:write name="receiptDetails2" property="receiptdate" /> </td>
		</tr>
		
		<tr class="tabin">
  			<td >Receipt No:</td>
  			<td class="header"><bean:write name="receiptDetails2" property="receiptno" /> </td>
  			<td >Payment Type:</td>
  			<td class="header"><bean:write name="receiptDetails2" property="paymenttype" /> </td>
		</tr>
		
		<tr style="height:10px">
    	<td></td>
    	</tr>
    	
    	<bean:define id="chequeval" name="receiptDetails2" property="paymenttype"/>
    	<bean:define id="chequestatus" name="receiptDetails2" property="chequestatus"/>
    	
    	<%  if(chequeval.toString().trim().equalsIgnoreCase("Cheque")){%>
	 <tr><td colspan="4" width="100%" class="tbn" height="10" align="left">CHEQUE DETAILS</td></tr> 
 			
		<tr class="tabin">
  			<td >Cheque No:</td>
  			<td class="header"><bean:write name="receiptDetails2" property="chequeno" /> </td>
  			<td >Cheque Date:</td>
  			<td class="header"><bean:write name="receiptDetails2" property="chequedate" /> </td>
		</tr>
		
		<tr class="tabin">
  			<td >Cheque Amount:</td>
  			<td class="header"><bean:write name="receiptDetails2" property="amount" /> </td>
  			<td >Bank Name And Location:</td>
  			<td class="header"><bean:write name="receiptDetails2" property="banknameandloc" /> </td>
		</tr>
		
		<tr class="tabin">
  			<td >Particulars:</td>
  			<td class="header"><bean:write name="receiptDetails2" property="particulars" /> </td>
  			<td colspan="2">&nbsp;</td>
  		</tr>
		<tr style="height:10px">
    	<td></td>
    	</tr>
    	<%} %>
    	
    	<%  if(chequeval.toString().trim().equalsIgnoreCase("Credit")){%>
  	    <tr><td colspan="4" width="100%" class="tbn" height="10" align="left">CREDIT CARD DETAILS</td></tr> 
 	
	    <tr class="tabin">
			<td>Credit Card Type:</td>
			<td class="header"><bean:write name="receiptDetails2" property="creditcardtype" /> </td>
	        <td> Credit Card Bank:</td>
		    <td class="header"><bean:write name="receiptDetails2" property="creditcardbank" /> </td>
		</tr>
		 
		<tr class="tabin">
		  <td>Transaction No:</td>
		  <td class="header"><bean:write name="receiptDetails2" property="transactionreference" /> </td>
		  <td colspan="2">&nbsp;</td>
		</tr>
		
		 <tr style="height:10px">
    	<td></td>
    	</tr>
    	
    	<%} %>
    	
		
		<tr><td colspan="4" width="100%" class="tbn" height="10" align="left">STATUS</td></tr> 
 		<%  if(chequestatus.toString().trim().equalsIgnoreCase("")){%>
 		<tr class="tabin" >
		<td colspan="4" align="center"> Status Not Known</td>
		 </tr>	
 		<%} %>
 		<%  if(!chequestatus.toString().trim().equalsIgnoreCase("")){%>
 		<tr class="tabin">
		  <td>Cheque Status:</td>
		  <td class="header"><b><bean:define id="chequestatus" name="receiptDetails2" property="chequestatus" /><%= chequestatus.toString().toUpperCase() %></b> </td>
		  <td>Date:</td>
		  <td class="header"><bean:write name="receiptDetails2" property="depositdate" /> </td>
		</tr>
		
		<%  if(chequestatus.toString().trim().equalsIgnoreCase("Returned")){%>
		
		<tr class="tabin">
		<td>Reason:</td>
		<td class="header"><bean:write name="receiptDetails2" property="reason" /> </td>
		<td colspan="2">&nbsp;</td>
		 </tr>	
		 
	    <%}} %>
	  
  	<tr>
   <td colspan = "4" align="center"><br />
   	      <input type="button" name="Cancel" value="Close" class="tbut" onclick = "javascript:window.close()">
	</td>  	   			
	</tr>
</table>

  
</body>
</html>