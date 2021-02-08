/**
 * 
 */
function btn_click(src){
	if(src=="login"){
		frmFind.action="/member/login";
		frmFind.method="get";
	}else{
		frmFind.action="/member/findPwForm";
		frmFind.method="get";
	}
}

$(document).ready(function(){
	$("#findPwBtn").click(function(){
		$.ajax({
			url : "/member/findPw",
			type : "POST",
			data : {
				user_id : $("#userId").val(),
				user_email : $("#userEmail").val()
			},
			success : function(result){
				alert(result);
			},
		});
	})

})