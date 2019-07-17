package jp.myouth.mail;



import java.util.ArrayList;



import org.apache.commons.text.StringEscapeUtils;



import jp.myouth.db.Events;



public class Templates {

	public Boolean accountVerificationMail(String name, String email, String userId) {
		try {
			String FROM = "admin@myouth.jp";

			String FROMNAME = "myouth";

			String SUBJECT = "�A�J�E���g���F";

			String HTMLBODY = String.join(System.getProperty("line.separator"),
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "<tr>",
					"<td style=\"text-align: center;\">",
					"<img src=\"https://drive.google.com/uc?id=1sWErf5BURqJPnQaEeVfK26dRCpjyqZ95\" width=\"350\" style=\"height: auto;\" />",
					"<h3>" + name + "����<br>myouth�A�J�E���g�̂��o�^<br>���肪�Ƃ��������܂�</h1>",
					"<p>���̃��[���A�h���X�����F����ɂ�<a href='https://myouth.jp/emailVerification/" + userId + "'>������</a>���N���b�N���Ă�������",
					"<p>���̃A�h���X�͔z�M��p�ł�<br>�ԐM���Ȃ��ł�������", "<p>�����k������ꍇ������̃��[���A�h���X��</p>",
					"<a href='mailto:multi.c.youth@gmail.com'>multi.c.youth@gmail.com</a>", "<p>�T�C�g�̃V�X�e���Ɋւ��Ă̑��k<br>",
					"<a href='mailto:rajan.valencia@au.com'>rajan.valencia@au.com</a><br>",
					"<img src=\"https://drive.google.com/uc?id=11msmk5NI2MplO4qpaBklVzvq7PVpCV8q\" width=\"200\" style=\"height: auto;\" />",
					"<p><a href='https://myouth.jp/'>myouth</a>", "</td>", "</tr>", "</table>");

			String TEXTBODY = name + "����\n"
					+"myouth�A�J�E���g�̂��o�^\n"
					+"���肪�Ƃ��������܂�\n"
					+"���̃��[���A�h���X�����F����ɂ�\n"
					+"https://myouth.jp/emailVerification/" + userId + "\n"
					+"���N���b�N���Ă�������\n"
					+"���̃A�h���X�͔z�M��p�ł�\n"
					+"�ԐM���Ȃ��ł�������\n"
					+"�T�C�g�̃V�X�e���Ɋւ��Ă̑��k\n"
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
			
			String SUBJECT = "���\���݊����̂��m�点";
			
			String HTMLBODY = String.join(System.getProperty("line.separator"),
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "<tr>",
					"<td style=\"text-align: center;\">",
					"<img src=\"https://drive.google.com/uc?id=1sWErf5BURqJPnQaEeVfK26dRCpjyqZ95\" width=\"350\" style=\"height: auto;\" />",
					"<h3>" + name + "����<br>���\���݂��肪�Ƃ��������܂�</h1>", "<p>�J�Ïꏊ:<br><b>" + place + "</b>",
					"<br>�J�Ó�:<br><b>" + year + "�N" + month + "��" + day + "��</b>", "<br>�W������:<br><b>" + hour + "��" + minute + "��</b>",
					"<p><a href=\"https://www.google.com/maps/dir/?api=1&destination=" + place
							+ "\">���ݒn������܂ł̃��[�g�͂�����</a></p>",
					"<p>�A�������͂�����̃��[���A�h���X<br>�ő��M�v���܂��B���������������B", "<p>" + name + "����̑��l���𐶂����A<br>���ǂ��Љ��z���܂��傤<br>",
					"<img src=\"http://cdn.worldslargestlesson.globalgoals.org/2016/08/Invent-Innovate-Campaign.gif\" width=\"350\" style=\"height: auto;\" />",
					"<p>" + name + "����ɉ�邱�Ƃ�<br>�y���݂ɂ��Ă��܂� ", "<p>���̃A�h���X�͔z�M��p�ł�<br>�ԐM���Ȃ��ł�������",
					"<p>�����k������ꍇ������̃��[���A�h���X��</p>",
					"<a href='mailto:multi.c.youth@gmail.com'>multi.c.youth@gmail.com</a>", "<p>�T�C�g�̃V�X�e���Ɋւ��Ă̑��k<br>",
					"<a href='mailto:rajan.valencia@au.com'>rajan.valencia@au.com</a><br>",
					"<img src=\"https://drive.google.com/uc?id=11msmk5NI2MplO4qpaBklVzvq7PVpCV8q\" width=\"200\" style=\"height: auto;\" />",
					"<p><a href='https://myouth.jp/'>myouth</a>", "</td>", "</tr>", "</table>");

			String TEXTBODY =  name + "����\n"
					+"���\���݂��肪�Ƃ��������܂�\n"
					+"�J�Ïꏊ:" + place + "\n"
					+"�J�Ó�:" + year + "�N" + month + "��" + day + "��\n"
					+"�J�n����:" + hour + "��" + minute + "��\n"
					+"���ݒn������܂ł̃��[�g��\n"
					+"https://www.google.com/maps/dir/?api=1&destination=" + place + "\n"
					+"�A�������͂�����̃��[���A�h���X\n"
					+"�ő��M�v���܂��B���������������B\n"
					+name + "����̑��l���𐶂���,���ǂ��Љ��z���܂��傤\n"
					+name + "����ɉ�邱�Ƃ�\n"
					+"�y���݂ɂ��Ă��܂� \n"
					+"���̃A�h���X�͔z�M��p�ł�\n"
					+"�ԐM���Ȃ��ł�������\n"
					+"�����k������ꍇ������̃��[���A�h���X��\n"
					+"multi.c.youth@gmail.com\n"
					+"�T�C�g�̃V�X�e���Ɋւ��Ă̑��k\n"
					+"rajan.valencia@au.com\n"
					+"�C�x���g�Ǘ��V�X�e���i�T�C�g)\n"
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
					"<td style=\"text-align: center;\">", "<br><br>*************���e�͂����܂�*************<br>",
					"<img src=\"https://media.giphy.com/media/6wcBC9tsubD5jrDL6g/giphy.gif\" width=\"100%\" style=\"height: auto;\" /><br>",
					"<br>" + username + "����<br>", "myouth���[���V�X�e���ɂđ��M����܂���<br>���̃A�h���X�͔z�M��p�A�h���X�ł�<br>",
					"�ԐM���Ȃ��ł�������<br><br>", 
					"<img src=\"https://media.giphy.com/media/UOdoMz3baCENO/giphy.gif\" width=\"100%\" style=\"height: auto;\" /><br>",
					eventname + "�z�[��<br>https://myouth.jp/events/" + event + "<br><br>",
					eventname + "�Q���\������<br>https://myouth.jp/events/" + event + "/form<br><br>",
					eventname + "�A���P�[�g<br>https://myouth.jp/events/" + event + "/survey<br><br>",
					eventname + "���܂ł̃��[�g<br>https://www.google.com/maps/dir/?api=1&destination=" + place + "<br><br>",
					"<img src=\"https://drive.google.com/uc?id=11msmk5NI2MplO4qpaBklVzvq7PVpCV8q\" width=\"200\" style=\"height: auto;\" /><br>",
					"�C�x���g�Ǘ��V�X�e��(�T�C�g)<br>https://myouth.jp", "</td>", "</tr>", "</table>");

			TEXTBODY = TEXTBODY.replaceAll("(\r\n|\n)", "");

			TEXTBODY = TEXTBODY
					+ username + "����\n"
					+"myouth���[���V�X�e���ɂđ��M����܂���\n"
					+"���̃A�h���X�͔z�M��p�A�h���X�ł�\n"
					+"�ԐM���Ȃ��ł�������\n" 
					+ eventname + "�z�[��\n"
					+"https://myouth.jp/events/" + event + "\n"
					+ eventname + "���܂ł̃��[�g\n"
					+"https://www.google.com/maps/dir/?api=1&destination=" + place + "\n"
					+ eventname + "�Q���\������\n"
					+"https://myouth.jp/events/" + event + "/form\n"
					+ eventname + "�A���P�[�g\n"
					+"https://myouth.jp/events/" + event + "/survey\n"
					+ "�C�x���g�Ǘ��V�X�e��(�T�C�g)\n"
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
 