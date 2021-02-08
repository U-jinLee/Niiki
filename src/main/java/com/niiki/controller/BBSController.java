package com.niiki.controller;

import javax.servlet.http.HttpServletResponse;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.niiki.dto.BoardDTO;
import com.niiki.dto.BoardLikeDTO;
import com.niiki.dto.Criteria;
import com.niiki.dto.PageDTO;
import com.niiki.service.BBSService;

@Controller
@RequestMapping("/bbs")
public class BBSController {
	/*게시판 관련 서비스*/
	@Autowired
	private BBSService bservice;
	
	// 게시판 글 작성
	@PostMapping("/boardWrite")
	public String boardWrite(@ModelAttribute BoardDTO bdto,RedirectAttributes rttr, HttpServletResponse response) throws Exception{
		rttr.addAttribute("user_id", bdto.getUser_id());
		bservice.boardWrite(bdto, response);
		return "redirect:/bbs/myBoardList";
	}
	// 게시글 삭제
	@PostMapping("/BBSDelete")
	public void BBSDelete(@RequestParam("bno") int bno, HttpServletResponse response) throws Exception {
		int integerNum = bno;
		bservice.BBSDelete(integerNum, response);
	}
	
	// 게시판 글 작성 폼 이동
	@GetMapping("/boardWriteForm")
	public String boardWriteForm() throws Exception{
			return "/bbs/boardWriteForm";
	}
	@ResponseBody
	@PostMapping("/moveBBS")
	public void moveBBS(int bno) throws Exception{
		bservice.approval_board(bno);
	}
	
	@GetMapping("/boardModifyForm")
	public void getBoardModifyForm(@ModelAttribute BoardDTO bdto, Model model) throws Exception{
		model.addAttribute("bdto", bservice.BBSDetail(bdto.getBno()));
	}
	
	@PostMapping("/boardModify")
	public String postBoardModify(@ModelAttribute BoardDTO bdto) throws Exception{
		bservice.boardModify(bdto);
		return "/index";
	}
	
	//게시판 목록 페이지
	@GetMapping("/boardList")
	public void boardList(Criteria cri,Model model) throws Exception {
		System.out.println("#######cri: "+cri);
		int total = bservice.getTotal();
		model.addAttribute("BBSList", bservice.BBSList(cri));
		model.addAttribute("pageMaker",new PageDTO(cri,total));
	}
	
	//마이게시판 목록 페이지
	@GetMapping("/myBoardList")
	public void myBoardList(Criteria cri, Model model, @RequestParam("user_id") String user_id) throws Exception {
		System.out.println("#######cri: "+cri);
		cri.setUser_id(user_id);
		int total = bservice.myGetTotal(user_id);
		model.addAttribute("myBBSList", bservice.myBBSList(cri));
		model.addAttribute("pageMaker",new PageDTO(cri,total));
	}
	
	//게시판 글 디테일
	@GetMapping("/boardDetail")
	public String boardDetail(Model model, @RequestParam("bno") int bno, @RequestParam(value="listCheck", required=false) String listCheck) throws Exception{
		model.addAttribute("listCheck", listCheck);
		model.addAttribute("bdto", bservice.BBSDetail(bno));
		return "/bbs/boardDetail";
	}
	@PostMapping("/like")
	public void boardLike(@ModelAttribute BoardLikeDTO bldto, HttpServletResponse response) throws Exception{
		System.out.println("#####user_id: "+bldto.getUser_id());
		System.out.println("#####bno: "+bldto.getBno());
		bservice.likeFunction(bldto, response);
	}
	
	@PostMapping("/recCount")
	public void recCount(@RequestParam("bno") int bno, HttpServletResponse response) throws Exception {
		bservice.recCount(bno, response);
	}
}