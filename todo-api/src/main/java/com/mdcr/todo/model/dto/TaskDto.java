package com.mdcr.todo.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TaskDto(
		String name,
		String description,
		LocalDate dueDate,
		LocalTime dueTime) {

}
