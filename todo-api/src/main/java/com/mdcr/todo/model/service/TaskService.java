package com.mdcr.todo.model.service;

import java.time.LocalDate;
import java.util.List;

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
	
	public List<Task> getAllTasksByUserId(int userId) {
		return taskRepo.findAllByUserId(userId);
	}
	
	public List<Task> getPastTasks(int userId) {
		return taskRepo.findByUserIdAndDueDateBefore(userId, LocalDate.now(), Sort.by("dueTime").descending());
	}
	
	public List<Task> getTodayTasks(int userId) {
		return taskRepo.findByUserIdAndDueDate(userId, LocalDate.now(), Sort.by("dueTime").descending());
	}
	
	public List<Task> getFutureTasks(int userId) {
		return taskRepo.findByUserIdAndDueDateAfter(userId, LocalDate.now(), Sort.by("dueTime").descending());
	}
}
