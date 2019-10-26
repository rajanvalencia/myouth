package jp.myouth.mailTemplates;

import java.util.ArrayList;

import jp.myouth.mail.Mail;

public class SetNewPasswordMail {
	
	public Boolean template(String email, String token) {
		try {
			String FROM = "reissuePassword@myouth.jp";
			String FROMNAME = "myouth";
			
			String SUBJECT = "パスワード再発行";
			
			ArrayList<String> TO = new ArrayList<String>();
			TO.add(email);
			
			String HTMLBODY = 
					"<table width=\"100%\" margin: 0; padding: 0; font-size: 100%; font: inherit; vertical-align: baseline; border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td>" +
					"<blockquote style=\"border-left: solid 4px  #e5e5e5; margin: 0 0 2em 0; padding: 0.5em 0 0.5em 2em;\"><h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; font-size: 1em; color: #646464\">このメールアドレスのパスワードを再発行するためのurlです<br />30分経過したらまたは一度クリックしたら無効になります</h5>" +
					"<a style=\"text-decoration: none; color: #e89980;\" href=\"https://myouth.jp/setNewPassword/" + token + "\"><h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; font-size: 1em;\">https://myouth.jp/setNewPassword/" + token + "</h5></a>" +
					"<h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; font-size: 1em; color: #646464\"><br />このアドレスは配信専用です<br />返信しないでください</h5></blockquote>" +
					" <img src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/backgrounds/myouth-logo.jpg\" width=\"200\" style=\"display: block; border-radius: 10px; margin-right: auto; margin-left: auto;\">\r\n" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 1.1em; line-height: 1.5em; letter-spacing: 0;\">イベント管理システム</h5>" + 
					" <a style=\"text-decoration: none;\" href=\"https://myouth.jp\"><h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; text-align: center; color: #e89980; font-size: 1em;\">https://myouth.jp</h5></a>" +
					"</td></tr></table>";
			
			String TEXTBODY = "このメールアドレスのパスワードを再発行するためのurlです\n\n"
					+"https://myouth.jp/setNewPassword/" + token + "\n\n"
					+"このアドレスは配信専用アドレスです\n"
					+"返信しないでください\n\n"
					+"イベント管理システム(サイト)\n"
					+"https://myouth.jp";

			
			Mail mail = new Mail();
			mail.send(FROM, TO, FROMNAME, SUBJECT, TEXTBODY, HTMLBODY);
			
			return true;

		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		String email = "rajan.valencia@au.com";
		String token = "test";
		SetNewPasswordMail mail = new SetNewPasswordMail();
		mail.template(email, token);
	}
}
