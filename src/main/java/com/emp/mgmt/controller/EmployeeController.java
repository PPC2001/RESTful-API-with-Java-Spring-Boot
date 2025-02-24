package com.emp.mgmt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import com.emp.mgmt.dto.EmployeeDTO;
import com.emp.mgmt.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Controller", description = "APIs for managing employees")
public class EmployeeController {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	@Operation(summary = "Add a new employee", description = "Create a new employee in the system")
	public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
		try {
			logger.info("Received request to add a new employee: {}", employeeDTO);
			if (employeeService.existsByEmail(employeeDTO.getEmail())) {
				logger.warn("Email already exists: {}", employeeDTO.getEmail());
				return new ResponseEntity<>("Email already exists: " + employeeDTO.getEmail(), HttpStatus.BAD_REQUEST);
			}
			EmployeeDTO savedEmployee = employeeService.addEmployee(employeeDTO);
			logger.info("Employee added successfully: {}", savedEmployee);
			return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
		} catch (Exception ex) {
			logger.error("EmployeeController :: addEmployee :: Error while adding employee: {}", ex.getMessage(), ex);
			return new ResponseEntity<>("An error occurred while adding the employee: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping
    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees")
	public ResponseEntity<?> getAllEmployees() {
		try {
			logger.info("Received request to fetch all employees");
			List<EmployeeDTO> employees = employeeService.getAllEmployees();
			logger.info("Fetched {} employees", employees.size());
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("EmployeeController :: getAllEmployees :: Error while fetching all employees: {}",
					ex.getMessage(), ex);
			return new ResponseEntity<>("An error occurred while fetching employees: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/paged")
	public ResponseEntity<?> getAllEmployeesPaged(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
		try {
			logger.info("Received request to fetch employees with pagination - Page: {}, Size: {}, SortBy: {}", page,
					size, sortBy);
			Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
			Page<EmployeeDTO> employees = employeeService.getAllEmployees(pageable);
			logger.info("Fetched {} employees on page {}", employees.getNumberOfElements(), page);
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error(
					"EmployeeController :: getAllEmployeesPaged :: Error while fetching employees with pagination: {}",
					ex.getMessage(), ex);
			return new ResponseEntity<>("An error occurred while fetching employees: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Retrieve an employee by their ID")
	public ResponseEntity<?> getEmployeeById(@PathVariable("id") Long id) {
		try {
			logger.info("Received request to fetch employee by ID: {}", id);
			if (!employeeService.existsById(id)) {
				logger.warn("Employee not found with ID: {}", id);
				return new ResponseEntity<>("No employee found with ID: " + id, HttpStatus.NOT_FOUND);
			}
			EmployeeDTO employee = employeeService.getEmployeeById(id);
			logger.info("Fetched employee: {}", employee);
			return new ResponseEntity<>(employee, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("EmployeeController :: getEmployeeById :: Error while fetching employee by ID {}: {}", id,
					ex.getMessage(), ex);
			return new ResponseEntity<>("An error occurred while fetching the employee: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/{id}")
    @Operation(summary = "Update employee", description = "Update an existing employee by their ID")
	public ResponseEntity<?> updateEmployee(@PathVariable("id") Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
		try {
			logger.info("Received request to update employee with ID: {}", id);
			if (!employeeService.existsById(id)) {
				logger.warn("Employee not found with ID: {}", id);
				return new ResponseEntity<>("No employee found with ID: " + id, HttpStatus.NOT_FOUND);
			}
			EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
			logger.info("Employee updated successfully: {}", updatedEmployee);
			return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("EmployeeController :: updateEmployee :: Error while updating employee with ID {}: {}", id,
					ex.getMessage(), ex);
			return new ResponseEntity<>("An error occurred while updating the employee: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/{id}")
    @Operation(summary = "Delete employee", description = "Delete an employee by their ID")
	public ResponseEntity<?> deleteEmployee(@PathVariable("id") Long id) {
		try {
			logger.info("Received request to delete employee with ID: {}", id);
			if (!employeeService.existsById(id)) {
				logger.warn("Employee not found with ID: {}", id);
				return new ResponseEntity<>("No employee found with ID: " + id, HttpStatus.NOT_FOUND);
			}

			employeeService.deleteEmployee(id);
			logger.info("Employee deleted successfully with ID: {}", id);
			return new ResponseEntity<>("Employee deleted successfully with ID: " + id, HttpStatus.OK);
		} catch (Exception ex) {
			logger.error("EmployeeController :: deleteEmployee :: Error while deleting employee with ID {}: {}", id,
					ex.getMessage(), ex);
			return new ResponseEntity<>("An error occurred while deleting the employee: " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}