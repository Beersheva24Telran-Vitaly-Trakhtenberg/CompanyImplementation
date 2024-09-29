package telran.employees;

public class SalesPerson extends WageEmployee{
    private float percent;
    private long sales;

    public SalesPerson(long id, int basic_salary, String department, int wage, int hours, float percent, long sales)
    {
        super(id, basic_salary, department, wage, hours);
        this.percent = percent;
        this.sales = sales;
    }

    @Override
    public int computeSalary()
    {
        return (int) (super.computeSalary() + sales * percent/100);
    }
}
