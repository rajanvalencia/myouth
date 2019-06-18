<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
		out.println("<br>name:" + name);
		
		String fname = request.getParameter("fname-field");
		out.println("<br>fname:" + fname);
		
		String gender = request.getParameter("sex-field");
		out.println("<br>gender:" + gender);
		
		String email = request.getParameter("email-field");
		out.println("<br>email:" + email);
		
		String phone = request.getParameter("phone-field");
		out.println("<br>phone:" + phone);
		
		String birthdate = request.getParameter("birth-year") + "-" + request.getParameter("birth-month") + "-"
				+ request.getParameter("birth-day");
		out.println("<br>birthdate:" + birthdate);
		
		String company = request.getParameter("school-field");
		out.println("<br>company:" + company);
		
		String country = request.getParameter("country-field");
		out.println("<br>country:" + country);
		
		String country2 = request.getParameter("country-field-2");
		out.println("<br>country2:" + country2);
		
		String country3 = request.getParameter("country-field-3");
		out.println("<br>country3:" + country3);
		
		String zip = request.getParameter("zip-field");
		out.println("<br>zip:" + zip);
		
		String pref = request.getParameter("pref-field");
		out.println("<br>pref:" + pref);
		
		String address = request.getParameter("address-field");
		out.println("<br>address:" + address);
		
		String allergy = request.getParameter("allergy-field");
		out.println("<br>allergy:" + allergy);

		String career = request.getParameter("other-grade-field");
		if (career == null || career.length() == 0) {
			career = request.getParameter("grade-field");
			out.println("<br>career:" + career);
		} else {
			out.println("<br>career:" + career);
		}
		String way = request.getParameter("other-trigger-field");
		if (way == null || way.length() == 0) {
			way = request.getParameter("trigger-field");
			out.println("<br>way:" + way);

		} else {
			out.println("<br>other_way:" + way);
		}

		MySQL db = new MySQL();
		db.open();
		boolean res = db.insertParticipantData(event, name, fname, gender, email, phone, birthdate, career, company, country, country2, country3, zip, pref, address, allergy, way);
		String formtype = db.eventForm(event);
		db.close();
		ConfirmationMail ses = new ConfirmationMail();
		boolean res1 = ses.Send(event, name, email);

		if (res) {
			if (res1) {
				out.println("<br>insert OK.");
				response.sendRedirect("/events/"+event+"/form/success");
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