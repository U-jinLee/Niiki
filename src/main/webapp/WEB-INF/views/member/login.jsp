<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${path}/resources/css/default.css"/>
    <link rel="stylesheet" href="${path}/resources/css/member/login.css?"/>
    <script type="text/javascript" src="${path}/resources/js/login.js"></script>
    <title>닛키: 로그인</title>
</head>
<body>
    <section class="login"> <!--X, Y축 자체가 가운데 정렬이 필요-->
	        <h2>로그인</h2>
        <form name="frm1">
	        <ul>
	            <li> <input type="text" placeholder="아이디" title="아이디 입력" name="user_id" value="${user_id}" maxlength="20"></li>
	            <li> <input type="password" placeholder="비밀번호" title="비밀번호 입력" name="user_pw" maxlength="20"></li>
	            <li> <input type="checkbox" id="chk_id"><label for="chk_id">아이디저장</label></li>
	            <li> <button type="submit" onclick="btn_click('login')">로그인</button></li>
	            <li> <a href="https://kauth.kakao.com/oauth/authorize?client_id=클라이언트 아이디&redirect_uri=http://localhost:8080/member/kakao&response_type=code">
    						<button type="button" id="kakao">카카오 로그인</button></a></li>
	        </ul>
        </form>
	        <div>
	            <ul>
	                <li><a href="#" onclick="location.href='/member/join';">회원가입</a></li>
	                <li><a href="#" onclick="location.href='/member/findIDForm';">아이디 찾기</a></li>
	                <li><a href="#" onclick="location.href='/member/findPwForm';">비밀번호 찾기</a></li>
	            </ul>
	        </div>
    </section>
</body>
</html>