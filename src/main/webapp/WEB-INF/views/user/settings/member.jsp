<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.User"%>
<%@ page import="java.util.ArrayList"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String userId = (String) session.getAttribute("userId");
	String event = (String) session.getAttribute("event");
	
	ArrayList<String> searchResults = (ArrayList<String>) session.getAttribute("searchResults");
	
	User db = new User();
	db.open();
	ArrayList<String> data = db.eventMember(event);
	db.close();
	
	int i = 0;
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>メンバー</title>
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
		<a href="/home/<%out.print(event);%>/settings"><span class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
		</section>
		<header>
		<h2>Member</h2>
		</header>
			<div class="row">
				<div class="col-12">

					<!-- Image -->
					<section class="box">
						<div class="col-12 col-12-mobilep">
							<section class="box">
								<form method="post" action="/home/<%out.print(event);%>/settings/member/search">
									<div class="row gtr-uniform gtr-50">
										<div class="col-9 col-12-mobilep">
											<input type="text" name="search" id="search" value=""
												placeholder="新たなメンバーを追加" />
										</div>
										<div class="col-3 col-12-mobilep">
												<input type="submit" value="検索" class="fit" />
										</div>
									</div>
								</form>
								<table>
								<tbody>
										<%
											if (searchResults != null) {
												i = 0;
												for (String string : searchResults) {
													if (i % 2 == 0)
														out.print(
																"<tr><td><img class=\"img-responsive\" src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/users/default/profile_pic.PNG\" alt=\"\" /></td><td>"
																		+ string + "</td>");
													else
														out.println("<td><a href=\"/home/" + event + "/settings/member/add/"+ string
																+ "\"><i class=\"fa fa-user-plus faa-pulse animated faa-hover\"></i></a></td></tr>");
													i++;
												}
											}
										%>
									</tbody>
								</table>
								<div class="table-wrapper">
							<table>
								<tbody>
								<%
									if (searchResults == null) {
										i = 0;
										for (String string : data) {
											if (i % 2 == 0)
												out.print(
														"<tr><td><img class=\"img-responsive\" src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/users/default/profile_pic.PNG\" alt=\"\" /></td><td>"
																+ string + "</td>");
											else
												out.println("<td><a href=\"/home/" + event + "/settings/member/remove/"+ string
														+ "\"><i class=\"fa fa-remove faa-pulse animated faa-hover\"></i></a></td></tr>");
											i++;
										}
									}
								session.setAttribute("searchResults", null);
								%>
								</tbody>
							</table>
						</div>
					</section>
						</div>
					</section>
				</div>
			</div>
		<section  class="back-button">
			<a href="/home/<%out.print(event);%>/settings"><span class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
		</section>
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
				var maxWidth = 70; // Max width for the image
			    var maxHeight = 70; // Max height for the image
			} else if(maxWindowWidth <= 736 && maxWindowWidth > 480){
				var maxWidth = 50; // Max width for the image
			    var maxHeight = 50; // Max height for the image
			}
			else {
		    	var maxWidth = 50; // Max width for the image
		    	var maxHeight = 50; // Max height for the image
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
