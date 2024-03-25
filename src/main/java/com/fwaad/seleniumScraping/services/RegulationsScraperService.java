/*
 * File: RegulationsScraperService
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package com.fwaad.seleniumScraping.services;

import com.fwaad.seleniumScraping.model.Regulation;
import com.fwaad.seleniumScraping.transformers.ObjToJsonTransformer;
import com.fwaad.seleniumScraping.transformers.RegulationsTransformer;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getDriver;

@Service
@AllArgsConstructor
public class RegulationsScraperService {

    public static final String URL = "https://codes.findlaw.com/{option}/";
    public static final List<String> URL_OPTIONS = List.of("cfr", "us");
    private final ChromeDriver driver;
    private final ObjToJsonTransformer objToJsonTransformer;

    @PostConstruct
    void postConstruct() {
        scrape();
    }

    public void scrape() {

        URL_OPTIONS.forEach(option -> {

            driver.get(URL.replace("{option}", option));

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("landingContent")));

            List<WebElement> titleElements = element.findElement(By.tagName("ul")).findElements(By.tagName("li"));

            List<Regulation> regulationList = titleElements.stream()
                                                           .map(webElement -> new Regulation(webElement.getText()
                                                                                                       .substring(
                                                                                                               webElement.getText()
                                                                                                                         .indexOf(
                                                                                                                                 ".") + 1)
                                                                                                       .trim(),
                                                                                             RegulationsTransformer.extractLinkFromTitle(
                                                                                                     webElement),
                                                                                             new ArrayList<>()))
                                                           .toList();

            regulationList.forEach(regulation -> {
                driver.get(regulation.getUrl());

                WebElement content = driver.findElement(By.className("codesTocContent"));

                regulation.getChapters().addAll(RegulationsTransformer.extractChapterNames(content));
            });

            objToJsonTransformer.transformAndWrite(option + "regulations", regulationList);
        });
        driver.quit();
    }
}
