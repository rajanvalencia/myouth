<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.security.Authorization"%>
<%@ page import="jp.myouth.db.User"%>
<!DOCTYPE HTML>
<!--
	Identity by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String userId = (String) session.getAttribute("userId");
	String event = (String) session.getAttribute("event");
	User db = new User();
	db.open();
	String name = db.name(userId);
	String word = db.word(userId);
	db.close();
%>
<html>
<head>
<link rel="apple-touch-icon" sizes="180x180"
	href="/resources/favicon/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="/resources/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="/resources/favicon/favicon-16x16.png">
<link rel="manifest" href="/resources/favicon/site.webmanifest">
<link rel="mask-icon" href="/resources/favicon/safari-pinned-tab.svg"
	color="#5bbad5">
<meta name="msapplication-TileColor" content="#2d89ef">
<meta name="theme-color" content="#ffffff">
<title>Home</title>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="/resources/user/css/main.css" />
<noscript>
	<link rel="stylesheet" href="/resources/user/css/noscript.css" />
</noscript>
</head>
<body class="is-preload">

	<!-- Wrapper -->
	<div id="wrapper">

		<!-- Main -->
		<section id="main">
			<header>
				<span class="avatar"><img src="https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/users/default/profile_pic.PNG" alt="" height="150" width="150"/></span>
				<h1><%out.print(name);%></h1>
				<p><%out.print(word);%></p>
			</header>
			<footer>
			<ul class="icons">
					<li><a href="/home/editProfile" class="fa-user">Edit</a></li>
					<li><a href="/home/events" class="fa-calendar">Events</a></li>
					<li><a href="/home/settings" class="fa-cog">Settings</a></li>
					<li><a href="/logoutRedirect" class="fa-sign-out">Sign out</a></li>
			</ul>
			</footer>
		</section>

		<!-- Footer -->
		<footer id="footer">
			<ul class="copyright">
				<li>myouth.jp</li>
				<li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
			</ul>
		</footer>

	</div>

	<!-- Scripts -->
	<script>
		if ('addEventListener' in window) {
			window.addEventListener('load', function() {
				document.body.className = document.body.className.replace(
						/\bis-preload\b/, '');
			});
			document.body.className += (navigator.userAgent
					.match(/(MSIE|rv:11\.0)/) ? ' is-ie' : '');
		}
	</script>

</body>
</html>