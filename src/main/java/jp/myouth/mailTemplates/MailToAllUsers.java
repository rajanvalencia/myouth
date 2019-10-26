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
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 0.9em; line-height: 1.5em; letter-spacing: 0;\">�J����:</h5>\r\n" + 
					" <img src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/users/fWSwV9AqUN6/profilePicture/4YcEcdZhYyEaYDtt0z8g.jpg\" style=\"display: block; border-radius: 50%; margin-right: auto; margin-left: auto;\" width=\"130\">\r\n" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 0.9em; line-height: 1.5em; letter-spacing: 0;\">Valencia Rajan Zamora</h5>\r\n" + 
					" <img src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/backgrounds/myouth-logo.jpg\" width=\"200\" style=\"display: block; border-radius: 10px; margin-right: auto; margin-left: auto;\">\r\n" + 
					" <h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; color: #646464; text-align: center; font-size: 1.1em; line-height: 1.5em; letter-spacing: 0;\">�C�x���g�Ǘ��V�X�e��</h5>" + 
					" <a style=\"text-decoration: none;\" href=\"https://myouth.jp\"><h5 style=\"font-weight: 300; line-height: 1em; margin: 0 0 0.5em 0; text-align: center; color: #e89980; font-size: 1em;\">https://myouth.jp</h5></a>" +
					"</td></tr></table>";

			
			String TEXT = TEXTBODY;
			
			TEXTBODY = TEXTBODY.replaceAll("(\r\n|\n)", "");
			
			TEXTBODY = TEXTBODY
					+"\n�C�x���g�Ǘ��V�X�e��\n"
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
		String subject = "���[�U�[�ւ̂��m�点";
		String body = "myouth�ɂ��o�^�����������肪�Ƃ��������܂��B\n\n"
					+ "�F���܂ɂ͂��̃��[���ɂāA�V���ȋ@�\�܂��͒������o�O�����������ő��M�v���܂��B\n\n"
					+ "���݂̃��[���A�h���X�͑��M�����ł��܂��񂪁A��M�@�\���������܂��B\n\n"
					+ "���̂Ƃ��ɁA���萔�ł����o�O�Ȃǌ����Ă����������炨�m�点�Ă�����������K���ł��B\n\n"
					+ "���݂͊e�C�x���g�̉摜�A�b�v���[�h�@�\���\�z���Ă���Œ��ł��B\n\n"
					+ "AI�ŕs�K�؂ȉ摜�͎����I�ɔr���������܂��B\n\n"
					+ "�Ќ��ł����A��낵�����肢���܂�";
		MailToAllUsers mail = new MailToAllUsers();
		Boolean res = mail.template(subject, body);
		System.out.println(res);
	}
}
