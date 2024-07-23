package com.example.springboot_shop_app.controllers;

import com.example.springboot_shop_app.dto.UserDTO;
import com.example.springboot_shop_app.dto.UserLoginDTO;
import com.example.springboot_shop_app.exceptions.DataNotFoundException;
import com.example.springboot_shop_app.exceptions.PermissionDenyException;
import com.example.springboot_shop_app.models.Role;
import com.example.springboot_shop_app.repositories.RoleRepository;
import com.example.springboot_shop_app.services.IUserService;
import com.example.springboot_shop_app.services.UserSerivce;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.IToken;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserSerivce userService;
    private final RoleRepository roleRepository;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result
    ) {
        try {

            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
                return ResponseEntity.badRequest().body("Password does not match");
            }
            Role role = roleRepository.findById(userDTO.getRoleId())
                    .orElseThrow(() -> new DataNotFoundException("Role not found"));
            if (role.getName().toUpperCase().equals(Role.ADMIN)) {
                throw new PermissionDenyException("You cannot register an admin account");
            }
            userService.createUser(userDTO);
            return ResponseEntity.ok("Registers succesfully");
            // return ResponseEntity.ok("Register succesfully");

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid
            @RequestBody UserLoginDTO userLoginDTO) {
        // kiem tra thoong tin dang nhap
        // Tra ve token cho reponse
        //= null;
        try {
            String Token = userService.login(
                    userLoginDTO.getPhoneNumber()
                    , userLoginDTO.getPassword());
            return ResponseEntity.ok(Token);
        } catch (Exception e) {
            //  throw new RuntimeException(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        //  return ResponseEntity.ok("Some Token");
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(
//            @Valid @RequestBody UserLoginDTO userLoginDTO) {
//        // Kiểm tra thông tin đăng nhập và sinh token
//        try {
//            String token = userService.login(
//                    userLoginDTO.getPhoneNumber(),
//                    userLoginDTO.getPassword());
//            // Trả về token trong response
//            return ResponseEntity.ok(token);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
}
