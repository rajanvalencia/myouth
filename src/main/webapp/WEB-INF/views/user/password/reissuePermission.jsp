<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% 
	String success = (String) session.getAttribute("reissuePermissionSuccess");
	String failure = (String) session.getAttribute("reissuePermissionFailure");
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>パスワード忘れ</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
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
			<section class="back-button">
				<a href="/home"><span
					class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
			</section>
			<header>
				<h2>Reissue Permission</h2>
			</header>
				<!-- Form -->
			<section style="background-color: #E8F9DF;" class="box <%out.print(success); session.setAttribute("reissuePermissionSuccess", "hidden");%>" id="success">
				<p>
					パスワードを再発行するために必要なurlは登録されたメールに送信されました <i class="fa fa-check faa-tada animated"></i> 
					<br />30分経過したら自動的に無効になりますのでご注意ください。
				</p>
			</section>
			<section style="background-color: #F4BAA7;" class="box <%out.print(failure); session.setAttribute("reissuePermissionFailure", "hidden");%>" id="success">
				<p>
					メールアドレスは登録されてません。<i class="fa fa-times faa-pulse animated"></i> 
				</p>
			</section>
			<section class="box">
				<form id="form" method="post" action="/sendReissuePermission">
					<div class="row gtr-50 gtr-uniform">
						<div class="col-12">
							<label for="email">登録されてるメールアドレス</label> <input type="email"
								name="email" id="email" value="" placeholder="" required />
						</div>
						<div class="col-12">
							<ul class="actions" id="swap">
								<li><input id="btn" type="submit" value="パスワード再発行 " /></li>
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
</body>
</html>