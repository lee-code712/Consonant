package com.project.consonant.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

}
