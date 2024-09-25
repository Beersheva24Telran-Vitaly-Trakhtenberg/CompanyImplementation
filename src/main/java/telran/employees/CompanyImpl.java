package telran.employees;

import java.util.*;

public class CompanyImpl implements Company
{
    private TreeMap<Long, Employee> employees = new TreeMap<>();
    private HashMap<String, List<Employee>> employees_department = new HashMap<>();
    private TreeMap<Float, List<Manager>> managers_factor = new TreeMap<>();

    @Override
    public Iterator<Employee> iterator() 
    {
       return new CompanyIterator();
    }

    @Override
    public void addEmployee(Employee employee)
    {
        long tmp_id = employee.getId();
        Employee res = employees.putIfAbsent(tmp_id, employee);
        if (res == null) {
            throw new IllegalStateException("The Employee (id=" + tmp_id + ") already exists.");
        }
        addEmployeeIntoMaps(employee);
    }

    private void addEmployeeIntoMaps(Employee employee)
    {
        employees_department.computeIfAbsent(String.valueOf(employee.getDepartmentId()), k -> new ArrayList<>()).add(employee);
        if (employee instanceof Manager manager) {  // since Java 16: Pattern Matching for instanceof
            managers_factor.computeIfAbsent(manager.getFactor(), k -> new ArrayList<>()).add(manager);
        }
    }

    @Override
    public Employee getEmployee(long id)
    {
        return employees.get(id);
    }

    @Override
    public Employee removeEmployee(long id) 
    {
        Employee removed_employee = employees.get(id);
        if (removed_employee != null) {
            removeEmployeeFromMaps(removed_employee);
            employees.remove(id);
        }

        return removed_employee;
    }

    private void removeEmployeeFromMaps(Employee removed_employee)
    {
        String department_employee_id = String.valueOf(removed_employee.getDepartmentId());
        List<Employee> department_employees = employees_department.get(department_employee_id);
        if (department_employees != null) {
            department_employees.remove(removed_employee);
            if (department_employees.isEmpty()) {
                employees_department.remove(department_employee_id);
            }
        }

        if (removed_employee instanceof Manager manager) {
            List<Manager> managers = managers_factor.get(manager.getFactor());
            if (managers != null) {
                managers.remove(removed_employee);
                if (managers.isEmpty()) {
                    managers_factor.remove(manager.getFactor());
                }
            }
        }
    }

    @Override
    public int getDepartmentBudget(String department_name)
    {
        int department_id = getDepartmentIDbyName(department_name);
        return calculateDepartmentBudget(department_id);
    }

    @Override
    public int getDepartmentBudget(int department_id)
    {
        return calculateDepartmentBudget(department_id);
    }

    private int calculateDepartmentBudget(int department_id)
    {
        List<Employee> department_employees = employees_department.get(String.valueOf(department_id));
        int sum = 0;
        if (department_employees != null) {
/*
            Iterator<Employee> iterator = department_employees.iterator();
            while (iterator.hasNext()) {
                sum += iterator.next().computeSalary();
            }
*/
            for (Employee employee : department_employees) {
                sum += employee.computeSalary();
            }
        }

        return sum;
    }

    private int getDepartmentIDbyName(String department_name)
    {
        int department_id = -1;
        List<Employee> department_employees = employees_department.get(department_name);
        if (department_employees != null && !department_employees.isEmpty()) {
            department_id = department_employees.get(0).department_id;
        }

        return department_id;
    }

    @Override
    public String[] getDepartments() 
    {
        Set<String> department_names = employees_department.keySet();
        return department_names.toArray(new String[0]);
    }

    @Override
    public Manager[] getManagersWithMostFactor() {
        Manager[] res = new Manager[0];
        if (!managers_factor.isEmpty()) {
            float max_factor = managers_factor.lastKey();
            List<Manager> managers_with_max_factor = managers_factor.get(max_factor);
            res = managers_with_max_factor.toArray(new Manager[0]);
        }

        return res;
    }

    private class CompanyIterator implements Iterator<Employee>
    {
        private final Iterator<Employee> iterator = employees.values().iterator();
        private Employee last_iterated;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Employee next() 
        {
            last_iterated = iterator.next();
            return last_iterated;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.
         * <p>
         * The behavior of an iterator is unspecified if the underlying collection
         * is modified while the iteration is in progress in any way other than by
         * calling this method, unless an overriding class has specified a
         * concurrent modification policy.
         * <p>
         * The behavior of an iterator is unspecified if this method is called
         * after a call to the {@link #forEachRemaining forEachRemaining} method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
            iterator.remove();
            if (last_iterated != null) {
                removeEmployeeFromIndexMaps(last_iterated);
                last_iterated = null;
            }
        }

        private void removeEmployeeFromIndexMaps(Employee last_iterated_employee)
        {
            List<Employee> departmentEmployees = employees_department.get(last_iterated_employee.getDepartment());
            if (departmentEmployees != null) {
                departmentEmployees.remove(last_iterated_employee);
                if (departmentEmployees.isEmpty()) {
                    employees_department.remove(last_iterated_employee.getDepartment());
                }
            }

            if (last_iterated_employee instanceof Manager) {
                Manager manager = (Manager) last_iterated_employee;
                List<Manager> factorManagers = managers_factor.get(manager.getFactor());
                if (factorManagers != null) {
                    factorManagers.remove(manager);
                    if (factorManagers.isEmpty()) {
                        managers_factor.remove(manager.getFactor());
                    }
                }
            }
        }
    }

}
