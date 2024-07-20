package com.example.springboot_shop_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {

    @JsonProperty("phone_number")
    @NotBlank(message="Phone Number is required")
    private String phoneNumber;

    @NotBlank(message="Message cannot be blank")
    private String password;
}
