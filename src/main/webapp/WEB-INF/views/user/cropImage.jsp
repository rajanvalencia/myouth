<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if(!user)
		response.sendRedirect("/login");
	Integer height = (Integer) session.getAttribute("height");
	Integer width = (Integer) session.getAttribute("width");
%>
<!DOCTYPE html>
<html>
<head>
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
<script type="text/javascript" src="/resources/jcrop/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/jcrop/js/jquery.Jcrop.js"></script>
<link rel="stylesheet" href="/resources/jcrop/css/jquery.Jcrop.css" type="text/css" />
<link rel="stylesheet" href="/resources/alpha/css/main.css" />
<title>画像切り込み</title>
 </head>
  	<body class="is-preload" style="background-color: black;">
		<div id="page-wrapper">
			<section id="main" class="container">
				<div class="row">
					<div class="col-12">
   						<section class="box">
    						<span class="image fit"><img src="<%out.print(session.getAttribute("photoUrl"));%>" id="cropbox"/></span>
    						<form method="post" action="<%out.print(session.getAttribute("postUrl"));%>">
								<input type="hidden" name="x1" id="x1" /> 
								<input type="hidden" name="y1" id="y1" /> 
								<input type="hidden" name="x2" id="x2" />
								<input type="hidden" name="y2" id="y2" /> 
								<input type="hidden" name="w" id="w" /> 
								<input type="hidden" name="h" id="h" /> 
								<input type="hidden" name="maxh" id="maxh" /> 
								<input type="hidden" name="maxw" id="maxw" />
								<div class="col-12">
									<ul class="actions">
										<li><input type="submit" class="button" value="完了" name="action"/></li>
									</ul>
								</div>
						</form>
						</section>
					</div>
				</div>
    		</section>
    	</div>
   <script type="text/javascript">
      $(window).load(function(){
    	  
    	//or however you get a handle to the IMG
    	var width = $('#cropbox').width();
    	var height = $('#cropbox').height();
    	$('#maxh').val(height);
    	$('#maxw').val(width);
    	
      	var jcrop_api;
        initJcrop();
        function initJcrop() {
          jcrop_api = $.Jcrop('#cropbox',{ onChange: setCoords, onSelect: setCoords });
          jcrop_api.setSelect([0,0,1500,1500]);
          jcrop_api.setOptions({ allowSelect: false, allowMove: true, allowResize: false, aspectRatio: 1 });
        }
      });
      function setCoords(c) {
        jQuery('#x1').val(c.x);
        jQuery('#y1').val(c.y);
        jQuery('#x2').val(c.x2);
        jQuery('#y2').val(c.y2);
        jQuery('#w').val(c.w);
        jQuery('#h').val(c.h);
       };
     </script>
  </body>
</html>