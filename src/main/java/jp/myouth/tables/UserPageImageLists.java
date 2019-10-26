package jp.myouth.tables;

import java.util.ArrayList;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Images;

public class UserPageImageLists {

	public void append(HttpServletRequest request) {
		String result = new String();

		HttpSession session = request.getSession();
		String event = (String) session.getAttribute("event");

		Images db = new Images();
		db.open();
		ArrayList<String> lists = db.getImageListViaUser(event);
		db.close();

		result += "<div class=\"box alt\">"
				+ "\n\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
				+ "\n\t\t\t\t\t\t\t\t<div class=\"col-4 col-12-mobile\">"
				+ "\n\t\t\t\t\t\t\t\t\t<span class=\"image fit\"><img id=\"uploadEventImageIcon\" src=\"https://s3-ap-northeast-1.amazonaws.com/jp.myouth.images/events/uploadImageIcon.jpg\" alt=\"\"></span>"
				+ "\n\t\t\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">";
		
		int i = 0;
		for (String string : lists) {
			if (i % 3 == 0) {
				result += "\n\t\t\t\t\t\t\t\t<div class=\"col-4 col-12-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t<span class=\"image fit\" style=\"text-align: center;\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t<div class=\"image-container\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<img src=\"" + string + "\" alt=\"\" />";
			}
			else if(i % 3 == 1) {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"top-right\">" 
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<a style=\"color: #fff; border-bottom: none;\" href=\"/home/"+event+"/photo-video/"+string+"/delete\"><span class=\"fa fa-trash fa-2x\"></span></a>" 
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>";
			}
			else {
				if(string != null) {
					result += "\n\t\t\t\t\t\t\t\t\t\t</div>"
							+ "\n\t\t\t\t\t\t\t\t\t\t"+string
							+ "\n\t\t\t\t\t\t\t\t\t</span>"
							+ "\n\t\t\t\t\t\t\t\t</div>";
				}
				
				else {
					result += "\n\t\t\t\t\t\t\t\t\t\t</div>"
							+ "\n\t\t\t\t\t\t\t\t\t</span>"
							+ "\n\t\t\t\t\t\t\t\t</div>";
				}
			}
			i++;
		}

		result += "\n\t\t\t\t\t\t\t</div>" 
				+ "\n\t\t\t\t\t\t</div>";

		request.setAttribute("userPageImageLists", result);
	}
}
