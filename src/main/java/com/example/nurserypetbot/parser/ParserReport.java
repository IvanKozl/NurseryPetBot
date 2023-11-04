package com.example.nurserypetbot.parser;

import com.example.nurserypetbot.enums.PetShelter;
import com.example.nurserypetbot.models.Report;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserReport {
    public static String parserReportString = ("([рацион]+\\s.+)(\\s)([самочувствие]+\\s.+)(\\s)([поведение]+\\s.+)");

    public static Report tryToParseReport(String text) {
        Pattern pattern =
                Pattern.compile(parserReportString);

        Matcher matcher = pattern.matcher(text);
        Report report = new Report();
        if (matcher.matches()) {
            report.setFood(matcher.group(1));
            report.setFeel(matcher.group(3));
            report.setBehavior(matcher.group(5));
        } else {
            throw new IllegalArgumentException("Wrong format, please, try again." +
                    "If you have some problems, please, find MENU and instructions ");
        }
        return report;

    }

    public static String getParserReportString() {
        return parserReportString;
    }

    public void setParserReportString(String parserReportString) {
        this.parserReportString = parserReportString;
    }
}
