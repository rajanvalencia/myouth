package jp.myouth.tables;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Participants;

public class UserPageParticipantInfoFull {
	
	public void  append(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		String event = (String) session.getAttribute("event");
		String participantId = (String) session.getAttribute("participantId");
		
		Participants db = new Participants();
		db.open();
		int i = 0;
		ArrayList<String> list = db.participantInfoFull(participantId, event);
		db.close();
		
		String result = 
				  "\n\t\t\t\t\t\t<div class=\"table-wrapper\">"
				+ "\n\t\t\t\t\t\t\t<table>"
				+ "\n\t\t\t\t\t\t\t\t<tbody>";
		i = 0;
		for (String string : list) {
			if (i % 2 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>" + string + "</td>";
			}
			else if(i % 2 == 1)
				result += "\n\t\t\t\t\t\t\t\t\t\t<td>" + string + "</td>";
			i++;
		}
		
		result += "\n\t\t\t\t\t\t\t\t</tbody>"
				+ "\n\t\t\t\t\t\t\t</table>"
				+ "\n\t\t\t\t\t\t</div>";
		
		request.setAttribute("userPageParticipantInfoFull", result);
	}
}
