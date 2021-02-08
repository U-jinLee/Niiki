package com.niiki.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.niiki.dto.MemberDTO;
import com.niiki.mapper.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO mdao;
	/*로그인 기능*/
	public MemberDTO memberLogin(MemberDTO mdto, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//아이디 확인
		if(mdao.userID_check(mdto.getUser_id()) == 0) {
			out.println("<script>");
			out.println("alert('등록된 아이디가 존재하지 않습니다.')");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return null;
		}
		//비밀번호 확인
		else {
			String user_pw = mdto.getUser_pw();
			mdto = mdao.memberLogin(mdto.getUser_id());
			if(!(mdto.getUser_pw().equals(user_pw))) {
				out.println("<script>");
				out.println("alert('비밀번호가 다릅니다.')");
				out.println("history.go(-1);");
				out.println("</script>");
				out.close();
				return null;
			}
			//이메일 인증 확인
			else if(!(mdto.getApprovalStatus().equals("true"))) {
				out.println("<script>");
				out.println("alert('이메일 인증 후 로그인 하세요.')");
				out.println("history.go(-1);");
				out.println("</script>");
				out.close();
				return null;
			}
			//로그인 성공
			else {
				mdao.update_log(mdto.getUser_id());
				return mdto;
			}
			
		}
	}
	
	
	/*가입관련 무작위 키 생성*/
	public String createKey() {
		String key="";
		Random r = new Random();
		
		for(int i=0; i<8; i++) {
			key += r.nextInt(10);
		}
		
		return key;
	}
	/*End*/
	
	/*회원가입 관련 기능*/
	public int memberJoin(MemberDTO mdto, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=utf-8");
		
		if(mdao.userID_check(mdto.getUser_id()) == 1) {
			out.println("<script>");
			out.println("alert('동일한 아이디가 존재합니다.');");
			out.println("history.go(-1)");
			out.println("</script>");
			out.close();
			return 0;
		} else {
			mdto.setApprovalKey(createKey());
			mdao.memberJoin(mdto);
			send_mail(mdto,"join");
			return 1;
		}
	}
	/*End*/

	public void userID_check(String id, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		out.println(mdao.userID_check(id));
		out.close();
	}

	public void send_mail(MemberDTO mdto, String div) throws Exception {
		// Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com";
		String hostSMTPid="leeujin1029";
		String hostSMTPpwd="자신의 비밀번호 입력";
		// 발신자 EMail, 제목, 내용
		String fromEmail = "leeujin1029@naver.com";
		String fromName="NIIKI";
		String subject ="";
		String msg = "";
		
		if(div.equals("join")) {
		// 회원가입 메일 내용
		subject = "NIIKI 회원가입 인증 메일입니다.";
		msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
		msg += "<h3 style='color: blue;'>";
		msg += mdto.getUser_name()+"님 회원가입을 환영합니다.</h3>";
		msg += "<div style='font-size: 130%'>";
		msg += "아래의 번호를 입력한 후<br>하단의 인증 버튼을 클릭해 주세요!</div><br/>";
		msg += "<form method='post' action='http://localhost:8080/member/approval_member'>";
		msg += "<p style='font-size:250%'>"+mdto.getApprovalKey()+"</p>";
		msg += "<input type='hidden' name='user_id' value='" + mdto.getUser_id() + "'>";
		msg += "<input type='text' name='approvalKey'><br>";
		msg += "<input type='submit' value='인증'></form><br/></div>";
		} else if(div.equals("findPw")) {
			subject += "NIIKI 비밀번호 찾기 메일입니다.";
			msg +="<div align='center' style='border:1px solid black; font-family:verdana'>";
			msg +="<h3 style='color: blue;'>";
			msg += mdto.getUser_id()+"님의 비밀번호입니다.</h3>";
			msg +="<p>비밀번호: ";
			msg +=mdto.getUser_pw()+"</p></div>";
		}
		// 받는 사람 E-Mail 주소
		String mail = mdto.getUser_email();
		try {
			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setCharset(charSet);
			email.setSSL(true);
			email.setHostName(hostSMTP);
			email.setSmtpPort(000); //smtpPort 번호
			
			email.setAuthentication(hostSMTPid, hostSMTPpwd);
			email.setTLS(true);
			email.addTo(mail, charSet);
			email.setFrom(fromEmail, fromName, charSet);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
			email.send();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("메일발송 실패: "+e);
		}
	}

	public void approval_member(MemberDTO mdto, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out= response.getWriter();
		if(mdao.approval_member(mdto) == 0) {
			out.println("<script>");
			out.println("alert('잘못된 접근입니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
		} else {
			out.println("<script>");
			out.println("alert('본인 인증 완료! 로그인 후 이용해 주세요.');");
			out.println("location.href='/member/login';");
			out.println("</script>");
			out.close();
		}
	}

	public void memberLogout(HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("location.href='/index';");
		out.println("</script>");
		out.close();
	}
	public MemberDTO findIDForm(HttpServletResponse response, String user_email) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		MemberDTO id = mdao.findIDForm(user_email);
		if(id == null) {
			out.println("<script>");
			out.println("alert('해당 이메일로 가입된 아이디가 존재하지 않습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return null;
		} else {
			return id;
		}
	}

	public boolean userBirthCheck(String user_birth, String format, HttpServletResponse response) throws Exception {
		SimpleDateFormat dateFormatParser = new SimpleDateFormat(format,Locale.KOREA);
		dateFormatParser.setLenient(false);
		try {
			System.out.println("최종값:"+dateFormatParser.parse(user_birth));
			return true;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void findPw(HttpServletResponse response, MemberDTO mdto) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		//아이디가 없으면
		if(mdao.userID_check(mdto.getUser_id()) == 0) {
			out.println("아이디가 없습니다.");
			out.close();
		}
		//가입에 사용한 이메일이 아니면
		else if(!mdto.getUser_email().equals(mdao.memberLogin(mdto.getUser_id()).getUser_email())) {
			out.println("잘못된 이메일입니다.");
			out.close();
		} else {
			String pw = "";
			for(int i=0; i<12; i++) {
				pw += (char)((Math.random()*26)+97);
			}
			mdto.setUser_pw(pw);
			mdao.updatePw(mdto);
			send_mail(mdto, "findPw");
			out.print("이메일로 임시 비밀번호를 발송했습니다.");
			out.close();
		}
	}


	public MemberDTO updateMyPage(MemberDTO mdto) throws Exception {
		mdao.updateMyPage(mdto);
		return mdao.memberLogin(mdto.getUser_id());
	}


	public MemberDTO updatePw(MemberDTO mdto, String oldPw, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if(!oldPw.equals(mdao.memberLogin(mdto.getUser_id()).getUser_pw())) {
			out.println("<script>");
			out.println("alert('기존 비밀번호가 다릅니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return null;
		} else {
			mdao.updatePw(mdto);
			return mdao.memberLogin(mdto.getUser_id());
		}
	}

	public boolean withDrawl(MemberDTO mdto, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if(mdao.withDrawal(mdto) != 1) {
			out.println("<script>");
			out.println("alert('회원탈퇴 실패');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return false;
		} else {
			return true;
		}
	}
	
	//KAKAO
	public String getAccessToken(String autorize_code) {
		String access_Token="";
		String refresh_Token="";
		String reqURL="https://kauth.kakao.com/oauth/token";
		
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			// POST 요청을 위해 기본값이 false인 setDOout를 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			//POST 요청에 필요로 요구하는 파라미터 스트링을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			
			sb.append("grant_type=authorization_code");
			sb.append("&"); // 본인이 발급받은 key
			sb.append("&redirect_uri=http://localhost:8080/member/kakao"); // 본인이 설정해 놓은 경로
			sb.append("&code="+autorize_code);
			bw.write(sb.toString());
			bw.flush();
			
			//결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode: "+responseCode);
			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line= "";
			String result= "";
			
			while((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response_body"+result);
			
			// GSON Library에 포함된 클래스로 JSON 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			
			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
			
			System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);
			System.out.println("---Access token close---");
            br.close();
            bw.close();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		return access_Token;
	}
	
	public HashMap<String, Object> getUserInfo (String access_Token) {
		HashMap<String, Object> userInfo = new HashMap<>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			
			// 요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization","Bearer " + access_Token);
			
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode: " + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line ="";
			String result="";
			
			while((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body: "+ result);
			
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			/*문제가 되는 부분*/
			//JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
			
			//String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String email = kakao_account.getAsJsonObject().get("email").getAsString();
			
			//userInfo.put("nickname", nickname);
			userInfo.put("email", email);
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	
}