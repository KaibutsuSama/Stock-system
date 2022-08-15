package com.kaibutsusama.stock.system.common.exception;

public abstract class AbstractException extends Exception {
    private static final long serialVersionUUID = -1L;

    public AbstractException(String message) {
        super(message);
    }

}
