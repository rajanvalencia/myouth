<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.Events"%>
<%@ page import="java.util.ArrayList"%>
<%/*
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String userId = (String) session.getAttribute("userId");
	String event = (String) session.getAttribute("event");*/
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>質問編成</title>
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
<link rel="stylesheet" href="/resources/css/toggle-switch.css">
<link rel="stylesheet" href="/resources/css/jquery-dialog.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
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
		
			<!-- Confirm icon -->
			<section class="box" style="background-color: #f5f5f5; box-shadow: none;">
				<div id="createFormIcons" class="row gtr-uniform gtr-50">
					<div class="col-11 col-9-mobile">
					</div>
					<div class="col-1 col-3-mobile">
						<span id="submit" class="fas fa-eye fa-2x mouse-pointer" onclick="checkForm()"></span>
					</div>
				</div>
				<div id="checkFormIcons" class="row gtr-uniform gtr-50 hidden">
					<div class="col-1-narrower col-1-mobile">
					</div>						
					<div class="col-10 col-8-mobile">
						<span id="submit" class="fas fa-arrow-left fa-2x mouse-pointer" onclick="createForm()"></span>
					</div>
					<div class="col-1 col-3-mobile">
						<span id="submit" class="fas fa-check fa-2x mouse-pointer" onclick="$('#questionIds').submit()"></span>
					</div>
				</div>
			</section>
			
			<!-- Question fields -->
			<div class="row">
				<div class="col-12">
					<section id="warning" class="box" style="background-color: #FFFACD;">
						<p>注意: 氏名、フリガナ、メールアドレスは自働的に生成されます。それ以外の質問をしてください。</p>
					</section>
					<section class="box">
						<h3 id="title" contenteditable="true">参加申し込み</h3>
						<p id="description" contenteditable="true">以下をご記入ください</p>
					</section>
					<div id="questionField"></div>
					<form id="checkQuestionContainer" class="box hidden">
						<div id="checkQuestionField" class="row gtr-uniform gtr-50 hidden">
							
						</div>
					</form>
				</div>
			</div>

			<!-- Add icon -->
			<section class="box" id="addQuestionField" style="background-color: #f5f5f5; box-shadow: none;">
				<div class="row gtr-uniform gtr-50">
					<div class="col-5-5 col-5-mobile">
					</div>
					<div class="col-6 col-7-mobile">
						<span id="addQuestion" class="fas fa-plus-circle fa-2x mouse-pointer"></span>
					</div>
				</div>
			</section>
			
		</section>

		<!-- Footer -->
		<footer id="footer">
			<ul class="copyright">
				<li>myouth.jp</li>
				<li>Design: <a href="http://html5up.net">HTML5 UP</a> & Rajan Valencia</li>
			</ul>
		</footer>

	</div>
	
	<form id="questionIds" method="post" action="/saveForm">
		<input type="hidden" value="?" />
	</form>
	
	<!-- Scripts -->
	<script src="/resources/alpha/js/jquery.min.js"></script>
	<script src="/resources/alpha/js/jquery.dropotron.min.js"></script>
	<script src="/resources/alpha/js/jquery.scrollex.min.js"></script>
	<script src="/resources/alpha/js/browser.min.js"></script>
	<script src="/resources/alpha/js/breakpoints.min.js"></script>
	<script src="/resources/alpha/js/util.js"></script>
	<script src="/resources/alpha/js/main.js"></script>
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<!-- jquery sortable for mobile touch -->
	<script src="/resources/js/jquery.ui.touch-punch.min.js"></script>
	<script type="text/javascript" src="/resources/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="/resources/js/createForm.js"></script>
	<script type="text/javascript" src="/resources/js/checkForm.js"></script>
	<script type="text/javascript" src="/resources/js/createFormFromTemplate.js"></script>
</body>
</html>
