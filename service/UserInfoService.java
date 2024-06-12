package com.userloginexample.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.userloginexample.entity.UserInfo;
import com.userloginexample.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder; 

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfoOptional = repository.findByusername(username);
        UserInfo userInfo = userInfoOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Instead of returning userInfo.getAuthorities(), return a fixed set of authorities
        return new User(userInfo.getUsername(), userInfo.getPassword(), Collections.emptyList());
    }

    public String addUser(UserInfo userInfo) {
        Optional<UserInfo> existingUser = repository.findByusername(userInfo.getUsername());
        if(existingUser.isPresent()) {
            return "User already exists";
        }
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }
    
    public List<UserInfo> getAllUsers() {
        return repository.findAll();
    }
    
    public UserInfo getUserById(int userId) {
        Optional<UserInfo> userOptional = repository.findById(userId);
        return userOptional.orElse(null);
    }
    
    public UserInfo updateUser(int userId, UserInfo userInfoDetails) {
        Optional<UserInfo> userOptional = repository.findById(userId);
        if (!userOptional.isPresent()) {
            return null;
        }
        UserInfo existingUser = userOptional.get();
        existingUser.setUsername(userInfoDetails.getUsername());
        existingUser.setEmail(userInfoDetails.getEmail());
        existingUser.setPassword(encoder.encode(userInfoDetails.getPassword()));
        return repository.save(existingUser);
    }
    
    public void deleteUser(int userId) {
        repository.deleteById(userId);
    }
}
