package com.emp.mgmt.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.emp.mgmt.dto.EmployeeDTO;
import com.emp.mgmt.exception.ResourceNotFoundException;

public interface EmployeeService {

	EmployeeDTO addEmployee(EmployeeDTO employeeDTO);

	List<EmployeeDTO> getAllEmployees();

	Page<EmployeeDTO> getAllEmployees(Pageable pageable);

	EmployeeDTO getEmployeeById(Long id) throws ResourceNotFoundException;

	EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

	void deleteEmployee(Long id);

	boolean existsById(Long id);

	boolean existsByEmail(String email);

}
