<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jp.myouth.db.User"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String userId = (String) session.getAttribute("userId");
	String event = (String) session.getAttribute("event");
	User db = new User();
	db.open();
	String name = db.name(userId);
	String fname = db.fname(userId);
	String word = db.word(userId);
	ArrayList<String> list = db.managingEvents(userId);
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
<title>Home</title>
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
		<section  class="back-button">
		<a href="/logoutRedirect"><span class="fas fa-door-open fa-2x"></span><span class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
		</section>
			<div class="row">
				<div class="col-12">

					<!-- Image -->
					<section class="box">
					<span class="image left"><img class="img-responsive" src="https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/users/default/profile_pic.PNG" alt="" /></span>
					<span class="image right"><a href="/home/profile" style="border-bottom-color: transparent;"><i class="fa fa-2x fa-pencil"></i></a></span>
					<h2><%out.print(name);%></h2>
					<h4><%out.print(fname);%></h4>
					<p><%out.print(word);%></p>
					</section>
					
					<section class="box">
					<div class="row">
					<div class="col-6 col-12-mobilep">
					<h3>Events</h3>
					</div>
					<div class="col-6 col-12-mobilep">
					<ul class="actions">
						<li><a href="#" class="button special"><i class="fa fa-plus faa-pulse animated"></i>    イベント作成</a></li>
					</ul>
					</div>
					</div>
					<div class="table-wrapper">
					<table>
					<tbody>
					<%
					int i = 0;
					for(String string : list){
						if(i % 2 == 0)
							out.println("<tr><td>"+string+"</td>");
						else {
							out.println("<td><a href=\"/events/"+string+"\"><i class=\"fa fa-home faa-tada animated-hover\"></i></a></td>");
							out.println("<td><a href=\"/home/"+string+"/settings\"><i class=\"fa fa-cog faa-spin animated-hover\"></i></a></td>");
							out.println("<td><a href=\"/home/"+string+"/participants\"><i class=\"fa fa-users faa-tada animated-hover\"></i></a></td>");
							out.println("<td><a href=\"/home/"+string+"/downloadSettings\"><i class=\"fa fa-download faa-tada animated-hover\"></i></a></td>");
							out.println("<td><a href=\"/home/"+string+"/mail\"><i class=\"fa fa-envelope faa-tada animated-hover\"></i></a></td>");
						}
						i++;
					}
					%>
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
	$(window).on('load', function(){
		
		$('.img-responsive').each(function() {
			var maxWindowWidth = $(window).width(); // New width
			if(maxWindowWidth >= 1240){
				var maxWidth = 350; // Max width for the image
			    var maxHeight = 150; // Max height for the image
			} else if(maxWindowWidth <= 736 && maxWindowWidth > 480){
				var maxWidth = 200; // Max width for the image
			    var maxHeight = 150; // Max height for the image
			}
			else {
		    	var maxWidth = 100; // Max width for the image
		    	var maxHeight = 150; // Max height for the image
			}
		    var ratio = 0;  // Used for aspect ratio
		    var width = $(this).width();    // Current image width
		    var height = $(this).height();  // Current image height

		    // Check if the current width is larger than the max
		    if(width > maxWidth){
		        ratio = maxWidth / width;   // get ratio for scaling image
		        $(this).css("width", maxWidth); // Set new width
		        $(this).css("height", height * ratio);  // Scale height based on ratio
		        height = height * ratio;    // Reset height to match scaled image
		    }

		    var width = $(this).width();    // Current image width
		    var height = $(this).height();  // Current image height

		    // Check if current height is larger than max
		    if(height > maxHeight){
		        ratio = maxHeight / height; // get ratio for scaling image
		        $(this).css("height", maxHeight);   // Set new height
		        $(this).css("width", width * ratio);    // Scale width based on ratio
		        width = width * ratio;    // Reset width to match scaled image
		    }
			
			});
			
		});
	</script>

</body>
</html>