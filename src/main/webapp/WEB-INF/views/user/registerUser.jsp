<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
		<title>新規登録</title>
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
					<a href="/"><span class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
				</section>
					<header>
						<h2>新規登録</h2>
					</header>
					<div class="box">
					<div class="row">
						<form id="form" method="post" action="/insertUser">
							<div class="row gtr-50 gtr-uniform">
								<div class="col-12">
									<label for="name">名前</label>
									<input type="text" name="name" id="name" value="" placeholder="" required/>
								</div>
								<div class="col-12">
									<label for="fname">フリガナ</label>
									<input type="text" name="fname" id="fname" value="" placeholder="" required/>
								</div>
								<div class="col-12">
									<label for="email">メールアドレス</label>
									<input type="email" name="email" id="email" value="" placeholder="" required/>
								</div>
								<div class="col-4 col-4-mobilep">
									<label for="years">生年月日  年</label>
									<select name="birth-year" id="years" required>
													<option label="" selected></option>
									</select>
								</div>
								<div class="col-4 col-4-mobilep">
									<label for="months">月</label>
									<select name="birth-month" id="months" required>
													<option label="" selected></option>
									</select>
								</div>
								<div class="col-4 col-4-mobilep">
									<label for="days">日</label>
									<select name="birth-day" id="days" required>
													<option value="" selected></option>
									</select>
								</div>
								<div class="col-12">
									<label for="password">パスワード<br />(8文字以上で1文字以上の数字、小文字アルファベット、大文字アルファベットがそれぞれ含まれていること)</label> <input type="password"
										name="password" id="password"
										pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
										title="8文字以上で1文字以上の数字、小文字アルファベット、大文字アルファベットがそれぞれ含まれていること"
										value="" required />
								</div>
								<div class="col-12">
									<label for="confirm_password">パスワードをもう一度入力</label> <input type="password"
									name="confirm_password" id="confirm_password"
									pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
									value="" required />
									<br />
									<span id='message'></span>
								</div>
								<div class="col-12">
									<ul class="actions" id="swap">
										<li><input id="btn" type="submit" value="登録 " disabled/></li>
									</ul>
								</div>
							</div>
						</form>
					</div>
				</div>
				<section  class="back-button">
					<a href="/"><span class="fas fa-arrow-left fa-3x faa-passing-reverse animated"></span></a>
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

		$(function() {

		    //populate our years select box
		    for ( i = new Date().getFullYear()-70; i <= new Date().getFullYear()-18; i++){
		        $('#years').append($('<option />').val(i).html(i));
		    }
		    //populate our months select box
		    for (i = 1; i <= 12; i++){
		        $('#months').append($('<option />').val(i).html(i));
		    }
		    //populate our Days select box
		    updateNumberOfDays(); 

		    //"listen" for change events
		    $('#years, #months').change(function(){
		        updateNumberOfDays(); 
		    });

		});

		//function to update the days based on the current values of month and year
		function updateNumberOfDays(){
		    $('#days').html('');
		    month = $('#months').val();
		    year = $('#years').val();
		    days = daysInMonth(month, year);

		    for(i = ''; i <= days ; i++){
		            $('#days').append($('<option />').val(i).html(i));
		    }
		}

		//helper function
		function daysInMonth(month, year) {
		    return new Date(year, month, 0).getDate();
		}
		
		
		$('#form').on('submit', function (){
			$('#swap')
		    .html('<li><i class="fa fa-refresh fa-2x faa-spin faa-fast animated"></i></li>')
		});
		
		$('#password, #confirm_password').on('keyup', function () {
			  if ($('#password').val() == $('#confirm_password').val() && $('#confirm_password').val() != '') {
			    $('#message').html('パスワードが一致しました').css('color', '#06A10B');
			    $('#btn').removeAttr("disabled");
			  }
			  else {
			    $('#message').html('パスワードが一致しません').css('color', '#ff7496');
			  	$('#btn').prop("disabled", true);
			  }
			});
	</script>

	</body>
</html>