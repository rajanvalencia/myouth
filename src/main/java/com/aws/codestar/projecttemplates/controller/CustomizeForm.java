package com.aws.codestar.projecttemplates.controller;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import jp.myouth.db.Credentials;
import jp.myouth.db.Library;
import jp.myouth.db.Questions;
import jp.myouth.security.GenerateSecureString;
import jp.myouth.utilities.StringDecoder;

public class CustomizeForm {

	public void initialize(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model, String event, String templateId) {

		response.setCharacterEncoding("utf-8");
		model.addAttribute("templateId", "sessionStorage.setItem('templateId', '"+templateId+"');");
		
		model.addAttribute("eventInSessionStorage", "sessionStorage.setItem('event', '"+event+"');");
		
		GenerateSecureString gen = new GenerateSecureString();
		String apiKey = gen.string(100);
		model.addAttribute("apiKey", "sessionStorage.setItem('apiKey', '"+apiKey+"');");
		
		String userId = (String) request.getSession().getAttribute("userId");
		Credentials db = new Credentials();
		db.open();
		db.insertAjaxApiKey(userId, apiKey);
		db.close();
		
		Library lib = new Library();
		lib.open();

		ArrayList<String> countryNames_language = lib.getCountriesLanguages();
		String countryNamesHtml = new String();

		for (String language : countryNames_language) {
			ArrayList<String> countries = lib.getCountryNames(language);
			countryNamesHtml += "\n\n\t\tvar countries_" + language + " = '<option label=\"\" selected></option>'";
			for (String country : countries) {
				countryNamesHtml += "\n\t\t\t\t\t\t+ '<option value=\"" + lib.getCountryCode(country, language) + "\">"+ country + "</option>'";
			}
		}
		model.addAttribute("countries", countryNamesHtml);
		
		ArrayList<String> prefectureCodes = lib.getAllPrefectureCodes();
		String prefectureNamesHtml = new String();
		prefectureNamesHtml += "\n\n\t\tvar prefectures = '<option label=\"\" selected></option>'";
		for (String prefectureCode : prefectureCodes) {
			prefectureNamesHtml += "\n\t\t\t\t\t\t+ '<option value=\"" + prefectureCode + "\">"+ lib.getPrefectureName(prefectureCode) + "</option>'";
		}
		model.addAttribute("prefectures", prefectureNamesHtml);
		
		lib.close();
		
		session.setAttribute("test", "true");
		
		String formSent = (String) session.getAttribute("formSent");
		if(formSent == null || formSent.equals("false")) {
			model.addAttribute("customizeOrCheck", "ajaxGetAllQuestions();");
		} else {
			model.addAttribute("customizeOrCheck", "checkForm();");
			model.addAttribute("formSentSuccess", "");
			session.setAttribute("formSent", "false");
		}
	}
}
