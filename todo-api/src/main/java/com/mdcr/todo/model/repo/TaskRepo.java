package com.mdcr.todo.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdcr.todo.model.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Integer> {

	List<Task> findAllByUserId(int id);
}
