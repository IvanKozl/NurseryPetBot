package com.example.nurserypetbot.parser;

import com.example.nurserypetbot.models.UsersContactInformation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserUserContactInfo {

    public static String parserInfoString = ("(\\w+)(\\s)([A-Za-zА-Яа-я]+)(\\s)([A-Za-zА-Яа-я]+)(\\s)(\\d+)(\\s)(\\d{11})(\\s+)([A-Za-z\\d@\\.]+)");

    public static UsersContactInformation tryToParseUsersInformation(String text) {
        Pattern pattern = Pattern.compile(parserInfoString);

        Matcher matcher = pattern.matcher(text);
        UsersContactInformation usersContactInformation = new UsersContactInformation();

        if (matcher.matches()) {
            usersContactInformation.setPetShelterType(matcher.group(1));
            usersContactInformation.setName(matcher.group(3));
            usersContactInformation.setSurname(matcher.group(5));
            usersContactInformation.setAge(Integer.parseInt(matcher.group(7)));
            usersContactInformation.setPhoneNumber(Long.parseLong(matcher.group(9)));
            usersContactInformation.setEmail(matcher.group(11));
        } else {
            throw new IllegalArgumentException("Wrong format, please, try again");
        }
        return usersContactInformation;
    }

    public static String getParserInfoString() {
        return parserInfoString;
    }

    public ParserUserContactInfo() {
    }

    public void setParserInfoString(String parserInfoString) {
        this.parserInfoString = parserInfoString;
    }
}
