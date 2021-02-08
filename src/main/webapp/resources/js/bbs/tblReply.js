/**
 * 
 */

$(document).ready(function(){
	var bno = $("#bno").val();
	
	getList();
	
	function getList(){
		var str = "";
		/*JSON type의  data를 보내준다.*/	
		$.getJSON("/replies/"+bno+".json",
			function(data){
				console.log(data);
				$(data).each(function(){
					str+="<li data-rno="+this.rno+" id=replies"+this.rno+">"+this.replyer+" "+this.replyDate.substring(2,10).replaceAll("-",".")+
					"<br>"+this.reply+" "+"<button type='button' id='replyDelBtn'>"+"삭제"+"</button>"+"</li>"+"<br>"
				})
				$("#commentList").html(str)
			})
	}
	/*댓글 추가*/
	$("#replyAddBtn").on("click",function(){
		var reply = $("#reply").val();
		var replyer = $("#replyer").val();
		$.ajax({
			type: "POST",
			url: "/replies/addReply",
			data: JSON.stringify({bno:bno,replyer:replyer,reply:reply}),
			contentType:"application/json;charset=UTF-8",
			success:function(){
				if(result="success"){
					console.log(bno);
					$("#reply").val("");
					getList();
				}
			},
			error:function(){
				alert("댓글쓰기 실패");
			}
		});
	})
	/*댓글 삭제*/
	$("#commentList").on("click", "#replyDelBtn", function(e){
		e.preventDefault();
		var reply = $(this).parent();
		var bno = $("#bno").val();
		rno = reply.attr("data-rno");
		$.ajax({
			type:"delete",
			url:"/replies/del",
			contentType:"application/json; charset=UTF-8",
			data:JSON.stringify({bno:bno, rno:rno}),
			success:function(result){
				if(result=="success"){
					getList();
				}
			},
			error:function(){
				alert("댓글 삭제 실패");
			}
		})
		
		
		
	})
	
})