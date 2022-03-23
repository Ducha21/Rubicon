package com.example.springboot1.service.Impl;

import com.example.springboot1.model.RoleModel;
import com.example.springboot1.model.UserModel;
import com.example.springboot1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username);
        /* xem username có tồn tại hay không*/
        if (userModel == null) {
            System.out.println("Username không tồn tại!");
            throw new UsernameNotFoundException(username);
        }
        /* duyệt xem user đó những quyền gì */
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (RoleModel roleModel : userModel.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roleModel.getName()));
        }
        return new org.springframework.security.core.userdetails.User(userModel.getUsername(), userModel.getPassword(), grantedAuthorities);
    }
}
