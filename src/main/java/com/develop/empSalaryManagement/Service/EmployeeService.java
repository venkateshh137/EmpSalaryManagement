package com.develop.empSalaryManagement.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

	public List<TaxDeductionResponse> getTaxDeductionForAll(String year) {

		List<Employee> employeeCount = employeeRepository.findAll();

		List<TaxDeductionResponse> employeeTaxList = new ArrayList<>();

		if (employeeCount.size() != 0 || employeeCount != null) {

			for (Employee employee : employeeCount) {

				LocalDate dateOfJoining = LocalDate.parse(employee.getDoj());

				Double yearlySalary = employee.getSalary() * 12.0;

				double taxableSalary = calculateTaxableSalary(yearlySalary, dateOfJoining);

				double taxAmount = calculateTax(taxableSalary);

				double cessAmount = calculateCess(yearlySalary);

				employeeTaxList.add(new TaxDeductionResponse(employee.getEmpId(), employee.getFirstName(),
						employee.getLastName(), yearlySalary, taxAmount, cessAmount));

			}

			return employeeTaxList;

		} else {
			throw new EmployeeNotFoundException("employee does not exist in database");
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

}
