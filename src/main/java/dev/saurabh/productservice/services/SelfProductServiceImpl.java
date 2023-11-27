package dev.saurabh.productservice.services;

import dev.saurabh.productservice.dtos.GenericProductDto;
import dev.saurabh.productservice.exceptions.NotFoundException;
import dev.saurabh.productservice.models.Category;
import dev.saurabh.productservice.models.CategoryType;
import dev.saurabh.productservice.models.Product;
import dev.saurabh.productservice.repository.CategoryRepository;
import dev.saurabh.productservice.repository.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Primary
@Service("selfProductServiceImp")
public class SelfProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductServiceImpl(ProductRepository productRepository , CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public GenericProductDto getProductById(UUID id) throws NotFoundException {
        Product product = productRepository.findById(id).get();

        if(product == null){
            throw new NotFoundException("Product with id " + id + " isn't available!!");
        }

        GenericProductDto genericProductDto = convertToGenericProductDto(product);
        return genericProductDto;
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto genericProductDto){
        Product product = convertToProductDto(genericProductDto);
        productRepository.save(product);
        return genericProductDto;
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<GenericProductDto> genericProducts = new ArrayList<>();

        for(Product product : products){
            GenericProductDto genericProductDto = convertToGenericProductDto(product);
            genericProducts.add(genericProductDto);
        }

        return genericProducts;
    }

    @Override
    public GenericProductDto deleteProductById(UUID id) {
        Product product = productRepository.findById(id).get();
        GenericProductDto genericProductDto = convertToGenericProductDto(product);
        productRepository.deleteById(id);

        return genericProductDto;
    }

    @Override
    public GenericProductDto updateProductById(UUID id, GenericProductDto genericProductDto) {
        Product product = convertToProductDto(genericProductDto);
        product.setId(id);
        productRepository.save(product);
        return genericProductDto;
    }

    private Product convertToProductDto(GenericProductDto genericProductDto) {
        Product product = new Product();
        product.setTitle(genericProductDto.getTitle());
        product.setPrice(genericProductDto.getPrice());
        product.setImage(genericProductDto.getImage());
        product.setDescription(product.getDescription());

        Category ct = categoryRepository.findCategoryByName(genericProductDto.getCategory());

        if(ct != null){
            product.setCategory(ct);
            return product;
        }

        Category category = new Category();
        category.setName(genericProductDto.getCategory());
        product.setCategory(category);

        return product;
    }
    private GenericProductDto convertToGenericProductDto(Product product){
        GenericProductDto genericProductDto = new GenericProductDto();

        genericProductDto.setCategory(product.getCategory().getName());
        genericProductDto.setImage(product.getImage());
        genericProductDto.setDescription(product.getDescription());
        genericProductDto.setPrice(product.getPrice());
        genericProductDto.setTitle(product.getTitle());

        return genericProductDto;
    }
}
