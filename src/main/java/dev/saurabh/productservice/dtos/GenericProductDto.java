package dev.saurabh.productservice.dtos;

import jdk.jfr.Category;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GenericProductDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;
}
