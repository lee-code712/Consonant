package com.project.consonant.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.consonant.domain.Category;
import com.project.consonant.domain.Game;
import com.project.consonant.domain.Member;
import com.project.consonant.service.GameService;

@Controller
@RequestMapping("/game/list")
public class GameListController {
	
	@Autowired
	GameService gameSvc;
	
	/* 게임 리스트 화면 이동 */
	@GetMapping("")
	public String goGameList(Model model, HttpSession session) throws Exception {
		Member memberInfo = (Member) session.getAttribute("member");
		List<Category> categoryList = gameSvc.getAllCategory();

		model.addAttribute("memberInfo", memberInfo);
		model.addAttribute("categoryList", categoryList);

		return "gameList";
	}

	/* 키워드에 따라 게임 리스트를 json으로 반환 */
	@GetMapping("/{categoryId}")
	@ResponseBody
	public PagedListHolder<Game> GameList(@PathVariable("categoryId") String categoryId, HttpSession session) throws IOException {
		Member memberInfo = (Member) session.getAttribute("member");
		
		PagedListHolder<Game> gameList = null;
		if (categoryId.equals("all")) {
			gameList = new PagedListHolder<Game>(gameSvc.findAllGames(memberInfo.getMemberId()));
		} else {
			gameList = new PagedListHolder<Game>(gameSvc.findAllGamesByCategory(memberInfo.getMemberId(), categoryId));
		}
		
		if(gameList != null) {
			gameList.setPageSize(5);
			session.setAttribute("gameList", gameList);	// gameList 객체 세션에 저장
		}
		
		return gameList;
	}
	
	@GetMapping("/page/{page}")
	@ResponseBody
	public PagedListHolder<Game> GameListPage(@PathVariable("page") String page,
			HttpSession session) throws IOException {
		@SuppressWarnings("unchecked")
		PagedListHolder<Game> gameList = (PagedListHolder<Game>) session.getAttribute("gameList");
		
		if ("next".equals(page)) {
			gameList.nextPage();
		}
		else if ("previous".equals(page)) {
			gameList.previousPage();
		}

		return gameList;
	}

//	// 카테고리별 게임 리스트
//	@GetMapping("/category/gameList")
//	public String gameListByCategory(Model model, HttpSession session, String categoryId) throws Exception {
//		Member memberInfo = (Member) session.getAttribute("member");
//		System.out.println(memberInfo.getMemberId());
//		List<Category> categoryList = gameSvc.getAllCategory();
//		List<Game> gameList = gameSvc.findAllGamesByCategory(memberInfo.getMemberId(), categoryId);
//
//		model.addAttribute("memberInfo", memberInfo);
//		model.addAttribute("categoryList", categoryList);
//		model.addAttribute("gameList", gameList);
//
//		return "gameList::#gameListDiv";
//	}

}
