package com.niiki.service;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.niiki.dto.BoardDTO;
import com.niiki.dto.BoardLikeDTO;
import com.niiki.dto.Criteria;
import com.niiki.mapper.BoardAttachDAO;
import com.niiki.mapper.BoardDAO;

@Service
public class BBSServiceImpl implements BBSService {
	@Autowired
	BoardDAO bdao;
	@Autowired
	BoardAttachDAO badao;
	
	public void boardWrite(BoardDTO bdto, HttpServletResponse response) throws Exception {
		bdao.boardWrite(bdto);
	}
	
	public ArrayList<BoardDTO> BBSList(Criteria cri) throws Exception {
		return bdao.BBSList(cri);
	}
	
	public ArrayList<BoardDTO> myBBSList(Criteria cri) throws Exception {
		return bdao.myBBSList(cri);
	}

	@Transactional
	public BoardDTO BBSDetail(int bno) throws Exception {
		bdao.increaseReadCount(bno);
		return bdao.BBSDetail(bno);
	}

	public int getTotal() throws Exception {
		return bdao.getTotal();
	}
	
	public int myGetTotal(String user_id) throws Exception {
		int result = bdao.myGetTotal(user_id);
		return result;
	}

	public void likeFunction(BoardLikeDTO bldto, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int result = bdao.getBoardLike(bldto);
		System.out.println("#####service: "+result);
		if(result == 0) {
			bdao.createBoardLike(bldto);
			bdao.updateBoardLike(bldto);
			result = 0;
			out.println(result);
		} else {
			bdao.deleteBoardLike(bldto);
			bdao.updateBoardLike(bldto);
			result = 1;
			out.println(result);
		}
	}

	public void recCount(int bno, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int count = bdao.recCount(bno);
		out.println(count);
		out.close();
	}

	//게시글 삭제
	public void BBSDelete(int bno, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		int DeleteResult = 1818;
		out.println(DeleteResult);
		bdao.BBSDelete(bno);
	}

	public void boardModify(BoardDTO bdto) throws Exception {
		bdao.boardModify(bdto);
	}

	@Override
	public void approval_board(int bno) throws Exception {
		bdao.approval_board(bno);
	}

}
