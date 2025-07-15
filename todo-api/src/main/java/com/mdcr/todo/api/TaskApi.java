package com.mdcr.todo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Task> getAllTasksByUserId(@RequestParam(required = false) int id) {
		return taskService.getAllTasksByUserId(id);
	}
}
