package com.bigcompany.org.service;

import java.util.Map;

import com.bigcompany.org.model.Employee;

public class OrganizationAnalyzer {

	//analyze employee list and check for given constraints
	public static void analyze(Map<String, Employee> employees) {
		
		//loop through employee list and check for salary constraint
        for (Employee e : employees.values()) {
            if (!e.subordinates.isEmpty()) {
                double avgSubSalary = e.subordinates.stream().mapToDouble(s -> s.salary).average().orElse(0.0);
                double minSalary = avgSubSalary * 1.2;
                double maxSalary = avgSubSalary * 1.5;

                if (e.salary < minSalary) {
                    System.out.printf("Manager %s earns %.2f LESS than required minimum (%.2f)\n", 
                            e.getFullName(), minSalary - e.salary, minSalary);
                } else if (e.salary > maxSalary) {
                    System.out.printf("Manager %s earns %.2f MORE than allowed maximum (%.2f)\n", 
                            e.getFullName(), e.salary - maxSalary, maxSalary);
                }
            }
        }

        //loop through employee list and check for manager depth constraint
        for (Employee e : employees.values()) {
            int depth = getManagerDepth(e);
            if (depth > 4) {
                System.out.printf("Employee %s has too long reporting line: depth = %d (too deep by %d)\n", 
                        e.getFullName(), depth, depth - 4);
            }
        }
    }

	//calculate and return depth of input Employee
    private static int getManagerDepth(Employee e) {
        int depth = 0;
        while (e.manager != null) {
            depth++;
            e = e.manager;
        }
        return depth;
    }
}
