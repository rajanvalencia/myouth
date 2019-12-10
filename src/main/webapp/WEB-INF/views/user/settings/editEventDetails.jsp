<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>詳細設定</title>
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
			<section class="back-button">
				<a href="/home/${event}/settings">
					<span class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span>
				</a>
			</section>
			<header>
				<h2>Edit Event Details</h2>
			</header>
			<div class="row">
				<div class="col-12">

					<!-- Form -->
					<section style="background-color: #E8F9DF;" class="box ${success}">
						<p>詳細設定は変更されました <i class="fa fa-check faa-tada animated"></i></p>
					</section>
					<section class="box">
						<form id="form" method="post" action="/servlets/events/updateEventDetails">
							<div class="row gtr-uniform gtr-50">
								<div class="col-12">
									<label for="name">イベント名</label>
									<input type="text" name="name" value="${eventName}" />
								</div>
								<div class="col-12">
									<label for="place">開催場所</label>
									<input type="text" name="place" value="${eventPlace}" />
								</div>
								<div class="col-6">
									<label for="date">開催予定</label>
									<input type="date" name="date" id="date" value="${eventDate}" />
								</div>
								<div class="col-6">
									<label for="recruitNo">募集人数</label>
									<input type="text" name="recruitLimit" pattern="[0-9]*" value="${recruitLimit}" />
								</div>
								<div class="col-6">
									<label for="startTime">開始時間</label>
									<input type="time" name="startTime" value="${eventStartTime}" />
								</div>
								<div class="col-6">
									<label for="endTime">終了時間</label>
									<input type="time" name="endTime" value="${eventEndTime}" />
								</div>
								<div class="col-6">
									<label for="recruitmentStartDate">募集開始</label>
									<input type="date" name="recruitmentStartDate" id="recruitmentStartDate" value="${recruitmentStartDate}" max="${recruitmentEndDate}"/>
								</div>
								<div class="col-6">
									<label for="recruitmentEndDate">募集〆切</label>
									<input type="date" name="recruitmentEndDate" id="recruitmentEndDate" value="${recruitmentEndDate}" min="${recruitmentStartDate}"/>
								</div>
								<div class="col-12">
									<label for="description">イベント紹介 (Googleなどに検索されたときに表示されます)</label>
									<textarea name="description" rows="6">${description}</textarea>
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
						<a href="/home/${event}/settings">
							<span class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span>
						</a>
					</section>
				</div>
			</div>
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
	<script src="/resources/alpha/js/jquery.min.js"></script>
	<script src="/resources/alpha/js/jquery.dropotron.min.js"></script>
	<script src="/resources/alpha/js/jquery.scrollex.min.js"></script>
	<script src="/resources/alpha/js/browser.min.js"></script>
	<script src="/resources/alpha/js/breakpoints.min.js"></script>
	<script src="/resources/alpha/js/util.js"></script>
	<script src="/resources/alpha/js/main.js"></script>
	<script type="text/javascript">
		$('#form').on('submit',function() {
			$('#swap')
			.html('<li><i class="fa fa-refresh fa-2x faa-spin faa-fast animated"></i></li>');
		});
		
		$('#recruitmentStartDate').blur(function(){
			$('#recruitmentEndDate').attr('min', $(this).val());
		});
		
		$('#recruitmentEndDate').blur(function(){
			$('#recruitmentStartDate').attr('max', $(this).val());
		});
	</script>
</body>
</html>