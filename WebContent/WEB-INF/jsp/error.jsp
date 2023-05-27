<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" 
	prefix="logic" %>

<logic:empty name="tpadetails">
<table width="90%" align="right">
	<tr>
		<td height="20pt">
			&nbsp;
		</td>
	</TR>
	<tr>
		<td class="error">
			<bean:write name="ExpTime" />
		</td>
	</TR>
	<tr>
		<td class="error">
			<bean:write name="ExpType" />
		</td>
	</tr>
	<tr>
		<td class="error">
			<bean:write name="ExpMsg" />
		</td>
	</tr>
	<tr>
		<td height="20pt">
			&nbsp;
		</td>
	</TR>
</table>
</logic:empty>
<logic:notEmpty name="tpadetails">
<% 
	String details = (String)request.getAttribute("tpadetails"); 
	String[] temp = details.split("~");
	if(temp.length==8){
%>
<input type="hidden" name="details1" id="details1" value="<%=temp[0].replaceAll("null","") %>" />
<input type="hidden" name="details2" id="details2" value="<%=temp[1].replaceAll("null","")+ "/" +temp[2].replaceAll("null","") %>" />
<input type="hidden" name="details3" id="details3" value="<%=temp[3].replaceAll("null","") %>" />
<input type="hidden" name="details4" id="details4" value="<%=temp[4].replaceAll("null","") %>" />
<input type="hidden" name="details5" id="details5" value="<%=temp[5].replaceAll("null","") %>" />
<input type="hidden" name="details6" id="details6" value="<%=temp[6].replaceAll("null","") %>" />
<input type="hidden" name="details7" id="details7" value="<%=temp[7].replaceAll("null","") %>" />
<%	} %>
</logic:notEmpty>