package telran.employees;

import java.util.*;
import java.io.*;
import java.nio.file.*;

import telran.io.Persistable;

public class CompanyImpl implements Company, Persistable
{
    private TreeMap<Long, Employee> employees = new TreeMap<>();
    private HashMap<String, List<Employee>> employees_department = new HashMap<>();
    private TreeMap<Float, List<Manager>> managers_factor = new TreeMap<>();

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
    }

    @Override
    public Iterator<Employee> iterator() 
    {
       return new CompanyIterator();
    }

    @Override
    public void addEmployee(Employee employee)
    {
        long tmp_id = employee.getId();
        Employee empl = employees.putIfAbsent(tmp_id, employee);
        if (empl != null) {
            throw new IllegalStateException("The Employee (id=" + tmp_id + ") already exists.");
        }
        addEmployeeIntoMaps(employee);
    }

    private void addEmployeeIntoMaps(Employee employee)
    {
        employees_department.computeIfAbsent(String.valueOf(employee.getDepartment()), k -> new ArrayList<>()).add(employee);
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
        if (removed_employee == null) {
            throw new NoSuchElementException("The Employee (id=" + id + ") does not exist.");
        }

        removeEmployeeFromIndexMaps(removed_employee);
        employees.remove(id);

        return removed_employee;
    }

    private void removeEmployeeFromIndexMaps(Employee removed_employee) {
        removeIndexMap(removed_employee.getDepartment(), employees_department, removed_employee);
        if (removed_employee instanceof Manager manager) {
            removeIndexMap(manager.getFactor(), managers_factor, manager);
        }
    }

    private <K, V extends Employee> void removeIndexMap(K key, Map<K, List<V>> map, V empl) {
        List<V> list = map.get(key);
        list.remove(empl);
        if (list.isEmpty()) {
            map.remove(key);
        }
    }

    @Override
    public int getDepartmentBudget(String department_name)
    {
        List<Employee> department_employees = employees_department.get(department_name);
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

    @Override
    public String[] getDepartments() 
    {
        Set<String> department_names = new HashSet<>();

        for (List<Employee> employees : employees_department.values()) {
            for (Employee employee : employees) {
                department_names.add(employee.getDepartment());
            }
        }
        String[] departments_array = department_names.toArray(new String[0]);
        Arrays.sort(departments_array);
        return departments_array;
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

    @Override
    public void saveToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            forEach(writer::println);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void restoreFromFile(String fileName) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
            reader.lines().map(Employee::getEmployeeFromJSON).forEach(this::addEmployee);
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
