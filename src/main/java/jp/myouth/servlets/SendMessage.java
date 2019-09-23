package jp.myouth.servlets;

import java.io.IOException;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.time.StopWatch;

import jp.myouth.db.Events;
import jp.myouth.db.Participants;
import jp.myouth.db.User;
import jp.myouth.mailTemplates.MailToAllParticipants;

@WebServlet("/sendMessage")
public class SendMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();	
		
		Boolean user = (Boolean) session.getAttribute("user");
		if (!user)
			response.sendRedirect("/login");
		
		String userId = (String)session.getAttribute("userId");
		String event = (String) session.getAttribute("event");
		
		ArrayList<String> TO = new ArrayList<String>();
		
		String startPeriod = new String();
		String endPeriod  = new String();
		
		request.setCharacterEncoding("UTF-8");
		String periodType = request.getParameter("periodType");
		
		if(periodType.equals("freePeriod")){
			startPeriod = request.getParameter("startYear")+"-"+request.getParameter("startMonth")+"-"+request.getParameter("startDay");
			endPeriod = request.getParameter("endYear")+"-"+request.getParameter("endMonth")+"-"+request.getParameter("endDay");	
		} else if(periodType.equals("recruitmentPeriod")){
			Events db = new Events();
			db.open();
			startPeriod = db.recruitmentStartDate(event);
			endPeriod = db.recruitmentEndDate(event);
			db.close();
		} else /*else if(periodType.equals("allPeriod"))*/{
			startPeriod = null;
			endPeriod = null;
		}
		
		User db1 = new User();
		db1.open();
		String senderEmail = db1.userEmailAddress(userId);
		db1.close();
		
		Participants db2 = new Participants();
		db2.open();
		TO = db2.participantsEmailAddress(event, senderEmail, periodType, startPeriod, endPeriod);
		db2.close();
		
		TO.add(senderEmail);
		
		String SUBJECT = request.getParameter("subject");
		String MESSAGE = request.getParameter("message");
		
		MailToAllParticipants mail = new MailToAllParticipants();
		Boolean res = mail.template(event, userId, SUBJECT, TO, MESSAGE);
		
		stopWatch.stop();
		session.setAttribute("stopWatch", stopWatch.getTime(TimeUnit.SECONDS));
		session.setAttribute("totalParticipants", String.valueOf(TO.size()));
		
		if(res){
			session.setAttribute("success","");
			response.sendRedirect("/home/"+event+"/mail/create");	
		}
	}

}
