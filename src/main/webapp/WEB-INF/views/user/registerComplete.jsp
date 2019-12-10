<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.User"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<title>成功</title>

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

