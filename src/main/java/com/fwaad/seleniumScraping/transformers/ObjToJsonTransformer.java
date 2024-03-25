/*
 * File: ObjToJsonTransformer
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package com.fwaad.seleniumScraping.transformers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class ObjToJsonTransformer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public <T> String transform(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public <T> void transformAndWrite(String fileName, T obj) {
        String content = transform(obj);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                "C:\\Users\\kittu\\Desktop\\MAC\\ACC\\Assignment 3\\seleniumScraping\\src\\main\\java\\com\\fwaad\\seleniumScraping\\output\\" + fileName + ".json"))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
    }
}
