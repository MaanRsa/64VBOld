<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>


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
			<bean:write name="title" />
		</td>
	</tr>
	<tr>
		<td colspan="2">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
		</td>
		<td class="statusMsg">
			<bean:write name="status" />
		</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
			<a href="<%=request.getContextPath()%>/<bean:write name="action"/>"
				class="button">Back</a>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			&nbsp;
		</td>
	</tr>
</table>
