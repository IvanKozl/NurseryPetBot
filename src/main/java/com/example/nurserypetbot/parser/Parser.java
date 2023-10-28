package com.example.nurserypetbot.parser;

import com.example.nurserypetbot.enums.PetShelter;
import com.example.nurserypetbot.enums.Responses;
import com.example.nurserypetbot.models.UsersContactInformation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private Responses responses;

    public static String parserString = ("(\\w+)(\\s)([A-Za-zА-Яа-я]+)(\\s)([A-Za-zА-Яа-я]+)(\\s)(\\d+)(\\s)(\\d{11})(\\s+)([A-Za-z\\d@\\.]+)");

    public static UsersContactInformation tryToParseUsersInformation(String text) {
        Pattern pattern =
                Pattern.compile(parserString);

        Matcher matcher = pattern.matcher(text);
        UsersContactInformation usersContactInformation = new UsersContactInformation();

        if (matcher.matches()) {

            usersContactInformation.setPetShelterType(PetShelter.valueOf(matcher.group(1)));
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

    public static String getParserString() {
        return parserString;
    }

    public void setParserString(String parserString) {
        this.parserString = parserString;
    }
}
