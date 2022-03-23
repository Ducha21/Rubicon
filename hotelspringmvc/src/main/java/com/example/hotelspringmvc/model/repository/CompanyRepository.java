package com.example.hotelspringmvc.model.repository;

import com.example.hotelspringmvc.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
}
