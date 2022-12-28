package com.project.consonant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SpeedGameServiceImpl implements SpeedGameService {

	@Override
	public List<String> getRandomConsonants() {
		String[] consonants = {"ㄱ", "ㄴ", "ㄷ", "ㄹ", "ㅁ", "ㅂ", "ㅅ", "ㅇ", "ㅈ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ"};
		
		int randomSize = (int) ((Math.random() * (3 - 1)) + 1);
		List<String> randomConsonants = new ArrayList<String>();
		
		for (int i = 0; i < randomSize; i++) {
			int randomIdx = (int) ((Math.random() * consonants.length));
			randomConsonants.add(consonants[randomIdx]);
		}
		
		return randomConsonants;
	}

}
