package com.project.consonant.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class History {
	private Date playDate;
	private int correctNumber;
	private int score;
	private int gameNo;
	private String memberId;
	
	public History() {}
	public History(int correctNumber, int score, int gameNo, String memberId) {
		super();
		this.correctNumber = correctNumber;
		this.score = score;
		this.gameNo = gameNo;
		this.memberId = memberId;
	}
	public History(Date playDate, int correctNumber, int score, int gameNo, String memberId) {
		super();
		this.playDate = playDate;
		this.correctNumber = correctNumber;
		this.score = score;
		this.gameNo = gameNo;
		this.memberId = memberId;
	}

	
	
}
