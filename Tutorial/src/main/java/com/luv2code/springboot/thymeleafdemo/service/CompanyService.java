package com.luv2code.springboot.thymeleafdemo.service;

import com.luv2code.springboot.thymeleafdemo.entity.Company;

import java.util.List;

public interface CompanyService {

    List<Company> findAll();

    Company findById(Long id);

    void save(Company company);

    void deleteById(Long id);
}
