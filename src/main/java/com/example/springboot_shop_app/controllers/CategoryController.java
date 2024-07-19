package com.example.springboot_shop_app.controllers;

import com.example.springboot_shop_app.dto.CategoryDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/v1/categories")
public class CategoryController {
    @GetMapping("")
    public ResponseEntity<?> GetAllCategory(
            @RequestParam("page") int page
            , @RequestParam("limit") int limit) {
        return ResponseEntity.ok(String.format("GetAllCategory,page=%d,limit=%d", page, limit));
    }

    @PostMapping("")
    public ResponseEntity<?> InsertCatergory(
            @Valid
            @RequestBody CategoryDTO categoryDTO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getFieldErrors().
                    stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        return ResponseEntity.ok("this is insert category" + categoryDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> undateCategory(@PathVariable long id){
        return ResponseEntity.ok("InsertCategory with id= "+id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable long id){
        return ResponseEntity.ok("DeleteCategory with id= "+id);
    }
}
