package com.niiki.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.niiki.dto.MemberDTO;
import com.niiki.service.MemberService;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService mservice;
	
	/*LoginPage 연결 Controller*/
	@GetMapping("/login")
	public String getLogin(@RequestParam(value="user_id", required = false) String user_id, Model model) throws Exception {
		model.addAttribute("user_id", user_id);
		return "/member/login";
	}
	@PostMapping("/login")
	public String PostLogin(@ModelAttribute("logInfo") MemberDTO mdto, HttpSession Msession, HttpServletResponse response) throws Exception {
		mdto = mservice.memberLogin(mdto, response);
		Msession.setAttribute("mdto", mdto);
		return "/index";	
	}
	//로그아웃
	@GetMapping("/logout")
	public void logout(HttpSession Msession, HttpServletResponse response) throws Exception{
		Msession.invalidate();
		mservice.memberLogout(response);
	}
	
	//회원가입 구현
	@GetMapping("/join")
		public void getJoin() {	
	}
	@PostMapping("/join")
		public String postJoin(MemberDTO mdto, HttpServletResponse response, RedirectAttributes rttr) throws Exception {
		rttr.addFlashAttribute("result", mservice.memberJoin(mdto, response));
		return"redirect:/member/login";
	}
	
	//아이디 중복 검사(ajax)
	@PostMapping("/userID_check")
	public void userID_check(@RequestParam("user_id") String user_id, HttpServletResponse response) throws Exception{
		mservice.userID_check(user_id, response);
	}
	//생년월일 유효성 검사(ajax)
	@PostMapping("/userBirthCheck")
	public ResponseEntity<String> userBirthCheck(@RequestParam("user_birth") String user_birth, String format, HttpServletResponse response) throws Exception {
		boolean result = mservice.userBirthCheck(user_birth,"yyyy-MM-dd", response);
		return result == true ? new ResponseEntity<>("true",HttpStatus.OK):
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	//회원 인증
	@PostMapping("/approval_member")
	public void approval_member(@ModelAttribute MemberDTO mdto, HttpServletResponse response) throws Exception {
		mservice.approval_member(mdto, response);
	}
		//아이디 찾기
		@GetMapping("/findIDForm")
		public void findID() throws Exception {
		};
		@PostMapping("/findIDResult")
		public String findIDCheck(HttpServletResponse response, @RequestParam("user_email") String user_email, Model model) throws Exception{
			model.addAttribute("user_id", mservice.findIDForm(response, user_email).getUser_id());
			model.addAttribute("user_email", mservice.findIDForm(response, user_email).getUser_email());
			return "/member/findIDResult";
		};
		//비밀번호 찾기
		@GetMapping("/findPwForm")
		public String findPwForm(@ModelAttribute("findPwInfo") MemberDTO mdto, Model model) throws Exception{
			model.addAttribute("user_id", mdto.getUser_id());
			model.addAttribute("user_email", mdto.getUser_email());
			return "/member/findPwForm";
		}
		@PostMapping("/findPw")
		public void findPw(@ModelAttribute MemberDTO mdto, HttpServletResponse response)throws Exception {
			mservice.findPw(response, mdto);
			
		}
		
		//회원 탈퇴
		@PostMapping("/withDrawal")
		public String withDrawal(@ModelAttribute MemberDTO mdto
				,HttpSession session, HttpServletResponse response) throws Exception{
			if(mservice.withDrawl(mdto, response)) {
				session.invalidate();
			}
			return "redirect:/index";
		}
		
		@GetMapping("/myPage")
		public String myPage() throws Exception {	
			return "/member/myPage";
		}
		
		@PostMapping("/updateMyPage")
		public String myPage(@ModelAttribute MemberDTO mdto, HttpSession session, RedirectAttributes rttr) throws Exception {
			session.setAttribute("mdto", mservice.updateMyPage(mdto));
			rttr.addFlashAttribute("msg","회원정보 수정 완료");
			return "redirect:/member/myPage";
		}
		
		@PostMapping("updatePw")
		public String updatePw(@ModelAttribute MemberDTO mdto,
				@RequestParam("old_pw") String old_pw, HttpSession session, 
				HttpServletResponse response, RedirectAttributes rttr) throws Exception {
			session.setAttribute("mdto", mservice.updatePw(mdto, old_pw, response));
			rttr.addFlashAttribute("msg", "비밀번호 수정 완료");
			return "redirect:/member/myPage";
		}
		/*KAKAO Login*/
		@RequestMapping("/kakao")
		public String home(@RequestParam(value="code", required = false) String code, HttpSession Msession) throws Exception{
			String access_Token = mservice.getAccessToken(code);
			HashMap<String, Object> userInfo = mservice.getUserInfo(access_Token);
			System.out.println("loginController: "+userInfo.get("email"));
		
			if(userInfo.get("email") != null) {				
				Msession.setAttribute("mdto", userInfo.get("email"));
			}
			return "index";
		}
		@GetMapping("/profile/{user_id}")
		public ResponseEntity<byte[]> getProfile(@PathVariable String user_id) throws Exception{
			System.out.println("/user/profile"+user_id+"Post요청");
			return null;
			
		}
				
}