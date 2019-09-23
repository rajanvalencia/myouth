package com.aws.codestar.projecttemplates.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import jp.myouth.db.ExistenceCheck;
import jp.myouth.db.Messages;
import jp.myouth.db.User;
import jp.myouth.servlets.DeleteImage;

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
		
		if(session.getAttribute("token") == null)
			response.sendRedirect("/error404");
		
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
	public ModelAndView editProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		ModelAndView mv = new ModelAndView("user/profile/index");
		return mv;
	}
	
	@RequestMapping("home/changeProfile")
	public ModelAndView changeProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		ModelAndView mv = new ModelAndView("user/profile/changeProfile");
		return mv;
	}
	
	@RequestMapping("home/registerEvent")
	public ModelAndView registerEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		
		if(session.getAttribute("registerEventSuccess") == null)
			session.setAttribute("registerEventSuccess", "hidden");
		
		if(session.getAttribute("registerEventFailure") == null)
			session.setAttribute("registerEventFailure", "hidden");
		
		ModelAndView mv = new ModelAndView("user/registerEvent");
		return mv;
	}
	
	@RequestMapping("home/cropImage")
	public ModelAndView cropProfilePicture(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		ModelAndView mv = new ModelAndView("user/cropImage");
		return mv;
	}
	
	
	@RequestMapping("home/{event}/participants")
	public ModelAndView participants(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		
		session.setAttribute("event", event);
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
	public ModelAndView mailSentByTransactionId(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("transactionId") String transactionId) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		
		session.setAttribute("event", event);
		session.setAttribute("transactionId", transactionId);
		ModelAndView mv = new ModelAndView("user/mail/sentMessage");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/mail/trashedInbox")
	public ModelAndView mailTrashedReceived(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		session.setAttribute("event", event);
		
		Messages db = new Messages();
		db.open();
		db.deleteTrashedReceivedMessage(messageId);
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
	
	@RequestMapping("home/{event}/mail/trashed/{transactionId}/undo")
	public  ModelAndView undoTrashedMessage(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("transactionId") String transactionId) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
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
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		session.setAttribute("event", event);
		
		Messages db = new Messages();
		db.open();
		ArrayList<String> messageIds = db.getTrashedReceivedMessageIdsByEvent(event);
		db.deleteAllTrashedReceivedMessages(messageIds);
		db.close();
		
		session.setAttribute("deleteAllTrashedMessagesSuccess", "");
		
		response.sendRedirect("/home/"+event+"/mail/trashedInbox");
	}
	
	@RequestMapping("home/{event}/settings")
	public ModelAndView settings(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		session.setAttribute("event", event);
		
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
	public ModelAndView details(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/details");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}

	@RequestMapping("home/{event}/settings/form")
	public ModelAndView formSettings(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		session.setAttribute("event", event);
		ModelAndView mv = new ModelAndView("user/settings/form");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/settings/survey")
	public ModelAndView surveySettings(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		
		String success = (String)session.getAttribute("success");
		if(success == null)
			session.setAttribute("success", "hidden");
		session.setAttribute("event", event);
		
		ModelAndView mv = new ModelAndView("user/settings/survey");
		
		String userId = (String) session.getAttribute("userId");
		ExistenceCheck check = new ExistenceCheck();
		check.open();
		if(!check.eventAccess(userId, event))
			mv = new ModelAndView("error404");
		check.close();
		
		return mv;
	}
	
	@RequestMapping("home/{event}/member")
	public ModelAndView member(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event) throws IOException {
		HttpSession session = request.getSession();
		Boolean user = (Boolean)session.getAttribute("user");
		if(user == null)
			response.sendRedirect("/login");
		
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
	public ModelAndView memberAdd(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("memberUserId") String memberUserId) throws IOException {
		HttpSession session = request.getSession();
		
		Boolean user = (Boolean) session.getAttribute("user");
		
		if(user == null)
			response.sendRedirect("/login");
		
		User db = new User();
		db.open();
		db.memberAdd(event, memberUserId);
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
	
	@RequestMapping("home/{event}/member/remove/{userId}")
	public ModelAndView memberRemove(HttpServletRequest request, HttpServletResponse response, @PathVariable("event") String event, @PathVariable("userId") String memberUserId) throws IOException {
		HttpSession session = request.getSession();
		
		Boolean user = (Boolean) session.getAttribute("user");
		
		if(user == null)
			response.sendRedirect("/login");
		
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
