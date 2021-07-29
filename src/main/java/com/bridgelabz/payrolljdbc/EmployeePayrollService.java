package com.bridgelabz.payrolljdbc;

import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollService {
    public enum IOService {
        DB_IO
    }
    private List<EmployeePayrollData> employeePayrollDataList;
    EmployeePayrollDBService employeePayrollDBService = new EmployeePayrollDBService();

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollDataList) {
        this.employeePayrollDataList = employeePayrollDataList;
    }
    public EmployeePayrollService() {
        this.employeePayrollDataList = new ArrayList<EmployeePayrollData>();
    }
    public List<EmployeePayrollData> readEmployeePayrollData(IOService ioService) {
        this.employeePayrollDataList = employeePayrollDBService.readData();
        return employeePayrollDataList;
    }

    public boolean checkEmployeePayrollInSyncWithDB(String name) {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
    }

    private EmployeePayrollData getEmployeePayrollData(String name) {
        return this.employeePayrollDataList.stream()
                .filter(employeePayrollData -> employeePayrollData.name.equals(name)).findFirst().orElse(null);
    }

    public void updateEmployeeBasic_pay(String name, double BasicPay) {
        int result = employeePayrollDBService.updateEmployeeData(name, BasicPay);
        if (result == 0)
            return;
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if (employeePayrollData != null)
            employeePayrollData.BasicPay = BasicPay;
    }
}
