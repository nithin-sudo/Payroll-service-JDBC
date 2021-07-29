package com.bridgelabz.payrolljdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EmployeePayrollTest {
        @Test
        public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
            EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
            List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.readData();
            Assertions.assertEquals(6, employeePayrollDataList.size());
        }
        @Test
        public void givenEmployeePayrollInDB_WhenRetrieved_ShouldnotMatchEmployeeCount() {
            EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();
            List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.readData();
            Assertions.assertNotSame(4, employeePayrollDataList.size());
        }
}