package org.taerock.apiserver.util;

public class CustomJWTException extends RuntimeException{

    public CustomJWTException(String msg){
        super((msg));
    }

}
