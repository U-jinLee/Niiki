<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<link rel="stylesheet" href="${path}/resources/css/default.css">
<link rel="stylesheet" href="${path}/resources/css/member/findPwForm.css">
<script type="text/javascript" src="${path}/resources/js/jquery-3.5.1.js"></script>
<script type="text/javascript" src="${path}/resources/js/findInfoForm.js"></script>
<meta charset="UTF-8">
<title>닛키: 비밀번호 찾기</title>
</head>
<body>
	<section class="findPwFrm1">
		<h2>비밀번호 찾기</h2>
		<form name="frm2">
			<ul>
				<li>
					<input type="text" id="userId" name="user_id" title="아이디" placeholder="아이디 입력" value="${user_id}" maxlength="20">
				</li>
				<li>
					<input type="text" id="userEmail" name="user_email" title="이메일" placeholder="메일 주소 입력" value="${user_email}" maxlength="100">
				</li>
				<li>
					<button type="submit" id="findPwBtn">비밀번호 찾기</button>
				</li>
				<li>
					<button type="button" onclick="history.go(-1)">취소</button>
				</li>				
			</ul>
		</form>
	</section>
</body>
</html>