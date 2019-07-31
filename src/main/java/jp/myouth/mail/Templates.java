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

			String SUBJECT = "�A�J�E���g���F";

			String HTMLBODY = String.join(System.getProperty("line.separator"),
					STYLE,
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
					+"���̃��[���A�h���X�����F����ɂ�\n\n"
					+"https://myouth.jp/emailVerification/" + userId + "\n\n"
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
					STYLE,
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "<tr>",
					"<td style=\"text-align: center;\">",
					"<img src=\"https://drive.google.com/uc?id=1sWErf5BURqJPnQaEeVfK26dRCpjyqZ95\" width=\"350\" style=\"height: auto;\" />",
					"<h3>" + name + "����<br>���\���݂��肪�Ƃ��������܂�</h1>", "<p>�J�Ïꏊ:<br><b>" + place + "</b>",
					"<br>�J�Ó�:<br><b>" + year + "�N" + month + "��" + day + "��</b>", "<br>�W������:<br><b>" + hour + "��" + minute + "��</b>",
					"<p><a href=\"https://www.google.com/maps/dir/?api=1&destination=" + place
							+ "\">���ݒn������܂ł̃��[�g�͂�����</a></p>",
					"<img src=\"http://cdn.worldslargestlesson.globalgoals.org/2016/08/Invent-Innovate-Campaign.gif\" width=\"350\" style=\"height: auto;\" />",
					"<p>" + name + "����ɉ�邱�Ƃ�<br>�y���݂ɂ��Ă��܂� ", "<p>���̃A�h���X�͔z�M��p�ł�<br>�ԐM���Ȃ��ł�������",
					"<p>�����k������ꍇ������̃��[���A�h���X��</p>",
					"<p><a href='mailto:multi.c.youth@gmail.com'>multi.c.youth@gmail.com</a></p>", 
					"<p>�T�C�g�̃V�X�e���Ɋւ��Ă̑��k<br>",
					"<a class=\"myButton\" href='mailto:rajan.valencia@au.com'>rajan.valencia@au.com</a></p>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "\">" + eventName + "�z�[��</a></p>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "/form\">" + eventName + "�Q���\������</a></p>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "/survey\">" + eventName + "�A���P�[�g</a></p>",
					"<p><a class=\"myButton\" href=\"https://www.google.com/maps/dir/?api=1&destination=" + place + "\">" + eventName + "���܂ł̃��[�g</a></p>",
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
					STYLE,
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "<tr>",
					"<td style=\"text-align: center;\">", "<br><br>*************���e�͂����܂�*************<br>",
					"<img src=\"https://media.giphy.com/media/6wcBC9tsubD5jrDL6g/giphy.gif\" width=\"100%\" style=\"height: auto;\" /><br>",
					"<br>" + username + "����<br>", "myouth���[���V�X�e���ɂđ��M����܂���<br>���̃A�h���X�͔z�M��p�A�h���X�ł�<br>",
					"�ԐM���Ȃ��ł�������<br><br>", 
					"<img src=\"https://media.giphy.com/media/UOdoMz3baCENO/giphy.gif\" width=\"100%\" style=\"height: auto;\" /><br>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "\">" + eventname + "�z�[��</a></p>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "/form\">" + eventname + "�Q���\������</a></p>",
					"<p><a class=\"myButton\" href=\"https://myouth.jp/events/" + event + "/survey\">" + eventname + "�A���P�[�g</a></p>",
					"<p><a class=\"myButton\" href=\"https://www.google.com/maps/dir/?api=1&destination=" + place + "\">" + eventname + "���܂ł̃��[�g</a></p>",
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
	
	public Boolean reissuePassword(String email, String token) {
		try {
			String FROM = "reissuePassword@myouth.jp";
			String FROMNAME = "myouth";
			
			String SUBJECT = "�p�X���[�h�Ĕ��s";
			
			ArrayList<String> TO = new ArrayList<String>();
			TO.add(email);
			
			String HTMLBODY = String.join(System.getProperty("line.separator"),
					"<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", "<tr>",
					"<td style=\"text-align: center;\">",
					"<br>myouth�œo�^����Ă�A�J�E���g"+email+"<br>�̃p�X���[�h���Ĕ��s���邽�߂�url�ł�",
					"<br><br><a href=\"https://myouth.jp/reissueNewPassword/"+ token +"\">https://myouth.jp/reissueNewPassword/"+token+"</a><br>",
					"<br><img src=\"https://media.giphy.com/media/3og0IuymsB9sy0C2Vq/giphy.gif\" width=\"100%\" style=\"height: auto;\">",
					"<br><br>myouth���[���V�X�e���ɂđ��M����܂���<br>���̃A�h���X�͔z�M��p�A�h���X�ł�<br>",
					"�ԐM���Ȃ��ł�������<br><br>", 
					"<img src=\"https://drive.google.com/uc?id=11msmk5NI2MplO4qpaBklVzvq7PVpCV8q\" width=\"200\" style=\"height: auto;\" /><br>",
					"�C�x���g�Ǘ��V�X�e��(�T�C�g)<br><a href=\"https://myouth.jp\">https://myouth.jp</a>", "</td>", "</tr>", "</table>");
			
			String TEXTBODY = "myouth�œo�^����Ă�A�J�E���g\n"+email+"\n�̃p�X���[�h���Ĕ��s���邽�߂�url�ł�\n\n"
					+"https://myouth.jp/reissueNewPassword/" + token + "\n\n"
					+"���̃A�h���X�͔z�M��p�A�h���X�ł�\n"
					+"�ԐM���Ȃ��ł�������\n\n"
					+"�C�x���g�Ǘ��V�X�e��(�T�C�g)\n"
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
 