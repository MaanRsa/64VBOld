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
    
    <title>addNewBankRejection.jsp</title>

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
   <html:form action="/bankRejection"   method="POST"  styleId="mainForm">


<logic:equal value="DisplayBankRejection" name="show">
<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="bankreject.heading" />
			</td>	
		</tr>	
		<tr class="tabin" >
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
									<display:table name="list" pagesize="10" requestURI="/bankRejection.do?method=newBankRejection" excludedParams="*" class="table" uid="row" id="record">
									<display:setProperty name="paging.banner.one_item_found"   value="" />
									<display:setProperty name="paging.banner.one_items_found"  value="" />
									<display:setProperty name="paging.banner.all_items_found"  value="" />
									<display:setProperty name="paging.banner.some_items_found" value="" />
									<display:setProperty name="paging.banner.placement" value="bottom" />
									<display:setProperty name="paging.banner.onepage" value="" />
								   
									<display:column sortable="true" title="Rejection Description" property="rejectiontypedesc" style="text-align:left;" />
									<display:column sortable="true" title="Rejection Type Id"  property="rejectiontypeid" style="text-align:left;"/>
									<display:column sortable="true" title="Status"  property="status" style="text-align:left;"/>
									<display:column style="text-align:center;" sortable="true" 	title="Action">
									<a style="color:000000" href="#" onclick="javascript: edit('<bean:write name="record" property="rejectionid"/>')"/>
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
				<bean:message key="bankreject.heading" />
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
		<tr class="tabin">	
			<td class="t" align="right">
				<b>*&nbsp;<bean:message key="admin.bankreject.rejectid" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
				<html:text property="rejectiontypeid" styleId="rejectiontypeid" maxlength="2" />
			
			</td>
		</tr>
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bankreject.rejectdesc" /> &nbsp; : </b>
			</td>
			<td class="t">
				<html:textarea property="rejectiontypedesc" styleId="rejectiontypedesc" />
			</td>
			
		</tr>
		
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bankreject.active" /> &nbsp; : </b>
			</td>
			<td class="t" align="left">
				<html:radio  styleId="active"  property="status" value="Y " />
					Yes
				<html:radio styleId="active" property="status" value="N " />
					No
		    </td>
			
		</tr>
        <tr class="tabin">
       		
			<td class="t" colspan="2" align="center" >
			   <input type="button" value="Submit" class="button" onclick="insertBankRejection()" /> 
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
				<bean:message key="bankreject.heading" />
			</td>	
		</tr>	
		<tr class="tabin" >
			<td colspan="4" align="center">			    
				<html:messages id="message" message="true">
					<li style="color: red;">
						<bean:write name="message" />
					</li>
				</html:messages>
			</td>
		</tr>		
		<tr class="tabin">	
			<td class="t" align="right">
				<b><bean:message key="admin.bankreject.rejectid" /> &nbsp; : </b>
			</td>
			<td align="left" class="t">
				<html:hidden property="rejectiontypeid" styleId="rejectiontypeid"  />
			     <bean:write name="bankRejectionForm" property="rejectiontypeid"/>
			</td>
		</tr>
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bankreject.rejectdesc" /> &nbsp; : </b>
			</td>
			<td class="t">
				<html:textarea property="rejectiontypedesc" styleId="rejectiontypedesc" />
			</td>
			
		</tr>
		
		<tr  class="tabin">
			<td class="t" align="right">
				<b><bean:message key="admin.bankreject.active" /> &nbsp; : </b>
			</td>
			<td class="t" align="left">
				<html:radio  styleId="active"  property="status" value="Y " />
					Yes
				<html:radio styleId="active" property="status" value="N " />
					No
		    </td>
			
		</tr>
		
		<tr class="tabin">
	          		
					
					<td class="t" colspan="2" align="center" >
							   <input type="button" value="Update" class="button" onclick="updateBankRejection('<bean:write name='bankRejectionForm'   property="rejectionid"/>')" /> 
							   <input type="button" value="Back" class="button" onclick="back2()" /> 
				    </td>
				    
				   
				</tr>
	
		
		</table>
				
		
	
	
	</logic:equal>
    <html:hidden property="page" styleId="page"/>
	<html:hidden property="rejectionid" styleId="rejectionid"/>
	</html:form>
  </body>
</html:html>

<script>

 function updateBankRejection(rejectionid)
  {
    document.getElementById('rejectionid').value=rejectionid;
   //alert(document.getElementById('rejectionid').value);
     document.mainForm.action="<%=request.getContextPath()%>/bankRejection.do?method=updateBankRejection";
     //alert(document.mainForm.action);
     document.mainForm.submit();
     return false;
  }
  
  function addNew()
  {
    //alert("hi");
     document.mainForm.action="<%=request.getContextPath()%>/bankRejection.do?method=addNewBankRejection";
     //alert(document.mainForm.action);
     document.mainForm.submit();
     return false;
  }
  
  function insertBankRejection()
  {
    //alert('hi');
    document.mainForm.action="<%=request.getContextPath()%>/bankRejection.do?method=insertNewBankRejection";
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
window.history.forward();
 document.mainForm.action="<%=request.getContextPath()%>/bankRejection.do?method=newBankRejection";
 document.mainForm.submit();
    return false;
}


function back2()
{
 var pageval=document.getElementById('page').value;
window.history.forward();
 document.mainForm.action="<%=request.getContextPath()%>/bankRejection.do?method=newBankRejection&d-446779-p="+pageval;
 document.mainForm.submit();
    return false;
}

function edit(rejectionid)
{
	var page=<%=request.getParameter("d-446779-p")%>;
    //alert(page);
    document.getElementById('page').value=page;
 	 //alert(rejectionid);
 	document.getElementById('rejectionid').value=rejectionid;
   // alert(document.getElementById('rejectionid').value);
    document.mainForm.action="<%=request.getContextPath()%>/bankRejection.do?method=getEdit";
    document.mainForm.submit();
    return false;
}
</script>
