package dev.saurabh.productservice.services;

import dev.saurabh.productservice.dtos.GenericProductDto;

import java.util.List;

public interface CategoryService {

    public List<String> getAllCategories();

    public List<GenericProductDto> getProductsByCategoryName(String categoryName);

}
