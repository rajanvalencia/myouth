package com.aws.codestar.projecttemplates.controller;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import jp.myouth.db.Images;
import jp.myouth.db.User;

public class UpdateAccountDetails {
	
	public void initialize(HttpSession session, ModelMap model) {
		
		String userId = (String) session.getAttribute("userId");
		
		User db = new User();
		db.open();
		model.addAttribute("name", db.getName(userId));
		model.addAttribute("fname", db.getFname(userId));
		model.addAttribute("word", db.getMotto(userId));
		db.close();
		
		Images db1 = new Images();
		db1.open();
		model.addAttribute("profilePictureUrl", db1.userProfilePicture(userId));
		db1.close();
	}
}
