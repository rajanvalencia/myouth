<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<!--
	Prologue by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html lang="en">
<head>
<meta charset="UTF-8" />
<title>${eventName}</title>
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
<meta name="description" content="開催日：${eventDate} 開催場所:${eventPlace} 時間:${eventStartTime} ~ ${eventEndTime} 紹介:${description}">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="/resources/prologue/css/main.css" />
<link rel="stylesheet" href="/resources/css/event-page-member.css" />
<link rel="stylesheet" href="/resources/css/starRatings.css" />
<link rel="stylesheet" href="/resources/css/am-charts.css" />
<link rel="stylesheet" href="/resources/css/toggle-switch.css">
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

	<!-- Header -->
	<div id="header">

		<div class="top">

			<!-- Logo -->
			<div id="logo">
				<span class="image avatar48"><img src="${eventLogoUrl}" alt="" /></span>
			</div>
			
			<!-- Nav -->
			<nav id="nav">
				<ul>
					<li>
						<a href="#introduction" id=""><span class="icon fa-arrow-up">Top</span></a>
					</li>
					<li>
						<a href="#administrator" id=""><span class="icon fa-user-circle">管理者</span></a>
					</li>
					<li>
						<a href="#media" id=""><span class="icon fa-picture-o">メディア</span></a>
					</li>
					<li>
						<a href="#analysis" id=""><span class="icon fa-bar-chart">分析</span></a>
					</li>
					<li>
						<a href="#reviews" id=""><span class="icon fa-comment">評価</span></a>
					</li>
				</ul>
			</nav>
		</div>
		${socialLinks}
	</div>

	<!-- Main -->
	<div id="main">

		<section id="introduction" class="two">
			<div class="container">
				<img src="${eventLogoUrl}" style="border-radius: 25px; margin-right: auto; margin-left:auto;" alt="${eventName}" width="150">
				<h2>${eventName}</h2>
				<p>${description}</p>
				<p>
					<span class="fa fa-map-marker-alt"></span> 
					<a target="blank" href="https://www.google.com/maps/dir/?api=1&destination=${eventPlace}">${eventPlace}</a>
				</p>
				<p><span class="fa fa-calendar-check"></span>${eventDate}</p>
				<p><span class="fa fa-clock"></span>${startTime} ~ ${endTime}</p>
				<p><span class="fa fa-users"></span>${recruitLimit}</p>
				<p><span class="fa fa-calendar"></span>${recruitmentStartDate} ~ ${recruitmentEndDate}</p>
				<p><span class="fa fa-user-plus"></span>${remainingRecruitLimit}</p>
				<p><span class="fa fa-envelope"></span><a href="mailto:${event}@myouth.jp">${event}@myouth.jp</a></p>
				${forms}
			</div>
		</section>

		<!-- Administrator -->
		<section id="administrator" class="three">
			<h3>管理者</h3>
			<br />
			<div class="container">
				<div class="row">
					${memberLists}
				</div>
				<br />
				<p>管理者ですか？<br />こちらで<a href="/login">ログイン</a>してください。</p>
			</div>
		</section>

		<!-- Media -->
		<section id="media" class="four">
			<h3>メディア</h3>
			<br />
			<div class="container">
				<div class="row">
					${eventImageLists}
				</div>
			</div>
		</section>

		<!-- Analysis -->
		<section id="analysis" class="three">
			<div class="container">
				<h3>分析</h3>
				${eventPageAnalyticsHtmlContent}
			</div>
		</section>

		<!-- Reviews -->
		${reviews}

	</div>

	<!-- Footer -->
	<div id="footer">

		<!-- Copyright -->
		<ul class="copyright">
			<li>Design: <a target="_blank" href="https://html5up.net" rel="noopener">HTML5 UP</a> & <a target="_blank" href="/developer">Rajan Valencia</a>.</li>
		</ul>

	</div>

	<!-- Scripts -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="/resources/prologue/js/jquery.scrolly.min.js"></script>
	<script src="/resources/prologue/js/jquery.scrollex.min.js"></script>
	<script src="/resources/prologue/js/browser.min.js"></script>
	<script src="/resources/prologue/js/breakpoints.min.js"></script>
	<script src="/resources/prologue/js/util.js"></script>
	<script src="/resources/prologue/js/main.js"></script>
	<!-- amCharts Resources -->
	<script src="https://www.amcharts.com/lib/4/core.js"></script>
	<script src="https://www.amcharts.com/lib/4/maps.js"></script>
	<script src="https://www.amcharts.com/lib/4/charts.js"></script>
	<script src="https://www.amcharts.com/lib/4/geodata/worldLow.js"></script>
	<script src="https://www.amcharts.com/lib/4/themes/frozen.js"></script>
	<script src="https://www.amcharts.com/lib/4/themes/material.js"></script>
	<script src="https://www.amcharts.com/lib/4/themes/animated.js"></script>
	
	${eventPageAnalyticsJsContent}

</body>
</html>