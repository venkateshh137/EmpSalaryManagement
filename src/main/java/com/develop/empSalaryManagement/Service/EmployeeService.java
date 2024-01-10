package com.develop.empSalaryManagement.Service;

import java.time.LocalDate;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.develop.empSalaryManagement.Entity.Employee;
import com.develop.empSalaryManagement.Exception.EmployeeNotFoundException;
import com.develop.empSalaryManagement.Repository.EmployeeRepository;
import com.develop.empSalaryManagement.Response.TaxDeductionResponse;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Employee saveEmployee(Employee employee) {
		return employeeRepository.save(employee);

	}
	
	
	public TaxDeductionResponse getTaxDeduction(Integer empId) {
		
		Optional<Employee> employeeOptional =employeeRepository.findById(empId);
		
		if(employeeOptional.isPresent())
		{
			Employee employee=employeeOptional.get();
			              
			LocalDate dateOfJoining = LocalDate.parse(employee.getDoj());

			Double yearlySalary = employee.getSalary() * 12.0;

		   double taxableSalary = calculateTaxableSalary(yearlySalary, dateOfJoining);

		   double taxAmount = calculateTax(taxableSalary);

		   double cessAmount = calculateCess(yearlySalary);

			return new TaxDeductionResponse(employee.getEmpId(), employee.getFirstName(), employee.getLastName(),
					yearlySalary, taxAmount, cessAmount);
			
		}else
	{
	throw new EmployeeNotFoundException("employee with this id does not exist");
	}

		
	}


	private double calculateCess(Double yearlySalary) {
		 if (yearlySalary > 2500000) {
	            return 0.02 * (yearlySalary - 2500000);
	        } else {
	            return 0;
	        }
	    }
	
	private double calculateTax(double salary) {
		  if (salary <= 250000) {
	            return 0;
	        } else if (salary <= 500000) {
	            return 0.05 * (salary - 250000);
	        } else if (salary <= 1000000) {
	            return 0.1 * (salary - 500000) + 12500;
	        } else {
	            return 0.2 * (salary - 1000000) + 12500 + 50000;
	        }
	}


	private double calculateTaxableSalary(Double yearlySalary, LocalDate dateOfJoining) {
		 LocalDate currentDate = LocalDate.now();
	        if (dateOfJoining.getYear() == currentDate.getYear() && dateOfJoining.getMonthValue() > 4) {
	            return yearlySalary * ((12 - dateOfJoining.getMonthValue() + 1) / 12);
	        } else {
	            return yearlySalary;
	        }
	}
	}
	
