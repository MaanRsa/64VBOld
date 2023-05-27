<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'addNewBranchMaster.jsp' starting page</title>
    
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
  <html:form action="/branchMaster" method="POST" styleId="mainForm">
  
  <logic:equal value="Display" name="page">
  <table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="branchMaster.heading" />
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
									<display:table name="lists" pagesize="10" requestURI="/branchMaster.do?method=InitBranch" excludedParams="*" class="table" uid="row" id="record">
										<display:setProperty name="paging.banner.one_item_found"   value="" />
										<display:setProperty name="paging.banner.one_items_found"  value="" />
										<display:setProperty name="paging.banner.all_items_found"  value="" />
										<display:setProperty name="paging.banner.some_items_found" value="" />
										<display:setProperty name="paging.banner.placement" value="bottom" />
										<display:setProperty name="paging.banner.onepage" value="" />
										
										<display:column sortable="true" title="Branch Name"  property="branch_Name" style="text-align:center;"/>
										<display:column sortable="true" title="Branch Code"  property="branch_Code" style="text-align:center;"/>
										<display:column style="text-align:center;" sortable="true" 	title="Action">
										<a style="color:000000" href="#" onclick="javascript: edit('<bean:write name="record" property="branch_Id"/>')"/>
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
  <logic:equal value="addNew" name="page">
  <table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="branchMaster.heading" />
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
				<b><bean:message key="admin.branch.branch_Name" /> &nbsp; : </b>
			</td>	
			<td class="t" align="left">
				<html:text property="branch_Name" styleId="branch_Name" maxlength="20"/>
			</td>
		</tr>
		<tr>			
			<td class="t" align="right">
				<b><bean:message key="admin.branch.branch_Code" /> &nbsp; : </b>
			</td>
			<td class="t" align="left">
				<html:text property="branch_Code" styleId="branch_Code" maxlength="20" />
			</td>
		</tr>
		 <tr class="tabin">
       		
			<td class="t" align="center" colspan="2">
			   <input type="button" value="Submit" class="button" onclick="insertBranchMaster()" /> 
			   <input type="button" value="Back" class="button" onclick="back1()" /> 
		    </td>
			
		</tr>	
	</table>
  
  </logic:equal>
  <logic:equal value="Edit" name="page">
   <table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			<td class="header" colspan="4">
				<img src="images/submitcut.gif" width="25" height="26">&nbsp;&nbsp;
				<bean:message key="branchMaster.heading" />
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
				<b><bean:message key="admin.branch.branch_Name" /> &nbsp; : </b>
			</td>	
			<td class="t">
				<html:text property="branch_Name" styleId="branch_Name" maxlength="20"/>
			</td>
		</tr>
		<tr>			
			<td class="t" align="right">
				<b><bean:message key="admin.branch.branch_Code" /> &nbsp; : </b>
			</td>
			<td class="t">
				<html:text property="branch_Code" styleId="branch_Code" maxlength="20" />
			</td>
		</tr>
   <tr class="tabin">
		       
				<td class="t" align="center" colspan="2" >
				   <input type="button" value="Update" class="button" onclick="updateBank('<bean:write name='branchMasterForm' property="branch_Id"/>')" /> 
				   <input type="button" value="Back" class="button" onclick="back2()" /> 
			    </td>
			   	</tr>
		</table>
  </logic:equal>
  <html:hidden property="pagenavination" styleId="pagenavination"/>
  <html:hidden property="branch_Id" styleId="branch_Id"/>
  </html:form>
    </body>
</html:html>
  <script>
   function addNew()
  {
   
     document.mainForm.action="<%=request.getContextPath()%>/branchMaster.do?method=addNewBankMaster";
     //alert(document.mainForm.action);
     document.mainForm.submit();
     return false;
  }
  
  function insertBranchMaster()
  {
    document.mainForm.action="<%=request.getContextPath()%>/branchMaster.do?method=InsertBranchMaster";
    document.mainForm.submit();
    return false; 
  }
  function back1()
  {
   window.history.forward();
   document.mainForm.action="<%=request.getContextPath()%>/branchMaster.do?method=InitBranch";
   document.mainForm.submit();
    return false;
  }
  function back2()
{
 var pageval=document.getElementById('pagenavination').value;
 window.history.forward();
 document.mainForm.action="<%=request.getContextPath()%>/branchMaster.do?method=InitBranch&d-446779-p="+pageval;
 document.mainForm.submit();
    return false;
}
  function edit(branch_Id)
  {
    var pagenav=<%=request.getParameter("d-446779-p")%>;
    document.getElementById('pagenavination').value=pagenav;
 	document.getElementById('branch_Id').value=branch_Id;
    document.mainForm.action="<%=request.getContextPath()%>/branchMaster.do?method=EditBranchMaster";  
    document.mainForm.submit();
    return false;
  }
 function updateBank(branch_Id)
 {
 
   document.getElementById('branch_Id').value=branch_Id;
   document.mainForm.action="<%=request.getContextPath()%>/branchMaster.do?method=UpdateBranchMaster";
   document.mainForm.submit();
   return false;
 }
  
  
  </script>
  
 

