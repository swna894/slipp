package com.study.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
