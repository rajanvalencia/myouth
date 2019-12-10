package jp.myouth.charts;

import java.util.ArrayList;

import jp.myouth.db.Answers;

public class Prefectures {

	public String generateJsContent(String questionId) {
		String result = new String();
		result = "\n\t<script>" 
				+ "\n\t\t// Themes begin" 
				+ "\n\t\tam4core.useTheme(am4themes_animated);"
				+ "\n\t\t// Themes end\n" 
				+ "\n\t\t// Create map instance"
				+ "\n\t\tvar chart = am4core.create(\"chartdiv\", am4maps.MapChart);" 
				+ "\n\t\t// Set map definition"
				+ "\n\t\tchart.geodataSource.url = 'https://www.amcharts.com/lib/4/geodata/json/japanLow.json';\r\n"
				+ "\n\t\t// Set projection" 
				+ "\n\t\tchart.projection = new am4maps.projections.Miller();"
				+ "\n\t\t// Create map polygon series"
				+ "\n\t\tvar polygonSeries = chart.series.push(new am4maps.MapPolygonSeries());"
				+ "\n\t\t//Set min/max fill color for each area" 
				+ "\n\t\tpolygonSeries.heatRules.push({"
				+ "\n\t\t\tproperty: \"fill\"," 
				+ "\n\t\t\ttarget: polygonSeries.mapPolygons.template,"
				+ "\n\t\t\tmin: chart.colors.getIndex(1).brighten(1),"
				+ "\n\t\t\tmax: chart.colors.getIndex(1).brighten(-0.3)" 
				+ "\n\t\t});\n"
				+ "\n\t\t// Make map load polygon data (state shapes and names) from GeoJSON"
				+ "\n\t\tpolygonSeries.useGeodata = true;" 
				+ "\n\t\t// Set heatmap values for each state"
				+ "\n\t\tpolygonSeries.data = [";
		Answers db = new Answers();
		db.open();
		int max = 1;
		int min = db.getTotalAnswersPerQuestion(questionId);
		ArrayList<String> answers = db.getGroupedAnswers(questionId);
		for (String answer : answers) {
			int total = db.getTotalAnswersPerAnswer(questionId, answer);
			result += "\n\t\t\t{" 
					+ "\n\t\t\t\tid : \"" + answer + "\"," 
					+ "\n\t\t\t\tname: \""+ db.getCountryName(answer, "jp") + "\"," 
					+ "\n\t\t\t\tvalue: " + total + "\n\t\t\t},";

			if (max < total)
				max = total;
			if (min > total)
				min = total;
		}
		db.close();
		result += "\n\t\t];\n" 
				+ "\n\t\t// Set up heat legend"
				+ "\n\t\tlet heatLegend = chart.createChild(am4maps.HeatLegend);"
				+ "\n\t\theatLegend.series = polygonSeries;" 
				+ "\n\t\theatLegend.align = \"right\";"
				+ "\n\t\theatLegend.valign = \"bottom\";" 
				+ "\n\t\theatLegend.width = am4core.percent(20);"
				+ "\n\t\theatLegend.marginRight = am4core.percent(4);" 
				+ "\n\t\theatLegend.minValue = "+min+";"
				+ "\n\t\theatLegend.maxValue = "+max+";"
				+ "\n\t\t// Set up custom heat map legend labels using axis ranges"
				+ "\n\t\tvar minRange = heatLegend.valueAxis.axisRanges.create();"
				+ "\n\t\tminRange.value = heatLegend.minValue;" 
				+ "\n\t\tminRange.label.text = "+min+";"
				+ "\n\t\tvar maxRange = heatLegend.valueAxis.axisRanges.create();"
				+ "\n\t\tmaxRange.value = heatLegend.maxValue;" 
				+ "\n\t\tmaxRange.label.text = "+max+";"
				+ "\n\t\t// Blank out internal heat legend value axis labels"
				+ "\n\t\theatLegend.valueAxis.renderer.labels.template.adapter.add(\"text\", function(labelText) {"
				+ "\n\t\t\treturn \"\";" 
				+ "\n\t\t});\n"
				+ "\n\t\t// Configure series tooltip"
				+ "\n\t\tvar polygonTemplate = polygonSeries.mapPolygons.template;"
				+ "\n\t\tpolygonTemplate.tooltipText = \"{name}: {value}\";"
				+ "\n\t\tpolygonTemplate.nonScalingStroke = true;" 
				+ "polygonTemplate.strokeWidth = 0.5;"
				+ "\n\t\t// Create hover state and set alternative fill color"
				+ "\n\t\tvar hs = polygonTemplate.states.create(\"hover\");"
				+ "\n\t\ths.properties.fill = am4core.color(\"#3c5bdc\");"
				+ "\n\t</script>";
		
		return result;
	}
}
