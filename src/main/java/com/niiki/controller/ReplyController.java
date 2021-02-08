package com.niiki.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.niiki.dto.TblReplyDTO;
import com.niiki.service.ReplyService;

@RestController
@RequestMapping(value="/replies")
public class ReplyController {
	@Autowired
	ReplyService rservice;
	
	@PostMapping("/addReply")
	public ResponseEntity<String> replyWrite(@RequestBody TblReplyDTO rdto) throws Exception{
		System.out.println("수정에 필요한 값이 필요에 있는지 확인"+ rdto);
		int result = rservice.replyWrite(rdto);
		return result == 1 ? new ResponseEntity<>("success",HttpStatus.OK):
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	@GetMapping("/{bno}")
	public ResponseEntity<ArrayList<TblReplyDTO>> getList(@PathVariable("bno") int bno) throws Exception{
		return new ResponseEntity<>(rservice.replyList(bno), HttpStatus.OK);
	}
	
	@RequestMapping(value="/del", method=RequestMethod.DELETE)
	public ResponseEntity<String> delete(@RequestBody TblReplyDTO rdto) throws Exception {
		System.out.println("수정에 필요한 값이 필요에 있는지 확인"+ rdto);
		int result = rservice.replyDelete(rdto);
		System.out.println("수정 result:"+result);
		return result == 1 ? new ResponseEntity<>("success",HttpStatus.OK):
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
