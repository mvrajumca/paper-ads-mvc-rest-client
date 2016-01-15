<%@page import="com.acme.ads.model.NewsPaper"%>
<%@page import="com.acme.ads.model.Advertisement"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>NewsAdMapping Page</title>
<script language="javascript">
function submitPage()
{
if(document.getElementById('newspaperId').value=='0' || document.getElementById('advertisementId').value=='0')
{
alert('Please complete the form...');
}
else {
document.newsad.action="./createnewspaperadvertisement.htm";
document.newsad.submit();
}
}
</script>
</head>
<%@ include file="home.jsp" %>
<%
List<NewsPaper> newsList = (List<NewsPaper>)request.getAttribute("newspapers");
List<Advertisement> adsList = (List<Advertisement>)request.getAttribute("advertisements");
%>
<p>&nbsp;</p>
<body>
<form name="newsad" method="post">
<%
String resultMsg = (String)request.getParameter("resultMsg");
if(resultMsg!=null){
out.println("<center>"+resultMsg+"</center><br>");
}
%>
<center>
NewsPaper: &nbsp;&nbsp;&nbsp;&nbsp;<select name="newspaper" id="newspaperId">
<option value="0">--Select NewsPaper--</option>
<%for(NewsPaper newsPaper : newsList) {%>
<option value="<%=newsPaper.getNewsPaperId()%>"><%=newsPaper.getNewsPaperName()%></option>
<%} %>
</select>
<br>
Advertisement: <select name="advertisement" id="advertisementId">
<option value="0">--Select Advertisement--</option>
<%for(Advertisement advertisement : adsList) {%>
<option value="<%=advertisement.getAdvId()%>"><%=advertisement.getAdvName()%></option>
<%} %>
</select><br><br>
<input type="button" name="b1" value="SubmitAd" onClick="submitPage()">
</form>
</body>
</html>