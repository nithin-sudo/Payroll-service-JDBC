package com.bridgelabz.payrolljdbc;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;

public class EmployeePayrollDBService
{
    public Connection getConnection() {
        String jdbcURL = "jdbc:mysql://localhost:3306/payrollservice?useSSL=false";
        String username = "root";
        String password = "nithin@123N";
        Connection connection = null;
        try {
            System.out.println("connecting to database");
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected Successfully!");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        listDrivers();
        return connection;
    }
    private static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = (Driver) driverList.nextElement();
            System.out.println(" " + driverClass.getClass().getName());
        }
    }
    public ArrayList<EmployeePayrollData> readData() {
        String sql = "SELECT * FROM employee_payroll";
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<EmployeePayrollData>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double BasicPay = resultSet.getDouble("BasicPay");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollList.add(new EmployeePayrollData(id, name, BasicPay, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }
}
