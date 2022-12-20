package com.project.consonant.domain;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class Quiz implements Serializable{
	private int quizNo;
	private String question;
	private String answer;
	private int quizDifficulty;
	private String hint;
	private int hintPoint;
	private int gameNo;
	
	public Quiz() {};
	public Quiz(String question, String answer, int quizDifficulty, String hint, int hintPoint, int gameNo) {
		super();
		this.question = question;
		this.answer = answer;
		this.quizDifficulty = quizDifficulty;
		this.hint = hint;
		this.hintPoint = hintPoint;
		this.gameNo = gameNo;
	}
	
	
}
