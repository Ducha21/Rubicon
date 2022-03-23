package com.example.hotelspringmvc.service;

import com.example.hotelspringmvc.model.entity.Company;
import com.example.hotelspringmvc.model.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    List<Company> finAll(){
        return companyRepository.findAll();
    }
    public void save(Company company){
        companyRepository.save(company);
    }
    public Company getById(int id){
        return companyRepository.findById(id).get();
    }

    public void delete(int id){
        companyRepository.deleteById(id);
    }
}
