package jp.myouth.servlets;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.StringEscapeUtils;

import jp.myouth.db.Events;
import jp.myouth.db.Simplification;
import jp.myouth.mail.Templates;

@WebServlet("/insertParticipantData")
public class InsertParticipantData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String event = (String) session.getAttribute("event");
		if (event == null)
			response.sendRedirect("/events");
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name-field");
		name = StringEscapeUtils.escapeHtml4(name);
		
		String fname = request.getParameter("fname-field");
		fname = StringEscapeUtils.escapeHtml4(fname);
		
		String gender = request.getParameter("sex-field");
		gender = StringEscapeUtils.escapeHtml4(gender);
		
		String email = request.getParameter("email-field");
		email = StringEscapeUtils.escapeHtml4(email);
		
		String phone = request.getParameter("phone-field");
		phone = StringEscapeUtils.escapeHtml4(phone);
		
		String birthdate = request.getParameter("birth-year") + "-" + request.getParameter("birth-month") + "-"
				+ request.getParameter("birth-day");
		birthdate = StringEscapeUtils.escapeHtml4(birthdate);
		Date birth = Date.valueOf(birthdate);
		
		String company = request.getParameter("company-field");
		company = StringEscapeUtils.escapeHtml4(company);
		
		String country = request.getParameter("country-field");
		country = StringEscapeUtils.escapeHtml4(country);
		
		String country2 = request.getParameter("country-field-2");
		country2 = StringEscapeUtils.escapeHtml4(country2);
		
		String country3 = request.getParameter("country-field-3");
		country3 = StringEscapeUtils.escapeHtml4(country3);
		
		String zip = new String();
		String zip1 = request.getParameter("zip1");
		String zip2 = request.getParameter("zip2");
		if(zip1 != null && zip2 != null){
			zip = zip1+"-"+zip2;
			zip = StringEscapeUtils.escapeHtml4(zip);
		}
		
		String pref = request.getParameter("pref-field");
		pref = StringEscapeUtils.escapeHtml4(pref);
		
		String address = request.getParameter("address-field");
		address = StringEscapeUtils.escapeHtml4(address);
		
		String allergy = request.getParameter("allergy-field");
		allergy = StringEscapeUtils.escapeHtml4(allergy);

		String career = request.getParameter("other-career-field");
		if (career == null || career.length() == 0) {
			career = request.getParameter("career-field");
			career = StringEscapeUtils.escapeHtml4(career);
		} else {
			career = StringEscapeUtils.escapeHtml4(career);
		}
		String way = request.getParameter("other-trigger-field");
		if (way == null || way.length() == 0) {
			way = request.getParameter("trigger-field");
			way = StringEscapeUtils.escapeHtml4(way);

		} else {
			way = StringEscapeUtils.escapeHtml4(way);
		}

		Events db = new Events();
		db.open();
		boolean res = db.insertParticipantData(event, name, fname, gender, email, phone, birth, career, company, country, country2, country3, zip, pref, address, allergy, way);
		db.close();
		
		Simplification db1 = new Simplification();
		db1.open();
		db1.simplifyCompany();
		db1.close();
		
		Templates send = new Templates();
		boolean res1 = send.joinConfirmedMail(event, name, email);

		if (res && res1) {
				session.setAttribute("success","");
				response.sendRedirect("/events/"+event+"/form");
		}
	}

}
