package com.manitas.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ServletUtility {

    private ServletUtility() {
        throw new IllegalStateException("Utility class");
    }
    
    public static HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
        return null;
    }

    public static String getToken() {
        String authToken = null;
        HttpServletRequest context = ServletUtility.getCurrentHttpRequest();
        if (Objects.nonNull( context )) {
            authToken = context.getHeader(HttpHeaders.AUTHORIZATION);
        }
        return authToken;
    }
}
