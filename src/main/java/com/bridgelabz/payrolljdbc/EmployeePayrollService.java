package com.bridgelabz.payrolljdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeePayrollService {
    public enum IOService {
        CONSOLE_IO, FILE_IO, DB_IO
    }
    private List<EmployeePayrollData> employeePayrollDataList;
    EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollDataList) {
        this.employeePayrollDataList = employeePayrollDataList;
    }
    public EmployeePayrollService() {
        this.employeePayrollDataList = new ArrayList<EmployeePayrollData>();
    }
    private void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.println("Please enter employee name");
        String name = consoleInputReader.nextLine();
        System.out.println("Please enter employee ID");
        int id = consoleInputReader.nextInt();
        System.out.println("Please enter employee salary");
        double BasicPay = consoleInputReader.nextDouble();
        employeePayrollDataList.add(new EmployeePayrollData(id, name, BasicPay));
    }
    public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) {
        this.employeePayrollDataList = employeePayrollDBService.readData();
        return employeePayrollDataList;
    }
    private EmployeePayrollData getEmployeePayrollData(String name) {
        return this.employeePayrollDataList.stream()
                .filter(employeePayrollData -> employeePayrollData.name.equals(name)).findFirst().orElse(null);
    }
    public void updateEmployeeBasic_pay(String name, double BasicPay) {
        int result = employeePayrollDBService.updateEmployeeData(name, BasicPay);
        if (result == 0)
            return;
        EmployeePayrollData employeePayrollData = (EmployeePayrollData) employeePayrollDBService.getEmployeePayrollData(name);
        if (employeePayrollData != null)
            employeePayrollData.BasicPay = BasicPay;
    }

    public List<EmployeePayrollData> retrieveEmployeesForGivenDataRange(String startDate, String endDate) {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.retrieveEmployeePayrollDataRange(startDate, endDate);
        return employeePayrollDataList;
    }
    public Map<String, Double> readAverageSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getAverageSalaryByGender();
        return null;
    }

    public Map<String, Double> readSumSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getSumSalaryByGender();
        return null;
    }

    public Map<String, Double> readMinimumSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getMinimumSalaryByGender();
        return null;
    }

    public Map<String, Double> readMaximumSalaryByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getMaximumSalaryByGender();
        return null;
    }

    public Map<String, Integer> readCountNameByGender(IOService ioService) {
        if (ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getCountNameByGender();
        return null;
    }

    public boolean checkEmployeePayrollSyncWithDB(String name) {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
    }

    public void writeEmployeePayrollData(EmployeePayrollService.IOService ioService) {
        if(ioService.equals(IOService.CONSOLE_IO)) {
            System.out.println("\n Writing Employee Payroll Roster to Console\n" + employeePayrollDataList);
        }
        else if(ioService.equals(IOService.FILE_IO)){
            new EmployeePayrollFileIOService().writeData(employeePayrollDataList);
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Employee Payroll service program!");
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
    }
}
