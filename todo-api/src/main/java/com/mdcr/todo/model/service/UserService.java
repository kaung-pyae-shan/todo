package com.mdcr.todo.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdcr.todo.exception.UserAlreadyExistsException;
import com.mdcr.todo.model.dto.UserDto;
import com.mdcr.todo.model.entity.User;
import com.mdcr.todo.model.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	public UserDto getUserDtoById(int id) {
		Optional<User> user = userRepo.findById(id);
		if (user.isPresent()) {
			return new UserDto(user.get().getId(), user.get().getName(), user.get().getEmail());
		}
		return null;
	}
	
	public Optional<User> getUserById(int id) {
		return userRepo.findById(id);
	}

	public User register(User user) {
		if (usernameExists(user.getEmail())) {
			throw new UserAlreadyExistsException("User already exists");
		}
		return userRepo.save(user);
	}

	public boolean usernameExists(String username) {
		return userRepo.findByEmail(username) != null;
	}

	public UserDto login(String email, String password) {
		User user = userRepo.findByEmailAndPassword(email, password);
		if(user == null) {
			return null;
		}
		return new UserDto(user.getId(), user.getName(), user.getEmail());
	}
}
