package com.example.culinaryblogapi.controller;

import com.example.culinaryblogapi.dto.CategoryDto;
import com.example.culinaryblogapi.model.Category;
import com.example.culinaryblogapi.service.CategoryService;
import com.example.culinaryblogapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/add")
    public ResponseEntity<?> add (
            @RequestBody CategoryDto categoryDto
    ) {
        Category category = categoryService.findCategoryByName(categoryDto.getName());
        if(categoryService.findCategoryByName(categoryDto.getName()) == null) {
            return ResponseEntity.ok(categoryService.addCategory(Category.builder()
                    .createdBy(userService.findUserById(categoryDto.getCreatedByUserId()).orElseThrow())
                    .name(categoryDto.getName())
                    .ordinalNr(categoryDto.getOrdinalNr())
                    .isVisible(categoryDto.getIsVisible())
                    .createdDate(LocalDateTime.now())
                    .build()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category with name: " + categoryDto.getName() + " already exist!");
        }
    }

    @DeleteMapping("/remove/{categoryId}")
    public ResponseEntity<?> remove (
            @PathVariable long categoryId
    ) {
        if(categoryService.findCategoryById(categoryId) != null){
            return ResponseEntity.ok(categoryService.deleteCategoryById(categoryId));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category with id: " + categoryId + " not found");
        }
    }

    @PutMapping("/edit/{categoryId}")
    public ResponseEntity<?> edit (
            @PathVariable("categoryId") long categoryId, @RequestBody CategoryDto categoryDto
    ) {
        Category category = categoryService.findCategoryById(categoryId);
        if(category != null){
            category.setName(category.getName());
            category.setOrdinalNr(categoryDto.getOrdinalNr());
            category.setIsVisible(categoryDto.getIsVisible());
            return ResponseEntity.ok(categoryService.save(category));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category with id: " + categoryId + " not found");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getAllCategories () {
        List<Category> categories = categoryService.getAll();
        List<CategoryDto> categoryDtos = categories.stream()
                .map(this::convertToDto)
                .filter(c -> c.getIsVisible() == 1)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDtos);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById (@PathVariable long categoryId) {
        Category category = categoryService.findCategoryById(categoryId);
        if(category != null){
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category with id: " + categoryId + " not found");
        }
    }

//    @PostMapping("/changeOrder")
//    public ResponseEntity<Category> changeOrder (
//            @RequestBody Map<Integer, Integer> order
//    ) {
//        List<Category> categories = categoryService.getAll();
//        categories.stream().
//        category.setCreatedBy(userService.findUserById(category.getCreatedBy().getId()).orElseThrow());
//        return ResponseEntity.ok(categoryService.addCategory(category));
//    }

    private CategoryDto convertToDto(Category category) {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        categoryDto.setCreatedByUserId(category.getId());
        return categoryDto;
    }
}
