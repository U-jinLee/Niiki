<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${path}/resources/css/default.css">
<link rel="stylesheet" href="${path}/resources/css/member/myPage.css">
<script type="text/javascript" src="${path}/resources/js/jquery-3.5.1.js"></script>
<script type="text/javascript" src="${path}/resources/js/myPage.js"></script>
<script>
	$(function(){
		if(${msg ne null}){
			alert("${msg}");
		};
	})
</script>
<meta charset="UTF-8">
<title>닛키: 내 정보</title>
</head>
<body>
<section class="myPage">
	<h2>내 정보</h2>
	
	<%@ include file="../member/profile.jsp" %>
	
	<form id="myForm" action="/member/updateMyPage" method="post">
		<ul>
			<li>
				<label>ID</label>
			</li>
			<li>
				<input type="text" id="user_id" name="user_id" readonly="readonly" value="${mdto.user_id}">
			</li>
			<li>
				<label>Email</label>
			</li>
			<li>
				<input type="text" id="user_email" name="user_email" value="${mdto.user_email}">
			</li>
			<li>
				<button type="submit">회원정보 변경</button>
			</li>
		</ul>
	</form>
		<br/>
	<form id="myPwBtn" action="/member/updatePw" method="post">
		<input type="hidden" name="user_id" value="${mdto.user_id}">
		<ul>
			<li>
				<label>Password</label>
			</li>
			<li>
				<div class="pw_wrap">
					<input type="password" id="old_pw" name="old_pw" value="${mdto.user_pw}" required="required">
					<input type="password" id="user_pw" name="user_pw" required="required" placeholder="변경할 비밀번호 입력">
				</div>
			</li>
			<li>
				<button type="submit" id="myPwBtn">비밀번호 변경</button>
			</li>
		</ul>
	</form>	
		<br/>
	<form action="/member/withDrawal" method="post">
		<input type="hidden" name="user_id" value="${mdto.user_id}">
		<ul>
			<li>
				<label>Confirm</label>
			</li>
			<li>
				<input type="password" class="user_pw_with" name="user_pw" required="required">
			</li>
			<li>
				<button type="submit">회원 탈퇴</button>
			</li>
		</ul>
	</form>
</section>
</body>
</html>