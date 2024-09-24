package telran.employees;

public interface Company extends Iterable<Employee>{
	public void addEmployee(Employee empl) ;
	public Employee getEmployee(long id) ;
	public Employee removeEmployee(long id) ;
	public int getDepartmentBudget(String department) ;
	public int getDepartmentBudget(int department_id) ;
	public String[] getDepartments() ;
	public Manager[] getManagersWithMostFactor() ;
}
