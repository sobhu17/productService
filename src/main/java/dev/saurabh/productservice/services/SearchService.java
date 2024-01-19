package dev.saurabh.productservice.services;

import dev.saurabh.productservice.dtos.GenericProductDto;
import dev.saurabh.productservice.models.Product;
import dev.saurabh.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private ProductRepository productRepository;

    public SearchService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Page<GenericProductDto> searchProducts(String query , int pageNumber , int sizeOfEachPage){
        Page<Product> productPage = productRepository.findAllByTitleContaining(query , PageRequest.of(pageNumber , sizeOfEachPage));

        List<Product> products = productPage.get().collect(Collectors.toList());
        List<GenericProductDto> genericProductDtos = new ArrayList<>();

        for(Product product : products){
            genericProductDtos.add(convertToGenericProductDto(product));
        }

        Page<GenericProductDto> genericProductDtoPage = new PageImpl<>(
                genericProductDtos , productPage.getPageable() , productPage.getTotalElements()
        );

        return genericProductDtoPage;
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
