package com.emp.mgmt.mapper;

import com.emp.mgmt.dto.EmployeeDTO;
import com.emp.mgmt.entity.Employee;

public class EmployeeMapper {

	public static EmployeeDTO toDTO(Employee employee) {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(employee.getId());
		dto.setName(employee.getName());
		dto.setEmail(employee.getEmail());
		dto.setDepartment(employee.getDepartment());
		dto.setSalary(employee.getSalary());
		dto.setJoiningDate(employee.getJoiningDate());
		return dto;
	}

	public static Employee toEntity(EmployeeDTO dto) {
		Employee employee = new Employee();
		employee.setName(dto.getName());
		employee.setEmail(dto.getEmail());
		employee.setDepartment(dto.getDepartment());
		employee.setSalary(dto.getSalary());
		employee.setJoiningDate(dto.getJoiningDate());
		return employee;
	}
}