package com.project.consonant.domain;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class InputQuiz implements Serializable{
	@NotBlank
	private String question;
	@NotBlank
	private String answer;
	private int quizDifficulty;
	@NotBlank
	private String hint;
	private int hintPoint;
	public InputQuiz() {}
	public InputQuiz(String question, String answer, int quizDifficulty, String hint, int hintPoint) {
		super();
		this.question = question;
		this.answer = answer;
		this.quizDifficulty = quizDifficulty;
		this.hint = hint;
		this.hintPoint = hintPoint;
	}
	
}
