package com.aws.codestar.projecttemplates.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import jp.myouth.db.Credentials;
import jp.myouth.db.Events;
import jp.myouth.security.GenerateSecureString;

public class EditEventDetailsPage {
	
	public void initialize(HttpServletRequest request, ModelMap model, String event) {
		
		Events db = new Events();
		db.open();
		model.addAttribute("eventName",db.eventName(event));
		model.addAttribute("eventPlace",db.eventPlace(event));
		model.addAttribute("eventDate",db.eventDate(event));
		model.addAttribute("eventStartTime",db.eventStartTime(event));
		model.addAttribute("eventEndTime",db.eventEndTime(event));
		model.addAttribute("recruitmentStartDate",db.recruitmentStartDate(event));
		model.addAttribute("recruitmentEndDate",db.recruitmentEndDate(event));
		model.addAttribute("description",db.eventDescription(event));
		model.addAttribute("recruitLimit",db.recruitmentLimit(event));
		db.close();
	}
}
