package jp.myouth.tables;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;

public class EventPageCompanyTable {

	public void append(HttpServletRequest request) {
		ArrayList<String> companyLists = new ArrayList<String>();

		HttpSession session = request.getSession();
		String event = (String) session.getAttribute("event");

		Events db = new Events();
		db.open();
		companyLists = db.companyTable(event);
		db.close();

		int i = 0;

		String result = "<table>"
				+ "\n\t\t\t\t\t<thead>"
				+ "\n\t\t\t\t\t\t<tr>";
		result += "\n\t\t\t\t\t\t\t<th>ŠwZ–¼‚Ü‚½‚Í‰ïĞ–¼</th><th>Q‰Ál”</th>";
		result += "\n\t\t\t\t\t\t</tr>"
				+ "\n\t\t\t\t\t</thead>"
				+ "\n\t\t\t\t\t<tbody>";

		for (String string : companyLists) {
			if (i % 2 == 0) {
				result += "\n\t\t\t\t\t\t<tr>";
				result += "\n\t\t\t\t\t\t\t<td>" + string + "</td>";
			} else {
				result += "\n\t\t\t\t\t\t\t<td>" + string + "</td>";
				result += "\n\t\t\t\t\t\t</tr>";
			}
			i++;
		}
		result += "\n\t\t\t\t\t</tbody>"
				+ "\n\t\t\t\t</table>";
		request.setAttribute("companyTable", result);
	}
}
