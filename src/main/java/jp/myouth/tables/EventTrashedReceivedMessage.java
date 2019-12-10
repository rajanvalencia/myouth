package jp.myouth.tables;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Messages;

public class EventTrashedReceivedMessage {

	public void append(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		String event =  (String) session.getAttribute("event");
		String messageId = (String) session.getAttribute("messageId");
		
		Messages db = new Messages();
		db.open();
		ArrayList<String> data = db.eventTrashedReceivedMessage(messageId);
		ArrayList<String> attachments = db.eventReceivedMessageAttachments(messageId);
		db.close();
		
		if(data.isEmpty())
			response.sendRedirect("/error404");
		
		String result = "<div class=\"table-wrapper\">"
				+ "\n\t\t\t\t\t\t\t<table>"
				+ "\n\t\t\t\t\t\t\t\t<tbody>";
		
		int i = 0;
		for(String string : data) {
			if(i % 4 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-10 col-8-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t差出人: "+string+"";
			
			}
			else if(i % 4 == 1) {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<h3>"+string+"</h3>";
			}
			else if(i % 4 == 2) {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t"+string
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"/home/"+event+"/mail/trashedInbox/"+messageId+"/undo\"><span class=\"fa fa-undo fa-2x\" style=\"color: #e89980; border-bottom-color: transparent;\"></span></a>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"/home/"+event+"/mail/trashedInbox/"+messageId+"/delete\"><span class=\"fa fa-trash fa-2x\" style=\"color: #e89980; border-bottom-color: transparent;\"></span></a>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";	
			}
			else {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"+string+"</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			}
			i++;
		}
		
		if(attachments.isEmpty()) {
			result += "\n\t\t\t\t\t\t\t\t</tbody>"
					+ "\n\t\t\t\t\t\t\t</table>"
					+ "\n\t\t\t\t\t\t</div>";
		} else {
			i = 0;
			for (String string : attachments) {
				if (i % 2 == 0) {
					result += "\n\t\t\t\t\t\t\t\t\t<tr>"
							+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
							+ "\n\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
							+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
							+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<a target=\"blank\" href=\"" + string
							+ "\"><span class=\"far fa-file fa-4x\"></span>" 
							+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>";
				}

				else if (i % 2 == 1) {
					result += "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-11 col-10-mobile\">"
							+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<br /><h4>" + string + "</h4>"
							+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>"
							+ "\n\t\t\t\t\t\t\t\t\t\t</div>" 
							+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
							+ "\n\t\t\t\t\t\t\t\t\t</tr>" 
							+ "\n\t\t\t\t\t\t\t\t</tbody>" 
							+ "\n\t\t\t\t\t\t\t</table>"
							+ "\n\t\t\t\t\t\t</div>";
				}
				i++;
			}
		}
		
		request.setAttribute("eventTrashedReceivedMessage", result);
	}
}
