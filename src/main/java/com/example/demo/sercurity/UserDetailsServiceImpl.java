package com.example.demo.sercurity;

import com.example.demo.entity.User;
import com.example.demo.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try{
            User user = userRepository.findByEmailAndDelFlagIsFalse(email);
            return UserDetailsImpl.build(user);
        }catch (Exception e){
            throw new UsernameNotFoundException("User Not Found with username: " + email);
        }
    }

}