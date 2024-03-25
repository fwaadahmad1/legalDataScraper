/*
 * File: Regulations
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package com.fwaad.seleniumScraping.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Regulation {
    String title;
    String url;
    List<String> chapters;
}
