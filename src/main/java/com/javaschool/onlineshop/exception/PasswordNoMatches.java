package com.javaschool.onlineshop.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PasswordNoMatches extends RuntimeException{
    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
