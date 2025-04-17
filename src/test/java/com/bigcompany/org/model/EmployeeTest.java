package com.bigcompany.org.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testEmployeeFullName() {
        Employee emp = new Employee("101", "Jane", "Doe", 50000, null);
        assertEquals("Jane Doe", emp.getFullName());
    }

    @Test
    void testAddSubordinate() {
        Employee manager = new Employee("1", "Manager", "One", 90000, null);
        Employee emp = new Employee("2", "Emp", "Two", 50000, "1");

        manager.subordinates.add(emp);
        emp.manager = manager;

        assertEquals(1, manager.subordinates.size());
        assertEquals(manager, emp.manager);
    }
}
