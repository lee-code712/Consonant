package com.project.consonant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game/speed")
public class SpeedGameController {
	
	/* 스피드 퀴즈 화면 이동 */
	@GetMapping("/playGame")
	public String goPlayGame() {
		return "speedGame";
	}

}
