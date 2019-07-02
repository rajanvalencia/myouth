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
	Boolean gender = Boolean.valueOf(request.getParameter("gender"));
	Boolean email = Boolean.valueOf(request.getParameter("email"));
	Boolean phone = Boolean.valueOf(request.getParameter("phone"));
	Boolean birthdate = Boolean.valueOf(request.getParameter("birthdate"));
	Boolean company = Boolean.valueOf(request.getParameter("company"));
	Boolean career = Boolean.valueOf(request.getParameter("career"));
	Boolean country = Boolean.valueOf(request.getParameter("country"));
	Boolean country2 = Boolean.valueOf(request.getParameter("country2"));
	Boolean country3 = Boolean.valueOf(request.getParameter("country3"));
	Boolean address = Boolean.valueOf(request.getParameter("address"));
	Boolean allergy = Boolean.valueOf(request.getParameter("allergy"));
	Boolean way = Boolean.valueOf(request.getParameter("way"));
	Boolean confirmation = Boolean.valueOf(request.getParameter("confirmation"));
	Boolean parent_confirmation = Boolean.valueOf(request.getParameter("parent_confirmation"));
	
	Events db = new Events();
	db.open();
	Boolean res = db.changeFormQuestion(event, name, fname, gender, email, phone, birthdate, company, career, country, country2, country3, address, allergy, way, confirmation, parent_confirmation);
	db.close();
	
	if(res){
		session.setAttribute("success","");
		response.sendRedirect("/home/"+event+"/settings/form");
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
		out.println("<br />gender: " + gender);
		out.println("<br />email: " + email);
		out.println("<br />phone: " + phone);
		out.println("<br />birthdate: " + birthdate);
		out.println("<br />career: " + career);
		out.println("<br />company: " + company);
		out.println("<br />country: " + country);
		out.println("<br />country2: " + country2);
		out.println("<br />country3: " + country3);
		out.println("<br />address: " + address);
		out.println("<br />allergy: " + allergy);
		out.println("<br />way: " + way);
		out.println("<br />confirmation: " + confirmation);
		out.println("<br />parent_confirmation: " + parent_confirmation);
	%>
	<div class="loading">
		<img src="https://a.top4top.net/p_1990j031.gif" alt="Loading">
	</div>
	<div class="mouse original"></div>
	<script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>



	<script src="/resources/js/insert.js"></script>


</body>

</html>