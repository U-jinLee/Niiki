package com.niiki.mapper;

import org.springframework.transaction.annotation.Transactional;

import com.niiki.dto.MemberDTO;

public interface MemberDAO {
	public int memberJoin(MemberDTO mdto);
	public int userID_check(String mdto);
	
	/*로그인*/
	public MemberDTO memberLogin(String id);
	public int update_log(String id);
	
	@Transactional
	public int approval_member(MemberDTO mdto);
	/*아이디 찾기*/
	public MemberDTO findIDForm(String user_email);
	
	public int updatePw(MemberDTO member);
	@Transactional
	public int updateMyPage(MemberDTO member);
	/*회원 탈퇴*/
	public int withDrawal(MemberDTO mdto);
}