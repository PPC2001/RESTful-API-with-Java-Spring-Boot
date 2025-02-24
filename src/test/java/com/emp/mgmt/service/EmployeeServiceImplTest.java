package com.emp.mgmt.service;

import com.emp.mgmt.dto.EmployeeDTO;
import com.emp.mgmt.entity.Employee;
import com.emp.mgmt.exception.ResourceNotFoundException;
import com.emp.mgmt.mapper.EmployeeMapper;
import com.emp.mgmt.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Test
	public void testAddEmployee() {
		// Arrange
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setName("Pratik Chavan");
		employeeDTO.setEmail("pratik@gmail.com.com");

		Employee employee = EmployeeMapper.toEntity(employeeDTO);
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

		// Act
		EmployeeDTO savedEmployee = employeeService.addEmployee(employeeDTO);

		// Assert
		assertNotNull(savedEmployee);
		assertEquals("Pratik Chavan", savedEmployee.getName());
		assertEquals("pratik@gmail.com.com", savedEmployee.getEmail());
		verify(employeeRepository, times(1)).save(any(Employee.class));
	}

	@Test
	public void testGetEmployeeById() {
		// Arrange
		Long id = 1L;
		Employee employee = new Employee();
		employee.setId(id);
		employee.setName("Pratik Chavan");

		when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

		// Act
		EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);

		// Assert
		assertNotNull(employeeDTO);
		assertEquals("Pratik Chavan", employeeDTO.getName());
		verify(employeeRepository, times(1)).findById(id);
	}

	@Test
	public void testGetEmployeeById_NotFound() {
		// Arrange
		Long id = 1L;
		when(employeeRepository.findById(id)).thenReturn(Optional.empty());

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(id));
		verify(employeeRepository, times(1)).findById(id);
	}

	@Test
	public void testGetAllEmployees() {
		// Arrange
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Pratik Chavan");

		when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

		// Act
		List<EmployeeDTO> employees = employeeService.getAllEmployees();

		// Assert
		assertNotNull(employees);
		assertEquals(1, employees.size());
		assertEquals("Pratik Chavan", employees.get(0).getName());
		verify(employeeRepository, times(1)).findAll();
	}

	@Test
	public void testUpdateEmployee() {
		// Arrange
		Long id = 1L;
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setName("Pratik Chavan Updated");
		employeeDTO.setEmail("john.doe.updated@example.com");

		Employee existingEmployee = new Employee();
		existingEmployee.setId(id);
		existingEmployee.setName("Pratik Chavan");

		when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
		when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

		// Act
		EmployeeDTO updatedEmployee = employeeService.updateEmployee(id, employeeDTO);

		// Assert
		assertNotNull(updatedEmployee);
		assertEquals("Pratik Chavan Updated", updatedEmployee.getName());
		assertEquals("john.doe.updated@example.com", updatedEmployee.getEmail());
		verify(employeeRepository, times(1)).findById(id);
		verify(employeeRepository, times(1)).save(any(Employee.class));
	}

	@Test
	public void testDeleteEmployee() {
		// Arrange
		Long id = 1L;
		when(employeeRepository.existsById(id)).thenReturn(true);

		// Act
		employeeService.deleteEmployee(id);

		// Assert
		verify(employeeRepository, times(1)).deleteById(id);
	}

	@Test
	public void testDeleteEmployee_NotFound() {
		// Arrange
		Long id = 99L;
		when(employeeRepository.existsById(id)).thenReturn(false);

		// Act & Assert
		assertThrows(ResourceNotFoundException.class, () -> employeeService.deleteEmployee(id));
		verify(employeeRepository, times(1)).existsById(id);
	}
}
