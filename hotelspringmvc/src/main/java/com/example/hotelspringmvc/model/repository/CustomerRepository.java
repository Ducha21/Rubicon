package com.example.hotelspringmvc.model.repository;

import com.example.hotelspringmvc.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
}
