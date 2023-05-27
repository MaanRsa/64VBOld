<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
	String selectedNo = request.getAttribute("SelectedNo")==null?"":(String)request.getAttribute("SelectedNo");
	String selectedSum = request.getAttribute("SelectedSum")==null?"":(String)request.getAttribute("SelectedSum");
%>
<html:html>
<body>
	<html:form action="/reversalReceipt.do" enctype="multipart/form-data">
		
      
		<logic:equal value="ReceiptDetails" name="PartToShow">

			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<BR />
					<td colspan="2" width="100%" class="tbn" height="20" align="left">
						&nbsp;Receipt Reversal Details
					</td>
				</tr>
				<tr>
				<td>
				<table align="center" border="0" align="center" width="100%"
				cellpadding="1" cellspacing="1">

				<tr valign="top" align="center" style="height:5px">
					<td colspan="2">
						<font color="red"> <html:errors /> </font>
					</td>
				</tr>
				<tr valign="top" align="center" style="height:5px;width:100%">
					<td colspan="2">
						<% String bid=request.getAttribute("bid").toString();%>
					Transaction No: <b><%=bid %></b>
						<%if(request.getAttribute("EditPayment")!=null){%>
					    &nbsp; &nbsp; &nbsp; Payment No: <b><%=request.getAttribute("EditPayment") %></b>
					     &nbsp; &nbsp; &nbsp; Payment Amount: <b><%=request.getAttribute("Amount") %></b>
						<html:hidden property="editPaymentNo" value='<%=request.getAttribute("EditPayment").toString() %>'/>
						<%} %>
					</td>
				</tr>

				</table>
				</td>
				</tr>
			
				<html:hidden property="transactionid"  value="<%=bid %>"/>
				<tr>
					<td align="center" colspan="2">
					<br/>
						<display:table name="ReceiptDetail" pagesize="10"
							requestURI="/reversalReceipt.do?method=transactionDetails" class="table"
							uid="row" id="record" >
							<display:setProperty name="paging.banner.one_item_found" value="" />
							<display:setProperty name="paging.banner.one_items_found"
								value="" />
							<display:setProperty name="paging.banner.all_items_found"
								value="" />
							<display:setProperty name="paging.banner.some_items_found"
								value="" />
							<display:setProperty name="paging.banner.placement"
								value="bottom" />
							<display:setProperty name="paging.banner.onepage" value="" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Receipt No" property="receiptNumber" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Receipt Date" property="receiptDate" />
							<display:column style="text-align:center;width:180px"
								sortable="true" title="Receipt AG Name" property="receiptAGName" />
							<display:column style="text-align:right;width:100px"
								sortable="true" title="Cheque Number" property="chequeNumber" />
							<display:column style="text-align:right;width:100px"
								sortable="true" title="Cheque Amount" property="chequeAmount" />
						
							<display:column sortable="true" 
								title='Reverse <input type="checkbox" name="choosen" onclick="checkAll(this);"/>'
								 style="text-align:center;width:34px;">
								<bean:define id="status" name="record" property="status" />
								<input type="checkbox" name="checkbox${record.receiptNumber}"
									value="${record.status}"
									<%=status.toString().trim().equalsIgnoreCase("C") ? "checked=checked":"" %>
									 />
								<input type="hidden" name="hidden${record.receiptNumber}"
									value="${record.status}" />
							</display:column>	
						
							
						</display:table>

					</td>
				</tr>

			</table>
			<br/>
			<center>
			<html:button property ="submitReceipt" value="Submit" styleClass="button"
										onclick="submitReceipts();" />
			<html:button property ="back" value="Back" styleClass="button"
										onclick="goBack()" />
			</center>
		</logic:equal>
		
<logic:equal name="PartToShow" value="ReversalRecordsSelected">
<table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="10" align="left">REVERSAL RECEIPTS  </td></tr> 
 <tr>
				<td>
				<table align="center" border="0" align="center" width="50%"
				cellpadding="1" cellspacing="1">

				<tr valign="top" align="center" style="height:5px">
					<td colspan="2">
						<font color="red"> <html:errors /> </font>
					</td>
				</tr>

				</table>
				</td>
</tr>
   <tr style="height:15px">
    <td>
    <table width="100%" >
    
     
    <tr>
    <td>
    &nbsp;
    <table align="right">
        <tr>
        <td height="80px">
       
        </td>
        </tr>
    	<tr>
        <td class="tbn" colspan=2 height="10px">
        Payment Details
        </td>
        </tr>
        <tr><td><br/></td></tr>
		<tr><td>Payment No</td>
		<td align="left">
		&nbsp;
		<html:text  property="paymnetNo" />
		 &nbsp;
		</td>
		</tr>
		
		<tr><td>Payment Date</td>
		<td align="left">
		&nbsp;
		<input type="text"  name="paymentDate"   id="paymentDate" readonly />
		<a href="javascript:paymentDate.popup();"
			onClick="document.getElementById('paymentDate').focus()"><img
				src="<%=request.getContextPath()%>/images/cal.gif" width="16"
				height="16" border="0" alt="Click Here Pick up the date">
		</a> &nbsp;
		<script>
		var paymentDate = new calendar1(document.forms[0].elements["paymentDate"]);
		paymentDate.year_scroll = true;
		paymentDate.time_comp = false;
	</script>
		&nbsp;
		</td>
		</tr>
		<tr><td>Payment Amount</td>
		<td align="left">
		&nbsp;
		<html:text  property="paymentAmount" />
		&nbsp;
		</td>
		</tr>
		
		<tr><td colspan="2" align="center">
		<br />
		 <input type="button" name="Submit2" value="Submit" class="tbut" onClick="return submitPayment()">
               <input type="button" name="button2" value="Cancel" onClick="return goHistoryBack()" class="tbut">
          
             </td>
		</tr>
    </table>
    </td>
    <td colspan="2" valign="top">
    <table width="70%" align="right">
    <tr>
    <td class="tbn" align="center"> Receipts Selected&nbsp;</td>
    </tr>
     <tr class="tabin">
        <td width="30%"  height="85" align="center" class="t">
         <table width="100%" cellspacing="2" cellpadding="2" align="center">
         <tr>
         <td width="65%" align="center"><span class="tb"> Transaction Id</span></td>
         <td align="left"> <span class="tb"><%=request.getAttribute("bid") %>
        
         
         </span></td>
         </tr>
         <tr>
         <td align="center"><span class="tb"> No. of Receipts Selected</span></td>
         <td align="left"> <span class="tb">
	 			<%=selectedNo%>
	 			 <html:hidden property="selected" value="<%=selectedNo%>"/>
	 			</span></td>
         </tr>
          <tr>
         <td align="center"><span class="tb">Sum of Selected Receipts</span></td>
         <td align="left"><span class="tb"> <%=selectedSum%>
          <html:hidden property="sum" value="<%=selectedSum%>"/>
	 			
         </span></td>
         </tr>
         </table>
         
         </td>
        
      </tr>
    </table>
    
    
    </td>
    </tr>
    
    <tr>
   
    </table></td></tr> 
 
</table>   
  
</logic:equal>
	
		<logic:equal value="ReversalSuccess" name="PartToShow">

			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<BR />
					<td colspan="2" width="100%" class="tbn" height="20" align="left">
						&nbsp;Receipt Reversal Details
					</td>
				</tr>
				<tr>
				<td>
				<table align="center" border="0" align="center" width="50%"
				cellpadding="1" cellspacing="1">

				<tr valign="top" align="center" style="height:5px">
					<td colspan="2">
						<font color="red"> <html:errors /> </font>
					</td>
				</tr>
				<tr valign="top" align="center" style="height:5px">
					<td colspan="2">
						<font color="green"> Reversal Process Done Successfully. </font>
					</td>
				</tr>
				<tr>
				<td>
				
				<table width="70%" align="center">
			    <tr>
			    <td class="tbn" colspan=2 align="center"> Reversal Details&nbsp;</td>
			    </tr>
			     <tr class="tabin"><td>
			     Transaction Id :
			     </td><td>
			      <%=request.getAttribute("TransId") %>
			      </td>
			      </tr>
			      <tr  class="tabin">
			     <td>
			     No of Receipts Selected:
			       </td><td><%=selectedNo%>
			       </td>
			      </tr>
			      <tr  class="tabin">
			     <td>
			      Sum of selected Receipts:   </td><td><%=selectedSum%>
			      </td>
			      </tr>
			      <tr  class="tabin">
			     <td>
			      Payment No:  </td><td><%=request.getAttribute("PaymentNo") %>
			       </td>
			      </tr>
			      <tr  class="tabin">
			     <td>
			      Payment Date:  </td><td><%=request.getAttribute("PaymentDate") %>
			      </td>
			      </tr>
			      <tr  class="tabin">
			     <td>
			      Payment Amount:  </td><td><%=request.getAttribute("PaymentAmount") %>
			      </td>
			     </tr>
			    </table>
     
				</td>
				</tr>
				</table>
				</td>
				</tr>
				
				
			</table>
			<br/>
			<center>
			
			<html:button property ="back" value="Back" styleClass="button"
										onclick="goBack()" />
			</center>
		</logic:equal>	
<logic:equal value="ReversalPayments" name="PartToShow">

			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<BR />
					<td colspan="2" width="100%" class="tbn" height="20" align="left">
						&nbsp;Receipt Reversal Payments
					</td>
				</tr>
				<tr>
				<td>
				<table align="center" border="0" align="center" width="50%"
				cellpadding="1" cellspacing="1">
				<tr valign="top" align="center" style="height:5px">
					<td colspan="2">tr<font color="red"><html:errors /> </font>
					</td>
				</tr>

				<tr valign="top" align="center" style="height:5px">
					<td colspan="2">
						Transaction No: <b><%=request.getAttribute("bid").toString() %></b>
					</td>
				</tr>

				</table>
				</td>
				</tr>
				<% String bId=request.getAttribute("bid").toString();%>
				<html:hidden property="transactionid"  value="<%=bId %>"/>
				<tr>
					<td align="center" colspan="2" >
					<table border=0 width="420px">
					<tr>
					<td>
					<br/>
						<display:table name="ReversalPayments" pagesize="10"
							requestURI="/reversalReceipt.do?method=transactionPayments" class="table"
							uid="row" id="record" >
							<display:setProperty name="paging.banner.one_item_found" value="" />
							<display:setProperty name="paging.banner.one_items_found"
								value="" />
							<display:setProperty name="paging.banner.all_items_found"
								value="" />
							<display:setProperty name="paging.banner.some_items_found"
								value="" />
							<display:setProperty name="paging.banner.placement"
								value="bottom" />
							<display:setProperty name="paging.banner.onepage" value="" />
							<display:column style="text-align:left;width:150px"
								sortable="true" title="Payment No" property="paymentNo" />
							<display:column sortable="true" style="text-align:center;width:200px" title="No of Receipts Matched"   class="formtxtc" >
									<bean:write name="record" property="matched" />
							</display:column>
							<display:column sortable="true" style="text-align:center;width:70px" title="Edit"   class="formtxtc" >
							<bean:define id="paymentNo" name="record" property="paymentNo" />
							<a href="#" onclick="return callReversalsEdit('<%=paymentNo %>');">
									Edit
							</a>
							</display:column>
							
						</display:table>
						</td>
					</tr>
					</table>
					</td>
				</tr>

			</table>
			<br/>
			<center>
			<html:button property ="back" value="Back" styleClass="button"
										onclick="goBack()" />
			</center>
		</logic:equal>		
		<html:hidden property="method" />
		<html:hidden property="requestfrom" />
		<html:hidden property = "editPaymentNo"/>
		<br/>
		
		
		
		
	</html:form>

	<script>
 
   function callResult(){
   	  	window.location.href ='<%=request.getContextPath()%>/reversalReceipt.do?method=transactionDetails2';
      	return false;
   }
  
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}


function checkAll(object)
{
	var elements=document.forms[0].elements;
	var tot=0;
	for(i=0; i<elements.length; i++)
		{
			var obj=document.forms[0].elements[i];
			if(obj.type=='checkbox')
			{
				if(object.checked==true)
				obj.checked=true;
				else
				obj.checked=false;
			}
		}
	
}


function submitReceipts(){
    document.reversalReceiptForm.method.value='submitReceipts';
    document.reversalReceiptForm.action='<%=request.getContextPath()%>/reversalReceipt.do';
	document.reversalReceiptForm.submit();
	return false;
}

function submitPayment(){

    if( document.reversalReceiptForm.paymnetNo.value!='' && document.reversalReceiptForm.paymentDate.value!=''  && document.reversalReceiptForm.paymentAmount.value!=''){
    var sumvalue = parseFloat(document.forms[0].sum.value);
   	var paymentVal =parseFloat( document.reversalReceiptForm.paymentAmount.value);
    if(sumvalue > paymentVal){
    
        if(!confirm('Receipts Amount greater than Payment Amount.Continue?'))
        {
         return false;
        }
      }
        if(sumvalue > paymentVal){
          if((sumvalue-paymentVal)>100)
          if(!confirm('Receipts Amount and Payment Amount difference greater than 100.Continue?'))
          {
          return false;
          }
                    
      }
       if(sumvalue < paymentVal){
          if((paymentVal-sumvalue)>100)
          if(!confirm('Receipts Amount and Payment Amount difference greater than 100.Continue?'))
          {
           return false;
          }
      }
     }
    document.reversalReceiptForm.method.value='submitPayment';
    document.reversalReceiptForm.action='<%=request.getContextPath()%>/reversalReceipt.do';
	document.reversalReceiptForm.submit();
	return false;
}

function submitEditPayment(){

    if( document.reversalReceiptForm.paymnetNo.value!='' && document.reversalReceiptForm.paymentDate.value!=''  && document.reversalReceiptForm.paymentAmount.value!=''){
    var sumvalue = parseFloat(document.forms[0].sum.value);
   	var paymentVal =parseFloat( document.reversalReceiptForm.paymentAmount.value);
    if(sumvalue > paymentVal){
    
        if(!confirm('Receipts Amount greater than Payment Amount.Continue?'))
        {
         return false;
        }
      }
        if(sumvalue > paymentVal){
          if((sumvalue-paymentVal)>100)
          if(!confirm('Receipts Amount and Payment Amount difference greater than 100.Continue?'))
          {
          return false;
          }
                    
      }
       if(sumvalue < paymentVal){
          if((paymentVal-sumvalue)>100)
          if(!confirm('Receipts Amount and Payment Amount difference greater than 100.Continue?'))
          {
           return false;
          }
      }
     }
    document.reversalReceiptForm.method.value='submitPayment';
    document.reversalReceiptForm.action='<%=request.getContextPath()%>/reversalReceipt.do';
	document.reversalReceiptForm.submit();
	return false;
}
function goBack()
{
    window.location.href='<%=request.getContextPath()%>/transaction.do?method=receiptReversalsTransactions';

}

function goHistoryBack()
{
  history.back();
}
  function callReversalsEdit(val){
  
    document.reversalReceiptForm.editPaymentNo.value = val;
   	document.reversalReceiptForm.method.value='transactionDetails';
   	document.reversalReceiptForm.requestfrom.value='';
    document.reversalReceiptForm.action='<%=request.getContextPath()%>/reversalReceipt.do';
	document.reversalReceiptForm.submit();
	return false;
   }
function callpage(val)
{      
      var pts="<%=request.getAttribute("PartToShow")%>";
	    if("ReceiptDetails"==pts ){
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
		method="transactionDetails";
		document.reversalReceiptForm.requestfrom.value="second";
		document.reversalReceiptForm.action="<%=request.getContextPath()%>/reversalReceipt.do?"+page+"method="+method;
       	document.reversalReceiptForm.submit();
		return false;
	  }
}

</script>

</body>
</html:html>
