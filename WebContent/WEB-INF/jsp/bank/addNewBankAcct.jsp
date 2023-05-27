<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
  <head>
    <html:base />
   
    <title>addNewBankAcct.jsp</title>

	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>  
  <body>
   <html:form action="/bankAcct"   method="POST"  styleId="mainForm">


<logic:equal value="DisplayBankAcct" name="show">
<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="bankacct.heading" />
			</td>	
		</tr>	
		<tr>
			<td colspan="4" align="center">			    
				<html:messages id="message" message="true">
					<li style="color: red;">
						<bean:write name="message" />
					</li>
				</html:messages>
			</td>
		</tr>		
		<tr>
			<td colspan="8">
					<table align="center" width="100%" border="0" cellspacing="0" cellpadding="5">
							<tr class="">
								<td class="">
									<display:table name="list" pagesize="10" requestURI="/bankAcct.do?method=newBankAcct" excludedParams="*" class="table" uid="row" id="record">
										<display:setProperty name="paging.banner.one_item_found"   value="" />
										<display:setProperty name="paging.banner.one_items_found"  value="" />
										<display:setProperty name="paging.banner.all_items_found"  value="" />
										<display:setProperty name="paging.banner.some_items_found" value="" />
										<display:setProperty name="paging.banner.placement" value="bottom" />
										<display:setProperty name="paging.banner.onepage" value="" />
									   
										<display:column sortable="true" title="Bank Name"  property="bankid" style="text-align:center;"/>
										<display:column sortable="true" title="Bank Account No"  property="bankAcctNo" style="text-align:left;"/>
										<display:column sortable="true" title="Bank Account Code"  property="bankAcctCode" style="text-align:left;"/>
										<display:column sortable="true" title="Status"  property="status" style="text-align:left;"/>
										<display:column style="text-align:center;" sortable="true" 	title="Action">
										<a style="color:000000" href="#" onclick="javascript: edit('<bean:write name="record" property="bankAcctCode"/>')"/>
										<input type="button" value="Edit" class="button">
										</a>
										</display:column>
									</display:table>
								</td>							
							</tr>								
							<tr>
							   <td align="center">
							   		<input type="button" value="Add New" class="button" onclick="addNew()" /> 
						      </td>
							</tr>							
					</table>
				</td>
			</tr>		
		<tr>
			<td colspan="3">
				&nbsp;
			</td>
		</tr>
	</table>
	<script>
	function edit(bankAcctCode)
	{
	      //alert(bankAcctCode);
	    var page=<%=request.getParameter("d-446779-p")%>;
	    //alert(page);
	    document.getElementById('page').value=page;
	    document.getElementById('bankAcctCode').value=bankAcctCode;
	    // alert(document.getElementById('bankid').value);
	    document.mainForm.action="<%=request.getContextPath()%>/bankAcct.do?method=getEdit";
	    document.mainForm.submit();
	    return false;
	}
	</script>
	</logic:equal>
	
	<logic:equal name="show" value="showAddPage">		
	<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="bankacct.heading" />
			</td>	
		</tr>	
		
		<tr class="tabin" >
			<td colspan="4" align="center">			    
				<html:messages id="message" message="true">
					<li style="color: red;">
						<bean:write name="message"/>
					</li>
				</html:messages>
			</td>
		</tr>		
		<tr  class="tabin" >				
						
			<td class="t" align="right" >
				<b><bean:message key="admin.bankacct.bankname" /> &nbsp; : </b>
			</td>
		
			<td align="left" class="t">
				<html:select property="bankid">
				<html:option value="Select">-Select-</html:option>
				<html:optionsCollection name="BankList" label="value" value="key" />
				</html:select>
				
			</td>
		</tr>
		<tr  class="tabin">				
						
			<td class="t" align="right">
				<b><bean:message key="admin.bankacct.acctno" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
				<html:text property="bankAcctNo" styleId="bankAcctNo" maxlength="20"/>
			</td>
		</tr>
		<tr  class="tabin">				
						
			<td class="t" align="right">
				<b>*&nbsp;<bean:message key="admin.bankacct.acctcode" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
				<html:text property="bankAcctCode" styleId="bankAcctCode" maxlength="5" />
			</td>
		</tr>
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bankacct.active" /> &nbsp; : </b>
			</td>
		
			<td class="t" align="left">
				<html:radio  styleId="act"  property="status" value="Y " />
					Yes
				<html:radio styleId="act" property="status" value="N " />
					No
		    </td>
		</tr>
		
        <tr class="tabin">
       		
			<td class="t" align="center" colspan="2">
			   <input type="button" value="Submit" class="button" onclick="insertBankAcct()" /> 
			   <input type="button" value="Back" class="button" onclick="back1()" /> 
		    </td>
			
		</tr>	
	</table>
	
</logic:equal>
	
<logic:equal name="show" value="edit">
		<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="bankacct.heading" />
			</td>	
		</tr>	
		<tr class="tabin" >
			<td colspan="4" align="center">			    
				<html:messages id="message" message="true">
					<li style="color: red;">
						<bean:write name="message"/>
					</li>
				</html:messages>
			</td>
		</tr>		
		<tr  class="tabin" >				
						
			<td class="t" align="right" >
				<b><bean:message key="admin.bankacct.bankname" /> &nbsp; : </b>
			</td>
		
			<td align="left" class="t">
				<html:select property="bankid">
				<html:option value="Select">-Select-</html:option>
				<html:optionsCollection name="BankList" label="value" value="key" />
				</html:select>
				
			</td>
		</tr>
		<tr  class="tabin">				
						
			<td class="t" align="right">
				<b><bean:message key="admin.bankacct.acctno" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
				<html:text property="bankAcctNo" styleId="bankAcctNo" maxlength="20" />
			</td>
		</tr>
		<tr  class="tabin">				
						
			<td class="t" align="right">
				<b><bean:message key="admin.bankacct.acctcode" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
			 <bean:write name="bankAcctForm" property="bankAcctCode" />
				<html:hidden property="bankAcctCode" styleId="bankAcctCode"  />
			</td>
		</tr>
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bankacct.active" /> &nbsp; : </b>
			</td>
		
			<td class="t" align="left">
				<html:radio  styleId="act"  property="status" value="Y " />
					Yes
				<html:radio styleId="act" property="status" value="N " />
					No
		    </td>
		</tr>
			    <tr class="tabin">
		       
				<td class="t" align="center" colspan="2" >
				   <input type="button" value="Update" class="button" onclick="updateBank('<bean:write name='bankAcctForm' property="bankAcctCode"/>')" /> 
				   <input type="button" value="Back" class="button" onclick="back2()" /> 
			    </td>
			   	</tr>
		
		
		</table>
				
		
	
	
	</logic:equal>
    <html:hidden property="bankAcctCode" styleId="bankAcctCode"/>
	<html:hidden property="page" styleId="page"/>
	</html:form>
  </body>
</html:html>

<script>

 function updateBank(bankAcctCode)
  {
    document.getElementById('bankAcctCode').value=bankAcctCode;
   //alert(document.getElementById('bankid').value);
     document.mainForm.action="<%=request.getContextPath()%>/bankAcct.do?method=updateBankAcct";
     //alert(document.mainForm.action);
     document.mainForm.submit();
     return false;
  }
  
  function addNew()
  {
    //alert("hi");
     document.mainForm.action="<%=request.getContextPath()%>/bankAcct.do?method=addNewBankAcct";
     //alert(document.mainForm.action);
     document.mainForm.submit();
     return false;
  }
  
  function insertBankAcct()
  {
    //alert('hi');
    document.mainForm.action="<%=request.getContextPath()%>/bankAcct.do?method=insertNewBankAcct";
    document.mainForm.submit();
    return false;
  }
  
function fnCancel(){
document.getElementById("bankid").value="";
document.getElementById("bankacctcode").value="";
document.getElementById("bankacctno").value="";
document.getElementById("bankname").value="";
document.getElementById("active").value="";

}
function back1()
{
window.history.forward();
 document.mainForm.action="<%=request.getContextPath()%>/bankAcct.do?method=newBankAcct";
 document.mainForm.submit();
    return false;
}

function back2()
{
 var pageval=document.getElementById('page').value; 
 window.history.forward();
 document.mainForm.action="<%=request.getContextPath()%>/bankAcct.do?method=newBankAcct&d-446779-p="+pageval;
 document.mainForm.submit();
 return false;
}
</script>
