package com.project.consonant.domain;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GameInfoVO {
	private int gameNo;
	@NotBlank
	private String gameTitle;
	@NotBlank
	private String gameIntro;
	private int gameDifficulty;
	private int quizNumber;
	private int gameScore;
	private String categoryId;
	private List<Quiz> quizList;
	
	public GameInfoVO() {}
}
