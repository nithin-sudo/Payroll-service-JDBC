package com.bridgelabz.payrolljdbc;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class EmployeePayrollDBService {
    private PreparedStatement employeePayrollDataStatement;

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

    public List<EmployeePayrollData> getEmployeePayrollData(String name) {
        List<EmployeePayrollData> employeePayrollDataList = null;
        if (this.employeePayrollDataStatement == null)
            this.prepareStatementForEmployeeData();
        try {
            employeePayrollDataStatement.setString(1, name);
            ResultSet resultSet;
            resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollDataList = this.getEmployeePayrollData(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    private List<EmployeePayrollData> getEmployeePayrollData(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double BasicPay = resultSet.getDouble("BasicPay");
                LocalDate startDate = null;
                startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(id, name, BasicPay, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    public Map<String, Double> getAverageSalaryByGender() {
        String sql = "SELECT gender, AVG(BasicPay) as average_salary FROM employee_payroll GROUP BY GENDER;";
        Map<String, Double> genderToAverageSalaryMap = new HashMap<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Double averageSalary = resultSet.getDouble("average_salary");
                genderToAverageSalaryMap.put(gender, averageSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToAverageSalaryMap;
    }

    public Map<String, Double> getSumSalaryByGender() {
        String sql = "SELECT gender, SUM(BasicPay) as sum_salary FROM employee_payroll GROUP BY GENDER;";
        Map<String, Double> genderToSumSalaryMap = new HashMap<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Double sumSalary = resultSet.getDouble("sum_salary");
                genderToSumSalaryMap.put(gender, sumSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToSumSalaryMap;
    }

    public Map<String, Double> getMinimumSalaryByGender() {
        String sql = "SELECT gender, Min(BasicPay) as minimum_salary FROM employee_payroll GROUP BY GENDER;";
        Map<String, Double> genderToMinimumSalaryMap = new HashMap<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Double minimumSalary = resultSet.getDouble("minimum_salary");
                genderToMinimumSalaryMap.put(gender, minimumSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToMinimumSalaryMap;
    }

    public Map<String, Double> getMaximumSalaryByGender() {
        String sql = "SELECT gender, Max(BasicPay) as maximum_salary FROM employee_payroll GROUP BY GENDER;";
        Map<String, Double> genderToMaximumSalaryMap = new HashMap<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Double maximumSalary = resultSet.getDouble("maximum_salary");
                genderToMaximumSalaryMap.put(gender, maximumSalary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToMaximumSalaryMap;
    }

    public Map<String, Integer> getCountNameByGender() {
        String sql = "SELECT gender, COUNT(name) as count_name FROM employee_payroll GROUP BY GENDER;";
        Map<String, Integer> genderToCountNameMap = new HashMap<>();
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                Integer countName = resultSet.getInt("count_name");
                genderToCountNameMap.put(gender, countName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genderToCountNameMap;
    }

    private void prepareStatementForEmployeeData() {
        try {
            Connection connection = this.getConnection();
            String sql = "SELECT * FROM employee_payroll WHERE name = ?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int updateEmployeeData(String name, double BasicPay) {
        return this.updateEmployeeDataUsingStatement(name, BasicPay);
    }

    private int updateEmployeeDataUsingStatement(String name, double BasicPay) {
        String sql = String.format("update employee_payroll set BasicPay = %.2f where name = '%s';", BasicPay, name);
        try (Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<EmployeePayrollData> retrieveEmployeePayrollDataRange(String startDate, String endDate) {
        List<EmployeePayrollData> employeePayrollDataList = null;
        try {
            if (this.employeePayrollDataStatement == null)
                this.prepareStatementForRetrieveEmployeePayrollDateRange();
            employeePayrollDataStatement.setString(1, startDate);
            employeePayrollDataStatement.setString(2, endDate);
            ResultSet resultSet;
            resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollDataList = this.retrieveEmployeePayrollDataRange(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    private List<EmployeePayrollData> retrieveEmployeePayrollDataRange(ResultSet resultSet) {
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double basic_pay = resultSet.getDouble("BasicPay");
                LocalDate startDate = resultSet.getDate("start").toLocalDate();
                employeePayrollDataList.add(new EmployeePayrollData(id, name, basic_pay, startDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollDataList;
    }

    private void prepareStatementForRetrieveEmployeePayrollDateRange() throws SQLException {
        Connection connection = null;
        connection = this.getConnection();
        String sql = "SELECT * FROM employee_payroll WHERE start BETWEEN ? AND ?";
        try {
            assert connection != null;
            employeePayrollDataStatement = connection.prepareStatement(sql);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
    public EmployeePayrollData addEmployeeToPayroll(String employeeName, String Department,String gender, double BasicPay, LocalDate start) {
        int employeeId = -1;
        EmployeePayrollData employeePayrollData = null;
        String sql = String.format("INSERT INTO employee_payroll (name,Department gender, BasicPay, start) " +
                "VALUES('%s', '%s','%s', '%s', '%s');", employeeName,Department,gender, BasicPay, start);

        try(Connection connection = this.getConnection()) {
            Statement statement = connection.createStatement();
            int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
            if (rowAffected == 1) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next())
                    employeeId = resultSet.getInt(1);
            }
            employeePayrollData = new EmployeePayrollData(employeeId, employeeName, BasicPay, start);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollData;
    }

}
