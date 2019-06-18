<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.MySQL"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="description" content="" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
<link rel="stylesheet" href="/resources/css/table.css">

<title>イベント一覧</title>

</head>
<body>

	<div id="demo">
		<!-- Responsive table starts here -->
		<!-- For correct display on small screens you must add 'data-title' to each 'td' in your table -->
		<div class="table-responsive-vertical shadow-z-1">
			<!-- Table starts here -->
			<table class="table table-hover table-mc-red" id="table">
				<thead>
					<tr>
						<th></th>
						<th>開催日</th>
						<th>募集人数</th>
						<th>リンク</th>
					</tr>
				</thead>
				<tbody>
					<%
						MySQL db = new MySQL();
						String event;
						db.open();
						ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
						int i = 0, j = 0;
						list = db.allEventsInfo();
						for (ArrayList<String> row : list) {
							for (String string : row) {
								if (i % 4 == 0) {
									event = row.get(3 + j);
									out.println("<tr>");
									out.println("<td data-title=\"\"><span style=\"font-size: 1.5em; font-color: #333;\">" + string + "</span><span><img src=\"" + db.eventLogo(event) + "\" alt=\"\" width=\"20\" height=\"20\"></span></td>");
								} else if (i % 4 == 1) {
									out.println("<td data-title=\"開催日\">" + string + "</td>");
								} else if (i % 4 == 2) {
									out.println("<td data-title=\"募集人数\">" + string + "</td>");
								} else {
									out.println("<td data-title=\"リンク\"><a href=\"/events/" + string + "\">myouth.jp/events/"
											+ string + "</a></td>");
									out.println("</tr>");
									j += 4;
								}
								i++;
							}
						}
						db.close();
					%>
				</tbody>
			</table>
		</div>

		<!-- Table Constructor change table classes, you don't need it in your project 
		<div style="width: 100%; display: inline-block; vertical-align: top">
			<h2>Table Constructor</h2>
			<p>
				<label for="table-bordered">Style: bordered</label> <select
					name="table-bordered" id="table-bordered">
					<option value="" selected="">No</option>
					<option value="table-bordered">Yes</option>
				</select>
			</p>
			<p>
				<label for="table-striped">Style: striped</label> <select
					name="table-striped" id="table-striped">
					<option value="" selected="">No</option>
					<option value="table-striped">Yes</option>
				</select>
			</p>
			<p>
				<label for="table-hover">Style: hover</label> <select
					name="table-hover" id="table-hover">
					<option value="">No</option>
					<option value="table-hover" selected="">Yes</option>
				</select>
			</p>
			<p>
				<label for="table-color">Style: color</label> <select
					name="table-color" id="table-color">
					<option value="">Default</option>
					<option selected value="table-mc-red">Red</option>
					<option value="table-mc-pink">Pink</option>
					<option value="table-mc-purple">Purple</option>
					<option value="table-mc-deep-purple">Deep Purple</option>
					<option value="table-mc-indigo">Indigo</option>
					<option value="table-mc-blue">Blue</option>
					<option value="table-mc-light-blue">Light Blue</option>
					<option value="table-mc-cyan">Cyan</option>
					<option value="table-mc-teal">Teal</option>
					<option value="table-mc-green">Green</option>
					<option value="table-mc-light-green">Light Green</option>
					<option value="table-mc-lime">Lime</option>
					<option value="table-mc-yellow">Yellow</option>
					<option value="table-mc-amber">Amber</option>
					<option value="table-mc-orange">Orange</option>
					<option value="table-mc-deep-orange">Deep Orange</option>
				</select>
			</p>
		</div>
	</div>-->
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>

		<script src="/resources/js/table.js"></script>
</body>

</html>
