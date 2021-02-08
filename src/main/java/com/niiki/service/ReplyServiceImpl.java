package com.niiki.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niiki.dto.TblReplyDTO;
import com.niiki.mapper.ReplyDAO;
@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	private ReplyDAO rdao;
	
	@Transactional
	public int replyWrite(TblReplyDTO rdto) throws Exception {
		int result = rdao.replyWrite(rdto);
		rdao.updateComtCount(rdto);
		return result;
	}
	
	public ArrayList<TblReplyDTO> replyList(int bno) {
		return rdao.replyList(bno);
	}
	
	@Transactional
	public int replyDelete(TblReplyDTO rdto) {
		int result = rdao.replyDelete(rdto);
		rdao.updateComtCount(rdto);
		return result;
	}

}
