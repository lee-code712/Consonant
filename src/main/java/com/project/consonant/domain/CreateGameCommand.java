package com.project.consonant.domain;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class CreateGameCommand {
	private String memberId;
	@NotBlank
	private String gameTitle;
	@NotBlank
	private String gameIntro;
	private int gameDifficulty;
	private String category;
	
	
}
