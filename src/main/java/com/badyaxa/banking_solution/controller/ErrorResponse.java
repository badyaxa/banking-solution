package com.badyaxa.banking_solution.controller;

public class ErrorResponse {
    private ErrorCode code;
    private String message;

    public ErrorResponse(ErrorCode code, String message) {
        this.code = code;
        this.message = message;
    }
}
