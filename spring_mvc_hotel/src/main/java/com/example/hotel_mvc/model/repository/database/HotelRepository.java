package com.example.hotel_mvc.model.repository.database;

import com.example.hotel_mvc.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Integer> {
}
