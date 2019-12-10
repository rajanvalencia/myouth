package com.aws.codestar.projecttemplates.controller;

import java.util.ArrayList;

import org.springframework.ui.ModelMap;

import jp.myouth.charts.RotatingGlobe;
import jp.myouth.charts.SimplePieChart;
import jp.myouth.db.Answers;
import jp.myouth.db.Events;
import jp.myouth.db.Questions;

public class EventPage {

	public void initialize(ModelMap model, String event) {
		Events db = new Events();
		db.open();
		model.addAttribute("event", event);
		model.addAttribute("eventName", db.eventName(event));
		model.addAttribute("description", db.eventDescription(event));
		model.addAttribute("eventLogoUrl", db.eventLogo(event));
		model.addAttribute("eventPlace", db.eventPlace(event));
		model.addAttribute("eventDate", db.eventDate(event));
		model.addAttribute("startTime", db.formattedEventStartTime(event));
		model.addAttribute("endTime", db.formattedEventEndTime(event));
		model.addAttribute("recruitmentStartDate", db.recruitmentStartDate(event));
		model.addAttribute("recruitmentEndDate", db.recruitmentEndDate(event));
		model.addAttribute("recruitLimit", db.recruitmentLimit(event));
		model.addAttribute("remainingRecruitLimit", db.remainingRecruitmentLimit(event));
		String instagramUrl = db.instagramUrl(event);
		String facebookUrl = db.facebookUrl(event);
		String twitterUrl = db.twitterUrl(event);
		
		ArrayList<String> memberData = db.eventPageMember(event);
		
		ArrayList<String> imageLists = db.getImage(event);
		db.close();
		
		//Generate public forms html content
		String forms = new String();
		Questions db1 = new Questions();
		db1.open();
		ArrayList<String> publicTemplateIds = db1.getPublicTemplateIds(event);
		for (String templateId : publicTemplateIds) {
			forms += "\n\t\t\t\t<p><span class=\"fa fa-file-alt\"></span> <a href=\"/events/" + event + "/forms/"
					+ templateId + "\">" + db1.getTitle(templateId) + "</a></p>";
		}
		
		ArrayList<String> templateIds = db1.getTemplateIds(event);
		db1.close();
		model.addAttribute("forms", forms);

		//Generate social links html content
		String socialLinks = "\n\t\t<div class=\"bottom\">" + "\n\t\t\t<ul class=\"icons\">" + "\n\t\t\t\t<li>"
				+ "\n\t\t\t\t\t<a href=\"/\" class=\"icon fa-home\"><span class=\"label\">Home</span></a>"
				+ "\n\t\t\t\t</li>";

		if (instagramUrl.length() > 0) {
			socialLinks += "\n\t\t\t\t<li>" 
						+ "\n\t\t\t\t\t<a href=\"" + instagramUrl+ "\" class=\"icon fa-instagram\"><span class=\"label\">Instagram</span></a>" 
						+ "\n\t\t\t\t</li>";
		}
		if (facebookUrl.length() > 0) {
			socialLinks += "\n\t\t\t\t<li>" 
						+ "\n\t\t\t\t\t<a href=\"" + facebookUrl+ "\" class=\"icon fa-facebook\"><span class=\"label\">Facebook</span></a>" 
						+ "\n\t\t\t\t</li>";
		}
		if (twitterUrl.length() > 0) {
			socialLinks += "\n\t\t\t\t<li>" 
						+ "\n\t\t\t\t\t<a href=\"" + twitterUrl + "\" class=\"icon fa-twitter\"><span class=\"label\">Twitter</span></a>" 
						+ "\n\t\t\t\t</li>";
		}
		socialLinks += "\n\t\t\t</ul>" 
					+ "\n\t\t</div>";
		model.addAttribute("socialLinks", socialLinks);
		
		//Generate member lists html content
		int i = 0;
		int row2 = 0, row3 = 0,  len = 0;
		len = memberData.size();
		
		if((memberData.size() / 2)  % 3 == 0) {
			row2 = memberData.size() / 3;
			row3 = (memberData.size() / 3) * 2;
		} else if((memberData.size() / 2)  % 3 == 1) {
			row2 = (memberData.size() / 3) + 2;
			row3 = (row2 * 2) - 2;
		} else {
			row2 = (memberData.size() / 3) + 1;
			row3 = row2 * 2;
		}
		
		String memberLists = new String();
		for (String string : memberData) {
			if (i == 0 || i == row2 || i == row3)
				memberLists += "\n\t\t\t\t\t<div class=\"col-4 col-12-mobile\">";

			if (i % 2 == 0) {
				memberLists += "\n\t\t\t\t\t\t<article class=\"item\">"
						+ "\n\t\t\t\t\t\t\t<div class=\"image fit\">"
						+ "\n\t\t\t\t\t\t\t\t<div class=\"row gtr-uniform gtr-50\">"
						+ "\n\t\t\t\t\t\t\t\t\t<div class=\"col-1-narrower\">"
						+ "\n\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t<div class=\"col-5\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t<img class=\"member-image\" src=\""+ string + "\" alt=\"\" />"
						+ "\n\t\t\t\t\t\t\t\t\t</div>";
			} else if (i % 2 == 1 && string != null) {
				memberLists += "\n\t\t\t\t\t\t\t\t\t<div class=\"col-6\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t<div class=\"member-image-text\">"
						+ "\n\t\t\t\t\t\t\t\t\t\t\t"+string
						+ "\n\t\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t\t</article>";
			} else {
				memberLists += "\n\t\t\t\t\t\t</article>";
			}

			if (i == row2 - 1 || i == row3 - 1 || i == len - 1)
				memberLists += "\n\t\t\t\t\t</div>";
			i++;
		}
		model.addAttribute("memberLists", memberLists);
		
		//Generate event images html content
		i = 0;
		row2 = 0;
		row3 = 0; 
		len = imageLists.size();
		
		if((imageLists.size() / 2)  % 3 == 0) {
			row2 = imageLists.size() / 3;
			row3 = (imageLists.size() / 3) * 2;
		} else if((imageLists.size() / 2)  % 3 == 1) {
			row2 = (imageLists.size() / 3) + 2;
			row3 = (row2 * 2) - 2;
		} else {
			row2 = (imageLists.size() / 3) + 1;
			row3 = row2 * 2;
		}

		String eventImageLists = new String();
		for (String string : imageLists) {
			if (i == 0 || i == row2 || i == row3)
				eventImageLists += "\n\t\t\t\t\t<div class=\"col-4 col-12-mobile\">";

			if (i % 2 == 0) {
				eventImageLists += "\n\t\t\t\t\t\t<article class=\"item\">";
				eventImageLists += "\n\t\t\t\t\t\t\t<a href=\"#\" class=\"image fit\">";
				eventImageLists += "<img src=\""+ string + "\" alt=\"\" />";
				eventImageLists += "</a>";
			} else if (i % 2 == 1 && string != null) {
				eventImageLists += "<header><h3>" + string + "</h3></header>";
				eventImageLists += "\n\t\t\t\t\t\t</article>";
			} else {
				eventImageLists += "\n\t\t\t\t\t\t</article>";
			}

			if (i == row2 - 1 || i == row3 - 1 || i == len - 1)
				eventImageLists += "\n\t\t\t\t\t</div>";
			i++;
		}
		model.addAttribute("eventImageLists", eventImageLists);
		
		//Generate event ratings html content
		String reviews = "<section id=\"reviews\" class=\"four\">"
				+ "\n\t\t\t<div class=\"container\">"
				+ "\n\t\t\t\t<header>"
				+ "\n\t\t\t\t\t<h2>評価</h2>"
				+ "\n\t\t\t\t</header>";
		
		Answers db2 = new Answers();
		db2.open();
		for (String templateId : templateIds) {
			int totalReviews = db2.getTotalReviews(templateId);
			double averageRating = db2.getReviewsAverageStars(templateId);
			
			reviews += "\n\t\t\t\t<p>"
					+ "\n\t\t\t\t\t" + totalReviews + " 件中平均満足度は " + averageRating + ""
					+ "\n\t\t\t\t\t<br />";
			
			for (i = 0; i < 5; i++) {
				if (i + 1 <= averageRating)
					reviews += "\n\t\t\t\t\t<span class=\"fa fa-star light\"></span>";
				else {
					if ((double) i + 1 - 0.5 <= averageRating) {
						reviews += "\n\t\t\t\t\t<span class=\"fa fa-star-half light\"></span>";
					} else
						reviews += "\n\t\t\t\t\t<span class=\"fa fa-star dark\"></span>";
				}
			}

			reviews += "\n\t\t\t\t</p>"
					+ "\n\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">";

			while(i > 0) {
				int totalAnswersByStar = db2.getTotalAnswersByStar(templateId, i);
				reviews += "\n\t\t\t\t\t<div class=\"col-1\">"
						+ "\n\t\t\t\t\t\t"+i
						+ "\n\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t<div class=\"col-1\">"
						+ "\n\t\t\t\t\t\t<span class=\"fa fa-star light\"></span>"
						+ "\n\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t<div class=\"col-8 col-8-mobile\">"
						+ "\n\t\t\t\t\t\t<div class=\"bar-container\">"
						+ "\n\t\t\t\t\t\t<div class=\"bar-"+i+"\" style=\"width: "+db2.getStarPercentageOfStarsTotalAnswer(templateId, i--)+"%;\"></div>"
						+ "\n\t\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t</div>"
						+ "\n\t\t\t\t\t<div class=\"col-2 col-2-mobile\">"
						+ "\n\t\t\t\t\t\t<span>"+totalAnswersByStar+"</span>"
						+ "\n\t\t\t\t\t</div>";
			}
			reviews += "\n\t\t\t\t</div>"
					+ "\n\t\t\t\t<br /";
	
			ArrayList<String> reviewIds = db2.getReviewIds(templateId);
			if (reviewIds.size() == 0) {
				reviews += "\n\t\t\t\tレビューがありません";
			} else {
				
				reviews += "\n\t\t\t\t<div class=\"table-wrapper\">"
						+ "\n\t\t\t\t\t<table>"
						+ "\n\t\t\t\t\t\t<tbody>";
						
				for (String reviewId : reviewIds) {
						reviews += "\n\t\t\t\t\t\t\t<tr style=\"background-color: #fff;\">"
								+ "\n\t\t\t\t\t\t\t\t<td>"
								+ "\n\t\t\t\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\" style=\"border: 1px solid #d7d8da; border-radius: 8px;\">"
								+ "\n\t\t\t\t\t\t\t\t\t\t<div class=\"col-10 col-8-mobile\">";
						
						int star = db2.getStar(templateId, reviewId);
						for(i = 0; i < star; i++) {
							reviews += "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star light\"></span>";
						}
						for(;i < 5; i++) {
							reviews += "\n\t\t\t\t\t\t\t\t\t\t\t<span class=\"fa fa-star dark\"></span>";
						}
						
						reviews += "\n\t\t\t\t\t\t\t\t\t\t</div>"
								+ "\n\t\t\t\t\t\t\t\t\t\t<div class=\"col-2 col-4-mobile\">"
								+ "\n\t\t\t\t\t\t\t\t\t\t\t"+db2.getReviewDate(templateId, reviewId)
								+ "\n\t\t\t\t\t\t\t\t\t\t</div>"
								+ "\n\t\t\t\t\t\t\t\t\t\t<div class=\"col-12\">"
								+ "\n\t\t\t\t\t\t\t\t\t\t\t" + db2.getReview(templateId, reviewId).replaceAll("\n", "\n\t\t\t\t\t\t\t<br />") 
								+ "\n\t\t\t\t\t\t\t\t\t\t</div>"
								+ "\n\t\t\t\t\t\t\t\t\t</div>"
								+ "\n\t\t\t\t\t\t\t\t</td>"
								+ "\n\t\t\t\t\t\t\t</tr>";
				}
				reviews += "\n\t\t\t\t\t\t</tbody>"
						+ "\n\t\t\t\t\t</table>"
						+ "\n\t\t\t\t</div>"
						+ "\n\t\t\t</div>"
						+ "\n\t\t</section>";
			}
		}
		db2.close();
		
		model.addAttribute("reviews", reviews);
		
		//Generate analytics js & html content
		String JsContent = new String();
		String HtmlContent = new String();

		RotatingGlobe rg = new RotatingGlobe();
		SimplePieChart spc = new SimplePieChart();

		db1.open();
		templateIds = db1.getTemplateIds(event);
		for (String templateId : templateIds) {
			ArrayList<String> questionIds = db1.getPublicGraphQuestionIds(templateId);
			int themeNo = 0;
			if(questionIds.size() == 0)
				HtmlContent = "<p>分析するデータがありません</p>";
			for (String questionId : questionIds) {
				String questionType = db1.getQuestionType(questionId);
				if (questionType.equals("countryNames")) {
					JsContent += rg.generateJsContent(questionId);
					HtmlContent += rg.generateHtmlContent(questionId, db1.getQuestion(questionId));
				} else if (questionType.equals("pulldown") || questionType.equals("radio")
						|| questionType.equals("checkbox")) {
					JsContent += spc.generateJsContent(questionId, spc.pieChartTheme(themeNo++));
					HtmlContent += spc.generateHtmlContent(questionId, db1.getQuestion(questionId));
				}
			}
		}
		db1.close();
		
		model.addAttribute("eventPageAnalyticsHtmlContent", HtmlContent);
		model.addAttribute("eventPageAnalyticsJsContent", JsContent);
	}
}
