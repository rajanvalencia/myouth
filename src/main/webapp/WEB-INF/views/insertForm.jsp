<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.sql.Date"%> 
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="org.apache.commons.text.StringEscapeUtils"%>
<%@ page import="jp.myouth.db.Events"%>
<%@ page import="jp.myouth.mail.Templates"%>
<% 
String event = (String) session.getAttribute("event");
if (event == null)
	response.sendRedirect("/events");
request.setCharacterEncoding("UTF-8");
%>
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
		out.println("event:" + event);
		
		String name = request.getParameter("name-field");
		name = StringEscapeUtils.escapeHtml4(name);
		out.println("<br>name:" + name);
		
		String fname = request.getParameter("fname-field");
		fname = StringEscapeUtils.escapeHtml4(fname);
		out.println("<br>fname:" + fname);
		
		String gender = request.getParameter("sex-field");
		gender = StringEscapeUtils.escapeHtml4(gender);
		out.println("<br>gender:" + gender);
		
		String email = request.getParameter("email-field");
		email = StringEscapeUtils.escapeHtml4(email);
		out.println("<br>email:" + email);
		
		String phone = request.getParameter("phone-field");
		phone = StringEscapeUtils.escapeHtml4(phone);
		out.println("<br>phone:" + phone);
		
		String birthdate = request.getParameter("birth-year") + "-" + request.getParameter("birth-month") + "-"
				+ request.getParameter("birth-day");
		birthdate = StringEscapeUtils.escapeHtml4(birthdate);
		Date birth = Date.valueOf(birthdate);
		
		out.println("<br>birthdate:" + birthdate);
		
		String company = request.getParameter("company-field");
		company = StringEscapeUtils.escapeHtml4(company);
		out.println("<br>company:" + company);
		
		String country = request.getParameter("country-field");
		country = StringEscapeUtils.escapeHtml4(country);
		out.println("<br>country:" + country);
		
		String country2 = request.getParameter("country-field-2");
		country2 = StringEscapeUtils.escapeHtml4(country2);
		out.println("<br>country2:" + country2);
		
		String country3 = request.getParameter("country-field-3");
		country3 = StringEscapeUtils.escapeHtml4(country3);
		out.println("<br>country3:" + country3);
		
		String zip = new String();
		String zip1 = request.getParameter("zip1");
		String zip2 = request.getParameter("zip2");
		if(zip1 != null && zip2 != null){
			zip = zip1+"-"+zip2;
			zip = StringEscapeUtils.escapeHtml4(zip);
		}
		out.println("<br>zip:" + zip);
		
		String pref = request.getParameter("pref-field");
		pref = StringEscapeUtils.escapeHtml4(pref);
		out.println("<br>pref:" + pref);
		
		String address = request.getParameter("address-field");
		address = StringEscapeUtils.escapeHtml4(address);
		out.println("<br>address:" + address);
		
		String allergy = request.getParameter("allergy-field");
		allergy = StringEscapeUtils.escapeHtml4(allergy);
		out.println("<br>allergy:" + allergy);

		String career = request.getParameter("other-career-field");
		if (career == null || career.length() == 0) {
			career = request.getParameter("career-field");
			career = StringEscapeUtils.escapeHtml4(career);
			out.println("<br>career:" + career);
		} else {
			career = StringEscapeUtils.escapeHtml4(career);
			out.println("<br>career:" + career);
		}
		String way = request.getParameter("other-trigger-field");
		if (way == null || way.length() == 0) {
			way = request.getParameter("trigger-field");
			way = StringEscapeUtils.escapeHtml4(way);
			out.println("<br>way:" + way);

		} else {
			way = StringEscapeUtils.escapeHtml4(way);
			out.println("<br>other_way:" + way);
		}

		Events db = new Events();
		db.open();
		boolean res = db.insertParticipantData(event, name, fname, gender, email, phone, birth, career, company, country, country2, country3, zip, pref, address, allergy, way);
		db.close();
		
		Templates send = new Templates();
		boolean res1 = send.joinConfirmedMail(event, name, email);

		if (res) {
			if (res1) {
				out.println("<br>insert OK.");
				session.setAttribute("success","");
				response.sendRedirect("/events/"+event+"/form");
			}
			else {
			out.println("正しいメールを入力してください");
			}
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