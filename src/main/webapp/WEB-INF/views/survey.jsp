<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jp.myouth.db.Events"%>
<%
	String event = (String) session.getAttribute("event");
	String success = (String) session.getAttribute("success");
	Events db = new Events();
	db.open();
	String eventName = db.eventName(event);
	ArrayList<Boolean> surveyQuestion = db.surveyQuestion(event);
	db.close();
	Boolean name = surveyQuestion.get(0);
	Boolean fname = surveyQuestion.get(1);
	Boolean satisfaction = surveyQuestion.get(2);
	Boolean comments = surveyQuestion.get(3);
	Boolean transportation = surveyQuestion.get(4);
	Boolean improvements = surveyQuestion.get(5);
	Boolean repetition = surveyQuestion.get(6);
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
		<title>アンケート</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="/resources/alpha/css/main.css" />
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="/resources/css/font-awesome-animation.css">
	</head>
	<body class="is-preload">
		<div id="page-wrapper">
			<!-- Main -->
				<section id="main" class="container">
				<section  class="back-button">
					<a href="/events/<%out.print(event);%>"><span class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
				</section>
					<header>
						<h2>Survey</h2>
					</header>
					<section style="background-color: #E8F9DF;"
						class="box <%out.print(success); session.setAttribute("success", "hidden");%>"
						id="success">
						<p>アンケートは正常に送られました <i class="fa fa-check faa-tada animated"></i></p>
					</section>
					<div class="box">
					<div class="row">
					
						<form id="form" method="post" action="/events/<%out.print(event);%>/survey/insertSurvey">
						<div class="row gtr-50 gtr-uniform">
							<div class="col-6 col-12-mobilep">
								<label for="eventName">イベント名</label> <input type="text"
									value="<%out.print(eventName);%>" name="eventName"
									id="eventName" disabled />
							</div>
							<div class="col-6">
							</div>
							<%
				if (name) {
					out.println("<div class=\"col-6 col-12-mobilep\">");
					out.println("<label for=\"name-field\">名前</label>");
					out.println("<input type=\"text\" id=\"name-field\" name=\"name-field\" placeholder=\"\" required>");
					out.println("</div>");
				}
				if (fname) {
					out.println("<div class=\"col-6 col-12-mobilep\">");
					out.println("<label for=\"fname-field\">フリガナ</label>");
					out.println(
							"<input type=\"text\" id=\"fname-field\" name=\"fname-field\" placeholder=\"\" required>");
					out.println("</div>");
				}

				if (satisfaction) {
					out.println("<br /></div>");
					out.println("<div class=\"row gtr-50 gtr-uniform\">");
					out.println("<div class=\"col-12\">");
					out.println("<label for=\"satisfaction-field\">");
					out.println(eventName + "イベントの満足度");
					out.println("</label>");
					out.println("<input type=\"radio\" name=\"satisfaction\"id=\"exampleRadios1\" value=\"5\" checked>");
					out.println("<label for=\"exampleRadios1\"> 非常に満足した </label>");
					out.println("</div>");
					out.println("<div class=\"col-12\">");
					out.println("<input type=\"radio\" name=\"satisfaction\" id=\"exampleRadios2\" value=\"4\">");
					out.println("<label for=\"exampleRadios2\"> 満足した </label>");
					out.println("</div>");
					out.println("<div class=\"col-12\">");
					out.println("<input type=\"radio\" name=\"satisfaction\" id=\"exampleRadios3\" value=\"3\">");
					out.println("<label for=\"exampleRadios3\"> 普通 </label>");
					out.println("</div>");
					out.println("<div class=\"col-12\">");
					out.println("<input type=\"radio\" name=\"satisfaction\" id=\"exampleRadios4\" value=\"2\">");
					out.println("<label for=\"exampleRadios4\"> 満足しなかった </label>");
					out.println("</div>");
					out.println("<div class=\"col-12\">");
					out.println("<input type=\"radio\" name=\"satisfaction\" id=\"exampleRadios5\" value=\"1\">");
					out.println("<label for=\"exampleRadios5\"> 最悪 </label>");
					out.println("</div>");
					out.println("</div>");
					out.println("<div class=\"row gtr-50 gtr-uniform\"><br />");
				}

				if (comments) {
					out.println("<div class=\"col-12 col-12-mobilep\">");
					out.println("<label for=\"text-field\">どうしてそう感じたのかをご記入ください</label>");
					out.println("<textarea name=\"details\" id=\"details\" rows=\"3\" required></textarea>");
					out.println("</div>");

				}

				if (transportation) {
					out.println("<div class=\"col-12 col-12-mobilep\">");
					out.println("<label for=\"transportation-field\" class=\"col-md-4 control-label\">会場までの交通手段（複数選択可）</label>");
					out.println(
							"<input type=\"checkbox\" name=\"train\" value=\"電車\" id=\"train\" checked=\"\">");
					out.println("<label for=\"train\"> 電車</label>");
					out.println("</div>");
					out.println("<div class=\"col-12 col-12-mobilep\">");
					out.println(
							"<input type=\"checkbox\" name=\"bus\" value=\"バス\" id=\"bus\"> <label class=\"form-check-label\" for=\"bus\"> バス </label>");
					out.println("</div>");
					out.println("<div class=\"col-12 col-12-mobilep\">");
					out.println(
							"<input type=\"checkbox\" name=\"four_wheel_vehicle\" value=\"四輪自動車\" id=\"four_wheel_vehicle\">");
					out.println("<label class=\"form-check-label\" for=\"four_wheel_vehicle\"> 四輪自動車 </label>");
					out.println("</div>");
					out.println("<div class=\"col-12 col-12-mobilep\">");
					out.println(
							"<input class=\"form-check-input\" type=\"checkbox\" name=\"two_wheel_vehicle\" value=\"二輪自動車\" id=\"two_wheel_vehicle\">");
					out.println("<label for=\"two_wheel_vehicle\"> 二輪自動車 </label>");
					out.println("</div>");
					out.println("<div class=\"col-12 col-12-mobilep\">");
					out.println(
							"<input type=\"checkbox\" name=\"other_transportation\" value=\"その他\" id=\"other_transportation\">");
					out.println("<label for=\"other_transportation\"> その他 </label>");
					out.println("</div>");
				}

				if (improvements) {
					out.println("<div class=\"col-12 col-12-mobilep\">");
					out.println("<label for=\"text-field\" class=\"col-md-4 control-label\">" + eventName
							+ "イベントを改善できるところがあれば述べてください(自由回答)</label>");
					out.println("<textarea name=\"improvement\" id=\"improvement\" rows=\"3\"></textarea>");
					out.println("</div>");
				}
				
				if (repetition) {
					out.println("<div class=\"col-12 col-12-mobilep\">");
					out.println("<label for=\"transportation-field\">また"+eventName+"に参加したいと思いますか?</label>");
					out.println("<input type=\"radio\" name=\"repetition\" value=\"はい\" id=\"repetition-true\">");
					out.println("<label for=\"repetition-true\"> はい</label>");
					out.println("</div>");
					out.println("<div class=\"col-12 col-12-mobilep\">");
					out.println("<input type=\"radio\" name=\"repetition\" value=\"どちらでもない\" id=\"repetition-maybe\"> <label for=\"repetition-maybe\"> どちらでもない</label>");
					out.println("</div>");
					out.println("<div class=\"col-12 col-12-mobilep\">");
					out.println("<input type=\"radio\" name=\"repetition\" value=\"いいえ\" id=\"repetition-false\"> <label for=\"repetition-false\"> いいえ</label>");
					out.println("</div>");
				}
			%>
							<div class="col-12">
								<ul class="actions" id="swap">
									<li><input id="btn" type="submit" value="送信 "/></li>
								</ul>
							</div>
						</div>
					</form>
					</div>
				</div>
				<section  class="back-button">
					<a href="/events/<%out.print(event);%>"><span class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
				</section>
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

		//選択幕がその他の場合
		function changeSubject(e) {
			  if(e.target.value === 'other-career') {
			    $('#hidden-other-career').removeClass('hidden');
			  } 
			  else if (e.target.value === 'other-trigger'){
			    $('#hidden-other-trigger').removeClass('hidden');
			    
			  }
			  
			  else {
			    $('#hidden-other-trigger').addClass('hidden');
			    $('#hidden-other-career').addClass('hidden');
			  }
			}
	</script>

	</body>
</html>