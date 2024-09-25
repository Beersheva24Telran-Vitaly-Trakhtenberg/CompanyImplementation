package telran.employees;

import java.util.Objects;
import java.util.Random;

public class Employee
{
    protected long id;
    protected int basic_salary;
    protected String department;
    protected int department_id;

    public Employee(long id, int basic_salary, String department)
    {
        this(id, basic_salary, getDepartmentIdByName(department));
        this.department = department;
    }
    public Employee(long id, int basic_salary, int department_id)
    {
        if (department_id == -1) { throw new RuntimeException("Incorrect Department ID"); }

        this.id = id;
        this.basic_salary = basic_salary;
        this.department_id = department_id;
        this.department = getDepartmentById(this.department_id);
    }

    protected static String getDepartmentById(int department_id)
    {
        return "Department_" + String.valueOf(department_id);   // TODO real getting name (from DB etc.)
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

    public int getDepartmentId()
    {
        return department_id;
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
                        department_id == employee.department_id &&
                        Objects.equals(department, employee.department);
            }
        }
        return res;
    }
}
