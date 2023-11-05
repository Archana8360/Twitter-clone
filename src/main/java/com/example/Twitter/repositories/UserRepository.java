package com.example.Twitter.repositories;

import com.example.Twitter.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser,Integer> {

    Optional<ApplicationUser> findByUsername(String username);
    Optional<ApplicationUser> findByEmailOrPhoneOrUsername(String email, String phone, String username);
}
