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
	
	String eventName = request.getParameter("eventName");
	eventName = StringEscapeUtils.escapeHtml4(eventName);
	
	String eventPlace = request.getParameter("eventPlace");
	eventPlace = StringEscapeUtils.escapeHtml4(eventPlace);

	String eventDate = request.getParameter("year") + "-" + request.getParameter("month") + "-"
			+ request.getParameter("day");
	eventDate = StringEscapeUtils.escapeHtml4(eventDate);

	String eventTime = request.getParameter("hour") + ":" + request.getParameter("minute");
	eventTime = StringEscapeUtils.escapeHtml4(eventTime);

	String recruitNo = request.getParameter("recruitNo");
	recruitNo = StringEscapeUtils.escapeHtml4(recruitNo);

	String recruitmentStartDate = request.getParameter("startYear") + "-" + request.getParameter("startMonth")
			+ "-" + request.getParameter("startDay");
	recruitmentStartDate = StringEscapeUtils.escapeHtml4(recruitmentStartDate);

	String recruitmentEndDate = request.getParameter("endYear") + "-" + request.getParameter("endMonth") + "-"
			+ request.getParameter("endDay");
	recruitmentEndDate = StringEscapeUtils.escapeHtml4(recruitmentEndDate);
	
	Events db = new Events();
	db.open();
	Boolean res = db.changeEventDetails(event, eventName, eventPlace, eventDate, recruitmentStartDate, recruitmentEndDate, eventTime, recruitNo);
	db.close();
	
	if(res){
		session.setAttribute("success","");
		response.sendRedirect("/home/"+event+"/settings/details");
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
		out.println("<br />eventName: " + eventName);
		out.println("<br />eventPlace: " + eventPlace);
		out.println("<br />eventDate: " + eventDate);
		out.println("<br />eventTime: " + eventTime);
		out.println("<br />recruitNo: " + recruitNo);
		out.println("<br />recruitmentStartDate: " + recruitmentStartDate);
		out.println("<br />recruitmentEndDate: " + recruitmentEndDate);
	%>
	<div class="loading">
		<img src="https://a.top4top.net/p_1990j031.gif" alt="Loading">
	</div>
	<div class="mouse original"></div>
	<script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>



	<script src="/resources/js/insert.js"></script>


</body>

</html>