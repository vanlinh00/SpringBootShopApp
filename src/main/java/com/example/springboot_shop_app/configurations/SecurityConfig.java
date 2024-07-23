package com.example.springboot_shop_app.configurations;


import com.example.springboot_shop_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    //User's detail object
    @Bean
    public UserDetailsService userDetailService() {

        // đầu vào là một trường duy nhất của user
        // trường sdt sẽ lưu và username của user detail
        return /*phone*/Number -> userRepository
                .findByPhoneNumber(/*phone*/Number)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Cannot find user with phone number =" + Number)
                );
      // nếu tìm thấy trả về giá trị nếu không lắm exception
    }

    @Bean  // mã hõa mật khẩu SHA256
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // trong này đã có thuật toán mã hõa rồi
    }



// authencation Provider
    // cần  userdetail và password
    // đây là hàm khởi tạo 2 thắng kia
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }
}
