package dev.saurabh.productservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionDto {
    private HttpStatus httpStatus;
    private String messgae;

    public ExceptionDto(HttpStatus httpStatus , String messgae){
        this.httpStatus = httpStatus;
        this.messgae = messgae;
    }
}
