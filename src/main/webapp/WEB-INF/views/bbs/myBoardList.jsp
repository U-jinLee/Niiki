<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${path}/resources/css/default.css">
<link rel="stylesheet" href="${path}/resources/css/bbs/boardList.css">
<script type="text/javascript" src="${path}/resources/js/jquery-3.5.1.js"></script>
<meta charset="UTF-8">
<title>닛키: 일기장</title>
</head>
<body>
	<div class="cafe_body">
		<div>
			<div class="info_tit">
				<h3 class="sub_tit_color">내 일기장</h3>
			</div>
			<form action="/bbs/boardWriteForm" method="get" name="frm">
				<table>
					<colgroup>
						<col style="width:88px">
			  			<col>
			  			<col style="width:118px">
			  			<col style="width:80px">
					</colgroup>
					<thead class="normarTableTitle">
						<tr class="">
							<th>번호</th>
		  					<th>제목</th>
		  					<th>작성자</th>
		  					<th>작성일</th>
						</tr>
					</thead>
				</table>
	<div class="articleBoard">
		<table>
			<colgroup>
				<col style="width:88px">
			  	<col>
				<col style="width:118px">
			  	<col style="width:80px">
			</colgroup>
			<tbody>
				<c:forEach items="${myBBSList}" var="myBBSList">
				<c:set var="writeDateForm" value='${myBBSList.writeDate}'/>
					<tr>
						<td>${myBBSList.bno}</td>
						<td><a href="/bbs/boardDetail?bno=${myBBSList.bno}&listCheck=m">${myBBSList.subject}</a></td>
						<td>${myBBSList.user_id}</td>
						<td>${fn:substring(writeDateForm,2,16)}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>					
					
					<c:set var="user_id" value="${mdto.user_id}"/>
					<c:if test="${user_id ne null}">
						<td colspan="6">
							<input type="hidden" name="writer" value="${user_id}">
							<input class="writeBtn" type="submit" value="글쓰기">
						</td>
					</c:if>	
			</form>
			<!-- 시작 -->
			<div class="prev_next">
			<c:if test="${pageMaker.prev}"><a href="/bbs/myBoardList?pageNum=${pageMaker.startPage-1}&amount=${pageMaker.cri.amount}&user_id=${user_id}">👈</a></c:if>
		  	<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
		  		<a href="/bbs/myBoardList?pageNum=${num}&amount=${pageMaker.cri.amount}&user_id=${user_id}">${num}</a>
		  	</c:forEach>
		  	<c:if test="${pageMaker.next}"><a href="/bbs/myBoardList?pageNum=${pageMaker.endPage+1}&amount=${pageMaker.cri.amount}&user_id=${user_id}">👉</a></c:if>
		  	</div>
			<!-- 끝 -->
		</div>
	</div>
</body>
</html>