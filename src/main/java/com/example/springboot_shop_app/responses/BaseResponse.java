package com.example.springboot_shop_app.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass

public class BaseResponse {

    @JsonProperty("created_at")
    private LocalDateTime createAt;

    @JsonProperty("updated_at")
    private LocalDateTime updateAt;

}
