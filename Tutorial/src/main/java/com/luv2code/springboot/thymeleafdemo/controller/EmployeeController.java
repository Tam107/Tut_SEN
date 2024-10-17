package com.luv2code.springboot.thymeleafdemo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.luv2code.springboot.thymeleafdemo.dao.CompanyRepository;
import com.luv2code.springboot.thymeleafdemo.entity.Company;
import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageRequest;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	@Autowired
	private CompanyRepository companyRepository;

	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}

	// Mapping for login page
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage() {
		return "employees/fancy-login";
	}

	// Mapping for access denied page
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		return "employees/access-denied";
	}

	@GetMapping("/list")
	public String listEmployees(
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "firstName") String sortField,
			@RequestParam(defaultValue = "asc") String sortDir,
			Model model) {

		// Determine sort direction
		Sort sort = sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

		// Get paginated employees list with sorting
		Page<Employee> employeePage = employeeService.findPaginated(page, size, sort);
		List<Employee> employees = employeePage.getContent();

		// Add attributes to model
		model.addAttribute("employees", employees);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", employeePage.getTotalPages());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);

		return "employees/list-employees";
	}


	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {

		// Fetch employee
		Employee theEmployee = employeeService.findById(theId);
		theModel.addAttribute("employee", theEmployee);

		return "employees/employee-form";
	}

	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee employee,
							   @RequestParam("photo") MultipartFile photo) {

		// Handle file upload
		if (!photo.isEmpty()) {
			Path filePath = Paths.get("uploads/" + photo.getOriginalFilename());
			try {
				Files.write(filePath, photo.getBytes());
				employee.setPhotoPath(filePath.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Ensure email is not null or empty
		if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
			// Handle email being null or empty, throw an exception or set a default email
			throw new IllegalArgumentException("Email cannot be null or empty");
		}

		// Save the employee
		employeeService.save(employee);

		return "redirect:/employees/list";
	}






	@PostMapping("/delete")
	public String deleteEmployee(@RequestParam("employeeId") int theId) {
		employeeService.deleteById(theId);
		return "redirect:/employees/list-employees";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		Employee employee = new Employee();
		List<Company> companies = companyRepository.findAll();
		model.addAttribute("employee", employee);
		model.addAttribute("companies", companies);
		return "employees/employee-form";
	}

	// Search employees
	@GetMapping("/search")
	public String searchEmployees(@RequestParam("name") String name, Model model) {
		List<Employee> employees = employeeService.searchByName(name, Sort.by("firstName"));
		model.addAttribute("employees", employees);
		return "employees/list-employees"; // Ensure to render the same view
	}

}
