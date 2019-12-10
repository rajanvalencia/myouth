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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/resources/css/font-awesome-animation.css">
	
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
				<section  class="back-button">
					<a href="/"><span class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
				</section>
				<header>
					<h2>Register</h2>
				</header>
				<section class="box ${success}" id="success" ${success}>
					<p style="color: #06A10B;">
						正常に送信されました <i class="fa fa-check faa-tada animated"></i>
						<br />
						只今メールアドレスに承認するためのリンクを送信致しました 
					</p>
					<p>
						<i class="fa fa-exclamation-triangle" style="color: #ff7496;"></i>
						30分経過したら自動的に無効になりますのでご注意ください。
						<br />
						メールに記載されているリンクは一度のみクリックできます。
					</p>
				</section>
				<section class="box">
					<div class="row">
						<form id="form" method="post" action="/register">
							<div class="row gtr-50 gtr-uniform">
								<div class="col-12">
									<label for="name">名前</label>
									<input type="text" name="name" required/>
								</div>
								<div class="col-12">
									<label for="fname">フリガナ</label>
									<input type="text" name="fname" required/>
								</div>
								<div class="col-12">
									<label for="email">
										メールアドレス
										<br />
										<span id="messageForEmailAddressAvailability"></span>
									</label>
									<input type="email" name="email" id="emailAddress" required/>
								</div>
								<div class="col-12">
									<label for="phone">電話番号</label>
									<input type="number" name="phone" required/>
								</div>
								<div class="col-3 col-5-mobile">
									<label for="birth-date">生年月日</label>
									<input type="date" id="birth-date" name="birth-date" required>
								</div>
								<div class="col-12">
									<label for="password">パスワード<br />(8文字以上で1文字以上の数字、小文字アルファベット、大文字アルファベットがそれぞれ含まれていること)</label>
									<input type="password" name="password" id="password" autocomplete="new-password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="8文字以上で1文字以上の数字、小文字アルファベット、大文字アルファベットがそれぞれ含まれていること" value="" required />
								</div>
								<div class="col-12">
									<label for="confirm_password">
										パスワードをもう一度入力
										<br />
										<span id='message'></span>
									</label>
									<input type="password" name="confirm_password" id="confirm_password" autocomplete="new-password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" value="" required />
								</div>
								<div id="btn" class="col-4 col-12-mobile">
									<ul class="actions">
										<li><input id="submit" type="submit" value="登録 " disabled/></li>
									</ul>
								</div>
								<div id="loading" class="col-12" hidden>
									<ul class="actions">
										<li>
											<i class="fa fa-refresh fa-2x faa-spin faa-fast animated"></i>
										</li>
									</ul>
								</div>
							</div>
						</form>
					</div>
				</section>
				<section  class="back-button">
					<a href="/"><span class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
				</section>
			</section>

			<!-- Footer -->
			<footer id="footer">
				<ul class="copyright">
					<li>myouth.jp</li><li>Design: <a href="http://html5up.net" target="_blank" rel="noopener">HTML5 UP</a> & <a href="/developer" target="_blank">Rajan Valencia</a></li>
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
		<script>
		
			$(function(){
				$('form').submit(function(){
					$('#btn').hide();
					$('#loading').attr('hidden', false);
				})
				
				var currentYear = new Date().getFullYear();
				$('#birth-date').attr('max', currentYear-14+'-12-31');
				$('#birth-date').attr('min', currentYear-85+'-01-01');
				
				var successAttr = $('#success').attr('hidden');
				var failureAttr = $('#failure').attr('hidden');
				
			    if(successAttr != 'hidden'){
			    	hideSection('success');
			    }
			   
			    if(failureAttr != 'hidden'){
					hideSection('failure');
			    }
			})
			
			function hideSection(sectionId){
				setTimeout(function(){
					$('#'+sectionId+'').toggle('blind');
				}, 5000)
			}
		</script>
		<script>
		
			$('#password, #confirm_password').keyup(function () {
				if($('#password').val().length == 0 || $('#confirm_password').val().length == 0) return ;
				
				if ($('#password').val() == $('#confirm_password').val()) {
					$('#message').html('パスワードが一致しました').css('color', '#06A10B');
					$('#submit').attr('disabled', false);
				} else {
					$('#message').html('パスワードが一致しません').css('color', '#ff7496');
					$('#submit').attr('disabled', true);
					
				}
			});
			
			$('#emailAddress').keyup(function(){
				if($('#emailAddress').val().length == 0) return ;
				
				checkIfEmailAddressIsAvailable($(this).val());
			})
			
			function checkIfEmailAddressIsAvailable(emailAddress){
				var params = {
					emailAddress : emailAddress
				}
				
				$.ajax({
					type	: 'POST',
					url		: '/apis/ajax/users/checkIfEmailAddressIsAvailable',
					data	: params,
					async	: true,
					success	: function(data){
						var response = data['res'];
						
						if(response){
							$('#messageForEmailAddressAvailability').html('メールアドレスは利用可能です。').css('color', '#06A10B');
							$('#submit').attr('disabled', false);
						} else {
							$('#messageForEmailAddressAvailability').html('メールアドレスはすでに利用されています。').css('color', '#ff7496');
							$('#submit').attr('disabled', true);
						}
					},
					error	: function(XMLHttpRequest, textStatus, errorThrown) {
						alert("リクエスト時になんらかのエラーが発生しました：" + textStatus +":\n" + errorThrown);
					}
				})
			}
		</script>
	</body>
</html>