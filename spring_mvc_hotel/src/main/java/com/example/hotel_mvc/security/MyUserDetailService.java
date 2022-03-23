package com.example.hotel_mvc.security;

import com.example.hotel_mvc.model.entity.Systemmanager;
import com.example.hotel_mvc.model.repository.database.SystemManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private SystemManagerRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Systemmanager systemmanager = repo.findByEmail(email);
        if(systemmanager == null){
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(systemmanager);
    }
}
