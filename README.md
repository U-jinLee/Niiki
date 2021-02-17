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
<!-- 회원가입 -->	
  <insert id="memberJoin">
    insert into member values(
    #{user_id}, #{user_pw}, #{user_name}, #{user_birth}, #{user_gender}, #{user_email}, now(), now(), 'false', #{approvalKey}, null)
  </insert>
```

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

### 아이디 찾기
![ezgif com-gif-maker (3)](https://user-images.githubusercontent.com/71121964/107353008-e3796f80-6b0f-11eb-9896-186d58cd0e03.gif)
> 이메일을 입력해 회원의 아이디를 찾을 수 있습니다.
>> 1. '로그인 하기' 버튼을 클릭 시
>> - ![ezgif com-gif-maker (5)](https://user-images.githubusercontent.com/71121964/107934987-1ade9500-6fc4-11eb-9d46-af6f2ea6bbb2.gif)
>> 2. '비밀번호 찾기' 버튼을 클릭 시
>> - ![ezgif com-gif-maker (6)](https://user-images.githubusercontent.com/71121964/107936693-48c4d900-6fc6-11eb-9d71-99c26d9940b3.gif)
#### Controller
```java
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
```
#### Service
```java
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
```
#### DAO
```java
<select id="findIDForm" parameterType="String" resultType="com.niiki.dto.MemberDTO">
		select user_id, user_email
		from member
		where user_email = #{user_email}
	</select>
```
### 비밀번호 찾기
![ezgif com-gif-maker (4)](https://user-images.githubusercontent.com/71121964/107356838-a82d6f80-6b14-11eb-9d04-50d3923d848b.gif)
> 아이디와 이메일을 입력해 회원의 변경된 비밀번호를 찾을 수 있습니다.

#### Controller
```java
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
```
#### Service
```java
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
```
#### DAO
```java
<!-- 비밀번호 변경 -->
	<update id="updatePw" parameterType="com.niiki.dto.MemberDTO">
		update member 
		set user_pw = #{user_pw}
		where user_id= #{user_id}
	</update>
```

### 내 정보
![ezgif com-gif-maker](https://user-images.githubusercontent.com/71121964/108015297-4c527180-7053-11eb-8fbb-9ad5de0c0bee.png)
> 회원의 정보 변경 가능 페이지 아래와 같은 정보를 수정할 수 있습니다.
>> 1. 사진 변경
>> 2. 회원정보 변경
>> 3. 비밀번호 변경
>> 4. 회원 탈퇴
#### Controller
```java
	@GetMapping("/myPage")
	public String myPage() throws Exception {	
		return "/member/myPage";
	}
	// 회원정보 변경	
	@PostMapping("/updateMyPage")
	public String myPage(@ModelAttribute MemberDTO mdto, HttpSession session, RedirectAttributes rttr) throws Exception {
		session.setAttribute("mdto", mservice.updateMyPage(mdto));
		rttr.addFlashAttribute("msg","회원정보 수정 완료");
		return "redirect:/member/myPage";
	}
	// 비밀번호 수정	
	@PostMapping("updatePw")
	public String updatePw(@ModelAttribute MemberDTO mdto, @RequestParam("old_pw") String old_pw, HttpSession session, 		HttpServletResponse response, RedirectAttributes rttr) throws Exception {
		session.setAttribute("mdto", mservice.updatePw(mdto, old_pw, response));
		rttr.addFlashAttribute("msg", "비밀번호 수정 완료");
		return "redirect:/member/myPage";
	}
	// 회원 탈퇴
	@PostMapping("/withDrawal")
	public String withDrawal(@ModelAttribute MemberDTO mdto, HttpSession session, HttpServletResponse response) 
	throws Exception {
		if(mservice.withDrawl(mdto, response)) {
			session.invalidate();
		}
		return "redirect:/index";
		}
		
	@GetMapping("/profile/{user_id}")	
	public ResponseEntity<byte[]> getProfile(@PathVariable String user_id) throws Exception{
		System.out.println("/user/profile"+user_id+"Post요청");
		return null;
	}
```
#### Service
```java
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
	
```
#### DAO
```java
<!-- 회원정보 변경 -->
	<update id="updateMyPage" parameterType="com.niiki.dto.MemberDTO">
		update member 
		set user_email = #{user_email} 
		where user_id= #{user_id}
	</update>
<!-- 비밀번호 변경 -->
	<update id="updatePw" parameterType="com.niiki.dto.MemberDTO">
		update member 
		set user_pw = #{user_pw}
		where user_id= #{user_id}
	</update>
<!-- 회원 탈퇴 -->
	<delete id="withDrawal" parameterType="com.niiki.dto.MemberDTO">
		delete 
		from member 
		where user_id= #{user_id} and user_pw= #{user_pw};
	</delete>
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
![ezgif com-gif-maker (7)](https://user-images.githubusercontent.com/71121964/108163618-b1c95f80-7132-11eb-82ba-8e338c2a4d2c.gif)
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 글보기 상세
![ezgif com-gif-maker (12)](https://user-images.githubusercontent.com/71121964/108167397-f952ea00-7138-11eb-85ca-d5fd6669e454.gif)
1. 글을 클릭했을 시 나오는 페이지로 '글 수정'과 '글 삭제'를 할 수 있습니다. 자신이 쓴 글이 아닌 경우 '글 수정'과 '글 삭제'가 불가능합니다.

![ezgif com-gif-maker](https://user-images.githubusercontent.com/71121964/108175516-353f7c80-7144-11eb-85fe-0ee6efc2d2e6.gif)
2. 일기장에서는 '공유하기' 버튼이 있으며 고민상담소에서는 '추천하기', '신고하기', '댓글달기'를 할 수 있습니다.
  
>> 1. 글 수정
>> - ![ezgif com-gif-maker (10)](https://user-images.githubusercontent.com/71121964/108165529-10dca380-7136-11eb-9bf8-7f18bba5a0fb.gif)
>> 2. 글 삭제
>> - ![ezgif com-gif-maker (11)](https://user-images.githubusercontent.com/71121964/108165774-7761c180-7136-11eb-9ed5-4972cc2b5cdc.gif)
#### Controller
```java
```
#### Service
```java
```
#### DAO
```java
```
### 댓글 달기
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
