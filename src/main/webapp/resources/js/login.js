/**
 * 
 */

function btn_click(src){
	if(src=="login"){
		frm1.action="/member/login";
		frm1.method="post";
	}else{
		frm1.action="/member/kakao";
		frm1.method="get";
	}
}