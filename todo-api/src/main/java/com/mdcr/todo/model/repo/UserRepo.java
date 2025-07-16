package com.mdcr.todo.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdcr.todo.model.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	
	User findByEmail(String email);

	User findByEmailAndPassword(String email, String password);
}
