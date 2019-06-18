<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.Participants"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	String userId = (String) session.getAttribute("userId");
%>
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

<title>参加者一覧</title>

</head>
<body>

	<div id="demo">
		<!-- Responsive table starts here -->
		<!-- For correct display on small screens you must add 'data-title' to each 'td' in your table -->
		<div class="table-responsive-vertical shadow-z-1">
			<!-- Table starts here -->
			<table class="table table-bordered table-hover table-mc-deep-orange" id="table">
				<tbody>
				<thead>
					<%
						Participants db = new Participants();
						String event = (String) session.getAttribute("event");
						db.open();
						ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
						int i = 0, j = 0;
						list = db.participantsInfo(event);
						out.println("<tr>");
						out.println("<th>氏名</th>");
						out.println("<th>メールアドレス</th>");
						out.println("<th>電話番号</th>");
						out.println("<th>ルーツをもつ国</th>");
						out.println("<th>学校名または会社名</th>");
						out.println("</tr>");
						out.println("</thead>");
						i = 0;
						for (ArrayList<String> row : list) {
							out.println("<tr>");
							for (String string : row) {
								if (i % 5 == 0)
									out.println("<td data-title=\"フリガナ\">" + string + "</td>");
								else if (i % 5 == 1)
									out.println("<td data-title=\"メールアドレス\"><a href=\"mailto:" + string + "?subject=myouth\">"
											+ string + "</a></td>");
								else if (i % 5 == 2) {
									if (string == null || string.length() == 0){
										string = "なし";
										out.println("<td data-title=\"電話番号\">" + string + "</a></td>");
									}
									else
										out.println("<td data-title=\"電話番号\"><a href=\"tel:"+string+"\">" + string + "</a></td>");
								} else if (i % 5 == 3)
									out.println("<td data-title=\"ルーツをもつ国\">" + string + "</td>");
								else if (i % 5 == 4) {
									out.println("<td data-title=\"学校名または会社名\">" + string + "</td>");
									out.println("</tr>");
								}
								i++;
							}
							break;
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
