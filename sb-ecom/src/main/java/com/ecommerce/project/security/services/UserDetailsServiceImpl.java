package com.ecommerce.project.security.services;

import com.ecommerce.project.model.User;
import com.ecommerce.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // Fetching the userdetails from database using our user model/entity and return the object of our User Model
        User user = userRepository.findByUserName(username)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found with username: " + username));

        // Spring Security expects it in its user model or its UserDetails interface, we're returning object of its implementation class object.
        return UserDetailsImpl.build(user);
    }
}
