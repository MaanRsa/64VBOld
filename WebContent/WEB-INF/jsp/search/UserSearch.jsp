<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>


<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html:html >
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  
    <title>UserSearch.jsp</title> 
<script>

</script>
  </head>
  
  <body >

<html:form action="/UserSearchDispatcAction.do" method="post">

<logic:messagesPresent>
<br />
<table  align="center" border="0" align="center" width="50%" cellpadding="1" cellspacing="1" >

<tr valign="top" align="center" style="height:5px">
<td colspan="2"> <font color="red"> <html:errors /> </font></td>
</tr>
<tr style="height:2px">
<td>&nbsp;</td>
</tr>
</table>
</logic:messagesPresent>

<logic:equal name="PartToShow" value="Search">
<br />
<table  align="center" border="0" align="center" width="45%" cellpadding="1" cellspacing="1" >

<tr><td><bean:message key="search.receiptno" /></td>
<td align="left">
&nbsp;
<html:text name="userSearchFormBean" property="receiptNo" />
&nbsp;
</td>
</tr>

<tr><td><bean:message key="search.chequeno" /></td>
<td align="left">
&nbsp;
<html:text name="userSearchFormBean" property="chequeNo" />
 &nbsp;
</td>
</tr>

<tr><td><bean:message key="search.chequeamt" /></td>
<td align="left">
&nbsp;
<html:text name="userSearchFormBean" property="chequeAmount" />
&nbsp;
</td>
</tr>

<tr><td colspan="2" align="center">
<br />
<html:submit styleClass="tbut" value="Search" />
</td>
</tr>

</table>
<br />
</logic:equal>
<logic:equal name="PartToShow" value="SearchResult">


 <table cellpadding="5" cellspacing="0" border="0" width="100%">   
 <tr><br /><td colspan="2" width="100%" class="tbn" height="10" align="left">SEARCH RESULT</td></tr> 
 
   <tr style="height:15px">
    <td></td>
    </tr>
    <tr><td colspan="1"  width="170" height="10" align="right">&nbsp;&nbsp;<b>You Searched For</b></td>
    <td>&nbsp;</td></tr> 
 
</table>   

<table width="100%">
<tr><td>

 <table   align="center" cellpadding="0" cellspacing="0"  width="85%" >   
 
<tr>
<td align="center" ><span class="tb">Receipt No: </span>&nbsp;&nbsp;<bean:write property="receiptNo" name="userSearchFormBean"/></td>
<td align="center"><span class="tb">Cheque No: </span>&nbsp;&nbsp;<bean:write property="chequeNo" name="userSearchFormBean"/></td>
<td align="center"><span class="tb">Cheque Amount: </span>&nbsp;&nbsp;<bean:write property="chequeAmount" name="userSearchFormBean"/></td>
</tr>


 </table>  
 </td>
 </tr>
 <tr><td align="right">
 <br /> 
	  	</td></tr>
	 </table>
 
  
<table border="0" cellpadding="0" cellspacing="0" width="100%">
  
    <tr>
     <td align="center" colspan="2">
     
          
		<display:table name="searchResult" pagesize="10" requestURI="/UserSearchDispatcAction.do" class="table" uid="row" id="record" >
		<display:setProperty name="paging.banner.one_item_found" value="" />
		<display:setProperty name="paging.banner.one_items_found" value="" />
		<display:setProperty name="paging.banner.all_items_found" value="" />
		<display:setProperty name="paging.banner.some_items_found" value="" />
		<display:setProperty name="paging.banner.placement" value="bottom" />
		<display:setProperty name="paging.banner.onepage" value="" />
	 	<display:column sortable="true" style="text-align:center;width:100px" title="Bank" property="bankName" />
	 	<display:column style="text-align:center;width:80px" sortable="true" title="Cheque No" property="chequeNo"  />
	 	<display:column style="text-align:center;width:80px" sortable="true" title="Cheque Amount" property="chequeAmount"/>
	 	<display:column style="text-align:center;width:80px" sortable="true" title="Receipt Date" property="depositDate" />
		<display:column style="text-align:center;width:170px" sortable="true" title="Receipt No" class="formtxtc"  property="receiptNo"  />

	    <display:column sortable="true" style="text-align:center;width:100px" title="Status" property="realisation" />
	    
		 <display:column style="text-align:center;width:170px" sortable="true" title="View" class="formtxtc" media="html">
	    <bean:define id="receiptNo"  name="record" property="receiptNo" />
	      <bean:define id="banknreceipt"  name="record" property="bankNo1" />
	       
	      <% if(((banknreceipt.toString().split("-")[1]).trim().equalsIgnoreCase(""))){ %>
	       -
	      <%}else{ %>
	     
	      	<logic:empty name="record" property="chequeNo"> 
             Report 
         </logic:empty>
         <logic:notEmpty name="record" property="chequeNo"> 
             
		<a href="#" name="${record.bankNo1}" onclick="return callPopup(this.name);"  style="cursor: pointer;" >Report</a>   
		</logic:notEmpty>
		<%} %>
	
		</display:column>
	   		
		</display:table>
		
	</td>
    
    </tr>
    <tr style="height:10px">
    <td></td>
    </tr>
    <tr>
		<td colspan="3" align="center"> <br />
			<html:submit styleClass="tbut" value="Back" style="width:65px" onclick="return cancelPage()" />
		</td>
	</tr>
    </table>
    <br /><br />
</logic:equal>

<html:hidden property="method"  value="submitSearch"/>

</html:form>
<script>
function callPopup(val)
   {
	var URL = '<%=request.getContextPath()%>/UserSearchDispatcAction.do?method=receiptDetails&receiptNo='+val;
	var windowName = "Details";
	var width  = screen.availWidth;
	var height = screen.availHeight;
	var w = 880;
	var h = 600;
	var features =
		'width='          + w +
		',height='		  + h +
		',left='		  + ((width - w - 10) * .5)  +
		',top='			  + ((height - h - 30) * .5) +
		',directories=no' +
		',location=no'	  +
		',menubar=no'	  +
		',scrollbars=yes' +
		',status=no'	  +
		',toolbar=no'	  +
		',resizable=no';
	var strOpen = window.open (URL, windowName, features);
	
	strOpen.focus();
	return false;
   }  
   		

   function checkNo(val)
   {
    if(isNaN(val))
    alert("Enter cheque number in numbers only");
    var len = document.userSearchFormBean.actualChequeAmount.length;
	for(var i=0;i<len;i++){
    if(isNaN(document.userSearchFormBean.actualChequeNo[i].value)){
     document.userSearchFormBean.actualChequeNo[i].value="";
	 }
	}
   }
   function checkAmt(val)
   {
   if(isNaN(val))
    alert("Enter cheque amount in numbers only");
   var len = document.userSearchFormBean.actualChequeAmount.length;
	
   	for(var i=0;i<len;i++){
    if(isNaN(document.userSearchFormBean.actualChequeAmount[i].value)){
    	 document.userSearchFormBean.actualChequeAmount[i].value="";
	 }
		}
  	}
   

function cancelPage(){
window.location.href='<%=request.getContextPath() %>/UserSearchDispatcAction.do?method=search';

	return false;
}

function submitPage(){
window.location.href='<%=request.getContextPath() %>/UserSearchDispatcAction.do?method=submitSearch';

	return false;
}
    </script>
  </body>
</html:html>
