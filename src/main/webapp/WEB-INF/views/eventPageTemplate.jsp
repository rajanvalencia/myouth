<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.MySQL"%>
<%@ page import="java.util.ArrayList"%>
<%@ page session="true"%>
<!DOCTYPE html>
<%
	MySQL db = new MySQL();
	db.open();
	String event = (String) session.getAttribute("event");
	String eventName = db.eventName(event);
	String instagramUrl = db.instagramUrl(event);
	String facebookUrl = db.facebookUrl(event);
	String twitterUrl = db.twitterUrl(event);
	String eventEmail = db.eventEmail(event);
	String eventLocation = db.eventLocation(event);
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
	<%
		out.print(eventName);
	%>ホーム
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
<link rel="stylesheet" href="/resources/assets/css/main.css" />
<link rel="stylesheet" href="/resources/css/starRatings.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script
	src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>


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
					<%
						out.print(eventName);
					%>
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

					if (eventLocation.length() > 0)
						out.println("<li><a href=\"https://www.google.com/maps/dir/?api=1&destination=" + eventLocation
								+ "\" class=\"icon fa-map-marker\"><span class=\"label\">アクセスマップ</span></a></li>");
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

					<h2>
						<strong> <%
 	out.print(db.eventName(event));
 %></strong>
					</h2>
					<br>

				</header>

			</div>

		</section>

		<section id="" class="two">
			<div class="container">

				<%
					i = 0;

					for (ArrayList<String> row : list) {
						for (String string : row) {
							switch (i) {
								case 1 :
									out.print("<h2>開催場所</h2>");
									break;
								case 2 :
									out.print("<h2>開催日</h2>");
									break;
								case 3 :
									out.print("<h2>集合時間</h2>");
									break;
								case 4 :
									out.print("<h2>募集人数</h2>");
									break;
								case 5 :
									out.print("<h2>残り応募人数</h2>");
									break;
							}
							if (i != 0) {
								if (string == null)
									out.print("<h2>-</h2><br>");
								else
									out.print("<h2>" + string + "</h2><br>");
							}
							i++;
						}
					}
				%>

				<h2></h2>
				<br>
				<p>
					<%
						out.print(db.eventDescription(event));
					%>
				</p>


				<div class="row">
					<%
						list = null;
						list = db.getImage(event);
						i = 0;
						int len1 = 0, len2 = 0, len3 = 0;
						for (ArrayList<String> row : list) {
							len1 = row.size() / 3;
							len2 = (row.size() / 3) * 2;
							len3 = row.size();
							for (String string : row) {
								if (i == 0 || i == len1 || i == len2)
									out.println("<div class=\"col-4 col-12-mobile\">");

								if (i % 2 == 0) {
									out.println("<article class=\"item\">");
									out.println("<a href=\"#\" class=\"image fit\">");
									out.println("<img src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/events/"
											+ event + "/" + string + "\" alt=\"\" />");
									out.println("</a>");
								}

								else if (i % 2 == 1 && string != null) {
									out.println("<header><h3>" + string + "</h3></header>");
									out.println("</article>");
								}

								else {
									out.println("</article>");
								}

								if (i == len1 - 1 || i == len2 - 1 || i == len3 - 1)
									out.println("</div>");

								i++;
							}
						}
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

				<p>
					参加申し込み後、<%
					out.print(eventName);
				%>から<br />のメールが送信されますので<br />ご確認ください。<br /> 参加申し込みは<a
						href="<%out.print(event);%>/form/">こちら</a>です。
				</p>

			</div>
		</section>

		<!-- Survey -->
		<section id="survey" class="four">
			<div class="container">

				<header>
					<h2>アンケート</h2>
				</header>

				<p>
					<%
						out.print(eventName);
					%>に<br />参加して頂きありがとうございます。<br /> 最後にアンケートにご協力ください。 <br />アンケートは<a
						href="/events/<%out.print(event);%>/survey">こちら</a>です。
				</p>

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
				<table>
					<thead>
						<tr>
							<%
								list = null;
								list = db.companyTable(event);
								i = 0;
								if(list.get(0).size() != 0)
									out.println("<th>学校名または会社名</th><th>参加人数</th>");
							%>
						</tr>
					</thead>
					<tbody>
						<%
							for (ArrayList<String> row : list) {
								for (String string : row) {
									if (i % 2 == 0) {
										out.println("<tr>");
										out.println("<td>" + string + "</td>");
									} else {
										out.println("<td>" + string + "</td>");
										out.println("</tr>");
									}
									i++;
								}
							}
						%>
					</tbody>
				</table>
			</div>
		</section>



		<!-- Reviews -->
		<section id="reviews" class="four">
			<div class="container">

				<header>
					<h2>評価</h2>
				</header>
				<%
					ArrayList<Double> ratingData = db.ratingData(event);
					int totalRatings;
					double averageRatings;

					totalRatings = ratingData.get(0).intValue();
					averageRatings = ratingData.get(1);
					for (i = 1; i <= 5; i++) {
						if (i <= averageRatings)
							out.println("<span class=\"fa fa-star checked\"></span>");
						else {
							if ((double) i - 0.5 <= averageRatings) {
								out.println("<span class=\"fa fa-star-half-o checked\"></span>");
							} else
								out.println("<span class=\"fa fa-star-o\"></span>");
						}
					}
					out.println("<p>" + totalRatings + " 件中　平均満足度は " + averageRatings + "<p>");
					out.println("<div class=\"row\">");
					i = 0;
					j = 0;
					ratingData = null;
					ratingData = db.totalPerSatisfaction(event);
					double total = totalRatings;
					for (Double data : ratingData) {
						if (i % 2 == 0) {
							out.println("<div class=\"side\"><div>");
							out.println("<span style=\"margin-right: 18px;\">" + data.intValue()
									+ "</span><span class=\"fa fa-star checked\"></span>");
							out.println("</div></div>");
							out.print("<div class=\"middle\"><div class=\"bar-container\"><div class=\"bar-" + data.intValue()
									+ "\"");
						} else {
							out.print("style=\"width:" + (data / total) * 100 + "%;\"></div></div></div>");
							out.println("<div class=\"side right\"><div>" + data.intValue() + "</div></div>");
						}
						i++;
					}
					out.println("</div>");
				%>
				<br /> <br />
				<h3>感想</h3>
				<%
					list = null;
					i = 0;
					list = db.commentsTable(event);
					if (list.get(0).size() == 0)
						out.println("データはありません<br />");
				%>
				<table>
					<tbody>
						<%
							for (ArrayList<String> row : list) {
								for (String string : row) {
									if (i % 3 == 0) {
										out.println("<tr>");
										out.println("<td><span>");
										int stars = Integer.parseInt(string);
										for (j = 0; j < stars; j++)
											out.println("<span class=\"fa fa-star checked\"></span>");

										for (; j < 5; j++)
											out.println("<span class=\"fa fa-star-o\"></span>");

										out.println("</span>");
									} else if (i % 3 == 1) {
										out.println(string + "</td>");
										out.println("</tr>");
									} else {
										out.println("<tr>");
										out.println("<td>" + string + "</td>");
										out.println("</tr>");
									}
									i++;
								}
							}
						%>
					</tbody>
				</table>
				<h3>改善案</h3>
				<%
					list = null;
					i = 0;
					list = db.improvementPlansTable(event);
					if (list.get(0).size() == 0)
						out.println("データはありません<br />");
				%>
				<table>
					<tbody>
						<%
							for (ArrayList<String> row : list) {
								for (String string : row) {
									if (i % 2 == 0) {
										out.println("<tr>");
										out.println("<td>" + string + "</td>");
										out.println("</tr>");
									} else {
										out.println("<tr>");
										out.println("<td>" + string + "</td>");
										out.println("</tr>");
									}
									i++;
								}
							}
						%>
					</tbody>
				</table>
			</div>
		</section>

	</div>

	<!-- Footer -->
	<div id="footer">

		<!-- Copyright -->
		<ul class="copyright">
			<li>Design: <a href="http://html5up.net">HTML5 UP</a>.Photo by
				NASA on <a href="https://unsplash.com">Unsplash</a>.
			</li>
		</ul>

	</div>

	<!-- Scripts -->
	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
	<script src="/resources/assets/js/jquery.min.js"></script>
	<script src="/resources/assets/js/jquery.scrolly.min.js"></script>
	<script src="/resources/assets/js/jquery.scrollex.min.js"></script>
	<script src="/resources/assets/js/browser.min.js"></script>
	<script src="/resources/assets/js/breakpoints.min.js"></script>
	<script src="/resources/assets/js/util.js"></script>
	<script src="/resources/assets/js/main.js"></script>
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
			options['backgroundColor'] = '#ecf1f1';
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
			}%>
		]);

			var options = {
				title : '参加者職種別'
			};
			options['backgroundColor'] = '#ecf1f1';
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
			}%>
		]);

			var options = {
				title : '参加のきっかけ'
			};
			options['backgroundColor'] = '#ecf1f1';
			var chart = new google.visualization.PieChart(document
					.getElementById('piechart_way'));

			chart.draw(data, options);
		}
	</script>
</body>
</html>