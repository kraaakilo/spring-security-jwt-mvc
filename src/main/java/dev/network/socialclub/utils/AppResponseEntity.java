package dev.network.socialclub.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class AppResponseEntity{
    private final String message;
    private final HttpStatusCode statusCode;
    public AppResponseEntity(String message,HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ResponseEntity<Map<String,Object>> buildResponse(){
        Map<String, Object> response = new HashMap<>();
        response.put("apiVersion", 1.0);
        response.put("message", this.message);
        return new ResponseEntity<>(response, this.statusCode);
    }
}
