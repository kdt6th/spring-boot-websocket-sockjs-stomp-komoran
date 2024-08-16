package com.green.nowon.websocket;

import java.util.Set;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.green.nowon.websocket.entity.AnswerEntity;
import com.green.nowon.websocket.entity.IntentionEntity;
import com.green.nowon.websocket.entity.IntentionEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BotService {
	
	private final SimpMessagingTemplate messagingTemplate;
	//private final RabbitTemplate rabbitTemplate;
	private final KomoranService komoranService;
	
	private final IntentionEntityRepository intentionRepository;
	

	public void questionProcess(Question dto) {
		
		//들어온 메세지가 어떤 의도인지 파악 ---> 머신러닝(형태소 분석기를 통해 분석)
		Set<String> result=komoranService.analyzeTokenAndGetNouns(dto.getContent());
		
		//명사->어떤 의도인지 파악
		IntentionEntity intention=null;
		for(String keyword : result){
			intention=intentionRepository.findByKeyword(keyword).orElse(null);
		}
		AnswerEntity answer=AnswerEntity.builder()
				.content("죄송합니다. 제가 답변 할 수 없는 질문이네요!")
				.build();
		if(intention!=null) {
			answer=intention.getAnswer();
		}
		messagingTemplate.convertAndSend("/topic/bot/"+dto.getKey(), answer.getContent());
	}

}
