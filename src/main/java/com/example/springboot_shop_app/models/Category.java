package com.example.springboot_shop_app.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name="categories")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="name",nullable=false)
    private String name;


}
