package com.project.consonant.domain;
import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class Game implements Serializable{
	private int gameNo;
	@NotBlank
	private String gameTitle;
	@NotBlank
	private String gameIntro;
	private int gameDifficulty;
	private int quizNumber;
	private int gameScore;
	private String categoryId;
	@NotBlank
	private String memberId;
	private int score; //로그인한 회원이 받았던 점수
	
	public Game() {}
	public Game(int gameNo, @NotBlank String gameTitle, @NotBlank String gameIntro, int gameDifficulty, int quizNumber,
			int gameScore, String categoryId) {
		super();
		this.gameNo = gameNo;
		this.gameTitle = gameTitle;
		this.gameIntro = gameIntro;
		this.gameDifficulty = gameDifficulty;
		this.quizNumber = quizNumber;
		this.gameScore = gameScore;
		this.categoryId = categoryId;
		this.memberId = "";
		this.score=0;
	}

	
	
}
