package hu.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import hu.board.BoardSvc;


public class LoginInterceptor implements HandlerInterceptor{

	static final Logger LOGGER = LoggerFactory.getLogger(BoardSvc.class);
	
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		HttpSession session = req.getSession();
		
		try {
			if(session==null || session.getAttribute("userno")==null) {
				res.sendRedirect("memberLogin");
				return false;
			}
		} catch(IOException ex) {
			LOGGER.error("LoginInterceptor");
		}
		
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}

}
