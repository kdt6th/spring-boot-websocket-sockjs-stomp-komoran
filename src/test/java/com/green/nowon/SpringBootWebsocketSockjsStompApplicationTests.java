package com.green.nowon;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.green.nowon.websocket.entity.AnswerEntity;
import com.green.nowon.websocket.entity.AnswerEntityRepogitory;
import com.green.nowon.websocket.entity.IntentionEntity;
import com.green.nowon.websocket.entity.IntentionEntityRepository;

@SpringBootTest
class SpringBootWebsocketSockjsStompApplicationTests {

	@Autowired
	private AnswerEntityRepogitory answerEntityRepogitory;
	
	@Autowired
	private IntentionEntityRepository intentionEntityRepository;
	
	//@Test
	void 챗봇인사말세팅() {
		
		answerEntityRepogitory.save(AnswerEntity.builder()
				.intent("인사말")
				.content("정말로 반갑습니다.!")
				.build());
	}
	
	//@Test
	void 인사말에대한_데이터사전등록() {
		List<String> keywords=Arrays.asList("하이","안녕하세요","안녕","밥 먹었니","반가워","hello");
		
		AnswerEntity answer= answerEntityRepogitory.findByIntent("인사말").orElseThrow();
		
		keywords.forEach(keyword->{
			
			// 키워드가 이미 존재하는지 확인
		    if (!intentionEntityRepository.existsByKeyword(keyword)) {
		        intentionEntityRepository.save(IntentionEntity.builder()
		            .keyword(keyword)
		            .answer(answer)
		            .build());
		    }
		});
		
	}

}
