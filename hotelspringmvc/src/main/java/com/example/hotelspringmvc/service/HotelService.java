package com.example.hotelspringmvc.service;

import com.example.hotelspringmvc.model.entity.Hotel;
import com.example.hotelspringmvc.model.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;

    List<Hotel> finAll(){
        return hotelRepository.findAll();
    }

    public void save(Hotel hotel){
        hotelRepository.save(hotel);
    }
    public Hotel getById(int id){
        return hotelRepository.findById(id).get();
    }

    public void delete(int id){
        hotelRepository.deleteById(id);
    }
}
