package com.blog.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    String resourceName ;
    String fieldName;
    Long fieldValue;
    String stringFieldValue;

    public ResourceNotFoundException(String resourceName,String fieldName,Long fieldValue)
    {
        super(String.format("%s not found with %s : %s ",resourceName,fieldName,fieldValue));
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String stringFieldValue) {
        super(String.format("%s not found with %s : %s ", resourceName, fieldName, stringFieldValue));
    }


}
