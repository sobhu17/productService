package dev.saurabh.productservice.Controllers;

import dev.saurabh.productservice.dtos.GenericProductDto;
import dev.saurabh.productservice.exceptions.NotFoundException;
import dev.saurabh.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    @Test
    public void returnNullWhenProductDoesntExist() throws NotFoundException {

        when(productService.getProductById(any()))
                .thenReturn(null);

        GenericProductDto genericProductDto = productController.getProductById(UUID.fromString("17f88939-4ed2-4987-86a2-e1035b1e7b92"));

        assertNull(genericProductDto);
    }

    @Test
    public void shouldReturnTitleNameWithProductId() throws NotFoundException {
        GenericProductDto genericProductDto = new GenericProductDto();
        genericProductDto.setTitle("saurabh");

        when(productService.getProductById(any()))
                .thenReturn(genericProductDto);

        GenericProductDto genericProductDto1 = productController.getProductById(UUID.fromString("17f88939-4ed2-4987-86a2-e1035b1e7b92"));

        assertEquals("saurabh" , genericProductDto1.getTitle());

    }
}
