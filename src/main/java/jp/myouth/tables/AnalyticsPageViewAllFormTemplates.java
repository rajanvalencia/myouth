package jp.myouth.tables;

import java.util.ArrayList;

import org.springframework.ui.ModelMap;

import jp.myouth.db.Questions;

public class AnalyticsPageViewAllFormTemplates {
	
	public void append(ModelMap model, String event) {
		String result = "\n\t\t\t\t\t\t\t<div class=\"table-wrapper\">"
					+ "\n\t\t\t\t\t\t\t\t<table>";
		
		Questions db = new Questions();
		db.open();
		ArrayList<String> templateIds = db.getTemplateIds(event);
		for(String templateId : templateIds) {
			result += "\n\t\t\t\t\t\t\t\t\t<tr>"
					+ "\n\t\t\t\t\t\t\t\t\t\t<td>"
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"/home/"+event+"/visualization/analytics/"+templateId+"\"  style=\"color: #e89980; border-bottom-color: transparent;\">" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-2\">" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span class=\"fas fa-file fa-2x\"></span>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t</div>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t<div class=\"col-10\">" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<h3>"+db.getTitle(templateId)+"</h3>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t\t\t</div>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t\t</div>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t</a>"
					+ "\n\t\t\t\t\t\t\t\t\t\t</td>"
					+ "\n\t\t\t\t\t\t\t\t\t</tr>";
		}
		db.close();
		
		result += "\n\t\t\t\t\t\t\t\t</table>"
				+ "\n\t\t\t\t\t\t\t</div>";
		
		model.addAttribute("analyticsViewAllFormTemplates", result);
	}
}
