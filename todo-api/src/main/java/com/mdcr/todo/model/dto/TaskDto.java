package com.mdcr.todo.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.mdcr.todo.model.entity.Task.Status;

public record TaskDto(
		int id,
		String name,
		String description,
		LocalDate dueDate,
		LocalTime dueTime,
		Status status) {

}
