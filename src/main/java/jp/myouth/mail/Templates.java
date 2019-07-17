package jp.myouth.mail;



import java.util.ArrayList;



import org.apache.commons.text.StringEscapeUtils;



import jp.myouth.db.Events;



public class Templates {

	public Boolean accountVerificationMail(String name, String email, String userId) {
		try {
			String FROM = "admin@myouth.jp";

			String FROMNAME = "myouth";

			String SUBJECT = "アカウント承認";

			String HTMLBODY = String.join(System.getProperty("line.separator"),
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
					+"このメールアドレスを承認するには\n"
					+"https://myouth.jp/emailVerification/" + userId + "\n"
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
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "<tr>",
					"<td style=\"text-align: center;\">",
					"<img src=\"https://drive.google.com/uc?id=1sWErf5BURqJPnQaEeVfK26dRCpjyqZ95\" width=\"350\" style=\"height: auto;\" />",
					"<h3>" + name + "さん<br>お申込みありがとうございます</h1>", "<p>開催場所:<br><b>" + place + "</b>",
					"<br>開催日:<br><b>" + year + "年" + month + "月" + day + "日</b>", "<br>集合時間:<br><b>" + hour + "時" + minute + "分</b>",
					"<p><a href=\"https://www.google.com/maps/dir/?api=1&destination=" + place
							+ "\">現在地から会場までのルートはこちら</a></p>",
					"<p>連絡事項はこちらのメールアドレス<br>で送信致します。ご了承ください。", "<p>" + name + "さんの多様性を生かし、<br>より良い社会を築きましょう<br>",
					"<img src=\"http://cdn.worldslargestlesson.globalgoals.org/2016/08/Invent-Innovate-Campaign.gif\" width=\"350\" style=\"height: auto;\" />",
					"<p>" + name + "さんに会えることを<br>楽しみにしています ", "<p>このアドレスは配信専用です<br>返信しないでください",
					"<p>ご相談がある場合こちらのメールアドレスへ</p>",
					"<a href='mailto:multi.c.youth@gmail.com'>multi.c.youth@gmail.com</a>", "<p>サイトのシステムに関しての相談<br>",
					"<a href='mailto:rajan.valencia@au.com'>rajan.valencia@au.com</a><br>",
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
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "<tr>",
					"<td style=\"text-align: center;\">", "<br><br>*************内容はここまで*************<br>",
					"<img src=\"https://media.giphy.com/media/6wcBC9tsubD5jrDL6g/giphy.gif\" width=\"100%\" style=\"height: auto;\" /><br>",
					"<br>" + username + "さんが<br>", "myouthメールシステムにて送信されました<br>このアドレスは配信専用アドレスです<br>",
					"返信しないでください<br><br>", 
					"<img src=\"https://media.giphy.com/media/UOdoMz3baCENO/giphy.gif\" width=\"100%\" style=\"height: auto;\" /><br>",
					eventname + "ホーム<br>https://myouth.jp/events/" + event + "<br><br>",
					eventname + "参加申し込み<br>https://myouth.jp/events/" + event + "/form<br><br>",
					eventname + "アンケート<br>https://myouth.jp/events/" + event + "/survey<br><br>",
					eventname + "会場までのルート<br>https://www.google.com/maps/dir/?api=1&destination=" + place + "<br><br>",
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

}
 