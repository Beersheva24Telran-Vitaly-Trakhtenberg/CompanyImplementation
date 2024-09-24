package telran.employees;

public class Manager extends Employee{
    private float factor;

    public Manager(long id, int basic_salary, int department_id, float factor)
    {
        super(id, basic_salary, department_id);
        this.factor = factor;
    }
    public Manager(long id, int basic_salary, String department, float factor)
    {
        super(id, basic_salary, department);
        this.factor = factor;
    }

    @Override
    public int computeSalary() {
        return (int)(super.computeSalary() * factor);
    }
    public float getFactor() {
        return factor;
    }
}
