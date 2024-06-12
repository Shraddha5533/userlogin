package com.userloginexample.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.userloginexample.entity.UserInfo;

import java.util.Optional;

@Repository // interface is a spring data repository
public interface UserRepository extends JpaRepository<UserInfo, Integer> 
{
    Optional<UserInfo> findByusername(String username);
    //custom query  method to find a user by user name
}
