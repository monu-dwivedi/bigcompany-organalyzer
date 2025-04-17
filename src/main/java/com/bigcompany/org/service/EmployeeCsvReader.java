package com.bigcompany.org.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import com.bigcompany.org.model.Employee;

public class EmployeeCsvReader {

	//create a map of employee id and employee object
	public static Map<String, Employee> readEmployees(String filePath) {
        Map<String, Employee> employees = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0].trim();
                String firstName = parts[1].trim();
                String lastName = parts[2].trim();
                double salary = Double.parseDouble(parts[3].trim());
                String managerId = parts.length > 4 ? parts[4].trim() : null; //check if employee has manager

                Employee e = new Employee(id, firstName, lastName, salary, managerId);
                employees.put(id, e);
            }

            // Assign managers and build hierarchy
            for (Employee e : employees.values()) {
                if (e.managerId != null && !e.managerId.isEmpty()) {
                    Employee manager = employees.get(e.managerId);
                    e.manager = manager;
                    manager.subordinates.add(e);
                }
            }

        } catch (Exception e) {
        	System.out.println("Error occured : "+ e.getMessage());
        }

        return employees;
    }
}
