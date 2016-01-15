<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create NewsPaper</title>
<script language="javascript">
function submitPage()
{
	var pName = document.newspaper.paperName.value;
	var pNbr = document.newspaper.newsContactNbr.value;
	if(pName=='' || pNbr == '') {
		alert("Please complete the form");
	} else {
		document.newspaper.action="./createpaper.htm";
		document.newspaper.submit();
	}
}
</script>
</head>
<%@ include file="home.jsp" %>
<body>
<form name="newspaper" method="post">
<p>&nbsp;</p>
<%
String resultMsg = (String)request.getAttribute("resultMsg");
if(resultMsg!=null){
out.println("<center>"+resultMsg+"</center>");
}
%>
<center>
NewsPaper Name: <input type="text" name="paperName" /><br>
Contact Number: &nbsp;&nbsp;&nbsp;<input type="text" name="newsContactNbr" /><br>
<p>&nbsp;</p>
<input type="button" name="b1" value="Add NewsPaper" onClick="submitPage()"/>&nbsp;&nbsp;&nbsp;
<input type="reset" name="r1" value="Reset" />
</center>
</form>
</body>
</html>