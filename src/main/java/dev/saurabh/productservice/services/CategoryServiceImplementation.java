package dev.saurabh.productservice.services;

import dev.saurabh.productservice.dtos.GenericProductDto;
import dev.saurabh.productservice.models.Category;
import dev.saurabh.productservice.models.Product;
import dev.saurabh.productservice.repository.CategoryRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoryServiceImplementation")
@Primary
public class CategoryServiceImplementation implements CategoryService{

    private CategoryRepository categoryRepository;

    public CategoryServiceImplementation(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<String> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        List<String> answer = new ArrayList<>();

        for(Category category : categories){
            answer.add(category.getName());
        }

        return answer;
    }

    @Override
    public List<GenericProductDto> getProductsByCategoryName(String categoryName) {
        Category category = categoryRepository.findCategoryByName(categoryName);

        List<Product> products = category.getProducts();

        List<GenericProductDto> genericProductDtos = new ArrayList<>();

        for(Product product : products){
            GenericProductDto genericProductDto = new GenericProductDto();
            genericProductDto.setCategory(product.getCategory().getName());
            genericProductDto.setImage(product.getImage());
            genericProductDto.setDescription(product.getDescription());
            genericProductDto.setPrice(product.getPrice());
            genericProductDto.setTitle(product.getTitle());
            genericProductDtos.add(genericProductDto);
        }

        return genericProductDtos;
    }
}
