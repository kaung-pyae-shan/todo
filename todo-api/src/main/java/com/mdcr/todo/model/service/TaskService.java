package com.mdcr.todo.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public List<Task> getAllTasksByUserId(int id) {
		return taskRepo.findAllByUserId(id);
	}
}
