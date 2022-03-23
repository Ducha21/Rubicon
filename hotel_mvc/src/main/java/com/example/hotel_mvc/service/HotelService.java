package com.example.hotel_mvc.service;

import com.example.hotel_mvc.model.entity.Customer;
import com.example.hotel_mvc.model.entity.Hotel;
import com.example.hotel_mvc.model.repository.database.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;

    List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public void save(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    public Hotel getById(int id) {
        return hotelRepository.findById(id).get();
    }

    public void delete(int id) {
        hotelRepository.deleteById(id);
    }
}
