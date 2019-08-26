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

		String result = "<table><thead><tr>";
		result += "<th>ŠwZ–¼‚Ü‚½‚Í‰ïĞ–¼</th><th>Q‰Ál”</th>";
		result += "</tr></thead><tbody>";

		for (String string : companyLists) {
			if (i % 2 == 0) {
				result += "<tr>";
				result += "<td>" + string + "</td>";
			} else {
				result += "<td>" + string + "</td>";
				result += "</tr>";
			}
			i++;
		}
		result += "</tbody></table>";
		request.setAttribute("companyTable", result);
	}
}
