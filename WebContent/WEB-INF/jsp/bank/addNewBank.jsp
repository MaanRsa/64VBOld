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
    
    <title>addNewBank.jsp</title>

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
   <html:form action="/bank"   method="POST"  styleId="mainForm">


<logic:equal value="DisplayBank" name="show">
<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="bank.heading" />
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
									<display:table name="list" pagesize="10" requestURI="/bank.do?method=newBank" excludedParams="*" class="table" uid="row" id="record">
										<display:setProperty name="paging.banner.one_item_found"   value="" />
										<display:setProperty name="paging.banner.one_items_found"  value="" />
										<display:setProperty name="paging.banner.all_items_found"  value="" />
										<display:setProperty name="paging.banner.some_items_found" value="" />
										<display:setProperty name="paging.banner.placement" value="bottom" />
										<display:setProperty name="paging.banner.onepage" value="" />
									   
										<display:column sortable="true" title="Bank ID"  property="bankid" style="text-align:left;"/>
										<display:column sortable="true" title="Bank Name"  property="bankname" style="text-align:left;"/>
										<display:column sortable="true" title="Table Name" property="banktable" style="text-align:left;" />
										<display:column sortable="true" title="Cheque No Field"  property="chequeno" style="text-align:left;"/>
										<display:column sortable="true" title="Cheque Amount Field"  property="chequeamt" style="text-align:left;"/>
										<display:column sortable="true" title="Credit/Debit Field"  property="chequestatus" style="text-align:left;"/>
										
										<display:column sortable="true" title="Return Reason Field"  property="reason" style="text-align:left;"/>
										<display:column sortable="true" title="Receipt No Field"  property="receiptNo" style="text-align:left;"/>
				
										<display:column sortable="true" title="Active" property="active" style="text-align:left;" />
										<display:column style="text-align:center;" sortable="true" 	title="Action">
													<a style="color:000000" href="#" onclick="javascript: edit('<bean:write name="record" property="bankid"/>')"/>
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
	</logic:equal>
	
	<logic:equal name="show" value="showAddPage">		
	<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="bank.heading" />
			</td>	
		</tr>	
		<tr class="tabin">
			<td colspan="4" align="center">			    
				<html:messages id="message" message="true">
					<li style="color: red;">
						<bean:write name="message"/>
					</li>
				</html:messages>
			</td>
		</tr>		
		<tr  class="tabin">				
			<td class="t" align="right">
				<b>*&nbsp;<bean:message key="admin.bank.bankid" /> &nbsp; : </b>
			</td>	
			<td class="t">
				<html:text property="bankid" styleId="bankid" maxlength="3"/>
			</td>			
			<td class="t" align="right">
				<b><bean:message key="admin.bank.bankname" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
				<html:text property="bankname" styleId="bankname" maxlength="20" />
			</td>
		</tr>
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bank.banktable" /> &nbsp; : </b>
			</td>
			<td class="t">
				<html:text property="banktable" styleId="banktable" maxlength="20" />
			</td>
			<td class="t" align="right">
				<b><bean:message key="admin.bank.chequeno" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
					<html:text property="chequeno" styleId="chequeno" maxlength="20" />
					
			</td>
		</tr>
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bank.chequeamt" /> &nbsp; : </b>
			</td>
			<td class="t">
				<html:text property="chequeamt" styleId="chequeamt"  maxlength="20"/>
			</td>
			<td class="t" align="right">
				<b><bean:message key="admin.bank.chequestatus" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
					<html:text property="chequestatus" styleId="chequestatus"  maxlength="20"/>
					
			</td>
		</tr>
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bank.receiptno" /> &nbsp; : </b>
			</td>
			<td class="t" align="left">
				<html:text property="receiptNo" styleId="receiptNo"  maxlength="20"/>
		    </td>
			<td class="t" align="right">
				<b><bean:message key="admin.bank.reason" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
					<html:text property="reason" styleId="reason"  maxlength="20"/>
					
			</td>
		</tr>
		<tr class="tabin">
		<td class="t" align="right">
				<b><bean:message key="admin.bank.active" /> &nbsp; : </b>
			</td>
			<td class="t" align="left">
				<html:radio  styleId="act"  property="active" value="Y " />
					Yes
				<html:radio styleId="act" property="active" value="N " />
					No
		    </td>
		    <td class="t" colspan="2"> &nbsp;</td>
		</tr>
        <tr class="tabin">
       		<td class="t" align="right" colspan="2">
			</td>
			<td class="t" >
			   <input type="button" value="Submit" class="button" onclick="insertBank()" /> 
			   <input type="button" value="Back" class="button" onclick="back1()" /> 
		    </td>
			<td class="t" align="right" colspan="2">
			</td>
		</tr>	
	</table>
	
</logic:equal>
	
<logic:equal name="show" value="edit">
	<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="bank.heading" />
			</td>	
		</tr>	
		<tr class="tabin">
			<td colspan="4" align="center">			    
				<html:messages id="message" message="true">
					<li style="color: red;">
						<bean:write name="message" />
					</li>
				</html:messages>
			</td>
		</tr>		
		<tr  class="tabin">				
			<td class="t" align="right">
				<b><bean:message key="admin.bank.bankid" /> &nbsp; : </b>
			</td>	
			<td class="t">
			    <bean:write name="bankForm" property="bankid" />
				<html:hidden property="bankid" styleId="bankid"  />
				</td>			
			<td class="t" align="right">
				<b><bean:message key="admin.bank.bankname" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
				<html:text property="bankname" styleId="bankname"  maxlength="20" />
			</td>
		</tr>
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bank.banktable" /> &nbsp; : </b>
			</td>
			<td class="t">
				<html:text property="banktable" styleId="banktable"  maxlength="20"/>
			</td>
			<td class="t" align="right">
				<b><bean:message key="admin.bank.chequeno" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
					<html:text property="chequeno" styleId="chequeno"  maxlength="20"/>
					
			</td>
		</tr>
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bank.chequeamt" /> &nbsp; : </b>
			</td>
			<td class="t">
				<html:text property="chequeamt" styleId="chequeamt"  maxlength="20"/>
			</td>
			<td class="t" align="right">
				<b><bean:message key="admin.bank.chequestatus" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
					<html:text property="chequestatus" styleId="chequestatus" maxlength="20" />
					
			</td>
		</tr>
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bank.receiptno" /> &nbsp; : </b>
			</td>
			<td class="t" align="left">
				<html:text property="receiptNo" styleId="receiptNo"  maxlength="20"/>
		    </td>
			<td class="t" align="right">
				<b><bean:message key="admin.bank.reason" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
					<html:text property="reason" styleId="reason"  maxlength="20"/>
					
			</td>
		</tr>
		<tr class="tabin">
		<td class="t" align="right">
				<b><bean:message key="admin.bank.active" /> &nbsp; : </b>
			</td>
			<td class="t" align="left">
				<html:radio  styleId="act"  property="active" value="Y " />
					Yes
				<html:radio styleId="act" property="active" value="N " />
					No
		    </td>
		    <td class="t" colspan="2"> &nbsp;</td>
		</tr>
				     
	          <tr class="tabin">
	          		<td class="t" align="right" colspan="2">
							
				</td>
			
					<td class="t" >
							   <input type="button" value="Update" class="button" onclick="updateBank('<bean:write name='bankForm'   property="bankid"/>')" /> 
							   <input type="button" value="Back" class="button" onclick="back2()" /> 
				    </td>
				    
				    <td class="t" align="right" colspan="2">
							
					</td>
				</tr>
	
		
		</table>
				
		
	
	
	</logic:equal>

	<html:hidden property="bankid" styleId="bankid"/>
	<html:hidden property="page" styleId="page"/>
	</html:form>
  </body>
</html:html>

<script>

 function updateBank(bankid)
  {
    document.getElementById('bankid').value=bankid;
   //alert(document.getElementById('bankid').value);
     document.mainForm.action="<%=request.getContextPath()%>/bank.do?method=updateBank";
     //alert(document.mainForm.action);
     document.mainForm.submit();
     return false;
  }
  
  function addNew()
  {
    //alert("hi");
     document.mainForm.action="<%=request.getContextPath()%>/bank.do?method=addNewBank";
     //alert(document.mainForm.action);
     document.mainForm.submit();
     return false;
  }
  
  function insertBank()
  {
    //alert('hi');
    document.mainForm.action="<%=request.getContextPath()%>/bank.do?method=insertNewBank";
    document.mainForm.submit();
    return false;
  }
  
function fnCancel(){
document.getElementById("bankid").value="";
document.getElementById("bankname").value="";
document.getElementById("banktable").value="";
document.getElementById("edate").value="";
document.getElementById("active").value="";

}
function back1()
{

 document.mainForm.action="<%=request.getContextPath()%>/bank.do?method=newBank";
window.history.forward();
 document.mainForm.submit();
   return false;
}
function back2()
{
	window.history.forward();
	var pageval=document.getElementById('page').value;
	document.mainForm.action="<%=request.getContextPath()%>/bank.do?method=newBank&d-446779-p="+pageval;
	document.mainForm.submit();
    return false;
}
function edit(bankid)
{
   
    var page=<%=request.getParameter("d-446779-p")%>;
    //alert(page);
    document.getElementById('page').value=page;
 	document.getElementById('bankid').value=bankid;
    // alert(document.getElementById('bankid').value);
    document.mainForm.action="<%=request.getContextPath()%>/bank.do?method=getEdit";
    document.mainForm.submit();
    return false;
}
</script>
