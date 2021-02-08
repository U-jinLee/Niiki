<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Document</title>
	<link rel="stylesheet" href="${path}/resources/css/style.css">
</head>
<body>
	<header>
		<a href="#" class="logo">NIIKI</a>
	    	<c:if test="${mdto == null}">
			    <ul>
					<li><a href="#" onclick="location.href='/member/login'">login</a></li>
			    </ul>
			</c:if>
			<c:if test="${mdto != null}">
				<ul>
					<li><a href="#" onclick="location.href='/member/myPage';">Mypage</a></li>
					<li><a href="#" onclick="location.href='/bbs/boardList';">Talk</a></li>
					<li><a href="#" onclick="location.href='/bbs/myBoardList?user_id=${mdto.user_id}';">Diary</a></li>
					<li><a href="#" onclick="location.href='/bbs/myBoardList?user_id=${mdto.user_id}';">Logout</a></li>
				</ul>
			</c:if>
   	</header>
        <section class="banner"></section>
		<Script type="text/javascript">
        window.addEventListener("scroll",function(){
            var header = document.querySelector("header");
            header.classList.toggle("sticky", window.scrollY > 0);
        })
        </Script>
	   <!--  <header>
	        <a href="/member/login" class="logo">NIIKI</a>
	        <ul>
	            <c:if test="${mdto == null}">
				<input type="button" value="로그인" onclick="location.href='/member/login'">
				</c:if>
				<c:if test="${mdto != null}">
					<input type="button" value="로그아웃" onclick="location.href='/member/logout'">
					<p>${mdto.user_id}님 환영합니다.</p>
					<li><a href="#" onclick="location.href='/member/myPage';">마이페이지</a></li>
					<li><a href="#" onclick="location.href='/bbs/boardList';">고민 상담소</a></li>
					<li><a href="#" onclick="location.href='/bbs/myBoardList?user_id=${mdto.user_id}';">내 일기장</a></li>
				</c:if>
	        </ul>
	    </header>-->
</body>
</html>

