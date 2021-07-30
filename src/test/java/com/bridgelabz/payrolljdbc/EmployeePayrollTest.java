package com.bridgelabz.payrolljdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.bridgelabz.payrolljdbc.EmployeePayrollService.IOService.DB_IO;

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
                .readEmployeePayrollData(DB_IO);
        employeePayrollService.updateEmployeeBasic_pay("Terisa", 30000001.00);
        boolean result = employeePayrollService.checkEmployeePayrollSyncWithDB("Terisa");
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDataRangeWhenRetrievedShouldMatchEmployeeCount() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollService.readEmployeePayrollData(DB_IO);
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.retrieveEmployeesForGivenDataRange("2018-01-01", "2019-01-03");
        Assertions.assertEquals(3, employeePayrollData.size());
    }

    @Test
    public void givenPayrollData_WhenAverageSalaryRetrieveByGender_ShouldReturnValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        Map<String, Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(DB_IO);
        Assertions.assertTrue(averageSalaryByGender.get("M").equals(8000000.0) && averageSalaryByGender.get("F").equals(26666686.186666667));
    }

    @Test
    public void givenPayrollData_WhenSumSalaryRetrieveByGender_ShouldReturnValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        Map<String, Double> sumSalaryByGender = employeePayrollService.readSumSalaryByGender(DB_IO);
        Assertions.assertTrue(sumSalaryByGender.get("M").equals(24000000) && sumSalaryByGender.get("F").equals(80000058.56));
    }

    @Test
    public void givenPayrollData_WhenMinimumSalaryRetrieveByGender_ShouldReturnValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        Map<String, Double> minimumSalaryByGender = employeePayrollService.readMinimumSalaryByGender(DB_IO);
        Assertions.assertTrue(minimumSalaryByGender.get("M").equals(1000000) && minimumSalaryByGender.get("F").equals(20000056.56));
    }

    @Test
    public void givenPayrollData_WhenMaximumSalaryRetrieveByGender_ShouldReturnValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        Map<String, Double> maximumSalaryByGender = employeePayrollService.readMaximumSalaryByGender(DB_IO);
        Assertions.assertTrue(maximumSalaryByGender.get("M").equals(20000000) && maximumSalaryByGender.get("F").equals(30000001));
    }

    @Test
    public void givenPayrollData_WhenCountNameRetrieveByGender_ShouldReturnValue() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(DB_IO);
        Map<String, Integer> countNameByGender = employeePayrollService.readCountNameByGender(DB_IO);
        Assertions.assertTrue(countNameByGender.get("M").equals(3) && countNameByGender.get("F").equals(3));
    }
}