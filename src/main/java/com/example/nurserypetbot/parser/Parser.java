package com.example.nurserypetbot.parser;

import com.example.nurserypetbot.models.UsersContactInformation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static UsersContactInformation tryToParseUsersInformation(String text) {
        Pattern pattern =
                Pattern.compile("([A-Za-zА-Яа-я]+)(\\s)([A-Za-zА-Яа-я]+)(\\s)(\\d+)(\\s)(\\d{11})(\\s+)([A-Za-z\\d@\\.]+)");

        Matcher matcher = pattern.matcher(text);
        UsersContactInformation usersContactInformation = new UsersContactInformation();

        if (matcher.matches()) {

            usersContactInformation.setName(matcher.group(1));
            usersContactInformation.setSurname(matcher.group(3));
            usersContactInformation.setAge(Integer.parseInt(matcher.group(5)));
            usersContactInformation.setPhoneNumber(Long.parseLong(matcher.group(7)));
            usersContactInformation.setEmail(matcher.group(9));

        } else {
            throw new IllegalArgumentException("Wrong format");
        }
        return usersContactInformation;
    }
}
