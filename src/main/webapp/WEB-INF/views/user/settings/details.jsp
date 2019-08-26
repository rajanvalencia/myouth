<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="jp.myouth.db.Events"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String success = (String)session.getAttribute("success");
	String userId = (String) session.getAttribute("userId");
	String event = (String) session.getAttribute("event");
	Events db = new Events();
	db.open();
	String eventName = db.eventName(event);
	String eventPlace = db.eventPlace(event);
	ArrayList<String> eventDate = db.eventDate(event);
	ArrayList<String> eventTime = db.eventTime(event);
	ArrayList<String> recruitmentStartDate = db.recruitmentStartDate(event);
	ArrayList<String> recruitmentEndDate = db.recruitmentEndDate(event);
	String description = db.eventDescription(event);
	int recruitNo = db.recruitmentLimit(event);
	db.close();

	String year = eventDate.get(0);
	String month = eventDate.get(1);
	String day = eventDate.get(2);

	int hour = Integer.valueOf(eventTime.get(0));
	int minute = Integer.valueOf(eventTime.get(1));
	
	String startYear = recruitmentStartDate.get(0);
	String startMonth = recruitmentStartDate.get(1);
	String startDay = recruitmentStartDate.get(2);

	String endYear = recruitmentEndDate.get(0);
	String endMonth = recruitmentEndDate.get(1);
	String endDay = recruitmentEndDate.get(2);
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>詳細設定</title>
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
				<a href="/home/<%out.print(event);%>/settings"><span
					class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
			</section>
			<header>
				<h2>Edit Event Details</h2>
			</header>
			<div class="row">
				<div class="col-12">

					<!-- Form -->
					<section style="background-color: #E8F9DF;" class="box <%out.print(success);session.setAttribute("success", "hidden");%>"
						id="success">
						<p>詳細設定は変更されました <i class="fa fa-check faa-tada animated"></i></p>
					</section>
					<section class="box">
						<form id="form" method="post"
							action="/changeEventDetails">
							<div class="row gtr-uniform gtr-50">
								<div class="col-12">
								<label for="eventName">イベント名</label>
									<input type="text" name="eventName" id="eventName"
										value="<%out.print(eventName);%>" />
								</div>
								<div class="col-12">
								<label for="eventPlace">開催場所</label>
									<input type="text" name="eventPlace" id="eventPlace"
										value="<%out.print(eventPlace);%>" />
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="year">開催予定</label>
									<select name="year" id="years">
										<option value="<%out.print(year);%>" selected>
											<%
												out.print(year);
											%>
										</option>
									</select>
								</div>

								<div class="col-2 col-4-mobilep">
								<label for="month">月</label>
									<select name="month" id="months">
										<option value="<%out.print(month);%>" selected>
											<%
												out.print(month);
											%>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="day">日</label>
									<select name="day" id="days">
										<option value="<%out.print(day);%>" selected>
											<%
												out.print(day);
											%>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="hour">開始時間</label>
									<select name="hour" id="hour">
										<option value="<%if(hour < 10) out.print("0"+hour); else out.print(hour);%>" selected>
											<%
											if(hour < 10) out.print("0"+hour); else out.print(hour);
											%>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="minute">分</label>
									<select name="minute" id="minute">
										<option value="<%if(minute < 10) out.print("0"+minute); else out.print(minute);%>" selected>
											<%
											if(minute < 10) out.print("0"+minute); else out.print(minute);
											%>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="recruitNo">募集人数</label>
									<input type="number" name="recruitNo" id="recruitNo"
										value="<%out.print(recruitNo);%>" />
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="startYear">募集期間</label>
									<select name="startYear" id="startYear">
										<option value="<%out.print(startYear);%>" selected>
											<%
												out.print(startYear);
											%>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="startMonth">月</label>
									<select name="startMonth" id="startMonth">
										<option value="<%out.print(startMonth);%>" selected>
											<%
												out.print(startMonth);
											%>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="startDay">日</label>
									<select name="startDay" id="startDay">
										<option value="<%out.print(startDay);%>" selected>
											<%
												out.print(startDay);
											%>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="endYear">募集〆切</label>
									<select name="endYear" id="endYear">
										<option value="<%out.print(endYear);%>" selected>
											<%
												out.print(endYear);
											%>
										</option>
									</select>
								</div>

								<div class="col-2 col-4-mobilep">
								<label for="endMonth">月</label>
									<select name="endMonth" id="endMonth">
										<option value="<%out.print(endMonth);%>" selected>
											<%
												out.print(endMonth);
											%>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="endDay">日</label>
									<select name="endDay" id="endDay">
										<option value="<%out.print(endDay);%>" selected>
											<%
												out.print(endDay);
											%>
										</option>
									</select>
								</div>
								<div class="col-12">
									<label for="description">イベント紹介</label>
									<textarea name="description" id="description" placeholder="<%out.print(description);%>"
										rows="6"></textarea>
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
		$(function() {

			//populate our years select box
			for (i = new Date().getFullYear(); i <= new Date().getFullYear() + 1; i++) {
				if (i != <%out.print(year);%>)
					$('#years').append($('<option />').val(i).html(i));
			}
			//populate our months select box
			for (i = 1; i <= 12; i++) {
				if (i != <%out.print(month);%>)
					$('#months').append($('<option />').val(i).html(i));
			}
			
			//populate our hours select box
			for (i = 0; i <= 23; i++) {
				if(i != <%out.print(hour);%>){
					if(i < 10)
						$('#hour').append($('<option />').val('0'+i).html('0'+i));
					else
						$('#hour').append($('<option />').val(i).html(i));
				}
			}
			//populate our minutes select box
			for (i = 0; i <= 59; i++) {
				if(i != <%out.print(minute);%>){
					if(i < 10)
						$('#minute').append($('<option />').val('0'+i).html('0'+i));
					else
						$('#minute').append($('<option />').val(i).html(i));
				}
			}
			//populate our Days select box
			updateNumberOfDays();

			//"listen" for change events
			$('#years, #months').change(function() {
				$('#days').html('');
				updateNumberOfDays();
			});
			

		});

		//function to update the days based on the current values of month and year
		function updateNumberOfDays() {
			month = $('#months').val();
			year = $('#years').val();
			days = daysInMonth(month, year);

			for (i = 1; i <= days; i++)
					$('#days').append($('<option />').val(i).html(i));
		}
		

		//helper function
		function daysInMonth(month, year) {
			return new Date(year, month, 0).getDate();
		}
		
		
		$('#form').find('input').focus(function() {
			$('#success')
			.addClass('hidden');
	    });

	</script>
	
	<script type="text/javascript">
		$(function() {

			//populate our years select box
			for (i = new Date().getFullYear(); i <= new Date().getFullYear() + 1; i++) {
				if (i != <%out.print(startYear);%>)
					$('#startYear').append($('<option />').val(i).html(i));
			}
			//populate our months select box
			for (i = 1; i <= 12; i++) {
				if (i != <%out.print(startMonth);%>)
					$('#startMonth').append($('<option />').val(i).html(i));
			}
			
			
			
			//populate our Days select box
			updateNumberOfRecruitmentStartDays();

			//"listen" for change events
			$('#startYear, #startMonth').change(function() {
				$('#startDay').html('');
				updateNumberOfRecruitmentStartDays();
			});
			

		});

		//function to update the days based on the current values of month and year
		function updateNumberOfRecruitmentStartDays() {
			month = $('#startMonth').val();
			year = $('#startYear').val();
			days = daysInMonth(month, year);

			for (i = 1; i <= days; i++) 
				$('#startDay').append($('<option />').val(i).html(i));
		}
		

		//helper function
		function daysInMonth(month, year) {
			return new Date(year, month, 0).getDate();
		}

	</script>

	<script type="text/javascript">
		$(function() {

			//populate our years select box
			for (i = new Date().getFullYear(); i <= new Date().getFullYear() + 1; i++) {
				if (i != <%out.print(endYear);%>)
					$('#endYear').append($('<option />').val(i).html(i));
			}
			//populate our months select box
			for (i = 1; i <= 12; i++) {
				if (i != <%out.print(endMonth);%>)
					$('#endMonth').append($('<option />').val(i).html(i));
			}
			
			
			
			//populate our Days select box
			updateNumberOfRecruitmentEndDays();

			//"listen" for change events
			$('#endYear, #endMonth').change(function() {
				$('#endDay').html('');
				updateNumberOfRecruitmentEndDays();
			});
			

		});

		//function to update the days based on the current values of month and year
		function updateNumberOfRecruitmentEndDays() {
			month = $('#endMonth').val();
			year = $('#endYear').val();
			days = daysInMonth(month, year);

			for (i = 1; i <= days; i++)
				$('#endDay').append($('<option />').val(i).html(i));
			
		}
		

		//helper function
		function daysInMonth(month, year) {
			return new Date(year, month, 0).getDate();
		}

	</script>
	
	<script type="text/javascript">
	$('#form').on('submit',function() {
		$('#swap')
		.html('<li><i class="fa fa-refresh fa-2x faa-spin faa-fast animated"></i></li>');
	});
	</script>
</body>
</html>