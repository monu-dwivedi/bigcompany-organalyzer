package com.bigcompany.org.service;

import org.junit.jupiter.api.Test;

import com.bigcompany.org.model.Employee;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OrganizationAnalyzerTest {

    @Test
    void testSalaryValidationOutput() {
        Employee manager = new Employee("1", "Boss", "Man", 48000, null);
        Employee emp1 = new Employee("2", "Sub", "One", 40000, "1");
        Employee emp2 = new Employee("3", "Sub", "Two", 40000, "1");

        manager.subordinates.add(emp1);
        manager.subordinates.add(emp2);
        emp1.manager = manager;
        emp2.manager = manager;

        Map<String, Employee> map = new HashMap<>();
        map.put("1", manager);
        map.put("2", emp1);
        map.put("3", emp2);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        OrganizationAnalyzer.analyze(map);

        String output = outContent.toString();
        assertTrue(output.contains("earns 4000.00 LESS"), "Output should contain underpaid message");
    }

    @Test
    void testDeepHierarchyOutput() {
        Employee ceo = new Employee("1", "CEO", "Top", 100000, null);
        Employee e2 = new Employee("2", "L2", "Emp", 90000, "1");
        Employee e3 = new Employee("3", "L3", "Emp", 80000, "2");
        Employee e4 = new Employee("4", "L4", "Emp", 70000, "3");
        Employee e5 = new Employee("5", "L5", "Emp", 60000, "4");
        Employee e6 = new Employee("6", "L6", "Emp", 50000, "5");

        ceo.subordinates.add(e2);
        e2.subordinates.add(e3);
        e3.subordinates.add(e4);
        e4.subordinates.add(e5);
        e5.subordinates.add(e6);

        e2.manager = ceo;
        e3.manager = e2;
        e4.manager = e3;
        e5.manager = e4;
        e6.manager = e5;

        Map<String, Employee> map = Map.of(
                "1", ceo, "2", e2, "3", e3, "4", e4, "5", e5, "6", e6
        );

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        OrganizationAnalyzer.analyze(map);

        String output = outContent.toString();
        assertTrue(output.contains("has too long reporting line"), "Output should show long reporting line");
    }
}
