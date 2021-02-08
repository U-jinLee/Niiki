<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${path}/resources/css/default.css">
<link rel="stylesheet" href="${path}/resources/css/bbs/boardWriteForm.css">
<script type="text/javascript" src="${path}/resources/js/jquery-3.5.1.js"></script>
<script type="text/javascript" src="${path}/resources/js/upload/upload_form.js"></script>
<meta charset="UTF-8">
<title>닛키: 게시판 글 작성</title>
</head>
<body>
	<form role="form" action="/bbs/boardWrite" method="post" enctype="multipart/form-data">
		<div class="articleWrite">
			<div class="layout_wrap">	
				<section class="container">
				<div class="writingWrap">
					<div class="writingHeader">
						<h2 class="title">오늘의 일기</h2>
						<div class="tool_area">
							<button type="button" class="btn_temp_save" onclick="history.go(-1)">취소</button>
							<button type="submit" class="btn_temp_save" id="checkBtn">확인</button>
						</div>
					</div>	
					<div class="writingContent">
						<div class="writingEditor">
							<div class="FlexableTextArea">
								<input name="subject" id="subject" class="textarea_input" name="subject" required="required" placeholder="제목을 입력해 주세요." style="height:40px;">
								<input type="hidden" name="uploadFile" class="textarea_input" value="파일 첨부" multiple="multiple">
								<div class="textarea_content">
									<textarea name="content" id="content" class="textarea_input" rows="10" placeholder="내용을 입력해 주세요." style="height: 480px;" required></textarea>
								</div>
							</div>
												
							<input type="hidden" name="user_id" value="${mdto.user_id}">
						</div>
					</div>	
					<div class="uploadResult">
						<ul>
						</ul>
					</div>
				</div>	
				</section>
			</div>
		</div>
	</form>
</body>
</html>