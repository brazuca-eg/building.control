package ua.nure.kravchenko.entity.project;

public enum SalaryTypes {
    MIN(7000, 0.025),
    MEDIUM(10000, 0.035),
    MAX(15000, 0.045);

    private int salary;
    private double coefficient;

    public int getSalary() {
        return salary;
    }

    public double getCoefficient() {
        return coefficient;
    }

    SalaryTypes(int salary, double coefficient) {
        this.salary = salary;
        this.coefficient = coefficient;
    }
}
