<%@ page language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<html:base />
	<title><tiles:getAsString name="title" />
	</title>
	<link href="<%=request.getContextPath()%>/css/design.css"
		rel="stylesheet" type="text/css">
	<link href="<%=request.getContextPath()%>/css/style.css"
		rel="stylesheet" type="text/css">
</head>
<body>
	<table width="80%" cellspacing="0" cellpadding="0" border="0" align="center">
		<tr>
			<td>
				<tiles:insert attribute="header" />
			</td>
		</tr>
		<tr>
			<td>
				<tiles:insert attribute="body" />
			</td>
		</tr>
		<tr>
			<td>
				<tiles:insert attribute="footer" />
			</td>
		</tr>
	</table>
</body>
</html:html>
