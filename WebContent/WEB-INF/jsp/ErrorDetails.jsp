<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>

<%@ taglib uri="../displaytag.tld" prefix="display"%>
	
	
<script type="text/javascript">

	function submitForm(val){
      document.getElementById("method").value=val;
	  document.getElementById("mainForm").submit();	       
	}
	
	function cancel(){		
		document.getElementById("mainForm").action = "<%=request.getContextPath()%>/menu.do?method=adminMenu";
		document.getElementById("mainForm").submit();
	}
	
	function update(id,path){	
  	  document.getElementById("updateErrorId").value = id;  	  
      document.getElementById("method").value=path;
	  document.getElementById("mainForm").submit();	       
	}
	
</script>

<html:form action="/error" styleId="mainForm">

<logic:equal value="NewError" name="path">
		<table width="100%" border="0" cellspacing="0" cellpadding="5">
		<tr>
			   <td class="header" width="40" height="44">
					<img src="images/submitcut.gif" width="25" height="26">
			   </td>
			   <td class="test" colspan="4">
					<bean:message key="error.heading" />
			   </td>
			   <td class="s" width="150" height="44" align="center">
					<a href="Home.htm"></a>
			   </td>
			   <td>&nbsp;</td>					
		</tr>
		
		<tr>
			<td>
					<html:messages id="message" message="true">
						<li style="color: red;">
							<bean:write name="message" />
						</li>
					</html:messages>
			</td>
			<td colspan="3">&nbsp;</td>				
		</tr>		
		</table>
		
	<table width="100%" border="0" cellspacing="0" cellpadding="5">
	<tr class="tabin">
	  <td class="t">
			<bean:message key="error.errorCode"/>
	   </td>
	   <td align="left" class="t">
			<html:text property="errorCode" styleId="errorCode" maxlength = "10"></html:text>
		</td>
	</tr>
	
	<tr class="tabin">
	    <td class="t">
  		   <bean:message key="error.errorDesc"/>
	    </td>
	    <td align="left" class="t">
		   <html:text property="errorDesc" styleId="errorDesc" maxlength = "90"></html:text>
	    </td>
	</tr>
	
	<tr class="tabin">
			 <td class="t">
					<bean:message key="error.active" />
				</td>

				<td class="t">
					<html:radio property="active" value="1"/>Yes
					<html:radio property="active" value="0"/>No
				</td>
	</tr>
	
	  <tr class="tabin">
				<td align="center" colspan="2" class="t" >
					<input type="button" value="Submit" onclick="javascript: submitForm('SubmitError')" class="button" />						
					<input type="button" value="cancel" onclick="javascript: cancel()" class="button"/>					
					<input type="button" value="List" onclick="javascript: submitForm('ErrorList')" class="button"/>
				</td>				
 	  </tr>	
	</table>
	<html:hidden property="mode" styleId="mode" />	
	<html:hidden property="method" styleId="method" />
</logic:equal>

<logic:equal value="ErrorList" name="path">

 	<table width="100%" border="0" cellspacing="0" cellpadding="5">
			<tr>
			 <td class="header" width="40" height="44">
					<img src="images/submitcut.gif" width="25" height="26">
				</td>
				<td class="test" colspan="4">
					<bean:message key="error.heading"/>
				</td>
				<td class="s" width="150" height="44" align="center">
					<a href="Home.htm"></a>
				</td>
				<td>
					&nbsp;
				</td>	
			</tr>
			<tr>
				<td colspan="4">
					<html:messages id="message" message="true">
						<li style="color: red;">
							<bean:write name="message" />
						</li>
					</html:messages>
				</td>
			</tr>
		</table>
		
		<table width="75%" border="0" cellspacing="0" cellpadding="5">
			<tr>
				<td><bean:message key="error.searchBy" /></td>				
				<td>
					<html:select property="searchBy" styleId="searchBy">
						<html:option value="select" styleId="select">-Select-</html:option>
						<html:option value="errorcode" styleId="">Error Code</html:option>
						<html:option value="errorname" styleId="">Error Name</html:option>
					</html:select>
				</td>				
				<td><bean:message key="error.searchValue" /></td>				
				<td>
					<html:text property="searchValue" styleId="searchValue" />
				</td>				
				<td>
					<a onclick="javascript: submitForm('ErrorSearch')"> 
						<input  type="button" value="Search" class="button"/>
					</a>
				</td>		
			</tr>
			
			<tr>
				<td colspan="5">
				  <logic:notEmpty scope="request" name="ErrorTransactionResult">
						<font color='blue'><bean:write name="ErrorTransactionResult" /></font>	
				  </logic:notEmpty>
				</td>
			</tr>
		</table>		
		
		<table width="50%" border="0" cellspacing="0" cellpadding="5">
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
		
		<table width="80%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="2">
					<display:table name="list" pagesize="10" requestURI="/error.do" class="table" uid="row" id="record">
						<display:setProperty name="paging.banner.one_item_found"   value="" />
						<display:setProperty name="paging.banner.one_items_found"  value="" />
						<display:setProperty name="paging.banner.all_items_found"  value="" />
						<display:setProperty name="paging.banner.some_items_found" value="" />
						<display:setProperty name="paging.banner.placement" value="bottom" />
						<display:setProperty name="paging.banner.onepage" value="" />
						<display:column sortable="true" title="ERROR CODE"  property="errorCode" />
						<display:column sortable="true" title="ERROR NAME"  property="errorDesc" />
						<display:column sortable="true" title="UPDATE" style="text-align:center;">
							<bean:define id="id" name="record" property="errorId" />
							<input type="button" class="button" value="UPDATE" onclick = "update('<%=id %>','UpdateError');" />
						</display:column>
					</display:table>
				</td>				
			</tr>
			
			<tr>			
		 	 	<td colspan="2">
				     <center><input type="button" value="Back" class="button" onclick="javascript: submitForm('newError')" /></center>
		     	</td>
			</tr>					
			</table>	
			<html:hidden property="updateErrorId" styleId="updateErrorId" />	
			<html:hidden property="method" styleId="method" />				
</logic:equal>	
</html:form>