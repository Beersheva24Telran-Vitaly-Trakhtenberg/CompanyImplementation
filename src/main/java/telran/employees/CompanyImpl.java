package telran.employees;

import java.util.*;

public class CompanyImpl implements Company
{
    private TreeMap<Long, Employee> employees = new TreeMap<>();
    private HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
    private TreeMap<Float, List<Manager>> managersFactor = new TreeMap<>();

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
        employeesDepartment.computeIfAbsent(String.valueOf(employee.getDepartmentId()), k -> new ArrayList<>()).add(employee);
        if (employee instanceof Manager manager) {  // since Java 16: Pattern Matching for instanceof
            managersFactor.computeIfAbsent(manager.getFactor(), k -> new ArrayList<>()).add(manager);
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
        Employee removed_employee = employees.remove(id);
        if (removed_employee != null) {
            removeEmployeeFromMaps(removed_employee);
        }

        return removed_employee;
    }

    private void removeEmployeeFromMaps(Employee removedEmployee)
    {
        // TODO Implement this method
        throw new UnsupportedOperationException("Method CompanyImpl.removeEmployeeFromMaps() not implemented yet");
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
            List<Employee> departmentEmployees = employeesDepartment.get(last_iterated_employee.getDepartment());
            if (departmentEmployees != null) {
                departmentEmployees.remove(last_iterated_employee);
                if (departmentEmployees.isEmpty()) {
                    employeesDepartment.remove(last_iterated_employee.getDepartment());
                }
            }

            if (last_iterated_employee instanceof Manager) {
                Manager manager = (Manager) last_iterated_employee;
                List<Manager> factorManagers = managersFactor.get(manager.getFactor());
                if (factorManagers != null) {
                    factorManagers.remove(manager);
                    if (factorManagers.isEmpty()) {
                        managersFactor.remove(manager.getFactor());
                    }
                }
            }
        }
    }

}
