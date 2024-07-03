package com.green.nowon.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller // /bot
public class BotController {
	
	
	@MessageMapping("/question") // '/bot/question'
	public void bot(Question dto) {
		System.out.println(">>>:"+dto);
	}
}
