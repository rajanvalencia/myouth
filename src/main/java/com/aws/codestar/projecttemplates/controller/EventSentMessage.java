package com.aws.codestar.projecttemplates.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import jp.myouth.db.Messages;

public class EventSentMessage {

	public void initialize(HttpServletResponse response, HttpSession session, ModelMap model, String event, String transactionId) throws IOException {
		
		Messages db = new Messages();
		db.open();
		ArrayList<String> data = db.eventSentMessage(transactionId);
		ArrayList<String> participants = db.alreadyReadParticipants(transactionId);
		ArrayList<String> users = db.alreadyReadUsers(transactionId);
		ArrayList<String> linkClickedUsers = db.linkClickedUsers(transactionId);
		ArrayList<String> linkClickedParticipants = db.linkClickedParticipants(transactionId);
		int totalMessageViews = db.totalMessageViews(transactionId);
		int totalLinkClicks = db.totalLinkClicks(transactionId);
		db.close();
		
		if(data.isEmpty())
			response.sendRedirect("/error404");
		
		String result = "<div class=\"table-wrapper\">"
				+ "\n\t\t\t\t\t\t\t<table>"
				+ "\n\t\t\t\t\t\t\t\t<tbody>";
		
		int i = 0;
		for(String string : data) {
			if(i % 3 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-11 col-10-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<h3>"+string+"</h3>";
			
			}
			else if(i % 3 == 1) {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t"+string+
						"\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"/home/"+event+"/mail/sent/"+transactionId+"/delete\"><span class=\"fa fa-trash fa-2x\" style=\"color: #e89980; border-bottom-color: transparent;\"></span></a>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			
			}
			else {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"+string+"</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>"
						+ "\n\t\t\t\t\t\t\t\t</tbody>"
						+ "\n\t\t\t\t\t\t\t</table>"
						+ "\n\t\t\t\t\t\t</div>";
			
			}
			i++;
		}
		
		result += "\n\t\t\t\t\t\t<div class=\"table-wrapper\">"
				+ "\n\t\t\t\t\t\t\t<table>"
				+ "\n\t\t\t\t\t\t\t\t<tbody>";
		result += "\n\t\t\t\t\t\t\t\t\t<span class=\"fa fa-eye fa-2x\"></span>"
				+ "\n\t\t\t\t\t\t\t\t\t<span style=\"font-size: 1.5em;\">  "+totalMessageViews+"</span>";
		
		i = 0;
		for(String user : users) {
			if(i % 3 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\""+user+"\" style=\"border-radius: 50%;\" width=\"35\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>";
			
			}
			else if(i % 3 == 1) {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-8 col-6-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t"+user+""
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>";
			}
			else
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-4-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t"+user+""
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			i++;
		}
		
		i = 0;
		for(String participant : participants) {
			if(i % 2 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-8 col-6-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t"+participant+""
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>";
			}
			else {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-4-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t"+participant+""
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			}
			i++;
		}
		
		result += "\n\t\t\t\t\t\t\t\t</tbody>"
				+ "\n\t\t\t\t\t\t\t</table>"
				+ "\n\t\t\t\t\t\t</div>";
		
		result += "\n\t\t\t\t\t\t<div class=\"table-wrapper\">"
				+ "\n\t\t\t\t\t\t\t<table>"
				+ "\n\t\t\t\t\t\t\t\t<tbody>";
		result += "\n\t\t\t\t\t\t\t\t\t<span class=\"fa fa-link fa-2x\"></span>"
				+ "\n\t\t\t\t\t\t\t\t\t<span style=\"font-size: 1.5em;\">  "+totalLinkClicks+"</span>";
		
		i = 0;
		for(String linkClickedUser : linkClickedUsers) {
			if(i % 3 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<img src=\""+linkClickedUser+"\" style=\"border-radius: 50%;\" width=\"35\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>";
			
			}
			else if(i % 3 == 1) {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-11 col-10-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t"+linkClickedUser+""
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>";
			}
			else
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-12\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<a style=\"color: #e89980;\" target=\"blank\" href=\""+linkClickedUser+"\">"+linkClickedUser+"</a>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			i++;
		}
		
		i = 0;
		for(String linkClickedParticipant : linkClickedParticipants) {
			if(i % 2 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-5 col-10-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t"+linkClickedParticipant+""
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>";
			}
			else {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-5 col-12-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<a style=\"color: #e89980;\" target=\"blank\" href=\""+linkClickedParticipant+"\">"+linkClickedParticipant+"</a>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			}
			i++;
		}
		
		result += "\n\t\t\t\t\t\t\t\t</tbody>"
				+ "\n\t\t\t\t\t\t\t</table>"
				+ "\n\t\t\t\t\t\t</div>";
		
		model.addAttribute("eventSentMessage", result);
	}
}
