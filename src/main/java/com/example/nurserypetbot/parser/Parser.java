package com.example.nurserypetbot.parser;

import com.example.nurserypetbot.models.Notification;
import com.example.nurserypetbot.models.UsersContactInformation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static UsersContactInformation tryToParseUsersInformation(String text){
        Pattern pattern =
                Pattern.compile("(info)(\\s)(\\W+)(\\s)(\\W+)(\\s)(\\d{2})(\\s)(\\d{11})(\\s)(\\w+@\\w+.\\w+)");


        Matcher mather = pattern.matcher(text);
        UsersContactInformation usersContactInformation = new UsersContactInformation();

        if(mather.matches()){
            usersContactInformation.setName(mather.group(3));
            usersContactInformation.setSurname(mather.group(5));
            usersContactInformation.setAge(Integer.parseInt(mather.group(7)));
            usersContactInformation.setPhoneNumber(Long.parseLong(mather.group(9)));
            usersContactInformation.setEmail(mather.group(11));
        } else {
            throw new IllegalArgumentException("Wrong format");
        }
        return new UsersContactInformation();

    }
}
