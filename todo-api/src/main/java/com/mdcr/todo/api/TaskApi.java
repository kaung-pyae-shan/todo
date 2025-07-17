package com.mdcr.todo.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdcr.todo.model.entity.Task;
import com.mdcr.todo.model.entity.User;
import com.mdcr.todo.model.service.TaskService;
import com.mdcr.todo.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/task")
public class TaskApi {

	@Autowired
	private TaskService taskService;
	@Autowired UserService userService;
	
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
	
	@GetMapping("/past")
	public ResponseEntity<List<Task>> getPastTasksByUserId(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		
		if (userId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<Task>());
	    }
		
		List<Task> tasks = taskService.getAllTasksByUserId(userId);
		if(tasks.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(taskService.getPastTasks(userId));
	}
	
	@GetMapping("/today")
	public ResponseEntity<List<Task>> getTodayTasksByUserId(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		
		if (userId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<Task>());
	    }
		
		List<Task> tasks = taskService.getAllTasksByUserId(userId);
		if(tasks.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(taskService.getTodayTasks(userId));
	}
	
	@GetMapping("/future")
	public ResponseEntity<List<Task>> getFutureTasksByUserId(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		
		if (userId == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<Task>());
	    }
		
		List<Task> tasks = taskService.getAllTasksByUserId(userId);
		if(tasks.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(taskService.getFutureTasks(userId));
	}
	
	@PostMapping("/add")
    public ResponseEntity<Task> createTask(@RequestBody Task task, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        User user = userService.getUserById(userId).orElse(null);
        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        task.setUser(user);
//        task.setStatus(Task.Status.PENDING); // Default status
        Task savedTask = taskService.addTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }
	
//	@PutMapping("/{id}")
//    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task updatedTask, HttpSession session) {
//        Integer userId = (Integer) session.getAttribute("userId");
//        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//
//        Task existingTask = taskService.findById(id);
//        if (existingTask == null || existingTask.getUser().getId() != userId) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        existingTask.setName(updatedTask.getName());
//        existingTask.setDescription(updatedTask.getDescription());
//        existingTask.setDueDate(updatedTask.getDueDate());
//        existingTask.setDueTime(updatedTask.getDueTime());
//        existingTask.setStatus(updatedTask.getStatus());
//
//        Task savedTask = taskService.addTask(existingTask);
//        return ResponseEntity.ok(savedTask);
//    }
//	
//	@DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteTask(@PathVariable int id, HttpSession session) {
//        Integer userId = (Integer) session.getAttribute("userId");
//        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//
//        Task task = taskService.findById(id);
//        if (task == null || task.getUser().getId() != userId) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        taskService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
}
