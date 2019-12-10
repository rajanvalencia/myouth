package jp.myouth.mailTemplates;

import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;

import jp.myouth.db.Messages;
import jp.myouth.db.User;
import jp.myouth.mail.Mail;
import jp.myouth.security.GenerateSecureString;

public class MailToAllUsers {
	
	private final String FROM = "admin@myouth.jp";
	
	String FROMNAME = "myouth";
	
	public Boolean template(String SUBJECT, String TEXTBODY) {
		try {
			
			User db = new User();
			db.open();
			ArrayList<String> TO = db.userEmailAddresses();
			//ArrayList<String> TO = new ArrayList<String>();
			//TO.add("rajan.valencia@au.com");
			db.close();
			String HTMLTEXTBODY = StringEscapeUtils.escapeHtml4(TEXTBODY);

			HTMLTEXTBODY = TEXTBODY.replaceAll("(\r\n|\n)", "<br />");

			String HTMLBODY = 
					"<table width=\"100%\" margin: 0; padding: 0; font-size: 100%; font: inherit; vertical-align: baseline; border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>" +
					"<blockquote style=\"border-left: solid 4px  #e5e5e5; margin: 0 0 2em 0; padding: 0.5em 0 0.5em 2em;\"><h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; font-size: 1em; color: #646464\">"+HTMLTEXTBODY+"</h5></blockquote>" +
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 0.9em; line-height: 1.5em; letter-spacing: 0;\">開発者:</h5>\r\n" + 
					" <img src=\"https://s3-ap-northeast-1.amazonaws.com/myouth-images/users/fWSwV9AqUN6/profilePicture/4YcEcdZhYyEaYDtt0z8g.jpg\" style=\"display: block; border-radius: 50%; margin-right: auto; margin-left: auto;\" width=\"130\">\r\n" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 0.9em; line-height: 1.5em; letter-spacing: 0;\">Valencia Rajan Zamora</h5>\r\n" + 
					" <img src=\"https://s3-ap-northeast-1.amazonaws.com/myouth-images/backgrounds/myouth-logo.jpg\" width=\"200\" style=\"display: block; border-radius: 10px; margin-right: auto; margin-left: auto;\">\r\n" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 1.1em; line-height: 1.5em; letter-spacing: 0;\">イベント管理システム</h5>" + 
					" <a style=\"text-decoration: none;\" href=\"https://myouth.jp\"><h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; text-align: center; color: #e89980; font-size: 1em;\">https://myouth.jp</h5></a>" +
					"</td></tr></table>";

			
			String TEXT = TEXTBODY;
			
			TEXTBODY = TEXTBODY.replaceAll("(\r\n|\n)", "");
			
			TEXTBODY = TEXTBODY
					+"\nイベント管理システム\n"
					+"https://myouth.jp";

			Mail mail = new Mail();
			ArrayList<String> messageId = mail.send(FROM, TO, FROMNAME, SUBJECT, TEXTBODY, HTMLBODY);
			
			GenerateSecureString gen = new GenerateSecureString();
			String transactionId = gen.string(20);
			
			Messages db3 = new Messages();
			db3.open();
			db3.registerSentMessage(transactionId, FROM, SUBJECT, TEXT);
			db3.registerMessageTransaction(transactionId, messageId);
			db3.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		String subject = "ユーザーへのお知らせ";
		String body = "myouthにご登録いただきありがとうございます。\n\n"
					+ "皆さまにはこのメールにて、新たな機能または直したバグを将来ここで送信致します。\n\n"
					+ "現在のメールアドレスは送信しかできませんが、受信機能を将来つけます。\n\n"
					+ "そのときに、お手数ですがバグなど見つけていただいたらお知らせていただけたら幸いです。\n\n"
					+ "現在は各イベントの画像アップロード機能を構築している最中です。\n\n"
					+ "AIで不適切な画像は自動的に排除いたします。\n\n"
					+ "片言ですが、よろしくお願いします";
		MailToAllUsers mail = new MailToAllUsers();
		Boolean res = mail.template(subject, body);
		System.out.println(res);
	}
}
