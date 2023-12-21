package dev.saurabh.productservice.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.saurabh.productservice.dtos.GenericProductDto;
import dev.saurabh.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAllProductsReturnEmptyListWhenNoProduct() throws Exception {
        when(productService.getAllProducts())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/products/"))
                .andExpect(status().is(200))
                .andExpect(content().string("[]"));
    }

    @Test
    public void returnListOfProductsWhenProductExist() throws Exception {
        List<GenericProductDto> products = new ArrayList<>();
        products.add(new GenericProductDto());
        products.add(new GenericProductDto());
        products.add(new GenericProductDto());

        when(productService.getAllProducts())
                .thenReturn(products);

        mockMvc.perform(get("/api/v1/products/"))
                .andExpect(status().is(200))
                .andExpect(content().string(objectMapper.writeValueAsString(products)));
    }

    @Test
    public void createProductShouldCreateProductAndReturnIt() throws Exception {
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setTitle("Iphone 12 Pro");
        genericProductDto.setPrice(121221);
        genericProductDto.setImage("hello hello I am a image");
        genericProductDto.setCategory("electronic");
        genericProductDto.setDescription("haha hahahah");

        GenericProductDto expectedProduct = new GenericProductDto();
        expectedProduct.setId(101L);
        expectedProduct.setTitle("Iphone 12 Pro");
        expectedProduct.setPrice(121221);
        expectedProduct.setImage("hello hello I am a image");
        expectedProduct.setCategory("electronic");
        expectedProduct.setDescription("haha hahahah");

        when(productService.createProduct(any()))
                .thenReturn(expectedProduct);

        mockMvc.perform(post("/api/v1/products/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(genericProductDto))
        ).andExpect(content().string(objectMapper.writeValueAsString(expectedProduct)));

    }
}
