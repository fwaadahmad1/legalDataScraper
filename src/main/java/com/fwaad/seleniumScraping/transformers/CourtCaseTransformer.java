/*
 * File: CourtCaseTransformer
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package com.fwaad.seleniumScraping.transformers;

import com.fwaad.seleniumScraping.model.CourtCase;
import lombok.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CourtCaseTransformer {
    private CourtCaseTransformer() {
        throw new IllegalStateException();
    }

    public static @NonNull List<CourtCase> transformSupremeCourtGov(@Nullable WebElement table) {
        if (table == null) return new ArrayList<>();
        List<CourtCase> courtCaseList = new ArrayList<>();
        final List<WebElement> rows = table.findElements(By.tagName("tr"));

        rows.forEach(row -> {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 6 && !cells.get(3).getText().isBlank())
                courtCaseList.add(new CourtCase(cells.get(3).getText(),
                                                cells.get(1).getText(),
                                                cells.get(2).getText(),
                                                "U.S. Supreme Court"));
        });

        return courtCaseList;
    }

    public static @NonNull List<CourtCase> transformFindLaw(@Nullable WebElement table, @NonNull String courtName) {
        if (table == null) return new ArrayList<>();
        List<CourtCase> courtCaseList = new ArrayList<>();
        final List<WebElement> rows = table.findElements(By.tagName("tr"));

        rows.forEach(row -> {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (cells.size() >= 3 && !cells.get(0).getText().isBlank())
                courtCaseList.add(new CourtCase(cells.get(0).getText(),
                                                cells.get(1).getText(),
                                                cells.get(2).getText().replaceAll("No.", "").trim(),
                                                courtName));
        });

        return courtCaseList;
    }
}
