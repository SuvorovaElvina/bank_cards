package com.example.bankcards.util;

public class Mask {
    private static final String REGEX_NUMBER_CARD = "\\d(?=.{4})";

    public static String maskNumberCard(String number) {
        return number.replaceAll(REGEX_NUMBER_CARD, "*");
    }
}
