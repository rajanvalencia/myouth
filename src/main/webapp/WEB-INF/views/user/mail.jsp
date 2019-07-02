<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.Events"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String event = (String) session.getAttribute("event");
	Events db = new Events();
	db.open();
	String eventName = db.eventName(event);
	db.close();

	Long stopWatch = (Long) session.getAttribute("stopWatch");
	String totalParticipants = (String) session.getAttribute("totalParticipants");
	/*
	*成功メッセージを表示するかどうか
	*/
	String success = (String) session.getAttribute("success");
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>メール送信</title>
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
				<a href="/home"><span
					class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
			</section>
			<header>
				<h2>Mail</h2>
			</header>
			<div class="row">
				<div class="col-12">

					<!-- Form -->
					<section style="background-color: #E8F9DF;" class="box <%out.print(success);session.setAttribute("success", "hidden");%>" id="success">
						<p>メールは正常に送信されました <i class="fa fa-check faa-tada animated"></i> <br />全参加者に送信するまでにかかった時間：<%out.print(stopWatch);%>秒
						<br />受信者数: <%out.print(totalParticipants);%></p>
					</section>
					<section class="box">
						<h3><%out.print(eventName);%>一斉送信</h3>
						<p>送信者にもメールが送信されますのでご了承ください
						<br />送信者のお名前は自動的に記入されますので記入する必要ありません</p>
						<form id="form" method="post"
							action="/home/<%out.print(event);%>/mail/send">
							<div class="row gtr-uniform gtr-50">
								<div class="col-12">
									<input type="text" name="subject" id="subject" placeholder="件名" />
								</div>
								<div class="col-12">
									<textarea name="message" id="message" placeholder="メッセージ" rows="6"></textarea>
								</div>
								<div class="col-12">
									<ul class="actions" id="swap">
										<li><input id="btn" type="submit" value="送信" disabled/></li>
									</ul>
								</div>
							</div>
						</form>

					</section>
				</div>
			</div>
			<section class="back-button">
				<a href="/home"><span
					class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
			</section>
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
	$('#form').on('submit', function (){
		$('#swap')
	    .html('<li><i class="fa fa-refresh fa-2x faa-spin faa-fast animated"></i></li>')
		});
	
	$("#subject").on('focus', function() {
		$('#success')
		.addClass('hidden');
		});
	
	$("#message").on('focus', function() {
		$('#success')
		.addClass('hidden');
		});
	
	$("#message").on('keyup', function() {
		$('#btn')
		.removeAttr('disabled');
		});
	</script>
</body>
</html>