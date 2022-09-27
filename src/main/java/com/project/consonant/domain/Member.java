package com.project.consonant.domain;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class Member implements Serializable {

	@NotBlank
	private String memberId;
	@NotBlank
	private String passwd;
	private int totalScore;
	private int rank;
	private int point;

}
