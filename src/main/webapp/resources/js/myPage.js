/**
 * 
 */

$(function(){		
		if($("#myPwBtn").submit(function(){
			  if ($("#user_pw").val().length < 8) {
				alert("비밀번호는 8자 이상으로 설정해야 합니다.");
				$("#user_pw").val("").focus();
				return false;
			} else if($.trim($("#user_pw").val()) !== $("#user_pw").val()){
				alert("공백은 입력이 불가능합니다.");
				return false;
			}
		})
		);
		
	})