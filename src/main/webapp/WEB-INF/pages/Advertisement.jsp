<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script language="javascript">
function submitPage()
{
var advertisementName = document.advertsement.advertisementName.value;
var adContactNbr = document.advertsement.adContactNbr.value;
var adDetails = document.advertsement.adDetails.value;
if(advertisementName=='' || adContactNbr=='' || adDetails=='')
{
alert('All fields are required. Please complete the form...');
}
else{
document.advertsement.action="./createad.htm";
document.advertsement.submit();
}
}
</script>
</head>
<%@ include file="home.jsp" %>
<body>
<form name="advertsement" method="post">
<p>&nbsp;</p>
<%
String resultMsg = (String)request.getAttribute("resultMsg");
if(resultMsg!=null){
out.println("<center>"+resultMsg+"</center>");
}
%>
<center>
Advertisement Name: <input type="text" name="advertisementName" /><br>
Contact Number: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="adContactNbr" /><br>
Advertisement Details: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="adDetails"></textarea><br>
<p>&nbsp;</p>
<input type="button" name="b1" value="Create Advertisement" onClick="submitPage()"/>&nbsp;&nbsp;&nbsp;
<input type="reset" name="r1" value="Reset" />
</center>
</form>
</body>
</html>