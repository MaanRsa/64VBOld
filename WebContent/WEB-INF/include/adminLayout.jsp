<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html:html locale="true">
<head>
	<title><tiles:getAsString name="title" />
	</title>
	<link href="<%=request.getContextPath()%>/css/style.css"
		rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/css/design.css"
		rel="stylesheet" type="text/css">
</head>
<body>
	<table width="779" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td colspan="2">
				<tiles:insert attribute="header" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<tiles:insert attribute="menu" />
			</td>
		</tr>
		<tr>
			<td width="30%">
				<tiles:insert attribute="leftMenu" />
			</td>
			<td width="70%">
				<tiles:insert attribute="body" />
			</td>
		</tr>
		<tr>
			<td colspan="2" class="footer">
				<tiles:insert attribute="footer" />
			</td>
		</tr>
	</table>
</body>
</html:html>
