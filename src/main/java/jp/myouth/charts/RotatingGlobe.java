package jp.myouth.charts;

import java.util.ArrayList;

import jp.myouth.db.Answers;

public class RotatingGlobe {
	
	public String generateJsContent(String questionId) {
		String result = new String();
		result = "\n\t<script>"
				+ "\n\tam4core.ready(function() {"
				+ "\n\t\t// Themes begin"
				+ "\n\t\tam4core.unuseTheme(am4themes_material);"
				+ "\n\t\tam4core.unuseTheme(am4themes_frozen);"
				+ "\n\t\tam4core.useTheme(am4themes_animated);" 
				+ "\n\t\t// Themes end"
				+ "\n\t\tvar chart = am4core.create(\""+questionId+"\", am4maps.MapChart);\n" 
				+ "\n\t\t// Set map definition" 
				+ "\n\t\tchart.geodata = am4geodata_worldLow;\n" 
				+ "\n\t\t// Set projection" 
				+ "\n\t\tchart.projection = new am4maps.projections.Orthographic();" 
				+ "\n\t\tchart.panBehavior = \"rotateLongLat\";" 
				+ "\n\t\tchart.deltaLatitude = -20;"
				+ "\n\t\tchart.deltaLongitude = -120;"
				+ "\n\t\tchart.padding(20,20,20,20);\n" 
				+ "\n\t\t// Create map polygon series" 
				+ "\n\t\tvar polygonSeries = chart.series.push(new am4maps.MapPolygonSeries());\n" 
				+ "\n\t\t// Make map load polygon (like country names) data from GeoJSON"
				+ "\n\t\tpolygonSeries.useGeodata = true;\n" 
				+ "\n\t\t// Configure series" 
				+ "\n\t\tvar polygonTemplate = polygonSeries.mapPolygons.template;" 
				+ "\n\t\tpolygonTemplate.tooltipText = \"{name}\";" 
				+ "\n\t\tpolygonTemplate.fill = am4core.color(\"#47c78a\");" 
				+ "\n\t\tpolygonTemplate.stroke = am4core.color(\"#454a58\");" 
				+ "\n\t\tpolygonTemplate.strokeWidth = 0.5;\n" 
				+ "\n\t\tvar graticuleSeries = chart.series.push(new am4maps.GraticuleSeries());" 
				+ "\n\t\tgraticuleSeries.mapLines.template.line.stroke = am4core.color(\"#ffffff\");" 
				+ "\n\t\tgraticuleSeries.mapLines.template.line.strokeOpacity = 0.08;" 
				+ "\n\t\tgraticuleSeries.fitExtent = false;" 
				+ "\n\t\tchart.backgroundSeries.mapPolygons.template.polygon.fillOpacity = 1;" 
				+ "\n\t\tchart.backgroundSeries.mapPolygons.template.polygon.fill = am4core.color(\"#454a58\");\n" 
				+ "\n\t\t// Create hover state and set alternative fill color" 
				+ "\n\t\tvar hs = polygonTemplate.states.create(\"hover\");" 
				+ "\n\t\ths.properties.fill = chart.colors.getIndex(0).brighten(-0.5);" 
				+ "\n\t\tlet animation;" 
				+ "\n\t\t$('#"+questionId+"-btn').click(function(){"
				+ "\n\t\t\tvar attr = $(this).attr('checked');"
				+ "\n\t\t\tif (typeof attr !== typeof undefined && attr !== false) {"
				+ "\n\t\t\t\tanimation.stop();" 
				+ "\n\t\t\t\t$(this).attr('checked', false);"
				+ "\n\t\t\t} else {"
				+ "\n\t\t\t\tanimation = chart.animate({property:\"deltaLongitude\", to:100000}, 10000000);" 
				+ "\n\t\t\t\t$(this).attr('checked', true);"
				+ "\n\t\t\t}"
				+ "\n\t\t});"
				+ "\n\t\t// Series for World map" 
				+ "\n\t\tvar worldSeries = chart.series.push(new am4maps.MapPolygonSeries());" 
				+ "\n\t\tworldSeries.useGeodata = true;" 
				+ "\n\t\tworldSeries.heatRules.push({" 
				+ "\n\t\t\tproperty: \"fill\"," 
				+ "\n\t\t\ttarget: worldSeries.mapPolygons.template," 
				+ "\n\t\t\tmin: chart.colors.getIndex(12).brighten(1)," 
				+ "\n\t\t\tmax: chart.colors.getIndex(17).brighten(-0.3)" 
				+ "\n\t\t});\n" 
				+ "\n\t\t// Set heatmap values for each state" 
				+ "\n\t\tworldSeries.data = ["; 
		
		Answers db = new Answers();
		db.open();
		int max = 1;
		int min = db.getTotalAnswersPerQuestion(questionId);
		ArrayList<String> answers = db.getGroupedAnswers(questionId);
		for(String answer : answers) {
			int total = db.getTotalAnswersPerAnswer(questionId, answer);
			result += "\n\t\t\t{"
					+ "\n\t\t\t\tid : \""+answer+"\","
					+ "\n\t\t\t\tname: \""+db.getCountryName(answer, "jp")+"\","
					+ "\n\t\t\t\tvalue: "+total
					+ "\n\t\t\t},";
			
			if(max < total)
				max = total;
			if(min > total)
				min = total;
		}
		db.close();		
				
		result += "\n\t\t];\n"
				+ "\n\t\t// Set up heat legend" 
				+ "\n\t\tlet heatLegend = chart.createChild(am4maps.HeatLegend);" 
				+ "\n\t\theatLegend.series = worldSeries;" 
				+ "\n\t\theatLegend.align = \"right\";" 
				+ "\n\t\theatLegend.valign = \"bottom\";" 
				+ "\n\t\theatLegend.width = am4core.percent(20);" 
				+ "\n\t\theatLegend.marginRight = am4core.percent(4);" 
				+ "\n\t\theatLegend.minValue = "+min+";" 
				+ "\n\t\theatLegend.maxValue = "+max+";\n" 
				+ "\n\t\t// Set up custom heat map legend labels using axis ranges" 
				+ "\n\t\tvar minRange = heatLegend.valueAxis.axisRanges.create();" 
				+ "\n\t\tminRange.value = heatLegend.minValue;" 
				+ "\n\t\tminRange.label.text = \""+min+"\";" 
				+ "\n\t\tvar maxRange = heatLegend.valueAxis.axisRanges.create();" 
				+ "\n\t\tmaxRange.value = heatLegend.maxValue;" 
				+ "\n\t\tmaxRange.label.text = \""+max+"\";\n" 
				+ "\n\t\t// Blank out internal heat legend value axis labels" 
				+ "\n\t\theatLegend.valueAxis.renderer.labels.template.adapter.add(\"text\", function(labelText) {" 
				+ "\n\t\t\treturn \"\";" 
				+ "\n\t\t});\n" 
				+ "\n\t\t// Configure series tooltip" 
				+ "\n\t\tvar polygonTemplate = worldSeries.mapPolygons.template;" 
				+ "\n\t\tpolygonTemplate.tooltipText = \"{name}: {value}\";" 
				+ "\n\t\tpolygonTemplate.nonScalingStroke = true;" 
				+ "\n\t\tpolygonTemplate.strokeWidth = 0.5;" 
				+ "\n\t}); // end am4core.ready()"
				+ "\n\t</script>";
		
		return result;
	}
	
	public String generateHtmlContent(String questionId, String question) {
		return    "\n\t\t\t\t\t<h5>\n\t\t\t\t\t\t" + question + "\n\t\t\t\t\t</h5>"
				+ "\n\t\t\t\t\t<div class=\"row gtr-uniform gtr-50\">"
				+ "\n\t\t\t\t\t\t<div class=\"col-7 col-6-mobile\">"
			    + "\n\t\t\t\t\t\t</div>"
			    + "\n\t\t\t\t\t\t<div class=\"col-1-and-half col-3-mobile\">"
			    + "\n\t\t\t\t\t\t\t<h5>自動回転</h5>"
			    + "\n\t\t\t\t\t\t</div>"
			    + "\n\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
			    + "\n\t\t\t\t\t\t\t<label class=\"switch\">"
			    + "\n\t\t\t\t\t\t\t\t<input type=\"checkbox\" id=\""+questionId+"-btn\">"
			    + "\n\t\t\t\t\t\t\t\t<span class=\"slider round\"></span>"
			    + "\n\t\t\t\t\t\t\t</label>"
			    + "\t\t\t\t\t\t\t</div>"
			    + "\n\t\t\t\t\t\t<div class=\"col-2 col-1-mobile\">"
			    + "\n\t\t\t\t\t\t</div>"
			    + "\n\t\t\t\t\t</div>"
			    + "\n\t\t\t\t\t<div class=\"am-charts\" id=\"" + questionId + "\"></div>";
	}
	
	public String userPageGenerateHtmlContent(String questionId, String question, Boolean publicOption) {
		
		String checkedHtml = "";
		if(publicOption)
			checkedHtml = "checked";
			
		return 	"\n\t\t\t\t\t\t<h3 style=\"text-align: center;\">"+question+"</h3>"
				+ "\n\t\t\t\t\t\t<div class=\"row gtr-50 gtr-uniform\">"
				+ "\n\t\t\t\t\t\t\t<div class=\"col-6 col-1-mobile\">"
				+ "\n\t\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t<div class=\"col-1-and-half col-2-and-half-mobile\">"
			    + "\n\t\t\t\t\t\t\t<h5>自動回転</h5>"
			    + "\n\t\t\t\t\t\t</div>"
			    + "\n\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
			    + "\n\t\t\t\t\t\t\t<label class=\"switch\">"
			    + "\n\t\t\t\t\t\t\t\t<input type=\"checkbox\" id=\""+questionId+"-btn\">"
			    + "\n\t\t\t\t\t\t\t\t<span class=\"slider round\"></span>"
			    + "\n\t\t\t\t\t\t\t</label>"
			    + "\t\t\t\t\t\t\t</div>"
			    + "\n\t\t\t\t\t\t<div class=\"col-0 col-1-mobile\">"
			    + "\n\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
				+ "\n\t\t\t\t\t\t\t\t<h5>公開</h5>"
				+ "\n\t\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t\t<div class=\"col-1 col-2-mobile\">"
				+ "\n\t\t\t\t\t\t\t\t<label class=\"switch\">"
				+ "\n\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" "+checkedHtml+" onclick=\"var attr = $(this).attr('checked'); if (typeof attr !== typeof undefined && attr !== false) {ajaxEditGraphPublicOption('"+questionId+"', false); $(this).attr('checked', false);} else {ajaxEditGraphPublicOption('"+questionId+"', true); $(this).attr('checked', true);}\">" 
				+ "\n\t\t\t\t\t\t\t\t\t<span class=\"slider round\"></span>"
				+ "\n\t\t\t\t\t\t\t\t</label>"
				+ "\n\t\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t</div>"
				+ "\n\t\t\t\t\t\t<div class=\"am-charts\" id=\""+questionId+"\"></div>";
	}
}
