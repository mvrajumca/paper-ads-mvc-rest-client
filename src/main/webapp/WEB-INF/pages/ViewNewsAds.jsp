<%@page import="java.util.Map.Entry"%>
<%@page import="com.acme.ads.model.NewsPaper"%>
<%@page import="com.acme.ads.model.Advertisement"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Iterator" %>
<html>
<head>
<title>NewsAds Report</title>
</head>
<%@ include file="home.jsp" %>
<%
Map<Map,Map> reportMap = (Map<Map, Map>)request.getAttribute("reportData");
%>
<p>&nbsp;</p>
<body>
<form name="reportData" method="get">
<center>
Advertisements - NewsPapers
<table border="0" width="50%" align="center">
<tr>
<td>Adv Id</td><td>Adv Name</td><td></td><td></td><td>NewsPaper Id</td><td>NewsPaper Name</td>
</tr>
<%
Iterator reportIter = reportMap.entrySet().iterator();
Entry adEntry = null;
Entry newsEntry = null;
while(reportIter.hasNext()){
Entry entry = (Entry)reportIter.next();
Map<String, String> newsMap = (Map<String, String>)entry.getKey();
Map<String, String> adsMap = (Map<String, String>)entry.getValue();
Iterator newsIter = newsMap.entrySet().iterator();
Iterator adsIter = adsMap.entrySet().iterator();
adEntry = (Entry)adsIter.next();
newsEntry = (Entry)newsIter.next();
%>
<tr>
<td><%=adEntry.getKey() %></td><td><%=adEntry.getValue() %></td>
<td></td><td></td><td><%=newsEntry.getKey()%></td><td><%=newsEntry.getValue()%></td>
</tr>
<%} %>
</table>
</center>
</form>
</body>
</html>