package jp.myouth.tables;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import jp.myouth.db.Questions;

public class FormTemplateTitleAndDescription {
	
	public void initialize(ModelMap model, String templateId) throws SQLException {
		
		Questions db = new Questions();
		db.open();

		String title = db.getTitle(templateId);
		String description = db.getDescription(templateId);
		
		String result = "\n\t\t\t\t<section class=\"box\">"
						+ "\n\t\t\t\t\t<h3>"+title+"</h3>"
						+ "\n\t\t\t\t\t<p>"+description+"</p>"
						+ "\n\t\t\t\t</section>"; 
		
		db.close();
		
		model.addAttribute("formTemplateTitleAndDescription", result);
	}
}
