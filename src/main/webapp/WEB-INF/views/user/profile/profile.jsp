<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="jp.myouth.db.Events"%>
<%@ page import="jp.myouth.db.User"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String success = (String)session.getAttribute("success");
	String userId = (String) session.getAttribute("userId");
	String event = (String) session.getAttribute("event");
	User db = new User();
	db.open();
	String name = db.name(userId);
	String fname = db.fname(userId);
	String word = db.word(userId);
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
<title>プロフィルの編集</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
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
					class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
			</section>
			<header>
				<h2>Edit Profile</h2>
			</header>
			<div class="row">
				<div class="col-12">

					<!-- Form -->
					<section style="background-color: #E8F9DF;" class="box <%out.print(success);session.setAttribute("success", "hidden");%>"
						id="success">
						<p>プロフィールは更新されました <i class="fa fa-check faa-tada animated"></i></p>
					</section>
					<section class="box">
						<form id="profilePicUplaodForm" enctype="multipart/form-data" method="post" action="/uploadProfilePicture">
								<input type="file" class="hidden" name="profilePicture" id="imgupload">
								<span class="image profile-image-center"><img class="img-responsive" style="border-radius: 50%;" src="https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/users/default/profile_pic.PNG" alt="" /></span>
								<div class="col-12">
									<ul class="actions">
										<li><input id="OpenImgUpload" type="button" value="プロフィール写真を変更する" /></li>
  									</ul>
  								</div>
  						</form>
						<form id="form" method="post"
							action="#">
							<div class="row gtr-uniform gtr-50">
								<div class="col-12">
								<label for="name">名前</label>
									<input type="text" name="name" id="name" value="<% out.print(name);%>" />
								</div>
								<div class="col-12">
								<label for="fname">フリガナ</label>
									<input type="text" name="fname" id="fname" value="<% out.print(fname);%>" />
								</div>
								<div class="col-12">
								<label for="career">一言</label>
									<input type="text" name="word" id="word" value="<% out.print(word);%>" />
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
						<a href="/home"><span
							class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
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
	
$(window).on('load', function(){
		
		$('.img-responsive').each(function() {
			var maxWindowWidth = $(window).width(); // New width
			var maxWidth = 200; // Max width for the image
			var maxHeight = 150; // Max height for the image
		    var ratio = 0;  // Used for aspect ratio
		    var width = $(this).width();    // Current image width
		    var height = $(this).height();  // Current image height

		    // Check if the current width is larger than the max
		    if(width > maxWidth){
		        ratio = maxWidth / width;   
		        $(this).css("width", maxWidth); // Set new width
		        $(this).css("height", height * ratio);  // Scale height based on ratio
		        height = height * ratio;    // Reset height to match scaled image
		    }

		    var width = $(this).width();    // Current image width
		    var height = $(this).height();  // Current image height

		    // Check if current height is larger than max
		    if(height > maxHeight){
		        ratio = maxHeight / height; 
		        $(this).css("height", maxHeight);   // Set new height
		        $(this).css("width", width * ratio);    // Scale width based on ratio
		        width = width * ratio;    // Reset width to match scaled image
		    }
			
			});
			
		});
		
	</script>
	
</body>
</html>