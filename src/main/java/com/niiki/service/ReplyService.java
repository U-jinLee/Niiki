package com.niiki.service;

import java.util.ArrayList;

import com.niiki.dto.TblReplyDTO;

public interface ReplyService {
	public int replyWrite(TblReplyDTO rdto) throws Exception;

	public ArrayList<TblReplyDTO> replyList(int bno);

	public int replyDelete(TblReplyDTO rdto);
	
}
