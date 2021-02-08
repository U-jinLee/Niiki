<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>

	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Document</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
	<link rel="stylesheet" href="${path}/resources/css/footerCss.css?ver1">
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
					<li><a href="#" onclick="location.href='/member/logout'">Logout</a></li>
				</ul>
			</c:if>
   	</header>
        <section class="banner">
        	<c:if test="${mdto == null}">
        		<div>
	        		<p class="loginCheck">Hi!;)<br>
	        		I'm NIIKI</p>
        		</div>
			</c:if>
        	<c:if test="${mdto != null}">
        		<div>
	        		<p class="loginCheck">Hello!;)<br>
	        		${mdto.user_id}</p>
        		</div>
			</c:if>
        	<div id="char_img">
	        	<img src="https://media.giphy.com/media/VbhSMgqSvh2ygMd8eK/giphy.gif">
        	</div>
        </section>
		<Script type="text/javascript">
        window.addEventListener("scroll",function(){
            var header = document.querySelector("header");
            header.classList.toggle("sticky", window.scrollY > 0);
        })
        </Script>
        <section class="banner"></section>
        
    <jsp:include page="footer.jsp"/>
</body>
</html>