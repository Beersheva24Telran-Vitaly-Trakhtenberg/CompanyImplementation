package telran.employees;

public class SalesPerson extends WageEmployee{
    private float percent;
    private long sales;

    public SalesPerson(long id, int basic_salary, int department_id, int wage, int hours, float percent, long sales)
    {
        super(id, basic_salary, department_id, wage, hours);
        this.percent = percent;
        this.sales = sales;
    }
    public SalesPerson(long id, int basic_salary, String department, int wage, int hours, float percent, long sales)
    {
        super(id, basic_salary, department, wage, hours);
        this.percent = percent;
        this.sales = sales;
    }

    @Override
    public int computeSalary()
    {
    // TODO Implement this method
    throw new UnsupportedOperationException("Method SalesPerson.computeSalary() not implemented yet");
    }
}
