package com.project.consonant.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.consonant.domain.Member;

@Service
public class SpeedGameServiceImpl implements SpeedGameService {
	
	private String[] consonants = {"ㄱ", "ㄲ", "ㄴ", "ㄷ", "ㄸ", "ㄹ", "ㅁ", "ㅂ", "ㅃ", "ㅅ", "ㅆ", "ㅇ", "ㅈ", "ㅉ", "ㅊ", "ㅋ", "ㅌ", "ㅍ", "ㅎ" };
	
	@Autowired
	private MemberService memberSvc;

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
		
		// 제시어 초성과 입력한 단어가 일치하는지 확인
		if (randomConsonants.size() == 1) {
			for (String answer : answers) {
				int uniBase = answer.charAt(0) - 44032;
				char chosung = (char)(uniBase / 28 / 21);
				
				if (randomConsonants.get(0).equals(consonants[(int)chosung])) {
					resultMap.put(answer, "O");
				}
				else {
					resultMap.put(answer, "X");
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
						resultMap.put(answer, "O");
					}
					else {
						resultMap.put(answer, "X");
					}
				}
				else {
					resultMap.put(answer, "X");
					continue;
				}
			}
		}
		
		// 국어사전에 존재하는지 단어인지 확인
		for (String answer : answers) {
			if (resultMap.get(answer).equals("O")) {	// 초성과 일치하는 단어인 경우에만 수행
				String desc = searchByDictionary(answer);
				if (desc != null) {
					resultMap.put(answer, desc);
				}
				else {
					resultMap.put(answer, "X");
				}
			}
		}
		
		return resultMap;
	}

	@Transactional
	@Override
	public Member reflectGameResult(Member member, Map<String, String> resultMap) {
		// 포인트 및 추가 점수 계산
		List<String> valueList = new ArrayList<String>(resultMap.values());
		int correctCount = 0;
		for (String value : valueList) {
			if (value.equals("X")) {
				continue;
			}
			correctCount++;
		}
		
		// 포인트 갱신 서비스 요청
		memberSvc.updatePoint(member.getMemberId(), (int) Math.floor(correctCount / 10) * 10, 1);
		
		// 총 점수 및 순위 갱신 서비스 요청
		memberSvc.updateTotalScoreAndRankings(member.getMemberId(), correctCount);
		
		return memberSvc.findMember(member.getMemberId()); // 정보가 갱신된 회원 정보를 조회해 반환
	}
	
	private String searchByDictionary(String word) {
		String resultJson = "";
		
		try {
			String key = "7EDD056BA6205FEB26D5483121828FBD";
			URL url = new URL("https://stdict.korean.go.kr/api/search.do?key=" + key
					+ "&type_search=search&req_type=json&q=" + word);
			
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = "";
			while ((line = br.readLine()) != null) {
				resultJson += line.trim() + " ";
			}
            br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (resultJson.equals("")) return null;
		return resultJson;
	}

}
