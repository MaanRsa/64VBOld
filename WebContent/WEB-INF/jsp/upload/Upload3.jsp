<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html:html>
<body>
	<html:form action="/upload3.do" enctype="multipart/form-data">
		<table align="center" cellpadding="5" cellspacing="5" border="0"
			width="100%">
			<tr>
				<td>
					<table align="center" border="0" cellpadding="5" cellspacing="0"
						width="40%">
						<tr>
							<td>
								<html:messages id="message" message="true">
									<li style="color: red;">
										<bean:write name="message" />
									</li>
								</html:messages>
								<logic:notEmpty name="error">
									<li style="color: red;">
										<bean:write name="error" />
									</li>
								</logic:notEmpty>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<logic:equal name="PartToShow" value="Upload">
			<div id="uploadsts" style="display:none">
				<table align="center">

					<tr align="center">
						<td colspan="2">
							<font color="#488AC7" size="4pt"><b>Upload In Progress</b>
							</font>
						</td>
					</tr>
					<tr>
						<td align="center">
							<img src="<%=request.getContextPath()%>/images/loader.gif"
								alt="Upload In Progress" />
						</td>
					</tr>
				</table>
			</div>
			<table align="center" cellpadding="5" cellspacing="5" border="0"
				width="100%">
				<tr>
					<td>
						<table align="center" border="0" cellpadding="5" cellspacing="0"
							width="50%">
							<tr class="tbn">
								<td colspan="2">
									<bean:message key="file.receiptno.head" />
								</td>
							</tr>
							<tr class="tabin">
								<td class="t">
									<bean:message key="file.upload.file" />
									&nbsp;:
								</td>
								<td class="t">
									<html:file property="uploadFile" styleId="uploadFile" />
								</td>
							</tr>
							<tr class="tabin">
								<td colspan="2">
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<table cellspacing="0" cellpadding="0" width="50%" align="center"
							border="0">
							<tr>
								<td align="center">
									<html:submit value="Cancel" styleClass="tbut"
										onclick="return cancelPage()" />
									<html:submit value="Submit" styleClass="tbut"
										onclick="return submitPage(this)" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</logic:equal>

		<logic:equal name="PartToShow" value="UploadResult">
			<table border="0" cellpadding="5" cellspacing="0" width="95%"
				align="center">
				<tr class="tabin">
					<td class="title" width="100%" height="10" align="center"
						colspan="2">
						Uploaded Transaction Details
					</td>
				</tr>
				<tr class="tabin">

					<td class="tb" width="50%" height="10" align="left">
						&nbsp;&nbsp;Uploaded Records&nbsp;:&nbsp;
						<span class="st"><bean:write name="TotalRecords"
								scope="request" />&nbsp;</span>
					</td>
					<td class="tb" width="50%" height="10" align="right">
						Not RECT Status&nbsp;:&nbsp;
						<span class="st"><bean:write name="NotRect" scope="request" />&nbsp;&nbsp;&nbsp;</span>
					</td>
				</tr>

				<tr class="tabin">
					<td class="tb" width="50%" height="10" align="left">
						&nbsp;&nbsp;Matched Records&nbsp;:&nbsp;
						<span class="st"><bean:write name="Matched" scope="request" />&nbsp;</span>
					</td>
					<td class="tb" width="50%" height="10" align="right">
						Non Matched Records&nbsp;:&nbsp;
						<span class="st"><bean:write name="NotMatched"
								scope="request" />&nbsp;&nbsp;&nbsp;</span>
					</td>
				</tr>
				<tr style="height:10px">
					<td></td>
				</tr>

				<tr>
					<td colspan="2" align="center">
						<a href="#" onclick="return callResult();">Click here to
							Download Matched Data</a>
					</td>
				</tr>
				<%
				if (request.getAttribute("errors") != null) {
				%>
				<tr>
					<td colspan="2">
						<font color="red"> <%=request.getAttribute("errors")%> </font>
					</td>
				</tr>
				<%
				}
				%>

			</table>
		</logic:equal>

		<logic:equal value="ReceiptDetails" name="PartToShow">

			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<BR />
					<td colspan="2" width="100%" class="tbn" height="20" align="left">
						&nbsp;Receipt No Details
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
				
						<display:table name="ReceiptDetail" pagesize="10"
							requestURI="/upload3.do?method=transactionDetails2" class="table"
							uid="row" id="record" export="true">
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
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Transaction source"
								property="transSource" media="excel"/>
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Payment Method" property="paymentMethod" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Cheque Number" property="chequeNumber" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Cheque Date" property="chequeDate" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Cheque Amount" property="chequeAmount" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title=" PYMT Receipt"
								property="paymentReceipt" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Bank No"
								property="bankNo" />
								<display:column style="text-align:center;width:100px"
								sortable="true" title="Bank Code"
								property="bankCode" />
								<display:column style="text-align:center;width:100px"
								sortable="true" title="Bank Name"
								property="bankName" />
								<display:column style="text-align:center;width:100px"
								sortable="true" title="Drawee Bank Name"
								property="draweeBankName" />
								<display:column style="text-align:center;width:100px"
								sortable="true" title="Deposit Date"
								property="depositDate" />
								<display:column style="text-align:center;width:100px"
								sortable="true" title="Debit / Credit Date"
								property="depositCreditDate" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title=" Status"
								property="realisationStatus" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title=" Remarks"
								property="manualRemarks" />	
								
							<%--<display:column style="text-align:center;width:100px"
								sortable="true" title="Realisation Date"
								property="realisationDate" />
							--%><display:column style="text-align:center;width:100px"
								sortable="true" title="Cheque Return Reason"
								property="chequeReturnReason" />
							<display:setProperty name="export.excel" value="true" />
							<display:setProperty name="export.csv" value="false" />
							<display:setProperty name="export.xml" value="false" />
							<display:setProperty name="export.pdf" value="false" />
							<display:setProperty name="export.excel.filename"
								value="ReceiptDetails_${record.dateTime}.xls" />
						</display:table>
						
					</td>
				</tr>

			</table>

		</logic:equal>
		
		
		<logic:equal value="ReceiptsDetailNotMatched" name="PartToShow">

			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<BR />
					<td colspan="2" width="100%" class="tbn" height="20" align="left">
						&nbsp;Non Matched Receipt No Details
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<display:table name="ReceiptDetail" pagesize="10"
							requestURI="/upload3.do?method=transactionDetails2" class="table"
							uid="row" id="record" export="true">
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
								sortable="true" title="Transaction source"
								property="transSource" />
							
								<display:column style="text-align:center;width:100px"
								sortable="true" title="Bank Code"
								property="bankCode" />
								<display:column style="text-align:center;width:100px"
								sortable="true" title="Bank Name"
								property="bankName" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Status"
								property="realisationStatus" />
							
							<display:setProperty name="export.excel" value="true" />
							<display:setProperty name="export.csv" value="false" />
							<display:setProperty name="export.xml" value="false" />
							<display:setProperty name="export.pdf" value="true" />
							<display:setProperty name="export.pdf.filename"
								value="ReceiptNotMatched_${record.dateTime}.pdf" />
								<display:setProperty name="export.excel.filename"
								value="ReceiptNotMatched_${record.dateTime}.xls" />
						</display:table>

					</td>
				</tr>

			</table>

		</logic:equal>
		<logic:equal value="submitBlock" name="PartToShow">

			<table border="0" cellpadding="0" cellspacing="0" width="100%">

				<tr style="height:20px">
					<td>
						<br>
					</td>
				</tr>

				<tr align="center">
					<td colspan="2">
						<html:errors />
					</td>
				</tr>

				<tr style="height:20px">
					<td>
						<br>
					</td>
				</tr>

				<tr align="center">
					<td>
						<html:submit value="Back" styleClass="button"
							onclick="return normalBack(); " />
						<br>
					</td>
				</tr>

				<tr style="height:20px">
					<td>
						<br>
						<br>
						<br>
					</td>
				</tr>
			</table>

		</logic:equal>
		<logic:equal name="PartToShow" value="PolicyRealized">
		   <display:table name="realizedPolicies" pagesize="10"
							requestURI="/upload3.do?method=realizedPolicy" class="table"
							uid="row" id="record" export="true">
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
								sortable="true" title="Policy No"
								property="POLICY_NUMBER" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Bank Name"
								property="BANK_NAME" />
								<display:column style="text-align:center;width:100px"
								sortable="true" title="Receipt No"
								property="RECEIPT_NO" />
								<display:column style="text-align:center;width:100px"
								sortable="true" title="Cheque No"
								property="INSTRUMENT_NO" />
								<display:column style="text-align:center;width:100px"
								sortable="true" title="Cheque Amount"
								property="INSTRUMENT_AMOUNT" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Cheque Date"
								property="CHEQUE_DATE" />
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Realized Date"
								property="REALIZED_DATE" />	
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Cheque Status"
								property="STATUS" />
								<logic:notEqual name="record" property="STATUS" value="Realized">
							<display:column style="text-align:center;width:100px"
								sortable="true" title="Return Reason"
								property="RETURN_REASON" />
								</logic:notEqual>
						<display:setProperty name="export.excel" value="true" />
							<display:setProperty name="export.csv" value="false" />
							<display:setProperty name="export.xml" value="false" />
						    <logic:equal name="record" property="STATUS" value="Realized">
								<display:setProperty name="export.excel.filename"
								value="RealizedPolicies${record.dateTime}.xls" />
							</logic:equal>	
							<logic:equal name="record" property="STATUS" value="Returned">
								<display:setProperty name="export.excel.filename"
								value="ReturnedPolicies${record.dateTime}.xls" />
							</logic:equal>	
							<logic:equal name="record" property="STATUS" value="Not Known">
								<display:setProperty name="export.excel.filename" value="NotKnownPolicies${record.dateTime}.xls" />
							</logic:equal>
								
		  </display:table>
		</logic:equal>
		<html:hidden property="method" />
		<html:hidden property="requestfrom" />
		<html:hidden property="transactionid" />
	</html:form>

	<script>
  function cancelPage(){
   	  	document.uploadform3.action ='<%=request.getContextPath()%>/menu.do?method=menu';
      	document.uploadform3.submit();
      	return false;
   }
   function callResult(){
   	  	window.location.href ='<%=request.getContextPath()%>/upload3.do?method=transactionDetails2';
      	return false;
   }
   function submitPage(val){
      	document.uploadform3.method.value = "uploadsubmit";
      	document.uploadform3.submit();
      	if( document.getElementById("uploadsts").style.display == 'none' )
			document.getElementById("uploadsts").style.display = 'block';
		else
			document.getElementById("uploadsts").style.display = 'none';

		val.disabled=true;
		document.getElementById("uploadFile").disabled = true;
   }
 
   function submitupdatedDetails()  
   {
   document.forms['uploadform3'].setAttribute('enctype', 'newEnctype', 0);
   document.uploadform3.method.value = "statusUpdationRecords";
   document.uploadform3.submit();
   return false;
   }
   
String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}
</script>

</body>
</html:html>
