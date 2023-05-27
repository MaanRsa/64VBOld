<%@ page language="java" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored ="false" %>
<%@ taglib uri="/WEB-INF/displaytag.tld" prefix="display"%>
<%@ taglib uri="/WEB-INF/displaytag-el.tld" prefix="display-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"  prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"  prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>


<html:html>
<body >
<html:form action="/upload2.do" enctype="multipart/form-data">
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
	    				<bean:message key="file.upload2.head" />
	    			</td>
				</tr>
				<tr class="tabin">
				 	<td class="t">Bank Name&nbsp;:<br></td>
	    			<td >
	    				<html:select property="bankname" >
		             	<html:option value="select" >-select-</html:option>
		             	<html:optionsCollection name="BankList" label="value" value="key" />
				   	    </html:select>
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
<div id="uploadsts1" style="display:none">
	<table align="center">
	
		<tr align="center">
			<td colspan="2"><font color="#488AC7" size="4pt"><b>Processing..</b></font></td>
		</tr>
		
		<tr>
			<td align="center"><img src="<%=request.getContextPath()%>/images/loader.gif" alt="Upload In Progress" /> </td>
		</tr>
	</table>
</div>
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
<div id="uploadsts2" style="display:none">
	<table align="center">
	
		<tr align="center">
			<td colspan="2"><font color="#488AC7" size="4pt"><b>Processing..</b></font></td>
		</tr>
		
		<tr>
			<td align="center"><img src="<%=request.getContextPath()%>/images/loader.gif" alt="Upload In Progress" /> </td>
		</tr>
	</table>
</div>
	<table  border="0" cellpadding="5" cellspacing="0"  width="80%" align="center">
 		<tr class="tabin">
			<td class="title" width="100%" height="10" align="center" colspan= "2">Process Result</td>
		</tr>
		<tr class="tabin">
			
			<td class="tb" width="100%" height="10" align="center">
				Total No. of Records Matched In Last Transaction&nbsp;:&nbsp;<span class="st"><bean:write name="TotalRecords" scope="request" />&nbsp;&nbsp;&nbsp;</span>
			</td>
		</tr>
		
           
		<tr style="height:15px">
    <td>&nbsp;</td>
    </tr>
	</table>
</logic:equal>
<logic:equal name="PartToShow" value="ProcessAllResult">

	<table  border="0" cellpadding="5" cellspacing="0"  width="80%" align="center">
 		<tr class="tabin">
			<td class="title" width="100%" height="10" align="center" colspan= "2">Process Result</td>
		</tr>
		<tr class="tabin">
			
			<td class="tb" width="100%" height="10" align="center">
				Total No. of Records Matched &nbsp;:&nbsp;<span class="st"><bean:write name="TotalRecords" scope="request" />&nbsp;&nbsp;&nbsp;</span>
			</td>
		</tr>
		
		<tr style="height:15px">
    <td>&nbsp;</td>
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
<html:hidden property="uploaderrdatas"/>
<html:hidden property="bankname"/>
</html:form>

<script>
       
   function cancelPage(){
   	  	document.uploadform2.action ='<%=request.getContextPath()%>/menu.do?method=menu';
      	document.uploadform2.submit();
      	return false;
   }
   
   function submitPage(val){
      	document.uploadform2.method.value = "uploadsubmit";
      	document.uploadform2.submit();
      	if( document.getElementById("uploadsts").style.display == 'none' )
			document.getElementById("uploadsts").style.display = 'block';
		else
			document.getElementById("uploadsts").style.display = 'none';

		val.disabled=true;
		document.getElementById("uploadFile").disabled = true;
   }
   function processPage(val){
      	document.uploadform2.method.value = "processRecords";
      	document.uploadform2.bankname.value= '<jsp:getProperty name="uploadform2" property="bankname" />';
      	document.uploadform2.submit();
      	if( document.getElementById("uploadsts1").style.display == 'none' )
			document.getElementById("uploadsts1").style.display = 'block';
		else
			document.getElementById("uploadsts1").style.display = 'none';
      	val.disabled=true;
      		
   }
   function processPage2(val){
      	document.uploadform2.method.value = "processAllRecords";
      	document.uploadform2.bankname.value= '<jsp:getProperty name="uploadform2" property="bankname" />';
      	document.uploadform2.submit();
      	if( document.getElementById("uploadsts2").style.display == 'none' )
			document.getElementById("uploadsts2").style.display = 'block';
		else
			document.getElementById("uploadsts2").style.display = 'none';
      	val.disabled=true;
      		
   }
   function submitupdatedDetails()  
   {
   document.forms['uploadform2'].setAttribute('enctype', 'newEnctype', 0);
   document.uploadform2.method.value = "statusUpdationRecords";
   document.uploadform2.submit();
   return false;
   }
   

String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}

   
</script>

</body>
</html:html>