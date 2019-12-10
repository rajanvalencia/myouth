package com.aws.codestar.projecttemplates.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import jp.myouth.db.Credentials;
import jp.myouth.db.Events;
import jp.myouth.security.GenerateSecureString;

public class SettingsPage {

	public void initialize(HttpServletRequest request, ModelMap model, String event) {
		
		Events db = new Events();
		db.open();
		model.addAttribute("eventName",db.eventName(event));
		db.close();
		
		String apiKey = new GenerateSecureString().string(50);
		model.addAttribute("apiKey", apiKey);
		
		String userId = (String) request.getSession().getAttribute("userId");
		
		Credentials db1 = new Credentials();
		db1.open();
		db1.insertAjaxApiKey(userId, apiKey);
		db1.close();
	}
}
