package com.bridgelabz.payrolljdbc;

import java.time.LocalDate;
import java.util.Objects;

public class EmployeePayrollData {
    public int id;
    public String name;
    public double BasicPay;
    public LocalDate startDate;

    public EmployeePayrollData(int id, String name, double BasicPay, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.BasicPay = BasicPay;
        this.startDate = startDate;
    }

    public EmployeePayrollData(int id, String name, double BasicPay) {
        this.id = id;
        this.name = name;
        this.BasicPay = BasicPay;
    }

    @Override
    public String toString() {
        return "EmployeePayrollData{" + "id=" + id + ", name='" + name + '\'' + ", salary=" + BasicPay + ", startDate="
                + startDate + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EmployeePayrollData that = (EmployeePayrollData) o;
        return id == that.id && Double.compare(that.BasicPay, BasicPay) == 0 && Objects.equals(name, that.name);
    }
}
