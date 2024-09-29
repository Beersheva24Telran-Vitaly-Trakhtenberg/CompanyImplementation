package telran.employees;

import java.util.Objects;
import java.util.Random;

public class Employee
{
    protected long id;
    protected int basic_salary;
    protected String department;

    public Employee(long id, int basic_salary, String department)
    {
        this.id = id;
        this.basic_salary = basic_salary;
        this.department = department;
    }

    protected static int getDepartmentIdByName(String department)
    {
        Random rnd = new Random();
        return rnd.nextInt(100000000);   // TODO real getting id (from DB etc.)
    }

    public int computeSalary()
    {
        return basic_salary;
    }

    public long getId()
    {
        return id;
    }

    public String getDepartment()
    {
        return department;
    }

    @Override
    public boolean equals(Object obj) 
    {
        boolean res = false;
        if (this == obj) {
            res = true;
        } else if (obj != null && getClass() == obj.getClass()) {
            if (!res) {
                Employee employee = (Employee) obj;
                res = basic_salary == employee.basic_salary &&
                        Objects.equals(department, employee.department);
            }
        }
        return res;
    }
}
