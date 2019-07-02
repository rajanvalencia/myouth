<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.apache.commons.text.StringEscapeUtils"%>
<%@ page import="jp.myouth.db.Events"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Pikachu Credits: Hazem Ashraf https://codepen.io/Tetsu/pen/rLJyPp -->
<meta charset="UTF-8">
<title>データ処理</title>
<link rel="stylesheet" href="/resources/css/insert.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">
</head>
<body>
	<%
		String event = (String) session.getAttribute("event");
		if (event == null)
			response.sendRedirect("/events");
		request.setCharacterEncoding("UTF-8");

		out.println("event:" + event);

		String name = request.getParameter("name-field");
		out.println("<br>name: "+name);
		name = StringEscapeUtils.escapeHtml4(name);
		
		String fname = request.getParameter("fname-field");
		out.println("<br>fname: "+fname);
		fname = StringEscapeUtils.escapeHtml4(fname);
		
		String satisfaction = request.getParameter("satisfaction");
		out.println("<br>satisfaction:" + satisfaction);
		satisfaction = StringEscapeUtils.escapeHtml4(satisfaction);

		String details = request.getParameter("details");
		out.println("<br>details:" + details);
		details = StringEscapeUtils.escapeHtml4(details);
		
		String improvement = request.getParameter("improvement");
		out.println("<br>improvement:" + improvement);
		improvement = StringEscapeUtils.escapeHtml4(improvement);
		
		String repetition = request.getParameter("repetition");
		out.println("<br>repetition:" + repetition);
		repetition = StringEscapeUtils.escapeHtml4(repetition);

		String train = request.getParameter("train");
		String bus = request.getParameter("bus");
		String four_wheel_vehicle = request.getParameter("four_wheel_vehicle");
		String two_wheel_vehicle = request.getParameter("two_wheel_vehicle");
		String other_transportation = request.getParameter("other_transportation");

		ArrayList<Boolean> transportation = new ArrayList<Boolean>();

		if (train != null)
			transportation.add(true);
		else
			transportation.add(false);

		if (bus != null)
			transportation.add(true);
		else
			transportation.add(false);

		if (four_wheel_vehicle != null)
			transportation.add(true);
		else
			transportation.add(false);

		if (two_wheel_vehicle != null)
			transportation.add(true);
		else
			transportation.add(false);

		if (other_transportation != null)
			transportation.add(true);
		else
			transportation.add(false);

		for (Boolean string : transportation) {
			if (string != null)
				out.println("<br>" + string + ": Yes");
		}

		Events db = new Events();
		db.open();
		Boolean res = db.insertSurveyData(event, name, fname, satisfaction, details, improvement, transportation, repetition);
		db.close();

		if (res) {
			out.println("<br>insert OK.");
			session.setAttribute("success", "");
			response.sendRedirect("/events/" + event + "/survey");
		} else {
			out.println("<br>insert NG.");
		}
	%>

	<div class="loading">
		<img src="https://a.top4top.net/p_1990j031.gif" alt="Loading">
	</div>
	<div class="mouse original"></div>
	<script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>



	<script src="/resources/js/insert.js"></script>


</body>

</html>