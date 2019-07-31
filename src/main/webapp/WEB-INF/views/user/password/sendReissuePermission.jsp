<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.security.Authentication"%>
<!DOCTYPE html>
<html lang="en">
<head>
<!-- Pikachu Credits: Hazem Ashraf https://codepen.io/Tetsu/pen/rLJyPp -->
<meta charset="UTF-8">
<title>許可認証</title>
<link rel="stylesheet" href="/resources/css/insert.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">
</head>
<body>
	<%
		String userId = (String) session.getAttribute("userId");
		request.setCharacterEncoding("UTF-8");
		String email = request.getParameter("email");
		String birthdate = request.getParameter("birth-year") + "-" + request.getParameter("birth-month") + "-" + request.getParameter("birth-day");
		Authentication auth = new Authentication();
		Boolean res = auth.identify(email, birthdate);
		if (res){
			out.println("<br>Insert succeeded");
			session.setAttribute("reissuePermissionSuccess", "");
			response.sendRedirect("/reissuePermission");	
		}
		else {
			out.println("<br>Insert failed.");
			session.setAttribute("reissuePermissionFailure", "");	
			response.sendRedirect("/reissuePermission");
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