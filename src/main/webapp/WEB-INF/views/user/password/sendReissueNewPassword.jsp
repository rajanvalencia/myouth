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
		String authToken = request.getParameter("authToken");
		String password = request.getParameter("password");
		Authentication auth = new Authentication();
		out.println("authToken: "+authToken);
		out.println("<br>password: "+password);
		Boolean res = auth.changePassword(authToken, password);
		if (res){
			out.println("<br>Update succeeded");
			session.setAttribute("reissueNewPasswordSuccess", "");
			response.sendRedirect("/reissueNewPassword/"+authToken);	
		}
		else {
			out.println("<br>Update failed.");
			session.setAttribute("reissueNewPasswordFailure", "");
			response.sendRedirect("/reissueNewPassword/"+authToken);
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