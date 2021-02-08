package com.niiki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niiki.dto.MemberDTO;
import com.niiki.mapper.BoardAttachDAO;

@Service
public class BoardAttachServiceImpl implements BoardAttachService{
	@Autowired
	private BoardAttachDAO badao;

	@Override
	public void memberUpdate(MemberDTO mdto) throws Exception {
		badao.profileUpdate(mdto);
	}

}
