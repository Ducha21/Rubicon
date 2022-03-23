package com.example.hotelspringmvc.security;

import com.example.hotelspringmvc.model.entity.Systemmanager;
import com.example.hotelspringmvc.model.repository.SystemmanagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private SystemmanagerRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Systemmanager systemmanager = repo.findByEmail(email);
        if(systemmanager == null){
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(systemmanager);
    }
}
