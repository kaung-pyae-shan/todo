package com.mdcr.todo.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdcr.todo.model.entity.Task;
import com.mdcr.todo.model.service.TaskService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/task")
public class TaskApi {

	@Autowired
	private TaskService taskService;
	
	@GetMapping
	public ResponseEntity<List<Task>> getAllTasksByUserId(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		
		if (userId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<Task>());
	    }
		
		List<Task> tasks = taskService.getAllTasksByUserId(userId);
		if(tasks.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(taskService.getAllTasksByUserId(userId));
	}
}
