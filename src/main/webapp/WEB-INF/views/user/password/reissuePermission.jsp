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
<title>パスワード再設定</title>
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
				<a href="/home"><span
					class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
			</section>
			<header>
				<h2>Reissue Permission</h2>
			</header>
				<!-- Form -->
			<section class="box ${success}" id="success" ${success}>
				<p style="color: #06A10B;">
					パスワードを再発行するために必要なurlはメールアドレスに送信しました。 
					<i class="fa fa-check faa-tada animated"></i> 
				</p>
				<p>
					<i class="fa fa-exclamation-triangle" style="color: #ff7496;"></i>
					30分経過したら自動的に無効になりますのでご注意ください。
					<br />
					メールに記載されているリンクは一度のみクリックできます。
				</p>
			</section>
			<section class="box ${failure}" id="failure" ${failure}>
				<p style="color: #ff7496;">
					そのようなメールアドレスは登録されてません。
					<i class="fa fa-times faa-pulse animated"></i> 
				</p>
			</section>
			<section class="box">
				<div class="row">
					<form id="form" method="post" action="/sendReissuePermission">
						<div class="row gtr-50 gtr-uniform">
							<div class="col-12">
								<label for="email">登録されてるメールアドレス</label> 
								<input type="email" name="email" required />
							</div>
							<div id="btn" class="col-12">
								<ul class="actions" id="swap">
									<li><input id="btn" type="submit" value="パスワード再発行 " /></li>
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
	<script>
		$(function(){
			
			$('form').submit(function(){
				$('#btn').hide();
				$('#loading').attr('hidden', false);
			})
			
			var successAttr = $('#success').attr('hidden');
			var failureAttr = $('#failure').attr('hidden');
			
		    if(successAttr != 'hidden'){
		    	hideSection('success');
		    }
		   
		    if(failureAttr != 'hidden'){
				hideSection('failure');
		    }
		})
		
		function hideSection(sectionId){
			setTimeout(function(){
				$('#'+sectionId+'').toggle('blind');
			}, 5000)
		}
	</script>
</body>
</html>