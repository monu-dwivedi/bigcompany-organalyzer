package com.bigcompany.org.service;


import org.junit.jupiter.api.Test;

import com.bigcompany.org.model.Employee;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeCsvReaderTest {

    @Test
    void testReadEmployees() throws Exception {
        Path tempFile = Files.createTempFile("test-employees", ".csv");
        try (PrintWriter writer = new PrintWriter(new FileWriter(tempFile.toFile()))) {
            writer.println("Id,firstName,lastName,salary,managerId");
            writer.println("1,John,Smith,80000,");
            writer.println("2,Alice,Jones,50000,1");
        }

        Map<String, Employee> employees = EmployeeCsvReader.readEmployees(tempFile.toString());

        assertEquals(2, employees.size());
        assertTrue(employees.containsKey("1"));
        assertTrue(employees.containsKey("2"));
        assertEquals("John", employees.get("1").firstName);
        assertEquals("Alice", employees.get("2").firstName);

        Employee alice = employees.get("2");
        assertEquals("1", alice.managerId);
        assertNotNull(alice.manager);
        assertEquals("John", alice.manager.firstName);
    }
}

