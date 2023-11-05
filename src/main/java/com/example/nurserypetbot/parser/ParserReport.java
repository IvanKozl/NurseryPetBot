package com.example.nurserypetbot.parser;

import com.example.nurserypetbot.enums.PetShelter;
import com.example.nurserypetbot.models.Report;
import com.example.nurserypetbot.repository.ReportRepository;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserReport {
    static ReportRepository reportRepository;

    public static String parserReportString = ("([рацион]+\\s.+)(\\s)([самочувствие]+\\s.+)(\\s)([поведение]+\\s.+)");

    public static Report tryToParseReport(String text) {
        Pattern pattern = Pattern.compile(parserReportString);
        String str = text.toLowerCase();

        LocalDate currentDate = LocalDate.now();
        Report report = reportRepository.findByDateTime(currentDate).orElse(new Report());

        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            report.setFood(matcher.group(1));
            report.setFeel(matcher.group(3));
            report.setBehavior(matcher.group(5));
            if (report.isFotoCheck()) {
                report.setReportCheck(true);
            }
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
