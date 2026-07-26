package com.rezendev.faturamento_manager_api.exception;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException () {
        super("ID não encontrado.");
    }
}
