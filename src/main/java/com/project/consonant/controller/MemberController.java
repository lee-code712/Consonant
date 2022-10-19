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
import com.project.consonant.service.exception.SignUpException;

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
			
			// 회원의 id, point, rank를 세션에 저장
			member.setPasswd(null);
			session.setAttribute("member", member);
			
			return "redirect:/home";
		} catch (LoginException e) {
			model.addAttribute("loginFailed", true);
			model.addAttribute("e", e.getMessage());
			return "loginForm";
		}	
	}
	
	/* 로그아웃 */
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "loginForm";
	}
	
	/* 회원가입 */
	@GetMapping("/signUp")
	public String signUpForm() {
		return "signUpForm";
	}
	
	@PostMapping("/signUp")
	public String signUp(@Valid @ModelAttribute("member") Member member, BindingResult result,
			HttpSession session, Model model) {
		
		if (result.hasErrors()) {
			return "signUpForm";
		}
		
		try {
			memberSvc.signUp(member);
			return "loginForm";
		} catch (SignUpException e) {
			model.addAttribute("signUpFailed", true);
			model.addAttribute("e", e.getMessage());
			return "signUpForm";
		}	
	}
	
}
