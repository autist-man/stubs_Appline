package ru.appline.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;

public class UtilRequest {
    public static void setJSONContentTypeAndReqEncode(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error!");
            e.printStackTrace();
        }
        response.setContentType("application/json;charset=utf-8");
    }
    public static JsonObject getJsonObject(final HttpServletRequest request, Gson gson) {
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        try{
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null){
                stringBuffer.append(line);
            }
        }catch (Exception e){
            System.out.println("Error!");
            e.printStackTrace();
        }
        return gson.fromJson(String.valueOf(stringBuffer),JsonObject.class);
    }
}
