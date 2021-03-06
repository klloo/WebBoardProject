package com.huiy.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huiy.domain.BoardVO;
import com.huiy.domain.Criteria;
import com.huiy.domain.PageDTO;
import com.huiy.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/member")
@RequiredArgsConstructor
@Controller
public class MemberController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/login")
	public String login(Authentication authentication) {
		if(authentication!=null) 
			return "member/accessdenied";
		return "member/login";
	}
	@PreAuthorize("hasAnyRole('ROLE_USER,ROLE_ADMIN')")
	@GetMapping("/update")
	public String update() {
		return "member/update";
	}
	@GetMapping("/join")
	public String join(Authentication authentication) {
		if(authentication!=null) 
			return "member/accessdenied";
		return "member/join";
	}
	@PreAuthorize("hasAnyRole('ROLE_USER,ROLE_ADMIN')")
	@GetMapping("/mypage")
	public String mypage(Criteria cri,Model model,Principal principal) {
		String userid = principal.getName();
		List<BoardVO> boardList = boardService.getListWithUser(userid, cri);
		int total = boardService.getCntWithUser(userid);
		model.addAttribute("boardList",boardList);
		model.addAttribute("pageMaker",new PageDTO(cri, total));
		return "member/mypage";
	}
	@PreAuthorize("hasAnyRole('ROLE_USER,ROLE_ADMIN')")
	@GetMapping("/mypage/like")
	public String mypageLike(Criteria cri,Model model,Principal principal) {
		String userid = principal.getName();
		List<BoardVO> boardList = boardService.getListWithLike(userid, cri);
		int total = boardService.getCntWithLike(userid);
		model.addAttribute("boardList",boardList);
		model.addAttribute("pageMaker",new PageDTO(cri, total));
		return "member/mypage-like";
	}
	@PreAuthorize("hasAnyRole('ROLE_USER,ROLE_ADMIN')")
	@GetMapping("/mypage/reply")
	public String mypageReply(Criteria cri,Model model,Principal principal) {
		String userid = principal.getName();
		List<BoardVO> boardList = boardService.getListWithReply(userid, cri);
		int total = boardService.getCntWithReply(userid);
		model.addAttribute("boardList",boardList);
		model.addAttribute("pageMaker",new PageDTO(cri, total));
		return "member/mypage-reply";
	}
}
