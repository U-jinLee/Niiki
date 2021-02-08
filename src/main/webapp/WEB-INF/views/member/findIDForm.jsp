<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${path}/resources/css/default.css">
<link rel="stylesheet" href="${path}/resources/css/member/findIDForm.css">
<script type="text/javascript" src="${path}/resources/js/jquery-3.5.1.js"></script>
<meta charset="UTF-8">
<title>닛키: 아이디 찾기</title>
</head>
<body>
	<section class="findIDFrm1">
		<h2>아이디 찾기</h2>
		<form name="frm1" action="/member/findIDResult" method="post">
			<ul>
				<li>
					<input type="text" id="findIDBtn" name="user_email" title="이메일" placeholder="인증받은 메일 입력" maxlength="100">
				</li>
				<li>
					<button type="submit" id="findBtn">아이디 찾기</button>
				</li>				
				<li>
					<button type="button" id="findBtn" onclick="history.go(-1)">취소</button>
				</li>				
			</ul>
		</form>
	</section>
</body>
</html>