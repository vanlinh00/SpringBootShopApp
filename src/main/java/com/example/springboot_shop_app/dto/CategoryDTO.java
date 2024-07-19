package com.example.springboot_shop_app.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
     @NotEmpty(message="Category's name cannot be emty")
    private String name;
}
