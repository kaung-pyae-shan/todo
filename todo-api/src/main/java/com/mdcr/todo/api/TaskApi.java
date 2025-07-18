package com.mdcr.todo.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdcr.todo.model.dto.TaskDto;
import com.mdcr.todo.model.entity.Task;
import com.mdcr.todo.model.entity.Task.Status;
import com.mdcr.todo.model.entity.User;
import com.mdcr.todo.model.service.TaskService;
import com.mdcr.todo.model.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/task")
public class TaskApi {

	@Autowired
	private TaskService taskService;
	@Autowired
	UserService userService;

	@GetMapping
	public ResponseEntity<List<Task>> getAllTasksByUserId(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");

		if (userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<Task>());
		}

		List<Task> tasks = taskService.getAllTasksByUserId(userId);
		if (tasks.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(taskService.getAllTasksByUserId(userId));
	}

	@GetMapping("/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable int id) {
		Task task = taskService.findById(id);
		if (task != null) {
			return ResponseEntity.ok(task);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/past")
	public ResponseEntity<List<Task>> getPastTasksByUserId(HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");

		if (userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<Task>());
		}

		List<Task> tasks = taskService.getAllTasksByUserId(userId);
		if (tasks.isEmpty()) {
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
		if (tasks.isEmpty()) {
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
		if (tasks.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(taskService.getFutureTasks(userId));
	}

	@PostMapping("/add")
	public ResponseEntity<Task> createTask(@RequestBody TaskDto taskDto, HttpSession session) {
		Integer userId = (Integer) session.getAttribute("userId");
		if (userId == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		User user = userService.getUserById(userId).orElse(null);
		if (user == null)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

		Task task = new Task();
		task.setName(taskDto.name());
		task.setDescription(taskDto.description());
		task.setDueDate(taskDto.dueDate());
		task.setDueTime(taskDto.dueTime());
		task.setStatus(Status.PENDING);
		task.setUser(user);
		
		Task savedTask = taskService.addTask(task);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
	}

	@PutMapping("/update/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody TaskDto updatedTask, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Task existingTask = taskService.findById(id);
        if (existingTask == null || existingTask.getUser().getId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        existingTask.setName(updatedTask.name());
        existingTask.setDescription(updatedTask.description());
        existingTask.setDueDate(updatedTask.dueDate());
        existingTask.setDueTime(updatedTask.dueTime());

        Task savedTask = taskService.addTask(existingTask);
        return ResponseEntity.ok(savedTask);
    }
	
	@PutMapping("/status/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Map<String, String> payload, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Task task = taskService.findById(id);
        if (task == null || task.getUser().getId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        task.setStatus(Status.valueOf(payload.get("status")));

        Task savedTask = taskService.addTask(task);
        return ResponseEntity.ok(savedTask);
    }
	
	@DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable int id, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Task task = taskService.findById(id);
        if (task == null || task.getUser().getId() != userId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
