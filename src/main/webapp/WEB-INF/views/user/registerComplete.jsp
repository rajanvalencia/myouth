<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.User"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>成功</title>

<link rel="apple-touch-icon" sizes="180x180"
	href="/resources/favicon/apple-touch-icon.png">
<link rel="icon" type="image/png" sizes="32x32"
	href="/resources/favicon/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16"
	href="/resources/favicon/favicon-16x16.png">
<link rel="manifest" href="/resources/favicon/site.webmanifest">
<link rel="mask-icon" href="/resources/favicon/safari-pinned-tab.svg"
	color="#5bbad5">
<meta name="msapplication-TileColor" content="#2d89ef">
<meta name="theme-color" content="#ffffff">

<link rel="stylesheet" href="/resources/css/success.css">
<link rel='stylesheet'
	href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css'>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-143752853-1"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-143752853-1');
</script>

</head>

<body>

	<div class="svg-container">
		<svg class="ft-green-tick" xmlns="http://www.w3.org/2000/svg"
			height="100" width="100" viewBox="0 0 48 48" aria-hidden="true">
        <circle class="circle" fill="#5bb543" cx="24" cy="24" r="22" />
        <path class="tick" fill="none" stroke="#FFF" stroke-width="6"
				stroke-linecap="round" stroke-linejoin="round"
				stroke-miterlimit="10" d="M14 27l5.917 4.917L34 17" />
    </svg>
		<br> <br>
		<h2>ご登録<br />ありがとうございます</h2>
		<br />
		<div style="color: #D61A46;">
		<p>アカウントを利用するには<br />メールアドレスを認証しなければなりません</p>
		</div>
		<br> <br>
		<div class="btn-group-vertical btn-group-lg" role="group"
			aria-label="Basic example">
			<a class="btn btn-secondary" href="/login"
				role="button"><i class="glyphicon glyphicon-log-in">
				ログインページ</i></a>
		</div>
	</div>
	<script src="/resources/js/success.js"></script>
</body>

</html>

