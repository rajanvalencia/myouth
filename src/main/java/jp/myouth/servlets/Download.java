package jp.myouth.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.myouth.db.Events;
import jp.myouth.db.Participants;
import jp.myouth.downloads.CSV;
import jp.myouth.downloads.PDF;

@WebServlet("/download")
public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Boolean user = (Boolean) session.getAttribute("user");
		if (!user)
			response.sendRedirect("/login");
		
		String event = (String) session.getAttribute("event");
		String startPeriod;
		String endPeriod;
		
		request.setCharacterEncoding("UTF-8");
		String dataType = request.getParameter("dataType");
		String periodType = request.getParameter("periodType");
		String fileType = request.getParameter("fileType");
		String fontSize = request.getParameter("fontSize");

		
		if(periodType.equals("freePeriod")){
			startPeriod = request.getParameter("startYear")+"-"+request.getParameter("startMonth")+"-"+request.getParameter("startDay");
			endPeriod = request.getParameter("endYear")+"-"+request.getParameter("endMonth")+"-"+request.getParameter("endDay");	
		} else if (periodType.equals("recruitmentPeriod")) {
			Events db = new Events();
			db.open();
			startPeriod = db.recruitmentStartDate(event).get(0) + "-" + db.recruitmentStartDate(event).get(1) + "-"
					+ db.recruitmentStartDate(event).get(2);
			endPeriod = db.recruitmentEndDate(event).get(0) + "-" + db.recruitmentEndDate(event).get(1) + "-"
					+ db.recruitmentEndDate(event).get(2);
			db.close();
		} else /*else if(periodType.equals("allPeriod"))*/ {
			startPeriod = null;
			endPeriod = null;
		}

		ArrayList<String> data = new ArrayList<String>();
		int total;

		Participants db1 = new Participants();
		db1.open();
		if (dataType.equals("applicationFormData")) {
			data = db1.participants(event, periodType, startPeriod, endPeriod);
			total = db1.totalParticipants(event, periodType, startPeriod, endPeriod);
		} else /*else if(dataType.equals("surveyData"))*/ {
			data = db1.survey(event, periodType, startPeriod, endPeriod);
			total = db1.totalSurveyAnswers(event, periodType, startPeriod, endPeriod);
		}
		db1.close();

		if (fileType.equals("csv")) {
			CSV csv = new CSV();
			csv.createCSVFile(response, event, data, total);
		} else /*else  if(fileType.equals("pdf"))*/ {
			PDF pdf = new PDF();
			try {
				pdf.createPDFFile(response, event, data, total, fontSize);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
