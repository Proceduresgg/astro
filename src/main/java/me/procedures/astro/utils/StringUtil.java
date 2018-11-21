package me.procedures.astro.utils;

public class StringUtil {

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
