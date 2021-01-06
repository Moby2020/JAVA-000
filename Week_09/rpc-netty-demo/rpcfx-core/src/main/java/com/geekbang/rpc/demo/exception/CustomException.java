package com.geekbang.rpc.demo.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
public class CustomException extends RuntimeException {
    private String message;

    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
    }
}
