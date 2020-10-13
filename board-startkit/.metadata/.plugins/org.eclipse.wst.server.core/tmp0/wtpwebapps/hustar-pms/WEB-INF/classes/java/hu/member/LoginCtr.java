package hu.member;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginCtr {
	private static final Integer cookieExpire = 60 * 60 * 24 * 30;// 1달 만료

	@Autowired
	private MemberSvc memberSvc;

	//////////// 로그인화면////////////
	@RequestMapping(value = "memberLogin")
	public String memberLogin(HttpServletRequest request, ModelMap modelMap) {
		String userid = get_cookie("sid", request);
		modelMap.addAttribute("userid", userid);

		return "member/memberLogin";
	}

	///// 로그인 처리//////////
	@RequestMapping(value = "memberLoginChk")
	public String memberLoginChk(HttpServletRequest request, HttpServletResponse response, LoginVO loginInfo,
			ModelMap modelMap) {
		UserVO userVO = memberSvc.selectMember4Login(loginInfo);

		if (userVO == null) {
			modelMap.addAttribute("msg", "로그인을 할 수 없습니다.");
			return "common/message";
		}

		memberSvc.insertLogIn(userVO.getUserno());

		HttpSession session = request.getSession();

		session.setAttribute("userid", userVO.getUserid());
		session.setAttribute("userrole", userVO.getUserrole());
		session.setAttribute("userno", userVO.getUserno());
		session.setAttribute("usernm", userVO.getUsernm());
		
		if("Y".equals(loginInfo.getRemember())) {
			set_cookie("sid", loginInfo.getUserid(), response);
		} else {
			set_cookie("sid", "", response);
		}
		
		return "redirect:/index";
	}
	
	/**
     * 쿠키 저장.     
     */
    public static void set_cookie(String cid, String value, HttpServletResponse res) {

        Cookie ck = new Cookie(cid, value);
        ck.setPath("/");
        ck.setMaxAge(cookieExpire);
        res.addCookie(ck);        
    }

	/**
	 * 쿠키 가져오기.
	 */
	public static String get_cookie(String cid, HttpServletRequest request) {
		String ret = "";

		if (request == null) {
			return ret;
		}

		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return ret;
		}

		for (Cookie ck : cookies) {
			if (ck.getName().equals(cid)) {
				ret = ck.getValue();

				ck.setMaxAge(cookieExpire);
				break;
			}
		}
		return ret;
	}
}
