package jp.myouth.tables;

import java.io.IOException;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Messages;

public class EventReceivedMessage {

	public void append(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		String event =  (String) session.getAttribute("event");
		String messageId = (String) session.getAttribute("messageId");
		
		Messages db = new Messages();
		db.open();
		ArrayList<String> data = db.eventReceivedMessage(messageId);
		ArrayList<String> attachments = db.eventReceivedMessageAttachments(messageId);
		db.close();
		
		if(data.isEmpty())
			response.sendRedirect("/error404");
		
		String result = "<div class=\"table-wrapper\">"
				+ "\n\t\t\t\t\t\t\t<table>"
				+ "\n\t\t\t\t\t\t\t\t<tbody>";
		
		int i = 0;
		for(String string : data) {
			if(i % 6 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t差出人: "+string+"";
			
			}
			else if(i % 4 == 1) {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<h3>"+string+"</h3>";
			}
			else if(i % 4 == 2) {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t"+string
						+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";	
			}
			else if(i % 4 == 3){
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
		
		result += "\n\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
				+ "\n\t\t\t\t\t\t\t<div class=\"col-3 col-2-mobile\"></div>" 
				+ "\n\t\t\t\t\t\t\t<div class=\"col-5 col-7-mobile\">"
				+ "\n\t\t\t\t\t\t\t\t<a href=\"/home/"+event+"/mail/create/"+messageId+"\" style=\"border-bottom-color: transparent;\"><span class=\"fa fa-reply fa-2x\"></span></a>"
				+ "\n\t\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t\t<div class=\"col-4 col-3-mobile\">"
				+ "\n\t\t\t\t\t\t\t\t<a href=\"/home/"+event+"/mail/inbox/"+messageId+"/delete\" style=\"border-bottom-color: transparent;\"><span class=\"fa fa-trash fa-2x\"></span></a>" 
				+ "\n\t\t\t\t\t\t\t</div>"  
				+ "\n\t\t\t\t\t\t</div>";
				
		
		request.setAttribute("eventReceivedMessage", result);
	}
}
