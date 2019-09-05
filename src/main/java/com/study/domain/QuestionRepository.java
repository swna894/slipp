package com.study.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
