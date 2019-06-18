<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="jp.myouth.db.MySQL"%>
<%@ page import="jp.myouth.mail.ConfirmationMail"%>
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
		
		String fname = request.getParameter("fname-field");
		out.println("<br>fname: "+fname);
		
		String satisfaction = request.getParameter("satisfaction");
		out.println("<br>satisfaction:" + satisfaction);

		String details = request.getParameter("details");
		out.println("<br>details:" + details);

		String improvement = request.getParameter("improvement");
		out.println("<br>improvement:" + improvement);

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

		MySQL db = new MySQL();
		db.open();
		boolean res = db.insertSurveyData(event, name, fname, satisfaction, details, improvement, transportation);
		db.close();

		if (res) {
			out.println("<br>insert OK.");
			response.sendRedirect("/events/" + event + "/survey/success");
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