package jp.myouth.tables;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Participants;

public class UserPageParticipantsInfo {
	
	public void  append(HttpServletRequest request, String periodType, String startPeriod, String endPeriod) throws IOException {
		HttpSession session = request.getSession();
		
		String event = (String) session.getAttribute("event");
		
		Participants db1 = new Participants();
		db1.open();
		int i = 0;
		ArrayList<String> list = db1.participantsInfoLess(event, periodType, startPeriod, endPeriod);
		db1.close();
		
		String result = 
				  "\n\t\t\t\t\t\t<div class=\"table-wrapper\">"
				+ "\n\t\t\t\t\t\t\t<table>"
				+ "\n\t\t\t\t\t\t\t\t<thead>"
				+ "\n\t\t\t\t\t\t\t\t\t<tr>"
				+ "\n\t\t\t\t\t\t\t\t\t\t<th>タイムスタンプ</th>"
				+ "\n\t\t\t\t\t\t\t\t\t\t<th>名前</th>"
				+ "\n\t\t\t\t\t\t\t\t\t</tr>"
				+ "\n\t\t\t\t\t\t\t\t</thead>"
				+ "\n\t\t\t\t\t\t\t\t<tbody>";
		i = 0;
		for (String string : list) {
			if (i % 3 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>" + string + "</td>";
			}
			else if(i % 3 == 1)
				result += "\n\t\t\t\t\t\t\t\t\t\t<td>" + string + "</td>";
			else
				result += "\n\t\t\t\t\t\t\t\t\t\t<td><a href=\"/home/" + event + "/participants/"+ string
						+ "\"><i class=\"fa fa-info-circle\"></i></a></td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			i++;
		}
		
		result += "\n\t\t\t\t\t\t\t\t</tbody>"
				+ "\n\t\t\t\t\t\t\t</table>"
				+ "\n\t\t\t\t\t\t</div>";
		
		request.setAttribute("userPageParticipantsInfo", result);
	}
}
