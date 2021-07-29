package com.bridgelabz.payrolljdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class EmployeePayrollDBService
{
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/payrollservice?useSSL=false";
        String username = "root";
        String password = "nithin@123N";
        Connection connection;
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
    }
    private static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while (driverList.hasMoreElements()) {
            Driver driverClass = (Driver) driverList.nextElement();
            System.out.println(" " + driverClass.getClass().getName());
        }
    }
}
