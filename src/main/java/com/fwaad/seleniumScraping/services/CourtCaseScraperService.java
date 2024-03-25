/*
 * File: CourtCaseScraper
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package com.fwaad.seleniumScraping.services;

import com.fwaad.seleniumScraping.model.CourtCase;
import com.fwaad.seleniumScraping.transformers.CourtCaseTransformer;
import com.fwaad.seleniumScraping.transformers.ObjToJsonTransformer;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CourtCaseScraperService {
    public static final String URL = "https://www.supremecourt.gov/opinions/slipopinion/";
    public static final String URL_2 = "https://caselaw.findlaw.com/court/{option}/recent";
    public static final List<String> URL_2_OPTIONS = List.of("us-1st-circuit",
                                                             "us-2nd-circuit",
                                                             "us-3rd-circuit",
                                                             "us-dc-circuit");

    private final ChromeDriver driver;
    private final ObjToJsonTransformer objToJsonTransformer;

    @PostConstruct
    void postConstruct() {
        scrape();
    }

    public void scrape() {
        List<String> years = List.of("23");
        List<CourtCase> courtCaseList = new ArrayList<>();
        years.forEach(year -> {
            driver.get(URL + year);

            WebElement cellTop10 = driver.findElement(By.id("cellTop10"));
            courtCaseList.addAll(CourtCaseTransformer.transformSupremeCourtGov(cellTop10));

            WebElement button = driver.findElement(By.className("accordion-toggle"));
            button.click();

            WebElement cellMore = driver.findElement(By.id("cellMore"));
            courtCaseList.addAll(CourtCaseTransformer.transformSupremeCourtGov(cellMore));

        });


        URL_2_OPTIONS.forEach(option -> {
            driver.get(URL_2.replace("{option}", option));
            try {
                WebElement casesTable = driver.findElement(By.className("responsive-card-table"));
                courtCaseList.addAll(CourtCaseTransformer.transformFindLaw(casesTable, option));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        objToJsonTransformer.transformAndWrite("courtCases", courtCaseList);
        driver.quit();
    }
}
