package com.aws.codestar.projecttemplates.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Basic Spring MVC controller that handles all GET requests.
 */
@Controller
@RequestMapping("/")
public class HelloWorldController {

	private final String siteName;

	public HelloWorldController(final String siteName) {
		this.siteName = siteName;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView helloWorld() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("siteName", this.siteName);
		return mav;
	}

	@RequestMapping("member")
	public ModelAndView member() {
		ModelAndView mv = new ModelAndView("member");
		return mv;
	}

	@RequestMapping("password")
	public ModelAndView password() {
		ModelAndView mv = new ModelAndView("password");
		return mv;
	}

	@RequestMapping("events")
	public ModelAndView events() {
		ModelAndView mv = new ModelAndView("events");
		return mv;
	}
	
	@RequestMapping("myouthCreator")
	public ModelAndView myouthCreator() {
		ModelAndView mv = new ModelAndView("myouthCreator");
		return mv;
	}

	@RequestMapping("error404")
	public ModelAndView error404() {
		ModelAndView mv = new ModelAndView("error404");
		return mv;
	}

	@RequestMapping("error500")
	public ModelAndView error500() {
		ModelAndView mv = new ModelAndView("error500");
		return mv;
	}

	@RequestMapping("eventPageTemplate")
	public ModelAndView eventpage() {
		ModelAndView mv = new ModelAndView("eventPageTemplate");
		return mv;
	}
	
	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request){
		HttpSession session = request.getSession();
		Boolean failure = (Boolean)session.getAttribute("failure");
		if(failure == null)
			session.setAttribute("failure", false);
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}
	
	@RequestMapping("logoutRedirect")
	public ModelAndView logoutRedirect() {
		ModelAndView mv = new ModelAndView("logoutRedirect");
		return mv;
	}

	@RequestMapping("loginRedirect")
	public ModelAndView loginRedirect() {
		ModelAndView mv = new ModelAndView("loginRedirect");
		return mv;
	}
	
	@RequestMapping("registerUser")
	public ModelAndView registerUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("registerUserSuccess") == null)
			session.setAttribute("registerUserSuccess", "hidden");
		
		if(session.getAttribute("registerUserFailure") == null)
			session.setAttribute("registerUserFailure", "hidden");
		
		if(session.getAttribute("registerUserName") == null)
			session.setAttribute("registerUserName", "");
		
		if(session.getAttribute("registerUserFname") == null)
			session.setAttribute("registerUserFname", "");
		
		if(session.getAttribute("registerUserPhone") == null)
			session.setAttribute("registerUserPhone", "");
		
		if(session.getAttribute("registerUserYear") == null)
			session.setAttribute("registerUserYear", "");
		
		if(session.getAttribute("registerUserMonth") == null)
			session.setAttribute("registerUserMonth", "");
		
		if(session.getAttribute("registerUserDay") == null)
			session.setAttribute("registerUserDay", "\" \"");
		
		ModelAndView mv = new ModelAndView("user/registerUser");
		return mv;
	}
	
	@RequestMapping("insertUser")
	public ModelAndView insertUser() {
		ModelAndView mv = new ModelAndView("user/insertUser");
		return mv;
	}
	
	@RequestMapping("reissueNewPassword/{token}")
	public ModelAndView reissueNewPassword(HttpServletRequest request, @PathVariable("token") String token) {
		HttpSession session = request.getSession();
		session.setAttribute("token", token);
		
		if(session.getAttribute("reissueNewPasswordSuccess") == null)
			session.setAttribute("reissueNewPasswordSuccess", "hidden");
		
		if(session.getAttribute("reissueNewPasswordFailure") == null)
			session.setAttribute("reissueNewPasswordFailure", "hidden");
		
		if(session.getAttribute("reissueNewPassword") == null)
			session.setAttribute("reissueNewPassword", "hidden");
		
		ModelAndView mv = new ModelAndView("user/password/reissueNewPassword");
		return mv;
	}
	
	@RequestMapping("sendReissueNewPassword")
	public ModelAndView sendReissueNewPassword() {
		ModelAndView mv = new ModelAndView("user/password/sendReissueNewPassword");
		return mv;
	}
	
	@RequestMapping("sendReissuePermission")
	public ModelAndView sendReissuePermission() {
		ModelAndView mv = new ModelAndView("user/password/sendReissuePermission");
		return mv;
	}
	
	@RequestMapping("reissuePermission")
	public ModelAndView reissuePermission(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		String success = (String) session.getAttribute("reissuePermissionSuccess");
		if(success == null)
			session.setAttribute("reissuePermissionSuccess", "hidden");
		
		String failure = (String) session.getAttribute("reissuePermissionFailure");
		if(failure == null)
			session.setAttribute("reissuePermissionFailure", "hidden");
		
		ModelAndView mv = new ModelAndView("user/password/reissuePermission");
		return mv;
	}
	
	@RequestMapping("registerComplete")
	public ModelAndView registerComplete() {
		ModelAndView mv = new ModelAndView("user/registerComplete");
		return mv;
	}
	
	@RequestMapping("emailVerification/{userId}")
	public ModelAndView emailVerification(HttpServletRequest request, @PathVariable ("userId") String userId) {
		HttpSession session = request.getSession();
		session.setAttribute("userId", userId);
		ModelAndView mv = new ModelAndView("user/verification");
		return mv;
	}

	@RequestMapping("home")
	public ModelAndView user(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		ModelAndView mv = new ModelAndView("user/home");
		return mv;
	}
	
	@RequestMapping("home/profile")
	public ModelAndView editProfile(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		ModelAndView mv = new ModelAndView("user/profile/profile");
		return mv;
	}
	
	@RequestMapping("home/changeProfile")
	public ModelAndView changeProfile(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		ModelAndView mv = new ModelAndView("user/profile/changeProfile");
		return mv;
	}
	
	@RequestMapping("home/cropProfilePicture")
	public ModelAndView cropProfilePicture(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		ModelAndView mv = new ModelAndView("user/profile/cropProfilePicture");
		return mv;
	}
	
	
	@RequestMapping("home/{event}/participants")
	public ModelAndView participants(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/participants");
		return mv;
	}
	
	@RequestMapping("home/{event}/downloadSettings")
	public ModelAndView downloadSettings(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/downloadSettings");
		return mv;
    }
	
	@RequestMapping("home/{event}/download")
	public ModelAndView download(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/download");
		return mv;
    }
	
	@RequestMapping("home/{event}/mail")
	public ModelAndView mail(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/mail");
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/send")
	public ModelAndView sendMail(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/send");
		return mv;
	}
	
	@RequestMapping("home/{event}/settings")
	public ModelAndView settings(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/settings");
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/details")
	public ModelAndView details(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/details");
		return mv;
	}

	@RequestMapping("home/{event}/settings/form")
	public ModelAndView formSettings(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/form");
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/form/change")
	public ModelAndView changeFormSettings(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/changeForm");
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/survey")
	public ModelAndView surveySettings(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/survey");
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/survey/change")
	public ModelAndView changeSurveySettings(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/changeSurvey");
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/details/change")
	public ModelAndView changeDetails(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/changeDetails");
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/member")
	public ModelAndView member(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/member");
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/member/search")
	public ModelAndView memberSearch(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/memberSearch");
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/member/add/{userId}")
	public ModelAndView memberAdd(HttpServletRequest request, @PathVariable("event") String event, @PathVariable("userId") String userId) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		session.setAttribute("addUser", userId);
		ModelAndView mv = new ModelAndView("user/settings/memberAdd");
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/member/remove/{userId}")
	public ModelAndView memberRemove(HttpServletRequest request, @PathVariable("event") String event, @PathVariable("userId") String userId) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		session.setAttribute("removeUser", userId);
		ModelAndView mv = new ModelAndView("user/settings/memberRemove");
		return mv;
	}
	
	@RequestMapping("events/{eventname}/form")
	public String form(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		return "form";
	}
	
	@RequestMapping("events/{eventname}/survey")
	public String survey(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		session.setAttribute("event", eventname);
		return "survey";
	}

	@RequestMapping("events/{eventname}/survey/insertSurvey")
	public String insertSurveyData(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		return "insertSurvey";
	}

	@RequestMapping("events/{eventname}/survey/success")
	public String surveysuccess(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		session.setAttribute("survey", true);
		return "success";
	}

	@RequestMapping("events/{eventname}")
	public String eventPage(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		return "eventPageTemplate";
	}

	@RequestMapping("events/{eventname}/form/success")
	public ModelAndView success(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		session.setAttribute("survey", false);
		ModelAndView mv = new ModelAndView("success");
		return mv;
	}

	@RequestMapping("events/{eventname}/form/insertForm")
	public String insert(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		return "insertForm";
	}
}
