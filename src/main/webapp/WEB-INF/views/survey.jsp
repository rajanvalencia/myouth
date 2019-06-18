<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.MySQL"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="en">
<%
	String event = (String) session.getAttribute("event");
	MySQL db = new MySQL();
	db.open();
	String eventName = db.eventName(event);
	String formtype = db.eventForm(event);
	ArrayList<Boolean> surveyQuestion = db.surveyQuestion(event);
	db.close();
%>
<head>
<meta charset="UTF-8">
<title>
	<%
		out.print(eventName);
	%>アンケート
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
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel='stylesheet'
	href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css'>

<link rel="stylesheet" href="/resources/css/form.css">

<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">
</head>

<body>

	<div class="container">
		<form action="/events/<%out.print(event);%>/survey/insertSurveyData"
			method="post" onsubmit="animate()">
			<legend>
				<%
					out.print(eventName);
				%>アンケート
			</legend>

			<%
				if (surveyQuestion.get(0)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"name-field\" class=\"col-md-4 control-label\">名前</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-user\"></i>");
					out.println("</span>");
					out.println(
							"<input type=\"text\" class=\"form-control\" id=\"name-field\" name=\"name-field\" placeholder=\"\" required>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}
				if (surveyQuestion.get(1)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"fname-field\" class=\"col-md-4 control-label\">フリガナ</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-user\"></i>");
					out.println("</span>");
					out.println(
							"<input type=\"text\" class=\"form-control\" id=\"fname-field\" name=\"fname-field\" placeholder=\"\" required>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (surveyQuestion.get(2)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"fname-field\" class=\"col-md-4 control-label\">");
					out.println(eventName + "イベントの満足度");
					out.println("</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"form-check\">");
					out.println(
							"<input class=\"form-check-input\" type=\"radio\" name=\"satisfaction\"id=\"exampleRadios1\" value=\"5\" checked>");
					out.println("<label class=\"form-check-label\" for=\"exampleRadios1\"> 非常に満足した </label>");
					out.println("</div>");
					out.println("<div class=\"form-check\">");
					out.println(
							"<input class=\"form-check-input\" type=\"radio\" name=\"satisfaction\" id=\"exampleRadios2\" value=\"4\">");
					out.println("<label class=\"form-check-label\" for=\"exampleRadios2\"> 満足した </label>");
					out.println("</div>");
					out.println("<div class=\"form-check\">");
					out.println(
							"<input class=\"form-check-input\" type=\"radio\" name=\"satisfaction\" id=\"exampleRadios3\" value=\"3\">");
					out.println("<label class=\"form-check-label\" for=\"exampleRadios3\"> 普通 </label>");
					out.println("</div>");
					out.println("<div class=\"form-check\">");
					out.println(
							"<input class=\"form-check-input\" type=\"radio\" name=\"satisfaction\" id=\"exampleRadios3\" value=\"2\">");
					out.println("<label class=\"form-check-label\" for=\"exampleRadios3\"> 満足しなかった </label>");
					out.println("</div>");
					out.println("<div class=\"form-check\">");
					out.println(
							"<input class=\"form-check-input\" type=\"radio\" name=\"satisfaction\" id=\"exampleRadios3\" value=\"1\">");
					out.println("<label class=\"form-check-label\" for=\"exampleRadios3\"> 最悪 </label>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (surveyQuestion.get(3)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"text-field\" class=\"col-md-4 control-label\">どうしてそう感じたのかをご記入ください</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"form-group\">");
					out.println("<label for=\"exampleFormControlTextarea1\"></label>");
					out.println(
							"<textarea class=\"form-control\" name=\"details\" id=\"details\" rows=\"3\" required></textarea>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");

				}

				if (surveyQuestion.get(4)) {
					out.println("<div id=\"grade-select\" class=\"form-group col-xs-12\">");
					out.println("<label for=\"grade-field\" class=\"col-md-4 control-label\">会場までの交通手段（複数選択可）</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"form-check\">");
					out.println(
							"<input class=\"form-check-input\" type=\"checkbox\" name=\"train\" value=\"電車\" id=\"train\" checked=\"\">");
					out.println("<label class=\"form-check-label\" for=\"train\"> 電車</label>");
					out.println("</div>");
					out.println("<div class=\"form-check\">");
					out.println(
							"<input class=\"form-check-input\" type=\"checkbox\" name=\"bus\" value=\"バス\" id=\"bus\"> <label class=\"form-check-label\" for=\"bus\"> バス </label>");
					out.println("</div>");
					out.println("<div class=\"form-check\">");
					out.println(
							"<input class=\"form-check-input\" type=\"checkbox\" name=\"four_wheel_vehicle\" value=\"四輪自動車\" id=\"four_wheel_vehicle\">");
					out.println("<label class=\"form-check-label\" for=\"four_wheel_vehicle\"> 四輪自動車 </label>");
					out.println("</div>");
					out.println("<div class=\"form-check\">");
					out.println(
							"<input class=\"form-check-input\" type=\"checkbox\" name=\"two_wheel_vehicle\" value=\"二輪自動車\" id=\"two_wheel_vehicle\">");
					out.println("<label class=\"form-check-label\" for=\"two_wheel_vehicle\"> 二輪自動車 </label>");
					out.println("</div>");
					out.println("<div class=\"form-check\">");
					out.println(
							"<input class=\"form-check-input\" type=\"checkbox\" name=\"other_transportation\" value=\"その他\" id=\"other_transportation\">");
					out.println("<label class=\"form-check-label\" for=\"other_transportation\"> その他 </label>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (surveyQuestion.get(5)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"text-field\" class=\"col-md-4 control-label\">" + eventName
							+ "イベントを改善できるところがあれば述べてください(自由回答)</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"form-group\">");
					out.println("<label for=\"exampleFormControlTextarea1\"></label>");
					out.println(
							"<textarea class=\"form-control\" name=\"improvement\" id=\"improvement\" rows=\"3\"></textarea>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}
			%>
			<div id="alert-field" class="alert hidden">
				<p>Uh oh! Something went wrong!</p>
			</div>

			<div class="form-group col-xs-12">
				<div class="col-md-4 inputGroupContainer">
					<div class="input-group">
						<button id="btn" class="btn btn-success btn-lg btn-block">
							送信 <span class="glyphicon glyphicon-send"></span>
						</button>
					</div>
				</div>
			</div>

		</form>
	</div>

	<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>
	<script
		src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
	<script src="/resources/js/animate.js"></script>

</body>
</html>