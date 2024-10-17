package com.luv2code.springboot.thymeleafdemo.service;

import java.util.List;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface EmployeeService {

	List<Employee> findAll();

	Employee findById(int theId);

	void save(Employee theEmployee);

	void deleteById(int theId);

	List<Employee> searchByName(String name, Sort firstName);


	Page<Employee> findPaginated(int pageNo, int pageSize,Sort sort);

	List<Employee> searchEmployeesByName(String name);
}
