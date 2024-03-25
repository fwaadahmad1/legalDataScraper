/*
 * File: RegulationsTransformer
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package com.fwaad.seleniumScraping.transformers;

import com.fwaad.seleniumScraping.model.CourtCase;
import com.fwaad.seleniumScraping.model.Regulation;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RegulationsTransformer {

    private RegulationsTransformer() {
        throw new IllegalStateException();
    }

    public static List<String> extractChapterNames(WebElement webElement) {
        List<WebElement> webElementList = webElement.findElements(By.tagName("a"));

        return webElementList.stream().map(RegulationsTransformer::extractTextFromContent).toList();
    }

    public static String extractLinkFromTitle(WebElement li) {
        WebElement a = li.findElement(By.tagName("a"));
        return a.getAttribute("href");
    }

    public static String extractTextFromContent(WebElement element) {
        return element.findElement(By.tagName("ul")).findElement(By.tagName("li")).getText();
    }


}
