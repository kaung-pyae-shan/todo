package com.mdcr.todo.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdcr.todo.model.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	Optional<User> findById(int id);
	
	Optional<User> findByEmail(String email);
}
