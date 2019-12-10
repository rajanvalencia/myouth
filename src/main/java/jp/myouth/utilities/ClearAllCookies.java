package jp.myouth.utilities;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClearAllCookies {

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		for (Cookie cookie : request.getCookies()) {
			cookie.setValue("");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return ;
	}
}
