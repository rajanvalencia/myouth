package jp.myouth.tables;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import jp.myouth.charts.RotatingGlobe;
import jp.myouth.charts.SimplePieChart;
import jp.myouth.db.Analytics;
import jp.myouth.db.Credentials;
import jp.myouth.db.Questions;
import jp.myouth.security.GenerateSecureString;

public class AnalyticsPageGenerateEventCharts {
	
	
	public Boolean append(HttpServletRequest request, HttpServletResponse response, ModelMap model, String templateId) {
		
		GenerateSecureString gen = new GenerateSecureString();
		String apiKey = gen.string(100);
		
		String userId = (String) request.getSession().getAttribute("userId");
		Credentials db = new Credentials();
		db.open();
		db.insertAjaxApiKey(userId, apiKey);
		db.close();
		
		String JsContent = new String();
		String HtmlContent = new String();
		
		RotatingGlobe rg = new RotatingGlobe();
		SimplePieChart spc = new SimplePieChart();
		
		Questions db1 = new Questions();
		db1.open();
		ArrayList<String> questionIds = db1.getQuestionIds(templateId);
		int themeNo = 0;
		for(String questionId : questionIds) {
			Analytics db2 = new Analytics();
			db2.open();
			Boolean publicOption = db2.getGraphPublicOption(templateId, questionId);
			db2.close();
			String questionType = db1.getQuestionType(questionId);
			if(questionType.equals("countryNames")) {
				JsContent += rg.generateJsContent(questionId);
				HtmlContent += rg.userPageGenerateHtmlContent(questionId, db1.getQuestion(questionId), publicOption);
			} else if(questionType.equals("pulldown") || questionType.equals("radio") || questionType.equals("checkbox")) {
				JsContent += spc.generateJsContent(questionId, pieChartTheme(themeNo++));
				HtmlContent += spc.userPageGenerateHtmlContent(questionId, db1.getQuestion(questionId), publicOption);
			}
		}
		db1.close();
		model.addAttribute("analyticsPageHtmlContent", HtmlContent);
		model.addAttribute("analyticsPageJsContent", JsContent);
		model.addAttribute("templateId", "sessionStorage.setItem('templateId', '"+templateId+"');");
		model.addAttribute("apiKey", "sessionStorage.setItem('apiKey', '"+apiKey+"');");
		return true;
	}
	
	public static String pieChartTheme(int themeNo) {
		
		String defaultTheme = "";
		String materialTheme = "\n\t\tam4core.useTheme(am4themes_material);";
		String frozenTheme = "\n\t\tam4core.useTheme(am4themes_frozen);";
		
		switch(themeNo % 3) {
			case 1 : 
				return materialTheme;
			case 2 : 
				return frozenTheme;
			default : 
				return defaultTheme;
		}
	}
	
}
