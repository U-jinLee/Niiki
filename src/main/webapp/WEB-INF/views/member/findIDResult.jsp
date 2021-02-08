<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${path}/resources/css/default.css">
<link rel="stylesheet" href="${path}/resources/css/member/findIDResult.css">
<script type="text/javascript" src="${path}/resources/js/jquery-3.5.1.js"></script>
<script type="text/javascript" src="${path}/resources/js/findInfoForm.js"></script>
<meta charset="UTF-8">
<title>닛키: 아이디 찾기</title>
</head>
<body>
	<section class="findIDResult">
		<h2>아이디 찾기</h2>
		<form name="frmFind" method="post">
			<input type="hidden" name="user_id" value="${user_id}" readonly="readonly">
					<input type="hidden" name="user_email" value="${user_email}" readonly="readonly">
			<ul>
				<li>
					<p>요청하신 아이디는<br><span>${user_id}</span>입니다.</p>
				</li>
				<li>
					<button type="submit" onclick="btn_click('login')">로그인 하기</button>
				</li>
				<li>
					<button type="submit" onclick="btn_click('Pw')">비밀번호 찾기</button>
				</li>
			</ul>
		</form>
	</section>
</body>
</html>