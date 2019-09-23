<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="jp.myouth.db.Events"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String success = (String) session.getAttribute("success");
	String userId = (String) session.getAttribute("userId");
	String event = (String) session.getAttribute("event");
	Events db = new Events();
	db.open();
	String eventName = db.eventName(event); 
	ArrayList<Boolean> question = db.formQuestion(event);
	db.close();
	
	Boolean name = question.get(0);
	Boolean fname = question.get(1);
	Boolean gender = question.get(2);
	Boolean email = question.get(3);
	Boolean phone = question.get(4);
	Boolean birthdate = question.get(5);
	Boolean career = question.get(6);
	Boolean company = question.get(7);
	Boolean country = question.get(8);
	Boolean country2 = question.get(9);
	Boolean country3 = question.get(10);
	Boolean address = question.get(11);
	Boolean allergy = question.get(12);
	Boolean way = question.get(13);
	Boolean confirmation = question.get(14);
	Boolean parent_confirmation = question.get(15);
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>参加申し込み設定</title>
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
				<a href="/home/<%out.print(event);%>/settings"><span
					class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
			</section>
			<header>
				<h2>Form</h2>
			</header>
			<div class="row">
				<div class="col-12">

					<!-- Form -->
					<section style="background-color: #E8F9DF;"
						class="box <%out.print(success); session.setAttribute("success", "hidden");%>"
						id="success">
						<p>参加申し込み設定は変更されました <i class="fa fa-check faa-tada animated"></i></p>
					</section>
					<section class="box">
						<h3><%out.print(eventName);%>参加申し込み設定</h3>
						<form id="form" method="post"
							action="/changeForm">
							<div class="row gtr-uniform gtr-50">
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="name" name="name" value="true" <%if(name) out.print("checked");%>>
									<label for="name">氏名</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="fname" name="fname" value="true" <%if(fname) out.print("checked");%>>
									<label for="fname">フリガナ</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="gender" name="gender" value="true" <%if(gender) out.print("checked");%>>
									<label for="gender">性別</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="email" name="email" value="true" <%if(email) out.print("checked");%>>
									<label for="email">メールアドレス</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="phone" name="phone" value="true" <%if(phone) out.print("checked");%>>
									<label for="phone">電話番号</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="birthdate" name="birthdate" value="true" <%if(birthdate) out.print("checked");%>>
									<label for="birthdate">生年月日</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="career" name="career" value="true" <%if(career) out.print("checked");%>>
									<label for="career">学年または職業</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="company" name="company" value="true" <%if(company) out.print("checked");%>>
									<label for="company">学校名または会社名</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="country" name="country" value="true" <%if(country) out.print("checked");%>>
									<label for="country">ルーツをもつ国</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="country2" name="country2" value="true" <%if(country2) out.print("checked");%>>
									<label for="country2">ルーツをもつ国（２）</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="country3" name="country3" value="true" <%if(country3) out.print("checked");%>>
									<label for="country3">ルーツをもつ国（３）</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="address" name="address" value="true" <%if(address) out.print("checked");%>>
									<label for="address">住所</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="allergy" name="allergy" value="true" <%if(allergy) out.print("checked");%>>
									<label for="allergy">アレルギー</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="way" name="way" value="true" <%if(way) out.print("checked");%>>
									<label for="way">参加のきかっけ</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="confirmation" name="confirmation" value="true" <%if(confirmation) out.print("checked");%>>
									<label for="confirmation">間違えないかの確認</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="checkbox" id="parent_confirmation" name="parent_confirmation" value="true" <%if(parent_confirmation) out.print("checked");%>>
									<label for="parent_confirmation">親の同意</label>
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
						<a href="/home/<%out.print(event);%>/settings"><span
							class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
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
	<script type="text/javascript">
	
	$('#form').find('input').focus(function() {
		$('#success')
		.addClass('hidden');
	});
	
	$('#form').on('submit',function() {
		$('#swap')
		.html('<li><i class="fa fa-refresh fa-2x faa-spin faa-fast animated"></i></li>');
	});
	</script>
</body>
</html>