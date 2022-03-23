package com.example.hotel_mvc.service;

import com.example.hotel_mvc.model.entity.Customer;
import com.example.hotel_mvc.model.repository.database.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer getById(int id) {
        return customerRepository.findById(id).get();
    }

    public void delete(int id) {
        customerRepository.deleteById(id);
    }

}
