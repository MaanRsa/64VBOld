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

  document.cashFormBean.method.value='cashSearch';
  document.cashFormBean.submit();
	return false;
}
function gobranchDetails()
{
     document.cashFormBean.method.value='showBranchDetails';
    document.cashFormBean.search.value='first';
    
	document.cashFormBean.action='<%=request.getContextPath()%>/Cash.do';
	document.cashFormBean.submit();
	return false;
}
function addAmount(){
	if(document.cashFormBean.receiptAmount[0]){
		var tot = 0;
		var len = document.cashFormBean.receiptAmount.length;
		//start
		var elements=document.cashFormBean.elements;
        var flag = new Array();
        var k=0;
        
		for(var j=0; j<elements.length; j++)
		{
			var obj=document.cashFormBean.elements[j];
			if(obj.type=='checkbox')
			{
				if(obj.checked==true)
				{
				 flag[k++]='T';
				}
				else 
				{
				flag[k++]='F';
				}
				
			}
			
		}
			
		//end
		for(var i=0;i<len;i++){
			var temp = document.cashFormBean.receiptAmount[i].value;
			if(flag[i+1]=='T')
			tot = parseInt(tot) + parseInt(temp);
		}
	
		document.getElementById("sumAmount").value = tot + parseInt(document.getElementById("previousTotal").value);
	}else if(document.cashFormBean.receiptAmount){
		var temp = document.cashFormBean.receiptAmount.value;
		document.getElementById("sumAmount").value = parseInt(temp) + parseInt(document.getElementById("previousTotal").value);
	}
}

function sumAmount(){
if(document.cashFormBean.receiptAmount){
	if(document.cashFormBean.receiptAmount[0]){
		var tot = 0;
		var len = document.cashFormBean.receiptAmount.length;
		//start
		var elements=document.cashFormBean.elements;
        var flag = new Array();
        var k=0;
		for(var j=0; j<elements.length; j++)
		{
			var obj=document.cashFormBean.elements[j];
			if(obj.type=='checkbox')
			{
				if(obj.checked==true)
				{
				 flag[k++]='T';
				}
				else 
				{
				flag[k++]='F';
				}
				
			}
			
		}
			
		//end
		for(var i=0;i<len;i++){
			var temp = document.cashFormBean.receiptAmount[i].value;
			if(flag[i+1]=='T')
			tot = parseInt(tot) + parseInt(temp);
		}
		
		
		document.getElementById("previousTotal").value = parseInt(document.getElementById("previousTotal").value) - tot;
	}else if(document.cashFormBean.receiptAmount){
		var temp = document.cashFormBean.receiptAmount.value;
		document.getElementById("previousTotal").value = parseInt(document.getElementById("previousTotal").value) - parseInt(temp);
	}
}
}

function submitBankId(){
    document.cashFormBean.method.value='showBranchDetails';
    document.cashFormBean.search.value='first';
	document.cashFormBean.action='<%=request.getContextPath()%>/Cash.do';
	document.cashFormBean.submit();
	return false;
}


function gosubmitBankId(){
    document.cashFormBean.method.value='submitSearch';
    document.cashFormBean.search.value='first';
    document.cashFormBean.bankId.value=document.getElementById('bankId').value;
	document.cashFormBean.transactionDate.value=document.getElementById('transactionDate').value;
	document.cashFormBean.action='<%=request.getContextPath()%>/Cash.do';
	document.cashFormBean.submit();
	return false;
}

function callEdit(val){

    document.cashFormBean.method.value='showBranchDetails';
    document.cashFormBean.operation.value='edit';
    document.cashFormBean.search.value='first'
   	document.cashFormBean.action='<%=request.getContextPath()%>/Cash.do';
   	document.cashFormBean.banknoedit.value=val;
   	document.cashFormBean.submit();
	return false;
}
function submitBranches(){
    document.cashFormBean.method.value='submitBankId';
	document.cashFormBean.action='<%=request.getContextPath()%>/Cash.do';
	document.cashFormBean.submit();
	return false;
}

function backReceipt(){
    document.cashFormBean.method.value='submitSearch';
    document.cashFormBean.search.value='first';
	document.cashFormBean.action='<%=request.getContextPath()%>/Cash.do';
	document.cashFormBean.submit();
	return false;
}
function backSearch(){
    document.cashFormBean.method.value='cashSearch';
	document.cashFormBean.action='<%=request.getContextPath()%>/Cash.do';
	document.cashFormBean.submit();
	return false;
}
function callPopup(val)
   {
	var URL = '<%=request.getContextPath()%>/Cash.do?method=gomatchdetail&bid='+val;
	var windowName = "Details";
	var width  = screen.availWidth;
	var height = screen.availHeight;
	var w = 600;
	var h = 430;
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
   		

function submitReceipt(){
    document.cashFormBean.method.value='submitReceipt';
	document.cashFormBean.action='<%=request.getContextPath()%>/Cash.do';
	document.cashFormBean.submit();
	return false;
}
function selectAll(box, classStyle) {
    var checked = box.checked;
    document.getElementsByClassName(classStyle).each( function(checkbox){
            checkbox.checked = checked;
        });
}

function checkAll(object)
{
	var elements=document.cashFormBean.elements;
	var tot=0;
	for(i=0; i<elements.length; i++)
		{
			var obj=document.cashFormBean.elements[i];
			if(obj.type=='checkbox')
			{
				if(object.checked==true)
				obj.checked=true;
				else
				obj.checked=false;
			}
		}
	
}
function callpage(val)
{      
      var pts="<%=request.getAttribute("PartToShow")%>";
	    if("ReceiptResult"==pts ){
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
		method="submitBankId";
		document.cashFormBean.search.value="second";
		document.cashFormBean.action="<%=request.getContextPath()%>/Cash.do?"+page+"method="+method;
       document.cashFormBean.submit();
		return false;
	  }
}
 
 
function sum() {   
     if(document.cashFormBean.receiptAmount){
	   if(document.cashFormBean.receiptAmount[0]){
		var tot = 0;
		var len = document.cashFormBean.receiptAmount.length;
		var elements=document.cashFormBean.elements;
        var flag = new Array();
        var k=0;
		for(var j=0; j<elements.length; j++)
		{
			var obj=document.cashFormBean.elements[j];
			if(obj.type=='checkbox')
			{
				if(obj.checked==true)
				{
				 flag[k++]='T';
				}
				else 
				{
				flag[k++]='F';
				}
				
			}
			
		}
			for(var i=0;i<len;i++)
			{
			if(flag[i]=='T')
			{
			tot+=parseInt(document.cashFormBean.receiptAmount[i-1].value);
			}
			}
			
			$("#sum").html(tot.toFixed(2)); 
		}
	}
  } </script>
</head>

<body onload="sumAmount()">

	<html:form action="/Cash.do">

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
		<logic:equal value="BranchDetails" name="PartToShow">
			<%--
		Start of Branch Allocate part
	--%>
			<table width="585" border="0" cellspacing="0" cellpadding="1"
				align="center">
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td class="header" width="35">
						<img src="images/subsid_ic.gif" width="32" height="32">
					</td>
					<td class="header" width="389">
						<bean:message key="branch.selected.title" />
					</td>
				</tr>
				<tr>
					<td colspan="2" class="error">
						<logic:empty name="branchList">
							<li>
								Please Allocate the Branch
							</li>
						</logic:empty>
					</td>
				</tr>
				<logic:notEmpty name="resultMsg">
					<tr>
						<td colspan="2" class="error">
							<bean:write name="resultMsg" />
						</td>
					</tr>
				</logic:notEmpty>
				<tr>
					<td colspan="2" class="tableBackGround">
						<table width="100%" border="0" cellspacing="1" cellpadding="3"
							bgcolor="#CCCCCC">
							<tr class="tableHead">
								<td width="250">
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
							<logic:notEmpty name="branchList">
								<tr>
									<td class="tableContent" colspan="2">
										<bean:message key="branch.allocation.choose" />
									</td>
								</tr>
								<tr>
									<td class="tableContent" colspan="2">
										<ul>
											<c:forEach var="main" items="${branchList}">
												<li>
													<bean:define id="id" property="branchCode" name="main" />
													<input type="checkbox" name="checkbox${main.branchCode }" value="${main.branchName }"/>
													<html:hidden property="branchCode"
														value='<%=(String) id%>' />
													<c:out value="${main.branchName}" />
   												</li>
											</c:forEach>
										</ul>
										<%--
								<%for(int i = 0; i<branchList.size(); i++) {
									CashVB cashVB= (CashVB) branchList.get(i);
								%>
								<html:checkbox property="branchCode"></html:checkbox>
								<%}%>
								--%>
									</td>
								</tr>
								<tr>
									<td class="tableContent">
									</td>
									<td class="tableContent">
									</td>
								</tr>
							</logic:notEmpty>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right" height="47">
						<html:button property="btn" styleClass="tbut"
								onclick="javascript: gosubmitBankId()" value="Back" />
						<logic:notEmpty name="branchList">
							<html:button property="btn" styleClass="tbut"
								onclick="javascript: submitBranches()" value="Submit" />
						</logic:notEmpty>
						<html:button property="btn" styleClass="tbut"
							onclick="javascript: backReceipt()" value="Cancel" />

					</td>
				</tr>
			</table>
			<%--
		End of Branch Allocate part
	--%>
		</logic:equal>

		<logic:equal name="PartToShow" value="CashSearch">

			<table align="center" border="0" align="center" width="45%"
				cellpadding="1" cellspacing="1">

				<tr style="height:2px">
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="2" width="100%" class="tbn" height="10" align="left">
						Search Cash Transaction
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
						<html:select property="bankId" name="cashFormBean">
							<html:option value="select">-Select-</html:option>
							<html:optionsCollection name="BankList" label="value" value="key" />
						</html:select>
						&nbsp;
					</td>

				</tr>
				<tr>
					<td>
						<bean:message key="cash.date" />
					</td>
					<td align="left">
						&nbsp;
						<input type="text" name="transactionDate" id="transactionDate"
							readonly="true" />
						<a href="javascript:transactionDate.popup();"
							onClick="document.getElementById('transactionDate').focus()"><img
								src="<%=request.getContextPath()%>/images/cal.gif" width="16"
								height="16" border="0" alt="Click Here Pick up the date">
						</a> &nbsp;
						<script>
		var transactionDate = new calendar1(document.forms[0].elements["transactionDate"]);
		transactionDate.year_scroll = true;
		transactionDate.time_comp = false;
	</script>
					</td>

				</tr>



				<tr>
					<td colspan="2" align="center">
						<br />
						<html:submit styleClass="tbut" value="Submit" />

					</td>

				</tr>

			</table>
			<br />
		</logic:equal>
		<logic:equal name="PartToShow" value="SearchResult">

			<table border="0" cellpadding="0" cellspacing="0" width="85%"
				align="center">
				<tr>
					<br />
					<td colspan="2" width="100%" class="tbn" height="20" align="left">
						Bank Cash Transaction
					</td>
				</tr>

				<tr>
					<br />
					<td colspan="2" width="100%" height="20" align="center">
						Bank Name:
						<b> <bean:write name="cashFormBean" property="bankName" /> </b>
						<br />
					</td>
				</tr>

				<tr>
					<td align="center" colspan="2">
						<display:table name="searchResult" pagesize="20"
							requestURI="/Cash.do?method=submitSearch" class="table" uid="row"
							id="record">
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
							<display:column style="text-align:center;width:50px"
								title="Select" sortable="true">
								<bean:define id="bankreceipt" name="record"
									property="bankreceipt"></bean:define>
								<%
								if (bankreceipt.toString().equalsIgnoreCase("")) {
								%>
								<html:radio name="cashFormBean" property="bankno"
									value="${record.bankNo}" />
								<%
								} else {
								%>
								<input type="radio" name="radio1" value="${record.bankNo}"
									disabled="true" />
								<%
								}
								%>
							</display:column>
							<display:column sortable="true"
								style="text-align:center;width:100px" title="Deposit No"
								property="depositNo" />
							<display:column sortable="true"
								style="text-align:center;width:100px" title="Deposit Date"
								property="depositDate" />
							<display:column sortable="true"
								style="text-align:center;width:100px" title="Location"
								property="location" />
							
							<display:column sortable="true"
								style="text-align:center;width:100px" title="Amount"
								property="bankAmount" />
                           <display:column sortable="true"
								style="text-align:center;width:100px" title="Matched Amount"
								property="matchedAmount" />
							<display:column style="text-align:center;width:70px"
								title="Matched" sortable="true">
								<bean:define id="bankreceipt" name="record"
									property="bankreceipt"></bean:define>
								<%
								if (bankreceipt.toString().equalsIgnoreCase("")) {
								%>No<%
								} else {
								%>
								<a href="#" name="${record.bankNo}"
									onclick="return callPopup(this.name);" style="cursor: pointer;">View
								</a>/
		<a href="#" name="${record.bankNo}"
									onclick="return callEdit(this.name);" style="cursor: pointer;">Edit</a>
								<%
								}
								%>

							</display:column>

						</display:table>

					</td>

				</tr>

				<tr style="height:10px">
					<td>
						&nbsp;
					</td>
				</tr>
				<logic:notEmpty name="searchResult">

					<tr>
						<td colspan="3" align="center">
							<br />
							<html:button property="btn" styleClass="tbut"
								onclick="javascript: gosearch()" value="Back" />
							<html:button property="btn" styleClass="tbut" value="Submit"
								style="width:65px" onclick="return submitBankId()" />
                           <html:button property="btn" styleClass="tbut"
								onclick="javascript: backSearch()" value="Cancel" />
						</td>
					</tr>
				</logic:notEmpty>
				<logic:empty name="searchResult">

					<tr>
						<td colspan="3" align="center">
							<br />
							<html:button property="btn" styleClass="tbut"
								onclick="javascript: goBack()" value="Back" />
							
						</td>
					</tr>
				</logic:empty>
			</table>
			<br />
			<br />
		</logic:equal>

		<logic:equal name="PartToShow" value="ReceiptResult">

			<table border="0" cellpadding="0" cellspacing="0" width="85%"
				align="center">
				<tr>
					<br />
					<td colspan="2" width="100%" class="tbn" height="20" align="left">
						Receipt Details
					</td>
				</tr>

				<tr>
					<br />
					<td colspan="1" width="100%" height="40" align="center">
						Bank Name:
						<b><bean:write name="cashFormBean" property="bankName" /> </b>&nbsp;&nbsp;&nbsp;&nbsp;Bank
						Location:
						<b><bean:write name="cashFormBean" property="bankLocation" />
						</b> &nbsp;&nbsp;&nbsp;&nbsp;Bank Amount:
						<b><bean:write name="cashFormBean" property="bankAmount" />
						</b>
					</td>
				</tr>

				<tr>
					<br />
					<td colspan="1" width="100%" height="40" align="center">
						Minimum Amount:
						<b><bean:write name="cashFormBean" property="minamount" /> </b>
						&nbsp;&nbsp;&nbsp;&nbsp;Maximum Amount:
						<b><bean:write name="cashFormBean" property="maxamount" />
						</b> &nbsp;&nbsp;&nbsp;&nbsp;Selected Sum:
						<font color="blue"><b> <html:text property="sumAmount"
									styleId="sumAmount" size="15" readonly="true" /> <input
									type="hidden" name="previousTotal" id="previousTotal"
									value='<bean:write name="cashFormBean" property="sumAmount" />' />
						</b>
						</font>
					</td>
				</tr>
				<logic:notEmpty name="checkstatus">
					<tr>
						<br />
						<td colspan="1" width="100%" height="40" align="center">
							<font color="red"><b><%=request.getAttribute("checkstatus")%>
							</b>
							</font>
						</td>
					</tr>
				</logic:notEmpty>

				<tr>
					<td align="center" colspan="2">
					
					     <br />
					   	<display:table name="receiptResult" pagesize="10"
							requestURI="/Cash.do?method=submitBankId&pagination=true" class="table" uid="row"
							id="record">
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
							<display:column sortable="true" style="width: 5%"
								title='<input type="checkbox" name="choosen" onclick="checkAll(this);addAmount()"/>'
								media="html">
								<bean:define id="status" name="record" property="status" />
								<logic:equal property="status" name="record" value="Y">
									<input type="checkbox" name="checkbox${record.receiptslno}"
										value="${record.status}"
										checked="checked"
										onclick="addAmount()" />
								</logic:equal>	
                                <logic:notEqual property="status" name="record" value="Y">
								<input type="checkbox" name="checkbox${record.receiptslno}"
									value="${record.status}"
									onclick="addAmount()" />	
								</logic:notEqual>	
								<input type="hidden" name="hidden${record.receiptslno}"
									value="${record.status}" />
							</display:column>

							<display:column sortable="true"
								style="text-align:center;width:100px" title="Receipt No"
								property="receiptNo" />
							<display:column sortable="true"
								style="text-align:center;width:100px" title="Receipt Date"
								property="receiptDate" />
							<display:column sortable="true"
								style="text-align:center;width:170px" title="Receipt AG Name"
								property="receiptAGName" />
							<display:column sortable="true"
								style="text-align:center;width:100px"
								title="Receipt Branch Name" property="receiptBranchCode" />
							<display:column sortable="true"
								style="text-align:center;width:100px" title="Transaction Source"
								property="tranSource" />
							<display:column sortable="true"
								style="text-align:center;width:100px" title="Amount">
								${record.receiptAmount}
								<html:hidden name="record" property="receiptAmount" />
													</display:column>
						</display:table>

					</td>

				</tr>

				<tr style="height:10px">
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="3" align="center">
						<br />
						<html:button property="btn" styleClass="tbut"
								onclick="return gobranchDetails()" value="Back" />
						<logic:notEmpty name="receiptResult">
							<html:button property="btn" styleClass="tbut" value="Submit"
								style="width:65px" onclick="return submitReceipt()" />
						</logic:notEmpty>
						<html:button property="btn" styleClass="tbut" value="Cancel"
							style="width:65px" onclick="return backReceipt()" />

					</td>
				</tr>
			</table>
			<br />
			<br />
		</logic:equal>
         <logic:equal name="PartToShow" value="Success">
         <table align="center" height="300px">
         <tr><td>
	         <center><font color="green">
	           <b>Records Submitted Successfully</b>
			 </font>
			 <br />
	         <html:button property="btn" styleClass="tbut" value="OK"
								style="width:65px" onclick="return backReceipt()" />
			</center>
			</td></tr>
			</table>
         </logic:equal>
		<html:hidden property="method" value="submitSearch" />
		<html:hidden property="operation" styleId="operation"/>
		<html:hidden property="banknoedit" styleId="banknoedit"/>
		<html:hidden property="search" styleId="search"/>
		<html:hidden property="bankId" styleId="bankId"/>
		<html:hidden property="transactionDate" styleId="transactionDate"/>
		<html:hidden property="branchCodes"/>
	</html:form>

</body>

</html:html>
