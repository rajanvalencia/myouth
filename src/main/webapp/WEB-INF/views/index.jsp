<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.Events" %>
<%@ page import="jp.myouth.db.User" %>
<%@ page import="jp.myouth.db.Participants" %>
<% 
Events db = new Events();
db.open();
Integer totalEvents = db.totalEvents();
db.close();

User db1 = new User();
db1.open();
Integer totalUsers = db1.totalUsers();
db1.close();

Participants db2 = new Participants();
db2.open();
Integer totalParticipants = db2.allEventsTotalParticipants();
db2.close();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
	Alpha by HTML5 UP
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

<meta name="author" content="バレンシア　ラジャン　ザモラ">

<meta name="description"
	content="誰でも無料にイベント管理が出来るWeb上にあるシステムです。参加申し込み、アンケートデータの分析までの自動処理を行います。一般公開されるまで数か月お待ちください">
	
<title>myouth</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="/resources/alpha/css/main.css" />
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
	<body class="landing is-preload">
		<div id="page-wrapper">

			<!-- Header -->
			 <header id="header" class="alt reveal">
				 <h1 class="hidden">誰でも無料にイベント管理が出来るウエブサービスです。参加申し込み、アンケートデータの分析までの自動処理を行います。</h1>
					  <!-- <nav id="nav">
						<ul>
							<li><a href="index.html">Home</a></li>
							<li>
								<a href="#" class="icon solid fa-angle-down">Layouts</a>
								<ul>
									<li><a href="generic.html">Generic</a></li>
									<li><a href="contact.html">Contact</a></li>
									<li><a href="elements.html">Elements</a></li>
									<li>
										<a href="#">Submenu</a>
										<ul>
											<li><a href="#">Option One</a></li>
											<li><a href="#">Option Two</a></li>
											<li><a href="#">Option Three</a></li>
											<li><a href="#">Option Four</a></li>
										</ul>
									</li>
								</ul>
							</li>
							<li><a href="#" class="button">Sign Up</a></li>
						</ul>
					</nav> -->
				</header> 



			<!-- Banner -->
				<section id="banner">
					<h2>myouth</h2>
					<h4>イベント管理システム</h4>
					<ul class="actions special">
						<li><a href="registerUser" class="button primary">無料登録</a></li>
						<li><a href="login" class="button primary">ログイン</a></li>
					</ul>
					<ul class="alt">
						<li><a href="events" class="button">イベント一覧</a></li>
					</ul>
					<h4>現在のイベント数は <% out.print(totalEvents);%></h4>
					<h4>利用者(管理者)数は <% out.print(totalUsers);%> 人</h4>
					<h4>全イベントの参加者数は <% out.print(totalParticipants);%> 人</h4>
				</section>
			<!-- Main -->
				<section id="main" class="container">

					<section class="box special">
						<header class="major">
							<h2>myouthとは？</h2>
							<p>イベントを管理することができるウエブサービスです。
							<br />
							あなたもmyouthでイベントを管理して仕事の大幅な時間短縮を目指しませんか?
							</p>
						</header>
						<span class="image featured"><img class="center" src="https://media.giphy.com/media/3oKIPEqDGUULpEU0aQ/giphy.gif" alt="" style="border-radius: 10px;"/></span>
					</section>

					<section class="box special features">
						<div class="features-row">
							<section>
								<span class="icon solid major fa fa-cog accent2"></span>
								<h3>管理</h3>
								<p>参加申し込み、アンケート、データのグラフ化、ダウンロードするcsvファイルまでリアルタイムで更新され、イベントの運営効率を上げられます。</p>
							</section>
							<section>
								<span class="icon solid major fa-envelope accent3"></span>
								<h3>コンタクト</h3>
								<p>各イベントの全参加者への一斉送信などの連絡ができます。その上、既読者数または既読者を把握することができます。</p>
							</section>
						</div>
						<div class="features-row">
							<section>
								<span class="icon solid major fa-cloud accent4"></span>
								<h3>拡大</h3>
								<p>利用者（管理者）のアカウント一つでいくつものイベントを運用することができます。その上、各イベントに管理者を登録可能です。</p>
							</section>
							<section>
								<span class="icon solid major fa-image accent5"></span>
								<h3>コンテンツ</h3>
								<p>不適切な画像や投稿を機械学習で自動的に排除します。投稿は多様な言語に対応しています。デザインはレスポンシブであるためPC,スマホー、タブレットで操作可能です。</p>
							</section>
						</div>
					</section>

					<div class="row">
						<div class="col-12">
							<section class="box special">
								<div class="center">
									<h3>開発者</h3>
									<span class="image featured"><img src="https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/rajanValencia/creator.jpg" alt="" style="border-radius: 10px;"/></span>
									<p>バレンシア・ラジャン・ザモラです。日本に来て<span id="stay"></span>年、フィリピンとインドのミックスで多文化ユースの実行委員の一人でもあります。</p>
									<p>第一回のイベントで参加者名簿の担当でしたが、その大変さを知ってしまい、自らイベント管理システムを開発しました。
									資格や過去のプロジェクトなどを閲覧したい場合は詳細をクリックしてください。</p>
								</div>
								<ul class="actions special">
									<li><a href="myouthCreator" class="button alt">詳細</a></li>
								</ul>
							</section>

						</div>
						
					</div>

				</section>

			<!-- Footer -->
				<footer id="footer">
					<ul class="copyright">
						<li>Design: <a href="https://html5up.net">HTML5 UP</a></li>
						<li>Images: <a href="https://giphy.com">GIPHY</a></li>
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
			<!-- <script src="/resources/alpha/js/main.js"></script> -->
	</body>
	
	<script>
	$(function() {
	    //stayed years in Japan
		$('#stay').html(new Date().getFullYear() - 2010);
	});
	</script>
</html>