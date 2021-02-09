# NIIKI

## 기획의도
> 학원에서 배운 내용을 바탕으로 일기장을 기본으로 하는 SNS를 제작해보자 하는 의도에서 만들었습니다. 평소에는 일기장과 같이 활용을 하다가 다른 사람의 조언이 필요하거나 고민 상담이 필요할 때 고민 게시판으로 일기를 보내 다른 사람들과 소통을 할 수 있게 하는 것을 중점으로 제작했습니다.
## 개발환경
- OS: window10
- IDE: Eclipse
- Server: Apache tomcat 9.0
- DB: MySQL
- Language: JAVA, JSP, JavaScropt(jquery)
- framework: Spring, bootstrap
- ORM: MyBatis
## UI설계
![Screenshot 2021-01-28 오전 2_28_23](https://user-images.githubusercontent.com/71121964/106030196-44b54200-6111-11eb-9275-f71eb08d7842.png)
- 링크: https://xd.adobe.com/view/04dce06f-d797-407d-80fd-6714a93d9d87-518f/?fullscreen
## Database ERD
![ExampleERD](https://user-images.githubusercontent.com/71121964/105983757-8a0b4c80-60dc-11eb-8342-b07f756fd380.PNG)
## NIIKI 기능 설명
![ezgif com-gif-maker](https://user-images.githubusercontent.com/71121964/105987500-b5dd0100-60e1-11eb-9bb8-9b732234177c.gif)
### 구현 기능
#### [관리자 기능]
------------
🚧
#### [사용자 기능]
------------
- 로그인 페이지
- 회원가입
  - 이메일 인증
- 내 정보
  - 사진 변경
  - 회원 정보 변경
  - 비밀번호 변경
  - 회원탈퇴
- 내 일기장
- 고민상담소
  - 검색창
- 아이디 찾기
- 아이디 찾기 결과창
  - 로그인 하기
  - 비밀번호 찾기
- 비밀번호 찾기
- 게시글_고민상담소
  - 추천하기
- 게시글_일기장
  - 공유하기
- 글쓰기 창
- 글수정 창
- 이메일 보내기 결과창
## 구현 기능 설명
### 로그인 페이지 
![ezgif com-gif-maker (1)](https://user-images.githubusercontent.com/71121964/106282509-068c5f80-6284-11eb-933d-236eb73f99fc.gif)
> 로그인 후 메인 화면의 문구가 변경돼 사용자를 반겨줍니다. 이메일 인증을 받아야 로그인이 가능하며 로그인 시에는 아이디 유무 확인, 비밀번호 확인 과정을 거칩니다. 로그인에 성공할 시 유저의 로그인 일자가 업데이트됩니다.
#### Controller
```java
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
```
#### Service
```java
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
```
#### DAO
```java
<!-- 아이디 유무 체크 -->
  <select id="userID_check" resultType="int">
    select count(*)
    from member 
    where user_id=#{user_id};
  </select>
<!-- 로그인 -->
  <select id="memberLogin" parameterType="String" resultType="com.niiki.dto.MemberDTO">
    select *  
    from member
    where user_id=#{user_id}
  </select>
<!-- 로그인 일자 업데이트 -->
  <update id="update_log" parameterType="String">
    update member
    set logDate = now()
    where user_id = #{user_id}
  </update>
```
### 회원가입
![ezgif com-gif-maker (2)](https://user-images.githubusercontent.com/71121964/107344598-fab35f80-6b05-11eb-9381-23747c04c6a6.gif)
> 회원가입 관련 창입니다. 회원가입을 할 동안 아래와 같은 확인 과정을 가집니다.
>> 1. 이미 중복된 아이디가 존재한다면 가입 불가
>> 2. 문자 길이가 8자 이하 20자 이상일 때, 공백이 존재할 때, 숫자 및 특수문자를 혼용하지 않았을 시 가입 불가
>> 3. 비밀번호 중복 확인 시 입력한 비밀번호가 같지 않다면 가입 불가
>> 4. 생년월일이 정상적이지 않을 시(ex:0000-00-35) 가입 불가
#### Controller
```java
/*회원가입 구현*/
	//회원가입 창
	@GetMapping("/join")
		public void getJoin() {	
	}
	//회원가입
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
```
#### Service
```java
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
	
/*유저아이디 중복 체크*/
	public void userID_check(String id, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		out.println(mdao.userID_check(id));
		out.close();
	}
	/*End*/
```
#### DAO
```java
<!-- 아이디 유무 체크 -->
  <select id="userID_check" resultType="int">
    select count(*)
    from member 
    where user_id=#{user_id};
  </select>
<!-- 아이디 유무 체크 -->	
  <insert id="memberJoin">
    insert into member values(
    #{user_id}, #{user_pw}, #{user_name}, #{user_birth}, #{user_gender}, #{user_email}, now(), now(), 'false', #{approvalKey}, null)
  </insert>
```
### 내 정보
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 내 일기장
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 고민상담소
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 아이디 찾기
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 아이디 찾기 결과창
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 비밀번호 찾기
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 게시글_고민상담소
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 게시글_일기장
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 글쓰기 창
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 글수정 창
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 이메일 보내기 결과창
#### Service
```java
public void send_mail(MemberDTO mdto, String div) throws Exception {
		// Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com";
		String hostSMTPid="leeujin1029";
		String hostSMTPpwd="*****";
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
			email.setSmtpPort(***);
			
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
```
