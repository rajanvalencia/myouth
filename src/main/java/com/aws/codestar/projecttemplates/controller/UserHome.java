package com.aws.codestar.projecttemplates.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import jp.myouth.db.Images;
import jp.myouth.db.User;

public class UserHome {
	
	public void initialize(HttpSession session, ModelMap model) {
		String userId = (String) session.getAttribute("userId");
		
		User db = new User();
		db.open();
		model.addAttribute("name", db.getName(userId));
		model.addAttribute("fname", db.getFname(userId));
		model.addAttribute("word", db.getMotto(userId));
		ArrayList<String> data = db.managingEvents(userId);
		db.close();
		
		Images db1 = new Images();
		db1.open();
		model.addAttribute("profilePictureUrl", db1.userProfilePicture(userId));
		db1.close();

		String result = "<div class=\"table-wrapper\">"
					+ "\n\t\t\t\t\t\t\t<table>"
					+ "\n\t\t\t\t\t\t\t\t<tbody>";

		int i = 0;
		for (String string : data) {
			if(i % 4 == 0) {
				result += "\n\t\t\t\t\t\t\t\t\t<tr>"
						+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2-5 col-1-mobile\"></div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/settings\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-cog fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/participants\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-users fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/member\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-user-circle fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2\"><a href=\"home/" + string + "/photo-video\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-image fa-2x\"></span></a></div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-3 col-1-mobile\"></div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-3 col-4-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"/events/" + string + "\">";
			}
			else if (i % 4 == 1)
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<img style=\"border-radius: 25px;\" src=\"" + string + "\" alt=\"\" width=\"100\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t</a>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t</div>";
			else if (i % 4 == 2)
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-6 col-7-mobile\"><br /><h3>" + string + "</h3></div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t</div>";
			else {
				result += "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2-5 col-1-mobile\"></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/mail\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-envelope fa-2x\"></span></a></div>";
			
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/visualization\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fas fa-chart-line fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-3-mobile\"><a href=\"home/" + string + "/notifications\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-bell fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-2-mobile\"><a href=\"home/" + string + "/downloadSettings\" style=\"color: #e89980; border-bottom-color: transparent;\"><span class=\"fa fa-download fa-2x\"></span></a></div>";
				
				result += "\n\t\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
						+ "\n\t\t\t\t\t\t\t\t\t</tr>";
			}
			i++;
		}
		result += "\n\t\t\t\t\t\t\t\t</tbody>"
				+ "\n\t\t\t\t\t\t\t</table>"
				+ "\n\t\t\t\t\t\t</div>";
		
		model.addAttribute("userPageEventLists", result);
	}
}
