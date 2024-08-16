package com.green.nowon.websocket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answer")
public class AnswerEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	private String intent;
	private String content;
	private Sevice service;

}

enum Sevice {
	BOOK,USER
}
