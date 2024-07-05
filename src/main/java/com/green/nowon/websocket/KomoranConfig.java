package com.green.nowon.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;

@Configuration
public class KomoranConfig {
	
	private String USER_DIC = "user.dic";

	@Bean
	Komoran komoran() {
		Komoran komoran = new Komoran(DEFAULT_MODEL.FULL);
		komoran.setUserDic(USER_DIC);
		
		return komoran;
	}
}
