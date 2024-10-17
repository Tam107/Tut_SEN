package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Company;
import com.luv2code.springboot.thymeleafdemo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listCompanies(Model model) {
        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        return "companies/list-companies";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Company company = new Company();
        model.addAttribute("company", company);
        return "companies/company-form";
    }

    @PostMapping("/save")
    public String saveCompany(Company company) {
        companyService.save(company);
        return "redirect:/companies/list";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("companyId") Long id) {
        companyService.deleteById(id);
        return "redirect:/companies/list";
    }
}
