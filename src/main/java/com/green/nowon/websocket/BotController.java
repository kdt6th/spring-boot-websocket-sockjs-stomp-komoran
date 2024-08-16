package com.green.nowon.websocket;

import java.text.MessageFormat;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import kr.co.shineware.nlp.komoran.core.Komoran;
import lombok.RequiredArgsConstructor;

@Controller // /bot
@RequiredArgsConstructor
public class BotController {
	private final BotService botService;
	//내부적으로 STOMP의 프로토콜을 사용하여 메세지를 전송
	//@SendTo 어노테이션을 처리하는 구현 객체
	private final SimpMessagingTemplate messagingTemplate;
	
	
	//질문창에 메세지를 입력한경우
	@MessageMapping("/question") // '/bot/question'
	public void bot(Question dto) {
		System.out.println(">>>:"+dto);
		botService.questionProcess(dto);
		
	}
	
	//최초인사말
	@MessageMapping("/hello") // '/bot/hello'
	public void hello(Question dto) {
		System.out.println(">>>:"+dto);
		String key = dto.getKey();
		String pattern = "{0}님 안녕하세요!";
		
		messagingTemplate.convertAndSend("/topic/bot/"+key, 
				MessageFormat.format(pattern, dto.getName())
				);
	}
}
