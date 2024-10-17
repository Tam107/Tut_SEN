package com.luv2code.springboot.thymeleafdemo.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Correct import
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Method to sort by last name
    public List<Employee> findAllByOrderByLastNameAsc();

    // Pageable method for pagination
    Page<Employee> findAll(Pageable pageable); // Now it uses the correct Pageable

    // Search method for first name or last name containing
    List<Employee> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    // Hoặc bạn có thể sử dụng truy vấn thủ công với @Query
    @Query("SELECT e FROM Employee e WHERE e.firstName LIKE %:name% OR e.lastName LIKE %:name%")
    List<Employee> searchByName(@Param("name") String name);

    List<Employee> findByFirstNameContainingOrLastNameContainingAllIgnoreCase(String name, String name1, Sort sort);
}
