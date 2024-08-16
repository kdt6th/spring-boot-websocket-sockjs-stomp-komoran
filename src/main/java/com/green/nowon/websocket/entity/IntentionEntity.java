package com.green.nowon.websocket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "intention", indexes = {
	    @Index(name = "idx_upper_intention", columnList = "upper_no"),
	    @Index(name = "idx_answer_intention", columnList = "answer_no")
})
@Entity
public class IntentionEntity {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long no;
	
	private String keyword;//명사
	
	private String behavior;//동사
	
	private String number;//sn
	
	@ManyToOne
	@JoinColumn(name="answer_no")
	private AnswerEntity answer;
	
	@ManyToOne
	@JoinColumn(name="upper_no")
	IntentionEntity upper;

}

