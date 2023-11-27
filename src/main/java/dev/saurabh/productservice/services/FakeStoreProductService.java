package dev.saurabh.productservice.services;

import dev.saurabh.productservice.dtos.FakeStoreProductDto;
import dev.saurabh.productservice.dtos.GenericProductDto;
import dev.saurabh.productservice.exceptions.NotFoundException;
import dev.saurabh.productservice.thirdPartyClients.productsservice.fakestore.FakeStoreProductServiceClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{

    private FakeStoreProductServiceClient fakeStoreProductServiceClient;

    public FakeStoreProductService(FakeStoreProductServiceClient fakeStoreProductServiceClient){
        this.fakeStoreProductServiceClient = fakeStoreProductServiceClient;
    }

    private GenericProductDto convertFakeStoreProductIntoGeneric(FakeStoreProductDto fakeStoreProductDto){
        GenericProductDto product = new GenericProductDto();

        product.setCategory(fakeStoreProductDto.getCategory());
        product.setImage(fakeStoreProductDto.getImage());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setTitle(fakeStoreProductDto.getTitle());

        return product;
    }

    @Override
    public GenericProductDto getProductById(UUID id) throws NotFoundException {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductServiceClient.getProductById(id);

        if(fakeStoreProductDto == null){
            throw new NotFoundException("Product with id " + id + " is not found!!!");
        }

        return convertFakeStoreProductIntoGeneric(fakeStoreProductDto);
    }

    public GenericProductDto createProduct(GenericProductDto product){
        return fakeStoreProductServiceClient.createProduct(product);
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<FakeStoreProductDto> response = fakeStoreProductServiceClient.getAllProducts();
        List<GenericProductDto> answer = new ArrayList<>();

        for(FakeStoreProductDto fakeStoreProductDto : response){
            answer.add(convertFakeStoreProductIntoGeneric(fakeStoreProductDto));
        }

        return answer;
    }

    @Override
    public GenericProductDto deleteProductById(UUID id) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductServiceClient.deleteProductById(id);

        return convertFakeStoreProductIntoGeneric(fakeStoreProductDto);
    }

    @Override
    public GenericProductDto updateProductById(UUID id, GenericProductDto product) {
        return fakeStoreProductServiceClient.updateProductById(id , product);
    }
}
