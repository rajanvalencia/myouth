package jp.myouth.charts;

import java.util.ArrayList;

import jp.myouth.db.Answers;

public class SimplePieChart {
	
	public String generateJsContent(String questionId, String theme) {
		String result = new String();
		
		result += "\n\t<script>" 
				+ "\n\tam4core.ready(function() {\n" 
				+ "\n\t\t// Themes begin"
				+ theme
				+ "\n\t\tam4core.useTheme(am4themes_animated);" 
				+ "\n\t\t// Themes end\n" 
				+ "\n\t\t// Create chart instance\r\n" 
				+ "\n\t\tvar chart = am4core.create(\""+questionId+"\", am4charts.PieChart);\n" 
				+ "\n\t\t// Add data" 
				+ "\n\t\tchart.data = [";
		Answers db = new Answers();
		db.open();
		ArrayList<String> options = db.getGroupedOptionsAnswers(questionId);
		for(String option : options) {
			int total = db.getTotalAnswersPerOption(questionId, option);
			result += "\n\t\t\t{"
					+ "\n\t\t\t\tname: \""+option+"\","
					+ "\n\t\t\t\tvalue: "+total
					+ "\n\t\t\t},";
		}
		db.close();
		
		result += "\n\t\t];\n" 
			 	+ "\n\t\t// Add and configure Series" 
			 	+ "\n\t\tvar pieSeries = chart.series.push(new am4charts.PieSeries());" 
			 	+ "\n\t\tpieSeries.dataFields.value = \"value\";" 
			 	+ "\n\t\tpieSeries.dataFields.category = \"name\";" 
			 	+ "\n\t\tpieSeries.slices.template.stroke = am4core.color(\"#fff\");" 
			 	+ "\n\t\tpieSeries.slices.template.strokeWidth = 2;" 
			 	+ "\n\t\tpieSeries.slices.template.strokeOpacity = 1;\n"  
			 	+ "\n\t\t// This creates initial animation" 
			 	+ "\n\t\tpieSeries.hiddenState.properties.opacity = 1;" 
			 	+ "\n\t\tpieSeries.hiddenState.properties.endAngle = -90;" 
			 	+ "\n\t\tpieSeries.hiddenState.properties.startAngle = -90;\n"  
			 	+ "\n\t\t// Disable ticks and labels" 
			 	+ "\n\t\tpieSeries.labels.template.disabled = true;" 
			 	+ "\n\t\tpieSeries.ticks.template.disabled = true;\n" 
			 	+ "\n\t\t// Disable tooltips\n" 
			 	+ "\n\t\tpieSeries.slices.template.tooltipText = \"\";\n" 
			 	+ "\n\t\t// Add a legend" 
			 	+ "\n\t\tchart.legend = new am4charts.Legend();"
			 	+ "\n\t}); // end am4core.ready()\r\n" 
			 	+ "\n\t</script>";
		
		return result;
	}
	
	public String generateHtmlContent(String questionId, String question) {
		Answers db = new Answers();
		db.open();
		ArrayList<String> options = db.getGroupedOptionsAnswers(questionId);
		db.close();
		
		String htmlContent = "\n\t\t\t\t\t<h5>\n\t\t\t\t\t\t" + question + "\n\t\t\t\t\t</h5>";
		
		if(options.size() == 0) {
			htmlContent += "\n\t\t\t\t\t<div class=\"am-charts-pie-charts\" id=\"" + questionId + "\" hidden></div>"
						+ "\n\t\t\t\t\t<p>データはありません</p>";
		} else {
			htmlContent += "\n\t\t\t\t\t<div class=\"am-charts-pie-charts\" id=\"" + questionId + "\"></div>";
		}
		return htmlContent;
	}
	
	public String userPageGenerateHtmlContent(String questionId, String question, Boolean publicOption) {
		
		String checkedHtml = "";
		if(publicOption)
			checkedHtml = "checked";
			
		return 	"\n\t\t\t\t\t\t<h3 style=\"text-align: center;\">"+question+"</h3>"
				+ "\n\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
				+ "\n\t\t\t\t\t\t\t<div class=\"col-8 col-5-mobile\">"
				+ "\n\t\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t\t<div class=\"col-1 col-3-mobile\">"
				+ "\n\t\t\t\t\t\t\t\t<h5>公開</h5>"
				+ "\n\t\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
				+ "\n\t\t\t\t\t\t\t\t<label class=\"switch\">"
				+ "\n\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" "+checkedHtml+" onclick=\"var attr = $(this).attr('checked'); if (typeof attr !== typeof undefined && attr !== false) {ajaxEditGraphPublicOption('"+questionId+"', false); $(this).attr('checked', false);} else {ajaxEditGraphPublicOption('"+questionId+"', true); $(this).attr('checked', true);}\">" 
				+ "\n\t\t\t\t\t\t\t\t\t<span class=\"slider round\"></span>"
				+ "\n\t\t\t\t\t\t\t\t</label>"
				+ "\n\t\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t<div class=\"am-charts-pie-charts\" id=\""+questionId+"\"></div>";
	}
	
	public String pieChartTheme(int themeNo) {

		String defaultTheme = "";
		String materialTheme = "\n\t\tam4core.useTheme(am4themes_material);";
		String frozenTheme = "\n\t\tam4core.useTheme(am4themes_frozen);";

		switch (themeNo % 3) {
		case 1:
			return materialTheme;
		case 2:
			return frozenTheme;
		default:
			return defaultTheme;
		}
	}
}
