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
<title>設定</title>
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
		<section  class="back-button">
		<a href="/home"><span class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
		</section>
		<header>
		<h2>Settings</h2>
		</header>
			<div class="row">
				<div class="col-12">
					<section class="box">
						 <div class="table-wrapper">
							<table>
								<tbody>
									<tr>
										<td>
											<a href="/home/${event}/settings/details" style="color: #e89980; border-bottom-color: transparent;">
												<div class="row gtr-50 gtr-uniform">
													<div class="col-4">
														<span class="fa fa-info-circle fa-2x"></span>
													</div>
													<div class="col-8">
														<h3>詳細</h3>
													</div>
												</div>
											</a>
										</td>
									</tr>
									<tr>
										<td>
											<a href="/home/${event}/settings/formTemplates"  style="color: #e89980; border-bottom-color: transparent;">
												<div class="row gtr-50 gtr-uniform">
													<div class="col-4">
														<span class="fa fa-file fa-2x"></span>
													</div>
													<div class="col-8">
														<h3>フォーム</h3>
													</div>
												</div>
											</a>
										</td>
									</tr>
									<tr>
										<td>
											<div class="row gtr-50 gtr-uniform mouse-pointer" style="color: #ff7496;">
												<div class="col-4">
													<span class="fa fa-trash fa-2x"></span>
												</div>
												<div class="col-8" id="deleteEvent">
													<h3>イベントを削除</h3>
												</div>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						 </div>
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
	<script>
		$('#deleteEvent').click(function(){
			var text = prompt('${eventName}イベントを削除したい場合以下の欄にイベントID(${event})を入力してください。');
			
			if(text != '${event}')
				return;

			var params = {
				event	: '${event}',
				apiKey	: '${apiKey}'
			}

			$.ajax({
				type	: 'POST',
				url 	: '/apis/ajax/events/deleteEvent',
				data	: params,
				async	: true,
				success	: function(res){
					if(res)
						window.location.href = '/home';
				},
				error	: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
				}
			})
		});
	</script>
</body>
</html>
