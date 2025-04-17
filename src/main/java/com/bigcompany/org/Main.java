package com.bigcompany.org;

import java.util.Map;

import com.bigcompany.org.model.Employee;
import com.bigcompany.org.service.EmployeeCsvReader;
import com.bigcompany.org.service.OrganizationAnalyzer;

public class Main {
	
	 public static void main(String[] args) {
	        String filePath = "big-company-employees.csv";
	        Map<String, Employee> employees = EmployeeCsvReader.readEmployees(filePath);
	        OrganizationAnalyzer.analyze(employees);
	    }
}
