package jp.myouth.mailTemplates;

import java.util.ArrayList;

import org.apache.commons.text.StringEscapeUtils;

import jp.myouth.db.Events;
import jp.myouth.db.Images;
import jp.myouth.db.Messages;
import jp.myouth.db.User;
import jp.myouth.mail.Mail;
import jp.myouth.security.GenerateSecureString;

public class SendMail {
	
	public Boolean template(String event, String userId, String SUBJECT,
			ArrayList<String> TO, String TEXTBODY) {
		try {
			String FROM = event + "@myouth.jp";
			
			Events db = new Events();
			db.open();
			String eventName = db.eventName(event);
			String eventLogo = db.eventLogo(event); 
			db.close();
			
			String FROMNAME = eventName;
			
			User db1 = new User();
			db1.open();
			String userName = db1.name(userId);
			db1.close();
			
			Images db2 = new Images();
			db2.open();
			String userProfilePicture = db2.userProfilePicture(userId);
			db2.close();
			
			String HTMLTEXTBODY = StringEscapeUtils.escapeHtml4(TEXTBODY);

			HTMLTEXTBODY = TEXTBODY.replaceAll("(\r\n|\n)", "<br />");

			String HTMLBODY = 
					"<table width=\"100%\" margin: 0; padding: 0; font-size: 100%; font: inherit; vertical-align: baseline; border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>" +
					"<blockquote style=\"border-left: solid 4px  #e5e5e5; margin: 0 0 2em 0; padding: 0.5em 0 0.5em 2em;\"><h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; font-size: 1em; color: #646464\">"+HTMLTEXTBODY+"</h5></blockquote>" +
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 0.9em; line-height: 1.5em; letter-spacing: 0;\">送信者:</h5>\r\n" + 
					" <img src=\""+userProfilePicture+"\" style=\"display: block; border-radius: 50%; margin-right: auto; margin-left: auto;\" width=\"130\">\r\n" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 0.9em; line-height: 1.5em; letter-spacing: 0;\">"+userName+"</h5>\r\n" + 
					" <img src=\""+eventLogo+"\" style=\"display: block; border-radius: 10px; margin-right: auto; margin-left: auto;\" width=\"130\">\r\n" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 1.1em; line-height: 1.5em; letter-spacing: 0;\">"+eventName+"</h5>\r\n" + 
					" <a style=\"text-decoration: none;\" href=\"https://myouth.jp/events/"+event+"\"><h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; text-align: center; color: #e89980; font-size: 1em;\">https://myouth.jp/events/"+event+"</h5></a>" +
					" <img src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/backgrounds/myouth-logo.jpg\" width=\"200\" style=\"display: block; border-radius: 10px; margin-right: auto; margin-left: auto;\">\r\n" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 1.1em; line-height: 1.5em; letter-spacing: 0;\">イベント管理システム</h5>" + 
					" <a style=\"text-decoration: none;\" href=\"https://myouth.jp\"><h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; text-align: center; color: #e89980; font-size: 1em;\">https://myouth.jp</h5></a>" +
					"</td></tr></table>";

			
			String TEXT = TEXTBODY;
			
			TEXTBODY = TEXTBODY.replaceAll("(\r\n|\n)", "");
			
			TEXTBODY = TEXTBODY
					+ "\n\n送信者:\n"
					+ userName + "\n\n"
					+ eventName + "\n"
					+"https://myouth.jp/events/" + event + "\n\n"
					+ "イベント管理システム\n"
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
	
}
