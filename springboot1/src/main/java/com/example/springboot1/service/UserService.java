package com.example.springboot1.service;

import com.example.springboot1.model.UserModel;


public interface UserService {
    /* lưu các thông tin user */
    void save(UserModel userModel);
    /*Tìm kiếm user theo tên*/
    UserModel findByUsername(String userName);
}
