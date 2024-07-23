package com.example.springboot_shop_app.services;

import com.example.springboot_shop_app.components.JwtTokenUtil;
import com.example.springboot_shop_app.dto.UserDTO;
import com.example.springboot_shop_app.exceptions.DataNotFoundException;
import com.example.springboot_shop_app.models.Role;
import com.example.springboot_shop_app.models.User;
import com.example.springboot_shop_app.repositories.RoleRepository;
import com.example.springboot_shop_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSerivce implements IUserService {

    private final UserRepository userRepository;  // khi có final thì nó sẽ khởi tạo ngay khi Userservice này được tạo ra
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

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
            String encodePassword = passwordEncoder.encode(password);
            newUser.setPassword(encodePassword);

        }
        return userRepository.save(newUser);
    }

    //    @Override
//    public User login(String phoneNumber, String password) throws Exception {
//          Optional<User> optionalUser=  userRepository.findByPhoneNumber(phoneNumber);
//            if(optionalUser.isEmpty()){
//                throw new DataNotFoundException("Invalid phonenumber / password");
//            }
//        // Sprint Security
//        return optionalUser.get();// laasy ra doi tuong user  // muon tra ve token json web token
//
//    }
    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid phone number / password");
        }

        //return optionalUser.get();//muốn trả JWT token ?
        User existingUser = optionalUser.get();

        //check password
        if (existingUser.getFacebookAccountId() == 0
                && existingUser.getGoogleAccountId() == 0) {
            // khiểm tra password nó matches với password được mã hóa không
            if (!passwordEncoder.matches(password, existingUser.getPassword())) {
                throw new BadCredentialsException("Wrong phone number or password");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password,
                existingUser.getAuthorities() // Role user
        );  // tạo ra đôi tượng authenticationToken

        //authenticate with Java Spring security   // xác thực với sring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
