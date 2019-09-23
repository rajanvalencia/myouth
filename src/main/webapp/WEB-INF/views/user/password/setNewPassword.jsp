<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% 
	String success = (String) session.getAttribute("setNewPasswordSuccess");
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>新パスワード設定</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="apple-touch-icon" sizes="57x57" href="/resources/favicon/apple-icon-57x57.png">
<link rel="apple-touch-icon" sizes="60x60" href="/resources/favicon/apple-icon-60x60.png">
<link rel="apple-touch-icon" sizes="72x72" href="/resources/favicon/apple-icon-72x72.png">
<link rel="apple-touch-icon" sizes="76x76" href="/resources/favicon/apple-icon-76x76.png">
<link rel="apple-touch-icon" sizes="114x114" href="/resources/favicon/apple-icon-114x114.png">
<link rel="apple-touch-icon" sizes="120x120" href="/resources/favicon/apple-icon-120x120.png">
<link rel="apple-touch-icon" sizes="144x144" href="/resources/favicon/apple-icon-144x144.png">
<link rel="apple-touch-icon" sizes="152x152" href="/resources/favicon/apple-icon-152x152.png">
<link rel="apple-touch-icon" sizes="180x180" href="/resources/favicon/apple-icon-180x180.png">
<link rel="icon" type="image/png" sizes="192x192"  href="/resources/favicon/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="/resources/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="96x96" href="/resources/favicon/favicon-96x96.png">
<link rel="icon" type="image/png" sizes="16x16" href="/resources/favicon/favicon-16x16.png">
<link rel="stylesheet" href="/resources/alpha/css/main.css" />
<link rel="stylesheet" href="/resources/css/font-awesome-animation.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

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
			<header>
				<h2>New Password</h2>
			</header>
				<!-- Form -->
			<section style="background-color: #E8F9DF;" class="box <%out.print(success); session.setAttribute("setNewPasswordSuccess", "hidden");%>" id="success">
				<p>
					パスワードは正常に変更されました <i class="fa fa-check faa-tada animated"></i> 
				</p>
			</section>
			<section class="box">
				<form id="form" method="post" action="/changeToNewPassword">
					<div class="row gtr-50 gtr-uniform">
						<div class="col-12">
							<label for="password">パスワード<br />(8文字以上で1文字以上の数字、小文字アルファベット、大文字アルファベットがそれぞれ含まれていること)</label> 
							<input type="password" name="password" id="password"
								pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
								title="8文字以上で1文字以上の数字、小文字アルファベット、大文字アルファベットがそれぞれ含まれていること"
								value="" required />
						</div>
						<div class="col-12">
							<label for="confirm_password">パスワードをもう一度入力</label> <input
								type="password" name="confirm_password" id="confirm_password"
								pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" value="" required />
							<br /> <span id='message'></span>
						</div>
						<input type="hidden" name="userId" value="<%out.print(session.getAttribute("userId"));%>">
						<div class="col-12">
							<ul class="actions" id="swap">
								<li><input id="btn" type="submit" value="パスワードを変更" disabled /></li>
							</ul>
						</div>
					</div>
				</form>
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
	
	$('#password, #confirm_password').on('keyup', function () {
		  if ($('#password').val() == $('#confirm_password').val() && $('#confirm_password').val() != '') {
		  	$('#message').html('パスワードが一致しました').css('color', '#06A10B');
		    	$('#btn').removeAttr("disabled");
		  }
		  else {
		    $('#message').html('パスワードが一致しません').css('color', '#ff7496');
		  }
	});
	</script>

</body>
</html>