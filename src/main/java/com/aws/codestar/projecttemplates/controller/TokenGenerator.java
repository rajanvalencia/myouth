package com.aws.codestar.projecttemplates.controller;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import jp.myouth.security.GenerateSecureString;

public class TokenGenerator {
	
	public void generate(HttpSession session, ModelMap model) {
		GenerateSecureString gen = new GenerateSecureString();
		String loginToken = gen.string(100);
		model.addAttribute("loginToken", loginToken);
		session.setAttribute("loginToken", loginToken);
	}
}
