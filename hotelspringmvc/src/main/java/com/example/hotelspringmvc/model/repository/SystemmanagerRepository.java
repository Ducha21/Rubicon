package com.example.hotelspringmvc.model.repository;

import com.example.hotelspringmvc.model.entity.Systemmanager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemmanagerRepository extends JpaRepository<Systemmanager,Integer> {
    @Query(value="select * from systemanager where email=?1 and password=?2",nativeQuery = true)
    Systemmanager findUser(String email,String password);
    Systemmanager findByEmail(String eamil);

}
