/**
 * 
 */
$(document).ready(function(){	
	recCount();
		
	function recCount(){
		$.ajax({
			type : 'POST',
			url : '/bbs/recCount',
			dataType:'json',
			data : {bno : $('#bno').val()},
			success: function(count) {
				$("#recCount").text(count);
			},
			error:function(){
				alert("좋아요 recCount 실패");
			}
		})
	}
	
	/*좋아요 관련 함수*/
	$("#like").on("click",function() {
		$.ajax({
			type : 'POST',
			url : '/bbs/like',
			dataType:'json',
			data : {bno : $('#bno').val(), user_id : $('#replyer').val()},
			success : function(result) {
				recCount();
			},
			error:function(){
				alert("좋아요 실패");
			}
		})
	})
	/*삭제 관련 ajax*/
	$("#BBSDelete").on("click",function() {
		$.ajax({			
			type : 'POST',
			url : '/bbs/BBSDelete',
			dataType : 'json',
			data : {bno : $("#bno").val()},
			success : function(result) {
				if(result){
					alert("게시글 삭제 성공");
				 	location.href="/index";
				}
			},
			error : function(request, status, error){
				alert("code: "+request.status+"message: "+request.responseText+"error: "+error);
				alert("통신 연결 실패");
			}
		});
	})
	
		$("#moveBBS").on("click",function() {
		$.ajax({			
			type : 'POST',
			url : '/bbs/moveBBS',
			dataType : 'text',
			data : {bno : $("#bno").val()},
			success : function(result) {
				if(result){
					alert("게시글 이동 성공");
				 	location.href="/index";
				}
			},
			error : function(request, status, error){
				alert("code: "+request.status+"message: "+request.responseText+"error: "+error);
				alert("통신 연결 실패");
			}
		});
	})
})

