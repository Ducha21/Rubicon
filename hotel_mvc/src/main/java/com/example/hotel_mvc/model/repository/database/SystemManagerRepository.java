package com.example.hotel_mvc.model.repository.database;

import com.example.hotel_mvc.model.entity.Systemmanager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemManagerRepository extends JpaRepository<Systemmanager, Integer> {

    @Query(value = "select * from systemmanager where email = ?1 and password = ?2", nativeQuery = true)
    Systemmanager findUser(String email, String password);

    Systemmanager findByEmail(String email);
}
