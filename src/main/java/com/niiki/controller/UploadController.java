package com.niiki.controller;
import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.niiki.dto.BoardAttachDTO;
import com.niiki.dto.MemberDTO;
import com.niiki.dto.ProfileAttachDTO;
import com.niiki.service.BoardAttachService;

@Controller
@RequestMapping("/upload")
public class UploadController {
	@Autowired
	private BoardAttachService baservice;
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@GetMapping("/uploadForm")
	public void uploadForm() throws Exception{
	}
	
		//현재 날짜 생성 메소드
	private String getFolderDate() {
		//날짜 형식 생성
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//현재 날짜 나오게 하기
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
		}
		
	@PostMapping("/uploadForm")
	public ResponseEntity<ArrayList<BoardAttachDTO>> uploadForm(MultipartFile[] uploadFile) throws Exception{
		ArrayList<BoardAttachDTO> list = new ArrayList<>();
		String uploadFolder ="C:\\Users\\USER-PC\\git\\Niiki\\src\\main\\webapp\\resources\\profileImage";
		String uploadFolderPath = getFolderDate();
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		//동일한 이름의 파일 업로드 시 기존 파일에 덮어지는 문제 해결
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for(MultipartFile files : uploadFile) {
			logger.info("####파일명: "+files.getOriginalFilename());
			logger.info("####파일크기: "+files.getSize());
			
			BoardAttachDTO attach = new BoardAttachDTO();
			String uploadFileName = files.getOriginalFilename();
			attach.setFileName(uploadFileName);
			UUID uuid =UUID.randomUUID();
			logger.info("####uuid= "+uuid);
			uploadFileName = uuid.toString()+"_"+files.getOriginalFilename();		
			try {
				File saveFile= new File(uploadPath,uploadFileName);
				logger.info("####saveFile: "+saveFile);
				files.transferTo(saveFile);
				attach.setUploadPath(uploadFolderPath);
				attach.setUuid(uuid.toString());
				list.add(attach);
				logger.info("list"+list);
			} catch (Exception e) {
				e.getMessage();
			}
		}
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFiles(String fileName){
		File file = new File("C:\\Users\\USER-PC\\git\\Niiki\\src\\main\\webapp\\resources\\profileImage\\"+fileName);
		ResponseEntity<byte[]> result= null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("content_type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/*-------------------------test page---------------------------------*/
	
	@PostMapping("/uploadProfile")
	private String uploadProfile(@RequestParam("user_id") String user_id, @RequestParam("fileName") MultipartFile mFile, @ModelAttribute MemberDTO mdto) throws Exception {
		UUID uuid =  UUID.randomUUID();
		Create_user_profile(user_id);
		String profile_photo = uuid.toString()+"_"+mFile.getOriginalFilename();
		mdto.setProfile_photo(profile_photo);
		baservice.memberUpdate(mdto);
		File saveFile = new File(Create_user_profile(user_id), profile_photo);
		System.out.println("############SaveFileName: "+saveFile);
		mFile.transferTo(saveFile);
		return "/index";
	}
	
	private String Create_user_profile(String user_id) {
		String userID = user_id;
		String path ="C:\\Users\\USER-PC\\git\\Niiki\\src\\main\\webapp\\resources\\profileImage\\"+userID;
		File folder = new File(path);
		
		if(!folder.exists()) {
			try {
				folder.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path+"\\";
	} 


}



