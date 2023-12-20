package dev.saurabh.productservice.Controllers;

import dev.saurabh.productservice.dtos.GenericProductDto;
import dev.saurabh.productservice.models.Product;
import dev.saurabh.productservice.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories/")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<String> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("{categoryName}")
    public List<GenericProductDto> getProductsByCategoryName(@PathVariable("categoryName") String categoryName){
        return categoryService.getProductsByCategoryName(categoryName);
    }

}
