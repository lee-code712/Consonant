package com.project.consonant.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.consonant.domain.Category;
import com.project.consonant.domain.CreateGameCommand;
import com.project.consonant.domain.InputQuiz;
import com.project.consonant.domain.Member;
import com.project.consonant.service.GameService;

@Controller
@RequestMapping("/game")
public class GameController {
	@Autowired
	GameService gameSvc;
	
	/*게임 만드는 화면으로 이동*/
	@GetMapping("/createGame")
	public ModelAndView goCreateGame(HttpSession session) throws Exception{
		Member memberInfo = (Member) session.getAttribute("member");
		System.out.println(memberInfo.getMemberId());
		List<Category> categoryList = gameSvc.goCreateGame();
		ModelAndView mav = new ModelAndView();
		mav.addObject("memberInfo", memberInfo);
		mav.addObject("categoryList", categoryList);
		mav.addObject("createGameCommand", new CreateGameCommand());
		mav.addObject("inputQuiz", new InputQuiz());
		mav.setViewName("createGame");
		return mav;
	}
	
	@PostMapping("/createGame")
	public ModelAndView createGame(HttpSession session, @Valid @ModelAttribute("createGameCommand") CreateGameCommand createGameCommand, BindingResult result) throws Exception{
		
		Member memberInfo = (Member) session.getAttribute("member");
		
		if (result.hasErrors()) {
			ModelAndView errorMav = new ModelAndView();
			List<Category> categoryList = gameSvc.goCreateGame();
			errorMav.addObject("categoryList", categoryList);
			//errorMav.addObject("createGameCommand", new CreateGameCommand());
			//errorMav.addObject("inputQuiz", new InputQuiz());
			errorMav.setViewName("createGame");
			return errorMav;
		}
		
		//System.out.println(createGameCommand.getGameTitle() + " " + createGameCommand.getGameDifficulty());
		List<InputQuiz> inputQuizList = gameSvc.getInputQuizList();
		
		createGameCommand.setMemberId(memberInfo.getMemberId());
		System.out.println("아이디: " + createGameCommand.getMemberId() + " 퀴즈리스트 사이즈: "+inputQuizList.size());
		boolean createResult = gameSvc.createGame(createGameCommand, inputQuizList);
		 // 리스트로 가도록 수정
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		return mav;
	}
	
	@PostMapping("/insertQuiz")
	public String insertQuiz(Model model, @Valid @RequestBody InputQuiz inputQuiz, BindingResult result) throws Exception{
		//@ModelAttribute("inputQuiz")
		//@RequestBody
		
		if (result.hasErrors()) {
			//System.out.println("확인: " + inputQuiz.getQuestion() );
			List<Category> categoryList = gameSvc.goCreateGame();
			model.addAttribute("categoryList", categoryList);
			return "createGame::#insertQuizForm";
		}
		
		List<InputQuiz> inputQuizList = gameSvc.insertQuiz(inputQuiz);
		model.addAttribute("inputQuizList", inputQuizList);
		return "createGame::#quizBox";
	}
	
	@PostMapping("/removeQuiz")
	public String removeQuiz(Model model, @RequestBody String question) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = mapper.readValue(question, Map.class);
		List<InputQuiz> inputQuizList = gameSvc.removeQuiz(map.get("question"));
		model.addAttribute("inputQuizList", inputQuizList);
		return "createGame::#resultDiv";
	}
}
