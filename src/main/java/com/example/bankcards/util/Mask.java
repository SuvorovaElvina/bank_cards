package com.example.bankcards.util;

public class Mask {
    private static final String regForNumberCard = "\\d(?=.{4})";

    public static String maskNumberCard(String number) {
        return number.replaceAll(regForNumberCard, "*");
    }
}
