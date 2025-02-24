package com.emp.mgmt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.emp.mgmt.dto.EmployeeDTO;
import com.emp.mgmt.entity.Employee;
import com.emp.mgmt.exception.ResourceNotFoundException;
import com.emp.mgmt.mapper.EmployeeMapper;
import com.emp.mgmt.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
		logger.info("Adding employee: {}", employeeDTO);
		Employee employee = EmployeeMapper.toEntity(employeeDTO);
		Employee savedEmployee = employeeRepository.save(employee);
		return EmployeeMapper.toDTO(savedEmployee);
	}

	@Override
	public List<EmployeeDTO> getAllEmployees() {
		logger.info("Fetching all employees");
		return employeeRepository.findAll().stream().map(EmployeeMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
		logger.info("Fetching employees with pagination: {}", pageable);
		return employeeRepository.findAll(pageable).map(EmployeeMapper::toDTO);
	}

	@Override
	public EmployeeDTO getEmployeeById(Long id) {
		logger.info("Fetching employee by ID: {}", id);
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
		return EmployeeMapper.toDTO(employee);
	}

	@Override
	public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
		logger.info("Updating employee with ID: {}", id);
		Employee existingEmployee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

		existingEmployee.setName(employeeDTO.getName());
		existingEmployee.setEmail(employeeDTO.getEmail());
		existingEmployee.setDepartment(employeeDTO.getDepartment());
		existingEmployee.setSalary(employeeDTO.getSalary());
		existingEmployee.setJoiningDate(employeeDTO.getJoiningDate());

		Employee updatedEmployee = employeeRepository.save(existingEmployee);
		return EmployeeMapper.toDTO(updatedEmployee);
	}

	@Override
	public void deleteEmployee(Long id) {
		logger.info("Deleting employee with ID: {}", id);
		if (!employeeRepository.existsById(id)) {
			throw new ResourceNotFoundException("Employee not found with ID: " + id);
		}
		employeeRepository.deleteById(id);
	}

	@Override
	public boolean existsById(Long id) {
		return employeeRepository.existsById(id);
	}

	@Override
	public boolean existsByEmail(String email) {
		return employeeRepository.existsByEmail(email);
	}

}
