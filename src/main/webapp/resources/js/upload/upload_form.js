/**
 * 
 */
$(document).ready(function(){
	var formObj = $("form[role='form']"); //속성선택자: formObj == form
	$("input[type='submit']").on("click",function(e){
		e.preventDefault();
		console.log("Click the submitInput");
		var str="";
		$(".uploadResult ul li").each(function(i,obj){
			var jobj=$(obj);
			
			str+="<input type='text' name='attachList["+i+"].fileName' value='"+jobj.data("fileName")+"'>";
			str+="<input type='text' name='attachList["+i+"].uuid' value='"+jobj.data("uuid")+"'>";
			str+="<input type='text' name='attachList["+i+"].uploadPath' value='"+jobj.data("uploadPath")+"'>"
			})
		formObj.append(str).submit();
	})
$("input[type='file']").change(function(e){
	var formData = new FormData();
	var inputFile = $("input[name='uploadFile']");
	var files=inputFile[0].files;
	for(var i=0;i<files.length;i++){
		formData.append("uploadFile",files[i]);
	}
	$.ajax({
		url:"/upload/uploadForm",
		data:formData,
		dataType:"json",
		processData:false,
		contentType:false,
		type:'POST',
		success:function(data){
			alert("업로드 됨");
			console.log(data);
			showUploadResult(data);
		}
		
	})
})

function showUploadResult(uploadResultArr){
	if(!uploadResultArr || uploadResultArr.length==0){
		return;
	}
	var uploadUL=$(".uploadResult ul");
	var str="";
	$(uploadResultArr).each(function(i, obj){
		console.log("obj.uploadPath"+obj.uploadPath);
		console.log("obj.uuid"+obj.uuid);
		console.log("obj.fileName"+obj.fileName);
		var fileCallPath = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);
		str+="<li data-path='"+obj.uploadPath+"'";
		str+=" data-uuid='"+obj.uuid+"' data-filename'"+obj.fileName+"'"
		str+="><div>";
		str+="<span> "+obj.fileName+"</span>";
		str+="<img src='/upload/display?fileName="+fileCallPath+"'>";
		str+="</div></li>";
		})
		uploadUL.append(str);
}
	
})