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
<title>${eventName}参加申し込み</title>
<meta charset="utf-8" />
<meta name="description" content="開催日：${eventDate} 開催場所:${eventPlace} 時間:${eventStartTime} ~ ${eventEndTime} 募集人数:${recruitLimit} 募集期間:${recruitmentStartDate} ~ ${recruitmentEndDate} 残り募集人数:${remainingRecruitLimit}">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
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
<link rel="stylesheet" href="/resources/css/starRatingsInput.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/resources/css/font-awesome-animation.css">

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async
	src="https://www.googletagmanager.com/gtag/js?id=UA-143752853-1"></script>
<script>
	window.dataLayer = window.dataLayer || [];
	function gtag() {
		dataLayer.push(arguments);
	}
	gtag('js', new Date());

	gtag('config', 'UA-143752853-1');
</script>

</head>
	<body class="is-preload">
		<div id="page-wrapper">
			<!-- Main -->
				<section id="main" class="container">
				<section  class="back-button">
					<a href="/events/${event}"><span class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
				</section>
				<section class="box ${success}" id="success" style="color: #06A10B;" ${success}>
					正常に送信されました <i class="fa fa-check faa-tada animated"></i>
				</section>
				${formTemplateTitleAndDescription}
				<section class="box">
					<div class="row">
						<form id="form" method="post" action="/forms/submitForm">
							<div class="row gtr-50 gtr-uniform">
								<div class="col-12">
									<label for="name">名前 <span class="fa fa-asterisk" style="color: #ff7496;"></span></label>
									<input type="text" id="name" name="name" value="" required/>
								</div>
								<div class="col-12">
									<label for="fname">フリガナ<span class="fa fa-asterisk" style="color: #ff7496;"></span></label>
									<input type="text" id="fname" name="fname" value="" required/>
								</div>
								<div class="col-12">
									<label>性別 <span class="fa fa-asterisk" style="color: #ff7496;"></span></label>
								</div>
								<div class="col-4">
									<input type="radio" id="male" name="gender" value="男性" required/>
									<label for="male">男性</label>
								</div>
								<div class="col-4">
									<input type="radio" id="female" name="gender" value="女性" />
									<label for="female">女性</label>
								</div>
								<div class="col-12">
									<label for="email-address">メールアドレス <span class="fa fa-asterisk" style="color: #ff7496;"></span></label>
									<input type="email" id="emailAddress" name="emailAddress" value="" required/>
								</div>
								<div class="col-3 col-5-mobile">
									<label for="birthdate">生年月日 <span class="fa fa-asterisk" style="color: #ff7496;"></span></label>
									<input type="date" id="birthdate" name="birthdate" value="" required/>
								</div>
								<div class="col-10 col-8-mobile">
								</div>
								<input type="hidden" name="event" value="${event}" />
								<input type="hidden" name="templateId" value="${templateId}" />
								${formTemplate}
								<div id="btn" class="col-12">
									<ul class="actions">
										<li>
											<input type="submit" value="送信"/>
										</li>
									</ul>
								</div>
								<div id="loading" class="col-12" hidden>
									<ul class="actions">
										<li>
											<i class="fa fa-refresh fa-2x faa-spin faa-fast animated"></i>
										</li>
									</ul>
								</div>
							</div>
						</form>
					</div>
				</section>
				<section  class="back-button">
					<a href="/events/${event}"><span class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
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
						<li>myouth.jp</li><li>Design: <a target="_blank" rel="noopener" href="http://html5up.net">HTML5 UP</a> & <a target="_blank" href="/developer">Rajan Valencia</a>.</li>
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
		<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>
		<!--<script src="/resources/alpha/js/main.js"></script>-->

		<!--Send API-->
		<script>
			$(function(){
				$('form').submit(function(){
					$('#btn').hide();
					$('#loading').attr('hidden', false);
				})
				
				var successAttr = $('#success').attr('hidden');
				if(successAttr != 'hidden'){
					setTimeout(function(){
						$('#success').toggle('blind');
					}, 8000);
				}
			})
		</script>
	</body>
</html>