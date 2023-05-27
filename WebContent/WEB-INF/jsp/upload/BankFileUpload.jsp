<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored ="false" %>
<%@ page import="java.util.*,java.text.*;" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>

<%

String pts=request.getAttribute("PartToShow")==null?"":(String)request.getAttribute("PartToShow");
String requestFrom=request.getAttribute("reqfrom")==null?"":(String)request.getAttribute("reqfrom"); 
String previouspage=request.getAttribute("previouspage")==null?"":(String)request.getAttribute("previouspage"); 
String Strerrdata=request.getAttribute("Errordatasexcel")==null?"":(String)request.getAttribute("Errordatasexcel");
//String Strerr1=request.getAttribute("Strerrors")==null?"err":(String)request.getAttribute("Strerrors").toString();
System.out.println("pts---->"+pts);
%>

<html:html>
<body >
<html:form action="/uploadbankfile.do" enctype="multipart/form-data">
<table  align="center" cellpadding="5" cellspacing="5" border="0" width="100%">   
   <tr>
	   	<td>
	   		<table align="center" border="0" cellpadding="5"  cellspacing="0" width="40%">
				<tr>
					<td>
						<html:messages id="message" message="true">
							<li style="color: red;">
								<bean:write name="message" />
							</li>
						</html:messages>
						<logic:notEmpty name="error">
							<li style="color: red;">
								<bean:write name="error" />
							</li>						
						</logic:notEmpty>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
	
<logic:equal name="PartToShow" value="Upload">
<div id="uploadsts" style="display:none">
	<table align="center">
	
		<tr align="center">
			<td colspan="2"><font color="#488AC7" size="4pt"><b>Upload In Progress</b></font></td>
		</tr>
		<tr>
			<td align="center"><img src="<%=request.getContextPath()%>/images/loader.gif" alt="Upload In Progress" /> </td>
		</tr>
	</table>
</div>
<table  align="center" cellpadding="5" cellspacing="5" border="0" width="100%">   
   <tr>
	   	<td>
	   		<table align="center" border="0" cellpadding="5"  cellspacing="0" width="50%" >
				<tr class="tbn">
	    			<td colspan="2">
	    				<bean:message key="file.uploadbankfile.head" />
	    			</td>
				</tr>
				<tr class="tabin">
	        		<td class="t">
	        			<bean:message key="file.upload.file" />&nbsp;:
	        		</td>
			        <td class="t">
			        	<html:file property="uploadFile" styleId="uploadFile" />
			        </td>
			    </tr>		     
			    <tr class="tabin">
			       	<td colspan="2">&nbsp;</td>
			    </tr>			
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table cellspacing="0" cellpadding="0" width = "50%" align = "center" border = "0">
		    	<tr>
        			<td align="center">
			    		<html:submit value="Cancel" styleClass="tbut" onclick = "return cancelPage()" />
			    		<html:submit value="Submit" styleClass="tbut" onclick = "return submitPage(this)" />
				   	</td>
		 	   	</tr>
			</table>
		</td>
	</tr>
</table>
</logic:equal>

<logic:equal name="PartToShow" value="UploadResult">
	<table  border="0" cellpadding="5" cellspacing="0"  width="80%" align="center">
 		<tr class="tabin">
			<td class="title" width="100%" height="10" align="center" colspan= "2">Uploaded Transaction Details</td>
		</tr>
		<tr class="tabin">
			<td class="tb" width="50%" height="10" align="left">
				&nbsp;&nbsp;Transaction ID&nbsp;:&nbsp;<span class="st"><bean:write name="TransactionID" scope="request" />&nbsp;</span>
			</td>
			<td class="tb" width="50%" height="10" align="right">
				Total No. Records&nbsp;:&nbsp;<span class="st"><bean:write name="TotalRecords" scope="request" />&nbsp;&nbsp;&nbsp;</span>
			</td>
		</tr>
		<tr class="tabin">
			<td class="tb" width="50%" height="10" align="left">
				&nbsp;&nbsp;Uploaded Records&nbsp;:&nbsp;<span class="st"><bean:write name="Uploadcount" scope="request" />&nbsp;</span>
			</td>
			<td class="tb" width="50%" height="10" align="right">
				Not Uploaded Records&nbsp;:&nbsp;<span class="st"><bean:write name="Errorcount" scope="request" />&nbsp;&nbsp;&nbsp;</span>
			</td>
		</tr>
		<%//if(request.getAttribute("Errorcount")!=null && Integer.parseInt(request.getAttribute("Errorcount").toString()) > 0){ %>
		<!--  <tr>
          <td  colspan="2">
		  	<a href="#"  onclick="return callFailed('<bean:write name="TransactionID" scope="request" />','Errordatas','invalidrecords');">Click here to Download Not uploaded Datas</a> 
		  </td>
		</tr>	-->	
		<%//}%>
		<%if(request.getAttribute("errors")!=null){ %>
		<tr>
          <td  colspan="2"><font color="red"> <%=request.getAttribute("errors") %></font></td> 		
		</tr>		
		<%}%>
		<tr>
          <td  colspan="2" align="center">
           <html:button styleClass="tbut" property="Process" value="Process" onclick = "return processPage(this)" /></td> 		
		</tr>	
	</table>
</logic:equal>

<logic:equal name="PartToShow" value="ProcessResult">

	<table  border="0" cellpadding="5" cellspacing="0"  width="80%" align="center">
 		<tr class="tabin">
			<td class="title" width="100%" height="10" align="center" colspan= "2">Process Result</td>
		</tr>
		<tr class="tabin">
			
			<td class="tb" width="100%" height="10" align="center">
				Total No. of Records Processed&nbsp;:&nbsp;<span class="st"><bean:write name="TotalRecords" scope="request" />&nbsp;&nbsp;&nbsp;</span>
			</td>
		</tr>
		
	</table>
</logic:equal>

<logic:equal value="submitBlock" name="PartToShow">
   
    <table border="0" cellpadding="0" cellspacing="0" width="100%">
    
    <tr style="height:20px">
    <td><br></td>
    </tr>
    
    <tr align="center">
    <td colspan="2"><html:errors/></td>
    </tr>
    
    <tr style="height:20px">
    <td><br></td>
    </tr>
	
    <tr align="center"> 
    <td><html:submit value="Back" styleClass="button" onclick="return normalBack(); " />  <br></td>
    </tr>
    
    <tr style="height:20px">
    <td><br><br><br></td>
    </tr>
    
    </table>
    
    </logic:equal>



<html:hidden property="method" />
<html:hidden property="requestfrom"/>
<html:hidden property="transactionid"/>
<html:hidden property="startDate"/>
<html:hidden property="endDate"/>
<html:hidden property="currentcheckeditems"/>
<html:hidden property="uploaderrdatas"/>

</html:form>

<script>
      
   function saveToXLS(transId,reqFrom){
   		document.uploadform.method.value = "saveToXls";
   		document.uploadform.transactionid.value=transId;
   		document.uploadform.requestfrom.value=reqFrom;
   		document.uploadform.uploaderrdatas.value=Strerrdata;
   		document.uploadform.submit();
   		return false;
   }
  function SaveXls(reqFrom){
  
   		document.uploadform.method.value = "SaveXls";
   		document.uploadform.requestfrom.value=reqFrom;
   		document.uploadform.submit();
   		return false;
   }
   function cancelPage(){
   	  	document.uploadform.action ='<%=request.getContextPath()%>/menu.do?method=menu';
      	document.uploadform.submit();
      	return false;
   }
   
   function submitPage(val){
      	document.uploadbankfile.method.value = "uploadsubmit";
      	document.uploadbankfile.submit();
      	if( document.getElementById("uploadsts").style.display == 'none' )
			document.getElementById("uploadsts").style.display = 'block';
		else
			document.getElementById("uploadsts").style.display = 'none';

		val.disabled=true;
		document.getElementById("uploadFile").disabled = true;
   }
   function processPage(val){
      	document.uploadbankfile.method.value = "processRecords";
      	document.uploadbankfile.submit();
      		
   }
  
   function submitupdatedDetails()  
   {
   document.forms['uploadbankfile'].setAttribute('enctype', 'newEnctype', 0);
   document.uploadbankfile.method.value = "statusUpdationRecords";
   document.uploadbankfile.submit();
   return false;
   }
   

String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}



function goBack(state,district,sdate,edate)
{
if(!((sdate=="" || sdate==null) && (edate=="" || edate==null)))
{
document.uploadbankfile.action="<%=request.getContextPath()%>/transaction.do";
document.uploadbankfile.method.value = "transaction";
document.uploadbankfile.submit();
}else{
document.forms['uploadbankfile'].setAttribute('enctype', 'newEnctype', 0);
document.uploadbankfile.action="<%=request.getContextPath()%>/uploadbankfile.do";
document.uploadbankfile.method.value = "packageDetails";
document.uploadbankfile.submit();
}
return false;
}
   

function callcheck(vll)
{
if(vll.checked==true)
{
for(i=0; i < document.uploadbankfile.checkedstatus.length; i++)
{
document.uploadbankfile.checkedstatus[i].checked=true;
}
}
else
{
for(i=0; i < document.uploadbankfile.checkedstatus.length; i++)
{
document.uploadbankfile.checkedstatus[i].checked=false;
}
}

if(document.uploadbankfile.checkedstatus.length==null)
{
if(document.uploadbankfile.checkedstatus!=null)
{
if(vll.checked==true)
document.uploadbankfile.checkedstatus.checked=true;
else
document.uploadbankfile.checkedstatus.checked=false;
}
}

}


function ReplaceAll(Source,stringToFind,stringToReplace){
 var temp = Source;
 var index = temp.indexOf(stringToFind);
 
 while(index != -1){
 temp = temp.replace(stringToFind,stringToReplace);
 index = temp.indexOf(stringToFind);
}

return temp;
}

function callpage(val)
{
var total="";
//alert(val.href);

var v=val.href;
v=ReplaceAll(v,"pagecount","");
v=ReplaceAll(v,"checks","");
v=ReplaceAll(v,"previouspage","");
//alert(v);
//v=ReplaceAll(v,"&method=submitBlock","");
//alert(v);

var sindex,lindex,pageval;
sindex=v.indexOf("p=");
lindex=v.indexOf("&", sindex);
pageval=v.substring(sindex+2,lindex);
//alert(pageval);
//document.uploadbankfile.pagecount.value=pageval;

for(i=0; i < document.uploadbankfile.checkedstatus.length; i++)
{
if(document.uploadbankfile.checkedstatus[i].checked)
{
total=total+document.uploadbankfile.checkedstatus[i].title+",";
}
}

if(total=="")
{
if(document.uploadbankfile.checkedstatus.checked)
total=document.uploadbankfile.checkedstatus.title+",";
}
if(total.length>0)
total=total.substring(0,total.length-1);

//document.uploadbankfile.checks.value=total;

val.href=v+"&pagecount="+pageval+"&checks="+total+"&previouspage=<%=previouspage%>&requestfrom="+document.uploadbankfile.requestfrom.value;
//alert('Href: '+val.href);
//alert(total);
}
   
function normalBack1()
{
document.forms['uploadbankfile'].setAttribute('enctype', 'newEnctype', 0);
document.uploadbankfile.action="<%=request.getContextPath()%>/uploadbankfile.do";
document.uploadbankfile.method.value ="statusUpdationRecords";    
document.uploadbankfile.transactionid.value = <%=request.getAttribute("transId")%>;
document.uploadbankfile.submit();
return false;
}

function normalBack()
{

document.forms['uploadbankfile'].setAttribute('enctype', 'newEnctype', 0);
document.uploadbankfile.action="<%=request.getContextPath()%>/uploadbankfile.do";
document.uploadbankfile.method.value = "statusUpdationRecords";
document.uploadbankfile.submit();
return false;
}   
   
function validateCheck()
{
var requests="<%=requestFrom%>";

var total="";

for(var i=0; i < document.uploadbankfile.checkedstatus.length; i++)
{
if(document.uploadbankfile.checkedstatus[i].checked)
{
total=total+document.uploadbankfile.checkedstatus[i].title+",";
}
}

if(total=="")
{
if(document.uploadbankfile.checkedstatus.checked)
{
total=document.uploadbankfile.checkedstatus.title+",";
}
}

//alert(total);

if(total.length>0)
total=total.substring(0,total.length-1);
document.uploadbankfile.currentcheckeditems.value=total;
document.forms['uploadbankfile'].setAttribute('enctype', 'newEnctype', 0);
document.uploadbankfile.action="<%=request.getContextPath()%>/uploadbankfile.do?reqfrom="+requests;
document.uploadbankfile.requestfrom.value = requests;
document.uploadbankfile.method.value = "submitStatusDetails";
document.uploadbankfile.submit();
return false;

}   
   
   
</script>

</body>
</html:html>