package com.niiki.service;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import com.niiki.dto.MemberDTO;

public interface MemberService {
	public MemberDTO memberLogin(MemberDTO mdto, HttpServletResponse response) throws Exception;
	public void memberLogout(HttpServletResponse response) throws Exception;
	public void userID_check(String id, HttpServletResponse response) throws Exception;
	public int memberJoin(MemberDTO mdto, HttpServletResponse response) throws Exception;
	
	public void approval_member(MemberDTO mdto,HttpServletResponse response) throws Exception;
	public void send_mail(MemberDTO mdto, String div) throws Exception;
	public String createKey();
	
	public MemberDTO findIDForm(HttpServletResponse response, String user_email) throws Exception;
	public boolean userBirthCheck(String user_birth, String format, HttpServletResponse response) throws Exception;
	public void findPw(HttpServletResponse response, MemberDTO mdto) throws Exception;
	
	public MemberDTO updateMyPage(MemberDTO mdto) throws Exception;
	public MemberDTO updatePw(MemberDTO mdto, String oldPw, HttpServletResponse response) throws Exception;
	
	public boolean withDrawl(MemberDTO mdto, HttpServletResponse response) throws Exception;
	/*카카오 로그인*/
	public String getAccessToken (String autorize_code) throws Exception;
	public HashMap<String, Object> getUserInfo (String access_Token) throws Exception;
}