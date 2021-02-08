<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${path}/resources/css/default.css">
<link rel="stylesheet" href="${path}/resources/css/bbs/boardDetail.css">
<script type="text/javascript" src="${path}/resources/js/jquery-3.5.1.js"></script>
<script type="text/javascript" src="${path}/resources/js/bbs/tblReply.js"></script>
<script type="text/javascript" src="${path}/resources/js/bbs/boardDetail.js"></script>
<meta charset="UTF-8">
<title>${bdto.subject}:내 일기장</title>
</head>
<body>
<div class="article">
	<div class="article_wrap">
		<c:set var="session" value="${mdto}"/>
		<c:set var="sessionAnother" value="${bdto}"/>
		<c:if test="${session.user_id eq sessionAnother.user_id}">		
			<div class="articleTopBtn">
				<div class="left-area">
					<a href="#" class="defaultBtn" onclick="location.href='/bbs/boardModifyForm?bno=${bdto.bno}';">글 수정</a><a href="#" class="defaultBtn" id="BBSDelete">글 삭제</a>
				</div>
			</div>
		</c:if>
		<c:if test="${session.user_id ne sessionAnother.user_id}">		
			<div class="articleTopBtn" style="width: 860px; height: 36px;">
			</div>
		</c:if>
		<div class="articleContentBox">
			<div class="article_header">		
				<div class="articleTitle">
					<div class="title_area">
						<h3 class="title_text">${bdto.subject}</h3>
					</div>
				</div>
				<div class="writerInfo">
					<a class="thumb"><img src="${path}/resources/profileImage/noImage.jpg" width="36" height="36"></a>
					<input type="hidden" id="bno" name="bno" value="${bdto.bno}">
					<c:set var="writeDateForm" value='${bdto.writeDate}'/>
						<div class="profile_area">
							<a>${bdto.user_id}</a>
							<br><span>${fn:substring(writeDateForm,2,16)}</span>
						</div>
				</div>
			</div>
			<div class="writeContent">
					<p>
						<span>
							${bdto.content}
						</span>
					</p>
			</div>
			<c:set var="listCheckWord" value="${listCheck}"/>
			<c:if test="${listCheckWord ne 'm'}">
				<div class="box_left">
					<button type="button" id="like">추천하기</button><span id="recCount"></span>
					<button type="button" id="moveBBS">신고하기</button>
				</div>			
						
				<%@ include file="../reply/comment.jsp" %>
			</c:if>		
			<c:if test="${listCheckWord eq 'm'}">
				<div class="box_left">
					<button type="button" id="moveBBS">공유하기</button>
				</div>
				<div class="commentWriter_after" style="height: 162px;">
				</div>			
						
			</c:if>		
		</div>
	</div>
</div>
</body>
</html>