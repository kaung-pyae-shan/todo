package com.mdcr.todo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mdcr.todo.model.entity.Task;
import com.mdcr.todo.model.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskApi {

	@Autowired
	private TaskService taskService;
	
	@GetMapping
	public ResponseEntity<List<Task>> getAllTasksByUserId(@RequestParam(required = false) int id) {
		List<Task> tasks = taskService.getAllTasksByUserId(id);
		if(tasks.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(taskService.getAllTasksByUserId(id));
	}
}
