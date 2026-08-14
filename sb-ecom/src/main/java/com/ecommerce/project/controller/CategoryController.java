package com.ecommerce.project.controller;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("api/public/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("api/public/categories")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);
        //return "Category added successfully";
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Long categoryId) {
       try {
           String status = categoryService.deleteCategory(categoryId);
           //return new ResponseEntity<>(status, HttpStatus.OK);
           //return ResponseEntity.ok(status); // another way to handle response status code
           return ResponseEntity.status(HttpStatus.OK).body(status);
       } catch (ResponseStatusException e) { // handling status code
           return new ResponseEntity<>(e.getReason(), e.getStatusCode());
       }
    }

    @PutMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category,
                                                 @PathVariable Long categoryId) {
      try {
          Category savedCategory = categoryService.updateCategory(category, categoryId);
          return new ResponseEntity<>("Category with category id: " + categoryId + " updated successfully.", HttpStatus.OK);
      } catch(ResponseStatusException e){
          return new ResponseEntity<>(e.getReason(), e.getStatusCode());
      }
    }
}
