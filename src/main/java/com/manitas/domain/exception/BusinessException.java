package com.manitas.domain.exception;

public class BusinessException extends Exception{
    public BusinessException() { super(); }
    public BusinessException(String message) { super(message); }
    public BusinessException(String message, Throwable throwable) { super(message, throwable); }
}
