<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.concurrent.TimeUnit"%>
<%@ page import="org.apache.commons.lang3.time.StopWatch" %>
<%@ page import="org.apache.commons.text.StringEscapeUtils"%>
<%@ page import="jp.myouth.db.Events"%>
<%@ page import="jp.myouth.db.Participants"%>
<%@ page import="jp.myouth.db.User"%>
<%@ page import="jp.myouth.mail.Templates"%>
<%
	StopWatch stopWatch = new StopWatch();
	stopWatch.start();	
	
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	
	String userId = (String)session.getAttribute("userId");
	String event = (String) session.getAttribute("event");
	
	ArrayList<String> TO = new ArrayList<String>();
	
	Events db = new Events();
	db.open();
	String eventName = db.eventName(event);
	db.close();
	
	String startPeriod;
	String endPeriod;
	
	request.setCharacterEncoding("UTF-8");
	String periodType = request.getParameter("periodType");
	
	if(periodType.equals("freePeriod")){
		startPeriod = request.getParameter("startYear")+"-"+request.getParameter("startMonth")+"-"+request.getParameter("startDay");
		endPeriod = request.getParameter("endYear")+"-"+request.getParameter("endMonth")+"-"+request.getParameter("endDay");	
	} else if(periodType.equals("recruitmentPeriod")){
		db.open();
		startPeriod = db.recruitmentStartDate(event).get(0)+"-"+db.recruitmentStartDate(event).get(1)+"-"+db.recruitmentStartDate(event).get(2);
		endPeriod = db.recruitmentEndDate(event).get(0)+"-"+db.recruitmentEndDate(event).get(1)+"-"+db.recruitmentEndDate(event).get(2);
		db.close();
	} else /*else if(periodType.equals("allPeriod"))*/{
		startPeriod = null;
		endPeriod = null;
	}
	
	User db1 = new User();
	db1.open();
	String senderEmail = db1.userEmailAddress(userId);
	String userName = db1.name(userId);
	db1.close();
	
	Participants db2 = new Participants();
	db2.open();
	/*
	*イベントに合計参加者数を習得
	*/
	Integer totalParticipants = db2.totalParticipantsByEmailAddress(event, senderEmail);
	TO = db2.participantsEmailAddress(event, senderEmail, periodType, startPeriod, endPeriod);
	db2.close();
	
	TO.add(senderEmail);
	
	String SUBJECT = request.getParameter("subject");
	String MESSAGE = request.getParameter("message");
	
	Templates send = new Templates();
	Boolean res = send.mailToAllParticipants(event, eventName, userName, SUBJECT, TO, MESSAGE);
	
	stopWatch.stop();
	session.setAttribute("stopWatch", stopWatch.getTime(TimeUnit.SECONDS));
	session.setAttribute("totalParticipants", String.valueOf(TO.size()));
	
	if(res){
		session.setAttribute("success","");
		response.sendRedirect("/home/"+event+"/mail");	
	}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Pikachu Credits: Hazem Ashraf https://codepen.io/Tetsu/pen/rLJyPp -->
<meta charset="UTF-8">
<title>メール送信</title>
<link rel="stylesheet" href="/resources/css/insert.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">
</head>
<body>
<%
out.println("userId: "+userId);
out.println("<br>event: "+event);
out.println("<br>eventName: "+eventName);
out.println("<br>totalParticipants: "+TO.size());
out.println("<br>senderEmail:  "+senderEmail);
out.println("<br>subject: "+SUBJECT);
out.println("<br>body: "+MESSAGE);
out.println("<br>send: "+res);

for(String string : TO)
	out.println("<br />"+string);

out.println("startPeriod: "+startPeriod);
out.println("endperiod"+endPeriod);
%>
	<div class="loading">
		<img src="https://a.top4top.net/p_1990j031.gif" alt="Loading">
	</div>
	<div class="mouse original"></div>
	<script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>



	<script src="/resources/js/insert.js"></script>


</body>

</html>