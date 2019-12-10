package jp.myouth.tables;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import jp.myouth.db.Credentials;
import jp.myouth.db.Events;
import jp.myouth.db.Library;
import jp.myouth.db.Questions;
import jp.myouth.security.GenerateSecureString;

public class FormTemplate {
	
	public void initialize(ModelMap model, String event, String templateId) throws SQLException {
		
		Questions db = new Questions();
		db.open();
		ArrayList<String> questionIds = db.getQuestionIds(templateId);
		
		String result = new String();
		
		if(db.getReviewOption(templateId)) {
			result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
					+ "\n\t\t\t\t\t\t\t\t\t<label for=\"stars\">タップして評価して下さい <span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span></label>"
					+ "\n\t\t\t\t\t\t\t\t\t<div id=\"review\">" 
					+ "\n\t\t\t\t\t\t\t\t\t\t<label>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"stars\" value=\"1\" />" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t</label>"
					+ "\n\t\t\t\t\t\t\t\t\t\t<label>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"stars\" value=\"2\" />" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>"
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t</label>"
					+ "\n\t\t\t\t\t\t\t\t\t\t<label>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"stars\" value=\"3\" />" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>"
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>"
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>"
					+ "\n\t\t\t\t\t\t\t\t\t\t</label>"
					+ "\n\t\t\t\t\t\t\t\t\t\t<label>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"stars\" value=\"4\" />" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>"
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t</label>"
					+ "\n\t\t\t\t\t\t\t\t\t\t<label>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<input type=\"radio\" name=\"stars\" value=\"5\" />" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>"
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star\"></span>"
					+ "\n\t\t\t\t\t\t\t\t\t\t</label>"
					+ "\n\t\t\t\t\t\t\t\t\t</div>"
					+ "\n\t\t\t\t\t\t\t\t\t<label for=\"review\">レビューを書いて下さい <span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span></label>" 
					+ "\n\t\t\t\t\t\t\t\t\t\t<textarea name=\"review\" rows=\"3\" required></textarea>"
					+ "\n\t\t\t\t\t\t\t\t\t</label>"
					+ "\n\t\t\t\t\t\t\t\t</div>";
		}
		
		for(String questionId : questionIds) {
			
			String questionType = db.getQuestionType(questionId);
			
			Boolean required = db.checkRequiredOption(questionId);
			String requiredIcon = "";
			String requiredText = "";
			if(required) {
				requiredIcon = "<span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span>";
				requiredText = "required";
			}
			
			if(questionType.equals("pulldown")) {
				result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
						+ "\n\t\t\t\t\t\t\t\t\t<label for=\""+questionId+"\">"+db.getQuestion(questionId)+" "+requiredIcon+"</label>"
						+ "\n\t\t\t\t\t\t\t\t\t<select id=\""+questionId+"\" name=\""+questionId+"\" "+requiredText+">"
						+ "\n\t\t\t\t\t\t\t\t\t\t<option value=\"\" selected></option>";
				ArrayList<String> optionIds = db.getQuestionOptionIds(questionId);
				for(String optionId : optionIds) {
					String option = db.getOption(questionId, optionId);
					result += "\n\t\t\t\t\t\t\t\t\t\t<option value=\""+option+"\">"+option+"</option>";
				}
				
				result += "\n\t\t\t\t\t\t\t\t\t</select>"
						+ "\n\t\t\t\t\t\t\t\t</div>";
			} else if(questionType.equals("radio")) {
				
				result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
						+ "\n\t\t\t\t\t\t\t\t\t<label>"+db.getQuestion(questionId)+" "+requiredIcon+"</label>"
						+ "\n\t\t\t\t\t\t\t\t</div>";
				
				ArrayList<String> optionIds = db.getQuestionOptionIds(questionId);
				int i = 0;
				for(String optionId : optionIds) {
					String option = db.getOption(questionId, optionId);
					if(required && i == 0) {
						result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
								+ "\n\t\t\t\t\t\t\t\t\t<input type=\""+questionType+"\" id=\""+optionId+"\" name=\""+questionId+"\" value=\""+option+"\" "+requiredText+"/>"
								+ "\n\t\t\t\t\t\t\t\t\t<label for=\""+optionId+"\">"+option+"</label>"
								+ "\n\t\t\t\t\t\t\t\t</div>";
					} else {
						result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
								+ "\n\t\t\t\t\t\t\t\t\t<input type=\""+questionType+"\" id=\""+optionId+"\" name=\""+questionId+"\" value=\""+option+"\"/>"
								+ "\n\t\t\t\t\t\t\t\t\t<label for=\""+optionId+"\">"+option+"</label>"
								+ "\n\t\t\t\t\t\t\t\t</div>";
					}
					i++;
				}
				
			} else if(questionType.equals("checkbox")) {
				
				result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
						+ "\n\t\t\t\t\t\t\t\t\t<label>"+db.getQuestion(questionId)+" "+requiredIcon+"</label>"
						+ "\n\t\t\t\t\t\t\t\t</div>";
				
				ArrayList<String> optionIds = db.getQuestionOptionIds(questionId);
				for(String optionId : optionIds) {
					String option = db.getOption(questionId, optionId);
					result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
							+ "\n\t\t\t\t\t\t\t\t\t<input type=\""+questionType+"\" id=\""+optionId+"\" name=\""+questionId+"-checkbox\" value=\""+option+"\" />"
							+ "\n\t\t\t\t\t\t\t\t\t<label for=\""+optionId+"\">"+option+"</label>"
							+ "\n\t\t\t\t\t\t\t\t</div>";
				}
			} else if(questionType.equals("time")) {
				result += "\n\t\t\t\t\t\t\t\t<div class=\"col-2 col-4-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t\t<label for=\""+questionId+"\">"+db.getQuestion(questionId)+" "+requiredIcon+"</label>"
						+ "\n\t\t\t\t\t\t\t\t\t<input type=\""+questionType+"\" id=\""+questionId+"\" name=\""+questionId+"\" min=\""+db.getTimeStartPeriod(questionId)+"\" max=\""+db.getTimeEndPeriod(questionId)+"\" value=\"\" "+requiredText+"/>"
						+ "\n\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t<div class=\"col-10 col-8-mobile\">"
						+ "\n\t\t\t\t\t\t\t\t</div>";
			} else if(questionType.equals("paragraph")){
				result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
						+ "\n\t\t\t\t\t\t\t\t\t<label for=\""+questionId+"\">"+db.getQuestion(questionId)+" "+requiredIcon+"</label>"
						+ "\n\t\t\t\t\t\t\t\t\t<textarea id=\""+questionId+"\" name=\""+questionId+"\" rows=\"6\" "+requiredText+"></textarea>"
						+ "\n\t\t\t\t\t\t\t\t</div>";
			} else if(questionType.equals("countryNames")){
				result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
						+ "\n\t\t\t\t\t\t\t\t\t<label for=\""+questionId+"\">"+db.getQuestion(questionId)+" "+requiredIcon+"</label>"
						+ "\n\t\t\t\t\t\t\t\t\t<select id=\""+questionId+"\" name=\""+questionId+"\" "+requiredText+">";
				
				Library lib = new Library();
				lib.open();
				ArrayList<String> countries = lib.getCountryNames("jp");
				result += "\n\t\t\t\t\t\t\t\t\t\t<option value=\"\" selected></option>";
				for(String country : countries) {
					result += "\n\t\t\t\t\t\t\t\t\t\t<option value=\""+lib.getCountryCode(country, "jp")+"\">"+country+"</option>";
				}
				lib.close();
				
				result += "\n\t\t\t\t\t\t\t\t\t</select>"
						+ "\n\t\t\t\t\t\t\t\t</div>";
				
			} else if(questionType.equals("prefectures")) {
				result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
						+ "\n\t\t\t\t\t\t\t\t\t<label for=\""+questionId+"\">"+db.getQuestion(questionId)+" "+requiredIcon+"</label>"
						+ "\n\t\t\t\t\t\t\t\t\t<select id=\""+questionId+"\" name=\""+questionId+"\" "+requiredText+">";
				
				Library lib = new Library();
				lib.open();
				ArrayList<String> prefectureCodes = lib.getAllPrefectureCodes();
				result += "\n\t\t\t\t\t\t\t\t\t\t<option label=\"\" selected></option>";
				for (String prefectureCode : prefectureCodes) {
					result += "\n\t\t\t\t\t\t\t\t\t\t<option value=\"" + prefectureCode + "\">"+ lib.getPrefectureName(prefectureCode) + "</option>";
				}
				lib.close();
				
				result += "\n\t\t\t\t\t\t\t\t\t</select>"
						+ "\n\t\t\t\t\t\t\t\t</div>";
				
			} else if(questionType.equals("address")){
				result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
						+ "\n\t\t\t\t\t\t\t\t\t<label for=\""+questionId+"\"><h4>"+db.getQuestion(questionId)+" "+requiredIcon+"</h4></label>"
						+ "\n\t\t\t\t\t\t\t\t</div>"
					    + "\n\t\t\t\t\t\t\t\t<div class=\"col-3 col-4-mobile\">"
					    + "\n\t\t\t\t\t\t\t\t\t<input type=\"text\" id=\""+questionId+"-zip\" name=\""+questionId+"-zip\" maxlength=\"7\" placeholder=\"�X�֔ԍ�\""
					    + "\n\t\t\t\t\t\t\t\t\tonKeyUp=\"AjaxZip3.zip2addr(this,'','"+questionId+"','"+questionId+"')\" "
					    + "\n\t\t\t\t\t\t\t\t\tonBlur=\"AjaxZip3.zip2addr(this,'','"+questionId+"','"+questionId+"')\""+requiredText+" />"
					    + "\n\t\t\t\t\t\t\t\t</div>"
					    + "\n\t\t\t\t\t\t\t\t<div class=\"col-9 col-8-mobile\">"
					    + "\n\t\t\t\t\t\t\t\t\t<input type=\"text\" id=\""+questionId+"\" name=\""+questionId+"\" "+requiredText+" />"
					    + "\n\t\t\t\t\t\t\t\t</div>";
			} else {
				result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
						+ "\n\t\t\t\t\t\t\t\t\t<label for=\""+questionId+"\">"+db.getQuestion(questionId)+" "+requiredIcon+"</label>"
						+ "\n\t\t\t\t\t\t\t\t\t<input type=\""+questionType+"\" id=\""+questionId+"\" name=\""+questionId+"\" value=\"\" "+requiredText+"/>"
						+ "\n\t\t\t\t\t\t\t\t</div>";
			}
		}
		
		if(questionIds.size() > 0) {
			result += "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">" 
					+ "\n\t\t\t\t\t\t\t\t\t<label>�����A�t���K�i�A���ʁA���[���A�h���X�A���N�����ȊO�̓��e���C�x���g�y�[�W�Ɍ��J����O���t�ɒǉ����Ă���낵���ł����H  <span class=\"fa fa-asterisk\" style=\"color: #ff7496;\"></span></label>" 
					+ "\n\t\t\t\t\t\t\t\t</div>"
					+ "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
					+ "\n\t\t\t\t\t\t\t\t\t<input type=\"radio\" id=\"privacy-yes\" name=\"privacy\" value=\"yes\" required/>" 
					+ "\n\t\t\t\t\t\t\t\t\t<label for=\"privacy-yes\">�͂�</label>" 
					+ "\n\t\t\t\t\t\t\t\t</div>" 
					+ "\n\t\t\t\t\t\t\t\t<div class=\"col-12\">"
					+ "\n\t\t\t\t\t\t\t\t\t<input type=\"radio\" id=\"privacy-no\" name=\"privacy\" value=\"no\" />" 
					+ "\n\t\t\t\t\t\t\t\t\t<label for=\"privacy-no\">������</label>\r\n" 
					+ "\n\t\t\t\t\t\t\t\t</div>";
		}
		db.close();
		model.addAttribute("formTemplate", result);
		
		Events db1 = new Events();
		db1.open();
		model.addAttribute("eventName",db1.eventName(event));
		model.addAttribute("eventPlace",db1.eventPlace(event));
		model.addAttribute("eventDate",db1.eventDate(event));
		model.addAttribute("eventStartTime",db1.eventStartTime(event));
		model.addAttribute("eventEndTime",db1.eventEndTime(event));
		model.addAttribute("recruitmentStartDate",db1.recruitmentStartDate(event));
		model.addAttribute("recruitmentEndDate",db1.recruitmentEndDate(event));
		model.addAttribute("description",db1.eventDescription(event));
		model.addAttribute("recruitLimit",db1.recruitmentLimit(event));
		model.addAttribute("remainingRecruitLimit", db1.remainingRecruitmentLimit(event));
		db1.close();
		
	}
}
