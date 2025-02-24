package com.emp.mgmt.controller;

import com.emp.mgmt.dto.EmployeeDTO;
import com.emp.mgmt.exception.ResourceNotFoundException;
import com.emp.mgmt.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private EmployeeService employeeService;

	@Test
	public void testAddEmployee() throws Exception {
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setName("Pratik Chavan");
		employeeDTO.setEmail("pratik@gmail.com");

		when(employeeService.addEmployee(any(EmployeeDTO.class))).thenReturn(employeeDTO);

		mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Pratik Chavan\",\"email\":\"pratik@gmail.com\"}")).andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Pratik Chavan"))
				.andExpect(jsonPath("$.email").value("pratik@gmail.com"));
	}


	@Test
	public void testGetEmployeeById_NotFound() throws Exception {
		when(employeeService.getEmployeeById(1L))
				.thenThrow(new ResourceNotFoundException("Employee not found with id: 1"));

		mockMvc.perform(get("/api/employees/1")).andExpect(status().isNotFound());
	}
}