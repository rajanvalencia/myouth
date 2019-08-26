<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if(!user)
		response.sendRedirect("/login");
	String path = (String) session.getAttribute("path");
	Integer height = (Integer) session.getAttribute("height");
	Integer width = (Integer) session.getAttribute("width");
%>
<!DOCTYPE html>
<html>
  <head>
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
							<form method="post" action="/cropAndSaveProfilePicture">
							<div class="row gtr-50 gtr-uniform">
								<div class="col-12">
									<input type="hidden" name="file"
										value="https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/<%out.print(path);%>" />
									<input type="hidden" name="x1" id="x1" /> <input type="hidden"
										name="y1" id="y1" /> <input type="hidden" name="x2" id="x2" />
									<input type="hidden" name="y2" id="y2" /> <input type="hidden"
										name="w" id="w" /> <input type="hidden" name="h" id="h" /> <input
										type="hidden" name="maxh" id="maxh" /> <input type="hidden"
										name="maxw" id="maxw" />
									<ul class="actions">
										<li id="btn-width"><input type="submit" class="button" value="完了" name="action"/></li>
									</ul>
								</div>
							</div>
						</form>
   						</section>
   						<section class="box">
    						<span class="image fit"><img src="https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/<%out.print(path);%>" id="cropbox"/></span>
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
    	
    	if(width <= 480)
    		$('#btn-width').width(width);
          
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