package com.develop.empSalaryManagement.Controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import com.develop.empSalaryManagement.Entity.Employee;
import com.develop.empSalaryManagement.Response.TaxDeductionResponse;
import com.develop.empSalaryManagement.Service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/addEmployee")
	public Employee saveEmployee( @Valid @RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);

	}

	@GetMapping("/tax-deduction/{year}")
	public List<TaxDeductionResponse> getTaxDeduction(@PathVariable String year) {
	
      return employeeService.getTaxDeductionForAll(year);
	}
	
	 // Handle validation errors globally
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}