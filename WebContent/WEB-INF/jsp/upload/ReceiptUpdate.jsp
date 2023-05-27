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

<%
			String reqmethod = request.getAttribute("method") == null ? ""
			: (String) request.getAttribute("method");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<title>Cash.jsp</title>
	<script>
function goBack()
{
 window.history.back();
}

function gosearch()
{
  document.uploadFileForm.method.value='receiptUpdate';
  document.uploadFileForm.mode.value='list';
  document.uploadFileForm.submit();
	return false;
}
function processUpdate(tranId){
  document.uploadFileForm.method.value='reprocessRecords';
  document.uploadFileForm.mode.value='list';
  document.uploadFileForm.submit();
	return false;
}
	</script>
</head>

<body>

	<html:form action="/fileUpload.do" styleId="mainForm" >
		<logic:messagesPresent>
			<br />
			<table align="center" border="0" align="center" width="50%"
				cellpadding="1" cellspacing="1">

				<tr valign="top" align="center" style="height:5px">
					<td colspan="2">
						<font color="red"> <html:errors /> </font>
					</td>
				</tr>

			</table>
		</logic:messagesPresent>
		

		<logic:equal name="PartToShow" value="searchProcess">
			<table align="center" border="0" align="center" width="45%"
				cellpadding="1" cellspacing="1">
				<tr style="height:2px">
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" class="tbn" height="10" align="left">
						RECEIPT UPDATE
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" height="10" align="left"></td>
				</tr>
				<tr>
					<td>
						<bean:message key="cash.bankname" />
					</td>
					<td align="left">
						&nbsp;
						<html:select property="bankName" name="uploadFileForm">
							<html:option value="select">-Select-</html:option>
							<html:optionsCollection name="BankList" label="value" value="key" />
						</html:select>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						Transaction Id
					</td>
					<td align="left">
						&nbsp;
						<html:text name="uploadFileForm" property="tranId" />
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<br />
						<html:button property="process" value="Submit" onclick="return gosearch();" styleClass="tbut" styleId="startAll"/>
					</td>
				</tr>
			</table>
			<br />
		</logic:equal>
		<logic:equal name="PartToShow" value="searchList">
			<table align="center" border="0" align="center" width="45%"
				cellpadding="1" cellspacing="1">
				<tr style="height:2px">
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" class="tbn" height="10" align="left">
						RECEIPT UPDATE
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" height="10" align="left"></td>
				</tr>
				<tr>
					<td>
						<bean:message key="cash.bankname" />
					</td>
					<td align="left">
						&nbsp;
						<html:select property="bankName" name="uploadFileForm">
							<html:option value="select">-Select-</html:option>
							<html:optionsCollection name="BankList" label="value" value="key" />
						</html:select>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						Transaction Id
					</td>
					<td align="left">
						&nbsp;
						<html:text name="uploadFileForm" property="tranId" />
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<br />
						<html:button property="process" value="Submit" onclick="return gosearch();" styleClass="tbut" styleId="startAll"/>
					</td>
				</tr>
			</table>
			<br/>
		 <%--<logic:iterate id="dept" name="Processed">
		 </logic:iterate>--%>
		 
		 <table border="0" cellpadding="4" cellspacing="0" width="100%">
			<tr><br/><td colspan="2" width="100%" class="tbn" height="20" align="left"> &nbsp;TRANSACTION DETAIL</td></tr>
			   <tr>
			     <td align="center" colspan="2">
					<display:table name="Processed" pagesize="10" requestURI="" class="table" uid="row" id="record">
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
					<bean:define id="pending" name="record" property="pending"/>
					
					<%if(processed.toString().equalsIgnoreCase("Y") && (Integer.parseInt(pending.toString()) >0 )) {%>
					<!--<font color="blue" >Processed</font>
					-->
						<html:button property="${record.transactionNo}" value="Process" styleId="start" onclick="return processUpdate('${record.transactionNo}');" styleClass="tbut" />
					<%} else
					{%>
					 <font color="blue" >No_Action</font>
				 	<%} %>
					
				 	</display:column>
					</display:table>
				</td>
			    
			    </tr>
			   
		    </table>
		  
		
		
		</logic:equal>
		<html:hidden property="mode" styleId="mode" />
		<html:hidden property="method" styleId="method" value="Upload"/>
		<html:hidden property="tranId" styleId="tranId" />
		<html:hidden property="uploadType" styleId="uploadType" />
		<html:hidden property="bankName" styleId="bankName"/>
	</html:form>

</body>

</html:html>
