package com.green.nowon.websocket;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.shineware.nlp.komoran.core.Komoran;
import kr.co.shineware.nlp.komoran.model.KomoranResult;
import kr.co.shineware.nlp.komoran.model.Token;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KomoranService {

	private final Komoran komoran;

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
