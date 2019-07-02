<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="jp.myouth.db.Participants"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if (!user)
		response.sendRedirect("/login");
	
	String userId = (String) session.getAttribute("userId");

	String event = (String) session.getAttribute("event");

	Participants db = new Participants();
	db.open();
	ArrayList<String> data = db.participants(event);
	int total = db.totalParticipants(event);
	db.close();

	response.setContentType("text/csv");
	response.setHeader("Content-Disposition", "attachment; filename=\"" + event + ".csv\"");
	OutputStream outputStream = response.getOutputStream();
	//BOMを付与
	outputStream.write(0xef);
	outputStream.write(0xbb);
	outputStream.write(0xbf);

	String outputResult;
	outputResult = "参加申込日,名前,フリガナ,性別,メールアドレス,電話番号,生年月日,ルーツをもつ国,ルーツをもつ国(2),ルーツをもつ国(3),学校名または会社名,学年または職業,アレルギー,郵便番号,住所,参加のきかっけ\n";
	outputStream.write(outputResult.getBytes("UTF-8"));
	int i = 1;
	int len = data.size() / total;
	for (String string : data) {
		if (i == len) {
			if (string == null)
				outputResult = "\n";
			else
				outputResult = string + "\n";
			i = 0;
		} else {
			if (string == null)
				outputResult = ",";
			else
				outputResult = string + ",";
		}
		outputStream.write(outputResult.getBytes("UTF-8"));
		i++;
	}
	outputStream.flush();
	outputStream.close();
%>

<!DOCTYPE html>
<html lang="en">
<head>
<!-- Pikachu Credits: Hazem Ashraf https://codepen.io/Tetsu/pen/rLJyPp -->
<meta charset="UTF-8">
<title>ダウンロード</title>
<link rel="stylesheet" href="/resources/css/insert.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1, viewport-fit=cover">
</head>
<body>

	<div class="loading">
		<img src="https://a.top4top.net/p_1990j031.gif" alt="Loading">
	</div>
	<div class="mouse original"></div>
	<script src='https://code.jquery.com/jquery-2.2.4.min.js'></script>



	<script src="/resources/js/insert.js"></script>


</body>

</html>