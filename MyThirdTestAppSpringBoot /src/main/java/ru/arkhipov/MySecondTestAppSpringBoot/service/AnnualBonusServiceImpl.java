package ru.arkhipov.MySecondTestAppSpringBoot.service;

import ru.arkhipov.MySecondTestAppSpringBoot.model.Positions;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Year;
import java.time.format.DateTimeParseException;

public class AnnualBonusServiceImpl {


    public double calculate(Positions position, double salary, double bonus, int workDays, String systemTime) {
        int year = extractYear(systemTime);
        int daysInYear = Year.isLeap(year) ? 366 : 365;

        double base = salary * bonus;
        double result = base * ((double) workDays / daysInYear);
        return Math.round(result * 1000000000000.0) / 1000000000000.0;
    }

    private int extractYear(String systemTime) {
        try {
            return OffsetDateTime.parse(systemTime).getYear();
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(systemTime).getYear();
            } catch (DateTimeParseException ex) {
                return LocalDate.now().getYear();
            }
        }
    }
}
