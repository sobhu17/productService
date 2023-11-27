package dev.saurabh.productservice.Controllers;

import dev.saurabh.productservice.dtos.ExceptionDto;
import dev.saurabh.productservice.dtos.GenericProductDto;
import dev.saurabh.productservice.exceptions.NotFoundException;
import dev.saurabh.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products/")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<GenericProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public GenericProductDto getProductById(@PathVariable("id") UUID id) throws NotFoundException {
        return productService.getProductById(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<GenericProductDto> deleteProductById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(productService.deleteProductById(id), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public GenericProductDto createProduct(@RequestBody GenericProductDto product){
        return productService.createProduct(product);
    }

    @PutMapping("{id}")
    public GenericProductDto updateProductById(@PathVariable("id") UUID id , @RequestBody GenericProductDto product){
        return productService.updateProductById(id , product);
    }
}