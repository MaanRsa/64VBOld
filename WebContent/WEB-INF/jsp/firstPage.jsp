<%@ page language="java" import="com.maan.login.LoginForm" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>


	<table width="100%" align="center" border="0" cellpadding="5"  cellspacing="0" >
		<tr>
			<% 
				LoginForm loginForm = (LoginForm) session.getAttribute("loginForm"); 
				if(!"uploaduser".equalsIgnoreCase(loginForm.getUserType())){
			%>
			<td width="20%" height="10" align="left"><span class="tb">STATE:</span> <span class="st"><%=loginForm.getStateName() %></span></td>
			<td width="20%" height="10" align="left"><span class="tb">DISTRICT:</span> <span class="st"><%=loginForm.getDistrictName() %></span></td>
			<td width="50%" height="10" align="right">
			<span class="tb"><bean:message key="admin.menu.master.startDate" />:</span>
			<span class="st"><%=loginForm.getStartDate() %></span>&nbsp;&nbsp;&nbsp;
			<span class="tb"><bean:message key="admin.menu.master.endDate" />:</span>
			<span class="st"><%=loginForm.getEndDate() %></span>
			</td>
			<%  } %>
		</tr>
		<tr>
		<td class="header"  width="50%" align="center">
			<img src="images/subsid_ic.gif" width="32" height="32">
		</td></tr>
	</table>


<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
