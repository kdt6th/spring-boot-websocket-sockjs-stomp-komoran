package com.green.nowon.websocket;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KomoranService {
	
	private final Komoran komoran;

	public Set<String> analyzeTokenAndGetNouns(String question){
		nlpProcess(question);
		//질문 내용을 분석
		KomoranResult analyzeResult = komoran.analyze(question);
		//분석된 결과중 명사만 수집->의도분석을 위해서 심플하게 명사만 사용해볼께요
		Set<String> nouns=analyzeResult.getNouns().stream().collect(Collectors.toSet());
		for(String noun:nouns) {
			System.out.println("명사:"+noun);
		}
		List<String> snList=analyzeResult.getMorphesByTags("SN");
		for(String sn:snList) {
			System.out.println("sn:"+sn);
		}
		return nouns;
		
	}
	
	public String analyzeTokenAndGetNumber(String question){
		nlpProcess(question);
		//질문 내용을 분석
		KomoranResult analyzeResult = komoran.analyze(question);
		//분석된 결과중 명사만 수집->의도분석을 위해서 심플하게 명사만 사용해볼께요
		
		//List<String> snList=analyzeResult.getMorphesByTags("SN");
		String number="";
		for(String sn:analyzeResult.getMorphesByTags("SN")) {
			number+=sn;
		}
		return number;
		
	}
	
	//komoran 예제
	public void nlpProcess(String content) {
		String strToAnalyze = content;
		
        KomoranResult analyzeResultList = komoran.analyze(strToAnalyze);

        System.out.println(analyzeResultList.getPlainText());

        List<Token> tokenList = analyzeResultList.getTokenList();
        for (Token token : tokenList) {
            System.out.format("(%2d, %2d) %s/%s\n", token.getBeginIndex(), token.getEndIndex(), token.getMorph(), token.getPos());
        }
		
	}
	
}
