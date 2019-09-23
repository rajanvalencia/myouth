<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.Events"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String event = (String) session.getAttribute("event");
	Events db = new Events();
	db.open();
	String eventName = db.eventName(event);
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
<title>データダウンロード</title>
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
				<a href="/home"><span
					class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
			</section>
			<header>
				<h2>Download</h2>
			</header>
			<div class="row">
				<div class="col-12">

					<!-- Form -->
					<section class="box">
						<h3>
							<%
								out.print(eventName);
							%>ダウンロード
						</h3>
						<form id="form" method="post"
							action="/download">
							<div class="row gtr-uniform gtr-50">
								<div class="col-12">
									<label for="">データの種類を選択</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="radio" id="applicationFormData" name="dataType"
										value="applicationFormData" checked> <label
										for="applicationFormData">参加申し込み</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="radio" id="surveyData" name="dataType"
										value="surveyData"> <label for="surveyData">アンケート</label>
								</div>
								<div class="col-12">
									<label for="">参加者の申し込み期間を選択</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="radio" id="allPeriod" name="periodType"
										value="allPeriod" checked> <label for="allPeriod">全期間</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="radio" id="recruitmentPeriod" name="periodType"
										value="recruitmentPeriod"> <label
										for="recruitmentPeriod">募集期間内</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="radio" id="freePeriod" name="periodType"
										value="freePeriod"> <label for="freePeriod">期間指定</label>
								</div>
								<div class="col-4 col-4-mobilep hidden" id="startYearRadio">
									<label for="startYear">開始期間</label> <select name="startYear"
										id="startYear">
										<option value="" selected></option>
									</select>
								</div>
								<div class="col-4 col-4-mobilep hidden" id="startMonthRadio">
									<label for="startMonth">月</label> <select name="startMonth"
										id="startMonth">
										<option value="" selected></option>
									</select>
								</div>
								<div class="col-4 col-4-mobilep hidden" id="startDayRadio">
									<label for="startDay">日</label> <select name="startDay"
										id="startDay">
										<option value="" selected></option>
									</select>
								</div>
								<div class="col-4 col-4-mobilep hidden" id="endYearRadio">
									<label for="endYear">終了期間</label> <select name="endYear"
										id="endYear">
										<option value="" selected></option>
									</select>
								</div>
								<div class="col-4 col-4-mobilep hidden" id="endMonthRadio">
									<label for="endMonth">月</label> <select name="endMonth"
										id="endMonth">
										<option value="" selected></option>
									</select>
								</div>
								<div class="col-4 col-4-mobilep hidden" id="endDayRadio">
									<label for="endDay">日</label> <select name="endDay" id="endDay">
										<option value="" selected></option>
									</select>
								</div>
								<div class="col-12">
									<label for="">形式を選択</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="radio" id="csv" name="fileType" value="csv"
										checked> <label for="csv">csv</label>
								</div>
								<div class="col-4 col-12-narrower">
									<input type="radio" id="pdf" name="fileType" value="pdf">
									<label for="pdf">pdf</label>
								</div>
								<div class="col-4 col-12-narrower">
								</div>
								<div class="col-12 hidden" id="fontLabel">
								<label for="">フォントサイズを選択</label>
								</div>
								<div class="col-4 col-12-narrower hidden" id="fontSize8">
									<input type="radio" id="8" name="fontSize" value=8> <label
										for="8">8</label>
								</div>
								<div class="col-4 col-12-narrower hidden" id="fontSize10">
									<input type="radio" id="10" name="fontSize" value=10> <label
										for="10">10</label>
								</div>
								<div class="col-4 col-12-narrower hidden" id="fontSize12">
									<input type="radio" id="12" name="fontSize" value=12> <label
										for="12">12</label>
								</div>
								<div class="col-12">
									<ul class="actions" id="swap">
										<li><input id="btn" type="submit" value="ダウンロード" /></li>
									</ul>
								</div>
							</div>
						</form>

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
	$('#form').on('submit', function (){
		$('#swap')
	    .html('<li><i class="fa fa-refresh fa-2x faa-spin faa-fast animated"></i></li>')
		});
	
	$("#subject").on('focus', function() {
		$('#success')
		.addClass('hidden');
		});
	
	$("#message").on('focus', function() {
		$('#success')
		.addClass('hidden');
		});
	
	$("#message").on('keyup', function() {
		$('#btn')
		.removeAttr('disabled');
		});
	
	$("input[name='periodType']").change(function() {
		if($("input[name='periodType']:checked").val() == "freePeriod") {
			$("#startYearRadio, #startMonthRadio, #startDayRadio, #endYearRadio, #endMonthRadio, #endDayRadio")
			.removeClass("hidden");
			
			$("#startYear, #startMonth, #startDay, #endYear, #endMonth, #endDay")
			.prop("required", true);
		}
		else {
			$("#startYearRadio, #startMonthRadio, #startDayRadio, #endYearRadio, #endMonthRadio, #endDayRadio")
			.addClass("hidden");
			$("#startYear, #startMonth, #startDay, #endYear, #endMonth, #endDay")
			.removeAttr("required");
		}
	});
		
	$("input[name='fileType']").change(function() {
		if($("input[name='fileType']:checked").val() == "pdf") {
			$("#fontLabel, #fontSize8, #fontSize10, #fontSize12")
			.removeClass("hidden")
			.prop("required", true);
				
		} else {
			$("#fontLabel, #fontSize8, #fontSize10, #fontSize12")
			.addClass("hidden")
			.removeAttr("required");
		}
	});
	</script>
	
	<script type="text/javascript">
		$(function() {

			//populate our years select box
			for (i = 2019; i <= new Date().getFullYear() + 1; i++) {
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
</body>
</html>