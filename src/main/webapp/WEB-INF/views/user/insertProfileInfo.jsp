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
<title>自己紹介挿入</title>
<link rel="stylesheet" href="/resources/css/insert.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">
</head>
<body>
	<%
		String userId = (String) session.getAttribute("userId");
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String word = request.getParameter("word");
		User db = new User();
		db.open();
		Boolean res = db.introduction(userId, name, word);
		db.close();
		if(res)
			response.sendRedirect("/home");
		else
			out.print("Change failed.");
	%>

	<div class="loading">
		<img src="https://a.top4top.net/p_1990j031.gif" alt="Loading">
	</div>
	<div class="mouse original"></div>
	<script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>



	<script src="/resources/js/insert.js"></script>


</body>

</html>