package com.rutika.demo.service;

import com.rutika.demo.entity.Employee;
import com.rutika.demo.exception.ResourceNotFoundException;
import com.rutika.demo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Get all employees
    public List<Employee> getAll() {
        return repository.findAll();
    }

    // Get employee by ID
    public Employee getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }

    // Add new employee
    public Employee add(Employee employee) {
        repository.findByEmail(employee.getEmail()).ifPresent(e -> {
            throw new IllegalArgumentException("Email already exists");
        });
        return repository.save(employee);
    }

    // Update employee
    public Employee update(Long id, Employee employee) {
        Employee existing = getById(id);
        existing.setName(employee.getName());
        existing.setEmail(employee.getEmail());
        return repository.save(existing);
    }

    // Delete employee
    public void delete(Long id) {
        repository.delete(getById(id));
    }
}