package com.mdcr.todo.model.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mdcr.todo.model.entity.Task;
import com.mdcr.todo.model.repo.TaskRepo;

@Service
public class TaskService {

	@Autowired
	private TaskRepo taskRepo;

	public Task addTask(Task task) {
		return taskRepo.save(task);
	}

	public Task findById(int id) {
		Optional<Task> task = taskRepo.findById(id);
		if (task.isPresent()) {
			return task.get();
		}
		return null;
	}

	public List<Task> getAllTasksByUserId(int userId) {
		return taskRepo.findAllByUserId(userId);
	}

	public List<Task> getPastTasks(int userId) {
		Sort sort = Sort.by(Sort.Order.desc("dueDate"))
				.and(Sort.by(Sort.Order.desc("dueTime")));
		return taskRepo.findByUserIdAndDueDateBefore(userId, LocalDate.now(), sort);
	}

	public List<Task> getTodayTasks(int userId) {
		Sort sort = Sort.by(Sort.Order.desc("status"))
				.and(Sort.by(Sort.Order.asc("dueTime")));
		return taskRepo.findByUserIdAndDueDate(userId, LocalDate.now(), sort);
	}

	public List<Task> getFutureTasks(int userId) {
		Sort sort = Sort.by(Sort.Order.asc("dueDate"))
				.and(Sort.by(Sort.Order.asc("dueTime")));
		return taskRepo.findByUserIdAndDueDateAfter(userId, LocalDate.now(), sort);
	}

	public void deleteById(int id) {
		taskRepo.deleteById(id);
	}
}
