package com.luv2code.springboot.thymeleafdemo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

	// define fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name="email", nullable=false)  // Ensure nullable=false
	private String email;

	@Column(name = "photo_path")
	private String photoPath;

	@ManyToOne
	@JoinColumn(name = "company_id", referencedColumnName = "id")
	private Company company;


	// define constructors
	public Employee() {
	}

	// Constructor including photoPath and company
	public Employee(int id, String firstName, String lastName, String email, String photoPath, Company company) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.photoPath = photoPath;
		this.company = company;
	}

	// Constructor without id (used when creating a new employee)
	public Employee(String firstName, String lastName, String email, String photoPath, Company company) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.photoPath = photoPath;
		this.company = company;
	}

	// Constructor without photoPath (for backward compatibility)
	public Employee(int id, String firstName, String lastName, String email, Company company) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.company = company;
	}

	public Employee(String firstName, String lastName, String email, Company company) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.company = company;
	}

	// Define getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	// Define toString for logging or debugging
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", photoPath=" + photoPath + ", company=" + company.getName() + "]";
	}
}
