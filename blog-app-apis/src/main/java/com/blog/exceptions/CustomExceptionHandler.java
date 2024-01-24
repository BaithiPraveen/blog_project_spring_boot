package com.blog.exceptions;

public class CustomExceptionHandler extends RuntimeException{

    public CustomExceptionHandler(String msg)
    {
        super(msg);
    }
}
