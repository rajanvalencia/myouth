package com.aws.codestar.projecttemplates.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import jp.myouth.db.Questions;

public class ViewAllFormTemplates {
	
	public void initialize (HttpSession session, ModelMap model, String event) {
		Questions db = new Questions();
		db.open();
		ArrayList<String> templateIds = db.getTemplateIds(event);
		
		String result = new String();
		for(String templateId : templateIds) {
			String publicOption = new String();
			if(db.getPublicOption(templateId)) {
				publicOption = "checked";
			} else {
				publicOption = "";
			}
			
			String reviewOption = new String();
			if(db.getReviewOption(templateId)) {
				reviewOption = "checked";
			} else {
				reviewOption = "";
			}
			result += "<section id=\""+templateId+"\" class=\"box\">"
					+ "\n\t\t\t\t\t\t<div class=\"row gtr-uniform gtr-50\">"
				    + "\n\t\t\t\t\t\t\t<div class=\"col-7 col-0-mobile\">"
				    + "\n\t\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t\t\t<div class=\"col-4-narrower\">"
				    + "\n\t\t\t\t\t\t\t\t<h5>レビュー機能</h5>"
				    + "\n\t\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t\t\t<div class=\"col-1 col-3-mobile\">"
				    + "\n\t\t\t\t\t\t\t\t<label class=\"switch\">"
				    + "\n\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" "+reviewOption+" onclick=\"var attr = $(this).attr('checked'); if (typeof attr !== typeof undefined && attr !== false) {ajaxEditReviewOption('"+templateId+"', false); $(this).attr('checked', false);} else {ajaxEditReviewOption('"+templateId+"', true); $(this).attr('checked', true);}\">"
				    + "\n\t\t\t\t\t\t\t\t\t<span class=\"slider round\"></span>"
				    + "\n\t\t\t\t\t\t\t\t</label>"
				    + "\n\t\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t\t\t<div class=\"col-2-narrower\">"
				    + "\n\t\t\t\t\t\t\t\t<h5>公開</h5>"
				    + "\n\t\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t\t\t<div class=\"col-1 col-3-mobile\">"
				    + "\n\t\t\t\t\t\t\t\t<label class=\"switch\">"
				    + "\n\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" "+publicOption+" onclick=\"var attr = $(this).attr('checked'); if (typeof attr !== typeof undefined && attr !== false) {ajaxEditPublicOption('"+templateId+"', false); $(this).attr('checked', false);} else {ajaxEditPublicOption('"+templateId+"', true); $(this).attr('checked', true);}\">"
				    + "\n\t\t\t\t\t\t\t\t\t<span class=\"slider round\"></span>"
				    + "\n\t\t\t\t\t\t\t\t</label>"
				    + "\n\t\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t\t<div class=\"col-12\">"
				    + "\n\t\t\t\t\t\t\t<h3>"+db.getTitle(templateId)+"</h3>"
				    + "\n\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t\t<div class=\"row gtr-uniform gtr-50\">"
				    + "\n\t\t\t\t\t\t\t<div class=\"col-0 col-1-mobile\">"
				    + "\n\t\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
				    + "\n\t\t\t\t\t\t\t\t<span class=\"fas fa-trash fa-2x\" style=\"color: #ff7496; cursor: -webkit-grabbing; cursor: grabbing;\" onclick=\"deleteTemplate('"+templateId+"')\"></span>"
				    + "\n\t\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t\t\t<div class=\"col-9 col-7-mobile\">"
				    + "\n\t\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
				    + "\n\t\t\t\t\t\t\t\t<a href=\"/home/"+event+"/settings/formTemplates/"+templateId+"\" style=\"color: #e89980; border-bottom-color: transparent;\">"
				    + "\n\t\t\t\t\t\t\t\t\t<span class=\"fas fa-edit fa-2x\" style=\"cursor: -webkit-grabbing; cursor: grabbing;\"></span>"
				    + "\n\t\t\t\t\t\t\t\t</a>"
				    + "\n\t\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t\t</div>"
				    + "\n\t\t\t\t\t</section>";
		}
		db.close();
		
		model.addAttribute("eventInSessionStorage", "sessionStorage.setItem('event', '"+event+"');");
		model.addAttribute("viewAllFormTemplates", result);
		
	}
}

