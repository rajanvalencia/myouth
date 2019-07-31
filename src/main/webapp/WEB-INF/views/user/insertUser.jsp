<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.security.Authentication"%>
<%@ page import="jp.myouth.db.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Pikachu Credits: Hazem Ashraf https://codepen.io/Tetsu/pen/rLJyPp -->
<meta charset="UTF-8">
<title>ユーザー登録</title>
<link rel="stylesheet" href="/resources/css/insert.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
	
		String name = request.getParameter("name");
		session.setAttribute("registerUserName", name);
		
		String fname = request.getParameter("fname");
		session.setAttribute("registerUserFname", fname);
		
		String phone = request.getParameter("phone");
		session.setAttribute("registerUserPhone", phone);
		
		String year = request.getParameter("birth-year");
		String month = request.getParameter("birth-month");
		String day = request.getParameter("birth-day");
		
		session.setAttribute("registerUserYear", year);
		session.setAttribute("registerUserMonth", month);
		session.setAttribute("registerUserDay", day);
		
		String birthdate =  year + "-" +  month + "-" + day;
		
		String email = request.getParameter("email");
		User db = new User();
		db.open();
		Boolean res = db.checkIfEmailDoesNotExist(email);
		db.close();
		
		String password = request.getParameter("password");
		out.print("name: " + name + "<br>fname: " + fname + "<br>email: " + email + "<br>phone: " + phone + "<br>birthdate: " + birthdate + "<br>password: " + password);
		Authentication auth = new Authentication();
		Boolean res1;
		if(res)
			res1 = auth.registerUser(name, fname, email, phone, birthdate, password);
		else
			res1 = false;
		
		if (res1){
			out.println("<br>Insert succeeded");
			session.setAttribute("registerUserSuccess", "");
		}
		else {
			out.println("<br>Insert failed.");
			session.setAttribute("registerUserFailure", "");
		}
		response.sendRedirect("/registerUser");	
	%>

	<div class="loading">
		<img src="https://a.top4top.net/p_1990j031.gif" alt="Loading">
	</div>
	<div class="mouse original"></div>
	<script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>



	<script src="/resources/js/insert.js"></script>


</body>

</html>