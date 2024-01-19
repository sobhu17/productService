package dev.saurabh.productservice.dtos;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class SortParameter {
    private String parameterName;
    private String sortType;

}
