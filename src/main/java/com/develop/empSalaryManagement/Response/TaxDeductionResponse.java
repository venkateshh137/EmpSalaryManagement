package com.develop.empSalaryManagement.Response;

public class TaxDeductionResponse {
	
	private Integer employeeCode;
    private String firstName;
    private String lastName;
    private double yearlySalary;
    private double taxAmount;
    private double cessAmount;
    
	public TaxDeductionResponse(Integer employeeCode, String firstName, String lastName, double yearlySalary,
			double taxAmount, double cessAmount) {
		super();
		this.employeeCode = employeeCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.yearlySalary = yearlySalary;
		this.taxAmount = taxAmount;
		this.cessAmount = cessAmount;
	}
	

	public Integer getEmployeeCode() {
		return employeeCode;
	}


	public void setEmployeeCode(Integer employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public double getYearlySalary() {
		return yearlySalary;
	}
	public void setYearlySalary(double yearlySalary) {
		this.yearlySalary = yearlySalary;
	}
	public double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(double taxAmount) {
		this.taxAmount = taxAmount;
	}
	public double getCessAmount() {
		return cessAmount;
	}
	public void setCessAmount(double cessAmount) {
		this.cessAmount = cessAmount;
	}
    
    

}
