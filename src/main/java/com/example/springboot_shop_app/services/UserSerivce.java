package com.example.springboot_shop_app.services;

import com.example.springboot_shop_app.dto.UserDTO;
import com.example.springboot_shop_app.exceptions.DataNotFoundException;
import com.example.springboot_shop_app.models.Role;
import com.example.springboot_shop_app.models.User;
import com.example.springboot_shop_app.repositories.RoleRepository;
import com.example.springboot_shop_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSerivce implements IUserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();

        // Khiem tra xem so dien thoai da ton tai chua
        if (userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("phone number already exists");
        }

        // convert from UserDTO=> User

        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getGoogleAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() ->
                        new DataNotFoundException("Role not found"));
        newUser.setRole(role);

        // Khiem tra neu co AccountId, khong yeu cau password

        if (userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0) {
            String password = userDTO.getPassword();
            //Spring Security
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {

        // Sprint Security
        return "";
    }
}
