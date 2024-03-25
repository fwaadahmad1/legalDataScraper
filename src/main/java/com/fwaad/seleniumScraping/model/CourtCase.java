/*
 * File: CourtCases
 * Created By: Fwaad Ahmad
 * Created On: 25-03-2024
 */
package com.fwaad.seleniumScraping.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourtCase {
    String name;
    String decidedDate;
    String docket;
    String court;
}
