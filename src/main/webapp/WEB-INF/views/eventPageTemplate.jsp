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
	String instagramUrl = db.instagramUrl(event);
	String facebookUrl = db.facebookUrl(event);
	String twitterUrl = db.twitterUrl(event);
	String eventEmail = db.eventEmail(event);
	String eventLocation = db.eventLocation(event);
	String eventPlace = db.eventPlace(event);
	ArrayList<String> eventDate = db.eventDate(event);
	ArrayList<String> eventTime = db.eventTime(event);
	ArrayList<String> recruitmentStartDate = db.recruitmentStartDate(event);
	ArrayList<String> recruitmentEndDate = db.recruitmentEndDate(event);
	Integer recruitNo = db.totalParticipants(event);
	Integer recruitLimit = db.recruitmentLimit(event);
	
	ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
	list = db.eventInfo(event);
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
<title>
	 <%out.print(eventName);%>
</title>
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
				<h1 id="title">
					<%out.print(eventName);%>
				</h1>
			</div>

			<!-- Nav -->
			<nav id="nav">
				<ul>
					<li><a href="#join" id=""><span class="icon fa-user-plus">参加申し込み</span></a></li>
					<li><a href="#survey" id=""><span
							class="icon fa-pencil-square">アンケート</span></a></li>
					<li><a href="#analysis" id=""><span
							class="icon fa-bar-chart">分析</span></a></li>
					<li><a href="#reviews" id=""><span
							class="icon fa-pie-chart">評価</span></a></li>
					<li><a href="#top" id=""><span class="icon fa-refresh">Top</span></a></li>
				</ul>
			</nav>

		</div>

		<div class="bottom">

			<!-- Social Icons -->
			<ul class="icons">
				<li><a href="/" class="icon fa-home"><span class="label">Home</span></a></li>
				<%
					if (instagramUrl.length() > 0)
						out.println("<li><a href=\"" + instagramUrl
								+ "\" class=\"icon fa-instagram\"><span class=\"label\">Instagram</span></a></li>");
					if (facebookUrl.length() > 0)
						out.println("<li><a href=\"" + facebookUrl
								+ "\" class=\"icon fa-facebook\"><span class=\"label\">Facebook</span></a></li>");
					if (twitterUrl.length() > 0)
						out.println("<li><a href=\"" + twitterUrl
								+ "\" class=\"icon fa-twitter\"><span class=\"label\">Twitter</span></a></li>");

					if (eventEmail.length() > 0)
						out.println("<li><a href=\"mailto:" + eventEmail
								+ "?subject=MYouthサイトから\" class=\"icon fa-envelope\"><span class=\"label\">お問い合わせ</span></a></li>");
				%>
			</ul>

		</div>

	</div>

	<!-- Main -->
	<div id="main">

		<!-- Intro -->
		<section id="top" class="one dark cover">
			<div class="container">

				<header>
				</header>

			</div>

		</section>

		<section id="" class="two">
			<div class="container">
				<h2><%out.print(eventName);%></h2>
				<br />
				
				<p>
				<span class="icon fa-map-marker"></span>
				<a target="blank" href="https://www.google.com/maps/dir/?api=1&destination=<% out.print(eventLocation);%>">
				<%out.print(eventPlace);%>
				</a>
				</p>
				
				<p>
				<span class="icon fa-calendar-check"></span>
				<% 
				i = 0;
				for(String string : eventDate){
					if(i == 2)
						out.print(string);
					else 
						out.print(string+"-");
					i++;
				}
				%>
				</p>
				
				<p>
				<span class="icon fa-clock"></span>
				<% 
				i = 0;
				for(String string : eventTime){
					if(i == 1){
						if(Integer.valueOf(string) < 10)
							out.print("0"+string);
						else
							out.print(string);
					}
					else 
						out.print(string+":");
					i++;
				}
				%>
				</p>
				
				<p>
				<span class="icon fa-user-plus"></span>
				<%out.print(recruitLimit-recruitNo);%>
				</p>
				
				<p>
				<span class="icon fa-calendar-alt"></span>
				<% 
				i = 0;
				for(String string : recruitmentStartDate){
					if(i == 2)
						out.print(string+" ~ ");
					else 
						out.print(string+"-");
					i++;
				}
				%>
				<% 
				i = 0;
				for(String string : recruitmentEndDate){
					if(i == 2)
						out.print(string);
					else 
						out.print(string+"-");
					i++;
				}
				%>
				</p>
				<p>
					<%
						out.print(db.eventDescription(event));
					%>
				</p>

				<h3>管理者</h3>
				<div class="row">
					<%
						EventPageMemberLists memberLists = new EventPageMemberLists();
						memberLists.append(request);
						out.print(request.getAttribute("memberLists"));
						request.removeAttribute("memberLists");
					%>
				</div>
				
				<br />
				<h3>写真</h3>
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

		<!-- Join -->
		<section id="join" class="three">
			<div class="container">

				<header>
					<h2>参加申し込み</h2>
				</header>

				<p>参加申し込み後、<% out.print(eventName); %>から
				<br />のメールが送信されますので
				<br />ご確認ください。
				<br /> 参加申し込みは<a href="<%out.print(event);%>/form/">こちら</a>です。</p>

			</div>
		</section>

		<!-- Survey -->
		<section id="survey" class="four">
			<div class="container">

				<header>
					<h2>アンケート</h2>
				</header>

				<p><%out.print(eventName);%>に
				<br />参加して頂きありがとうございます。
				<br /> 最後にアンケートにご協力ください。 
				<br />アンケートは<a href="/events/<%out.print(event);%>/survey">こちら</a>です。</p>

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