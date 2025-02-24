package com.emp.mgmt.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EmployeeDTO {

	private Long id;

	@NotBlank(message = "Name is mandatory")
	private String name;

	@Email(message = "Email should be valid")
	private String email;

	private String department;

	@Positive(message = "Salary must be a positive number")
	private Double salary;

	private LocalDate joiningDate;
}