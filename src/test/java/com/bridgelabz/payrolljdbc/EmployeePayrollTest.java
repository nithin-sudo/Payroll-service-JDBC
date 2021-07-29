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
        @Test
        public void givenNewEmployeeSalaryShouldUpdateWithDatabase() {
            EmployeePayrollService employeePayrollService = new EmployeePayrollService();
            List<EmployeePayrollData> employeePayrollDataList = employeePayrollService
                    .readEmployeePayrollData(EmployeePayrollService.IOService.DB_IO);
            employeePayrollService.updateEmployeeBasic_pay("Terisa", 30000001.00);
            boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
            Assertions.assertTrue(result);
        }
}