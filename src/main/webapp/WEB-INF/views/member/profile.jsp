<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
<div class="profile">
	<div class="img">
		<div class="title_image">
			<c:choose>
				<c:when test="${mdto.profile_photo == null}">
					<img src="${path}/resources/profileImage/noImage.jpg" class="profile_image">
				</c:when>
				<c:otherwise>
					<img src="${path}/resources/profileImage/${mdto.user_id}/${mdto.profile_photo}" class="profile_image">
				</c:otherwise>
			</c:choose>
		</div>
		<div class="fileBox">
			<form action="/upload/uploadProfile" id="form" name="form" method="post" enctype="multipart/form-data" autocomplete="off">
				<input type="file" id="ex_file" name="fileName" required />
				<input type="hidden" name="user_id" value="${mdto.user_id}">
				<button type="submit" class="btn btn-default">사진 변경</button>
			</form>
		</div>
	</div>
</div>
</body>
</html>