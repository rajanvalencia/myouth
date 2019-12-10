<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML>
<!--
	Eventually by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
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
<meta name="msapplication-TileColor" content="#2d89ef">
<meta name="theme-color" content="#ffffff">
<title>myouthログイン</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="/resources/eventually/css/main.css" />
<link rel="stylesheet" href="/resources/css/font-awesome-animation.css">

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
	<header id="header">
		<a href="/" style="border-bottom-color: transparent;"><span class="icon fa fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
		<h1>Login to myouth</h1>
	</header>
	
	<form id="signup-form">
	<input type="button" value="新規登録" style="background-color: #FF7700;" onclick="window.location.href='/registerUser';"/>
	<input type="button" value="パスワードお忘れですか？" style="background-color: #368ce7;" onclick="window.location.href='/reissuePermission';"/>
	</form>
	
	<!-- Signup Form -->
	<form id="signup-form" method="post" action="/loginRedirect">
	<span class="message" id="hello"></span>
		<input type="email" name="email" id="email" placeholder="メールアドレス" required />
		<input type="password" name="password" id="パスワード" placeholder="パスワード" required /> <input type="submit" value="ログイン" />
		<input type="hidden" name="token" value="${loginToken}" />
	</form>

	<!-- Footer -->
	<footer id="footer">
		<ul class="copyright">
			<li>myouth.jp</li>
			<li>Credits: <a href="http://html5up.net">HTML5 UP</a></li>
			<li>Images: <a href="http://unsplash.com/">UNSPLASH</a></li>
		</ul>
	</footer>

	<!-- Scripts -->
	<script src="/resources/eventually/js/main.js"></script>
	<script>
		
		// Signup Form.
		(function() {

			// Vars.
			var $form = document.querySelectorAll('#signup-form')[0], $submit = document
					.querySelectorAll('#signup-form input[type="submit"]')[0], $message;

			// Bail if addEventListener isn't supported.
			if (!('addEventListener' in $form))
				return;

			// Message.
			$message = document.createElement('span');
			$message.classList.add('message');
			$form.appendChild($message);

			$message._show = function(type, text) {

				$message.innerHTML = text;
				$message.classList.add(type);
				$message.classList.add('visible');

				window.setTimeout(function() {
					$message._hide();
				}, 3000);

			};

			$message._hide = function() {
				$message.classList.remove('visible');
			};
			
			${loginFailureText}
		})();
	</script>

</body>
</html>