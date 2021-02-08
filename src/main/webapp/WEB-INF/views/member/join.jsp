<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<link rel="stylesheet" href="${path}/resources/css/default.css">
    <link rel="stylesheet" href="${path}/resources/css/member/join.css?ver1">
    <script type="text/javascript" src="${path}/resources/js/jquery-3.5.1.js"></script>
    <script type="text/javascript" src="${path}/resources/js/join.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>닛키: 회원가입</title>
</head>
<body>
    <section class="Join">
    	<h2>회원가입</h2>
    	<form id="joinForm" action="/member/join" method="POST">
            <ul>
                <li>아이디</li>
                <li><input type="text" id="user_id" title="아이디 입력" name="user_id" maxlength="20"></li>
                <span id=userID_check></span>
                <span></span>
                <li>비밀번호</li>
                <li><input type="password" id="user_pw" title="비밀번호 입력" name="user_pw" maxlength="20"></li>
                <span id=userPw_check></span>
                <span></span>
                <li>비밀번호 확인</li>
                <li><input type="password" id="user_pw_check" title="비밀번호 재확인"></li>
                <li>이름</li>
                <li><input type="text" title="이름 입력" name="user_name"></li>
                <li>생년월일</li>
                <li>
                	<div id="bir_wrap">
	                	<input type="text" title="생년월일 입력" placeholder="년(4자)" id="bir_yy">                
	                    <select name="memberMonth" title="생년월일 입력" class="birth" id="bir_MM">
	                        <option>월</option>
	                        <option value="01">01</option>
	                        <option value="02">02</option>
	                        <option value="03">03</option>
	                        <option value="04">04</option>
	                        <option value="05">05</option>
	                        <option value="06">06</option>
	                        <option value="07">07</option>
	                        <option value="08">08</option>
	                        <option value="09">09</option>
	                        <option value="10">10</option>
	                        <option value="11">11</option>
	                        <option value="12">12</option>
	                    </select>
	                	<input type="text" title="생년월일 입력" placeholder="일" class="birth" id="bir_dd">
	                	<input type="hidden" id="user_birth" name="user_birth">
                	</div>
                </li>
                <span id=userBirth_check></span>
                <span></span>
                <li>성별</li>
                <li>
                    <select id="user_gender" name="user_gender" title="생년월일 입력">
                        <option>성별</option>
                        <option value="m">남자</option>
                        <option value="w">여자</option>
                        <option value="n">선택 안함</option>
                    </select>
                </li>
                <li>본인 확인 이메일</li>
                <li><input type="text" title="아이디 입력" id="user_email" name="user_email" placeholder="선택입력" maxlength="100"></li>
                <li><button type="submit" id="joinBtn">회원가입</button></li>
            </ul>    
    	</form>
    </section>
</body>
</html>