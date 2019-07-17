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

<meta name="author" content="バレンシア　ラジャン　ザモラ">

<meta name="description"
	content="誰でも無料にイベント管理が出来るWeb上にあるシステムです。参加申し込み、アンケートデータの分析までの自動処理を行います。一般公開されるまで数か月お待ちください">
	
		<title>myouth イベント管理システム</title>
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
				 <h1 class="hidden">誰でも無料にイベント管理が出来るWeb上にあるシステムです。参加申し込み、アンケートデータの分析までの自動処理を行います。一般公開されるまで数か月お待ちください</h1>
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
					<ul>
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
							<p>誰でも簡単にイベントを主催し、管理することができるサービスです。
							<br />
							あなたもmyouthでイベントを管理してみませんか?
							</p>
						</header>
						<span class="image featured"><img src="/resources/images/event.jpg" alt="" /></span>
					</section>

					<section class="box special features">
						<div class="features-row">
							<section>
								<span class="icon solid major fa fa-cog faa-pulse animated accent2"></span>
								<h3>管理</h3>
								<p>参加申し込み、アンケート、データのグラフ化、ダウンロードするcsvファイルまでリアルタイムで更新され、イベントの運営効率を上げられます。</p>
							</section>
							<section>
								<span class="icon solid major fa-envelope faa-pulse animated accent3"></span>
								<h3>コンタクト</h3>
								<p>一人一人の参加者に対してのメールやイベントごとの参加者への一斉送信などの連絡ができます。</p>
							</section>
						</div>
						<div class="features-row">
							<section>
								<span class="icon solid major fa-cloud faa-pulse animated accent4"></span>
								<h3>拡大</h3>
								<p>利用者（管理者）のアカウント一つでいくつものイベントを運用することができます。その上、各イベントに管理者を登録可能です。</p>
							</section>
							<section>
								<span class="icon solid major fa-lock faa-pulse animated accent5"></span>
								<h3>セキュリティー</h3>
								<p>パスワードをデータベースに保存しないアルゴリズムとによって厳重なセキュリティーを確立しています。その他のセキュリティ対策もしています。</p>
							</section>
						</div>
					</section>

					<div class="row">
					<div class="col-6 col-12-narrower">

							<section class="box special">
								<span class="image featured"><img src="https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/events/multiculturalyouth/myouth-1.jpg" alt="" /></span>
								<h3>多文化ユース</h3>
								<p>多文化ユースはmyouthで管理されている最初のイベントです。myouthは多文化ユースの英訳である「multiculturalyouth」からきています。
								多文化ユースにご興味のある方は詳細をクリックしてください。</p>
								<ul class="actions special">
									<li><a href="/events/multiculturalyouth" class="button alt">詳細</a></li>
								</ul>
							</section>

						</div>
						<div class="col-6 col-12-narrower">
							<section class="box special">
								<span class="image featured"><img src="https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/rajanValencia/creator.jpg" alt="" /></span>
								<h3>Developer</h3>
								<p>日本に来て<span id="stay"></span>年、フィリピンとインドのミックスで多文化ユースの実行委員の一人でもあります。第一回のイベントで参加者名簿の担当でしたが、その大変さを知ってしまい、自らイベント管理システムを製作しました。
								資格や成果物などを閲覧したい場合は詳細をクリックしてください。</p>
								<ul class="actions special">
									<li><a href="myouthCreator" class="button alt">詳細</a></li>
								</ul>
							</section>

						</div>
						
					</div>

				</section>

			<!-- CTA -->
				<!--  <section id="cta">

					<h2>Sign up for beta access</h2>
					<p>Blandit varius ut praesent nascetur eu penatibus nisi risus faucibus nunc.</p>

					<form>
						<div class="row gtr-50 gtr-uniform">
							<div class="col-8 col-12-mobilep">
								<input type="email" name="email" id="email" placeholder="Email Address" />
							</div>
							<div class="col-4 col-12-mobilep">
								<input type="submit" value="Sign Up" class="fit" />
							</div>
						</div>
					</form>

				</section>-->

			<!-- Footer -->
				<footer id="footer">
					<ul class="copyright">
						<li>myouth.jp</li>
						<li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
						<li>Images: <a href="http://unsplash.com/">UNSPLASH</a></li>
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
	</body>
	
	<script>
	$(function() {
	    //stayed years in Japan
		$('#stay').html(new Date().getFullYear() - 2010);
	});
	</script>
</html>