package dev.saurabh.productservice.thirdPartyClients.productsservice.fakestore;

import dev.saurabh.productservice.dtos.FakeStoreProductDto;
import dev.saurabh.productservice.dtos.GenericProductDto;
import dev.saurabh.productservice.exceptions.NotFoundException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FakeStoreProductServiceClient {

    private RestTemplateBuilder restTemplateBuilder;
    private String specificProductRequestUrl = "https://fakestoreapi.com/products/{id}";
    private String productRequestBaseUrl = "https://fakestoreapi.com/products";

    public FakeStoreProductServiceClient(RestTemplateBuilder restTemplateBuilder){
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public FakeStoreProductDto getProductById(UUID id) throws NotFoundException {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(specificProductRequestUrl, FakeStoreProductDto.class , id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        if(fakeStoreProductDto == null){
            throw new NotFoundException("Product with id " + id + " is not found!!!");
        }

        return fakeStoreProductDto;
    }

    public GenericProductDto createProduct(GenericProductDto product){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<GenericProductDto> response = restTemplate.postForEntity(
                productRequestBaseUrl, product , GenericProductDto.class);

        return response.getBody();
    }

    public List<FakeStoreProductDto> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(productRequestBaseUrl , FakeStoreProductDto[].class);

        List<FakeStoreProductDto> answer = new ArrayList<>();

        for(FakeStoreProductDto fakeStoreProductDto : response.getBody()){
            answer.add(fakeStoreProductDto);
        }

        return answer;
    }

    public FakeStoreProductDto deleteProductById(UUID id) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(specificProductRequestUrl, HttpMethod.DELETE, requestCallback, responseExtractor, id);

        FakeStoreProductDto fakeStoreProductDto = response.getBody();

        return fakeStoreProductDto;
    }

    public GenericProductDto updateProductById(UUID id, GenericProductDto product) {
        RestTemplate  restTemplate = restTemplateBuilder.build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GenericProductDto> requestEntity = new HttpEntity<>(product, headers);

        // Execute the request
        ResponseEntity<GenericProductDto> response = restTemplate.exchange(
                specificProductRequestUrl,
                HttpMethod.PUT,
                requestEntity,
                GenericProductDto.class,
                id
        );

        return response.getBody();
    }
}
