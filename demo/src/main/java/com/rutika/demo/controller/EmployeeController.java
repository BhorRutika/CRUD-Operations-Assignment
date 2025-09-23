package com.rutika.demo.controller;

import com.rutika.demo.entity.Employee;
import com.rutika.demo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // ----- DISPLAY ALL EMPLOYEES -----
    @GetMapping("/all")
    public List<Employee> displayAll() {
        return service.getAll();
    }

    // ----- DISPLAY ONE EMPLOYEE BY ID -----
    @GetMapping("/{id}")
    public Employee displayOne(@PathVariable Long id) {
        return service.getById(id);
    }

    // ----- CREATE NEW EMPLOYEE -----
    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
        Employee saved = service.add(employee);
        return ResponseEntity.created(URI.create("/api/employees/" + saved.getId())).body(saved);
    }

    // ----- UPDATE EMPLOYEE -----
    @PutMapping("/update/{id}")
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        return service.update(id, employee);
    }

    // ----- DELETE EMPLOYEE -----
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}