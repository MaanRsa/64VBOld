<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>


<br>
<div style="position:absolute; left:604px; top:111px; width:218px; height:246px; z-index:1; border: 1px none #000000">
	<html:form action="/login.do">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" 	height="">
			<tr>
				<td><img src="<%=path%>/images/login_head.gif" width="218" height="35"></td> 
			</tr>
			<tr>
				<td class="error" background="<%=path%>/images/login_back.gif" height="24">
					<html:messages id="message" message="true">
						<li style="color: red;">
							<bean:write name="message" />
						</li>
					</html:messages>
				</td>
			</tr>
			<tr>
				<td background="<%=path%>/images/login_back.gif" height="40">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span class="t">Login Name<br>&nbsp;&nbsp;&nbsp;&nbsp;<html:text property="enteredLoginID" /> </span>
				</td>
			</tr>
			<tr>
				<td background="<%=path%>/images/login_back.gif" height="40">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<span class="t">Password<br> &nbsp;&nbsp;&nbsp;&nbsp; <html:password property="enteredPassword" /> </span>
				</td>
			</tr>
			<tr>
				<td background="<%=path%>/images/login_back.gif" height="56"
					align="center">
					<input type="submit" name="Submit" value="Submit" class="tbut">
					<input type="reset" name="Submit2" value="Reset" class="tbut">
					<html:hidden property="method" value="login" />
				</td>
			</tr>
			<tr>
				 <td height="3"><img src="<%=path%>/images/login_down.gif" width="218" height="5"></td> 
			</tr>
		</table>
	</html:form>
</div>

