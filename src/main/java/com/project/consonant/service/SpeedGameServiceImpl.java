package com.project.consonant.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class SpeedGameServiceImpl implements SpeedGameService {
	
	private String[] consonants = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };

	@Override
	public List<String> getRandomConsonants() {
		int randomSize = (int) ((Math.random() * (3 - 1)) + 1);
		List<String> randomConsonants = new ArrayList<String>();
		
		for (int i = 0; i < randomSize; i++) {
			int randomIdx = (int) ((Math.random() * consonants.length));
			randomConsonants.add(consonants[randomIdx]);
		}
		
		return randomConsonants;
	}
	
	@Override
	public Map<String, String> checkConsonants(List<String> randomConsonants, List<String> answers) {
		Map<String, String> resultMap = new HashMap<String, String>();
		
		// 제시어 초성과 일치하는 글자인지 확인
		if (randomConsonants.size() == 1) {
			for (String answer : answers) {
				int uniBase = answer.charAt(0) - 44032;
				char chosung = (char)(uniBase / 28 / 21);
				
				System.out.println(consonants[(int)chosung - 1]);
				if (randomConsonants.get(0).equals(consonants[(int)chosung])) {
					resultMap.put(answer, "T");
				}
				else {
					resultMap.put(answer, "F");
				}
			}
		} else if (randomConsonants.size() == 2) {
			for (String answer : answers) {
				int uniBase = answer.charAt(0) - 44032;
				char chosung = (char)(uniBase / 28 / 21);
				
				if (randomConsonants.get(0).equals(consonants[(int)chosung])) {
					uniBase = answer.charAt(1) - 44032;
					chosung = (char)(uniBase / 28 / 21);
					
					if (randomConsonants.get(1).equals(consonants[(int)chosung])) {
						resultMap.put(answer, "T");
					}
					else {
						resultMap.put(answer, "F");
					}
				}
				else {
					resultMap.put(answer, "F");
					continue;
				}
			}
		}
		
		// resultMap의 값이 T인 글자 중에서 국어사전에 존재하는지 확인(API사용)
		
		
		return resultMap;
	}

}
