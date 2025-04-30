package com.webapp.employee.EmployeeWebApp;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")

public class Controller {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@PostMapping("/saveEmployee")
	public ResponseEntity<ApiResponse<Employee>> saveEmployee(@RequestBody Employee employee){
		employeeRepository.save(employee);
		
		ApiResponse<Employee> response = new ApiResponse<>();
		response.setStatus("Success");
		response.setMessage("Data sent Successfully");
		response.setData(employee);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getAllEmployee")
	public List<Employee> getAllEmployee() {
		List<Employee> employee = employeeRepository.findAll();
		
		return employee;
	}
	
	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<ApiResponse<List<Employee>>> deleteEmployee(@PathVariable Long id) {
		if(!employeeRepository.existsById(id)){
			return ResponseEntity.notFound().build();
		}
		employeeRepository.deleteById(id);
		
		List<Employee> newList = employeeRepository.findAll();
		
		ApiResponse<List<Employee>> response = new ApiResponse<>();
		response.setStatus("Success");
		response.setMessage("Deleted Employee successfully");
		response.setData(newList);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PutMapping("/updateEmployee/{id}")
	public ResponseEntity<ApiResponse<List<Employee>>> updateEmployee(@PathVariable long id, @RequestBody Employee updatedEmployee){
		Optional<Employee> optionalEmp = employeeRepository.findById(id);
		
		if(!optionalEmp.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		Employee existingEmp = optionalEmp.get();
		existingEmp.setFirstName(updatedEmployee.getFirstName());
		existingEmp.setLastName(updatedEmployee.getLastName());
		existingEmp.setGender(updatedEmployee.getGender());
		existingEmp.setAge(updatedEmployee.getAge());
		existingEmp.setDOB(updatedEmployee.getDOB());
		
		employeeRepository.save(existingEmp);
		
		List<Employee> newList = employeeRepository.findAll();
		ApiResponse<List<Employee>> response = new ApiResponse<>();
		response.setStatus("Success");
		response.setMessage("Updatete successfully");
		response.setData(newList);
		
		return ResponseEntity.ok(response);
	}
}
