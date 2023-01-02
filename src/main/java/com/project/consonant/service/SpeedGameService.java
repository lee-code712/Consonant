package com.project.consonant.service;

import java.util.List;
import java.util.Map;

import com.project.consonant.domain.Member;

public interface SpeedGameService {
	
	List<String> getRandomConsonants();
	
	Map<String, String> checkConsonants(List<String> randomConsonants, List<String> answers);

	Member reflectGameResult(Member member, Map<String, String> resultMap);

}
