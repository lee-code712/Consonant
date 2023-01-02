package com.project.consonant.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.consonant.domain.Member;
import com.project.consonant.service.SpeedGameService;

@Controller
@RequestMapping("/game/speed")
public class SpeedGameController {
	
	@Autowired
	SpeedGameService speedGameSvc;
	
	/* 스피드 퀴즈 화면 이동 */
	@GetMapping("/playGame")
	public String goPlayGame(Model model) {
		List<String> randomConsonants = speedGameSvc.getRandomConsonants();
		
		if (randomConsonants != null) {
			model.addAttribute("randomConsonants", randomConsonants);
		}
		
		return "speedGame";
	}
	
	/* 스피드 퀴즈 결과 화면 이동 */
	@PostMapping("/resultGame")
	public String resultGame(@RequestParam(value="answers", required=false) Set<String> answers,
			@RequestParam(value="consonants") List<String> consonants, Model model, HttpSession session) {
		
		if (answers == null) {
			model.addAttribute("resultMap", new HashMap<String, String>());
			return "speedGameResult";
		}
		
		List<String> answerList = new ArrayList<String>(answers); // 중복을 제거한 set을 list로 변경
		
		// 입력한 단어 체크
		Map<String, String> resultMap = speedGameSvc.checkConsonants(consonants, answerList);
		
		if (resultMap != null) {
			// 값이 X가 아닌 개수만큼 포인트 부여, 총 점수 및 랭킹 갱신
			Member member = (Member) session.getAttribute("member");
			member = speedGameSvc.reflectGameResult(member, resultMap);
			
			if (member != null) {
				session.setAttribute("member", member);	// 세션에 저장한 회원 정보 갱신
			}
			model.addAttribute("resultMap", resultMap);
		}
		
		return "speedGameResult";
	}
	

}
