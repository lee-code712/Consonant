package com.project.consonant.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.consonant.domain.Member;
import com.project.consonant.service.MemberService;
import com.project.consonant.service.exception.LoginException;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberSvc;
	
	@ModelAttribute("member")
	public Member formBacking() {
		return new Member();
	}
	
	/* 로그인 */
	@GetMapping("/login")
	public String loginForm() {
		return "loginForm";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("member") Member member, BindingResult result,
			HttpSession session, Model model) {
		
		if (result.hasErrors()) {
			return "loginForm";
		}
		
		try {
			member = memberSvc.login(member);
			session.setAttribute("id", member.getMemberId());
			return "home";
		} catch (LoginException e) {
			model.addAttribute("loginFailed", true);
			model.addAttribute("e", e.getMessage());
			return "loginForm";
		}
		
	}
	
}
