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

	@RequestMapping("admin")
	public ModelAndView admin() {
		ModelAndView mv = new ModelAndView("admin");
		return mv;
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
	public ModelAndView registerUser() {
		ModelAndView mv = new ModelAndView("user/registerUser");
		return mv;
	}
	
	@RequestMapping("registerUser2")
	public ModelAndView registerUser2() {
		ModelAndView mv = new ModelAndView("user/registerUser2");
		return mv;
	}
	
	@RequestMapping("insertUser")
	public ModelAndView insertUser() {
		ModelAndView mv = new ModelAndView("user/insertUser");
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
	
	@RequestMapping("home/events")
	public ModelAndView userEvents(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		ModelAndView mv = new ModelAndView("user/events");
		return mv;
	}
	
	@RequestMapping("home/settings")
	public ModelAndView settings(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		ModelAndView mv = new ModelAndView("user/settings");
		return mv;
	}
	
	@RequestMapping("home/events/{event}")
	public ModelAndView eventManagementHome(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/manage");
		return mv;
	}
	
	@RequestMapping("home/editProfile")
	public ModelAndView editProfile(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		ModelAndView mv = new ModelAndView("user/editProfile");
		return mv;
	}
	
	@RequestMapping("home/insertProfileInfo")
	public ModelAndView userEdit(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		ModelAndView mv = new ModelAndView("user/insertProfileInfo");
		return mv;
	}
	
	
	@RequestMapping("home/events/{event}/participants")
	public ModelAndView participants(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/participants");
		return mv;
	}
	
	@RequestMapping("home/events/{event}/download")
	public ModelAndView download(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/download");
		return mv;
    }

	@RequestMapping("home/events/{event}/details")
	public ModelAndView details(HttpServletRequest request, @PathVariable("event") String event) {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			session.setAttribute("user", false);
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/details");
		return mv;
	}
	
	@RequestMapping("events/{eventname}/form")
	public String form(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		return "form";
	}

	@RequestMapping("events/{eventname}/survey")
	public String survey(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		return "survey";
	}

	@RequestMapping("events/{eventname}/survey/insertSurveyData")
	public String insertSurveyData(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		return "insertSurveyData";
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

	@RequestMapping("events/{eventname}/form/insert")
	public String insert(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		return "insert";
	}
}
