package jp.myouth.mail;

import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;

import jp.myouth.db.Events;

public class Templates {

	final String STYLE = "<style>"+
			".myButton {" + 
			"	-moz-box-shadow:inset 0px 1px 0px 0px #ffffff;" + 
			"	-webkit-box-shadow:inset 0px 1px 0px 0px #ffffff;" + 
			"	box-shadow:inset 0px 1px 0px 0px #ffffff;" + 
			"	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #f9f9f9), color-stop(1, #e9e9e9));" + 
			"	background:-moz-linear-gradient(top, #f9f9f9 5%, #e9e9e9 100%);" + 
			"	background:-webkit-linear-gradient(top, #f9f9f9 5%, #e9e9e9 100%);" + 
			"	background:-o-linear-gradient(top, #f9f9f9 5%, #e9e9e9 100%);" + 
			"	background:-ms-linear-gradient(top, #f9f9f9 5%, #e9e9e9 100%);" + 
			"	background:linear-gradient(to bottom, #f9f9f9 5%, #e9e9e9 100%);" + 
			"	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#f9f9f9', endColorstr='#e9e9e9',GradientType=0);" + 
			"	background-color:#f9f9f9;" + 
			"	-moz-border-radius:6px;" + 
			"	-webkit-border-radius:6px;" + 
			"	border-radius:6px;" + 
			"	border:1px solid #dcdcdc;" + 
			"	display:inline-block;" + 
			"	cursor:pointer;" + 
			"	color:#666666;" + 
			"	font-family:Arial;" + 
			"	font-size:15px;" + 
			"	font-weight:bold;" + 
			"	padding:6px 24px;" + 
			"	text-decoration:none;" + 
			"	text-shadow:0px 1px 0px #ffffff;" + 
			"}" + 
			".myButton:hover {" + 
			"	background:-webkit-gradient(linear, left top, left bottom, color-stop(0.05, #e9e9e9), color-stop(1, #f9f9f9));" + 
			"	background:-moz-linear-gradient(top, #e9e9e9 5%, #f9f9f9 100%);" + 
			"	background:-webkit-linear-gradient(top, #e9e9e9 5%, #f9f9f9 100%);" + 
			"	background:-o-linear-gradient(top, #e9e9e9 5%, #f9f9f9 100%);" + 
			"	background:-ms-linear-gradient(top, #e9e9e9 5%, #f9f9f9 100%);" + 
			"	background:linear-gradient(to bottom, #e9e9e9 5%, #f9f9f9 100%);" + 
			"	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#e9e9e9', endColorstr='#f9f9f9',GradientType=0);" + 
			"	background-color:#e9e9e9;" + 
			"}" + 
			".myButton:active {" + 
			"	position:relative;" + 
			"	top:1px;" + 
			"}" + 
			"</style>";
	
	public Boolean accountVerificationMail(String name, String email, String userId) {
		try {
			String FROM = "admin@myouth.jp";

			String FROMNAME = "myouth";

			String SUBJECT = "アカウント承認";

			String HTMLBODY = String.join(System.getProperty("line.separator"),
					STYLE,
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "<tr>",
					"<td style=\"text-align: center;\">",
					"<img src=\"https://drive.google.com/uc?id=1sWErf5BURqJPnQaEeVfK26dRCpjyqZ95\" width=\"350\" style=\"height: auto;\" />",
					"<h3>" + name + "さん<br>myouthアカウントのご登録<br>ありがとうございます</h1>",
					"<p>このメールアドレスを承認するには<a href='https://myouth.jp/emailVerification/" + userId + "'>こちら</a>をクリックしてください",
					"<p>このアドレスは配信専用です<br>返信しないでください", "<p>ご相談がある場合こちらのメールアドレスへ</p>",
					"<a href='mailto:multi.c.youth@gmail.com'>multi.c.youth@gmail.com</a>", "<p>サイトのシステムに関しての相談<br>",
					"<a href='mailto:rajan.valencia@au.com'>rajan.valencia@au.com</a><br>",
					"<img src=\"https://drive.google.com/uc?id=11msmk5NI2MplO4qpaBklVzvq7PVpCV8q\" width=\"200\" style=\"height: auto;\" />",
					"<p><a href='https://myouth.jp/'>myouth</a>", "</td>", "</tr>", "</table>");

			String TEXTBODY = name + "さん\n"
					+"myouthアカウントのご登録\n"
					+"ありがとうございます\n"
					+"このメールアドレスを承認するには\n\n"
					+"https://myouth.jp/emailVerification/" + userId + "\n\n"
					+"をクリックしてください\n"
					+"このアドレスは配信専用です\n"
					+"返信しないでください\n"
					+"サイトのシステムに関しての相談\n"
					+"rajan.valencia@au.com";

			ArrayList<String> TO = new ArrayList<String>();
			TO.add(email);
			Mail mail = new Mail();
			mail.send(FROM, TO, FROMNAME, SUBJECT, TEXTBODY, HTMLBODY);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean joinConfirmedMail(String event, String name, String email) {
		try {
			Events db = new Events();
			db.open();

			String eventName = db.eventName(event);
			String place = db.eventLocation(event);
			ArrayList<String> date = db.eventDate(event);
			String year = date.get(0);
			String month = date.get(1);
			String day = date.get(2);
			ArrayList<String> time = db.eventTime(event);
			String hour = time.get(0);
			String minute = time.get(1);

			if(Integer.valueOf(minute) < 10)
				minute = "0"+minute;
			db.close();

			String FROM = event + "@myouth.jp";
		
			String FROMNAME = eventName;
			
			String SUBJECT = "お申込み完了のお知らせ";
			
			String HTMLBODY = String.join(System.getProperty("line.separator"),
					STYLE,
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "<tr>",
					"<td style=\"text-align: center;\">",
					"<img src=\"https://drive.google.com/uc?id=1sWErf5BURqJPnQaEeVfK26dRCpjyqZ95\" width=\"350\" style=\"height: auto;\" />",
					"<h3>" + name + "さん<br>お申込みありがとうございます</h1>", "<p>開催場所:<br><b>" + place + "</b>",
					"<br>開催日:<br><b>" + year + "年" + month + "月" + day + "日</b>", "<br>集合時間:<br><b>" + hour + "時" + minute + "分</b>",
					"<p><a href=\"https://www.google.com/maps/dir/?api=1&destination=" + place
							+ "\">現在地から会場までのルートはこちら</a></p>",
					"<img src=\"http://cdn.worldslargestlesson.globalgoals.org/2016/08/Invent-Innovate-Campaign.gif\" width=\"350\" style=\"height: auto;\" />",
					"<p>" + name + "さんに会えることを<br>楽しみにしています ", "<p>このアドレスは配信専用です<br>返信しないでください",
					"<p>ご相談がある場合こちらのメールアドレスへ</p>",
					"<p><a href='mailto:multi.c.youth@gmail.com'>multi.c.youth@gmail.com</a></p>", 
					"<p>サイトのシステムに関しての相談<br>",
					"<a class=\"myButton\" href='mailto:rajan.valencia@au.com'>rajan.valencia@au.com</a></p>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "\">" + eventName + "ホーム</a></p>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "/form\">" + eventName + "参加申し込み</a></p>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "/survey\">" + eventName + "アンケート</a></p>",
					"<p><a class=\"myButton\" href=\"https://www.google.com/maps/dir/?api=1&destination=" + place + "\">" + eventName + "会場までのルート</a></p>",
					"<img src=\"https://drive.google.com/uc?id=11msmk5NI2MplO4qpaBklVzvq7PVpCV8q\" width=\"200\" style=\"height: auto;\" />",
					"<p><a href='https://myouth.jp/'>myouth</a>", "</td>", "</tr>", "</table>");

			String TEXTBODY =  name + "さん\n"
					+"お申込みありがとうございます\n"
					+"開催場所:" + place + "\n"
					+"開催日:" + year + "年" + month + "月" + day + "日\n"
					+"開始時間:" + hour + "時" + minute + "分\n"
					+"現在地から会場までのルートは\n"
					+"https://www.google.com/maps/dir/?api=1&destination=" + place + "\n"
					+"連絡事項はこちらのメールアドレス\n"
					+"で送信致します。ご了承ください。\n"
					+name + "さんの多様性を生かし,より良い社会を築きましょう\n"
					+name + "さんに会えることを\n"
					+"楽しみにしています \n"
					+"このアドレスは配信専用です\n"
					+"返信しないでください\n"
					+"ご相談がある場合こちらのメールアドレスへ\n"
					+"multi.c.youth@gmail.com\n"
					+"サイトのシステムに関しての相談\n"
					+"rajan.valencia@au.com\n"
					+"イベント管理システム（サイト)\n"
					+"https://myouth.jp/";

			ArrayList<String> TO = new ArrayList<String>();
			TO.add(email);
			Mail mail = new Mail();
			mail.send(FROM, TO, FROMNAME, SUBJECT, TEXTBODY, HTMLBODY);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Boolean mailToAllParticipants(String event, String eventname, String username, String SUBJECT,
			ArrayList<String> TO, String TEXTBODY) {
		try {
			String FROM = event + "@myouth.jp";
			String FROMNAME = eventname;
			
			Events db = new Events();
			db.open();
			String place = db.eventLocation(event);
			db.close();

			String HTMLTEXTBODY = StringEscapeUtils.escapeHtml4(TEXTBODY);

			HTMLTEXTBODY = TEXTBODY.replaceAll("(\r\n|\n)", "<br />");

			String HTMLBODY = String.join(System.getProperty("line.separator"),
					HTMLTEXTBODY,
					STYLE,
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "<tr>",
					"<td style=\"text-align: center;\">", "<br><br>*************内容はここまで*************<br>",
					"<img src=\"https://media.giphy.com/media/6wcBC9tsubD5jrDL6g/giphy.gif\" width=\"100%\" style=\"height: auto;\" /><br>",
					"<br>" + username + "さんが<br>", "myouthメールシステムにて送信されました<br>このアドレスは配信専用アドレスです<br>",
					"返信しないでください<br><br>", 
					"<img src=\"https://media.giphy.com/media/UOdoMz3baCENO/giphy.gif\" width=\"100%\" style=\"height: auto;\" /><br>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "\">" + eventname + "ホーム</a></p>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "/form\">" + eventname + "参加申し込み</a></p>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "/survey\">" + eventname + "アンケート</a></p>",
					"<p><a class=\"myButton\" href=\"https://www.google.com/maps/dir/?api=1&destination=" + place + "\">" + eventname + "会場までのルート</a></p>",
					"<img src=\"https://drive.google.com/uc?id=11msmk5NI2MplO4qpaBklVzvq7PVpCV8q\" width=\"200\" style=\"height: auto;\" /><br>",
					"イベント管理システム(サイト)<br>https://myouth.jp", "</td>", "</tr>", "</table>");

			TEXTBODY = TEXTBODY.replaceAll("(\r\n|\n)", "");

			TEXTBODY = TEXTBODY
					+ username + "さんが\n"
					+"myouthメールシステムにて送信されました\n"
					+"このアドレスは配信専用アドレスです\n"
					+"返信しないでください\n" 
					+ eventname + "ホーム\n"
					+"https://myouth.jp/events/" + event + "\n"
					+ eventname + "会場までのルート\n"
					+"https://www.google.com/maps/dir/?api=1&destination=" + place + "\n"
					+ eventname + "参加申し込み\n"
					+"https://myouth.jp/events/" + event + "/form\n"
					+ eventname + "アンケート\n"
					+"https://myouth.jp/events/" + event + "/survey\n"
					+ "イベント管理システム(サイト)\n"
					+"https://myouth.jp";

			Mail mail = new Mail();
			Boolean res = mail.send(FROM, TO, FROMNAME, SUBJECT, TEXTBODY, HTMLBODY);

			if(res)
				return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean reissuePassword(String email, String token) {
		try {
			String FROM = "reissuePassword@myouth.jp";
			String FROMNAME = "myouth";
			
			String SUBJECT = "パスワード再発行";
			
			ArrayList<String> TO = new ArrayList<String>();
			TO.add(email);
			
			String HTMLBODY = String.join(System.getProperty("line.separator"),
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "<tr>",
					"<td style=\"text-align: center;\">",
					"<br>myouthで登録されてるアカウント"+email+"<br>のパスワードを再発行するためのurlです",
					"<br><br><a href=\"https://myouth.jp/reissueNewPassword/"+ token +"\">https://myouth.jp/reissueNewPassword/"+token+"</a><br>",
					"<br><img src=\"https://media.giphy.com/media/3og0IuymsB9sy0C2Vq/giphy.gif\" width=\"100%\" style=\"height: auto;\">",
					"<br><br>myouthメールシステムにて送信されました<br>このアドレスは配信専用アドレスです<br>",
					"返信しないでください<br><br>", 
					"<img src=\"https://drive.google.com/uc?id=11msmk5NI2MplO4qpaBklVzvq7PVpCV8q\" width=\"200\" style=\"height: auto;\" /><br>",
					"イベント管理システム(サイト)<br><a href=\"https://myouth.jp\">https://myouth.jp</a>", "</td>", "</tr>", "</table>");
			
			String TEXTBODY = "myouthで登録されてるアカウント\n"+email+"\nのパスワードを再発行するためのurlです\n\n"
					+"https://myouth.jp/reissueNewPassword/" + token + "\n\n"
					+"このアドレスは配信専用アドレスです\n"
					+"返信しないでください\n\n"
					+"イベント管理システム(サイト)\n"
					+"https://myouth.jp";

			
			Mail mail = new Mail();
			Boolean res = mail.send(FROM, TO, FROMNAME, SUBJECT, TEXTBODY, HTMLBODY);
			
			if(res)
				return true;
			else
				return false;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
 