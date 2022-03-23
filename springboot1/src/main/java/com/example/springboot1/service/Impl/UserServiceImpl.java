package com.example.springboot1.service.Impl;

import com.example.springboot1.model.UserModel;
import com.example.springboot1.repository.RoleRepository;
import com.example.springboot1.repository.UserRepository;
import com.example.springboot1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    /* lưu các set giá trị password đưcọ mã hóa và tìm cảu user*/
    public void save(UserModel userModel) {
        userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
        userModel.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(userModel);
    }

    @Override
    /* Tìm tên user*/
    public UserModel findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }
}
