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
<title>イベント作成</title>
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
					<a href="/home"><span class="fas fa-arrow-left fa-2x faa-passing-reverse animated"></span></a>
				</section>
					<div class="box">
					<div class="row">
						<form id="form" method="post" action="/servlets/events/registerEvent">
							<div class="row gtr-50 gtr-uniform">
								<div class="col-12">
									<label for="name">イベント名</label>
									<input type="text" name="name" required/>
								</div>
								<div class="col-12">
									<label for="id">イベントID(アルファベット・数字の組み合わせのみ)
										<br />
										当イベントのメールアドレスはこれになります。
										<br />
										<span id="message"></span>
									</label>
									<input type="text" name="id" pattern="[a-zA-Z0-9]+" onkeyup="checkIfIdIsAvailable($(this).val())" required/>
								</div>
								<div class="col-12">
									<label for="place">開催場所</label>
									<input type="text" name="place" required/>
								</div>
								<div class="col-6">
									<label for="date">開催日</label>
									<input type="date" name="date" id="date" required/>
								</div>
								<div class="col-6">
									<label for="recruitLimit">募集人数</label>
									<input type="number" name="recruitLimit" required/>
								</div>
								<div class="col-6">
									<label for="startTime">開始時間</label>
									<input type="time" name="startTime" id="startTime" required/>
								</div>
								<div class="col-6">
									<label for="endTime">終了時間</label>
									<input type="time" name="endTime" id="endTime" required/>
								</div>
								<div class="col-6">
									<label for="recruitmentStartDate">募集開始</label>
									<input type="date" name="recruitmentStartDate" id="recruitmentStartDate" required/>
								</div>
								<div class="col-6">
									<label for="recruitmentEndDate">募集〆切</label>
									<input type="date" name="recruitmentEndDate" id="recruitmentEndDate" required/>
								</div>
								<div class="col-12">
									<label for="description">イベント紹介 (Googleなどに検索されたときに表示されます)</label>
									<textarea name="description" rows="6"></textarea>
								</div>
								<div class="col-12" id="btn">
									<ul class="actions" id="swap">
										<li><input type="submit" value="登録 "/></li>
									</ul>
								</div>
							</div>
						</form>
					</div>
				</div>
			</section>

			<!-- Footer -->
				<footer id="footer">
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
			$('#form').on('submit',function() {
				$('#swap').html('<li><i class="fa fa-refresh fa-2x faa-spin faa-fast animated"></i></li>');
			});
			
			$('#recruitmentStartDate').blur(function(){
				$('#recruitmentEndDate').attr('min', $(this).val());
				$('#eventDate').attr('min', $(this).val());
			});
			
			$('#recruitmentEndDate').blur(function(){
				$('#recruitmentStartDate').attr('max', $(this).val());
			});
			
			function checkIfIdIsAvailable(id){

				if(id.length == 0) {
					$('#message').removeHtml();
					return;
				}

				var params = {
					id : id
				}
				
				$.ajax({
					type	: 'POST',
					url		: '/apis/ajax/events/checkIfIdIsAvailable',
					data	: params,
					async	: true,
					success : function(data){
						res = data['res'];
						if(res){
							$('#message').html('IDは利用可能です。').css('color', '#06A10B');
							$('#btn').show();
						} else {
							$('#message').html('IDはすでに利用されています。').css('color', '#ff7496');
							$('#btn').hide();
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