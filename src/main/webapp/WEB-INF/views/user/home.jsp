<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.tables.*" %>
<%@ page import="jp.myouth.db.User"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	
	String userId = (String) session.getAttribute("userId");
	String event = (String) session.getAttribute("event");
	
	User db = new User();
	db.open();
	String name = db.name(userId);
	String fname = db.fname(userId);
	String word = db.word(userId);
	String path = db.userProfilePicture(userId);
	db.close();
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>Home</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="/resources/alpha/css/main.css" />
<link rel="stylesheet" href="/resources/css/font-awesome-animation.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-143752853-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-143752853-1');
</script>

</head>
<body class="is-preload">
	<div id="page-wrapper">
	
		<!-- Main -->
		<section id="main" class="container">
		<section  class="back-button">
		<a href="/logoutRedirect"><span class="fas fa-door-open fa-2x"></span><span class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
		</section>
			<div class="row">
				<div class="col-12">

					<!-- Image -->
					<section class="box">
						<div class="row gtr-50 gtr-uniform">
							<div class="col-3 col-5-mobile">
								<img style="border-radius: 50%;" src="https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/<% out.print(path);%>" alt="" width="130"/>
							</div>
							<div class="col-8 col-5-mobile">
								<h3><%out.print(name);%></h3>
								<h4><%out.print(fname);%></h4>
								<p><%out.print(word);%></p>
							</div>
							<div class="col-1 col-2-mobile">
								<a href="/home/profile" style="border-bottom-color: transparent;"><span class="fa fa-cog fa-2x faa-spin animated"></span></a>
							</div>
						</div>
					</section>
					
					<section class="box">
					<div class="row">
					<div class="col-6 col-12-mobilep">
					<h3>管理するイベント</h3>
					</div>
					<div class="col-6 col-12-mobilep">
					<ul class="actions">
						<li><a href="#" class="button special"><span class="fa fa-plus faa-pulse animated"></span> イベント作成</a></li>
					</ul>
					</div>
					</div>
					<%
						UserPageEventLists lists = new UserPageEventLists();
						lists.append(request);
						out.print(request.getAttribute("userPageEventLists"));
						request.removeAttribute("userPageEventLists");
					%>
					</section>
				</div>
			</div>
		</section>

		<!-- Footer -->
		<footer id="footer">
			<!-- <ul class="icons">
						<li><a href="#" class="icon brands fa-twitter"><span class="label">Twitter</span></a></li>
						<li><a href="#" class="icon brands fa-facebook-f"><span class="label">Facebook</span></a></li>
						<li><a href="#" class="icon brands fa-instagram"><span class="label">Instagram</span></a></li>
						<li><a href="#" class="icon brands fa-github"><span class="label">Github</span></a></li>
						<li><a href="#" class="icon brands fa-dribbble"><span class="label">Dribbble</span></a></li>
						<li><a href="#" class="icon brands fa-google-plus"><span class="label">Google+</span></a></li>
					</ul> -->
			<ul class="copyright">
				<li>myouth.jp</li>
				<li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
			</ul>
		</footer>

	</div>

	<!-- Scripts -->
	<script src="/resources/alpha/js/jquery.min.js"></script>
	<script src="/resources/alpha/js/jquery.dropotron.min.js"></script>
	<script src="/resources/alpha/js/jquery.scrollex.min.js"></script>
	<script src="/resources/alpha/js/browser.min.js"></script>
	<script src="/resources/alpha/js/breakpoints.min.js"></script>
	<script src="/resources/alpha/js/util.js"></script>
	<script src="/resources/alpha/js/main.js"></script>
</body>
</html>