package com.mdcr.todo.model.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mdcr.todo.model.entity.Task;

public interface TaskRepo extends JpaRepository<Task, Integer> {

	List<Task> findAllByUserId(int id);
	
	// 1. Tasks with due date in the past (before today)
    List<Task> findByUserIdAndDueDateBefore(int userId, LocalDate today, Sort sort);

    // 2. Tasks due today
    List<Task> findByUserIdAndDueDate(int userId, LocalDate today, Sort sort);

    // 3. Tasks with due date in the future (after today)
    List<Task> findByUserIdAndDueDateAfter(int userId, LocalDate today, Sort sort);
}
