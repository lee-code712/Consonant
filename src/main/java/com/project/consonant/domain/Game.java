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
	private String category;
	@NotBlank
	private String memberId;

}
