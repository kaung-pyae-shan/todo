package com.mdcr.todo.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdcr.todo.exception.UserAlreadyExistsException;
import com.mdcr.todo.model.dto.LoginRequest;
import com.mdcr.todo.model.dto.UserDto;
import com.mdcr.todo.model.entity.User;
import com.mdcr.todo.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class UserApi {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody User user) {
		try {
			User registeredUser = userService.register(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
		} catch (UserAlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
		String email = loginRequest.email();
		String password = loginRequest.password();

		UserDto user = userService.login(email, password);
		if (user != null) {
			session.setAttribute("userId", user.id());
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session) {
	    session.invalidate();
	    return ResponseEntity.ok("Logged out successfully");
	}
	
	@GetMapping("/me")
	public ResponseEntity<UserDto> getLoggedInUser(HttpSession session) {
	    Integer userId = (Integer) session.getAttribute("userId");
	    if (userId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }

	    UserDto user = userService.getUserDtoById(userId);
	    
	    if(user != null) {
	    	return ResponseEntity.ok(user);
	    } else {	    	
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	    }
	}
}
