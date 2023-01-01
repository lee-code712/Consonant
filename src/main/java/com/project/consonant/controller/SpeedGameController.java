package com.project.consonant.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@PostMapping("/resultGame")
	public String resultGame(@ModelAttribute("answers") Set<String> answers,
			@ModelAttribute("consonants") List<String> consonants, Model model) {
		List<String> answerList = new ArrayList<String>(answers); // 중복을 제거한 set을 list로 변경
		
		// 입력한 단어 체크
		Map<String, String> resultMap = speedGameSvc.checkConsonants(consonants, answerList);
		
		if (resultMap != null) {
			// 값이 X가 아닌 개수 10개당 10포인트 부여, 랭킹 갱신
			
			model.addAttribute("resultMap", resultMap);
		}
		
		return "speedGameResult";
	}
	

}
