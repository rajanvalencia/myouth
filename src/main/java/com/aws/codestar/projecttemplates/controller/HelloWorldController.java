package com.aws.codestar.projecttemplates.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.myouth.db.User;

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
	
	@RequestMapping("setNewPassword/{token}")
	public void setNewPasswordWithToken(HttpServletRequest request, HttpServletResponse response, @PathVariable("token") String token) throws IOException {
		HttpSession session = request.getSession();
		session.setAttribute("token", token);
		response.sendRedirect("/processPasswordToken");
	}
	
	@RequestMapping("setNewPassword")
	public ModelAndView setNewPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("setNewPasswordSuccess") == null)
			session.setAttribute("setNewPasswordSuccess", "hidden");
		
		ModelAndView mv = new ModelAndView("/user/password/setNewPassword");
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
	
	@RequestMapping("emailVerification/{token}")
	public void verifyEmail(HttpServletRequest request, HttpServletResponse response, @PathVariable ("token") String token) throws IOException {
		HttpSession session= request.getSession();
		session.setAttribute("token", token);
		response.sendRedirect("/verifyEmail");
	}
	
	@RequestMapping("emailVerification")
	public ModelAndView emailVerification() {
		ModelAndView mv = new ModelAndView("user/emailVerification");
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
	
	@RequestMapping("home/{event}/settings/member/add/{userId}")
	public ModelAndView memberAdd(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("userId") String userId) throws IOException {
		HttpSession session = request.getSession();
		
		Boolean user = (Boolean) session.getAttribute("user");
		
		if(!user)
			response.sendRedirect("/login");
		
		User db = new User();
		db.open();
		db.memberAdd(event, userId);
		db.close();
		
		ModelAndView mv = new ModelAndView("user/settings/member");
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/member/remove/{userId}")
	public ModelAndView memberRemove(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("userId") String userId) throws IOException {
		HttpSession session = request.getSession();
		
		Boolean user = (Boolean) session.getAttribute("user");
		
		if(!user)
			response.sendRedirect("/login");
		
		User db = new User();
		db.open();
		db.memberRemove(event, userId);
		db.close();
		
		ModelAndView mv = new ModelAndView("user/settings/member");
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

	@RequestMapping("events/{eventname}")
	public String eventPage(HttpServletRequest request, @PathVariable("eventname") String eventname) {
		HttpSession session = request.getSession(true);
		session.setAttribute("event", eventname);
		return "eventPageTemplate";
	}
}
