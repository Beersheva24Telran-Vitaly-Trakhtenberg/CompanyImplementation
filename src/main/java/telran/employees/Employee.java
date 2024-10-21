package telran.employees;

import java.util.Objects;
import java.util.Random;
import org.json.JSONObject;

public class Employee
{
    protected long id;
    protected int basic_salary;
    protected String department;
    public Employee(){

    }
    @SuppressWarnings("unchecked")
    static public Employee getEmployeeFromJSON(String json_str) {
        JSONObject json_obj = new JSONObject(json_str);
        String className = json_obj.getString("className");
        try {
            Class<Employee> clazz = (Class<Employee>) Class.forName(className);
            Employee employee =  clazz.getConstructor().newInstance();
            employee.setObject(json_obj);
            return employee;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void setObject(JSONObject json_obj) {
        id = json_obj.getLong("id");
        basic_salary = json_obj.getInt("basicSalary");
        department = json_obj.getString("department");
    }

    public Employee(long id, int basic_salary, String department)
    {
        this.id = id;
        this.basic_salary = basic_salary;
        this.department = department;
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
    @Override
    public String toString() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("className", getClass().getName());
        fillJSON(jsonObj);
        return jsonObj.toString();
    }
    protected void fillJSON(JSONObject jsonObj) {
        jsonObj.put("id",id);
        jsonObj.put("basicSalary", basic_salary);
        jsonObj.put("department", department);
    }
}
