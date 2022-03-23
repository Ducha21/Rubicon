package com.example.springboot1.repository;

import com.example.springboot1.model.RoleModel;
import com.example.springboot1.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel,Long> {
    UserModel findByName(String username);
}
