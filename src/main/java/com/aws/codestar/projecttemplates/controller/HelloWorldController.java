package com.aws.codestar.projecttemplates.controller;

import java.io.IOException;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.myouth.db.Credentials;
import jp.myouth.db.ExistenceCheck;
import jp.myouth.db.Messages;
import jp.myouth.db.User;
import jp.myouth.security.GenerateSecureString;
import jp.myouth.servlets.DeleteImage;
import jp.myouth.servlets.DeleteTrashedInbox;
import jp.myouth.tables.AnalyticsPageGenerateEventCharts;
import jp.myouth.tables.AnalyticsPageViewAllFormTemplates;
import jp.myouth.tables.FormTemplate;
import jp.myouth.tables.FormTemplateTitleAndDescription;

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
	
	@RequestMapping("developer")
	public ModelAndView myouthCreator() {
		ModelAndView mv = new ModelAndView("developer");
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
	public ModelAndView login(HttpServletRequest request, HttpSession session, ModelMap model){
		new  TokenGenerator().generate(session, model);
		showFailureMessageOnLogin(session, model);
		return new ModelAndView("login");
	}
	
	@RequestMapping("registerUser")
	public ModelAndView registerUser(HttpSession session, ModelMap model) {
		successOrFailure(session, model);
		return new ModelAndView("user/registerUser");
	}
	
	@RequestMapping("setNewPassword/{token}")
	public void setNewPasswordWithToken(HttpServletResponse response, HttpSession session, @PathVariable("token") String token) throws IOException {
		session.setAttribute("token", token);
		response.sendRedirect("/processPasswordToken");
	}
	
	@RequestMapping("setNewPassword")
	public ModelAndView setNewPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("setNewPasswordSuccess") == null)
			session.setAttribute("setNewPasswordSuccess", "hidden");
		
		if(session.getAttribute("token") == null)
			response.sendRedirect("/error404");
		
		ModelAndView mv = new ModelAndView("/user/password/setNewPassword");
		return mv;
	}
	
	@RequestMapping("reissuePermission")
	public ModelAndView reissuePermission(HttpServletRequest request, HttpSession session, ModelMap model) {
		successOrFailure(session, model);
		return new ModelAndView("user/password/reissuePermission");
	}
	
	@RequestMapping("emailVerification/{token}")
	public void verifyEmail(HttpServletRequest request, HttpServletResponse response, HttpSession session, @PathVariable ("token") String token) throws IOException {
		session.setAttribute("token", token);
		response.sendRedirect("/verifyEmail");
	}
	
	@RequestMapping("emailVerification")
	public ModelAndView emailVerification() {
		return new ModelAndView("user/emailVerification");
	}

	@RequestMapping("home")
	public ModelAndView user(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		new UserHome().initialize(session, model);
		return new ModelAndView("user/home");
	}
	
	@RequestMapping("home/account")
	public ModelAndView accountSettings(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		return new ModelAndView("user/account/index");
	}
	
	@RequestMapping("home/account/profile")
	public ModelAndView updateAccountDetails(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		new UpdateAccountDetails().initialize(session, model);
		return new ModelAndView("user/account/profile");
	}
	
	@RequestMapping("home/registerEvent")
	public ModelAndView registerEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		return new ModelAndView("user/registerEvent");
	}
	
	@RequestMapping("home/cropImage")
	public ModelAndView cropProfilePicture(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		return new ModelAndView("user/cropImage");
	}
	
	@RequestMapping("home/{event}/visualization")
	public ModelAndView visualization(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/visualization/index");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/visualization/ratings")
	public ModelAndView visualizationRatings(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/visualization/ratings");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		model.addAttribute("event", event);
		
		return mv;
	}
	
	@RequestMapping("home/{event}/visualization/analytics")
	public ModelAndView visualizationAnalyticsIndex(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/visualization/analytics/index");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		AnalyticsPageViewAllFormTemplates view = new AnalyticsPageViewAllFormTemplates();
		view.append(model, event);
		
		model.addAttribute("event", event);
		
		return mv;
	}
	
	@RequestMapping("home/{event}/visualization/analytics/{templateId}")
	public ModelAndView visualizationAnalytics(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("event") String event, @PathVariable("templateId") String templateId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/visualization/analytics/analytics");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		AnalyticsPageGenerateEventCharts gen = new AnalyticsPageGenerateEventCharts();
		gen.append(request, response, model, templateId);
		
		model.addAttribute("event", event);
		
		return mv;
	}
	
	@RequestMapping("home/{event}/participants")
	public ModelAndView participants(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/participants/index");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/participants/{participantId}")
	public ModelAndView participantFullInfo(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("participantId") String participantId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		session.setAttribute("participantId", participantId);
		
		ModelAndView mv = new ModelAndView("user/participants/participantFullInfo");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/downloadSettings")
	public ModelAndView downloadSettings(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/downloadSettings");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
    }
	
	@RequestMapping("home/{event}/photo-video")
	public ModelAndView photoVideo(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/photo-video/index");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/photo-video/description")
	public ModelAndView writeEventImageDescription(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/photo-video/description");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/photo-video/{imageId}/delete")
	public ModelAndView deletePhotoVideo(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("imageId") String imageId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		request.setAttribute("imageId", imageId);
		DeleteImage del = new DeleteImage();
		del.event(request, response);
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event)) {
			ModelAndView mv = new ModelAndView("error404");
			check.close();
			return mv;
		}
		
		return null;
	}
	
	@RequestMapping("home/{event}/mail")
	public ModelAndView mail(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/mail/index");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/create")
	public ModelAndView mailCreate(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			return new ModelAndView("error404");
		check.close();
		
		return new ModelAndView("user/mail/create");
	}
	
	@RequestMapping("home/{event}/mail/create/{messageId}")
	public ModelAndView mailReplyTo(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("messageId") String messageId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		
		session.setAttribute("event", event);
		
		Messages db = new Messages();
		db.open();
		String replyTo = db.replyTo(messageId); 
		db.close();
		
		session.setAttribute("replyTo", replyTo);
		
		ModelAndView mv = new ModelAndView("user/mail/create");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/inbox")
	public ModelAndView mailReceived(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/mail/receivedMessages");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/inbox/{messageId}")
	public ModelAndView mailReceivedByMessageId(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("messageId") String messageId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		session.setAttribute("messageId", messageId);
		
		ModelAndView mv = new ModelAndView("user/mail/receivedMessage");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/sent")
	public ModelAndView mailSent(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/mail/sentMessages");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/sent/{transactionId}")
	public ModelAndView mailSentByTransactionId(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model, @PathVariable("event") String event, @PathVariable("transactionId") String transactionId) throws IOException {
		redirectToLoginPageIfUserDoesntExist(response, session);
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			return new ModelAndView("error404");
		check.close();
		
		new EventSentMessage().initialize(response, session, model, event, transactionId);
		return new ModelAndView("user/mail/sentMessage");
	}
	
	@RequestMapping("home/{event}/mail/trashedInbox")
	public ModelAndView mailTrashedReceived(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		if(session.getAttribute("deleteAllTrashedMessagesSuccess") == null)
			session.setAttribute("deleteAllTrashedMessagesSuccess", "hidden");
		
		ModelAndView mv = new ModelAndView("user/mail/trashedReceivedMessages");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/trashed")
	public ModelAndView mailTrashed(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		if(session.getAttribute("deleteAllTrashedMessagesSuccess") == null)
			session.setAttribute("deleteAllTrashedMessagesSuccess", "hidden");
		
		ModelAndView mv = new ModelAndView("user/mail/trashedMessages");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/inbox/{messageId}/delete")
	public  ModelAndView deleteReceivedMesssage(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("messageId") String messageId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		Messages db = new Messages();
		db.open();
		db.deleteReceivedMessage(messageId);
		db.close();
		
		ModelAndView mv = new ModelAndView("user/mail/receivedMessages");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/sent/{messageId}/delete")
	public  ModelAndView deleteMesssage(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("messageId") String messageId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		Messages db = new Messages();
		db.open();
		db.deleteMessage(messageId);
		db.close();
		
		ModelAndView mv = new ModelAndView("user/mail/sentMessages");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/trashed/{transactionId}")
	public ModelAndView mailTrashedByMessageId(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("transactionId") String transactionId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		
		session.setAttribute("event", event);
		session.setAttribute("transactionId", transactionId);
		ModelAndView mv = new ModelAndView("user/mail/trashedMessage");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/trashedInbox/{messageId}")
	public ModelAndView receivedTrashedByMessageId(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("messageId") String messageId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		
		session.setAttribute("event", event);
		session.setAttribute("messageId", messageId);
		ModelAndView mv = new ModelAndView("user/mail/trashedReceivedMessage");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/trashed/{transactionId}/delete")
	public  ModelAndView deleteTrashedMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("transactionId") String transactionId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		Messages db = new Messages();
		db.open();
		ArrayList<String> messageIds = db.getMessageIdsByTransactionId(transactionId);
		db.deleteTrashedMessage(transactionId, messageIds);
		db.close();
		
		ModelAndView mv = new ModelAndView("user/mail/trashedMessages");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/trashedInbox/{messageId}/delete")
	public  ModelAndView deleteTrashedReceivedMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("messageId") String messageId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		Messages db = new Messages();
		db.open();
		db.deleteTrashedReceivedMessage(messageId);
		db.close();
		
		DeleteTrashedInbox del = new DeleteTrashedInbox();
		del.attachments(messageId);
		
		ModelAndView mv = new ModelAndView("user/mail/trashedReceivedMessages");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/trashed/{transactionId}/undo")
	public  ModelAndView undoTrashedMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("transactionId") String transactionId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		Messages db = new Messages();
		db.open();
		db.undoTrashedMessage(transactionId);
		db.close();
		
		ModelAndView mv = new ModelAndView("user/mail/trashedMessages");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/trashedInbox/{messageId}/undo")
	public  ModelAndView undoTrashedReceivedMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("messageId") String messageId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		Messages db = new Messages();
		db.open();
		db.undoTrashedReceivedMessage(messageId);
		db.close();
		
		ModelAndView mv = new ModelAndView("user/mail/trashedReceivedMessages");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("/home/{event}/mail/trashed/deleteAllTrashedMessages")
	public void deleteTrashedMessages(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		Messages db = new Messages();
		db.open();
		ArrayList<String> transactionIds = db.getTransactionIdsByEvent(event);
		ArrayList<String> messageIds = db.getMessageIdsByTransactionIds(transactionIds);
		db.deleteAllTrashedMessages(transactionIds, messageIds);
		db.close();
		
		session.setAttribute("deleteAllTrashedMessagesSuccess", "");
		
		response.sendRedirect("/home/"+event+"/mail/trashed");
	}
	
	@RequestMapping("/home/{event}/mail/trashedInbox/deleteAllTrashedMessages")
	public void deleteTrashedReceivedMessages(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		
		Messages db = new Messages();
		db.open();
		ArrayList<String> messageIds = db.getTrashedReceivedMessageIdsByEvent(event);
		db.deleteAllTrashedReceivedMessages(messageIds);
		db.close();
		
		DeleteTrashedInbox del = new DeleteTrashedInbox();
		del.attachments(messageIds);
		
		session.setAttribute("deleteAllTrashedMessagesSuccess", "");
		
		response.sendRedirect("/home/"+event+"/mail/trashedInbox");
	}
	
	@RequestMapping("home/{event}/settings")
	public ModelAndView settings(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		new SettingsPage().initialize(request, model, event);
		ModelAndView mv = new ModelAndView("user/settings/index");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/details")
	public ModelAndView editEventDetails(HttpServletRequest request, HttpServletResponse response, ModelMap model, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/editEventDetails");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		new EditEventDetailsPage().initialize(request, model, event);
		
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/formTemplates")
	public ModelAndView formTemplates(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model, @PathVariable("event") String event) throws IOException {
		redirectToLoginPageIfUserDoesntExist(response, session);
		session.setAttribute("event", event);
		model.addAttribute("event", event);
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			return new ModelAndView("error404");
		check.close();
		
		model.addAttribute("eventSessionStorage", "sessionStorage.setItem('event', '"+event+"');");
		
		GenerateSecureString gen = new GenerateSecureString();
		String apiKey = gen.string(100);
		model.addAttribute("apiKey", "sessionStorage.setItem('apiKey', '"+apiKey+"');");
		
		Credentials db1 = new Credentials();
		db1.open();
		db1.insertAjaxApiKey(userId, apiKey);
		db1.close();
		
		new ViewAllFormTemplates().initialize(session, model, event);
		
		return new ModelAndView("user/settings/formTemplates");
	}

	@RequestMapping("home/{event}/settings/formTemplates/{templateId}")
	public ModelAndView formCustomization(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model, @PathVariable("event") String event, @PathVariable("templateId") String templateId) throws IOException {
		redirectToLoginPageIfUserDoesntExist(response, session);
		
		session.setAttribute("event", event);
		model.addAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/customizeForm");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		if(model.get("changeRequestSuccess") == null) {
			model.addAttribute("changeRequestSuccess", "hidden");
		}
		
		if(model.get("formSentSuccess") == null) {
			model.addAttribute("formSentSuccess", "hidden");
		}
		
		if(model.get("warning") == null) {
			model.addAttribute("warning", "");
		}
		
		session.setAttribute("templateId", templateId);
		
		new CustomizeForm().initialize(request, response, session, model, event, templateId);
		
		return mv;
	}
	
	@RequestMapping("home/{event}/member")
	public ModelAndView member(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/member");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/member/add/{memberUserId}")
	public void memberAdd(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("memberUserId") String memberUserId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		
		User db = new User();
		db.open();
		db.memberAdd(event, memberUserId);
		db.close();
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			response.sendRedirect("error404");
		check.close();
		
		response.sendRedirect("/home/"+event+"/member");
	}
	
	@RequestMapping("home/{event}/member/remove/{userId}")
	public ModelAndView memberRemove(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("userId") String memberUserId) throws IOException {
		HttpSession session = request.getSession();
		redirectToLoginPageIfUserDoesntExist(response, session);
		
		User db = new User();
		db.open();
		db.memberRemove(event, memberUserId);
		db.close();
		
		ModelAndView mv = new ModelAndView("user/member");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("events/{event}/forms/{templateId}")
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model, @PathVariable("event") String event, @PathVariable("templateId") String templateId) throws SQLException {
		ExistenceCheck db = new ExistenceCheck();
		db.open();
		Boolean res = db.event(event);
		db.close();
		if(!res) return new ModelAndView("error404");
		
		new FormTemplateTitleAndDescription().initialize(model, templateId);
		new FormTemplate().initialize(model, event, templateId);
		
		model.addAttribute("event", event);
		model.addAttribute("templateId", templateId);
		
		successOrFailure(session, model);
		
		return new ModelAndView("form");
	}

	@RequestMapping("events/{event}")
	public ModelAndView eventPage(ModelMap model, @PathVariable("event") String event) throws IOException {
		ExistenceCheck db = new ExistenceCheck();
		db.open();
		Boolean res = db.event(event);
		db.close();
		if(!res) return new ModelAndView("error404");
		new EventPage().initialize(model, event);
		return new ModelAndView("eventPage");
	}
	
	private static void redirectToLoginPageIfUserDoesntExist(HttpServletResponse response, HttpSession session) throws IOException {
		String user = (String) session.getAttribute("userId");
		if(user == null)
			response.sendRedirect("/login");
	}
	
	private static void successOrFailure(HttpSession session, ModelMap model) {
		
		String success = (String) session.getAttribute("success");
		String failure = (String) session.getAttribute("failure");
		
		if(success == null || failure == null) {
			if(success == null) {
				model.addAttribute("success", "hidden");
			} 
			
			if(failure == null) {
				model.addAttribute("failure", "hidden");
			}
			return ;
		}
		
		if(success.length() == 0) {
			model.addAttribute("success", "");
			session.setAttribute("success", "hidden");
		} else if(failure.length() == 0) {
			model.addAttribute("failure", "");
			session.setAttribute("failure", "hidden");
		}
	}
	
	private static void showFailureMessageOnLogin(HttpSession session, ModelMap model) {
		Boolean loginFailure = (Boolean) session.getAttribute("loginFailure");
		
		if(loginFailure == null || !loginFailure) {
			model.addAttribute("loginFailureText", "");
		} else if(loginFailure) {
			model.addAttribute("loginFailureText", "$message._show('failure', 'メールアドレスまたはパスワードが間違っています');");
		}
	}
}
