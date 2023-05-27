<html>
<head>
<title>Error Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="<%=request.getContextPath() %>/css/style.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath() %>/css/design.css" rel="stylesheet" type="text/css">
</head>

<body bgcolor="#FFFFFF">
<form name="sessionForm" action="<%=request.getContextPath() %>/welcome.do">
  <table width="760" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><table width="760" border="0" cellspacing="0" cellpadding="0">
        <tr> 
            <td align="left" valign="top"></td>
          <td>&nbsp;</td>
            <td align="right" valign="middle"></td>
        </tr>
		      </table>



<br>
<table width="550" border="1" cellspacing="0" cellpadding="0" align="center">
  <tr bgcolor="#000099" align="right" valign="middle"> 
    <td > 
      <table width="500" border="0" cellspacing="0" cellpadding="0" align="left">
        <tr>
          <td><font face="Verdana, Arial, Helvetica, sans-serif" size="2" color="#FFFFFF"><b>&nbsp;&nbsp;Error</b></font></td>
        </tr>
      </table>
      <img src="<%=request.getContextPath() %>/images/top_but.gif" width="50" height="14"></td>
  </tr>
  <tr bgcolor="#F2F2F2" align="center" valign="top"> 
    <td height="212"> 
      <table width="89%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr> 
          <td height="38" width="10%" valign="top"><img src="<%=request.getContextPath() %>/images/error.gif" width="34" height="33"></td>
          <td height="38" width="90%"><b><font face="Arial, Helvetica, sans-serif" size="2">64VB</font></b></td>
        </tr>
        <tr> 
          <td height="90" width="10%">&nbsp;</td>
          <td height="90" width="90%" valign="top"> <font face="Arial, Helvetica, sans-serif" size="2">
		  
	   						  <table width='100%' border='1' cellspacing='1' cellpadding='1' align='center' >
						 <tr> 
						   <td> 
							 <p><b><br>Sorry, the  requested page could not be found. The reason may be :</b><br>
							   <br>1.Session ID is not valid.<br>
							   <br>2.We are unable to find a page with this session ID.<br>
							   <br>If the problem continues, please mail to test@test.com<br>
							   <br><b>Please note the error message above, click the Back button on your 
							   browser and then continue</b></p>
						   </td>
						 </tr>
					   </table>		  
		  
		  </font></td>
        </tr>
        <tr align="center"> 
          <td height="39" colspan="2">
          	<input type="button" class="tbut" name="Back" value="Back" style="cursor: pointer;" onclick="javascript:document.sessionForm.submit();return false;" />
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</td>
</tr>
</table>
</form>
</body>
</html>
