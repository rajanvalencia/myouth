<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.Events" %>
<%@ page import="jp.myouth.tables.*" %>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String event = (String) session.getAttribute("event");
	Events db = new Events();
	db.open();
	String path = db.eventLogo(event);
	db.close();
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>画像</title>
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
		<h2>Photos and Videos</h2>
		</header>
			<div class="row">
				<div class="col-12">

					<!-- Image -->
					<section class="box">
						<form id="profilePicUplaodForm" enctype="multipart/form-data" method="post" action="/uploadEventLogo">
							<input type="file" accept="image/*" class="hidden" name="eventLogo" id="imgupload">
							<span class="image profile-image-center"><img style="border-radius: 10px;" src="<% out.print(path);%>" alt="" width="140"/></span>
							<div class="col-12">
								<ul class="actions">
									<li><input id="OpenImgUpload" type="button" value="イベントのロゴを変更する" /></li>
  								</ul>
  							</div>
  						</form>
  						<form id="uploadEventImageForm" enctype="multipart/form-data" method="post" action="/uploadEventImage">
							<input type="file" accept="image/*" class="hidden" name="eventImage" id="uploadEventImageButton">
  						</form>
						<% 
							UserPageImageLists list = new UserPageImageLists();
							list.append(request);
							out.print(request.getAttribute("userPageImageLists"));
							request.removeAttribute("userPageImageLists");
						%>
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
 		$('#OpenImgUpload').click(function(){ $('#imgupload').trigger('click'); });
	 	$('#imgupload').on('change', function(){ $('#profilePicUplaodForm').submit(); });
		
	 	$('#uploadEventImageIcon').click(function(){ $('#uploadEventImageButton').trigger('click'); });
	 	$('#uploadEventImageButton').on('change', function(){ $('#uploadEventImageForm').submit(); });
	</script>

</body>
</html>
