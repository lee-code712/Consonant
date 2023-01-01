package com.project.consonant.service;

import java.util.List;
import java.util.Map;

public interface SpeedGameService {
	
	List<String> getRandomConsonants();
	
	Map<String, String> checkConsonants(List<String> randomConsonants, List<String> answers);

}
