package ru.flendger.demo.store.demo.store.exeptions;

public class CartEmptyException extends RuntimeException{
    public CartEmptyException(String message) {
        super(message);
    }
}
