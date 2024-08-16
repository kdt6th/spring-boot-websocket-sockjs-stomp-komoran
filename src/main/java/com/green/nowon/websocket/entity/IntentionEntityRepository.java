package com.green.nowon.websocket.entity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IntentionEntityRepository extends JpaRepository<IntentionEntity, Long>{
	
	//키워드가 존재하는지 검색
	Optional<IntentionEntity> findByKeyword(String keyword);

	boolean existsByKeyword(String keyword);
}
