package jp.myouth.mailTemplates;

import java.util.ArrayList;

import jp.myouth.db.Events;
import jp.myouth.mail.Mail;

public class EventJoinedConfirmationMail {
	
	public Boolean template(String event, String name, String email) {
		try {
			Events db = new Events();
			db.open();

			String eventLogo = db.eventLogo(event);
			String eventName = db.eventName(event);
			String place = db.eventLocation(event);
			String date = db.eventDate(event);
			String time = db.eventTime(event);

			db.close();

			String FROM = event + "@myouth.jp";
		
			String FROMNAME = eventName;
			
			String SUBJECT = "お申込み完了のお知らせ";
			
			String HTMLBODY = 
					"<table width=\"100%\" margin: 0; padding: 0; font-size: 100%; font: inherit; vertical-align: baseline; border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>" +
					" <h2 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 1.4em; line-height: 1.5em; letter-spacing: 0;\">"+name+"さん<br />お申込みありがとうございます</h2>" +
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 1.1em; line-height: 1.5em; letter-spacing: 0;\">開催場所: " +
					" <a style=\"text-decoration: none;\" href=\"https://www.google.com/maps/dir/?api=1&destination=" + place + "\">" +
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; text-align: center; color: #e89980; font-size: 1em;\">"+place+"</h5></a></h5>" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 1.1em; line-height: 1.5em; letter-spacing: 0;\">開催日: "+date+"</h5>" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 1.1em; line-height: 1.5em; letter-spacing: 0;\">開始時間: "+time+"</h5>" +
					" <img src=\""+eventLogo+"\" style=\"display: block; border-radius: 10px; margin-right: auto; margin-left: auto;\" width=\"130\">\r\n" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 1.1em; line-height: 1.5em; letter-spacing: 0;\">"+eventName+"</h5>\r\n" + 
					" <a style=\"text-decoration: none;\" href=\"https://myouth.jp/events/"+event+"\"><h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; text-align: center; color: #e89980; font-size: 1em;\">https://myouth.jp/events/"+event+"</h5></a>" +
					" <img src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/backgrounds/myouth-logo.jpg\" width=\"200\" style=\"display: block; border-radius: 10px; margin-right: auto; margin-left: auto;\">\r\n" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 1.1em; line-height: 1.5em; letter-spacing: 0;\">イベント管理システム</h5>" + 
					" <a style=\"text-decoration: none;\" href=\"https://myouth.jp\"><h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; text-align: center; color: #e89980; font-size: 1em;\">https://myouth.jp</h5></a>" +
					"</td></tr></table>";

			String TEXTBODY =  name + "さん\n"
					+"お申込みありがとうございます\n"
					+"開催場所:" + place + "\n"
					+"開催日: "+date+"\n"
					+"開始時間:"+time+"\n"
					+"多文化ユース\n"
					+"https://myouth.jp/events/"+event
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
	
	public static void main(String[] args) {
		String event = "altervoice";
		String name = "バレンシア　ラジャン　ザモラ";
		String email = "rajan.valencia@au.com";
		EventJoinedConfirmationMail mail = new EventJoinedConfirmationMail();
		mail.template(event, name, email);
	}
}
