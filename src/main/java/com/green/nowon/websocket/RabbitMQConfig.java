package com.green.nowon.websocket;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
	//바인딩(Binding)
	//exchange-토픽 <- 큐(Queue)
	////메세지Q를 사용하려면 메세지컨버터가 필요 (직렬화, 암호화했다가 복호화하는 것)
	//메시지컨버터(변환기)
	
	//AMQP 메시지의 직렬화, 역직렬화를 통해 JSON 형식으로 처리하기 위해
	//직렬화 : 자바의 객체를 JSON 형식으로 변환하여 메시지 브로커에 보낼 수 있게 함.
	//역직렬화 : 메시지 브로커로부터 수신한 JSON 형식의 메시지를 자바 객체로 변환
	@Bean
	MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
		//클라이언트와 서버간의 메시지 교환 기술. 
	}
	
}
