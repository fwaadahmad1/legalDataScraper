/*
 * File: SeleniumConfig
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package com.fwaad.seleniumScraping.config;

import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Bean
    public ChromeDriver driver() {
        return new ChromeDriver();
    }
}
