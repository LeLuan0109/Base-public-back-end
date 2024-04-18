package com.project.app.securitys.exceptionSecurity;
public class PermissionDenyException extends Exception{
    public PermissionDenyException(String message) {
        super(message);
    }
}