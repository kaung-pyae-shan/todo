package com.mdcr.todo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdcr.todo.exception.UserAlreadyExistsException;
import com.mdcr.todo.model.entity.User;
import com.mdcr.todo.model.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	public User register(User user) {
		if (usernameExists(user.getEmail())) {
			throw new UserAlreadyExistsException("User already exists");
		}
		return userRepo.save(user);
	}

	public boolean usernameExists(String username) {
		return userRepo.findByEmail(username) != null;
	}

	public User login(String email, String password) {
		return userRepo.findByEmailAndPassword(email, password);
	}
}
