package com.niiki.mapper;

import java.util.ArrayList;

import com.niiki.dto.TblReplyDTO;

public interface ReplyDAO {
	public int replyWrite(TblReplyDTO rdto);

	public ArrayList<TblReplyDTO> replyList(int bno);

	public int replyDelete(TblReplyDTO rdto);

	public void updateComtCount(TblReplyDTO rdto);
}
