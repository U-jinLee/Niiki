<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" href="${path}/resources/css/default.css">
	<link rel="stylesheet" href="${path}/resources/css/bbs/boardList.css?ver1">
	<script type="text/javascript" src="${path}/resources/js/jquery-3.5.1.js"></script>
	<meta charset="UTF-8">
	<title>닛키: 고민 상담소</title>
</head>
<body>
	<div class="cafe_body">
	  <div>
	  	<div class="info_tit">
		  	<h3 class="sub_tit_color">고민 상담소</h3>
	  	</div>
	  	<form action="/bbs/boardWriteForm" method="get" name="frm">
		  	<table>
		  		<colgroup>
		  			<col style="width:88px">
		  			<col>
		  			<col style="width:118px">
		  			<col style="width:80px">
		  			<col style="width:68px">
		  			<col style="width:68px">
		  		</colgroup>
		  		<thead class="normarTableTitle">
		  			<tr class="">
		  				<th>번호</th>
		  				<th>제목</th>
		  				<th>작성자</th>
		  				<th>작성일</th>
		  				<th>조회수</th>
		  				<th>추천</th>
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
		  		<col style="width:68px">
		  		<col style="width:68px">
		  	</colgroup>
			<tbody>
				<!-- Model Parameter을 통해 BBSController에서 받아온 ArrayList를 -->
				<!-- 'BBSList'라는 변수명에 넣어 사용한다. -->
				<c:forEach items="${BBSList}" var="BBSList">
					<c:set var="writeDateForm" value='${BBSList.writeDate}'/>
					<tr>
						<!--위에서부터 순서대로 번호, 제목, 작성자, 작성일 순으로 삽입해 준다.  -->
						<td>${BBSList.bno}</td>
						<td><a href="/bbs/boardDetail?bno=${BBSList.bno}">${BBSList.subject}</a><span id="comtCount"> (${BBSList.comtCount}) </span></td>
						<td>${BBSList.user_id}</td>
						<td>${fn:substring(writeDateForm,2,16)}</td>
						<td>${BBSList.readCount}</td>
						<td>${BBSList.recCount}</td>
					</tr>
				</c:forEach>
			</tbody>		
		</table>
	</div>	
		  		
	  	</form>
	  		<div class="prev_next">
			  	<c:if test="${pageMaker.prev}"><a href="/bbs/boardList?pageNum=${pageMaker.startPage-1}&amount=${pageMaker.cri.amount}">👈</a></c:if>
			  	<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
			  		<a href="/bbs/boardList?pageNum=${num}&amount=${pageMaker.cri.amount}">${num}</a>
			  	</c:forEach>
			  	<c:if test="${pageMaker.next}"><a href="/bbs/boardList?pageNum=${pageMaker.endPage+1}&amount=${pageMaker.cri.amount}">👉</a></c:if>
	  		</div>
	  		<div class="list_search">	  		
				<form action="/bbs/boardList" method="get">
					<div class="selectComponent">
						<select name="type">
							<option value="s">제목</option>
							<option value="c">내용</option>
							<option value="u">작성자</option>
						</select>
					</div>
					<div class="input_search_area">
						<div class="input_component">
							<input type="text" name="keyword" placeholder="검색어를 입력해 주세요.">
						</div>
						<button type="submit" class="btn_search_green">검색</button>
					</div>
				</form>  	
	  		</div>
	  </div>
	</div>
</body>
</html>