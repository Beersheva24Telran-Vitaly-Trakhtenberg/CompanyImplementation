package telran.employees;

import java.util.*;

public class CompanyImpl implements Company{
    private TreeMap<Long, Employee> employees = new TreeMap<>();
    private HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
    private TreeMap<Float, List<Manager>> managersFactor = new TreeMap<>();

    @Override
    public Iterator<Employee> iterator() 
    {
       return new CompanyIterator();    //TODO
    }

    @Override
    public void addEmployee(Employee empl) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyImpl.addEmployee() not implemented yet");
    }

    @Override
    public Employee getEmployee(long id)
    {
        return employees.get(id);
    }

    @Override
    public Employee removeEmployee(long id) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyImpl.removeEmployee() not implemented yet");
    }

    @Override
    public int getDepartmentBudget(String department) 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyImpl.getDepartmentBudget() not implemented yet");
    }

    @Override
    public int getDepartmentBudget(int department_id) {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyImpl.getDepartmentBudget() not implemented yet");
    }

    @Override
    public String[] getDepartments() 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyImpl.getDepartments() not implemented yet");    
    }

    @Override
    public Manager[] getManagersWithMostFactor() 
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyImpl.getManagersWithMostFactor() not implemented yet");
    }

}
