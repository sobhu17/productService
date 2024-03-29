package dev.saurabh.productservice.services;

import dev.saurabh.productservice.dtos.GenericProductDto;
import dev.saurabh.productservice.exceptions.NotFoundException;
import dev.saurabh.productservice.models.Category;
import dev.saurabh.productservice.models.Product;
import dev.saurabh.productservice.repository.CategoryRepository;
import dev.saurabh.productservice.repository.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Service("selfProductServiceImp")
public class SelfProductServiceImpl implements ProductService{

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private RedisTemplate<String , Object> redisTemplate;

    public SelfProductServiceImpl(ProductRepository productRepository , CategoryRepository categoryRepository , RedisTemplate<String  , Object> redisTemplate){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public GenericProductDto getProductById(UUID id) throws NotFoundException {
        GenericProductDto cacheGenericProductDto = (GenericProductDto) redisTemplate.opsForHash().get("PRODUCTS" , id);

        if(cacheGenericProductDto != null){
            System.out.println("Fetching From Cache!!!");
            return cacheGenericProductDto;
        }

        Optional<Product> op = productRepository.findById(id);

        if(op.isEmpty() == true){
            throw new NotFoundException("Product with id " + id + " isn't available!!");
        }

        Product product = op.get();

        GenericProductDto genericProductDto = convertToGenericProductDto(product);

        redisTemplate.opsForHash().put("PRODUCTS" , id , genericProductDto);

        System.out.println("Fetching from DB!!!");

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
    public GenericProductDto deleteProductById(UUID id) throws NotFoundException {
        Optional<Product> op = productRepository.findById(id);

        if(op.isEmpty() == true){
            throw new NotFoundException("Product with id " + id + " isn't available!!");
        }

        Product product = op.get();
        GenericProductDto genericProductDto = convertToGenericProductDto(product);
        productRepository.deleteById(id);

        return genericProductDto;
    }

    @Override
    public GenericProductDto updateProductById(UUID id, GenericProductDto genericProductDto) throws NotFoundException {
        Optional<Product> op = productRepository.findById(id);

        if(op.isEmpty() == true){
            throw new NotFoundException("Product with id " + id + " isn't available!!");
        }

        Product product = op.get();
        product.setId(id);
        product.setTitle(genericProductDto.getTitle());
        product.setPrice(genericProductDto.getPrice());
        product.setImage(genericProductDto.getImage());
        product.setDescription(product.getDescription());

        if(!product.getCategory().getName().equals(genericProductDto.getCategory())){
            Category category = new Category();
            category.setName(genericProductDto.getCategory());
            product.setCategory(category);
        }

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
