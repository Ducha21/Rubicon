package com.example.hotelspringmvc.model.repository;

import com.example.hotelspringmvc.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {
}
