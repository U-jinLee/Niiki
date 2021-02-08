<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<div class="container">
		<form id="commentForm" name="commentForm" method="post">
		<input type="hidden" id="bno" name="bno" value="${bdto.bno}">
		<br><br>
			<div class="commentBox">
				<div class="commentOption">
					<h2 class="commentTitle">댓글</h2><span id="cCnt"></span>
				</div>
				<div class="commentWriter">
						<div>
							<input type="hidden" id="replyer" name="replyer" value="${mdto.user_id}"><h4>${mdto.user_id}</h4>
						</div>
					<table class="table">
						<tr>
							<td>
								<textarea class="comment_inbox_text" rows="3" cols="30" id="reply" name="reply" placeholder="댓글을 남겨주세요!"
								style="overflow: hidden; overflow-wrap: break-word; height: 17px;"></textarea>
								<br>
								<div class="commentAttach">
									<a href="#" class="button" id="replyAddBtn">등록</a>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
	</div>
	<div class="container">
		<form action="commentListForm" name="commentListForm" method="post">
			<div id="commentList">
			</div>
		</form>
	</div>
</body>
</html>