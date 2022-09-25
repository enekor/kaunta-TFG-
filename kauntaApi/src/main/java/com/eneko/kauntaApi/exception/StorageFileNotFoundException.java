package com.eneko.kauntaApi.exception;

public class StorageFileNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 8482217129851689197L;

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
