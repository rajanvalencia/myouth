<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page import="jp.myouth.db.User"%>
<%
	Boolean user = (Boolean) session.getAttribute("user");
	if(!user)
		response.sendRedirect("/login");
	String path = (String) session.getAttribute("path");
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
  	<body class="is-preload">
		<div id="page-wrapper">
			<section id="main" class="container">
				<div class="row">
					<div class="col-12">
						<section class="box">
    						<span class="image" style="max-width: 500px; max-height: 500px; width: auto; height: auto;"><img src="https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/<%out.print(path);%>" id="cropbox"/></span>
     						<form action="#">
    							<input type="hidden" name="file" value="https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/<%out.print(path);%>" />
     							<input type="hidden" name="x1" id="x1"/>
     							<input type="hidden" name="y1" id="y1"/>
     							<input type="hidden" name="x2" id="x2"/>
     							<input type="hidden" name="y2" id="y2"/>
     							<input type="hidden" name="w" id="w"/>
     							<input type="hidden" name="h" id="h"/>
     							<input type="submit" value="切り込み" name="action" />
   							</form>
						</section>
					</div>
				</div>
    		</section>
    	</div>
   <script type="text/javascript">
      $(window).load(function(){
        var jcrop_api;
        initJcrop();
        function initJcrop()
        {
          jcrop_api = $.Jcrop('#cropbox',{ onChange: setCoords, onSelect: setCoords });
          jcrop_api.setSelect([0,0,500,500]);
          jcrop_api.setOptions({ allowSelect: false, allowMove: true, allowResize: false, aspectRatio: 1 });
        }
      });
      function setCoords(c)
      {
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