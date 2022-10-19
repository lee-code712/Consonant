package com.project.consonant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.consonant.domain.Member;
import com.project.consonant.service.RankingService;

@Controller
@RequestMapping("/home")
public class HomeContoller {
	
	@Autowired
	RankingService rankingSvc;

	/* 홈 이동 */
	@GetMapping("")
	public String goHome(Model model) {
		List<Member> rankingMembers = rankingSvc.getTopRankings();
		
		if (rankingMembers != null) {
			model.addAttribute("rankingMembers", rankingMembers);
		}
		return "home";
	}
	
}
