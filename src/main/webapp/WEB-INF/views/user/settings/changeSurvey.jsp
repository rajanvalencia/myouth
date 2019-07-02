<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="org.apache.commons.text.StringEscapeUtils"%>
<%@ page import="jp.myouth.db.Events"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String event = (String) session.getAttribute("event");

	request.setCharacterEncoding("UTF-8");
	
	Boolean name = Boolean.valueOf(request.getParameter("name"));
	Boolean fname = Boolean.valueOf(request.getParameter("fname"));
	Boolean satisfaction = Boolean.valueOf(request.getParameter("satisfaction"));
	Boolean comments = Boolean.valueOf(request.getParameter("comments"));
	Boolean transportation = Boolean.valueOf(request.getParameter("transportation"));
	Boolean improvements = Boolean.valueOf(request.getParameter("improvements"));
	Boolean repetition = Boolean.valueOf(request.getParameter("repetition"));
	
	Events db = new Events();
	db.open();
	Boolean res = db.changeSurveyQuestion(event, name, fname, satisfaction, comments, transportation, improvements, repetition);
	db.close();
	
	if(res){
		session.setAttribute("success","");
		response.sendRedirect("/home/"+event+"/settings/survey");
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Pikachu Credits: Hazem Ashraf https://codepen.io/Tetsu/pen/rLJyPp -->
<meta charset="UTF-8">
<title>詳細設定変更</title>
<link rel="stylesheet" href="/resources/css/insert.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">
</head>
<body>
	<%
		out.println("<br />name: " + name);
		out.println("<br />fname: " + fname);
		out.println("<br />satisfaction: " + satisfaction);
		out.println("<br />comments: " + comments);
		out.println("<br />transportation: " + transportation);
		out.println("<br />improvements: " + improvements);
		out.println("<br />repetition: " + repetition);
	%>
	<div class="loading">
		<img src="https://a.top4top.net/p_1990j031.gif" alt="Loading">
	</div>
	<div class="mouse original"></div>
	<script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>



	<script src="/resources/js/insert.js"></script>


</body>

</html>