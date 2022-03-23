package com.example.hotelspringmvc.service;

import com.example.hotelspringmvc.model.entity.Customer;
import com.example.hotelspringmvc.model.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.ComponentUI;
import java.security.PublicKey;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    List<Customer> finAll(){
        return customerRepository.findAll();
    }

    public void save(Customer customer){
        customerRepository.save(customer);
    }

    public Customer getById(int id){
        return customerRepository.findById(id).get();
    }
    public void delete(int id){
        customerRepository.deleteById(id);
    }
}
