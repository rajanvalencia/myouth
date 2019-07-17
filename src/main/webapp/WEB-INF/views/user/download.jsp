<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="jp.myouth.db.Participants"%>
<%@ page import="jp.myouth.db.Events"%>
<%@ page import="jp.myouth.downloads.*"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	
	String userId = (String) session.getAttribute("userId");
	String event = (String) session.getAttribute("event");
	String startPeriod;
	String endPeriod;
	
	request.setCharacterEncoding("UTF-8");
	String dataType = request.getParameter("dataType");
	String periodType = request.getParameter("periodType");
	String fileType = request.getParameter("fileType");
	String fontSize = request.getParameter("fontSize");

	
	if(periodType.equals("freePeriod")){
		startPeriod = request.getParameter("startYear")+"-"+request.getParameter("startMonth")+"-"+request.getParameter("startDay");
		endPeriod = request.getParameter("endYear")+"-"+request.getParameter("endMonth")+"-"+request.getParameter("endDay");	
	} else if (periodType.equals("recruitmentPeriod")) {
		Events db = new Events();
		db.open();
		startPeriod = db.recruitmentStartDate(event).get(0) + "-" + db.recruitmentStartDate(event).get(1) + "-"
				+ db.recruitmentStartDate(event).get(2);
		endPeriod = db.recruitmentEndDate(event).get(0) + "-" + db.recruitmentEndDate(event).get(1) + "-"
				+ db.recruitmentEndDate(event).get(2);
		db.close();
	} else /*else if(periodType.equals("allPeriod"))*/ {
		startPeriod = null;
		endPeriod = null;
	}

	ArrayList<String> data = new ArrayList<String>();
	int total;

	Participants db1 = new Participants();
	db1.open();
	if (dataType.equals("applicationFormData")) {
		data = db1.participants(event, periodType, startPeriod, endPeriod);
		total = db1.totalParticipants(event, periodType, startPeriod, endPeriod);
	} else /*else if(dataType.equals("surveyData"))*/ {
		data = db1.survey(event, periodType, startPeriod, endPeriod);
		total = db1.totalSurveyAnswers(event, periodType, startPeriod, endPeriod);
	}
	db1.close();

	if (fileType.equals("csv")) {
		CSV csv = new CSV();
		csv.createCSVFile(response, event, data, total);
	} else /*else  if(fileType.equals("pdf"))*/ {
		PDF pdf = new PDF();
		pdf.createPDFFile(response, event, data, total, fontSize);
	}
%>

<!DOCTYPE html>
<html lang="en">
<head>
<!-- Pikachu Credits: Hazem Ashraf https://codepen.io/Tetsu/pen/rLJyPp -->
<meta charset="UTF-8">
<title>ダウンロード</title>
<link rel="stylesheet" href="/resources/css/insert.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">
</head>
<body>

	<div class="loading">
		<img src="https://a.top4top.net/p_1990j031.gif" alt="Loading">
	</div>
	<div class="mouse original"></div>
	<script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>



	<script src="/resources/js/insert.js"></script>


</body>

</html>