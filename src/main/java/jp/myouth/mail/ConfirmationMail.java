package jp.myouth.mail;

import jp.myouth.db.MySQL;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ConfirmationMail {
	
	SESVariables var = new SESVariables();
	
	// Replace sender@example.com with your "From" address.
    // This address must be verified.
    static final String FROM = "events@myouth.jp";
    
    // Replace smtp_username with your Amazon SES SMTP user name.
    final String SMTP_USERNAME = var.username();
    
    // Replace smtp_password with your Amazon SES SMTP password.
    final String SMTP_PASSWORD = var.password();
    
    // The name of the Configuration Set to use for this message.
    // If you comment out or remove this variable, you will also need to
    // comment out or remove the header below.
    static final String CONFIGSET = "ConfigSet";
    
    // Amazon SES SMTP host name. This example uses the 米国西部 (オレゴン) region.
    // See https://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html#region-endpoints
    // for more information.
    final String HOST = var.host();
    
    // The port you will connect to on the Amazon SES SMTP endpoint. 
    final int PORT = var.port();
    
    static final String SUBJECT = "お申込み完了のお知らせ";


    public Boolean Send(String event, String name, String email) throws Exception {
    	
    	MySQL db = new MySQL();
    	db.open();
    	ArrayList<ArrayList<String>> info = new ArrayList<ArrayList<String>>();
    	info = db.eventInfo(event);
    	db.close();
    	
    	int i = 0;
    	String eventname = new String();
    	String place = new String();
    	String date = new String();
    	String time = new String();
    	for(ArrayList<String> row : info) {
    		for(String string : row) {
    			switch(i) {
    			case 0:
    				eventname = string;
    			case 1:
    				place = string;
    				break;
    			case 2:
    				date = string;
    				break;
    			case 3:
    				time = string;
    				break;
    			}
    			i++;
    		}
    	}
    	
    	String FROMNAME = eventname;
    	String TO = email;
    	String BODY = String.join(
        	    System.getProperty("line.separator"),
        	    "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">", 
        	    "<tr>",
        	    "<td style=\"text-align: center;\">",
        	    "<img src=\"https://drive.google.com/uc?id=1sWErf5BURqJPnQaEeVfK26dRCpjyqZ95\" width=\"350\" style=\"height: auto;\" />",
        	    "<h3>"+name+"さん<br>お申込みありがとうございます</h1>",
        	    "<p>開催場所:<br><b>"+place+"</b>",
        	    "<br>開催日:<br><b>"+date+"</b>",
        	    "<br>集合時間:<br><b>"+time+"</b>",
        	    "<p><a href=\"https://www.google.com/maps/dir/?api=1&destination="+place+"\">現在地から会場までのルートはこちら</a></p>",
        	    "<p>連絡事項はこちらのメールアドレス<br>で送信致します。ご了承ください。",
        	    "<p>"+name+"さんの多様性を生かし、<br>より良い社会を築きましょう<br>",
        	    "<img src=\"http://cdn.worldslargestlesson.globalgoals.org/2016/08/Invent-Innovate-Campaign.gif\" width=\"350\" style=\"height: auto;\" />",
        	    "<p>"+name+"さんに会えることを<br>楽しみにしています ", 
        	    "<p>このアドレスは配信専用です<br>返信しないでください",
        	    "<p>ご相談がある場合こちらのメールアドレスへ</p>",
        	    "<a href='mailto:multi.c.youth@gmail.com'>multi.c.youth@gmail.com</a>",
        	    "<p>サイトのシステムに関しての相談<br>",
        	    "<a href='mailto:rajan.valencia@au.com'>rajan.valencia@au.com</a><br>",
        	    "<img src=\"https://drive.google.com/uc?id=11msmk5NI2MplO4qpaBklVzvq7PVpCV8q\" width=\"200\" style=\"height: auto;\" />",
        	    "<p><a href='https://myouth.jp/'>MYouth</a>",
        	    "</td>",
        	    "</tr>",
        	    "</table>"
        	);

        // Create a Properties object to contain connection configuration information.
    	Properties props = System.getProperties();
    	props.put("mail.transport.protocol", "smtp");
    	props.put("mail.smtp.port", PORT); 
    	props.put("mail.smtp.starttls.enable", "true");
    	props.put("mail.smtp.auth", "true");

        // Create a Session object to represent a mail session with the specified properties. 
    	Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information. 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY,"text/html; charset=utf-8");
        
        // Add a configuration set header. Comment or delete the 
        // next line if you are not using a configuration set
        msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);
            
        // Create a transport.
        Transport transport = session.getTransport();
                    
        // Send the message.
        try
        {
            System.out.println("Sending...");
            
            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
        	
            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
            
            return true;
        }
        catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            
            return false;
        }
        finally
        {
            // Close and terminate the connection.
            transport.close();
        }
    }
}