package com.example.utils;

import cn.hutool.core.convert.Convert;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class ServletUtils {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = getRequestAttributes();
        return requestAttributes.getRequest();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) requestAttributes;
    }

    public static Integer getParameterToInt(String parameterName, Integer defaultValue) {
        HttpServletRequest request = getRequest();
        String strValue = request.getParameter(parameterName);
        Integer intValue = Convert.toInt(strValue, defaultValue);
        return intValue;
    }

    public static String getParameterToString(String parameterName, String defaultValue) {
        HttpServletRequest request = getRequest();
        String strValue = request.getParameter(parameterName);
        String intValue = Convert.toStr(strValue, defaultValue);

        return intValue;
    }

    public static Integer getParameterToInt(String parameterName) {
        return getParameterToInt(parameterName, null);
    }

    public static String getParameterToString(String parameterName) {
        return getParameterToString(parameterName, null);
    }
}
