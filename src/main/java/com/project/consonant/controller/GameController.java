package com.project.consonant.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.consonant.domain.Category;
import com.project.consonant.domain.CreateGameCommand;
import com.project.consonant.domain.Game;
import com.project.consonant.domain.GameInfoVO;
import com.project.consonant.domain.InputQuiz;
import com.project.consonant.domain.Member;
import com.project.consonant.domain.Quiz;
import com.project.consonant.service.GameService;
import com.project.consonant.service.MemberService;
import com.project.consonant.service.exception.GameException;

@Controller
@RequestMapping("/game")
public class GameController {
	@Autowired
	GameService gameSvc;
	@Autowired
	MemberService memberSvc;
	
	//게임 생성
	/*게임 만드는 화면으로 이동*/
	@GetMapping("/createGame")
	public String goCreateGame(Model model, HttpSession session) throws Exception{
		Member memberInfo = (Member) session.getAttribute("member");
		System.out.println(memberInfo.getMemberId());
		List<Category> categoryList = gameSvc.goCreateGame();

		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("createGameCommand", new CreateGameCommand());
		model.addAttribute("inputQuiz", new InputQuiz());
		
		return "createGame";
	}
	
	//게임 생성-등록
	@PostMapping("/createGame")
	public ModelAndView createGame(HttpSession session, @Valid @ModelAttribute("createGameCommand") CreateGameCommand createGameCommand, BindingResult result, @ModelAttribute("inputQuiz") InputQuiz inputQuiz) throws Exception{
		
		Member memberInfo = (Member) session.getAttribute("member");
		ModelAndView mav = new ModelAndView();
		if (result.hasErrors()) {
			List<Category> categoryList = gameSvc.getAllCategory();
			List<InputQuiz> inputQuizList = gameSvc.getInputQuizList();
			mav.addObject("categoryList", categoryList);
			mav.addObject("inputQuizList", inputQuizList);
			mav.setViewName("createGame");
		
			return mav;
		}
			
		try {
			List<InputQuiz> inputQuizList = gameSvc.getInputQuizList();
			createGameCommand.setMemberId(memberInfo.getMemberId());
			//System.out.println("아이디: " + createGameCommand.getMemberId() + " 퀴즈리스트 사이즈: "+inputQuizList.size());
			boolean createResult = gameSvc.createGame(createGameCommand, inputQuizList);
		
			if(createResult == true) {
				memberSvc.updatePoint(memberInfo.getMemberId(), 50, 1);
				Member newMemberInfo = memberSvc.findMember(memberInfo.getMemberId());
				newMemberInfo.setPasswd(null);
				session.setAttribute("member", newMemberInfo);
			}
		 
			mav.setViewName("redirect:/game/list"); //게임 생성시 리스트로 이동
		}catch (GameException e) {
			List<Category> categoryList = gameSvc.getAllCategory();
			List<InputQuiz> inputQuizList = gameSvc.getInputQuizList();
			mav.addObject("categoryList", categoryList);
			mav.addObject("inputQuizList", inputQuizList);
			mav.addObject("createFailed", true);
			mav.addObject("e", e.getMessage());
			mav.setViewName("redirect:/game/createGame");
			return mav;
		}	
		return mav;
	}
	
	//게임 생성할때 퀴즈 추가
	@PostMapping("/insertQuiz")
	public String insertQuiz(Model model, @Valid @RequestBody InputQuiz inputQuiz, BindingResult result, @ModelAttribute("createGameCommand") CreateGameCommand createGameCommand) throws Exception{

		if (result.hasErrors()) {
			return "createGame::#insertQuizForm";
		}
		
		List<InputQuiz> inputQuizList = gameSvc.insertQuiz(inputQuiz);
		model.addAttribute("inputQuizList", inputQuizList);
		return "createGame::#quizBox";
	}
	
	//게임 생성할때 추가했던 퀴즈 삭제
	@PostMapping("/removeQuiz")
	public String removeQuiz(Model model, @RequestBody String question) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = mapper.readValue(question, Map.class);
		List<InputQuiz> inputQuizList = gameSvc.removeQuiz(map.get("question"));
		model.addAttribute("inputQuizList", inputQuizList);
		return "createGame::#resultDiv";
	}
	
	//게임 시작
	@GetMapping("/playGame/{gameNo}")
	public String playGame(Model model, HttpSession session, @PathVariable("gameNo") int gameNo) throws Exception{
		GameInfoVO gameInfoVO = gameSvc.findGame(gameNo);
		System.out.println("게임 타이틀: " + gameInfoVO.getGameTitle() );
		for(Quiz q : gameInfoVO.getQuizList()) {
			System.out.println("퀴즈: " + q.getAnswer());
		}
		gameSvc.setGameInfo(new Game( gameInfoVO.getGameNo(), gameInfoVO.getGameTitle(), gameInfoVO.getGameIntro(),
							gameInfoVO.getGameDifficulty(), gameInfoVO.getQuizNumber(), gameInfoVO.getGameScore(),
							gameInfoVO.getCategoryId(), gameInfoVO.getCategoryName()));

		gameSvc.setPlayGameQuiz(gameInfoVO.getQuizList());
		
		Quiz quiz = gameSvc.getPlayGameQuiz().get(0); //첫번째 퀴즈 보냄
		String question = quiz.getQuestion();
		char[] questionArray = question.toCharArray();
		
		model.addAttribute("gameInfo", gameSvc.getGameInfo());
		model.addAttribute("quiz", quiz);
		model.addAttribute("quizQuestion", questionArray);
		model.addAttribute("currentQue", 1);
		
		return "playGame";
	}
	
	//정답 입력->다음 퀴즈로 넘기기
	@GetMapping("/playGame/{gameNo}/{quizIdx}/{answer}")
	public String solveQuiz(Model model, HttpSession session, @PathVariable("gameNo") int gameNo, @PathVariable("quizIdx") int quizIdx, @PathVariable("answer") String quizAnswer) throws Exception{
		gameSvc.getUserAnswer().put(quizIdx, quizAnswer); //입력한 답안을 답안배열에 저장
		
	    if(quizIdx + 1 == gameSvc.getPlayGameQuiz().size()) {
	    	
	 		return "redirect:/game/result"; //결과로 이동
	    }
	    else if(quizIdx + 1 <= gameSvc.getPlayGameQuiz().size()) {
		    Quiz quiz = gameSvc.getPlayGameQuiz().get(quizIdx + 1); //다음 퀴즈 보냄
			String question = quiz.getQuestion(); //다음 퀴즈 초성
			char[] questionArray = question.toCharArray();
			
			model.addAttribute("gameInfo", gameSvc.getGameInfo());
			model.addAttribute("quiz", quiz);
			model.addAttribute("quizQuestion", questionArray);
			model.addAttribute("currentQue", quizIdx + 2);
			return "playGame::#playGameDiv";
		}
	    return "playGame";
	}
	
	//힌트 보기-포인트 차감
	@GetMapping("/getHint/{quizIdx}")
	public String getHint(Model model, HttpSession session, @PathVariable("quizIdx") int quizIdx) throws Exception{
		Member memberInfo = (Member) session.getAttribute("member");
		
		Quiz quiz = gameSvc.getPlayGameQuiz().get(quizIdx);
		
		memberSvc.updatePoint(memberInfo.getMemberId(), quiz.getHintPoint(), -1);
		Member newMemberInfo = memberSvc.findMember(memberInfo.getMemberId());
		newMemberInfo.setPasswd(null);
		session.setAttribute("member", newMemberInfo);
	
		model.addAttribute("quiz", quiz);
		
		return "playGame::#hint";
	}
	
	
	//게임 결과
	@PostMapping("/result/{gameNo}")
	public String getResult(Model model, HttpSession session, @PathVariable("gameNo") int gameNo, HttpServletRequest request) throws Exception{
		Member memberInfo = (Member) session.getAttribute("member");
		String answer = request.getParameter("answer");
		gameSvc.getUserAnswer().put(gameSvc.getPlayGameQuiz().size() - 1, answer);
		
		Map<String, Integer> resultMap = gameSvc.gameResult(memberInfo.getMemberId());
		int score = resultMap.get("score");
		int point = resultMap.get("point");
		int correctNum = resultMap.get("correctNum");
		String[] resultArray = gameSvc.getResultArray();
		List<Quiz> quizList = gameSvc.getPlayGameQuiz();
		Map<Integer, String> userAnswer = gameSvc.getUserAnswer();
		
		Member newMemberInfo = memberSvc.findMember(memberInfo.getMemberId());
		newMemberInfo.setPasswd(null);
		session.setAttribute("member", newMemberInfo);
		
		model.addAttribute("score", score);
		model.addAttribute("point", point);
		model.addAttribute("correctNum", correctNum);
		model.addAttribute("quizList", quizList);
		model.addAttribute("resultArray", resultArray);
		model.addAttribute("userAnswer", userAnswer);
		
		return "gameResult";
	}
}
