package com.example.miniaspireapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author devenderchaudhary
 */
@Data
@AllArgsConstructor
public class BaseResponse<T> {
    private int statusCode;
    private String message;
    private T payLoad;

    public BaseResponse(T payLoad) {
        statusCode = HttpStatus.OK.value();
        message = "SUCCESS";
        this.payLoad = payLoad;
    }

}
