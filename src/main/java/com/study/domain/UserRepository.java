package com.study.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserId(String userId);

}
