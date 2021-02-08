package com.niiki.mapper;

import com.niiki.dto.BoardAttachDTO;
import com.niiki.dto.MemberDTO;

public interface BoardAttachDAO {
	
	public void insert(BoardAttachDTO badto);
	
	public void profileUpdate(MemberDTO mdto);
}
