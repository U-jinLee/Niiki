package com.niiki.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.niiki.dto.BoardDTO;
import com.niiki.dto.BoardLikeDTO;
import com.niiki.dto.Criteria;

public interface BBSService {
	public void boardWrite(BoardDTO bdto, HttpServletResponse response) throws Exception;
	/*게시글을 받아올 서비스 인터페이스*/
	public ArrayList<BoardDTO> BBSList(Criteria cri) throws Exception;
	public ArrayList<BoardDTO> myBBSList(Criteria cri) throws Exception;
	public BoardDTO BBSDetail(int bno) throws Exception;
	//게시글 삭제
	public void BBSDelete(int bno, HttpServletResponse response) throws Exception;
	public void boardModify(BoardDTO bdto) throws Exception;
	//게시글 수 가져오기 서비스 인터페이스
	public int getTotal() throws Exception;
	public int myGetTotal(String user_id) throws Exception;
	/*좋아요 기능관련 서비스 인터페이스*/
	public void likeFunction(BoardLikeDTO bldto, HttpServletResponse response) throws Exception;
	public void recCount(int bno, HttpServletResponse response) throws Exception;
	public void approval_board(int bno) throws Exception;
}
