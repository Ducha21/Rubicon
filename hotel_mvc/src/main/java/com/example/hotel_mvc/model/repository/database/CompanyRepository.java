package com.example.hotel_mvc.model.repository.database;

import com.example.hotel_mvc.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
