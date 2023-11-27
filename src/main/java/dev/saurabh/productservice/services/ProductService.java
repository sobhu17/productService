package dev.saurabh.productservice.services;

import dev.saurabh.productservice.dtos.GenericProductDto;
import dev.saurabh.productservice.exceptions.NotFoundException;
import dev.saurabh.productservice.models.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    public GenericProductDto getProductById(UUID id) throws NotFoundException;
    public GenericProductDto createProduct(GenericProductDto product);

    public List<GenericProductDto> getAllProducts();

    public GenericProductDto deleteProductById(UUID id);

    public GenericProductDto updateProductById(UUID  id , GenericProductDto product);
}
