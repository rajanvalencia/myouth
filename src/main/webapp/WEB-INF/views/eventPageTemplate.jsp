<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.Events"%>
<%@ page import="jp.myouth.tables.*"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<%
	Events db = new Events();
	db.open();
	String event = (String) session.getAttribute("event");
	String eventName = db.eventName(event);
	int i, j;
%>
<!--
	Prologue by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html lang="en">
<head>
<meta charset="UTF-8" />
<title><%out.print(eventName);%></title>
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
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="/resources/prologue/css/main.css" />
<link rel="stylesheet" href="/resources/css/starRatings.css" />
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">

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

	<!-- Header -->
	<div id="header">

		<div class="top">

			<!-- Logo -->
			<div id="logo">
				<span class="image avatar48"><img
					src="<%out.print(db.eventLogo(event));%>" alt="" /></span>
				<h1 id="title"><%out.print(eventName);%></h1>
			</div>

			<!-- Nav -->
			<nav id="nav">
				<ul>
					<li>
						<a href="#introduction" id=""><span class="icon fa-arrow-up">Top</span></a>
					</li>
					<li>
						<a href="#administrator" id=""><span class="icon fa-user-circle">管理者</span></a>
					</li>
					<li>
						<a href="#media" id=""><span class="icon fa-picture-o">メディア</span></a>
					</li>
					<li>
						<a href="#analysis" id=""><span class="icon fa-bar-chart">分析</span></a>
					</li>
					<li>
						<a href="#reviews" id=""><span class="icon fa-comment">評価</span></a>
					</li>
				</ul>
			</nav>
		</div>
		<% 
			SocialLinks socialLinks = new SocialLinks();
			socialLinks.append(request);
			out.print(request.getAttribute("socialLinks"));
			request.removeAttribute("socialLinks");
		%>
	</div>

	<!-- Main -->
	<div id="main">

		<section id="introduction" class="two">
			<div class="container">
				<% 
					EventPageEventInfo eventInfo = new EventPageEventInfo();
					eventInfo.append(request);
					out.print(request.getAttribute("eventPageEventInfo"));
					request.removeAttribute("eventPageEventInfo");
				%>
			</div>
		</section>

		<!-- Administrator -->
		<section id="administrator" class="three">
			<h3>管理者</h3>
			<div class="container">
				<div class="row">
				<%
					EventPageMemberLists memberLists = new EventPageMemberLists();
					memberLists.append(request);
					out.print(request.getAttribute("memberLists"));
					request.removeAttribute("memberLists");
				%>
				</div>
			</div>
		</section>

		<!-- Media -->
		<section id="media" class="four">
			<h3>メディア</h3>
			<div class="container">
				<div class="row">
				<%
					EventPageImageLists imageLists = new EventPageImageLists();
					imageLists.append(request);
					out.print(request.getAttribute("imageLists"));
					request.removeAttribute("imageLists");
				%>
				</div>
			</div>
		</section>

		<!-- Analysis -->
		<section id="analysis" class="three">
			<div class="container">

				<header>
					<h2>分析</h2>
				</header>

				<p></p>
				<div id="regions_div" class="chart"></div>
				<div id="piechart_career" class="piechart"></div>
				<div id="piechart_way" class="piechart"></div>
				
				<% 
					EventPageCompanyTable companyTable = new EventPageCompanyTable();
					companyTable.append(request);
					out.print(request.getAttribute("companyTable"));
					request.removeAttribute("companyTable");
				%>
			</div>
		</section>

		<!-- Reviews -->
		<% 
			Ratings ratings = new Ratings();
			ratings.append(request);
			out.println(request.getAttribute("eventRatings"));
			request.removeAttribute("eventRatings");
		%>

	</div>

	<!-- Footer -->
	<div id="footer">

		<!-- Copyright -->
		<ul class="copyright">
			<li>Design: <a href="http://html5up.net">HTML5 UP</a>.</li>
		</ul>

	</div>

	<!-- Scripts -->
	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
	<script src="/resources/prologue/js/jquery.min.js"></script>
	<script src="/resources/prologue/js/jquery.scrolly.min.js"></script>
	<script src="/resources/prologue/js/jquery.scrollex.min.js"></script>
	<script src="/resources/prologue/js/browser.min.js"></script>
	<script src="/resources/prologue/js/breakpoints.min.js"></script>
	<script src="/resources/prologue/js/util.js"></script>
	<script src="/resources/prologue/js/main.js"></script>
	<script type="text/javascript"
		src="https://www.gstatic.com/charts/loader.js"></script>


	<script type="text/javascript">
		google.charts.load('current', {
			'packages' : [ 'geochart' ],
			'mapsApiKey' : 'AIzaSyD-9tSrke72PouQMnMX-a7eZSW0jkFMBWY'
		});
		google.charts.setOnLoadCallback(drawMap);
		function drawMap() {
			var data = google.visualization
					.arrayToDataTable([
	<%ArrayList<ArrayList<String>> country_data = new ArrayList<ArrayList<String>>();

			country_data = db.geoMap(event);
			i = 0;
			out.print("[\"ルーツをもつ国\",\"参加人数\"],");
			for (ArrayList<String> row : country_data) {
				for (String string : row) {
					if (i == 0)
						out.print("[\"" + string + "\"");
					else if (i % 2 == 0 && i > 0)
						out.print(",[\"" + string + "\"");
					else
						out.print(", " + string + "]");
					i++;
				}
			}%>
		]);
			var options = {
				title : 'ルーツをもつ国別参加者'
			};
			options['backgroundColor'] = '#97BAEC';
			options['colors'] = [ '#98FB98', '#006400' ];
			var chart = new google.visualization.GeoChart(document
					.getElementById('regions_div'));
			chart.draw(data, options);
		}
	</script>

	<script type="text/javascript">
		google.charts.load('current', {
			'packages' : [ 'corechart' ]
		});
		google.charts.setOnLoadCallback(drawChart);

		function drawChart() {

			var data = google.visualization
					.arrayToDataTable([
	<%ArrayList<ArrayList<String>> career_data = new ArrayList<ArrayList<String>>();

			career_data = db.careerPieChart(event);
			i = 0;
			out.print("[\"職種\",\"割合\"],");
			for (ArrayList<String> row : career_data) {
				for (String string : row) {
					if (i == 0)
						out.print("[\"" + string + "\"");
					else if (i % 2 == 0 && i > 0)
						out.print(",[\"" + string + "\"");
					else
						out.print(", " + string + "]");
					i++;
				}
			}
			%>
		]);

			var options = {
				title : '参加者職種別'
			};
			options['backgroundColor'] = '#f5f5f5';
			var chart = new google.visualization.PieChart(document
					.getElementById('piechart_career'));

			chart.draw(data, options);
		}
	</script>
	<script type="text/javascript">
		google.charts.load('current', {
			'packages' : [ 'corechart' ]
		});
		google.charts.setOnLoadCallback(drawChart);

		function drawChart() {

			var data = google.visualization
					.arrayToDataTable([
	<%ArrayList<ArrayList<String>> way_data = new ArrayList<ArrayList<String>>();

			way_data = db.wayPieChart(event);
			i = 0;
			out.print("[\"きっかけ\",\"割合\"],");
			for (ArrayList<String> row : way_data) {
				for (String string : row) {
					if (i == 0)
						out.print("[\"" + string + "\"");
					else if (i % 2 == 0 && i > 0)
						out.print(",[\"" + string + "\"");
					else
						out.print(", " + string + "]");
					i++;
				}
			}
			db.close();%>
		]);

			var options = {
				title : '参加のきっかけ'
			};
			options['backgroundColor'] = '#f5f5f5';
			var chart = new google.visualization.PieChart(document
					.getElementById('piechart_way'));

			chart.draw(data, options);
		}
	</script>
</body>
</html>