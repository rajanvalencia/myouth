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
	ArrayList<Boolean> formQuestion = db.formQuestion(event);
	db.close();
%>
<head>
<meta charset="UTF-8">
<title><%out.print(eventName);%>参加申し込み</title>
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
		<form action="/events/<%out.print(event);%>/form/insert" method="post" onsubmit="animate()">
			<legend><%out.print(eventName);%>参加申し込み</legend>

			<%
				if (formQuestion.get(0)) {
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

				if (formQuestion.get(1)) {
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

				if (formQuestion.get(2)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"fname-field\" class=\"col-md-4 control-label\">性別</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-user\"></i>");
					out.println("</span>");
					out.println("<select class=\"form-control\" name=\"sex-field\" required>");
					out.println("<option label=\" \" selected></option>");
					out.println("<option value=\"男性\">男性</option>");
					out.println("<option value=\"女性\">女性</option>");
					out.println("</select>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");

				}

				if (formQuestion.get(3)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"fname-field\" class=\"col-md-4 control-label\">メールアドレス</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-envelope\"></i>");
					out.println("</span>");
					out.println(
							"<input type=\"email\" class=\"form-control\" id=\"email-field\" name=\"email-field\" placeholder=\"\" required>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (formQuestion.get(4)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"fname-field\" class=\"col-md-4 control-label\">電話番号</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-phone\"></i>");
					out.println("</span>");
					out.println(
							"<input pattern=\"\\d{10,11}\" class=\"form-control\" id=\"phone-field\" name=\"phone-field\" placeholder=\"ハイフンなしで入力\" required>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (formQuestion.get(5)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"fname-field\" class=\"col-md-4 control-label\">生年月日</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-calendar\"></i>");
					out.println("</span>");
					out.println("<select class=\"form-control\" name=\"birth-year\" id=\"years\" required>");
					out.println("<option label=\" \" selected></option>");
					out.println("</select>");
					out.println("<select class=\"form-control\" name=\"birth-month\" id=\"months\" required>");
					out.println("<option label=\" \" selected></option>");
					out.println("</select>");
					out.println("<select class=\"form-control\" name=\"birth-day\" id=\"days\" required> </select>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (formQuestion.get(6)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"fname-field\" class=\"col-md-4 control-label\">学年または職種</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-education\"></i>");
					out.println("</span>");
					out.println(
							"<select class=\"form-control\" name=\"grade-field\" onchange=\"changeSubject(event)\" required>");
					out.println("<option label=\" \" selected></option>");
					out.println("<option value=\"中学１年生\">中学１年生</option>");
					out.println("<option value=\"中学２年生\">中学２年生</option>");
					out.println("<option value=\"中学３年生\">中学３年生</option>");
					out.println("<option value=\"高校１年生\">高校１年生</option>");
					out.println("<option value=\"高校２年生\">高校２年生</option>");
					out.println("<option value=\"高校３年生\">高校３年生</option>");
					out.println("<option value=\"大学１年生\">大学１年生</option>");
					out.println("<option value=\"大学２年生\">大学２年生</option>");
					out.println("<option value=\"大学３年生\">大学３年生</option>");
					out.println("<option value=\"大学４年生\">大学４年生</option>");
					out.println("<option value=\"大学院生\">大学院生</option>");
					out.println("<option value=\"短大学生\">短大学生</option>");
					out.println("<option value=\"専門学生\">専門学生</option>");
					out.println("<option value =\"浪人生\">浪人生</option>");
					out.println("<option value=\"フリーター\">フリーター</option>");
					out.println("<option value=\"社会人\">社会人</option>");
					out.println("<option value=\"other-grade\">その他</option>");
					out.println("</select>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");

					out.println("<div id=\"hidden-other-grade\" class=\"form-group col-xs-12 hidden\">");
					out.println("<label for=\"other-grade-field\" class=\"col-md-4 control-label\">その他とは</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-education\"></i>");
					out.println("</span>");
					out.println(
							"<input type=\"text\" class=\"form-control\" id=\"other-grade-field\" name=\"other-grade-field\" placeholder=\"\" />");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (formQuestion.get(7)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"fname-field\" class=\"col-md-4 control-label\">学校名または会社名</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-road\"></i>");
					out.println("</span>");
					out.println(
							"<input type=\"text\" class=\"form-control\" id=\"school-field\" name=\"school-field\" placeholder=\"\" required>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (formQuestion.get(8)) {
					out.println("<div id=\"country-select\" class=\"form-group col-xs-12\">");
					out.println("<label for=\"country-field\" class=\"col-md-4 control-label\">ルーツをもつ国</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-globe\"></i></span>");
					out.println("<select class=\"form-control\" name=\"country-field\" required>");
					out.println("<option label=\" \" selected></option>");
					out.println("<option value=\"Afghanistan\">Afghanistan</option>");
					out.println("<option value=\"Albania\">Albania</option>");
					out.println("<option value=\"Algeria\">Algeria</option>");
					out.println("<option value=\"American Samoa\">American Samoa</option>");
					out.println("<option value=\"Andorra\">Andorra</option>");
					out.println("<option value=\"Angola\">Angola</option>");
					out.println("<option value=\"Anguilla\">Anguilla</option>");
					out.println("<option value=\"Antartica\">Antarctica</option>");
					out.println("<option value=\"Antigua and Barbuda\">Antigua and Barbuda</option>");
					out.println("<option value=\"Argentina\">Argentina</option>");
					out.println("<option value=\"Armenia\">Armenia</option>");
					out.println("<option value=\"Aruba\">Aruba</option>");
					out.println("<option value=\"Australia\">Australia</option>");
					out.println("<option value=\"Austria\">Austria</option>");
					out.println("<option value=\"Azerbaijan\">Azerbaijan</option>");
					out.println("<option value=\"Bahamas\">Bahamas</option>");
					out.println("<option value=\"Bahrain\">Bahrain</option>");
					out.println("<option value=\"Bangladesh\">Bangladesh</option>");
					out.println("<option value=\"Barbados\">Barbados</option>");
					out.println("<option value=\"Belarus\">Belarus</option>");
					out.println("<option value=\"Belgium\">Belgium</option>");
					out.println("<option value=\"Belize\">Belize</option>");
					out.println("<option value=\"Benin\">Benin</option>");
					out.println("<option value=\"Bermuda\">Bermuda</option>");
					out.println("<option value=\"Bhutan\">Bhutan</option>");
					out.println("<option value=\"Bolivia\">Bolivia</option>");
					out.println("<option value=\"Bosnia and Herzegowina\">Bosnia and Herzegowina</option>");
					out.println("<option value=\"Botswana\">Botswana</option>");
					out.println("<option value=\"Bouvet Island\">Bouvet Island</option>");
					out.println("<option value=\"Brazil\">Brazil</option>");
					out.println("<option value=\"British Indian Ocean Territory\">British Indian Ocean Territory</option>");
					out.println("<option value=\"Brunei Darussalam\">Brunei Darussalam</option>");
					out.println("<option value=\"Bulgaria\">Bulgaria</option>");
					out.println("<option value=\"Burkina Faso\">Burkina Faso</option>");
					out.println("<option value=\"Burundi\">Burundi</option>");
					out.println("<option value=\"Cambodia\">Cambodia</option>");
					out.println("<option value=\"Cameroon\">Cameroon</option>");
					out.println("<option value=\"Canada\">Canada</option>");
					out.println("<option value=\"Cape Verde\">Cape Verde</option>");
					out.println("<option value=\"Cayman Islands\">Cayman Islands</option>");
					out.println("<option value=\"Central African Republic\">Central African Republic</option>");
					out.println("<option value=\"Chad\">Chad</option>");
					out.println("<option value=\"Chile\">Chile</option>");
					out.println("<option value=\"China\">China</option>");
					out.println("<option value=\"Christmas Island\">Christmas Island</option>");
					out.println("<option value=\"Cocos Islands\">Cocos (Keeling) Islands</option>");
					out.println("<option value=\"Colombia\">Colombia</option>");
					out.println("<option value=\"Comoros\">Comoros</option>");
					out.println("<option value=\"Congo\">Congo</option>");
					out.println("<option value=\"Congo\">Congo, the Democratic Republic of the</option>");
					out.println("<option value=\"Cook Islands\">Cook Islands</option>");
					out.println("<option value=\"Costa Rica\">Costa Rica</option>");
					out.println("<option value=\"Cota D'Ivoire\">Cote d'Ivoire</option>");
					out.println("<option value=\"Croatia\">Croatia (Hrvatska)</option>");
					out.println("<option value=\"Cuba\">Cuba</option>");
					out.println("<option value=\"Cyprus\">Cyprus</option>");
					out.println("<option value=\"Czech Republic\">Czech Republic</option>");
					out.println("<option value=\"Denmark\">Denmark</option>");
					out.println("<option value=\"Djibouti\">Djibouti</option>");
					out.println("<option value=\"Dominica\">Dominica</option>");
					out.println("<option value=\"Dominican Republic\">Dominican Republic</option>");
					out.println("<option value=\"East Timor\">East Timor</option>");
					out.println("<option value=\"Ecuador\">Ecuador</option>");
					out.println("<option value=\"Egypt\">Egypt</option>");
					out.println("<option value=\"El Salvador\">El Salvador</option>");
					out.println("<option value=\"Equatorial Guinea\">Equatorial Guinea</option>");
					out.println("<option value=\"Eritrea\">Eritrea</option>");
					out.println("<option value=\"Estonia\">Estonia</option>");
					out.println("<option value=\"Ethiopia\">Ethiopia</option>");
					out.println("<option value=\"Falkland Islands\">Falkland Islands (Malvinas)</option>");
					out.println("<option value=\"Faroe Islands\">Faroe Islands</option>");
					out.println("<option value=\"Fiji\">Fiji</option>");
					out.println("<option value=\"Finland\">Finland</option>");
					out.println("<option value=\"France\">France</option>");
					out.println("<option value=\"France Metropolitan\">France, Metropolitan</option>");
					out.println("<option value=\"French Guiana\">French Guiana</option>");
					out.println("<option value=\"French Polynesia\">French Polynesia</option>");
					out.println("<option value=\"French Southern Territories\">French Southern Territories</option>");
					out.println("<option value=\"Gabon\">Gabon</option>");
					out.println("<option value=\"Gambia\">Gambia</option>");
					out.println("<option value=\"Georgia\">Georgia</option>");
					out.println("<option value=\"Germany\">Germany</option>");
					out.println("<option value=\"Ghana\">Ghana</option>");
					out.println("<option value=\"Gibraltar\">Gibraltar</option>");
					out.println("<option value=\"Greece\">Greece</option>");
					out.println("<option value=\"Greenland\">Greenland</option>");
					out.println("<option value=\"Grenada\">Grenada</option>");
					out.println("<option value=\"Guadeloupe\">Guadeloupe</option>");
					out.println("<option value=\"Guam\">Guam</option>");
					out.println("<option value=\"Guatemala\">Guatemala</option>");
					out.println("<option value=\"Guinea\">Guinea</option>");
					out.println("<option value=\"Guinea-Bissau\">Guinea-Bissau</option>");
					out.println("<option value=\"Guyana\">Guyana</option>");
					out.println("<option value=\"Haiti\">Haiti</option>");
					out.println("<option value=\"Heard and McDonald Islands\">Heard and Mc Donald Islands</option>");
					out.println("<option value=\"Holy See\">Holy See (Vatican City State)</option>");
					out.println("<option value=\"Honduras\">Honduras</option>");
					out.println("<option value=\"Hong Kong\">Hong Kong</option>");
					out.println("<option value=\"Hungary\">Hungary</option>");
					out.println("<option value=\"Iceland\">Iceland</option>");
					out.println("<option value=\"India\">India</option>");
					out.println("<option value=\"Indonesia\">Indonesia</option>");
					out.println("<option value=\"Iran\">Iran (Islamic Republic of)</option>");
					out.println("<option value=\"Iraq\">Iraq</option>");
					out.println("<option value=\"Ireland\">Ireland</option>");
					out.println("<option value=\"Israel\">Israel</option>");
					out.println("<option value=\"Italy\">Italy</option>");
					out.println("<option value=\"Jamaica\">Jamaica</option>");
					out.println("<option value=\"Japan\">Japan</option>");
					out.println("<option value=\"Jordan\">Jordan</option>");
					out.println("<option value=\"Kazakhstan\">Kazakhstan</option>");
					out.println("<option value=\"Kenya\">Kenya</option>");
					out.println("<option value=\"Kiribati\">Kiribati</option>");
					out.println("<option value=\"Democratic People's Republic of Korea\">Korea, Democratic People's Republic of</option>");
					out.println("<option value=\"Korea\">Korea, Republic of</option>");
					out.println("<option value=\"Kuwait\">Kuwait</option>");
					out.println("<option value=\"Kyrgyzstan\">Kyrgyzstan</option>");
					out.println("<option value=\"Lao\">Lao People's Democratic Republic</option>");
					out.println("<option value=\"Latvia\">Latvia</option>");
					out.println("<option value=\"Lebanon\">Lebanon</option>");
					out.println("<option value=\"Lesotho\">Lesotho</option>");
					out.println("<option value=\"Liberia\">Liberia</option>");
					out.println("<option value=\"Libyan Arab Jamahiriya\">Libyan Arab Jamahiriya</option>");
					out.println("<option value=\"Liechtenstein\">Liechtenstein</option>");
					out.println("<option value=\"Lithuania\">Lithuania</option>");
					out.println("<option value=\"Luxembourg\">Luxembourg</option>");
					out.println("<option value=\"Macau\">Macau</option>");
					out.println("<option value=\"Macedonia\">Macedonia, The Former Yugoslav Republic of</option>");
					out.println("<option value=\"Madagasca\">Madagascar</option>");
					out.println("<option value=\"Malawi\">Malawi</option>");
					out.println("<option value=\"Malaysia\">Malaysia</option>");
					out.println("<option value=\"Maldives\">Maldives</option>");
					out.println("<option value=\"Mali\">Mali</option>");
					out.println("<option value=\"Malta\">Malta</option>");
					out.println("<option value=\"Marshall Island\">Marshall Islands</option>");
					out.println("<option value=\"Martinique\">Martinique</option>");
					out.println("<option value=\"Mauritania\">Mauritania</option>");
					out.println("<option value=\"Mauritius\">Mauritius</option>");
					out.println("<option value=\"Mayotte\">Mayotte</option>");
					out.println("<option value=\"Mexico\">Mexico</option>");
					out.println("<option value=\"Micronesia\">Micronesia, Federated States of</option>");
					out.println("<option value=\"Moldova\">Moldova, Republic of</option>");
					out.println("<option value=\"Monaco\">Monaco</option>");
					out.println("<option value=\"Mongolia\">Mongolia</option>");
					out.println("<option value=\"Montserrat\">Montserrat</option>");
					out.println("<option value=\"Morocco\">Morocco</option>");
					out.println("<option value=\"Mozambique\">Mozambique</option>");
					out.println("<option value=\"Myanmar\">Myanmar</option>");
					out.println("<option value=\"Namibia\">Namibia</option>");
					out.println("<option value=\"Nauru\">Nauru</option>");
					out.println("<option value=\"Nepal\">Nepal</option>");
					out.println("<option value=\"Netherlands\">Netherlands</option>");
					out.println("<option value=\"Netherlands Antilles\">Netherlands Antilles</option>");
					out.println("<option value=\"New Caledonia\">New Caledonia</option>");
					out.println("<option value=\"New Zealand\">New Zealand</option>");
					out.println("<option value=\"Nicaragua\">Nicaragua</option>");
					out.println("<option value=\"Niger\">Niger</option>");
					out.println("<option value=\"Nigeria\">Nigeria</option>");
					out.println("<option value=\"Niue\">Niue</option>");
					out.println("<option value=\"Norfolk Island\">Norfolk Island</option>");
					out.println("<option value=\"Northern Mariana Islands\">Northern Mariana Islands</option>");
					out.println("<option value=\"Norway\">Norway</option>");
					out.println("<option value=\"Oman\">Oman</option>");
					out.println("<option value=\"Pakistan\">Pakistan</option>");
					out.println("<option value=\"Palau\">Palau</option>");
					out.println("<option value=\"Panama\">Panama</option>");
					out.println("<option value=\"Papua New Guinea\">Papua New Guinea</option>");
					out.println("<option value=\"Paraguay\">Paraguay</option>");
					out.println("<option value=\"Peru\">Peru</option>");
					out.println("<option value=\"Philippines\">Philippines</option>");
					out.println("<option value=\"Pitcairn\">Pitcairn</option>");
					out.println("<option value=\"Poland\">Poland</option>");
					out.println("<option value=\"Portugal\">Portugal</option>");
					out.println("<option value=\"Puerto Rico\">Puerto Rico</option>");
					out.println("<option value=\"Qatar\">Qatar</option>");
					out.println("<option value=\"Reunion\">Reunion</option>");
					out.println("<option value=\"Romania\">Romania</option>");
					out.println("<option value=\"Russia\">Russian Federation</option>");
					out.println("<option value=\"Rwanda\">Rwanda</option>");
					out.println("<option value=\"Saint Kitts and Nevis\">Saint Kitts and Nevis</option>");
					out.println("<option value=\"Saint LUCIA\">Saint LUCIA</option>");
					out.println("<option value=\"Saint Vincent\">Saint Vincent and the Grenadines</option>");
					out.println("<option value=\"Samoa\">Samoa</option>");
					out.println("<option value=\"San Marino\">San Marino</option>");
					out.println("<option value=\"Sao Tome and Principe\">Sao Tome and Principe</option>");
					out.println("<option value=\"Saudi Arabia\">Saudi Arabia</option>");
					out.println("<option value=\"Senegal\">Senegal</option>");
					out.println("<option value=\"Seychelles\">Seychelles</option>");
					out.println("<option value=\"Sierra\">Sierra Leone</option>");
					out.println("<option value=\"Singapore\">Singapore</option>");
					out.println("<option value=\"Slovakia\">Slovakia (Slovak Republic)</option>");
					out.println("<option value=\"Slovenia\">Slovenia</option>");
					out.println("<option value=\"Solomon Islands\">Solomon Islands</option>");
					out.println("<option value=\"Somalia\">Somalia</option>");
					out.println("<option value=\"South Africa\">South Africa</option>");
					out.println("<option value=\"South Georgia\">South Georgia and the South Sandwich Islands</option>");
					out.println("<option value=\"Span\">Spain</option>");
					out.println("<option value=\"Sri Lanka\">Sri Lanka</option>");
					out.println("<option value=\"St. Helena\">St. Helena</option>");
					out.println("<option value=\"St. Pierre and Miguelon\">St. Pierre and Miquelon</option>");
					out.println("<option value=\"Sudan\">Sudan</option>");
					out.println("<option value=\"Suriname\">Suriname</option>");
					out.println("<option value=\"Svalbard\">Svalbard and Jan Mayen Islands</option>");
					out.println("<option value=\"Swaziland\">Swaziland</option>");
					out.println("<option value=\"Sweden\">Sweden</option>");
					out.println("<option value=\"Switzerland\">Switzerland</option>");
					out.println("<option value=\"Syria\">Syrian Arab Republic</option>");
					out.println("<option value=\"Taiwan\">Taiwan, Province of China</option>");
					out.println("<option value=\"Tajikistan\">Tajikistan</option>");
					out.println("<option value=\"Tanzania\">Tanzania, United Republic of</option>");
					out.println("<option value=\"Thailand\">Thailand</option>");
					out.println("<option value=\"Togo\">Togo</option>");
					out.println("<option value=\"Tokelau\">Tokelau</option>");
					out.println("<option value=\"Tonga\">Tonga</option>");
					out.println("<option value=\"Trinidad and Tobago\">Trinidad and Tobago</option>");
					out.println("<option value=\"Tunisia\">Tunisia</option>");
					out.println("<option value=\"Turkey\">Turkey</option>");
					out.println("<option value=\"Turkmenistan\">Turkmenistan</option>");
					out.println("<option value=\"Turks and Caicos\">Turks and Caicos Islands</option>");
					out.println("<option value=\"Tuvalu\">Tuvalu</option>");
					out.println("<option value=\"Uganda\">Uganda</option>");
					out.println("<option value=\"Ukraine\">Ukraine</option>");
					out.println("<option value=\"United Arab Emirates\">United Arab Emirates</option>");
					out.println("<option value=\"United Kingdom\">United Kingdom</option>");
					out.println("<option value=\"United States\">United States</option>");
					out.println("<option value=\"United States Minor Outlying Islands\">United States Minor Outlying Islands</option>");
					out.println("<option value=\"Uruguay\">Uruguay</option>");
					out.println("<option value=\"Uzbekistan\">Uzbekistan</option>");
					out.println("<option value=\"Vanuatu\">Vanuatu</option>");
					out.println("<option value=\"Venezuela\">Venezuela</option>");
					out.println("<option value=\"Vietnam\">Viet Nam</option>");
					out.println("<option value=\"Virgin Islands (British)\">Virgin Islands (British)</option>");
					out.println("<option value=\"Virgin Islands (U.S)\">Virgin Islands (U.S.)</option>");
					out.println("<option value=\"Wallis and Futana Islands\">Wallis and Futuna Islands</option>");
					out.println("<option value=\"Western Sahara\">Western Sahara</option>");
					out.println("<option value=\"Yemen\">Yemen</option>");
					out.println("<option value=\"Yugoslavia\">Yugoslavia</option>");
					out.println("<option value=\"Zambia\">Zambia</option>");
					out.println("<option value=\"Zimbabwe\">Zimbabwe</option>");
					out.println("</select>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");

				}

				if (formQuestion.get(9)) {
					out.println("<div id=\"country-select\" class=\"form-group col-xs-12\">");
					out.println("<label for=\"country-field\" class=\"col-md-4 control-label\">ルーツをもつ国(二つ目)</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-globe\"></i></span>");
					out.println("<select class=\"form-control\" name=\"country-field-2\">");
					out.println("<option label=\"非選択可\"selected></option>");
					out.println("<option value=\"Afghanistan\">Afghanistan</option>");
					out.println("<option value=\"Albania\">Albania</option>");
					out.println("<option value=\"Algeria\">Algeria</option>");
					out.println("<option value=\"American Samoa\">American Samoa</option>");
					out.println("<option value=\"Andorra\">Andorra</option>");
					out.println("<option value=\"Angola\">Angola</option>");
					out.println("<option value=\"Anguilla\">Anguilla</option>");
					out.println("<option value=\"Antartica\">Antarctica</option>");
					out.println("<option value=\"Antigua and Barbuda\">Antigua and Barbuda</option>");
					out.println("<option value=\"Argentina\">Argentina</option>");
					out.println("<option value=\"Armenia\">Armenia</option>");
					out.println("<option value=\"Aruba\">Aruba</option>");
					out.println("<option value=\"Australia\">Australia</option>");
					out.println("<option value=\"Austria\">Austria</option>");
					out.println("<option value=\"Azerbaijan\">Azerbaijan</option>");
					out.println("<option value=\"Bahamas\">Bahamas</option>");
					out.println("<option value=\"Bahrain\">Bahrain</option>");
					out.println("<option value=\"Bangladesh\">Bangladesh</option>");
					out.println("<option value=\"Barbados\">Barbados</option>");
					out.println("<option value=\"Belarus\">Belarus</option>");
					out.println("<option value=\"Belgium\">Belgium</option>");
					out.println("<option value=\"Belize\">Belize</option>");
					out.println("<option value=\"Benin\">Benin</option>");
					out.println("<option value=\"Bermuda\">Bermuda</option>");
					out.println("<option value=\"Bhutan\">Bhutan</option>");
					out.println("<option value=\"Bolivia\">Bolivia</option>");
					out.println("<option value=\"Bosnia and Herzegowina\">Bosnia and Herzegowina</option>");
					out.println("<option value=\"Botswana\">Botswana</option>");
					out.println("<option value=\"Bouvet Island\">Bouvet Island</option>");
					out.println("<option value=\"Brazil\">Brazil</option>");
					out.println("<option value=\"British Indian Ocean Territory\">British Indian Ocean Territory</option>");
					out.println("<option value=\"Brunei Darussalam\">Brunei Darussalam</option>");
					out.println("<option value=\"Bulgaria\">Bulgaria</option>");
					out.println("<option value=\"Burkina Faso\">Burkina Faso</option>");
					out.println("<option value=\"Burundi\">Burundi</option>");
					out.println("<option value=\"Cambodia\">Cambodia</option>");
					out.println("<option value=\"Cameroon\">Cameroon</option>");
					out.println("<option value=\"Canada\">Canada</option>");
					out.println("<option value=\"Cape Verde\">Cape Verde</option>");
					out.println("<option value=\"Cayman Islands\">Cayman Islands</option>");
					out.println("<option value=\"Central African Republic\">Central African Republic</option>");
					out.println("<option value=\"Chad\">Chad</option>");
					out.println("<option value=\"Chile\">Chile</option>");
					out.println("<option value=\"China\">China</option>");
					out.println("<option value=\"Christmas Island\">Christmas Island</option>");
					out.println("<option value=\"Cocos Islands\">Cocos (Keeling) Islands</option>");
					out.println("<option value=\"Colombia\">Colombia</option>");
					out.println("<option value=\"Comoros\">Comoros</option>");
					out.println("<option value=\"Congo\">Congo</option>");
					out.println("<option value=\"Congo\">Congo, the Democratic Republic of the</option>");
					out.println("<option value=\"Cook Islands\">Cook Islands</option>");
					out.println("<option value=\"Costa Rica\">Costa Rica</option>");
					out.println("<option value=\"Cota D'Ivoire\">Cote d'Ivoire</option>");
					out.println("<option value=\"Croatia\">Croatia (Hrvatska)</option>");
					out.println("<option value=\"Cuba\">Cuba</option>");
					out.println("<option value=\"Cyprus\">Cyprus</option>");
					out.println("<option value=\"Czech Republic\">Czech Republic</option>");
					out.println("<option value=\"Denmark\">Denmark</option>");
					out.println("<option value=\"Djibouti\">Djibouti</option>");
					out.println("<option value=\"Dominica\">Dominica</option>");
					out.println("<option value=\"Dominican Republic\">Dominican Republic</option>");
					out.println("<option value=\"East Timor\">East Timor</option>");
					out.println("<option value=\"Ecuador\">Ecuador</option>");
					out.println("<option value=\"Egypt\">Egypt</option>");
					out.println("<option value=\"El Salvador\">El Salvador</option>");
					out.println("<option value=\"Equatorial Guinea\">Equatorial Guinea</option>");
					out.println("<option value=\"Eritrea\">Eritrea</option>");
					out.println("<option value=\"Estonia\">Estonia</option>");
					out.println("<option value=\"Ethiopia\">Ethiopia</option>");
					out.println("<option value=\"Falkland Islands\">Falkland Islands (Malvinas)</option>");
					out.println("<option value=\"Faroe Islands\">Faroe Islands</option>");
					out.println("<option value=\"Fiji\">Fiji</option>");
					out.println("<option value=\"Finland\">Finland</option>");
					out.println("<option value=\"France\">France</option>");
					out.println("<option value=\"France Metropolitan\">France, Metropolitan</option>");
					out.println("<option value=\"French Guiana\">French Guiana</option>");
					out.println("<option value=\"French Polynesia\">French Polynesia</option>");
					out.println("<option value=\"French Southern Territories\">French Southern Territories</option>");
					out.println("<option value=\"Gabon\">Gabon</option>");
					out.println("<option value=\"Gambia\">Gambia</option>");
					out.println("<option value=\"Georgia\">Georgia</option>");
					out.println("<option value=\"Germany\">Germany</option>");
					out.println("<option value=\"Ghana\">Ghana</option>");
					out.println("<option value=\"Gibraltar\">Gibraltar</option>");
					out.println("<option value=\"Greece\">Greece</option>");
					out.println("<option value=\"Greenland\">Greenland</option>");
					out.println("<option value=\"Grenada\">Grenada</option>");
					out.println("<option value=\"Guadeloupe\">Guadeloupe</option>");
					out.println("<option value=\"Guam\">Guam</option>");
					out.println("<option value=\"Guatemala\">Guatemala</option>");
					out.println("<option value=\"Guinea\">Guinea</option>");
					out.println("<option value=\"Guinea-Bissau\">Guinea-Bissau</option>");
					out.println("<option value=\"Guyana\">Guyana</option>");
					out.println("<option value=\"Haiti\">Haiti</option>");
					out.println("<option value=\"Heard and McDonald Islands\">Heard and Mc Donald Islands</option>");
					out.println("<option value=\"Holy See\">Holy See (Vatican City State)</option>");
					out.println("<option value=\"Honduras\">Honduras</option>");
					out.println("<option value=\"Hong Kong\">Hong Kong</option>");
					out.println("<option value=\"Hungary\">Hungary</option>");
					out.println("<option value=\"Iceland\">Iceland</option>");
					out.println("<option value=\"India\">India</option>");
					out.println("<option value=\"Indonesia\">Indonesia</option>");
					out.println("<option value=\"Iran\">Iran (Islamic Republic of)</option>");
					out.println("<option value=\"Iraq\">Iraq</option>");
					out.println("<option value=\"Ireland\">Ireland</option>");
					out.println("<option value=\"Israel\">Israel</option>");
					out.println("<option value=\"Italy\">Italy</option>");
					out.println("<option value=\"Jamaica\">Jamaica</option>");
					out.println("<option value=\"Japan\">Japan</option>");
					out.println("<option value=\"Jordan\">Jordan</option>");
					out.println("<option value=\"Kazakhstan\">Kazakhstan</option>");
					out.println("<option value=\"Kenya\">Kenya</option>");
					out.println("<option value=\"Kiribati\">Kiribati</option>");
					out.println("<option value=\"Democratic People's Republic of Korea\">Korea, Democratic People's Republic of</option>");
					out.println("<option value=\"Korea\">Korea, Republic of</option>");
					out.println("<option value=\"Kuwait\">Kuwait</option>");
					out.println("<option value=\"Kyrgyzstan\">Kyrgyzstan</option>");
					out.println("<option value=\"Lao\">Lao People's Democratic Republic</option>");
					out.println("<option value=\"Latvia\">Latvia</option>");
					out.println("<option value=\"Lebanon\">Lebanon</option>");
					out.println("<option value=\"Lesotho\">Lesotho</option>");
					out.println("<option value=\"Liberia\">Liberia</option>");
					out.println("<option value=\"Libyan Arab Jamahiriya\">Libyan Arab Jamahiriya</option>");
					out.println("<option value=\"Liechtenstein\">Liechtenstein</option>");
					out.println("<option value=\"Lithuania\">Lithuania</option>");
					out.println("<option value=\"Luxembourg\">Luxembourg</option>");
					out.println("<option value=\"Macau\">Macau</option>");
					out.println("<option value=\"Macedonia\">Macedonia, The Former Yugoslav Republic of</option>");
					out.println("<option value=\"Madagasca\">Madagascar</option>");
					out.println("<option value=\"Malawi\">Malawi</option>");
					out.println("<option value=\"Malaysia\">Malaysia</option>");
					out.println("<option value=\"Maldives\">Maldives</option>");
					out.println("<option value=\"Mali\">Mali</option>");
					out.println("<option value=\"Malta\">Malta</option>");
					out.println("<option value=\"Marshall Island\">Marshall Islands</option>");
					out.println("<option value=\"Martinique\">Martinique</option>");
					out.println("<option value=\"Mauritania\">Mauritania</option>");
					out.println("<option value=\"Mauritius\">Mauritius</option>");
					out.println("<option value=\"Mayotte\">Mayotte</option>");
					out.println("<option value=\"Mexico\">Mexico</option>");
					out.println("<option value=\"Micronesia\">Micronesia, Federated States of</option>");
					out.println("<option value=\"Moldova\">Moldova, Republic of</option>");
					out.println("<option value=\"Monaco\">Monaco</option>");
					out.println("<option value=\"Mongolia\">Mongolia</option>");
					out.println("<option value=\"Montserrat\">Montserrat</option>");
					out.println("<option value=\"Morocco\">Morocco</option>");
					out.println("<option value=\"Mozambique\">Mozambique</option>");
					out.println("<option value=\"Myanmar\">Myanmar</option>");
					out.println("<option value=\"Namibia\">Namibia</option>");
					out.println("<option value=\"Nauru\">Nauru</option>");
					out.println("<option value=\"Nepal\">Nepal</option>");
					out.println("<option value=\"Netherlands\">Netherlands</option>");
					out.println("<option value=\"Netherlands Antilles\">Netherlands Antilles</option>");
					out.println("<option value=\"New Caledonia\">New Caledonia</option>");
					out.println("<option value=\"New Zealand\">New Zealand</option>");
					out.println("<option value=\"Nicaragua\">Nicaragua</option>");
					out.println("<option value=\"Niger\">Niger</option>");
					out.println("<option value=\"Nigeria\">Nigeria</option>");
					out.println("<option value=\"Niue\">Niue</option>");
					out.println("<option value=\"Norfolk Island\">Norfolk Island</option>");
					out.println("<option value=\"Northern Mariana Islands\">Northern Mariana Islands</option>");
					out.println("<option value=\"Norway\">Norway</option>");
					out.println("<option value=\"Oman\">Oman</option>");
					out.println("<option value=\"Pakistan\">Pakistan</option>");
					out.println("<option value=\"Palau\">Palau</option>");
					out.println("<option value=\"Panama\">Panama</option>");
					out.println("<option value=\"Papua New Guinea\">Papua New Guinea</option>");
					out.println("<option value=\"Paraguay\">Paraguay</option>");
					out.println("<option value=\"Peru\">Peru</option>");
					out.println("<option value=\"Philippines\">Philippines</option>");
					out.println("<option value=\"Pitcairn\">Pitcairn</option>");
					out.println("<option value=\"Poland\">Poland</option>");
					out.println("<option value=\"Portugal\">Portugal</option>");
					out.println("<option value=\"Puerto Rico\">Puerto Rico</option>");
					out.println("<option value=\"Qatar\">Qatar</option>");
					out.println("<option value=\"Reunion\">Reunion</option>");
					out.println("<option value=\"Romania\">Romania</option>");
					out.println("<option value=\"Russia\">Russian Federation</option>");
					out.println("<option value=\"Rwanda\">Rwanda</option>");
					out.println("<option value=\"Saint Kitts and Nevis\">Saint Kitts and Nevis</option>");
					out.println("<option value=\"Saint LUCIA\">Saint LUCIA</option>");
					out.println("<option value=\"Saint Vincent\">Saint Vincent and the Grenadines</option>");
					out.println("<option value=\"Samoa\">Samoa</option>");
					out.println("<option value=\"San Marino\">San Marino</option>");
					out.println("<option value=\"Sao Tome and Principe\">Sao Tome and Principe</option>");
					out.println("<option value=\"Saudi Arabia\">Saudi Arabia</option>");
					out.println("<option value=\"Senegal\">Senegal</option>");
					out.println("<option value=\"Seychelles\">Seychelles</option>");
					out.println("<option value=\"Sierra\">Sierra Leone</option>");
					out.println("<option value=\"Singapore\">Singapore</option>");
					out.println("<option value=\"Slovakia\">Slovakia (Slovak Republic)</option>");
					out.println("<option value=\"Slovenia\">Slovenia</option>");
					out.println("<option value=\"Solomon Islands\">Solomon Islands</option>");
					out.println("<option value=\"Somalia\">Somalia</option>");
					out.println("<option value=\"South Africa\">South Africa</option>");
					out.println("<option value=\"South Georgia\">South Georgia and the South Sandwich Islands</option>");
					out.println("<option value=\"Span\">Spain</option>");
					out.println("<option value=\"Sri Lanka\">Sri Lanka</option>");
					out.println("<option value=\"St. Helena\">St. Helena</option>");
					out.println("<option value=\"St. Pierre and Miguelon\">St. Pierre and Miquelon</option>");
					out.println("<option value=\"Sudan\">Sudan</option>");
					out.println("<option value=\"Suriname\">Suriname</option>");
					out.println("<option value=\"Svalbard\">Svalbard and Jan Mayen Islands</option>");
					out.println("<option value=\"Swaziland\">Swaziland</option>");
					out.println("<option value=\"Sweden\">Sweden</option>");
					out.println("<option value=\"Switzerland\">Switzerland</option>");
					out.println("<option value=\"Syria\">Syrian Arab Republic</option>");
					out.println("<option value=\"Taiwan\">Taiwan, Province of China</option>");
					out.println("<option value=\"Tajikistan\">Tajikistan</option>");
					out.println("<option value=\"Tanzania\">Tanzania, United Republic of</option>");
					out.println("<option value=\"Thailand\">Thailand</option>");
					out.println("<option value=\"Togo\">Togo</option>");
					out.println("<option value=\"Tokelau\">Tokelau</option>");
					out.println("<option value=\"Tonga\">Tonga</option>");
					out.println("<option value=\"Trinidad and Tobago\">Trinidad and Tobago</option>");
					out.println("<option value=\"Tunisia\">Tunisia</option>");
					out.println("<option value=\"Turkey\">Turkey</option>");
					out.println("<option value=\"Turkmenistan\">Turkmenistan</option>");
					out.println("<option value=\"Turks and Caicos\">Turks and Caicos Islands</option>");
					out.println("<option value=\"Tuvalu\">Tuvalu</option>");
					out.println("<option value=\"Uganda\">Uganda</option>");
					out.println("<option value=\"Ukraine\">Ukraine</option>");
					out.println("<option value=\"United Arab Emirates\">United Arab Emirates</option>");
					out.println("<option value=\"United Kingdom\">United Kingdom</option>");
					out.println("<option value=\"United States\">United States</option>");
					out.println("<option value=\"United States Minor Outlying Islands\">United States Minor Outlying Islands</option>");
					out.println("<option value=\"Uruguay\">Uruguay</option>");
					out.println("<option value=\"Uzbekistan\">Uzbekistan</option>");
					out.println("<option value=\"Vanuatu\">Vanuatu</option>");
					out.println("<option value=\"Venezuela\">Venezuela</option>");
					out.println("<option value=\"Vietnam\">Viet Nam</option>");
					out.println("<option value=\"Virgin Islands (British)\">Virgin Islands (British)</option>");
					out.println("<option value=\"Virgin Islands (U.S)\">Virgin Islands (U.S.)</option>");
					out.println("<option value=\"Wallis and Futana Islands\">Wallis and Futuna Islands</option>");
					out.println("<option value=\"Western Sahara\">Western Sahara</option>");
					out.println("<option value=\"Yemen\">Yemen</option>");
					out.println("<option value=\"Yugoslavia\">Yugoslavia</option>");
					out.println("<option value=\"Zambia\">Zambia</option>");
					out.println("<option value=\"Zimbabwe\">Zimbabwe</option>");
					out.println("</select>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (formQuestion.get(10)) {
					out.println("<div id=\"country-select\" class=\"form-group col-xs-12\">");
					out.println("<label for=\"country-field\" class=\"col-md-4 control-label\">ルーツをもつ国(三つ目)</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-globe\"></i></span>");
					out.println("<select class=\"form-control\" name=\"country-field-3\">");
					out.println("<option label=\"非選択可\"selected></option>");
					out.println("<option value=\"Afghanistan\">Afghanistan</option>");
					out.println("<option value=\"Albania\">Albania</option>");
					out.println("<option value=\"Algeria\">Algeria</option>");
					out.println("<option value=\"American Samoa\">American Samoa</option>");
					out.println("<option value=\"Andorra\">Andorra</option>");
					out.println("<option value=\"Angola\">Angola</option>");
					out.println("<option value=\"Anguilla\">Anguilla</option>");
					out.println("<option value=\"Antartica\">Antarctica</option>");
					out.println("<option value=\"Antigua and Barbuda\">Antigua and Barbuda</option>");
					out.println("<option value=\"Argentina\">Argentina</option>");
					out.println("<option value=\"Armenia\">Armenia</option>");
					out.println("<option value=\"Aruba\">Aruba</option>");
					out.println("<option value=\"Australia\">Australia</option>");
					out.println("<option value=\"Austria\">Austria</option>");
					out.println("<option value=\"Azerbaijan\">Azerbaijan</option>");
					out.println("<option value=\"Bahamas\">Bahamas</option>");
					out.println("<option value=\"Bahrain\">Bahrain</option>");
					out.println("<option value=\"Bangladesh\">Bangladesh</option>");
					out.println("<option value=\"Barbados\">Barbados</option>");
					out.println("<option value=\"Belarus\">Belarus</option>");
					out.println("<option value=\"Belgium\">Belgium</option>");
					out.println("<option value=\"Belize\">Belize</option>");
					out.println("<option value=\"Benin\">Benin</option>");
					out.println("<option value=\"Bermuda\">Bermuda</option>");
					out.println("<option value=\"Bhutan\">Bhutan</option>");
					out.println("<option value=\"Bolivia\">Bolivia</option>");
					out.println("<option value=\"Bosnia and Herzegowina\">Bosnia and Herzegowina</option>");
					out.println("<option value=\"Botswana\">Botswana</option>");
					out.println("<option value=\"Bouvet Island\">Bouvet Island</option>");
					out.println("<option value=\"Brazil\">Brazil</option>");
					out.println("<option value=\"British Indian Ocean Territory\">British Indian Ocean Territory</option>");
					out.println("<option value=\"Brunei Darussalam\">Brunei Darussalam</option>");
					out.println("<option value=\"Bulgaria\">Bulgaria</option>");
					out.println("<option value=\"Burkina Faso\">Burkina Faso</option>");
					out.println("<option value=\"Burundi\">Burundi</option>");
					out.println("<option value=\"Cambodia\">Cambodia</option>");
					out.println("<option value=\"Cameroon\">Cameroon</option>");
					out.println("<option value=\"Canada\">Canada</option>");
					out.println("<option value=\"Cape Verde\">Cape Verde</option>");
					out.println("<option value=\"Cayman Islands\">Cayman Islands</option>");
					out.println("<option value=\"Central African Republic\">Central African Republic</option>");
					out.println("<option value=\"Chad\">Chad</option>");
					out.println("<option value=\"Chile\">Chile</option>");
					out.println("<option value=\"China\">China</option>");
					out.println("<option value=\"Christmas Island\">Christmas Island</option>");
					out.println("<option value=\"Cocos Islands\">Cocos (Keeling) Islands</option>");
					out.println("<option value=\"Colombia\">Colombia</option>");
					out.println("<option value=\"Comoros\">Comoros</option>");
					out.println("<option value=\"Congo\">Congo</option>");
					out.println("<option value=\"Congo\">Congo, the Democratic Republic of the</option>");
					out.println("<option value=\"Cook Islands\">Cook Islands</option>");
					out.println("<option value=\"Costa Rica\">Costa Rica</option>");
					out.println("<option value=\"Cota D'Ivoire\">Cote d'Ivoire</option>");
					out.println("<option value=\"Croatia\">Croatia (Hrvatska)</option>");
					out.println("<option value=\"Cuba\">Cuba</option>");
					out.println("<option value=\"Cyprus\">Cyprus</option>");
					out.println("<option value=\"Czech Republic\">Czech Republic</option>");
					out.println("<option value=\"Denmark\">Denmark</option>");
					out.println("<option value=\"Djibouti\">Djibouti</option>");
					out.println("<option value=\"Dominica\">Dominica</option>");
					out.println("<option value=\"Dominican Republic\">Dominican Republic</option>");
					out.println("<option value=\"East Timor\">East Timor</option>");
					out.println("<option value=\"Ecuador\">Ecuador</option>");
					out.println("<option value=\"Egypt\">Egypt</option>");
					out.println("<option value=\"El Salvador\">El Salvador</option>");
					out.println("<option value=\"Equatorial Guinea\">Equatorial Guinea</option>");
					out.println("<option value=\"Eritrea\">Eritrea</option>");
					out.println("<option value=\"Estonia\">Estonia</option>");
					out.println("<option value=\"Ethiopia\">Ethiopia</option>");
					out.println("<option value=\"Falkland Islands\">Falkland Islands (Malvinas)</option>");
					out.println("<option value=\"Faroe Islands\">Faroe Islands</option>");
					out.println("<option value=\"Fiji\">Fiji</option>");
					out.println("<option value=\"Finland\">Finland</option>");
					out.println("<option value=\"France\">France</option>");
					out.println("<option value=\"France Metropolitan\">France, Metropolitan</option>");
					out.println("<option value=\"French Guiana\">French Guiana</option>");
					out.println("<option value=\"French Polynesia\">French Polynesia</option>");
					out.println("<option value=\"French Southern Territories\">French Southern Territories</option>");
					out.println("<option value=\"Gabon\">Gabon</option>");
					out.println("<option value=\"Gambia\">Gambia</option>");
					out.println("<option value=\"Georgia\">Georgia</option>");
					out.println("<option value=\"Germany\">Germany</option>");
					out.println("<option value=\"Ghana\">Ghana</option>");
					out.println("<option value=\"Gibraltar\">Gibraltar</option>");
					out.println("<option value=\"Greece\">Greece</option>");
					out.println("<option value=\"Greenland\">Greenland</option>");
					out.println("<option value=\"Grenada\">Grenada</option>");
					out.println("<option value=\"Guadeloupe\">Guadeloupe</option>");
					out.println("<option value=\"Guam\">Guam</option>");
					out.println("<option value=\"Guatemala\">Guatemala</option>");
					out.println("<option value=\"Guinea\">Guinea</option>");
					out.println("<option value=\"Guinea-Bissau\">Guinea-Bissau</option>");
					out.println("<option value=\"Guyana\">Guyana</option>");
					out.println("<option value=\"Haiti\">Haiti</option>");
					out.println("<option value=\"Heard and McDonald Islands\">Heard and Mc Donald Islands</option>");
					out.println("<option value=\"Holy See\">Holy See (Vatican City State)</option>");
					out.println("<option value=\"Honduras\">Honduras</option>");
					out.println("<option value=\"Hong Kong\">Hong Kong</option>");
					out.println("<option value=\"Hungary\">Hungary</option>");
					out.println("<option value=\"Iceland\">Iceland</option>");
					out.println("<option value=\"India\">India</option>");
					out.println("<option value=\"Indonesia\">Indonesia</option>");
					out.println("<option value=\"Iran\">Iran (Islamic Republic of)</option>");
					out.println("<option value=\"Iraq\">Iraq</option>");
					out.println("<option value=\"Ireland\">Ireland</option>");
					out.println("<option value=\"Israel\">Israel</option>");
					out.println("<option value=\"Italy\">Italy</option>");
					out.println("<option value=\"Jamaica\">Jamaica</option>");
					out.println("<option value=\"Japan\">Japan</option>");
					out.println("<option value=\"Jordan\">Jordan</option>");
					out.println("<option value=\"Kazakhstan\">Kazakhstan</option>");
					out.println("<option value=\"Kenya\">Kenya</option>");
					out.println("<option value=\"Kiribati\">Kiribati</option>");
					out.println("<option value=\"Democratic People's Republic of Korea\">Korea, Democratic People's Republic of</option>");
					out.println("<option value=\"Korea\">Korea, Republic of</option>");
					out.println("<option value=\"Kuwait\">Kuwait</option>");
					out.println("<option value=\"Kyrgyzstan\">Kyrgyzstan</option>");
					out.println("<option value=\"Lao\">Lao People's Democratic Republic</option>");
					out.println("<option value=\"Latvia\">Latvia</option>");
					out.println("<option value=\"Lebanon\">Lebanon</option>");
					out.println("<option value=\"Lesotho\">Lesotho</option>");
					out.println("<option value=\"Liberia\">Liberia</option>");
					out.println("<option value=\"Libyan Arab Jamahiriya\">Libyan Arab Jamahiriya</option>");
					out.println("<option value=\"Liechtenstein\">Liechtenstein</option>");
					out.println("<option value=\"Lithuania\">Lithuania</option>");
					out.println("<option value=\"Luxembourg\">Luxembourg</option>");
					out.println("<option value=\"Macau\">Macau</option>");
					out.println("<option value=\"Macedonia\">Macedonia, The Former Yugoslav Republic of</option>");
					out.println("<option value=\"Madagasca\">Madagascar</option>");
					out.println("<option value=\"Malawi\">Malawi</option>");
					out.println("<option value=\"Malaysia\">Malaysia</option>");
					out.println("<option value=\"Maldives\">Maldives</option>");
					out.println("<option value=\"Mali\">Mali</option>");
					out.println("<option value=\"Malta\">Malta</option>");
					out.println("<option value=\"Marshall Island\">Marshall Islands</option>");
					out.println("<option value=\"Martinique\">Martinique</option>");
					out.println("<option value=\"Mauritania\">Mauritania</option>");
					out.println("<option value=\"Mauritius\">Mauritius</option>");
					out.println("<option value=\"Mayotte\">Mayotte</option>");
					out.println("<option value=\"Mexico\">Mexico</option>");
					out.println("<option value=\"Micronesia\">Micronesia, Federated States of</option>");
					out.println("<option value=\"Moldova\">Moldova, Republic of</option>");
					out.println("<option value=\"Monaco\">Monaco</option>");
					out.println("<option value=\"Mongolia\">Mongolia</option>");
					out.println("<option value=\"Montserrat\">Montserrat</option>");
					out.println("<option value=\"Morocco\">Morocco</option>");
					out.println("<option value=\"Mozambique\">Mozambique</option>");
					out.println("<option value=\"Myanmar\">Myanmar</option>");
					out.println("<option value=\"Namibia\">Namibia</option>");
					out.println("<option value=\"Nauru\">Nauru</option>");
					out.println("<option value=\"Nepal\">Nepal</option>");
					out.println("<option value=\"Netherlands\">Netherlands</option>");
					out.println("<option value=\"Netherlands Antilles\">Netherlands Antilles</option>");
					out.println("<option value=\"New Caledonia\">New Caledonia</option>");
					out.println("<option value=\"New Zealand\">New Zealand</option>");
					out.println("<option value=\"Nicaragua\">Nicaragua</option>");
					out.println("<option value=\"Niger\">Niger</option>");
					out.println("<option value=\"Nigeria\">Nigeria</option>");
					out.println("<option value=\"Niue\">Niue</option>");
					out.println("<option value=\"Norfolk Island\">Norfolk Island</option>");
					out.println("<option value=\"Northern Mariana Islands\">Northern Mariana Islands</option>");
					out.println("<option value=\"Norway\">Norway</option>");
					out.println("<option value=\"Oman\">Oman</option>");
					out.println("<option value=\"Pakistan\">Pakistan</option>");
					out.println("<option value=\"Palau\">Palau</option>");
					out.println("<option value=\"Panama\">Panama</option>");
					out.println("<option value=\"Papua New Guinea\">Papua New Guinea</option>");
					out.println("<option value=\"Paraguay\">Paraguay</option>");
					out.println("<option value=\"Peru\">Peru</option>");
					out.println("<option value=\"Philippines\">Philippines</option>");
					out.println("<option value=\"Pitcairn\">Pitcairn</option>");
					out.println("<option value=\"Poland\">Poland</option>");
					out.println("<option value=\"Portugal\">Portugal</option>");
					out.println("<option value=\"Puerto Rico\">Puerto Rico</option>");
					out.println("<option value=\"Qatar\">Qatar</option>");
					out.println("<option value=\"Reunion\">Reunion</option>");
					out.println("<option value=\"Romania\">Romania</option>");
					out.println("<option value=\"Russia\">Russian Federation</option>");
					out.println("<option value=\"Rwanda\">Rwanda</option>");
					out.println("<option value=\"Saint Kitts and Nevis\">Saint Kitts and Nevis</option>");
					out.println("<option value=\"Saint LUCIA\">Saint LUCIA</option>");
					out.println("<option value=\"Saint Vincent\">Saint Vincent and the Grenadines</option>");
					out.println("<option value=\"Samoa\">Samoa</option>");
					out.println("<option value=\"San Marino\">San Marino</option>");
					out.println("<option value=\"Sao Tome and Principe\">Sao Tome and Principe</option>");
					out.println("<option value=\"Saudi Arabia\">Saudi Arabia</option>");
					out.println("<option value=\"Senegal\">Senegal</option>");
					out.println("<option value=\"Seychelles\">Seychelles</option>");
					out.println("<option value=\"Sierra\">Sierra Leone</option>");
					out.println("<option value=\"Singapore\">Singapore</option>");
					out.println("<option value=\"Slovakia\">Slovakia (Slovak Republic)</option>");
					out.println("<option value=\"Slovenia\">Slovenia</option>");
					out.println("<option value=\"Solomon Islands\">Solomon Islands</option>");
					out.println("<option value=\"Somalia\">Somalia</option>");
					out.println("<option value=\"South Africa\">South Africa</option>");
					out.println("<option value=\"South Georgia\">South Georgia and the South Sandwich Islands</option>");
					out.println("<option value=\"Span\">Spain</option>");
					out.println("<option value=\"Sri Lanka\">Sri Lanka</option>");
					out.println("<option value=\"St. Helena\">St. Helena</option>");
					out.println("<option value=\"St. Pierre and Miguelon\">St. Pierre and Miquelon</option>");
					out.println("<option value=\"Sudan\">Sudan</option>");
					out.println("<option value=\"Suriname\">Suriname</option>");
					out.println("<option value=\"Svalbard\">Svalbard and Jan Mayen Islands</option>");
					out.println("<option value=\"Swaziland\">Swaziland</option>");
					out.println("<option value=\"Sweden\">Sweden</option>");
					out.println("<option value=\"Switzerland\">Switzerland</option>");
					out.println("<option value=\"Syria\">Syrian Arab Republic</option>");
					out.println("<option value=\"Taiwan\">Taiwan, Province of China</option>");
					out.println("<option value=\"Tajikistan\">Tajikistan</option>");
					out.println("<option value=\"Tanzania\">Tanzania, United Republic of</option>");
					out.println("<option value=\"Thailand\">Thailand</option>");
					out.println("<option value=\"Togo\">Togo</option>");
					out.println("<option value=\"Tokelau\">Tokelau</option>");
					out.println("<option value=\"Tonga\">Tonga</option>");
					out.println("<option value=\"Trinidad and Tobago\">Trinidad and Tobago</option>");
					out.println("<option value=\"Tunisia\">Tunisia</option>");
					out.println("<option value=\"Turkey\">Turkey</option>");
					out.println("<option value=\"Turkmenistan\">Turkmenistan</option>");
					out.println("<option value=\"Turks and Caicos\">Turks and Caicos Islands</option>");
					out.println("<option value=\"Tuvalu\">Tuvalu</option>");
					out.println("<option value=\"Uganda\">Uganda</option>");
					out.println("<option value=\"Ukraine\">Ukraine</option>");
					out.println("<option value=\"United Arab Emirates\">United Arab Emirates</option>");
					out.println("<option value=\"United Kingdom\">United Kingdom</option>");
					out.println("<option value=\"United States\">United States</option>");
					out.println("<option value=\"United States Minor Outlying Islands\">United States Minor Outlying Islands</option>");
					out.println("<option value=\"Uruguay\">Uruguay</option>");
					out.println("<option value=\"Uzbekistan\">Uzbekistan</option>");
					out.println("<option value=\"Vanuatu\">Vanuatu</option>");
					out.println("<option value=\"Venezuela\">Venezuela</option>");
					out.println("<option value=\"Vietnam\">Viet Nam</option>");
					out.println("<option value=\"Virgin Islands (British)\">Virgin Islands (British)</option>");
					out.println("<option value=\"Virgin Islands (U.S)\">Virgin Islands (U.S.)</option>");
					out.println("<option value=\"Wallis and Futana Islands\">Wallis and Futuna Islands</option>");
					out.println("<option value=\"Western Sahara\">Western Sahara</option>");
					out.println("<option value=\"Yemen\">Yemen</option>");
					out.println("<option value=\"Yugoslavia\">Yugoslavia</option>");
					out.println("<option value=\"Zambia\">Zambia</option>");
					out.println("<option value=\"Zimbabwe\">Zimbabwe</option>");
					out.println("</select>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (formQuestion.get(11)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"zip-field\" class=\"col-md-4 control-label\">郵便番号(ハイフンなし)</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-map-marker\"></i>");
					out.println("</span>");
					out.println(
							"<input pattern=\"\\d{7}\" class=\"form-control\" id=\"zip-field\" name=\"zip-field\" placeholder=\"\" onkeyup=\"AjaxZip3.zip2addr(this,'','pref-field','address-field');\" required>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"pref-field\" class=\"col-md-4 control-label\">都道府県</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-map-marker\"></i>");
					out.println("</span>");
					out.println(
							"<input type=\"text\" class=\"form-control\" id=\"pref-field\" name=\"pref-field\" placeholder=\"\" required>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"address-field\" class=\"col-md-4 control-label\">住所</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-home\"></i>");
					out.println("</span>");
					out.println(
							"<input type=\"text\" class=\"form-control\" id=\"address-field\" name=\"address-field\" placeholder=\"\" required>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (formQuestion.get(12)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"allergy-field\" class=\"col-md-4 control-label\">アレルギー</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-ban-circle\"></i>");
					out.println("</span>");
					out.println(
							"<input type=\"text\" class=\"form-control\" id=\"allergy-field\" name=\"allergy-field\" placeholder=\"無記入可\">");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");
				}

				if (formQuestion.get(13)) {
					out.println("<div id=\"trigger-select\" class=\"form-group col-xs-12\">");
					out.println("<label for=\"trigger-field\" class=\"col-md-4 control-label\">イベントを知ったきっかけ</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-question-sign\"></i>");
					out.println("</span>");
					out.println(
							"<select class=\"form-control\" name=\"trigger-field\" onchange=\"changeSubject(event)\" required>");
					out.println("<option label=\" \" selected></option>");
					out.println("<option value=\"友人または知り合い\">友人または知り合い</option>");
					out.println("<option value=\"先生や講師の方\">先生や講師の方</option>");
					out.println("<option value=\"MYouthサイトから\">MYouthサイトから</option>");
					out.println("<option value=\"SNS\">SNS</option>");
					out.println("<option value=\"パンフレット\">パンフレット</option>");
					out.println("<option value=\"実行委員\">実行委員</option>");
					out.println("<option value=\"other-trigger\">その他</option>");
					out.println("</select>");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");

					out.println("<div id=\"hidden-other-trigger\" class=\"form-group col-xs-12 hidden\">");
					out.println("<label for=\"other-trigger-field\" class=\"col-md-4 control-label\">その他とは</label>");
					out.println("<div class=\"col-md-4 inputGroupContainer\">");
					out.println("<div class=\"input-group\">");
					out.println("<span class=\"input-group-addon\">");
					out.println("<i class=\"glyphicon glyphicon-question-sign\"></i>");
					out.println("</span>");
					out.println(
							"<input type=\"text\" class=\"form-control\" id=\"other-trigger-field\" name=\"other-trigger-field\" placeholder=\"\" />");
					out.println("</div>");
					out.println("</div>");
					out.println("</div>");

				}

				if (formQuestion.get(14)) {
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"permission-field\" class=\"col-md-4 control-label\">ご両親の同意はもらってますか？");
					out.println("</label>");
					out.println("<div class=\"radio\">");
					out.println("<label><input name=\"permission-field\" type=\"checkbox\" value=\"はい\" required> はい</label>");
					out.println("</div>");
					out.println("</div>");
				}
				
				if(formQuestion.get(15)){
					out.println("<div class=\"form-group col-xs-12\">");
					out.println("<label for=\"permission-field\" class=\"col-md-4 control-label\">上記記入したことは間違いないですか？</label>");
					out.println("<div class=\"radio\">");
					out.println("<label> <input name=\"permission-field\" type=\"checkbox\" value=\"はい\" required> はい </label>");
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

	<script src="/resources/js/form.js"></script>
	<script src="/resources/js/animate.js"></script>

</body>
</html>
