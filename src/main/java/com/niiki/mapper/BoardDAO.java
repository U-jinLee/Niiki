package com.niiki.mapper;

import java.util.ArrayList;

import com.niiki.dto.BoardDTO;
import com.niiki.dto.BoardLikeDTO;
import com.niiki.dto.Criteria;

public interface BoardDAO {
	/*게시글 리스트 목록*/
	public ArrayList<BoardDTO> BBSList(Criteria cri);
	public ArrayList<BoardDTO> myBBSList(Criteria cri);
	public int boardWrite(BoardDTO bdto);
	public void boardModify(BoardDTO bdto);
	public void approval_board(int bno);
	/*게시글 삭제*/
	public void BBSDelete(int bno);
	
	
	/*조회수 증가 관련*/
	public void increaseReadCount(int bno);
	/*조회수 가져오기*/
	public BoardDTO BBSDetail(int bno);
	/*게시판 숫자 가져오기*/
	public int getTotal();
	public int myGetTotal(String user_id);
	
	//좋아요 관련 기능
	public void createBoardLike(BoardLikeDTO bldto);
	public void deleteBoardLike(BoardLikeDTO bldto);
	public void updateBoardLike(BoardLikeDTO bldto);
	public int getBoardLike(BoardLikeDTO bldto);
	public int recCount(int bno);
}