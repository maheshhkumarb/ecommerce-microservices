package com.ecommerce.orderservice.exceptionhandler;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message){
        super(message);
    }

}
