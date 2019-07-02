<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="jp.myouth.db.Events"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String success = (String) session.getAttribute("success");
	String userId = (String) session.getAttribute("userId");
	String event = (String) session.getAttribute("event");
	Events db = new Events();
	db.open();
	String eventName = db.eventName(event); 
	ArrayList<Boolean> question = db.surveyQuestion(event);
	db.close();
	
	Boolean name = question.get(0);
	Boolean fname = question.get(1);
	Boolean satisfaction = question.get(2);
	Boolean comments = question.get(3);
	Boolean transportation = question.get(4);
	Boolean improvements = question.get(5);
	Boolean repetition = question.get(6);
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>アンケート設定</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="/resources/alpha/css/main.css" />
<link rel="stylesheet" href="/resources/css/font-awesome-animation.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body class="is-preload">
	<div id="page-wrapper">

		<!-- Main -->
		<section id="main" class="container">
			<section class="back-button">
				<a href="/home/<%out.print(event);%>/settings"><span
					class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
			</section>
			<header>
				<h2>Survey</h2>
			</header>
			<div class="row">
				<div class="col-12">

					<!-- Form -->
					<section style="background-color: #E8F9DF;"
						class="box <%out.print(success); session.setAttribute("success", "hidden");%>"
						id="success">
						<p>アンケート設定は変更されました <i class="fa fa-check faa-tada animated"></i></p>
					</section>
					<section class="box">
						<h3><%out.print(eventName);%>アンケート設定</h3>
						<form id="form" method="post"
							action="/home/multiculturalyouth/settings/survey/change">
							<div class="row gtr-uniform gtr-50">
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="name" name="name" value="true" <%if(name) out.print("checked");%>>
									<label for="name">氏名</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="fname" name="fname" value="true" <%if(fname) out.print("checked");%>>
									<label for="fname">フリガナ</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="satisfaction" name="satisfaction" value="true" <%if(satisfaction) out.print("checked");%>>
									<label for="satisfaction">満足度</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="comments" name="comments" value="true" <%if(comments) out.print("checked");%>>
									<label for="comments">感想</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="transportation" name="transportation" value="true" <%if(transportation) out.print("checked");%>>
									<label for="transportation">交通手段</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="improvements" name="improvements" value="true" <%if(improvements) out.print("checked");%>>
									<label for="improvements">改善案</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="repetition" name="repetition" value="true" <%if(repetition) out.print("checked");%>>
									<label for="repetition">もう一度来るかどうか</label>
								</div>
								<div class="col-12">
									<ul class="actions" id="swap">
										<li><input id="btn" type="submit" value="変更" /></li>
									</ul>
								</div>
							</div>
						</form>

					</section>
					<section class="back-button">
						<a href="/home/<%out.print(event);%>/settings"><span
							class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
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
	<script type="text/javascript">
	
	$('#form').find('input').focus(function() {
		$('#success')
		.addClass('hidden');
	});
	
	$('#form').on('submit',function() {
		$('#swap')
		.html('<li><i class="fa fa-refresh fa-2x faa-spin faa-fast animated"></i></li>');
	});
	</script>
</body>
</html>