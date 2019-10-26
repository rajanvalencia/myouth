<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	String success = (String) session.getAttribute("registerEventSuccess");
	String failure = (String) session.getAttribute("registerEventFailure");
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
<title>イベント作成</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
	<body class="is-preload">
		<div id="page-wrapper">
			<!-- Main -->
				<section id="main" class="container">
				<section  class="back-button">
					<a href="/home"><span class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
				</section>
				<section style="background-color: #F4BAA7;" class="box <%out.print(failure); session.setAttribute("registerUserFailure", "hidden");%>" id="success">
					<p>イベントIDは使用できません<i class="fa fa-times faa-pulse animated"></i></p>
				</section>
					<div class="box">
					<div class="row">
						<form id="form" method="post" action="/registerEvent">
							<div class="row gtr-50 gtr-uniform">
								<div class="col-12">
									<label for="eventName">イベント名</label>
									<input type="text" name="eventName" id="eventName" value="" placeholder="" required/>
								</div>
								<div class="col-12">
									<label for="eventId">イベントID(アルファベット・数字の組み合わせのみ)</label>
									<input type="text" name="eventId" id="eventId" pattern="[a-zA-Z0-9]+" value="" placeholder="" required/>
								</div>
								<div class="col-12">
									<label for="eventPlace">開催場所</label>
									<input type="text" name="eventPlace" id="eventPlace" value="" required/>
								</div>
								<div class="col-2 col-4-mobilep">
									<label for="years">開催日 年</label>
									<select name="year" id="years" required>
										<option label="" selected></option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
									<label for="months">月</label>
									<select name="month" id="months" required>
										<option label="" selected></option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
									<label for="days">日</label>
									<select name="day" id="days" required>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="hour">開始時間</label>
									<select name="hour" id="hour">
										<option value="" selected>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="minute">分</label>
									<select name="minute" id="minute">
										<option value="" selected>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="recruitNo">募集人数</label>
									<input type="number" name="recruitNo" id="recruitNo" value="" />
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="startYear">募集期間</label>
									<select name="startYear" id="startYear">
										<option value="" selected>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="startMonth">月</label>
									<select name="startMonth" id="startMonth">
										<option value="" selected>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="startDay">日</label>
									<select name="startDay" id="startDay">
										<option value="" selected>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="endYear">募集〆切</label>
									<select name="endYear" id="endYear">
										<option value="" selected>
										</option>
									</select>
								</div>

								<div class="col-2 col-4-mobilep">
								<label for="endMonth">月</label>
									<select name="endMonth" id="endMonth">
										<option value="" selected>
										</option>
									</select>
								</div>
								<div class="col-2 col-4-mobilep">
								<label for="endDay">日</label>
									<select name="endDay" id="endDay">
										<option value="" selected>
										</option>
									</select>
								</div>
								<div class="col-4 col-12-mobile">
									<ul class="actions" id="swap">
										<li><input id="btn" type="submit" value="登録 "/></li>
									</ul>
								</div>
							</div>
						</form>
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
						<li>myouth.jp</li><li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
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
		<!--<script src="/resources/alpha/js/main.js"></script>-->
		<script type="text/javascript">
		$(function() {

			//populate our years select box
			for (i = new Date().getFullYear(); i <= new Date().getFullYear() + 1; i++) {
				$('#years').append($('<option />').val(i).html(i));
			}
			//populate our months select box
			for (i = 1; i <= 12; i++) {
				$('#months').append($('<option />').val(i).html(i));
			}
			
			//populate our hours select box
			for (i = 0; i <= 23; i++) {
				if(i < 10)
					$('#hour').append($('<option />').val('0'+i).html('0'+i));
				else
					$('#hour').append($('<option />').val(i).html(i));
			}
			//populate our minutes select box
			for (i = 0; i <= 59; i++) {
				if(i < 10)
					$('#minute').append($('<option />').val('0'+i).html('0'+i));
				else
					$('#minute').append($('<option />').val(i).html(i));
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
				$('#startYear').append($('<option />').val(i).html(i));
			}
			//populate our months select box
			for (i = 1; i <= 12; i++) {
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
				$('#endYear').append($('<option />').val(i).html(i));
			}
			//populate our months select box
			for (i = 1; i <= 12; i++) {
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