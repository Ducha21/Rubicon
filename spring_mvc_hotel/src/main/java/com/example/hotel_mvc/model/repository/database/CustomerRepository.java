package com.example.hotel_mvc.model.repository.database;

import com.example.hotel_mvc.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
