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
	ArrayList<Boolean> formQuestion = db.formQuestion(event);
	db.close();
	Boolean name = formQuestion.get(0);
	Boolean fname = formQuestion.get(1);
	Boolean gender = formQuestion.get(2);
	Boolean email = formQuestion.get(3);
	Boolean phone = formQuestion.get(4);
	Boolean birthdate = formQuestion.get(5);
	Boolean career = formQuestion.get(6);
	Boolean company = formQuestion.get(7);
	Boolean country = formQuestion.get(8);
	Boolean country2 = formQuestion.get(9);
	Boolean country3 = formQuestion.get(10);
	Boolean address = formQuestion.get(11);
	Boolean allergy = formQuestion.get(12);
	Boolean way = formQuestion.get(13);
	Boolean confirmation = formQuestion.get(14);
	Boolean parent_permission = formQuestion.get(15);
%>
<!DOCTYPE HTML>
<!--
	Alpha by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
	<head>
		<title>参加申し込み</title>
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
						<h2>Application Form</h2>
					</header>
					<section style="background-color: #E8F9DF;"
						class="box <%out.print(success); session.setAttribute("success", "hidden");%>"
						id="success">
						<p>正常に申し込まれました <i class="fa fa-check faa-tada animated"></i></p>
					</section>
					<div class="box">
					<div class="row">
					
						<form id="form" method="post" action="/events/<%out.print(event);%>/form/insertForm">
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
									out.println("<input type=\"text\" id=\"fname-field\" name=\"fname-field\" placeholder=\"\" required>");
									out.println("</div>");
								}

								if (gender) {
									out.println("<div class=\"col-2 col-4-mobilep\">");
									out.println("<label for=\"fname-field\">性別</label>");
									out.println("<select name=\"sex-field\" required>");
									out.println("<option value=\"男性\" selected>男性</option>");
									out.println("<option value=\"女性\">女性</option>");
									out.println("</select>");
									out.println("</div>");
									out.println("<div class=\"col-10 col-8-mobilep\">");
									out.println("</div>");
								}

								if (email) {
									out.println("<div class=\"col-6 col-12-mobilep\">");
									out.println("<label for=\"fname-field\">メールアドレス</label>");
									out.println("<input type=\"email\" id=\"email-field\" name=\"email-field\" placeholder=\"\" required>");
									out.println("</div>");
									out.println("<div class=\"col-6\">");
									out.println("</div>");
								}

								if (phone) {
									out.println("<div class=\"col-6 col-12-mobilep\">");
									out.println("<label for=\"fname-field\">電話番号</label>");
									out.println(
											"<input type=\"number\" pattern=\"\\d{10,11}\" id=\"phone-field\" name=\"phone-field\" placeholder=\"ハイフンなしで入力\" required>");
									out.println("</div>");
								}

								if (birthdate) {
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
									out.println("<div class=\"col-4 col-4-mobilep\">");
									out.println("<label for=\"birth-year\">生年月日</label>");
									out.println("<select name=\"birth-year\" id=\"years\" required>");
									out.println("<option label=\" \" selected></option>");
									out.println("</select>");
									out.println("</div>");
									out.println("<div class=\"col-4 col-4-mobilep\">");
									out.println("<label for=\"birth-month\">月</label>");
									out.println("<select name=\"birth-month\" id=\"months\" required>");
									out.println("<option label=\" \" selected></option>");
									out.println("</select>");
									out.println("</div>");
									out.println("<div class=\"col-4 col-4-mobilep\">");
									out.println("<label for=\"birth-day\">日</label>");
									out.println("<select name=\"birth-day\" id=\"days\" required> </select>");
									out.println("</div>");
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\"><br />");
								}

								if (career) {
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
									out.println("<div class=\"col-6 col-12-mobilep\">");
									out.println("<label for=\"career-field\">学年または職種</label>");
									out.println("<select name=\"career-field\" onchange=\"changeSubject(event)\" required>");
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
									out.println("<option value=\"公務員\">公務員</option>");
									out.println("<option value=\"経営者・役員\">経営者・役員</option>");
									out.println("<option value=\"会社員\">会社員</option>");
									out.println("<option value=\"自営業\">自営業</option>");
									out.println("<option value=\"自由業\">自由業</option>");
									out.println("<option value=\"専業主婦\">専業主婦</option>");
									out.println("<option value=\"パート・アルバイト\">パート・アルバイト</option>");
									out.println("<option value=\"other-career\">その他</option>");
									out.println("</select>");
									out.println("</div>");

									out.println("<div id=\"hidden-other-career\" class=\"col-6  col-12-mobilep hidden\">");
									out.println("<label for=\"other-career-field\" class=\"col-md-4 control-label\">その他とは</label>");
									out.println(
											"<input type=\"text\" id=\"other-career-field\" name=\"other-career-field\" placeholder=\"\" />");
									out.println("</div>");
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
								}

								if (company) {
									out.println("<div class=\"col-6 col-12-mobilep\">");
									out.println("<label for=\"company-field\">学校名または会社名</label>");
									out.println(
											"<input type=\"text\" class=\"form-control\" id=\"company-field\" name=\"company-field\" placeholder=\"\" required>");
									out.println("</div>");
								}

								if (country) {
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
									out.println("<div class=\"col-6 col-12-mobilep\">");
									out.println("<label for=\"country-field\">ルーツをもつ国</label>");
									out.println("<select name=\"country-field\" required>");
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
									out.println(
											"<option value=\"Democratic People's Republic of Korea\">Korea, Democratic People's Republic of</option>");
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
									out.println(
											"<option value=\"United States Minor Outlying Islands\">United States Minor Outlying Islands</option>");
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
									out.println("<div class=\"row gtr-50 gtr-uniform\"><br />");
								}

								if (country2) {
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
									out.println("<div class=\"col-6 col-12-mobilep\">");
									out.println("<label for=\"country-field-2\">ルーツをもつ国(2)</label>");
									out.println("<select name=\"country-field-2\">");
									out.println("<option label=\"非選択可\" selected></option>");
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
									out.println(
											"<option value=\"Democratic People's Republic of Korea\">Korea, Democratic People's Republic of</option>");
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
									out.println(
											"<option value=\"United States Minor Outlying Islands\">United States Minor Outlying Islands</option>");
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
									out.println("<div class=\"row gtr-50 gtr-uniform\"><br />");
								}

								if (country3) {
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
									out.println("<div class=\"col-6 col-12-mobilep\">");
									out.println("<label for=\"country-field-3\">ルーツをもつ国(3)</label>");
									out.println("<select name=\"country-field-3\">");
									out.println("<option label=\"非選択可\" selected></option>");
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
									out.println(
											"<option value=\"Democratic People's Republic of Korea\">Korea, Democratic People's Republic of</option>");
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
									out.println(
											"<option value=\"United States Minor Outlying Islands\">United States Minor Outlying Islands</option>");
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
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
								}

								if (address) {
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
									out.println("<div class=\"col-3 col-3-mobilep\">");
									out.println("<label for=\"zip1\">郵便番号</label>");
									out.println("<input type=\"text\" pattern=\"\\d{3}\" class=\"form-control\" id=\"zip1\" name=\"zip1\" placeholder=\"\" required>");
									out.println("</div>");
									out.println("<div class=\"col-3 col-3-mobilep\">");
									out.println("<label for=\"zip2\">4桁</label>");
									out.println("<input type=\"text\" pattern=\"\\d{4}\" class=\"form-control\" id=\"zip2\" name=\"zip2\" placeholder=\"\" onkeyup=\"AjaxZip3.zip2addr('zip1','zip2','pref-field','address-field');\" required>");
									out.println("</div>");
									out.println("<div class=\"col-6 col-6-mobilep\">");
									out.println("<label for=\"pref-field\" class=\"col-md-4 control-label\">都道府県</label>");
									out.println("<select id=\"pref-field\" name=\"pref-field\" required>");
									out.println("<option label=\"\" selected>選択してください</option>");
									out.println("<option value=\"北海道\">北海道</option>");
									out.println("<option value=\"青森県\">青森県</option>");
									out.println("<option value=\"岩手県\">岩手県</option>");
									out.println("<option value=\"宮城県\">宮城県</option>");
									out.println("<option value=\"秋田県\">秋田県</option>");
									out.println("<option value=\"山形県\">山形県</option>");
									out.println("<option value=\"茨城県\">茨城県</option>");
									out.println("<option value=\"栃木県\">栃木県</option>");
									out.println("<option value=\"群馬県\">群馬県</option>");
									out.println("<option value=\"埼玉県\">埼玉県</option>");
									out.println("<option value=\"千葉県\">千葉県</option>");
									out.println("<option value=\"東京都\" selected>東京都</option>");
									out.println("<option value=\"神奈川県\">神奈川県</option>");
									out.println("<option value=\"新潟県\">新潟県</option>");
									out.println("<option value=\"富山県\">富山県</option>");
									out.println("<option value=\"石川県\">石川県</option>");
									out.println("<option value=\"福井県\">福井県</option>");
									out.println("<option value=\"山梨県\">山梨県</option>");
									out.println("<option value=\"長野県\">長野県</option>");
									out.println("<option value=\"岐阜県\">岐阜県</option>");
									out.println("<option value=\"静岡県\">静岡県</option>");
									out.println("<option value=\"愛知県\">愛知県</option>");
									out.println("<option value=\"三重県\">三重県</option>");
									out.println("<option value=\"滋賀県\">滋賀県</option>");
									out.println("<option value=\"京都府\">京都府</option>");
									out.println("<option value=\"大阪府\">大阪府</option>");
									out.println("<option value=\"兵庫県\">兵庫県</option>");
									out.println("<option value=\"奈良県\">奈良県</option>");
									out.println("<option value=\"和歌山県\">和歌山県</option>");
									out.println("<option value=\"鳥取県\">鳥取県</option>");
									out.println("<option value=\"島根県\">島根県</option>");
									out.println("<option value=\"岡山県\">岡山県</option>");
									out.println("<option value=\"広島県\">広島県</option>");
									out.println("<option value=\"山口県\">山口県</option>");
									out.println("<option value=\"徳島県\">徳島県</option>");
									out.println("<option value=\"香川県\">香川県</option>");
									out.println("<option value=\"愛媛県\">愛媛県</option>");
									out.println("<option value=\"高知県\">高知県</option>");
									out.println("<option value=\"福岡県\">福岡県</option>");
									out.println("<option value=\"佐賀県\">佐賀県</option>");
									out.println("<option value=\"長崎県\">長崎県</option>");
									out.println("<option value=\"熊本県\">熊本県</option>");
									out.println("<option value=\"大分県\">大分県</option>");
									out.println("<option value=\"宮崎県\">宮崎県</option>");
									out.println("<option value=\"鹿児島県\">鹿児島県</option>");
									out.println("<option value=\"沖縄県\">沖縄県</option>");
									out.println("</select>");
									out.println("</div>");
									out.println("<div class=\"col-12 col-12-mobilep\">");
									out.println("<label for=\"address-field\" class=\"col-md-4 control-label\">住所</label>");
									out.println("<input type=\"text\" class=\"form-control\" id=\"address-field\" name=\"address-field\" placeholder=\"\" required>");
									out.println("</div>");
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
								}

								if (allergy) {
									out.println("<br /></div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
									out.println("<div class=\"col-6 col-12-mobilep\">");
									out.println("<label for=\"allergy-field\">アレルギー</label>");
									out.println(
											"<input type=\"text\" class=\"form-control\" id=\"allergy-field\" name=\"allergy-field\" placeholder=\"\">");
									out.println("</div>");
									out.println("<div class=\"col-6 col-2-mobilep\">");
									out.println("</div>");
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
								}

								if (way) {
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
									out.println("<div class=\"col-6 col-12-mobilep\">");
									out.println("<label for=\"trigger-field\">イベントを知ったきっかけ</label>");
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

									out.println("<div id=\"hidden-other-trigger\" class=\"col-6 col-12-mobilep hidden\">");
									out.println("<label for=\"other-trigger-field\">その他とは</label>");
									out.println("<input type=\"text\" id=\"other-trigger-field\" name=\"other-trigger-field\" placeholder=\"\" />");
									out.println("</div>");
									out.println("</div>");
									out.println("<div class=\"row gtr-50 gtr-uniform\">");
								}

								if (confirmation) {
									out.println("<div class=\"col-6 col-12-narrower\">");
									out.println("<label for=\"confirmation\"><br />上記記入したことは間違いないですか？</label>");
									out.println("<input type=\"checkbox\" name=\"confirmation\" id=\"confirmation\">");
									out.println("<label for=\"confirmation\">はい</label>");
									out.println("</div>");
								}

								if (parent_permission) {
									out.println("<div class=\"col-6 col-12-narrower\">");
									out.println("<label for=\"parent-permission\"><br />ご両親の同意はもらってますか？</label>");
									out.println("<input type=\"checkbox\" name=\"parent-permission\" id=\"parent-permission\">");
									out.println("<label for=\"parent-permission\">はい</label>");
									out.println("</div>");
								}
							%>
							<div class="col-12">
								<ul class="actions" id="swap">
									<li><input id="btn" type="submit" value="登録 "/></li>
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
			<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>
		<!--<script src="/resources/alpha/js/main.js"></script>-->
		<script type="text/javascript">

		$(function() {

		    //populate our years select box
		    for ( i = new Date().getFullYear()-70; i <= new Date().getFullYear()-10; i++){
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