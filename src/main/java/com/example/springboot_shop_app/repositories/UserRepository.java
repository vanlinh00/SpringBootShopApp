package com.example.springboot_shop_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.springboot_shop_app.models.*;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean exitsByPhoneNumber(String phoneNumber);

    Optional<User> findByPhoneNumber(String phoneNumber);
    //SELECT * FROM users WHERE phoneNumber=?;

}
