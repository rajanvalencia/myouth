<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.security.Authorization"%>
<%@ page import="jp.myouth.db.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Pikachu Credits: Hazem Ashraf https://codepen.io/Tetsu/pen/rLJyPp -->
<meta charset="UTF-8">
<title>認証</title>
<link rel="stylesheet" href="/resources/css/insert.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">
</head>
<body>
	<%
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		Authorization authorization = new Authorization();
		Boolean res = authorization.login(email, password);
		String sessionId = session.getId();
		if (res) {
			User db = new User();
			db.open();
			String userId = db.userId(email);
			db.close();
			session.setAttribute("userId", userId);
			session.setAttribute("user", true);
			response.sendRedirect("/home");
		} else {
			session.setAttribute("failure", true);
			response.sendRedirect("/login");
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