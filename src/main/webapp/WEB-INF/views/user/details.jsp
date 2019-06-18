<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="jp.myouth.db.Events"%>
<!DOCTYPE HTML>
<!--
	Identity by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String userId = (String) session.getAttribute("userId");
	String event = (String) session.getAttribute("event");
	Events db = new Events();
	db.open();
	String eventName = db.eventName(event);
	String eventPlace = db.eventPlace(event);
	ArrayList<String> eventTime = db.eventTime(event);
	ArrayList<String> eventDate = db.eventDate(event);
	db.close();
%>
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
<title>Home</title>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/resources/user/css/main.css" />
<noscript>
	<link rel="stylesheet" href="/resources/user/css/noscript.css" />
</noscript>
</head>
<body class="is-preload">

	<!-- Wrapper -->
	<div id="wrapper">

		<!-- Main -->
		<section id="main">

			<hr />
			<h2>イベント設定</h2>
			<form method="post" action="#">
				<div class="fields">
					<div class="field">
						<label for="name">名前</label> <input type="text" name="name"
							id="name" value="<%out.println(eventName);%>" />
					</div>
					<div class="field">
						<label for="name">開催場所</label> <input type="text" name="place"
							id="place" value="<%out.println(eventPlace);%>" />
					</div>
					<div class="field">
						<label for="date">開催日</label> <select class="form-control"
							name="birth-year" id="years">
							<option label="<%out.print(eventDate.get(0));%>" selected></option>
						</select> <select class="form-control" name="birth-month" id="months">
							<option label="<%out.print(eventDate.get(1));%>" selected></option>
						</select> <select class="form-control" name="birth-day" id="days">
							<option label="<%out.print(eventDate.get(2));%>" selected></option>
						</select>
					</div>
					<div class="field">
						<label for="time">開始時間</label> <select class="form-control"
							name="time" id="time">
							<option label="<%out.print(eventTime.get(0)+":"+eventTime.get(1));%>0" selected></option>
						</select>
					</div>
				</div>
				<div class="actions special">
					<input type="submit" value="保存" />
				</div>
			</form>
			<hr />
			<footer>
				<ul class="icons">
					<li><a href="/home/events/<%out.print(event);%>"
						class="fa-arrow-left">Back</a></li>
					<li><a href="/home" class="fa-home">Home</a></li>
				</ul>
			</footer>
		</section>

		<!-- Footer -->
		<footer id="footer">
			<ul class="copyright">
				<li>myouth.jp</li>
				<li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
			</ul>
		</footer>

	</div>

	<!-- Scripts -->
	<script>
		if ('addEventListener' in window) {
			window.addEventListener('load', function() {
				document.body.className = document.body.className.replace(
						/\bis-preload\b/, '');
			});
			document.body.className += (navigator.userAgent
					.match(/(MSIE|rv:11\.0)/) ? ' is-ie' : '');
		}
	</script>
	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
	<script type="text/javascript">
		function changeSubject(e) {
			if (e.target.value === 'other-grade') {

				$('#hidden-other-grade').removeClass('hidden');
			} else if (e.target.value === 'other-trigger') {
				$('#hidden-other-trigger').removeClass('hidden');
			}

			else {
				$('#hidden-other-trigger').addClass('hidden');
				$('#hidden-other-grade').addClass('hidden');
			}
		}

		$(function() {

			//populate our years select box
			for (i = new Date().getFullYear(); i <= new Date().getFullYear() + 1; i++) {
				if (i != <%out.print(eventDate.get(0));%>)
					$('#years').append($('<option />').val(i).html(i));
			}
			//populate our months select box
			for (i = 1; i < 13; i++) {
				if (i != <%out.print(eventDate.get(1));%>)
					$('#months').append($('<option />').val(i).html(i));
			}
			
			//populate our time select box
			for (i = 0; i < 24; i++) {
				if (i != <%out.print(eventTime.get(0));%>){
					$('#time').append($('<option />').val(i+':00').html(i+':00'));
					$('#time').append($('<option />').val(i+':30').html(i+':30'));
				}
				else if(<%out.print(eventTime.get(1));%> == 3)
					$('#time').append($('<option />').val(i+':00').html(i+':00'));
				
				else if(<%out.print(eventTime.get(1));%> == 0)
					$('#time').append($('<option />').val(i+':30').html(i+':30'));
			}
			//populate our Days select box
			updateNumberOfDays();

			//"listen" for change events
			$('#years, #months').change(function() {
				updateNumberOfDays();
			});

		});

		//function to update the days based on the current values of month and year
		function updateNumberOfDays() {
			$('#days').html('');
			month = $('#months').val();
			year = $('#years').val();
			days = daysInMonth(month, year);

			$('#days').append($('<option />').val(<%out.print(eventDate.get(2));%>).html(<%out.print(eventDate.get(2));%>));
			for (i = 1; i < days + 1; i++) {
				if (i != <%out.print(eventDate.get(2));%>)
					$('#days').append($('<option />').val(i).html(i));
			}
		}

		//helper function
		function daysInMonth(month, year) {
			return new Date(year, month, 0).getDate();
		}
	</script>

</body>
</html>