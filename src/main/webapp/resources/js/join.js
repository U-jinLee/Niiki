/**
 * 
 */

$(document).ready(function(){
	// 옵션과 관련된 Method
	function selectOption(id, color, msg, option) {
		$(id).css('color', color).html(msg);
		
		if(option == 'false'){		
	        $("#joinBtn").attr("disabled","disabled");
		} else {
			$("#joinBtn").removeAttr("disabled");
		}
	} 
	
	$("#joinForm").submit(function(){
		if($("#user_pw").val() !== $("#user_pw_check").val()){
			alert("비밀번호가 다릅니다.");
			$("#user_pw").val("").focus;
			$("#user_pw_check").val("");
			return false;
		} else if($("#user_pw").val().length <= 8) {
			alert("비밀번호는 8자 이상으로 입력해 주세요");
            $("#user_pw").val("").focus;
            $("#user_pw_check").val("");
			return false;
        }	
	})
	

	$('#bir_yy, #bir_MM, #bir_dd').blur(function(){
		var y = $("#bir_yy").val();
		var m = $("#bir_MM").val();
		var d = $("#bir_dd").val();
		var birth = $("#user_birth").val(y+'-'+m+'-'+d);
			$.ajax({
			url : "/member/userBirthCheck",
			type : "POST",
			data : {
				user_birth : $("#user_birth").val() 
			},
			success : function(result){
			if(result == "true"){
				selectOption("#userBirth_check", '#08a600', "", 'true');
			}
			},
			error: function(){
					selectOption("#userBirth_check", '#F20530', "유효하지 않은 날짜입니다.", 'false');
			}
	});
})

	
	$("#user_id").blur(function(){
			$.ajax({
				url : "/member/userID_check",
				type : "POST",
				data : {
					user_id : $("#user_id").val()
				},
				success : function(result){
					if (result == 1){
						selectOption("#userID_check", '#F20530', "중복된 아이디가 있습니다.", 'false');
					} else {
						selectOption("#userID_check", '#08a600', "사용가능한 아이디입니다!", 'true');
					}
				},
			});
		})
			
	/*-- Password check --*/
	// 비밀번호 유효성 검사
	$("#user_pw").keyup(function(){
		/*개별 문자: https://hamait.tistory.com/342*/
		var pw = $("#user_pw").val();
		var num = pw.search(/[0-9]/g);
		var char =pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
		
		if(pw.length < 8 || pw.length > 20){
			selectOption("#userPw_check", '#F20530', "8 ~ 20자리 이내로 입력해 주세요", 'false');
		} else if(pw.search(/\s/) != -1) {
			selectOption("#userPw_check", '#F20530',"비밀번호는 공백 없이 입력해 주세요",'false');
		} else if (num < 0 || char < 0) {
			selectOption("#userPw_check", '#F20530',"숫자, 특수문자를 혼용해서 사용해 주세요",'false');
		} else {
			selectOption("#userPw_check", '#08a600',"사용 가능한 비밀번호입니다",'true');
		}
	})
	// 비밀번호 확인 유효성 재검사
	$("#user_pw_check").blur(function(){
		if($("#user_pw").val() !== $("#user_pw_check").val()){
			selectOption("#userPw_check", '#F84C20',"동일한 비밀번호가 아닙니다",'false');
		}else {
			selectOption("#userPw_check", '#08a600',"사용 가능한 비밀번호입니다",'true');
		}
	})
	/*-- END --*/
	//	
		
})